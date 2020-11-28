#!/bin/bash
#title           :start_esb-camel.sh
#
#description     :initial start script for Docker container 
#
#author          :tobias.schoeneberg@metasfresh.com
#                :leaning on work done by julian.bischof@metasfresh.com
#date            :2020-03-15
#==============================================================================
set -e
set -u

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement

# RabbitMQ connection
rabbitmq_host=${RABBITMQ_HOST:-localhost}
rabbitmq_port=${RABBITMQ_PORT:-5672}
rabbitmq_user=${RABBITMQ_USER:-guest}
rabbitmq_password=${RABBITMQ_PASSWORD:-$(echo $secret_rabbitmq_password)}

# Java parameters
java_disable_jar_checking=${JAVA_DISABLE_JAR_CHECKING:-true}
java_max_heap=${JAVA_MAX_HEAP:-128M}
java_heap_dump_path=${JAVA_HEAP_DUMP_PATH:-/opt/metasfresh-esb-camel/heapdump}
debug_port=${DEBUG_PORT:-8792}
debug_suspend=${DEBUG_SUSPEND:-n}
java_additional_jvm_params=${JAVA_JVM_PARAMS:-""}

# Spring profiles
spring_active_profiles=${SPRING_ACTIVE_PROFILES:-""}


# logstash settings
apm_server_url=${APM_SERVER_URL:-http://172.17.0.1:8200}

# general settings
metasfresh_instance_name=${METASFRESH_INSTANCE_NAME:-metasfresh}
debug_print_bash_cmds=${DEBUG_PRINT_BASH_CMDS:-n}


# output variables for easy debugging
#
echo_variable_values(){

echo "
*************************************************************
Display the variable values we run with
*************************************************************
Note: all these variables can be set using the -e parameter.

METASFRESH_INSTANCE_NAME=${metasfresh_instance_name}
SPRING_ACTIVE_PROFILES=${spring_active_profiles}

**RABBITMQ CONNECTION SETTINGS*******************************

RABBITMQ_HOST=${rabbitmq_host}
RABBITMQ_PORT=${rabbitmq_port}
RABBITMQ_USER=${rabbitmq_user}
RABBITMQ_PASSWORD=*******

**JAVA SETTINGS**********************************************

JAVA_MAX_HEAP=${java_max_heap}
JAVA_DISABLE_JAR_CHECKING=${java_disable_jar_checking}
JAVA_HEAP_DUMP_PATH=${java_heap_dump_path}
JAVA_JVM_PARAMS=${java_additional_jvm_params}
"

if [[ "${spring_active_profiles}" = *"logstash"* ]]; then
echo "
**APM SETTINGS******************************************

APM_SERVER_URL=${apm_server_url}
"
fi

echo "
**DEBUG SETTINGS*********************************************

DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}
DEBUG_PORT=${debug_port}
DEBUG_SUSPEND=${debug_suspend}

*************************************************************"
}


# resolve all required java parameters used for executing the application
#
resolve_java_params(){

java_params=" \
 -Dcamel.component.rabbitmq.hostname=${rabbitmq_host}\
 -Dcamel.component.rabbitmq.port-number=${rabbitmq_port}\
 -Dcamel.component.rabbitmq.username=${rabbitmq_user}\
 -Dcamel.component.rabbitmq.password=${rabbitmq_password}\
 -Dsun.misc.URLClassPath.disableJarChecking=${java_disable_jar_checking} \
 -Xmx${java_max_heap}\
 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${java_heap_dump_path} \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=${debug_suspend},address=*:${debug_port} \
 -Dspring.profiles.active=${spring_active_profiles} \
"

if [[ -n "$spring_active_profiles" ]]; then
    case "${spring_active_profiles}" in
        *"logstash"*) logstash_java_params="-javaagent:/opt/metasfresh-esb-camel/elastic-apm-agent.jar \
                                        -Delastic.apm.application_packages=de.metas.*,org.compiere.*,org.adempiere.* \
                                        -Delastic.apm.server_urls=${apm_server_url} \
                                        "
                      java_params="${java_params} ${logstash_java_params}"
                      ;;
    esac
fi

java_params="${java_params} ${java_additional_jvm_params}"

}

# start the application with java parameters generated in resolve_java_params()
#
run_metasfresh(){

 resolve_java_params
 cd /opt/metasfresh-esb-camel/ && java ${java_params} -jar de-metas-camel-edi-exec.jar
 
}

# start printing all bash commands from here onwards, if "DEBUG_PRINT_BASH_CMDS" is set
#
if [ "$debug_print_bash_cmds" != "n" ];
then
	echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}, so from here we will output all bash commands; set to n (just the lowercase letter) to skip this."
	set -x
fi

echo_variable_values

run_metasfresh

exit 0 
