package o2b2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import o2b2.JPanel01.JPanelTest;


class JPanel02 extends JPanel{
	
	private JLabel jlabel1;
	
	private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    ///
//    static Object[][] data;
    
    private JTextField jtextfield1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
	JComboBox<String> c = new JComboBox();
	JTable table;
	SingleTon s = SingleTon.getInstanse();
	
	static final int TABLE_MENU = 0;
	
	static final int TABLE_SERIALNUM = 0;
	static final int TABLE_PHONENUM = 1;
	static final int TABLE_PWNUM = 2;
    final String[] columnType = { "시리얼넘버", "전화번호", "비밀번호"};
    static boolean DBGTest = false;
    
    String serialnum = null;
    String phonenum = null;
    String passworld = null;
    
    
    void makeCombo() {  // select 박스
    	Connection conn = null;
    	Statement stmt = null;
		ResultSet rs = null;

        c.setSize(300,40);        
        c.setLocation(300, 680);
        c.setFont(new Font("나눔고딕",Font.BOLD,20));

        
        ImageIcon img1 = new ImageIcon("image/study1.png");  //이미지 경로
	    JLabel imagelJLabel1 = new JLabel (img1);     
	    imagelJLabel1.setSize(200,200);
	    imagelJLabel1.setLocation(860,80);
	    add(imagelJLabel1);

    	

    	try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");
		
			stmt = conn.createStatement();
		
			String sql = "SELECT * FROM profile ORDER BY serialnum asc;";
		
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				// 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
				// 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.
				s.serialnum_singleTon.add(rs.getString(1));
				String serialnum = rs.getString(1);
				s.phonenum_singleTon.add(rs.getString(2));
				String phonenum = rs.getString(2);
				s.password1_singleTon.add(rs.getString(3));
				String password1 = rs.getString(3);
		
				s.profileval = serialnum + "/" + phonenum + "/" + password1 + "\n";
				c.addItem(s.profileval);
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
    	c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				s.Delete_pro = c.getSelectedItem().toString();
			}
		});
    }
    /////////////////////////////////////////////////////////////
    public JPanel02(JPanelTest win){
    	
    	SingleTon s = SingleTon.getInstanse();
    	JPanel mPannel = this;
//        jlabel1 = new JLabel("회원정보 데이터");
//        jlabel1.setSize(200,40);
//        jlabel1.setLocation(375,10);  
//        jlabel1.setForeground(Color.white); // 글 색상
//        jlabel1.setFont(jlabel1.getFont().deriveFont(20.0f));
//        add(jlabel1);
    	
  	    ImageIcon img = new ImageIcon("image/oba-study2.jpg");  //이미지 경로
	    JLabel imagelJLabel = new JLabel (img);     
	    imagelJLabel.setSize(170,60);
	    imagelJLabel.setLocation(1000,650);
	    
	    add(imagelJLabel);
	    setVisible(true); // 화면에 보이기   	
	    
    	makeCombo();
    	
        setLayout(null); 
        
        uploadJtable();
        
	    ImageIcon img1 = new ImageIcon("image/profile.png");  //이미지 경로
	    JLabel imagelJLabel1 = new JLabel (img1);     
	    imagelJLabel1.setSize(300,60);
	    imagelJLabel1.setLocation(280,45);
	    
	    add(imagelJLabel1);
        
        jButton1 = new JButton(new ImageIcon("image/UP.PNG"));
        jButton1.setSize(250,60);        
        jButton1.setLocation(820, 310);
        add(jButton1);
        //
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// 업로딩시 콤보박스 재로딩
            	
            	jTextArea1.setText("");
               Select_Profile mP = new Select_Profile();
               mP.loadProfile(jTextArea1);
               // 표 생성하기
               uploadJtable();
            }
         });
        //
        
        jButton2 = new JButton(new ImageIcon("image/inbtn.PNG"));
        jButton2.setSize(250,60);        
        jButton2.setLocation(820, 435);
        add(jButton2);
        //
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	///	기존 INSERT
//           	try {
//				jTextArea1.setText("");
//				s.get_textfield_profile = jtextfield1.getText();
//				System.out.println(s.get_textfield_profile);
//				jTextArea1.append(s.get_textfield_profile+"\n");
//				jtextfield1.setText(null);
//				String[] array = s.get_textfield_profile.split("/");
//				String serialnum = array[0];
//				String phonenum = array[1];
//				String password1 = array[2];
//				Insert_Profile.insert(serialnum, phonenum, password1, jTextArea1);
//				c.addItem(s.get_textfield_profile);
//				//UPLoad를 다시 실행하여 테이블을 새로고침 한다
//				uploadJtable();
//           	} catch (Exception e2) {
//				// TODO: handle exception
//				jTextArea1.append("실패했습니다. 값을 다시 한번 확인해 주세요.");
//				//	팝업창 띄우기
//				JOptionPane.showMessageDialog(null, "실패했습니다. 값을 확인해주세요", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
//			}
            	
            	try {
                    while (true) {
                       serialnum = JOptionPane.showInputDialog("시리얼 넘버를 입력하세요.");
                       if (serialnum.contentEquals("")) {
                          JOptionPane.showMessageDialog(null, "공백입니다.");
                          continue;
                       }
                       while (true) {
                          phonenum = JOptionPane.showInputDialog("전화 번호를 입력하세요.");
                          if (phonenum.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "공백입니다.");
                             continue;
                          }
                          break;
                       }
                       while (true) {
                          passworld = JOptionPane.showInputDialog("비밀 번호를 입력하세요.");
                          if (passworld.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "공백입니다.");
                             continue;
                          }
                          break;
                       }
                       break;
                    }
                    String sumsum = serialnum+"/"+phonenum+"/"+passworld;
                    System.out.println(sumsum);
                    
    				Insert_Profile.insert(serialnum, phonenum, passworld, jTextArea1);
    				c.addItem(sumsum);

                    uploadJtable();
                    
                 } catch (Exception k) {

                 }
            	
           	}
         });
        //
        
        jButton3 = new JButton(new ImageIcon("image/DL.PNG"));
        jButton3.setSize(250,60);        
        jButton3.setLocation(820, 560);
        add(jButton3);
        //
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           	 
            jTextArea1.setText("");
//           	 s.Delete_pro = "01012345673,asd1233";
           	 String[] array = s.Delete_pro.split("/");
//           	 String serialnum = array[0];
           	 String delphonenum = array[1];
//           	 String delpassword = array[2];
           	 Delete_Profile.delete(delphonenum, /*delpassword,*/ jTextArea1);
           	c.removeItem(s.Delete_pro);
          //UPLoad를 다시 실행하여 테이블을 새로고침 한다
			uploadJtable();
            }
         });
        //
        
        jButton4 = new JButton(new ImageIcon("image/clbtn.PNG"));
        jButton4.setSize(250,60);        
        jButton4.setLocation(820,360);
        //add(jButton4);
        //
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	jTextArea1.setText("");
            }
        });
        //
        ////////안보이게 Location옮김
        jTextArea1 = new JTextArea();
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setSize(300,320);
        jScrollPane1.setLocation(2300,120);
        jTextArea1.setFont(new Font("나눔고딕",Font.BOLD,20));
        add(jScrollPane1);
        
        jtextfield1 = new JTextField();
        jtextfield1.setSize(300,40);
        jtextfield1.setLocation(700, 680);
        jtextfield1.setFont(new Font("나눔고딕",Font.BOLD,20));
//        add(jtextfield1);
        //uploadJtable(this);
        


        
    }
    void uploadJtable() {
    	Select_Profile sP = new Select_Profile();
//    	sP.initValue();
    	sP.loadProfile();
        //Object[][] pro_obj= new Object[s.serialnum_singleTon.size()+1][3];
        Object[][] pro_obj= new Object[s.serialnum_singleTon.size()+2][3];
//        table = new JTable(pro_obj,columnType);

        int count =0;

        
        for(int i=0; i<columnType.length; i++)
        {
        	pro_obj[TABLE_MENU][i] = columnType[i];
        }

        for (int i = 0; i < s.serialnum_singleTon.size(); i++) {
        	pro_obj[i+1][TABLE_SERIALNUM]=s.serialnum_singleTon.get(i);
        	pro_obj[i+1][TABLE_PHONENUM]=s.phonenum_singleTon.get(i);
        	pro_obj[i+1][TABLE_PWNUM]=s.password1_singleTon.get(i);
        	//count++;
        	System.out.println("s.serialnum_singleTon.get(i) : " + s.serialnum_singleTon.get(i));
        };
//        count++;
//        if(DBGTest == false) {
//	        for(int i=0; i<columnType.length; i++)
//	        {
//	        	pro_obj[count][i] = columnType[i];
//	        }
//	        DBGTest = true;
//        }
		if(table != null)
		{
			System.out.println("table != null");
			remove(table);
	        revalidate();
	        repaint();
		}
        table = new JTable(pro_obj,columnType);
//		JTable table = new JTable(pro_obj,columnType);
        table.setRowHeight(25);
        table.setSize(650,500);
        table.setLocation(100,120);
        //
        // DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
        DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
        // DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
        tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);      
        // 정렬할 테이블의 ColumnModel을 가져옴
        TableColumnModel tcmSchedule = table.getColumnModel();
        // 반복문을 이용하여 테이블을 가운데 정렬로 지정
        for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
        tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
        }
        //
        table.setFont(new Font("나눔고딕",Font.BOLD,24));
        add(table);
        revalidate();
        repaint();
        
    }
        
    
}

