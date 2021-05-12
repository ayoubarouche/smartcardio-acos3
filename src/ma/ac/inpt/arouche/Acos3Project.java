package ma.ac.inpt.arouche;

import java.util.List;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public class Acos3Project {
	private static byte[] IC_APDU = {(byte)0x80, 0x20,0x07,0x00, 0x08, 
			0x41, 0x43, 0x4F, 0x53, 0x54, 0x45,0x53, 0x54};
	private static byte[] read_personnalized_file = {
			(byte)0x80, (byte) 0xB2, 0x01,0x00,0x04
	};
	private static byte[] select_personnalized_file = {
			(byte)0x80, (byte) 0xA4, 0x00,0x00,0x02,(byte) 0xFF,0x02
	};
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TerminalFactory tf = TerminalFactory.getDefault();
		CardTerminals readers = tf.terminals();
		List<CardTerminal> readersList = readers.list();
		String firstTest = "Gemalto Prox-DU Contact_12400715 0";
		String secandTest = "Gemalto Prox-DU Contactless_12400715 0";
		// for (CardTerminal ct : readersList) {
		// System.out.println("the name of the reader is : " + ct.getName());
		// }

		CardTerminal myReader = readers.getTerminal(firstTest);
		while (!myReader.isCardPresent())
			System.out.println("please insert une carte");
		;
		Card card = myReader.connect("*");
		System.out.println("Terminal connecter");
		if (card != null) {
			ATR atr = card.getATR();
			byte[] atr_bytes = atr.getBytes();
			String atr_hex = bytesToHex(atr_bytes);

			System.out.println("atr de la carte : " + atr_hex);
			System.out.println("le protocol de la carte est : " + card.getProtocol());

			CardChannel channel = card.getBasicChannel();
				traitement(channel);
				select_personnalized_file(channel);
				read_record_personnalized_file(channel);
			card.disconnect(true);
		} else
			System.out.println("error dans la carte");

	}

	public static String bytesToHex(byte[] atr) {

		StringBuilder sb = new StringBuilder();
		for (byte b : atr) {
			sb.append(String.format("%02X ", b));
		}
		return sb.toString();

	}
	public static void traitement(CardChannel channel) throws Exception{
		CommandAPDU Submit_ic_apdu = new CommandAPDU(IC_APDU);
		ResponseAPDU reponse = channel.transmit(Submit_ic_apdu);
		if(reponse.getSW()== 0x9000) {
			System.out.println("code issuer submited successefly ");
			System.out.println("the value of the code issuer is : "+Integer.toHexString(reponse.getSW()));
		}
		
		else System.out.println("error dans le code issuer");
	}
	public static void clear_card(CardChannel channel) {
		
	}
	
	public static void read_record_personnalized_file(CardChannel channel) throws Exception {
		CommandAPDU submit_read_record_personnalized_file = new CommandAPDU(read_personnalized_file);
		ResponseAPDU reponse = channel.transmit(submit_read_record_personnalized_file);
		if(reponse.getSW()== 0x9000) {
			System.out.println("read personnalized file successefly ");
			System.out.println("the value of the code issuer for read personna file is : "+Integer.toHexString(reponse.getSW()));
			System.out.println("the result of reading personna file is : "+bytesToHex(reponse.getData()));
		}
		
		else System.out.println("error dans le code pour reading personnalized file");
	
	}
	public static void select_personnalized_file(CardChannel channel)throws Exception {
		CommandAPDU submit_select_personnalized_file = new CommandAPDU(select_personnalized_file);
		ResponseAPDU reponse = channel.transmit(submit_select_personnalized_file);
		if(reponse.getSW()== 0x9000) {
			System.out.println("select personnalized file successefly ");
			System.out.println("the value of the code issuer for personna file is : "+Integer.toHexString(reponse.getSW()));
		}
		
		else System.out.println("error dans le code pour personnalized file");
	}
	
}
