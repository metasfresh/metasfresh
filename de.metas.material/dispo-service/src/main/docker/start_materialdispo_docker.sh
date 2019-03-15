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

# debug
debug_port=${DEBUG_PORT:-8790}
debug_suspend=${DEBUG_SUSPEND:-n}
debug_print_bash_cmds=${DEBUG_PRINT_BASH_CMDS:-n}

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
 echo "DEBUG_PORT=${debug_port}"
 echo "DEBUG_SUSPEND=${debug_suspend}"
 echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}"
 echo "METASFRESH_ADMIN_URL=${admin_url}"
 echo "APP_HOST=${app_host}"
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

 # thx to https://blog.csanchez.org/2017/05/31/running-a-jvm-in-a-container-without-getting-killed/
# MaxRAMFraction=1 doesn't any emory for anything else and might cause the OS to kill the java process
# local MEMORY_PARAMS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1"
local MEMORY_PARAMS="-Xmx512M"

 # note that we assume the spring-boot uber jar was exploded into the docker image

 cd /opt/metasfresh/metasfresh-material-dispo/ && java -Dsun.misc.URLClassPath.disableJarChecking=true \
 ${MEMORY_PARAMS}\
 -XX:+HeapDumpOnOutOfMemoryError\
 -DPropertyFile=/opt/metasfresh/metasfresh-material-dispo/metasfresh.properties\
 -Djava.security.egd=file:/dev/./urandom\
 ${metasfresh_db_connectionpool_params}\
 ${metasfresh_admin_params}\
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=${debug_suspend},address=${debug_port}\
 org.springframework.boot.loader.JarLauncher
}

echo_variable_values

# start printing all bash commands from here onwards, if activated
if [ "$debug_print_bash_cmds" != "n" ];
then
	echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}, so from here we will output all bash commands; set to n (just the lowercase letter) to skip this."
	set -x
fi

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
