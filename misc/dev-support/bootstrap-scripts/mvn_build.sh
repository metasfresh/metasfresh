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

set -e          # Exit immediately if a command exits with a non-zero status
set -o pipefail # Return value of a pipeline is the status of the last command to exit with a non-zero status

# ============================================================================
# Configuration
# ======================================================================

# Java versions for different build phases
# Java 8 for: parent-pom, de-metas-common, backend
# Java 17 for: services (camel, procurement-webui, federated-rabbitmq)

# Default Java 8 home (only used if JAVA8_HOME is not already set)
DEFAULT_JAVA8_HOME="$HOME/.jdks/temurin-1.8.0_452"

# Default Java 17 home (only used if JAVA17_HOME is not already set)
DEFAULT_JAVA17_HOME="$HOME/.jdks/temurin-17.0.15"

# Multithreading configuration
MULTITHREAD_PARAM=''
#MULTITHREAD_PARAM='-T2C'

# Additional Maven parameters
ADDITIONAL_PARAMS='-Djib.skip=true -Dmaven.gitcommitid.skip=true -Dlicense.skip=true'
#ADDITIONAL_PARAMS="${ADDITIONAL_PARAMS} -DskipTests"
#ADDITIONAL_PARAMS="${ADDITIONAL_PARAMS} -Dmaven.test.skip=true"

# Backend-specific parameters (migration scripts test skip)
# By default, skip migration scripts test for faster builds
# Override by setting environment variable: export SKIP_MIGRATION_SCRIPTS_TEST=false
SKIP_MIGRATION_SCRIPTS_TEST="${SKIP_MIGRATION_SCRIPTS_TEST:-true}"
BACKEND_PARAMS="-DSKIP_MIGRATION_SCRIPTS_TEST=${SKIP_MIGRATION_SCRIPTS_TEST}"

# Default Maven goals (used if no goals provided as arguments)
# Incremental build by default (no clean) for faster development cycles
# Use --clean flag or pass "clean install" explicitly for full clean builds
DEFAULT_GOALS='install'

# ============================================================================
# Functions
# ============================================================================

# Function to run Maven build and handle errors
run_mvn_build() {
    local module_name="$1"
    local java_version="$2"
    shift 2
    local mvn_args=("$@")

    echo "$(date): Building $module_name ($java_version)..."

    if JAVA_HOME="$java_version" mvn "${mvn_args[@]}"; then
        echo "$(date): ✓ $module_name build succeeded"
        return 0
    else
        local exit_code=$?
        echo ""
        echo "============================================================================"
        echo "ERROR: Build failed for $module_name"
        echo "============================================================================"
        echo "Exit code: $exit_code"
        echo "Java version: $java_version"
        echo "Maven command: mvn ${mvn_args[*]}"
        echo "============================================================================"
        exit $exit_code
    fi
}

show_help() {
    cat << EOF
Usage: $(basename "$0") [OPTIONS] [MAVEN_GOALS]

Builds metasfresh using Maven with workspace-specific local repository.

IMPORTANT - FULL BUILD DURATION:
    Full builds (clean install) take 20-25 minutes to complete.
    Consider running in background and monitoring periodically:

    ./mvn_build.sh > /tmp/metasfresh_build.log 2>&1 &

    Then check progress with:
    tail -f /tmp/metasfresh_build.log

OPTIONS:
    -h, --help              Show this help message
    --clean                 Clean before building (adds 'clean' goal)
                            By default, builds are incremental (no clean)
    --deps-only             Build only core dependencies (parent-pom, de-metas-common)
                            Fast option for installing dependencies (~2-3 minutes)
    --skip-tests            Skip running tests (adds -DskipTests)
    --parallel              Enable parallel build (-T2C)

MAVEN_GOALS:
    Maven goals to execute (default: clean install)
    Examples: "test", "clean install", "verify", "package"

EXAMPLES:
    $(basename "$0")                          # Default: incremental install (no clean)
    $(basename "$0") --clean                  # Full clean install
    $(basename "$0") --deps-only              # Install only dependencies (parent-pom, de-metas-common)
    $(basename "$0") test                     # Run tests only
    $(basename "$0") clean verify             # Clean and verify
    $(basename "$0") --skip-tests             # Install without tests
    $(basename "$0") --parallel --clean       # Parallel clean build

ENVIRONMENT VARIABLES:
    JAVA8_HOME                      Java 8 installation for parent-pom, de-metas-common, backend
                                    (default: $DEFAULT_JAVA8_HOME)
    JAVA17_HOME                     Java 17 installation for services (camel, procurement-webui, federated-rabbitmq)
                                    (default: $DEFAULT_JAVA17_HOME)
    SKIP_MIGRATION_SCRIPTS_TEST     Skip migration scripts test during backend build
                                    (default: true, set to 'false' to run the test)

WORKSPACE-SPECIFIC LOCAL REPOSITORY:
    This script uses a workspace-specific Maven local repository at:
    <metasfresh-root>/.m2-local

    This keeps artifacts isolated per workspace, useful for working with
    multiple branches in parallel.

EOF
}

# ============================================================================
# Parse command line arguments
# ============================================================================

MAVEN_GOALS=""
SKIP_TESTS=""
DEPS_ONLY=false
DO_CLEAN=false

while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            show_help
            exit 0
            ;;
        --clean)
            DO_CLEAN=true
            shift
            ;;
        --deps-only)
            DEPS_ONLY=true
            shift
            ;;
        --skip-tests)
            SKIP_TESTS="-DskipTests"
            shift
            ;;
        --parallel)
            MULTITHREAD_PARAM="-T2C"
            shift
            ;;
        *)
            # Treat as Maven goals
            MAVEN_GOALS="$MAVEN_GOALS $1"
            shift
            ;;
    esac
done

# Use default goals if none provided
if [ -z "$MAVEN_GOALS" ]; then
    MAVEN_GOALS="$DEFAULT_GOALS"
fi

# Prepend 'clean' if --clean flag was used
if [ "$DO_CLEAN" = true ]; then
    MAVEN_GOALS="clean $MAVEN_GOALS"
fi

# If deps-only mode, force skip tests and skip test compilation
if [ "$DEPS_ONLY" = true ]; then
    SKIP_TESTS="-DskipTests -Dmaven.test.skip=true"
fi

# Add skip tests if requested
if [ -n "$SKIP_TESTS" ]; then
    ADDITIONAL_PARAMS="$ADDITIONAL_PARAMS $SKIP_TESTS"
fi

# ============================================================================
# Setup paths and environment
# ============================================================================

# Get script directory and compute workspace root
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WORKSPACE_ROOT="$(cd "$SCRIPT_DIR/../../.." && pwd)"

# Setup workspace-specific Maven local repository
LOCAL_REPO="$WORKSPACE_ROOT/.m2-local"
mkdir -p "$LOCAL_REPO"

# Maven local repo parameter
MAVEN_REPO_PARAM="-Dmaven.repo.local=$LOCAL_REPO"

# Setup Java homes (use environment variables if set, otherwise use defaults)
if [ -z "$JAVA8_HOME" ]; then
    JAVA8_HOME="$DEFAULT_JAVA8_HOME"
    echo "JAVA8_HOME not set, using default: $JAVA8_HOME"
else
    echo "Using JAVA8_HOME from environment: $JAVA8_HOME"
fi

if [ -z "$JAVA17_HOME" ]; then
    JAVA17_HOME="$DEFAULT_JAVA17_HOME"
    echo "JAVA17_HOME not set, using default: $JAVA17_HOME"
else
    echo "Using JAVA17_HOME from environment: $JAVA17_HOME"
fi

# Verify Java installations exist
if [ ! -d "$JAVA8_HOME" ]; then
    echo "ERROR: JAVA8_HOME directory does not exist: $JAVA8_HOME"
    exit 1
fi

if [ ! -d "$JAVA17_HOME" ]; then
    echo "ERROR: JAVA17_HOME directory does not exist: $JAVA17_HOME"
    exit 1
fi

# Maven settings file (relative to WORKSPACE_ROOT)
MAVEN_SETTINGS="$WORKSPACE_ROOT/misc/dev-support/maven/settings.xml"

# ============================================================================
# Build configuration summary
# ============================================================================

echo "============================================================================"
if [ "$DEPS_ONLY" = true ]; then
    echo "metasfresh Core Dependencies Installation"
else
    echo "metasfresh Maven Build"
fi
echo "============================================================================"
echo "Workspace:        $WORKSPACE_ROOT"
echo "Local Repository: $LOCAL_REPO"
echo "JAVA8_HOME:       $JAVA8_HOME"
if [ "$DEPS_ONLY" = false ]; then
    echo "JAVA17_HOME:      $JAVA17_HOME (for services)"
fi
echo "Maven Goals:      $MAVEN_GOALS"
if [ "$DEPS_ONLY" = true ]; then
    echo "Build Mode:       Dependencies only (parent-pom, de-metas-common)"
else
    echo "Build Mode:       Full build (parent-pom, de-metas-common, backend, services)"
fi
echo "Multithreading:   ${MULTITHREAD_PARAM:-disabled}"
echo "Additional Params: $ADDITIONAL_PARAMS"
if [ "$DEPS_ONLY" = false ]; then
    echo "Backend Params:   $BACKEND_PARAMS"
fi
echo "============================================================================"
echo ""

# ============================================================================
# Build modules in order
# ============================================================================

run_mvn_build "parent-pom" "$JAVA8_HOME" \
    --file "$WORKSPACE_ROOT/misc/parent-pom/pom.xml" \
    --settings "$MAVEN_SETTINGS" \
    $MAVEN_REPO_PARAM \
    $ADDITIONAL_PARAMS \
    $MAVEN_GOALS

run_mvn_build "de-metas-common" "$JAVA8_HOME" \
    --file "$WORKSPACE_ROOT/misc/de-metas-common/pom.xml" \
    --settings "$MAVEN_SETTINGS" \
    $MAVEN_REPO_PARAM \
    $MULTITHREAD_PARAM \
    $ADDITIONAL_PARAMS \
    $MAVEN_GOALS

# Only build backend and services if not in deps-only mode
if [ "$DEPS_ONLY" = false ]; then
    run_mvn_build "backend" "$JAVA8_HOME" \
        -e --file "$WORKSPACE_ROOT/backend/pom.xml" \
        --settings "$MAVEN_SETTINGS" \
        $MAVEN_REPO_PARAM \
        $MULTITHREAD_PARAM \
        $ADDITIONAL_PARAMS \
        $BACKEND_PARAMS \
        $MAVEN_GOALS

    run_mvn_build "camel" "$JAVA17_HOME" \
        --file "$WORKSPACE_ROOT/misc/services/camel/pom.xml" \
        --settings "$MAVEN_SETTINGS" \
        $MAVEN_REPO_PARAM \
        $MULTITHREAD_PARAM \
        $ADDITIONAL_PARAMS \
        $MAVEN_GOALS

    run_mvn_build "procurement-webui-backend" "$JAVA17_HOME" \
        --file "$WORKSPACE_ROOT/misc/services/procurement-webui/procurement-webui-backend/pom.xml" \
        --settings "$MAVEN_SETTINGS" \
        $MAVEN_REPO_PARAM \
        $MULTITHREAD_PARAM \
        $ADDITIONAL_PARAMS \
        $MAVEN_GOALS

    run_mvn_build "federated-rabbitmq" "$JAVA17_HOME" \
        --file "$WORKSPACE_ROOT/misc/services/federated-rabbitmq/pom.xml" \
        --settings "$MAVEN_SETTINGS" \
        $MAVEN_REPO_PARAM \
        $MULTITHREAD_PARAM \
        $ADDITIONAL_PARAMS \
        $MAVEN_GOALS
fi

echo ""
echo "============================================================================"
if [ "$DEPS_ONLY" = true ]; then
    echo "$(date): Dependencies installed successfully!"
    echo "============================================================================"
    echo ""
    echo "Core dependencies (parent-pom, de-metas-common) are now available in:"
    echo "  $LOCAL_REPO"
    echo ""
    echo "You can now:"
    echo "  - Compile individual modules"
    echo "  - Run unit tests in specific modules"
    echo "  - Build the full codebase with: ./mvn_build.sh"
else
    echo "$(date): Build completed successfully!"
fi
echo "============================================================================"
