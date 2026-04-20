#!/bin/bash
set -o errexit   # abort on nonzero exitstatus
set -o nounset   # abort on unbound variable
set -o pipefail  # don't hide errors within pipes

echo ""
echo "==================="
echo " preparing periods & opening period controls in the cucumber DB ..."
echo "==================="
PGPASSWORD=metasfresh psql -h db -U metasfresh -d metasfresh -v ON_ERROR_STOP=1 -f /setup_periods.sql

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
