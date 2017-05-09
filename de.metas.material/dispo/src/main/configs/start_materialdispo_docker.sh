#!/bin/bash

set -e

DB_HOST=db
APP_HOST=app

set_properties()
{
 local prop_file="$1"
 if [[ $(cat $prop_file | grep FOO | wc -l) -ge "1" ]]; then
	sed -Ei "s/FOO_DBMS/$DB_HOST/g" $prop_file
	sed -Ei "s/FOO_APP/$APP_HOST/g" $prop_file
 fi
}

wait_dbms()
{
 until nc -z $DB_HOST 5432
 do
   sleep 1
 done
}

# Note: the Djava.security.egd param is supposed to let tomcat start quicker, see https://spring.io/guides/gs/spring-boot-docker/
run_metasfresh()
{
 cd /opt/metasfresh-webui-api/ && java -Dsun.misc.URLClassPath.disableJarChecking=true \
 -Xmx512M -XX:+HeapDumpOnOutOfMemoryError \
 -DPropertyFile=/opt/metasfresh/metasfresh-material-dispo/metasfresh.properties \
 -Djava.security.egd=file:/dev/./urandom \
 -Dspring.boot.admin.url=http://localhost:9090 \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8790 \
 -jar metasfresh-material-dispo.jar
}

set_properties /opt/metasfresh/metasfresh-material-dispo/metasfresh.properties

wait_dbms
run_metasfresh

exit 0 
