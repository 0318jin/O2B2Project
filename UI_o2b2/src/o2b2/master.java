package o2b2;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import o2b2.JPanel01.JPanelTest;


class JPanel07 extends JPanel{
	
	private JButton jButton1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    
    private JLabel jlabel1;


    
    public JPanel07(JPanelTest win){
    	
  	    ImageIcon img = new ImageIcon("image/oba-study2.jpg");  //이미지 경로
	    JLabel imagelJLabel = new JLabel (img);     
	    imagelJLabel.setSize(200,60);
	    imagelJLabel.setLocation(8,45);
	    
	    add(imagelJLabel);
	    setVisible(true); // 화면에 보이기   	
	    
        setLayout(null);        
        
        jlabel1 = new JLabel("PassWord : ");
        jlabel1.setFont(new Font("serif",Font.BOLD,20));
//        jlabel1.setForeground(Color.ORANGE);
        jlabel1.setSize(150,40);
        jlabel1.setLocation(380, 50);
        add(jlabel1);
        
        jButton1 = new JButton("btntest");
        jButton1.setSize(130,40);        
        jButton1.setLocation(40, 50);
        add(jButton1);
        
        String password = "1234";
        jTextArea1 = new JTextArea(password);
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setSize(200,20);
        jScrollPane1.setLocation(500,60);
        
        add(jScrollPane1);
        
}
}



