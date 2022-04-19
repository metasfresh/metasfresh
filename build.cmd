docker build -f Dockerfile.common -t mvn-common:latest . || @goto error

docker build -f Dockerfile.backend -t mvn-backend:latest . || @goto error
docker build -f Dockerfile.camel -t mvn-camel:latest . || @goto error

docker build -f Dockerfile.backend.api -t my-api:latest . || @goto error
docker build -f Dockerfile.backend.app -t my-app:latest . || @goto error
docker build -f Dockerfile.camel.externalsystems -t my-externalsystems:latest . || @goto error
docker build -f Dockerfile.frontend -t my-frontend:latest . || @goto error

docker build -f Dockerfile.db -t my-db:latest . || @goto error
docker build -f Dockerfile.db-init -t my-db-init:latest . || @goto error

@REM docker build -f Dockerfile.procurement.frontend -t my-procurement-frontend:latest . || @goto error

:success
@echo.
@echo --------------------------
@echo success
@echo --------------------------
@exit

:error
@echo.
@echo --------------------------
@echo failure
@echo --------------------------