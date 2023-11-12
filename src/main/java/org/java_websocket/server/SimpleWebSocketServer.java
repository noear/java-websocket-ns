package org.java_websocket.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;

/**
 * 简单服务端
 *
 * @author noear
 * @since 2.0
 */
public class SimpleWebSocketServer extends WebSocketServer {
    public SimpleWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    public SimpleWebSocketServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }
}
