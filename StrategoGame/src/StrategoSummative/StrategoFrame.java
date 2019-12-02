/*
 * Author: Logan Wong
 * Date started: 12/16/2016
 * Last Modified: 1/25/2017
 * Purpose: Frame that game is played on
 */
package StrategoSummative;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class StrategoFrame extends JFrame implements ActionListener,
		java.io.Serializable {
	private static final long serialVersionUID = -3561356922848126348L;
//static instance of frame
	private static StrategoFrame StrategoGame;
//holds buttons used for board
	private JPanel boardPanel = new JPanel();
//holds buttons used for blue side bar
	private JPanel bluePiecePanel = new JPanel();
//holds buttons used for red side bar
	private JPanel redPiecePanel = new JPanel();
//used to space out format
	private JPanel spacer = new JPanel();

	JButton done = new JButton("Done");

	boolean setUpPieces = true;

	boolean firstTurn;

	public static boolean errorCatch;//true while board is not instantiated

	public static void main(String[] args) {
		StrategoGame = new StrategoFrame();
		StrategoGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public StrategoFrame() {
		super("Stratego!");

		JPanel pane = (JPanel) getContentPane();

		JPanel gamePane = new JPanel();

		JPanel pieces = new JPanel();

		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");

		JMenu help = new JMenu("Help");

		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(this);
		file.add(newGame);

		file.addSeparator();

		JMenuItem load = new JMenuItem("Load Game");
		load.addActionListener(this);
		file.add(load);

		JMenuItem save = new JMenuItem("Save Game");
		save.addActionListener(this);
		file.add(save);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);

		menuBar.add(file);

		JMenuItem howToPlay = new JMenuItem("How to Play");
		howToPlay.addActionListener(this);
		help.add(howToPlay);

		menuBar.add(help);

		setJMenuBar(menuBar);

		gamePane.setLayout(new BorderLayout(10, 10));

		gamePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// rightPane.
		// 10x10 grid
		boardPanel.setLayout(new GridLayout(10, 10));
		// Padding around border
		boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		boardPanel.setBackground(Color.BLACK);

		gamePane.add(boardPanel, BorderLayout.CENTER);

		gamePane.setBackground(Color.BLUE);

		pane.setLayout(new BorderLayout(2, 1));

		pane.add(gamePane, BorderLayout.WEST);

		pieces.setLayout(new BorderLayout(10, 10));

		bluePiecePanel.setLayout(new GridLayout(2, 6));

		done.addActionListener(this);

		bluePiecePanel.setBorder(BorderFactory
				.createEmptyBorder(10, 10, 10, 10));

		redPiecePanel.setLayout(new GridLayout(2, 6));

		redPiecePanel
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		spacer.setLayout(new BorderLayout(1, 2));

		JLabel image = new JLabel();

		image.setIcon(new ImageIcon("Stratego.gif"));

		spacer.add(done, BorderLayout.SOUTH);

		spacer.add(image, BorderLayout.CENTER);

		pieces.add(bluePiecePanel, BorderLayout.NORTH);

		pieces.add(redPiecePanel, BorderLayout.SOUTH);

		pieces.add(spacer, BorderLayout.CENTER);

		pane.add(pieces, BorderLayout.EAST);

		errorCatch = true;

		firstTurn = true;

		setUp();

		errorCatch = false;

		setMinimumSize(new Dimension(825, 550)); // Minimum size of the window

		setResizable(false);

		pack();

		setVisible(true);

		setLocationRelativeTo(null);
	}

	/*
	 *  Author: Logan Wong
	 * Name: actionPerformed
	 * Description: On button clicks does something
	 * Parameters: ActionEvent
	 * Return: null
	 * Dependencies: java.awt
	 * Creation date: 12/26/2016
	 * Throws: none
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof SquareButton
				&& !Board.getBoard().endOfGame) {
			SquareButton btn = (SquareButton) e.getSource();
			Square square = btn.getSquare();
			if (btn.getParent() == bluePiecePanel
					|| btn.getParent() == redPiecePanel) {
				if (setUpPieces) {
					square.sideClick(btn);
				}
			} else if (setUpPieces) {
				square.placeClick(btn);
			} else {
				square.onClick(btn);
			}
		} else if (e.getActionCommand().equals("Done")) {
			int piecesLeft = 0;
			if (Board.getBoard().isRedTurn()) {
				for (Component btn : redPiecePanel.getComponents()) {
					SquareButton myButton = (SquareButton) btn;
					piecesLeft += myButton.getSquare().getNumber();
				}
				if (piecesLeft == 0) {
					spacer.remove(done);
					spacer.add(done, BorderLayout.NORTH);
					nextTurn(null);
				} else {
					JOptionPane.showMessageDialog(this,
							"Place all pieces first");
				}
			} else {
				for (Component btn : bluePiecePanel.getComponents()) {
					SquareButton myButton = (SquareButton) btn;
					piecesLeft += myButton.getSquare().getNumber();
				}
				if (piecesLeft == 0) {
					spacer.remove(done);
					setUpPieces = false;
					nextTurn(null);
				} else {
					JOptionPane.showMessageDialog(this,
							"Place all pieces first");
				}
			}
		} else if (e.getActionCommand() == "New Game") {
			setUpPieces = true;
			spacer.add(done, BorderLayout.SOUTH);
			errorCatch = true;
			setUp();
			errorCatch = false;

		} else if (e.getActionCommand() == "Exit") {
			System.exit(0);

		} else if (e.getActionCommand() == "Save Game" && !setUpPieces) {
			saveGame();
		} else if (e.getActionCommand() == "Load Game") {
			loadGame();
		} else if (e.getActionCommand() == "How to Play") {
			JFrame help = new JFrame("How to Play");
			JPanel panel = (JPanel) help.getContentPane();
			panel.setLayout(new BorderLayout());
			JTextArea instructions = new JTextArea();
			JTextArea controls= new JTextArea();
			instructions.setLineWrap(true);
			instructions.setMargin(new Insets(10, 10, 10, 10));
			instructions.setText("Stratego is a game in which you need to capture the flag of your opponent while defending your own flag. To capture the flag you use your army of 40 pieces.  Pieces have a rank and represent individual officers and soldiers in an army. In addition to those ranked pieces you can use bombs to protect your flag. Pieces move 1 square per turn, horizontally or vertically. Only the scout (2) can move over multiple empty squares per turn. Pieces cannot jump over another piece.If a piece is moved onto a square occupied by an opposing piece, their identities are revealed. The weaker piece is removed from the board, and the stronger piece is moved into the place formerly occupied by the weaker piece. If the engaging pieces are of equal rank, they are both removed. Pieces may not move onto a square already occupied by another piece without attacking. Exception to the rule of the higher rank winning is the spy(1). When the spy (1)attacks the marshal, the spy defeats the higher ranked marshal(10). However, when the marshal attacks the spy, the spy loses. Bombs(B) lose when they are defused by a miner(3). The bombs and the flag(F) cannot be moved. A bomb defeats every piece that tries to attack it, except the miner. The flag loses from every other piece. When you capture the flag of your opponent you win the game. If, however, you cannot make a move on your turn, you will lose the game.");
			
			controls.setLineWrap(true);
			controls.setMargin(new Insets(10,10,10,10));
			controls.setText("Each Player will take turns, red going first. To start, each player will click on a piece on the side bar, and select one of their squares to place it on. Each player has the following pieces: One F, Six B's, One 1, One 10, One 9, Two 8's, Three 7's, Four 6's, Four 5's, Four 4's, Five 3's, Eight 2's. After putting on all your pieces, you can change them around by clicking two pieces to switch. When finished, press done. \n **NOTE** When you see a message to switch turns, let your opponent play their turn. DON'T LOOK!");
			panel.add(instructions, BorderLayout.NORTH);
			panel.add(controls, BorderLayout.SOUTH);
			help.setMinimumSize(new Dimension(550, 450));
			help.setLocationRelativeTo(null);
			help.setVisible(true);
			help.setDefaultCloseOperation(HIDE_ON_CLOSE);
		}
	}

	public static StrategoFrame getStrategoGame() {
		return StrategoGame;
	}

	/*
	 *  Author: Logan Wong
	 * Name: nextTurn
	 * Description: Switches turns
	 * Parameters: Movement
	 * Return: none
	 * Dependencies: java.awt; javax.swing; SquareButton, Piece, Square
	 * Creation date: 12/26/2016
	 * Throws: none
	 */
	public void nextTurn(Movement attack) {

		Board board = Board.getBoard();
		board.setRedTurn(!board.isRedTurn());

		for (Component component : boardPanel.getComponents()) {
			if (component instanceof SquareButton) {
				SquareButton btn = (SquareButton) component;
				Piece piece = btn.getSquare().getPiece();

				if (setUpPieces) {
					if (btn.getSquare().isRedStart() == 0) {
						btn.setBorder(new LineBorder(Color.YELLOW, 2));
					} else {
						btn.setBorder(new LineBorder(Color.BLACK, 2));
					}
				}
				if (piece != null && piece.isRed() != board.isRedTurn()) {
					btn.changeIcon(piece.getColour() + "");
				}

			}
		}
		if (!firstTurn) {
			JOptionPane.showMessageDialog(StrategoFrame.getStrategoGame(),
					"Switch Players");
		} else {
			firstTurn = false;
		}

		for (Component component : boardPanel.getComponents()) {
			if (component instanceof SquareButton) {
				SquareButton btn = (SquareButton) component;
				Piece piece = btn.getSquare().getPiece();
				if (piece != null && piece.isRed() == board.isRedTurn()) {
					btn.changeIcon(piece.getType() + piece.getColour() + "");
				}

			}
		}
		if (attack != null) {
			JOptionPane.showMessageDialog(StrategoFrame.getStrategoGame(),
					"Your " + attack.getType1() + " was attacked by a "
							+ attack.getType2());
			Square.removeBorder();
		}

		board.setSelected(null);
		if (!setUpPieces) {
			board.setMoves();
			Square.removeBorder();
			if (board.getMoves().isEmpty()) {
				endGame();
			}
		}

	}

	/*
	 *  Author: Logan Wong
	 * Name: endGame
	 * Description: disables buttons, says who won
	 * Parameters: none
	 * Return: none
	 * Dependencies: java.awt
	 * Creation date: 12/26/2016
	 * Throws: none
	 */
	public void endGame() {
		Board board = Board.getBoard();
		for (Component component : boardPanel.getComponents()) {
			if (component instanceof SquareButton) {
				SquareButton btn = (SquareButton) component;
				Piece piece = btn.getSquare().getPiece();
				if (piece != null) {
					btn.changeIcon(piece.getType() + piece.getColour() + "");
				}
			}
		}
		if (board.isRedWin()) {
			JOptionPane.showMessageDialog(this, "Red Wins!");
		} else {
			JOptionPane.showMessageDialog(this, "Blue Wins!");
		}
		
	}

	/*
	 *  Author: Logan Wong
	 * Name: setUp
	 * Description: Resets board and images
	 * Parameters: none
	 * Return: none
	 * Dependencies: java.awt, Square, Board, SquareButton
	 * Creation date: 12/26/2016
	 * Throws: none
	 */
	public void setUp() {
		Board board = Board.getBoard();
		board.setRedTurn(false);// debug
		nextTurn(null);

		boardPanel.removeAll();
		board.setGrid();
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				Square square = board.getSquare(i, j);
				SquareButton squareButton = new SquareButton(square);

				if (square.isBlocked()) {
					squareButton.changeIcon(squareButton.blockedSquare);
				} else {
					squareButton.changeIcon(squareButton.emptySquare);
					squareButton.addActionListener(this);
				}
				if (square.isRedStart() == 1) {
					squareButton.setBorder(new LineBorder(Color.YELLOW, 2));
				}
				boardPanel.add(squareButton);

			}

		}

		bluePiecePanel.removeAll();
		redPiecePanel.removeAll();
		addButtons();
		for (Component btn : bluePiecePanel.getComponents()) {
			if (btn instanceof SquareButton) {
				SquareButton myButton = (SquareButton) btn;
				myButton.changeIcon(myButton.getSquare().getPiece().getType()
						+ myButton.getSquare().getPiece().getColour());
			}
		}
		for (Component btn : redPiecePanel.getComponents()) {
			if (btn instanceof SquareButton) {
				SquareButton myButton = (SquareButton) btn;
				myButton.changeIcon(myButton.getSquare().getPiece().getType()
						+ myButton.getSquare().getPiece().getColour());
			}
		}
	}

	
	public JPanel getBoardPanel() {
		return boardPanel;
	}

	
	public JPanel getBluePiecePanel() {
		return bluePiecePanel;
	}

	
	public JPanel getRedPiecePanel() {
		return redPiecePanel;
	}

	/*
	 *  Author: Logan Wong
	 * Name: addButtons
	 * Description: Creates buttons for side bar and adds to panel
	 * Parameters: 
	 * Return: null
	 * Dependencies: java.awt, Square, SquareButton, javax.swing
	 * Creation date: 12/26/2016
	 * Throws: none
	 */
	public void addButtons() {
		Square blueFlagSquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			blueFlagSquare.addPiece(new Flag(false));
		}
		SquareButton blueFlag = new SquareButton(blueFlagSquare);
		blueFlagSquare.setPiece(new Flag(false));
		blueFlag.addActionListener(this);
		bluePiecePanel.add(blueFlag);

		Square blueBombSquare = new Square(6);
		for (int i = 0; i < 6; i++) {
			blueBombSquare.addPiece(new Bomb(false));
		}
		SquareButton blueBomb = new SquareButton(blueBombSquare);
		blueBombSquare.setPiece(new Bomb(false));
		blueBomb.addActionListener(this);
		bluePiecePanel.add(blueBomb);

		Square blueSpySquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			blueSpySquare.addPiece(new Spy(false));
		}
		SquareButton blueSpy = new SquareButton(blueSpySquare);
		blueSpySquare.setPiece(new Spy(false));
		blueSpy.addActionListener(this);
		bluePiecePanel.add(blueSpy);

		Square blueMarshallSquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			blueMarshallSquare.addPiece(new Marshall(false));
		}
		SquareButton blueMarshall = new SquareButton(blueMarshallSquare);
		blueMarshallSquare.setPiece(new Marshall(false));
		blueMarshall.addActionListener(this);
		bluePiecePanel.add(blueMarshall);

		Square blueGeneralSquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			blueGeneralSquare.addPiece(new General(false));
		}
		SquareButton blueGeneral = new SquareButton(blueGeneralSquare);
		blueGeneralSquare.setPiece(new General(false));
		blueGeneral.addActionListener(this);
		bluePiecePanel.add(blueGeneral);

		Square blueColonelSquare = new Square(2);
		for (int i = 0; i < 2; i++) {
			blueColonelSquare.addPiece(new Colonel(false));
		}
		SquareButton blueColonel = new SquareButton(blueColonelSquare);
		blueColonelSquare.setPiece(new Colonel(false));
		blueColonel.addActionListener(this);
		bluePiecePanel.add(blueColonel);

		Square blueMajorSquare = new Square(3);
		for (int i = 0; i < 3; i++) {
			blueMajorSquare.addPiece(new Major(false));
		}
		SquareButton blueMajor = new SquareButton(blueMajorSquare);
		blueMajorSquare.setPiece(new Major(false));
		blueMajor.addActionListener(this);
		bluePiecePanel.add(blueMajor);

		Square blueCaptainSquare = new Square(4);
		for (int i = 0; i < 4; i++) {
			blueCaptainSquare.addPiece(new Captain(false));
		}
		SquareButton blueCaptain = new SquareButton(blueCaptainSquare);
		blueCaptainSquare.setPiece(new Captain(false));
		blueCaptain.addActionListener(this);
		bluePiecePanel.add(blueCaptain);

		Square blueLieutenantSquare = new Square(4);
		for (int i = 0; i < 4; i++) {
			blueLieutenantSquare.addPiece(new Lieutenant(false));
		}
		SquareButton blueLieutenant = new SquareButton(blueLieutenantSquare);
		blueLieutenantSquare.setPiece(new Lieutenant(false));
		blueLieutenant.addActionListener(this);
		bluePiecePanel.add(blueLieutenant);

		Square blueSergeantSquare = new Square(4);
		for (int i = 0; i < 4; i++) {
			blueSergeantSquare.addPiece(new Sergeant(false));
		}
		SquareButton blueSergeant = new SquareButton(blueSergeantSquare);
		blueSergeantSquare.setPiece(new Sergeant(false));
		blueSergeant.addActionListener(this);
		bluePiecePanel.add(blueSergeant);

		Square blueMinerSquare = new Square(5);
		for (int i = 0; i < 5; i++) {
			blueMinerSquare.addPiece(new Miner(false));
		}
		SquareButton blueMiner = new SquareButton(blueMinerSquare);
		blueMinerSquare.setPiece(new Miner(false));
		blueMiner.addActionListener(this);
		bluePiecePanel.add(blueMiner);

		Square blueScoutSquare = new Square(8);
		for (int i = 0; i < 8; i++) {
			blueScoutSquare.addPiece(new Scout(false));
		}
		SquareButton blueScout = new SquareButton(blueScoutSquare);
		blueScoutSquare.setPiece(new Scout(false));
		blueScout.addActionListener(this);
		bluePiecePanel.add(blueScout);

		Square redFlagSquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			redFlagSquare.addPiece(new Flag(true));
		}
		SquareButton redFlag = new SquareButton(redFlagSquare);
		redFlagSquare.setPiece(new Flag(true));
		redFlag.addActionListener(this);
		redPiecePanel.add(redFlag);

		Square redBombSquare = new Square(6);
		for (int i = 0; i < 6; i++) {
			redBombSquare.addPiece(new Bomb(true));
		}
		SquareButton redBomb = new SquareButton(redBombSquare);
		redBombSquare.setPiece(new Bomb(true));
		redBomb.addActionListener(this);
		redPiecePanel.add(redBomb);

		Square redSpySquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			redSpySquare.addPiece(new Spy(true));
		}
		SquareButton redSpy = new SquareButton(redSpySquare);
		redSpySquare.setPiece(new Spy(true));
		redSpy.addActionListener(this);
		redPiecePanel.add(redSpy);

		Square redMarshallSquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			redMarshallSquare.addPiece(new Marshall(true));
		}
		SquareButton redMarshall = new SquareButton(redMarshallSquare);
		redMarshallSquare.setPiece(new Marshall(true));
		redMarshall.addActionListener(this);
		redPiecePanel.add(redMarshall);

		Square redGeneralSquare = new Square(1);
		for (int i = 0; i < 1; i++) {
			redGeneralSquare.addPiece(new General(true));
		}
		SquareButton redGeneral = new SquareButton(redGeneralSquare);
		redGeneralSquare.setPiece(new General(true));
		redGeneral.addActionListener(this);
		redPiecePanel.add(redGeneral);

		Square redColonelSquare = new Square(2);
		for (int i = 0; i < 2; i++) {
			redColonelSquare.addPiece(new Colonel(true));
		}
		SquareButton redColonel = new SquareButton(redColonelSquare);
		redColonelSquare.setPiece(new Colonel(true));
		redColonel.addActionListener(this);
		redPiecePanel.add(redColonel);

		Square redMajorSquare = new Square(3);
		for (int i = 0; i < 3; i++) {
			redMajorSquare.addPiece(new Major(true));
		}
		SquareButton redMajor = new SquareButton(redMajorSquare);
		redMajorSquare.setPiece(new Major(true));
		redMajor.addActionListener(this);
		redPiecePanel.add(redMajor);

		Square redCaptainSquare = new Square(4);
		for (int i = 0; i < 4; i++) {
			redCaptainSquare.addPiece(new Captain(true));
		}
		SquareButton redCaptain = new SquareButton(redCaptainSquare);
		redCaptainSquare.setPiece(new Captain(true));
		redCaptain.addActionListener(this);
		redPiecePanel.add(redCaptain);

		Square redLieutenantSquare = new Square(4);
		for (int i = 0; i < 4; i++) {
			redLieutenantSquare.addPiece(new Lieutenant(true));
		}
		SquareButton redLieutenant = new SquareButton(redLieutenantSquare);
		redLieutenantSquare.setPiece(new Lieutenant(true));
		redLieutenant.addActionListener(this);
		redPiecePanel.add(redLieutenant);

		Square redSergeantSquare = new Square(4);
		for (int i = 0; i < 4; i++) {
			redSergeantSquare.addPiece(new Sergeant(true));
		}
		SquareButton redSergeant = new SquareButton(redSergeantSquare);
		redSergeantSquare.setPiece(new Sergeant(true));
		redSergeant.addActionListener(this);
		redPiecePanel.add(redSergeant);

		Square redMinerSquare = new Square(5);
		for (int i = 0; i < 5; i++) {
			redMinerSquare.addPiece(new Miner(true));
		}
		SquareButton redMiner = new SquareButton(redMinerSquare);
		redMinerSquare.setPiece(new Miner(true));
		redMiner.addActionListener(this);
		redPiecePanel.add(redMiner);

		Square redScoutSquare = new Square(8);
		for (int i = 0; i < 8; i++) {
			redScoutSquare.addPiece(new Scout(true));
		}
		SquareButton redScout = new SquareButton(redScoutSquare);
		redScoutSquare.setPiece(new Scout(true));
		redScout.addActionListener(this);
		redPiecePanel.add(redScout);
	}
	/*
	 *  Author: Logan Wong
	 * Name: saveGame
	 * Description: serializes and writes all Squarebuttons to a file
	 * Parameters: none
	 * Return: none
	 * Dependencies: java.awt, java.io, SquareButton, javax.swing
	 * Creation date: 12/26/2016
	 * Throws: none
	 */
	private void saveGame() {
		try {
			FileOutputStream fileOut = new FileOutputStream("SaveFile.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			for (Component btn : redPiecePanel.getComponents()) {
				if (btn instanceof SquareButton) {
					SquareButton myButton = (SquareButton) btn;
					out.writeObject(myButton);
				}
			}
			for (Component btn : bluePiecePanel.getComponents()) {
				if (btn instanceof SquareButton) {
					SquareButton myButton = (SquareButton) btn;
					out.writeObject(myButton);
				}
			}
			for (Component btn : boardPanel.getComponents()) {
				if (btn instanceof SquareButton) {
					SquareButton myButton = (SquareButton) btn;
					out.writeObject(myButton);
				}
			}
			out.writeBoolean(Board.getBoard().isRedTurn());
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"There was an error saving your file");
		}

	}
	/*
	 *  Author: Logan Wong
	 * Name: loadGame
	 * Description: Reads and deserializes SquareButtons from file
	 * Parameters: none
	 * Return: none
	 * Dependencies: java.awt, java.io, SquareButton, javax.swing
	 * Creation date: 12/26/2016
	 * Throws: none
	 */
	private void loadGame() {
		try {
			FileInputStream fileIn = new FileInputStream("SaveFile.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			redPiecePanel.removeAll();
			bluePiecePanel.removeAll();
			boardPanel.removeAll();
			for (int j = 0; j < 12; j++) {

				SquareButton myButton = (SquareButton) in.readObject();
				redPiecePanel.add(myButton);
			}

			for (int j = 0; j < 12; j++) {

				SquareButton myButton = (SquareButton) in.readObject();
				bluePiecePanel.add(myButton);
			}

			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < 10; i++) {
					SquareButton myButton = (SquareButton) in.readObject();
					boardPanel.add(myButton);
					Board.getBoard().getGrid()[i][j] = myButton.getSquare();
				}

			}
			Board.getBoard().setRedTurn(!in.readBoolean());
			in.close();
			fileIn.close();
			setUpPieces = false;
			spacer.remove(done);
			nextTurn(null);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					"There was an error reading your file");
		}
	}
}
