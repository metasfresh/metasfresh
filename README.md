# metasfresh-esb-camel

## How to deploy
The is a spring boot camel project. Please follow the following steps to build and deploy with docker and kubernetes:
1. Build the project with maven

    `mvn clean install`
2. Create docker image

    `docker build -t metasfresh-esb-camel .`
3. Go to kubernetes/local 
4. Check the properties in [esb-camel.properties](./kubernetes/local/esb-camel.properties) and change according to your environment
   Note: In the existing one, it is assumed that there is a service named `metasfresh-rabbitmq-mf15-dev` in kubernetes
5. Create a config map with name `esb-camel-properties` from the [esb-camel.properties](./kubernetes/local/esb-camel.properties) file

    `kubectl create configmap esb-camel-properties --from-env-file=esb-camel.properties`
6. Create `metasfresh-esb-camel` service

    `kubectl create -f metasfresh-esb-camel-svc_local.yaml`
7. Create `metasfresh-esb-camel` deployment

    `kubectl create -f metasfresh-esb-camel-deploy_local.yaml`
