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
    look absent. Previously such a feature was simply not in permalinks.json."""
    bdir = tmp_path / "allure" / "cucumber" / "data"
    bdir.mkdir(parents=True)
    (bdir / "behaviors.json").write_text(json.dumps({"children": [
        {"name": "some.feature", "uid": "s".ljust(32, "0"), "children": [
            _leaf("a".ljust(32, "0"), "F12345")]}]}), encoding="utf-8")
    feats, _ = gp.build_index(str(tmp_path))
    assert feats["F12345"]["cucumber"] == {"uid": None, "count": 0, "tagged": 1}


def test_a_node_backed_feature_keeps_its_uid_and_gains_the_tagged_count(tmp_path):
    bdir = tmp_path / "allure" / "cucumber" / "data"
    bdir.mkdir(parents=True)
    (bdir / "behaviors.json").write_text(json.dumps({"children": [
        {"name": "E0105 Picking", "uid": "e".ljust(32, "0"), "children": [
            {"name": "F00230 MobileUI Picking", "uid": "f".ljust(32, "0"), "children": [
                _leaf("a".ljust(32, "0"), "F00230"),
                _leaf("b".ljust(32, "0"), "F00230")]}]}]}), encoding="utf-8")
    feats, _ = gp.build_index(str(tmp_path))
    assert feats["F00230"]["cucumber"] == {
        "uid": "f".ljust(32, "0"), "count": 2, "tagged": 2}


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
