package com.tw.exam.darkhorse.infrastructure.adapter;

import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import com.tw.exam.darkhorse.mq.MessageQueue;
import org.springframework.stereotype.Component;

@Component
public class OrderRefundAdapter {

    public boolean pushOrderRefund(OrderRefund refund) {
        return MessageQueue.add(refund);
    }
}
