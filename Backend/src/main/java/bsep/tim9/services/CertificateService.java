package bsep.tim9.services;

import bsep.tim9.DTOs.EndUserCertificateDTO;
import bsep.tim9.DTOs.IntermediateCertificateDTO;
import bsep.tim9.exceptions.AliasAlreadyExistsException;
import bsep.tim9.model.Certificate;
import bsep.tim9.model.CertificateType;
import bsep.tim9.model.IssuerData;
import bsep.tim9.model.SubjectData;
import bsep.tim9.repositories.CertificateRepository;
import bsep.tim9.utilities.Base64KeyDecoder;
import bsep.tim9.utilities.CertificateGenerator;
import bsep.tim9.utilities.KeyStoreReader;
import bsep.tim9.utilities.KeyStoreWriter;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    @Value("${keystore_path}")
    private String keystorePath;

    @Value("${keystore_pass}")
    private String keystorePass;


    public String createEndUserCertificate(EndUserCertificateDTO endUserCertificateDTO) throws AliasAlreadyExistsException {
        Base64KeyDecoder base64KeyDecoder = new Base64KeyDecoder();
        PublicKey subjectPublicKey = base64KeyDecoder.decodePublicKey(endUserCertificateDTO.getSubjectPublicKey());

        X500Name subjectName = endUserCertificateDTO.getSubjectName().getX500Name();

        String issuerAlias = endUserCertificateDTO.getIssuerAlias();
        String subjectAlias = endUserCertificateDTO.getSubjectAlias();

        Date startDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.YEAR, 2);
        Date endDate = cal.getTime();

        KeyStoreReader keyStoreReader = new KeyStoreReader();
        if (keyStoreReader.checkIfAliasExists(keystorePath, keystorePass, subjectAlias)) {
            throw new AliasAlreadyExistsException("Alias already taken");
        }

        String subjectSerialNumber = UUID.randomUUID().toString();

        SubjectData subjectData = new SubjectData(subjectPublicKey, subjectName, subjectSerialNumber, startDate, endDate);
        IssuerData issuerData = keyStoreReader.readIssuerFromStore(keystorePath, issuerAlias, keystorePass.toCharArray(), keystorePass.toCharArray());

        CertificateGenerator cg = new CertificateGenerator();
        X509Certificate cert = cg.generateCertificate(subjectData, issuerData, false);

        KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
        keyStoreWriter.loadKeyStore(keystorePath, keystorePass.toCharArray());
        keyStoreWriter.writeCertificate(subjectAlias, cert);
        keyStoreWriter.saveKeyStore(keystorePath, keystorePass.toCharArray());

        return "Certificate created under alias '" + subjectAlias + "'";
    }

    @PostConstruct
    private void init() {
        System.out.println("\n\nADDING PROVIDER\n\n");
        Security.addProvider(new BouncyCastleProvider());
    }

    public Object createIntermediateCertificate(IntermediateCertificateDTO intermediateCertificateDTO) throws AliasAlreadyExistsException {

        KeyPair keyPairIssuer = generateKeys();

        X500Name subjectName = intermediateCertificateDTO.getSubjectName().getX500Name();

        String issuerAlias = intermediateCertificateDTO.getIssuerAlias();
        String subjectAlias = intermediateCertificateDTO.getSubjectAlias();

        Date startDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.YEAR, 2);
        Date endDate = cal.getTime();

        KeyStoreReader keyStoreReader = new KeyStoreReader();
        if (keyStoreReader.checkIfAliasExists(keystorePath, keystorePass, subjectAlias)) {
            throw new AliasAlreadyExistsException("Alias already taken");
        }

        String subjectSerialNumber = UUID.randomUUID().toString();

        SubjectData subjectData = new SubjectData(keyPairIssuer.getPublic(), subjectName, subjectSerialNumber, startDate, endDate);
        IssuerData issuerData = keyStoreReader.readIssuerFromStore(keystorePath, issuerAlias, keystorePass.toCharArray(), keystorePass.toCharArray());

        CertificateGenerator cg = new CertificateGenerator();
        X509Certificate cert = cg.generateCertificate(subjectData, issuerData, true);

        KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
        keyStoreWriter.loadKeyStore(keystorePath, keystorePass.toCharArray());
        keyStoreWriter.writeCertificate(subjectAlias, cert);
        keyStoreWriter.saveKeyStore(keystorePath, keystorePass.toCharArray());
        keyStoreWriter.write(subjectAlias,keyPairIssuer.getPrivate(), keystorePass.toCharArray(), cert);

        return "Certificate created under alias '" + subjectAlias + "'";
    }

    private KeyPair generateKeys() {
        try {
            //Generator para kljuceva
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            //Za kreiranje kljuceva neophodno je definisati generator pseudoslucajnih brojeva
            //Ovaj generator mora biti bezbedan (nije jednostavno predvideti koje brojeve ce RNG generisati)
            //U ovom primeru se koristi generator zasnovan na SHA1 algoritmu, gde je SUN provajder
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            //inicijalizacija generatora, 2048 bitni kljuc
            keyGen.initialize(2048, random);

            //generise par kljuceva koji se sastoji od javnog i privatnog kljuca
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Certificate> getAll() {
        return certificateRepository.findAllByIsActiveTrue();
    }

    public List<Certificate> getAllByType(CertificateType type){
        return certificateRepository.findAllByTypeAndIsActiveTrue(type);
    }

    public Object getOne(String alias) {
        return certificateRepository.findByAlias(alias);
    }

    public void revoke(String alias) {
        for (Certificate cert : certificateRepository.findAllByIssueralias(alias)) {
            revoke(cert.getAlias());
        }
        Certificate certificate = certificateRepository.findByAlias(alias);
        certificate.setActive(false);
        certificateRepository.save(certificate);
    }


}
