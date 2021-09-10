## How to reset the DB or search

* stop the whole thing with `docker-compose down`
* delete 
  * the DB's data volume with `docker volume rm metasfresh_postgres`
  * search's data volume with `docker volume rm metasfresh_elasticsearch`
* (optionally) get the latest with `docker-compose build --pull`
* restart again with `docker-compose up -d`

For bonus-points you can then do e.g. `docker-compose logs -f db` to see how the DB is repopulated.
Don't forget to apply the latest migration scripts after that.

Reset commands to copy&paste
```bash
docker-compose down
docker volume rm metasfresh_postgres
docker volume rm metasfresh_elasticsearch
docker-compose build --pull
docker-compose up -d
```