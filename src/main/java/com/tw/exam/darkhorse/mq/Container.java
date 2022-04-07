package com.tw.exam.darkhorse.mq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Container {

    private static final BlockingQueue<Long> queue = new ArrayBlockingQueue(1024);

    public static Long take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static boolean add(Long orderId) {
        return queue.add(orderId);
    }
}
