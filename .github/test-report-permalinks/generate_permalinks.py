#!/usr/bin/env python3
"""Generate branches/{branch}/permalinks.json from a just-published build's Allure trees.
Pure I/O: reads behaviors.json (features) + suites.json (spec files); writes a stable
feature/spec -> {suite: {uid, count}} map. Runs server-side (piped over ssh) and locally (tests)."""
import json, os, re, sys, tempfile

ALLURE_SUITES = ["cucumber", "frontend-webui", "mobile-webui"]
#: Suites that run and report totals but publish no Allure report at all, so
#: they carry no feature labels. "No test" never means "no test" — it means no
#: cucumber and no Playwright test.
NO_ALLURE_SUITES = ["junit/backend", "junit/camel", "junit/jest"]
FCODE_RE = re.compile(r"^(F\d+(?:\.\d+)?)\b")
#: An F-code as it appears in a test's own `tags`: bare ("F00230") or with its
#: name ("F00230: MobileUI Picking"). The subfeature separator is written "."
#: by the Playwright specs and "_" by cucumber, so both are accepted and
#: normalised to the dot form -- otherwise one subfeature indexes as two.
TAG_FCODE_RE = re.compile(r"^\s*(F\d+(?:[._]\d+)?)\s*(?::|$)")
ECODE_RE = re.compile(r"^E\d+\b")
SPEC_SUFFIXES = (".spec.js", ".feature")

def _count_leaves(node):
    # A leaf test-case has no "children" key (Allure uses null); a group node has a
    # list — count its leaves (an explicit empty list yields 0, not a phantom 1).
    ch = node.get("children")
    if ch is None:
        return 1
    return sum(_count_leaves(c) for c in ch)

def extract_features(behaviors_root):
    """depth-0 non-epic nodes + children of epic (E\\d+) nodes; keyed by full name, aliased by F-code."""
    out = {}
    def add(name, uid, count):
        if not name or not uid:
            return
        out[name] = (uid, count)
        m = FCODE_RE.match(name)
        if m:
            out.setdefault(m.group(1), (uid, count))
    for top in behaviors_root.get("children") or []:
        tname = top.get("name") or ""
        if ECODE_RE.match(tname):
            for feat in top.get("children") or []:
                if feat.get("children"):
                    add(feat.get("name"), feat.get("uid"), _count_leaves(feat))
        elif top.get("children"):
            add(tname, top.get("uid"), _count_leaves(top))
    return out

def _canonical_fcode(code):
    """`F5001_1` and `F5001.1` are the same subfeature; index them once."""
    return code.replace("_", ".", 1) if re.match(r"^F\d+_\d", code) else code

def extract_tagged_features(behaviors_root):
    """{F-code: number of tests carrying it as a TAG}, for the whole tree.

    `extract_features` above indexes by Allure grouping-node NAME, which is
    populated only by `allure.feature(...)`. The specs overwhelmingly use
    `allure.tag('Fxxxx: Name')` + `allure.tag('Fxxxx')` instead, and a tag
    creates no grouping node -- so a feature can have many tests here and at
    most a token presence in the Behaviours tree. Measured on
    5.175-intensive-care-release.43783: F00700's Behaviours node holds 1 test
    while 141 carry its tag; for F00230 the node holds 67 against 190 tagged.
    Indexing the tag route is what lets the resolver page say which of the two
    numbers a link is about to show.
    """
    seen = {}   # F-code -> set of leaf uids, because a test appears MORE THAN ONCE
                # in the tree (cucumber lists every test under its .feature file AND
                # again under Epic -> Feature). Counting occurrences inflated F00230
                # from 190 real tests to 293 before this was de-duplicated.
    def walk(node):
        ch = node.get("children")
        if ch is None:
            uid = node.get("uid") or node.get("name")
            for tag in node.get("tags") or []:
                m = TAG_FCODE_RE.match(str(tag))
                if m:
                    seen.setdefault(_canonical_fcode(m.group(1)), set()).add(uid)
            return
        for c in ch:
            walk(c)
    for top in behaviors_root.get("children") or []:
        walk(top)
    return {code: len(uids) for code, uids in seen.items()}

def _leaf_features(node, inherited, out):
    """Collect {F-code: {uid: status}} for one subtree.

    Mirrors the semantics the Knowledge Map recipe established against real
    builds, because the two must agree:

    - a leaf is credited to the UNION of the F-codes in its own `tags` and the
      enclosing `Fxxxx` node, not to whichever comes first. Both routes are
      real and neither is sufficient alone: `allure.tag` lands in `tags` and
      builds no node, `allure.feature` builds a node and sets no tag, and a
      spec can use either. Reading tags *instead of* the node (which this
      docstring wrongly described until 2026-09-12) drops 27 attributions on
      5.175-intensive-care-release.43783 and loses `F01010`, `F01010.3`,
      `F01010.5` and `F8016` entirely — while leaving the headline totals
      untouched, so the numbers in the tests do not catch it;
    - results are de-duplicated on Allure's `uid`, because a test is listed
      more than once in the tree;
    - an enclosing node that is merely the PARENT of a tag-named subfeature is
      not credited, so `F5001` does not inherit `F5001.1`'s tests.
    """
    ch = node.get("children")
    if ch is None:
        uid = node.get("uid") or node.get("name")
        status = node.get("status") or "unknown"
        found = set()
        for tag in node.get("tags") or []:
            m = TAG_FCODE_RE.match(str(tag))
            if m:
                found.add(_canonical_fcode(m.group(1)))
        if inherited and not any(f.startswith(inherited + ".") for f in found):
            found.add(inherited)
        for code in found:
            out.setdefault(code, {})[uid] = status
        return
    m = FCODE_RE.match(node.get("name") or "")
    nf = _canonical_fcode(m.group(1)) if m else inherited
    for c in ch:
        _leaf_features(c, nf, out)


def _count_distinct_leaves(behaviors_root):
    """Every distinct leaf uid in the tree, annotated or not.

    The figure to reconcile against `failures.json`. Counting only LABELLED
    leaves would compare a subset against the whole and disagree on any suite
    holding an unannotated test — which is every suite here.
    """
    seen = set()
    def walk(node):
        ch = node.get("children")
        if ch is None:
            seen.add(node.get("uid") or node.get("name"))
            return
        for c in ch:
            walk(c)
    for top in behaviors_root.get("children") or []:
        walk(top)
    return len(seen)


def extract_coverage(behaviors_root):
    """{F-code: {uid: status}} across the whole tree — the per-feature answer."""
    out = {}
    for top in behaviors_root.get("children") or []:
        _leaf_features(top, None, out)
    return out


def read_suite_totals(build_dir):
    """Per-suite totals from the build's failures.json — the independent figure
    a parsed tree is reconciled against, and the only evidence that a suite with
    no Allure report nonetheless RAN."""
    path = os.path.join(build_dir, "failures.json")
    if not os.path.isfile(path):
        return {}
    try:
        with open(path, encoding="utf-8") as f:
            data = json.load(f)
    except Exception:
        return {}
    suites = data.get("suites") if isinstance(data, dict) else None
    if not isinstance(suites, dict):
        return {}
    return {k: v.get("total") for k, v in suites.items()
            if isinstance(v, dict) and isinstance(v.get("total"), int)}


def build_coverage(build_dir):
    """The whole-branch coverage answer, for `coverage.html` to render.

    Published alongside the permalink index so ANY branch can be answered from
    a browser. The published Knowledge Map answer can only ever show one
    branch, because a static capture is one branch by construction; this is
    the live counterpart.

    It is served from this host so it is same-origin with the data and with
    the per-feature permalinks it links to, and so it cannot break when
    someone else's CORS configuration changes. (An earlier version of this
    comment claimed the host sends no CORS headers and that a page elsewhere
    therefore could not read this data. That was wrong: it sends
    `access-control-allow-origin: *`. A cross-origin page IS possible; this
    one is same-origin by choice, not by necessity.)
    """
    reported = read_suite_totals(build_dir)
    suites, features = {}, {}
    for suite in ALLURE_SUITES:
        bpath = os.path.join(build_dir, "allure", suite, "data", "behaviors.json")
        total = reported.get(suite)
        if not os.path.isfile(bpath):
            suites[suite] = {"state": "absent" if total is not None else "unknown",
                             "ran": total, "tests": None, "labelled": None}
            continue
        with open(bpath, encoding="utf-8") as f:
            behaviors = json.load(f)
        per_feature = extract_coverage(behaviors)
        # Two INDEPENDENT measurements, and they must be kept independent:
        #   `parsed`   — every distinct leaf in the tree, labelled or not
        #   `total`    — what failures.json says the suite ran
        # `labelled` is a subset of `parsed` by construction, so comparing
        # `labelled` against `total` is not a reconciliation — it is a
        # guaranteed mismatch on any suite with an unannotated test.
        parsed = _count_distinct_leaves(behaviors)
        labelled = {uid for tests in per_feature.values() for uid in tests}
        if total is not None and parsed != total:
            # A tree that does not reconcile is not an answer. The recipe drops
            # the suite's per-feature data here rather than publish counts from
            # a mis-parsed tree, and so must this: every miscount this code has
            # had (occurrence-counting, hierarchy-only reading, the dropped bare
            # node) showed up first as exactly this disagreement.
            suites[suite] = {"state": "unknown", "ran": total, "tests": None,
                             "labelled": None, "parsed": parsed,
                             "reason": f"parsed {parsed} distinct test(s) but "
                                       f"failures.json reports {total}"}
            continue
        for code, tests in per_feature.items():
            for uid, status in tests.items():
                entry = features.setdefault(code, {}).setdefault(
                    suite, {"tests": 0, "status": {}})
                entry["tests"] += 1
                entry["status"][status] = entry["status"].get(status, 0) + 1
        suites[suite] = {"state": "measured", "ran": total, "tests": parsed,
                         "labelled": len(labelled), "parsed": parsed}
    for suite in NO_ALLURE_SUITES:
        total = reported.get(suite)
        if total is not None:
            suites[suite] = {"state": "absent", "ran": total, "tests": None, "labelled": None}
    return {"suites": suites, "features": features}


def extract_specs(suites_root):
    out = {}
    def walk(node):
        for c in node.get("children") or []:
            name = c.get("name") or ""
            if c.get("children") is not None and name.endswith(SPEC_SUFFIXES):
                out.setdefault(name, (c.get("uid"), _count_leaves(c)))
            walk(c)
    walk(suites_root)
    return out

def build_index(build_dir):
    features, specs = {}, {}
    for suite in ALLURE_SUITES:
        bpath = os.path.join(build_dir, "allure", suite, "data", "behaviors.json")
        if os.path.isfile(bpath):
            with open(bpath, encoding="utf-8") as f:
                behaviors = json.load(f)
            for key, (uid, count) in extract_features(behaviors).items():
                features.setdefault(key, {})[suite] = {"uid": uid, "count": count, "tagged": 0}
            # A tag creates no grouping node, so a feature can be well covered in
            # this suite and still have no entry above. Record it either way --
            # with a null uid when there is nothing to deep-link to, so the
            # resolver can say "no linkable node" instead of the key looking absent.
            for code, tagged in extract_tagged_features(behaviors).items():
                entry = features.setdefault(code, {}).setdefault(
                    suite, {"uid": None, "count": 0, "tagged": 0})
                entry["tagged"] = tagged
        spath = os.path.join(build_dir, "allure", suite, "data", "suites.json")
        if os.path.isfile(spath):
            with open(spath, encoding="utf-8") as f:
                for name, (uid, count) in extract_specs(json.load(f)).items():
                    specs.setdefault(name, {})[suite] = {"uid": uid, "count": count}
    return features, specs

def main(argv):
    branch, version = argv[1], argv[2]
    base = argv[3] if len(argv) > 3 else "/var/www/test-reports"
    build_dir = os.path.join(base, "branches", branch, "builds", version)
    features, specs = build_index(build_dir)
    # Parse BEFORE creating the temp file. Anything that can raise belongs
    # outside the mkstemp block: this directory is served by nginx, and an
    # exception between mkstemp and os.replace would strand a `*.tmp` there on
    # every build -- silently, because the calling step is continue-on-error.
    coverage = build_coverage(build_dir)
    out_dir = os.path.join(base, "branches", branch)
    os.makedirs(out_dir, exist_ok=True)
    fd, tmp = tempfile.mkstemp(dir=out_dir, suffix=".tmp")
    with os.fdopen(fd, "w", encoding="utf-8") as f:
        json.dump({"version": version, "features": features, "specs": specs,
                   "coverage": coverage}, f, indent=2, ensure_ascii=False)
    # mkstemp creates the temp file 0600; the web server runs as a different user and
    # must be able to read the published file (else nginx serves 403). Widen to 0644
    # before the atomic rename so the served file is group+other readable.
    os.chmod(tmp, 0o644)
    os.replace(tmp, os.path.join(out_dir, "permalinks.json"))
    print(f"permalinks.json: {len(features)} feature keys, {len(specs)} spec keys")

if __name__ == "__main__":
    main(sys.argv)
