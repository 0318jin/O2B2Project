package o2b2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

class Rasp_Socket_Thread {
	static Rasp_Socket_Thread m = null;
	ServerSocket server = null;
	Socket sock = null;
	boolean isInterrupt = false;
	ArrayList<String> mRead_Queue = new ArrayList<String>();

	private Rasp_Socket_Thread() {

	}

	static Rasp_Socket_Thread get() {
		if (m == null)
			m = new Rasp_Socket_Thread();
		return m;
	}

	void Join() {

		try {
			System.out.println("Join 함수 들어옴.");
			server = new ServerSocket(8888);
			System.out.println("라즈베리파이 연결 기다리는중...");

			sock = server.accept();
			Rasp_Socket_Thread_Read mRasp_Socket_Thread_Read = new Rasp_Socket_Thread_Read();
			mRasp_Socket_Thread_Read.start();
			InetAddress inetaddr = sock.getInetAddress();
			isInterrupt = false;

			System.out.println("라즈베리파이 IP : "+ inetaddr.getHostAddress() + " 로부터 접속했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String readData() {
		String msg = null;
		if (mRead_Queue.size() > 0) {
			msg = mRead_Queue.get(0);
			mRead_Queue.remove(0);
		}
		return msg;
	}

	void readThreadStart() {
		Rasp_Socket_Thread_Read mRead = new Rasp_Socket_Thread_Read();
		mRead.start();
	}

	void write(String msg) {
		try {
			OutputStream out = sock.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(msg);
			sock.close();
			server.close();

		} catch (Exception e) {

		}
	}
}

class Rasp_Socket_Thread_Read extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			Rasp_Socket_Thread mRasp_Socket_Thread = Rasp_Socket_Thread.get();
			InputStream in = mRasp_Socket_Thread.sock.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while (!mRasp_Socket_Thread.isInterrupt) {
				String readMsg = br.readLine();
				mRasp_Socket_Thread.mRead_Queue.add(readMsg);
				System.out.println("queue add : " + readMsg);
				Thread.sleep(100);
			}

		} catch (Exception e) {

		}
	}
}
