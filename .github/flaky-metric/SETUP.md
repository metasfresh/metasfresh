# Deployment setup — flaky-metric (me03#30024)

The tool runs and writes to the sheet **locally already** (creds + sheet done).
This doc covers promoting it to the unattended GitHub Action.

## Status of prerequisites

| Step | Owner | Status |
|---|---|---|
| Google service-account JSON at `~/.credentials/google-sheets-service-account.json` | human | ✅ done (`metasfresh-e2e-sheets@feature-and-test-management.iam.gserviceaccount.com`) |
| Target sheet created + shared with the service account | human | ✅ done — https://docs.google.com/spreadsheets/d/1D9oMjC7uG0H427v2kWyUFt9FcnMErtXnQpS97yAl_RM |
| Tool extracts + classifies + upserts | tool | ✅ done (17 failures / 12 metric rows for the 05-25→29 window) |
| **Repo placement decision** | human | ⛔ OPEN — see below |
| **Repo secret `GOOGLE_SHEETS_CREDENTIALS_JSON`** | human | ⛔ pending placement |
| **Repo variable `FLAKY_SHEET_ID`** | human | ⛔ pending placement |

## OPEN DECISION — which repo hosts the Action?

The me03#30024 description says *"metasfresh | new_dawn_uat … (…or maybe rather in
repo me03-gh-automation…)"*. Two options:

1. **metasfresh/metasfresh** — `cicd.yaml` lives here, so `workflow_run` can
   observe it directly with zero cross-repo token. Node project goes in
   `.github/flaky-metric/`. This repo is in Claude's allowed-write list, so the
   PR can be opened by Claude.
2. **metasfresh/me03-gh-automation** — keeps CI-tooling out of the product repo.
   But `workflow_run` cannot observe a workflow in another repo, so the trigger
   would have to be `repository_dispatch` from cicd.yaml or a pure nightly
   `schedule` poll. **Claude is NOT permitted to write to me03-gh-automation**
   (not in the allowed-repos list) — a human would have to land it there.

**Recommendation:** option 1 (metasfresh/metasfresh) — simplest trigger, and
Claude can prepare the PR. If you prefer me03-gh-automation, that part is
human-only.

## Once placement is chosen

1. Copy the node project (`lib/`, `scripts/`, `package.json`, `package-lock.json`)
   into `<repo>/.github/flaky-metric/`, and `workflow/flaky-metric.yaml` into
   `<repo>/.github/workflows/`.
2. Add repo secret **`GOOGLE_SHEETS_CREDENTIALS_JSON`** = the full contents of the
   service-account JSON. (Settings → Secrets and variables → Actions → New secret.)
3. Add repo variable **`FLAKY_SHEET_ID`** = `1D9oMjC7uG0H427v2kWyUFt9FcnMErtXnQpS97yAl_RM`.
4. Merge. The Action then fires on every `cicd` completion + nightly backfill.

## Notes

- The service account only needs the **one** sheet shared with it — no
  domain-wide access.
- The Action uses the runner's `github.token` for `gh` artifact download — no PAT
  needed as long as artifacts are in the same repo.
- Existing tab **"CICD Failures"** in the sheet is untouched by the tool (it
  writes "Failures" + "Metrics"). If you'd rather the tool write into your
  "CICD Failures" tab, change `FAILURES_TAB` in `lib/sheets.js`.
