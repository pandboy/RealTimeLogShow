package org.xfl.onlinelog.factory;

import org.xfl.onlinelog.bean.Message;
import org.xfl.onlinelog.server.FileServer;
import org.xfl.onlinelog.util.Const;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by bl04559 on 2016/8/18.
 */
public class FileServerListener implements IProcess {
    private FileServer fileServer;
    @Override
    public Process createProcess(Message message, Session session) throws IOException {
        return null;
    }

    @Override
    public void handleMessage(Message message, Session session) throws IOException {
        fileServer = new FileServer(Const.SERVER_PORT, session);
        Thread thread = new Thread(fileServer);
        thread.start();
    }
}
