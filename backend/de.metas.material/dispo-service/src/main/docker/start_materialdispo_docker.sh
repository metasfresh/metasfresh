#!/bin/bash

set -e

#The variable DB_HOST shall be set from outside, e.g. via -e "DB_HOST=mydbms" or from the docker-compose.yml file
#DB_HOST=db

APP_HOST=app

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
 cd /opt/metasfresh/metasfresh-material-dispo/ && java -Dsun.misc.URLClassPath.disableJarChecking=true \
 -Xmx512M -XX:+HeapDumpOnOutOfMemoryError \
 -DPropertyFile=/opt/metasfresh/metasfresh-material-dispo/metasfresh.properties \
 -Djava.security.egd=file:/dev/./urandom \
 -Dspring.boot.admin.url=http://localhost:9090 \
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
