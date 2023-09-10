package miniproject_final;

import java.sql.*;
import java.util.*;

public class NaverDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	String url, user, pass;
	
	public NaverDAO() {
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
	
	
	public List<NaverMember> listNaver() {
		// order by tot asc : 총점으로 오름차순, default 값
		// order by tot desc : 총점으로 내림차순
		String sql = "select * from naver2";
		try {
			System.out.println("리스트되니3324");
			con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			java.util.List<NaverMember> list = new ArrayList<>();
			while(rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				
				NaverMember nm = new NaverMember(id, name);
				list.add(nm);
				
			}
			System.out.println("리스트되니");
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
	
}
