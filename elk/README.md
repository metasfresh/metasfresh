
Many thanks to 
* https://hub.docker.com/r/sebp/elk
  * https://github.com/spujadas/elk-docker/issues/264#issuecomment-481191669
* https://www.howtoforge.com/tutorial/how-to-create-docker-images-with-dockerfile/

```bash
# build the docker image; the CACHEBUST build-arg is optional and merely makes sure that apt upgrade etc is performed at least once a day
docker build --tag metasfresh-elk .
```

```bash
# run it; note that in addition to the documentation, we also have `-p 5000:5000`
# docker run --rm -d -p 5601:5601 -p 9200:9200 -p 5044:5044 -p 5000:5000 -v ./volumes/elk/elasticsearch:/var/lib/elasticsearch metasfresh-elk
docker run --rm -d -p 5601:5601 -p 9200:9200 -p 5044:5044 -p 5000:5000 metasfresh-elk
```

You can then run metasfresh-app and/or metasfresh-webui-api with `-Dlogstash.enabled=true -Dlogstash.hort=localhost -Dlogstash.port=5000` to have it direct log messages to localhost.

With that you can go to Kibana on http://localhost:5601/ and there you can discover the "index pattern" logstash-* .
There you can then do sortof olap on the log data that were send so far.
