set "tag=local"
set "registry=mazorn"

@echo.
@echo --------------------------
@echo building maven artifacts
@echo --------------------------

docker build -f docker-builds/Dockerfile.common -t %registry%/metas-mvn-common:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.backend -t %registry%/metas-mvn-backend:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.camel -t %registry%/metas-mvn-camel:%tag% . || @goto error

docker build -f docker-builds/Dockerfile.junit -t %registry%/metas-junit:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.cucumber -t %registry%/metas-cucumber:%tag% . || @goto error


@echo.
@echo --------------------------
@echo building deployables
@echo --------------------------

docker build -f docker-builds/Dockerfile.backend.api -t %registry%/metas-api:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.backend.app -t %registry%/metas-app:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.camel.externalsystems -t %registry%/metas-externalsystems:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.frontend -t %registry%/metas-frontend:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.mobile -t %registry%/metas-mobile:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.db-standalone -t %registry%/metas-db:%tag% . || @goto error
docker build -f docker-builds/Dockerfile.db-preloaded -t %registry%/metas-db:%tag%-preloaded . || @goto error


@REM ----- for a rainy day -----
@REM docker build -f docker-builds/Dockerfile.e2e -t %registry%/metas-e2e:%tag% . || @goto error
@REM docker build -f docker-builds/Dockerfile.procurement.frontend -t %registry%/metas-procurement-frontend:%tag% . || @goto error
@REM ---------------------------


:success
@echo.
@echo --------------------------
@echo success
@echo --------------------------
@docker images --filter=reference="%registry%/metas-*"
@echo.
@exit

:error
@echo.
@echo --------------------------
@echo failure
@echo --------------------------