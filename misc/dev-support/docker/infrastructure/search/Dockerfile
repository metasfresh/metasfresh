FROM elasticsearch:2.4

COPY sources/configs/elasticsearch.yml /usr/share/elasticsearch/config/
RUN chown elasticsearch:elasticsearch /usr/share/elasticsearch/config/elasticsearch.yml
RUN mkdir -p /usr/share/elasticsearch/data/metasfresh && \
chown elasticsearch:elasticsearch /usr/share/elasticsearch/data/metasfresh
