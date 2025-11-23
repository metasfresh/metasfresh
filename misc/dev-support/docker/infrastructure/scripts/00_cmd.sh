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

# Source common utility functions
SCRIPT_DIR=$(dirname "$0")
. "${SCRIPT_DIR}/00_common.sh"

# Check if first parameter is an existing env file name
if [ ! -z "$1" ] && [ -f "../env-files/$1.env" ]; then
    # First param is a branch name
    BRANCH_NAME=$1
    shift
else
    # Auto-detect branch name
    echo "Auto-detecting branch from git..."
    BRANCH_NAME=$(auto_detect_branch_name)
    echo "Using env file: ${BRANCH_NAME}.env"
fi

if [ -z "$1" ]; then
    echo "!! Command parameter is required !!"
    echo "!! Usage: $0 [branch_name] <docker-compose-command> [args...] !!"
    echo "!! Example: $0 logs -f db !!"
    echo "!! Example: $0 release logs -f db !!"
    exit 1
fi

set -u

COMPOSE_FILE=../docker-compose.yml
ENV_FILE=../env-files/${BRANCH_NAME}.env

# Execute docker-compose command
docker-compose --file ${COMPOSE_FILE} --env-file ${ENV_FILE} --project-name ${BRANCH_NAME}_infrastructure "$@"

