#!/usr/bin/env python3
"""Generate branches/{branch}/permalinks.json from a just-published build's Allure trees.
Pure I/O: reads behaviors.json (features) + suites.json (spec files); writes a stable
feature/spec -> {suite: {uid, count, tagged, attributed, tests?}} map. Runs server-side (piped over ssh) and locally (tests)."""
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
#: Cap on per-test deep links published for one feature in one suite. The true
#: total stays in `attributed` (NOT in `tagged`, which counts the tag route
#: alone), so a capped list is reported as "N of M".
MAX_LINKED_TESTS = 25
#: Test names are free text; bound them so one pathological name cannot bloat
#: the published index.
NAME_MAX = 160

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

def node_leaf_uids(behaviors_root):
    """{node uid: set of leaf uids beneath it} for the nodes `extract_features`
    indexes.

    Lets `_attach_test_links` ask the only question that matters — does the node
    this feature links to actually REACH the tests attributed to it? — instead of
    comparing a leaf count against a differently-built set.
    """
    out = {}
    def leaves(node, acc):
        ch = node.get("children")
        if ch is None:
            acc.add(node.get("uid") or node.get("name"))
            return acc
        for c in ch:
            leaves(c, acc)
        return acc
    for top in behaviors_root.get("children") or []:
        tname = top.get("name") or ""
        if ECODE_RE.match(tname):
            for feat in top.get("children") or []:
                if feat.get("children") and feat.get("uid"):
                    out[feat["uid"]] = leaves(feat, set())
        elif top.get("children") and top.get("uid"):
            out[top["uid"]] = leaves(top, set())
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
    5.175-intensive-care-release.43783, and note all three columns are
    DIFFERENT questions about the same feature:

        feature   node (extract_features)   tag (this fn)   tag-or-node
        F00700                          1             120           141
        F00230                         67             158           190

    This function returns the middle column. The right-hand column is what
    `extract_coverage` produces and what the coverage page shows; quoting it
    here as "the tag count" is an error this docstring carried until
    2026-09-12. Indexing the tag route is what lets the resolver page say
    which number a link is about to show -- the node column is what the link
    opens, and it can be a tiny fraction of the other two.
    """
    seen = {}   # F-code -> set of leaf uids, because a test appears MORE THAN ONCE
                # in the tree (cucumber lists every test under its .feature file AND
                # again under Epic -> Feature). For F00230 the tag occurs 293 times
                # across the tree and belongs to 158 distinct tests.
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


def leaf_names(behaviors_root):
    """{uid: name} for every leaf, so a published test link can carry its title.

    A separate pass rather than a change to `_leaf_features`, whose {uid: status}
    shape `build_coverage` depends on.
    """
    out = {}
    def walk(node):
        ch = node.get("children")
        if ch is None:
            uid = node.get("uid")
            # ONLY real uids. `_leaf_features` falls back to the NAME as its key
            # when a leaf has no uid; publishing that as a link would build
            # `#testresult/<a test name>`, which resolves to nothing. Keying on
            # real uids here makes this map double as the "is it linkable" set.
            if uid and uid not in out:
                out[uid] = (node.get("name") or "")[:NAME_MAX]
            return
        for c in ch:
            walk(c)
    for top in behaviors_root.get("children") or []:
        walk(top)
    return out


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
            # A tree that does not reconcile is not an answer: drop the suite's
            # per-feature data rather than publish counts from a mis-parsed tree.
            # This catches the miscounts that change the LEAF SET -- occurrence
            # counting and the dropped bare node both did. It does NOT catch a
            # mis-ATTRIBUTION: reading tags instead of the union moves 27 leaves
            # between features and still reconciles perfectly. Tests, not this
            # gate, are what guard attribution.
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
            # The tests a link should reach come from `extract_coverage`, NOT from
            # the tag route above: a leaf is credited to the UNION of its own tags
            # and the enclosing `Fxxxx` node, which is the semantics the coverage
            # page uses. Reading tags alone drops 27 attributions on
            # 5.175-intensive-care-release.43783 and loses F01010 entirely, so a
            # tags-only list would disagree with the coverage page and omit tests.
            names = leaf_names(behaviors)
            reach = node_leaf_uids(behaviors)
            for code, by_uid in extract_coverage(behaviors).items():
                entry = features.setdefault(code, {}).setdefault(
                    suite, {"uid": None, "count": 0, "tagged": 0})
                # EVERY attributed test, linkable or not. `uid` is None for a
                # leaf the report gives no uid (`_leaf_features` keys those by
                # name): it cannot be deep-linked, but it still carries the
                # feature, so it must count toward `attributed` -- filtering it
                # out here made `attributed` absent whenever nothing at all was
                # linkable, and the page then fell back to a different number.
                entry["_tests"] = [{"uid": u if u in names else None,
                                    "name": names.get(u, ""), "status": st}
                                   for u, st in by_uid.items()]
                # `_leaf_features` and `node_leaf_uids` key a leaf the same way
                # (uid, else name), so these compare directly -- including the
                # uid-less leaves, which a linkable-only check would have let a
                # node "reach" by simply not looking at them.
                entry["_attr_keys"] = set(by_uid)
                entry["_node_uids"] = reach.get(entry.get("uid")) or set()
            # `extract_features` indexes one node under BOTH its full name
            # ("F00700 Invoicing") and its F-code alias, but `extract_coverage`
            # is keyed by F-code, so only the alias got a verdict above. Without
            # this, `?feature=F00700 Invoicing` fell through to the legacy guess
            # (`count >= tagged`, and `tagged` is 0 on a name key, so always
            # true) and redirected into the very node its F-code twin knows is
            # short -- the exact defect this whole change removes.
            for name, per_suite in features.items():
                ent = per_suite.get(suite)
                if not ent or "_tests" in ent:
                    continue
                m = FCODE_RE.match(name)
                if not m:
                    continue                      # not an F-code-prefixed name
                # `_canonical_fcode` is a no-op on this match (FCODE_RE cannot
                # capture the `_` spelling) and `not twin` is unreachable, since
                # `extract_features` always setdefaults the alias in this same
                # suite. Both are kept to mirror `_leaf_features`, which
                # canonicalises the identical regex match, and to fail soft
                # rather than raise if either assumption ever changes.
                twin = (features.get(_canonical_fcode(m.group(1))) or {}).get(suite)
                if not twin or "_tests" not in twin:
                    continue          # that F-code has no attributed tests
                # The attributed SET belongs to the F-code, so it is shared; the
                # REACH is a property of this key's own node, so it is looked up
                # per key. Requiring the twin's uid to match left a second node
                # with the same F-code prefix ("F00700 Billing" beside "F00700
                # Invoicing") with no verdict at all, falling back to the legacy
                # guess and redirecting into a node holding 2 of 3 tests.
                ent["_tests"] = twin["_tests"]
                ent["_attr_keys"] = twin["_attr_keys"]
                ent["_node_uids"] = reach.get(ent["uid"]) or set()
        spath = os.path.join(build_dir, "allure", suite, "data", "suites.json")
        if os.path.isfile(spath):
            with open(spath, encoding="utf-8") as f:
                for name, (uid, count) in extract_specs(json.load(f)).items():
                    specs.setdefault(name, {})[suite] = {"uid": uid, "count": count}
    _attach_test_links(features)
    return features, specs

def _attach_test_links(features):
    """Publish per-test deep links where the node link cannot stand in for them.

    Two cases, measured on 5.175-new-dawn-release.44677:
      * NO Behaviours node at all -- 83 feature/suite pairs covering 339 tests.
        The page could previously only say "no linkable node".
      * A node reaching FEWER tests than are attributed to the feature -- worse
        than the first case, because the link LOOKS complete: F00700 in cucumber
        opens a node holding 1 test while 115 carry the tag.

    `attributed` is the size of the union set from `extract_coverage`. It is
    published on EVERY entry with coverage, including complete ones that need no
    list -- it is the single number the PERMALINK page uses for "how big is this
    suite", and making it conditional is what let two consumers derive it from
    different fields and disagree. Note it is NOT reconciled against
    failures.json the way `build_coverage` is, so for a suite whose tree does not
    reconcile the coverage page drops the suite while this index still carries an
    `attributed` for it. The two pages can therefore disagree on a mis-parsed
    suite; only the coverage page treats that as a reason to say nothing. The page must therefore NOT read its presence as
    "incomplete" -- the presence of the `tests` KEY is that signal, and it is
    written (possibly as an empty list) for every entry the node falls short of.

    Completeness is decided on uid overlap, never by comparing `count` with
    `tagged`. `count` is `_count_leaves` on the node: it includes untagged
    siblings, and counts a test twice when cucumber lists it under both the
    .feature file and Epic -> Feature. A node of 5 untagged leaves would
    otherwise look like it covered 3 attributed tests it does not contain.

    Where the node already reaches every attributed test, no `tests` key is
    published (`attributed` still is):
    attaching the list unconditionally grew permalinks.json by ~70% (+117 KB)
    against ~19% (+32 KB) for the cases that need it.
    """
    for entry in features.values():
        for e in entry.values():
            # BOTH scratch keys come off first, before any early exit -- leaving
            # one behind publishes build-time state into permalinks.json.
            tests = e.pop("_tests", None)
            reached = e.pop("_node_uids", None) or set()
            attr_keys = e.pop("_attr_keys", None) or set()
            if not tests:
                continue
            # ALWAYS publish the size of the attributed set, even when the node
            # reaches all of it and no list is needed. It is one integer per
            # entry (~11% on the published file) and it is what makes the page's
            # "how big is this suite" answer single-sourced: every earlier
            # revision had two consumers deriving it from different fields
            # (`count`, `tagged`) and disagreeing with each other.
            e["attributed"] = len(tests)
            # `e.get("uid")` is redundant -- a node-less entry has an empty
            # `reached` and a non-empty `attr_keys`, so the subset test is
            # already False -- but it is kept for the same reason as the guards
            # above: it states the invariant (only a node-backed entry can be
            # complete) rather than relying on two other facts to imply it.
            if e.get("uid") and attr_keys <= reached:
                continue          # the node link already opens every one of them
            # Past here the node is KNOWN not to reach everything, so the `tests`
            # key is written unconditionally -- that key's presence is the signal
            # the page reads. Writing it only when the list is non-empty let an
            # entry whose attributed tests are all unlinkable look complete.
            linkable = [t for t in tests if t.get("uid")]
            # A failure is what a reader follows the link for, so it must survive
            # the cap: order failed/broken first, then the rest, each in tree order.
            rank = {"failed": 0, "broken": 1}
            ordered = sorted(linkable, key=lambda t: rank.get(t.get("status"), 2))
            e["tests"] = ordered[:MAX_LINKED_TESTS]

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
