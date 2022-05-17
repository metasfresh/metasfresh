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

