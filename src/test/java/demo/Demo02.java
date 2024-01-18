package demo;

import org.java_websocket.handshake.ServerHandshake;
import org.noear.java_websocket.client.SimpleWebSocketClient;

import java.util.concurrent.TimeUnit;

/**
 * @author noear 2024/1/18 created
 */
public class Demo02 {
    public static void main(String[] args) throws Exception {
        //::启动客户端
        SimpleWebSocketClient client = new SimpleWebSocketClient("ws://127.0.0.1:18080") {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);

                System.out.println("连接成功!");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };

        try {

            //开始连接
            client.connectBlocking(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //开始心跳 + 心跳时自动重连
        client.heartbeat(20_000, true);

        System.in.read();
    }
}
