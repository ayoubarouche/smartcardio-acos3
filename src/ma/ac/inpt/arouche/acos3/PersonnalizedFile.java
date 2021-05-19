package ma.ac.inpt.arouche.acos3;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class PersonnalizedFile {
	//read the personnalized file 
	private Acos3Files personnalized_file= Acos3Files.PERSONNALIZED_FILE;
	private CardChannel channel;
	//select the personnalized file 

	public PersonnalizedFile(CardChannel channel) {
		this.channel = channel;
	}
	public PersonnalizedFile() {
		this.channel = Acos3.get_channel();
	}
	public String read_record_personnalized_file() throws Exception {
		CommandAPDU submit_read_record = new CommandAPDU(Acos3Commands.get_read_file_byte_code(personnalized_file));
		ResponseAPDU reponse = channel.transmit(submit_read_record);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			return HelpersFunctions.bytesToHex(reponse.getData());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("read personnalized file", Integer.toHexString(reponse.getSW()));
		}
	
	}
	
	public void select_personnalized_file()throws Exception {
		CommandAPDU submit_select = new CommandAPDU(Acos3Commands.get_select_file_byte_code(personnalized_file));
		ResponseAPDU reponse = channel.transmit(submit_select);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("select personnalized file", Integer.toHexString(reponse.getSW()));
		}
		
	}
	public void create_n_file(byte number_of_files_to_create) throws Exception {
		CommandAPDU create_number_of_files = new CommandAPDU(Acos3Commands.get_write_nof__byte_code(number_of_files_to_create));
		ResponseAPDU reponse = channel.transmit(create_number_of_files);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("add the number of files to personnalized file", Integer.toHexString(reponse.getSW()));
		}
	}
	
}
