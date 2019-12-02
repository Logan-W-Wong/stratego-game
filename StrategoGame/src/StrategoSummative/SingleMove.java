/*
 * Author: Logan Wong
 * Date started: 12/16/2016
 * Last Modified: 1/25/2017
 * Purpose: Used to get moves of pieces, pieces that can move 1 square extend from this class
 */
package StrategoSummative;
import java.util.ArrayList;

public class SingleMove extends Piece implements java.io.Serializable{
	
	private static final long serialVersionUID = 5725445299526295132L;
	public SingleMove(char type, boolean red) {
		super(type, red);
	}
	@Override
	protected ArrayList<Movement> getAllPossibilities() {
		ArrayList<Movement> valid = new ArrayList<Movement>();
		Board board = Board.getBoard();
		Square current = board.posOfPiece(this);

		addIfValid(valid, board.getNextSquare(current, -1, 0));
		addIfValid(valid, board.getNextSquare(current, 0, 1));
		addIfValid(valid, board.getNextSquare(current, 0, -1));
		addIfValid(valid, board.getNextSquare(current, 1, 0));

		return valid;
	}
	
}
