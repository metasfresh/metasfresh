'use strict';

// Root-cause bucketing — the "minimum of analysis" layer.
//
// Each failure record (from parse-junit.js) is matched against an ORDERED list
// of rules. First match wins. A rule looks at the exception type, the failure
// message/detail, and the scenario/spec name. The buckets mirror the manual
// flake-audit taxonomy maintained in ai-work/flaky/ (buckets A..M + generic
// fallbacks), so the sheet stays comparable with the hand-written audits.
//
// HOW TO ADD A BUCKET: append a rule object to RULES. Keep the most specific
// rules near the top; the generic fallbacks at the bottom catch everything else
// so nothing is ever "unbucketed" silently. When a new pattern appears in CI,
// add a rule here rather than re-classifying by hand — that is the whole point
// of this tool.
//
// A rule: { id, label, match(f) -> boolean }
//   f = { testType, suite, classname, name, fullName, exceptionType, message, detail }

const has = (s, needle) => (s || '').toLowerCase().includes(needle.toLowerCase());
const hasAny = (f, needle) =>
  has(f.message, needle) || has(f.detail, needle) || has(f.exceptionType, needle);

const RULES = [
  // ---- Backend / cucumber, real-bug buckets ---------------------------------
  {
    id: 'E',
    label: 'E: m_cost deadlock on voidCosts (mf15#4160)',
    match: (f) =>
      has(f.exceptionType, 'DBDeadLockDetectedException') &&
      (hasAny(f, 'm_cost') || hasAny(f, 'M_Cost')),
  },
  {
    id: 'I',
    label: 'I: ShipmentScheduleEnqueuer lock-transfer race',
    match: (f) =>
      has(f.exceptionType, 'LockFailedException') &&
      hasAny(f, 'ShipmentScheduleEnqueuer'),
  },
  {
    id: 'I-downstream',
    label: 'I-downstream: shipment not ready ("keine Lieferungen")',
    match: (f) =>
      hasAny(f, 'Keine Lieferungen') || hasAny(f, 'no shipments can be created'),
  },
  {
    id: 'J',
    label: 'J: Parent PO reference expired',
    match: (f) => hasAny(f, 'Parent PO reference expired'),
  },
  {
    id: 'K',
    label: 'K: EDI Desadv pack element missing',
    match: (f) =>
      hasAny(f, 'EDI_Exp_Desadv_Pack') ||
      (has(f.fullName, 'EDI') && has(f.exceptionType, 'AssertionError') && hasAny(f, 'Desadv')),
  },
  {
    id: 'F',
    label: 'F: invoice-candidate REST response item missing',
    match: (f) =>
      has(f.fullName, 'invoice candidate status') && hasAny(f, 'not to be null'),
  },
  {
    id: 'G',
    label: 'G: productionCandidate qty timing',
    match: (f) => has(f.fullName, 'productionCandidate') || has(f.fullName, 'Production candidate'),
  },
  {
    id: 'N',
    label: 'N: material/async queue drain timeout (5 min)',
    // "Queue has not been entirely processed in 5 minutes !" — a wait-step
    // timing out waiting for a rabbitMQ queue (often de.metas.material) to drain.
    // First seen 2026-05-28 on "Shipment schedule export rest-api" (profile7),
    // deterministic across runs — distinct from the bucket-A JVM-crash cascade.
    match: (f) => hasAny(f, 'Queue has not been entirely processed'),
  },

  // ---- Backend / cucumber, infra buckets ------------------------------------
  {
    id: 'A',
    label: 'A: run failed, 0 test failures parsed (JVM cascade / build / infra)',
    // Synthesised at the run level in extract.js when a failed run produced no
    // parsed test-level failure (cucumber "Tests run: 0" cascade, or a build /
    // infra failure with no test artifacts). Keeps the failed run visible.
    match: (f) => f.exceptionType === '__TESTS_RUN_ZERO__',
  },
  {
    id: 'H',
    label: 'H: SMTP relay auth regression (pre-MailPit)',
    match: (f) => hasAny(f, '535 5.7.8') || hasAny(f, 'Invalid Username/Password'),
  },

  // ---- Mobile playwright buckets --------------------------------------------
  {
    id: 'C',
    label: 'C: mobile humanager Bulk-Move screen timeout',
    match: (f) =>
      has(f.fullName, 'huManager') &&
      (hasAny(f, 'Bulk actions - Move') || hasAny(f, 'ApplicationsListScreen') || hasAny(f, 'HOME - Wait for screen')),
  },
  {
    id: 'B',
    label: 'B: mobile picking invalid-QR recover timeout',
    match: (f) => has(f.fullName, 'picking.spec.js') && hasAny(f, 'invalid HU QR'),
  },
  {
    id: 'B-residual',
    label: 'B-residual: mobile workplace2 picking job start (PR 24044 residual)',
    match: (f) => has(f.fullName, 'pick_what_was_scheduled_to_workplace'),
  },
  {
    id: 'L',
    label: 'L: mobile close_partially_shipped (team-dropped target)',
    match: (f) => has(f.fullName, 'close_partially_shipped'),
  },
  {
    id: 'M',
    label: 'M: mobile productBasedPicking partial pick',
    match: (f) => has(f.fullName, 'productBasedPicking'),
  },

  // ---- Generic fallbacks (always classify something) ------------------------
  {
    id: 'deadlock-other',
    label: 'deadlock (other relation)',
    match: (f) => has(f.exceptionType, 'DeadLock'),
  },
  {
    id: 'lock-other',
    label: 'lock failure (other)',
    match: (f) => has(f.exceptionType, 'LockFailedException'),
  },
  {
    id: 'timeout',
    label: 'timeout (unclassified)',
    match: (f) => has(f.exceptionType, 'Timeout') || hasAny(f, 'TimeoutError') || hasAny(f, 'Timeout ') ,
  },
  {
    id: 'assertion',
    label: 'assertion (unclassified)',
    // Matches AssertionError AND metasfresh's AssertionWithContextInfoError,
    // plus the playwright reporter's bare type="FAILURE".
    match: (f) => has(f.exceptionType, 'Assertion') || has(f.exceptionType, 'FAILURE'),
  },
  {
    id: 'unclassified',
    label: 'unclassified',
    match: () => true,
  },
];

function bucketize(failure) {
  for (const rule of RULES) {
    if (rule.match(failure)) {
      return { bucketId: rule.id, bucketLabel: rule.label };
    }
  }
  // RULES ends with a catch-all, so this is unreachable, but be defensive.
  return { bucketId: 'unclassified', bucketLabel: 'unclassified' };
}

module.exports = { bucketize, RULES };
