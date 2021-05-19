package ma.ac.inpt.arouche.acos3;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class Acos3 {
	// code issuer du carte acos3
	static String sw_result_code;
	private static byte[] IC_APDU = { (byte) 0x80, 0x20, 0x07, 0x00, 0x08, 0x41, 0x43, 0x4F, 0x53, 0x54, 0x45, 0x53,
			0x54 };
	private Card cardacos3;
	private static byte[] IC_SUBMIT_PIN = { (byte) 0x80, (byte) 0x20, (byte) 0x06, (byte) 0x00, (byte) 0x08, 0x00, 0x00,
			0x00, 0X00, 0x00, 0x00, 0x00, 0x00 };
	private static byte[] CLEAR_CARD_CODE = { (byte) 0x80, (byte) 0x30, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
	static CardChannel channel; // la chaine pour communiquer avec la carte

	public Acos3(CardTerminal reader) throws Exception {
		while (!reader.isCardPresent())
			System.out.println("please insert une carte ....");
		;
		System.out.println("la carte et inserer avec success");
		cardacos3 = reader.connect("*");
		if (cardacos3 == null)
			throw new Acos3Exception("connecting to the acos3 card", "xxxx");
		else {
			channel = cardacos3.getBasicChannel();
		}

	}

	// get atr de la carte sous forme d'une chaine de caractere
	public String getATR() {
		if (cardacos3 == null)
			return null;
		ATR atr = cardacos3.getATR();
		byte[] atr_bytes = atr.getBytes();
		String atr_hex = HelpersFunctions.bytesToHex(atr_bytes);
		return atr_hex;
	}

	public void submit_issuer_code() throws Exception {
		CommandAPDU Submit_ic_apdu = new CommandAPDU(IC_APDU);
		ResponseAPDU reponse = channel.transmit(Submit_ic_apdu);
		if (reponse.getSW() == 0x9000) {
			System.out.println("code issuer submited avec succès ");
			sw_result_code = Integer.toHexString(reponse.getSW());
		}

		else {
			sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception(" trying to submit the issuer code", Integer.toHexString(reponse.getSW()));

		}
	}

	public void submit_pin_code(String pin) throws Exception {
		byte[] pin_binaire = pin.getBytes();
		for (int j = 0; j < pin_binaire.length; j++)
			IC_SUBMIT_PIN[5 + j] = pin_binaire[j];
		CommandAPDU Submit_IC_code_APDU = new CommandAPDU(IC_SUBMIT_PIN);
		ResponseAPDU reponse = channel.transmit(Submit_IC_code_APDU);
		if (reponse.getSW() == 0x9000) {

			sw_result_code = Integer.toHexString(reponse.getSW());
		}

		else {
			sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception(" trying to submit the pin code", Integer.toHexString(reponse.getSW()));

		}
	}

	public void clear_card() throws Acos3Exception, CardException {

		CommandAPDU Submit_ic_apdu = new CommandAPDU(CLEAR_CARD_CODE);
		ResponseAPDU reponse = channel.transmit(Submit_ic_apdu);
		if (reponse.getSW() == 0x9000) {
			System.out.println("card cleared avec succès ");
			sw_result_code = Integer.toHexString(reponse.getSW());
		}

		else {
			sw_result_code = Integer.toHexString(reponse.getSW());
			throw new Acos3Exception(" trying to clear the card", Integer.toHexString(reponse.getSW()));

		}
	}

	public String get_the_last_sw_result_code() {
		return sw_result_code;
	}

	public static CardChannel get_channel() {
		return channel;
	}

	public void disconnect() throws CardException {
		cardacos3.disconnect(true);
	}

}
