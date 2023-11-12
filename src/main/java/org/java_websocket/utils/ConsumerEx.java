package org.java_websocket.utils;

/**
 * @author noear
 * @since 1.0
 */
@FunctionalInterface
public interface ConsumerEx<T> {
    void accept(T t) throws Exception;
}