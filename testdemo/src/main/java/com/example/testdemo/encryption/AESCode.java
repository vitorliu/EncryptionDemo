package com.example.testdemo.encryption;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESCode {

//	public static void main(String[] args) throws Exception {
//		String key = "msdmsd0123456789";
//		String originJsonStr = "中文测试中文测试中文测试中文测试-中~!@#$%^&*()_+''/\\\\n\\\\p测试";
//		String aesEncryptStr = encode(originJsonStr, key);
//		System.out.println("aesEncryptStr=" + aesEncryptStr);
//
//		String aesDecryptStr = decode(aesEncryptStr, key);
//		System.out.println("aesDecryptStr=" + aesDecryptStr);
//
//		System.out.println("-------end-----------");
//	}

	/**
	 * 加密--把加密后的byte数组先进行二进制转16进制在进行base64编码
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String encode(String sSrc, String sKey) throws Exception {
		try {
			if (sKey == null) {
				throw new IllegalArgumentException("Argument sKey is null.");
			}
			if (sKey.length() != 16) {
				throw new IllegalArgumentException("Argument sKey'length is not 16.");
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
			return new String(Base64.encode(encrypted));
		} catch (Exception e) {
			throw new Exception("-----encode error!,sKey=" + sKey);
		}
	}

	/**
	 * 解密--先 进行base64解码，在进行16进制转为2进制然后再解码
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String decode(String sSrc, String sKey) throws Exception {
		try {
			if (sKey == null) {
				throw new IllegalArgumentException("499");
			}
			if (sKey.length() != 16) {
				throw new IllegalArgumentException("498");
			}

			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			byte[] encrypted1 = Base64.decode(sSrc.getBytes());

			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception e) {
			throw new Exception("-----encode error!,sKey=" + sKey);
		}
	}
}