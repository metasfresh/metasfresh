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

echo_variable_values()
{
 echo "Note: all these variables can be set using the -e parameter."
 echo ""
 echo "DB_HOST=${db_host}"
 echo "DB_PORT=${db_port}"
 echo "DB_NAME=${db_name}"
 echo "DB_USER=${db_user}"
 echo "DB_PASSWORD=dude, that's a secret"
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
 until nc -z $db_host $db_port
 do
   sleep 1
 done
}

run_db_update()
{
 sleep 10
 
 settings_file="/opt/metasfresh/run_db_update_settings.properties"

 echo "" > $settings_file
 echo "IMPORTANT" >> $settings_file
 echo "This file is rewritten each time the container starts" >> $settings_file
 echo "" >> $settings_file
 echo "METASFRESH_DB_SERVER=${db_host}" >> $settings_file
 echo "METASFRESH_DB_PORT=${db_port}" >> $settings_file
 echo "METASFRESH_DB_NAME=${db_name}" >> $settings_file
 echo "METASFRESH_DB_USER=${db_user}" >> $settings_file
 echo "METASFRESH_DB_PASSWORD=${db_password}" >> $settings_file
 echo "" > $settings_file
 
 # -s sets the "Name of the (s)ettings file (e.g. settings_<hostname>.properties) *within the Rollout-Directory*" (which is /opt/metasfresh in this case)
 # run with -h for a description of all params
 cd /opt/metasfresh/dist/install/ && java -jar ./lib/de.metas.migration.cli.jar -s run_db_update_settings.properties
}

# Note: the Djava.security.egd param is supposed to let tomcat start quicker, see https://spring.io/guides/gs/spring-boot-docker/
run_metasfresh()
{
 cd /opt/metasfresh/metasfresh-report/ && java -Dsun.misc.URLClassPath.disableJarChecking=true \
 -Xmx512M -XX:+HeapDumpOnOutOfMemoryError \
 -DPropertyFile=/opt/metasfresh/metasfresh-report/metasfresh.properties \
 -Djava.security.egd=file:/dev/./urandom \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8791 \
 -jar metasfresh-report.jar
}

echo "*************************************************************"
echo "Display the variable values we run with"
echo "*************************************************************"
echo_variable_values
echo ""

set_properties /opt/metasfresh/metasfresh-report/


echo "*************************************************************"
echo "Wait for the database server to start on DB_HOST = '${db_host}'"
echo "*************************************************************"
wait_dbms
echo ">>>>>>>>>>>> Database Server has started"

echo "*************************************************************"
echo "Run the local migration scripts"
echo "*************************************************************"
run_db_update
echo ">>>>>>>>>>>> Local migration scripts were run"

echo "*************************************************************"
echo "Start metasfresh-report";
echo "*************************************************************"
run_metasfresh

exit 0 
