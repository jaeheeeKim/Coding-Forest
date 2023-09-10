package miniproject_final;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainServerGUI extends JFrame implements ActionListener{
	private JButton exit = new JButton("서버 종료");
	
	public static void main(String[] args) {
		new MainServerGUI("놀러와요 코딩의 숲 - 서버");
		
		Thread hallServer = new Thread() {
			@Override
			public void run() {
				new HallServer();
			}
		};
		hallServer.start();
		
	}
	
	public MainServerGUI(String title) {
		super(title);
		init();
		
		this.setSize(450, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		
		this.setVisible(true);
	}

	private void init() {
		Container con = this.getContentPane();
		con.add(exit);	exit.addActionListener(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
