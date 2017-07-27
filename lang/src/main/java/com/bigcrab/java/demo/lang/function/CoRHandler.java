package com.bigcrab.java.demo.lang.function;

import java.util.Objects;

/**
 * 职责链模式事件处理接口
 *
 * @author sky
 */
@FunctionalInterface
public interface CoRHandler<T> {

    /**
     * 处理事务的方法，该 Handler 能够处理该 event，返回 true；否则返回 false
     *
     */
    boolean handle(T event);

    /**
     * 职责链中，如果靠前的 Handler 能够处理某事件，则不需要后续的 Handler 继续处理该事件。
     * 那么应该用 or 连接整条职责链。
     */
    default CoRHandler<T> or(CoRHandler<? super T> after) {
        Objects.requireNonNull(after);
        return (event) -> handle(event) || after.handle(event);
    }

}