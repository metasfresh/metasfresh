@echo.
@echo --------------------------
@echo building maven artifacts
@echo --------------------------

docker build -f Dockerfile.common -t mazorn/metas-mvn-common:local . || @goto error
docker build -f Dockerfile.backend -t mazorn/metas-mvn-backend:local . || @goto error
docker build -f Dockerfile.camel -t mazorn/metas-mvn-camel:local . || @goto error

docker build -f Dockerfile.junit -t mazorn/metas-junit:local . || @goto error

@echo.
@echo --------------------------
@echo building deployables
@echo --------------------------

docker build -f Dockerfile.backend.api -t mazorn/metas-api:local . || @goto error
docker build -f Dockerfile.backend.app -t mazorn/metas-app:local . || @goto error
docker build -f Dockerfile.camel.externalsystems -t mazorn/metas-externalsystems:local . || @goto error
docker build -f Dockerfile.frontend -t mazorn/metas-frontend:local . || @goto error
docker build -f Dockerfile.mobile -t mazorn/metas-mobile:local . || @goto error
docker build -f Dockerfile.db-standalone -t mazorn/metas-db:local . || @goto error
docker build -f Dockerfile.db-preloaded -t mazorn/metas-db:local-preloaded . || @goto error

@REM docker build -f Dockerfile.procurement.frontend -t mazorn/metas-procurement-frontend:local . || @goto error
@REM docker build -f Dockerfile.cucumber -t mazorn/metas-cucumber:local . || @goto error
@REM docker build -f Dockerfile.e2e -t mazorn/metas-e2e:local . || @goto error

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