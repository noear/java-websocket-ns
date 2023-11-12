package org.java_websocket.client;


import org.java_websocket.utils.ConsumerEx;

/**
 * @author noear
 * @since 1.0
 */
public class HeartbeatHandlerDefault implements ConsumerEx<SimpleWebSocketClient> {
    @Override
    public void accept(SimpleWebSocketClient client) throws Exception{
        if (client.isClosed()) {
            if (client.isAutoReconnect()) {
                client.reconnectBlocking();
            }
        } else {
            client.sendPing();
        }
    }
}
