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

set -o errexit   # abort on nonzero exitstatus
set -o nounset   # abort on unbound variable
set -o pipefail  # don't hide errors within pipes

echo ""
echo "==================="
echo " displaying environment variables before running cucumber tests ..."
echo "==================="
echo "TEST_SMTP_HOST=$TEST_SMTP_HOST"
echo "TEST_SMTP_FROM=$TEST_SMTP_FROM"
echo "TEST_SMTP_USER=$TEST_SMTP_USER"
echo "CUCUMBER_IS_USING_PROVIDED_INFRASTRUCTURE=$CUCUMBER_IS_USING_PROVIDED_INFRASTRUCTURE"
echo "CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_HOST=$CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_HOST"
echo "CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_PORT=$CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_PORT"
echo "CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_USER=$CUCUMBER_EXTERNALLY_RUNNING_RABBITMQ_USER"
echo "CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_HOST=$CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_HOST"
echo "CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_PORT=$CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_PORT"
echo "CUCUMBER_EXTERNALLY_RUNNING_POSTGREST_HOST=$CUCUMBER_EXTERNALLY_RUNNING_POSTGREST_HOST"
echo "CUCUMBER_EXTERNALLY_RUNNING_POSTGREST_PORT=$CUCUMBER_EXTERNALLY_RUNNING_POSTGREST_PORT"


echo ""
echo "==================="
echo " running cucumber tests ..."
echo "==================="

mvn --offline surefire:test --fail-never "$@"

echo "==================="
echo " exporting reports ..."
echo "==================="

cp target/*.xml /reports
cp target/*.html /reports
