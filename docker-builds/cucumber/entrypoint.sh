#!/bin/bash
set -o errexit   # abort on nonzero exitstatus
set -o nounset   # abort on unbound variable
set -o pipefail  # don't hide errors within pipes

echo ""
echo "==================="
echo " opening all C_PeriodControl rows in the cucumber DB ..."
echo "==================="
# Legacy assert_period_open() on this branch only supports PeriodStatus='O',
# and the preloaded DB seeds all period controls with 'N' (Never Opened).
# Feature files use 2021/2022 dates, so without this they all fail with @PeriodClosed@.
# Runs only in the ephemeral cucumber compose stack — no migration touches customer DBs.
PGPASSWORD=metasfresh psql -h db -U metasfresh -d metasfresh -v ON_ERROR_STOP=1 \
  -c "UPDATE c_periodcontrol SET periodstatus='O', updated=now(), updatedby=99 WHERE periodstatus<>'O'"

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
