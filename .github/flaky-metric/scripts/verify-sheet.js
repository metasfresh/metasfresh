#!/usr/bin/env node
'use strict';
// Quick read-back of the target sheet, for sanity-checking after an upsert.
// Usage: FLAKY_SHEET_ID=<id> node scripts/verify-sheet.js
const os = require('os');
const { google } = require('googleapis');

const id = process.env.FLAKY_SHEET_ID || process.argv[2];
if (!id) { console.error('Set FLAKY_SHEET_ID or pass the id as arg1'); process.exit(1); }
const creds = require(os.homedir() + '/.credentials/google-sheets-service-account.json');

(async () => {
  const auth = new google.auth.GoogleAuth({ credentials: creds, scopes: ['https://www.googleapis.com/auth/spreadsheets.readonly'] });
  const sheets = google.sheets({ version: 'v4', auth });
  const meta = await sheets.spreadsheets.get({ spreadsheetId: id });
  console.log('Tabs:', meta.data.sheets.map((s) => s.properties.title).join(', '));

  const m = await sheets.spreadsheets.values.get({ spreadsheetId: id, range: 'Metrics!A1:H' });
  console.log('\n=== METRICS ===');
  console.log('fails'.padStart(5), 'bucket'.padEnd(44), 'scenario');
  for (const r of (m.data.values || []).slice(1)) {
    console.log(String(r[3] || '').padStart(5), String(r[1] || '').slice(0, 42).padEnd(44), String(r[0] || '').slice(0, 60));
  }
  const f = await sheets.spreadsheets.values.get({ spreadsheetId: id, range: 'Failures!A1:J' });
  console.log(`\n=== FAILURES === (${(f.data.values || []).length - 1} rows)`);
})().catch((e) => { console.error(e.message); process.exit(1); });
