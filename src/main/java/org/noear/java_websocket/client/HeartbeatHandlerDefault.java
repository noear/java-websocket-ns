package org.noear.java_websocket.client;


import org.noear.java_websocket.utils.ConsumerEx;

/**
 * 心跳默认处理
 *
 * @author noear
 * @since 1.0
 */
public class HeartbeatHandlerDefault implements ConsumerEx<SimpleWebSocketClient> {
    @Override
    public void accept(SimpleWebSocketClient client) throws Exception{
        if (client.isClosed()) {
            if (client.isAutoReconnect()) {
                client.reconnect();
            }
        } else {
            client.sendPing();
        }
    }
}
