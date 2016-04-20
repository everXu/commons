/**
 * 
 */
package com.kamenbaby.commons.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author <a href="mailto:xiai.fei@gmail.com">xiai_fei</a>
 * 
 * @date  2016年4月20日  上午11:23:53
 */
public class DESUtils {
	
	private static Key key ;
	
	private static final String KEY_STR = "";
	
	static{
		try{
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY_STR.getBytes());
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	
	public static String encryptStrinng(String str){
		try {
			BASE64Encoder base64en = new BASE64Encoder();
			byte[] strBytes = str.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64en.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	  
	public static String decryptString(String str){
		try {
			BASE64Decoder base64de = new BASE64Decoder();
			byte[] strBytes = base64de.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes,"UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
