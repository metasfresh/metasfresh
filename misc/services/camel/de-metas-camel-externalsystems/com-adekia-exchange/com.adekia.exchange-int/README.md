# Adekia Exchange interface

Interface module for Adekia Exchange Project
- Context Provider : Provider of context properties for exchange flow 
- Provider : Input flow data provider
- Sender : Output flow data sender
- Transformer : Model transformer from BUL model to sender data model specifications

## Implementation Example : GetOrders
### Provider 

Create your new Service provider Implements GetOrdersProvider

Annote your service with springboot annotations :

```java
@Service
@ConditionalOnProperty(prefix = "order", name = "provider", havingValue = "myNewService")
```

Adjust application.properties with myNewService value

```properties
order.provider=myNewService
```

### Provider

Create your new Service provider Implements OrderSender

Annote your service with springboot annotations :

```java
@Service
@ConditionalOnProperty(prefix = "order", name = "sender", havingValue = "myNewService")
```

Adjust application.properties with myNewService value

```properties
order.sender=myNewService
```

#### Transformer
Create three new services transformer Implements :
- OrderTransformer
- OrderBPTransformer
- OrderPaymentTransformer

Annote your services with springboot annotations :

```java
@Component
@ConditionalOnExpression(value = "'${shipment.sender}' matches '.*myNewService.*'")
```

## Author


