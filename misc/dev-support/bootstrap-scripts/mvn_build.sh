#!/bin/bash
set -e

#needed if your locally installed "default" java is not java-21
#export JAVA_HOME=c:/Users/tobia/.jdks/temurin-21.0.7 

MULTITHREAD_PARAM='-T2C'
#MULTITHREAD_PARAM=''

ADDITIONAL_PARAMS='-DskipTests'
#ADDITIONAL_PARAMS='-DskipTests -Dmaven.gitcommitid.skip=true -Dlicense.skip=true'
#ADDITIONAL_PARAMS='-Dmaven.test.skip=true -Dmaven.gitcommitid.skip=true -Dlicense.skip=true'

mvn --file ../../parent-pom/pom.xml --settings ../maven/settings.xml $ADDITIONAL_PARAMS clean install
mvn --file ../../parent-pom/pom.xml --settings ../maven/settings.xml dependency:sources

mvn --file ../../de-metas-common/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS clean install
mvn --file ../../de-metas-common/pom.xml --settings ../maven/settings.xml dependency:sources

mvn --file ../../../backend/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS install
mvn --file ../../../backend/pom.xml --settings ../maven/settings.xml dependency:sources

mvn --file ../../services/camel/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS clean install
mvn --file ../../services/camel/pom.xml --settings ../maven/settings.xml dependency:sources
