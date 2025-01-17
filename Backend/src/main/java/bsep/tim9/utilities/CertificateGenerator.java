package bsep.tim9.utilities;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bsep.tim9.model.IssuerData;
import bsep.tim9.model.SubjectData;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateGenerator {
	public CertificateGenerator() {}
	
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, boolean _ca) {
		try {
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
			UUID randomUUID = UUID.fromString(subjectData.getSerialNumber());
			bb.putLong(randomUUID.getMostSignificantBits());
			bb.putLong(randomUUID.getLeastSignificantBits());
			BigInteger bigInteger = new BigInteger(1, bb.array());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					bigInteger,
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey());

			//Postavljanje ekstenzije Subject Alternative Name (za Chrome)
			final List<ASN1Encodable> subjectAlternativeNames = new ArrayList<ASN1Encodable>();
			subjectAlternativeNames.add(new GeneralName(GeneralName.dNSName, "localhost"));
			subjectAlternativeNames.add(new GeneralName(GeneralName.iPAddress, "127.0.0.1"));
			final DERSequence subjectAlternativeNamesExtension = new DERSequence(
					subjectAlternativeNames.toArray(new ASN1Encodable[subjectAlternativeNames.size()]));
			certGen.addExtension(Extension.subjectAlternativeName, false, subjectAlternativeNamesExtension);

			//Postavljanje ekstenzije Basic Constraints
			certGen.addExtension(Extension.basicConstraints, false, new BasicConstraints(_ca));

			//Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (CertIOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
