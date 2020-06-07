package discussion.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import discussion.exceptions.AppExceptionMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {
	private KeyStore keyStore;
	
	@PostConstruct
	public void init() {
		try {
			keyStore=KeyStore.getInstance("JKS");
			InputStream stream=getClass().getResourceAsStream("/discussion.jks");
			keyStore.load(stream,"discussion".toCharArray());
		}catch(KeyStoreException|NoSuchAlgorithmException|CertificateException|IOException e) {
			throw new AppExceptionMessage("Exception occured while loading keystore");
		}
	}
	
	public String generateJwtToken(Authentication authentication) {
		User principal=(User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername())
				.signWith(getPrivateKeyFromKeystore()).compact();
	}
	
	public boolean validateExtractedJwtKey(String key) {
		Jwts.parser().setSigningKey(getPublicKeyFromKeyStore()).parseClaimsJws(key);
		return true;
	}

	private PublicKey getPublicKeyFromKeyStore() {
		try {
			return keyStore.getCertificate("discussion").getPublicKey();
		} catch (KeyStoreException e) {
			throw new AppExceptionMessage("Error occured while retriving public key");
		}
	}

	private PrivateKey getPrivateKeyFromKeystore() {
		try {
			return (PrivateKey) keyStore.getKey("discussion", "discussion".toCharArray());
		}catch(KeyStoreException|NoSuchAlgorithmException|UnrecoverableKeyException e) {
			throw new AppExceptionMessage("Error occured while retriving private key");
		}
	}

	public String getUsernameFromExtractedJwtToken(String extractedJwt) {
		Claims claims=Jwts.parser().setSigningKey(getPublicKeyFromKeyStore())
				.parseClaimsJws(extractedJwt).getBody();
		return claims.getSubject();
	}
	
	
	
	

}
