package ma.ac.inpt.arouche.acos3;

public class Acos3Commands {
	private static byte[] SELECT_APDU_FILE = { (byte) 0x80, (byte) 0xA4, 0x00, 0x00, 0x02, (byte) 0xFF, 0x00 };

	private static byte[] READ_APDU_FILE = { (byte) 0x80, (byte) 0xB2, 0x00, 0x00, 0x00 };

	private static byte[] CREATE_APDU_N_FILES = { (byte) 0x80, (byte) 0xD2, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00 };
	private static byte[] ADD_THE_FILE_ATTRIBUTES = { (byte) 0x80, (byte) 0xD2, 0x00, 0x00, 0x06, 0x14, 0x04, 0x00,
			0x00, (byte) 0xAA, 0x10 };
	

	public static byte[] get_select_file_byte_code(Acos3Files file) {
		switch (file) {
		case PERSONNALIZED_FILE:
			SELECT_APDU_FILE[5] = (byte) 0xFF;
			SELECT_APDU_FILE[6] = 0x02;
			break;
		case ACCOUNT_FILE:
			break;
		case ACCOUNT_SECURITY_FILE:
			break;
		case SECURITY_FILE:
			SELECT_APDU_FILE[5] = (byte) 0xFF;
			SELECT_APDU_FILE[6] = 0x03;
			break;
		case USER_FILE_DATA_AREA:
			break;
		case USER_FILE_MANAGEMENT_FILE:
			SELECT_APDU_FILE[5] = (byte) 0xFF;
			SELECT_APDU_FILE[6] = 0x04;
			break;
		}
		return SELECT_APDU_FILE;
	}

	public static byte[] get_read_file_byte_code(Acos3Files file) {
		switch (file) {
		case PERSONNALIZED_FILE:
			READ_APDU_FILE[4] = 0x04;

			break;
		case ACCOUNT_FILE:
			break;
		case ACCOUNT_SECURITY_FILE:
			break;
		case SECURITY_FILE:
			READ_APDU_FILE[4] = (byte) (0x08);
			break;
		case USER_FILE_DATA_AREA:
			break;
		case USER_FILE_MANAGEMENT_FILE:
			break;
		}
		return READ_APDU_FILE;
	}

	public static byte[] get_write_nof__byte_code(byte number_of_files) {
		CREATE_APDU_N_FILES[7] = number_of_files;
		return CREATE_APDU_N_FILES;
	}

	public static byte[] get_create_new_file_in_user_file_management_byte_code(byte record_numero,byte record_length, byte number_of_records,
			byte read_security_attribute, byte write_security_attribute, byte[] file_identifier) {
			ADD_THE_FILE_ATTRIBUTES[2]  = record_numero;
			ADD_THE_FILE_ATTRIBUTES[5]  = record_length ; 
			ADD_THE_FILE_ATTRIBUTES[6]  = number_of_records ; 
			ADD_THE_FILE_ATTRIBUTES[7]  = read_security_attribute ;
			ADD_THE_FILE_ATTRIBUTES[8]  = write_security_attribute ;
			ADD_THE_FILE_ATTRIBUTES[9]  = file_identifier[0] ;
			ADD_THE_FILE_ATTRIBUTES[10] = file_identifier[1] ; 
			return ADD_THE_FILE_ATTRIBUTES ;
	}
	public static byte[] get_select_file_byte_code(byte[] file_identifier) {
		SELECT_APDU_FILE[5] = file_identifier[0];
		SELECT_APDU_FILE[6] = file_identifier[1];
		return SELECT_APDU_FILE;
	}
	public static byte[] write_to_the_file_byte_code(byte record_length_include_apdu_header , byte record_number , byte[] what_to_write) {
		byte[] write_record = new byte[record_length_include_apdu_header];
		write_record[0] = (byte) 0x80;
		write_record[1] = (byte) 0xD2;
		write_record[2] = record_number;
		write_record[3] = (byte) 0x00;
		write_record[4] = (byte) 0x80;
		
		return write_record;
	}

}
