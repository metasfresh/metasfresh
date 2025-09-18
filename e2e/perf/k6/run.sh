#!/usr/bin/env bash
#
# %L
# master
# %%
# Copyright (C) 2025 metas GmbH
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

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

DEBUG_PARAMS=""
#DEBUG_PARAMS="--verbose --http-debug"

# Configurable via env vars with safe defaults
export BASE_URL="${BASE_URL:-http://localhost:8282/api/v2}"
export AUTH_TOKEN="${AUTH_TOKEN}"
export DATA_STRATEGY="${DATA_STRATEGY:-generated}"   # 'existing' | 'generated' | 'mixed'
export EXISTING_RATIO="${EXISTING_RATIO:-0.7}"   # used when DATA_STRATEGY='mixed'
export ORG_CODE="${ORG_CODE:-001}"                  # need to be provided, unless set in the request-body
export BP_BODY_FILES="${BP_BODY_FILES:-../test-data/extracted-bpartner-requests.json}"

# If VUS and DURATION are not specified, k6 wil use a ramping-vus plan. See bpartner-load-test.js
export VUS="${VUS:-100}" # (VU=concurrent virtual user)
export DURATION="${DURATION:-500s}"

echo "Running k6 with:"
echo "  BASE_URL=${BASE_URL}"
echo "  DATA_STRATEGY=${DATA_STRATEGY}"
echo "  EXISTING_RATIO=${EXISTING_RATIO}"
echo "  ORG_CODE=${ORG_CODE}"
echo "  BP_BODY_FILES=${BP_BODY_FILES}"

k6 run ${DEBUG_PARAMS} \
  -e BASE_URL="${BASE_URL}" \
  -e AUTH_TOKEN="${AUTH_TOKEN}" \
  -e DATA_STRATEGY="${DATA_STRATEGY}" \
  -e EXISTING_RATIO="${EXISTING_RATIO}" \
  -e ORG_CODE="${ORG_CODE}" \
  -e BP_BODY_FILES="${BP_BODY_FILES}" \
  "${SCRIPT_DIR}/bpartner/bpartner-load-test.js"
