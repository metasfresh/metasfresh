@echo.
@echo --------------------------
@echo building maven artifacts
@echo --------------------------

docker build -f Dockerfile.common -t metas-mvn-common:latest . || @goto error
docker build -f Dockerfile.backend -t metas-mvn-backend:latest . || @goto error
docker build -f Dockerfile.camel -t metas-mvn-camel:latest . || @goto error

@echo.
@echo --------------------------
@echo building deployables
@echo --------------------------

docker build -f Dockerfile.backend.api -t metas-api:latest . || @goto error
docker build -f Dockerfile.backend.app -t metas-app:latest . || @goto error
docker build -f Dockerfile.camel.externalsystems -t metas-externalsystems:latest . || @goto error
docker build -f Dockerfile.frontend -t metas-frontend:latest . || @goto error
docker build -f Dockerfile.db -t metas-db:latest . || @goto error
docker build -f Dockerfile.db-init -t metas-db-init:latest . || @goto error

@REM docker build -f Dockerfile.procurement.frontend -t metas-procurement-frontend:latest . || @goto error

:success
@echo.
@echo --------------------------
@echo success
@echo --------------------------
@docker images --filter=reference="metas-*"
@echo.
@exit

:error
@echo.
@echo --------------------------
@echo failure
@echo --------------------------