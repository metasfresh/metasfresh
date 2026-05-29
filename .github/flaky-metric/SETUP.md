# Deployment setup — flaky-metric (me03#30024)

The tool runs and writes to the sheet **locally already** (creds + sheet done).
This doc covers promoting it to the unattended GitHub Action.

## Status of prerequisites

| Step | Owner | Status |
|---|---|---|
| Google service-account JSON at `~/.credentials/google-sheets-service-account.json` | human | ✅ done (`metasfresh-e2e-sheets@feature-and-test-management.iam.gserviceaccount.com`) |
| Target sheet created + shared with the service account | human | ✅ done — https://docs.google.com/spreadsheets/d/1B4WtERTrjg6TRYcJ15b4LWdDGZofducKcOOQS1EkU70 |
| Tool extracts + classifies + upserts | tool | ✅ done (17 failures / 12 metric rows for the 05-25→29 window) |
| **Repo placement** | done | ✅ metasfresh/metasfresh — https://github.com/metasfresh/metasfresh/pull/24334 (CI green) |
| **Secret `QA_SHEETS_SERVICE_ACCOUNT_JSON` available to metasfresh/metasfresh** | human | ⛔ pending — see below |
| **Repo variable `FLAKY_SHEET_ID`** | human | ⛔ pending |

## Go-live steps

1. **Merge** https://github.com/metasfresh/metasfresh/pull/24334 into `new_dawn_uat`
   (the workflow only activates once on the default branch).
2. **Make `QA_SHEETS_SERVICE_ACCOUNT_JSON` available to metasfresh/metasfresh.**
   This is the same org-standard QA Google-Sheets service-account secret that
   me03-gh-automation already uses. GitHub does **not** share repo secrets across
   repos, so one of:
   - it is an **org-level** secret → add `metasfresh/metasfresh` to its
     *Repository access* list (Org → Settings → Secrets and variables → Actions),
     or
   - create a **repo-level** secret of the same name in metasfresh/metasfresh with
     the same JSON value (Settings → Secrets and variables → Actions → New secret).
3. Add repo **variable** **`FLAKY_SHEET_ID`** = `1B4WtERTrjg6TRYcJ15b4LWdDGZofducKcOOQS1EkU70`.
4. After merge + 2 + 3, the Action fires on every failed `cicd` run on
   `new_dawn_uat` + the nightly backfill.

## Notes

- The service account only needs the **one** sheet shared with it — no
  domain-wide access.
- The Action uses the runner's `github.token` for `gh` artifact download — no PAT
  needed as long as artifacts are in the same repo.
- Existing tab **"CICD Failures"** in the sheet is untouched by the tool (it
  writes "Failures" + "Metrics"). If you'd rather the tool write into your
  "CICD Failures" tab, change `FAILURES_TAB` in `lib/sheets.js`.
