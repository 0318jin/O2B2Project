package o2b2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import o2b2.JPanel01.JPanelTest;
import java.awt.Font;


class homemain extends JPanel{
	
	private JButton jButton1;
    private JTextField jtextfield1;
    private JLabel jlabel1;

    /////////////////////////////////////////////////////////////
    public homemain(JPanelTest win){
    	
    	SingleTon s = SingleTon.getInstanse();
    	
        setLayout(null);        
        
  	    ImageIcon img = new ImageIcon("image/phonebook.png");  //이미지 경로
	    JLabel imagelJLabel = new JLabel (img);     
	    imagelJLabel.setSize(300,300);
	    imagelJLabel.setLocation(570,310);
	   
	    add(imagelJLabel);
	    
        jlabel1 = new JLabel("STUDY ASSISTANS");
        jlabel1.setFont(new Font("serif",Font.BOLD,30));
        jlabel1.setForeground(Color.ORANGE);
        jlabel1.setSize(300,100);
        jlabel1.setLocation(500, 470);
        add(jlabel1);
	    
        //////////////////////////////////////////////
        jtextfield1 = new JTextField();
        jtextfield1.setSize(200,40);
        jtextfield1.setLocation(300, 320);
        jtextfield1.setBackground(Color.orange);
        add(jtextfield1);
        //////////////////////////////////////////////
        jButton1 = new JButton(new ImageIcon("image/oba.png"));
        jButton1.setSize(300,140);        
        jButton1.setLocation(250, 150);
        add(jButton1);
        
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            
            
            if(jtextfield1.getText().equals("1234")) {  // 비밀번호 입력 값
            	o2b2.MenuBar mb = o2b2.MenuBar.getIntance();
            	mb.setVisible();
            }
            else {
            	JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요");
            }
            
            }
         });

}
    
}




