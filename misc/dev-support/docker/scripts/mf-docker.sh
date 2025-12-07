#!/bin/bash
#
# mf-docker.sh - Master script for Docker-based metasfresh development
#
# This script unifies Docker workflows for:
# - Running E2E tests against CI-built images
# - Incremental builds on top of CI base
# - IntelliJ dependency synchronization
#
# Usage: mf-docker.sh <command> [options]
#
# Commands:
#   pull <branch>       Pull latest CI images for a branch
#   e2e-stack <action>  Manage E2E test stack (start|stop|status|logs)
#   incremental-build   Build only changed modules on CI base
#   sync-deps           Sync Maven dependencies from CI image
#   info                Show current configuration and image versions
#   help                Show this help message
#

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
VERSION_FILE="$SCRIPT_DIR/.mf-docker-version"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Helper functions
info() { echo -e "${BLUE}→${NC} $1"; }
success() { echo -e "${GREEN}✓${NC} $1"; }
warn() { echo -e "${YELLOW}!${NC} $1"; }
error() { echo -e "${RED}✗${NC} $1" >&2; }

show_banner() {
    echo ""
    echo "╔═══════════════════════════════════════════════════════════════╗"
    echo "║       mf-docker - Docker-based metasfresh Development        ║"
    echo "╚═══════════════════════════════════════════════════════════════╝"
    echo ""
}

show_help() {
    show_banner
    echo "Usage: mf-docker.sh <command> [options]"
    echo ""
    echo "Commands:"
    echo "  pull <branch>         Pull latest CI images for a branch"
    echo "                        (defaults to current git branch)"
    echo ""
    echo "  e2e-stack <action>    Manage E2E test stack"
    echo "    start               Start the metasfresh stack"
    echo "    start --no-webui    Start without webui (for local frontend dev)"
    echo "    stop                Stop all containers"
    echo "    status              Show container status"
    echo "    logs [service]      Tail logs"
    echo ""
    echo "  incremental-build [module]"
    echo "                        Build changed modules on CI base"
    echo "                        (coming soon)"
    echo ""
    echo "  sync-deps             Sync Maven dependencies from CI"
    echo "                        (coming soon)"
    echo ""
    echo "  info                  Show current configuration"
    echo ""
    echo "Examples:"
    echo "  mf-docker.sh pull new_dawn_uat"
    echo "  mf-docker.sh e2e-stack start"
    echo "  mf-docker.sh e2e-stack logs webapi"
    echo "  mf-docker.sh e2e-stack stop"
    echo ""
    echo "Typical E2E Workflow:"
    echo "  1. mf-docker.sh pull new_dawn_uat   # Get latest CI images"
    echo "  2. mf-docker.sh e2e-stack start     # Start the stack"
    echo "  3. cd e2e/frontend-webui && npm test  # Run tests"
    echo "  4. mf-docker.sh e2e-stack stop      # Clean up"
    echo ""
}

cmd_info() {
    show_banner

    info "Script location: $SCRIPT_DIR"
    echo ""

    if [ -f "$VERSION_FILE" ]; then
        local tag=$(cat "$VERSION_FILE")
        info "Current image tag: $tag"
    else
        warn "No image tag set. Run 'mf-docker.sh pull <branch>' first."
    fi

    echo ""

    # Check Docker
    if command -v docker &> /dev/null && docker info &> /dev/null; then
        success "Docker: Connected"

        # Show relevant images
        info "Available metasfresh images:"
        docker images "metasfresh/*" --format "  {{.Repository}}:{{.Tag}}\t{{.Size}}" 2>/dev/null | head -10

        if [ $(docker images "metasfresh/*" --format "{{.Repository}}" 2>/dev/null | wc -l) -gt 10 ]; then
            echo "  ... and more"
        fi
    else
        error "Docker: Not available"
        echo "  Make sure Docker Desktop is running with WSL2 integration."
    fi

    echo ""
}

cmd_pull() {
    "$SCRIPT_DIR/mf-docker-pull.sh" "$@"
}

cmd_e2e_stack() {
    "$SCRIPT_DIR/mf-docker-e2e.sh" "$@"
}

cmd_incremental_build() {
    if [ -f "$SCRIPT_DIR/mf-docker-build.sh" ]; then
        "$SCRIPT_DIR/mf-docker-build.sh" "$@"
    else
        warn "Incremental build is not yet implemented."
        echo "  This feature will build only changed modules on top of CI base."
        echo "  For now, use: mvn -f backend/<module>/pom.xml package -DskipTests"
    fi
}

cmd_sync_deps() {
    if [ -f "$SCRIPT_DIR/mf-docker-sync-deps.sh" ]; then
        "$SCRIPT_DIR/mf-docker-sync-deps.sh" "$@"
    else
        warn "Dependency sync is not yet implemented."
        echo "  This feature will extract Maven dependencies from CI images."
        echo "  For now, use: mvn_build.sh --deps-only"
    fi
}

main() {
    local command="$1"
    shift 2>/dev/null || true

    case "$command" in
        pull)
            cmd_pull "$@"
            ;;
        e2e-stack|e2e|stack)
            cmd_e2e_stack "$@"
            ;;
        incremental-build|build)
            cmd_incremental_build "$@"
            ;;
        sync-deps|deps)
            cmd_sync_deps "$@"
            ;;
        info)
            cmd_info
            ;;
        help|--help|-h)
            show_help
            ;;
        "")
            show_help
            ;;
        *)
            error "Unknown command: $command"
            echo ""
            show_help
            exit 1
            ;;
    esac
}

main "$@"
