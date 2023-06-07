#!/bin/sh

#
# %L
# master
# %%
# Copyright (C) 2023 metas GmbH
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

# We assume that the stuff was started with 10_reset_db_to_seed_dump.sh
# We assume that the stuff was started with 10_reset_db_to_seed_dump.sh
# We assume that in the folder misc/dev-support/docker/infrastructure/env-files/ there exists an env file named ${BRANCH_NAME}.env
if ! [ -z "$1" ]; then
    BRANCH_NAME=$1
else
    echo "!! The first parameter needs do correspond to an env-File !!"
    echo "!! E.g. to use the env-file env-files/release.env, run 20_convert_db_to_template.sh release !!"
    exit
fi

set -u

# the winpty is needed to avoid an error when running the script in git bash on windows

winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "UPDATE pg_database SET datistemplate='false' WHERE datname='metasfresh_template_${BRANCH_NAME}';"
winpty docker exec -it ${BRANCH_NAME}_db  psql -U postgres -c "drop database if exists metasfresh_template_${BRANCH_NAME};"

echo "The template-DB metasfresh_template_${BRANCH_NAME} was dropped and can be recreated with 20_convert_db_to_template.sh"
