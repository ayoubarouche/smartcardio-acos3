package ma.ac.inpt.arouche.acos3;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class UserFileManagementFile {
	private Acos3Files user_file_management_file = Acos3Files.USER_FILE_MANAGEMENT_FILE;
	private CardChannel channel;
	public UserFileManagementFile(CardChannel channel) {
		this.channel = channel;
	}
	public UserFileManagementFile() {
		this.channel = Acos3.get_channel();
	}
	public String read_record_user_file_management(byte number_of_file) throws Exception {
		byte[] read_command= Acos3Commands.get_read_file_byte_code(user_file_management_file);
		read_command[4] = (byte) (0x06);
		read_command[2] = (byte)number_of_file;
 		CommandAPDU submit_read_record = new CommandAPDU(read_command);
		
		ResponseAPDU reponse = channel.transmit(submit_read_record);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			return HelpersFunctions.bytesToHex(reponse.getData());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("read user file management file", Integer.toHexString(reponse.getSW()));
		}
	
	}
	
	public void select_user_file_management()throws Exception {
		CommandAPDU submit_select = new CommandAPDU(Acos3Commands.get_select_file_byte_code(user_file_management_file));
		ResponseAPDU reponse = channel.transmit(submit_select);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("select user file management file", Integer.toHexString(reponse.getSW()));
		}
		
	}
	public void create_or_update_the_file_definition(byte which_record, byte record_length, byte number_of_records,
			byte read_security_attribute, byte write_security_attribute, byte[] file_identifier)throws Exception{
			CommandAPDU submit_create = new CommandAPDU(Acos3Commands.get_create_new_file_in_user_file_management_byte_code(which_record,record_length , number_of_records , read_security_attribute , write_security_attribute , file_identifier));
			ResponseAPDU reponse = channel.transmit(submit_create);
			if(reponse.getSW()== 0x9000) {
				Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			}
			
			else {
				Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
				throw new Acos3Exception("create a new file in file user management", Integer.toHexString(reponse.getSW()));
			}
	}
}
