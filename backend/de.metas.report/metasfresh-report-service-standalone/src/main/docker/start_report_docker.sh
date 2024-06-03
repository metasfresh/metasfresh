#!/bin/bash

#title           :start_app.sh
#
#description     :initial start script for Docker container 
#                 also used to update the database with custom SQLs (see 'run_db_update()')
#
#author          :tobias.schoeneberg@metasfresh.com
#                :leaning on work done by julian.bischof@metasfresh.com
#date            :2020-03-15
#==============================================================================
set -e
set -u

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement

# Container hostnames
db_host=${DB_HOST:-db}
db_port=${DB_PORT:-5432}
db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-metasfresh}
db_wait_for_dbms=${DB_WAIT_FOR_DBMS:-y}
db_connection_pool_max_size=${DB_CONNECTION_POOL_MAX_SIZE:-UNSET}
app_host=${APP_HOST:-app}
skip_run_db_update=${SKIP_DB_UPDATE:-false}
admin_url=${METASFRESH_ADMIN_URL:-NONE}
server_port=${SERVER_PORT:-8183}

# Java parameters
java_disable_jar_checking=${JAVA_DISABLE_JAR_CHECKING:-true}
java_max_heap=${JAVA_MAX_HEAP:-128M}
java_property_file=${JAVA_PROPERTY_FILE:-/opt/metasfresh/metasfresh-report/metasfresh.properties}
java_heap_dump_path=${JAVA_HEAP_DUMP_PATH:-/opt/metasfresh/heapdump}
debug_port=${DEBUG_PORT:-8791}
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

DB_HOST=${db_host}
DB_PORT=${db_port}
DB_NAME=${db_name}
DB_USER=${db_user}
DB_PASSWORD=*****
DB_WAIT_FOR_DBMS=${db_wait_for_dbms}
DB_CONNECTION_POOL_MAX_SIZE=${db_connection_pool_max_size}

SKIP_DB_UPDATE=${skip_run_db_update}

APP_HOST=${app_host}
SERVER_PORT=${server_port}

METASFRESH_INSTANCE_NAME=${metasfresh_instance_name}
SPRING_ACTIVE_PROFILES=${spring_active_profiles}


**JAVA SETTINGS**********************************************

JAVA_MAX_HEAP=${java_max_heap}
JAVA_DISABLE_JAR_CHECKING=${java_disable_jar_checking}
JAVA_PROPERTY_FILE=${java_property_file}
JAVA_HEAP_DUMP_PATH=${java_heap_dump_path}
JAVA_JVM_PARAMS=${java_additional_jvm_params}
"

if [[ "${spring_active_profiles}" = *"logstash"* ]]; then
echo "
**LOGSTASH SETTINGS******************************************

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


# replace "FOO_" with Docker internal hostname of "app" and "db" container
# required for setting correct properties
# 
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
	echo "**************************************"
	echo "Wait for the database server to start " # host & port were logged by echo_variable_values
	echo "**************************************"
	
	echo "We will repeatedly invoke 'nc -z ${db_host} ${db_port}' until it returns successfully"
	echo "."
	until nc -z $db_host $db_port
	do
		sleep 1
		echo -n "."
	done
	echo "Database Server has started"
}

run_db_update()
{
 if [ "$skip_run_db_update" != "false" ]; then
	echo "We skip running the migration scripts because SKIP_DB_UPDATE=${skip_run_db_update}"
	return 0
 fi

 sleep 10
 settings_file="/opt/metasfresh/dist/run_db_update_settings.properties"

 echo "" > $settings_file
 echo "IMPORTANT" >> $settings_file
 echo "This file is rewritten each time the container starts" >> $settings_file
 echo "" >> $settings_file
 echo "METASFRESH_DB_SERVER=${db_host}" >> $settings_file
 echo "METASFRESH_DB_PORT=${db_port}" >> $settings_file
 echo "METASFRESH_DB_NAME=${db_name}" >> $settings_file
 echo "METASFRESH_DB_USER=${db_user}" >> $settings_file
 echo "METASFRESH_DB_PASSWORD=${db_password}" >> $settings_file
 echo "" >> $settings_file
 
 # -s sets the "Name of the (s)ettings file (e.g. settings_<hostname>.properties) *within the Rollout-Directory*" (which is /opt/metasfresh/dist in this case)
 # -v -u disable both checking and updating the local DB's version info since this is not a "main" migration and only those can currently be handeled like that.
 # run with -h for a description of all params
 cd /opt/metasfresh/dist/install/ && java -jar ./lib/de.metas.migration.cli.jar -v -u -s run_db_update_settings.properties

 echo "Local migration scripts were run"
}



# resolve all required java parameters used for executing the application
#
resolve_java_params(){

# Note: the Djava.security.egd param is supposed to let tomcat start quicker, see https://spring.io/guides/gs/spring-boot-docker/

java_params=" \
 -Dsun.misc.URLClassPath.disableJarChecking=${java_disable_jar_checking} \
 -Xmx${java_max_heap}\
 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${java_heap_dump_path} \
 -DPropertyFile=${java_property_file} \
 -Djava.security.egd=file:/dev/./urandom\
 -Dserver.port=${server_port}\
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=${debug_suspend},address=${debug_port} \
 -Dspring.profiles.active=${spring_active_profiles} \
"

if [ "$db_connection_pool_max_size" != "UNSET" ]; then
	java_params="${java_params} -Dc3p0.maxPoolSize=${db_connection_pool_max_size}"
fi

if [ "$admin_url" != "NONE" ]; then
	# see https://codecentric.github.io/spring-boot-admin/1.5.0/#spring-boot-admin-client
	# spring.boot.admin.client.prefer-ip=true because within docker, the hostname is no help
	java_params="${java_params} -Dspring.boot.admin.url=${admin_url} -Dmanagement.security.enabled=false -Dspring.boot.admin.client.prefer-ip=true"
fi

# add the external font jars we might have in the external lib folder
# this assumes that the metasfresh-report.jar uses PropertiesLauncher (can be verified by opening the jar e.g. with 7-zip and checking META-INF/MANIFEST.MF)
# Also see https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar-property-launcher-features
java_params="${java_params} -Dloader.path=/opt/metasfresh/metasfresh-report/external-lib"

if [[ -n "$spring_active_profiles" ]]; then
    case "${spring_active_profiles}" in
        *"logstash"*) logstash_java_params="-javaagent:/opt/metasfresh/metasfresh-report/elastic-apm-agent.jar \
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
 cd /opt/metasfresh/metasfresh-report/ && java ${java_params} org.springframework.boot.loader.PropertiesLauncher
 
}

# start printing all bash commands from here onwards, if "DEBUG_PRINT_BASH_CMDS" is set
#
if [ "$debug_print_bash_cmds" != "n" ];
then
	echo "DEBUG_PRINT_BASH_CMDS=${debug_print_bash_cmds}, so from here we will output all bash commands; set to n (just the lowercase letter) to skip this."
	set -x
fi

echo_variable_values

set_properties /opt/metasfresh/metasfresh-report/metasfresh.properties

if [ "$db_wait_for_dbms" != "n" ];
then
	echo "DB_WAIT_FOR_DBMS=${db_wait_for_dbms}, so we wait for the DBMS to be reachable; set to n (just the lowercase letter) to skip this."
	wait_dbms

else
	echo "DB_WAIT_FOR_DBMS=${db_wait_for_dbms}, so we do not wait for the DBMS to be reachable."
fi

echo "*************************************************************"
echo "Run the local migration scripts"
echo "*************************************************************"
run_db_update


echo "*************************************************************"
echo "Start metasfresh-report";
echo "*************************************************************"
run_metasfresh


exit 0 
