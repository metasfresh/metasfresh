import fcntl, json, os, sys, time, pathlib
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
    slow_nice = stub_bin / "nice"
    slow_nice.write_text('#!/bin/sh\nsleep 3\nexec /usr/bin/nice "$@"\n', encoding="utf-8")
    slow_nice.chmod(0o755)
    monkeypatch.setenv("PATH", f"{stub_bin}{os.pathsep}{os.environ['PATH']}")
    (tmp_path / "branches" / BRANCH / "builds" / "v4").mkdir()
    started = time.time()
    ubm.main(["x", BRANCH, "v4", str(tmp_path)])
    assert time.time() - started < 1.5
    assert _builds_on_disk(tmp_path) == ["v2", "v3", "v4"]


def test_evicted_build_is_eventually_deleted(tmp_path):
    for v in ["v1", "v2", "v3", "v4"]:
        _publish(tmp_path, v)
    # only the deleter's own lock/status files (dot-files) stay behind
    assert _wait_until(lambda: _trash_entries(tmp_path) == [])


def _trash_entries(base):
    trash = base / "_trash"
    return sorted(n for n in os.listdir(trash) if not n.startswith(".")) if trash.exists() else []


def test_never_more_than_one_deleter(tmp_path):
    # A hung delete (e.g. a stalled disk) must not be joined by one more deleter per publish.
    for v in ["v1", "v2", "v3"]:
        _publish(tmp_path, v)
    (tmp_path / "_trash").mkdir(exist_ok=True)
    with open(tmp_path / "_trash" / ".sweep.lock", "w") as held:
        fcntl.flock(held, fcntl.LOCK_EX | fcntl.LOCK_NB)  # stands in for a deleter that is still running
        _publish(tmp_path, "v4")
        time.sleep(1.5)
        assert len(_trash_entries(tmp_path)) == 1, "a second deleter ran while one held the lock"
        assert not (tmp_path / "_trash" / ".last-sweep.json").exists(), "a second deleter completed a sweep"
    _publish(tmp_path, "v5")  # the lock is free again: this run's deleter also takes the leftover
    assert _wait_until(lambda: _trash_entries(tmp_path) == [])


def test_deleter_records_its_result(tmp_path):
    for v in ["v1", "v2", "v3", "v4"]:
        _publish(tmp_path, v)
    status = tmp_path / "_trash" / ".last-sweep.json"
    assert _wait_until(status.exists)
    assert json.loads(status.read_text(encoding="utf-8"))["errors"] == []


def test_reports_the_trash_state(tmp_path, capsys):
    _publish(tmp_path, "v1")
    out = capsys.readouterr().out
    assert "trash:" in out
    assert "::warning::" not in out


def test_warns_when_deletion_is_stuck(tmp_path, capsys):
    # an entry evicted 2 h ago that is still there means the background delete is not working
    trash = tmp_path / "_trash"
    trash.mkdir()
    (trash / f"{BRANCH}__v0__{int(time.time()) - 7200}").mkdir()
    with open(trash / ".sweep.lock", "w") as held:
        fcntl.flock(held, fcntl.LOCK_EX | fcntl.LOCK_NB)
        _publish(tmp_path, "v1")
    assert "::warning::" in capsys.readouterr().out


def test_warns_when_the_last_delete_failed(tmp_path, capsys):
    trash = tmp_path / "_trash"
    trash.mkdir()
    (trash / ".last-sweep.json").write_text(json.dumps({"finished": time.time(), "errors": ["x: Permission denied"]}), encoding="utf-8")
    with open(trash / ".sweep.lock", "w") as held:
        fcntl.flock(held, fcntl.LOCK_EX | fcntl.LOCK_NB)
        _publish(tmp_path, "v1")
    out = capsys.readouterr().out
    assert "::warning::" in out and "Permission denied" in out


def test_monitoring_never_fails_the_publish(tmp_path, monkeypatch, capsys):
    # an entry the running deleter removes between listing and stat must not fail the publish
    trash = tmp_path / "_trash"
    trash.mkdir()
    (trash / "unparsable-name").mkdir()

    def vanished(path):
        raise FileNotFoundError(path)

    monkeypatch.setattr(ubm.os.path, "getmtime", vanished)
    _publish(tmp_path, "v1")
    assert "trash:" in capsys.readouterr().out


def test_old_pid_suffix_is_not_read_as_a_timestamp(tmp_path, capsys):
    # entries of the first background-prune version end in a pid, not in epoch seconds
    trash = tmp_path / "_trash"
    trash.mkdir()
    (trash / f"{BRANCH}__v0__12345").mkdir()
    with open(trash / ".sweep.lock", "w") as held:
        fcntl.flock(held, fcntl.LOCK_EX | fcntl.LOCK_NB)
        _publish(tmp_path, "v1")
    assert "::warning::" not in capsys.readouterr().out


def test_warning_text_stays_on_one_line(tmp_path, capsys):
    trash = tmp_path / "_trash"
    trash.mkdir()
    (trash / ".last-sweep.json").write_text(json.dumps({"finished": time.time(), "errors": ["x: line one\nline two"]}), encoding="utf-8")
    with open(trash / ".sweep.lock", "w") as held:
        fcntl.flock(held, fcntl.LOCK_EX | fcntl.LOCK_NB)
        _publish(tmp_path, "v1")
    warnings = [l for l in capsys.readouterr().out.splitlines() if l.startswith("::warning::")]
    assert len(warnings) == 1 and "line one line two" in warnings[0]
