package com.tw.exam.darkhorse.infrastructure.repository;

import com.tw.exam.darkhorse.infrastructure.repository.entity.OrderRefund;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRefundRepository extends CrudRepository<OrderRefund, Long> {

}
