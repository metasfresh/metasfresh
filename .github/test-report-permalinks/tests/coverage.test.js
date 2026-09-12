// tests/coverage.test.js — the per-branch coverage page's pure logic.
const assert = require('assert');
const C = require('../coverage.js');

// --- formatResult: the rows worth acting on must stand out -----------------
assert.strictEqual(C.formatResult({ passed: 3 }), 'passed');
assert.strictEqual(C.formatResult({}), 'unknown');
assert.strictEqual(C.formatResult({ passed: 2, failed: 1 }), '1 failed');
assert.strictEqual(C.formatResult({ broken: 1, failed: 2 }), '1 broken, 2 failed');
// a feature with NO passing test must never read "passed"
assert.strictEqual(C.formatResult({ failed: 4 }), '4 failed');

const cov = {
  suites: {
    cucumber: { state: 'measured', ran: 1394, tests: 1394, labelled: 1278 },
    'mobile-webui': { state: 'measured', ran: 377, tests: 377, labelled: 363 },
    'junit/backend': { state: 'absent', ran: 11077, tests: null, labelled: null },
  },
  features: {
    F00230: { cucumber: { tests: 23, status: { passed: 23 } },
              'mobile-webui': { tests: 164, status: { passed: 164 } } },
    F00100: { cucumber: { tests: 39, status: { passed: 38, failed: 1 } } },
  },
};

// --- featureRows: totals sum ACROSS suites, statuses merge ----------------
const rows = C.featureRows(cov);
assert.strictEqual(rows.length, 2);
assert.strictEqual(rows[0].feature, 'F00230');        // ordered by test count
assert.strictEqual(rows[0].tests, 187);               // 23 + 164, not one suite's
assert.deepStrictEqual(rows[0].suites, ['cucumber', 'mobile-webui']);
assert.strictEqual(rows[1].result, '1 failed');

// --- summarise: an absent suite contributes no phantom totals -------------
const s = C.summarise(rows, cov);
assert.strictEqual(s.features, 2);
assert.strictEqual(s.fullyPassing, 1);
assert.strictEqual(s.notPassing, 1);
assert.strictEqual(s.notPassingRows[0].feature, 'F00100');
// junit/backend is `absent` with 11077 ran — it must NOT be added to the totals,
// or the page would claim feature-labelled coverage it does not have.
assert.strictEqual(s.tests, 1394 + 377);
assert.strictEqual(s.labelled, 1278 + 363);

// The guard above is only meaningful if an absent suite carrying REAL numbers is
// still excluded. Today the generator pairs `absent` with tests:null, so a fixture
// using null passes whether the guard exists or not — it pins nothing. This one
// fails the moment the state check is dropped.
const covLoud = {
  suites: {
    cucumber: { state: 'measured', ran: 10, tests: 10, labelled: 8 },
    'junit/backend': { state: 'absent', ran: 11077, tests: 11077, labelled: 11077 },
  },
  features: { F1: { cucumber: { tests: 1, status: { passed: 1 } } } },
};
const sLoud = C.summarise(C.featureRows(covLoud), covLoud);
assert.strictEqual(sLoud.tests, 10, 'an absent suite must not contribute tests');
assert.strictEqual(sLoud.labelled, 8, 'an absent suite must not contribute labelled tests');

// --- the link is branch-scoped, and both branch spellings work ------------
assert.strictEqual(C.permalinkFor('intensive-care-hotfix', 'F00230'),
  'branches/intensive-care-hotfix/permalink.html?feature=F00230');
assert.strictEqual(C.permalinkFor('x', 'F1'),
  'branches/x/permalink.html?feature=F1', 'branch-scoped, never build-scoped');
assert.strictEqual(C.normaliseBranch('intensive_care_hotfix'), 'intensive-care-hotfix');
assert.strictEqual(C.normaliseBranch('intensive-care-hotfix'), 'intensive-care-hotfix');
assert.strictEqual(C.normaliseBranch('  new_dawn_uat  '), 'new-dawn-uat');
assert.strictEqual(C.normaliseBranch(null), '');

// normaliseBranch must mirror cicd.yaml's sanitize-branch-for-gh-pages EXACTLY.
// Each case below is one stage of that pipeline; dropping any stage 404s on a
// branch the host published fine. Verified 2026-09-12 against the shell
// pipeline over all 142 branches the host holds plus 167 local git branch
// names: identical output on all 309.
assert.strictEqual(C.normaliseBranch('new_dawn_uat_CoveragePageAllBranches'),
  'new-dawn-uat-coveragepageallbranches',
  'lower-cases: EVERY feature branch here is {base}_{MixedCaseDescription}');
assert.strictEqual(C.normaliseBranch('deep_tundra_release_31039_ManufacturingReceiptGuard'),
  'deep-tundra-release-31039-manufacturingreceiptguard',
  'a real branch on the host; the underscore-only version 404s on it');
assert.strictEqual(C.normaliseBranch('merge/keen_hawk_release-to-new_dawn_uat'),
  'merge-keen-hawk-release-to-new-dawn-uat', 'slash is a separator, like underscore');
assert.strictEqual(C.normaliseBranch('release@2.0+rc1'), 'release2.0rc1',
  'tr -cd DROPS a disallowed char, it does not substitute a dash');
assert.strictEqual(C.normaliseBranch('feature/Foo__Bar'), 'feature-foo-bar',
  'runs of dashes collapse to one');
assert.strictEqual(C.normaliseBranch('--leading-and-trailing--'), 'leading-and-trailing',
  'leading and trailing dashes are stripped');
assert.strictEqual(C.normaliseBranch('___'), '', 'a name that sanitises to nothing yields nothing');

// --- a branch/feature name is URL-encoded into the href -------------------
assert.ok(C.permalinkFor('a/b', 'F1&x').indexOf('a%2Fb') !== -1);
assert.ok(C.permalinkFor('a/b', 'F1&x').indexOf('F1%26x') !== -1);

// --- an empty or missing coverage payload degrades, never throws ----------
assert.deepStrictEqual(C.featureRows(undefined), []);
assert.deepStrictEqual(C.featureRows({}), []);
assert.strictEqual(C.summarise([], {}).features, 0);

// --- a feature failing in TWO suites reports the SUM, not the last one -----
// The cross-suite merge in featureRows was unpinned: replacing `+=` with `=`
// survived the whole suite, because every fixture above has each status in one
// suite only. A feature red in two suites is exactly the row a reader acts on.
var covTwo = { suites: { cucumber: { state: 'measured', tests: 10, labelled: 10 },
                         'frontend-webui': { state: 'measured', tests: 10, labelled: 10 } },
  features: { F00700: { cucumber: { tests: 5, status: { passed: 3, failed: 2 } },
                        'frontend-webui': { tests: 4, status: { passed: 1, failed: 3 } } } } };
var rowTwo = C.featureRows(covTwo)[0];
assert.strictEqual(rowTwo.tests, 9, 'tests sum across suites');
assert.deepStrictEqual(rowTwo.status, { passed: 4, failed: 5 }, 'statuses SUM across suites');
assert.strictEqual(rowTwo.result, '5 failed', 'the merged count is what the reader sees');
assert.deepStrictEqual(rowTwo.suites, ['cucumber', 'frontend-webui']);

// Sorted, counted, and every non-passing status named -- pins the sort in
// formatResult, which insertion order alone would otherwise satisfy.
assert.strictEqual(C.formatResult({ passed: 1, failed: 2, broken: 1 }), '1 broken, 2 failed');
assert.strictEqual(C.formatResult({ failed: 2, broken: 1, passed: 1 }), '1 broken, 2 failed',
  'same result whatever order the statuses arrive in');

// --- a dropped suite is always announced, including when its total is 0 ----
// `reason` marks a suite dropped by failed reconciliation. Keying the warning
// off `ran` instead silently skipped the ran===0 case -- the loudest possible
// disagreement -- because 0 is falsy. Reproduced in a browser before the fix.
assert.deepStrictEqual(C.droppedSuites({ suites: {
  cucumber: { state: 'unknown', ran: 0, parsed: 1390,
              reason: 'parsed 1390 distinct test(s) but failures.json reports 0' } } }),
  ['cucumber'], 'a dropped suite with ran:0 must still be announced');
assert.deepStrictEqual(C.droppedSuites({ suites: {
  cucumber: { state: 'unknown', ran: 99, parsed: 1, reason: 'parsed 1 ... reports 99' },
  'mobile-webui': { state: 'measured', ran: 10, tests: 10, labelled: 8 } } }),
  ['cucumber']);
// `unknown` with NO reason is the never-ran case, not a drop: nothing was
// verified about it, so there is no under-count to warn about.
assert.deepStrictEqual(C.droppedSuites({ suites: {
  cucumber: { state: 'unknown', ran: null, tests: null, labelled: null } } }), [],
  'a suite that simply never ran is not a dropped suite');
assert.deepStrictEqual(C.droppedSuites({}), []);
assert.deepStrictEqual(C.droppedSuites(undefined), []);

// --- the page must keep loading the script by the EXACT tag the deploy inlines
// cicd.yaml replaces this byte string to ship one self-contained file. If the tag
// is reformatted, the deploy aborts inside a continue-on-error step: the job stays
// green and the published page silently stops updating. Fail here instead.
const fs = require('fs'), path = require('path');
const pageHtml = fs.readFileSync(path.join(__dirname, '..', 'coverage.html'), 'utf8');
assert.ok(pageHtml.indexOf('<script src="coverage.js"></script>') !== -1,
  'coverage.html must load coverage.js by the exact tag cicd.yaml inlines');
assert.strictEqual(pageHtml.split('<script src="coverage.js"></script>').length - 1, 1,
  'exactly one such tag, or the deploy would inline the script twice');
assert.ok(!/<\/script|<!--|<script/i.test(fs.readFileSync(path.join(__dirname, '..', 'coverage.js'), 'utf8')),
  'coverage.js must contain no sequence that can terminate or nest a script element once inlined');

console.log('coverage.test.js: all assertions passed');
