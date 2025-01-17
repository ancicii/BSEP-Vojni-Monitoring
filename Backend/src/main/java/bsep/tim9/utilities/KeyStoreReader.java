package bsep.tim9.utilities;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import bsep.tim9.model.CertificateType;
import bsep.tim9.model.IssuerData;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

public class KeyStoreReader {
	//KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
	//Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
	// - Sertifikati koji ukljucuju javni kljuc
	// - Privatni kljucevi
	// - Tajni kljucevi, koji se koriste u simetricnima siframa
	public KeyStoreReader() { }
	/**
	 * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci privatni kljuc.
	 * Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
	 * 
	 * @param keyStoreFile - datoteka odakle se citaju podaci
	 * @param alias - alias putem kog se identifikuje sertifikat izdavaoca
	 * @param password - lozinka koja je neophodna da se otvori key store
	 * @param keyPass - lozinka koja je neophodna da se izvuce privatni kljuc
	 * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
	 */
	public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//Datoteka se ucitava
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, password);
			//Iscitava se sertifikat koji ima dati alias
			Certificate cert = ks.getCertificate(alias);
			//Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
			PrivateKey privKey = (PrivateKey) ks.getKey(alias, keyPass);
			in.close();
			ks.store(new FileOutputStream(keyStoreFile), password);
			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
			return new IssuerData(privKey, issuerName);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | IOException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ucitava sertifikat is KS fajla
	 */
    public Certificate readCertificate(String keyStoreFile, char[] keyStorePass, String alias) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass);

			Certificate ret = ks.getCertificate(alias);
			in.close();
			ks.store(new FileOutputStream(keyStoreFile), keyStorePass);
			return ret;

		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<bsep.tim9.model.Certificate> readAllCertificates(String keyStoreFile, char[] keyStorePass, String trustStoreFile, char[] trustStorePass){
		List<bsep.tim9.model.Certificate> allCertificates = new ArrayList<>();
    	try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass);

			for (Enumeration<String> aliases = ks.aliases(); aliases.hasMoreElements();){
				String alias = aliases.nextElement();
				Certificate cert =  ks.getCertificate(alias);
				X500Name subjectName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
				X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getIssuer();
				if(subjectName.equals(issuerName)){
					bsep.tim9.model.Certificate certificateModel = new bsep.tim9.model.Certificate(
							ks.getCertificateAlias(cert),
							IETFUtils.valueToString(issuerName.getRDNs(BCStyle.CN)[0].getFirst().getValue()),
							ks.getCertificateAlias(cert),
							((X509Certificate) cert).getSerialNumber().toString(),
							((X509Certificate) cert).getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
							((X509Certificate) cert).getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
							true,
							CertificateType.ROOT);
					allCertificates.add(certificateModel);
				}
				else{
					Certificate[] certifiacatesChain  = ks.getCertificateChain(alias);
					Certificate issuer =  certifiacatesChain[1];
					bsep.tim9.model.Certificate certificateModel = new bsep.tim9.model.Certificate(
							ks.getCertificateAlias(cert),
							IETFUtils.valueToString(issuerName.getRDNs(BCStyle.CN)[0].getFirst().getValue()),
							ks.getCertificateAlias(issuer),
							((X509Certificate) cert).getSerialNumber().toString(),
							((X509Certificate) cert).getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
							((X509Certificate) cert).getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
							true,
							CertificateType.INTERMEDIATE);
					allCertificates.add(certificateModel);
				}

			}

			KeyStore ts = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			in = new BufferedInputStream(new FileInputStream(trustStoreFile));
			ts.load(in, trustStorePass);

			for (Enumeration<String> aliases = ts.aliases(); aliases.hasMoreElements();) {
				String alias = aliases.nextElement();
				Certificate cert = ts.getCertificate(alias);
				X500Name subjectName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
				X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getIssuer();
				if(!subjectName.equals(issuerName)){
					bsep.tim9.model.Certificate certificateModel = new bsep.tim9.model.Certificate(
							ts.getCertificateAlias(cert),
							IETFUtils.valueToString(issuerName.getRDNs(BCStyle.CN)[0].getFirst().getValue()),
							IETFUtils.valueToString(issuerName.getRDNs(BCStyle.CN)[0].getFirst().getValue()),
							((X509Certificate) cert).getSerialNumber().toString(),
							((X509Certificate) cert).getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
							((X509Certificate) cert).getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
							true,
							CertificateType.ENDUSER);
					allCertificates.add(certificateModel);
				}

			}

			in.close();
			ks.store(new FileOutputStream(keyStoreFile), keyStorePass);
			ts.store(new FileOutputStream(trustStoreFile), trustStorePass);
			return allCertificates;

		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Ucitava niz sertifikata is KS fajla
	 */
	public Certificate[] readCertificateChain(String keyStoreFile, String keyStorePass, String alias) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			char[] temp = keyStorePass.toCharArray();
			ks.load(in, temp);

			Certificate[] ret = ks.getCertificateChain(alias);
			in.close();
			ks.store(new FileOutputStream(keyStoreFile), temp);
			return ret;

		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ucitava privatni kljuc is KS fajla
	 */
	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			char[] temp = keyStorePass.toCharArray();
			ks.load(in, temp);
			
			if(ks.isKeyEntry(alias)) {
				PrivateKey ret = (PrivateKey) ks.getKey(alias, pass.toCharArray());
				in.close();
				ks.store(new FileOutputStream(keyStoreFile), temp);
				return ret;
			}
		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Proverava postojanje alias-a
	 */
	public Boolean checkIfAliasExists(String keyStoreFile, String keyStorePass, String alias) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			FileInputStream in = new FileInputStream(keyStoreFile);
			char[] temp = keyStorePass.toCharArray();
			ks.load(in, temp);

			boolean ret = ks.containsAlias(alias);
			in.close();
			ks.store(new FileOutputStream(keyStoreFile), temp);
			return ret;
		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
