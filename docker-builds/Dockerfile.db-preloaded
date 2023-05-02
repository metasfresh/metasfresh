ARG REFNAME=local
FROM metasfresh/metas-db:$REFNAME as db

# we are prepopulating the image with pgdata
# https://cadu.dev/creating-a-docker-image-with-database-preloaded/
# dirty but works

FROM db as init

RUN ["sed", "-i", "s/exec \"$@\"/echo \"skipping...\"/", "/usr/local/bin/docker-entrypoint.sh"]
RUN /usr/local/bin/docker-entrypoint.sh postgres

FROM db as final

COPY --from=init $PGDATA $PGDATA