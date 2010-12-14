package cz.vutbr.fit.tam.and10.helpers;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import android.util.Base64;
import java.math.BigInteger;

public class Encryption {

	public static BigInteger dh_p = new BigInteger("265252859812191058636308479999999");
	public static BigInteger dh_g = new BigInteger("5");

	public static BigInteger dhGetToSend(BigInteger myInteger)
	{
		return dh_g.modPow(myInteger, dh_p);
	}
	
	public static BigInteger dhGetSecret(BigInteger myInteger, BigInteger received)
	{
		return received.modPow(myInteger, dh_p);
	}
	
	public static String nullPad(String toPad, int blockSize) {
		String output = toPad;
		
		int rem = toPad.length() % blockSize;
		if (rem != 0) {
			rem = blockSize - rem;
			for (int i = 0; i < rem; ++i) {
				output += (char) 0;
			}
		}
		
		return output;
	}
	
	public static String encrypt(String plainText, String keyString) {
		byte[] paddedKeyBytes = nullPad(keyString, 32).getBytes();
		
		SecretKeySpec keySpec = new SecretKeySpec(paddedKeyBytes, "AES");
		
		String in = Integer.toString(plainText.length()) + '|' + plainText;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			
			byte[] plainTextBytes = nullPad(in, 16).getBytes();
			byte[] cipherText = cipher.doFinal(plainTextBytes);
			
			return new String(Base64.encode(cipherText, Base64.DEFAULT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String decrypt(String cipherText, String keyString) {
		byte[] paddedKeyBytes = nullPad(keyString, 32).getBytes();
		
		SecretKeySpec keySpec = new SecretKeySpec(paddedKeyBytes, "AES");
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			
			byte[] cipherTextBytes = Base64.decode(cipherText, Base64.DEFAULT);
			byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
			
			String plainText = new String(plainTextBytes);
			int delimiter = plainText.indexOf('|');
			int length = Integer.valueOf(plainText.substring(0, delimiter));
			
			return plainText.substring(delimiter + 1, length + delimiter + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
