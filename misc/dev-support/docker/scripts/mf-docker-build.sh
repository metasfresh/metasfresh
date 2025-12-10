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
#
# Prerequisites:
#   - E2E stack running in dev-mode: mf-docker.sh e2e-stack start --dev-mode
#   - Maven available locally
#   - Maven repository extracted to .m2-local/ (done automatically by --dev-mode)
#
# How it works:
#   - Runs Maven with -pl (project list) and -am (also-make) to build
#     the target module AND all its dependencies that have changed
#   - Uses workspace-local Maven repo (.m2-local/) for fast builds
#   - Copies results to .dev-artifacts/ (mounted by compose-dev.yml)
#   - Restarts container to pick up changes (for backend)

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
METASFRESH_ROOT="$(cd "$SCRIPT_DIR/../../../.." && pwd)"
COMPOSE_DIR="$METASFRESH_ROOT/docker-builds/frontendtest"
PROJECT_NAME="mfstack"
ARTIFACTS_DIR="$METASFRESH_ROOT/.dev-artifacts"

# Maven configuration
SETTINGS_FILE="$METASFRESH_ROOT/misc/dev-support/maven/settings.xml"
LOCAL_REPO="$METASFRESH_ROOT/.m2-local"
BACKEND_DIR="$METASFRESH_ROOT/backend"

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

# Check if E2E stack is running in dev mode
check_dev_mode() {
    if [ ! -f "$COMPOSE_DIR/compose-dev.yml" ]; then
        error "compose-dev.yml not found. Cannot use incremental builds."
        exit 1
    fi

    # Check if containers are running
    if ! docker compose -p "$PROJECT_NAME" -f "$COMPOSE_DIR/compose.yml" ps --format json 2>/dev/null | grep -q "running"; then
        warn "E2E stack is not running. Start it first with:"
        echo "  mf-docker.sh e2e-stack start --dev-mode"
        # Don't exit - allow building without running stack
    fi
}

# Check Maven repository is available
check_maven_repo() {
    if [ ! -d "$LOCAL_REPO/de/metas" ]; then
        error "Maven repository not found in .m2-local/"
        echo ""
        echo "  The workspace-local Maven repository is required for incremental builds."
        echo "  It should be extracted automatically when starting in dev-mode:"
        echo ""
        echo "    mf-docker.sh e2e-stack start --dev-mode"
        echo ""
        echo "  Or pull images and extract manually with:"
        echo ""
        echo "    mf-docker.sh pull new_dawn_uat"
        echo "    rm -rf .dev-artifacts/  # Force re-extraction"
        echo "    mf-docker.sh e2e-stack start --dev-mode"
        echo ""
        exit 1
    fi
}

# Build Maven arguments
get_maven_args() {
    local args=""

    # Use workspace-local settings and repo
    if [ -f "$SETTINGS_FILE" ]; then
        args="$args -s $SETTINGS_FILE"
    fi
    args="$args -Dmaven.repo.local=$LOCAL_REPO"

    # Skip tests and git plugin
    args="$args -DskipTests -Dmaven.test.skip=true -Dmaven.gitcommitid.skip=true"

    # Parallel builds
    args="$args -T 1C"

    echo "$args"
}

# Run Maven build from reactor root with -pl and -am
run_maven_build() {
    local module="$1"
    local output_path="$2"
    local artifact_dest="$3"

    info "Building $module (with dependencies)..."
    echo "  Using: mvn -pl $module -am package"
    echo ""

    local mvn_args=$(get_maven_args)

    # Change to backend directory (reactor root)
    cd "$BACKEND_DIR"

    # Run Maven with -pl (project list) and -am (also-make dependencies)
    if ! mvn -pl "$module" -am package $mvn_args; then
        error "Maven build failed"
        cd "$METASFRESH_ROOT"
        return 1
    fi

    cd "$METASFRESH_ROOT"
    echo ""

    # Check if JAR was created
    local full_output_path="$BACKEND_DIR/$output_path"
    if [ ! -f "$full_output_path" ]; then
        error "Build output not found: $full_output_path"
        return 1
    fi

    # Get JAR size
    local jar_size=$(du -h "$full_output_path" | cut -f1)

    # Copy JAR to artifacts directory
    info "Copying artifact to $artifact_dest..."
    mkdir -p "$(dirname "$ARTIFACTS_DIR/$artifact_dest")"
    cp "$full_output_path" "$ARTIFACTS_DIR/$artifact_dest"

    success "Built: $artifact_dest ($jar_size)"

    return 0
}

# Build webapi (metasfresh-webui-api)
build_webapi() {
    info "Building metasfresh-webui-api..."
    echo ""

    check_maven_repo

    mkdir -p "$ARTIFACTS_DIR/webapi"

    run_maven_build \
        "metasfresh-webui-api" \
        "metasfresh-webui-api/target/docker/metasfresh-webui-api.jar" \
        "webapi/metasfresh-webui-api.jar"
}

# Build app (metasfresh-dist/serverRoot)
build_app() {
    info "Building metasfresh-dist (app server)..."
    echo ""

    check_maven_repo

    mkdir -p "$ARTIFACTS_DIR/app"

    run_maven_build \
        "metasfresh-dist/serverRoot" \
        "metasfresh-dist/dist/target/docker/app/metasfresh_server.jar" \
        "app/metasfresh_server.jar"

    # Also copy reports if they exist
    local reports_src="$BACKEND_DIR/metasfresh-dist/dist/target/docker/app/reports"
    if [ -d "$reports_src" ]; then
        mkdir -p "$ARTIFACTS_DIR/reports"
        cp -r "$reports_src"/* "$ARTIFACTS_DIR/reports/" 2>/dev/null || true
        success "Copied reports to $ARTIFACTS_DIR/reports/"
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

        # Copy built files into running container
        # (compose-dev.yml doesn't mount .dev-artifacts/webui due to config.js conflict)
        # Note: config.js is mounted as a volume, so we exclude it to avoid "device or resource busy" error
        local container="${PROJECT_NAME}-webui-1"
        if docker ps --format '{{.Names}}' | grep -q "^${container}$"; then
            info "Copying files to container $container..."
            # Use tar to exclude config.js (which is mounted as a volume and can't be overwritten)
            if tar -c -C "$artifact_dest" --exclude='config.js' . | docker exec -i "$container" tar -x -C /usr/share/nginx/html/; then
                success "Files transferred to running container"
            else
                error "Failed to copy files to container"
                echo "  The files are in $artifact_dest/ but weren't transferred."
                echo "  Try manually: tar -c -C $artifact_dest --exclude='config.js' . | docker exec -i $container tar -x -C /usr/share/nginx/html/"
                return 1
            fi
        else
            warn "Container $container is not running"
            echo "  Files built to: $artifact_dest/"
            echo "  Start the stack with: mf-docker.sh e2e-stack start --dev-mode"
            echo "  Or copy manually when started: tar -c -C $artifact_dest --exclude='config.js' . | docker exec -i $container tar -x -C /usr/share/nginx/html/"
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
        local timeout=90
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
    echo "  - Maven available locally"
    echo "  - Maven repository in .m2-local/ (extracted by dev-mode)"
    echo ""
    echo "How it works:"
    echo "  1. Runs Maven from backend/ reactor root"
    echo "  2. Uses -pl -am to build module AND its changed dependencies"
    echo "  3. Uses .m2-local/ workspace-local Maven repository"
    echo "  4. Copies JAR to .dev-artifacts/ (mounted by compose-dev.yml)"
    echo "  5. Restarts container to pick up changes"
    echo ""
    echo "Examples:"
    echo "  mf-docker-build.sh webapi           # Build and restart webapi"
    echo "  mf-docker-build.sh webapi --no-restart"
    echo "  mf-docker-build.sh webui            # Build frontend (refresh browser)"
    echo "  mf-docker-build.sh all              # Build everything"
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
            check_dev_mode
            build_webui
            echo ""
            success "Frontend updated - refresh your browser to see changes"
            echo "  URL: http://localhost:3000"
            echo ""
            info "Note: For faster iteration, use: cd frontend && yarn start"
            ;;
        mobile)
            check_dev_mode
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
