#!/bin/bash
set -e

echo ""
echo "==================="
echo " Creating role ..."
echo "==================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE ROLE metasfresh LOGIN ENCRYPTED PASSWORD 'metasfresh' SUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "======================================="
echo " Creating database and permissions ..."
echo "======================================="
psql -v ON_ERROR_STOP=1 --username=postgres <<- EOSQL
CREATE DATABASE metasfresh WITH OWNER = metasfresh;
GRANT ALL PRIVILEGES ON DATABASE metasfresh to metasfresh;
EOSQL
echo "==========="
echo " ... done!"
echo "==========="

echo ""
echo "==================="
echo "Restoring pgdump"
echo "==================="
pg_restore -Fc --exit-on-error --username metasfresh --dbname metasfresh /metasfresh.pgdump
echo "=========="
echo " ...done!"
echo "=========="

echo ""
echo "======================================="
echo " Applying migrations ..."
echo "======================================="
# We want to use the regular postgres container init and do not want to override the original entrypoint.
# Therefore we cannot use our metasfresh java migration tool here since it wants to open a JDBC connection via TCP socket, which is not open during postgres init for security reasons.
# Only a local unix socket is available, which allows us to use psql command line.
# We emulate what the java migration tool would do, by iterating over the migration sqls in the right order, checking whether they need applying and marking each applied migration in the database.

find /docker-entrypoint-initdb.d/migrations -type f -printf '%f\n' | sort | while read f; do
  case "$f" in
    *.sql)
        readarray -t parts < <( echo "${f//'---'/$'\n'}" );
        printf "${parts[0]} in ${parts[1]%.sql}: ";
        
        exists=`psql -U metasfresh -tc "select exists(select 1 from ad_migrationscript where name = '${parts[1]%.sql}' || '->' || '${parts[0]}' || '.sql')"`;
        if [ $exists = 't' ]; then
            echo "skipped";
            continue;
        fi
        
        psql --username=metasfresh -v ON_ERROR_STOP=ON -q1f "/docker-entrypoint-initdb.d/migrations/${f}" > /tmp/migration.log 2>&1 || {
          echo "failed";
          cat /tmp/migration.log
          exit 1            
        };
        
        cat /tmp/scripts/mark-migration-as-applied.sql | awk "{gsub(\"##project##\",\"${parts[1]%.sql}\");gsub(\"##file##\",\"${parts[0]}\");print}" | psql -v ON_ERROR_STOP=ON -q1 --username=metasfresh || {
          echo "failed marking";
          exit 1
        };
        
        echo "applied" ;
        ;;
    *)
        echo "$0: ignoring $f" ;;
  esac
done
echo "=========="
echo " ...done!"
echo "=========="


echo ""
echo "==================="
echo "adjusting configuration"
echo "==================="
psql -U metasfresh -d metasfresh ON_ERROR_STOP=ON -q1f /tmp/scripts/adjust-config.sql
echo "=========="
echo " ...done!"
echo "=========="