package o2b2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

class Rasp_Main extends Thread {

	public void run() {
////	���� ��¥�� �������� �κ�
	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
	Calendar time = Calendar.getInstance();
		super.run();
		try {
			while (true) {
				Rasp_Socket_Thread mRasp_Socket_Thread = Rasp_Socket_Thread.get();
				mRasp_Socket_Thread.Join();
				// ���� �д� �κ� �ʱ�ȭ �� �־�� �Ѵ�.
				String readData = null;

				while (true) { // Ŭ���̾�Ʈ���� ���� �޴� while��
					try {
						readData = mRasp_Socket_Thread.readData();

						if (readData != null) {
							System.out.println("readData �����޾� while �� ��������");
							break;
						}
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
				//�̱��� �߰��κ�================================
				SingleTon s = SingleTon.getInstanse();
				s.readData_singleTon = readData;
			
				System.out.println("Ŭ���̾�Ʈ���� �޾ƿ� �� : " + readData);
				System.out.println("�̱��� �׽�Ʈ �� : " + s.readData_singleTon);
				String sendmsg = readData;
				//��������̿��� ���� ���� �����ͺ��̽��� �����ϴ� �κ�
				String[] array = s.readData_singleTon.split("/");
				String serialnum = array[0];
				System.out.println(serialnum);
				String studytime = array[1];
				System.out.println(studytime);
				String date = format1.format(time.getTime());
				System.out.println(date);
				String subject = "1";
				Insert_RealStudyTime.insert(serialnum, studytime, date, subject);
			
				// Ŭ���̾�Ʈ�� ���� ������ ����
				mRasp_Socket_Thread.write("send : " + sendmsg);
				JOptionPane.showMessageDialog(null,  studytime+"��������� �н��ð��� �߰��Ǿ����ϴ�.");
			}

		} catch (Exception e) {

		}

	}
}