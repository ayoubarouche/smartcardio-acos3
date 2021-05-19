package ma.ac.inpt.arouche.acos3;

public class HelpersFunctions {

	public static String bytesToHex(byte[] atr) {

		StringBuilder sb = new StringBuilder();
		for (byte b : atr) {
			sb.append(String.format("%02X ", b));
		}
		return sb.toString();

	}

	public static String stringArrayToString(String[] array) {
		String result = "";
		for (String parcour : array)
			result += parcour;
		return result;
	}
}
