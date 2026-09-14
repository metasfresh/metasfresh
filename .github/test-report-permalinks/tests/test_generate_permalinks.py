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


def test_a_customer_suffix_is_not_a_subfeature_separator():
    """`F00138_se203` scopes a feature to one customer; folding it into `F00138`
    would be the same class of defect in a new place."""
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
    """Pins `_canonical_fcode` DIRECTLY.

    `test_a_customer_suffix_is_not_a_subfeature_separator` above goes through
    TAG_FCODE_RE, which rejects `F00138_se203` before this function is reached —
    so it passes even if this guard is deleted. Without this test the guard is
    unpinned, which is how a defence quietly stops defending."""
    assert gp._canonical_fcode("F5001_1") == "F5001.1"
    assert gp._canonical_fcode("F5001.1") == "F5001.1"
    assert gp._canonical_fcode("F5001") == "F5001"
    assert gp._canonical_fcode("F00138_se203") == "F00138_se203"
    assert gp._canonical_fcode("F00762.1_is184") == "F00762.1_is184"
