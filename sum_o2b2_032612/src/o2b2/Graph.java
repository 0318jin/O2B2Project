package o2b2;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

class ResultPanel extends JPanel{
	SingleTon s = SingleTon.getInstanse();
	private JButton jButton_select;
	static boolean isFirstBoot = true;

	public void paint(Graphics g) {
		
		////	버튼	////////////////////////////////////////////////////////////////
		jButton_select = new JButton("선택");
		jButton_select.setSize(100, 40);
		jButton_select.setLocation(610, 455);
		add(jButton_select);
		////	콤보박스에서 선택된 값 가져오기	////
		select_serial();
		
		//////////////////////////////////////////////////////////////////////
		
		jButton_select.requestFocus();
		
		if(isFirstBoot) {
			makeCombo();
			isFirstBoot =false;
		}else {
			c.requestFocus();
		}
		
		setLayout(null);
		
		g.clearRect(0,0,getWidth(),getHeight());
		g.drawLine(50,  400,  700,  400);
		
		for (int i = 1; i< 11 ; i++) {
			g.drawString(i*2 + "", 25, 400-(35*i));
			g.drawLine(50,  400-(35*i), 700, 400-(35*i));
		}
		
		g.drawLine(50, 20, 50, 400);
		g.setColor(Color.BLUE);
		
		///////////////////////////////////		실제 학습시간 그리기	////////////////////////////////////////////////////////////
		int x_data=70;
		int x_p =65;
		System.out.println("mListGraph : "+ s.mListGraph);
		for(int i=0; i< s.mListGraph.size(); i++)
		{
			g.drawString((i+1) + "일차", x_p, 430);
			g.fillRect(x_data, 400 - s.mListGraph.get(i)*17, 10, s.mListGraph.get(i)*17);
			x_data+=100;
			x_p +=100;
		}
		

		///////////////////////////////////////		계획학습시간 그리기	////////////////////////////////////////////////
		//	계획학습 불러오기
		
		g.setColor(Color.green);
		int x_data1=85;
		System.out.println("mListGraph_Schedule : "+ s.mListGraph_Schedule);
		for(int i=0; i< s.mListGraph_Schedule.size(); i++)
		{
			g.fillRect(x_data1, 400 - s.mListGraph_Schedule.get(i)*17, 10, s.mListGraph_Schedule.get(i)*17);
			x_data1+=100;
		}
//		setVisible(true); // 화면에 보이기
		
		////////////////////////////////////	버튼 액션		///////////////////////////////////////////////////////////
		jButton_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//////	버튼을 눌렀을 때 repaint하여 바로 그래프가 나오도록 하였다.	////
				revalidate();
	            repaint();
				
				loadRealStudyTime_sel();
				loadScheduleStudyTime_sel();
				
				System.out.println("선택된 시리얼 넘버: " + s.select_serial);
				

				ArrayList<Integer> m = new ArrayList<Integer>();
				System.out.println("실제학습 카운트"+s.RealGraph_count);
				System.out.println("실제학습 값"+s.SelectRealstudytime2_singleTon);
				if (s.RealGraph_count < 7 ) {
					for (int i = s.RealGraph_count; i > 0; i--) {
						m.add(Integer.parseInt(s.SelectRealstudytime2_singleTon.get(s.SelectRealstudytime2_singleTon.size() - i)));
					}
				} else {
					for (int i = 7; i > 0; i--) {
						m.add(Integer
								.parseInt(s.SelectRealstudytime2_singleTon.get(s.SelectRealstudytime2_singleTon.size() - i)));
					}
				}

				
				ArrayList<Integer> mSchedule = new ArrayList<Integer>();
				System.out.println("스케쥴학습 카운트"+s.ScheduleGraph_count);
				System.out.println("스케쥴 값"+s.SelectSchedulestudytime2_singleTon);
				if (s.ScheduleGraph_count < 7 ) {
					for (int i = s.ScheduleGraph_count; i > 0; i--) {
						mSchedule.add(Integer.parseInt(s.SelectSchedulestudytime2_singleTon.get(s.SelectSchedulestudytime2_singleTon.size() - i)));
					}
				} else {
					for (int i = 7; i > 0; i--) {
						mSchedule.add(Integer
								.parseInt(s.SelectSchedulestudytime2_singleTon.get(s.SelectSchedulestudytime2_singleTon.size() - i)));
					}
				}
				s.setGraph(m);
				s.setGraph_S(mSchedule);
				setVisible(true); // 화면에 보이기
			}
		});
		
	}
	
	////////		콤보박스 생성부분	////////////
	JComboBox<String> c = new JComboBox();
	void combo_loadData() {
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		
			stmt = conn.createStatement();
		
			String sql = "SELECT * FROM profile ORDER BY serialnum asc;";
		
			rs = stmt.executeQuery(sql);
			//
//			if(c.getItemCount() > 0) {
//				c.removeAllItems();
//			}
			while (rs.next()) {				
				// 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
				// 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.
				String serialnum = rs.getString(1);
				s.SelectRealserialnum_singleTon.add(rs.getString(1));
		
				c.addItem(serialnum);
				System.out.println("c.addItem( !!! ");
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
		add(c);
		//	창이 띄워졌을 때 바로 보이게 하기
		c.requestFocus();
		
	}
	////		콤보박스 값 가져오기
	void select_serial() {
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (c.getSelectedItem().toString() != null) {
					s.select_serial = c.getSelectedItem().toString();
				}
				
				
			}
		});
	}
	////////

	////////////////	콤보박스 만들기
	void makeCombo() { // select 박스
		
		c.setSize(470, 40);
		c.setLocation(100, 455);
		combo_loadData();



	}
	
	////////////////		RealStudytime	공부시간 값 가져오기		//////////////////////////
public void loadRealStudyTime_sel() {
		
		SingleTon s =SingleTon.getInstanse();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		s.RealGraph_count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		
			stmt = conn.createStatement();
		
			String sql = "SELECT * FROM realstudytime where serialnum = " + s.select_serial;
		
			rs = stmt.executeQuery(sql);
		
			while (rs.next()) {
				// 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
				// 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.


				s.SelectRealstudytime2_singleTon.add(rs.getString(2));
		
				s.RealGraph_count++;
				System.out.println("RealGraph_count: " + s.RealGraph_count);
				
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

/////////////////////////////////			ScheduleStudytime	가져오기		/////////////////////////////////////

public void loadScheduleStudyTime_sel() {
	
	SingleTon s =SingleTon.getInstanse();
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	s.ScheduleGraph_count = 0;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
	
		stmt = conn.createStatement();
	
		String sql = "SELECT * FROM schedulestudytime where serialnum = " + s.select_serial;
	
		rs = stmt.executeQuery(sql);
	
		while (rs.next()) {
			// 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
			// 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.

			s.SelectSchedulestudytime2_singleTon.add(rs.getString(2));

			s.ScheduleGraph_count++;
			System.out.println("ScheduleGraph_count: " + s.ScheduleGraph_count);
			
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



	
	
	
}

