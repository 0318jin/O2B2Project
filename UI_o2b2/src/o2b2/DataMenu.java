package o2b2;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import o2b2.JPanel01.JPanelTest;


class JPanel05 extends JPanel{
	
	private JButton jButton1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    
    private JLabel jlabel1;

    public JPanel05(JPanelTest win){
    	
    	
//  	    ImageIcon img1 = new ImageIcon("image/study1.png");  //이미지 경로
//	    JLabel imagelJLabel1 = new JLabel (img1);     
//	    imagelJLabel1.setSize(300,300);
//	    imagelJLabel1.setLocation(8,45);
//	   
//	    add(imagelJLabel1);
	    
	    
  	    ImageIcon img = new ImageIcon("image/oba-study2.jpg");  //이미지 경로
	    JLabel imagelJLabel = new JLabel (img);     
	    imagelJLabel.setSize(200,60);
	    imagelJLabel.setLocation(8,45);
	    
	    add(imagelJLabel);
	    setVisible(true); // 화면에 보이기   	
	    
        setLayout(null);        
        
        jlabel1 = new JLabel("labeltest");
        
        jlabel1.setSize(100,40);
        jlabel1.setLocation(100, 200);
        add(jlabel1);
        
        jButton1 = new JButton("btntest");
        jButton1.setSize(130,40);        
        jButton1.setLocation(40, 50);
        add(jButton1);
        

        jTextArea1 = new JTextArea();
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setSize(500,300);
        jScrollPane1.setLocation(200,50);
        add(jScrollPane1);
        
}
	
}

public class DataMenu {

}
