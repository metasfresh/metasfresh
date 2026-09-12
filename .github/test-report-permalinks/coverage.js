// coverage.js — pure, testable logic for the per-branch coverage page.
// Mirrors the Knowledge Map recipe's presentation rules so the two agree:
// a feature whose tests all passed reads "passed"; anything else names the
// non-passing statuses with counts, so a long table still scans while the
// rows worth acting on stand out.
(function (root) {
  function formatResult(status) {
    var keys = Object.keys(status || {});
    if (!keys.length) return 'unknown';
    var bad = keys.filter(function (k) { return k !== 'passed'; }).sort();
    if (!bad.length) return 'passed';
    return bad.map(function (k) { return status[k] + ' ' + k; }).join(', ');
  }

  // One row per feature: totals summed across the suites it appears in.
  function featureRows(coverage) {
    var features = (coverage && coverage.features) || {};
    return Object.keys(features).map(function (code) {
      var perSuite = features[code], tests = 0, status = {}, suites = [];
      Object.keys(perSuite).sort().forEach(function (s) {
        tests += perSuite[s].tests || 0;
        suites.push(s);
        Object.keys(perSuite[s].status || {}).forEach(function (k) {
          status[k] = (status[k] || 0) + perSuite[s].status[k];
        });
      });
      return { feature: code, tests: tests, status: status,
               result: formatResult(status), suites: suites };
    }).sort(function (a, b) { return (b.tests - a.tests) || a.feature.localeCompare(b.feature); });
  }

  function summarise(rows, coverage) {
    var notPassing = rows.filter(function (r) { return r.result !== 'passed'; });
    var suites = (coverage && coverage.suites) || {};
    var tests = 0, labelled = 0;
    Object.keys(suites).forEach(function (s) {
      if (suites[s].state === 'measured') {
        tests += suites[s].tests || 0;
        labelled += suites[s].labelled || 0;
      }
    });
    return { features: rows.length, notPassing: notPassing.length,
             fullyPassing: rows.length - notPassing.length,
             tests: tests, labelled: labelled, notPassingRows: notPassing };
  }

  // The permalink resolver is branch-scoped on purpose: a build-scoped URL
  // rots at the next build.
  function permalinkFor(branch, feature) {
    return 'branches/' + encodeURIComponent(branch) + '/permalink.html?feature=' +
           encodeURIComponent(feature);
  }

  // A branch name as the host spells it. This MUST mirror the server's
  // sanitize-branch-for-gh-pages step in cicd.yaml exactly, or the fetch 404s
  // on a branch that was published perfectly well:
  //
  //   tr '/' '-' | tr '_' '-' | tr -cd 'a-zA-Z0-9.-' | tr '[:upper:]' '[:lower:]'
  //     | sed 's/--*/-/g' | sed 's/^-*//' | sed 's/-*$//'
  //
  // Lower-casing is the one that bites hardest: this repo's own convention is
  // `{base_branch}_{FeatureDescription}`, so essentially every feature branch
  // is mixed-case. Handling only `_` -> `-` (as this did at first) 404s on
  // `new_dawn_uat_CoveragePageAllBranches` while the server holds
  // `new-dawn-uat-coveragepageallbranches`.
  function normaliseBranch(branch) {
    return String(branch || '')
      .trim()
      .replace(/[/_]/g, '-')
      .replace(/[^a-zA-Z0-9.-]/g, '')   // tr -cd: DROPS, never substitutes
      .toLowerCase()
      .replace(/-{2,}/g, '-')
      .replace(/^-+/, '')
      .replace(/-+$/, '');
  }

  var api = { formatResult: formatResult, featureRows: featureRows, summarise: summarise,
              permalinkFor: permalinkFor, normaliseBranch: normaliseBranch };
  if (typeof module !== 'undefined' && module.exports) module.exports = api;
  else root.Coverage = api;
})(typeof window !== 'undefined' ? window : this);
