#!/bin/bash
set -e

rabbitmq_host=${RABBITMQ_HOST:-localhost}
rabbitmq_port=${RABBITMQ_PORT:-5672}
rabbitmq_user=${RABBITMQ_USER:-guest}
rabbitmq_password=${RABBITMQ_PASSWORD:-$(echo $secret_rabbitmq_password)}

debug_port=${DEBUG_PORT:-8792}
debug_suspend=${DEBUG_SUSPEND:-n}
debug_print_bash_cmds=${DEBUG_PRINT_BASH_CMDS:-n}
admin_url=${METASFRESH_ADMIN_URL:-NONE}
server_port=${SERVER_PORT:-8184}

java_max_heap=${JAVA_MAX_HEAP:-128M}


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
 echo "SERVER_PORT=${server_port}"
 echo "JAVA_MAX_HEAP=${java_max_heap}"
 echo ""
 echo "RABBITMQ_HOST=${rabbitmq_host}"
 echo "RABBITMQ_PORT=${rabbitmq_port}"
 echo "RABBITMQ_USER=${rabbitmq_user}"
 echo "RABBITMQ_PASSWORD=*******"
 echo ""
}

# Note: the Djava.security.egd param is supposed to let tomcat start quicker, see https://spring.io/guides/gs/spring-boot-docker/
run_edi()
{
 if [ "$admin_url" != "NONE" ]; 
 then
	# see https://codecentric.github.io/spring-boot-admin/1.5.0/#spring-boot-admin-client
	# spring.boot.admin.client.prefer-ip=true because within docker, the hostname is no help
	metasfresh_admin_params="-Dspring.boot.admin.url=${admin_url} -Dmanagement.security.enabled=false -Dspring.boot.admin.client.prefer-ip=true"
 else
	metasfresh_admin_params=""
 fi

 cd /opt/metasfresh-esb-camel/\
 && java\
 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/metasfresh-esb-camel/heapdump \
 ${metasfresh_admin_params}\
 -Xmx${java_max_heap}\
 -Dsun.misc.URLClassPath.disableJarChecking=true\
 -Djava.security.egd=file:/dev/./urandom\
 -Dserver.port=${server_port}\
 -Dcamel.component.rabbitmq.hostname=${rabbitmq_host}\
 -Dcamel.component.rabbitmq.port-number=${rabbitmq_port}\
 -Dcamel.component.rabbitmq.username=${rabbitmq_user}\
 -Dcamel.component.rabbitmq.password=${rabbitmq_password}\
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=${debug_suspend},address=${debug_port}\
 -jar de-metas-edi-esb-camel.jar
}

echo "********************************"
echo " Starting metasfresh-esb-camel ";
echo "********************************"

echo_variable_values

# start printing all bash commands from here onwards, if activated
if [ "$debug_print_bash_cmds" != "n" ];
then
	echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}, so from here we will output all bash commands; set to n (just the lowercase letter) to skip this."
	set -x
fi

run_edi
exit 0 
