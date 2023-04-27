
## Build

### local
build all docker images on a local windows machine by executing ```build.cmd``` from repository root<br>
docker images will not get pushed to any regsitry and just sit on your local system with _:local_ tags<br>

### CICD (github actions)
<<<<<<< HEAD

- pipeline is located under _.github\workflows\cicd.yaml_ and gets executed on push<br>
- executions can be followed under: https://github.com/metasfresh/metasfresh/actions<br>
- junit and cucumber test results will be accumulated under: https://metasfresh.testspace.com/<br>
- images will get pushed to our docker hub registries with _{branch-name}.{build-number}_ tags<br>
=======
pipeline is located under _.github\workflows\cicd.yaml_ and gets executed on push<br>
executions can be followed under: https://github.com/metasfresh/metasfresh/actions<br>
junit and cucumber test results will be accumulated under: https://metasfresh.testspace.com/<br>
images will get pushed to our docker hub registries with _{branch-name}.{build-number}_ tags<br>
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))

### (DB) Version
to be somewhat compatible with our current versioning _\docker-builds\version.info_ holds information on what _dbversion_ this build will produce/expect<br>

<<<<<<< HEAD
### Further reading about github actions

- https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry
=======
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))

## Run

### local
run a fresh metasfresh instance via docker compose on your local system by executing ```run.cmd``` from repository root<br>
access it under http://localhost with user: metasfresh and pw: metasfresh<br>
or under http://localhost:8880/mobile with user: cynthia and pw: metasfresh<br>
tear it all down with ```stop.cmd```<br>

### k8s
can be run on k8s using the _\mf15-kubernetes\distribution\metasfresh_ helm chart<br>

### instances.metasfresh.com
the build also produces slightly adjusted images compatible with our current rollout process<br>
which can be rolled out using _{branch-name}.{build-number}-compat_ as BaseVersion<br>
an overview of existing tags can be found here: https://hub.docker.com/repository/registry-1.docker.io/metasfresh/metasfresh-app/tags?page=1&ordering=last_updated&name=compat<br>


## Tests
CICD: junit and cucumber test results will be accumulated under: https://metasfresh.testspace.com/<br>
For local builds:<br>

### JUnit
unit tests get executed during the build<br>
you can access the report files on your local machine after running the following command from repository root<br>
```docker run --rm -v "$(pwd)/docker-builds/junit:/reports" metasfresh/metas-junit:local```<br>
in your _docker-builds/junit_ folder<br>

### Cucumber
to run cucumber tests, go to _\docker-builds\cucumber_ and execute ```run.cmd```<br>
should take about 60 minutes<br>

### Cypress
to run cucumber tests, go to _\docker-builds\e2e and execute ```run.cmd```<br>
should take about 120 minutes localy<br>
currently not all tests are green though<br>
are currently disabled for github actions since they are not completed even after 4 hours<br>
