package demo;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.noear.java_websocket.server.SimpleWebSocketServer;

/**
 * @author noear 2024/1/18 created
 */
public class Demo02_server {
    public static void main(String[] args) throws Exception {
        //::启动服务端
        SimpleWebSocketServer server = new SimpleWebSocketServer(18080) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                super.onOpen(conn, handshake);

                System.out.println("有新的连接进来!");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                System.out.println(message);
            }
        };
        server.start();
    }
}
