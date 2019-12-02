
/**
 * Piece class that all pieces extend from
 * Date created: 22 December 2016
 * Last modified: 25 January 2016
 * @author Logan Wong
 */
package StrategoSummative;
import java.util.ArrayList;

public abstract class Piece implements java.io.Serializable{
	
	private static final long serialVersionUID = 2611067660860407383L;

	//type of piece as char
	char type;
	
	//colour of piece as boolean
	boolean red;
	
	
		
	public Piece(char type, boolean red){
		this.type = type;
		
		this.red = red;
				
		
	}
	
	protected abstract ArrayList<Movement> getAllPossibilities();
		
	public ArrayList<Movement> getAllMoves(){
		ArrayList<Movement> possible = new ArrayList<Movement>();		
			for(Movement move : getAllPossibilities()){
				if(move.getMoveType() != Movement.AllyAttack){
					possible.add(move);		
				}
			}
		
		return possible;
	}

	public char getType() {
		return type;
	}
	
	public String getColour(){
		if(red){
			return "Red";
		}
		else{
			return "Blue";
		}
	}
	
	public boolean isRed() {
		return red;
	}

	protected void addIfValid(ArrayList<Movement> list, Square square) {
		if (square != null && square.isBlocked() == false) {
			list.add(new Movement(this, Board.getBoard().posOfPiece(this), square));
		}
	}
	
	protected void addLineIfValid(ArrayList<Movement> list, Square current, int h, int v) {
		Board board = Board.getBoard();
		boolean canContinue = true;
		
		Square possible = current;
		
		while(canContinue){
			possible = board.getNextSquare(possible, h, v);
			
			if(possible != null && possible.getPiece() == null && possible.isBlocked() == false){
				list.add(new Movement(current.getPiece(), current, possible));	
			}
			else if(possible != null && possible.getPiece() != null){
				list.add(new Movement(current.getPiece(), current, possible));		
				canContinue = false;
			}
			else{
				canContinue = false;
			}
		}
		
	}
		
		
}
