import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;  
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



/**
 * 
 */

/**
 * @author 余
 *
 */
public class CertificateCoder {
	
	public static enum HashType {
		SHA256,
		MD5
	}
	
	public PrivateKey pvk = null;
	public PublicKey puk = null;
	
	private final String KeyStorePath = "D:\\keystore\\sbvkba.jks";
	private final String Alias = "SBV";
	private final String Pswd = "sbvkba";
	private final String CertPath = "D:\\keystore\\sbv.cer";
	private final String KEY_STORE = "JKS";
	
	public CertificateCoder() {
		getPublicKey(CertPath);
		getPrivateKey(KeyStorePath,Alias,Pswd);
	}
	
	public String CreateSignature(String plaintext) {
		
		String result = "";
		// 先以UTF-8編碼成 Binary
		byte[] bytes = plaintext.getBytes(StandardCharsets.UTF_8);
		byte[] hashed = null;
		// 以SHA256做hash
		try {
			hashed = hashType(bytes,HashType.SHA256);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 對hash值產生簽章
		if(hashed != null & hashed.length > 0) {
			try {
				result = Signature(hashed,KeyStorePath,Alias,Pswd);
			} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	private byte[] decryptBASE64(String key) {
		return Base64.getDecoder().decode(key);
	}
	
	private String encryptBASE64(byte[] input) {
		return Base64.getEncoder().encodeToString(input);
	}
	
	private byte[] hashType(byte[] data,HashType t) throws NoSuchAlgorithmException {
		
		MessageDigest digest = null;
		if(t == HashType.SHA256) {
			digest = MessageDigest.getInstance("SHA-256");
		} else if (t == HashType.MD5) {
			digest = MessageDigest.getInstance("MD5");			
		} 
		digest.update(data);
		return digest.digest();
	}
	
	
	
	// Use Private key to generate signature
	private String Signature(byte[] sign,String keystore,String alias,String pswd) 
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		X509Certificate X509cert = (X509Certificate)getCertificate(keystore,alias,pswd);
		
		if(pvk == null)
			getPrivateKey(keystore,alias,pswd);
		
		
		Signature signature = Signature.getInstance(X509cert.getSigAlgName());
		signature.initSign(pvk);
		signature.update(sign);
		return encryptBASE64(signature.sign());
	}
	
	// Verify the Signature
	public boolean VerifySignature(byte[] data,String sign,String certPath) 
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		X509Certificate X509Cert = (X509Certificate)getCertificate(certPath);
		
		if(puk == null)
			getPublicKey(certPath);
		
		Signature signature = Signature.getInstance(X509Cert.getSigAlgName());
		signature.initVerify(puk);
		signature.update(data);
		return signature.verify(decryptBASE64(sign));
	}
	
	// Use Private key to decrypt outgoing data
	public byte[] decryptByPrivatekey(byte[] data,String keystore,String alias,String pswd) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException {
		
		if (pvk == null)
			getPrivateKey(keystore,alias,pswd);
		
		Cipher cipher = Cipher.getInstance(pvk.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pvk);
		return cipher.doFinal(data);
	}
	
	// Use Public key to decrypt received data
	public byte[] decryptByPublickey(byte[] data,String path) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException {
		
		if (puk == null)
			getPublicKey(path);
		
		Cipher cipher = Cipher.getInstance(puk.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, puk);
		return cipher.doFinal(data);
	}
	
	public byte[] encryptByPublickey(byte[] data,String path) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException {
		
		if(puk == null)
			getPublicKey(path);
		
		Cipher cipher = Cipher.getInstance(puk.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, puk);
		return cipher.doFinal(data);
	}
	
	public byte[] encryptByPrivatekey(byte[] data,String keystore,String alias,String pswd) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
			IllegalBlockSizeException, BadPaddingException {
		
		if(pvk == null)
			getPrivateKey(keystore,alias,pswd);
		
		Cipher cipher = Cipher.getInstance(pvk.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pvk);
		
		return cipher.doFinal(data);
	}
	
	// Get Private Key
	private void getPrivateKey(String keystore,String alias,String pswd) {
		
		try {
			pvk = (PrivateKey)(getKeyStore(keystore,pswd).getKey(alias, pswd.toCharArray()));
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Get Public Key
	private void getPublicKey(String path) {
		
		puk = getCertificate(path).getPublicKey();
		
	}
	
	private KeyStore getKeyStore(String kspath,String pswd) {
		KeyStore ks = null;
		try {
			FileInputStream fis = new FileInputStream(kspath);
			ks = KeyStore.getInstance(KEY_STORE);
			ks.load(fis, pswd.toCharArray());
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ks;
	}
	
	
	// Get Certification - via Certification Path - Public Key 
	public Certificate getCertificate(String path) {
		
		Certificate cert = null;
		try {
			CertificateFactory cFactory = CertificateFactory.getInstance("X.509");	// Why X.509
			FileInputStream fis = new FileInputStream(path);
			cert = cFactory.generateCertificate(fis);
			fis.close();
			
		} catch (CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return cert;
	}
	
	// Get Certification - via Key Store - Private Key
	public Certificate getCertificate(String keystore,String alias,String pswd) {
		
		Certificate cert = null;
		
		try {
			cert = getKeyStore(keystore,pswd).getCertificate(alias);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		return cert;
	}
	
}
