package miniproject_final;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;

import javax.swing.*;
import javax.xml.crypto.Data;

class MyButton03 extends Button {	// 두더지 이미지 넣기
	private Image img;
	
	public MyButton03(Image img) {
		this.img = img;
	}
	
	public void setImg(Image img) {
		this.img = img;
	}
	
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth()-3, this.getHeight()-3, this);
	}
}

class GameDudu extends JDialog implements ActionListener, Runnable{
	// 버튼 9개 배열로 생성
	private MyButton03[] mbt = new MyButton03[16];
	
	// 라벨과 버튼
	private JLabel score_jlb = new JLabel("점수 : 00점" , JLabel.CENTER);
	private JLabel time_jlb = new JLabel("시간 : 20초", JLabel.CENTER);
	private JLabel rank_jlb = new JLabel("랭킹", JLabel.CENTER);
	private JButton start_jbt = new JButton("시작");
	protected JButton rank_jbt = new JButton("자랑하기");
	
	private JTextArea rank_jta = new JTextArea();
	
	// 이미지 
	private Image img1 = Toolkit.getDefaultToolkit().getImage("img/game/gamedudu2.png");	// 너굴 없는거
	private Image img2 = Toolkit.getDefaultToolkit().getImage("img/game/gamedudu1.png");	// 너굴 있는거

	
	private JPanel jp = new JPanel();
	private JPanel east_jp = new JPanel();
	private JPanel rank_jp = new JPanel();
	
	private boolean isEnable = false;
	private int time = 20;
	private int co = 0;
	private int ransu;
	
	public void init() {
		// 두더지 버튼 세팅
		this.setLayout(new BorderLayout());
		this.add("Center", jp);
		jp.setLayout(new GridLayout(4,4));
		for(int i=0; i<mbt.length; ++i) {
			mbt[i] = new MyButton03(img1);
			mbt[i].addActionListener(this);
			jp.add(mbt[i]);
		}
		
		// 점수, 시간, 랭킹, 시작버튼을 배치할 패널
		east_jp.setPreferredSize(new Dimension(100, 300));
		east_jp.setBackground(Color.WHITE);
		this.add("East", east_jp);
		east_jp.setLayout(new GridLayout(5,1));
		
		// 점수 
		score_jlb.setFont(new Font("", Font.BOLD, 15));
		east_jp.add(score_jlb);
		
		// 시간 
		time_jlb.setFont(new Font("", Font.BOLD, 15));
		time_jlb.setForeground(Color.RED);
		east_jp.add(time_jlb);
		
		// 랭킹
		rank_jp.setBackground(new Color(232, 255, 241));
		east_jp.add(rank_jp);
		rank_jp.setLayout(new BorderLayout());
		rank_jlb.setFont(new Font("", Font.BOLD, 12));
		rank_jp.add("North", rank_jlb);
		rank_jp.add("Center", rank_jta);
		rank_jta.setEditable(false);		// 랭킹창에 입력하지 못하게
		
		// 시작 버튼
		east_jp.add(start_jbt);
		east_jp.add(rank_jbt);
		start_jbt.addActionListener(this);
		rank_jbt.addActionListener(this);
		
		buttonSetting();
	}
	
	public void setRank(String rank) {
		rank_jta.setText("");
		System.out.println("세팅준비");
		System.out.println(rank);
		if(rank == null) {
			rank_jta.append("");
		}else {
			String[] text = rank.split("@");
			System.out.println(text.length);
			if(text.length < 3) {
				for(int i=0; i<text.length; ++i) {
					String[] data = text[i].split("#");
					rank_jta.append(data[1] + "  " + data[2] + "\n");
				}
			} else {
				for(int i=0; i<3; ++i) {
					String[] data = text[i].split("#");
					rank_jta.append(data[1] + "  " + data[2] + "\n");
				}
			}
		}
	}
	
	public int sendRank() {
		return co;
	}
	
	public void clearScore() {
		co = 0;
		time = 0;
		score_jlb.setText("점수 : " + co + "점");
		time_jlb.setText("시간 : " + time + "초");
	}
	
	public void buttonSetting() {
		start_jbt.setEnabled(!isEnable);
		for(int i=0; i<mbt.length; ++i) {
			mbt[i].setEnabled(isEnable);
		}
	}
	
	public GameDudu(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(600, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}

	public void dudu() {
		while(true) {
			int su = (int)(Math.random()*16);
			if (ransu != su) { 
				ransu = su;
				break;
			}
		}
		mbt[ransu].setImg(img2);
		mbt[ransu].repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start_jbt) {
			co = 0;						
			time = 20;					// 시간 20초로 늘림
			score_jlb.setText("점수 : " + co + "점");
			time_jlb.setText("시간 : " + time + "초");
			
			isEnable = true;
			buttonSetting();
			
			Thread th = new Thread(this);
			th.start();
			dudu();	
		} else {
			for(int i=0; i<mbt.length; ++i) {
				if (e.getSource() == mbt[i]) {
					if (ransu == i) {			
						mbt[i].setImg(img1);	
						mbt[i].repaint();
						co++;					
						if (co < 10) {			
							score_jlb.setText("점수 : 0" + co  + "점");
							if (co < 0) {	
								score_jlb.setText("점수 : " + co + "점");
							}
						} else {				
							score_jlb.setText("점수 : " + co + "점");
						}
						dudu();
					} else if (ransu != i) {	// 너굴 그림이 없는 버튼을 클릭하면 -1점씩 점수가 깎이게 구현
						co--;
						if (co < 10) {			
							score_jlb.setText("점수 : 0" + co  + "점");
							if (co < 0) {	
								score_jlb.setText("점수 : " + co + "점");
							}
						} else {				
							score_jlb.setText("점수 : " + co + "점");
						}
					}
				}
			}
		} 
		
	}
	
	public void buttonClear() {
		for(int i=0; i<mbt.length; ++i) {
			mbt[i].setImg(img1);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			time--;
			try {
				Thread.sleep(1000);
			} catch	(InterruptedException e) {}
			
			time_jlb.setText("시간 : " + time + "초");
			
			if (time == 0) {				
				isEnable = false;			
				mbt[ransu].setImg(img1);	
				mbt[ransu].repaint();
				buttonSetting();
				break;
			}
		}
		
	}

}

