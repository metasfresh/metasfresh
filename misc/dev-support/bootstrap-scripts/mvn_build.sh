#!/bin/bash
set -e

export JAVA_HOME=/c/Users/tobia/.jdks/temurin-21.0.7 

#mvn --file ../../parent-pom/pom.xml -DskipTests --settings ../maven/settings.xml clean install

#mvn --file ../../de-metas-common/pom.xml -DskipTests --settings ../maven/settings.xml clean install

mvn --file ../../../backend/pom.xml --settings ../maven/settings.xml dependency:sources

#mvn --file ../../../backend/pom.xml -T2C -DskipTests --settings ../maven/settings.xml clean install
#mvn --file ../../../backend/pom.xml -DskipTests --settings ../maven/settings.xml install -rf :de.metas.util.web


#mvn --file ../../services/camel/pom.xml -T2C -DskipTests --settings ../maven/settings.xml clean install
