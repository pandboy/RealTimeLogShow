package org.xfl.onlinelog.client;

import org.xfl.onlinelog.bean.Message;
import org.xfl.onlinelog.util.Command;
import org.xfl.onlinelog.util.Const;
import org.xfl.onlinelog.util.JsonUtil;

import javax.websocket.Session;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by xfl on 2016/8/18.
 */
public class WebSocketClient {
    private String destUri = "";
    private Session browerSession;

    private CallbackHandle callbackHandle;
    public WebSocketClient(Session browerSession) {
        this.browerSession = browerSession;
    }
    public void sendMsg (Message message) {
        try {
            URI uri = new URI(destUri);
            WebSocketClientEndpoint clientEndpoint = new WebSocketClientEndpoint(uri);
            clientEndpoint.addMessageHandler(new WebSocketClientEndpoint.MessageHandler() {
                @Override
                public void handleMessage(String message) {
                    System.out.printf(message);
                    //接受文件服务器发来的消息，
                    if(callbackHandle != null) {
                        Message serverMsg = JsonUtil.converStringToObject(message, Message.class);

                        Command command = Command.getByValue(serverMsg.getMsgType().intValue());
                        switch (command) {
                            case START_FILE_SERVER_FINISH:
                                //发送开始传输文件
                                FileClient client = new FileClient(Const.host, Const.SERVER_PORT, Const.SOURCE_FILE_PATH);
                                client.setSession(browerSession);
                                Thread thread = new Thread(client);
                                thread.start();
                                break;
                            default:
                                break;
                        }
                        callbackHandle.handleMessage(message);
                    }
                }
            });
            clientEndpoint.sendMessage(JsonUtil.convertObjectToString(message, Message.class));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
    public void addMessageListener(CallbackHandle callbackHandle) {
        this.callbackHandle = callbackHandle;
    }
    public static interface CallbackHandle {
        public void handleMessage(String message);
    }
}

