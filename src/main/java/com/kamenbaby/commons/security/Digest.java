package com.kamenbaby.commons.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 为保存密码计算摘�?
 * @author xu.lingfeng
 * @date 2012-8-31
 */
public class Digest {
	
	public static char[] DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};
	
	private static byte[] mix(byte[] words, String salt){
		
		byte[] mixed = new byte[words.length];
		byte[] paddedSalt = Arrays.copyOf(salt.getBytes(), words.length);
		for(int i=0; i<words.length; i++){
			mixed[i] = (byte) (words[i]^paddedSalt[i]^(i & 0x1F));
		}
		return mixed;
	}
	
	private static byte[] mixHex(String hexString, String salt){
		byte[] words = new byte[hexString.length()>>1];
		for(int i=0; i<words.length; i++){
			words[i] = (byte) Integer.parseInt(hexString.substring(i<<1, (i<<1)+2), 16);
		}
		return mix(words, salt);
	}
	
	private static String toHexString(byte[] bytes){
		char[] cs = new char[bytes.length<<1];
		for(int i=0, j=0; i<bytes.length; i++){
			cs[j++] = DIGITS[(bytes[i] & 0xF0) >>> 4];
			cs[j++] = DIGITS[bytes[i] & 0x0F];
		}
		return new String(cs);
	}
	
	/**
	 * 计算摘要密码是Hex字符�?
	 * @param hexString 要计算摘要的字符�?
	 * @param salt 用于混淆的字�?
	 * @return
	 */
	public static String hexSaltSHA(String hexString, String salt){
		byte[] mixed = mixHex(hexString, salt);
		try {
			return toHexString(MessageDigest.getInstance("SHA1").digest(mixed));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 计算摘要
	 * @param words 要计算摘要的字符�?
	 * @param salt 用于混淆的字�?
	 * @return
	 */
	public static String saltSHA(String words, String salt){
		byte[] mixed = mix(words.getBytes(), salt);
		try {
			return toHexString(MessageDigest.getInstance("SHA1").digest(mixed));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 使用SHA-256算法计算摘要
	 * @param words
	 * @return
	 */
	public static String SHA256(String words){
		try {
			return toHexString(MessageDigest.getInstance("SHA-256").digest(words.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
	}
	
	/***
	 * 将需要加密的内容和salt加盐后返�?
	 * @param content
	 * @param salt
	 * @return
	 */
	public static String digestWithSalt(String content , String salt){
		return Digest.hexSaltSHA(Digest.SHA256(content),salt);
	}
	
	public static void main(String[] args){
//		System.out.println(hexSaltSHA("F6C1E5A89CACB04CC317B66968F1014FF72FD56E673325FA0CA5382F96616DA6","Immanuel"));
//		System.out.println(saltSHA("48347@$#dhf","Immanuel"));
		System.out.println(Digest.hexSaltSHA(Digest.SHA256("123456"),"12121212001"));
		System.out.println(Digest.hexSaltSHA(Digest.SHA256("123456"),"12121212002"));
		System.out.println(Digest.hexSaltSHA(Digest.SHA256("123456"),"12121212003"));
		System.out.println(Digest.hexSaltSHA(Digest.SHA256("123456"),"12121212004"));
		System.out.println(Digest.hexSaltSHA(Digest.SHA256("123456"),"12121212005"));

	}

}
