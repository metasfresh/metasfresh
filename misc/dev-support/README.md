# dev-support

This folder contains resources intended to help metasfresh developers:
* all our [eclipse config files](https://github.com/metasfresh/metasfresh-dev/tree/master/eclipse-config) (formatter, code cleanup, templates etc) and a documentation on how to set import them into eclipse
* intellij runConfigurations and other configs
* `application.properties` and other config files to set ports and stuff when running metasfresh parts locally
* docker-compose files to run "infrastructure" (postgresql, rabbitmq, elk-stask etc)
* a maven `settings.xml` file to access a maven repo with 3rd-party libs that are not found in maven-central
* helpful sqls


### Rabbitmq Config for Imp_Processor to reduce connection errors in log

```sql
UPDATE IMP_Processor 
SET port = 5672, -- or port from used env-file
    account = 'guest', 
    passwordinfo = 'guest' 
WHERE TRUE;
```