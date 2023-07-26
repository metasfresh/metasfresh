# Please keep the version in sync with the version used by metasfresh
# Also see https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-dependency-versions.html
FROM docker.elastic.co/elasticsearch/elasticsearch:7.9.3

# Also see https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#docker-cli-run-dev-mode
ENV discovery.type=single-node

COPY sources/start-custom.sh /usr/local/bin/start-custom.sh
RUN chmod +x /usr/local/bin/start-custom.sh

#disclaiimer: IDK what this tini thing is about, but i didn't manage to get it to work
#ENTRYPOINT ["/bin/tini", "--", "/usr/local/bin/start-custom.sh"]
ENTRYPOINT ["/usr/local/bin/start-custom.sh"]
