'use strict';

const test = require('node:test');
const assert = require('node:assert');
const { computeMetricsFromFailures } = require('../lib/sheets');

// Failures row layout: [Key, Run, Date, Commit, TestType, Profile, Scenario, Bucket, Exception, Message]
const row = (runId, date, scenario, bucket, testType = 'cucumber') => [
  `${runId}::${scenario}`, `https://x/runs/${runId}`, date, 'abc123', testType, 'cucumber/profile1', scenario, bucket, 'Exc', 'msg',
];

test('metrics projection: counts rows + distinct runs per scenario', () => {
  const rows = [
    row(100, '2026-05-25 10:00', 'TC5b', 'E'),
    row(101, '2026-05-26 10:00', 'TC5b', 'E'),
    row(101, '2026-05-26 10:00', 'EDI export', 'K'),
  ];
  const out = computeMetricsFromFailures(rows);
  const tc5b = out.find((r) => r[0] === 'TC5b');
  // [scenario, bucket, testType, failCount, runsFailed, first, last, lastRun]
  assert.strictEqual(tc5b[3], 2, 'failCount = 2 rows');
  assert.strictEqual(tc5b[4], 2, 'runsFailed = 2 distinct runs');
  assert.strictEqual(tc5b[5], '2026-05-25 10:00', 'firstFailed = earliest');
  assert.strictEqual(tc5b[6], '2026-05-26 10:00', 'lastFailed = latest');
});

test('metrics projection: idempotent — same input twice yields identical output', () => {
  const rows = [row(100, '2026-05-25 10:00', 'TC5b', 'E'), row(101, '2026-05-26 10:00', 'TC5b', 'E')];
  const a = computeMetricsFromFailures(rows);
  const b = computeMetricsFromFailures(rows);
  assert.deepStrictEqual(a, b);
  // Critical-fix guard: re-projecting must NOT double the count (the old
  // accumulate-delta logic inflated this on every nightly run).
  assert.strictEqual(a[0][3], 2);
});

test('metrics projection: most-recent classification wins for a re-bucketed scenario', () => {
  const rows = [
    row(100, '2026-05-25 10:00', 'flaky', 'unclassified'),
    row(101, '2026-05-26 10:00', 'flaky', 'N: queue drain'),
  ];
  const out = computeMetricsFromFailures(rows);
  assert.strictEqual(out[0][1], 'N: queue drain');
});

test('metrics projection: rows sorted by failCount desc', () => {
  const rows = [
    row(100, '2026-05-25 10:00', 'rare', 'X'),
    row(100, '2026-05-25 10:00', 'common', 'Y'),
    row(101, '2026-05-26 10:00', 'common', 'Y'),
  ];
  const out = computeMetricsFromFailures(rows);
  assert.strictEqual(out[0][0], 'common');
});
