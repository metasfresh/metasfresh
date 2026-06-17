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

console.log('OK');
