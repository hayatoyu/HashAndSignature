import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CertificateCoder coder = new CertificateCoder();
		
		System.out.println(coder.puk);
		System.out.println(coder.pvk);
		/*
		String plaintext = "test";
		System.out.println(plaintext);
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(plaintext.getBytes(StandardCharsets.UTF_8));
			//System.out.println(new String(hash,StandardCharsets.UTF_8));
			//System.out.println(bytesToString(hash));
			
			System.out.println(Base64.getEncoder().encodeToString(hash));
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/
	}
	
	public static String bytesToString(byte[] bytes) {
		String output = "";
		for(int i = 0;i < bytes.length;i++) {
			output += (char)bytes[i];
		}
		return output;
	}

}
