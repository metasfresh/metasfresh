#!/usr/bin/env node
'use strict';

// metasfresh CI flaky-metric extractor (me03#30024).
//
// Modes:
//   node scripts/extract.js --run <id> [--dry-run]
//       Process a single cicd run (used by the GitHub Action workflow_run trigger).
//   node scripts/extract.js --since 7d [--branch new_dawn_uat] [--dry-run]
//       Backfill: process every finished cicd run on the branch since the window.
//
// Flags:
//   --dry-run         Write ./out/failures.csv + ./out/*.json instead of pushing
//                     to Google Sheets. Needs NO credentials — use this to
//                     validate the logic.
//   --sheet <id>      Target spreadsheet id (or env FLAKY_SHEET_ID).
//   --branch <name>   Branch (default new_dawn_uat).
//
// Exit code is always 0 on a successful extraction even if failures were found —
// this is a reporting tool, not a gate.

const fs = require('fs');
const path = require('path');
const { listRuns, junitArtifactNames, downloadArtifact, makeTmpRoot, buildRunUrl } = require('../lib/gh');
const { parseJUnitFile } = require('../lib/parse-junit');
const { bucketize } = require('../lib/bucketize');

function parseArgs(argv) {
  const a = { dryRun: false, branch: 'new_dawn_uat' };
  for (let i = 2; i < argv.length; i++) {
    const t = argv[i];
    if (t === '--dry-run') a.dryRun = true;
    else if (t === '--run') a.run = argv[++i];
    else if (t === '--since') a.since = argv[++i];
    else if (t === '--branch') a.branch = argv[++i];
    else if (t === '--sheet') a.sheet = argv[++i];
    // Authoritative attempt for the per-run trigger — the workflow_run event's
    // run_attempt. Pins the URL to the attempt that actually failed.
    else if (t === '--attempt') a.attempt = argv[++i];
    else throw new Error(`Unknown arg: ${t}`);
  }
  return a;
}

// Map an artifact name to a short profile/spec label for the sheet.
function profileLabel(artifactName) {
  return artifactName
    .replace(/^junit-results-cucumber-/, 'cucumber/')
    .replace(/^junit-results-playwright-/, 'pw/');
}

// Process one run -> array of failure records enriched with run metadata + bucket.
// attemptOverride: the authoritative attempt from the workflow_run event (per-run
// trigger); falls back to the run metadata's attempt, then 1.
function processRun(run, tmpRoot, attemptOverride) {
  const runId = run.databaseId;
  const attempt = Number(attemptOverride) || Number(run.attempt) || 1;
  const runUrl = buildRunUrl(run.url, attempt);
  const records = [];
  for (const artifactName of junitArtifactNames()) {
    const xmlFiles = downloadArtifact(runId, artifactName, tmpRoot);
    for (const xmlFile of xmlFiles) {
      const { failures } = parseJUnitFile(xmlFile);
      for (const f of failures) {
        const { bucketId, bucketLabel } = bucketize(f);
        records.push({
          key: `${runId}::${f.fullName}`,
          runId,
          runUrl,
          branch: run.headBranch || '',
          dateUtc: (run.createdAt || '').slice(0, 19).replace('T', ' '),
          commit: (run.headSha || '').slice(0, 9),
          testType: f.testType,
          profile: profileLabel(artifactName),
          scenario: f.fullName,
          bucketId,
          bucketLabel,
          exceptionType: f.exceptionType,
          message: f.message,
        });
      }
    }
  }

  // Cascade / no-parsed-failure guard: if the run is marked failed but we parsed
  // zero test-level failures, the failure is invisible at the test level — a JVM
  // crash mid-profile (cucumber "Tests run: 0" cascade), a compile/build failure,
  // or an infra failure that produced no test artifacts. Emit one synthetic
  // record so the run still shows up in the sheet instead of looking green.
  if (records.length === 0 && run.conclusion === 'failure') {
    records.push({
      key: `${runId}::__no_parsed_failure__`,
      runId,
      runUrl,
      branch: run.headBranch || '',
      dateUtc: (run.createdAt || '').slice(0, 19).replace('T', ' '),
      commit: (run.headSha || '').slice(0, 9),
      testType: 'unknown',
      profile: '(run-level)',
      scenario: '(run failed — no test-level failure parsed)',
      bucketId: 'A',
      bucketLabel: 'A: run failed, 0 test failures parsed (JVM cascade / build / infra)',
      exceptionType: '__TESTS_RUN_ZERO__',
      message: 'Run conclusion=failure but no JUnit artifact reported a failed testcase.',
    });
  }
  return records;
}

// Aggregate failure records into per-(branch, scenario) metric rows for the
// dry-run CSV. The sheet path uses computeMetricsFromFailures (same grouping)
// recomputed from the Failures tab; this mirrors it for the local CSV.
function aggregate(records) {
  const byKey = new Map();
  for (const r of records) {
    const k = `${r.branch} ${r.scenario}`;
    let m = byKey.get(k);
    if (!m) {
      m = {
        branch: r.branch,
        scenario: r.scenario,
        bucket: r.bucketLabel,
        testType: r.testType,
        failCount: 0,
        firstFailed: r.dateUtc,
        lastFailed: r.dateUtc,
        lastRun: r.runUrl,
      };
      byKey.set(k, m);
    }
    m.failCount += 1;
    m.bucket = r.bucketLabel;
    if (r.dateUtc < m.firstFailed) m.firstFailed = r.dateUtc;
    if (r.dateUtc >= m.lastFailed) {
      m.lastFailed = r.dateUtc;
      m.lastRun = r.runUrl;
    }
  }
  return [...byKey.values()].map((m) => ({
    branch: m.branch,
    scenario: m.scenario,
    bucket: m.bucket,
    testType: m.testType,
    failCount: m.failCount,
    firstFailed: m.firstFailed,
    lastFailed: m.lastFailed,
    lastRun: m.lastRun,
  }));
}

function toCsv(rows, header) {
  const esc = (v) => {
    const s = String(v ?? '');
    return /[",\n]/.test(s) ? `"${s.replace(/"/g, '""')}"` : s;
  };
  return [header.join(','), ...rows.map((r) => r.map(esc).join(','))].join('\n');
}

async function main() {
  const args = parseArgs(process.argv);

  let runs;
  if (args.run) {
    // Build a minimal run object; fetch its metadata via gh.
    const { execFileSync } = require('child_process');
    const meta = JSON.parse(
      execFileSync('gh', [
        'run', 'view', args.run, '--repo', process.env.FLAKY_REPO || 'metasfresh/metasfresh',
        '--json', 'databaseId,createdAt,headSha,headBranch,attempt,displayTitle,url,conclusion',
      ], { encoding: 'utf8' })
    );
    runs = [meta];
  } else if (args.since) {
    runs = listRuns({ branch: args.branch, since: args.since });
    // Only finished runs are useful.
    runs = runs.filter((r) => r.status === 'completed' || r.conclusion);
  } else {
    throw new Error('Specify --run <id> or --since <Nd|date>');
  }

  console.error(`Processing ${runs.length} run(s)...`);
  const tmpRoot = makeTmpRoot();
  let allRecords = [];
  for (const run of runs) {
    // --attempt only applies to the single-run (per-run trigger) mode; for a
    // --since sweep each run carries its own attempt in the metadata.
    const attemptOverride = args.run ? args.attempt : undefined;
    const recs = processRun(run, tmpRoot, attemptOverride);
    console.error(`  run ${run.databaseId} (${(run.conclusion||'?')}): ${recs.length} failure(s)`);
    allRecords = allRecords.concat(recs);
  }

  const metrics = aggregate(allRecords);

  if (args.dryRun || (!args.sheet && !process.env.FLAKY_SHEET_ID)) {
    const outDir = path.join(__dirname, '..', 'out');
    fs.mkdirSync(outDir, { recursive: true });
    const failHeader = ['Key','Run','Branch','Date (UTC)','Commit','Test type','Profile/Spec','Scenario','Bucket','Exception','Message'];
    const failRows = allRecords.map((r) => [
      r.key, r.runUrl, r.branch, r.dateUtc, r.commit, r.testType, r.profile, r.scenario, r.bucketLabel, r.exceptionType, (r.message||'').slice(0,500),
    ]);
    const metricHeader = ['Branch','Scenario','Bucket','Test type','Fail count','First failed','Last failed','Last failure run'];
    const metricRows = metrics.map((m) => [m.branch,m.scenario,m.bucket,m.testType,m.failCount,m.firstFailed,m.lastFailed,m.lastRun]);

    fs.writeFileSync(path.join(outDir, 'failures.csv'), toCsv(failRows, failHeader));
    fs.writeFileSync(path.join(outDir, 'metrics.csv'), toCsv(metricRows, metricHeader));
    fs.writeFileSync(path.join(outDir, 'failures.json'), JSON.stringify(allRecords, null, 2));
    fs.writeFileSync(path.join(outDir, 'metrics.json'), JSON.stringify(metrics, null, 2));

    console.error(`\nDRY RUN — wrote ${failRows.length} failure rows + ${metricRows.length} metric rows to ${outDir}/`);
    // Console summary by bucket.
    const byBucket = {};
    for (const r of allRecords) byBucket[r.bucketLabel] = (byBucket[r.bucketLabel]||0)+1;
    console.error('\nBy bucket:');
    for (const [b, n] of Object.entries(byBucket).sort((a,b)=>b[1]-a[1])) console.error(`  ${n}\t${b}`);
    return;
  }

  const { upsert } = require('../lib/sheets');
  const sheetId = args.sheet || process.env.FLAKY_SHEET_ID;
  const failuresForSheet = allRecords.map((r) => ({
    key: r.key, runUrl: r.runUrl, branch: r.branch, dateUtc: r.dateUtc, commit: r.commit,
    testType: r.testType, profile: r.profile, scenario: r.scenario,
    bucketLabel: r.bucketLabel, exceptionType: r.exceptionType, message: r.message,
  }));
  const res = await upsert(sheetId, { failures: failuresForSheet });
  console.error(`Sheet updated: +${res.appendedFailures} failure rows, ${res.metricRows} metric rows total.`);
}

main().catch((e) => {
  console.error(e.stack || e.message);
  process.exit(1);
});
