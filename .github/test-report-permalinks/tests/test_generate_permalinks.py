import json, os, sys, pathlib
HERE = pathlib.Path(__file__).resolve().parent
sys.path.insert(0, str(HERE.parent))
import generate_permalinks as gp

FIX = HERE / "fixtures"

def _layout(tmp_path, branch, version):
    """Build a fake server tree: branches/{branch}/builds/{version}/allure/{suite}/data/..."""
    bdir = tmp_path / "branches" / branch / "builds" / version / "allure"
    for suite, fix in [("cucumber", "behaviors_cucumber.json"),
                       ("mobile-webui", "suites_mobile-webui.json"),
                       ("frontend-webui", "suites_frontend-webui.json")]:
        d = bdir / suite / "data"
        d.mkdir(parents=True)
        # behaviors.json for cucumber; suites.json for the playwright suites
        name = "behaviors.json" if suite == "cucumber" else "suites.json"
        (d / name).write_text((FIX / fix).read_text(encoding="utf-8"), encoding="utf-8")
    return tmp_path

def test_feature_indexed_by_fcode(tmp_path):
    base = _layout(tmp_path, "new-dawn-uat", "v1")
    feats, specs = gp.build_index(str(base / "branches" / "new-dawn-uat" / "builds" / "v1"))
    # a known F-code present in the cucumber behaviors fixture
    assert "F00102" in feats
    assert "cucumber" in feats["F00102"]
    assert len(feats["F00102"]["cucumber"]["uid"]) == 32
    assert feats["F00102"]["cucumber"]["count"] >= 1

def test_spec_indexed_by_path(tmp_path):
    base = _layout(tmp_path, "new-dawn-uat", "v1")
    feats, specs = gp.build_index(str(base / "branches" / "new-dawn-uat" / "builds" / "v1"))
    key = "spec/manufacturing/receiving_by_products.spec.js"
    assert key in specs
    assert "mobile-webui" in specs[key]
    assert len(specs[key]["mobile-webui"]["uid"]) == 32

def test_unknown_key_absent(tmp_path):
    base = _layout(tmp_path, "new-dawn-uat", "v1")
    feats, specs = gp.build_index(str(base / "branches" / "new-dawn-uat" / "builds" / "v1"))
    assert "F99999" not in feats
    assert "spec/does/not/exist.spec.js" not in specs

def test_main_writes_versioned_json(tmp_path):
    base = _layout(tmp_path, "new-dawn-uat", "v1")
    gp.main(["prog", "new-dawn-uat", "v1", str(base)])
    out = json.loads((base / "branches" / "new-dawn-uat" / "permalinks.json").read_text(encoding="utf-8"))
    assert out["version"] == "v1"
    assert out["features"] and out["specs"]


def test_main_output_is_world_readable(tmp_path):
    """The published file is served by the web server (a different user than the CI
    publisher), so it MUST be group+other readable. tempfile.mkstemp creates 0600 —
    the generator must widen it, else nginx returns 403 (file present but unreadable)."""
    base = _layout(tmp_path, "new-dawn-uat", "v1")
    gp.main(["prog", "new-dawn-uat", "v1", str(base)])
    out = base / "branches" / "new-dawn-uat" / "permalinks.json"
    mode = out.stat().st_mode & 0o777
    assert mode & 0o044 == 0o044, f"permalinks.json must be group+other readable, got {oct(mode)}"


# --- extract_features: F-code alias path (a node named "F#### Description") ---
def test_feature_aliased_by_fcode_when_node_name_has_description():
    """A feature node named 'F67042 HU receipt date' is reachable by BOTH the full
    node name AND the short F-code alias — exercises the setdefault() alias branch."""
    root = {"children": [
        {"name": "E2300 Attributes", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F67042 HU receipt date", "uid": "a".ljust(32, "0"),
             "children": [{"name": "scenario 1"}, {"name": "scenario 2"}]},
        ]},
    ]}
    feats = gp.extract_features(root)
    assert feats["F67042 HU receipt date"] == ("a".ljust(32, "0"), 2)   # full-name key
    assert feats["F67042"] == ("a".ljust(32, "0"), 2)                    # F-code alias


# --- extract_features: a node WITHOUT an F-code prefix is indexed by name only ---
def test_feature_node_without_fcode_yields_no_fcode_key_from_the_NODE_route():
    """The node route is keyed off the Allure grouping node NAME: a node whose name
    carries no F-code contributes no F-code key. The TAG route (below) is what covers
    the far commoner case where the F-code is on the test rather than the node; these
    leaves carry no tags, so neither route produces one here."""
    root = {"children": [
        {"name": "E2300 Attributes", "uid": "e".ljust(32, "0"), "children": [
            {"name": "HU_DateReceived attribute population", "uid": "b".ljust(32, "0"),
             "children": [{"name": "scenario"}]},
        ]},
    ]}
    feats = gp.extract_features(root)
    assert "HU_DateReceived attribute population" in feats
    assert not any(k.startswith("F") for k in feats)


# --- _count_leaves: leaf (children=None) -> 1; empty group ([]) -> 0 ---
def test_count_leaves_semantics():
    assert gp._count_leaves({"name": "leaf"}) == 1                       # no children key
    assert gp._count_leaves({"name": "leaf", "children": None}) == 1     # explicit null
    assert gp._count_leaves({"name": "empty group", "children": []}) == 0
    assert gp._count_leaves({"children": [{"name": "a"}, {"name": "b"}]}) == 2


# --- extract_specs: a .feature-named group node is indexed (not only .spec.js) ---
def test_spec_indexed_for_feature_extension():
    root = {"children": [
        {"name": "cucumber", "uid": "x".ljust(32, "0"), "children": [
            {"name": "receiving.feature", "uid": "c".ljust(32, "0"),
             "children": [{"name": "step"}]},
        ]},
    ]}
    specs = gp.extract_specs(root)
    assert specs["receiving.feature"] == ("c".ljust(32, "0"), 1)


# --- the TAG route: where the F-code actually lives -------------------------

def _leaf(uid, *tags):
    return {"name": "t-" + uid, "uid": uid, "tags": list(tags)}


def test_a_tag_carries_the_fcode_even_with_no_grouping_node():
    """`allure.tag('F00230')` creates no Behaviours node, so the node route sees
    nothing. This is the case that made a permalink show 1 test of 141."""
    root = {"children": [
        {"name": "some.feature", "uid": "s".ljust(32, "0"), "children": [
            _leaf("1".ljust(32, "0"), "F00700: Invoicing", "F00700"),
            _leaf("2".ljust(32, "0"), "F00700"),
        ]},
    ]}
    assert gp.extract_tagged_features(root) == {"F00700": 2}
    assert gp.extract_features(root) == {"some.feature": ("s".ljust(32, "0"), 2)}


def test_a_test_listed_twice_in_the_tree_is_counted_once():
    """Cucumber lists every test under its .feature file AND again under
    Epic -> Feature. Counting occurrences inflated F00230 from 190 to 293."""
    leaf = _leaf("d".ljust(32, "0"), "F00230")
    root = {"children": [
        {"name": "some.feature", "uid": "s".ljust(32, "0"), "children": [leaf]},
        {"name": "E0105 Picking", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F00230 MobileUI Picking", "uid": "f".ljust(32, "0"), "children": [leaf]},
        ]},
    ]}
    assert gp.extract_tagged_features(root) == {"F00230": 1}


def test_the_named_and_bare_tag_of_one_test_count_once():
    """The specs emit BOTH `F00900: Business Partner` and a standalone `F00900`."""
    root = {"children": [{"name": "g", "uid": "s".ljust(32, "0"), "children": [
        _leaf("a".ljust(32, "0"), "F00900: Business Partner", "F00900", "en_US")]}]}
    assert gp.extract_tagged_features(root) == {"F00900": 1}


def test_the_underscore_and_dot_subfeature_spellings_index_as_one():
    """Cucumber tags `F5001_1`, the Playwright specs tag `F5001.1` -- one subfeature."""
    root = {"children": [{"name": "g", "uid": "s".ljust(32, "0"), "children": [
        _leaf("a".ljust(32, "0"), "F5001_1"),
        _leaf("b".ljust(32, "0"), "F5001.1: Consolidate CU-TU Allocation")]}]}
    assert gp.extract_tagged_features(root) == {"F5001.1": 2}


def test_an_unrecognised_suffix_is_dropped_not_folded_into_the_parent():
    """An `F00138_se203`-shaped tag is NOT a known convention: measured
    2026-09-12, no F-code tag in build 5.175-intensive-care-release.43783
    carries a non-numeric suffix (2182 tag occurrences across the three
    suites), and no test annotation on `new_dawn_uat`,
    `intensive_care_release` or `intensive_care_hotfix` uses the form. It IS a
    real identifier in another namespace -- it names doc issues, e.g.
    `F00652_se203` -- so it is pinned here as the generic unrecognised-suffix
    case, to fix
    the behaviour if the form ever appears: the tag is skipped, so nothing is
    silently credited to `F00138`. Folding it into the parent would be the
    subfeature-rollup defect in a new place; dropping it is the safe default
    because it under-reports visibly rather than over-reporting invisibly."""
    root = {"children": [{"name": "g", "uid": "s".ljust(32, "0"), "children": [
        _leaf("a".ljust(32, "0"), "F00138_se203")]}]}
    assert gp.extract_tagged_features(root) == {}


def test_a_tag_only_feature_is_published_with_a_null_uid(tmp_path):
    """It must appear in the index -- with nothing to deep-link to -- rather than
    look absent. Previously such a feature was simply not in permalinks.json.

    It now ALSO carries `tests`: with no Behaviours node there is no node uid to
    link at, so the per-test uids are the only way to reach the tests at all."""
    bdir = tmp_path / "allure" / "cucumber" / "data"
    bdir.mkdir(parents=True)
    (bdir / "behaviors.json").write_text(json.dumps({"children": [
        {"name": "some.feature", "uid": "s".ljust(32, "0"), "children": [
            _leaf("a".ljust(32, "0"), "F12345")]}]}), encoding="utf-8")
    feats, _ = gp.build_index(str(tmp_path))
    assert feats["F12345"]["cucumber"] == {
        "uid": None, "count": 0, "tagged": 1, "attributed": 1,
        "tests": [{"uid": "a".ljust(32, "0"), "name": "t-" + "a".ljust(32, "0"),
                   "status": "unknown"}]}


def test_a_node_backed_feature_keeps_its_uid_and_gains_the_tagged_count(tmp_path):
    bdir = tmp_path / "allure" / "cucumber" / "data"
    bdir.mkdir(parents=True)
    (bdir / "behaviors.json").write_text(json.dumps({"children": [
        {"name": "E0105 Picking", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F00230 MobileUI Picking", "uid": "f".ljust(32, "0"), "children": [
                _leaf("a".ljust(32, "0"), "F00230"),
                _leaf("b".ljust(32, "0"), "F00230")]}]}]}), encoding="utf-8")
    feats, _ = gp.build_index(str(tmp_path))
    # `attributed` rides along on EVERY entry with coverage, including complete
    # ones that need no test list -- it is the single number the page uses for
    # "how big is this suite", so it must not be conditional.
    assert feats["F00230"]["cucumber"] == {
        "uid": "f".ljust(32, "0"), "count": 2, "tagged": 2, "attributed": 2}


def test_canonical_fcode_rewrites_only_a_numeric_subfeature_separator():
    r"""Pins `_canonical_fcode` DIRECTLY.

    `test_an_unrecognised_suffix_is_dropped_not_folded_into_the_parent` above
    goes through TAG_FCODE_RE, which rejects `F00138_se203` before this
    function is reached — so it passes even if this guard is deleted. Without
    this test the guard is unpinned, which is how a defence quietly stops
    defending.

    The separator this guard DOES exist for is real and in daily use: measured
    2026-09-12 on build 5.175-intensive-care-release.43783, cucumber writes the
    subfeature with `_` (7 distinct tests) while both Playwright suites write
    it with `.` (33: 10 frontend-webui, 23 mobile-webui). Without the rewrite
    those index as two different features.

    Note an underscore after the F-number is USUALLY part of a name, not a
    subfeature: cucumber labels features `@allure.label.feature:F00701_Sales_
    Invoice_Candidates`. Measured 2026-09-12, distinct ids in such labels in
    `*.feature` files: 70 on intensive_care_release, 71 on new_dawn_uat, 40 on
    intensive_care_hotfix. (Counting every `F\d+_Word` token in those files
    instead gives 78/79/41 -- the extras are scenario ids like
    `@Id:F36025_sql_default_resolves` and test data like `F00127_E2E`, not
    feature labels. The predicate matters, so it is stated rather than implied.)

    None of them reach THIS parser in that form: Allure renders the label as a
    node name with the underscores turned into spaces (`F00701 Sales Invoice
    Candidates`), and FCODE_RE's `\b` would not match the underscore form
    anyway. The dot-rewrite in `_canonical_fcode` is a separate guard, and it
    fires only when a DIGIT follows the underscore -- across all three branches
    that is the single genuine subfeature `F5001_1`.

    Those are DISTINCT TESTS, not tag occurrences — the occurrence counts are
    14 and 42, inflated by the same listed-twice duplication that
    `extract_tagged_features` de-duplicates above. Stating a tag-occurrence
    count as if it were a test count is the very defect this module exists to
    avoid, so the unit is named here rather than left to the reader."""
    assert gp._canonical_fcode("F5001_1") == "F5001.1"
    assert gp._canonical_fcode("F5001.1") == "F5001.1"
    assert gp._canonical_fcode("F5001") == "F5001"
    assert gp._canonical_fcode("F00138_se203") == "F00138_se203"
    assert gp._canonical_fcode("F00762.1_is184") == "F00762.1_is184"


# --- the coverage answer: same semantics as the Knowledge Map recipe --------

def _cov_tree(*leaves):
    return {"children": [{"name": "some.feature", "uid": "s".ljust(32, "0"),
                          "children": list(leaves)}]}


def _cleaf(uid, status, *tags):
    return {"name": "t-" + uid, "uid": uid, "status": status, "tags": list(tags)}


def test_coverage_counts_a_test_once_though_the_tree_lists_it_twice():
    leaf = _cleaf("d".ljust(32, "0"), "passed", "F00230")
    root = {"children": [
        {"name": "some.feature", "uid": "s".ljust(32, "0"), "children": [leaf]},
        {"name": "E0105 Picking", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F00230 MobileUI Picking", "uid": "f".ljust(32, "0"), "children": [leaf]}]},
    ]}
    cov = gp.extract_coverage(root)
    assert cov == {"F00230": {"d".ljust(32, "0"): "passed"}}


def test_coverage_reads_the_fcode_from_a_tag_when_no_node_carries_it():
    cov = gp.extract_coverage(_cov_tree(_cleaf("a".ljust(32, "0"), "passed", "F00700: Invoicing",
                                               "F00700")))
    assert cov == {"F00700": {"a".ljust(32, "0"): "passed"}}


def test_coverage_does_not_credit_a_parent_with_its_subfeatures_tests():
    """`F5001_1` tagged under a node Allure renders as `F5001 1 …` must not give
    `F5001` the child's tests."""
    root = {"children": [{"name": "F5001 1 Consolidate", "uid": "n".ljust(32, "0"), "children": [
        _cleaf("a".ljust(32, "0"), "passed", "F5001_1")]}]}
    cov = gp.extract_coverage(root)
    assert set(cov) == {"F5001.1"}


def test_coverage_keeps_each_tests_status():
    cov = gp.extract_coverage(_cov_tree(
        _cleaf("a".ljust(32, "0"), "passed", "F1000"),
        _cleaf("b".ljust(32, "0"), "failed", "F1000")))
    assert cov["F1000"] == {"a".ljust(32, "0"): "passed", "b".ljust(32, "0"): "failed"}


def test_a_suite_that_ran_but_publishes_no_allure_report_is_absent(tmp_path):
    (tmp_path / "failures.json").write_text(json.dumps({"suites": {
        "cucumber": {"total": 2}, "junit/backend": {"total": 11077}}}), encoding="utf-8")
    d = tmp_path / "allure" / "cucumber" / "data"
    d.mkdir(parents=True)
    (d / "behaviors.json").write_text(json.dumps(_cov_tree(
        _cleaf("a".ljust(32, "0"), "passed", "F1000"),
        _cleaf("b".ljust(32, "0"), "passed", "F1000"))), encoding="utf-8")
    cov = gp.build_coverage(str(tmp_path))
    assert cov["suites"]["junit/backend"] == {
        "state": "absent", "ran": 11077, "tests": None, "labelled": None}
    assert cov["suites"]["cucumber"]["state"] == "measured"
    assert cov["suites"]["cucumber"]["ran"] == 2


def test_a_suite_with_no_report_and_no_reported_total_is_unknown_not_absent(tmp_path):
    """Nothing was verified about it — saying `absent` would claim knowledge."""
    (tmp_path / "failures.json").write_text(json.dumps({"suites": {}}), encoding="utf-8")
    cov = gp.build_coverage(str(tmp_path))
    assert cov["suites"]["cucumber"]["state"] == "unknown"


def test_build_coverage_is_published_inside_permalinks_json(tmp_path):
    (tmp_path / "failures.json").write_text(json.dumps({"suites": {"cucumber": {"total": 1}}}),
                                            encoding="utf-8")
    d = tmp_path / "allure" / "cucumber" / "data"
    d.mkdir(parents=True)
    (d / "behaviors.json").write_text(json.dumps(_cov_tree(
        _cleaf("a".ljust(32, "0"), "passed", "F1000"))), encoding="utf-8")
    base = tmp_path.parent / "srv"
    bdir = base / "branches" / "b" / "builds" / "v1"
    bdir.parent.mkdir(parents=True)
    bdir.symlink_to(tmp_path, target_is_directory=True)
    gp.main(["prog", "b", "v1", str(base)])
    out = json.loads((base / "branches" / "b" / "permalinks.json").read_text(encoding="utf-8"))
    assert out["coverage"]["features"]["F1000"]["cucumber"]["tests"] == 1
    assert out["features"], "the existing permalink index must still be published"


# --- the figures the page actually prints, and the reconciliation gate ------
# Every test below was written because the mutation it describes SURVIVED the
# suite as it stood on 2026-09-12. A published number that no test constrains
# is a number free to drift.


def _suite_build(tmp_path, leaves, total, suite="cucumber"):
    (tmp_path / "failures.json").write_text(
        json.dumps({"suites": {suite: {"total": total}}}), encoding="utf-8")
    d = tmp_path / "allure" / suite / "data"
    d.mkdir(parents=True)
    (d / "behaviors.json").write_text(json.dumps(_cov_tree(*leaves)), encoding="utf-8")
    return gp.build_coverage(str(tmp_path))


def test_labelled_counts_only_the_tests_carrying_a_feature_id(tmp_path):
    """`labelled` is a headline figure on the page ("N carrying an F-id") and
    was asserted nowhere: replacing it with a constant kept the suite green."""
    cov = _suite_build(tmp_path, [
        _cleaf("a".ljust(32, "0"), "passed", "F1000"),
        _cleaf("b".ljust(32, "0"), "passed", "F1000"),
        _cleaf("c".ljust(32, "0"), "passed"),          # no tag: parsed, not labelled
    ], total=3)
    assert cov["suites"]["cucumber"]["labelled"] == 2
    assert cov["suites"]["cucumber"]["parsed"] == 3
    assert cov["suites"]["cucumber"]["tests"] == 3


def test_a_tree_that_does_not_reconcile_is_unknown_and_publishes_no_features(tmp_path):
    """The safety gate. Every miscount this code has had showed up first as the
    parsed tree disagreeing with failures.json, so a disagreement must refuse to
    answer rather than publish per-feature counts from a mis-parsed tree."""
    cov = _suite_build(tmp_path, [
        _cleaf("a".ljust(32, "0"), "passed", "F1000")], total=99)
    assert cov["suites"]["cucumber"]["state"] == "unknown"
    assert cov["suites"]["cucumber"]["tests"] is None
    assert cov["suites"]["cucumber"]["parsed"] == 1
    assert cov["suites"]["cucumber"]["ran"] == 99
    # The reason is rendered to the reader, so the two numbers must not be
    # transposed. Asserting only that "99" appears passes with them swapped.
    assert cov["suites"]["cucumber"]["reason"] == (
        "parsed 1 distinct test(s) but failures.json reports 99")
    assert cov["features"] == {}, "no per-feature data may survive a failed reconciliation"


def test_reconciliation_compares_distinct_tests_not_labelled_ones(tmp_path):
    """`labelled` is a SUBSET of `parsed`, so reconciling `labelled` against the
    reported total would fail on any suite holding an unannotated test — i.e.
    all of them. This build has one unannotated test and must still reconcile."""
    cov = _suite_build(tmp_path, [
        _cleaf("a".ljust(32, "0"), "passed", "F1000"),
        _cleaf("b".ljust(32, "0"), "passed"),
    ], total=2)
    assert cov["suites"]["cucumber"]["state"] == "measured"


def test_a_test_listed_twice_reconciles_once(tmp_path):
    """Allure lists a cucumber test under both its .feature file and its Epic.
    If the reconciliation counted occurrences it would see 2 against a reported
    1 and wrongly refuse the whole suite."""
    dup = "a".ljust(32, "0")
    cov = _suite_build(tmp_path, [
        _cleaf(dup, "passed", "F1000"), _cleaf(dup, "passed", "F1000")], total=1)
    assert cov["suites"]["cucumber"]["state"] == "measured"
    assert cov["suites"]["cucumber"]["parsed"] == 1
    assert cov["features"]["F1000"]["cucumber"]["tests"] == 1


def test_a_no_allure_suite_is_only_reported_when_it_actually_ran(tmp_path):
    """"No test" means "no cucumber and no Playwright test" only because these
    suites are shown WITH their real totals. Reporting one that failures.json
    never mentioned would invent a suite; the guard was unpinned."""
    (tmp_path / "failures.json").write_text(
        json.dumps({"suites": {"junit/backend": {"total": 11077}}}), encoding="utf-8")
    cov = gp.build_coverage(str(tmp_path))
    assert cov["suites"]["junit/backend"] == {
        "state": "absent", "ran": 11077, "tests": None, "labelled": None}
    assert "junit/camel" not in cov["suites"], "a suite with no reported total is not invented"


def test_a_non_integer_total_is_not_a_total(tmp_path):
    """`failures.json` is read off a live host; a null/string total must be
    treated as absent rather than compared against or published."""
    (tmp_path / "failures.json").write_text(json.dumps({"suites": {
        "junit/backend": {"total": None}, "junit/camel": {"total": "250"}}}), encoding="utf-8")
    cov = gp.build_coverage(str(tmp_path))
    assert "junit/backend" not in cov["suites"]
    assert "junit/camel" not in cov["suites"]


def test_the_union_of_tag_and_node_is_taken_not_one_or_the_other(tmp_path):
    """A leaf tagged `F00700` under a node named `F01010 …` covers BOTH. Reading
    tags INSTEAD of the node (which this module's docstring wrongly described)
    silently drops 27 attributions on 5.175-intensive-care-release.43783 while
    leaving every headline figure identical — so only a test shaped like this
    can catch it."""
    root = {"children": [{"name": "E1 Epic", "uid": "e".ljust(32, "0"), "children": [
        {"name": "F01010 Something", "uid": "f".ljust(32, "0"), "children": [
            _cleaf("a".ljust(32, "0"), "passed", "F00700")]}]}]}
    per_feature = gp.extract_coverage(root)
    assert set(per_feature) == {"F00700", "F01010"}


def test_a_node_named_for_a_subfeature_is_read_as_that_subfeature(tmp_path):
    """Pins FCODE_RE's `(?:\\.\\d+)?` group DIRECTLY.

    Dropping it survived all 31 tests while collapsing `F01010.3` into
    `F01010` — silently moving 8 real attributions to the parent, which is the
    subfeature-rollup defect this module exists to prevent. Nothing else
    exercises the node route with a dotted name, because the tag route usually
    supplies the subfeature first.
    """
    root = {"children": [{"name": "F01010.3 Payment Allocation",
                          "uid": "f".ljust(32, "0"), "children": [
        {"name": "t1", "uid": "a".ljust(32, "0"), "status": "passed"}]}]}
    assert set(gp.extract_coverage(root)) == {"F01010.3"}


def test_the_parent_guard_requires_the_dot_not_a_bare_prefix():
    """`F1200` must not be suppressed by a leaf tagged `F12000`.

    The guard is `startswith(inherited + ".")`; written as `startswith(inherited)`
    it survives every other test, because no fixture pairs two F-codes where one
    id is a string prefix of the other. Real ids do collide that way.
    """
    root = {"children": [{"name": "F1200 Parent", "uid": "f".ljust(32, "0"), "children": [
        {"name": "t", "uid": "a".ljust(32, "0"), "status": "passed", "tags": ["F12000"]}]}]}
    found = gp.extract_coverage(root)
    assert set(found) == {"F1200", "F12000"}, \
        "F12000 is a different feature, not a subfeature of F1200"


# --- per-test deep links: published ONLY where the node link falls short -----

def _behaviors(tmp_path, suite, root):
    d = tmp_path / "allure" / suite / "data"
    d.mkdir(parents=True)
    (d / "behaviors.json").write_text(json.dumps(root), encoding="utf-8")
    return tmp_path


def test_no_node_at_all_publishes_every_tagged_test_as_a_link(tmp_path):
    """The case the resolver could only answer with 'no linkable node'."""
    base = _behaviors(tmp_path, "frontend-webui", {"children": [
        {"name": "E0100: Sales", "uid": "e".ljust(32, "0"), "children": [
            {"name": "Complete Order-to-Cash", "uid": "s".ljust(32, "0"), "children": [
                _leaf("1".ljust(16, "0"), "F00105"),
                _leaf("2".ljust(16, "0"), "F00105")]}]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F00105"]["frontend-webui"]
    assert e["uid"] is None and e["tagged"] == 2
    assert [t["uid"] for t in e["tests"]] == ["1".ljust(16, "0"), "2".ljust(16, "0")]


def test_a_node_that_covers_everything_publishes_no_test_list(tmp_path):
    """The node link already reaches every tagged test, so a list is pure weight —
    attaching it unconditionally grew the published index by ~70%."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "E0105 Picking", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F00230 MobileUI Picking", "uid": "f".ljust(32, "0"), "children": [
                _leaf("a".ljust(32, "0"), "F00230"),
                _leaf("b".ljust(32, "0"), "F00230")]}]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F00230"]["cucumber"]
    assert e["uid"] == "f".ljust(32, "0") and e["count"] == 2 and e["tagged"] == 2
    assert "tests" not in e


def test_a_node_covering_fewer_than_tagged_publishes_the_list(tmp_path):
    """Worse than having no node: the link LOOKS complete. Measured on the real
    report, F00700/cucumber links to a node holding 1 test while 115 are tagged."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "E1 Epic", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F00700 Invoicing", "uid": "f".ljust(32, "0"), "children": [
                _leaf("a".ljust(32, "0"), "F00700")]}]},
        {"name": "other.feature", "uid": "o".ljust(32, "0"), "children": [
            _leaf("b".ljust(32, "0"), "F00700"),
            _leaf("c".ljust(32, "0"), "F00700")]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F00700"]["cucumber"]
    assert e["count"] == 1 and e["tagged"] == 3, "node shows fewer than carry the tag"
    assert [t["uid"] for t in e["tests"]] == [
        "a".ljust(32, "0"), "b".ljust(32, "0"), "c".ljust(32, "0")]


def test_the_published_test_list_is_capped(tmp_path):
    """`tagged` keeps the true total, so the page can say 'showing N of M'."""
    leaves = [_leaf(str(i).rjust(32, "0"), "F00900") for i in range(gp.MAX_LINKED_TESTS + 7)]
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "g.feature", "uid": "s".ljust(32, "0"), "children": leaves}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F00900"]["cucumber"]
    assert e["tagged"] == gp.MAX_LINKED_TESTS + 7
    assert len(e["tests"]) == gp.MAX_LINKED_TESTS


def test_a_test_without_a_uid_is_not_published_as_a_link(tmp_path):
    """It cannot be linked. `tagged` still counts it, so the page reports
    'showing N of M' instead of implying the list is complete."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "g.feature", "uid": "s".ljust(32, "0"), "children": [
            {"name": "no-uid test", "tags": ["F01234"]},
            _leaf("b".ljust(32, "0"), "F01234")]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F01234"]["cucumber"]
    assert e["tagged"] == 2
    assert [t["uid"] for t in e["tests"]] == ["b".ljust(32, "0")]


def test_leaf_names_keys_only_real_uids(tmp_path):
    """`_leaf_features` falls back to the NAME when a leaf has no uid. Publishing
    that as a link would build `#testresult/<a test name>`, which resolves to
    nothing — so this map keys on real uids only and doubles as the linkable set."""
    root = {"children": [{"name": "g", "uid": "s".ljust(32, "0"), "children": [
        _leaf("a".ljust(32, "0"), "F00900"),
        {"name": "leaf with no uid", "tags": ["F00900"]}]}]}
    assert gp.leaf_names(root) == {"a".ljust(32, "0"): "t-" + "a".ljust(32, "0")}
    # extract_coverage still credits both — the uid-less one just cannot be linked
    assert len(gp.extract_coverage(root)["F00900"]) == 2


def test_no_build_time_scratch_key_survives_into_the_published_file(tmp_path):
    """Every `_`-prefixed key is build-time state. They are popped on the early
    exits too — leaving one behind publishes internals (and a `set`, which is not
    JSON-serialisable, so `main()` would simply crash)."""
    base = _layout(tmp_path, "new-dawn-uat", "v1")
    gp.main(["prog", "new-dawn-uat", "v1", str(base)])
    out = json.loads((base / "branches" / "new-dawn-uat" / "permalinks.json").read_text(
        encoding="utf-8"))
    leaked = sorted({k for e in out["features"].values() for s in e.values() for k in s
                     if k.startswith("_")})
    assert leaked == [], f"build-time scratch leaked into permalinks.json: {leaked}"


def test_a_long_test_name_is_truncated(tmp_path):
    """Names are free text; one pathological name must not bloat the index."""
    long_name = "x" * (gp.NAME_MAX + 50)
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "g.feature", "uid": "s".ljust(32, "0"), "children": [
            {"name": long_name, "uid": "a".ljust(32, "0"), "tags": ["F02222"]}]}]})
    feats, _ = gp.build_index(str(base))
    assert len(feats["F02222"]["cucumber"]["tests"][0]["name"]) == gp.NAME_MAX


def test_a_node_reaching_every_attributed_test_publishes_nothing_even_when_it_holds_more(tmp_path):
    """`count` is not the measure: a node can hold untagged siblings, so count >
    attributed while still reaching all of them. Nothing to publish."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "F03333 Thing", "uid": "f".ljust(32, "0"), "children": [
            _leaf("a".ljust(32, "0"), "F03333"),
            {"name": "untagged sibling", "uid": "b".ljust(32, "0")}]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F03333"]["cucumber"]
    assert e["count"] == 2 and e["tagged"] == 1
    assert "tests" not in e, "the node opens every attributed test"


def test_a_node_that_does_not_reach_every_attributed_test_publishes_them(tmp_path):
    """The case a `count >= tagged` rule would have hidden.

    The node holds 3 leaves and only 2 tests carry the tag, so `count >= tagged`
    is true and the old rule published nothing — while the node reaches NEITHER
    tagged test. Note the three leaves under `F04444 Thing` are themselves
    attributed to it by inheritance, so all five belong to the feature and the
    node opens only three of them."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "F04444 Thing", "uid": "f".ljust(32, "0"), "children": [
            {"name": "untagged 1", "uid": "p".ljust(32, "0")},
            {"name": "untagged 2", "uid": "q".ljust(32, "0")},
            {"name": "untagged 3", "uid": "r".ljust(32, "0")}]},
        {"name": "other.feature", "uid": "o".ljust(32, "0"), "children": [
            _leaf("a".ljust(32, "0"), "F04444"),
            _leaf("b".ljust(32, "0"), "F04444")]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F04444"]["cucumber"]
    assert e["count"] == 3 and e["tagged"] == 2, "count >= tagged, yet the node reaches neither"
    assert e["attributed"] == 5
    assert sorted(t["uid"] for t in e["tests"]) == sorted(
        [c.ljust(32, "0") for c in "abpqr"]), \
        "every attributed test is published, because the node opens only 3 of the 5"


def test_failed_tests_survive_the_cap(tmp_path):
    """A failure is what a reader follows the link for, so it must not be the
    entry the cap discards."""
    leaves = [{"name": "ok-" + str(i), "uid": str(i).rjust(32, "0"),
               "status": "passed", "tags": ["F06666"]} for i in range(gp.MAX_LINKED_TESTS + 5)]
    leaves.append({"name": "the failure", "uid": "f".ljust(32, "0"),
                   "status": "failed", "tags": ["F06666"]})
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "g.feature", "uid": "s".ljust(32, "0"), "children": leaves}]})
    feats, _ = gp.build_index(str(base))
    published = feats["F06666"]["cucumber"]["tests"]
    assert len(published) == gp.MAX_LINKED_TESTS
    assert published[0]["status"] == "failed", "failures are ordered first"
    assert any(t["name"] == "the failure" for t in published)


def test_a_group_whose_tests_all_lack_a_uid_publishes_an_empty_list(tmp_path):
    """Nothing linkable -> an EMPTY `tests` list, not a missing key.

    The key's presence is what tells the page the node does not reach everything.
    Omitting it here made such an entry indistinguishable from a complete one, so
    the page redirected into a node holding none of these tests."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "g.feature", "uid": "s".ljust(32, "0"), "children": [
            {"name": "no uid one", "tags": ["F07777"]},
            {"name": "no uid two", "tags": ["F07777"]}]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F07777"]["cucumber"]
    assert e["tagged"] == 2 and e["tests"] == []
    assert e["attributed"] == 2, "nothing is linkable, but 2 tests DO carry the feature"
    # the early-exit path must still drop BOTH scratch keys -- moving the pops
    # below `if not tests: continue` would leak them here without failing the
    # main()-based test, whose fixture never takes this path
    assert [k for k in e if k.startswith("_")] == []


def test_a_feature_whose_tests_are_all_unlinkable_still_reports_its_count(tmp_path):
    """The generator publishes {uid: None, count: 0, tagged: N} here. The page
    turns N == 0 into "not found" and N > 0 into an honest "N tests, no grouping
    node" — so `tagged` must survive even when nothing can be linked."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "g.feature", "uid": "s".ljust(32, "0"), "children": [
            {"name": "no uid", "tags": ["F09999"]}]}]})
    feats, _ = gp.build_index(str(base))
    assert feats["F09999"]["cucumber"] == {
        "uid": None, "count": 0, "tagged": 1, "attributed": 1, "tests": []}


def test_an_unlinkable_test_outside_the_node_keeps_the_node_incomplete(tmp_path):
    """The reach check compares EVERY attributed test, not just the linkable ones.

    Checking only linkable tests let a node count as complete by simply not
    looking at a uid-less test it does not contain — and the page would then
    redirect there as though it showed everything."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "F08888 Thing", "uid": "f".ljust(32, "0"), "children": [
            _leaf("a".ljust(32, "0"), "F08888")]},
        {"name": "other.feature", "uid": "o".ljust(32, "0"), "children": [
            {"name": "uid-less, outside the node", "tags": ["F08888"]}]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F08888"]["cucumber"]
    assert e["attributed"] == 2, "both tests carry the feature"
    assert e["tests"], "the node does NOT reach the uid-less one, so publish what we can link"
    assert [t["uid"] for t in e["tests"]] == ["a".ljust(32, "0")]


def test_a_node_backed_entry_with_no_linkable_test_is_still_marked_short(tmp_path):
    """The reach check must run BEFORE the "is anything linkable" check.

    A node exists, but the tests carrying the feature are uid-less and live
    OUTSIDE it. Bailing on "nothing linkable" first published {uid, attributed}
    with no `tests` key — which the page reads as complete, and redirects into a
    node that holds none of them."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        # every leaf under the node is uid-less too, so nothing here is linkable
        {"name": "F06000 Thing", "uid": "f".ljust(32, "0"), "children": [
            {"name": "inside, uid-less"}]},
        {"name": "other.feature", "uid": "o".ljust(32, "0"), "children": [
            {"name": "outside, uid-less, tagged", "tags": ["F06000"]}]}]})
    feats, _ = gp.build_index(str(base))
    e = feats["F06000"]["cucumber"]
    assert e["uid"] == "f".ljust(32, "0"), "the node exists and would be linked"
    assert e["tests"] == [], "nothing linkable — but the key marks the node short"


def test_the_full_name_alias_gets_the_same_verdict_as_its_fcode_key(tmp_path):
    """`extract_features` indexes one node under BOTH its full name and its
    F-code alias, but `extract_coverage` is keyed by F-code alone.

    Without propagation the name key carried no verdict, fell through to the
    legacy `count >= tagged` guess (and `tagged` is 0 on a name key, so it is
    always true) and redirected into the node its F-code twin knows is short."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "E1 Epic", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F00700 Invoicing", "uid": "f".ljust(32, "0"), "children": [
                _leaf("a".ljust(32, "0"), "F00700")]}]},
        {"name": "other.feature", "uid": "o".ljust(32, "0"), "children": [
            _leaf("b".ljust(32, "0"), "F00700"),
            _leaf("c".ljust(32, "0"), "F00700")]}]})
    feats, _ = gp.build_index(str(base))
    code, alias = feats["F00700"]["cucumber"], feats["F00700 Invoicing"]["cucumber"]
    assert alias["uid"] == code["uid"], "same node"
    assert alias["attributed"] == code["attributed"] == 3
    assert [t["uid"] for t in alias["tests"]] == [t["uid"] for t in code["tests"]]


def test_a_spec_file_node_is_not_treated_as_an_fcode_alias(tmp_path):
    """`other.feature` carries no F-code, so there is no feature verdict to
    inherit — asking for that node by name should still just open it."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "other.feature", "uid": "o".ljust(32, "0"), "children": [
            _leaf("b".ljust(32, "0"), "F00700")]}]})
    feats, _ = gp.build_index(str(base))
    assert "tests" not in feats["other.feature"]["cucumber"]
    assert "attributed" not in feats["other.feature"]["cucumber"]


def test_two_nodes_sharing_an_fcode_prefix_each_get_their_own_reach_verdict(tmp_path):
    """The attributed SET belongs to the F-code; the REACH belongs to the node.

    Here the two nodes genuinely differ: "Invoicing" holds only the shared leaf,
    while "Billing" holds that leaf AND a second one it credits by inheritance —
    so Billing reaches everything and Invoicing does not. Handing the alias
    twin's reach to both (rather than looking each node up) would mark Billing
    short and make the page refuse to redirect to a node that does open all of
    its tests."""
    shared = _leaf("l1".ljust(32, "0"), "F00700")      # Allure lists one test twice
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "E1", "uid": "e1".ljust(32, "0"), "children": [
            {"name": "F00700 Invoicing", "uid": "a".ljust(32, "0"), "children": [shared]}]},
        {"name": "E2", "uid": "e2".ljust(32, "0"), "children": [
            {"name": "F00700 Billing", "uid": "b".ljust(32, "0"), "children": [
                shared, {"name": "also billing", "uid": "l2".ljust(32, "0")}]}]}]})
    feats, _ = gp.build_index(str(base))
    inv, bil = feats["F00700 Invoicing"]["cucumber"], feats["F00700 Billing"]["cucumber"]
    assert inv["uid"] != bil["uid"] and inv["attributed"] == bil["attributed"] == 2
    assert "tests" in inv, "Invoicing holds 1 of the 2 attributed tests -> short"
    assert "tests" not in bil, "Billing holds BOTH -> complete, and must still redirect"


def test_a_name_key_whose_fcode_has_no_coverage_is_left_alone(tmp_path):
    """`F5002 Parent`'s only leaf is tagged with the SUBFEATURE `F5002.1`, which
    `_leaf_features` deliberately does not credit to the parent. So `F5002` has
    no attributed tests and no verdict to lend, and the propagation must not
    reach for one — without its guard this raises KeyError, `main()` dies, and
    the branch silently stops publishing an index at all."""
    base = _behaviors(tmp_path, "cucumber", {"children": [
        {"name": "F5002 Parent", "uid": "p".ljust(32, "0"), "children": [
            _leaf("s".ljust(32, "0"), "F5002.1")]}]})
    feats, _ = gp.build_index(str(base))          # must not raise
    parent = feats["F5002 Parent"]["cucumber"]
    assert "attributed" not in parent and "tests" not in parent
    assert feats["F5002.1"]["cucumber"]["attributed"] == 1, "the subfeature keeps its own"
