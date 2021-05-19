package ma.ac.inpt.arouche;

import java.security.PublicKey;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

import ma.ac.inpt.arouche.acos3.Acos3;
import ma.ac.inpt.arouche.acos3.HelpersFunctions;
import ma.ac.inpt.arouche.acos3.PersonnalizedFile;
import ma.ac.inpt.arouche.acos3.SecurityFile;
import ma.ac.inpt.arouche.acos3.UserFileDataArea;
import ma.ac.inpt.arouche.acos3.UserFileManagementFile;
//MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOcNZsuQNFI70JlzO+daC9LUsJQ2bm0VH7GDP9xfGv3S9x+TyHrPRlpiYtOxfyvw2dbfrzkkBNNaFd4gzw0dqFECAwEAAQ==
public class Acos3Project {
	private static String [] user_info = {
			"Mr                              " ,
			"AROUCHE Ayoub                   " ,
			"1111222233330000                ",
			"01012025                        "
			
	};
// 	private static String privatekey = "-----BEGIN PRIVATE KEY-----\r\n"
//			+ "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEApdT4xbY6j3lkjVGF\r\n"
//			+ "GOYrztFjQCSaQWBkCrYOheetepvdKnYQJi+ASHYaddtzgVdLDZCc2rswWd/zHK21\r\n"
//			+ "l2+RVwIDAQABAkAmmHOx6GCmxdUOQvvcOK3Xe8pCwoIYVyvRhl/I8IvirgYPSEYb\r\n"
//			+ "yon3TkgEM99Xe/qZzyMAzb57YuzDRrtqENTpAiEA1CfjPFL/tepVQ4YtS9Fotry2\r\n"
//			+ "v+sSTQaP/CT9P85ta1sCIQDIGlEUX57vepKCZ+/Av14SPyHIqaBF8Y7VYVVqEtoe\r\n"
//			+ "tQIgEnLhhMZm7CfTgEqzt6sfIvzoG2pSrhYLAU8qIplN1I8CIG3bZOAWjx9S39SQ\r\n"
//			+ "U2Qyq+bAmj+cQM2ljJFBWq9dojpxAiBq4JQyjnIXI5aW/A8z132NGe2ggRmzJtRX\r\n" + "o4oWjZf9Qg==\r\n"
//			+ "-----END PRIVATE KEY-----\r\n";
//
//	private static String publickey = "-----BEGIN PUBLIC KEY-----\r\n"
//			+ "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKXU+MW2Oo95ZI1RhRjmK87RY0AkmkFg\r\n"
//			+ "ZAq2DoXnrXqb3Sp2ECYvgEh2GnXbc4FXSw2QnNq7MFnf8xyttZdvkVcCAwEAAQ==\r\n" + "-----END PUBLIC KEY-----\r\n";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TerminalFactory tf = TerminalFactory.getDefault();
		CardTerminals readers = tf.terminals();
		String firstTest = "Gemalto Prox-DU Contact_12400715 0";
		CardTerminal myReader = readers.getTerminal(firstTest);
		Acos3 acos3 = new Acos3(myReader);
		//submiting the issuer code
		acos3.submit_issuer_code();
		acos3.clear_card();
	System.out.println("the card is cleared ");
		//select the security file to specify the pin code 
		
		// generate the public , private and signature for the user info 
		System.out.println("generate the public private and signature for the user info");
 		System.out.println("--------------------------------------------");
		Cryptage cryptage = new Cryptage();
		cryptage.generate_private_and_public_key_files(512);
		String private_key = cryptage.read_private_key_from_generated_file();
		String public_key = cryptage.read_public_key_from_generated_file();
		PublicKey publickey = cryptage.getPublicKey(public_key);
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("the public key : "+ publickey);
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("the public key is : "+ public_key);
		String concatenation_du_user_info = HelpersFunctions.stringArrayToString(user_info);
		
		//  la signature des info de l'utilisateur
		System.out.println("concatened user info is : "+concatenation_du_user_info);
		byte[] cipher = cryptage.encrypt(Cryptage.MD5(concatenation_du_user_info), publickey);
		System.out.println("configuring the security  file changing the pin code ");
 		System.out.println("--------------------------------------------");
		SecurityFile security_file = new SecurityFile();
		acos3.submit_issuer_code();
		security_file.select_securtiy_file();
		System.out.println("the security file selected successefly");
		
		String result = security_file.read_record_security_file((byte) 0x01);
		System.out.println("the result of reading security file is : "+result);
		security_file.update_pin_code("12345678");
		System.out.println(" the pin is submitted ");
		
		result = security_file.read_record_security_file((byte) 0x01); // reading the pin code in hex format
		System.out.println("the result of reading security file is : "+result);

		//selecting the personnalized  file to specify the number of files we want to create 
		System.out.println("configuring the personnalized file");
 		System.out.println("--------------------------------------------");
 		acos3.submit_issuer_code();
		PersonnalizedFile personnalized_file = new PersonnalizedFile();
		personnalized_file.select_personnalized_file();
		System.out.println("the personnalized file is selected ");
		
		personnalized_file.create_n_file((byte)0x03); // the number is 3
		System.out.println("the files length is added to the personnalized file" );

		acos3.submit_issuer_code();
		result = personnalized_file.read_record_personnalized_file();
		System.out.println("reading the personnalized file result is : "+ result);
		
		System.out.println("configuring the user file management file create the data files ");
 		System.out.println("--------------------------------------------");
		UserFileManagementFile user_file_management_file  = new UserFileManagementFile();
		user_file_management_file.select_user_file_management();
		
		// creating the first file
		byte[] first_file = {
			(byte)0xAA , 0x10
		};
		
		
		user_file_management_file.create_or_update_the_file_definition(
									(byte)0x00,     // which record in the user file management file
									(byte)0x32 ,	// number of bytes in each record in the user file data area
									(byte)0x04,	// number of records
									(byte)0x00,		// read security attribute
									(byte)0x80,		// write security attribute
									first_file	// file identifier of two byte you need to avoid using 0xFF in the first byte
									); // 
		System.out.println("the first file is created avec success");
		// creating the secand file
			byte[] secand_file = {
			(byte)0xAA , 0x11
		};
		
		
		user_file_management_file.create_or_update_the_file_definition(
									(byte)0x01,     // which record in the user file management file
									(byte)cipher.length,	// number of bytes in each record in the user file data area (record length) 
									(byte)0x01,	// number of records
									(byte)0x00,		// read security attribute
									(byte)0x80,		// write security attribute
									secand_file	// file identifier of two byte you need to avoid using 0xFF in the first byte
									); // 
		
		System.out.println("the secend file is created avec succes");
		
		// creating the third file
		byte[] third_file = {
		(byte)0xAA , 0x12
	};
	
	
	user_file_management_file.create_or_update_the_file_definition(
								(byte)0x02,     // which record in the user file management file
								(byte)128,	// number of bytes in each record in the user file data area
								(byte)0x01,		// number of records
								(byte)0x40,		// read security attribute
								(byte)0x80,			// write security attribute
								third_file		// file identifier of two byte you need to avoid using 0xFF in the first byte
								); // 
	
	System.out.println("the third file is created avec succes");
		//remplissage des donnees dans les fichiers 
	
	
	
	// first file 
	System.out.println("writing data to the data files");
		System.out.println("--------------------------------------------");
	UserFileDataArea first_user_file = new UserFileDataArea(first_file);
	
	first_user_file.select_user_file();
	System.out.println("the first file is selected ");
	acos3.submit_issuer_code();
	first_user_file.personna_the_file(0x04, 32, user_info);
	System.out.println("user info is addedd to the first file ");
	
	
	UserFileDataArea secand_user_file = new UserFileDataArea(secand_file);
	
	secand_user_file.select_user_file();
	System.out.println("the secand file is selected ");
	acos3.submit_issuer_code();
	secand_user_file.personna_the_file(0x00, cipher.length, cipher);
	System.out.println("user info is addedd to the secand file ");
	System.out.println("the cipher length is : "+ cipher.length);
	
	acos3.submit_issuer_code();
	UserFileDataArea third_user_file = new UserFileDataArea(third_file);
	
	third_user_file.select_user_file();
	System.out.println("the third file is selected ");
	acos3.submit_issuer_code();
	third_user_file.personna_the_file(0x00, 128, public_key);
	System.out.println("user info is addedd to the third file ");
	
	
	
	
	
	System.out.println("reading the files");
	System.out.println("---------------------------");
	first_user_file.select_user_file();
	System.out.println("reading the first file");
	for (int i = 0; i < 4; i++) {
		String info = first_user_file.read_string_record((byte) i, (byte) 32);
		System.out.println("firs_info is : " + info + "the length of the info is : " + info.length());
		
	}
	
	secand_user_file.select_user_file();
	
	System.out.println("reading the secand file : " + HelpersFunctions.bytesToHex(secand_user_file.read_byte_record((byte)0x00, (byte)cipher.length)));
	
	third_user_file.select_user_file();
	acos3.submit_pin_code("12345678"); // obliger pour trouver la cle public 
	System.out.println("reading the third file : " + first_user_file.read_string_record((byte)0x00, (byte)128));
 
		
	acos3.disconnect();

//		String h = "h";
//		System.out.println(h.getBytes().length);

	}

}
