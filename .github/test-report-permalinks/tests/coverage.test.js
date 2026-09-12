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
assert.ok(C.permalinkFor('x', 'F1').indexOf('builds/') === -1, 'must not be build-scoped');
assert.strictEqual(C.normaliseBranch('intensive_care_hotfix'), 'intensive-care-hotfix');
assert.strictEqual(C.normaliseBranch('intensive-care-hotfix'), 'intensive-care-hotfix');
assert.strictEqual(C.normaliseBranch('  new_dawn_uat  '), 'new-dawn-uat');
assert.strictEqual(C.normaliseBranch(null), '');

// --- a branch/feature name is URL-encoded into the href -------------------
assert.ok(C.permalinkFor('a/b', 'F1&x').indexOf('a%2Fb') !== -1);
assert.ok(C.permalinkFor('a/b', 'F1&x').indexOf('F1%26x') !== -1);

// --- an empty or missing coverage payload degrades, never throws ----------
assert.deepStrictEqual(C.featureRows(undefined), []);
assert.deepStrictEqual(C.featureRows({}), []);
assert.strictEqual(C.summarise([], {}).features, 0);

console.log('coverage.test.js: all assertions passed');
