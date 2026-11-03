'use strict';

const test = require('node:test');
const assert = require('node:assert');
const { computeMetricsFromFailures, reattachAnnotations, METRICS_HEADER } = require('../lib/sheets');

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

// --- reattachAnnotations: manual annotation columns survive the recompute ---

// A freshly-computed metric row (8 owned columns) for (branch, scenario).
const metric = (branch, scenario, failCount = 1) =>
  [branch, scenario, 'E', 'cucumber', failCount, '2026-05-25', '2026-05-26', 'https://x/runs/1'];

// A prior Metrics sheet: header (owned + annotation headers) then data rows.
const priorSheet = (annHeaders, dataRows) => [[...METRICS_HEADER, ...annHeaders], ...dataRows];

test('reattach: no annotation columns yet → rows pass through unchanged', () => {
  const rows = [metric('new_dawn_uat', 'A')];
  const { rows: out, annWidth } = reattachAnnotations(rows, priorSheet([], []));
  assert.strictEqual(annWidth, 0);
  assert.deepStrictEqual(out, rows);
});

test('reattach: empty prior sheet → rows pass through unchanged', () => {
  const rows = [metric('new_dawn_uat', 'A')];
  const { rows: out, annWidth } = reattachAnnotations(rows, []);
  assert.strictEqual(annWidth, 0);
  assert.deepStrictEqual(out, rows);
});

test('reattach: annotation follows its scenario across a re-sort', () => {
  // Prior order: A then B. New recompute sorts B first (higher count).
  const prior = priorSheet(['Fixing PR'], [
    [...metric('new_dawn_uat', 'A'), 'PR-A'],
    [...metric('new_dawn_uat', 'B'), 'PR-B'],
  ]);
  const newRows = [metric('new_dawn_uat', 'B', 9), metric('new_dawn_uat', 'A', 2)];
  const { rows: out, annWidth } = reattachAnnotations(newRows, prior);
  assert.strictEqual(annWidth, 1);
  assert.strictEqual(out[0][1], 'B');
  assert.strictEqual(out[0][METRICS_HEADER.length], 'PR-B', 'B keeps PR-B after re-sort');
  assert.strictEqual(out[1][1], 'A');
  assert.strictEqual(out[1][METRICS_HEADER.length], 'PR-A', 'A keeps PR-A after re-sort');
});

test('reattach: a new scenario gets a blank annotation cell', () => {
  const prior = priorSheet(['Fixing PR'], [[...metric('new_dawn_uat', 'A'), 'PR-A']]);
  const newRows = [metric('new_dawn_uat', 'A'), metric('new_dawn_uat', 'NEW')];
  const { rows: out } = reattachAnnotations(newRows, prior);
  const a = out.find((r) => r[1] === 'A');
  const n = out.find((r) => r[1] === 'NEW');
  assert.strictEqual(a[METRICS_HEADER.length], 'PR-A');
  assert.strictEqual(n[METRICS_HEADER.length], '', 'new scenario → blank, never inherits another row');
});

test('reattach: same scenario on two branches keeps separate annotations', () => {
  const prior = priorSheet(['Fixing PR'], [
    [...metric('new_dawn_uat', 'X'), 'PR-nd'],
    [...metric('soft_panda_hotfix', 'X'), 'PR-sp'],
  ]);
  const newRows = [metric('soft_panda_hotfix', 'X'), metric('new_dawn_uat', 'X')];
  const { rows: out } = reattachAnnotations(newRows, prior);
  assert.strictEqual(out.find((r) => r[0] === 'new_dawn_uat')[METRICS_HEADER.length], 'PR-nd');
  assert.strictEqual(out.find((r) => r[0] === 'soft_panda_hotfix')[METRICS_HEADER.length], 'PR-sp');
});

test('reattach: multiple annotation columns are all preserved in order', () => {
  const prior = priorSheet(['Fixing PR', 'Notes'], [
    [...metric('new_dawn_uat', 'A'), 'PR-A', 'note-A'],
  ]);
  const { rows: out, annWidth } = reattachAnnotations([metric('new_dawn_uat', 'A')], prior);
  assert.strictEqual(annWidth, 2);
  assert.deepStrictEqual(out[0].slice(METRICS_HEADER.length), ['PR-A', 'note-A']);
});
