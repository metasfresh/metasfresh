#!/bin/bash
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

set -e

# =============================================================================
# AUTO-DETECT METASFRESH ROOT (works from any working directory)
# =============================================================================
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Script is at misc/dev-support/bootstrap-scripts/ → metasfresh root is 3 levels up
METASFRESH_ROOT="$(cd "$SCRIPT_DIR/../../.." && pwd)"

if [[ ! -f "$METASFRESH_ROOT/backend/pom.xml" ]]; then
    echo "ERROR: Could not find metasfresh root (expected backend/pom.xml at $METASFRESH_ROOT)"
    exit 1
fi

# Use absolute path for .m2-local (critical on Windows Git Bash)
LOCAL_REPO="$(cd "$METASFRESH_ROOT" && pwd -W 2>/dev/null || pwd)/.m2-local"
mkdir -p "$LOCAL_REPO"

echo "Metasfresh root: $METASFRESH_ROOT"
echo "Local Maven repo: $LOCAL_REPO"

# =============================================================================
# JAVA VERSION
# =============================================================================
# Java 8 for backend; override JAVA8_HOME or JAVA_HOME as needed
if [[ -n "${JAVA8_HOME:-}" ]]; then
    export JAVA_HOME="$JAVA8_HOME"
elif [[ -z "${JAVA_HOME:-}" ]]; then
    # Fallback: look for temurin-1.8 in standard location
    for jdk in "$HOME"/.jdks/temurin-1.8.*; do
        [[ -d "$jdk" ]] && export JAVA_HOME="$jdk" && break
    done
fi
echo "JAVA_HOME: $JAVA_HOME"

# =============================================================================
# PARSE OPTIONS
# =============================================================================
CLEAN_FLAG=''
SKIP_TESTS_FLAG=''
PARALLEL_FLAG=''
DEPS_ONLY=false

for arg in "$@"; do
    case "$arg" in
        --clean)      CLEAN_FLAG='clean' ;;
        --skip-tests) SKIP_TESTS_FLAG='-DskipTests' ;;
        --parallel)   PARALLEL_FLAG='-T2C' ;;
        --deps-only)  DEPS_ONLY=true ;;
        *)            echo "Unknown option: $arg"; exit 1 ;;
    esac
done

GOALS="${CLEAN_FLAG} install"

MULTITHREAD_PARAM="${PARALLEL_FLAG}"

ADDITIONAL_PARAMS="-Djib.skip=true -Dmaven.gitcommitid.skip=true -Dlicense.skip=true ${SKIP_TESTS_FLAG}"

SETTINGS="$METASFRESH_ROOT/misc/dev-support/maven/settings.xml"
REPO_FLAG="-Dmaven.repo.local=$LOCAL_REPO"
MVN_FLAGS="--settings $SETTINGS $REPO_FLAG --no-transfer-progress"

# =============================================================================
# BUILD
# =============================================================================
echo ""
echo "$(date): Starting build (goals: $GOALS)"
echo ""

mvn --file "$METASFRESH_ROOT/misc/parent-pom/pom.xml" $MVN_FLAGS $ADDITIONAL_PARAMS $GOALS && \

mvn --file "$METASFRESH_ROOT/misc/de-metas-common/pom.xml" $MVN_FLAGS $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS

if $DEPS_ONLY; then
    echo "$(date): Done (deps-only: parent-pom + de-metas-common)"
    exit 0
fi

JAVA17_HOME="${JAVA17_HOME:-}"

mvn -e --file "$METASFRESH_ROOT/backend/pom.xml" $MVN_FLAGS $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS && \

if [[ -n "$JAVA17_HOME" ]]; then
    JAVA_HOME="$JAVA17_HOME" mvn --file "$METASFRESH_ROOT/misc/services/camel/pom.xml" $MVN_FLAGS $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS && \
    JAVA_HOME="$JAVA17_HOME" mvn --file "$METASFRESH_ROOT/misc/services/procurement-webui/procurement-webui-backend/pom.xml" $MVN_FLAGS $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS && \
    JAVA_HOME="$JAVA17_HOME" mvn --file "$METASFRESH_ROOT/misc/services/federated-rabbitmq/pom.xml" $MVN_FLAGS $MULTITHREAD_PARAM $ADDITIONAL_PARAMS $GOALS
else
    echo ""
    echo "WARNING: JAVA17_HOME not set — skipping camel, procurement-webui-backend, federated-rabbitmq"
    echo "Set JAVA17_HOME to build Java 17 services."
fi

echo ""
echo "$(date): Done"
