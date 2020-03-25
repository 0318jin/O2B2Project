package o2b2;
import java.awt.Color;
import javax.swing.*;

class JPanel01 extends JPanel {  // 1번째 패널
    
	//JPanel01 col = new JPanel01(null);
	
    private JButton jButton1;
    private JButton jButton2;

    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    
    public JPanel01(JPanelTest win){
    	
  	    ImageIcon img = new ImageIcon("image/oba-study2.jpg");  //이미지 경로
	    JLabel imagelJLabel = new JLabel (img);     
	    imagelJLabel.setSize(200,60);
	    imagelJLabel.setLocation(8,45);
	    
	    add(imagelJLabel);
	    setVisible(true); // 화면에 보이기   	
	    
        setLayout(null);
              
        jButton1 = new JButton("SOCKET ON");
        jButton1.setSize(130,40);        
        jButton1.setLocation(40, 50);
        add(jButton1);
         
        jButton2 = new JButton("DELETE");
        jButton2.setSize(130,40);        
        jButton2.setLocation(40, 120);
        add(jButton2);
       
        jTextArea1 = new JTextArea();
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setSize(500,300);
        jScrollPane1.setLocation(200,50);
        add(jScrollPane1);
        
}

class JPanelTest extends JFrame{
    
    public JPanel01 jpanel01 = null;
    public JPanel02 jpanel02 = null;
    public JPanel03 jpanel03 = null;
    public JPanel04 jpanel04 = null;
    public JPanel05 jpanel05 = null;
    }
}