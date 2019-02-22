# this docker image contains electron; 
# running the docker image with electron fails, probably due to one of these reasons: https://github.com/cypress-io/cypress/issues/1235
#FROM cypress/base:10

# This docker image contains the latest (currently 71) chrome;
# See the Dockerfile here: https://github.com/cypress-io/cypress-docker-images/blob/master/browsers/chrome69/Dockerfile
FROM cypress/browsers:chrome69

# to make sure that the cache is only used during one day, run docker build --build-arg CACHEBUST=$(date "+%Y-%m-%d")
# that way we should get the latest updates since the release of our base image 
# thx to https://github.com/moby/moby/issues/1996#issuecomment-185872769
ARG CACHEBUST=1

RUN apt-get update && apt-get -y upgrade && apt-get -y autoremove

WORKDIR /e2e

RUN npm install --save-dev cypress@3.1.5
RUN npm install --save-dev @cypress/snapshot@2.0.1
RUN npm install --save-dev @cypress/webpack-preprocessor@4.0.2

COPY reporter-config.json /e2e

# note: if we had a recent git version in here, we could follow https://stackoverflow.com/a/3489576 to check out the revision we need
COPY cypress-git-repo /e2e

# The following npm install is needed; without it, running cypress would fail as follows
# --------------
# Your pluginsFile is set to '/e2e/cypress/plugins/index.js', but either the file is missing, it contains a syntax error, or threw an error when required. The pluginsFile must be a .js or .coffee file.

# Please fix this, or set 'pluginsFile' to 'false' if a plugins file is not necessary for your project.

# The following error was thrown:

# Error: Cannot find module 'webpack'
#     at Function.Module._resolveFilename (module.js:485:15)
# --------------
RUN npm install

# thx to https://docs.cypress.io/guides/tooling/reporters.html#Multiple-Reporters
# mocha 6.0.0 and 6.0.1 don't work, thx to https://github.com/cypress-io/cypress/issues/3537
RUN npm install --save-dev mocha@5.2.0 mocha-multi-reporters@1.1.7 mocha-junit-reporter@1.18.0

RUN $(npm bin)/cypress verify

#add entry-script
COPY run_cypress.sh /
# owner may read and execute
RUN chmod 0500 /run_cypress.sh

ENTRYPOINT ["/run_cypress.sh"]
