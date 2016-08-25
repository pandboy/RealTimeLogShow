package org.xfl.onlinelog.server;

import org.xfl.onlinelog.util.Const;

import javax.websocket.Session;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by bl04559 on 2016/8/18.
 */
public class FileServer implements Runnable{
    private final static Logger looger = Logger.getLogger(FileServer.class.getName());
    private boolean running = true;

    private ServerSocket ss;
    private Session session;
    public FileServer(int port, Session session) {
        try {
            ss = new ServerSocket(port);
            this.session = session;
        } catch (IOException e) {
            looger.log(Level.WARNING,"init file server failure",e);
        }
    }

    public FileServer(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            looger.log(Level.WARNING,"init file server failure",e);
        }
    }



    public void stop () {
        running = false;
    }

    @Override
    public void run() {
       /* Message message = new Message();
        message.setMsgType(Message.TypeEnum.START_FILE_SERVER_FINISH.getVal());
        try {
            this.session.getBasicRemote().sendText(JsonUtil.convertObjectToString(message, Message.class));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        while (running) {
            try {
                Socket clientSocket = ss.accept();
                saveFile(clientSocket);
                stop();
              //  Message fileSavedMsg = new Message(Message.TypeEnum.FILE_SAVED.getVal());
              //  this.session.getBasicRemote().sendText(JsonUtil.convertObjectToString(fileSavedMsg, Message.class));
            } catch (IOException e) {
                looger.log(Level.WARNING,"save file server failure",e);
                stop();
               // Message exceptMsg = new Message(Message.TypeEnum.EXCEPT.getVal());
                /*try {
                    this.session.getBasicRemote().sendText(JsonUtil.convertObjectToString(exceptMsg, Message.class));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
            }
        }
    }

    private void saveFile(Socket clientSocket) throws IOException {
        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
       // FileOutputStream fos = new FileOutputStream("/Data/deploy/ROOT.war");
        FileOutputStream fos = new FileOutputStream("D://out.txt");
        byte[] buffer = new byte[4096];
        int fileSize = 15123; // Send file size in separate msg
        int read = 0;
        int totalRead = 0;
        int remaining = fileSize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            looger.log(Level.SEVERE, "read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }
        fos.close();
        dis.close();
    }

    public static void main(String[] args) {
        FileServer fileServer = new FileServer(Const.SERVER_PORT);
        Thread thread = new Thread(fileServer);
        thread.start();
    }
}
