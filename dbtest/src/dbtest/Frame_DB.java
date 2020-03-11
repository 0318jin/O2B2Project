package dbtest;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Frame_DB extends JFrame {
   // �������� ����ϱ� static String sst = null;
   public Frame_DB() {
      
	   
      
      setTitle("o2b2 DB");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container contentPane = getContentPane();
 
      
      contentPane.setLayout(null);
      
      
      JLabel la1 = new JLabel("ȸ������");
      la1.setLocation(320,30);
      la1.setSize(200,20);
      contentPane.add(la1);
      
      JLabel la2 = new JLabel("���� �н� �ð�");
      la2.setLocation(305,185);
      la2.setSize(200,50);
      contentPane.add(la2);
      
      JLabel la3 = new JLabel("��ȹ �н� �ð�");
      la3.setLocation(305,370);
      la3.setSize(200,20);
      contentPane.add(la3);
      
      ////////////    JTextArea    /////////////////
      
      
      JTextArea txta1 = new JTextArea();
      JScrollPane scroll1 = new JScrollPane(txta1);
      scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      scroll1.setBounds(190, 60, 300, 100);
      contentPane.add(scroll1);
      
      JTextArea txta2 = new JTextArea();
      JScrollPane scroll2 = new JScrollPane(txta2);
      scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      scroll2.setBounds(190,235, 300, 100);
      contentPane.add(scroll2);
      
      JTextArea txta3 = new JTextArea();
      JScrollPane scroll3 = new JScrollPane(txta3);
      scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      scroll3.setBounds(190,410, 300, 100);
      contentPane.add(scroll3);
      
//      JTextArea txta4 = new JTextArea();
//      JScrollPane scroll4 = new JScrollPane(txta4);
//      scroll4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//      scroll4.setBounds(660,60, 90, 80);
//      contentPane.add(scroll4);
      
      
      ////////////    Button    /////////////////
      
      JButton btn_selP = new JButton("ȸ������");
      btn_selP.setLocation(40,60);
      btn_selP.setSize(120,80);
      contentPane.add(btn_selP);
      
      btn_selP.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
            Select_Profile mP = new Select_Profile();
            mP.loadProfile(txta1);
         }
      });
      
      JButton btn_selR = new JButton("���� �н� �ð�");
      btn_selR.setLocation(40,235);
      btn_selR.setSize(120,80);
      contentPane.add(btn_selR);
      
      btn_selR.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

        	 Select_RealStudyTime mR = new Select_RealStudyTime();
             mR.loadProfile(txta2);
         }
      });
      
      JButton btn_selS = new JButton("��ȹ �н��ð�");
      btn_selS.setLocation(40,410);
      btn_selS.setSize(120,80);
      contentPane.add(btn_selS);
      btn_selS.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
        	 Select_ScheduleStudyTime mS = new Select_ScheduleStudyTime();
             mS.loadProfile(txta3);
         }
      });
      
      JButton btn_insP = new JButton("ȸ������ �ֱ�");
      btn_insP.setLocation(520,60);
      btn_insP.setSize(120,80);
      contentPane.add(btn_insP);
      btn_insP.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
        	String serialNum = null;
       		String phoneNum = null;
       		String password = null;
       		
        	 Insert_Profile.insert( serialNum, phoneNum, password);
        	
         }
      });
      
      JButton btn_insR = new JButton("�����н� �ֱ�");
      btn_insR.setLocation(520,150);
      btn_insR.setSize(120,80);
      contentPane.add(btn_insR);
      btn_insR.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
        	String serialNum = null;
     		String studytime = null;
     		String date = null;
     		String subject = null;
     		
        	Insert_RealStudyTime.insert(serialNum, studytime, date, subject);

         }
      });
      
      JButton btn_insS = new JButton("��ȹ�н� �ֱ�");
      btn_insS.setLocation(520,240);
      btn_insS.setSize(120,80);
      contentPane.add(btn_insS);
      btn_insS.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	
        	String sst = "2,3,2020-02-11,3";
        	String[] array = sst.split(",");
        	String serialNum = array[0];
      		String studytime = array[1];
      		String date = array[2];
      		String subject = array[3];
      		
         	Insert_ScheduleStudyTime.insert(serialNum, studytime, date, subject);

         }
      });
      
      JButton btn_delP = new JButton("ȸ������ ����");
      btn_delP.setLocation(645,60);
      btn_delP.setSize(120,80);
      contentPane.add(btn_delP);
      btn_delP.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 
        	 String delphonenum = null;
        	 String delpassword = null;S
        	 Delete_Profile.delete(delphonenum, delpassword);

         }
      });
      
      JButton btn_delR = new JButton("�����н� ����");
      btn_delR.setLocation(645,150);
      btn_delR.setSize(120,80);
      contentPane.add(btn_delR);
      btn_delR.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 

         }
      });
      
      JButton btn_delS = new JButton("��ȹ�н� ����");
      btn_delS.setLocation(645,240);
      btn_delS.setSize(120,80);
      contentPane.add(btn_delS);
      btn_delS.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 

         }
      });
      
      
      
      
      JButton btn_clr = new JButton("�� �ʱ�ȭ");
      btn_clr.setLocation(650,380);
      btn_clr.setSize(100,50);
      contentPane.add(btn_clr);
      btn_clr.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            txta1.setText("");
            txta2.setText("");
            txta3.setText("");
         }
      });
      
      JButton btn_exit = new JButton("����");
      btn_exit.setLocation(650,450);
      btn_exit.setSize(100,50);
      contentPane.add(btn_exit);
      btn_exit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      
      /////////////////////////////////////////////////////////
      setSize(800,600);
      setVisible(true);
      
      
   }
   public static void main(String[] args) {
      new Frame_DB();
   }
}

////================= profile �� ��������==============================
//class Profile{
//	public void loadProfile(JTextArea txtArea) {
//		String profileval = null;
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//	
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/o2b2", "root", "1234");
//		
//			stmt = conn.createStatement();
//		
//			String sql = "SELECT * FROM profile;";
//		
//			rs = stmt.executeQuery(sql);
//		
//			while (rs.next()) {
//				// ���ڵ��� Į���� �迭�� �޸� 0���� �������� �ʰ� 1���� �����Ѵ�.
//				// �����ͺ��̽����� �������� �������� Ÿ�Կ� �°� getString �Ǵ� getInt ���� ȣ���Ѵ�.
//				String serialnum = rs.getString(1);
//				String phonenum = rs.getString(2);
//				String password1 = rs.getString(3);
//		
//				profileval = serialnum + " / " + phonenum + " / " + password1 + " "+ "\n";
//				txtArea.append(profileval);
//				
//			}
//	
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			try {
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//				if (stmt != null && !stmt.isClosed()) {
//					stmt.close();
//				}
//				if (rs != null && !rs.isClosed()) {
//					rs.close();
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//}
//
////================= RealStudyTime �� ��������==============================
//class RealStudyTime{
//	public void loadProfile(JTextArea txtArea) {
//		String realstudytimeval = null;
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//	
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/o2b2", "root", "1234");
//		
//			stmt = conn.createStatement();
//		
//			String sql = "SELECT * FROM realstudytime;";
//		
//			rs = stmt.executeQuery(sql);
//		
//			while (rs.next()) {
//				// ���ڵ��� Į���� �迭�� �޸� 0���� �������� �ʰ� 1���� �����Ѵ�.
//				// �����ͺ��̽����� �������� �������� Ÿ�Կ� �°� getString �Ǵ� getInt ���� ȣ���Ѵ�.
//				String serialnum = rs.getString(1);
//				String studytime = rs.getString(2);
//				String date = rs.getString(3);
//				String subject = rs.getString(4);
//		
//				realstudytimeval = serialnum + " / " + studytime + " / " + date + " / "+ subject + "\n";
//				txtArea.append(realstudytimeval);
//				
//			}
//	
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			try {
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//				if (stmt != null && !stmt.isClosed()) {
//					stmt.close();
//				}
//				if (rs != null && !rs.isClosed()) {
//					rs.close();
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//}
////================= ScheduleStudyTime �� ��������==============================
//class ScheduleStudyTime{
//	public void loadProfile(JTextArea txtArea) {
//		String schedulestudytimeval = null;
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//	
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/o2b2", "root", "1234");
//		
//			stmt = conn.createStatement();
//		
//			String sql = "SELECT * FROM realstudytime;";
//		
//			rs = stmt.executeQuery(sql);
//		
//			while (rs.next()) {
//				// ���ڵ��� Į���� �迭�� �޸� 0���� �������� �ʰ� 1���� �����Ѵ�.
//				// �����ͺ��̽����� �������� �������� Ÿ�Կ� �°� getString �Ǵ� getInt ���� ȣ���Ѵ�.
//				String serialnum = rs.getString(1);
//				String studytime = rs.getString(2);
//				String date = rs.getString(3);
//				String subject = rs.getString(4);
//		
//				schedulestudytimeval = serialnum + " / " + studytime + " / " + date + " / "+ subject + "\n";
//				txtArea.append(schedulestudytimeval);
//				
//			}
//	
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			try {
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//				if (stmt != null && !stmt.isClosed()) {
//					stmt.close();
//				}
//				if (rs != null && !rs.isClosed()) {
//					rs.close();
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}
//}

////======================== profile Insert �� �ֱ�===============================
//
//class profileInsert{
//
//public static void insert(String serialnum, String phonenum, String password1) {
//	
//	Connection conn = null;
//	PreparedStatement pstmt = null;
//	
//try {
//	Class.forName("com.mysql.jdbc.Driver");
//	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/o2b2", "root", "1234");
//	
//	String sql = "INSERT INTO profile VALUES (?,?,?)";
//	pstmt = conn.prepareStatement(sql);
//
//	pstmt.setString(1, serialnum);
//	pstmt.setString(2, phonenum);
//	pstmt.setString(3, password1);
//	
//	int count = pstmt.executeUpdate();
//	if (count == 0) {
//		System.out.println("������ �Է� ����");
//	}else {
//		System.out.println("������ �Է� ����");
//	}
//	
//	
//
//} catch (Exception e) {
//
//	e.printStackTrace();
//
//} finally {
//	try {
//		if (pstmt != null && !pstmt.isClosed()) {
//			pstmt.close();
//		}
//		if (conn != null && !conn.isClosed()) {
//			conn.close();
//		}
//		
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//}
//}
//}


