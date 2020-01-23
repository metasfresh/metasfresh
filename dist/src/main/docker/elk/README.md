
Many thanks to 
* https://hub.docker.com/r/sebp/elk
  * https://github.com/spujadas/elk-docker/issues/264#issuecomment-481191669
* https://www.howtoforge.com/tutorial/how-to-create-docker-images-with-dockerfile/

```bash
# build it
docker build .

# run it; note that in addition to the documentation, we also have `-p 5000:5000`
docker run -d -p 5601:5601 -p 9200:9200 -p 5044:5044 -p 5000:5000 --name elk_mf elk_mf
```

You can then run metasfresh-app and/or metasfresh-webui-api with `-Dlogstash.enabled=true -Dlogstash.hort=localhost -Dlogstash.port=5000` to have it direct log messages to localhost.

(note that i'm a total noob here)

You can then go to Kibana on http://localhost:5601/ and there you can "discover the "index" %{[@metadata][tcp]}-* .
There you can then do sortof olap on the log data that were send so far.
...that's how far i got until now..
