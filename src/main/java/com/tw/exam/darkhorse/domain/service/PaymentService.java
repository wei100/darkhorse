package com.tw.exam.darkhorse.domain.service;


import com.tw.exam.darkhorse.adapter.RedEnvelopePayAdapter;
import com.tw.exam.darkhorse.api.exception.BusinessException;
import com.tw.exam.darkhorse.api.exception.ErrorCode;
import com.tw.exam.darkhorse.domain.model.RedEnvelope;
import com.tw.exam.darkhorse.mq.Container;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final OrderService orderService;

    private final RedEnvelopePayAdapter redEnvelopePayAdapter;

    public boolean payOrder(Long orderId) {
        orderService.getOrderById(orderId);
        boolean payStatus;
        try {
            List<RedEnvelope> redEnvelopes = redEnvelopePayAdapter.getRedEnvelopes();
            RedEnvelope redEnvelope = redEnvelopes.get(0);
            redEnvelope.setStatus("used");
            RedEnvelope returnData = redEnvelopePayAdapter.updateRedEnvelopes(redEnvelope);
            payStatus = returnData != null;
        } catch (Exception e) {
            payStatus = processFailurePay(orderId);
        }
        if(!payStatus) {
            throw new BusinessException(ErrorCode.PAYMENT_FAILURE);
        }
        return payStatus;
    }

    public boolean processFailurePay(Long orderId) {
        return Container.add(orderId);
    }
}
