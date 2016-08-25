package org.xfl.onlinelog.client;

import org.xfl.onlinelog.util.Const;

import javax.websocket.Session;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by xfl on 2016/8/18.
 */
public class FileClient implements Runnable{
    private Socket s;
    private String filePath;
    private Session session;
    public FileClient(String host, int port, String filePath) {
        try {
            s = new Socket(host, port);
            this.filePath = filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            FileInputStream fis = new FileInputStream(filePath);
            byte[] buff = new byte[4096];
            while (fis.read(buff) > 0) {
                dos.write(buff);
            }
            fis.close();
            dos.close();
            this.session.getBasicRemote().sendText("send finish");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void setSession(Session session) {
        this.session = session;
    }

    public static void main(String[] args) {
        FileClient client = new FileClient(Const.host, Const.SERVER_PORT, "D://data.txt");
        Thread thread = new Thread(client);
        thread.start();
    }
}
