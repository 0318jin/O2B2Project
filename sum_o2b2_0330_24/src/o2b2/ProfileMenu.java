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
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
    
    private JTextField jtextfield1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
	JComboBox<String> c = new JComboBox();
	JTable table;
	JScrollPane scroll;
	SingleTon s = SingleTon.getInstanse();
	
	static final int TABLE_MENU = 0;
	
	static final int TABLE_SERIALNUM = 0;
	static final int TABLE_PHONENUM = 1;
	static final int TABLE_PWNUM = 2;
    final String[] columnType = { "�ܸ���ȣ", "��ȭ��ȣ", "��й�ȣ"};
    static boolean DBGTest = false;
    
    String serialnum = null;
    String phonenum = null;
    String passworld = null;

    public JPanel02(JPanelTest win){
    	
    	SingleTon s = SingleTon.getInstanse();
    	JPanel mPannel = this;
    	
    	ImageIcon img2 = new ImageIcon("image/study1.png");  //�̹��� ���
	    JLabel imagelJLabel2 = new JLabel (img2);     
	    imagelJLabel2.setSize(200,200);
	    imagelJLabel2.setLocation(860,80);
	    add(imagelJLabel2);
    	
  	    ImageIcon img = new ImageIcon("image/oba-study2.jpg");  //�̹��� ���
	    JLabel imagelJLabel = new JLabel (img);     
	    imagelJLabel.setSize(170,60);
	    imagelJLabel.setLocation(1000,650);
	    
	    add(imagelJLabel);
	    setVisible(true); // ȭ�鿡 ���̱�   	
    	
        setLayout(null); 
        
        uploadJtable();
        
	    ImageIcon img1 = new ImageIcon("image/profile.png");  //�̹��� ���
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
            	// ���ε��� �޺��ڽ� ��ε�
            	JOptionPane.showMessageDialog(null,  "���ΰ�ħ �Ǿ����ϴ�.");
            	jTextArea1.setText("");
               Select_Profile mP = new Select_Profile();
               mP.loadProfile(jTextArea1);
               // ǥ �����ϱ�
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

            	try {
                    while (true) {
                       serialnum = JOptionPane.showInputDialog("�ø��� �ѹ��� �Է��ϼ���.");
                       if (serialnum.contentEquals("")) {
                          JOptionPane.showMessageDialog(null, "�����Դϴ�.");
                          continue;
                       }
                       while (true) {
                          phonenum = JOptionPane.showInputDialog("��ȭ ��ȣ�� �Է��ϼ���.");
                          if (phonenum.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "�����Դϴ�.");
                             continue;
                          }
                          break;
                       }
                       while (true) {
                          passworld = JOptionPane.showInputDialog("��� ��ȣ�� �Է��ϼ���.");
                          if (passworld.contentEquals("")) {
                             JOptionPane.showMessageDialog(null, "�����Դϴ�.");
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

				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;

				ArrayList<String> test = new ArrayList<String>();

				try {

					Class.forName("com.mysql.jdbc.Driver");

					conn = DriverManager.getConnection("jdbc:mysql://localhost:3309/o2b2", "root", "1234");

					stmt = conn.createStatement();

					String sql = "SELECT * FROM profile ORDER BY serialnum asc;";

					rs = stmt.executeQuery(sql);

					while (rs.next()) {
						//���ڵ��� Į���� �迭�� �޸� 0���� �������� �ʰ� 1���� �����Ѵ�.
						//�����ͺ��̽����� �������� �������� Ÿ�Կ� �°� getString �Ǵ� getInt ���� ȣ���Ѵ�.
						s.serialnum_singleTon.add(rs.getString(1));
						String serialnum = rs.getString(1);
						s.phonenum_singleTon.add(rs.getString(2));
						String phonenum = rs.getString(2);
						s.password1_singleTon.add(rs.getString(3));
						String password1 = rs.getString(3);
				
						s.profileval = serialnum + "/" + phonenum + "/" + password1 + "\n";
						test.add(s.profileval);
					}

				} catch (Exception e1) {

				}

				String[] b = new String[test.size()];

				for (int i = 0; i < test.size(); i++) {
					b[i] = test.get(i);
				}

				Object selected = JOptionPane.showInputDialog(null, "�����ϼ���.", "test", JOptionPane.QUESTION_MESSAGE,
						null, b, b[0]);

				if (selected == null) {
					JOptionPane.showMessageDialog(null, "������ ����Ͽ����ϴ�.");
				} else {
					JOptionPane.showMessageDialog(null, selected + "�� �����ϼ̽��ϴ�.");
				}
           	 
				if(selected != null) {
           	 String[] array = String.valueOf(selected).split("/");
           	 String delphonenum = array[1];
           	 Delete_Profile.delete(delphonenum, jTextArea1);
           	c.removeItem(s.Delete_pro);
          //UPLoad�� �ٽ� �����Ͽ� ���̺��� ���ΰ�ħ �Ѵ�
			uploadJtable();
				}
            }
         });
        
        jButton4 = new JButton(new ImageIcon("image/clbtn.PNG"));
        jButton4.setSize(250,60);        
        jButton4.setLocation(820,360);
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	jTextArea1.setText("");
            }
        });
        //
        ////////�Ⱥ��̰� Location�ű�
        jTextArea1 = new JTextArea();
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setSize(300,320);
        jScrollPane1.setLocation(2300,120);
        jTextArea1.setFont(new Font("�������",Font.BOLD,20));
        add(jScrollPane1);
        
        jtextfield1 = new JTextField();
        jtextfield1.setSize(300,40);
        jtextfield1.setLocation(700, 680);
        jtextfield1.setFont(new Font("�������",Font.BOLD,20));
        
    }
    void uploadJtable() {
    	Select_Profile sP = new Select_Profile();
    	sP.loadProfile();
        Object[][] pro_obj= new Object[s.serialnum_singleTon.size()][3];

        int count =0;

        for(int i=0; i<columnType.length; i++)
        {
        	pro_obj[TABLE_MENU][i] = columnType[i];
        }

        for (int i = 0; i < s.serialnum_singleTon.size(); i++) {
        	pro_obj[i][TABLE_SERIALNUM]=s.serialnum_singleTon.get(i);
        	pro_obj[i][TABLE_PHONENUM]=s.phonenum_singleTon.get(i);
        	pro_obj[i][TABLE_PWNUM]=s.password1_singleTon.get(i);
        };

		if(table != null)
		{
			System.out.println("table != null");
			remove(scroll);
	        revalidate();
	        repaint();
		}
        table = new JTable(pro_obj,columnType);
        
        JTableHeader Theader = table.getTableHeader();
        Color color = new Color(241,196,21);
        Theader.setBackground(color);
        Theader.setFont(new Font("�������", Font.BOLD, 25));
        
        table.setRowHeight(25);
        table.setSize(650,500);
        table.setLocation(110,120);
        
        // DefaultTableCellHeaderRenderer ���� (��� ������ ����)
        DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
        // DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
        tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);      
        // ������ ���̺��� ColumnModel�� ������
        TableColumnModel tcmSchedule = table.getColumnModel();
        // �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
        for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
        tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
        }
        //
        table.setFont(new Font("�������",Font.BOLD,24));
        scroll= new JScrollPane(table);

        scroll.setSize(650,500);
        scroll.setLocation(110,120);
        add(scroll);
        revalidate();
        repaint();
        
    }
        
    
}

