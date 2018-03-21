#!/bin/bash
set -e


# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement
db_host=${DB_HOST:-db}
db_port=${DB_PORT:-5432}
db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-metasfresh}
app_host=${APP_HOST:-app}
skip_run_db_update=${SKIP_DB_UPDATE:-false}
debug_port=${DEBUG_PORT:-8791}
debug_suspend=${DEBUG_SUSPEND:-n}
admin_url=${METASFRESH_ADMIN_URL:-NONE}
java_max_heap=${JAVA_MAX_HEAP:-256M}
server_port=${SERVER_PORT:-8183}

echo_variable_values()
{
 echo "Note: all these variables can be set using the -e parameter."
 echo ""
 echo "DB_HOST=${db_host}"
 echo "DB_PORT=${db_port}"
 echo "DB_NAME=${db_name}"
 echo "DB_USER=${db_user}"
 echo "DB_PASSWORD=*******"
 echo "SKIP_DB_UPDATE=${skip_run_db_update}"
 echo "APP_HOST=${app_host}"
 echo "DEBUG_PORT=${debug_port}"
 echo "DEBUG_SUSPEND=${debug_suspend}"
 echo "METASFRESH_ADMIN_URL=${admin_url}"
 echo "JAVA_MAX_HEAP=${java_max_heap}"
 echo "SERVER_PORT=${server_port}"
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
 until nc -z $db_host $db_port
 do
   sleep 1
 done
}

run_db_update()
{
 if [ "$skip_run_db_update" != "false" ]; then
	echo ">>>>>>>>>>>> We skip running the migration scripts because SKIP_DB_UPDATE=${skip_run_db_update}"
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

 echo ">>>>>>>>>>>> Local migration scripts were run"
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

 # add the external font jars we might have in the external lib folder
 # this assumes that the metasfresh-report.jar uses PropertiesLauncher (can be verified by opening the jar e.g. with 7-zip and checking META-INF/MANIFEST.MF)
 # Also see https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar-property-launcher-features
 ext_lib_param="-Dloader.path=/opt/metasfresh/metasfresh-report/external-lib"

 cd /opt/metasfresh/metasfresh-report/ && java -Dsun.misc.URLClassPath.disableJarChecking=true \
 ${ext_lib_param}\
 -Xmx${java_max_heap}\
 -XX:+HeapDumpOnOutOfMemoryError ${metasfresh_admin_params}\
 -DPropertyFile=/opt/metasfresh/metasfresh-report/metasfresh.properties\
 -Djava.security.egd=file:/dev/./urandom\
 -Dserver.port=${server_port}
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=${debug_suspend},address=${debug_port}\
 -jar metasfresh-report.jar
}

echo "*************************************************************"
echo "Display the variable values we run with"
echo "*************************************************************"
echo_variable_values
echo ""

set_properties /opt/metasfresh/metasfresh-report/metasfresh.properties


echo "*************************************************************"
echo "Wait for the database server to start on DB_HOST = '${db_host}'"
echo "*************************************************************"
wait_dbms
echo ">>>>>>>>>>>> Database Server has started"

echo "*************************************************************"
echo "Run the local migration scripts"
echo "*************************************************************"
run_db_update


echo "*************************************************************"
echo "Start metasfresh-report";
echo "*************************************************************"
run_metasfresh

exit 0 
