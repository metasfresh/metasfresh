@echo off

@REM Simple Script to run a docker-based build - kindof like github-actions are doing it
@REM
@REM
@REM This skript now builds against a local docker registry that runs on localhost:5000
@REM
@REM If instead you want to build against dockerhub, you need to
@REM set "registry="
echo --------------------------
echo starting local docker-registry that persists data between script restarts
echo --------------------------
docker run -d -p 5000:5000 --name local-registry -v local-registry-data:/var/lib/registry registry:2
set "registry=localhost:5000/"

@REM This skript now runs with the maven build-settings from misc/dev-support/maven
@REM
@REM If instead you want to run with the maven-settings that the github-action runs with, you need to
@REM set "maven_settings=docker-builds\mvn\settings.xml"
set "maven_settings=misc\dev-support\maven\settings.xml"

set "pubregistry=%registry%metasfresh"
set "qualifier=local"

set /P version=<docker-builds/version.info
for /F "tokens=*" %%g in ('powershell -Command "& {Get-Date}"') do (set buildtime=%%g)
for /F "tokens=*" %%g in ('powershell -Command "& {Get-Date -Format """"yyMMddHHmm""""}"') do (set buildnr=%%g)

if not exist docker-builds\mvn\local-settings.xml ( copy %maven_settings% docker-builds\mvn\local-settings.xml)

(
	echo build.name=local windows
	echo build.system=%ComputerName%
	echo build.time=%buildtime%
	echo build.user=%USERNAME%
	echo build.version=%version%.3-%qualifier%.%buildnr%
	echo build.number=%buildnr%
) > docker-builds/metadata/build-info.properties

(
	echo git.remote.origin.url=https\://github.com/metasfresh/metasfresh.git
	echo git.branch=n/a
	echo git.commit.id=n/a
	echo git.commit.message=n/a
) > docker-builds/metadata/git.properties

echo.
echo --------------------------
echo declaring metadata
echo --------------------------
echo.
type docker-builds\metadata\build-info.properties
echo.
type docker-builds\metadata\git.properties
echo.
@echo on


@echo.
@echo --------------------------
@echo building maven artifacts
@echo --------------------------

docker build -f docker-builds/Dockerfile.common --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-mvn-common:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.backend --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-mvn-backend:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.backend.dist --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-mvn-backend-dist:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-mvn-camel:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.dist --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-mvn-camel-dist:%qualifier% . || @goto error

docker build -f docker-builds/Dockerfile.junit --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-junit:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.junit --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-camel-junit:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.cucumber --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-cucumber:%qualifier% . || @goto error


@echo.
@echo --------------------------
@echo building deployables
@echo --------------------------

docker build -f docker-builds/Dockerfile.backend.api --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-api:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.backend.app --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-app:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.externalsystems --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-externalsystems:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.camel.edi --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-edi:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.frontend --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-frontend:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.mobile --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-mobile:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.mobile.test --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-mobile-test:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.db-init --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-db:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.db-migration-tool --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-db-migration-tool:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.db-preloaded --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-db:%qualifier%-preloaded . || @goto error

docker build -f docker-builds/Dockerfile.procurement.backend --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% --secret id=mvn-settings,src=docker-builds/mvn/local-settings.xml -t %pubregistry%/metas-procurement-backend:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.procurement.nginx --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-procurement-nginx:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.procurement.rabbitmq --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-procurement-rabbitmq:%qualifier% . || @goto error
docker build -f docker-builds/Dockerfile.procurement.frontend --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-procurement-frontend:%qualifier% . || @goto error

@echo --------------------------
@echo building classic-compatible deployables
@echo --------------------------

docker build -f docker-builds/Dockerfile.backend.api.compat --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-api:%qualifier%-compat . || @goto error
docker build -f docker-builds/Dockerfile.backend.app.compat --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-app:%qualifier%-compat . || @goto error
docker build -f docker-builds/Dockerfile.mobile.compat --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-mobile:%qualifier%-compat . || @goto error
docker build -f docker-builds/Dockerfile.frontend.compat --build-arg REGISTRY=%registry% --build-arg REFNAME=%qualifier% -t %pubregistry%/metas-frontend:%qualifier%-compat . || @goto error


:success
@echo.
@echo --------------------------
@echo success
@echo --------------------------
@docker images --filter=reference="%pubregistry%/metas-*"
@echo.
@exit

:error
@echo.
@echo --------------------------
@echo failure
@echo --------------------------