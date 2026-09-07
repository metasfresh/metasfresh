#!/usr/bin/env bash
# Diagnostic docker push wrapper for the cicd.yaml push-image steps.
#
# Why this exists: pushes to Docker Hub (registry-1.docker.io) intermittently run
# very slow / time out under concurrency. The push steps used `docker push --quiet`,
# which hides everything, so we could never see WHY. This wrapper:
#   - logs the image size + layer count (payload),
#   - pushes WITHOUT --quiet so per-layer progress, "Layer already exists" /
#     "Mounted from" (cache hits) and "Retrying in Ns" (registry throttling) are
#     visible in the CI log,
#   - reports the elapsed push time,
# while preserving the exact exit code so nick-fields/retry still retries on failure.
#
# Usage: docker-push.sh <image:tag>
#
# NOTE: `-e` is intentionally omitted. We must log the elapsed time / result line and
# close the ::group:: even when `docker push` fails; with `-e` the script would abort
# on the failed push and emit neither. The push exit code is captured and re-exited
# explicitly so nick-fields/retry and the Merge Gate still see the failure.
set -uo pipefail

TAG="${1:?usage: docker-push.sh <image:tag>}"

size=$(docker image inspect "$TAG" --format '{{.Size}}' 2>/dev/null || echo 0)
size=${size//[^0-9]/}; [ -z "$size" ] && size=0
layers=$(docker image inspect "$TAG" --format '{{len .RootFS.Layers}}' 2>/dev/null || echo '?')

echo "::group::docker push $TAG"
echo "[push-diag] image=$TAG size_bytes=$size size_MiB=$((size/1048576)) layers=$layers registry=registry-1.docker.io"

start=$(date +%s)
docker push "$TAG"
rc=$?
echo "[push-diag] result tag=$TAG rc=$rc elapsed_s=$(( $(date +%s) - start ))"
echo "::endgroup::"

exit "$rc"
