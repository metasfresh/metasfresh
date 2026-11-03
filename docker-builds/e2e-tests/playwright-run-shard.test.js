'use strict';

const test = require('node:test');
const assert = require('node:assert');
const fs = require('fs');
const os = require('os');
const path = require('path');
const { assignShards, parseShard, fileFilter, listFiles } = require('./playwright-run-shard');

const files = ['spec/a.spec.js', 'spec/b.spec.js', 'spec/c.spec.js', 'spec/d.spec.js', 'spec/e.spec.js', 'spec/new.spec.js'];
const durations = { 'spec/a.spec.js': 300, 'spec/b.spec.js': 200, 'spec/c.spec.js': 100, 'spec/d.spec.js': 100, 'spec/e.spec.js': 50 };

test('every file is assigned to exactly one shard', () => {
  for (const total of [1, 2, 3, 6, 10]) {
    const shards = assignShards(files, durations, total);
    assert.strictEqual(shards.length, total);
    assert.deepStrictEqual(shards.flat().sort(), [...files].sort());
  }
});

test('assignment is deterministic and independent of input order', () => {
  const reversed = [...files].reverse();
  assert.deepStrictEqual(assignShards(files, durations, 3), assignShards(reversed, durations, 3));
});

test('balances by duration, not by count', () => {
  // 850 s in total over 2 shards: the greedy split must end within one spec (100 s) of the even 425/425
  const shards = assignShards(files, durations, 2);
  const load = shards.map((list) => list.reduce((s, f) => s + (durations[f] ?? 100), 0));
  assert.ok(Math.abs(load[0] - load[1]) <= 100, `unbalanced: ${load}`);
});

test('unknown spec files count with the median duration', () => {
  const shards = assignShards(['spec/x.spec.js', 'spec/y.spec.js'], {}, 2);
  assert.deepStrictEqual(shards, [['spec/x.spec.js'], ['spec/y.spec.js']]);
});

test('parseShard', () => {
  assert.deepStrictEqual(parseShard('2/6'), { index: 2, total: 6 });
  assert.strictEqual(parseShard(''), null);
  assert.strictEqual(parseShard(undefined), null);
  assert.throws(() => parseShard('7/6'));
  assert.throws(() => parseShard('0/6'));
});

test('fileFilter escapes regex characters and anchors at a path segment', () => {
  const re = new RegExp(fileFilter('spec/a.spec.js'));
  assert.ok(re.test('/app/tests/spec/a.spec.js'));
  assert.ok(!re.test('/app/tests/spec/xa.spec.js'));
  assert.ok(!re.test('/app/tests/spec/aXspec.js'));
  assert.ok(!re.test('/app/tests/spec/a.spec.js.snap'));
});

test('listFiles reads the JSON report even when the config prints braces to stdout', () => {
  // stands in for `playwright test`: logs noise with braces to stdout, writes the report where the json reporter would
  const dir = fs.mkdtempSync(path.join(os.tmpdir(), 'pw-shard-'));
  const fake = path.join(dir, 'fake-playwright.js');
  fs.writeFileSync(fake, `
    console.log('global setup {config: loaded}');
    const report = { suites: [{ file: 'spec/b.spec.js' }, { file: 'spec/a.spec.js' }, { file: 'spec/a.spec.js' }] };
    const target = process.env.PLAYWRIGHT_JSON_OUTPUT_FILE;
    if (target) require('fs').writeFileSync(target, JSON.stringify(report)); else console.log(JSON.stringify(report));
    console.log('done }');
  `);
  assert.deepStrictEqual(listFiles([process.execPath, fake], []), ['spec/a.spec.js', 'spec/b.spec.js']);
});
