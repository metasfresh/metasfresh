@echo off

set "mfversion=local"
set "mfregistry=metasfresh"
set /P version=<docker-builds/version.info
for /F "tokens=*" %%g in ('powershell -Command "& {Get-Date -Format """"yyMMddHHmm""""}"') do (set buildnr=%%g)

echo.
echo --------------------------
echo building %version%-%mfversion%.%buildnr%
@echo on


@echo.
@echo --------------------------
@echo building maven artifacts
@echo --------------------------

docker build -f docker-builds/Dockerfile.common -t %mfregistry%/metas-mvn-common:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.backend -t %mfregistry%/metas-mvn-backend:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.camel -t %mfregistry%/metas-mvn-camel:%mfversion% . || @goto error

docker build -f docker-builds/Dockerfile.junit -t %mfregistry%/metas-junit:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.cucumber -t %mfregistry%/metas-cucumber:%mfversion% . || @goto error


@echo.
@echo --------------------------
@echo building deployables
@echo --------------------------

docker build -f docker-builds/Dockerfile.backend.api -t %mfregistry%/metas-api:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.backend.app -t %mfregistry%/metas-app:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.camel.externalsystems -t %mfregistry%/metas-externalsystems:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.frontend -t %mfregistry%/metas-frontend:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.mobile -t %mfregistry%/metas-mobile:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.db-standalone -t %mfregistry%/metas-db:%mfversion% . || @goto error
docker build -f docker-builds/Dockerfile.db-preloaded -t %mfregistry%/metas-db:%mfversion%-preloaded . || @goto error


@echo.
@echo --------------------------
@echo building classic-compatible deployables
@echo --------------------------
docker build -f docker-builds/Dockerfile.backend.api.compat -t %mfregistry%/metas-api:%mfversion%-compat . || @goto error
docker build -f docker-builds/Dockerfile.backend.app.compat --build-arg VERSION=%version%-%mfversion%.%buildnr% -t %mfregistry%/metas-app:%mfversion%-compat . || @goto error
docker build -f docker-builds/Dockerfile.mobile.compat -t %mfregistry%/metas-mobile:%mfversion%-compat . || @goto error
docker build -f docker-builds/Dockerfile.frontend.compat -t %mfregistry%/metas-frontend:%mfversion%-compat . || @goto error

@REM ----- for a rainy day -----
@REM docker build -f docker-builds/Dockerfile.e2e -t %mfregistry%/metas-e2e:%mfversion% . || @goto error
@REM docker build -f docker-builds/Dockerfile.procurement.frontend -t %mfregistry%/metas-procurement-frontend:%mfversion% . || @goto error
@REM ---------------------------


:success
@echo.
@echo --------------------------
@echo success
@echo --------------------------
@docker images --filter=reference="%mfregistry%/metas-*"
@echo.
@exit

:error
@echo.
@echo --------------------------
@echo failure
@echo --------------------------