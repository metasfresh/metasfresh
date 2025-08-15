#!/bin/bash
#
# %L
# master
# %%
# Copyright (C) 2025 metas GmbH
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program. If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# L%
#

set -e

#needed if your locally installed "default" java is not the one expected by maven
export JAVA_HOME=c:/Users/tobia/.jdks/temurin-21.0.7 
#export JAVA_HOME=c:/Users/tobia/.jdks/temurin-17.0.15
#export JAVA_HOME=c:/Users/tobia/.jdks/temurin-1.8.0_452

MULTITHREAD_PARAM=''
#MULTITHREAD_PARAM='-T2C'

ADDITIONAL_PARAMS='-Djib.skip=true -Dmaven.gitcommitid.skip=true -Dlicense.skip=true -DskipTests'
#ADDITIONAL_PARAMS='${ADDITIONAL_PARAMS} -DskipTests'
#ADDITIONAL_PARAMS='${ADDITIONAL_PARAMS} -Dmaven.test.skip=true'

#GOALS='test'
GOALS='clean install'

mvn --file ../../parent-pom/pom.xml --settings ../maven/settings.xml $ADDITIONAL_PARAMS $GOALS && \

mvn --file ../../de-metas-common/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS && \

mvn --file ../../../backend/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS && \

mvn --file ../../services/camel/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS && \

mvn --file ../../services/procurement-webui/procurement-webui-backend/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS && \

mvn --file ../../services/federated-rabbitmq/pom.xml --settings ../maven/settings.xml $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS

echo "$(date): Done"
