#!/bin/bash

set -e

# postgres
db_host=${DB_HOST:-db}
db_port=${DB_PORT:-5432}
db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-metasfresh}
db_connection_pool_max_size=${DB_CONNECTION_POOL_MAX_SIZE:-UNSET}

# metasfresh-admin
admin_url=${METASFRESH_ADMIN_URL:-NONE}

# app
app_host=${APP_HOST:-app}

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
 echo "METASFRESH_ADMIN_URL=${admin_url}"
 echo "APP_HOST=${app_host}"
}

set_properties()
{
 echo "set_properties BEGIN"
 local prop_file="$1"
 if [[ $(cat $prop_file | grep FOO | wc -l) -ge "1" ]]; then
	sed -Ei "s/FOO_DBMS/$DB_HOST/g" $prop_file
	sed -Ei "s/FOO_APP/$APP_HOST/g" $prop_file
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

 # thx to https://blog.csanchez.org/2017/05/31/running-a-jvm-in-a-container-without-getting-killed/
 local MEMORY_PARAMS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1"

 cd /opt/metasfresh/metasfresh-material-dispo/ && java -Dsun.misc.URLClassPath.disableJarChecking=true \
 ${MEMORY_PARAMS} \
 -XX:+HeapDumpOnOutOfMemoryError \
 -DPropertyFile=/opt/metasfresh/metasfresh-material-dispo/metasfresh.properties \
 -Djava.security.egd=file:/dev/./urandom \
 ${metasfresh_db_connectionpool_params} \
 ${metasfresh_admin_params} \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8790 \
 -jar metasfresh-material-dispo.jar
}

set_properties /opt/metasfresh/metasfresh-material-dispo/metasfresh.properties

echo "*********************************************************"
echo "Waiting for the database server to start on host $DB_HOST"
echo "*********************************************************"
wait_dbms
echo ">>>>>>>>>>>> Database Server has started"

echo "***********************************"
echo "Starting metasfresh-material-dispo ";
echo "***********************************"
run_metasfresh

exit 0 
