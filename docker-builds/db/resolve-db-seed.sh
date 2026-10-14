#!/usr/bin/env bash
# Picks the DB seed that docker-builds/Dockerfile.db-init restores (build arg DB_SEED) for the commit being built:
# - the seed of its base branch, metasfresh/metas-db:seed-<base-refname> (pushed by .github/workflows/db-seed.yaml),
#   if that seed was built from a commit in the history of the commit being built;
# - otherwise the 5.172 dump (stage seed-legacy): always correct, only slower, since every migration script since
#   5.172 is applied.
# So a seed is never ahead of the branch that restores it, and a branch without a seed of its own still builds.
# The seed image carries its source commit in the label org.opencontainers.image.revision. The tag is pinned to its
# digest, so a db-seed run that moves the tag during this build cannot swap the seed after the check.
#
# usage: resolve-db-seed.sh <base-refname> <commit-sha>     (needs docker, gh and GITHUB_REPOSITORY)
# Prints DB_SEED=<value>; also writes db-seed=<value> to $GITHUB_OUTPUT when that is set.
set -euo pipefail

base_refname=$1
sha=$2
seed="metasfresh/metas-db:seed-${base_refname}"

pick() {
  echo "DB_SEED=$1"
  if [ -n "${GITHUB_OUTPUT:-}" ]; then echo "db-seed=$1" >> "${GITHUB_OUTPUT}"; fi
  exit 0
}

legacy() {
  echo "$1"
  echo "restoring the 5.172 dump instead (all migration scripts since 5.172 are applied)"
  pick seed-legacy
}

# reads only the manifest; the build pulls the image itself
err=$(mktemp)
if ! info=$(docker buildx imagetools inspect "${seed}" \
    --format '{{.Manifest.Digest}} {{with .Image.Config.Labels}}{{index . "org.opencontainers.image.revision"}}{{end}}' 2>"${err}"); then
  case "$(cat "${err}")" in
    *"not found"*) legacy "there is no DB seed ${seed} (run workflow db-seed on a green cicd run of that branch)" ;;
    *) legacy "::warning::cannot read the DB seed ${seed}: $(cat "${err}")" ;;
  esac
fi
read -r digest rev <<< "${info}"
if ! [[ "${digest}" =~ ^sha256:[0-9a-f]{64}$ ]]; then
  legacy "::warning::unexpected digest '${digest}' for the DB seed ${seed}"
fi
if [ -z "${rev:-}" ]; then
  legacy "::warning::the DB seed ${seed} has no org.opencontainers.image.revision label"
fi

if ! status=$(gh api "repos/${GITHUB_REPOSITORY}/compare/${rev}...${sha}" --jq .status 2>&1); then
  legacy "::warning::cannot compare the seed commit ${rev} with ${sha}: ${status}"
fi
case "${status}" in
  ahead | identical)
    echo "DB seed ${seed} (${digest}) was built from ${rev}, which is in the history of ${sha}"
    pick "metasfresh/metas-db@${digest}"
    ;;
  *)
    legacy "the DB seed ${seed} was built from ${rev}, which is not in the history of ${sha} (compare: ${status})"
    ;;
esac
