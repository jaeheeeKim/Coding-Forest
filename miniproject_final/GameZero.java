package miniproject_final;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class MyButton0 extends Button{
	private Image img;
	
	public void setImage(Image img) {
		this.img = img;
	}
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

public class GameZero  extends JDialog implements ActionListener, MouseListener, Runnable {
	private JPanel lp = new JPanel();
	private JPanel rp = new JPanel();
	
	private JLabel left_lb = new JLabel("시간 : 0초", JLabel.CENTER);
	private MyButton0 start_bt = new MyButton0();
	private JLabel right_lb = new JLabel("남은화석수 : 10개", JLabel.CENTER);
	protected JButton rank_jbt = new JButton("자랑하기");
	
	private JPanel null_p = new JPanel() {
	private  ImageIcon back_img = new ImageIcon("img/game/white.png");
	public void paintComponent(Graphics g) {
		g.drawImage(back_img.getImage(), 0, 0, null);
		}
		   };
	private MyButton0 bt[][] = new MyButton0[9][9];
	private JPanel p = new JPanel();
	private JPanel north_p = new JPanel();
	private JPanel north_center_p = new JPanel();
	
	private JDialog dlg = new JDialog(this, "메세지", true);
	private JDialog dlg2 = new JDialog(this, "메세지", true);
	private JLabel dlg_lb2 = new JLabel("", JLabel.CENTER);
	
	private JLabel dlg_lb = new JLabel("", JLabel.CENTER);
	private JButton dlg_bt = new JButton("확인");
	
	private JPanel dlg_p3 = new JPanel();
	private JButton dlg_bt3 = new JButton("확인");
	
	private JPanel dlg_p = new JPanel(){
		private  ImageIcon back_img = new ImageIcon("img/game/pop.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), -87, -70, null);
			}
			   };
		private JPanel dlg_p2 = new JPanel() {
		private  ImageIcon back_img = new ImageIcon("img/game/back.png");
		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), 0, 0, null);
			}
			   };
	
	private JTextArea rule_area = new JTextArea();
	private JTextArea ranking_area = new JTextArea();		   

	private JPanel rank_jp = new JPanel();
	private JPanel rank_jp2 = new JPanel();
	private JLabel rank_jlb = new JLabel("♠ 화석캐기 랭킹", JLabel.CENTER);
	
	private int[][] arr = new int[9][9];
	private boolean[][] check = new boolean[9][9];
	int count = 10;
	boolean isTime = true;
	boolean isEnable = true;
	int time = 0;
	
	public void init() {
		dlg.setLayout(new BorderLayout());
		dlg.setBackground(Color.white);
		dlg_lb.setFont(new Font("", Font.BOLD, 20));
		
		dlg.add("Center", dlg_p);
		dlg.add("South", dlg_p2);
		dlg_p2.setLayout(new FlowLayout());
		dlg_p2.add(dlg_bt);	dlg_bt.addActionListener(this);
		
		
		
		dlg2.add("Center", dlg_lb2);
		dlg2.add("South", dlg_p3);
		dlg_p3.setLayout(new FlowLayout());
		dlg_p3.add(dlg_bt3);	dlg_bt3.addActionListener(this);
		
		
		
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());

		con.add("Center", lp);
		lp.setLayout(new BorderLayout());
		lp.add("North", north_p);
		north_p.setBackground(Color.white);
		north_p.setLayout(new GridLayout(1, 3));
		north_p.setPreferredSize(new Dimension(100,50));
		left_lb.setForeground(new Color(143,132,107));
		north_p.add(left_lb);
		north_p.add(north_center_p);
		right_lb.setForeground(new Color(143,132,107));
		north_p.add(right_lb);
		
		north_center_p.setLayout(new GridLayout(1,4));
		
		north_center_p.add(null_p);
		
		start_bt.setImage(Toolkit.getDefaultToolkit().getImage("img/game/sap.png"));
		north_center_p.add(start_bt); start_bt.addActionListener(this);
		north_center_p.add(null_p);
		
		lp.add("Center", p);
		p.setLayout(new GridLayout(9,9));
		for(int i=0; i<9; ++i) {
			for (int j=0; j<9; ++j) {
				bt[i][j] = new MyButton0();
				bt[i][j].setImage(Toolkit.getDefaultToolkit().getImage("img/game/land.png"));
				bt[i][j].setEnabled(false);
				bt[i][j].addMouseListener(this);
				p.add(bt[i][j]);
			}
		}
		
		con.add("East", rp);
		rp.setPreferredSize(new Dimension(150,400));
		rp.setLayout(new GridLayout(2,1));
		rp.add(rule_area);
		rp.add(rank_jp);
		rank_jp.setLayout(new GridLayout(2,1));
		
		rule_area.setText("");
		rule_area.append("\n");
		rule_area.append("\n");
		rule_area.append("\n");
	
		rule_area.append("              ♠ 화석캐기룰\n");
		rule_area.append("  1. 삽버튼을 누른다\n");
		rule_area.append("  2. 화석이 있을 곳에\n      오른쪽 마우스 클릭 \n");
		rule_area.append("  3. 화석을 클릭시 \n      텃밭이 망가져서 종료!\n");
		rule_area.append("  4. 재시작시 골드버튼 클릭\n");
		rule_area.setEditable(false);
		
		rank_jp.add(rank_jp2);
		rank_jp2.setLayout(new BorderLayout());
		rank_jp2.add("North", rank_jlb);
		rank_jp2.add("Center", ranking_area);
		ranking_area.setText("");

		ranking_area.setEditable(false);
		rank_jp.add(rank_jbt);
	}
	
	public void clearScore() {
		start_bt.setImage(Toolkit.getDefaultToolkit().getImage("img/game/sap.png"));
		start_bt.repaint();
		count = 10;
		time = 0;
		right_lb.setText("남은화석수 : " + count + "개");
		left_lb.setText("시간 : " + time + "초");
	}
	
	public void setRank(String rank) {
		ranking_area.setText("");
		System.out.println("세팅준비");
		System.out.println(rank);
		if(rank == null) {
			ranking_area.append("");
		}else {
			String[] text = rank.split("@");
			System.out.println(text.length);
			if(text.length < 3) {
				for(int i=0; i<text.length; ++i) {
					String[] data = text[i].split("#");
					ranking_area.append(data[1] + "  " + data[2] + "\n");
				 }

			} else {
				for(int i=0; i<3; ++i) {
					String[] data = text[i].split("#");
					ranking_area.append(data[1] + "  " + data[2] + "\n");
				}
			}
		}
	}
	
	public void clearBoomb() {
		for(int i=0; i<9; ++i) {
			for (int j=0; j<9; ++j) {
				bt[i][j].setImage(Toolkit.getDefaultToolkit().getImage("img/game/land.png"));
				arr[i][j] = 0;
				check[i][j] = false;
			}
		}
		boombSetting();
	}
	
	public void boombSetting() {
		for(int i=1; i<=10; ++i) {
			int x, y;
			do {
				x = (int)(Math.random()*9);
				y = (int)(Math.random()*9);
			}while(arr[x][y] != 0);
			arr[x][y] = -1;
			int minI = x-1; if (minI<0) minI = 0;
			int maxI = x+1; if (maxI>8) maxI = 8;
			int minJ = y-1; if (minJ<0) minJ = 0;
			int maxJ = y+1; if (maxJ>8) maxJ = 8;
			for(int a=minI; a<=maxI; ++a) {
				for(int b=minJ; b<=maxJ; ++b) {
					if (arr[a][b] == -1) continue;
					arr[a][b]++;
				}
			}
		}
		buttonSetting();
	}
	
	public void buttonSetting() {
		start_bt.setEnabled(isEnable);
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				bt[i][j].setEnabled(!isEnable);
			}
		}
	}
	
	public int sendRank() {
		return time;
	}
	
	public GameZero(Frame owner, String title, boolean modal) {
		super(owner, title, modal);

		this.init();
		
		this.setSize(500, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		dlg.setResizable(false);
		dlg.setBounds(xpos, ypos, 400,200);
		dlg2.setResizable(false);
		dlg2.setBounds(xpos, ypos, 400,200);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==start_bt) {
			start_bt.setImage(Toolkit.getDefaultToolkit().getImage("img/game/gold.png"));
			start_bt.repaint();
			count = 10;
			time = 0;
			isEnable = false;
			isTime = true;
			clearBoomb();
			right_lb.setText("남은화석수 : 10개");
			Thread th = new Thread(this);
			th.start();
		}else if (e.getSource()==dlg_bt) {
			dlg.setVisible(false);
		}else if (e.getSource()==dlg_bt3) {
			dlg2.setVisible(false);
		}
	}

	@Override
	public void run() {
		while(isTime) {
			time++;
			left_lb.setText("시간 : 0"+time+"초");
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
				
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				if (bt[i][j] == e.getSource()) {
					if (e.getButton() == 1) {
						if (arr[i][j] == -1) {
							bt[i][j].setImage(Toolkit.getDefaultToolkit().getImage("img/game/pa.png"));
							bt[i][j].repaint();
							start_bt.setImage(Toolkit.getDefaultToolkit().getImage("img/game/gold.png"));
							isEnable = true;
							isTime = false;
							dlg_lb.setText("텃밭이 망가져서 게임을 종료합니다.");
							dlg.setVisible(true);
							buttonSetting();
						}
						else {
							if (arr[i][j] == 0) {
								bt[i][j].setImage(Toolkit.getDefaultToolkit().getImage("img/game/land.png"));
								bt[i][j].repaint();
								zeroCheck(i, j);
							}else {
								bt[i][j].setImage(Toolkit.getDefaultToolkit().getImage("img/game/"+arr[i][j]+".png"));
								bt[i][j].repaint();
								check[i][j] = true;
							}
						}
					}else if (e.getButton() == 3){
						if (!check[i][j]) {
							bt[i][j].setImage(Toolkit.getDefaultToolkit().getImage("img/game/hwa3.png"));
							bt[i][j].repaint();
							check[i][j] = true;
							count--;
							right_lb.setText("남은화석수 : "+count+"개");
						}else {
							bt[i][j].setImage(Toolkit.getDefaultToolkit().getImage("img/game/land.png"));
							bt[i][j].repaint();
							check[i][j] = false;
							count++;
							right_lb.setText("남은화석수 : "+count+"개");
						}
					}
					endBoomb();
				}
			}
		}
	}
		
	public void endBoomb() {
		if (count<0) {
			return;
		}
		for(int i=0; i<9; ++i) {
			for(int j=0; j<9; ++j) {
				if (!check[i][j]) {
					return;
				}
			}
		}
		isEnable = true;
		isTime = false;
		dlg_lb2.setText(time+"초만에 화석캐기 완료!.");
		dlg2.setVisible(true);
		buttonSetting();
		return;
	}
	
	public void zeroCheck(int x, int y) {
		check[x][y] = true;
		int minI = x-1; if (minI<0) minI = 0;
		int maxI = x+1; if (maxI>8) maxI = 8;
		int minJ = y-1; if (minJ<0) minJ = 0;
		int maxJ = y+1; if (maxJ>8) maxJ = 8;
		for(int a=minI; a<=maxI; ++a) {
			for(int b=minJ; b<=maxJ; ++b) {
				if (check[a][b]) continue;
				if (arr[a][b] == 0) {
					bt[a][b].setImage(Toolkit.getDefaultToolkit().getImage("img/game/0.png"));
					bt[a][b].repaint();
					zeroCheck(a, b);
				}
				bt[a][b].setImage(Toolkit.getDefaultToolkit().getImage("img/game/"+arr[a][b]+".png"));
				bt[a][b].repaint();
				check[a][b] = true;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
