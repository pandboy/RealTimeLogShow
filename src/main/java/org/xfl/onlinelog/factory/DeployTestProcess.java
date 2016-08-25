package org.xfl.onlinelog.factory;

import org.xfl.onlinelog.bean.Message;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by bl04559 on 2016/8/17.
 */
public class DeployTestProcess implements IProcess {
    @Override
    public Process createProcess(Message message, Session session) throws IOException {
        return Runtime.getRuntime().exec("/home/appadmin/deploy.sh");
    }

    @Override
    public void handleMessage(Message message, Session session) throws IOException {

    }
}
