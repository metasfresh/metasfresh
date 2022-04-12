docker build -f Dockerfile.common -t mvn-common:latest .
docker build -f Dockerfile.backend -t mvn-backend:latest .
docker build -f Dockerfile.camel -t mvn-camel:latest .

docker build -f Dockerfile.backend.api -t my-api:latest .
docker build -f Dockerfile.backend.app -t my-app:latest .
docker build -f Dockerfile.camel.externalsystems -t my-externalsystems:latest .
docker build -f Dockerfile.frontend -t yarn-frontend:latest .

docker build -f Dockerfile.procurement.frontend -t yarn-frontend:latest .