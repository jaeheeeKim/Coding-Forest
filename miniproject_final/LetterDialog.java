package miniproject_final;

import java.awt.*;
import javax.swing.*;

//버튼에 내용이 없을 때 나오는 Dialog
public class LetterDialog extends JDialog{
	// 이미지를 구현하는 jtb
	ImageIcon ii1 = new ImageIcon("img/myroom/letter1.png");
	ImageIcon ii2 = new ImageIcon("img/myroom/letter2.png");
	ImageIcon ii3 = new ImageIcon("img/myroom/letter3.png");
	ImageIcon ii4 = new ImageIcon("img/myroom/letter4.png");
	
	private JToggleButton jtb1 = new JToggleButton(ii1);
	private JToggleButton jtb2 = new JToggleButton(ii2);
	private JToggleButton jtb3 = new JToggleButton(ii3);
	private JToggleButton jtb4 = new JToggleButton(ii4);
	
	ButtonGroup bg = new ButtonGroup();
	
	private JPanel center_jp = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel south_jp = new JPanel();
	
	// 메세지 입력창 jtf
	private JTextField jtf = new JTextField();
	
	// 보내기와 취소 버튼
	protected JButton ok_jbt = new JButton("보내기");
	protected JButton cancel_jbt = new JButton("취소");
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("Center", center_jp);
		center_jp.setLayout(new BorderLayout());
		center_jp.add(jp2);
		
		// 붙일 포스트잇을 선택할 화면 구현
		jp2.setLayout(new GridLayout(1,4));
		jtb1.setToolTipText("별 포스트잇");	// 마우스를 올리면 뜨는 메세지
		bg.add(jtb1);
		jp2.add(jtb1);
		
		jtb2.setToolTipText("하트 포스트잇");	// 마우스를 올리면 뜨는 메세지
		bg.add(jtb2);
		jp2.add(jtb2);
		
		jtb3.setToolTipText("클로버 포스트잇");	// 마우스를 올리면 뜨는 메세지
		bg.add(jtb3);
		jp2.add(jtb3);
		
		jtb4.setToolTipText("곰돌이 포스트잇");	// 마우스를 올리면 뜨는 메세지
		bg.add(jtb4);
		jp2.add(jtb4);
		
		// 메세지를 적을 jtf 구현
		center_jp.add("South", jp3);
		jp3.setLayout(new BorderLayout());
		jp3.add(jtf);
		
		// 보내기, 취소버튼 구현
		con.add("South", south_jp);
		south_jp.setLayout(new FlowLayout());
		south_jp.add(ok_jbt);
		south_jp.add(cancel_jbt);
		
	}
	
	public void clearText() { //tf 지우기
		jtf.setText("");
	}
	
	public String selectButton() { //선택된 버튼의 값 반환
		String select;
		if(jtb1.isSelected()) {
			return select = "1"; //별
		}else if(jtb2.isSelected()) {
			return select = "2"; //하트
		}else if(jtb3.isSelected()) {
			return select = "3"; //클로버
		}else if(jtb4.isSelected()) {
			return select = "4"; //곰돌이
		}else {
			return null;
		}
	}
	
	public String sendText() { //방명록 내용/포스트잇 내보내기
		String letter = selectButton() + '#'+ jtf.getText(); //1#내용 형식으로 반환
		clearText();
		return letter;
	}
	
	
	public LetterDialog(Frame owner, String title, boolean modal) {
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
//버튼에 내용이 있을 때 뜨는 Dialog
class ViewDialog extends JDialog{
	
	private JTextArea jta = new JTextArea();
	private JScrollPane jsp = new JScrollPane(jta);
	protected JButton bt_exit = new JButton("나가기");
	
	private String text;
	
	public void setText(String text) { //방명록 내용 세팅
		String[] letter = text.split("#");
		jta.setText(letter[1]);
	}
	public void clearText() { //방명록 창 다시 clear
		jta.setText("");
	}

	public void init() {
		this.setLayout(new BorderLayout());
		this.add("Center",jsp);
		jta.setEnabled(false);
		this.add("South",bt_exit);
		
	}
	public ViewDialog(Frame owner, String title, boolean modal) {
		super(owner,title, modal);
		this.init();
		
		this.setSize(200, 200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - this.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - this.getHeight()/2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
}
