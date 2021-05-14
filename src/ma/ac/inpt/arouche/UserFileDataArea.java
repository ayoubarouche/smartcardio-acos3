package ma.ac.inpt.arouche;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class UserFileDataArea {
	private Acos3Files personnalized_file = Acos3Files.PERSONNALIZED_FILE;
	private CardChannel channel;
	// select the personnalized file
	private byte[] file_identifier;

	public UserFileDataArea(CardChannel channel, byte[] file_identifier) {
		this.channel = channel;
		this.file_identifier = file_identifier;
	}

	public UserFileDataArea(byte[] file_identifier) {
		this.channel = Acos3.get_channel();
		this.file_identifier = file_identifier;
	}

	public void select_file() throws Exception {
		CommandAPDU submit_select = new CommandAPDU(Acos3Commands.get_select_file_byte_code(file_identifier));
		ResponseAPDU reponse = channel.transmit(submit_select);
		if (reponse.getSW() == 0x9000 || reponse.getSW() == 0x9100) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
		}

		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("select an user file", Integer.toHexString(reponse.getSW()));
		}

	}

	public void personna_the_file(int nombre_record, int write_record_length,byte record_length, String[] file_text) throws Exception {
		int i;
		for (i = 0; i < nombre_record; i++) {
			byte[] nom_b = file_text[i].getBytes();
			byte[] write_record = new byte[write_record_length];
			write_record[0] = (byte) 0x80;
			write_record[1] = (byte) 0xD2;
			write_record[2] = (byte) i;
			write_record[3] = (byte) 0x00;
			write_record[4] = (byte) record_length;
			for (int j = 0; j < nom_b.length; j++) {
				
				write_record[5 + j] = nom_b[j];

			}

			CommandAPDU write_apdu = new CommandAPDU(write_record);
			ResponseAPDU reponse = channel.transmit(write_apdu);
			if (reponse.getSW() == 0x9000) {
				Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			}

			else {
				Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
				throw new Acos3Exception("writing inside the file", Integer.toHexString(reponse.getSW()));
			}
		}

	}
	public String read_record(byte record, byte number_of_bytes ) throws Exception {
		byte[] read_record = { (byte) 0x80, (byte) 0xB2, (byte) record, 0x00, (byte) number_of_bytes };
		CommandAPDU write_apdu = new CommandAPDU(read_record);
		ResponseAPDU reponse = channel.transmit(write_apdu);
		if (reponse.getSW() == 0x9000) {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
		
			return new String(reponse.getData());
		}
		else {
			Acos3.sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception("reading the file", Integer.toHexString(reponse.getSW()));
		}
	}
	
	
}
