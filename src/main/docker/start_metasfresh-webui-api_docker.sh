#!/bin/bash

set -e

# postgres
db_host=${DB_HOST:-db}
db_port=${DB_PORT:-5432}
db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-metasfresh}

# elastic search
es_host=${ES_HOST:-search}
es_port=${ES_PORT:-9300}

# app
app_host=${APP_HOST:-app}

set_properties()
{
 echo "set_properties BEGIN"
 local prop_file="$1"
 if [[ $(cat $prop_file | grep FOO | wc -l) -ge "1" ]]; then
	sed -Ei "s/FOO_DBMS/${db_host}/g" $prop_file
	sed -Ei "s/FOO_DBMS_PORT/${db_port}/g" $prop_file
	sed -Ei "s/FOO_DB_NAME/${db_name}/g" $prop_file
	sed -Ei "s/FOO_DB_USER/${db_user}/g" $prop_file
	sed -Ei "s/FOO_DB_PASSWORD/${db_password}/g" $prop_file
	sed -Ei "s/FOO_APP/${app_host}/g" $prop_file
 fi
 echo "set_properties END"
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
 cd /opt/metasfresh/metasfresh-webui-api/ \
 && java \
 -Xmx512M \
 -XX:+HeapDumpOnOutOfMemoryError \
 -Dsun.misc.URLClassPath.disableJarChecking=true \
 -Dspring.data.elasticsearch.cluster-nodes=${es_host}:${es_port} \
 -DPropertyFile=/opt/metasfresh/metasfresh-webui-api/metasfresh.properties \
 -Djava.security.egd=file:/dev/./urandom \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8789 \
 -jar metasfresh-webui-api.jar
}

set_properties /opt/metasfresh/metasfresh-webui-api/metasfresh.properties

echo "************************************************************"
echo "Waiting for the database server to start on DB_HOST=$DB_HOST"
echo "************************************************************"
wait_dbms
echo ">>>>>>>>>>>> Database Server has started"

echo "*****************************"
echo "Starting metasfresh-webui-api"
echo "*****************************"
run_metasfresh

exit 0 
