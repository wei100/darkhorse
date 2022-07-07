package com.tw.exam.darkhorse.adapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tw.exam.darkhorse.domain.model.OrderRefundModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class RedEnvelopePayAdapter implements PaymentAdapter {

    private String url = "https://624ea73d77abd9e37c893fac.mockapi.io/api/v1/redEnvelope";

    @Autowired
    RestTemplate restTemplate;

    public List<OrderRefundModel> getRedEnvelopes() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<>(headers);

        String body = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        Gson gson = new Gson();
        return gson.fromJson(body, new TypeToken<List<OrderRefundModel>>(){}.getType());
    }

    public OrderRefundModel updateRedEnvelopes(OrderRefundModel orderRefundModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<OrderRefundModel> entity = new HttpEntity<OrderRefundModel>(orderRefundModel, headers);
        String body = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        Gson gson = new Gson();
        return gson.fromJson(body, OrderRefundModel.class);
    }
}
