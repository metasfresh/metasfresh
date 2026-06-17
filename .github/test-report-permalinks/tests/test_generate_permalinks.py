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
