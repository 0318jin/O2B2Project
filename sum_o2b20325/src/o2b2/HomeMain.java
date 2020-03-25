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
        
       ImageIcon img = new ImageIcon("image/bgr1.png");  //이미지 경로
       JLabel imagelJLabel = new JLabel (img);     
       imagelJLabel.setSize(600,730);
       imagelJLabel.setLocation(0,0);
      
       add(imagelJLabel);
       
       ImageIcon img1 = new ImageIcon("image/bgr2.png");  //이미지 경로
       JLabel imagelJLabel1 = new JLabel (img1);     
       imagelJLabel1.setSize(600,370);
       imagelJLabel1.setLocation(600,0);
      
       add(imagelJLabel1);
       
       ImageIcon img2 = new ImageIcon("image/hellowoba.JPG");  //이미지 경로
       JLabel imagelJLabel2 = new JLabel (img2);     
       imagelJLabel2.setSize(600,365);
       imagelJLabel2.setLocation(550,300);
      
       add(imagelJLabel2);
       
       ImageIcon img3 = new ImageIcon("image/color.png");  //이미지 경로
       JLabel imagelJLabel3 = new JLabel (img3);     
       imagelJLabel3.setSize(1200,5);
       imagelJLabel3.setLocation(0,730);
      
       add(imagelJLabel3);
       
        jlabel1 = new JLabel("STUDY ASSISTANS");
        jlabel1.setFont(new Font("serif",Font.BOLD,20));
        jlabel1.setForeground(Color.ORANGE);
        jlabel1.setSize(200,100);
        jlabel1.setLocation(1000,695);
        add(jlabel1);
       
        //////////////////////////////////////////////
        jtextfield1 = new JTextField();
        jtextfield1.setSize(200,40);
        jtextfield1.setLocation(730, 600);
        jtextfield1.setBackground(Color.orange);
        add(jtextfield1);
        //////////////////////////////////////////////
        jButton1 = new JButton(new ImageIcon("image/homeicon1.png"));
        jButton1.setSize(120,70);        
        jButton1.setLocation(940, 580);
        
        add(jButton1);
        
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            
            
            if(jtextfield1.getText().equals("1234")) {  // 비밀번호 입력 값
               o2b2.MenuBar mb = o2b2.MenuBar.getIntance();
               JOptionPane.showMessageDialog(null, "로그인 되었습니다");
               mb.setVisible();
            }
            else {
               JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요");
            }
            
            }
         });

}
    
}


