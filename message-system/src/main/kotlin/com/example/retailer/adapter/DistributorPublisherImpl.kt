package com.example.retailer.adapter

import com.example.retailer.api.distributor.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Message
import com.example.retailer.AmqpConfiguration.Companion.DISTRIBUTOR_EXCHANGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired

class DistributorPublisherImpl : DistributorPublisher {
    @Autowired
    private lateinit var template: RabbitTemplate


    override fun placeOrder(order: Order): Boolean {
        val objectMapper = ObjectMapper()
        val message = objectMapper.writeValueAsString(order)
        if (order.id != null) {
            template.convertAndSend(
                DISTRIBUTOR_EXCHANGE,
                "distributor.placeOrder.elvina-ganieva.${order.id}",
                message
            ) { m: Message ->
                m.messageProperties.headers["Notify-Exchange"] = "distributor_exchange"
                m.messageProperties.headers["Notify-RoutingKey"] = "retailer.elvina-ganieva"
                m
            }
            return true
        } else
            return false
    }
}