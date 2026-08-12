'use strict';

const test = require('node:test');
const assert = require('node:assert');
const path = require('path');
const { parseJUnitFile } = require('../lib/parse-junit');
const { bucketize } = require('../lib/bucketize');

const FIX = path.join(__dirname, '..', 'fixtures');

function bucketsOf(file) {
  const { failures } = parseJUnitFile(path.join(FIX, file));
  return failures.map((f) => ({ name: f.fullName, ...bucketize(f) }));
}

test('m_cost deadlock -> bucket E', () => {
  const b = bucketsOf('cucumber-deadlock-profile1.xml');
  assert.strictEqual(b.length, 1);
  assert.strictEqual(b[0].bucketId, 'E');
});

test('EDI Desadv pack missing -> bucket K', () => {
  const b = bucketsOf('cucumber-edi-profile5.xml');
  assert.strictEqual(b[0].bucketId, 'K');
});

test('mobile 3-fail run -> L, B-residual, M', () => {
  const b = bucketsOf('mobile-3fails.xml');
  const ids = b.map((x) => x.bucketId).sort();
  assert.deepStrictEqual(ids, ['B-residual', 'L', 'M']);
});

test('mobile huManager -> bucket C', () => {
  const b = bucketsOf('mobile-humanager.xml');
  assert.strictEqual(b[0].bucketId, 'C');
});

// Synthetic records to lock the rule table against regression.
test('synthetic: ShipmentScheduleEnqueuer lock -> bucket I', () => {
  const { bucketId } = bucketize({
    exceptionType: 'de.metas.lock.exceptions.LockFailedException',
    message: 'Record ref{M_ShipmentSchedule/1000022} ... ShipmentScheduleEnqueuer_1000361',
    detail: '', fullName: 'EDI INVOIC export :: x', testType: 'cucumber',
  });
  assert.strictEqual(bucketId, 'I');
});

test('synthetic: Parent PO reference expired -> bucket J', () => {
  const { bucketId } = bucketize({
    exceptionType: 'org.adempiere.exceptions.AdempiereException',
    message: 'Parent PO reference expired', detail: '',
    fullName: 'receipts :: Complete receipt similar with case 230', testType: 'cucumber',
  });
  assert.strictEqual(bucketId, 'J');
});

test('synthetic: SMTP auth regression -> bucket H', () => {
  const { bucketId } = bucketize({
    exceptionType: 'javax.mail.AuthenticationFailedException',
    message: '535 5.7.8 Invalid Username/Password', detail: '',
    fullName: 'mail :: send', testType: 'cucumber',
  });
  assert.strictEqual(bucketId, 'H');
});

test('synthetic: queue-not-processed -> bucket N', () => {
  const { bucketId } = bucketize({
    exceptionType: 'org.adempiere.exceptions.AdempiereException',
    message: 'Queue has not been entirely processed in 5 minutes !', detail: '',
    fullName: 'Shipment schedule export rest-api :: Export oxid shipment candidate',
    testType: 'cucumber',
  });
  assert.strictEqual(bucketId, 'N');
});

test('synthetic: AssertionWithContextInfoError -> bucket assertion (not unclassified)', () => {
  const { bucketId } = bucketize({
    exceptionType: 'de.metas.cucumber.stepdefs.context.SharedTestContext$AssertionWithContextInfoError',
    message: '[DueAmt] expected: 20596.32 but was: 68654.4', detail: '',
    fullName: 'Split-payment :: S4 - Payment reversal rolls LC balance', testType: 'cucumber',
  });
  assert.strictEqual(bucketId, 'assertion');
});

test('synthetic: __TESTS_RUN_ZERO__ marker -> bucket A (cascade / no-parsed-failure)', () => {
  const { bucketId } = bucketize({
    exceptionType: '__TESTS_RUN_ZERO__',
    message: 'Run conclusion=failure but no JUnit artifact reported a failed testcase.',
    detail: '', fullName: '(run failed — no test-level failure parsed)', testType: 'unknown',
  });
  assert.strictEqual(bucketId, 'A');
});

test('synthetic: unknown failure -> unclassified (never silently dropped)', () => {
  const { bucketId } = bucketize({
    exceptionType: 'java.lang.IllegalStateException',
    message: 'something totally new', detail: '',
    fullName: 'foo :: bar', testType: 'cucumber',
  });
  assert.strictEqual(bucketId, 'unclassified');
});
