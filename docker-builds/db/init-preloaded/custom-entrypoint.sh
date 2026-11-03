#!/usr/bin/env bash
set -ex

if [ ! -s "$PGDATA/PG_VERSION" ]; then
    cp -a /var/lib/postgresql/initdata/* $PGDATA
fi
# because of backwards compatibility with CMD ["postgres"] in custom dockerfiles we don't use /usr/local/bin/docker-entrypoint.sh postgres "$@"
# exec: postgres must replace this shell as PID 1. A bash PID 1 ignores the SIGINT of `docker stop`, so postgres
# never shut down cleanly; it was SIGKILLed after the stop timeout and left its data dir in crash-recovery state.
exec /usr/local/bin/docker-entrypoint.sh "$@"