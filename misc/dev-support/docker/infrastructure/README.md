
## It needs to run with an env-File

In order to allow multiple databases, each running at the same time with the SQL from different base branches,
the `docker-compose.yml` contains environment variables.

Therefor, docker-compose.yml file needs to be run in conjunction with a dedicated env-File.
There is one env-File for each base branch (if one is missing, just add it :-D )

## There are scripts

There are shell scripts in the `scripts` folder.

### Example 1

To spin up the docker-compose infrastructure for e.g. the release-branch, run (tested with git bash under windows):
```bash
./10_reset_db_to_seed_dump.sh release
```

### Example 2

To spin up the docker-compose infrastructure with the **default** ports (e.g. `5432` for PostgreSQL), you can run:
```bash
./10_reset_db_to_seed_dump.sh default
```


## The long story - if you want to do it by foot

You can then run `docker-compose` with 
- specifying the base-branch that matches the code in your workspace
- specifying a project name that includes the branch name

e.g.
```bash
docker-compose --env-file ./env-files/intensive_care_uat.env --project-name ${BRANCH_NAME}_infrastructure build
```

### How to reset the DB or search

* for *even more* convenience (scnr), set the base branch to an environment variable with e.g. `BRANCH_NAME=intensive_care_uat`
* for *even more* convenience (scnr), set the base branch to an environment variable with e.g. `BRANCH_NAME=intensive_care_uat`
* stop the whole thing with `docker-compose --env-file ./env-files/${BRANCH_NAME}.env --project-name ${BRANCH_NAME}_infrastructure down`
* delete
  * the DB's data volume with `docker volume rm ${BRANCH_NAME}_metasfresh_postgres`
  * search's data volume with `docker volume rm ${BRANCH_NAME}_metasfresh_elasticsearch`
* (optionally) get the latest with `docker-compose --env-file ./env-files/${BRANCH_NAME}.env build --pull`
* restart again with `docker-compose --env-file ./env-files/${BRANCH_NAME}.env  --project-name ${BRANCH_NAME}_infrastructure up -d`

For bonus-points you can then do e.g. 
`docker-compose --env-file ./env-files/${BRANCH_NAME}.env logs -f db` to see how the DB is repopulated.
Don't forget to apply the latest migration scripts after that.

### The reset commands to copy&paste

```bash
BRANCH_NAME=your_branch_name
```

```bash
docker-compose --env-file ./env-files/${BRANCH_NAME}.env --project-name ${BRANCH_NAME}_infrastructure down
docker volume rm ${BRANCH_NAME}_metasfresh_postgres
docker volume rm ${BRANCH_NAME}_metasfresh_elasticsearch
docker-compose --env-file ./env-files/${BRANCH_NAME}.env build --pull
docker-compose --env-file ./env-files/${BRANCH_NAME}.env --project-name ${BRANCH_NAME}_infrastructure up -d
```
