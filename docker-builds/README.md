
## Build

### local
Build all docker images on a local windows machine by executing ```build.cmd``` from repository root<br>
The resulting docker images will not get pushed to any registry and just sit on your local system with _:local_ or _:local-compat_ tags<br>
<br>
The java builds need some third party maven package. Since we now get them from github (instead of nexus) and github packages currently only supports access to maven repositories when you are logged in, you need to supply credentials for the local build to work.<br>
Otherwise you will encounter: `[ERROR] Failed to execute goal on project metasfresh-assemblies` [...] `401 Unauthorized`.<br>
<br>
Do the following
* create a classic PAT: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token
* select only the packages read scope
* put the credentials into `docker-builds/mvn/local-settings.xml` in the following section
```
	<servers>
		...
		<server>
			<id>github-3rdparty-maven</id>
			<username>your github username goes here</username>
			<password>your PAT goes here</password>
		</server>
	</servers>
```
The `docker-builds/mvn/local-settings.xml` is a copy of `docker-builds/mvn/settings.xml` that gets/got created on the first `build.cmd`.

### CICD (github actions)

- pipeline is located under _.github\workflows\cicd.yaml_ and gets executed on push<br>
- executions can be followed under: https://github.com/metasfresh/metasfresh/actions<br>
- junit and cucumber test results will be accumulated under: https://metasfresh.testspace.com/<br>
- images will get pushed to our docker hub registries with _{branch-name}.{build-number}_ tags<br>

#### Further reading about github related topics

- actions: https://docs.github.com/en/actions
- packages: https://docs.github.com/en/packages
- packages (mvn): https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry

### (DB) Version
to be somewhat compatible with our current versioning _\docker-builds\version.info_ holds information on what _dbversion_ this build will produce/expect<br>

### Further reading about github actions

- https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry

## Run

### local
run a fresh metasfresh instance via docker compose on your local system by executing ```run.cmd``` from repository root<br>
access it under http://localhost with user: metasfresh and pw: metasfresh<br>
or under http://localhost:8880/mobile with user: cynthia and pw: metasfresh<br>
tear it all down with ```stop.cmd```<br>

by default this will use the images produced by your last local build<br>
this can be adjusted by modifying the *mfversion* property in _docker-builds/compose/.env_<br>
so it is possible to locally run images produced by the github actions pipeline<br>

### k8s
can be run on k8s using the _\mf15-kubernetes\distribution\metasfresh_ helm chart<br>

### instances.metasfresh.com
the build also produces slightly adjusted images compatible with our current rollout process<br>
which can be rolled out using _{branch-name}.{build-number}-compat_ as BaseVersion<br>
an overview of existing tags can be found here: https://hub.docker.com/repository/registry-1.docker.io/metasfresh/metasfresh-app/tags?page=1&ordering=last_updated&name=compat<br>


## Tests
CICD: junit and cucumber test results will be accumulated under: https://metasfresh.testspace.com/<br>
in addition to that, a database image with the post cucumber run state is available for every cucumber run as:<br>
`metasfresh/metas-db:<tag>-postcucumber`<br>
<br>
For local builds:<br>

### JUnit
unit tests get executed during the build<br>
you can access the report files on your local machine after running the following command from repository root<br>
```docker run --rm -v "$(pwd)/docker-builds/junit:/reports" metasfresh/metas-junit:local```<br>
in your _docker-builds/junit_ folder<br>

### Cucumber
to run cucumber tests, go to _docker-builds/cucumber_ and execute ```run.cmd```<br>
this can take about 60 - 120 minutes<br>
execute ```stop.cmd``` to clean up running and/or stopped containers<br>
<br>
cucumber results get stored under __docker-builds/cucumber/cucumber_<br>
in addition to that, a database image with the post cucumber run state is available as: `metasfresh/metas-db:local-postcucumber`<br>
which can be run like this: ```docker run --rm -p 15432:5432 metasfresh/metas-db:local-postcucumber```
to be accessed under *localhost:15432*<br>
alternatively the local docker compose run can be adjusted by switching the *dbqualifier* in _docker-builds/compose/.env_ to *postcucumber*<br>
to run with a database in post cucumber state<br>
<br>
by default cucumber will use the images produced by your last local build<br>
this can be adjusted by modifying the *mfversion* property in _docker-builds/cucumber/.env_<br>
so it is possible to locally run cucumber tests for images produced by the github actions pipeline<br>
<br>
by default cucumber will run tests for all features defined under _backend/de.metas.cucumber/src/test/resources_<br>
this can be adjusted by modifying the *cucumber.command* section in _docker-builds/cucumber/compose.yml_<br>
<br>

### Cypress
to run cucumber tests, go to _\docker-builds\e2e and execute ```run.cmd```<br>
should take about 120 minutes localy<br>
currently not all tests are green though<br>
are currently disabled for github actions since they are not completed even after 4 hours<br>
