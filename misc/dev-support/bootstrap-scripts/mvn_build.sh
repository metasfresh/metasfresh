#!/bin/bash
set -e

mvn --file ../../parent-pom/pom.xml -DskipTests --settings ../maven/settings.xml clean install

mvn --file ../../de-metas-common/pom.xml -DskipTests --settings ../maven/settings.xml clean install

mvn --file ../../../backend/pom.xml -T2C -DskipTests --settings ../maven/settings.xml clean install
