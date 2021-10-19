package com.pmic.apachecamelfulfillmentcenterrabbit.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.springframework.stereotype.Component;

import com.pmic.apachecamelfulfillmentcenterrabbit.domain.OrderDto;
import com.pmic.apachecamelfulfillmentcenterrabbit.processor.ShippingProcessor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipRoutes extends RouteBuilder {

	private final ShippingProcessor shippingProcessor;

	public ShipRoutes(ShippingProcessor shippingProcessor) {
		this.shippingProcessor = shippingProcessor;
	}

	@Override
	public void configure() throws Exception {
		JacksonDataFormat orderListformat = new ListJacksonDataFormat(OrderDto.class);
		from("{{ship-route}}").log("Received orders by fulfillment center rabbit").unmarshal(orderListformat)
				.process(shippingProcessor);
	}
}
