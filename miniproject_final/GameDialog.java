package miniproject_final;

import java.awt.*;
import javax.swing.*;

class MyButton003 extends Button{
	private Image img;
	
	public MyButton003(Image img) {
		this.img = img;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, 5, 5, this.getWidth()-10, this.getHeight()-10, this);
	}
}

class GameDialog extends JDialog{
	Image img1 = Toolkit.getDefaultToolkit().getImage("img/game/game1.png");
	Image img2 = Toolkit.getDefaultToolkit().getImage("img/game/game2.png");
	Image img3 = Toolkit.getDefaultToolkit().getImage("img/game/game3.png");
	protected MyButton003 bt1 = new MyButton003(img1);
	protected MyButton003 bt2 = new MyButton003(img2);
	protected MyButton003 bt3 = new MyButton003(img3);

	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(1,3));
		con.add(bt1);
		con.add(bt2);
		con.add(bt3);
	}
	
	public GameDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(600, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
}