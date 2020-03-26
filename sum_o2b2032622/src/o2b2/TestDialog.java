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
      setTitle("회원가입 추가 예제");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      contentPane = getContentPane();
      setLocation(600, 400);
      setSize(300, 100);
      contentPane.add(new MyPanel(), BorderLayout.CENTER);
      setVisible(true);
   }

   class MyPanel extends Panel {
      JButton inputBtn = new JButton("회원 추가 버튼");
      JTextField tf = new JTextField(10);

      MyPanel() {
         setBackground(Color.LIGHT_GRAY);
         add(inputBtn);
         add(tf);
         inputBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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