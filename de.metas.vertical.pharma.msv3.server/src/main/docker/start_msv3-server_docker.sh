#!/bin/bash
set -e

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement
db_host=${DB_HOST:-db}
db_port=${DB_PORT:-5432}
db_name=${DB_NAME:-msv3server}
db_user=${DB_USER:-msv3server}
db_password=${DB_PASSWORD:-msv3server}
db_wait_for_dbms=${DB_WAIT_FOR_DBMS:-y}

rabbitmq_host=${RABBITMQ_HOST:-localhost}
rabbitmq_port=${RABBITMQ_PORT:-5672}
rabbitmq_user=${RABBITMQ_USER:-guest}
rabbitmq_password=${RABBITMQ_PASSWORD:-$(echo $secret_rabbitmq_password)}

debug_port=${DEBUG_PORT:-8791}
debug_suspend=${DEBUG_SUSPEND:-n}
debug_print_bash_cmds=${DEBUG_PRINT_BASH_CMDS:-n}
debug_jpa_show_sql=${DEBUG_JPA_SHOW_SQL:-false}
admin_url=${METASFRESH_ADMIN_URL:-NONE}
java_max_heap=${JAVA_MAX_HEAP:-128M}
server_port=${SERVER_PORT:-8080}
request_all_data_on_startup=${SERVER_REQUEST_ALL_DATA_ON_STARTUP:-true}

echo_variable_values()
{
 echo "*************************************************************"
 echo "Display the variable values we run with"
 echo "*************************************************************"
 echo "Note: all these variables can be set using the -e parameter."
 echo ""
 echo "DEBUG_PORT=${debug_port}"
 echo "DEBUG_SUSPEND=${debug_suspend}"
 echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}"
 echo "METASFRESH_ADMIN_URL=${admin_url}"
 echo "JAVA_MAX_HEAP=${java_max_heap}"
 echo "SERVER_PORT=${server_port}"
 echo "SERVER_REQUEST_ALL_DATA_ON_STARTUP=${request_all_data_on_startup}"
 
 echo "RABBITMQ_HOST=${rabbitmq_host}"
 echo "RABBITMQ_PORT=${rabbitmq_port}"
 echo "RABBITMQ_USER=${rabbitmq_user}"
 echo "RABBITMQ_PASSWORD=*******"

 echo "DB_HOST=${db_host}"
 echo "DB_PORT=${db_port}"
 echo "DB_NAME=${db_name}"
 echo "DB_USER=${db_user}"
 echo "DB_PASSWORD=*******"
}

# Note: the Djava.security.egd param is supposed to let tomcat start quicker, see https://spring.io/guides/gs/spring-boot-docker/
run_metasfresh()
{
 if [ "$admin_url" != "NONE" ]; 
 then
	# see https://codecentric.github.io/spring-boot-admin/1.5.0/#spring-boot-admin-client
	# spring.boot.admin.client.prefer-ip=true because within docker, the hostname is no help
	metasfresh_admin_params="-Dspring.boot.admin.url=${admin_url} -Dmanagement.security.enabled=false -Dspring.boot.admin.client.prefer-ip=true"
 else
	metasfresh_admin_params=""
 fi

 cd /opt/metasfresh-msv3-server/\
 && java\
 -XX:+HeapDumpOnOutOfMemoryError\
 ${metasfresh_admin_params}\
 -Xmx${java_max_heap}\
 -Dsun.misc.URLClassPath.disableJarChecking=true\
 -Djava.security.egd=file:/dev/./urandom\
 -Dserver.port=${server_port}\
 -Dspring.rabbitmq.host=${rabbitmq_host}\
 -Dspring.rabbitmq.port=${rabbitmq_port}\
 -Dspring.rabbitmq.username=${rabbitmq_user}\
 -Dspring.rabbitmq.password=${rabbitmq_password}\
 -Dspring.datasource.url=jdbc:postgresql://${db_host}/${db_name}\
 -Dspring.datasource.username=${db_user}\
 -Dspring.datasource.password=${db_password}\
 -Dspring.jpa.hibernate.ddl-auto=create-drop\
 -Dspring.jpa.show-sql=${debug_jpa_show_sql}\
 -Dmsv3server.startup.requestAllData=${SERVER_REQUEST_ALL_DATA_ON_STARTUP}
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=${debug_suspend},address=${debug_port}\
 -jar metasfresh-pharma.msv3.server.jar
}

echo "*********************************"
echo " Starting metasfresh-msv3-server ";
echo "*********************************"

# start printing all bash commands from here onwards, if activated
if [ "$debug_print_bash_cmds" != "n" ];
then
	echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}, so from here we will output all bash commands; set to n (just the lowercase letter) to skip this."
	set -x
fi

run_metasfresh
exit 0 
