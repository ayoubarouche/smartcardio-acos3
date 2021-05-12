package ma.ac.inpt.arouche;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

public class Acos3Project {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	//	TerminalFactory tf = TerminalFactory.getDefault();
//		CardTerminals readers = tf.terminals();
//		String firstTest = "Gemalto Prox-DU Contact_12400715 0";
//		CardTerminal myReader = readers.getTerminal(firstTest);
//		Acos3 acos3 = new Acos3(myReader);
//		acos3.submit_issuer_code();
//		PersonnalizedFile personnalized_file = new PersonnalizedFile();
//		// personnalized_file.select_personnalized_file();
//		System.out.println("the personnalized file selected successefly");
	//	System.out.println(personnalized_file.read_record_personnalized_file());
//		personnalized_file.create_n_file((byte) 0x01);
//		System.out.println("the number of file added successefly");
//		System.out.println("the personnalized file "+ personnalized_file.read_record_personnalized_file());
//		UserFileManagementFile user_file_management_file = new UserFileManagementFile();
//		user_file_management_file.select_user_file_management();
//		System.out.println("the number of record files  : " + user_file_management_file.read_record_user_file_management((byte) 0x01));
//		byte[] file_identifier = { (byte) 0xAA, 0x10 };
//		user_file_management_file.create_a_new_file_definition((byte)0x14, (byte)0x04,(byte) 0x00, (byte)0x00,file_identifier );
//		System.out.println("the number of record files after creating is  : " + user_file_management_file.read_record_user_file_management((byte) 0x01));
//		UserFileDataArea user_file = new UserFileDataArea(file_identifier);
//		user_file.select_file();
//		System.out.println("the file selected successefly ");
//		String[] text = { "Mr", "CHAMI Mouhcine", "0000111122223333", "01012018" };
//
//		user_file.personna_the_file(text);
//		System.out.println("the file personnalized successefly ");
//		for(int i = 0; i<4;i++) {
//			String result = 	user_file.read_record((byte)i, (byte)0x14);
//			System.out.println("reding the record "+ i + " : "+result);
//		}
//	
//
//		acos3.disconnect();
		String h = "h";
		System.out.println(h.getBytes().length);

	}

}
