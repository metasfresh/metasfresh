# make sure with the tag to have a common ancestor for all possible user branches
<<<<<<< HEAD
FROM metasfresh/metasfresh-db:5.174
=======
FROM metasfresh/metasfresh-db:5.173
>>>>>>> c0c63b5d89c (scary_plum_uat_update_configFiles and migration-tool (#14972))

RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
RUN localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8

ENV LANG=de_DE.UTF-8
ENV LANGUAGE=en_US.UTF-8
ENV LC_MESSAGES=en_US.UTF-8

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]

CMD ["postgres"]
