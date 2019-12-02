/*
 * Author: Logan Wong
 * Date started: 12/16/2016
 * Last Modified: 1/25/2017
 * Purpose: Movement class, each possible move defined as an instance
 */
package StrategoSummative;

public class Movement implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2038004403543703631L;

	//Value of attacking an ally
	public static final int AllyAttack = 0;
	
	//value of attacking an enemy
	public static final int AttackMove = 1;
	
	//value of move with no attack
	public static final int Move = 2;
	//holds the type of move	
	public int MoveType;
	//holds the piece moving
	private Piece piece;
	//holds the piece attacked
	private Piece attackedPiece;
	//holds the type of moving piece
	private String type1;
	//holds type of attacked piece
	private String type2;
	//holds initial square
	private Square from;
	//holds square where piece will end up
	private Square to;
	
	public Movement(Piece piece, Square from, Square to){
		this.piece = piece;
		this.from = from;
		this.to = to;
		//if there is a piece on square to
		if(to.getPiece() != null){
			attackedPiece = to.getPiece();
			type1 = attackedPiece.getType() + "";
			type2 = piece.getType() + "";
			try{
				type1 = String.valueOf(Integer.valueOf(type1) + 1);
			}catch(Exception e){}
			try{
				type2 = String.valueOf(Integer.valueOf(type2) + 1);
			}catch(Exception e){}
			if(attackedPiece.isRed() == piece.isRed()){
				MoveType = AllyAttack;
			}
			else{
			MoveType = AttackMove;
			}
		}
		else{
			MoveType = Move;
		}
		
	}

	public String getType1() {
		return type1;
	}

	public String getType2() {
		return type2;
	}

	public int getMoveType() {
		return MoveType;
	}

	public void setMoveType(int moveType) {
		MoveType = moveType;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Piece getAttackedPiece() {
		return attackedPiece;
	}

	public void setAttackedPiece(Piece attackedPiece) {
		this.attackedPiece = attackedPiece;
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

	public static int getAllyattack() {
		return AllyAttack;
	}

	public static int getAttackmove() {
		return AttackMove;
	}

	public static int getMove() {
		return Move;
	}
}
