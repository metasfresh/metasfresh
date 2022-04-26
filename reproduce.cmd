@echo.
@echo [this works]
docker build -f Dockerfile.oraclelinux .

@echo.
@echo [but this does not]
docker build -f Dockerfile.mvn-jdk .
