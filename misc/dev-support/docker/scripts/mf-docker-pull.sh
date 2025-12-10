#!/bin/bash
#
# mf-docker-pull.sh - Pull latest CI-built Docker images for a branch
#
# Usage: mf-docker-pull.sh [options] [branch-name]
#        If branch-name is not provided, uses current git branch
#
# Options:
#   --repo <name>       Pull from custom repo (e.g., ms205) instead of metasfresh/metasfresh
#   --repo-root <path>  Path to custom repo checkout (for version.info)
#   --skip-builder      Skip the large maven builder image
#
# This script:
# 1. Queries GitHub Actions for the latest successful CI build
# 2. Pulls all required Docker images with that tag
# 3. Saves the configuration for use by other mf-docker scripts
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

# Login to GitHub Container Registry
ghcr_login() {
    info "Logging into GitHub Container Registry..."
    local gh_user
    gh_user=$(gh api user -q .login 2>/dev/null) || gh_user="token"

    if echo "$(gh auth token)" | docker login ghcr.io -u "$gh_user" --password-stdin 2>/dev/null; then
        success "Logged into ghcr.io"
    else
        error "Failed to login to GHCR"
        echo "  Make sure your GitHub token has 'read:packages' scope."
        echo "  Run: gh auth refresh -s read:packages"
        exit 1
    fi

    # Check if token has read:packages scope
    local scopes=$(gh auth status 2>&1 | grep "Token scopes" || true)
    if ! echo "$scopes" | grep -q "read:packages"; then
        warn "GitHub token may not have 'read:packages' scope"
        echo "  Current scopes: $scopes"
        echo "  To add the scope, run: gh auth refresh -s read:packages"
        echo ""
    fi
}

# Get branch name (from argument or current git branch)
get_branch_name() {
    local branch="$1"
    local repo_root="$2"

    if [ -z "$branch" ]; then
        # Try to get current git branch from repo root
        local git_dir="${repo_root:-$METASFRESH_ROOT}"
        if git -C "$git_dir" rev-parse --git-dir &> /dev/null; then
            branch=$(git -C "$git_dir" branch --show-current)
        fi
    fi

    if [ -z "$branch" ]; then
        error "Could not determine branch name."
        echo "  Please specify a branch name: mf-docker-pull.sh [--repo <name>] <branch-name>"
        exit 1
    fi

    echo "$branch"
}

# Get metasfresh version from version.info
get_mf_version() {
    local repo_root="$1"
    local version_info

    if [ -n "$repo_root" ]; then
        version_info="$repo_root/docker-builds/version.info"
    else
        version_info="$METASFRESH_ROOT/docker-builds/version.info"
    fi

    if [ ! -f "$version_info" ]; then
        error "version.info not found at $version_info"
        exit 1
    fi

    cat "$version_info" | tr -d '[:space:]'
}

# Get latest CI build number for a branch
get_latest_build_number() {
    local branch="$1"
    local github_repo="$2"
    local allow_failed="$3"

    local status_filter="success"
    if [ "$allow_failed" == "true" ]; then
        status_filter=""  # No status filter - get any build
        info "Querying GitHub for latest build (any status) on branch: $branch" >&2
    else
        info "Querying GitHub for latest successful build on branch: $branch" >&2
    fi
    info "Repository: $github_repo" >&2

    # Query GitHub Actions API for the latest run of cicd.yaml
    local run_info
    if [ -n "$status_filter" ]; then
        run_info=$(gh run list \
            --repo "$github_repo" \
            --branch "$branch" \
            --workflow "cicd.yaml" \
            --status "$status_filter" \
            --limit 1 \
            --json number,createdAt,displayTitle,status,conclusion 2>/dev/null)
    else
        run_info=$(gh run list \
            --repo "$github_repo" \
            --branch "$branch" \
            --workflow "cicd.yaml" \
            --limit 1 \
            --json number,createdAt,displayTitle,status,conclusion 2>/dev/null)
    fi

    if [ -z "$run_info" ] || [ "$run_info" == "[]" ]; then
        if [ "$allow_failed" == "true" ]; then
            error "No CI builds found for branch: $branch"
        else
            error "No successful CI builds found for branch: $branch"
            echo "  Tip: Use --allow-failed to pull from failed builds" >&2
        fi
        echo "  Check if the branch exists and has a cicd workflow run." >&2
        echo "  You can view runs at: https://github.com/$github_repo/actions" >&2
        exit 1
    fi

    # Extract the run number (this is the GitHub Actions run number used in image tags)
    local run_number=$(echo "$run_info" | jq -r '.[0].number')
    local created_at=$(echo "$run_info" | jq -r '.[0].createdAt')
    local conclusion=$(echo "$run_info" | jq -r '.[0].conclusion')
    local status=$(echo "$run_info" | jq -r '.[0].status')

    if [ -z "$run_number" ] || [ "$run_number" == "null" ]; then
        error "Could not parse build number from GitHub response"
        exit 1
    fi

    if [ "$conclusion" != "success" ] && [ "$conclusion" != "null" ]; then
        warn "Build #$run_number has conclusion: $conclusion" >&2
        echo "  Some images may not exist or may be incomplete." >&2
    fi

    info "Found: build #$run_number (created: $created_at, status: $status, conclusion: $conclusion)" >&2

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
    # Use || true to prevent set -e from exiting on failure
    local result=0
    docker pull "$image" || result=$?

    if [ $result -eq 0 ]; then
        success "$image"
        return 0
    else
        error "Failed to pull $image"
        return 1
    fi
}

# Get images list for standard metasfresh CI
get_standard_images() {
    local tag="$1"
    local skip_builder="$2"

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
    if [ "$skip_builder" != "true" ]; then
        images+=("metasfresh/metas-mvn-backend:$tag")
    fi

    echo "${images[@]}"
}

# Get images list for custom repo (e.g., ms205)
get_custom_repo_images() {
    local repo="$1"
    local tag="$2"
    local registry="ghcr.io/metasfresh/$repo"

    # Custom repos use different image naming: metasfresh-* instead of metas-*
    # And use -compat suffix
    local images=(
        "$registry/metasfresh-webapi:$tag-compat"
        "$registry/metasfresh-app:$tag-compat"
        "$registry/metasfresh-webui:$tag-compat"
        "$registry/metasfresh-db:$tag-compat"
        "$registry/metasfresh-externalsystems:$tag-compat"
        "$registry/metasfresh-edi:$tag-compat"
    )

    echo "${images[@]}"
}

# Save version configuration
save_version_config() {
    local tag="$1"
    local repo="$2"
    local repo_root="$3"
    local registry="$4"

    cat > "$VERSION_FILE" << EOF
# Generated by mf-docker-pull.sh at $(date -Iseconds)
TAG=$tag
REPO=$repo
REPO_ROOT=$repo_root
REGISTRY=$registry
EOF

    success "Saved configuration to $VERSION_FILE"
}

# Parse command line arguments
parse_args() {
    REPO=""
    REPO_ROOT=""
    SKIP_BUILDER="false"
    ALLOW_FAILED="false"
    BRANCH_ARG=""

    while [[ $# -gt 0 ]]; do
        case $1 in
            --repo)
                REPO="$2"
                shift 2
                ;;
            --repo-root)
                REPO_ROOT="$2"
                shift 2
                ;;
            --skip-builder)
                SKIP_BUILDER="true"
                shift
                ;;
            --allow-failed)
                ALLOW_FAILED="true"
                shift
                ;;
            --help|-h)
                show_help
                exit 0
                ;;
            -*)
                error "Unknown option: $1"
                show_help
                exit 1
                ;;
            *)
                BRANCH_ARG="$1"
                shift
                ;;
        esac
    done
}

show_help() {
    echo "Usage: mf-docker-pull.sh [options] [branch-name]"
    echo ""
    echo "Pull latest CI-built Docker images for a branch."
    echo ""
    echo "Options:"
    echo "  --repo <name>       Pull from custom repo (e.g., ms205) from GHCR"
    echo "  --repo-root <path>  Path to custom repo checkout (for version.info)"
    echo "  --skip-builder      Skip the large maven builder image"
    echo "  --allow-failed      Pull from failed/incomplete builds (for debugging)"
    echo "  -h, --help          Show this help message"
    echo ""
    echo "Examples:"
    echo "  # Pull from main metasfresh CI (Docker Hub)"
    echo "  mf-docker-pull.sh new_dawn_uat"
    echo ""
    echo "  # Pull from custom repo (GHCR)"
    echo "  mf-docker-pull.sh --repo ms205 memorable_shiny_uat"
    echo "  mf-docker-pull.sh --repo ms205 --repo-root ../ms205"
    echo ""
    echo "  # Pull from a failed build (for debugging)"
    echo "  mf-docker-pull.sh --repo ms205 --allow-failed some_branch"
    echo ""
}

# Main function
main() {
    parse_args "$@"

    echo ""
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║          mf-docker-pull - Pull CI Docker Images           ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo ""

    # Prerequisites
    check_prerequisites

    # Determine if using custom repo
    local github_repo="metasfresh/metasfresh"
    local registry="metasfresh"
    local is_custom_repo="false"

    if [ -n "$REPO" ]; then
        github_repo="metasfresh/$REPO"
        registry="ghcr.io/metasfresh/$REPO"
        is_custom_repo="true"
        info "Using custom repo: $REPO"

        # Try to auto-detect repo root if not specified
        if [ -z "$REPO_ROOT" ]; then
            # Look for repo in common locations relative to metasfresh
            local possible_paths=(
                "$METASFRESH_ROOT/../$REPO"
                "$METASFRESH_ROOT/../../$REPO"
            )
            for path in "${possible_paths[@]}"; do
                if [ -d "$path/docker-builds" ]; then
                    REPO_ROOT="$(cd "$path" && pwd)"
                    info "Auto-detected repo root: $REPO_ROOT"
                    break
                fi
            done
        fi

        if [ -z "$REPO_ROOT" ]; then
            warn "Custom repo root not found. Will use metasfresh's version.info"
            warn "Specify --repo-root to use the custom repo's version.info"
        fi
    fi

    # Get branch name
    local branch=$(get_branch_name "$BRANCH_ARG" "$REPO_ROOT")
    local sanitized_branch=$(sanitize_branch_name "$branch")

    # Get version
    local mf_version=$(get_mf_version "$REPO_ROOT")
    info "metasfresh version: $mf_version"

    # Get latest build number
    local build_number=$(get_latest_build_number "$branch" "$github_repo" "$ALLOW_FAILED")

    # Check if build number was found (exit happens in subshell, so we check here)
    if [ -z "$build_number" ]; then
        exit 1
    fi

    # Construct the tag
    local tag="${mf_version}-${sanitized_branch}.${build_number}"

    echo ""
    info "Image tag: $tag"
    if [ "$is_custom_repo" == "true" ]; then
        info "Registry: $registry"
    fi
    echo ""

    # Login to GHCR if using custom repo
    if [ "$is_custom_repo" == "true" ]; then
        ghcr_login
        echo ""
    fi

    # Get images list based on repo type
    local images
    if [ "$is_custom_repo" == "true" ]; then
        read -ra images <<< "$(get_custom_repo_images "$REPO" "$tag")"
    else
        read -ra images <<< "$(get_standard_images "$tag" "$SKIP_BUILDER")"
    fi

    echo "Pulling images..."
    echo ""

    local failed=0
    for image in "${images[@]}"; do
        if ! pull_image "$image"; then
            failed=$((failed + 1))
        fi
    done

    echo ""

    if [ $failed -gt 0 ]; then
        warn "$failed image(s) failed to pull"
        echo "  Some images might not exist for this build."
        echo "  You can still try running the E2E stack with available images."
    fi

    # Save configuration
    if [ "$is_custom_repo" == "true" ]; then
        save_version_config "$tag" "$REPO" "$REPO_ROOT" "$registry"
    else
        # For backward compatibility, save just the tag for standard builds
        # But also save full config for consistency
        save_version_config "$tag" "" "$METASFRESH_ROOT" "$registry"
    fi

    echo ""
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║  Done! Run 'mf-docker.sh e2e-stack start' to launch stack ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo ""
}

# Run main function
main "$@"
