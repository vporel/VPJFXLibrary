package vplibrary.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashLib {
	public static String md5(String pwd) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(pwd.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer(2*hash.length);
			for(byte b : hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
