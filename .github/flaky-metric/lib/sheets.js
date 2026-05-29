'use strict';

// Google Sheets upsert for the two tabs:
//   "Failures" — append-only event log, one row per (run, failed scenario).
//                Idempotent: a row whose key (runId::fullName) already exists is
//                skipped, so re-running over an overlapping window is safe.
//   "Metrics"  — one row per scenario (fullName), upserted in place: running
//                fail count, distinct runs failed, first/last failed, current
//                bucket, and the last-5-runs failure marker string.
//
// Auth mirrors the existing qa-commit-history-sync skill: a service-account
// JSON at ~/.credentials/google-sheets-service-account.json (or the path in
// GOOGLE_SHEETS_CREDENTIALS / the inline GOOGLE_SHEETS_CREDENTIALS_JSON env for
// the GitHub Action). The sheet must be shared with the service account email.

const fs = require('fs');
const os = require('os');
const path = require('path');
const { google } = require('googleapis');

const SCOPES = ['https://www.googleapis.com/auth/spreadsheets'];

const FAILURES_TAB = 'Failures';
const METRICS_TAB = 'Metrics';

const FAILURES_HEADER = [
  'Key', 'Run', 'Date (UTC)', 'Commit', 'Test type', 'Profile/Spec',
  'Scenario', 'Bucket', 'Exception', 'Message',
];
const METRICS_HEADER = [
  'Scenario', 'Bucket', 'Test type', 'Fail count', 'Runs failed',
  'First failed', 'Last failed', 'Last failure run',
];

function loadCredentials() {
  if (process.env.GOOGLE_SHEETS_CREDENTIALS_JSON) {
    return JSON.parse(process.env.GOOGLE_SHEETS_CREDENTIALS_JSON);
  }
  const p =
    process.env.GOOGLE_SHEETS_CREDENTIALS ||
    path.join(os.homedir(), '.credentials', 'google-sheets-service-account.json');
  if (!fs.existsSync(p)) {
    throw new Error(
      `Google Sheets credentials not found at ${p}. See SETUP.md (human-only step).`
    );
  }
  return JSON.parse(fs.readFileSync(p, 'utf8'));
}

async function getClient() {
  const auth = new google.auth.GoogleAuth({ credentials: loadCredentials(), scopes: SCOPES });
  return google.sheets({ version: 'v4', auth });
}

async function ensureTab(sheets, spreadsheetId, title, header) {
  const meta = await sheets.spreadsheets.get({ spreadsheetId });
  const exists = (meta.data.sheets || []).some((s) => s.properties.title === title);
  if (!exists) {
    await sheets.spreadsheets.batchUpdate({
      spreadsheetId,
      requestBody: { requests: [{ addSheet: { properties: { title } } }] },
    });
  }
  // Ensure the header row is present.
  const got = await sheets.spreadsheets.values.get({
    spreadsheetId,
    range: `${title}!A1:Z1`,
  });
  if (!got.data.values || got.data.values.length === 0) {
    await sheets.spreadsheets.values.update({
      spreadsheetId,
      range: `${title}!A1`,
      valueInputOption: 'RAW',
      requestBody: { values: [header] },
    });
  }
}

async function getColumn(sheets, spreadsheetId, title, col) {
  const r = await sheets.spreadsheets.values.get({
    spreadsheetId,
    range: `${title}!${col}2:${col}`,
  });
  return (r.data.values || []).map((row) => row[0] || '');
}

// failures: [{ key, runUrl, dateUtc, commit, testType, profile, scenario, bucketLabel, exceptionType, message }]
// Metrics is recomputed from the Failures tab — no separate metrics input.
async function upsert(spreadsheetId, { failures }) {
  const sheets = await getClient();
  await ensureTab(sheets, spreadsheetId, FAILURES_TAB, FAILURES_HEADER);
  await ensureTab(sheets, spreadsheetId, METRICS_TAB, METRICS_HEADER);

  // --- Failures: append only the new keys ---
  const existingKeys = new Set(await getColumn(sheets, spreadsheetId, FAILURES_TAB, 'A'));
  const newRows = failures
    .filter((f) => !existingKeys.has(f.key))
    .map((f) => [
      f.key, f.runUrl, f.dateUtc, f.commit, f.testType, f.profile,
      f.scenario, f.bucketLabel, f.exceptionType, (f.message || '').slice(0, 500),
    ]);
  if (newRows.length) {
    await sheets.spreadsheets.values.append({
      spreadsheetId,
      range: `${FAILURES_TAB}!A1`,
      valueInputOption: 'RAW',
      insertDataOption: 'INSERT_ROWS',
      requestBody: { values: newRows },
    });
  }

  // --- Metrics: recompute as a pure PROJECTION of the Failures tab ---
  // Metrics is never accumulated independently — it is derived fresh from the
  // (idempotent, deduped) Failures event log every run. This makes the whole
  // pipeline idempotent: re-processing an overlapping window (e.g. the nightly
  // backfill re-seeing runs the per-run trigger already handled) cannot inflate
  // any count, because the Failures tab already rejected the duplicate rows.
  const allFailures = await sheets.spreadsheets.values.get({
    spreadsheetId,
    range: `${FAILURES_TAB}!A2:J`,
  });
  const metricRows = computeMetricsFromFailures(allFailures.data.values || []);

  // Clear the old Metrics data block first so a shrinking set never leaves
  // stale trailing rows (defensive — the log only grows in practice).
  await sheets.spreadsheets.values.clear({ spreadsheetId, range: `${METRICS_TAB}!A2:H` });
  if (metricRows.length) {
    await sheets.spreadsheets.values.update({
      spreadsheetId,
      range: `${METRICS_TAB}!A2`,
      valueInputOption: 'USER_ENTERED',
      requestBody: { values: metricRows },
    });
  }

  return { appendedFailures: newRows.length, metricRows: metricRows.length };
}

// Failures row layout: [Key, Run, Date, Commit, TestType, Profile, Scenario,
// Bucket, Exception, Message]. Key is `${runId}::${scenario}`.
function computeMetricsFromFailures(rows) {
  const byScenario = new Map();
  for (const r of rows) {
    const key = r[0] || '';
    const runUrl = r[1] || '';
    const date = r[2] || '';
    const testType = r[4] || '';
    const scenario = r[6] || '';
    const bucket = r[7] || '';
    const runId = key.split('::')[0];
    if (!scenario) continue;

    let m = byScenario.get(scenario);
    if (!m) {
      m = { scenario, bucket, testType, failCount: 0, runs: new Set(), firstFailed: date, lastFailed: date, lastRun: runUrl };
      byScenario.set(scenario, m);
    }
    m.failCount += 1;
    m.runs.add(runId);
    if (date && (!m.firstFailed || date < m.firstFailed)) m.firstFailed = date;
    if (date && (!m.lastFailed || date >= m.lastFailed)) {
      m.lastFailed = date;
      m.lastRun = runUrl;
      m.bucket = bucket; // most-recent classification wins
    }
  }
  return [...byScenario.values()]
    .sort((a, b) => b.failCount - a.failCount)
    .map((m) => [m.scenario, m.bucket, m.testType, m.failCount, m.runs.size, m.firstFailed, m.lastFailed, m.lastRun]);
}

module.exports = { upsert, computeMetricsFromFailures, FAILURES_HEADER, METRICS_HEADER };
