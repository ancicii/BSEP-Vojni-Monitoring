package bsep.SIEMagent;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

@SpringBootApplication
public class SiemAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiemAgentApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		File file = new File("src/main/resources/keystore.jks");
		File fileTrust = new File("src/main/resources/truststore.jks");
		InputStream is = new FileInputStream(file);
//		KeyStore keyStore = KeyStore.getInstance("jks");
//		keyStore.load(is, "pass123".toCharArray());

		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(file, "pass123".toCharArray(), "pass123".toCharArray())
				.loadTrustMaterial(fileTrust, "trust123".toCharArray()).build();

		HttpClient client = HttpClients.custom().setSSLContext(sslContext)
				.build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
		return restTemplate;


	}

}
