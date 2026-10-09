#!/usr/bin/env node
'use strict';

/*
 * Duration-balanced Playwright sharding.
 *
 * Playwright's own --shard splits by test-file count, so with very different spec durations
 * one shard can take twice as long as another. This runner splits by MEASURED duration instead:
 * every shard independently computes the same assignment (longest spec first, onto the currently
 * least-loaded shard), so each spec file runs in exactly one shard.
 *
 * Usage (from the suite directory, e.g. /app in the test image):
 *   node playwright-run-shard.js <durations.json> <command...>
 *     e.g. node playwright-run-shard.js shard-durations.json npx playwright test
 *   - PLAYWRIGHT_SHARD=<i>/<n> (e.g. 2/6) selects the shard; without it the command runs unchanged (all tests).
 *   - <durations.json> maps a spec file (path relative to the Playwright testDir, as in the JUnit report)
 *     to its measured duration in seconds. Specs missing from it count with the median duration.
 *
 *   node playwright-run-shard.js --update-durations <out.json> <junit.xml...>
 *     Rebuilds <out.json> from Playwright JUnit reports (average of all occurrences per spec file).
 */

const { spawnSync } = require('child_process');
const fs = require('fs');

function parseShard(value) {
  const m = /^(\d+)\/(\d+)$/.exec(String(value || '').trim());
  if (!m) return null;
  const index = Number(m[1]);
  const total = Number(m[2]);
  if (total < 1 || index < 1 || index > total) throw new Error(`invalid PLAYWRIGHT_SHARD "${value}"`);
  return { index, total };
}

function median(values) {
  if (values.length === 0) return 60;
  const sorted = [...values].sort((a, b) => a - b);
  const mid = Math.floor(sorted.length / 2);
  return sorted.length % 2 ? sorted[mid] : (sorted[mid - 1] + sorted[mid]) / 2;
}

/**
 * Deterministic longest-processing-time-first assignment.
 * @returns {string[][]} one sorted file list per shard (index 0 = shard 1)
 */
function assignShards(files, durations, total) {
  const fallback = median(Object.values(durations));
  const weighted = [...new Set(files)]
    .map((file) => ({ file, seconds: typeof durations[file] === 'number' ? durations[file] : fallback }))
    .sort((a, b) => b.seconds - a.seconds || (a.file < b.file ? -1 : a.file > b.file ? 1 : 0));
  const load = new Array(total).fill(0);
  const shards = Array.from({ length: total }, () => []);
  for (const { file, seconds } of weighted) {
    let target = 0;
    for (let i = 1; i < total; i++) if (load[i] < load[target]) target = i;
    shards[target].push(file);
    load[target] += seconds;
  }
  return shards.map((list) => list.sort());
}

function escapeRegExp(s) {
  return s.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

// Playwright treats positional arguments as regular expressions matched against the file path.
function fileFilter(file) {
  return `/${escapeRegExp(file)}$`;
}

function listFiles(command, extraArgs) {
  const res = spawnSync(command[0], [...command.slice(1), '--list', '--reporter=json', ...extraArgs], {
    encoding: 'utf8',
    maxBuffer: 256 * 1024 * 1024,
  });
  if (res.status !== 0) {
    process.stderr.write(res.stderr || '');
    throw new Error(`listing the tests failed (exit ${res.status})`);
  }
  const json = JSON.parse(res.stdout.slice(res.stdout.indexOf('{'), res.stdout.lastIndexOf('}') + 1));
  return [...new Set((json.suites || []).map((s) => s.file))].sort();
}

function updateDurations(outFile, xmlFiles) {
  const sums = {};
  for (const xml of xmlFiles) {
    const content = fs.readFileSync(xml, 'utf8');
    for (const m of content.matchAll(/<testsuite\s[^>]*?name="([^"]+)"[^>]*?\stime="([\d.]+)"/g)) {
      (sums[m[1]] = sums[m[1]] || []).push(Number(m[2]));
    }
  }
  const out = {};
  for (const file of Object.keys(sums).sort()) {
    const values = sums[file];
    out[file] = Math.round((values.reduce((a, b) => a + b, 0) / values.length) * 10) / 10;
  }
  fs.writeFileSync(outFile, JSON.stringify(out, null, 1) + '\n');
  console.log(`wrote ${Object.keys(out).length} spec durations to ${outFile}`);
}

function main(argv) {
  if (argv[0] === '--update-durations') {
    updateDurations(argv[1], argv.slice(2));
    return 0;
  }
  const [durationsFile, ...command] = argv;
  if (!durationsFile || command.length === 0) {
    console.error('usage: playwright-run-shard.js <durations.json> <command...>');
    return 2;
  }
  const shard = parseShard(process.env.PLAYWRIGHT_SHARD);
  let args = [];
  if (shard) {
    const durations = fs.existsSync(durationsFile) ? JSON.parse(fs.readFileSync(durationsFile, 'utf8')) : {};
    const allFiles = listFiles(command, []);
    const mine = assignShards(allFiles, durations, shard.total)[shard.index - 1];
    const fallback = median(Object.values(durations));
    const estimate = mine.reduce((sum, f) => sum + (durations[f] ?? fallback), 0);
    console.log(`[shard ${shard.index}/${shard.total}] ${mine.length} of ${allFiles.length} spec files, estimated ${(estimate / 60).toFixed(1)} min`);
    if (mine.length === 0) {
      console.log('[shard] no spec files assigned to this shard');
      return 0;
    }
    args = mine.map(fileFilter);
    const matched = listFiles(command, args);
    if (JSON.stringify(matched) !== JSON.stringify(mine)) {
      console.error(`[shard] file filters do not select exactly the assigned files.\nassigned: ${mine.join(' ')}\nmatched:  ${matched.join(' ')}`);
      return 2;
    }
  }
  const res = spawnSync(command[0], [...command.slice(1), ...args], { stdio: 'inherit' });
  return res.status === null ? 1 : res.status;
}

if (require.main === module) {
  process.exit(main(process.argv.slice(2)));
}

module.exports = { assignShards, parseShard, fileFilter, median };
