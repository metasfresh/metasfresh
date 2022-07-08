
## Build

### local
build all docker images on a local windows machine by executing ```build.cmd``` from repository root
docker images will not get pushed to any regsitry and just sit on your local system with _:local_ tags

### CICD (github actions)
pipeline is located under _.github\workflows\cicd.yaml_ and gets executed on push
executions can be followed under: https://github.com/metasfresh/metasfresh/actions
junit and cucumber test results will be accumulated under: https://metasfresh.testspace.com/
images will get pushed to our docker hub registries with _{branch-name}.{build-number}_ tags

### (DB) Version
to be somewhat compatible with our current versioning _\docker-builds\version.info_ holds information on what _dbversion_ this build will produce/expect


## Run

### local
run a fresh metasfresh instance via docker compose on your local system by executing ```run.cmd``` from repository root
access it under http://localhost with user: metasfresh and pw: metasfresh
or under http://localhost:8880/mobile with user: cynthia and pw: metasfresh
tear it all down with ```stop.cmd```

### k8s
can be run on k8s using the _\mf15-kubernetes\distribution\metasfresh_ helm chart

### instances.metasfresh.com
the build also produces slightly adjusted images compatible with our current rollout process
which can be rolled out using _{branch-name}.{build-number}-compat_ as BaseVersion
an overview of existing tags can be found here: https://hub.docker.com/repository/registry-1.docker.io/metasfresh/metasfresh-app/tags?page=1&ordering=last_updated&name=compat


## Tests
CICD: junit and cucumber test results will be accumulated under: https://metasfresh.testspace.com/
For local builds:

### JUnit
unit tests get executed during the build
you can access the report files on your local machine after running the following command from repository root 
```docker run --rm -v "$(pwd)/docker-builds/junit:/reports" metasfresh/metas-junit:local```
in your _docker-builds/junit_ folder

### Cucumber
to run cucumber tests, go to _\docker-builds\cucumber_ and execute ```run.cmd```
should take about 60 minutes

### Cypress
to run cucumber tests, go to _\docker-builds\e2e and execute ```run.cmd```
should take about 120 minutes localy
currently not all tests are green though
are currently disabled for github actions since they are not completed even after 4 hours
