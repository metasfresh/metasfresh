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

winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "alter database metasfresh rename to metasfresh_template_master_integration;"
winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "alter database metasfresh_template_master_integration is_template true;"

echo "The local database has been converted to a template database."
echo "You can drop this template database by running the following commands:"
echo ""
echo "winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c \"UPDATE pg_database SET datistemplate='false' WHERE datname='metasfresh_template_master_integration';\""
echo "winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c \"drop database if exists metasfresh_template_master_integration;\""
echo ""
echo "You can now proceed with creating the actual database from this template"