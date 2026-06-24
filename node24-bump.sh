#!/usr/bin/env bash
# Bump GitHub Actions uses: references to Node-24-compatible majors on a single
# head branch, then open a PR. Honors me03 29844 / PR 24247.
#
# Strategy: try a direct cherry-pick of the new_dawn_uat squash commit first;
# if it does not apply cleanly (workflow files diverge per customer branch),
# fall back to a deterministic semantic bump of the 6 action packages that
# new_dawn_uat changed.
#
# Usage: node24-bump.sh <target-base-branch>
set -euo pipefail

BRANCH="$1"
SQUASH=36edbb7cc5b3375ed0fa1ea2f00a84e064628dc9
FEAT="${BRANCH}_ActionsNode24Migration"

git config user.name  "Adrian Stefan" >/dev/null 2>&1 || true
git config user.email "adrian.stefan@metasfresh.com" >/dev/null 2>&1 || true

git fetch origin "$BRANCH" --quiet

# Skip if the PR branch already exists on origin (idempotent re-runs)
if git ls-remote --exit-code --heads origin "$FEAT" >/dev/null 2>&1; then
  echo "SKIP   $BRANCH : feature branch $FEAT already on origin"
  exit 0
fi

git checkout -B "$FEAT" "origin/$BRANCH" --quiet

METHOD=""
if git cherry-pick "$SQUASH" >/dev/null 2>&1; then
  METHOD="cherry-pick"
else
  git cherry-pick --abort >/dev/null 2>&1 || true
  git reset --hard "origin/$BRANCH" --quiet
  mapfile -t FILES < <(find .github -type f \( -name '*.yml' -o -name '*.yaml' \) 2>/dev/null)
  if [ "${#FILES[@]}" -eq 0 ]; then
    echo "SKIP   $BRANCH : no .github workflow/action files"
    exit 0
  fi
  sed -i -E \
    -e 's#(actions/checkout@)v[0-9]+#\1v6#g' \
    -e 's#(actions/upload-artifact@)v[0-9]+#\1v6#g' \
    -e 's#(actions/download-artifact@)v[0-9]+#\1v8#g' \
    -e 's#(docker/login-action@)v[0-9]+#\1v4#g' \
    -e 's#(nick-fields/retry@)v[0-9]+#\1v4#g' \
    -e 's#(dawidd6/action-download-artifact@)v[0-9]+#\1v21#g' \
    "${FILES[@]}"
  if git diff --quiet; then
    echo "NOCHG  $BRANCH : already Node-24 compatible, nothing to bump"
    exit 0
  fi
  METHOD="semantic-bump"
  git add -A
  git commit --quiet -m "gh#29844 Bump GitHub Actions to Node 24-compatible versions

GitHub deprecated Node 20 on Actions runners (forced default 2026-06-02,
removal 2026-09-16). Bump the action uses: references that new_dawn_uat
migrated, to Node-24-compatible majors:

- actions/checkout            -> v6
- actions/upload-artifact     -> v6
- actions/download-artifact   -> v8
- docker/login-action         -> v4
- nick-fields/retry           -> v4
- dawidd6/action-download-artifact -> v21

Ported from new_dawn_uat (PR #24247). A literal cherry-pick did not apply
because this branch's workflow files have diverged, so the same version
bumps were applied directly.

Reference: https://github.blog/changelog/2025-09-19-deprecation-of-node-20-on-github-actions-runners/"
fi

# Verify no targeted package is left on an old major
LEFT=$(git grep -hoE '(actions/(checkout|upload-artifact|download-artifact)|docker/login-action|nick-fields/retry|dawidd6/action-download-artifact)@v[0-9]+' -- .github 2>/dev/null \
  | grep -vE '(checkout@v6|upload-artifact@v6|download-artifact@v8|login-action@v4|retry@v4|action-download-artifact@v21)' || true)
if [ -n "$LEFT" ]; then
  echo "WARN   $BRANCH : leftover old versions after bump:"; echo "$LEFT" | sort | uniq -c
fi

git push -u origin "$FEAT" --quiet

PR_URL=$(gh pr create --repo metasfresh/metasfresh --base "$BRANCH" --head "$FEAT" \
  --title "gh#29844 Bump GitHub Actions to Node 24-compatible versions" \
  --body "Ports the Node-24 GitHub Actions migration from \`new_dawn_uat\` (https://github.com/metasfresh/metasfresh/pull/24247, me03 https://github.com/metasfresh/me03/issues/29844) to \`$BRANCH\`.

GitHub forced the Node-24 default on Actions runners on 2026-06-02 (Node 20 removal 2026-09-16), so CI on this branch needs the bumped action versions to keep running.

Method on this branch: **$METHOD**. Bumped: \`actions/checkout@v6\`, \`actions/upload-artifact@v6\`, \`actions/download-artifact@v8\`, \`docker/login-action@v4\`, \`nick-fields/retry@v4\`, \`dawidd6/action-download-artifact@v21\` (the same package set new_dawn_uat migrated).

Reference: https://github.blog/changelog/2025-09-19-deprecation-of-node-20-on-github-actions-runners/" 2>&1)

echo "PR     $BRANCH ($METHOD) : $PR_URL"
