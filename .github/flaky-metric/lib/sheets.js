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
// GOOGLE_SHEETS_CREDENTIALS / the inline QA_SHEETS_SERVICE_ACCOUNT_JSON env for
// the GitHub Action). The sheet must be shared with the service account email.

const fs = require('fs');
const os = require('os');
const path = require('path');
const { google } = require('googleapis');

const SCOPES = ['https://www.googleapis.com/auth/spreadsheets'];

const FAILURES_TAB = 'Failures';
const METRICS_TAB = 'Metrics';

const FAILURES_HEADER = [
  'Key', 'Run', 'Branch', 'Date (UTC)', 'Commit', 'Test type', 'Profile/Spec',
  'Scenario', 'Bucket', 'Exception', 'Message',
];
const METRICS_HEADER = [
  'Branch', 'Scenario', 'Bucket', 'Test type', 'Fail count',
  'First failed', 'Last failed', 'Last failure run',
];

function loadCredentials() {
  // Inline service-account JSON — uses the org-standard secret name
  // QA_SHEETS_SERVICE_ACCOUNT_JSON (same one me03-gh-automation uses).
  if (process.env.QA_SHEETS_SERVICE_ACCOUNT_JSON) {
    return JSON.parse(process.env.QA_SHEETS_SERVICE_ACCOUNT_JSON);
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
  // Ensure the header row is present AND matches the current schema. If a tab
  // from an older schema version has a different header, rewrite the header and
  // wipe the data rows — the data is always reconstructable from CI artifacts
  // via a `--since` backfill, so a clean re-fill on a schema change is correct
  // and avoids silently misaligned columns.
  const got = await sheets.spreadsheets.values.get({
    spreadsheetId,
    range: `${title}!A1:Z1`,
  });
  const current = (got.data.values && got.data.values[0]) || [];
  const matches = current.length === header.length && header.every((h, i) => current[i] === h);
  if (!matches) {
    await sheets.spreadsheets.values.clear({ spreadsheetId, range: `${title}!A:Z` });
    // Reset the data rows' number format to automatic. Google Sheets keeps a
    // column's format across schema changes, so when a column shifts (e.g. a new
    // column is inserted) a count can inherit a stale date format from whatever
    // used to live in that column. Clearing the format lets each value render by
    // its own type (numbers as numbers, parsed dates as dates).
    const sheetId = (meta.data.sheets || []).find((s) => s.properties.title === title)?.properties.sheetId;
    if (sheetId !== undefined) {
      await sheets.spreadsheets.batchUpdate({
        spreadsheetId,
        requestBody: {
          requests: [{
            repeatCell: {
              range: { sheetId, startRowIndex: 1 },
              cell: {},
              fields: 'userEnteredFormat.numberFormat',
            },
          }],
        },
      });
    }
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

// failures: [{ key, runUrl, branch, dateUtc, commit, testType, profile, scenario, bucketLabel, exceptionType, message }]
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
      f.key, f.runUrl, f.branch, f.dateUtc, f.commit, f.testType, f.profile,
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
    range: `${FAILURES_TAB}!A2:K`,
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

// Failures row layout: [Key, Run, Branch, Date, Commit, TestType, Profile,
// Scenario, Bucket, Exception, Message]. Key is `${runId}::${scenario}`.
// Metrics are grouped per (branch, scenario) so the same scenario failing on
// two different branches stays as two distinct rows.
function computeMetricsFromFailures(rows) {
  const byKey = new Map();
  for (const r of rows) {
    const runUrl = r[1] || '';
    const branch = r[2] || '';
    const date = r[3] || '';
    const testType = r[5] || '';
    const scenario = r[7] || '';
    const bucket = r[8] || '';
    if (!scenario) continue;

    // One Failures row per (run, scenario) — the key is runId::scenario — so a
    // simple row count per (branch, scenario) IS the distinct-runs-failed count.
    const groupKey = `${branch} ${scenario}`;
    let m = byKey.get(groupKey);
    if (!m) {
      m = { branch, scenario, bucket, testType, failCount: 0, firstFailed: date, lastFailed: date, lastRun: runUrl };
      byKey.set(groupKey, m);
    }
    m.failCount += 1;
    if (date && (!m.firstFailed || date < m.firstFailed)) m.firstFailed = date;
    if (date && (!m.lastFailed || date >= m.lastFailed)) {
      m.lastFailed = date;
      m.lastRun = runUrl;
      m.bucket = bucket; // most-recent classification wins
    }
  }
  return [...byKey.values()]
    .sort((a, b) => b.failCount - a.failCount)
    .map((m) => [m.branch, m.scenario, m.bucket, m.testType, m.failCount, m.firstFailed, m.lastFailed, m.lastRun]);
}

module.exports = { upsert, computeMetricsFromFailures, FAILURES_HEADER, METRICS_HEADER };
