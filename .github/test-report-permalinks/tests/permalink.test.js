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

// --- per-test deep links -----------------------------------------------------

// buildTestUrl targets Allure's single-test route, verified against the live
// report: .../index.html#testresult/<uid> opens that test's detail page.
assert.strictEqual(
  P.buildTestUrl('5.175-x.1', 'frontend-webui', '80b362963a825ee0'),
  'builds/5.175-x.1/allure/frontend-webui/index.html#testresult/80b362963a825ee0');
assert.strictEqual(P.buildTestUrl('v', 's', null), null, 'no uid -> no link');
assert.strictEqual(P.buildTestUrl(null, 's', 'u'), null, 'no version -> no link');

// isComplete decides whether a silent redirect is honest.
assert.strictEqual(P.isComplete({ uid: 'x', count: 5, tagged: 5 }), true);
assert.strictEqual(P.isComplete({ uid: 'x', count: 5, tagged: 0 }), true, 'node-only feature');
assert.strictEqual(P.isComplete({ uid: 'x', count: 1, tagged: 115 }), false,
  'F00700/cucumber: the node holds 1 of 115 tagged — redirecting there looks complete');
assert.strictEqual(P.isComplete({ uid: null, count: 0, tagged: 2 }), false);
assert.strictEqual(P.isComplete(null), false);

// suitesByCount carries the published test list through to the page.
const tagged = {
  'frontend-webui': { uid: null, count: 0, tagged: 2,
                      tests: [{ uid: 'u1', name: 'English UI', status: 'passed' },
                              { uid: 'u2', name: 'German UI', status: 'passed' }] },
  'cucumber': { uid: 'c'.repeat(32), count: 13, tagged: 6 }
};
const byCount = P.suitesByCount(tagged);
assert.strictEqual(byCount[0].suite, 'cucumber', 'linkable suites sort first');
assert.strictEqual(byCount[1].tests.length, 2, 'test list survives suitesByCount');
// An absent `tests` key must STAY absent through suitesByCount. Defaulting it
// to [] made "the key is present" true for every entry, so isComplete read them
// all as incomplete and no feature redirected to its node again.
assert.strictEqual(P.suitesByCount({ 'x': { uid: 'u', count: 1 } })[0].tests, undefined,
  'an absent test list must not be normalised into an empty one');
assert.deepStrictEqual(P.suitesByCount({ 'x': { uid: 'u', count: 1, tests: [] } })[0].tests, [],
  'an explicitly empty list is preserved — it means the node falls short');

// A tag-only suite still has no node link — the per-test links are the only route.
assert.strictEqual(P.buildRedirectUrl('v', 'feature', tagged, 'frontend-webui'), null);
assert.strictEqual(P.chooseSuite(tagged), 'cucumber');

// --- decideRoute: the whole routing matrix, previously untestable inline ------

const linkable  = { 'cucumber': { uid: 'c'.repeat(32), count: 3, tagged: 3 } };
const partial   = { 'cucumber': { uid: 'c'.repeat(32), count: 1, tagged: 115,
                                  attributed: 115, tests: [{ uid: 'u1', name: 'a' }] } };
const nodeless  = { 'frontend-webui': { uid: null, count: 0, tagged: 2, attributed: 2,
                                        tests: [{ uid: 'u1', name: 'EN' }, { uid: 'u2', name: 'DE' }] } };
const routeSpecEntry = { 'mobile-webui': { uid: 'd'.repeat(32), count: 4 } };

// an &suite= naming a suite this key has NO tests in must say "not found" — it
// used to render "has tests in 0 suites" over an empty list.
assert.strictEqual(P.decideRoute('v', 'feature', linkable, 'does-not-exist').action, 'notFound');
// an &suite= that is present but unlinkable shows THAT suite, not all of them
const filtered = P.decideRoute('v', 'feature', nodeless, 'frontend-webui');
assert.strictEqual(filtered.action, 'chooser');
assert.strictEqual(filtered.suites.length, 1);
assert.strictEqual(filtered.suites[0].suite, 'frontend-webui');
// an explicit, linkable &suite= still redirects — previously-shared permalinks
// must keep resolving exactly as before, even into a partial node
assert.strictEqual(P.decideRoute('v', 'feature', linkable, 'cucumber').action, 'redirect');
assert.strictEqual(P.decideRoute('v', 'feature', partial, 'cucumber').action, 'redirect');
// one suite whose node opens everything -> straight there
assert.strictEqual(P.decideRoute('v', 'feature', linkable, null).action, 'redirect');
// one suite whose node opens a fraction -> show the page instead of pretending
assert.strictEqual(P.decideRoute('v', 'feature', partial, null).action, 'chooser');
// no node at all -> the page, carrying the per-test links
assert.strictEqual(P.decideRoute('v', 'feature', nodeless, null).action, 'chooser');
// specs have no tag route, so a single linkable spec suite still redirects
assert.strictEqual(P.decideRoute('v', 'spec', routeSpecEntry, null).action, 'redirect');
// an empty entry is "not found", never a 0-item chooser
assert.strictEqual(P.decideRoute('v', 'feature', {}, null).action, 'notFound');
assert.strictEqual(P.decideRoute('v', 'feature', null, null).action, 'notFound');

// `attributed` reaches the page
assert.strictEqual(P.suitesByCount(nodeless)[0].attributed, 2);
assert.strictEqual(P.suitesByCount(linkable)[0].attributed, 0, 'absent -> 0');

// a uid with URL-significant characters cannot retarget the link
assert.strictEqual(P.buildTestUrl('v', 's', 'a/b#c'),
  'builds/v/allure/s/index.html#testresult/a%2Fb%23c');

// --- completeness is the GENERATOR's call, not re-derived here ---------------
// The generator compares the uids the node reaches against the attributed set
// and signals "not complete" by publishing the `tests` KEY (`attributed` rides
// on every entry with coverage and is NOT a signal). Deriving it
// again from count vs tagged compared DIFFERENT SETS: `count` is the node's
// leaf total including untagged siblings. This entry is the case that escaped —
// count(3) >= tagged(2) reads "complete" while the node opens 3 of 5 attributed
// tests and contains neither tagged one.
const reachGap = { 'cucumber': { uid: 'f'.repeat(32), count: 3, tagged: 2,
                                 attributed: 5,
                                 tests: [{ uid: 'u1', name: 'a' }, { uid: 'u2', name: 'b' }] } };
assert.strictEqual(P.isComplete(P.suitesByCount(reachGap)[0]), false,
  'a published test list means the node does NOT open everything');
assert.strictEqual(P.decideRoute('v', 'feature', reachGap, null).action, 'chooser',
  'must not redirect into a node that reaches 3 of 5 — that is the "looks complete" defect');

// `attributed` is published on EVERY entry with coverage, so it is NOT a
// "not complete" signal. Reading it as one made every complete feature render a
// one-item chooser instead of redirecting to its Allure node. The signal is the
// presence of `tests`: the generator publishes a list only when the node falls
// short, having already compared the uids the node reaches.
assert.strictEqual(P.isComplete({ uid: 'x', count: 9, tagged: 0, attributed: 12 }), true,
  'no tests list -> the generator checked reach and found the node sufficient');
assert.strictEqual(
  P.decideRoute('v', 'feature', { 'c': { uid: 'n', count: 2, tagged: 2, attributed: 2 } }, null)
    .action,
  'redirect',
  'the shape the generator emits for a complete node must still redirect');
assert.strictEqual(
  P.decideRoute('v', 'feature',
    { 'c': { uid: 'n', count: 1, tagged: 115, attributed: 115, tests: [{ uid: 'u' }] } }, null)
    .action,
  'chooser', 'a published list means the node falls short');

// an index published BEFORE `tests` existed still resolves sensibly
assert.strictEqual(P.isComplete({ uid: 'x', count: 5, tagged: 5 }), true, 'legacy: complete');
assert.strictEqual(P.isComplete({ uid: 'x', count: 1, tagged: 115 }), false, 'legacy: partial');

// --- suiteLabel: the four shapes, previously untestable inline in the HTML ----

// complete node: one number, because the node opens exactly what it should
assert.strictEqual(P.suiteLabel({ suite: 'cucumber', uid: 'c', count: 13, tagged: 13 }),
  'cucumber — 13 tests');
assert.strictEqual(P.suiteLabel({ suite: 'cucumber', uid: 'c', count: 1, tagged: 1 }),
  'cucumber — 1 test', 'singular');

// partial node: NEVER conflate the two numbers. This entry is the F1 case —
// the node opens 5 tests and NONE of them are the 2 that carry the feature;
// the old label derived "partial" from total > count and so read just "5 tests".
assert.strictEqual(
  P.suiteLabel({ suite: 'cucumber', uid: 'c', count: 5, tagged: 0, attributed: 2,
                 tests: [{ uid: 'u1' }, { uid: 'u2' }] }),
  'cucumber — the node opens 5 tests; 2 tests carry this feature');
// the real F00700 shape
assert.strictEqual(
  P.suiteLabel({ suite: 'cucumber', uid: 'c', count: 1, tagged: 115, attributed: 115,
                 tests: [{ uid: 'u1' }] }),
  'cucumber — the node opens 1 test; 115 tests carry this feature');

// node-less: the count of tests that carry the feature, and why there is no link
assert.strictEqual(
  P.suiteLabel({ suite: 'frontend-webui', uid: null, count: 0, tagged: 2, attributed: 2,
                 tests: [{ uid: 'u1' }, { uid: 'u2' }] }),
  'frontend-webui — 2 tests, no grouping node');
// node-less with attribution only via the node route (tagged would read 0)
assert.strictEqual(
  P.suiteLabel({ suite: 'cucumber', uid: null, count: 0, tagged: 0, attributed: 3,
                 tests: [{ uid: 'u1' }] }),
  'cucumber — 3 tests, no grouping node', 'must not read "0 tests" over a real list');

// spec: no tag route at all, so the node count is the whole story
assert.strictEqual(P.suiteLabel({ suite: 'mobile-webui', uid: 'd', count: 4 }),
  'mobile-webui — 4 tests');

// a suite with nothing to offer is dropped before the chooser ever sees it
assert.strictEqual(P.isShowable({ suite: 'x', uid: null, count: 0, tagged: 0 }), false);
assert.strictEqual(P.isShowable({ suite: 'x', uid: null, count: 0, tagged: 2 }), true);
assert.strictEqual(P.decideRoute('v', 'spec', { 'x': { uid: null, count: 0 } }, 'x').action,
  'notFound', 'a dead suite is "not found", not a chooser line with nothing in it');

// A bare {uid:null,count:0,tagged:0} entry offers nothing: no link, nothing to
// list, no count to report. It must be "not found" on BOTH paths, not a chooser
// line reading "— 0 tests, no grouping node". (The current generator gives every
// coverage entry an `attributed` and a `tests` key, so it no longer emits this
// shape — kept as a defensive assertion against a hand-edited or older index.)
const bare = { 'cucumber': { uid: null, count: 0, tagged: 0 } };
assert.strictEqual(P.decideRoute('v', 'feature', bare, 'cucumber').action, 'notFound');
assert.strictEqual(P.decideRoute('v', 'feature', bare, null).action, 'notFound');
// but an entry whose tests exist and merely cannot be LINKED still reports them,
// because "no tests found" would be false
const unlinkableOnly = { 'cucumber': { uid: null, count: 0, tagged: 2 } };
assert.strictEqual(P.decideRoute('v', 'feature', unlinkableOnly, null).action, 'chooser');
assert.strictEqual(P.suiteLabel(P.suitesByCount(unlinkableOnly)[0]),
  'cucumber — 2 tests, no grouping node');

// --- "(most)" must mark the biggest COVERAGE, not the biggest node ----------
// It used to be the first entry of a sort that ranks on the node's leaf count,
// so a 3-test complete node outranked a node-less suite holding 50.
const twoSuites = [
  { suite: 'cucumber', uid: 'c', count: 3, tagged: 3 },
  { suite: 'frontend-webui', uid: null, count: 0, tagged: 50, attributed: 50,
    tests: [{ uid: 'u1' }] },
];
assert.strictEqual(P.mostCovered(twoSuites), 'frontend-webui');
assert.strictEqual(P.effectiveTotal(twoSuites[0]), 3);
assert.strictEqual(P.effectiveTotal(twoSuites[1]), 50);
// a single suite is not a choice, so there is no "most"
assert.strictEqual(P.mostCovered([twoSuites[0]]), null);
// a tie has no "most" either — claiming one would be arbitrary
assert.strictEqual(P.mostCovered([{ suite: 'a', count: 4 }, { suite: 'b', count: 4 }]), null);
assert.strictEqual(P.mostCovered([]), null);

// grammar: one test CARRIES the feature
assert.strictEqual(
  P.suiteLabel({ suite: 'x', uid: 'u', count: 5, attributed: 1, tests: [{ uid: 'a' }] }),
  'x — the node opens 5 tests; 1 test carries this feature');

// a uid-less entry with a real test count is still showable: `extract_specs`
// does not guard on uid, and "no tests found" would be false for 4 tests
assert.strictEqual(P.isShowable({ suite: 'a', uid: null, count: 4 }), true);
assert.strictEqual(P.decideRoute('v', 'spec', { 'a': { uid: null, count: 4 } }, 'a').action,
  'chooser');

// --- one source for "how big is this suite" ---------------------------------
// The label and the ranking used to compute this from different fields, so they
// disagreed. Every shape below asserts BOTH, together.

// the regression the F3 fix introduced: isShowable admitted a uid-less entry
// carrying only `count`, and the label rendered the false "0 tests" that
// isShowable exists to prevent. The earlier F3 test checked only the routing.
const uidlessCount = { 'a': { uid: null, count: 4 } };
assert.strictEqual(P.effectiveTotal(P.suitesByCount(uidlessCount)[0]), 4);
assert.strictEqual(P.suiteLabel(P.suitesByCount(uidlessCount)[0]),
  'a — 4 tests, no grouping node', 'must never read "0 tests" for a shown suite');

// a complete node with untagged siblings: the link opens 13, only 2 carry the
// feature. Printing "13 tests" overstated coverage, and ranking used 2.
const withSiblings = { suite: 'c', uid: 'u', count: 13, tagged: 2, attributed: 2 };
assert.strictEqual(P.effectiveTotal(withSiblings), 2);
assert.strictEqual(P.suiteLabel(withSiblings),
  'c — the node opens 13 tests; 2 tests carry this feature');

// when the two genuinely agree, one number is right
assert.strictEqual(P.suiteLabel({ suite: 'c', uid: 'u', count: 3, attributed: 3 }),
  'c — 3 tests');

// mostCovered: a tie only suppresses the hint when it is a tie for the TOP
assert.strictEqual(
  P.mostCovered([{ suite: 'a', count: 5 }, { suite: 'b', count: 5 }, { suite: 'c', count: 7 }]),
  'c', 'a tie below the top must not suppress the hint');

// `attributed` present with no `tests` KEY means the generator ran the reach
// check and found the node sufficient. This entry is the only shape that pins
// it: the legacy fallback (count >= tagged) says the OPPOSITE here, so the
// assertion fails if the generator's verdict is not consulted first. Every
// earlier fixture happened to satisfy both rules, so deleting the line passed.
assert.strictEqual(P.isComplete({ uid: 'x', count: 1, tagged: 3, attributed: 3 }), true,
  'the generator verdict outranks the legacy count-vs-tagged guess');
assert.strictEqual(
  P.decideRoute('v', 'feature', { 'c': { uid: 'x', count: 1, tagged: 3, attributed: 3 } }, null)
    .action,
  'redirect');
// and an EMPTY tests list is still the "node falls short" signal — the key's
// presence is what counts, not its length
assert.strictEqual(P.isComplete({ uid: 'x', count: 9, tagged: 0, attributed: 4, tests: [] }),
  false, 'nothing linkable, but the node still does not reach everything');
assert.strictEqual(
  P.decideRoute('v', 'feature',
    { 'c': { uid: 'x', count: 9, tagged: 0, attributed: 4, tests: [] } }, null).action,
  'chooser');

// --- routing: more than one suite is a CHOICE, never a silent redirect -------
// Dropping the `suites.length === 1` gate passed every other assertion, so a
// feature present in two suites would have redirected to whichever sorted first
// and hidden the other entirely.
const twoComplete = {
  'cucumber': { uid: 'c'.repeat(32), count: 9, tagged: 9 },
  'mobile-webui': { uid: 'm'.repeat(32), count: 4, tagged: 4 },
};
assert.strictEqual(P.decideRoute('v', 'feature', twoComplete, null).action, 'chooser',
  'two complete suites must be offered, not silently collapsed to one');
assert.strictEqual(P.decideRoute('v', 'feature', twoComplete, null).suites.length, 2);

// --- defensive fallbacks, pinned so they are not silently dropped -----------
// The generator always emits `attributed` alongside `tests`, so these terms are
// defensive only. Pinned rather than deleted: a hand-edited or older index can
// still present this shape, and an untested fallback is one that quietly rots.
assert.strictEqual(P.isShowable({ suite: 'x', uid: null, count: 0, tagged: 0,
                                  tests: [{ uid: 'u1' }] }), true,
  'a list with no `attributed` is still something to show');
assert.strictEqual(P.effectiveTotal({ suite: 'x', tests: [{ uid: 'u1' }, { uid: 'u2' }] }), 2,
  'falls back to the list length when `attributed` is absent');
// the sort's second tie-break: equal `count`, more tagged first
const tieOnCount = { 'a': { uid: 'a', count: 2, tagged: 1 }, 'b': { uid: 'b', count: 2, tagged: 9 } };
assert.strictEqual(P.suitesByCount(tieOnCount)[0].suite, 'b',
  'equal node counts -> the suite where more tests carry the feature sorts first');

// --- the chooser order and the "(most)" hint must use the SAME measure ------
// Ranking rows on the node's `count` while marking "(most)" by coverage put the
// marked row BELOW an unmarked one: cucumber's node holds 5, but frontend-webui
// carries 100 of the feature's tests.
const orderVsHint = {
  'cucumber': { uid: 'c', count: 5, attributed: 3 },
  'frontend-webui': { uid: 'f', count: 2, attributed: 100, tests: [{ uid: 'u1' }] },
};
const byCoverage = P.suitesByCount(orderVsHint);
assert.strictEqual(byCoverage[0].suite, 'frontend-webui', 'most coverage sorts first');
assert.strictEqual(P.mostCovered(byCoverage), byCoverage[0].suite,
  'the row marked "(most)" must be the row listed first');
// a linkable row still beats an unlinkable one, whatever the coverage
const linkFirst = P.suitesByCount({
  'a': { uid: null, count: 0, attributed: 99, tests: [{ uid: 'u' }] },
  'b': { uid: 'b', count: 1, attributed: 1 },
});
assert.strictEqual(linkFirst[0].suite, 'b', 'a clickable row comes first');
