#!/bin/sh

#
# %L
# de.metas.cucumber
# %%
# Copyright (C) 2022 metas GmbH
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 2 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program. If not, see
# <http://www.gnu.org/licenses/gpl-2.0.html>.
# L%
#

set -e
set -u

COMPOSE_FILE=../../../misc/dev-support/docker/infrastructure/docker-compose.yml

# reset the database
docker-compose --file $COMPOSE_FILE down
docker volume rm metasfresh_postgres
docker volume rm metasfresh_elasticsearch
docker-compose --file $COMPOSE_FILE build --pull
docker-compose --file $COMPOSE_FILE up -d

echo "The local database is now reset to the seed dump."
echo "Now you can use this command:" 
echo ""
echo "docker-compose  --file $COMPOSE_FILE logs -f db"
echo ""
echo "..to see when the DB is actually up."
echo "When the DB is up, apply the local migration scripts and then proceed with converting this DB into a template."
