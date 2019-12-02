package StrategoSummative;
import java.util.ArrayList;


public class Bomb extends Piece implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7470055892841640287L;
	public Bomb(boolean red){
		super('B', red);
	}
	protected ArrayList<Movement> getAllPossibilities() {
		ArrayList<Movement> valid = new ArrayList<Movement>(); 
		return valid;
	}
}
