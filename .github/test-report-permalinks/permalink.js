// permalink.js — pure, testable resolver logic (browser + node)
(function (root) {
  // A suite is only a candidate if it has a uid to deep-link to. A feature can
  // be well covered in a suite by TAG alone, which creates no Behaviours node
  // and so nothing to link at -- picking that suite would yield a dead link.
  function isLinkable(e) { return !!(e && e.uid); }
  function chooseSuite(entry) {
    var best = null, bestCount = -1;
    Object.keys(entry || {}).forEach(function (s) {
      if (!isLinkable(entry[s])) return;
      var c = (entry[s] && entry[s].count) || 0;
      if (c > bestCount) { bestCount = c; best = s; }
    });
    return best;
  }
  function buildRedirectUrl(version, kind, entry, suite) {
    if (!entry) return null;
    suite = suite || chooseSuite(entry);
    if (!suite || !isLinkable(entry[suite])) return null;
    var tab = kind === 'spec' ? 'suites' : 'behaviors';
    return 'builds/' + version + '/allure/' + suite + '/index.html#' + tab + '/' + entry[suite].uid;
  }
  // A single test result, for the cases where there is no Behaviours node to
  // link at -- or where the node covers fewer tests than carry the F-code.
  function buildTestUrl(version, suite, uid) {
    if (!version || !suite || !uid) return null;
    // The uid is the report-derived variable; encoding it keeps a stray '/', '?'
    // or '#' from retargeting the link. A no-op for Allure's hex uids.
    return 'builds/' + version + '/allure/' + suite + '/index.html#testresult/'
           + encodeURIComponent(uid);
  }
  // True when following the node link would open EVERY test attributed to the
  // feature. Only then is a silent redirect honest: a node holding 1 test while
  // 115 carry the tag looks complete when you land on it.
  //
  // The GENERATOR owns this decision -- it compares the uids the node reaches
  // against the attributed set. It signals the answer by publishing `tests`:
  // a list exists ONLY when the node falls short. `attributed` is published on
  // every entry with coverage and is therefore NOT a signal -- reading it as one
  // made every complete feature render a one-item chooser instead of
  // redirecting. Its presence does tell us the generator ran the reach check,
  // which is what lets us skip the legacy fallback below.
  function isComplete(e) {
    if (!e || !e.uid) return false;
    // The KEY's presence is the signal, not the list's length: the generator
    // writes it (possibly empty) for every entry the node falls short of, so an
    // entry whose attributed tests are all unlinkable still reads as incomplete.
    if (Array.isArray(e.tests)) return false;
    if (e.attributed) return true;
    // Fallback for an index published before the reach check existed. `count`
    // and `tagged` count different sets, so this is a guess -- and it errs
    // toward REDIRECTING (it is true whenever the node's leaf total reaches the
    // tag count, untagged siblings included), which is why it is reached only
    // when the generator published no verdict of its own.
    return (e.count || 0) >= (e.tagged || 0);
  }
  function lookup(data, kind, key) {
    if (!data) return null;
    var bag = kind === 'spec' ? data.specs : data.features;
    return (bag && bag[key]) || null;
  }
  // The suites a feature/spec appears in, most tests carrying the feature first.
  // Ordered by `effectiveTotal`, the SAME measure the label and the "(most)"
  // hint use -- ranking on the node's `count` here let the row marked "(most)"
  // sit below a row with a bigger node but less coverage.
  function suitesByCount(entry) {
    return Object.keys(entry || {})
      .map(function (s) {
        return {
          suite: s,
          count: (entry[s] && entry[s].count) || 0,
          // How many tests carry this F-code as a TAG in that suite. A tag makes
          // no Behaviours node, so `tagged` can far exceed `count` -- surfacing
          // both is what stops a link silently showing a fraction of the tests.
          tagged: (entry[s] && entry[s].tagged) || 0,
          // Individual tests, published by the generator only where the node
          // does not reach them all. Passed through AS-IS, never defaulted to
          // []: the KEY's presence is the "node falls short" signal, and
          // normalising an absent key into an empty array destroyed it --
          // isComplete then read every entry as incomplete.
          tests: entry[s] && entry[s].tests,
          // Size of the union set the tests were drawn from; the page compares
          // the (capped) list against THIS, not against `tagged`.
          attributed: (entry[s] && entry[s].attributed) || 0,
          uid: entry[s] && entry[s].uid
        };
      })
      .sort(function (a, b) {
        // linkable suites first (a row you can click beats one you cannot),
        // then by how many tests carry the feature, then by the node size.
        if (!!a.uid !== !!b.uid) return a.uid ? -1 : 1;
        return (effectiveTotal(b) - effectiveTotal(a)) || (b.count - a.count);
      });
  }
  // A suite is worth rendering when it offers something: a node link, listable
  // tests, or at least a real number of tests to report as unlinkable. A suite
  // with none of those (e.g. a spec entry whose uid is null, where `tagged` is
  // always 0) would render a dead "— 0 tests, no grouping node" line.
  function isShowable(s) {
    return !!(s && (s.uid || (s.tests && s.tests.length)
                    || s.attributed || s.tagged || s.count));
  }
  // How many tests carry this feature in this suite -- the honest size of a
  // suite, as opposed to `count`, which is whatever the node happens to hold.
  function effectiveTotal(s) {
    return (s && (s.attributed || (s.tests && s.tests.length) || s.tagged || s.count)) || 0;
  }
  // The one-line summary for a suite in the chooser. Pure, so it is testable --
  // it lived inline in permalink.html, where the "partial" qualifier was derived
  // from `total > count` and so inherited the very set-mismatch `isComplete` was
  // fixed for: `count` is the node's leaf total, including untagged siblings and
  // leaves counted twice, so a node with count 5 that opens NONE of the feature's
  // 2 tests read simply "5 tests". The qualifier is now keyed on the generator's
  // signal, and the two numbers are never conflated into one.
  function suiteLabel(s) {
    if (!s) return '';
    var attributed = effectiveTotal(s);
    var plural = function (n) { return n === 1 ? ' test' : ' tests'; };
    if (!s.uid) {
      return s.suite + ' — ' + attributed + plural(attributed) + ', no grouping node';
    }
    // Two numbers whenever they differ, whether or not the node is "complete":
    // `count` is what the link opens (the node's leaves, untagged siblings and
    // all), `attributed` is what carries the feature. Printing only `count`
    // overstated coverage for a node with untagged siblings.
    if (s.count !== attributed) {
      return s.suite + ' — the node opens ' + s.count + plural(s.count)
           + '; ' + attributed + plural(attributed)
           + (attributed === 1 ? ' carries' : ' carry') + ' this feature';
    }
    return s.suite + ' — ' + s.count + plural(s.count);
  }
  // The suite name with the most tests carrying the feature, or null when that is
  // not unique -- a "(most)" hint is only worth showing when there IS a most.
  function mostCovered(suites) {
    if (!suites || suites.length < 2) return null;
    var best = null, bestN = -1, tied = false;
    suites.forEach(function (s) {
      var n = effectiveTotal(s);
      if (n > bestN) { bestN = n; best = s.suite; tied = false; }
      else if (n === bestN) { tied = true; }
    });
    return tied ? null : best;
  }
  // The whole routing decision, in one testable place. It used to live inline in
  // permalink.html's <script>, where it could not be covered -- which is how an
  // unknown &suite= came to render an empty 0-item chooser instead of "not found".
  // Returns { action: 'redirect'|'chooser'|'notFound', url?, suites? }.
  function decideRoute(version, kind, entry, suite) {
    var suites = suitesByCount(entry).filter(isShowable);
    if (!suites.length) return { action: 'notFound' };
    if (suite) {
      // An explicit &suite= is a deliberate choice, so it still redirects when it
      // can -- that keeps every previously-shared permalink resolving as before.
      var url = buildRedirectUrl(version, kind, entry, suite);
      if (url) return { action: 'redirect', url: url };
      var only = suites.filter(function (s) { return s.suite === suite; });
      // present with something to show -> show it (its tests may still be
      // reachable individually); otherwise the caller named a suite this key has
      // nothing in, which is "not found" exactly as it was before.
      return only.length ? { action: 'chooser', suites: only } : { action: 'notFound' };
    }
    // One suite whose node opens every attributed test: nothing to choose or
    // qualify. Redirecting when the node is partial (or absent) is what made the
    // page look complete while showing a fraction.
    if (suites.length === 1 && isComplete(suites[0])) {
      var one = buildRedirectUrl(version, kind, entry, suites[0].suite);
      if (one) return { action: 'redirect', url: one };
    }
    return { action: 'chooser', suites: suites };
  }
  var api = { chooseSuite: chooseSuite, decideRoute: decideRoute, isShowable: isShowable,
             suiteLabel: suiteLabel, effectiveTotal: effectiveTotal,
             mostCovered: mostCovered, isLinkable: isLinkable, isComplete: isComplete,
             buildRedirectUrl: buildRedirectUrl, buildTestUrl: buildTestUrl,
             lookup: lookup, suitesByCount: suitesByCount };
  if (typeof module !== 'undefined' && module.exports) module.exports = api;
  else root.Permalink = api;
})(typeof window !== 'undefined' ? window : this);
