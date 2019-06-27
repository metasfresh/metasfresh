# this docker image contains electron; 
# running the docker image with electron fails, probably due to one of these reasons: https://github.com/cypress-io/cypress/issues/1235
#FROM cypress/base:10

# This docker image contains the latest (currently 71) chrome;
# See the Dockerfile here: https://github.com/cypress-io/cypress-docker-images/blob/master/browsers/chrome69/Dockerfile
FROM cypress/browsers:node11.13.0-chrome73

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1


RUN apt-get update && apt-get -y upgrade && apt-get -y autoremove

WORKDIR /e2e

# I think this is needed so the npm intall will get all needed dependencies
COPY package.json .

#TODO: verify that with our package.json file, only the npm install down there is needed
RUN npm install --save-dev cypress@3.3.1
RUN npm install

COPY .babelrc .

COPY reporter-config.json .

COPY cypress.json .
COPY src ./src
COPY cypress ./cypress

COPY webpack.config.js .


RUN $(npm bin)/cypress verify

#add entry-script
COPY run_cypress.sh /
# owner may read and execute
RUN chmod 0500 /run_cypress.sh

ENTRYPOINT ["/run_cypress.sh"]
