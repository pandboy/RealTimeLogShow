package org.xfl.onlinelog;

import org.xfl.onlinelog.bean.Message;
import org.xfl.onlinelog.factory.IProcess;
import org.xfl.onlinelog.factory.ProcessFactory;
import org.xfl.onlinelog.util.Command;
import org.xfl.onlinelog.util.JsonUtil;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 基于websocket的实时服务，
 * 提供实时日志展示，服务器部署
 */
@ServerEndpoint("/realTime")
public class RealTimeWebSocketServer {
	private final static Logger looger = Logger.getLogger(RealTimeWebSocketServer.class.getName());
	private InputStream inputStream;
	private Session session;

	/**
	 * 新的WebSocket请求开启
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		looger.log(Level.SEVERE, "open id:"+session.getId()+"; reqURI:"+session.getRequestURI());

	}

	@OnMessage
	public void onMessage(String message) {
		looger.log(Level.SEVERE, "*****onmessage："+message+"+***********");
		if (null == message || message.trim().isEmpty()) {
			looger.log(Level.SEVERE, "*****onmessage：is empty***********");
			return;
		}
		try {
			Message msg = JsonUtil.converStringToObject(message, Message.class);
			Command command = Command.getByValue(msg.getMsgType());
			IProcess processInstance = ProcessFactory.getInstance().createProcess(command);
			Process process = processInstance.createProcess(msg, this.session);
			if ( process != null ) {
				inputStream = process.getInputStream();
				// 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
				TailLogThread thread = new TailLogThread(inputStream, this.session);
				this.session.getBasicRemote().sendText("开始处理...<br>");
				thread.start();
			} else {
				processInstance.handleMessage(msg, this.session);
			}

		}catch (Exception e) {
			e.printStackTrace();
			try {
				this.session.getBasicRemote().sendText("异常..终止<br>"+e.getMessage()+"<br>");
				this.session.close();
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * WebSocket请求关闭
	 */
	@OnClose
	public void onClose() {
		try {
			if(inputStream != null)
				inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@OnError
	public void onError(Throwable thr) {
		System.out.println("***********onError*******************");
		if (this.session != null) {
			try {
				this.session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		thr.printStackTrace();
	}
}