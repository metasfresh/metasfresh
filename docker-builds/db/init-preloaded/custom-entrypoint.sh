#!/usr/bin/env bash
set -ex

if [ ! -s "$PGDATA/PG_VERSION" ]; then
    cp -a /var/lib/postgresql/initdata/* $PGDATA
fi

/usr/local/bin/docker-entrypoint.sh postgres "$@"