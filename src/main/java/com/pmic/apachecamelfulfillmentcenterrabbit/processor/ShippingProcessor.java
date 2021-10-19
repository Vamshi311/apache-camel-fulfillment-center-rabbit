package com.pmic.apachecamelfulfillmentcenterrabbit.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.pmic.apachecamelfulfillmentcenterrabbit.domain.OrderDto;
import com.pmic.apachecamelfulfillmentcenterrabbit.entity.OrderStatus;
import com.pmic.apachecamelfulfillmentcenterrabbit.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShippingProcessor implements Processor {

	private final OrderRepository orderRepository;

	public ShippingProcessor(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		List<OrderDto> orders = exchange.getIn().getBody(List.class);
		log.info("Received {} via rabbit event process", orders);
		List<Long> orderIds = orders.stream().map(o -> o.getId()).collect(Collectors.toList());
		orderRepository.upadteOrderStatus(orderIds, OrderStatus.PROCESSED);
		log.info("Finished processing orders from rabbit");
	}
}
