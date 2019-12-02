import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import hsa2.GraphicsConsole;

public class ScreenSaver {

	static final int SLEEPTIME = 5;
	static final int GRWIDTH = 800;
	static final int GRHEIGHT = 600;
	static final double pi = Math.PI / 100;
	static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static final int width = (int) screenSize.getWidth();
	static final int height = (int) screenSize.getHeight();
	private Ball ball = new Ball(width - 100, height - 100);
	private Ball ball2 = new Ball(width - 100, height - 100);
	private Ball ball3 = new Ball(width - 100, height - 100);
	private Ball ball4 = new Ball(width - 100, height - 100);
	private Ball ball5 = new Ball(width - 100, height - 100);
	private GraphicsConsole gc = new GraphicsConsole(true, width, height);

	public static void main(String[] args) {
		new ScreenSaver();

	}

	ScreenSaver() {
		initialize();
		int num = 1;
		int counter = 0;
		boolean quit = true;
		while (true) {
			while (quit) {
				drawGraphics(num, counter, 0, 0);
				if (counter <= 200) {
					moveBall(ball, ball2, ball3, ball4, ball5);
					moveBall(ball2, ball, ball3, ball4, ball5);
					moveBall(ball3, ball, ball2, ball4, ball5);
					moveBall(ball4, ball, ball2, ball3, ball5);
					moveBall(ball5, ball4, ball3, ball2, ball);
				} else {
					quit = false;
				}
				gc.sleep(SLEEPTIME);
				counter++;
			}
			quit = true;
			num = 2;
			gc.clear();
			int x = (int) (Math.random() * (width - 400));
			int y = (int) (Math.random() * (height - 400));
			boolean thing = true;
			int h = 0;
			while (quit) {
				if (counter <= 1500 && thing) {
					drawGraphics(num, counter, x, y);
					counter = counter + 20;
				} else {
					drawGraphics(num, counter, x, y);
					counter = counter - 20;
					thing = false;
				}
				if (counter <= -1500) {
					thing = true;
					x = (int) (Math.random() * (width));
					y = (int) (Math.random() * (height));
					gc.clear();
					h = h + 1;
				}
				if (h == 1) {
					quit = false;
				}
				gc.sleep(SLEEPTIME);
			}
			quit = true;
			num = 3;
			gc.clear();
			counter = 1;
			while (quit) {
				gc.clear();
				drawGraphics(num, counter, x, y);
				counter++;
				gc.sleep(25);
				if (counter == 300) {
					quit = false;
				}
			}
			quit = true;
			num = 1;
			gc.clear();
			counter = 0;
		}
	}

	void moveBall(Ball ball, Ball ball2, Ball ball3, Ball ball4, Ball ball5) {
		ball.x += ball.xspeed;
		ball.y += ball.yspeed;
		if ((ball.y + ball.diameter) > gc.getDrawHeight()) {
			ball.yspeed *= -1;
			ball.colour = new Color(Color.HSBtoRGB((float) Math.random(), 1.0f, 1.0f));
		}
		if ((ball.x + ball.diameter) > gc.getDrawWidth()) {
			ball.xspeed *= -1;
			ball.colour = new Color(Color.HSBtoRGB((float) Math.random(), 1.0f, 1.0f));
		}
		if (ball.y < 0) {
			ball.yspeed *= -1;
			ball.colour = new Color(Color.HSBtoRGB((float) Math.random(), 1.0f, 1.0f));
		}
		if (ball.x < 0) {
			ball.xspeed *= -1;
			ball.colour = new Color(Color.HSBtoRGB((float) Math.random(), 1.0f, 1.0f));
		}
		// intersects(ball, ball2);
		// intersects(ball, ball3);
		// intersects(ball, ball4);
		// intersects(ball, ball5);
	}

	// void intersects(Ball ball, Ball ball2){
	// if (ball.intersects(ball2)){
	// if (ball.yspeed >0){
	// ball.yspeed *=-1;
	// ball.xspeed *= -1;
	// }
	// }
	// }
	void initialize() {
		gc.setFont(new Font("Georgia", Font.PLAIN, 25));
		gc.setAntiAlias(true);
		gc.setBackgroundColor(Color.BLACK);
		// gc.setUndecorated(true);
		// gc.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// gc.setVisible(true);
		gc.clear();
	}

	void drawGraphics(int num, int c, int num1, int num2) {
		synchronized (gc) {
			if (num == 1) {
				gc.setBackgroundColor(Color.BLACK);
				gc.setColor(ball.colour);
				gc.fillOval(ball.x, ball.y, ball.width, ball.height);
				gc.setColor(ball2.colour);
				gc.fillOval(ball2.x, ball2.y, ball2.width, ball2.height);
				gc.setColor(ball3.colour);
				gc.fillOval(ball3.x, ball3.y, ball3.width, ball3.height);
				gc.setColor(ball.colour);
				gc.fillOval(ball4.x, ball4.y, ball4.width, ball4.height);
				gc.setColor(ball5.colour);
				gc.fillOval(ball5.x, ball5.y, ball5.width, ball5.height);
			}
			if (num == 2) {
				Color colour = new Color(Color.HSBtoRGB((float) Math.random(), 1.0f, 1.0f));
				gc.setColor(colour);
				// gc.setBackgroundColor(colour);
				gc.setStroke(1);
				gc.drawStar((num1 - (c / 2)), (num2 - (c / 2)), (0 + c), (0 + c));
			}
			if (num == 3) {
				gc.setBackgroundColor(Color.WHITE);
				gc.setColor(Color.RED);
				gc.fillRect(0, 0, width / 4, height);
				gc.fillRect((width / 4) * 3, 0, width / 4, height);
				gc.setColor(Color.RED);
				gc.fillMapleLeaf((width / 2) - 200, (height / 2) - 200, 400, 400, pi * c);
			}
		}
	}
}
