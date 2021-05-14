package ma.ac.inpt.arouche;

public class HelpersFunctions {
	
	public static String bytesToHex(byte[] atr) {

		StringBuilder sb = new StringBuilder();
		for (byte b : atr) {
			sb.append(String.format("%02X ", b));
		}
		return sb.toString();

	}
	
	public static void writeStringToFile() {
		
	}
	public static void generate_public_private_key() {
		
	}
}
