#!/bin/sh

#
# %L
# metasfresh
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

# Source common utility functions
SCRIPT_DIR=$(dirname "$0")
. "${SCRIPT_DIR}/00_common.sh"

# We assume that the stuff was started with 10_reset_db_to_seed_dump.sh
# We assume that in the folder misc/dev-support/docker/infrastructure/env-files/ there exists an env file named ${BRANCH_NAME}.env
BRANCH_NAME=$(resolve_branch_name "$1")

set -u

# Detect if winpty is needed (only in interactive TTY on Windows)
WINPTY=$(get_winpty)
DOCKER_EXEC_FLAGS=$(get_docker_exec_flags)

echo "Stopping ${BRANCH_NAME}_postgrest"
$WINPTY docker stop ${BRANCH_NAME}_postgrest

$WINPTY docker exec $DOCKER_EXEC_FLAGS ${BRANCH_NAME}_db  psql -U postgres -c "alter database metasfresh rename to metasfresh_template_${BRANCH_NAME};"
$WINPTY docker exec $DOCKER_EXEC_FLAGS ${BRANCH_NAME}_db  psql -U postgres -c "alter database metasfresh_template_${BRANCH_NAME} with is_template true;"

echo "The local database has been converted to a template database."
echo "You can drop this template database by running "
echo ""
echo "./21_drop_template.sh ${BRANCH_NAME}"

echo ""
echo "You can now proceed with creating the actual database from this template"