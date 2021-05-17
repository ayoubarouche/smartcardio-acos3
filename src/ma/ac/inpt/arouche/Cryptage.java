package ma.ac.inpt.arouche;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

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

	public static PublicKey getPublicKey(String publickey) throws Exception {
		String publicKeyPEM = publickey;

		byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
		return pubKey;
	}

	public static PrivateKey getPrivateKey(String privatekey) throws Exception {
		String privateKeyPEM = privatekey;

		byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
		return privKey;
	}

	public static byte[] encrypt(String data, String publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return cipher.doFinal(data.getBytes());
	}

	public static byte[] encrypt(String data, PublicKey publicKey) throws Exception {
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

	public static String decrypt(String data, String privatekey) throws Exception {
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



	public static String read_public_key_from_generated_file() {
		String publickey = "";
		try {
			File myObj = new File("./public_key.pem");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				publickey += myReader.nextLine();
			
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}	
	
		publickey = 	publickey.replace("-----BEGIN PUBLIC KEY-----", "");
		publickey = 	publickey.replace("-----END PUBLIC KEY-----", "");
		publickey = 	publickey.replace("\r\n", "");
		return publickey;
	}
	public static String read_private_key_from_generated_file() {
		String privatekey = "";
		try {
			File myObj = new File("./private_key.pem");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				privatekey += myReader.nextLine();
				
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		privatekey = 	privatekey.replace( "-----BEGIN PRIVATE KEY-----", "");
		privatekey = 	privatekey.replace("-----END PRIVATE KEY-----", "");
		privatekey = 	privatekey.replace("\r\n", "");
		return privatekey;
	}

	public static void generate_private_and_public_key(int keySize) throws Exception {
		String line;
		File myObj = new File("./openssl/openssl.exe");
		String[] commands = { "cmd /c " + myObj.getAbsolutePath() + " genrsa -out rsa_private_text.pem " + keySize , 
				"cmd /c " + myObj.getAbsolutePath() + " pkcs8 -topk8 -inform PEM -in rsa_private_text.pem -out private_key.pem -nocrypt ",
				"cmd /c " + myObj.getAbsolutePath() + " rsa -in private_key.pem -pubout -outform PEM -out public_key.pem "
		};

		Process p = Runtime.getRuntime().exec(commands[0]);

		BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		while ((line = bre.readLine()) != null) {
			System.out.println(line);
		}
		bre.close();
		p.waitFor();
		p = Runtime.getRuntime().exec(commands[1]);
		BufferedReader bre2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		while ((line = bre2.readLine()) != null) {
			System.out.println(line);
		}
		bre2.close();
		p.waitFor();
		p = Runtime.getRuntime().exec(commands[2]);
		BufferedReader bre3 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		while ((line = bre3.readLine()) != null) {
			System.out.println(line);
		}
		bre3.close();
		p.waitFor();
		System.out.println("Done.");

	}
}
