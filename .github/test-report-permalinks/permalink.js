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
  var api = { chooseSuite: chooseSuite, buildRedirectUrl: buildRedirectUrl, lookup: lookup };
  if (typeof module !== 'undefined' && module.exports) module.exports = api;
  else root.Permalink = api;
})(typeof window !== 'undefined' ? window : this);
