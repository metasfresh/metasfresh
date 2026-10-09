'use strict';

const test = require('node:test');
const assert = require('node:assert');
const { buildRunUrl, isTrackedJunitArtifact } = require('../lib/gh');

const BASE = 'https://github.com/metasfresh/metasfresh/actions/runs/26543609110';

test('buildRunUrl: pins to the attempt (even attempt 1)', () => {
  assert.strictEqual(buildRunUrl(BASE, 1), `${BASE}/attempts/1`);
  assert.strictEqual(buildRunUrl(BASE, 2), `${BASE}/attempts/2`);
});

test('buildRunUrl: coerces string attempt', () => {
  assert.strictEqual(buildRunUrl(BASE, '3'), `${BASE}/attempts/3`);
});

test('buildRunUrl: falls back to bare URL when attempt is missing/invalid', () => {
  assert.strictEqual(buildRunUrl(BASE, undefined), BASE);
  assert.strictEqual(buildRunUrl(BASE, 0), BASE);
  assert.strictEqual(buildRunUrl(BASE, 'x'), BASE);
});

test('buildRunUrl: tolerates empty base', () => {
  assert.strictEqual(buildRunUrl('', 2), '');
});

test('isTrackedJunitArtifact: any number of Playwright shards, mobile and frontend', () => {
  for (let s = 1; s <= 12; s++) {
    assert.ok(isTrackedJunitArtifact(`junit-results-playwright-mobile-shard${s}`), `mobile shard${s}`);
    assert.ok(isTrackedJunitArtifact(`junit-results-playwright-frontend-shard${s}`), `frontend shard${s}`);
  }
  // runs from before mobile sharding still carry the unsharded name
  assert.ok(isTrackedJunitArtifact('junit-results-playwright-mobile'));
});

test('isTrackedJunitArtifact: any number of cucumber profiles + catchall', () => {
  for (let p = 1; p <= 12; p++) {
    assert.ok(isTrackedJunitArtifact(`junit-results-cucumber-profile${p}`), `profile${p}`);
  }
  assert.ok(isTrackedJunitArtifact('junit-results-cucumber-catchall'));
});

test('isTrackedJunitArtifact: same families as before, nothing else', () => {
  for (const name of [
    'junit-results-jest',
    'junit-results-backend',
    'junit-results-camel',
    'junit-results-cucumber-report',
    'junit-results-playwright-mobile-shard',
    'allure-results-mobile-shard1',
    'playwright-frontend-report-shard1',
  ]) {
    assert.ok(!isTrackedJunitArtifact(name), name);
  }
});
