package StrategoSummative;
import java.util.ArrayList;


public class Scout extends Piece implements java.io.Serializable{
	
	private static final long serialVersionUID = -5753758355337710079L;
	
	public Scout(boolean red){
		super('1', red);
	}
	@Override
	protected ArrayList<Movement> getAllPossibilities() {
		ArrayList<Movement> valid = new ArrayList<Movement>();
		Board board = Board.getBoard();
		Square current = board.posOfPiece(this);

		addLineIfValid(valid, current, 1, 0);
		addLineIfValid(valid, current, 0, -1);
		addLineIfValid(valid, current, -1, 0);
		addLineIfValid(valid, current, 0, 1);
		return valid;
	}
	
}


