package dbtest;

import java.sql.*;
import java.util.Scanner;

public class DBTest_Insert_time {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String serialNum = null;
		String studytime = null;
		String date = null;
		String subject = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("�ø����ȣ �Է�: ");
		serialNum = sc.next();
		System.out.println("�������νð� �Է�: ");
		studytime = sc.next();
		System.out.println("��¥ �Է�: ");
		date = sc.next();
		System.out.println("���� �Է�: ");
		subject = sc.next();
		
		
		insert(serialNum, studytime, date, subject);
	}
		
		public static void insert(String serialnum, String studytime, String date, String subject) {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/o2b2", "root", "1234");
			
			String sql = "INSERT INTO RealStudyTime VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, serialnum);
			pstmt.setString(2, studytime);
			pstmt.setString(3, date);
			pstmt.setString(4, subject);
			
			int count = pstmt.executeUpdate();
			if (count == 0) {
				System.out.println("������ �Է� ����");
			}else {
				System.out.println("������ �Է� ����");
			}
			
			

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
