package miniproject_final;

import java.sql.*;
import java.util.*;

public class MemberDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	String url, user, pass;
	
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 오류 발생!!");
			System.exit(0);
		}
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		user = "aws01";
		pass = "aws01";
	}
	
	public int insertMember(Member mb) {
		String sql = "insert into mini0330 values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, mb.getLogin());
			ps.setString(2, mb.getChara());
			ps.setString(3, mb.getID());
			ps.setString(4, mb.getPW());
			ps.setString(5, mb.getName());
			ps.setString(6, mb.getMbti());
			ps.setString(7, mb.getHobby());
			ps.setString(8, mb.getFood());
			ps.setString(9, mb.getColor());
			ps.setString(10, mb.getMemo1());
			ps.setString(11, mb.getMemo2());
			ps.setString(12, mb.getMemo3());
			ps.setString(13, mb.getMemo4());
			ps.setString(14, mb.getMemo5());
			int res = ps.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch(SQLException e) {}
		}
		return 0;
	}
	
	public List<Member> listMember() {//회원리스트 보여주기
		String sql = "select * from mini0330";
		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			java.util.List<Member> list = new ArrayList<>();
			while(rs.next()) {
				String login = rs.getString("login");
				String chara = rs.getString("chara");
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String mbti = rs.getString("mbti");
				String hobby = rs.getString("hobby");
				String food = rs.getString("food");
				String color = rs.getString("color");
				String memo1 = rs.getString("memo1");
				String memo2 = rs.getString("memo2");
				String memo3 = rs.getString("memo3");
				String memo4 = rs.getString("memo4");
				String memo5 = rs.getString("memo5");
				Member mb = new Member(login, chara, id, pw, name, mbti, hobby, food, color, memo1, memo2, memo3, memo4, memo5);
				list.add(mb);
			}
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	public Member getMember(String id) {
		try {	
			String sql = "select * from mini0330 where id=?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {	
				String login = rs.getString("login");
				String chara = rs.getString("chara");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String mbti = rs.getString("mbti");
				String hobby = rs.getString("hobby");
				String food = rs.getString("food");
				String color = rs.getString("color");
				String memo1 = rs.getString("memo1");
				String memo2 = rs.getString("memo2");
				String memo3 = rs.getString("memo3");
				String memo4 = rs.getString("memo4");
				String memo5 = rs.getString("memo5");
				Member mb = new Member(login, chara, id, pw, name, mbti, hobby, food, color, memo1, memo2, memo3, memo4, memo5);
				return mb;
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	
	public int updateMemo(Member mb) { //방명록 업데이트 할 때
		try {
			String sql = "update mini0330 set  memo1=?, memo2=?, memo3=?, memo4=?, memo5=? where id=?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(6, mb.getID());
			ps.setString(1, mb.getMemo1());
			ps.setString(2, mb.getMemo2());
			ps.setString(3, mb.getMemo3());
			ps.setString(4, mb.getMemo4());
			ps.setString(5, mb.getMemo5());
			int res = ps.executeUpdate();
			return res;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}
		return 0;
	}
	public int updateMember (Member mb) {//getMember > 회원정보수정
		try {
			String sql = "update mini0330 set mbti=?, hobby=?, food=?, color=? where id=?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, mb.getMbti());
			ps.setString(2, mb.getHobby());
			ps.setString(3, mb.getFood());
			ps.setString(4, mb.getColor());
			ps.setString(5, mb.getID());
			int res = ps.executeUpdate();
			return res;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}
		return 0;
	}
	
	public int updatelogin (Member mb) {//getMember > 로그인여부수정
		try {
			String sql = "update mini0330 set login=? where id=?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, mb.getLogin());
			ps.setString(2, mb.getID());
			int res = ps.executeUpdate();
			return res;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}
		return 0;
	}
	
	public int LogOff (String id) {
		try {
			String sql = "update mini0330 set login=? where id=?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, "0");
			ps.setString(2, id);
			int res = ps.executeUpdate();
			return res;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {}
		}
		return 0;
	}
	
}
