# What it does

This camel component connects to metasfresh's shipment and receipt REST API, 
converts this between JSON (metasfresh) to XML and reads/writes that XML to disk and a remote directory.

# How to deploy it

## docker-compose v1 fragment:

```yml
shipping:
  build: shipping
  hostname: shipping
  mem_limit: 256m
  links:
    - app:app
  volumes:
    - /etc/localtime:/etc/localtime:ro
    - /etc/timezone:/etc/timezone:ro
    - ./volumes/shipping/resources:/app/resources:ro
    - ./volumes/shipping/output/xml:/app/output/xml:rw
    - ./volumes/shipping/heapdump:/app/heapdump:rw
  environment:
    JAVA_TOOL_OPTIONS: "-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/app/heapdump -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8794"
  restart: always
```

## Sample Dockerfile

According to the `docker-compose.yml` above, the Dockerfile shall be placed in `<base-directory>/shipping`

```
FROM nexus.metasfresh.com:6001/metasfresh/de-metas-esb-shipmentschedule-camel:5.147.2_54_gh7012shipmentschedulerestapi
```

The actually correct image can be found the respective jenkins build, e.g. https://jenkins.metasfresh.com/job/metasfresh/job/master/

## Config-Files

According to the `docker-compose.yml` above, a customized `shipping.properties` and `log4j2.properties`can be placed in `<base-directory>/volumes/shipping/resources`.

To persist XML files on the docker-host, the `shipping.properties`'s `local.file.output_path` 
property has to be `/app/output/xml` or a subfolder within `/app/output/xml`.

Note that camel will generate all the required folders on the fly.
