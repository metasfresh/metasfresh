#!/bin/bash
#
# mf-docker-build.sh - Incremental build for Docker development workflow
#
# Usage: mf-docker-build.sh <component> [options]
#
# Components:
#   webapi      Build metasfresh-webui-api and restart container
#   app         Build metasfresh-dist (app server) and restart container
#   webui       Build frontend assets (just refresh browser)
#   mobile      Build mobile assets (just refresh browser)
#   all         Build all components
#
# Options:
#   --no-restart    Build only, don't restart containers
#   --skip-tests    Skip tests during build (default for speed)
#
# Prerequisites:
#   - E2E stack running in dev-mode: mf-docker.sh e2e-stack start --dev-mode
#   - Maven and Node.js/yarn available
#   - Parent POM installed: mvn_build.sh --deps-only
#
# How it works:
#   - Builds to standard Maven target directories
#   - Copies results to .dev-artifacts/ (which is mounted by compose-dev.yml)
#   - Restarts container to pick up changes (for backend)

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
METASFRESH_ROOT="$(cd "$SCRIPT_DIR/../../../.." && pwd)"
COMPOSE_DIR="$METASFRESH_ROOT/docker-builds/frontendtest"
PROJECT_NAME="mfstack"
ARTIFACTS_DIR="$METASFRESH_ROOT/.dev-artifacts"

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

# Check if running in dev mode
check_dev_mode() {
    if [ ! -f "$COMPOSE_DIR/compose-dev.yml" ]; then
        error "compose-dev.yml not found. Cannot use incremental builds."
        exit 1
    fi

    # Check if containers are running
    if ! docker compose -p "$PROJECT_NAME" -f "$COMPOSE_DIR/compose.yml" ps --format json 2>/dev/null | grep -q "running"; then
        warn "E2E stack is not running. Start it first with:"
        echo "  mf-docker.sh e2e-stack start --dev-mode"
        exit 1
    fi
}

# Build webapi (metasfresh-webui-api)
build_webapi() {
    info "Building metasfresh-webui-api..."

    local pom="$METASFRESH_ROOT/backend/metasfresh-webui-api/pom.xml"
    local output="$METASFRESH_ROOT/backend/metasfresh-webui-api/target/docker/metasfresh-webui-api.jar"
    local artifact_dest="$ARTIFACTS_DIR/webapi"

    if [ ! -f "$pom" ]; then
        error "POM not found: $pom"
        exit 1
    fi

    # Build with Maven
    local mvn_args="-f $pom package -DskipTests -Dmaven.gitcommitid.skip=true"

    # Use workspace-local Maven repo
    local settings="$METASFRESH_ROOT/misc/dev-support/maven/settings.xml"
    local local_repo="$METASFRESH_ROOT/.m2-local"
    if [ -f "$settings" ]; then
        mvn_args="$mvn_args -s $settings -Dmaven.repo.local=$local_repo"
    fi

    info "Running: mvn $mvn_args"
    mvn $mvn_args

    if [ -f "$output" ]; then
        success "Built: $output"
        echo "  Size: $(du -h "$output" | cut -f1)"

        # Copy to .dev-artifacts/ for compose-dev.yml volume mount
        mkdir -p "$artifact_dest"
        cp "$output" "$artifact_dest/metasfresh-webui-api.jar"
        success "Copied to: $artifact_dest/metasfresh-webui-api.jar"
    else
        error "Build failed - JAR not found: $output"
        exit 1
    fi
}

# Build app (metasfresh-dist/serverRoot)
build_app() {
    info "Building metasfresh-dist (app server)..."

    local pom="$METASFRESH_ROOT/backend/metasfresh-dist/serverRoot/pom.xml"
    local output="$METASFRESH_ROOT/backend/metasfresh-dist/dist/target/docker/app/metasfresh_server.jar"
    local reports_src="$METASFRESH_ROOT/backend/metasfresh-dist/dist/target/docker/app/reports"
    local artifact_dest="$ARTIFACTS_DIR/app"
    local reports_dest="$ARTIFACTS_DIR/reports"

    if [ ! -f "$pom" ]; then
        error "POM not found: $pom"
        exit 1
    fi

    # Build with Maven
    local mvn_args="-f $pom package -DskipTests -Dmaven.gitcommitid.skip=true"

    # Use workspace-local Maven repo
    local settings="$METASFRESH_ROOT/misc/dev-support/maven/settings.xml"
    local local_repo="$METASFRESH_ROOT/.m2-local"
    if [ -f "$settings" ]; then
        mvn_args="$mvn_args -s $settings -Dmaven.repo.local=$local_repo"
    fi

    info "Running: mvn $mvn_args"
    mvn $mvn_args

    if [ -f "$output" ]; then
        success "Built: $output"
        echo "  Size: $(du -h "$output" | cut -f1)"

        # Copy JAR to .dev-artifacts/ for compose-dev.yml volume mount
        mkdir -p "$artifact_dest"
        cp "$output" "$artifact_dest/metasfresh_server.jar"
        success "Copied to: $artifact_dest/metasfresh_server.jar"

        # Copy reports if they exist
        if [ -d "$reports_src" ]; then
            mkdir -p "$reports_dest"
            cp -r "$reports_src"/* "$reports_dest/" 2>/dev/null || true
            success "Copied reports to: $reports_dest/"
        fi
    else
        error "Build failed - JAR not found: $output"
        exit 1
    fi
}

# Build webui (frontend)
build_webui() {
    info "Building frontend (webui)..."

    local frontend_dir="$METASFRESH_ROOT/frontend"
    local output_dir="$frontend_dir/dist"
    local config_src="$COMPOSE_DIR/web-config.js"
    local artifact_dest="$ARTIFACTS_DIR/webui"

    if [ ! -d "$frontend_dir" ]; then
        error "Frontend directory not found: $frontend_dir"
        exit 1
    fi

    cd "$frontend_dir"

    # Check if node_modules exists
    if [ ! -d "node_modules" ]; then
        info "Installing dependencies..."
        yarn install
    fi

    # Build
    info "Running: yarn build-prod"
    yarn build-prod

    if [ -d "$output_dir" ] && [ "$(ls -A $output_dir)" ]; then
        success "Built: $output_dir"
        echo "  Files: $(ls -1 "$output_dir" | wc -l)"

        # Copy to .dev-artifacts/ for compose-dev.yml volume mount
        mkdir -p "$artifact_dest"
        cp -r "$output_dir"/* "$artifact_dest/"
        success "Copied to: $artifact_dest/"

        # Copy config.js for dev-mode
        if [ -f "$config_src" ]; then
            cp "$config_src" "$artifact_dest/config.js"
            success "Copied config.js to $artifact_dest/"
        fi
    else
        error "Build failed - dist directory empty or not found"
        exit 1
    fi

    cd "$METASFRESH_ROOT"
}

# Build mobile
build_mobile() {
    info "Building mobile frontend..."

    local mobile_dir="$METASFRESH_ROOT/misc/services/mobile-webui/mobile-webui-frontend"
    local output_dir="$mobile_dir/build"
    local artifact_dest="$ARTIFACTS_DIR/mobile"

    if [ ! -d "$mobile_dir" ]; then
        error "Mobile directory not found: $mobile_dir"
        exit 1
    fi

    cd "$mobile_dir"

    # Check if node_modules exists
    if [ ! -d "node_modules" ]; then
        info "Installing dependencies..."
        yarn install
    fi

    # Build (Create React App)
    info "Running: yarn build"
    yarn build

    if [ -d "$output_dir" ] && [ "$(ls -A $output_dir)" ]; then
        success "Built: $output_dir"
        echo "  Files: $(ls -1 "$output_dir" | wc -l)"

        # Copy to .dev-artifacts/ for compose-dev.yml volume mount
        mkdir -p "$artifact_dest"
        cp -r "$output_dir"/* "$artifact_dest/"
        success "Copied to: $artifact_dest/"
    else
        error "Build failed - build directory empty or not found"
        exit 1
    fi

    cd "$METASFRESH_ROOT"
}

# Restart a container
restart_container() {
    local service="$1"
    local container="${PROJECT_NAME}-${service}-1"

    info "Restarting $container..."

    if docker restart "$container" > /dev/null 2>&1; then
        success "Restarted $container"

        # Wait for health check
        info "Waiting for health check..."
        local timeout=60
        local elapsed=0

        while [ $elapsed -lt $timeout ]; do
            local health=$(docker inspect --format='{{.State.Health.Status}}' "$container" 2>/dev/null || echo "none")

            if [ "$health" == "healthy" ]; then
                success "$container is healthy"
                return 0
            elif [ "$health" == "unhealthy" ]; then
                error "$container is unhealthy - check logs with: docker logs $container"
                return 1
            fi

            sleep 2
            elapsed=$((elapsed + 2))
        done

        warn "Health check timeout - container may still be starting"
    else
        error "Failed to restart $container"
        return 1
    fi
}

# Show help
show_help() {
    echo "Usage: mf-docker-build.sh <component> [options]"
    echo ""
    echo "Components:"
    echo "  webapi      Build metasfresh-webui-api JAR and restart container"
    echo "  app         Build metasfresh-dist (app server) and restart container"
    echo "  webui       Build frontend assets (refresh browser to see changes)"
    echo "  mobile      Build mobile assets (refresh browser to see changes)"
    echo "  all         Build all components"
    echo ""
    echo "Options:"
    echo "  --no-restart    Build only, don't restart containers"
    echo ""
    echo "Prerequisites:"
    echo "  - E2E stack running in dev-mode"
    echo "  - Maven and Node.js/yarn available"
    echo "  - Parent POM installed: ./mvn_build.sh --deps-only"
    echo ""
    echo "Examples:"
    echo "  mf-docker-build.sh webapi     # Build and restart webapi"
    echo "  mf-docker-build.sh webui      # Build frontend (refresh browser)"
    echo "  mf-docker-build.sh all        # Build everything"
    echo ""
    echo "How it works:"
    echo "  1. Builds to standard Maven/yarn output directories"
    echo "  2. Copies artifacts to .dev-artifacts/ (mounted by compose-dev.yml)"
    echo "  3. Restarts container to pick up changes (for backend)"
    echo ""
    echo "Artifact locations (.dev-artifacts/):"
    echo "  webapi:  .dev-artifacts/webapi/metasfresh-webui-api.jar"
    echo "  app:     .dev-artifacts/app/metasfresh_server.jar"
    echo "  reports: .dev-artifacts/reports/"
    echo "  webui:   .dev-artifacts/webui/"
    echo "  mobile:  .dev-artifacts/mobile/"
}

# Main
main() {
    local component="$1"
    local no_restart=false

    # Parse options
    shift 2>/dev/null || true
    while [[ $# -gt 0 ]]; do
        case $1 in
            --no-restart)
                no_restart=true
                shift
                ;;
            *)
                shift
                ;;
        esac
    done

    case "$component" in
        webapi)
            check_dev_mode
            build_webapi
            if [ "$no_restart" != "true" ]; then
                restart_container "webapi"
            else
                info "Skipping restart (--no-restart)"
                echo "  Restart manually: docker restart ${PROJECT_NAME}-webapi-1"
            fi
            ;;
        app)
            check_dev_mode
            build_app
            if [ "$no_restart" != "true" ]; then
                restart_container "app"
            else
                info "Skipping restart (--no-restart)"
                echo "  Restart manually: docker restart ${PROJECT_NAME}-app-1"
            fi
            ;;
        webui)
            build_webui
            success "Frontend built - refresh your browser to see changes"
            echo "  URL: http://localhost:3000"
            ;;
        mobile)
            build_mobile
            success "Mobile built - refresh your browser to see changes"
            echo "  URL: http://localhost:8880"
            warn "Note: Mobile is not in frontendtest compose - may need additional setup"
            ;;
        all)
            check_dev_mode
            echo ""
            echo "Building all components..."
            echo ""

            build_webui
            echo ""

            build_webapi
            if [ "$no_restart" != "true" ]; then
                restart_container "webapi"
            fi
            echo ""

            build_app
            if [ "$no_restart" != "true" ]; then
                restart_container "app"
            fi
            echo ""

            success "All components built!"
            ;;
        help|--help|-h)
            show_help
            ;;
        "")
            error "No component specified"
            echo ""
            show_help
            exit 1
            ;;
        *)
            error "Unknown component: $component"
            echo ""
            show_help
            exit 1
            ;;
    esac
}

main "$@"
