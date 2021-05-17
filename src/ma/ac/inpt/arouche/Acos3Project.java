package ma.ac.inpt.arouche;

import java.security.PublicKey;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

public class Acos3Project {
	private static String privatekey = "-----BEGIN PRIVATE KEY-----\r\n"
			+ "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEApdT4xbY6j3lkjVGF\r\n"
			+ "GOYrztFjQCSaQWBkCrYOheetepvdKnYQJi+ASHYaddtzgVdLDZCc2rswWd/zHK21\r\n"
			+ "l2+RVwIDAQABAkAmmHOx6GCmxdUOQvvcOK3Xe8pCwoIYVyvRhl/I8IvirgYPSEYb\r\n"
			+ "yon3TkgEM99Xe/qZzyMAzb57YuzDRrtqENTpAiEA1CfjPFL/tepVQ4YtS9Fotry2\r\n"
			+ "v+sSTQaP/CT9P85ta1sCIQDIGlEUX57vepKCZ+/Av14SPyHIqaBF8Y7VYVVqEtoe\r\n"
			+ "tQIgEnLhhMZm7CfTgEqzt6sfIvzoG2pSrhYLAU8qIplN1I8CIG3bZOAWjx9S39SQ\r\n"
			+ "U2Qyq+bAmj+cQM2ljJFBWq9dojpxAiBq4JQyjnIXI5aW/A8z132NGe2ggRmzJtRX\r\n" + "o4oWjZf9Qg==\r\n"
			+ "-----END PRIVATE KEY-----\r\n";

	private static String publickey = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKXU+MW2Oo95ZI1RhRjmK87RY0AkmkFg\r\n"
			+ "ZAq2DoXnrXqb3Sp2ECYvgEh2GnXbc4FXSw2QnNq7MFnf8xyttZdvkVcCAwEAAQ==\r\n" + "-----END PUBLIC KEY-----\r\n";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		TerminalFactory tf = TerminalFactory.getDefault();
//		CardTerminals readers = tf.terminals();
//		String firstTest = "Gemalto Prox-DU Contact_12400715 0";
//		CardTerminal myReader = readers.getTerminal(firstTest);
//		Acos3 acos3 = new Acos3(myReader);
//		acos3.submit_issuer_code();
////		SecurityFile security_file = new SecurityFile();
//	//	security_file.select_securtiy_file();
//	//	System.out.println("the security file selected successefly");
//		//String result = security_file.read_record_security_file((byte) 0x01);
//		//System.out.println("the result of reading security file is : "+result);
//	publickey = 	publickey.replace("-----BEGIN PUBLIC KEY-----\r\n", "");
//	publickey = 	publickey.replace("-----END PUBLIC KEY-----\r\n", "");
//	publickey = 	publickey.replace("\r\n", "");
//	
//	privatekey = 	privatekey.replace( "-----BEGIN PRIVATE KEY-----\r\n", "");
//	privatekey = 	privatekey.replace("-----END PRIVATE KEY-----\r\n", "");
//	privatekey = 	privatekey.replace("\r\n", "");
//		System.out.println("the public key is : "  + publickey);
//		System.out.println("the private key is : "+ privatekey );
		Cryptage cryptage = new Cryptage();
		cryptage.generate_private_and_public_key(512);
		String private_key = cryptage.read_private_key_from_generated_file();

		String public_key = cryptage.read_public_key_from_generated_file();
		System.out.println("the public key is : "+ public_key);
		System.out.println("the private key is : "+ private_key);
		byte[] cipher = cryptage.encrypt(Cryptage.MD5("hello world"), cryptage.getPublicKey(public_key));
		System.out.println("the text crypted is : " + HelpersFunctions.bytesToHex(cipher));
		System.out.println("the private key is : " + cryptage.getPrivateKey(private_key));
		System.out.println("the size of the cipher text is : " + cipher.length);
		String text_result = cryptage.decrypt(cipher, cryptage.getPrivateKey(private_key));
		System.out.println("the text after decryption is : " + text_result);
		if (Cryptage.MD5("hello world").equals(text_result))
			System.out.println("they are equals ");

		// cryptage.read_private_key_from_file(null);

//	//	acos3.submit_pin_code("12345678");
//	//	security_file.update_pin_code("12345678");
////		 result = security_file.read_record_security_file((byte) 0x01);
////		System.out.println("the result of reading security file after update is  : "+result);
////		PersonnalizedFile personnalized_file = new PersonnalizedFile();
////		personnalized_file.select_personnalized_file();
////		System.out.println("the personnalized file selected successefly");
////		System.out.println(personnalized_file.read_record_personnalized_file());
////		personnalized_file.create_n_file((byte) 0x03);
////		System.out.println("the number of file added successefly");
////		System.out.println("the personnalized file "+ personnalized_file.read_record_personnalized_file());
//		UserFileManagementFile user_file_management_file = new UserFileManagementFile();
//		user_file_management_file.select_user_file_management();
//	//System.out.println("the number of record files  : " + user_file_management_file.read_record_user_file_management((byte) 0x02));
//	byte[] file_identifier = { (byte) 0xAA, 0x10 };
////	user_file_management_file.create_or_update_the_file_definition((byte)0x00,(byte)0x20, (byte)0x04,(byte) 0x00, (byte)0x00,file_identifier );
////		System.out.println("the number of record files after creating is  : " + user_file_management_file.read_record_user_file_management((byte) 0x00));
////		file_identifier[1] = (byte) 0x11 ;
////		user_file_management_file.create_or_update_the_file_definition((byte)0x01,(byte)0x40, (byte)0x01,(byte) 0x00, (byte)0x00,file_identifier );
////		System.out.println("the number of record files after creating is  : " + user_file_management_file.read_record_user_file_management((byte) 0x01));
////		file_identifier[1] = (byte) 0x12 ;
////		user_file_management_file.create_or_update_the_file_definition((byte)0x02,(byte)0x80, (byte)0x01,(byte) 0x00, (byte)0x00,file_identifier );
////		System.out.println("the number of record files after creating is  : " + user_file_management_file.read_record_user_file_management((byte) 0x02));
//		System.out.println("re reading all the recording files : ");
//		System.out.println("reading the first  file  : " + user_file_management_file.read_record_user_file_management((byte) 0x00));
//		System.out.println("reading the secand file  : " + user_file_management_file.read_record_user_file_management((byte) 0x01));
//		System.out.println("reading the third  file  : " + user_file_management_file.read_record_user_file_management((byte) 0x02));
//		
//		UserFileDataArea user_file = new UserFileDataArea(file_identifier);
//	//	user_file.read_record((byte)0x00, (byte)0x20);
//		//		UserFileDataArea user_file = new UserFileDataArea(file_identifier);
//		user_file.select_file();
//		System.out.println("the file selected successefly ");
//		String[] text = { "arouche", "ayoub", "00001111222233334444555566667777", "01012018" };
//		user_file.personna_the_file(4 , 0x20 ,  text);
//		System.out.println("the file personnalized successefly ");
//		String result;
//		for(int i = 0; i<4;i++) {
//			 result = 	user_file.read_record((byte)i, (byte)0x20);
//			System.out.println("reding the record "+ i + " : "+result);
//		}
//	
//
//	acos3.disconnect();

//		String h = "h";
//		System.out.println(h.getBytes().length);

	}

}
