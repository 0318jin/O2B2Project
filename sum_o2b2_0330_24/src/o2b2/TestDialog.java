package o2b2;
import javax.sql.rowset.serial.SerialArray;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TestDialog extends JFrame {
   Container contentPane;
   String serialnum = null;
   String phonenum = null;
   String passworld = null;
   
   TestDialog() {
      setTitle("ȸ������ �߰� ����");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      contentPane = getContentPane();
      setLocation(600, 400);
      setSize(300, 100);
      contentPane.add(new MyPanel(), BorderLayout.CENTER);
      setVisible(true);
   }

   class MyPanel extends Panel {
      JButton inputBtn = new JButton("ȸ�� �߰� ��ư");
      JTextField tf = new JTextField(10);

      MyPanel() {
         setBackground(Color.LIGHT_GRAY);
         add(inputBtn);
         add(tf);
         inputBtn.addActionListener(new ActionListener() {
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
                  tf.setText(sumsum);
                  
               } catch (Exception k) {

               }
            }
         });
      }
   }

   public static void main(String[] args) {
      new TestDialog();
   }
}