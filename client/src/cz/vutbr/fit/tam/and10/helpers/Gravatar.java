package cz.vutbr.fit.tam.and10.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Trida pro praci s gravatary
 * @author misa
 *
 */
public class Gravatar {

	
	public static String hex(byte[] array) {
	      StringBuffer sb = new StringBuffer();
	      for (int i = 0; i < array.length; ++i) {
		  sb.append(Integer.toHexString((array[i]
		      & 0xFF) | 0x100).substring(1,3));        
	      }
	      return sb.toString();
	}
	
	public static String md5Hex (String message) {
	      try {
		  MessageDigest md = 
		      MessageDigest.getInstance("MD5");
		  return hex (md.digest(message.getBytes("CP1252")));
	      } catch (NoSuchAlgorithmException e) {
	      } catch (UnsupportedEncodingException e) {
	      }
	      return null;
	}
	
	
	public static String getGravatarURL(String email) {
		String hash = md5Hex(email);
		return "http://www.gravatar.com/avatar/"+hash;
	}
	
	public static String getGravatarHash(String email) {
		return md5Hex(email);
	}

}
