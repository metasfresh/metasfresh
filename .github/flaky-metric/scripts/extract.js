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
const { listRuns, junitArtifactNames, downloadArtifact, makeTmpRoot } = require('../lib/gh');
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
function processRun(run, tmpRoot) {
  const runId = run.databaseId;
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
          runUrl: run.url,
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
  return records;
}

// Aggregate failure records into per-scenario metric deltas for this window.
function aggregate(records) {
  const byScenario = new Map();
  for (const r of records) {
    let m = byScenario.get(r.scenario);
    if (!m) {
      m = {
        scenario: r.scenario,
        bucket: r.bucketLabel,
        testType: r.testType,
        failCount: 0,
        _runs: new Set(),
        firstFailed: r.dateUtc,
        lastFailed: r.dateUtc,
        lastRun: r.runUrl,
      };
      byScenario.set(r.scenario, m);
    }
    m.failCount += 1;
    m._runs.add(r.runId);
    m.bucket = r.bucketLabel;
    if (r.dateUtc < m.firstFailed) m.firstFailed = r.dateUtc;
    if (r.dateUtc >= m.lastFailed) {
      m.lastFailed = r.dateUtc;
      m.lastRun = r.runUrl;
    }
  }
  return [...byScenario.values()].map((m) => ({
    scenario: m.scenario,
    bucket: m.bucket,
    testType: m.testType,
    failCount: m.failCount,
    runsFailed: m._runs.size,
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
        '--json', 'databaseId,createdAt,headSha,displayTitle,url,conclusion',
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
    const recs = processRun(run, tmpRoot);
    console.error(`  run ${run.databaseId} (${(run.conclusion||'?')}): ${recs.length} failure(s)`);
    allRecords = allRecords.concat(recs);
  }

  const metrics = aggregate(allRecords);

  if (args.dryRun || (!args.sheet && !process.env.FLAKY_SHEET_ID)) {
    const outDir = path.join(__dirname, '..', 'out');
    fs.mkdirSync(outDir, { recursive: true });
    const failHeader = ['Key','Run','Date (UTC)','Commit','Test type','Profile/Spec','Scenario','Bucket','Exception','Message'];
    const failRows = allRecords.map((r) => [
      r.key, r.runUrl, r.dateUtc, r.commit, r.testType, r.profile, r.scenario, r.bucketLabel, r.exceptionType, (r.message||'').slice(0,500),
    ]);
    const metricHeader = ['Scenario','Bucket','Test type','Fail count','Runs failed','First failed','Last failed','Last failure run'];
    const metricRows = metrics.map((m) => [m.scenario,m.bucket,m.testType,m.failCount,m.runsFailed,m.firstFailed,m.lastFailed,m.lastRun]);

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
    key: r.key, runUrl: r.runUrl, dateUtc: r.dateUtc, commit: r.commit,
    testType: r.testType, profile: r.profile, scenario: r.scenario,
    bucketLabel: r.bucketLabel, exceptionType: r.exceptionType, message: r.message,
  }));
  const res = await upsert(sheetId, { failures: failuresForSheet, metrics });
  console.error(`Sheet updated: +${res.appendedFailures} failure rows, ${res.metricRows} metric rows total.`);
}

main().catch((e) => {
  console.error(e.stack || e.message);
  process.exit(1);
});
