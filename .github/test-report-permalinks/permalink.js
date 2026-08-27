// permalink.js — pure, testable resolver logic (browser + node)
(function (root) {
  function chooseSuite(entry) {
    var best = null, bestCount = -1;
    Object.keys(entry || {}).forEach(function (s) {
      var c = (entry[s] && entry[s].count) || 0;
      if (c > bestCount) { bestCount = c; best = s; }
    });
    return best;
  }
  function buildRedirectUrl(version, kind, entry, suite) {
    if (!entry) return null;
    suite = suite || chooseSuite(entry);
    if (!suite || !entry[suite]) return null;
    var tab = kind === 'spec' ? 'suites' : 'behaviors';
    return 'builds/' + version + '/allure/' + suite + '/index.html#' + tab + '/' + entry[suite].uid;
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
        return { suite: s, count: (entry[s] && entry[s].count) || 0, uid: entry[s] && entry[s].uid };
      })
      .sort(function (a, b) { return b.count - a.count; });
  }
  var api = { chooseSuite: chooseSuite, buildRedirectUrl: buildRedirectUrl, lookup: lookup, suitesByCount: suitesByCount };
  if (typeof module !== 'undefined' && module.exports) module.exports = api;
  else root.Permalink = api;
})(typeof window !== 'undefined' ? window : this);
