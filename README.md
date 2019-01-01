# build

Build the docker image using

```
docker build --tag metasfresh-e2e --build-arg CACHEBUST=$(date "+%Y-%m-%d") .
```

# run

run the docker container 

```
docker run --rm\
 -e "DEBUG_CYPRESS_OUTPUT=y"\
 -e "DEBUG_PRINT_BASH_CMDS=y"\
 -e "FRONTEND_URL=http://172.17.0.1:30080"\
 -e "API_URL=http=http://172.17.0.1:8080/rest/api"\
 -e "WS_URL=http=http://172.17.0.1:8080/stomp"\
 -e "RECORD_KEY=6082dd8a-4093-4d0c-a2d8-8cbc1bd45fcb"\
 metasfresh-e2e
```