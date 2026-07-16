'use strict';

const test = require('node:test');
const assert = require('node:assert');
const { buildRunUrl } = require('../lib/gh');

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
