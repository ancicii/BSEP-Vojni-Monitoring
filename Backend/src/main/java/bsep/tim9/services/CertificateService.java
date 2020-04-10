package bsep.tim9.services;

import bsep.tim9.DTOs.EndUserCertificateDTO;
import bsep.tim9.DTOs.IntermediateCertificateDTO;
import bsep.tim9.exceptions.AliasAlreadyExistsException;
import bsep.tim9.exceptions.InvalidCertificateException;
import bsep.tim9.model.Certificate;
import bsep.tim9.model.CertificateType;
import bsep.tim9.model.IssuerData;
import bsep.tim9.model.SubjectData;
import bsep.tim9.repositories.CertificateRepository;
import bsep.tim9.utilities.Base64KeyDecoder;
import bsep.tim9.utilities.CertificateGenerator;
import bsep.tim9.utilities.KeyStoreReader;
import bsep.tim9.utilities.KeyStoreWriter;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    @Value("${keystore_path}")
    private String keystorePath;

    @Value("${keystore_pass}")
    private String keystorePass;


    public String createEndUserCertificate(EndUserCertificateDTO endUserCertificateDTO) throws AliasAlreadyExistsException, InvalidCertificateException {
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

        LocalDateTime startLocalDateTime = Instant.ofEpochMilli( startDate.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();

        LocalDateTime endLocalDateTime = Instant.ofEpochMilli( endDate.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();

        Certificate newCertificate = new Certificate(
                endUserCertificateDTO.getSubjectAlias(),
                endUserCertificateDTO.getSubjectName().getCn(),
                endUserCertificateDTO.getIssuerAlias(),
                subjectSerialNumber,
                startLocalDateTime,
                endLocalDateTime,
                true,
                CertificateType.ENDUSER);

        if (validateCertificate(newCertificate, cert)) {
            certificateRepository.saveAndFlush(newCertificate);
        }
        else {
            throw new InvalidCertificateException("Certificate invalid");
        }

        KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
        keyStoreWriter.loadKeyStore(keystorePath, keystorePass.toCharArray());
        keyStoreWriter.writeCertificate(subjectAlias, cert);
        keyStoreWriter.saveKeyStore(keystorePath, keystorePass.toCharArray());

        return "Certificate created under alias '" + subjectAlias + "'";
    }

    @PostConstruct
    private void init() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public Object createIntermediateCertificate(IntermediateCertificateDTO intermediateCertificateDTO) throws AliasAlreadyExistsException, InvalidCertificateException {

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

        LocalDateTime startLocalDateTime = Instant.ofEpochMilli( startDate.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();

        LocalDateTime endLocalDateTime = Instant.ofEpochMilli( endDate.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();

        Certificate newCertificate = new Certificate(
                intermediateCertificateDTO.getSubjectAlias(),
                intermediateCertificateDTO.getSubjectName().getCn(),
                intermediateCertificateDTO.getIssuerAlias(),
                subjectSerialNumber,
                startLocalDateTime,
                endLocalDateTime,
                true,
                CertificateType.INTERMEDIATE);
        if (validateCertificate(newCertificate, cert)) {
            certificateRepository.saveAndFlush(newCertificate);
        }
        else {
            throw new InvalidCertificateException("Certificate invalid");
        }

        KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
        keyStoreWriter.loadKeyStore(keystorePath, keystorePass.toCharArray());
        keyStoreWriter.write(subjectAlias, keyPairIssuer.getPrivate(), keystorePass.toCharArray(), cert);
        keyStoreWriter.saveKeyStore(keystorePath, keystorePass.toCharArray());

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

    private boolean validateCertificate(Certificate certificate, X509Certificate subjectCert) {
        // Check certificate date
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (currentDateTime.isBefore(certificate.getStart_date()) || currentDateTime.isAfter(certificate.getEnd_date())) {
            return false;
        }

        // Check if it is revoked
        if (!certificate.getActive()) {
            return false;
        }

        // Check if root
        if (certificate.getType() == CertificateType.ROOT) {
            return true;
        }

        // Get Certificate objects
        KeyStoreReader keyStoreReader = new KeyStoreReader();
        if (subjectCert == null) subjectCert = (X509Certificate) keyStoreReader.readCertificate(keystorePath, keystorePass, certificate.getAlias());
        X509Certificate issuerCert = (X509Certificate) keyStoreReader.readCertificate(keystorePath, keystorePass, certificate.getIssueralias());

        // Check Signature
        try {
            subjectCert.verify(issuerCert.getPublicKey());
        } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchProviderException e) {
            return false;
        }

        Certificate issuerCertificate = certificateRepository.findByAlias(certificate.getIssueralias());
        return validateCertificate(issuerCertificate, null);
    }

    public List<Certificate> getAll() {
        return certificateRepository.findAll();
    }

    public List<Certificate> getAllByType(CertificateType type){
        return certificateRepository.findAllByType(type);
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
