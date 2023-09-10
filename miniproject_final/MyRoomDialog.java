package miniproject_final;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

class MyImage extends Canvas{ //프로필 이미지
	private Image img;
	
	public MyImage() { //생성자
		//회원가입때 선택한 캐릭터의 이미지가 뜨게 설정할 예정. 우선, lion 이미지로 대체.
		 img = Toolkit.getDefaultToolkit().getImage("img/lion.png");
	}
	public void setImage(Image img) { //이미지 변경
		this.img = img;
	}
	  @Override 
	  public void paint(Graphics g) {
	       g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
	  }
	}

class MyButton extends Button{ //버튼 이미지
	private Image img = Toolkit.getDefaultToolkit().getImage("img/myroom/null.png");
	
	public void setImage(Image img) { //버튼 이미지를 바꾸는 기능
		this.img = img;
	}
	public void paint(Graphics g) {
		g.drawImage(img, 5, 5, this.getWidth()-10, this.getHeight()-10, this);
	}
}

public class MyRoomDialog extends JDialog{
	//다른 class
	private MyImage mg = new MyImage(); //이미지
	protected MyButton[] mbt = new MyButton[5]; //버튼 이미지

	private JPanel jp1 = new JPanel();
	//배경 이미지
	private JPanel jp2 = new JPanel() {
		private  ImageIcon back_img = new ImageIcon("img/myroom/MyRoom.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), 0, 0, null);
		}
	};
	
	private JPanel jp2_2 = new JPanel();
	private JPanel jp2_3 = new JPanel();
	private JPanel jp2_4 = new JPanel();
		
	private JPanel jp3 = new JPanel();
		
	// ID's Room 라벨
	private JLabel myName_jlb = new JLabel("ID 's Room", JLabel.CENTER);
		
	// sub title
	private JLabel id_jlb = new JLabel("ID", JLabel.CENTER);
	private JLabel im_jlb = new JLabel("I AM", JLabel.CENTER);
	private JLabel letter_jlb = new JLabel("방명록", JLabel.CENTER);
		
	//종료 버튼 
//	protected JButton edit_jbt = new JButton("수정");
	protected JButton friend_jbt = new JButton("놀러가기");
	protected JButton exit_jbt = new JButton("나가기");
		
	// img (남자, 여자)
	private Image img1 = Toolkit.getDefaultToolkit().getImage("img/myroom/mychar1.png");
	private Image img2 = Toolkit.getDefaultToolkit().getImage("img/myroom/mychar2.png");
	
	// i am 패널
	private JLabel im1_jlb = new JLabel("MBTI", JLabel.LEFT);
	private JLabel im2_jlb = new JLabel("취미", JLabel.LEFT);
	private JLabel im3_jlb = new JLabel("좋아하는 음식", JLabel.LEFT);
	private JLabel im4_jlb = new JLabel("좋아하는 색깔", JLabel.LEFT);
	private JTextField im1_jtf = new JTextField();
	private JTextField im2_jtf = new JTextField();
	private JTextField im3_jtf = new JTextField();
	private JTextField im4_jtf = new JTextField();
	 
	 // 방명록 패널
	 private JLabel lb_test = new JLabel("테스트"); //삭제 필요
	 private JPanel jp_bt = new JPanel();
	 private boolean[] isTrue = new boolean[5]; //방명록 버튼에 내용이 있는지 없는지
	 private String bt_num; //몇번 버튼을 눌렀는지 체크하기 위한 변수
	 private Image  img = Toolkit.getDefaultToolkit().getImage("image/null.png");
	 private Image i_letter1 = Toolkit.getDefaultToolkit().getImage("img/myroom/letter1.png");
	 private Image i_letter2 = Toolkit.getDefaultToolkit().getImage("img/myroom/letter2.png");
	 private Image i_letter3 = Toolkit.getDefaultToolkit().getImage("img/myroom/letter3.png");
	 private Image i_letter4 = Toolkit.getDefaultToolkit().getImage("img/myroom/letter4.png");
	
	 //초기 정보로 마이룸 세팅을 위한 변수
	 String chara, id, pw, name, mbti, hobby, food, color, memo1, memo2, memo3, memo4, memo5;
	 
	 //마이룸 초기 세팅을 위한 메소드
	 public void firstSetting(String str) { //str : 초기정보를 String 형태로 붙여서 받음
		System.out.println(str);
		String[] data = str.split(",",15);
			
		id = data[0];
		chara = data[1];	
		mbti = data[2];
		hobby = data[3];
		food = data[4];
		color = data[5];
		memo1 = data[6];
		memo2 = data[7];
		memo3 = data[8];
		memo4 = data[9];
		memo5 = data[10];
			
		myName_jlb.setText(id+" 's Room");
		id_jlb.setText(id);
			
		if(chara.equals("2")) {
			mg.setImage(img2);
		}else if(chara.equals("1")) {
			mg.setImage(img1);
		}
		mg.repaint();
			
		im1_jtf.setText(mbti);
		im2_jtf.setText(hobby);
		im3_jtf.setText(food);
		im4_jtf.setText(color);
		buttonSetting(); //버튼 이미지 세팅 메소드 호출
	}
	//버튼 이미지 세팅 메소드
	public void buttonSetting() {
		//1번버튼
		String[] data = memo1.split("#");
		if(data[0].equals("1")) {
			mbt[0].setImage(i_letter1);
			isTrue[0] = true;
		}else if(data[0].equals("2")) {
			mbt[0].setImage(i_letter2);
			isTrue[0] = true;
		}else if(data[0].equals("3")) {
			mbt[0].setImage(i_letter3);
			isTrue[0] = true;
		}else if(data[0].equals("4")) {
			mbt[0].setImage(i_letter4);
			isTrue[0] = true;
		}else if(data[0].equals("0")) {
			mbt[0].setImage(img);
		}
		//2번버튼
		String[] data2 = memo2.split("#");
		if(data2[0].equals("1")) {
			mbt[1].setImage(i_letter1);
			isTrue[1] = true;
		}else if(data2[0].equals("2")) {
			mbt[1].setImage(i_letter2);
			isTrue[1] = true;
		}else if(data2[0].equals("3")) {
			mbt[1].setImage(i_letter3);
			isTrue[1] = true;
		}else if(data2[0].equals("4")) {
			mbt[1].setImage(i_letter4);
			isTrue[1] = true;
		}else if(data2[0].equals("0")) {
			mbt[1].setImage(img);
		}
		//3번버튼
		String[] data3 = memo3.split("#");
		if(data3[0].equals("1")) {
			mbt[2].setImage(i_letter1);
			isTrue[2] = true;
		}else if(data3[0].equals("2")) {
			mbt[2].setImage(i_letter2);
			isTrue[2] = true;
		}else if(data3[0].equals("3")) {
			mbt[2].setImage(i_letter3);
			isTrue[2] = true;
		}else if(data3[0].equals("4")) {
			mbt[2].setImage(i_letter4);
			isTrue[2] = true;
		}else if(data3[0].equals("0")) {
			mbt[2].setImage(img);
		}
		//4번버튼
		String[] data4 = memo4.split("#");
		if(data4[0].equals("1")) {
			mbt[3].setImage(i_letter1);
			isTrue[3] = true;
		}else if(data4[0].equals("2")) {
			mbt[3].setImage(i_letter2);
			isTrue[3] = true;
		}else if(data4[0].equals("3")) {
			mbt[3].setImage(i_letter3);
			isTrue[3] = true;
		}else if(data4[0].equals("4")) {
			mbt[3].setImage(i_letter4);
			isTrue[3] = true;
		}else if(data4[0].equals("0")) {
			mbt[3].setImage(img);
		}
		//5번버튼
		String[] data5 = memo5.split("#");
		if(data3[0].equals("1")) {
			mbt[4].setImage(i_letter1);
			isTrue[4] = true;
		}else if(data5[0].equals("2")) {
			mbt[4].setImage(i_letter2);
			isTrue[4] = true;
		}else if(data5[0].equals("3")) {
			mbt[4].setImage(i_letter3);
			isTrue[4] = true;
		}else if(data5[0].equals("4")) {
			mbt[4].setImage(i_letter4);
			isTrue[4] = true;
		}else if(data5[0].equals("0")) {
			mbt[4].setImage(img);
		}
		//변경한 이미지로 repaint();
		for(int i=0; i<mbt.length; ++i) {
			mbt[i].repaint();
		}
	}
	//마이룸에서 해당 버튼을 눌렀을 때, 내용이 있는 방인지 없는 방인지 알려주기위한 메소드
	public boolean buttonTrue(int i) {
		if(isTrue[i]) { //버튼에 값이 있으면 true 반환
			return true; 
		}else {
			return false; //버튼에 값이 없으면 false 반환
		}
	} 
	public void friendbtSetting() {
		friend_jbt.setEnabled(false);
	}
	public void init() {
		this.setLayout(new BorderLayout());
			
		// 상단에 ID's Room 구현
		this.add("North", jp1);
		jp1.setLayout(new BorderLayout());
		jp1.setBackground(Color.PINK);
		jp1.add(myName_jlb);
				
		// 가운데에 올 ID, I AM, 방명록 구현
		this.add("Center", jp2);
		jp2.setLayout(new BorderLayout());
		jp2_2.setBackground(new Color(216, 198, 234));
		jp2.add("North", jp2_2);
		jp2_2.setLayout(new GridLayout(1,3));
		jp2_2.add(id_jlb);
		jp2_2.add(im_jlb);
		jp2_2.add(letter_jlb);
		jp2_3.setBackground(new Color(255, 0, 0, 0));	
		jp2.add("Center", jp2_3);
		jp2_3.setLayout(new GridLayout(1,3));
		jp2_3.add(mg); //이미지 넣기
		
		jp2_4.setBackground(new Color(255, 0, 0, 0));
		jp2_3.add(jp2_4);
		jp2_4.setLayout(new GridLayout(8,1));
		im1_jlb.setFont(new Font("", Font.ITALIC, 13));
		im2_jlb.setFont(new Font("", Font.ITALIC, 13));
		im3_jlb.setFont(new Font("", Font.ITALIC, 13));
		im4_jlb.setFont(new Font("", Font.ITALIC, 13));
		jp2_4.add(im1_jlb);
		jp2_4.add(im1_jtf);
		im1_jtf.setEnabled(false);
		      
		jp2_4.add(im2_jlb);
		jp2_4.add(im2_jtf);
		im2_jtf.setEnabled(false);
		      
		jp2_4.add(im3_jlb);
		jp2_4.add(im3_jtf);
		im3_jtf.setEnabled(false);
		      
		jp2_4.add(im4_jlb);
		jp2_4.add(im4_jtf);
		im4_jtf.setEnabled(false);

		jp2_3.add(jp_bt);
		jp_bt.setLayout(new GridLayout(5,1));
		for(int i=0; i<mbt.length; ++i) {
			mbt[i] = new MyButton();
			jp_bt.add(mbt[i]);
		}
			
		// 종료 버튼 구현
		this.add("South", jp3);
		jp3.setLayout(new FlowLayout());
		jp3.setBackground(Color.PINK);
		jp3.add(friend_jbt);
		jp3.add(exit_jbt);	
//		jp3.add(edit_jbt);
		
	}
	
	public MyRoomDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(700, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
}