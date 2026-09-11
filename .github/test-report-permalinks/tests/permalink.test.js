// tests/permalink.test.js
const assert = require('assert');
const P = require('../permalink.js');

const entry = { 'cucumber': { uid: 'a'.repeat(32), count: 3 },
                'frontend-webui': { uid: 'b'.repeat(32), count: 9 } };

// chooseSuite picks the suite with the most tests
assert.strictEqual(P.chooseSuite(entry), 'frontend-webui');

// feature -> #behaviors on the default (max-count) suite
assert.strictEqual(
  P.buildRedirectUrl('5.175-x.42', 'feature', entry),
  'builds/5.175-x.42/allure/frontend-webui/index.html#behaviors/' + 'b'.repeat(32));

// explicit suite override
assert.strictEqual(
  P.buildRedirectUrl('5.175-x.42', 'feature', entry, 'cucumber'),
  'builds/5.175-x.42/allure/cucumber/index.html#behaviors/' + 'a'.repeat(32));

// spec -> #suites
const specEntry = { 'mobile-webui': { uid: 'c'.repeat(32), count: 2 } };
assert.strictEqual(
  P.buildRedirectUrl('v1', 'spec', specEntry),
  'builds/v1/allure/mobile-webui/index.html#suites/' + 'c'.repeat(32));

// explicit suite override for a suite NOT in the entry -> null (shows "not found")
assert.strictEqual(
  P.buildRedirectUrl('v1', 'feature', { 'cucumber': { uid: 'a'.repeat(32), count: 1 } }, 'frontend-webui'),
  null);

// misses
assert.strictEqual(P.buildRedirectUrl('v1', 'feature', null), null);
assert.strictEqual(P.lookup({features:{F1:entry}, specs:{}}, 'feature', 'F1'), entry);
assert.strictEqual(P.lookup({features:{}, specs:{}}, 'feature', 'nope'), null);

// suitesByCount: list a feature/spec's suites, highest test count first (for the multi-suite chooser)
const multi = { 'cucumber': { uid: 'a'.repeat(32), count: 15 },
                'mobile-webui': { uid: 'b'.repeat(32), count: 22 },
                'frontend-webui': { uid: 'c'.repeat(32), count: 1 } };
const by = P.suitesByCount(multi);
assert.strictEqual(by.length, 3);
assert.strictEqual(by[0].suite, 'mobile-webui');       // highest count first
assert.strictEqual(by[0].count, 22);
assert.strictEqual(by[0].uid, 'b'.repeat(32));
assert.strictEqual(by[2].suite, 'frontend-webui');     // lowest count last
assert.strictEqual(P.suitesByCount(entry)[0].suite, 'frontend-webui'); // count 9 > 3
assert.deepStrictEqual(P.suitesByCount({}), []);
assert.deepStrictEqual(P.suitesByCount(null), []);
// an empty entry resolves to no URL -> the page falls to the not-found path (never a 0-item chooser)
assert.strictEqual(P.buildRedirectUrl('v1', 'feature', {}), null);
// the default suite (chooseSuite) is always suitesByCount()[0]
assert.strictEqual(P.suitesByCount(multi)[0].suite, P.chooseSuite(multi));

console.log('OK');

// --- a suite a feature reaches only by TAG has no Behaviours node to link at ---

const tagOnly = {
  'cucumber': { uid: 'a'.repeat(32), count: 1, tagged: 115 },
  'frontend-webui': { uid: null, count: 0, tagged: 5 },
};

// the unlinkable suite is never chosen, however many tests carry the tag
assert.strictEqual(P.chooseSuite(tagOnly), 'cucumber');

// asking for it explicitly yields null rather than a dead .../#behaviors/null link
assert.strictEqual(P.buildRedirectUrl('v1', 'feature', tagOnly, 'frontend-webui'), null);

// a feature reachable ONLY by tag has nothing to link at, in any suite
assert.strictEqual(
  P.buildRedirectUrl('v1', 'feature', { 'frontend-webui': { uid: null, count: 0, tagged: 5 } }),
  null);

// suitesByCount surfaces `tagged` so the page can say "1 shown of 115 tagged",
// and orders linkable suites first even when an unlinkable one is far larger
const ordered = P.suitesByCount(tagOnly);
assert.deepStrictEqual(ordered.map(function (s) { return s.suite; }),
  ['cucumber', 'frontend-webui']);
assert.strictEqual(ordered[0].tagged, 115);
assert.strictEqual(ordered[1].uid, null);

// an entry predating this change (no `tagged` key) still reports 0, not undefined
assert.strictEqual(P.suitesByCount({ 'cucumber': { uid: 'a'.repeat(32), count: 3 } })[0].tagged, 0);

// isLinkable is the single predicate both call sites use
assert.strictEqual(P.isLinkable({ uid: 'a'.repeat(32) }), true);
assert.strictEqual(P.isLinkable({ uid: null }), false);
assert.strictEqual(P.isLinkable(undefined), false);

console.log('permalink.test.js: all assertions passed');

// chooseSuite must skip an unlinkable suite even when it reports MORE tests.
// Today the generator only ever pairs uid:null with count:0, so a count-based
// comparison alone would happen to pick right — this pins the guard against a
// future generator that emits a count for a node-less suite.
assert.strictEqual(
  P.chooseSuite({ 'cucumber': { uid: 'a'.repeat(32), count: 1, tagged: 1 },
                  'frontend-webui': { uid: null, count: 99, tagged: 99 } }),
  'cucumber');
