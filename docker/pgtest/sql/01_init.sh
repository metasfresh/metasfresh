#!/usr/bin/env bash
# distilled from https://github.com/docker-library/postgres/issues/179

echo ""
echo "======================================="
echo " Applying migrations ..."
echo "======================================="

find /docker-entrypoint-initdb.d -mindepth 2 -type f -print0 | while read -d $'\0' f; do
  case "$f" in
    *.sql)    echo "$0: running $f"; psql -v ON_ERROR_STOP=1 --username=metasfresh -f "$f"; echo ;;
    *)        echo "$0: ignoring $f" ;;
  esac
  echo
done

echo "=========="
echo " ...done!"
echo "=========="