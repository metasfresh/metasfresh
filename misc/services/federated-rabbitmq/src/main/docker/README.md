
This project makes sure that we can have two rabbitmq instances that
* share federated queues
* communicate with each other encrypted.

Note about the nginx containers:
* i wanted to have them talk TLS directly without a terminating proxy, but failed
* see https://groups.google.com/g/rabbitmq-users/c/qFJBrg8PrYM/m/Xps5Nb9pAQAJ
