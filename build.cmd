@echo.
@echo --------------------------
@echo building maven artifacts
@echo --------------------------

docker build -f docker-builds/Dockerfile.common -t mazorn/metas-mvn-common:local . || @goto error
docker build -f docker-builds/Dockerfile.backend -t mazorn/metas-mvn-backend:local . || @goto error
docker build -f docker-builds/Dockerfile.camel -t mazorn/metas-mvn-camel:local . || @goto error

docker build -f docker-builds/Dockerfile.junit -t mazorn/metas-junit:local . || @goto error

@echo.
@echo --------------------------
@echo building deployables
@echo --------------------------

docker build -f docker-builds/Dockerfile.backend.api -t mazorn/metas-api:local . || @goto error
docker build -f docker-builds/Dockerfile.backend.app -t mazorn/metas-app:local . || @goto error
docker build -f docker-builds/Dockerfile.camel.externalsystems -t mazorn/metas-externalsystems:local . || @goto error
docker build -f docker-builds/Dockerfile.frontend -t mazorn/metas-frontend:local . || @goto error
docker build -f docker-builds/Dockerfile.mobile -t mazorn/metas-mobile:local . || @goto error
docker build -f docker-builds/Dockerfile.db-standalone -t mazorn/metas-db:local . || @goto error
docker build -f docker-builds/Dockerfile.db-preloaded -t mazorn/metas-db:local-preloaded . || @goto error

@REM docker build -f docker-builds/Dockerfile.procurement.frontend -t mazorn/metas-procurement-frontend:local . || @goto error
@REM docker build -f docker-builds/Dockerfile.cucumber -t mazorn/metas-cucumber:local . || @goto error
@REM docker build -f docker-builds/Dockerfile.e2e -t mazorn/metas-e2e:local . || @goto error

:success
@echo.
@echo --------------------------
@echo success
@echo --------------------------
@docker images --filter=reference="mazorn/metas-*"
@echo.
@exit

:error
@echo.
@echo --------------------------
@echo failure
@echo --------------------------