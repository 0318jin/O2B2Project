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

class And_SocketThread {
	static And_SocketThread m = null;
	ServerSocket server = null;
	Socket sock = null;
	boolean isInterrupt = false;
	ArrayList<String> mRead_Queue = new ArrayList<String>();

	private And_SocketThread() {

	}

	static And_SocketThread get() {
		if (m == null)
			m = new And_SocketThread();
		return m;
	}

	void join() {
		try {
			server = new ServerSocket(0754);
			System.out.println("안드로이드 App 연결 기다리는 중.....");

			sock = server.accept();
			SocketThread_Read mSocketThread_Read = new SocketThread_Read();
			mSocketThread_Read.start();
			InetAddress inetaddr = sock.getInetAddress();
			isInterrupt = false;

			System.out.println("안드로이드 App Ip : " + inetaddr.getHostAddress() + " 로부터 접속했습니다.");
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
		SocketThread_Read mRead = new SocketThread_Read();
		mRead.start();
	}

	void write(String msg) {
		try {
			OutputStream out = sock.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			pw.println(msg);
			pw.flush();
		} catch (Exception e) {

		}
	}

}

class SocketThread_Read extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			And_SocketThread mSocketThread = And_SocketThread.get();
			InputStream in = mSocketThread.sock.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while (!mSocketThread.isInterrupt && !mSocketThread.sock.isClosed()) {
				String readMsg = br.readLine();
				mSocketThread.mRead_Queue.add(readMsg);
				// System.out.println("queue add : " + readMsg); //에러 확인용 Print문
				Thread.sleep(100);
			}
			System.out.println("disconnected");
		} catch (Exception e) {

		}
	}
}
