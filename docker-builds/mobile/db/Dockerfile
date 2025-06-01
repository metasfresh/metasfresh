ARG mfregistry
ARG mfversion

FROM $mfregistry/metas-db:$mfversion-$dbqualifier

RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
RUN localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8

ENV LANG=de_DE.UTF-8
ENV LANGUAGE=en_US.UTF-8
ENV LC_MESSAGES=en_US.UTF-8

#ENTRYPOINT ["/usr/local/bin/custom-entrypoint.sh"]

CMD ["postgres"]