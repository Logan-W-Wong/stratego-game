package StrategoSummative;
import java.util.ArrayList;


public class Flag extends Piece implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2581446350228874789L;
	public Flag(boolean red){
		super('F', red);
	}
	protected ArrayList<Movement> getAllPossibilities() {
		ArrayList<Movement> valid = new ArrayList<Movement>(); 
		return valid;	}
}
