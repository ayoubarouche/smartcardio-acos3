package ma.ac.inpt.arouche;

import java.security.PublicKey;
import java.util.Arrays;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

import ma.ac.inpt.arouche.acos3.Acos3;
import ma.ac.inpt.arouche.acos3.HelpersFunctions;
import ma.ac.inpt.arouche.acos3.UserFileDataArea;
/*
 * i did not completed all problem in creating the new signature is always different from the origin 
 */
public class Verifying {


	// MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAM4F60fEg+Hp6nQHXxXRoMPgP0cLZqZ9pR5zojemgQQk3dhucqe+P8Qq5X7ZiE1zgw/2KSrFLkEqYXi6PIhicBkCAwEAAQ==
	// 01012025 ÿ
	// Mr a
	// Mr a
	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		TerminalFactory tf = TerminalFactory.getDefault();
		CardTerminals readers = tf.terminals();
		String firstTest = "Gemalto Prox-DU Contact_12400715 0";
		CardTerminal myReader = readers.getTerminal(firstTest);
		Acos3 acos3 = new Acos3(myReader);
		// submiting the issuer code
		acos3.submit_issuer_code();
		// crypting only to get the length of the file
		Cryptage cryptage = new Cryptage();
		// selecting the secand file
		byte[] third_file = { (byte) 0xAA, 0x12 };
		byte[] first_file = { (byte) 0xAA, 0x10 };
		byte[] secand_file ={ (byte) 0xAA, 0x11 };
		System.out.println("getting the public key from card");
		// getting the public key from the card 0xAA12
		UserFileDataArea third_user_file = new UserFileDataArea(third_file);
		third_user_file.select_user_file();
		acos3.submit_pin_code("12345678");
		String public_key = third_user_file.read_string_record((byte) 0x00, (byte) 128);
		System.out.println("the public key is : " + public_key);
		
		PublicKey publickey = cryptage.getPublicKey(public_key);


//		// getting the user info and concatenate them
		System.out.println("selecting the user info file first file");
		UserFileDataArea first_user_file = new UserFileDataArea(first_file);
		first_user_file.select_user_file();
		
		System.out.println("getting info.....");
		String concatened_user_info = "";
		for (int i = 0; i < 4; i++) {
			String info = first_user_file.read_string_record((byte) i, (byte) 32);
			System.out.println("firs_info is : " + info + "the length of the info is : " + info.length());
			concatened_user_info += (info);
		}
		
		System.out.println("the concatenated file is : " + concatened_user_info);
		byte[] the_new_cipher = cryptage.encrypt(Cryptage.MD5(concatened_user_info), publickey);
		String result_the_new_cipher = HelpersFunctions.bytesToHex(the_new_cipher);
		System.out.println("le resultat de notre signature est : "+result_the_new_cipher);
		//getting the origin signature 0xAA11
		System.out.println("selecting the secand user file to get the signature ....");
		
		
		UserFileDataArea secand_user_file = new UserFileDataArea(secand_file);
		secand_user_file.select_user_file();
		byte[] the_origin_cipher = secand_user_file.read_byte_record((byte) 0x00, (byte) the_new_cipher.length);
		String result_the_origin_cipher = HelpersFunctions.bytesToHex(the_origin_cipher);
		System.out.println("the origin signature is : "+result_the_origin_cipher);
		
		if(result_the_origin_cipher.equals(result_the_new_cipher))System.out.println("signature verified");
		else System.out.println("signature not verified pay attention ");
		acos3.disconnect();

	}

}
