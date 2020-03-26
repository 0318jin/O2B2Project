package o2b2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;

public class Select_RealStudyTime {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	SingleTon s =SingleTon.getInstanse();
	
	
public void loadRealStudyTime() {
	
		s.SelectRealserialnum_singleTon.clear();
		s.SelectRealstudytime_singleTon.clear();
		s.SelectRealdate_singleTon.clear();
		s.SelectRealsubject_singleTon.clear();
			
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		
			stmt = conn.createStatement();
		
			String sql = "SELECT * FROM realstudytime ORDER BY serialnum asc;";
		
			rs = stmt.executeQuery(sql);
		
			while (rs.next()) {
				// 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
				// 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.
				String serialnum = rs.getString(1);
				s.SelectRealserialnum_singleTon.add(rs.getString(1));
				String studytime = rs.getString(2);
				s.SelectRealstudytime_singleTon.add(rs.getString(2));
				String date = rs.getString(3);
				s.SelectRealdate_singleTon.add(rs.getString(3));
				String subject = rs.getString(4);
				s.SelectRealsubject_singleTon.add(rs.getString(4));
		
				s.realstudytimeval = serialnum + " / " + studytime + " / " + date + " / "+ subject + "\n";
				
				
			}
	
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	
	public int loadRealStudyTime(JTextArea txtArea) {
		
		SingleTon s =SingleTon.getInstanse();
		
		String realstudytimeval = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int count =0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		
			stmt = conn.createStatement();
		
			String sql = "SELECT * FROM realstudytime ORDER BY serialnum asc;";
		
			rs = stmt.executeQuery(sql);
		
			while (rs.next()) {
				// 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
				// 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.
				String serialnum = rs.getString(1);
				s.SelectRealserialnum_singleTon.add(rs.getString(1));
				String studytime = rs.getString(2);
				s.SelectRealstudytime_singleTon.add(rs.getString(2));
				String date = rs.getString(3);
				s.SelectRealdate_singleTon.add(rs.getString(3));
				String subject = rs.getString(4);
				s.SelectRealsubject_singleTon.add(rs.getString(4));
		
				realstudytimeval = serialnum + " / " + studytime + " / " + date + " / "+ subject + "\n";
				txtArea.append(realstudytimeval);
				count++;
			}
	
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}
}
