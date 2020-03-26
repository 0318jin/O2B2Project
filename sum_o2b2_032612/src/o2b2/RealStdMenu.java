package o2b2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import o2b2.JPanel01.JPanelTest;

class JPanel04 extends JPanel {

	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;

	private JLabel jlabel1;

	private JTextField jtextfield1;

	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	SingleTon s =SingleTon.getInstanse();
	JComboBox<String> c = new JComboBox();
	JTable table;
	
	static final int TABLE_MENU = 0;
	
	static final int TABLE_SERIALNUM = 0;
	static final int TABLE_PHONENUM = 1;
	static final int TABLE_DATE = 2;
	static final int TABLE_SUBJECT = 3;
    final String[] columnType = {"시리얼넘버", "공부시간", "날짜", "과목수"};
    static boolean DBGTest = false;
    
//    ResultPanel Rp;
	
	
	////////////////	콤보박스에 값 넣기
	void combo_loadData() {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		 ImageIcon img1 = new ImageIcon("image/book3.png");  //이미지 경로
		    JLabel imagelJLabel1 = new JLabel (img1);     
		    imagelJLabel1.setSize(200,200);
		    imagelJLabel1.setLocation(65,90);
		    add(imagelJLabel1);
	
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
		
				s.realstudytimeval = serialnum + "/" + studytime + "/" + date + "/"+ subject + "\n";
				c.addItem(s.realstudytimeval);
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
		}add(c);
	}
	////////////     삭제할 데이터 선택
	void select_del() {
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (c.getSelectedItem().toString() != null) {
					s.Delete_rst = c.getSelectedItem().toString();
				}
				
				
			}
		});
	}
	////////////////	콤보박스 만들기
	void makeCombo() { // select 박스
		
		c.setSize(300, 40);
		c.setLocation(300, 680);
		c.setFont(new Font("나눔고딕",Font.BOLD,20));
		combo_loadData();
		select_del();

	}

	public JPanel04(JPanelTest win) {

		SingleTon s = SingleTon.getInstanse();
		
		ImageIcon img = new ImageIcon("image/oba-study2.jpg"); // 이미지 경로
		JLabel imagelJLabel = new JLabel(img);
		imagelJLabel.setSize(170, 60);
		imagelJLabel.setLocation(1000, 650);

		add(imagelJLabel);
		
		ImageIcon img1 = new ImageIcon("image/real1.png");  //이미지 경로
	    JLabel imagelJLabel1 = new JLabel (img1);     
	    imagelJLabel1.setSize(300,60);
	    imagelJLabel1.setLocation(400,42);
	    
	    add(imagelJLabel1);
		setVisible(true); // 화면에 보이기

		makeCombo();

		setLayout(null);
		///////////////	창 진입시 바로 표가 뜨게 하는 부분.
		uploadJtable();
		
		
		jButton1 = new JButton(new ImageIcon("image/UP.PNG"));
		jButton1.setSize(170, 40);
		jButton1.setLocation(65, 310);
		//
		jButton1.setBorderPainted(false);
	     //JButton의 Border(외곽선)을 없애준다.   
		jButton1.setContentAreaFilled(false);
	     //JButton의 내용영역 채루기 안함
		jButton1.setFocusPainted(false);
		add(jButton1);
		//
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				////
				//Rp = new ResultPanel(e.getActionCommand());
				////////
				jTextArea1.setText("");
				Select_RealStudyTime mR = new Select_RealStudyTime();
				int countnum = mR.loadRealStudyTime(jTextArea1);
				uploadJtable();
				if(countnum == 1)
				{
					System.out.println("count num ==1");
//					makeCombo();
				}
			}
		});
		//

		jButton2 = new JButton(new ImageIcon("image/inbtn.PNG"));
		jButton2.setSize(170, 40);
		jButton2.setLocation(65, 580);
		add(jButton2);
		//
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				try {
					s.get_textfield_realstudy = jtextfield1.getText();
					System.out.println(s.get_textfield_realstudy);
					jTextArea1.append(s.get_textfield_realstudy+"\n");
					jtextfield1.setText(null);
					String[] array = s.get_textfield_realstudy.split("/");
					String serialNum = array[0];
					String studytime = array[1];
					String date = array[2];
					String subject = array[3];

					Insert_RealStudyTime.insert(serialNum, studytime, date, subject, jTextArea1);
					c.addItem(s.get_textfield_realstudy);
					//
//					c.revalidate();
//					c.repaint(); 
					
				} catch (Exception e2) {
					// TODO: handle exception
					jTextArea1.append("실패했습니다. 값을 다시 한번 확인해 주세요.");
					JOptionPane.showMessageDialog(null, "실패했습니다. 값을 확인해주세요", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
				//
				}
		});
		//

		jButton3 = new JButton(new ImageIcon("image/DL.PNG"));
		jButton3.setSize(170, 40);
		jButton3.setLocation(65, 490);
		add(jButton3);

		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextArea1.setText("");
//				c.removeAllItems();
//				combo_loadData();
				select_del();
				System.out.println(s.Delete_rst);
				if(s.Delete_rst != null) {
					String[] array = s.Delete_rst.split("/");
					String serialNum = array[0];
					String studytime = array[1];
					String date = array[2];
					String subject = array[3];
	
					Delete_RealStudyTime.delete(serialNum, studytime, date, subject, jTextArea1);
					/////////////////////
					c.removeItem(s.Delete_rst);
					
					if(c.getItemAt(0) ==null)
					{
						System.out.println("c.removeAllItems()");
//						c.removeAllItems();
					}
				}
				else {
					System.out.println("삭제할 값이 없습니다.");
					JOptionPane.showMessageDialog(null, "삭제할 값이 없습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					c.removeAll();
//					remove(c);
				}
			}
		});
		//

		jButton4 = new JButton(new ImageIcon("image/clbtn.PNG"));
		jButton4.setSize(170, 40);
		jButton4.setLocation(65, 400);
		add(jButton4);
		jButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextArea1.setText("");
			}
		});
		//
		///////안보이게 Location옮김
		jTextArea1 = new JTextArea();
		jScrollPane1 = new JScrollPane(jTextArea1);
		jScrollPane1.setSize(300, 320);
		jScrollPane1.setLocation(2300, 120);
		jTextArea1.setFont(new Font("나눔고딕",Font.BOLD,20));
		add(jScrollPane1);

		jtextfield1 = new JTextField();
		jtextfield1.setSize(300, 40);
		jtextfield1.setLocation(700, 680);
		jtextfield1.setFont(new Font("나눔고딕",Font.BOLD,20));
		add(jtextfield1);
	}
		void uploadJtable() {
	    	Select_RealStudyTime sP = new Select_RealStudyTime();
//	    	sP.initValue();
	    	sP.loadRealStudyTime();
	        //Object[][] pro_obj= new Object[s.serialnum_singleTon.size()+1][3];
	        Object[][] real_obj= new Object[s.SelectRealserialnum_singleTon.size()+2][4];
//	        table = new JTable(pro_obj,columnType);

	        int count =0;

	        System.out.println("columnType.length : "+columnType.length);
	        for(int i=0; i<columnType.length; i++)
	        {
	        	real_obj[TABLE_MENU][i] = columnType[i];
	        }

	        for (int i = 0; i < s.SelectRealserialnum_singleTon.size(); i++) {
	        	real_obj[i+1][TABLE_SERIALNUM]=s.SelectRealserialnum_singleTon.get(i);
	        	real_obj[i+1][TABLE_PHONENUM]=s.SelectRealstudytime_singleTon.get(i);
	        	real_obj[i+1][TABLE_DATE]=s.SelectRealdate_singleTon.get(i);
	        	real_obj[i+1][TABLE_SUBJECT]=s.SelectRealsubject_singleTon.get(i);
	        	//count++;
	        	System.out.println("s.serialnum_singleTon.get(i) : " + s.SelectRealserialnum_singleTon.get(i));
	        };
//	        count++;
//	        if(DBGTest == false) {
//		        for(int i=0; i<columnType.length; i++)
//		        {
//		        	pro_obj[count][i] = columnType[i];
//		        }
//		        DBGTest = true;
//	        }
			if(table != null)
			{
				System.out.println("table != null");
				remove(table);
		        revalidate();
		        repaint();
			}
	        table = new JTable(real_obj,columnType);
//			JTable table = new JTable(pro_obj,columnType);
	        table.setRowHeight(25);
	        table.setSize(450,500);
	        table.setLocation(650,120);
	        table.setFont(new Font("나눔고딕",Font.BOLD,20));
	        add(table);
	        revalidate();
	        repaint();
	        
	        
	    }
		
		

}

