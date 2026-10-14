'use strict';

const test = require('node:test');
const assert = require('node:assert');
const { buildRunUrl, junitArtifactNames } = require('../lib/gh');

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

test('junitArtifactNames: mobile shards + legacy name', () => {
  const names = junitArtifactNames();
  for (let s = 1; s <= 3; s++) {
    assert.ok(names.includes(`junit-results-playwright-mobile-shard${s}`), `missing mobile shard${s}`);
  }
  // runs from before mobile sharding still carry the unsharded name
  assert.ok(names.includes('junit-results-playwright-mobile'));
});
