package miniproject_final;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MainHallGUI extends JFrame
		implements Runnable, ActionListener, KeyListener, WindowListener, MouseListener {
	private Container con;
	// 화면의 왼쪽에 나올 그림 패널
	private JPanel main_jp1 = new JPanel() {
		private ImageIcon back_img = new ImageIcon("img/main/main1.png");

		public void paintComponent(Graphics g) {
			g.drawImage(back_img.getImage(), -50, -50, null);
		}
	};
	private int mainWidth = 584;
	private int mainHeight = 561;

	// 화면의 오른쪽에 들어갈 패널
	private JPanel main_jp2 = new JPanel();

	// 채팅창과 접속자창을 나눌 패널
	private JPanel chat_jp = new JPanel();
	private JPanel user_jp = new JPanel();

	// 채팅
	private JLabel chat_jlb = new JLabel("채팅", JLabel.CENTER);
	private JTextArea chat_jta = new JTextArea();
	private JScrollPane chat_jsp = new JScrollPane(chat_jta); // 스크롤바 추가

	// 채팅안에 들어갈 jtf, 버튼
	private JPanel chat_jp2 = new JPanel();
	private JTextField chat_jtf = new JTextField();
	private JButton ok_jbt = new JButton("보내기");

	// 접속자
	private JLabel user_jlb = new JLabel("접속자", JLabel.CENTER);
	private JTextArea user_jta = new JTextArea();
	private JScrollPane user_jsp = new JScrollPane(user_jta); // 스크롤바 추가

	// 내 캐릭터 패널 및 설정 정보
	private CharacterLabel myCharaLabel;
	private Member myMember;
	private String myNickname;
	private int mycharaNum;

	// 건물 아이콘
	private MapIcon gameCenter = new MapIcon("게임센터", 1, 50, 50);
	private MapIcon myRoom = new MapIcon("마이룸", 2, 350, 200);

	// 네트워크 관련
	private InetAddress ia;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;

	// 현재 접속자(화면에 존재하는 캐릭터)
	private Hashtable<String, CharacterLabel> characterHt = new Hashtable<>(); // <닉네임, 캐릭터 패널>

	// 마우스 커서 이미지 넣기
	private Image cursorimg = Toolkit.getDefaultToolkit().getImage("img/main/cursor.png");
	private Point point = new Point(30, 30);

	// help버튼
	private Image img_help = Toolkit.getDefaultToolkit().getImage("img/main/helpbt.png");
	private MyButton01 mb2 = new MyButton01(img_help);
	
	// 친구추가 버튼
	private Image img_naver = Toolkit.getDefaultToolkit().getImage("img/friend.png");
	private MyButton01 mb3 = new MyButton01(img_naver);

	// 회원가입&로그인을 위한 변수
	String login; // 로그인중인지 확인하는 변수
	String id; // id(닉네임을 받을 String 변수

	// 마이룸을 위한 변수
	String imfo; // id 의 해당 정보를 받을 변수(,id,chara, 형식으로)
	String bt_num; // 몇번 버튼을 눌렀는지 값을 저장
	String friend_id; // 놀러갈 친구의 아이디를 입력받기 위한 변수
	String f_imfo; // 놀러갈 친구의 정보를 받아올 변수(f,id,chara, 형식으로)
	String f_bt_num; // 친구룸 몇번 버튼 눌렀는지 값을 저장

	// 게임을 위한 변수
	String gd = "dudu";
	String gz = "zero";
	String gr = "rock";
	String rank_d;
	String rank_r;
	String rank_z;
	
	//친구추가 변수
	String mbi = "memberInformation";

	// 노래
	Music introMusic;

// 다이얼로그
//----------------------------------------------------------------------------------------------	
	// 도움말
	private HelpDialog help_dlg = new HelpDialog(this, "도움말", false);
	
	//친구추가
	private Naver2 naver = new Naver2(); 

	// 회원가입, 로그인
	private LoginDialog login_dlg = new LoginDialog(this, "로그인", false);
	private JoinDialog join_dlg = new JoinDialog(this, "회원가입", false);

	// 게임용
	private GameDialog game_dlg = new GameDialog(this, "게임", false);
	private GameRock rock_dlg = new GameRock(this, "가위바위보", false);
	private GameZero zero_dlg = new GameZero(this, "화석찾기", false);
	private GameDudu dudu_dlg = new GameDudu(this, "너굴잡기게임", false);

	// 마이룸용
	private MyRoomDialog myroom_dlg = new MyRoomDialog(this, "마이룸", true);
	private MyRoomDialog friendroom_dlg = new MyRoomDialog(this, "마이룸", true);
	private LetterDialog letter_dlg = new LetterDialog(this, "방명록쓰기", true);
	private ViewDialog view_dlg = new ViewDialog(this, "방명록읽기", true);
	private LetterDialog f_letter_dlg = new LetterDialog(this, "친구 방명록쓰기", true);
	private ViewDialog f_view_dlg = new ViewDialog(this, "친구 방명록읽기", true);

// 메인 스레드
//----------------------------------------------------------------------------------------------	
	public static void main(String[] args) {
		new MainHallGUI("놀러와요 코딩의 숲");
	}

// 생성자 (초기설정, UI)
//----------------------------------------------------------------------------------------------	
	public MainHallGUI(String title) {
		super(title);

		login_dlg.setVisible(true);

		this.setSize(800, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);

		this.init();
		this.addListeners();

		con.setFocusable(true);
		con.requestFocus();

		start();

		introMusic = new Music(true);
		introMusic.start();
	}

	public void init() {
		con = this.getContentPane();

		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorimg, point, "");
		con.setCursor(cursor);

		con.setLayout(new BorderLayout());
		con.add("Center", main_jp1);
		con.add("East", main_jp2);

		// 메인 복도
		main_jp1.setLayout(null);
		mb2.setBounds(0, 0, 55, 55);
		main_jp1.add(mb2);
		mb3.setBounds(57, 0, 55, 55);
		main_jp1.add(mb3);

		// 채팅 창
		main_jp2.setLayout(new GridLayout(2, 1));
		chat_jp.setPreferredSize(new Dimension(200, 300));
		chat_jp.setBackground(new Color(216, 198, 234));
		main_jp2.add(chat_jp);
		chat_jp.setLayout(new BorderLayout());
		chat_jlb.setFont(new Font("", Font.TYPE1_FONT, 15));
		chat_jp.add("North", chat_jlb);
		chat_jta.setFont(new Font("", Font.ITALIC, 13));
		chat_jp.add("Center", chat_jsp); // 스크롤바 될 수 있게
		chat_jp.add("South", chat_jp2);
		chat_jp2.setLayout(new GridLayout(1, 2));
		chat_jp.setPreferredSize(new Dimension(200, 300));
		chat_jp2.add(chat_jtf);
		chat_jp2.add(ok_jbt);
		chat_jta.setEditable(false); // 채팅창에서 입력하지 못하게

		// 접속자창
		user_jp.setBackground(new Color(216, 198, 234));
		main_jp2.add(user_jp);
		user_jp.setLayout(new BorderLayout());
		user_jlb.setFont(new Font("", Font.TYPE1_FONT, 15));
		user_jp.add("North", user_jlb);
		user_jta.setFont(new Font("", Font.ITALIC, 13));
		user_jp.add("Center", user_jsp); // 스크롤바 될 수 있게
		user_jta.setEditable(false); // 접속자창에서 입력하지 못하게
		
		//복도 최종 너비 구하기
		//mainWidth = main_jp1.getWidth();
		//mainHeight = main_jp1.getHeight();

	}

	// 이벤트리스너 추가
	private void addListeners() {
		this.addWindowListener(this);
		con.addKeyListener(this);
		main_jp1.addMouseListener(this);

		// help 버튼
		mb2.addActionListener(this);
		// 친구추가 버튼
		mb3.addActionListener(this);
		//친구 목록 다이얼로그
		naver.search.addActionListener(this);
		naver.co_bt.addActionListener(this);
		naver.ok_bt.addActionListener(this);

		// 채팅창
		chat_jtf.addActionListener(this);
		ok_jbt.addActionListener(this);

		// 로그인 : 회원가입버튼, 로그인버튼
		login_dlg.addWindowListener(this);
		login_dlg.join_jbt.addActionListener(this);
		login_dlg.login_jbt.addActionListener(this);
		login_dlg.pw_jtf.addActionListener(this);

		// 회원가입 : 확인버튼, 취소버튼
		join_dlg.ok_jbt.addActionListener(this);
		join_dlg.color_jtf.addActionListener(this);
		join_dlg.cancel_jbt.addActionListener(this);

		// 게임창
		game_dlg.bt1.addActionListener(this);
		game_dlg.bt2.addActionListener(this);
		game_dlg.bt3.addActionListener(this);

		// 마이룸
		myroom_dlg.friend_jbt.addActionListener(this);
		myroom_dlg.exit_jbt.addActionListener(this);
		for (int i = 0; i < myroom_dlg.mbt.length; ++i) { // 방명록 버튼
			myroom_dlg.mbt[i].addActionListener(this);
		}

		// 친구룸
		friendroom_dlg.friend_jbt.addActionListener(this);
		friendroom_dlg.exit_jbt.addActionListener(this);
		for (int i = 0; i < friendroom_dlg.mbt.length; ++i) { // 방명록 버튼
			friendroom_dlg.mbt[i].addActionListener(this);
		}

		// 마이룸 - letter 쓰기
		letter_dlg.ok_jbt.addActionListener(this);
		letter_dlg.cancel_jbt.addActionListener(this);

		// 마이룸 - view 보기
		view_dlg.bt_exit.addActionListener(this);

		// 친구룸 - letter 쓰기
		f_letter_dlg.ok_jbt.addActionListener(this);
		f_letter_dlg.cancel_jbt.addActionListener(this);

		// 친구룸 - view 보기
		f_view_dlg.bt_exit.addActionListener(this);

		// 게임
		dudu_dlg.rank_jbt.addActionListener(this);
		rock_dlg.bt6.addActionListener(this);
		zero_dlg.rank_jbt.addActionListener(this);

	}

	// 창이 활성화되면 호출
	public void start() {
		try {
			// 텍스트 수신 시작
			ia = InetAddress.getByName("localhost");
			soc = new Socket(ia, 12345);
			Thread th = new Thread(this);
			th.start();
		} catch (Exception e) {e.printStackTrace();}
	}

//보조 기능
//----------------------------------------------------------------------------------------------
	// 화면에 캐릭터 추가
	private void NewCharacterIn(CharacterLabel cl) {
		main_jp1.add(cl);
		cl.setSize(60, 95);
		cl.setX(getRandomPosition(mainWidth));
		cl.setY(getRandomPosition(mainHeight));
		setCharacterLocation(cl);
	}

	// 좌표에 저장된 값으로 보이는 위치 재설정
	private void setCharacterLocation(CharacterLabel cl) {
		cl.setLocation(cl.getX(), cl.getY());
	}

	// 랜덤좌표 얻기
	private int getRandomPosition(int size) {
		return (int) ((size - 80) * Math.random() + 1);
	}
	
	// 접속자 창 갱신
	private void setUserJta() {
		user_jta.setText("★" + myNickname + "\n");
		Enumeration<String> enu = characterHt.keys();
		while (enu.hasMoreElements()) {
			String nicks = enu.nextElement();
			user_jta.append("★" + nicks + "\n");
		}
	}
	
	// 집 삭제 후 재배치
	private void reAddHouse() {
		main_jp1.remove(gameCenter);
		main_jp1.remove(myRoom);
		
		main_jp1.add(gameCenter);
		gameCenter.setSize(100, 110);
		gameCenter.setLocation(gameCenter.getX(), gameCenter.getY());
		main_jp1.add(myRoom);
		myRoom.setSize(100, 110);
		myRoom.setLocation(myRoom.getX(), myRoom.getY());
	}

//주요 기능
//----------------------------------------------------------------------------------------------	
	// 메인복도 초기설정
	private void MainHallStart() throws Exception {
		// 내 캐릭터 등장
		myNickname = id;
		mycharaNum = Integer.parseInt(myMember.getChara());
		myCharaLabel = new CharacterLabel(myNickname, mycharaNum, 0, 0); // db에서 받아와야함 닉네임, 캐릭터번호 (x좌표, y좌표)-이건 안받아두됨
		NewCharacterIn(myCharaLabel);

		// 건물 배치
		main_jp1.add(gameCenter);
		gameCenter.setSize(100, 110);
		gameCenter.setLocation(gameCenter.getX(), gameCenter.getY());
		main_jp1.add(myRoom);
		myRoom.setSize(100, 110);
		myRoom.setLocation(myRoom.getX(), myRoom.getY());

		// 접속자 알림 및 초기 위치 전송
		pw = new PrintWriter(soc.getOutputStream(), true);
		pw.println("#" + myNickname + ":" + myCharaLabel.getX() + ":" + myCharaLabel.getY() + ":" + mycharaNum);
		pw.flush();
	}

	// 서버로 텍스트 전송
	private void SendText(String str) throws Exception {
		pw = new PrintWriter(soc.getOutputStream(), true);
		pw.println(str);
		pw.flush();
	}

	// 좌표 변화 전송
	private void sendChangePosStr() {
		try {
			SendText("$" + myNickname + ":" + myCharaLabel.getX() + ":" + myCharaLabel.getY() + ":"
					+ myCharaLabel.getCharaNum());
		} catch (Exception ex) {ex.printStackTrace();}
	}

	// 종료 알림 전송
	private void sendExitStr() {
		try {
			SendText("%" + myNickname);
		} catch (Exception ex) {ex.printStackTrace();}
	}

	// 서버로부터 텍스트 수신
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			while (true) {
				String msg = br.readLine();
				if (msg == null)
					break;
				AnalizeGotText(msg);
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	// 받은 텍스트 해독 및 실행
	private void AnalizeGotText(String msg) {
		if (msg.charAt(0) == '#') { // 새 캐릭터 접속 알림 받음 //#닉네임:x좌표:y좌표:캐릭터번호
			String[] arr = msg.substring(1).split(":");
			String name = arr[0];
			int xpos = Integer.parseInt(arr[1]);
			int ypos = Integer.parseInt(arr[2]);
			int charNum = Integer.parseInt(arr[3]);

			if (!name.equals(myNickname)) {
				// 새로운 (남의) 캐릭터 생성
				characterHt.put(name, new CharacterLabel(name, charNum, xpos, ypos));
				CharacterLabel cl = characterHt.get(name);
				main_jp1.add(cl);
				cl.setSize(60, 95);
				setCharacterLocation(cl);
				
				reAddHouse();

				// 입장하셨습니다 메시지 보여주기
				chat_jta.append("[ " + name + " ]님 입장 * * * * * * * * * *\n");
			}
			setUserJta();
			
		} else if (msg.charAt(0) == '$') { // 위치 변화 알림 받음 //$닉네임:x좌표:y좌표:캐릭터번호
			String[] arr = msg.substring(1).split(":");
			String name = arr[0];
			int xpos = Integer.parseInt(arr[1]);
			int ypos = Integer.parseInt(arr[2]);

			CharacterLabel cl = characterHt.get(name);
			cl.setX(xpos);
			cl.setY(ypos);
			setCharacterLocation(cl);
		} else if (msg.charAt(0) == '%') { // 종료 알림 받음 //%닉네임
			String name = msg.substring(1);
			CharacterLabel cl = characterHt.get(name);
			main_jp1.remove(cl);
			this.repaint();
			characterHt.remove(name);
			setUserJta();
		} else if (msg.charAt(0) == '^') { // 기존 캐릭터 위치 받음(최초접속시) //^닉네임:x좌표:y좌표:캐릭터번호
			String[] arr = msg.substring(1).split(":");
			String name = arr[0];
			int xpos = Integer.parseInt(arr[1]);
			int ypos = Integer.parseInt(arr[2]);
			int charNum = Integer.parseInt(arr[3]);
			
			//기존 캐릭터 추가
			characterHt.put(name, new CharacterLabel(name, charNum, xpos, ypos));
			CharacterLabel cl = characterHt.get(name);
			main_jp1.add(cl);
			cl.setSize(60, 95);
			setCharacterLocation(cl);
			
			reAddHouse();
			
		} else if (msg.charAt(0) == '!') { // 채팅 받음
			chat_jta.append(msg.substring(1) + "\n");
		}

		// 게임
		else if (msg.charAt(0) == '@') {
			if (msg.charAt(1) == 'd') {
				rank_d = msg.substring(1);
				dudu_dlg.setRank(rank_d);
				gameDuduRank(gr);
			} else if (msg.charAt(1) == 'r') {
				rank_r = msg.substring(1);
				rock_dlg.setRank(rank_r);
				gameDuduRank(gz);
			} else if (msg.charAt(1) == 'z') {
				rank_z = msg.substring(1);
				zero_dlg.setRank(rank_z);
			}
		} else if (msg.substring(0, 2).equals("게임")) {
			gameDuduRank(gd); // 다시 게임들의 랭킹데이터를 받아온다.
		}

		// 마이룸
		else if (msg.charAt(0) == ',') { // 초기 데이터 보낸 후, 받기용
			if (msg.charAt(1) == ',') { // 친구
				f_imfo = msg.substring(2);
				System.out.println(f_imfo);
				friendroom_dlg.firstSetting(f_imfo);
				friendroom_dlg.friendbtSetting();
				friendroom_dlg.setVisible(true);
			} else {
				imfo = msg.substring(1);
				myroom_dlg.firstSetting(imfo);
			}

		} else if (msg.substring(0, 2).equals("성공")) { // 방명록쓰고, db저장 완료 후 서버에서 "성공"을 보내면, 다시 마이룸을 세팅한다.
			String[] data = msg.split(":");
			if (data[1].equals(id)) { // 내 방명록
				dataStart();
			} else {
				friendData(); // 친구 방명록
			}
		}
		
		//친구 추가창
		else if (msg.charAt(0) == 'm') {
			System.out.println(msg);
			naver.send(msg);	
		}

		// 회원가입&로그인
		//가입성공
		else if (msg.equals("가입성공")) {
			join_dlg.clear();
			join_dlg.setVisible(false);
			login_dlg.setVisible(true);
		
		//로그인 실패
		} else if (msg.equals("로그인비번오류")) {
			JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("로그인아이디오류")) {
			JOptionPane.showMessageDialog(null, "저희 회원이 아닙니다.", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("이미 접속중")) {
			JOptionPane.showMessageDialog(null, "이미 접속중입니다.", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("친구목록없음")) {
			JOptionPane.showMessageDialog(null, "친구목록이 없습니다.", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (msg.equals("친구없음")) {
			JOptionPane.showMessageDialog(null, "해당 id 친구가 없습니다.", "알림", JOptionPane.ERROR_MESSAGE);
		}
		//로그인 성공
		else {
			String[] arr = msg.split("@");
			if (arr[0].equals("로그인성공")) {
				myMember = new Member(arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9],
						arr[10], arr[11], arr[12], arr[13], arr[14]);
				JOptionPane.showMessageDialog(null, id + "님 환영합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
				dataStart();
				login_dlg.setVisible(false);
				try {MainHallStart();} catch (Exception e) {e.printStackTrace();}
				this.setTitle(id + "의 코딩의 숲♣");
				this.setVisible(true);
			}
		} 
	}

//회원가입&로그인
//----------------------------------------------------------------------------------------------	
	// 회원가입, 로그인 시 서버로 요청 전송
	public void joinData(String joinData) {
		try {
			pw = new PrintWriter(soc.getOutputStream(), true);
			pw.println(joinData);
			System.out.println("회원가입 및 로그인 요청");
			pw.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	// 회원가입 비밀번호 제한
	public boolean checkInputOnlyNumberAndAlphabet(String textInput) {
		char chrInput;
		for (int i = 0; i < textInput.length(); i++) {
			chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
				// 영문(소문자) OK!
			} else if (chrInput >= 0x41 && chrInput <= 0x5A) {
				// 영문(대문자) OK!
			} else if (chrInput >= 0x30 && chrInput <= 0x39) {
			} // 숫자 OK!
			else {
				return false; // 영문자도 아니고 숫자도 아님!
			}
		}
		return true;
	}
	
//마이룸
//----------------------------------------------------------------------------------------------	
	public void dataStart() {// 초기 세팅을 위한 데이터 요청
		pw.println("," + id);
		System.out.println("," + id + "마이룸데이터 요청");
		pw.flush();
	}

	public void friendData() {// 친구 마이룸 세팅을 위한 데이터 요청
		pw.println(",," + friend_id);
		pw.flush();
	}

	public void sendData(String letter, String id) { // 방명록 쓰면, 서버에 정보 보내기
		String text = ':' + id + ':' + letter;
		pw.println(text); // :이름:번호:2#내용
		pw.flush();
	}

//게임
//----------------------------------------------------------------------------------------------
	public void gameDuduRank(String game) { // 게임별로 랭킹 데이터요청
		pw.println("g" + game); // gdudu gzero grock
		pw.flush();
	}

	public void gameDuduSend(String game, int rank) { // 3등 까지 랭크 설정
		String su = String.valueOf(rank);
		String text = '.' + game + ',' + id + ',' + su;
		pw.println(text); // .gr.이름.점수
		pw.flush();
	}
	
//친구추가 요청
//--------------------------------------------------------
public void naverData(String infor) { //서버에 데이터 요청
	try {
		pw.println(infor);		
		pw.flush();
		System.out.println("요청전송완료");
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//이벤트
//----------------------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		// 채팅창 ok버튼 누를시
		if (e.getSource() == ok_jbt || e.getSource() == chat_jtf) {
			String msg = "!" + myNickname + ":";
			msg += chat_jtf.getText();
			pw.println(msg);
			pw.flush();
			chat_jtf.setText("");
			chat_jta.append(myNickname + " : " + msg.split(":")[1] + "\n");

			con.setFocusable(true);
			con.requestFocus();
		}

		//help 버튼
		else if (e.getSource() == mb2) {
			help_dlg.setVisible(true);
		}
		
		//친구추가 버튼
		else if(e.getSource() == mb3) {	// 친구목록 아이콘을 눌렀을 때
			naver.setVisible(true);	
			naverData(mbi);
		}else if(e.getSource()==naver.search) {
			naver.dlg.setVisible(true);
			naver.dlg_ta.setText(naver.ddg);
		}else if (e.getSource()==naver.co_bt) {
			naver.dlg_co.setVisible(true);
			
		}else if (e.getSource()==naver.ok_bt) {
			//입력한 애들이 / 처음 친구추천 창 텍스트에어리어에 뜨게 하고 종료하기
			naver.dlg_co.setVisible(false);
			String memo1 = naver.dlg_co_center_left_ta.getText();
			String memo2 = naver.dlg_co_center_right_ta.getText();
			naver.ta.append(memo1+"\t");
			naver.ta.append(memo2);
			naver.ta.append("\n");
			naver.clearText();
		}

		// 게임
		else if (e.getSource() == game_dlg.bt1) { // 가위바위보 게임버튼
			rock_dlg.clearScore(); // 가위바위보 게임세팅 초기화
			rock_dlg.setVisible(true);
		} else if (e.getSource() == rock_dlg.bt6) { // 가위바위보 자랑하기
			int rank = rock_dlg.sendRank(); // 현재 가위바위보 랭크정보 가져오기
			gameDuduSend(gr, rank); // 서버에 rank 데이터 보내기

		} else if (e.getSource() == game_dlg.bt2) { // 화석찾기 게임버튼
			zero_dlg.clearBoomb(); // 화석찾기 게임세팅 초기화
			zero_dlg.clearScore(); // 화석찾기 게임세팅 초기화
			zero_dlg.setVisible(true);

		} else if (e.getSource() == zero_dlg.rank_jbt) { // 화석찾기 자랑하기
			int rank = zero_dlg.sendRank(); // 현재 화석찾기 랭크정보 가져오기
			gameDuduSend(gz, rank); // 서버에 rank 데이터 보내기

		} else if (e.getSource() == game_dlg.bt3) { // 두더지 게임버튼
			dudu_dlg.clearScore();
			dudu_dlg.setVisible(true);

		} else if (e.getSource() == dudu_dlg.rank_jbt) { // 두더지 자랑하기
			int rank = dudu_dlg.sendRank(); // 두더지의 랭크정보 가져오기
			gameDuduSend(gd, rank);

		// 회원가입
		} else if (e.getSource() == login_dlg.join_jbt) {
			join_dlg.setVisible(true);
		} else if (e.getSource() == join_dlg.ok_jbt || e.getSource() == join_dlg.color_jtf) {
			login = "0";// (미접속)
			String chara = join_dlg.getChara();
			id = join_dlg.getID();
			String pw = join_dlg.getPW();
			String name = join_dlg.getName();
			String mbti = join_dlg.getMbti();
			String hobby = join_dlg.getHobby();
			String food = join_dlg.getFood();
			String color = join_dlg.getColor();
			String memo1 = "0";
			String memo2 = "0";
			String memo3 = "0";
			String memo4 = "0";
			String memo5 = "0";

			if (chara.equals("0")) {
				JOptionPane.showMessageDialog(null, "캐릭터를 선택 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (id.length() == 0) {
				JOptionPane.showMessageDialog(null, "아이디를 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (pw.length() == 0) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (!checkInputOnlyNumberAndAlphabet(pw)) {
				JOptionPane.showMessageDialog(null, "비밀번호를 영문으로 입력해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (name.length() == 0) {
				JOptionPane.showMessageDialog(null, "이름을 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (mbti.length() == 0) {
				JOptionPane.showMessageDialog(null, "MBTI를 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (hobby.length() == 0) {
				JOptionPane.showMessageDialog(null, "취미를 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (food.length() == 0) {
				JOptionPane.showMessageDialog(null, "좋아하는 음식을 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (color.length() == 0) {
				JOptionPane.showMessageDialog(null, "좋아하는 색깔을 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else {
				joinData("j@" + login + "@" + chara + "@" + id + "@" + pw + "@" + name + "@" + mbti + "@" + hobby + "@"
						+ food + "@" + color + "@" + memo1 + "@" + memo2 + "@" + memo3 + "@" + memo4 + "@" + memo5);

				JOptionPane.showMessageDialog(null, "가입 되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == join_dlg.cancel_jbt) { // 회원가입 취소
			join_dlg.setVisible(false);

		// 로그인
		} else if (e.getSource() == login_dlg.login_jbt || e.getSource() == login_dlg.pw_jtf) {
			id = login_dlg.id_jtf.getText().trim(); // 로그인창에서 id입력한거
			String pw = login_dlg.pw_jtf.getText().trim(); // 로그인창에서 pw입력한거
			if (id.length() == 0) {
				JOptionPane.showMessageDialog(null, "아이디를 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else if (pw.length() == 0) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력 해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			} else {
				joinData("l@" + id + "@" + pw); // login@id@pw 형식으로 전송 (로그인시도)
			}
		}

		// 마이룸
		else if (e.getSource() == myroom_dlg.exit_jbt) { // 마이룸의 나가기 버튼
			myroom_dlg.setVisible(false);

		} else if (e.getSource() == letter_dlg.ok_jbt) { // 마이룸 - 편지쓰기 창 확인
			String letter = bt_num + ':' + letter_dlg.sendText(); // 번호:2#내용 형태의 내용
			System.out.println(letter); // 테스트
			sendData(letter, id); // 서버로 해당 내용 전송
			letter_dlg.setVisible(false);

		} else if (e.getSource() == letter_dlg.cancel_jbt) { // 마이룸 - 편지쓰기 창 취소
			letter_dlg.clearText();
			letter_dlg.setVisible(false);

		} else if (e.getSource() == myroom_dlg.friend_jbt) { // 마이룸의 놀러가기 버튼
			friend_id = JOptionPane.showInputDialog(this, "친구의 id 입력 : ", "입력", JOptionPane.QUESTION_MESSAGE);
			friendData();

		} else if (e.getSource() == view_dlg.bt_exit) { // 마이룸 편지보기 창 확인
			view_dlg.setVisible(false);

		} else if (e.getSource() == friendroom_dlg.exit_jbt) { // 친구룸 나가기 버튼
			friendroom_dlg.setVisible(false);

		} else if (e.getSource() == f_letter_dlg.ok_jbt) { // 친구룸 편지쓰기 창 확인
			String letter = f_bt_num + ':' + f_letter_dlg.sendText(); // 번호:2#내용 형태의 내용
			sendData(letter, friend_id); // 서버로 해당 내용 전송
			f_letter_dlg.setVisible(false);

		} else if (e.getSource() == f_letter_dlg.cancel_jbt) { // 친구룸 편지쓰기 창 취소
			f_letter_dlg.clearText();
			f_letter_dlg.setVisible(false);

		} else if (e.getSource() == f_view_dlg.bt_exit) { // 친구룸 편지읽기 창 나가기
			f_view_dlg.setVisible(false);

		} else {
			// 마이룸의 방명록 버튼을 눌렀을 때
			for (int i = 0; i < 5; ++i) {
				if (e.getSource() == myroom_dlg.mbt[i]) {
					boolean result = myroom_dlg.buttonTrue(i);
					if (result) { // 버튼에 값이 있으면
						String[] data = imfo.split(",");
						view_dlg.setText(data[i + 6]);
						view_dlg.setVisible(true);
					} else if (!result) { // 버튼에 값이 없으면
						int su = i + 1;
						bt_num = String.valueOf(su);
						letter_dlg.setVisible(true);
					}
				// 친구룸 방명록 버튼을 눌렀을 때
				} else if (e.getSource() == friendroom_dlg.mbt[i]) {
					boolean result = friendroom_dlg.buttonTrue(i);
					if (result) {
						String[] data = f_imfo.split(",");
						f_view_dlg.setText(data[i + 6]);
						f_view_dlg.setVisible(true);
					} else if (!result) { // 버튼에 값이 없으면
						int su = i + 1;
						f_bt_num = String.valueOf(su);
						f_letter_dlg.setVisible(true);
					}
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// WASD나 방향키로 이동
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (myCharaLabel.getX() >= -10) {
				myCharaLabel.setX(myCharaLabel.getX() - 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}
		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (myCharaLabel.getX() <= (int) main_jp1.getWidth() - 50) {
				myCharaLabel.setX(myCharaLabel.getX() + 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}
		} else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			if (myCharaLabel.getY() >= 10) {
				myCharaLabel.setY(myCharaLabel.getY() - 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}
		} else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (myCharaLabel.getY() <= (int) main_jp1.getHeight() - 100) {
				myCharaLabel.setY(myCharaLabel.getY() + 10);
				setCharacterLocation(myCharaLabel);
				sendChangePosStr();
			} else {}

			// 스페이스로 건물 입장
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (myCharaLabel.getX() >= myRoom.getX() - 80 && myCharaLabel.getX() <= myRoom.getX() + 80) {
				if (myCharaLabel.getY() >= myRoom.getY() - 100 && myCharaLabel.getY() <= myRoom.getY() + 100) {
					// 마이룸으로 이동
					myroom_dlg.firstSetting(imfo); // 초기 정보로 마이룸을 세팅
					System.out.println("마이룸 입장!!");
					myroom_dlg.setVisible(true);
				}
			} else if (myCharaLabel.getX() >= gameCenter.getX() - 80 && myCharaLabel.getX() <= gameCenter.getX() + 80) {
				if (myCharaLabel.getY() >= gameCenter.getY() - 100 && myCharaLabel.getY() <= gameCenter.getY() + 100) {
					// 게임센터로 이동
					gameDuduRank(gd); // 두더지 랭크 요청 (두더지랭크가 받아지면, 다른게임도 순서대로 가져온다)
					System.out.println("게임센터 입장!!");
					game_dlg.setVisible(true);
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if(e.getSource()==this) {
			introMusic.isLoop = false;
			sendExitStr();
			System.exit(0);
		}
		else if(e.getSource()==login_dlg) {
			introMusic.isLoop = false;
			System.exit(0);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		con.setFocusable(true);
		con.requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}

class MyButton01 extends Button { // button을 상속받음
	private Image img;

	public MyButton01(Image img) {
		this.img = img;
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}