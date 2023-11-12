package org.java_websocket.utils;


import java.util.concurrent.*;

/**
 * 运行工具
 *
 * @author noear
 * @since 1.12
 */
public class RunUtils {
    /**
     * 调度执行器（一般用于延时任务）
     */
    private static ScheduledExecutorService scheduledExecutor;

    static {
        scheduledExecutor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
    }

    public static void setScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
        if (scheduledExecutor != null) {
            ScheduledExecutorService old = RunUtils.scheduledExecutor;
            RunUtils.scheduledExecutor = scheduledExecutor;
            old.shutdown();
        }
    }

    /**
     * 延迟执行
     */
    public static ScheduledFuture<?> delay(Runnable task, long millis) {
        return scheduledExecutor.schedule(task, millis, TimeUnit.MILLISECONDS);
    }

    /**
     * 延迟执行并重复
     */
    public static ScheduledFuture<?> delayAndRepeat(Runnable task, long millis) {
        return scheduledExecutor.scheduleWithFixedDelay(task, 1000, millis, TimeUnit.MILLISECONDS);
    }
}