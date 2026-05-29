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
async function upsert(spreadsheetId, { failures, metrics }) {
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

  // --- Metrics: read current rows, merge, rewrite the block ---
  const existing = await sheets.spreadsheets.values.get({
    spreadsheetId,
    range: `${METRICS_TAB}!A2:H`,
  });
  const byScenario = new Map();
  for (const row of existing.data.values || []) {
    const [scenario, bucket, testType, failCount, runsFailed, firstFailed, lastFailed, lastRun] = row;
    byScenario.set(scenario, {
      scenario, bucket, testType,
      failCount: Number(failCount) || 0,
      runsFailed: Number(runsFailed) || 0,
      firstFailed, lastFailed, lastRun,
    });
  }
  // metrics here is the per-scenario delta computed for THIS extraction window.
  for (const m of metrics) {
    const cur = byScenario.get(m.scenario);
    if (!cur) {
      byScenario.set(m.scenario, { ...m });
    } else {
      cur.failCount += m.failCount;
      cur.runsFailed += m.runsFailed;
      cur.bucket = m.bucket; // latest classification wins
      cur.testType = m.testType;
      if (!cur.firstFailed || m.firstFailed < cur.firstFailed) cur.firstFailed = m.firstFailed;
      if (!cur.lastFailed || m.lastFailed > cur.lastFailed) {
        cur.lastFailed = m.lastFailed;
        cur.lastRun = m.lastRun;
      }
    }
  }
  const merged = [...byScenario.values()].sort((a, b) => b.failCount - a.failCount);
  const metricRows = merged.map((m) => [
    m.scenario, m.bucket, m.testType, m.failCount, m.runsFailed,
    m.firstFailed, m.lastFailed, m.lastRun,
  ]);
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

module.exports = { upsert, FAILURES_HEADER, METRICS_HEADER };
