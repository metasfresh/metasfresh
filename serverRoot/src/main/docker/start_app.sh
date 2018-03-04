#!/bin/bash

set -e
set -u

# The variables have defaults that can be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file.
# Also see https://docs.docker.com/engine/reference/builder/#environment-replacement

# postgres
db_host=${DB_HOST:-db}
db_port=${DB_PORT:-5432}
db_name=${DB_NAME:-metasfresh}
db_user=${DB_USER:-metasfresh}
db_password=${DB_PASSWORD:-metasfresh}

# elastic search
es_host=${ES_HOST:-search}
es_port=${ES_PORT:-9300}

# self
app_host=${APP_HOST:-app}

set_properties()
{
 local prop_file="$1"
 if [[ $(cat $prop_file | grep FOO | wc -l) -ge "1" ]]; then
	sed -Ei "s/FOO_DBMS/${db_host}/g" $prop_file
	sed -Ei "s/FOO_DBMS_PORT/${db_port}/g" $prop_file
	sed -Ei "s/FOO_DB_NAME/${db_name}/g" $prop_file
	sed -Ei "s/FOO_DB_USER/${db_user}/g" $prop_file
	sed -Ei "s/FOO_DB_PASSWORD/${db_password}/g" $prop_file
	sed -Ei "s/FOO_APP/${app_host}/g" $prop_file
 fi
}

#set_hosts()
#{
# if [[ -z $(grep ${app_host} /etc/hosts) ]]; then
#        sed -i 's/'$(hostname)'/'$(hostname)' '${app_host}'/' /etc/hosts
# fi
#}
wait_dbms()
{
 until nc -z $db_host $db_port
 do
   sleep 1
 done
}

#run_install()
#{
# if [[ ! -f /opt/metasfresh/metasfresh-app.jar ]]; then#
#	cp -R /opt/metasfresh/dist/deploy/* /opt/metasfresh/
#    chmod 700 /opt/metasfresh/metasfresh-app.jar
#	chown root:root -R /opt/metasfresh
#	
#	mkdir -p /opt/metasfresh/reports/ 
#	chmod -R a+w /opt/metasfresh/reports/
# fi
#}

# run_db_update()
# {
#  sleep 10
#  cd /opt/metasfresh/dist/install/ && java -jar ./lib/de.metas.migration.cli.jar $@
# } 

run_metasfresh()
{
 cd /opt/metasfresh/ \
 && java \
 -Dsun.misc.URLClassPath.disableJarChecking=true \
 -Xmx1024M \
 -XX:MaxPermSize=512M \
 -XX:+HeapDumpOnOutOfMemoryError \
 -Dspring.data.elasticsearch.cluster-nodes=${es_host}:${es_port} \
 -DPropertyFile=/opt/metasfresh/metasfresh.properties \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8788 \
 -jar metasfresh-app.jar
}

# run_install

set_properties /opt/metasfresh/metasfresh.properties
set_properties /opt/metasfresh/local_settings.properties
set_properties /root/local_settings.properties

wait_dbms

# run_db_update

run_metasfresh

exit 0
