package com.tw.exam.darkhorse.mq;

import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {

    private static final BlockingQueue<OrderRefund> queue = new ArrayBlockingQueue(1024);

    public static OrderRefund take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean add(OrderRefund orderRefund) {
        return queue.add(orderRefund);
    }
}
