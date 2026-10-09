#!/usr/bin/env bash
set -ex

if [ ! -s "$PGDATA/PG_VERSION" ]; then
    cp -a /var/lib/postgresql/initdata/* $PGDATA
fi
# because of backwards compatibility with CMD ["postgres"] in custom dockerfiles we don't use /usr/local/bin/docker-entrypoint.sh postgres "$@"
/usr/local/bin/docker-entrypoint.sh "$@"