'use strict';

// Parses a JUnit XML file (the format produced by both the cucumber
// `junit:target/cucumber-junit.xml` plugin AND the Playwright junit reporter)
// into a flat array of failure records.
//
// We only emit FAILED testcases — passing/skipped ones are ignored. Each record
// captures just enough to bucketize and to act as a stable key in the sheet.
//
// The two formats differ slightly:
//
//  cucumber  <testsuite name="Cucumber" tests=.. failures=..>
//              <testcase classname="<Feature title>" name="<Scenario name>">
//                <failure type="<exception class>" message="<msg>">CDATA(stacktrace)</failure>
//
//  playwright <testsuites><testsuite name="spec/foo.spec.js">
//                <testcase name="<test title>" classname="spec/foo.spec.js">
//                  <failure message="foo.spec.js:72:5 <title>" type="FAILURE">CDATA(details)</failure>
//
// fast-xml-parser handles both; we normalise into one record shape.

const fs = require('fs');
const { XMLParser } = require('fast-xml-parser');

const parser = new XMLParser({
  ignoreAttributes: false,
  attributeNamePrefix: '@_',
  // Keep CDATA / text content under a predictable key.
  textNodeName: '#text',
  // failure/error can legitimately appear 0..n times — always coerce to array
  // so callers don't have to special-case the single-element case.
  isArray: (name) => ['testsuite', 'testcase', 'failure', 'error'].includes(name),
  trimValues: true,
});

function asArray(x) {
  if (x === undefined || x === null) return [];
  return Array.isArray(x) ? x : [x];
}

// Extract the human-readable detail text from a <failure> / <error> node,
// whatever shape fast-xml-parser gave it (string, or { '#text': ... , '@_...' }).
function failureText(node) {
  if (node == null) return '';
  if (typeof node === 'string') return node;
  return node['#text'] || '';
}

function failureAttr(node, attr) {
  if (node == null || typeof node === 'string') return '';
  return node[`@_${attr}`] || '';
}

// Returns: { testType: 'cucumber'|'playwright', failures: [ {suite, classname, name, fullName, exceptionType, message, detail} ] }
function parseJUnitString(xml, { testType } = {}) {
  const root = parser.parse(xml);

  // cucumber emits a single top-level <testsuite>; playwright wraps in <testsuites>.
  let suites = [];
  if (root.testsuites) {
    suites = asArray(root.testsuites.testsuite);
  } else if (root.testsuite) {
    suites = asArray(root.testsuite);
  }

  // Heuristic: cucumber's single suite is literally name="Cucumber".
  const inferredType =
    testType ||
    (suites.length === 1 && suites[0]['@_name'] === 'Cucumber' ? 'cucumber' : 'playwright');

  const failures = [];
  for (const suite of suites) {
    const suiteName = suite['@_name'] || '';
    for (const tc of asArray(suite.testcase)) {
      const fNodes = [...asArray(tc.failure), ...asArray(tc.error)];
      if (fNodes.length === 0) continue;

      const classname = tc['@_classname'] || '';
      const name = tc['@_name'] || '';
      // fullName is the stable key: feature/spec + scenario/test title.
      const scope = classname || suiteName;
      const fullName = scope ? `${scope} :: ${name}` : name;

      // One testcase can carry multiple <failure> nodes; concatenate detail,
      // and take the first failure's type/message as the representative one.
      const exceptionType = failureAttr(fNodes[0], 'type');
      const message = failureAttr(fNodes[0], 'message');
      const detail = fNodes.map(failureText).join('\n---\n');

      failures.push({
        testType: inferredType,
        suite: suiteName,
        classname,
        name,
        fullName,
        exceptionType,
        message,
        detail,
      });
    }
  }

  return { testType: inferredType, failures };
}

function parseJUnitFile(filePath, opts) {
  const xml = fs.readFileSync(filePath, 'utf8');
  return parseJUnitString(xml, opts);
}

module.exports = { parseJUnitString, parseJUnitFile };
