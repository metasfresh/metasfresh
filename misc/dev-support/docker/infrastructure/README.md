## How to reset the DB

* stop the whole thing with `docker-compose down`
* delete the DB's data volume with `docker volume rm metasfresh_postgres`
* restart again with `docker-compose up -d`

For bonus-points you can then do `docker-compose logs -f db` to see how the DB is repopulated.
Don't forget to apply the latest migration scripts after that.
