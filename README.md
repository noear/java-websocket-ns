# java-websocket-ns


org.java-websocket 是优秀的框架。在此基础，加了点小便利：简化，心跳，自动重连，心跳定制


* 引入依赖

```xml
<dependency>
    <groupId>org.noear</groupId>
    <artifactId>java-websocket-ns</artifactId>
    <version>1.1</version>
</dependency>
```

* 代码示例

提醒：客户端的关闭使用 `release()` 替代 `close()`。`release()` 会同时停止心跳与自动重连！

```java
public class Demo01 {
    public static void main(String[] args) throws Exception {
        //::启动服务端
        SimpleWebSocketServer server = new SimpleWebSocketServer(18080){
            //需要什么方法，就重写什么
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
            //需要什么方法，就重写什么
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
            }
        };

        //开始连接
        client.connectBlocking(10, TimeUnit.SECONDS);
        //定制心跳（可选）
        //client.heartbeatHandler(new HeartbeatHandlerDefault());
        //开始心跳 + 心跳时自动重连
        client.heartbeat(20_000, true);

        //发送测试
        client.send("test");

        //休息会儿
        Thread.sleep(1000);

        //关闭
        client.close();
        server.stop();
    }
}
```
