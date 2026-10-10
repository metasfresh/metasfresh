"""Record a published build in its branch's metadata and prune the builds beyond the cap.

Runs ON the reports server (the cicd `publish test reports` job pipes it over ssh):
    python3 - <branch> <version> [<base-dir>] < update_branch_metadata.py
"""
import json, sys, os, subprocess
from datetime import datetime, timezone

MAX_BUILDS_PER_BRANCH = 3


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
    for ver in evicted:
        build_path = os.path.join(builds_dir, ver)
        if os.path.isdir(build_path):
            os.rename(build_path, os.path.join(trash_dir, f"{branch}__{ver}__{os.getpid()}"))
            print(f"Pruned old build: {branch}/{ver}")
    delete_in_background([os.path.join(trash_dir, name) for name in os.listdir(trash_dir)])

    print(f"Kept {len(data['builds'])} build(s), pruned {len(evicted)} old build(s)")


def delete_in_background(paths):
    """Start `rm -rf` detached from this process, so neither it nor the ssh session waits for it.
    Includes leftovers of earlier runs whose delete was interrupted; deleting an entry another
    run is already deleting is harmless."""
    if not paths:
        return
    subprocess.Popen(["nice", "-n", "19", "rm", "-rf", "--", *paths],
                     stdin=subprocess.DEVNULL, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
                     start_new_session=True)


if __name__ == "__main__":
    main(sys.argv)
