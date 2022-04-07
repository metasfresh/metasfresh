docker build -f Dockerfile.common -t mvn-common:latest .
docker build -f Dockerfile.backend -t mvn-backend:latest .
docker build -f Dockerfile.camel -t mvn-camel:latest .

docker build -f Dockerfile.frontend -t yarn-frontend:latest .
