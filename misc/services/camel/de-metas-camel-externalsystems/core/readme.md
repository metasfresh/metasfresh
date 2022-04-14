
### Metasfresh => Camel External System Dispatcher

 - the entry point for any message is the `rabbitMQ` camel route consumer defined as: `rabbitmq:MF_TO_ExternalSystem`, with the following options:
   - `durable=true`
   - `autoDelete=false`
   - `prefetchCount=1`
     - *The maximum number of messages that the server will deliver* 
     - `prefetchEnabled=true` (mandatory complementary option to `prefetchCount`):
       - *Enables the quality of service (QOS) on the RabbitMQConsumer side* 
     - `prefetchGlobal=false` (mandatory complementary option to `prefetchCount`):
       - *If the settings should be applied to the entire channel rather than each consumer* 
     - `prefetchSize=0` (mandatory complementary option to `prefetchCount`): 
       - *The maximum amount of content (measured in octets) that the server will deliver, 0 if unlimited* 
   - `routingKey=MF_TO_ExternalSystem`
   - `queue=MF_TO_ExternalSystem`
 - the message is then routed to the appropriate "external system" route in an async manner:
   - the `asynchronicity` is ensured via a `SEDA` camel component, offering the following options:
     - the `SEDA queue` capacity (i.e. the number of messages it can hold) can be configured by setting the `dispatcher.seda.queueSize` property
     - the number of threads consuming the `SEDA queue` can be configured by setting the `dispatcher.seda.concurrentConsumers` property
       - *NOTE: the thread pool size is limited to 500 according to camel documentation*
     - the behaviour on `"SEDA queue is full"` can be configured by setting the `dispatcher.seda.blockWhenFull` property
       - `true` => means, the thread adding messages to queue will wait if the queue is full
       - `false` => there will be an error thrown in the thread adding to a full queue