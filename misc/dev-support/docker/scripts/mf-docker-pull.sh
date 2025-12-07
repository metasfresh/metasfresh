#!/bin/bash
#
# mf-docker-pull.sh - Pull latest CI-built Docker images for a branch
#
# Usage: mf-docker-pull.sh [branch-name]
#        If branch-name is not provided, uses current git branch
#
# This script:
# 1. Queries GitHub Actions for the latest successful CI build
# 2. Pulls all required Docker images with that tag
# 3. Saves the tag for use by other mf-docker scripts
#

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
METASFRESH_ROOT="$(cd "$SCRIPT_DIR/../../../.." && pwd)"
VERSION_FILE="$SCRIPT_DIR/.mf-docker-version"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Helper functions
info() { echo -e "${BLUE}→${NC} $1"; }
success() { echo -e "${GREEN}✓${NC} $1"; }
warn() { echo -e "${YELLOW}!${NC} $1"; }
error() { echo -e "${RED}✗${NC} $1" >&2; }

# Check prerequisites
check_prerequisites() {
    # Check Docker
    if ! command -v docker &> /dev/null; then
        error "Docker is not available."
        echo "  Make sure Docker Desktop is running with WSL2 integration enabled."
        echo "  See: https://docs.docker.com/go/wsl2/"
        exit 1
    fi

    # Test Docker connection
    if ! docker info &> /dev/null; then
        error "Cannot connect to Docker daemon."
        echo "  Is Docker Desktop running?"
        exit 1
    fi

    # Check GitHub CLI
    if ! command -v gh &> /dev/null; then
        error "GitHub CLI (gh) is not installed."
        echo "  Install with: sudo apt install gh"
        exit 1
    fi

    # Check gh authentication
    if ! gh auth status &> /dev/null; then
        error "GitHub CLI is not authenticated."
        echo "  Run: gh auth login"
        exit 1
    fi
}

# Get branch name (from argument or current git branch)
get_branch_name() {
    local branch="$1"

    if [ -z "$branch" ]; then
        # Try to get current git branch
        if git rev-parse --git-dir &> /dev/null; then
            branch=$(git branch --show-current)
        fi
    fi

    if [ -z "$branch" ]; then
        error "Could not determine branch name."
        echo "  Please specify a branch name: mf-docker-pull.sh <branch-name>"
        exit 1
    fi

    echo "$branch"
}

# Get metasfresh version from version.info
get_mf_version() {
    local version_info="$METASFRESH_ROOT/docker-builds/version.info"

    if [ ! -f "$version_info" ]; then
        error "version.info not found at $version_info"
        exit 1
    fi

    cat "$version_info" | tr -d '[:space:]'
}

# Get latest successful CI build number for a branch
get_latest_build_number() {
    local branch="$1"

    info "Querying GitHub for latest successful build on branch: $branch"

    # Query GitHub Actions API for the latest successful run of cicd.yaml
    local run_info=$(gh run list \
        --repo metasfresh/metasfresh \
        --branch "$branch" \
        --workflow "cicd.yaml" \
        --status success \
        --limit 1 \
        --json number,createdAt,displayTitle 2>/dev/null)

    if [ -z "$run_info" ] || [ "$run_info" == "[]" ]; then
        error "No successful CI builds found for branch: $branch"
        echo "  Check if the branch exists and has a successful cicd workflow run."
        echo "  You can view runs at: https://github.com/metasfresh/metasfresh/actions"
        exit 1
    fi

    # Extract the run number (this is the GitHub Actions run number used in image tags)
    local run_number=$(echo "$run_info" | jq -r '.[0].number')
    local created_at=$(echo "$run_info" | jq -r '.[0].createdAt')

    if [ -z "$run_number" ] || [ "$run_number" == "null" ]; then
        error "Could not parse build number from GitHub response"
        exit 1
    fi

    info "Found: build #$run_number (created: $created_at)"

    echo "$run_number"
}

# Sanitize branch name (same logic as CI)
sanitize_branch_name() {
    local branch="$1"
    # Replace non-alphanumeric chars (except dots) with dashes, remove leading/trailing dashes
    echo "$branch" | sed -r 's/([^a-zA-Z0-9.]+)/-/g' | sed -r 's/(^-|-$)//g'
}

# Pull a single Docker image
pull_image() {
    local image="$1"

    info "Pulling $image..."
    if docker pull "$image" --quiet > /dev/null 2>&1; then
        success "$image"
        return 0
    else
        error "Failed to pull $image"
        return 1
    fi
}

# Main function
main() {
    local branch_arg="$1"
    local skip_builder="${2:-false}"  # Optional: skip large builder image

    echo ""
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║          mf-docker-pull - Pull CI Docker Images           ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo ""

    # Prerequisites
    check_prerequisites

    # Get branch name
    local branch=$(get_branch_name "$branch_arg")
    local sanitized_branch=$(sanitize_branch_name "$branch")

    # Get version
    local mf_version=$(get_mf_version)
    info "metasfresh version: $mf_version"

    # Get latest build number
    local build_number=$(get_latest_build_number "$branch")

    # Construct the tag
    local tag="${mf_version}-${sanitized_branch}.${build_number}"

    echo ""
    info "Image tag: $tag"
    echo ""

    # Define images to pull
    local images=(
        "metasfresh/metas-api:$tag"
        "metasfresh/metas-app:$tag"
        "metasfresh/metas-frontend:$tag"
        "metasfresh/metas-mobile:$tag"
        "metasfresh/metas-db:$tag-preloaded"
        "metasfresh/metas-externalsystems:$tag"
        "metasfresh/metas-edi:$tag"
    )

    # Optionally include the large builder image (for incremental builds)
    if [ "$skip_builder" != "true" ] && [ "$skip_builder" != "--skip-builder" ]; then
        images+=("metasfresh/metas-mvn-backend:$tag")
    fi

    echo "Pulling images..."
    echo ""

    local failed=0
    for image in "${images[@]}"; do
        if ! pull_image "$image"; then
            ((failed++))
        fi
    done

    echo ""

    if [ $failed -gt 0 ]; then
        warn "$failed image(s) failed to pull"
        echo "  Some images might not exist for this build."
        echo "  You can still try running the E2E stack with available images."
    fi

    # Save the tag for other scripts
    echo "$tag" > "$VERSION_FILE"
    success "Saved tag to $VERSION_FILE"

    echo ""
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║  Done! Run 'mf-docker.sh e2e-stack start' to launch stack ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo ""
}

# Run main function
main "$@"
