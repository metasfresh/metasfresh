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
    '--json', 'databaseId,status,conclusion,createdAt,headSha,displayTitle,url',
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

// The set of junit artifacts a cicd run produces, covering all test types.
function junitArtifactNames() {
  const names = [];
  for (let p = 1; p <= 7; p++) names.push(`junit-results-cucumber-profile${p}`);
  names.push('junit-results-cucumber-catchall');
  names.push('junit-results-playwright-mobile');
  for (let s = 1; s <= 3; s++) names.push(`junit-results-playwright-frontend-shard${s}`);
  return names;
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

module.exports = { listRuns, junitArtifactNames, downloadArtifact, makeTmpRoot, REPO };
