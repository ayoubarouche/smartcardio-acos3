package ma.ac.inpt.arouche;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class SecurityFile {
	private Acos3Files security_file= Acos3Files.SECURITY_FILE;
	private CardChannel channel;
	//select the personnalized file 

	public SecurityFile(CardChannel channel) {
		this.channel = channel;
	}
	public SecurityFile() {
		this.channel = Acos3.get_channel();
	}
	public String read_record_security_file() throws Exception {
		CommandAPDU submit_read_record = new CommandAPDU(Acos3Commands.get_read_file_byte_code(security_file));
		ResponseAPDU reponse = channel.transmit(submit_read_record);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			return HelpersFunctions.bytesToHex(reponse.getData());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("read security file", Integer.toHexString(reponse.getSW()));
		}
	
	}
	public void select_securtiy_file()throws Exception {
		CommandAPDU submit_select = new CommandAPDU(Acos3Commands.get_select_file_byte_code(security_file));
		ResponseAPDU reponse = channel.transmit(submit_select);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("select security file", Integer.toHexString(reponse.getSW()));
		}
		
	}
	public String read_record_security_file(byte which_record_to_read) throws Exception {
		byte[] read_command= Acos3Commands.get_read_file_byte_code(security_file);
		read_command[2] = (byte) (which_record_to_read);
		CommandAPDU submit_read_record = new CommandAPDU(read_command);
		
		ResponseAPDU reponse = channel.transmit(submit_read_record);
		if(reponse.getSW()== 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			return HelpersFunctions.bytesToHex(reponse.getData());
		}
		
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("reading the security file", Integer.toHexString(reponse.getSW()));
		}
	
	}
	public void update_pin_code(String pin) throws Exception{
		byte[] write_record ; 
		   write_record = new byte[13];
		   write_record[0]=(byte) 0x80;
		   write_record[1]=(byte) 0xD2;
		   write_record[2]=0x01; //code PIN
		   write_record[3]=0x00;	        	
		   write_record[4]=0x08;
		   byte[] binaire_du_pin = pin.getBytes();
		   for (int j=0;j<binaire_du_pin.length;j++)
			   write_record[5+j]=binaire_du_pin[j];
		   CommandAPDU submit_write_pin = new CommandAPDU(write_record);
		   ResponseAPDU reponse  = channel.transmit(submit_write_pin);
		   if(reponse.getSW()== 0x9000) {
				Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
				
			}
			
			else {
				Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
				throw new Acos3Exception("writing the pin code", Integer.toHexString(reponse.getSW()));
			}

	}
}
