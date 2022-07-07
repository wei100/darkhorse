package com.tw.exam.darkhorse.api.request;

import com.sun.istack.NotNull;
import com.tw.exam.darkhorse.domain.model.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private String price;

    @NotNull
    private Long addressId;

    @NotNull
    private Long goodsId;

    @NotNull
    @Pattern(regexp = "(wechat|alipay|redenvelopepay|unionpay)")
    private String payType;

    public OrderModel of() {
        return OrderModel.builder()
                .price(price)
                .addressId(addressId)
                .goodsId(goodsId)
                .build();
    }
}
