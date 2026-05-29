'use strict';

const test = require('node:test');
const assert = require('node:assert');
const { computeMetricsFromFailures } = require('../lib/sheets');

// Failures row layout: [Key, Run, Branch, Date, Commit, TestType, Profile, Scenario, Bucket, Exception, Message]
// Metrics row layout:  [Branch, Scenario, Bucket, TestType, FailCount, First, Last, LastRun]
const row = (runId, date, scenario, bucket, branch = 'new_dawn_uat', testType = 'cucumber') => [
  `${runId}::${scenario}`, `https://x/runs/${runId}`, branch, date, 'abc123', testType, 'cucumber/profile1', scenario, bucket, 'Exc', 'msg',
];

test('metrics projection: counts rows + distinct runs per scenario', () => {
  const rows = [
    row(100, '2026-05-25 10:00', 'TC5b', 'E'),
    row(101, '2026-05-26 10:00', 'TC5b', 'E'),
    row(101, '2026-05-26 10:00', 'EDI export', 'K'),
  ];
  const out = computeMetricsFromFailures(rows);
  const tc5b = out.find((r) => r[1] === 'TC5b');
  assert.strictEqual(tc5b[0], 'new_dawn_uat', 'branch column');
  assert.strictEqual(tc5b[4], 2, 'failCount = 2 rows (= distinct runs, by key design)');
  assert.strictEqual(tc5b[5], '2026-05-25 10:00', 'firstFailed = earliest');
  assert.strictEqual(tc5b[6], '2026-05-26 10:00', 'lastFailed = latest');
});

test('metrics projection: same scenario on two branches stays as two rows', () => {
  const rows = [
    row(100, '2026-05-25 10:00', 'TC5b', 'E', 'new_dawn_uat'),
    row(200, '2026-05-26 10:00', 'TC5b', 'E', 'soft_panda_hotfix'),
  ];
  const out = computeMetricsFromFailures(rows);
  assert.strictEqual(out.length, 2, 'one row per (branch, scenario)');
  assert.deepStrictEqual(out.map((r) => r[0]).sort(), ['new_dawn_uat', 'soft_panda_hotfix']);
});

test('metrics projection: idempotent — same input twice yields identical output', () => {
  const rows = [row(100, '2026-05-25 10:00', 'TC5b', 'E'), row(101, '2026-05-26 10:00', 'TC5b', 'E')];
  const a = computeMetricsFromFailures(rows);
  const b = computeMetricsFromFailures(rows);
  assert.deepStrictEqual(a, b);
  // Critical-fix guard: re-projecting must NOT double the count (the old
  // accumulate-delta logic inflated this on every nightly run).
  assert.strictEqual(a[0][4], 2);
});

test('metrics projection: most-recent classification wins for a re-bucketed scenario', () => {
  const rows = [
    row(100, '2026-05-25 10:00', 'flaky', 'unclassified'),
    row(101, '2026-05-26 10:00', 'flaky', 'N: queue drain'),
  ];
  const out = computeMetricsFromFailures(rows);
  assert.strictEqual(out[0][2], 'N: queue drain');
});

test('metrics projection: rows sorted by failCount desc', () => {
  const rows = [
    row(100, '2026-05-25 10:00', 'rare', 'X'),
    row(100, '2026-05-25 10:00', 'common', 'Y'),
    row(101, '2026-05-26 10:00', 'common', 'Y'),
  ];
  const out = computeMetricsFromFailures(rows);
  assert.strictEqual(out[0][1], 'common');
});
