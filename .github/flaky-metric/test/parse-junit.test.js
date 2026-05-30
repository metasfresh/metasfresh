'use strict';

const test = require('node:test');
const assert = require('node:assert');
const path = require('path');
const { parseJUnitFile } = require('../lib/parse-junit');

const FIX = path.join(__dirname, '..', 'fixtures');

test('parses cucumber junit: detects the single m_cost deadlock failure', () => {
  const { testType, failures } = parseJUnitFile(path.join(FIX, 'cucumber-deadlock-profile1.xml'));
  assert.strictEqual(testType, 'cucumber');
  assert.strictEqual(failures.length, 1);
  const f = failures[0];
  assert.match(f.exceptionType, /DBDeadLockDetectedException/);
  assert.match(f.fullName, /Split-payment/);
  assert.match(f.message, /m_cost/);
});

test('parses cucumber junit: EDI Desadv assertion failure', () => {
  const { failures } = parseJUnitFile(path.join(FIX, 'cucumber-edi-profile5.xml'));
  assert.strictEqual(failures.length, 1);
  assert.match(failures[0].message, /EDI_Exp_Desadv_Pack/);
  assert.match(failures[0].exceptionType, /AssertionError/);
});

test('parses playwright mobile junit: 3 failures, spec names captured', () => {
  const { testType, failures } = parseJUnitFile(path.join(FIX, 'mobile-3fails.xml'));
  assert.strictEqual(testType, 'playwright');
  assert.strictEqual(failures.length, 3);
  const fullNames = failures.map((f) => f.fullName).join('\n');
  assert.match(fullNames, /close_partially_shipped/);
  assert.match(fullNames, /pick_what_was_scheduled_to_workplace/);
  assert.match(fullNames, /productBasedPicking/);
});

test('parses playwright mobile junit: single huManager failure', () => {
  const { failures } = parseJUnitFile(path.join(FIX, 'mobile-humanager.xml'));
  assert.strictEqual(failures.length, 1);
  assert.match(failures[0].fullName, /huManager/);
});
