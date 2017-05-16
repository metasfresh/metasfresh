#!/bin/bash

set -e
set -u

DB_HOST=db
APP_HOST=app

set_properties()
{
 local prop_file="$1"
 if [[ $(cat $prop_file | grep FOO | wc -l) -ge "1" ]]; then
	sed -Ei "s/FOO_DBMS/$DB_HOST/g" $prop_file
	sed -Ei "s/FOO_APP/$APP_HOST/g" $prop_file
 fi
}

set_hosts()
{
 if [[ -z $(grep ${APP_HOST} /etc/hosts) ]]; then
        sed -i 's/'$(hostname)'/'$(hostname)' '${APP_HOST}'/' /etc/hosts
 fi
}
wait_dbms()
{
 until nc -z $DB_HOST 5432
 do
   sleep 1
 done
}

run_metasfresh-material-dispo()
{
 cd /opt/metasfresh/material-dispo && java \
 -Dsun.misc.URLClassPath.disableJarChecking=true \
 -Xmx1024M -XX:+HeapDumpOnOutOfMemoryError \
 -Djava.security.egd=file:/dev/./urandom
 -DPropertyFile=/opt/metasfresh/material-dispo/metasfresh-material-dispo.properties \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8789 \
 -jar metasfresh-material-dispo.jar
}

set_properties /opt/metasfresh/material-dispo/metasfresh.properties
set_properties /opt/metasfresh/material-dispo/local_settings.properties

wait_dbms

run_metasfresh-material-dispo

exit 0 
