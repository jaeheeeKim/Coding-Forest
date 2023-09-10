package miniproject_final;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class LoginDialog extends JDialog {
	
	// 배경사진을 넣은 패널
	private JPanel jp1 = new JPanel() {
		private  ImageIcon back_img = new ImageIcon("img/login.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), -215, -105, null);
		}
	};
	
	private JPanel jp1_2 = new JPanel();
	
	// 아이디, 비밀번호, 회원가입 버튼, 로그인 버튼을 구현할 패널	
	private JPanel jp2 = new JPanel();
	private JPanel jp2_2 = new JPanel();
	
	// 아이디, 비밀번호의 라벨과 jtf
	private JLabel id_jlb = new JLabel("아이디", JLabel.CENTER);
	JTextField id_jtf = new JTextField();
	private JLabel pw_jlb = new JLabel("비밀번호", JLabel.CENTER);
	JTextField pw_jtf = new JTextField();
	
	// 회원가입과 로그인의 버튼
	JButton join_jbt = new JButton("회원가입");
	JButton login_jbt = new JButton("로그인");
	
	public void init() {
		Container con = this.getContentPane();
		// 이미지를 넣은 패널
		con.add(jp1);
		jp1.setLayout(new BorderLayout());
		jp1_2.setBackground(new Color(255, 0, 0, 0));
		jp1.add("Center", jp1_2);

		// 아이디, 비밀번호를 구현할 패널
		jp2.setBackground(new Color(255, 0, 0, 0));		// 패널 투명
		jp1.add("South", jp2);
		jp2.setLayout(new FlowLayout());
		jp2_2.setBackground(new Color(255, 0, 0, 0));	// 패널 투명
		
		jp2.add(jp2_2);
		
		// 아이디, 비밀번호 라벨과 jtf
		jp2_2.setLayout(new GridLayout(3,2));
		jp2_2.add(id_jlb);
		jp2_2.add(id_jtf);
		
		jp2_2.add(pw_jlb);
		jp2_2.add(pw_jtf);
		
		// 회원가입과 로그인 버튼
		jp2_2.add(join_jbt);
		jp2_2.add(login_jbt);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 창의 x를 누르면 반응 없음. 종료는 따로 구현해야함.
	}
	
	public String getID() {
		return id_jtf.getText();
	}
	public String getPW() {
		return pw_jtf.getText();
	}
	public void clear() {
		id_jtf.setText("");
		pw_jtf.setText("");				
	}
	
	public LoginDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(600, 500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);

	}

}
