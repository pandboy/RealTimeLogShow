package org.xfl.onlinelog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.websocket.Session;

public class TailLogThread extends Thread {
	
	private BufferedReader reader;
	private Session session;
	private boolean running = true;
	public TailLogThread(InputStream in, Session session) {
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.session = session;
		
	}
	
	@Override
	public void run() {
		String line;
		try {
			while((line = reader.readLine()) != null && running) {
				// 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
				if (session != null) {
					session.getBasicRemote().sendText(line + "<br>");
				} else {
					setRunning(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}