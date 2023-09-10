package miniproject_final;

import java.sql.*;
import java.util.*;

public class GameDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	String url, user, pass;
	
	public GameDAO() {
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
	
	public int insertGame(String game, String id, int tot) {
		String sql = "insert into gametest1 values(?, ?, ?)";
		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, game);
			ps.setString(2, id);
			ps.setInt(3, tot);
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
	// ******************추가
	public List<Game> listGame(String game) {
		System.out.println("꺼내러옴");
		String sql = "select game, id, tot from gametest1 where game=? order by game, tot desc";
		try {
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, game);
			rs = ps.executeQuery();
			java.util.List<Game> list = new ArrayList<>();
			while(rs.next()) {
//				String game = rs.getString("game");
				String id = rs.getString("id");
				Integer tot = rs.getInt("tot");
				Game gd = new Game(game, id, tot);
				System.out.println("전송완료");
				list.add(gd);
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
	
	public Game getGame(String game) {
		System.out.println("꺼내러옴");
		String sql = "select * from gametest1 where game=?";
		try {	
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, game);
			rs = ps.executeQuery();
			if (rs.next()) {	
				//String id = rs.getString("id");
				String id = rs.getString("id");
				int tot = rs.getInt("tot");
				Game gd = new Game(game, id, tot);
				System.out.println("전송완료");
				return gd;
			}else {
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
	public int updateGame (Game game) {
		try {
			String sql = "update gametest1 set id=?, tot=? where game=?";
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			ps.setString(1, game.getID());
			ps.setInt(2, game.getTot());
			ps.setString(3, game.getGame());
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

