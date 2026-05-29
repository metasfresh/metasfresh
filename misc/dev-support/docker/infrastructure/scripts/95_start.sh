#!/bin/sh

#
# %L
# master
# %%
# Copyright (C) 2024 metas GmbH
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

# We assume that in the folder misc/dev-support/docker/infrastructure/env-files/ there exists an env file named ${BRANCH_NAME}.env
BRANCH_NAME=$(resolve_branch_name "$1")

set -u

COMPOSE_FILE=../docker-compose.yml
ENV_FILE=../env-files/${BRANCH_NAME}.env

docker-compose --file ${COMPOSE_FILE} --env-file ${ENV_FILE} --project-name ${BRANCH_NAME}_infrastructure start
