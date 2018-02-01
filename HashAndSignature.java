import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;  

/**
 * 
 */
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author 51942
 *
 */
public class HashAndSignature {

	// Key Pair
	private PublicKey puk = null;	//公鑰
	private PrivateKey pvk = null;	//私鑰
	
	// Key Store Information
	private String KeyStorePath = "";	//金鑰儲存庫路徑
	private String Alias = "";			//金鑰別名
	private String Pswd = "";			//金鑰密碼
	private String CertPath = "";		//憑證路徑
	private String KEY_STORE_TYPE = "JKS";		//金鑰儲存庫類型
	
	// 枚舉類型，選擇公鑰或私鑰
	public enum Key_Type {
		Public,
		Private
	}
	
	// 枚舉類型，選擇加密或解密
	public enum DecEnc {
		Decryption,
		Encryption
	}
	
	// 建構子，需傳入金鑰相關資訊
	public HashAndSignature(String KeyStorePath,String Alias,
			String Pswd,String CertPath) {
		this.KeyStorePath = KeyStorePath;
		this.Alias = Alias;
		this.Pswd = Pswd;
		this.CertPath = CertPath;
		
		// 取得公鑰及私鑰
		try {
			getPublicKey();
			getPrivateKey();
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | CertificateException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Base64編碼及解碼。
	 * 早期的 sun.misc套件下的BASE64Encoder及Decoder由於效能欠佳，目前Java已不再支援
	 * 請改用java.util.Base64下的套件
	 * */
	
	private byte[] decryptBASE64(String input) {
		return Base64.getDecoder().decode(input);
	}
	
	private String encryptBASE64(byte[] input) {
		return Base64.getEncoder().encodeToString(input);
	}
	
	// 將輸入資料作SHA256雜湊後回傳byte陣列
	private byte[] SHA256Hash(byte[] input) throws NoSuchAlgorithmException {
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(input);
		return digest.digest();		
	}
	
	// 取得公鑰或私鑰的憑證	
	private Certificate getCertificate(Key_Type CT) throws KeyStoreException, 
		NoSuchAlgorithmException, CertificateException, IOException {
		
		Certificate cert = null;
		
		if(CT == Key_Type.Private) {
						
			cert = getKeyStore().getCertificate(Alias);
			
		}
		else if (CT == Key_Type.Public) {
			
			CertificateFactory cFactory = CertificateFactory.getInstance("X.509");
			FileInputStream fis = new FileInputStream(CertPath);
			cert = cFactory.generateCertificate(fis);
			fis.close();
			
		}
		
		return cert;
	}
	
	// 取得金鑰儲存庫
	private KeyStore getKeyStore() throws KeyStoreException,
		NoSuchAlgorithmException, CertificateException, IOException {
		
		FileInputStream fis = new FileInputStream(KeyStorePath);
		KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
		ks.load(fis,Pswd.toCharArray());
		fis.close();
		return ks;
	}
	
	// 取得公鑰
	private void getPublicKey() throws KeyStoreException, NoSuchAlgorithmException,
		CertificateException, IOException {
		
		puk = getCertificate(Key_Type.Public).getPublicKey();
	}
	
	// 取得私鑰
	private void getPrivateKey() throws UnrecoverableKeyException,
		KeyStoreException, NoSuchAlgorithmException, CertificateException,
		IOException {
		
		pvk = (PrivateKey)getKeyStore().getKey(Alias, Pswd.toCharArray());
	}
	

	/*
	 * 產生簽章
	 * 
	 * 產生簽章步驟：
	 * 	1. 將原始資料轉為 UTF-8編碼的byte[]陣列。
	 * 	2. 將byte[]陣列使用SHA256演算法雜湊處理。
	 * 	3. 使用私鑰加密雜湊值。
	 * 	4. 將加密後的字串轉為Base64編碼回傳。
	 * */
	public String Sign(byte[] bytes) throws KeyStoreException,
		NoSuchAlgorithmException, CertificateException, IOException,
		InvalidKeyException, SignatureException, UnrecoverableKeyException {						
								
		X509Certificate X509Cert = (X509Certificate)getCertificate(Key_Type.Private);
		
		if(pvk == null)
			getPrivateKey();
		
		Signature signature = Signature.getInstance(X509Cert.getSigAlgName());
		signature.initSign(pvk);
		signature.update(SHA256Hash(bytes));
		return encryptBASE64(signature.sign());
	
	}
	
	/*
	 * 驗章
	 * 
	 * 驗章步驟：
	 * 	1. 將收到的資料經SHA256雜湊。
	 * 	2. 將收到的簽章值以Base64解碼。
	 * 	3. 將簽章值以公鑰解密。
	 * 	4. 比對資料的雜湊值和解密後簽章的雜湊值是否相同。
	 * 
	 * */
	public boolean VerifySignature(byte[] data,String signature) 
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, InvalidKeyException,
			SignatureException {
		
		X509Certificate X509Cert = (X509Certificate)getCertificate(Key_Type.Public);
		
		if(puk == null)
			getPublicKey();
		
		Signature sign = Signature.getInstance(X509Cert.getSigAlgName());
		sign.initVerify(puk);
		sign.update(SHA256Hash(data));
		return sign.verify(decryptBASE64(signature));
	}
	
	public byte[] DecryptOrEncryptByKey(byte[] data,DecEnc DE,Key_Type CT) 
			throws NoSuchAlgorithmException, 
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnrecoverableKeyException, KeyStoreException, CertificateException, IOException {
		
		Key key = null;
		
		Cipher cipher;
		
		// 先依照需求取得公鑰或私鑰
		if(CT == Key_Type.Private)
		{
			
			if(pvk == null)
				getPrivateKey();
			key = pvk;
			
		} else if (CT == Key_Type.Public){
			
			if(puk == null)
				getPublicKey();
			key = puk;
			
		}
		
		cipher = Cipher.getInstance(key.getAlgorithm());
		
		// 看是加密或解密		
		if(DE == DecEnc.Decryption) {
			
			cipher.init(Cipher.DECRYPT_MODE, key);			
			
		} else if (DE == DecEnc.Encryption) {
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
		}
		
		return cipher.doFinal(data);
		
	}
	
}
