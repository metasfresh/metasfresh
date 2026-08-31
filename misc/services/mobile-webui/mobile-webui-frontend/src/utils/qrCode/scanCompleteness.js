// How complete an in-progress scanned code is. Lets a keyboard/wedge reader keep waiting while a
// long multi-chunk QR code is still arriving, instead of flushing a fragment.
//
// The reader acts on each value as: COMPLETE_SCAN => force-complete now (no idle wait);
// PARTIAL_SCAN => hold back the idle flush; NOT_APPLICABLE => normal timing (Enter/idle as before).
//
// TERMINAL INVARIANT (per-format checks MUST honour it): return COMPLETE_SCAN ONLY when the code is
// unambiguously TERMINAL — there is NO way a continuation of the current string could produce a
// (different) valid scanned code. Because the reader force-completes on COMPLETE_SCAN, a
// non-terminal "looks complete now, but a longer string is also valid" format (e.g. a bare numeric
// m_hu_id where both "123" and "1234" parse) must NEVER return COMPLETE_SCAN — it uses
// PARTIAL_SCAN / NOT_APPLICABLE and relies on the Enter terminator / idle-flush instead.
//
// This enum lives in its own leaf module (imported by both ./common and ./hu) so the ENUM is not
// part of any import cycle. (./common and ./hu themselves DO still import each other; that cycle is
// kept safe by reading cross-module symbols only inside function bodies — see the note in ./common.)
export const ScanCompleteness = Object.freeze({
  NOT_APPLICABLE: 'NOT_APPLICABLE', // not a recognised streamed QR code => keep default behaviour
  PARTIAL_SCAN: 'PARTIAL_SCAN', //     a recognised QR code that is still arriving => keep waiting
  COMPLETE_SCAN: 'COMPLETE_SCAN', //   a recognised code that is complete AND terminal => flush now
});
