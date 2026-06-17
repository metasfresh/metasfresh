#!/usr/bin/env python3
"""Generate branches/{branch}/permalinks.json from a just-published build's Allure trees.
Pure I/O: reads behaviors.json (features) + suites.json (spec files); writes a stable
feature/spec -> {suite: {uid, count}} map. Runs server-side (piped over ssh) and locally (tests)."""
import json, os, re, sys, tempfile

ALLURE_SUITES = ["cucumber", "frontend-webui", "mobile-webui"]
FCODE_RE = re.compile(r"^(F\d+(?:\.\d+)?)\b")
ECODE_RE = re.compile(r"^E\d+\b")
SPEC_SUFFIXES = (".spec.js", ".feature")

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
                for key, (uid, count) in extract_features(json.load(f)).items():
                    features.setdefault(key, {})[suite] = {"uid": uid, "count": count}
        spath = os.path.join(build_dir, "allure", suite, "data", "suites.json")
        if os.path.isfile(spath):
            with open(spath, encoding="utf-8") as f:
                for name, (uid, count) in extract_specs(json.load(f)).items():
                    specs.setdefault(name, {})[suite] = {"uid": uid, "count": count}
    return features, specs

def main(argv):
    branch, version = argv[1], argv[2]
    base = argv[3] if len(argv) > 3 else "/var/www/test-reports"
    build_dir = os.path.join(base, "branches", branch, "builds", version)
    features, specs = build_index(build_dir)
    out_dir = os.path.join(base, "branches", branch)
    os.makedirs(out_dir, exist_ok=True)
    fd, tmp = tempfile.mkstemp(dir=out_dir, suffix=".tmp")
    with os.fdopen(fd, "w", encoding="utf-8") as f:
        json.dump({"version": version, "features": features, "specs": specs}, f, indent=2, ensure_ascii=False)
    # mkstemp creates the temp file 0600; the web server runs as a different user and
    # must be able to read the published file (else nginx serves 403). Widen to 0644
    # before the atomic rename so the served file is group+other readable.
    os.chmod(tmp, 0o644)
    os.replace(tmp, os.path.join(out_dir, "permalinks.json"))
    print(f"permalinks.json: {len(features)} feature keys, {len(specs)} spec keys")

if __name__ == "__main__":
    main(sys.argv)
