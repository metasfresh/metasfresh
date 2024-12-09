@from:cucumber
Feature: Validate Events are correctly sent to RabbitMQ when enqueued via EventBus

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Distributed event is sent to all bound queues
    Given topic is created
      | Identifier | Topic.Name        | Topic.Type  |
      | t_1        | de.metas.cucumber | DISTRIBUTED |

    And rabbitMQ channel is created
      | Identifier |
      | channel_1  |
    And rabbitMQ 'Event' consumer is created
      | Identifier | Channel.Identifier |
      | c_1        | channel_1          |
    And rabbitMQ queue is created
<<<<<<< HEAD
      | Identifier | ExchangeNamePrefix         | Topic.Identifier |
=======
      | Identifier | ExchangeName         | Topic.Identifier |
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
      | q_1        | metasfresh-cucumber-events | t_1              |
    And register consumer for queue
      | Queue.Identifier | Channel.Identifier | Consumer.Identifier |
      | q_1              | channel_1          | c_1                 |

    And rabbitMQ channel is created
      | Identifier |
      | channel_2  |
    And rabbitMQ 'Event' consumer is created
      | Identifier | Channel.Identifier |
      | c_2        | channel_2          |
    And serverBoot local queue is created
<<<<<<< HEAD
      | Identifier | Configuration.Identifier | Topic.Identifier | ExchangeNamePrefix         |
      | q_2        | config_2                 | t_1              | metasfresh-cucumber-events |
=======
      | Identifier | Topic.Identifier | ExchangeName         |
      | q_2        | t_1              | metasfresh-cucumber-events |
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    And register consumer for queue
      | Queue.Identifier | Channel.Identifier | Consumer.Identifier |
      | q_2              | channel_2          | c_2                 |

    And enqueue 'Event'
      | Topic.Identifier | Event.Body        |
      | t_1              | testFromCucumber  |
      | t_1              | testFromCucumber1 |
      | t_1              | testFromCucumber2 |

    Then consumer 'c_1' receives messages
      | Event.body        |
      | testFromCucumber  |
      | testFromCucumber1 |
      | testFromCucumber2 |
    And consumer 'c_2' receives messages
      | Event.body        |
      | testFromCucumber  |
      | testFromCucumber1 |
      | testFromCucumber2 |

    And channel is closed
      | Channel.Identifier |
      | channel_1          |
      | channel_2          |
    And deregister queue for topic
      | Topic.Identifier |
      | t_1              |

  @from:cucumber
  Scenario: Local event is sent only to local queues
    Given topic is created
      | Identifier | Topic.Name        | Topic.Type |
      | t_1        | de.metas.cucumber | LOCAL      |

    And rabbitMQ channel is created
      | Identifier |
      | channel_1  |
    And rabbitMQ 'Event' consumer is created
      | Identifier | Channel.Identifier |
      | c_1        | channel_1          |
    And rabbitMQ queue is created
<<<<<<< HEAD
      | Identifier | ExchangeNamePrefix         | Topic.Identifier |
=======
      | Identifier | ExchangeName         | Topic.Identifier |
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
      | q_1        | metasfresh-cucumber-events | t_1              |
    And register consumer for queue
      | Queue.Identifier | Channel.Identifier | Consumer.Identifier |
      | q_1              | channel_1          | c_1                 |

    And rabbitMQ channel is created
      | Identifier |
      | channel_2  |
    And rabbitMQ 'Event' consumer is created
      | Identifier | Channel.Identifier |
      | c_2        | channel_2          |
    And serverBoot local queue is created
<<<<<<< HEAD
      | Identifier | Topic.Identifier | ExchangeNamePrefix         |
=======
      | Identifier | Topic.Identifier | ExchangeName         |
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
      | q_2        | t_1              | metasfresh-cucumber-events |
    And register consumer for queue
      | Queue.Identifier | Channel.Identifier | Consumer.Identifier |
      | q_2              | channel_2          | c_2                 |

    And enqueue 'Event'
      | Topic.Identifier | Event.Body        |
      | t_1              | testFromCucumber  |
      | t_1              | testFromCucumber1 |
      | t_1              | testFromCucumber2 |

    Then consumer c_1 receives no message within the next 5s
    And consumer 'c_2' receives messages
      | Event.body        |
      | testFromCucumber  |
      | testFromCucumber1 |
      | testFromCucumber2 |

    And channel is closed
      | Channel.Identifier |
      | channel_1          |
      | channel_2          |