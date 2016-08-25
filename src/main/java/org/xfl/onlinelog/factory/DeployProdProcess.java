package org.xfl.onlinelog.factory;

import org.xfl.onlinelog.bean.Message;
import org.xfl.onlinelog.bean.PublishInfo;
import org.xfl.onlinelog.client.WebSocketClient;
import org.xfl.onlinelog.util.Command;

import javax.websocket.Session;
import java.io.IOException;

/**
 * 服务器部署
 * Created by xfl on 2016/8/18.
 */
public class DeployProdProcess implements IProcess {
    private WebSocketClient webSocketClient;

    @Override
    public Process createProcess(Message message, final Session session) throws IOException {
        Command command = Command.getByValue(message.getMsgType());
        Process process = null;
        switch (command) {
            case PROD_DEPLOY:
                //开始部署执行部署脚本
                PublishInfo publishInfo = message.getPublishInfo();
                Integer pubType = publishInfo.getPublishType();
                if (pubType != null && publishInfo.getIp() != null) {
                    if (pubType == 1) {
                        session.getBasicRemote().sendText("开始生产发布" + publishInfo.getIp() + "<br>");
                        process = Runtime.getRuntime().exec("sh /home/appadmin/script/run.sh " + publishInfo.toString());
                    } else if (pubType == 0) {
                        session.getBasicRemote().sendText("开始生产回滚" + publishInfo.getIp() + "<br>");
                        process = Runtime.getRuntime().exec("sh /home/appadmin/script/run.sh " + publishInfo.toString());
                    }
                }
            break;
            default:
                break;
        }
        return process;
    }

    @Override
    public void handleMessage(Message message, final Session session) throws IOException {
        webSocketClient = new WebSocketClient(session);
        Message startServerMsg = new Message();
        startServerMsg.setMsgType(Command.START_FILE_SERVER.getVal());

        webSocketClient.addMessageListener(new WebSocketClient.CallbackHandle() {
            @Override
            public void handleMessage(String message) {
                try {
                    //发送到前端显示
                    session.getBasicRemote().sendText(message+"<br>");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //先启动服务器
        webSocketClient.sendMsg(startServerMsg);
        //再文件传输
        //最后发送部署命令
        //webSocketClient.sendMsg();
    }
}
