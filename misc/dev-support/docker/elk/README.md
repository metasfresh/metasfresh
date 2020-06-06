
Many thanks to 
* https://hub.docker.com/r/sebp/elk
  * https://github.com/spujadas/elk-docker/issues/264#issuecomment-481191669
* https://www.howtoforge.com/tutorial/how-to-create-docker-images-with-dockerfile/


```bash
# run it; note that in addition to the documentation, we also have `-p 5000:5000`
docker-compose up -d
```

You can then run metasfresh-app and/or metasfresh-webui-api with `-Dspring.profiles.active=logstash` to have it direct log messages to localhost port 5000.
To change host and port, you can use `-Dlogstash.hort=yourhost -Dlogstash.port=yourport`

With that you can go to Kibana on http://localhost:5601/ and there you can discover the "index pattern" logstash-* .

Note: if you run into the following problem with docker-desktop

## Trouble-Shooting

```
ERROR: for elk_elk_1  Cannot create container for service elk: status code not OK but 500: {"Message":"Unhandled exception: Drive has not been shared"}
```

then you probably need to 
* open the settings
* go to "Reousrces" => "File sharing"
* and make the respective drive available
