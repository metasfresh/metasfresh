#!/bin/bash
set -o errexit   # abort on nonzero exitstatus
set -o nounset   # abort on unbound variable
set -o pipefail  # don't hide errors within pipes

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
