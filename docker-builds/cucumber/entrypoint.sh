#!/bin/bash
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
