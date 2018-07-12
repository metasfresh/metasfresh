#
# This docker file shall *also* allow API developers to run the metasfresh-webui-frontend in a container,
# without the need to locally install npm and node.
# It's generally build by our jenkins.
#
# To run the latest master build from jenkins (assuming that you run the webui-api locally on port 8080), do something like
# sudo docker run -d --name metasfresh-webui-frontend -p 3000:80 -e "WEBUI_API_PROTOCOL=http" -e "WEBUI_API_HOST=$(hostname)" -e "WEBUI_API_PORT=8080" docker.metasfresh.com/metasfresh/metasfresh-webui-dev:master_LATEST
#
# To build it, go to this repo's base dir and run something like
# sudo docker build --file ./docker/nginx/Dockerfile --tag metasfresh-webui-frontend-dev .
#
# To run it, run something like
# sudo docker run --name metasfresh-webui-frontend -p 3000:80 -e "WEBUI_API_PROTOCOL=http" -e "WEBUI_API_HOST=$(hostname)" -e "WEBUI_API_PORT=8080" -d metasfresh-webui-frontend-dev
#
# About `-e "WEBUI_API_.."`:
# The API-URL for the webui-api service to connect is written into the docker image's config.js file when the container is run, 
# right before the nodejs server is started.
#

FROM nginx
COPY dist /usr/share/nginx/html

COPY config.js /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d

#add entry-script
COPY start_webui.sh /
RUN chmod +x /start_webui.sh

ENTRYPOINT ["/start_webui.sh"]
