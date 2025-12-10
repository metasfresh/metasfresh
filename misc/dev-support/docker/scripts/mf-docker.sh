#!/bin/bash
#
# mf-docker.sh - Master script for Docker-based metasfresh development
#
# This script unifies Docker workflows for:
# - Running E2E tests against CI-built images
# - Running E2E tests against custom repo images (e.g., ms205) from GHCR
# - Incremental builds on top of CI base
# - IntelliJ dependency synchronization
#
# Usage: mf-docker.sh <command> [options]
#
# Commands:
#   pull [options] <branch>   Pull latest CI images for a branch
#   e2e-stack <action>        Manage E2E test stack (start|stop|status|logs)
#   build <component>         Incremental builds for dev-mode (webapi|app|webui|mobile|all)
#   sync-deps                 Sync Maven dependencies from CI image
#   info                      Show current configuration and image versions
#   help                      Show this help message
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
    echo "  pull [options] <branch>"
    echo "                        Pull latest CI images for a branch"
    echo "                        (defaults to current git branch)"
    echo ""
    echo "    Options for pull:"
    echo "      --repo <name>       Pull from custom repo (e.g., ms205) from GHCR"
    echo "      --repo-root <path>  Path to custom repo checkout"
    echo "      --skip-builder      Skip the large maven builder image"
    echo ""
    echo "  e2e-stack <action>    Manage E2E test stack"
    echo "    start               Start the metasfresh stack"
    echo "    start --no-webui    Start without webui (for local frontend dev)"
    echo "    start --dev-mode    Start with dev-mode for hot reload"
    echo "    stop                Stop all containers"
    echo "    status              Show container status"
    echo "    logs [service]      Tail logs"
    echo ""
    echo "    Options for e2e-stack:"
    echo "      --repo <name>       Use custom repo (overrides .mf-docker-version)"
    echo "      --repo-root <path>  Path to custom repo checkout"
    echo ""
    echo "  build <component>     Incremental build for dev-mode"
    echo "    webapi              Build metasfresh-webui-api and restart"
    echo "    app                 Build metasfresh-dist and restart"
    echo "    webui               Build frontend (refresh browser)"
    echo "    mobile              Build mobile UI (refresh browser)"
    echo "    all                 Build all components"
    echo ""
    echo "  sync-deps             Sync Maven dependencies from CI"
    echo "                        (coming soon)"
    echo ""
    echo "  info                  Show current configuration"
    echo ""
    echo "Examples:"
    echo ""
    echo "  # Standard metasfresh workflow (Docker Hub)"
    echo "  mf-docker.sh pull new_dawn_uat"
    echo "  mf-docker.sh e2e-stack start"
    echo "  cd e2e/frontend-webui && npm test"
    echo "  mf-docker.sh e2e-stack stop"
    echo ""
    echo "  # Dev-mode with incremental builds"
    echo "  mf-docker.sh pull new_dawn_uat"
    echo "  mf-docker.sh e2e-stack start --dev-mode"
    echo "  mf-docker.sh build webapi        # Build and restart webapi"
    echo "  mf-docker.sh build webui          # Build frontend (refresh browser)"
    echo ""
    echo "  # Custom repo workflow (e.g., ms205 from GHCR)"
    echo "  mf-docker.sh pull --repo ms205 memorable_shiny_uat"
    echo "  mf-docker.sh e2e-stack start"
    echo "  # (uses ms205's compose.yml automatically)"
    echo ""
}

cmd_info() {
    show_banner

    info "Script location: $SCRIPT_DIR"
    echo ""

    if [ -f "$VERSION_FILE" ]; then
        # Check if new format
        if grep -q "^TAG=" "$VERSION_FILE" 2>/dev/null; then
            source "$VERSION_FILE"
            info "Current image tag: $TAG"
            if [ -n "$REPO" ]; then
                info "Custom repo: $REPO"
                info "Registry: $REGISTRY"
                info "Repo root: $REPO_ROOT"
            fi
        else
            local tag=$(cat "$VERSION_FILE")
            info "Current image tag: $tag"
        fi
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

        # Also show GHCR images if any
        local ghcr_count=$(docker images "ghcr.io/metasfresh/*" --format "{{.Repository}}" 2>/dev/null | wc -l)
        if [ "$ghcr_count" -gt 0 ]; then
            echo ""
            info "Available GHCR images:"
            docker images "ghcr.io/metasfresh/*" --format "  {{.Repository}}:{{.Tag}}\t{{.Size}}" 2>/dev/null | head -10
            if [ "$ghcr_count" -gt 10 ]; then
                echo "  ... and more"
            fi
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
    "$SCRIPT_DIR/mf-docker-build.sh" "$@"
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
