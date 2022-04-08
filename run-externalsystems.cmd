docker run -d --name my-externalsystems my-externalsystems:latest
docker ps -a
docker logs --tail 100 -f my-externalsystems
