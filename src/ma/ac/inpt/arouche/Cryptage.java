package ma.ac.inpt.arouche;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cryptage {
	PrivateKey privatekey;
	PublicKey publickey;

	public Cryptage() throws NoSuchAlgorithmException {
		// TODO Auto-generated constructor stub
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(512);
		KeyPair pair = keyGen.generateKeyPair();
		this.privatekey = pair.getPrivate();
		this.publickey = pair.getPublic();
	}

	public static PublicKey getPublicKey(String publickey) throws Exception{
//		PublicKey publicKey = null;
//		try {
//			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publickey.getBytes()));
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			publicKey = keyFactory.generatePublic(keySpec);
//			return publicKey;
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (InvalidKeySpecException e) {
//			e.printStackTrace();
//		}
//		return publicKey;
		byte[] publicDecoded = Base64.getDecoder().decode(publickey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicDecoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		return pubKey;
	}

	public static PrivateKey getPrivateKey(String privatekey) {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privatekey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	public static byte[] encrypt(String data, String publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return cipher.doFinal(data.getBytes());
	}
	public static byte[] encrypt(String data, PublicKey publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data.getBytes());
	}
	
	public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}

	public static String decrypt(String data, String privatekey) throws IllegalBlockSizeException,
			InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(privatekey));
	}

	static String MD5(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("MD5");
		byte[] result = mDigest.digest(input.getBytes());
		return HelpersFunctions.bytesToHex(result);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub

	}

}
