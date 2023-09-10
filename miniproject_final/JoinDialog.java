package miniproject_final;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class JoinDialog extends JDialog{
	// 회원가입 아이디, 비밀번호 기입
	private JLabel join_jlb = new JLabel("회원가입", JLabel.CENTER);
	private JLabel id_jlb = new JLabel("아이디", JLabel.CENTER);
	private JTextField id_jtf = new JTextField();
	private JLabel pw_jlb = new JLabel("비밀번호", JLabel.CENTER);
	private JTextField pw_jtf = new JTextField();
	
	// 이미지를 구현하는 jtb
	ImageIcon ii1 = new ImageIcon("img/join/char1.png");
	ImageIcon ii2 = new ImageIcon("img/join/char2.png");
	
	JToggleButton jtb1 = new JToggleButton(ii1);
	JToggleButton jtb2 = new JToggleButton(ii2);
	
	ButtonGroup bg = new ButtonGroup();
	
	// 캐릭터 Choice
//	private Choice char_ch = new Choice();
	
	// 자기소개창
	private JLabel name_jlb = new JLabel("이 름", JLabel.CENTER);
	private JTextField name_jtf = new JTextField();
	private JLabel mbti_jlb = new JLabel("MBTI", JLabel.CENTER);
	private JTextField mbti_jtf = new JTextField();
	private JLabel hobby_jlb = new JLabel("취 미", JLabel.CENTER);
	private JTextField hobby_jtf = new JTextField();
	private JLabel food_jlb = new JLabel("좋아하는 음식", JLabel.CENTER);
	private JTextField food_jtf = new JTextField();
	private JLabel color_jlb = new JLabel("좋아하는 색깔", JLabel.CENTER);
	JTextField color_jtf = new JTextField();
	
	// 확인, 취소 버튼
	JButton ok_jbt = new JButton("확인");
	JButton cancel_jbt = new JButton("취소");
	
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel center_jp = new JPanel();
	private JPanel join_jp = new JPanel();
	private JPanel bt_jp = new JPanel();
	
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		join_jp.setBackground(new Color(216, 198, 234));
		// 회원가입 라벨
		con.add("North", join_jp);
		join_jp.add(join_jlb);
		
		// 캐릭터 선택 구현
		con.add("Center", center_jp);
		center_jp.setLayout(new GridLayout(2,1));
		jp1.setBackground(Color.WHITE);
		center_jp.add(jp1);
		jp1.setLayout(new GridLayout(1,2));
		jtb1.setBackground(Color.WHITE);
		jtb1.setToolTipText("남자 캐릭터 선택");	// 마우스를 올리면 뜨는 메세지
		bg.add(jtb1);
		jp1.add(jtb1);
		jtb2.setBackground(Color.WHITE);
		jtb2.setToolTipText("여자 캐릭터 선택");	// 마우스를 올리면 뜨는 메세지
		bg.add(jtb2);
		jp1.add(jtb2);
		
		// 가입 정보 넣는 곳 구현
		jp2.setBackground(new Color(241, 231, 254));
		center_jp.add(jp2);
		jp2.setLayout(new GridLayout(7,2));
		id_jlb.setFont(new Font("", Font.BOLD, 13));
		jp2.add(id_jlb);
		jp2.add(id_jtf);
		pw_jlb.setFont(new Font("", Font.BOLD, 13));
		jp2.add(pw_jlb);
		jp2.add(pw_jtf);
		name_jlb.setFont(new Font("", Font.BOLD, 13));
		jp2.add(name_jlb);
		jp2.add(name_jtf);
		mbti_jlb.setFont(new Font("", Font.BOLD, 13));
		jp2.add(mbti_jlb);
		jp2.add(mbti_jtf);
		hobby_jlb.setFont(new Font("", Font.BOLD, 13));
		jp2.add(hobby_jlb);
		jp2.add(hobby_jtf);
		food_jlb.setFont(new Font("", Font.BOLD, 13));
		jp2.add(food_jlb);
		jp2.add(food_jtf);
		color_jlb.setFont(new Font("", Font.BOLD, 13));
		jp2.add(color_jlb);
		jp2.add(color_jtf);
		
		// 확인, 취소 버튼 구현
		con.add("South", bt_jp);
		bt_jp.setBackground(new Color(216, 198, 234));
		bt_jp.setLayout(new FlowLayout());
		bt_jp.add(ok_jbt);
		bt_jp.add(cancel_jbt);
		
	}
	
//	public void setText(SungJuk sungJuk) {
//		name_tf.setText(sungJuk.getName());
//		kor_tf.setText(String.valueOf(sungJuk.getKor()));
//		eng_tf.setText(String.valueOf(sungJuk.getEng()));
//		name_tf.setEditable(false);
//	}
	
	public String getID() {
		return id_jtf.getText();
	}
	public String getPW() {
		return pw_jtf.getText();
	}
	public String getName() {
		return name_jtf.getText();
	}
	public String getChara() {
		if(bg.getSelection()==jtb1.getModel()) {
		//if(bg.getSelection().equals(jtb1.getModel())) {
			return "1";
		}else if(bg.getSelection()==jtb2.getModel()) {
			return "2";
		}
		return "0";
	}
	public String getMbti() {
		return mbti_jtf.getText();
	}
	public String getHobby() {
		return hobby_jtf.getText();
	}
	public String getFood() {
		return food_jtf.getText();
	}
	public String getColor() {
		return color_jtf.getText();
	}
	public void clear() {
		id_jtf.setText("");
		pw_jtf.setText("");		
		name_jtf.setText("");
		bg.setSelected(jtb1.getModel(), false);
		bg.setSelected(jtb2.getModel(), false);		
		mbti_jtf.setText("");
		hobby_jtf.setText("");		
		food_jtf.setText("");
		color_jtf.setText("");		
	}
	
	public JoinDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		this.init();
		
		this.setSize(600, 700);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
//	public static void main(String[] args) {
//		JFrame jf = new JFrame();
//		JoinDialog jd = new JoinDialog(jf, "", true);
//		
//		jd.setVisible(true);
//		System.out.print(jd.getChara());
//	}
}

