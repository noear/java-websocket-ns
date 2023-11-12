package demo;


import org.java_websocket.WebSocket;
import org.java_websocket.client.SimpleWebSocketClient;
import org.java_websocket.server.SimpleWebSocketServer;

import java.util.concurrent.TimeUnit;

public class Demo01 {
    public static void main(String[] args) throws Exception {
        //::启动服务端
        SimpleWebSocketServer server = new SimpleWebSocketServer(18080){
            @Override
            public void onMessage(WebSocket conn, String message) {
                System.out.println(message);
            }
        };
        server.start();


        //休息会儿
        Thread.sleep(1000);


        //::启动客户端
        SimpleWebSocketClient client = new SimpleWebSocketClient("ws://127.0.0.1:18080") {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
            }
        };

        //开始连接
        client.connectBlocking(10, TimeUnit.SECONDS);
        //定制心跳
        client.heartbeatHandler(e -> {
            e.sendPing();
            System.out.println("jump");
        });
        //开始心跳 + 心跳时自动重连
        client.heartbeat(2_000, true);

        //发送测试
        client.send("test");

        //休息会儿
        Thread.sleep(1000);

        //关闭
        client.close();
        server.stop();
    }
}
