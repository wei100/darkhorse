package com.tw.exam.darkhorse.infrastructure.repository;

import com.tw.exam.darkhorse.domain.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Order findByAddressId(Long id);
}
