package com.example.retailer.adapter

class DistributorPublisherImpl : DistributorPublisher {

    @Autowired
    private lateinit var template: RabbitTemplate

    @Autowired
    private lateinit var topic: TopicExchange

    override fun placeOrder(order: Order): Boolean {
        val objectMapper = ObjectMapper()
        val message = objectMapper.writeValueAsString(order)
        if (order.id != null) {
            template.convertAndSend(
                topic.name,
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