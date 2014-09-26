package com.matrixloop.timecute.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	private MD5(){}
	
	private static final ThreadLocal<MessageDigest> holder = new ThreadLocal<MessageDigest>(){
		@Override
		protected MessageDigest initialValue() {
			// TODO Auto-generated method stub
			try {
				return MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("Initial MD5 MessageDigest failed !!!") ;
			}
		}
	} ;
	
	public static String encode2String(String source){
		byte[] mdbytes = encode2Bytes(source) ;
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			String hex = Integer.toHexString(0xff & mdbytes[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
	
	public static byte[] encode2Bytes(String source){
		if(source == null || source.length() <= 0){
			throw new IllegalArgumentException("message should not be empty") ;
		}
		MessageDigest md = holder.get() ;
		byte[] btInput = source.getBytes();
		md.reset() ;
		md.update(btInput);
		return md.digest();
	}
}
