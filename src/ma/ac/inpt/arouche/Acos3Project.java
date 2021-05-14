package ma.ac.inpt.arouche;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

public class Acos3Project {
	private static String privatekey = "MIICXQIBAAKBgQC+/IHPptKS/Svw9kJHwUFfBF4aycLNZV4VuMIpMOLf56IS/mnXfouMxv8JLZPmQ/DuPTQqgXLsyogRkInak+EdMqPH+jLT58LlLBwc1uyau1mdaR+7Cxgq2Sa4+F+iLPfZ1AtmInWqmLGUg5f36jobbV4AP3ByquABIAwexdnvTwIDAQABAoGAOavqTxgjEjvdHwBd92kGInkhKZmvd3KavJh0GRgoClDaAj5NG8OcxD/7F3xp4ui1Qlu8+vDoEVT/Tb6FwjlRiodx2b7pVMyOQ2hcYops1sbjI8qNBm6uDDjs4/iysyqzjdvWygyWaD8NbS2rhU7Sq605hLIoPUD8Rf8fO5PAHUECQQDzGJ7TXzYnYbxLCH68n1pDZkYlz25zJfzKwI/kWFOA2471NXaou7XX3nAYZLZSZQQ0KgITs81yfriD7HmnGyTRAkEAyR/HKnXpr6224XEHSVGOjYCx3pWzOu5kFuwcvibfWrV/RLW6CGB0ilFDefbBckRbtQJl3l5Z0HxOitNQiG9aHwJBALEEzasn2vxkfEO7ROa/t2/7Cru/yDMLh4BxJpbmBrB/sSM4Mlb+kZVHQiNROz7WlnAR4v9L4CAuzNgsHA4mgvECQA1e/Hm5LxkL3n2sawSX1HdZ1/lpUGoZkTPo7JxDr1Ozf0uNHVoTN4w+jNLd8cxr+QDWsPkQor32la4rEE6RwJcCQQDrjfQSU2aQ4Vqggt6eYHirrkNYtwmpkLvnOgf9AxYPCR3jntiu8EP4tzJRwX93YV1NYOx6CTz1clQ45dD5nPUP"
			;
	private static String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+/IHPptKS/Svw9kJHwUFfBF4aycLNZV4VuMIpMOLf56IS/mnXfouMxv8JLZPmQ/DuPTQqgXLsyogRkInak+EdMqPH+jLT58LlLBwc1uyau1mdaR+7Cxgq2Sa4+F+iLPfZ1AtmInWqmLGUg5f36jobbV4AP3ByquABIAwexdnvTwIDAQAB";
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TerminalFactory tf = TerminalFactory.getDefault();
		CardTerminals readers = tf.terminals();
		String firstTest = "Gemalto Prox-DU Contact_12400715 0";
		CardTerminal myReader = readers.getTerminal(firstTest);
		Acos3 acos3 = new Acos3(myReader);
		acos3.submit_issuer_code();
//		SecurityFile security_file = new SecurityFile();
	//	security_file.select_securtiy_file();
	//	System.out.println("the security file selected successefly");
		//String result = security_file.read_record_security_file((byte) 0x01);
		//System.out.println("the result of reading security file is : "+result);
			Cryptage cryptage = new Cryptage();
			byte[] cipher = cryptage.encrypt(Cryptage.MD5("world is the best"), cryptage.publickey);
			System.out.println("the text crypted is : "+HelpersFunctions.bytesToHex(cipher));
			System.out.println("the size of the cipher text is : "+ cipher.length);
			String text_result = cryptage.decrypt(cipher, cryptage.privatekey);
			System.out.println("the text after decryption is : "+ text_result);
			if(Cryptage.MD5("hello world").equals(text_result))System.out.println("they are equals ");
	//	acos3.submit_pin_code("12345678");
	//	security_file.update_pin_code("12345678");
//		 result = security_file.read_record_security_file((byte) 0x01);
//		System.out.println("the result of reading security file after update is  : "+result);
//		PersonnalizedFile personnalized_file = new PersonnalizedFile();
//		personnalized_file.select_personnalized_file();
//		System.out.println("the personnalized file selected successefly");
//		System.out.println(personnalized_file.read_record_personnalized_file());
//		personnalized_file.create_n_file((byte) 0x03);
//		System.out.println("the number of file added successefly");
//		System.out.println("the personnalized file "+ personnalized_file.read_record_personnalized_file());
		UserFileManagementFile user_file_management_file = new UserFileManagementFile();
		user_file_management_file.select_user_file_management();
	//System.out.println("the number of record files  : " + user_file_management_file.read_record_user_file_management((byte) 0x02));
	byte[] file_identifier = { (byte) 0xAA, 0x10 };
	user_file_management_file.create_or_update_the_file_definition((byte)0x00,(byte)0x20, (byte)0x04,(byte) 0x00, (byte)0x00,file_identifier );
		System.out.println("the number of record files after creating is  : " + user_file_management_file.read_record_user_file_management((byte) 0x00));
		file_identifier[1] = (byte) 0x11 ;
		user_file_management_file.create_or_update_the_file_definition((byte)0x01,(byte)0x40, (byte)0x01,(byte) 0x00, (byte)0x00,file_identifier );
		System.out.println("the number of record files after creating is  : " + user_file_management_file.read_record_user_file_management((byte) 0x01));
		file_identifier[1] = (byte) 0x12 ;
		user_file_management_file.create_or_update_the_file_definition((byte)0x02,(byte)0x80, (byte)0x01,(byte) 0x00, (byte)0x00,file_identifier );
		System.out.println("the number of record files after creating is  : " + user_file_management_file.read_record_user_file_management((byte) 0x02));
		System.out.println("re reading all the recording files : ");
		System.out.println("reading the first  file  : " + user_file_management_file.read_record_user_file_management((byte) 0x00));
		System.out.println("reading the secand file  : " + user_file_management_file.read_record_user_file_management((byte) 0x01));
		System.out.println("reading the third  file  : " + user_file_management_file.read_record_user_file_management((byte) 0x02));

		//		UserFileDataArea user_file = new UserFileDataArea(file_identifier);
//		user_file.select_file();
//		System.out.println("the file selected successefly ");
//		String[] text = { "hello", "world", "00001111222233334444", "01012018" };
//		user_file.personna_the_file(4 , 25 , (byte)0x14 ,  text);
//		System.out.println("the file personnalized successefly ");
//		for(int i = 0; i<4;i++) {
	//		 result = 	user_file.read_record((byte)i, (byte)0x14);
//			System.out.println("reding the record "+ i + " : "+result);
//		}
//	
//
//	acos3.disconnect();
//		String h = "h";
//		System.out.println(h.getBytes().length);

	}

}
