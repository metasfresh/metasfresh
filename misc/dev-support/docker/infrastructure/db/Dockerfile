# Make sure with the tag to have a common ancestor for all possible user branches
# However, 5.175-release-5.175 is postgresql-15 and we should by now have pg-15 in master
ARG DB_DOCKER_IMG=metasfresh/metas-db:5.175-release-5.175
FROM $DB_DOCKER_IMG

RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
RUN localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8

ENV LANG=de_DE.UTF-8
ENV LANGUAGE=en_US.UTF-8
ENV LC_MESSAGES=en_US.UTF-8

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]

CMD ["postgres"]
