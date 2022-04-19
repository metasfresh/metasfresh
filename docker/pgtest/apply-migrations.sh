#!/bin/bash
set -e

echo ""
echo "======================================="
echo " Applying migrations ..."
echo "======================================="
cd /tmp/init
java -jar de.metas.migration.cli.jar -v -u -s migrate.properties -d .
echo "=========="
echo " ...done!"
echo "=========="

# # distilled from https://github.com/docker-library/postgres/issues/179
# find /docker-entrypoint-initdb.d -mindepth 2 -type f -print0 | sort -z | while read -d $'\0' f; do
  # case "$f" in
    # *.sql)    echo "$0: running $f"; psql -v VERBOSITY=terse --username=metasfresh -f "$f"; echo ;;
    # *)        echo "$0: ignoring $f" ;;
  # esac
  # echo
# done