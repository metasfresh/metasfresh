"""Record a published build in its branch's metadata and prune the builds beyond the cap.

Runs ON the reports server (the cicd `publish test reports` job pipes it over ssh):
    python3 - <branch> <version> [<base-dir>] < update_branch_metadata.py
"""
import json, sys, os, subprocess, time
from datetime import datetime, timezone

MAX_BUILDS_PER_BRANCH = 3
# a trash entry older than this means the background delete is stuck or failing
STUCK_AFTER_SECONDS = 3600

# Runs detached as its own process: deletes everything in the trash dir, then records the outcome.
# The non-blocking lock keeps it to ONE deleter: if a previous one still runs (e.g. on a stalled disk),
# this one exits at once instead of piling up; the running one, or the next publish, takes the leftovers.
SWEEPER = '''
import fcntl, json, os, shutil, sys, time
trash = sys.argv[1]
lock = open(os.path.join(trash, ".sweep.lock"), "w")
try:
    fcntl.flock(lock, fcntl.LOCK_EX | fcntl.LOCK_NB)
except BlockingIOError:
    sys.exit(0)
errors = {}
# repeat until empty: entries renamed in while this sweep ran (their own deleter exited on the lock) go too
while True:
    pending = [n for n in os.listdir(trash) if not n.startswith(".") and n not in errors]
    if not pending:
        break
    for name in pending:
        try:
            shutil.rmtree(os.path.join(trash, name))
        except Exception as ex:
            errors[name] = f"{name}: {ex}"
errors = list(errors.values())
with open(os.path.join(trash, ".last-sweep.json"), "w") as f:
    json.dump({"finished": time.time(), "errors": errors[:5]}, f)
'''


def main(argv):
    branch = argv[1]
    version = argv[2]
    base = argv[3] if len(argv) > 3 else "/var/www/test-reports"
    metadata_dir = os.path.join(base, "_metadata")
    metadata_file = os.path.join(metadata_dir, f"{branch}.json")
    builds_dir = os.path.join(base, "branches", branch, "builds")

    os.makedirs(metadata_dir, exist_ok=True)

    # Read existing metadata or create new
    if os.path.exists(metadata_file):
        with open(metadata_file, "r") as f:
            try:
                data = json.load(f)
            except json.JSONDecodeError:
                data = {"branch": branch, "builds": []}
    else:
        data = {"branch": branch, "builds": []}

    # Deduplicate: remove existing entry for this version
    data["builds"] = [b for b in data["builds"] if b.get("version") != version]

    # Prepend new build
    data["builds"].insert(0, {
        "version": version,
        "timestamp": datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    })

    # Cap metadata and collect evicted versions
    evicted = [b["version"] for b in data["builds"][MAX_BUILDS_PER_BRANCH:]]
    data["builds"] = data["builds"][:MAX_BUILDS_PER_BRANCH]

    with open(metadata_file, "w") as f:
        json.dump(data, f, indent=2)

    # Move evicted builds out of the served tree (a rename: instant) and delete them in the background.
    # A build holds tens of thousands of trace/video files; deleting one in the foreground took 8 min,
    # which the publish job had to wait for.
    trash_dir = os.path.join(base, "_trash")
    os.makedirs(trash_dir, exist_ok=True)
    report_trash_state(trash_dir)
    for ver in evicted:
        build_path = os.path.join(builds_dir, ver)
        if os.path.isdir(build_path):
            # the epoch seconds in the name date the eviction, for the stuck-delete warning
            os.rename(build_path, os.path.join(trash_dir, f"{branch}__{ver}__{int(time.time())}"))
            print(f"Pruned old build: {branch}/{ver}")
    delete_in_background(trash_dir)

    print(f"Kept {len(data['builds'])} build(s), pruned {len(evicted)} old build(s)")


def report_trash_state(trash_dir):
    """Print what is still waiting for deletion and how the last background delete went; emit a
    GitHub warning annotation when the delete is stuck or failing, so it shows on the cicd run.
    Monitoring only: it never fails the publish."""
    try:
        _report_trash_state(trash_dir)
    except OSError as ex:
        print(f"trash: state not readable ({ex})")


def _entry_age_seconds(trash_dir, name, now):
    suffix = name.rsplit("__", 1)[-1]
    # epoch seconds since 2001; the first background-prune version ended the name in a pid instead
    if suffix.isdigit() and int(suffix) > 1_000_000_000:
        return now - int(suffix)
    return now - os.path.getmtime(os.path.join(trash_dir, name))


def _one_line(text):
    return " ".join(str(text).split())


def _report_trash_state(trash_dir):
    now = time.time()
    entries = [n for n in os.listdir(trash_dir) if not n.startswith(".")]
    ages = [_entry_age_seconds(trash_dir, name, now) for name in entries]
    oldest_min = int(max(ages) / 60) if ages else 0
    last = None
    try:
        with open(os.path.join(trash_dir, ".last-sweep.json")) as f:
            last = json.load(f)
    except (OSError, ValueError):
        pass
    errors = _one_line("; ".join(last.get("errors") or [])) if last else ""
    last_text = "never" if last is None else (
        f"{int((now - last.get('finished', now)) / 60)} min ago, " + (f"errors: {errors}" if errors else "ok"))
    print(f"trash: {len(entries)} old build(s) pending deletion, oldest {oldest_min} min; last delete finished {last_text}")
    if ages and max(ages) > STUCK_AFTER_SECONDS:
        print(f"::warning::test-reports server: {len(entries)} pruned build(s) not deleted, oldest {oldest_min} min - the background delete in {trash_dir} is stuck")
    if errors:
        print(f"::warning::test-reports server: the last background delete in {trash_dir} failed: {errors}")


def delete_in_background(trash_dir):
    """Start the sweeper detached from this process (all std streams closed, own session),
    so neither this script nor the ssh session waits for it."""
    subprocess.Popen(["nice", "-n", "19", sys.executable, "-c", SWEEPER, trash_dir],
                     stdin=subprocess.DEVNULL, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
                     start_new_session=True)


if __name__ == "__main__":
    main(sys.argv)
