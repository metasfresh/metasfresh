import json, os, sys, time, pathlib
HERE = pathlib.Path(__file__).resolve().parent
sys.path.insert(0, str(HERE.parent))
import update_branch_metadata as ubm

BRANCH = "new-dawn-uat"


def _publish(base, version):
    """What the publish job does before the metadata step: the build's tree exists on the server."""
    d = base / "branches" / BRANCH / "builds" / version / "allure" / "frontend-webui"
    d.mkdir(parents=True)
    (d / "index.html").write_text("report", encoding="utf-8")
    ubm.main(["x", BRANCH, version, str(base)])


def _metadata(base):
    return json.loads((base / "_metadata" / f"{BRANCH}.json").read_text(encoding="utf-8"))


def _builds_on_disk(base):
    return sorted(os.listdir(base / "branches" / BRANCH / "builds"))


def _wait_until(predicate, seconds=10):
    deadline = time.time() + seconds
    while time.time() < deadline:
        if predicate():
            return True
        time.sleep(0.1)
    return predicate()


def test_newest_first_and_capped(tmp_path):
    for v in ["v1", "v2", "v3", "v4"]:
        _publish(tmp_path, v)
    assert [b["version"] for b in _metadata(tmp_path)["builds"]] == ["v4", "v3", "v2"]


def test_republished_version_is_not_duplicated(tmp_path):
    _publish(tmp_path, "v1")
    ubm.main(["x", BRANCH, "v1", str(tmp_path)])
    assert [b["version"] for b in _metadata(tmp_path)["builds"]] == ["v1"]


def test_evicted_build_leaves_the_served_tree_immediately(tmp_path):
    for v in ["v1", "v2", "v3", "v4"]:
        _publish(tmp_path, v)
    assert _builds_on_disk(tmp_path) == ["v2", "v3", "v4"]


def test_does_not_wait_for_the_slow_delete(tmp_path, monkeypatch):
    # On the server, removing one old build (tens of thousands of trace/video files) took 8 min,
    # and the publish job waited for it. The prune must not block the caller.
    for v in ["v1", "v2", "v3"]:
        _publish(tmp_path, v)
    stub_bin = tmp_path / "bin"
    stub_bin.mkdir()
    slow_rm = stub_bin / "rm"
    slow_rm.write_text('#!/bin/sh\nsleep 3\nexec /bin/rm "$@"\n', encoding="utf-8")
    slow_rm.chmod(0o755)
    monkeypatch.setenv("PATH", f"{stub_bin}{os.pathsep}{os.environ['PATH']}")
    (tmp_path / "branches" / BRANCH / "builds" / "v4").mkdir()
    started = time.time()
    ubm.main(["x", BRANCH, "v4", str(tmp_path)])
    assert time.time() - started < 1.5
    assert _builds_on_disk(tmp_path) == ["v2", "v3", "v4"]


def test_evicted_build_is_eventually_deleted(tmp_path):
    for v in ["v1", "v2", "v3", "v4"]:
        _publish(tmp_path, v)
    trash = tmp_path / "_trash"
    assert _wait_until(lambda: not trash.exists() or not os.listdir(trash))
