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
	private PublicKey puk = null;	//���_
	private PrivateKey pvk = null;	//�p�_
	
	// Key Store Information
	private String KeyStorePath = "";	//���_�x�s�w���|
	private String Alias = "";			//���_�O�W
	private String Pswd = "";			//���_�K�X
	private String CertPath = "";		//���Ҹ��|
	private String KEY_STORE_TYPE = "JKS";		//���_�x�s�w����
	
	// �T�|�����A��ܤ��_�Ψp�_
	public enum Key_Type {
		Public,
		Private
	}
	
	// �T�|�����A��ܥ[�K�θѱK
	public enum DecEnc {
		Decryption,
		Encryption
	}
	
	// �غc�l�A�ݶǤJ���_������T
	public HashAndSignature(String KeyStorePath,String Alias,
			String Pswd,String CertPath) {
		this.KeyStorePath = KeyStorePath;
		this.Alias = Alias;
		this.Pswd = Pswd;
		this.CertPath = CertPath;
		
		// ���o���_�Ψp�_
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
	 * Base64�s�X�θѽX�C
	 * ������ sun.misc�M��U��BASE64Encoder��Decoder�ѩ�į��ΡA�ثeJava�w���A�䴩
	 * �Ч��java.util.Base64�U���M��
	 * */
	
	private byte[] decryptBASE64(String input) {
		return Base64.getDecoder().decode(input);
	}
	
	private String encryptBASE64(byte[] input) {
		return Base64.getEncoder().encodeToString(input);
	}
	
	// �N��J��Ƨ@SHA256�����^��byte�}�C
	private byte[] SHA256Hash(byte[] input) throws NoSuchAlgorithmException {
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(input);
		return digest.digest();		
	}
	
	// ���o���_�Ψp�_������	
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
	
	// ���o���_�x�s�w
	private KeyStore getKeyStore() throws KeyStoreException,
		NoSuchAlgorithmException, CertificateException, IOException {
		
		FileInputStream fis = new FileInputStream(KeyStorePath);
		KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
		ks.load(fis,Pswd.toCharArray());
		fis.close();
		return ks;
	}
	
	// ���o���_
	private void getPublicKey() throws KeyStoreException, NoSuchAlgorithmException,
		CertificateException, IOException {
		
		puk = getCertificate(Key_Type.Public).getPublicKey();
	}
	
	// ���o�p�_
	private void getPrivateKey() throws UnrecoverableKeyException,
		KeyStoreException, NoSuchAlgorithmException, CertificateException,
		IOException {
		
		pvk = (PrivateKey)getKeyStore().getKey(Alias, Pswd.toCharArray());
	}
	

	/*
	 * ����ñ��
	 * 
	 * ����ñ���B�J�G
	 * 	1. �N��l����ର UTF-8�s�X��byte[]�}�C�C
	 * 	2. �Nbyte[]�}�C�ϥ�SHA256�t��k����B�z�C
	 * 	3. �ϥΨp�_�[�K����ȡC
	 * 	4. �N�[�K�᪺�r���ରBase64�s�X�^�ǡC
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
	 * �糹
	 * 
	 * �糹�B�J�G
	 * 	1. �N���쪺��ƸgSHA256����C
	 * 	2. �N���쪺ñ���ȥHBase64�ѽX�C
	 * 	3. �Nñ���ȥH���_�ѱK�C
	 * 	4. ����ƪ�����ȩM�ѱK��ñ��������ȬO�_�ۦP�C
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
		
		// ���̷ӻݨD���o���_�Ψp�_
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
		
		// �ݬO�[�K�θѱK		
		if(DE == DecEnc.Decryption) {
			
			cipher.init(Cipher.DECRYPT_MODE, key);			
			
		} else if (DE == DecEnc.Encryption) {
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
		}
		
		return cipher.doFinal(data);
		
	}
	
}
