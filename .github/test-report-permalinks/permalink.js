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
    return 'builds/' + version + '/allure/' + suite + '/index.html#testresult/' + uid;
  }
  // True when following the node link would show EVERY test carrying the F-code.
  // Only then is a silent redirect honest: F00700/cucumber has a node holding 1
  // test while 115 carry the tag, and redirecting there looks complete.
  function isComplete(e) {
    if (!e || !e.uid) return false;
    return (e.count || 0) >= (e.tagged || 0);
  }
  function lookup(data, kind, key) {
    if (!data) return null;
    var bag = kind === 'spec' ? data.specs : data.features;
    return (bag && bag[key]) || null;
  }
  // The suites a feature/spec appears in, highest test count first. [0] is the
  // default suite (== chooseSuite); used to render the multi-suite chooser.
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
          // link cannot stand in for them. Capped -- compare against `tagged`.
          tests: (entry[s] && entry[s].tests) || [],
          uid: entry[s] && entry[s].uid
        };
      })
      .sort(function (a, b) {
        // linkable suites first, then by how many tests the link will show,
        // then by the true tagged size so a dead-but-large suite is still visible
        if (!!a.uid !== !!b.uid) return a.uid ? -1 : 1;
        return (b.count - a.count) || (b.tagged - a.tagged);
      });
  }
  var api = { chooseSuite: chooseSuite, isLinkable: isLinkable, isComplete: isComplete,
             buildRedirectUrl: buildRedirectUrl, buildTestUrl: buildTestUrl,
             lookup: lookup, suitesByCount: suitesByCount };
  if (typeof module !== 'undefined' && module.exports) module.exports = api;
  else root.Permalink = api;
})(typeof window !== 'undefined' ? window : this);
