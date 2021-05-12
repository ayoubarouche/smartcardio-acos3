package ma.ac.inpt.arouche;

public class Acos3Exception extends Exception{
	public Acos3Exception(String what_you_wanted_to_do , String erreur_code) {
		System.err.println("erreur dans : "+ what_you_wanted_to_do + " erreur code est : "+ erreur_code);
	}
}
