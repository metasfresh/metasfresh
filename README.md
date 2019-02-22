# build

To build the docker image locally, you can do

```
git clone -b master --depth=1 https://github.com/metasfresh/metasfresh-webui-frontend.git cypress-git-repo
docker build --tag metasfresh-e2e --build-arg CACHEBUST=$(date "+%Y-%m-%d") .
```

# run

run the docker container, e.g. like this

```
hostname=dev289.metasfresh.com
docker run --rm\
 -e "FRONTEND_URL=https://${hostname}:443"\
 -e "API_URL=https://${hostname}:443/rest/api"\
 -e "WS_URL=https://${hostname}:443/stomp"\
 -e "USERNAME=dev"\
 -e "PASSWORD=password"\
 -e "RECORD_KEY=NOT_SET"\
 -e "BROWSER=chrome"\
 -e "DEBUG_CYPRESS_OUTPUT=n"\
 -e "DEBUG_PRINT_BASH_CMDS=n"\
 -e "DEBUG_SLEEP_AFTER_FAIL=y"\
 metasfresh-e2e
```