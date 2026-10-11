#!/usr/bin/env bash
# Fails unless the DB seed that docker-builds/Dockerfile.db-init restores was built from a commit in the
# history of the commit being built, so a seed is never ahead of the branch that restores it (e.g. a
# new_dawn_uat seed carried down to a release line, or a branch older than the seed).
# The seed image (built by .github/workflows/db-seed.yaml) carries its source commit in the label
# org.opencontainers.image.revision.
#
# usage: check-db-seed.sh <Dockerfile.db-init> <commit-sha>     (needs docker, gh and GITHUB_REPOSITORY)
set -euo pipefail

dockerfile=$1
sha=$2

seed=$(sed -nE 's#^COPY --from=([^ ]+-seed) .*#\1#p' "${dockerfile}")
if [ -z "${seed}" ]; then
  echo "${dockerfile} restores no -seed image (the dump is downloaded): nothing to check"
  exit 0
fi

docker pull -q "${seed}" > /dev/null
rev=$(docker inspect "${seed}" --format '{{index .Config.Labels "org.opencontainers.image.revision"}}')
if [ -z "${rev}" ]; then
  echo "::error::the DB seed ${seed} has no org.opencontainers.image.revision label"
  exit 1
fi

status=$(gh api "repos/${GITHUB_REPOSITORY}/compare/${rev}...${sha}" --jq .status) \
  || { echo "::error::cannot compare the seed commit ${rev} with ${sha}"; exit 1; }
case "${status}" in
  ahead | identical)
    echo "DB seed ${seed} was built from ${rev}, which is in the history of ${sha}"
    ;;
  *)
    echo "::error::the DB seed ${seed} was built from commit ${rev}, which is not in the history of ${sha} (compare: ${status}). Use a seed built from this branch's own cicd runs (workflow db-seed)."
    exit 1
    ;;
esac
