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
set -u # this will fail this script if no branch name is given as parameter

# We assume that the stuff was started with 10_reset_db_to_seed_dump.sh
BRANCH_NAME=$1

# the winpty is needed to avoid an error when running the script in git bash on windows

winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "drop database if exists metasfresh;"
winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "create database metasfresh template metasfresh_template_master_integration;"

echo "The local database has been recreated from the template database."
echo "You can rerun this script to reset the local database to the template database."
