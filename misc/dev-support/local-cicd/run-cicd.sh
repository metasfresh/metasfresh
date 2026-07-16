#!/bin/bash
#
# run-cicd.sh - Run CI/CD pipeline locally using act
#
# Usage:
#   ./run-cicd.sh list          - List all available jobs
#   ./run-cicd.sh java          - Build Java/Maven artifacts
#   ./run-cicd.sh frontend      - Build frontend images
#   ./run-cicd.sh junit         - Run JUnit tests
#   ./run-cicd.sh cucumber      - Run Cucumber integration tests
#   ./run-cicd.sh full          - Run entire pipeline
#   ./run-cicd.sh <job-name>    - Run any specific job by name
#

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../../.." && pwd)"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

log_info() { echo -e "${GREEN}[INFO]${NC} $1"; }
log_warn() { echo -e "${YELLOW}[WARN]${NC} $1"; }
log_error() { echo -e "${RED}[ERROR]${NC} $1"; }

# Check prerequisites
check_prerequisites() {
    if ! command -v act &> /dev/null; then
        log_error "act is not installed. Install it with:"
        echo "  curl -sfL https://raw.githubusercontent.com/nektos/act/master/install.sh | sudo bash -s -- -b /usr/local/bin"
        exit 1
    fi

    if ! command -v docker &> /dev/null; then
        log_error "docker is not installed or not in PATH"
        exit 1
    fi

    if ! docker info &> /dev/null; then
        log_error "Docker daemon is not running"
        exit 1
    fi

    if [ ! -f "$SCRIPT_DIR/.secrets" ]; then
        log_error "Secrets file not found: $SCRIPT_DIR/.secrets"
        echo "  Copy .secrets.template to .secrets and fill in your credentials:"
        echo "  cp $SCRIPT_DIR/.secrets.template $SCRIPT_DIR/.secrets"
        exit 1
    fi
}

# Common act arguments
# Note: Using 'act-latest' image instead of 'full-latest' for proper Docker socket permissions
# See: https://github.com/nektos/act/issues/2616
ACT_ARGS=(
    --secret-file "$SCRIPT_DIR/.secrets"
    -C "$REPO_ROOT"
    -P ubuntu-latest=ghcr.io/catthehacker/ubuntu:act-latest
    --artifact-server-path=/tmp/act-artifacts
    --env GITHUB_RUN_NUMBER=1
    --var RETRY_ATTEMPTS=3
    --var RETRY_DELAY=5
    --var RETRY_TIMEOUT=10
)
# Note: ACT environment variable is automatically set by 'act' when running locally
# The workflow uses 'env.ACT != true' to skip Docker pushes when running locally

show_help() {
    cat << EOF
Usage: $0 <command> [options]

Commands:
  list              List all available jobs in the workflow
  full              Run the entire CI/CD pipeline
  java              Build Java/Maven artifacts (metas-mvn-common, backend, camel)
  frontend          Build frontend and mobile images
  backend           Build runtime Docker images (api, app, db, etc.)
  junit             Run JUnit unit tests
  cucumber          Run Cucumber integration tests (all profiles)
  cucumber-build    Build the Cucumber test container
  health_check      Run health checks on all services
  mobile_test       Run Playwright mobile E2E tests
  frontend_test     Run Playwright frontend E2E tests
  <job-name>        Run any specific job by name (use 'list' to see all)

Options:
  -v, --verbose     Enable verbose output
  -n, --dry-run     Show what would be run without executing
  -h, --help        Show this help message

Examples:
  $0 list                    # See all available jobs
  $0 junit                   # Run JUnit tests
  $0 cucumber -v             # Run Cucumber with verbose output
  $0 java --dry-run          # See what Java build would do

Environment:
  DOCKER_BUILDKIT=1          Enable BuildKit (recommended, set automatically)
EOF
}

# Ensure BuildKit is enabled
export DOCKER_BUILDKIT=1

# Parse command and options
COMMAND="${1:-help}"
shift || true

VERBOSE=""
DRY_RUN=""

while [[ $# -gt 0 ]]; do
    case $1 in
        -v|--verbose)
            VERBOSE="-v"
            shift
            ;;
        -n|--dry-run)
            DRY_RUN="--dryrun"
            shift
            ;;
        -h|--help)
            show_help
            exit 0
            ;;
        *)
            log_error "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
done

case "$COMMAND" in
    help|-h|--help)
        show_help
        exit 0
        ;;
    list)
        log_info "Available jobs in cicd.yaml:"
        act -l -C "$REPO_ROOT"
        exit 0
        ;;
    full)
        check_prerequisites
        log_info "Running full CI/CD pipeline..."
        log_warn "This may take 1-2 hours depending on caching"
        act "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    java)
        check_prerequisites
        log_info "Building Java/Maven artifacts..."
        act -j java "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    frontend)
        check_prerequisites
        log_info "Building frontend images..."
        act -j frontend "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    backend)
        check_prerequisites
        log_info "Building backend runtime images..."
        act -j backend "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    junit)
        check_prerequisites
        log_info "Running JUnit tests..."
        act -j junit "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    cucumber)
        check_prerequisites
        log_info "Running Cucumber integration tests..."
        log_warn "This runs all 10 profiles sequentially (may take several hours)"
        act -j cucumber-run "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    cucumber-build)
        check_prerequisites
        log_info "Building Cucumber test container..."
        act -j cucumber-build "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    health_check)
        check_prerequisites
        log_info "Running health checks..."
        act -j health_check "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    mobile_test)
        check_prerequisites
        log_info "Running Playwright mobile tests..."
        act -j mobile_test "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    frontend_test)
        check_prerequisites
        log_info "Running Playwright frontend tests..."
        act -j frontend_webui_test "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
    *)
        # Try to run it as a job name
        check_prerequisites
        log_info "Running job: $COMMAND"
        act -j "$COMMAND" "${ACT_ARGS[@]}" $VERBOSE $DRY_RUN
        ;;
esac

log_info "Done!"
log_info "Artifacts available at: /tmp/act-artifacts/"
