package org.noear.java_websocket.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.noear.java_websocket.utils.ConsumerEx;
import org.noear.java_websocket.utils.RunUtils;

import java.net.URI;
import java.util.concurrent.ScheduledFuture;

/**
 * 简单客户端（添加了心跳、自动重连机制、自定义心跳处理）
 *
 * @author noear
 * @since 1.0
 */
public class SimpleWebSocketClient extends WebSocketClient {
    public SimpleWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public SimpleWebSocketClient(String serverUri) {
        super(URI.create(serverUri));
    }


    private boolean autoReconnect;
    private long heartbeatInterval = 20_000;
    private ConsumerEx<SimpleWebSocketClient> heartbeatHandler = new HeartbeatHandlerDefault();
    private ScheduledFuture<?> heartbeatScheduledFuture;

    /**
     * 是否自动重连
     */
    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    /**
     * 心跳间隔
     */
    public long getHeartbeatInterval() {
        return heartbeatInterval;
    }

    /**
     * 心跳
     */
    public SimpleWebSocketClient heartbeat(long millisInterval, boolean autoReconnect) {
        this.heartbeatInterval = millisInterval;
        this.autoReconnect = autoReconnect;

        heartbeatFutureInit();
        return this;
    }

    /**
     * 设置心跳处理
     */
    public SimpleWebSocketClient heartbeatHandler(ConsumerEx<SimpleWebSocketClient> heartbeatHandler) {
        if (heartbeatHandler != null) {
            this.heartbeatHandler = heartbeatHandler;
        }
        return this;
    }

    private void heartbeatFutureInit() {
        if (heartbeatScheduledFuture != null) {
            heartbeatScheduledFuture.cancel(true);
        }

        heartbeatScheduledFuture = RunUtils.delayAndRepeat(() -> {
            try {
                heartbeatHandler.accept(this);
            } catch (Exception e) {
                onError(e);
            } catch (Throwable e) {

            }
        }, heartbeatInterval);
    }

    private void heartbeatFutureStop(){
        if (heartbeatScheduledFuture != null) {
            //停止心跳
            heartbeatScheduledFuture.cancel(true);
            heartbeatScheduledFuture = null;
        }
    }

    @Override
    public void close() {
        heartbeatFutureStop();
        super.close();
    }

    @Override
    public void close(int code) {
        heartbeatFutureStop();
        super.close(code);
    }

    @Override
    public void close(int code, String message) {
        heartbeatFutureStop();
        super.close(code, message);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (isAutoReconnect()) {
            try {
                //等三秒后自动重连
                Thread.sleep(3_000);
                this.reconnectBlocking();
            } catch (Exception e) {
                onError(e);
            }
        }
    }

    @Override
    public void onError(Exception ex) {

    }
}
