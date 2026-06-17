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
def test_feature_without_fcode_indexed_by_name_only():
    """Documents the by-node-name limitation: indexing is keyed off the Allure grouping
    node NAME, not test tags. A node whose name carries no F-code gets no F-code key."""
    root = {"children": [
        {"name": "E2300 Attributes", "uid": "e".ljust(32, "0"), "children": [
            {"name": "HU_DateReceived attribute population", "uid": "b".ljust(32, "0"),
             "children": [{"name": "scenario"}]},
        ]},
    ]}
    feats = gp.extract_features(root)
    assert "HU_DateReceived attribute population" in feats
    assert not any(k.startswith("F") for k in feats)  # no tag-derived F-code key


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
