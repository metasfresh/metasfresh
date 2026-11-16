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

async function ensureTab(sheets, spreadsheetId, title, header, { allowTrailingColumns = false } = {}) {
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
  //
  // allowTrailingColumns: treat the owned header as a required PREFIX and accept
  // any extra columns to its right. Those extra columns are manually-maintained
  // annotations (e.g. a human-filled "Fixing PR" column) that this tool does not
  // own — it must never wipe them just because they are not in its own schema.
  // Only a genuine change to one of the OWNED columns (prefix mismatch) still
  // triggers the clean re-fill.
  //
  // Caveat: this only detects owned-column RENAMES, not a REMOVAL. If the owned
  // header ever shrinks (a column dropped), the now-stale column would still
  // prefix-match and be silently carried as a phantom "annotation". The schema
  // has only ever grown; should a column be removed, clear the tab manually once.
  const got = await sheets.spreadsheets.values.get({
    spreadsheetId,
    range: `${title}!A1:Z1`,
  });
  const current = (got.data.values && got.data.values[0]) || [];
  const prefixMatches = header.every((h, i) => current[i] === h);
  const matches = allowTrailingColumns
    ? current.length >= header.length && prefixMatches
    : current.length === header.length && prefixMatches;
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
  // allowTrailingColumns on BOTH tabs: a manual annotation column to the right of
  // the owned schema must not trip the schema-change wipe. On Failures that wipe
  // would also blow away the whole append-only event log; on Metrics it would
  // erase the annotation. The Metrics annotations are additionally re-joined to
  // their scenario below (Metrics rows are re-sorted every run).
  await ensureTab(sheets, spreadsheetId, FAILURES_TAB, FAILURES_HEADER, { allowTrailingColumns: true });
  await ensureTab(sheets, spreadsheetId, METRICS_TAB, METRICS_HEADER, { allowTrailingColumns: true });

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

  // Read the Metrics tab AS IT IS (header + any manual annotation columns) before
  // we overwrite it, so we can carry the annotations forward. Because the metric
  // rows are re-sorted by fail count every run, an annotation cannot simply be
  // left in place — it must be re-joined to its scenario and re-emitted in the
  // new row order. reattachAnnotations does that purely from the prior values.
  const priorMetrics = await sheets.spreadsheets.values.get({
    spreadsheetId,
    range: `${METRICS_TAB}!A1:Z`,
  });
  const { rows: metricRowsOut } = reattachAnnotations(metricRows, priorMetrics.data.values || []);

  // Clear the full data block (owned columns + any annotation columns) so a
  // shrinking set or a re-sort never leaves a stale or misaligned cell behind.
  // Row 1 (header, including the annotation header) is preserved.
  await sheets.spreadsheets.values.clear({ spreadsheetId, range: `${METRICS_TAB}!A2:Z` });
  if (metricRowsOut.length) {
    await sheets.spreadsheets.values.update({
      spreadsheetId,
      range: `${METRICS_TAB}!A2`,
      valueInputOption: 'USER_ENTERED',
      requestBody: { values: metricRowsOut },
    });
  }

  return { appendedFailures: newRows.length, metricRows: metricRowsOut.length };
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

// Carry manually-maintained annotation columns (everything to the right of the
// owned METRICS_HEADER, e.g. a human-filled "Fixing PR" column) forward across a
// recompute. The metric rows are re-sorted every run, so an annotation is keyed
// to its scenario by (branch, scenario) — the same group key the projection uses
// — and re-emitted in the freshly-sorted order. New scenarios get blank cells;
// scenarios that dropped out of the metrics simply lose their (now-orphan) cells.
// The annotation HEADER text is never defined here — it lives only in the sheet.
//
//   metricRows:    freshly-computed 8-column rows from computeMetricsFromFailures.
//   priorValues:   the Metrics tab's current values (row 0 = header, incl. any
//                  annotation columns), as returned by values.get on A1:Z.
// Returns { rows, annWidth } where each row is the 8 owned columns followed by
// annWidth annotation cells (0 when the sheet has no annotation columns yet).
function reattachAnnotations(metricRows, priorValues) {
  const prior = priorValues || [];
  const header = prior[0] || [];
  const annWidth = Math.max(0, header.length - METRICS_HEADER.length);
  if (annWidth === 0) return { rows: metricRows, annWidth: 0 };

  const annByKey = new Map();
  for (const r of prior.slice(1)) {
    const branch = r[0] || '';
    const scenario = r[1] || '';
    if (!scenario) continue;
    const ann = [];
    for (let i = 0; i < annWidth; i++) ann.push(r[METRICS_HEADER.length + i] || '');
    if (ann.some((c) => c !== '')) annByKey.set(`${branch} ${scenario}`, ann);
  }

  const blank = new Array(annWidth).fill('');
  const rows = metricRows.map((m) => {
    const ann = annByKey.get(`${m[0]} ${m[1]}`) || blank;
    return [...m, ...ann];
  });
  return { rows, annWidth };
}

module.exports = { upsert, computeMetricsFromFailures, reattachAnnotations, FAILURES_HEADER, METRICS_HEADER };
