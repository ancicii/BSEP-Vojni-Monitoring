package bsep.tim9.services;

import bsep.tim9.DTOs.EndUserCertificateDTO;
import bsep.tim9.exceptions.AliasAlreadyExistsException;
import bsep.tim9.model.IssuerData;
import bsep.tim9.model.SubjectData;
import bsep.tim9.repositories.IssuerRepository;
import bsep.tim9.utilities.Base64KeyDecoder;
import bsep.tim9.utilities.CertificateGenerator;
import bsep.tim9.utilities.KeyStoreReader;
import bsep.tim9.utilities.KeyStoreWriter;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class CertificateService {

    @Autowired
    IssuerRepository issuerRepository;

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
        X509Certificate cert = cg.generateCertificate(subjectData, issuerData);

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
}
