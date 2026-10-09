'use strict';

// Thin wrapper around the `gh` CLI. We shell out rather than use Octokit so the
// tool works with zero extra auth setup both locally (your `gh auth login`) and
// inside the GitHub Action (the runner's GITHUB_TOKEN / GH_TOKEN).

const { execFileSync } = require('child_process');
const fs = require('fs');
const os = require('os');
const path = require('path');

const REPO = process.env.FLAKY_REPO || 'metasfresh/metasfresh';

function gh(args, { json = false } = {}) {
  const out = execFileSync('gh', args, {
    encoding: 'utf8',
    maxBuffer: 64 * 1024 * 1024,
  });
  return json ? JSON.parse(out) : out;
}

// List finished cicd.yaml runs on a branch since a cutoff (ISO date or "Nd").
function listRuns({ branch = 'new_dawn_uat', since, limit = 50 } = {}) {
  const args = [
    'run', 'list',
    '--repo', REPO,
    '--branch', branch,
    '--workflow', 'cicd.yaml',
    '--limit', String(limit),
    '--json', 'databaseId,status,conclusion,createdAt,headSha,headBranch,attempt,displayTitle,url',
  ];
  if (since) {
    const created = sinceToFilter(since);
    args.push('--created', `>=${created}`);
  }
  return gh(args, { json: true });
}

// Accepts an ISO date ("2026-05-25") or a relative "Nd"/"Nh" window.
function sinceToFilter(since) {
  const rel = /^(\d+)([dh])$/.exec(since);
  if (!rel) return since; // assume already a date
  const n = Number(rel[1]);
  const ms = rel[2] === 'd' ? n * 86400e3 : n * 3600e3;
  return new Date(Date.now() - ms).toISOString().slice(0, 10);
}

// The junit artifacts the metric tracks: every cucumber profile + catchall and
// every Playwright shard (mobile and frontend). Matched by pattern, not by a fixed
// list, so a change in the number of profiles or shards in cicd.yaml is picked up
// without touching this tool.
const TRACKED_JUNIT_ARTIFACT = new RegExp(
  '^junit-results-(?:' +
    'cucumber-(?:profile\\d+|catchall)' +
    '|playwright-mobile' + // runs from before mobile sharding
    '|playwright-(?:mobile|frontend)-shard\\d+' +
  ')$'
);

function isTrackedJunitArtifact(name) {
  return TRACKED_JUNIT_ARTIFACT.test(name);
}

// `gh api --paginate --jq '.artifacts[].name'` output (one name per line, pages
// concatenated) -> the tracked junit artifact names, in order, without duplicates.
function parseArtifactNames(out) {
  return [...new Set(out.split('\n').map((n) => n.trim()).filter(isTrackedJunitArtifact))];
}

// The tracked junit artifacts a given run actually has (expired ones included;
// downloadArtifact treats those as absent). One retry: a single transient API
// error should not abort a whole --since backfill; a persistent one still throws,
// so a run is never silently treated as having no test results.
function listJunitArtifacts(runId) {
  const args = [
    'api', '--paginate',
    `repos/${REPO}/actions/runs/${runId}/artifacts`,
    '--jq', '.artifacts[].name',
  ];
  let out;
  try {
    out = gh(args);
  } catch (e) {
    console.warn(`listing artifacts of run ${runId} failed, retrying once: ${e.message}`);
    out = gh(args);
  }
  return parseArtifactNames(out);
}

// Download one artifact for a run into a temp dir; returns the list of .xml
// files found, or [] if the artifact doesn't exist for that run.
function downloadArtifact(runId, artifactName, destRoot) {
  const dest = path.join(destRoot, String(runId), artifactName);
  fs.mkdirSync(dest, { recursive: true });
  try {
    gh(['run', 'download', String(runId), '--repo', REPO, '-n', artifactName, '--dir', dest]);
  } catch (e) {
    // Artifact absent for this run (e.g. profile not exercised) — not an error.
    return [];
  }
  return fs
    .readdirSync(dest)
    .filter((f) => f.endsWith('.xml'))
    .map((f) => path.join(dest, f));
}

function makeTmpRoot() {
  return fs.mkdtempSync(path.join(os.tmpdir(), 'flaky-metric-'));
}

// Pin a run URL to a specific attempt: `.../runs/<id>/attempts/<n>`.
// A re-run keeps the same run id but increments the attempt; the bare
// `.../runs/<id>` URL always shows the LATEST attempt — so a flaky run that
// failed on attempt 1 and passed on the re-run would link to a green page,
// which is misleading. We pin to the attempt where the failure was observed
// (even attempt 1, so a later re-run can't retroactively turn the link green).
function buildRunUrl(baseUrl, attempt) {
  const n = Number(attempt);
  if (!baseUrl) return baseUrl;
  if (!Number.isFinite(n) || n < 1) return baseUrl;
  return `${baseUrl}/attempts/${n}`;
}

module.exports = { listRuns, isTrackedJunitArtifact, parseArtifactNames, listJunitArtifacts, downloadArtifact, makeTmpRoot, buildRunUrl, REPO };
