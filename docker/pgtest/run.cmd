docker run -d --name pgtest -p 6432:5432 pgtest:latest
docker ps
docker logs --tail 500 -f pgtest
