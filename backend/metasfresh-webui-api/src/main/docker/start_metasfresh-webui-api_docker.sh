#!/bin/bash

set -e

# postgres
db_host=${DB_HOST:-db}
db_port=${DB_PORT:-5432}
db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-metasfresh}
db_connection_pool_max_size=${DB_CONNECTION_POOL_MAX_SIZE:-UNSET}

# elastic search
es_host=${ES_HOST:-search}
es_port=${ES_PORT:-9300}

# rabbitmq
rabbitmq_host=${RABBITMQ_HOST:-localhost}
rabbitmq_port=${RABBITMQ_PORT:-5672}
rabbitmq_user=${RABBITMQ_USER:-guest}
rabbitmq_password=${RABBITMQ_PASSWORD:-$(echo $secret_rabbitmq_password)}

# metasfresh-admin
admin_url=${METASFRESH_ADMIN_URL:-NONE}

# app
app_host=${APP_HOST:-app}

# webui
webui_frontend_url=${WEBUI_FRONTEND_URL:-NONE}

echo_variable_values()
{
 echo "Note: all these variables can be set using the -e parameter."
 echo ""
 echo "DB_HOST=${db_host}"
 echo "DB_PORT=${db_port}"
 echo "DB_NAME=${db_name}"
 echo "DB_USER=${db_user}"
 echo "DB_PASSWORD=*******"
 echo "DB_CONNECTION_POOL_MAX_SIZE=${db_connection_pool_max_size}"
 echo ""
 echo "RABBITMQ_HOST=${rabbitmq_host}"
 echo "RABBITMQ_PORT=${rabbitmq_port}"
 echo "RABBITMQ_USER=${rabbitmq_user}"
 echo "RABBITMQ_PASSWORD=*******"
 echo ""
 echo "ES_HOST=${es_host}"
 echo "ES_PORT=${es_port}"
 echo "METASFRESH_ADMIN_URL=${admin_url}"
 echo "APP_HOST=${app_host}"
 echo "WEBUI_FRONTEND_URL=${webui_frontend_url}"
}


set_properties()
{
 echo "set_properties BEGIN"
 local prop_file="$1"
 if [[ $(cat $prop_file | grep FOO | wc -l) -ge "1" ]]; then
	sed -Ei "s/FOO_DBMS_HOST/${db_host}/g" $prop_file
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
 until nc -z ${db_host} ${db_port}
 do
   sleep 1
 done
}

# Note: the Djava.security.egd param is supposed to let tomcat start quicker, see https://spring.io/guides/gs/spring-boot-docker/
run_metasfresh()
{
 if [ "$db_connection_pool_max_size" != "UNSET" ];
 then
 	metasfresh_db_connectionpool_params="-Dc3p0.maxPoolSize=${db_connection_pool_max_size}"
 else 
	metasfresh_db_connectionpool_params=""
 fi

 if [ "$admin_url" != "NONE" ]; 
 then
	# see https://codecentric.github.io/spring-boot-admin/1.5.0/#spring-boot-admin-client
	# spring.boot.admin.client.prefer-ip=true because within docker, the hostname is no help
	metasfresh_admin_params="-Dspring.boot.admin.url=${admin_url} -Dmanagement.security.enabled=false -Dspring.boot.admin.client.prefer-ip=true"
 else
	metasfresh_admin_params=""
 fi

 if [ "$webui_frontend_url" != "NONE" ]; 
 then
	webui_frontend_url_params="-Dwebui.frontend.url=${webui_frontend_url}"
 else
	webui_frontend_url_params=""
 fi

 local es_params="-Dspring.data.elasticsearch.cluster-nodes=${es_host}:${es_port}"
 
 local rabbitmq_params="-Dspring.rabbitmq.host=${rabbitmq_host}\
 -Dspring.rabbitmq.port=${rabbitmq_port}\
 -Dspring.rabbitmq.username=${rabbitmq_user}\
 -Dspring.rabbitmq.password=${rabbitmq_password}"

 # thx to https://medium.com/adorsys/jvm-memory-settings-in-a-container-environment-64b0840e1d9e
 #local MEMORY_PARAMS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAM=$(( $(cat /sys/fs/cgroup/memory/memory.limit_in_bytes) * 100 / 70 )) -XX:MaxRAMFraction=1"
 local MEMORY_PARAMS="-Xmx512M"

 cd /opt/metasfresh/metasfresh-webui-api/ \
 && java \
 ${MEMORY_PARAMS} \
 -XX:+HeapDumpOnOutOfMemoryError \
 -Dsun.misc.URLClassPath.disableJarChecking=true \
 ${es_params} \
 ${rabbitmq_params} \
 ${metasfresh_admin_params} \
 ${metasfresh_db_connectionpool_params}\
 ${webui_frontend_url_params} \
 -DPropertyFile=/opt/metasfresh/metasfresh-webui-api/metasfresh.properties \
 -Djava.security.egd=file:/dev/./urandom \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8789 \
 org.springframework.boot.loader.JarLauncher \
 || ( echo "java returned with exit code $?; sleeping for 30m" && sleep 30m )

}

echo_variable_values

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
