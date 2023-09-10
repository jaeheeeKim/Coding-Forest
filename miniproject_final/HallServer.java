package miniproject_final;

import java.net.*;
import java.io.*;
import java.util.*;

public class HallServer {
	private MemberDAO dao;
	private GameDAO GameDAO;
	private NaverDAO NaverDAO;

	private ServerSocket ss;
	private Socket soc;
	private PrintWriter pw;
	private BufferedReader br;

	private Hashtable<String, Socket> ht_soc; // <닉네임, 소켓>
	private Hashtable<String, String> ht_pos = new Hashtable<>(); // <닉네임, x좌표:y좌표:캐릭터번호>

	// 랜덤 좌표에 생성시, 입장 받을 때 좌표도 #name#x좌표#y좌표 로 한번에 받아야함.
	// 입장 보내는 메소드 #name#캐릭터 번호 #x좌표#y좌표 (이건 디폴트나 랜덤으로 할 시 생략 가능)
	// 받는 쪽에서 입장 출력 뒤에 캐릭터 생성.(디폴트 위치에)

	public HallServer() {
		dao = new MemberDAO();
		GameDAO = new GameDAO();
		NaverDAO = new NaverDAO();

		try {
			ss = new ServerSocket(12345);
			ht_soc = new Hashtable<>();

			while (true) {
				soc = ss.accept();
				OnlineClient oc = new OnlineClient(soc);
				oc.start();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	// 마이룸&게임 등 클라이언트에게 정보를 보낼 때
	public void sendData(Socket soc, String str) throws Exception {
		pw = new PrintWriter(soc.getOutputStream(), true);
		pw.println(str);
		pw.flush();
	}

	// 게임을 랭킹순으로 가져오기 위한 메소드
	public String view(String game) {
		String str = "";
		List<Game> list = GameDAO.listGame(game);
		for (Game g : list) {
			String text = '@' + g.getGame() + '#' + g.getID() + '#' + g.getTot();
			str = str + text;
		}
		return str;
	}
	
	//친구추가 창 메소드
	public String memberView() { 
		System.out.println("멤버리스트 받아옴");
		String str = "";
		List<Member> list = dao.listMember();
		for(Member g : list) {
			String text = 'm' + g.getID() + " - " + g.getName();
			System.out.println(text);
			str = str + text;
			System.out.println(str);
		}
		return str;
	}
		
	//친구추가창 메소드
	public void memberData(Socket soc, String str) throws Exception{	
		pw = new PrintWriter(soc.getOutputStream(),true);
		pw.println(str);
		pw.flush();	
	}

//-------------------------------------------------------------------------------------------------------------------------------------------
	// 클라이언트 개개인에게서 텍스트를 수신
	class OnlineClient extends Thread {
		Socket soc;

		Member mb; // 본인
		Member mb2; // 친구
		Game game;

		OnlineClient(Socket soc) {
			this.soc = soc;
		}

		@Override
		public void run() {
			while (true) {
				try {
					br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
					String str = br.readLine();
					if (str == null)
						break;
					if (str.charAt(0) == '$') {// 기존 캐릭터 위치변화
						sendText(str);

						String[] arr = str.substring(1).split(":"); // 이름:x좌표:y좌표:캐릭터번호
						String name = arr[0];
						int xpos = Integer.parseInt(arr[1]);
						int ypos = Integer.parseInt(arr[2]);
						int charNum = Integer.parseInt(arr[3]);

						ht_pos.put(name, xpos + ":" + ypos + ":" + charNum);
					} else if (str.charAt(0) == '#') {// 새로운 캐릭터 접속
						String[] arr = str.substring(1).split(":"); // 이름:x좌표:y좌표:캐릭터번호
						String name = arr[0];
						int xpos = Integer.parseInt(arr[1]);
						int ypos = Integer.parseInt(arr[2]);
						int charNum = Integer.parseInt(arr[3]);

						// 서버 목록에 추가
						ht_soc.put(name, soc);
						ht_pos.put(name, xpos + ":" + ypos + ":" + charNum);

						// 해당 클라이언트에게 현재 접속자 정보 전송
						pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
						Enumeration<String> enu = ht_pos.keys();
						while (enu.hasMoreElements()) {
							String nicks = enu.nextElement();
							if (nicks.equals(name))
								continue;
							pw.println("^" + nicks + ":" + ht_pos.get(nicks)); // ^상대닉네임:x좌표:y좌표:캐릭터번호
							pw.flush();
						}

						sendTextAll(str);
					} else if (str.charAt(0) == '%') {// 클라이언트 종료
						sendText(str);
						ht_soc.remove(str.substring(1));
						ht_pos.remove(str.substring(1));
						dao.LogOff (str.substring(1));
					} else if (str.charAt(0) == '!') {// 채팅을 받음
						sendText(str);
					}
					
					//회원가입&로그인
					else if (str.charAt(0) == 'j') { // 회원가입시 join@login@형태로 받기
						String[] join = str.split("@");
						mb = new Member(join[1], join[2], join[3], join[4], join[5], join[6], join[7], join[8], join[9],
								join[10], join[11], join[12], join[13], join[14]);
						int res = dao.insertMember(mb);
						if (res > 0) {
							System.out.println(mb.getID() + "님을 DB에 등록하였습니다.");
							sendData(soc, "가입성공");

							List<Member> list = dao.listMember();
							for (int i = 0; i < list.size(); i++) {
								String[] data = list.get(i).toString().split("/");
							}
						} else {
							System.out.println(mb.getID() + "님을 DB에 등록 중 오류 발생");
						}
					} else if (str.charAt(0) == 'l') { // 로그인시도 login@id@pw
						String[] login = str.split("@");
						String id = login[1];
						String pw = login[2];

						String login_ok = "";

						Member member = dao.getMember(id);

						if (member == null) {
							sendData(soc, "로그인아이디오류");
						} else {
							if (id.equals(member.getID()) && pw.equals(member.getPW())) {
								if (member.getLogin().equals("0")) {
									System.out.println(id + "," + member.getID() + "," + pw + "," + member.getPW() + ","+ member.getLogin());
									login_ok = "성공";
									Member login_update = dao.getMember(id);
									login_update.setLogin("1");
									int res = dao.updatelogin(login_update);
									if (res > 0) {
										System.out.println(id + "님이 접속하셨습니다.");
										sendData(soc, "로그인성공"+"@"+login_update.getLogin()+"@"+login_update.getChara()+"@"+login_update.getID()
										+"@"+login_update.getPW()+"@"+login_update.getName()+"@"+login_update.getMbti()+"@"+login_update.getHobby()
										+"@"+login_update.getFood()+"@"+login_update.getColor()+"@"+login_update.getMemo1()+"@"+login_update.getMemo2()
										+"@"+login_update.getMemo3()+"@"+login_update.getMemo4()+"@"+login_update.getMemo5());
									} else {
										System.out.println(id + "님 접속시 오류 발생!");
									}
								}else {
									System.out.println("이미 접속중");
									sendData(soc, "이미 접속중");
								}	
							} else {
								System.out.println("비밀번호 오류");
								sendData(soc, "로그인비번오류");
							}
						}
					
					// 게임 
					} else if (str.charAt(0) == 'g') { // 게임 랭킹 정보요청을 받을 때
						str = str.substring(1);

						if (str.equals("dudu")) {
							String text = view("dudu");
							System.out.println(text);
							sendData(soc, text);
							System.out.println("두두게임데이터전송완료");
						}

						else if (str.equals("rock")) {
							String text = view("rock");
							System.out.println(text);
							sendData(soc, text);
							System.out.println("락게임데이터전송완료");
						}

						else if (str.equals("zero")) {
							String text = view("zero");
							sendData(soc, text);
							System.out.println("제로게임데이터전송완료");
						}

					} else if (str.charAt(0) == '.') { // 자랑하기로 게임 점수를 받을 때
						str = str.substring(1); // gr.이름.점수
						String[] data = str.split(",");
						String game = data[0];
						String id = data[1];
						int tot = Integer.parseInt(data[2]);
						GameDAO.insertGame(game, id, tot);
						sendData(soc, "게임");
					
					//마이룸
					} else if (str.charAt(0) == ':') { // 방명록 작성으로 DB 업데이트가 필요할 때
						str = str.substring(1); // :빼고
						String[] letter = str.split(":");
						mb = dao.getMember(letter[0]);

						if (letter[1].equals("1")) {
							mb.setMemo1(letter[2]);
						} else if (letter[1].equals("2")) {
							mb.setMemo2(letter[2]);
						} else if (letter[1].equals("3")) {
							mb.setMemo3(letter[2]);
						} else if (letter[1].equals("4")) {
							mb.setMemo4(letter[2]);
						} else {
							mb.setMemo5(letter[2]);
						}
						int res = dao.updateMemo(mb);
						if (res > 0) {
							System.out.println("방명록 수정완료");
							String send = "성공:" + letter[0];
							sendData(soc, send);
						} else {
							System.out.println("방명록 수정실패");
						}
						
					//친구추가
					} else if (str.charAt(0) == 'm') {//멤버인포메이션
						System.out.println("왔다");
						String ttt = memberView();
						memberData(soc, ttt);
						System.out.println("게임데이터전송완료");
					} else if (str.charAt(0) == ',') { // 정보를 보내줘야 할 때
						if (str.charAt(1) == ',') { // 친구 데이터
							str = str.substring(2);
							mb = dao.getMember(str); // id에 해당하는 member
							String text = ",," + mb.getID() + ',' + mb.getChara() + ',' + mb.getMbti() + ','
									+ mb.getHobby() + ',' + mb.getFood() + ',' + mb.getColor() + ',' + mb.getMemo1()
									+ ',' + mb.getMemo2() + ',' + mb.getMemo3() + ',' + mb.getMemo4() + ','
									+ mb.getMemo5();
							sendData(soc, text);
							System.out.println("친구 데이터 전송완료");
						} else { // 내 데이터
							str = str.substring(1);
							mb = dao.getMember(str); // id에 해당하는 member
							String text = ',' + mb.getID() + ',' + mb.getChara() + ',' + mb.getMbti() + ','
									+ mb.getHobby() + ',' + mb.getFood() + ',' + mb.getColor() + ',' + mb.getMemo1()
									+ ',' + mb.getMemo2() + ',' + mb.getMemo3() + ',' + mb.getMemo4() + ','
									+ mb.getMemo5();
							sendData(soc, text);
							System.out.println("업데이트된 데이터 전송완료");
						}

					}

				} catch (Exception e) {e.getStackTrace();}

			}
		}
	}
	
//-------------------------------------------------------------------------------------------------------------------------------------------
	// 받은 텍스트를 보낸 놈 빼고 다 보내준다.
	private void sendText(String str) throws Exception {
		String[] arr = str.substring(1).split(":"); // 이름:x좌표:y좌표:캐릭터번호
		String nick = arr[0];
		Enumeration<String> enu = ht_soc.keys();
		while (enu.hasMoreElements()) {
			String nicks = enu.nextElement();
			if (nicks.equals(nick))
				continue; // 정보를 보낸 클라이언트는 제외
			Socket s = ht_soc.get(nicks);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
			pw.println(str);
			pw.flush();
		}
	}

	// 받은 텍스트를 나 포함 모두에게 보내준다.
	private void sendTextAll(String str) throws Exception {
		Enumeration<String> enu = ht_soc.keys();
		while (enu.hasMoreElements()) {
			String nicks = enu.nextElement();
			Socket s = ht_soc.get(nicks);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
			pw.println(str);
			pw.flush();
		}
	}

}