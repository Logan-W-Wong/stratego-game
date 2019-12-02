/*
 * Author: Logan Wong
 * Date started: 12/16/2016
 * Last Modified: 1/25/2017
 * Purpose: Game board, holda array of squares
 */
package StrategoSummative;

import java.util.ArrayList;

public class Board implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9096264024985700483L;
	private static Board board = new Board();
	// holds grid
	private Square[][] grid;

	// holds selected square
	private Square selected;

	// holds square selected on side panel
	private Square sideSelected;

	public Square getSideSelected() {
		return sideSelected;
	}

	public void setSideSelected(Square sideSelected) {
		this.sideSelected = sideSelected;
	}

	// holds whose turn
	private boolean redTurn;

	// holds last move made
	private Square from;

	private ArrayList<Piece> redPieces = new ArrayList<>();

	private ArrayList<Piece> bluePieces = new ArrayList<>();

	// holds possible moves
	private ArrayList<Movement> moves = new ArrayList<>();

	public ArrayList<Piece> getRedPieces() {
		return redPieces;
	}

	public void setRedPieces(ArrayList<Piece> redPieces) {
		this.redPieces = redPieces;
	}

	public ArrayList<Piece> getBluePieces() {
		return bluePieces;
	}

	public void setBluePieces(ArrayList<Piece> bluePieces) {
		this.bluePieces = bluePieces;
	}

	private Square to;

	public boolean endOfGame;

	public boolean redWin;

	public boolean isEndOfGame() {
		return endOfGame;
	}

	public boolean isRedWin() {
		return redWin;
	}

	public ArrayList<Movement> getMoves() {
		return moves;
	}

	public void setMoves() {
		moves.clear();
		for (Square[] row : grid) {
			for (Square mySquare : row) {
				if (mySquare.getPiece() != null
						&& mySquare.getPiece().isRed() == redTurn) {
					ArrayList<Movement> myArray = new ArrayList<>(mySquare
							.getPiece().getAllMoves());
					if (!myArray.isEmpty()) {
						moves.addAll(mySquare.getPiece().getAllMoves());
					}
				}
			}
		}
	}

	public Square posOfPiece(Piece curr) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (curr == grid[i][j].getPiece()) {
					// Check if the pieces are the same
					return grid[i][j];
				}
			}
		}
		return null;
	}

	public static Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		Board.board = board;
	}

	public Square getSquare(int h, int v) {
		return grid[h][v];
	}

	public boolean isRedTurn() {
		return redTurn;
	}

	public Square[][] getGrid() {
		return grid;
	}

	public void setGrid(Square[][] grid) {
		this.grid = grid;
	}

	public Square getSelected() {
		return selected;
	}

	public void setSelected(Square selected) {
		this.selected = selected;
	}

	public void setRedTurn(boolean redTurn) {
		this.redTurn = redTurn;
	}

	public Square getFrom() {
		return from;
	}

	public void setFrom(Square from) {
		this.from = from;
	}

	public Square getTo() {
		return to;
	}

	public void setTo(Square to) {
		this.to = to;
	}

	public Square getNextSquare(Square square, int x, int y) {
		int h = square.getxCoor() + x;
		int v = square.getyCoor() + y;

		if (h > 9 || h < 0 || v > 9 || v < 0) {
			return null;
		}
		return getSquare(h, v);
	}

	public Board() {

		grid = new Square[10][10];

		setGrid();

	}
	
	public void setGrid(){
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid[i][j] = new Square(i, j);
				if (j < 4) {
					grid[i][j].setRedStart(0);
				} else if (j == 4 || j == 5) {
					if (i == 2 || i == 3 || i == 6 || i == 7) {
						grid[i][j].setBlocked(true);
					}
					grid[i][j].setRedStart(-1);
				} else {
					grid[i][j].setRedStart(1);
				}

			}
		}
	}

}