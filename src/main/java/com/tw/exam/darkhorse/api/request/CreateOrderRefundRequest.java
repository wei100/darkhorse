package com.tw.exam.darkhorse.api.request;

import com.sun.istack.NotNull;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import com.tw.exam.darkhorse.domain.model.OrderRefundModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRefundRequest {

    @NotNull
    @DecimalMax("100.0") @DecimalMin("1.0")
    private Double price;

    @NotNull
    private Long orderId;

    public OrderRefundModel of() {
        return OrderRefundModel.builder()
                .price(price)
                .orderId(orderId)
                .status("returning")
                .build();
    }
}
