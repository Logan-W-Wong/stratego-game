/*
 * Author: Logan Wong
 * Date started: 12/16/2016
 * Last Modified: 1/25/2017
 * Purpose: Squares theoretical board contains
 */
package StrategoSummative;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class Square implements java.io.Serializable{

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 689878789889675841L;

	private static final int WIN = 1;

	private static final int LOSE = -1;

	private static final int TIE = 0;

	private int xCoor;

	private int yCoor;

	// holds how many pieces of same type are contained on side panel
	private int number;

	private ArrayList<Piece> pieceList = new ArrayList<>();

	public Piece nextPiece() {
		return pieceList.get(number - 1);
	}

	public void addPiece(Piece piece) {
		pieceList.add(piece);
	}

	private boolean isBlocked;

	private int redStart;

	public void setRedStart(int redStart) {
		this.redStart = redStart;
	}

	public int isRedStart() {
		return redStart;
	}

	private Piece piece;

	public Square(int xCoor, int yCoor) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.piece = null;

	}

	public Square(int number) {
		this.number = number;
	}

	public int getxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
		if (StrategoFrame.errorCatch == false) {
			if (piece != null) {
				getButton().changeIcon(piece.getType() + piece.getColour());
			} else {
				getButton().changeIcon("");
			}
		}
	}

	public SquareButton getButton() {
		StrategoFrame StrategoGame = StrategoFrame.getStrategoGame();

		try {
			for (Component btn : StrategoGame.getBoardPanel().getComponents()) {
				if (btn instanceof SquareButton) {
					SquareButton myButton = (SquareButton) btn;
					if (myButton.getSquare() == this) {
						return myButton;
					}
				}
			}
		} catch (Exception e) {
		}
		try {
			for (Component btn : StrategoGame.getRedPiecePanel()
					.getComponents()) {
				if (btn instanceof SquareButton) {
					SquareButton myButton = (SquareButton) btn;
					if (myButton.getSquare() == this) {
						return myButton;
					}
				}
			}
		} catch (Exception e) {
		}
		try {
			for (Component btn : StrategoGame.getBluePiecePanel()
					.getComponents()) {
				if (btn instanceof SquareButton) {
					SquareButton myButton = (SquareButton) btn;
					if (myButton.getSquare() == this) {
						return myButton;
					}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static void removeBorder() {
		for (Component btn : StrategoFrame.getStrategoGame().getBoardPanel()
				.getComponents()) {
			SquareButton button = (SquareButton) btn;
			button.setBorder(new LineBorder(Color.BLACK, 2));
		}
	}

	public void onClick(SquareButton btn) {
		// Instance of board
		Board board = Board.getBoard();
		// Already selected square
		Square alreadySelected = board.getSelected();

		boolean canMove = false;

		boolean myMove = false;

		ArrayList<Square> moveTo = new ArrayList<>();

		ArrayList<Square> possibilities = new ArrayList<>();

		Movement thisMove = null;

		for (Movement move : board.getMoves()) {
			if (move.getFrom() == board.getSelected() && move.getTo() == this) {
				myMove = true;
				thisMove = move;
			}
			if (!possibilities.contains(move.getFrom())) {
				possibilities.add(move.getFrom());
			}

			if (move.getFrom() == this) {
				canMove = true;
				moveTo.add(move.getTo());

			}
		}
		if (piece != null && piece.isRed() == board.isRedTurn()) {
			if (alreadySelected != null) {
				if (this == alreadySelected) {
					removeBorder();
					board.setSelected(null);
					btn.setBorder(new LineBorder(Color.BLACK, 2));
				} else if (canMove) {
					removeBorder();
					btn.setBorder(new LineBorder(Color.YELLOW, 2));
					board.setSelected(this);
					for (Square squ : moveTo) {
						squ.getButton().setBorder(
								new LineBorder(Color.YELLOW, 2));
					}
				}

			} else if (canMove) {
				board.setSelected(this);
				getButton().setBorder(new LineBorder(Color.YELLOW, 2));
				for (Square squ : moveTo) {
					squ.getButton().setBorder(new LineBorder(Color.YELLOW, 2));
				}
			} else {

				for (Square squ : possibilities) {
					squ.getButton().setBorder(new LineBorder(Color.YELLOW, 2));
				}

			}

		} else if (myMove == true) {
			removeBorder();
			thisMove.getFrom().getButton()
					.setBorder(new LineBorder(Color.RED, 2));
			getButton().setBorder(new LineBorder(Color.ORANGE, 2));
			if (thisMove.getMoveType() == Movement.AttackMove) {
				getButton().changeIcon(piece.getType() + piece.getColour());
				JOptionPane.showMessageDialog(StrategoFrame.getStrategoGame(),
						"You attacked a " + thisMove.getType1() + " with a "
								+ thisMove.getType2());
				int outcome = compare(thisMove);
				if (outcome == WIN) {
					setPiece(thisMove.getPiece());
					
					thisMove.getFrom().setPiece(null);
				} else if (outcome == TIE) {
					setPiece(null);
					thisMove.getFrom().setPiece(null);
				} else {
					thisMove.getFrom().setPiece(null);
				}
				StrategoFrame.getStrategoGame().nextTurn(thisMove);
			} else {
				setPiece(thisMove.getPiece());
				thisMove.getFrom().setPiece(null);
				StrategoFrame.getStrategoGame().nextTurn(null);
			}

		} else {
			removeBorder();
			for (Square squ : possibilities) {
				squ.getButton().setBorder(new LineBorder(Color.YELLOW, 2));
			}
		}

	}

	public int compare(Movement move) {

		char attacker = move.getPiece().getType();
		char defender = move.getAttackedPiece().getType();
		if (Character.getNumericValue(defender) > 9) {
			if (defender == 'B') {
				if (attacker == '2') {
					return WIN;
				} else {
					return LOSE;
				}
			} else if (defender == 'S') {
				if (attacker == 'S') {
					return TIE;
				}
				return WIN;
			} else {
				Board board = Board.getBoard();
				board.redWin = board.isRedTurn();
				board.endOfGame = true;
				StrategoFrame.getStrategoGame().endGame();
				return WIN;
			}
		} else if (Character.getNumericValue(attacker) > 9) {
			if (defender == '9') {
				return WIN;
			}
			if (defender == 'S') {
				return TIE;
			}
			return LOSE;

		} else {
			if (defender == attacker) {
				return TIE;
			} else if (attacker > defender) {
				return WIN;
			} else {
				return LOSE;
			}
		}

	}

	public void sideClick(SquareButton btn) {
		// Instance of board
		Board board = Board.getBoard();
		// Already selected square
		Square alreadySelected = board.getSideSelected();
		if (alreadySelected != null) {
			if (board.isRedTurn() == piece.isRed())
				if (this == alreadySelected) {
					board.setSideSelected(null);
					btn.setBorder(new LineBorder(Color.BLACK, 2));
				} else if (number != 0) {
					board.getSideSelected().getButton()
							.setBorder(new LineBorder(Color.BLACK, 2));
					btn.setBorder(new LineBorder(Color.YELLOW, 2));
					board.setSideSelected(this);
				} else if (number == 0) {
					JOptionPane.showMessageDialog(
							StrategoFrame.getStrategoGame(),
							"You have already placed all of these pieces.");
				} else {
					JOptionPane.showMessageDialog(
							StrategoFrame.getStrategoGame(),
							"Select One Of your Own Pieces");
				}
		} else if (number != 0) {
			if (board.isRedTurn() == piece.isRed()) {
				board.setSideSelected(this);
				btn.setBorder(new LineBorder(Color.YELLOW, 2));
			} else {
				JOptionPane.showMessageDialog(StrategoFrame.getStrategoGame(),
						"Select One Of your Own Pieces");
			}
		} else {
			JOptionPane.showMessageDialog(StrategoFrame.getStrategoGame(),
					"You have already placed all of these pieces.");
		}

	}

	public int getNumber() {
		return number;
	}

	public void placeClick(SquareButton btn) {
		Board board = Board.getBoard();

		Square toPutOnBoard = board.getSideSelected();
		if ((board.isRedTurn() == true && redStart == 1)
				|| (board.isRedTurn() != true && redStart == 0)) {
			if (toPutOnBoard != null) {
				if (board.getSelected() != null) {
					board.getSelected().getButton()
							.setBorder(new LineBorder(Color.BLACK, 2));
					board.setSelected(null);
				}

				if (piece == null) {
					setPiece(toPutOnBoard.nextPiece());
					toPutOnBoard.subtract();
					btn.setBorder(new LineBorder(Color.BLACK, 2));
				}

			} else if (board.getSelected() != null) {
				if (btn.getSquare().getPiece() != null) {
					Piece holder = btn.getSquare().getPiece();
					btn.getSquare().setPiece(board.getSelected().getPiece());
					btn.setBorder(new LineBorder(Color.BLACK, 2));
					board.getSelected().setPiece(holder);
					board.getSelected().getButton()
							.setBorder(new LineBorder(Color.BLACK, 2));
					board.setSelected(null);
				} else {
					btn.getSquare().setPiece(board.getSelected().getPiece());
					btn.setBorder(new LineBorder(Color.BLACK, 2));
					board.getSelected().getButton()
							.setBorder(new LineBorder(Color.YELLOW, 2));
					board.getSelected().setPiece(null);
					board.setSelected(null);
				}
			} else if (btn.getSquare().getPiece() != null) {
				board.setSelected(btn.getSquare());
				btn.setBorder(new LineBorder(Color.BLUE, 2));

			} else {
				JOptionPane.showMessageDialog(StrategoFrame.getStrategoGame(),
						"Select a Piece first.");
			}
		} else {
			JOptionPane.showMessageDialog(StrategoFrame.getStrategoGame(),
					"Place on one of the highlighted squares.");
		}
	}

	public void subtract() {
		number -= 1;
		if (number == 0) {
			getButton().changeIcon(piece.getColour() + "");
			Board board = Board.getBoard();
			board.setSideSelected(null);
			this.getButton().setBorder(new LineBorder(Color.BLACK, 2));
		}
	}
}
