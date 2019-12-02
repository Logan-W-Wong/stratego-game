/*
 * Author: Logan Wong
 * Date started: 12/16/2016
 * Last Modified: 1/25/2017
 * Purpose: JButtons that contain a square
 */
package StrategoSummative;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class SquareButton extends JButton implements java.io.Serializable{

	private static final long serialVersionUID = -4119109346232418433L;

	final String emptySquare = "SquareGrass";

	final String blockedSquare = "SquareWater";

	// holds how many pieces of same type are contained on side panel

	private Square square;

	public SquareButton(Square square) {
		this.square = square;
		setBorder(new LineBorder(Color.BLACK, 2));
		setFocusPainted(false);

	}

	public void updateButton() {
		Square selected = Board.getBoard().getSelected();
		if (square == selected) {
			setBorder(new LineBorder(Color.YELLOW, 2));
		} else {
			setBorder(new LineBorder(Color.BLACK, 2));
		}
		changeIcon((square.getPiece().getType()) + "");
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public void changeIcon(String image) {
		if(image.length() != 0 && new File(image + ".gif").isFile()){
				this.setIcon(new ImageIcon(image + ".gif"));
				this.setText(null);
		}
		else if(image.length()!= 0){
				this.setIcon(null);
				this.setText(square.getPiece().getType() + "");
		}
		else{
			this.setText(null);
			this.setIcon(new ImageIcon(emptySquare + ".gif"));
		}
	}

}
