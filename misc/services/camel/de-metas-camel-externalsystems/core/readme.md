
### Metasfresh => Camel External System Dispatcher

- the entry point for any message is the `rabbitMQ` camel route consumer defined as: `rabbitmq:MF_TO_ExternalSystem`, with the following options:
    - `durable=true`
    - `autoDelete=false`
    - `autoAck=false`
      - messages will only be acknowledged when the camel route is done processing the exchange.
    - `concurrentConsumers=4`
      - the number of concurrent consumers the camel component will use when consuming from RabbitMQ.
    - `threadPoolSize=4`
      - the consumers use a Thread Pool Executor with a fixed number of threads. This setting specifies that number.
    - `routingKey=MF_TO_ExternalSystem`
    - `queue=MF_TO_ExternalSystem`
- the message is then routed to the appropriate "external system" route
