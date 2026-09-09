# metasfresh flaky-metric extractor

Automates the manual CI-flake audit: pulls test failures from GitHub Actions
artifacts, classifies each by root-cause **bucket**, and upserts a flakyness
metric into a Google Sheet. Tracks https://github.com/metasfresh/me03/issues/30024.

## What it does

For each `cicd.yaml` run on `new_dawn_uat`:

1. Downloads every JUnit artifact (`junit-results-cucumber-profile1..7` +
   `-catchall`, `junit-results-playwright-mobile`,
   `junit-results-playwright-frontend-shard1..3`).
2. Parses each XML, keeps only FAILED testcases.
3. Classifies each failure into a **bucket** via an ordered rule table
   (`lib/bucketize.js`) — the same taxonomy as the hand-written audits in
   `ai-work/flaky/` (E = m_cost deadlock, I = ShipmentScheduleEnqueuer lock,
   N = queue-drain timeout, … + generic `timeout` / `assertion` / `unclassified`
   fallbacks so nothing is ever silently dropped).
4. Writes two tabs to the target Google Sheet:
   - **Failures** — append-only event log, one row per `(run, failed scenario)`.
     Idempotent: re-running over an overlapping window appends nothing new
     (dedup on the `runId::scenario` key).
   - **Metrics** — one row per `(branch, scenario)`, recomputed from the Failures
     log each run: fail count (= distinct runs failed, since the Failures key is
     `runId::scenario`), first/last failed, current bucket.

## Usage

```bash
npm install

# Validate the logic without any credentials — writes ./out/*.csv + *.json:
node scripts/extract.js --since 7d --dry-run

# Process one run (what the GitHub Action does on each cicd completion):
FLAKY_SHEET_ID=<id> node scripts/extract.js --run <run-id>

# Backfill a window into the sheet:
FLAKY_SHEET_ID=<id> node scripts/extract.js --since 2026-05-25

# Read the sheet back to sanity-check:
FLAKY_SHEET_ID=<id> node scripts/verify-sheet.js
```

Flags: `--dry-run` (no creds, write to `./out/`), `--run <id>`, `--since <Nd|date>`,
`--branch <name>` (default `new_dawn_uat`), `--sheet <id>` (or `FLAKY_SHEET_ID` env).

## Configuration

| Env var | Meaning | Default |
|---|---|---|
| `FLAKY_SHEET_ID` | target spreadsheet id | — (dry-run if unset) |
| `FLAKY_REPO` | source repo | `metasfresh/metasfresh` |
| `GOOGLE_SHEETS_CREDENTIALS` | path to service-account JSON (local use) | `~/.credentials/google-sheets-service-account.json` |
| `QA_SHEETS_SERVICE_ACCOUNT_JSON` | inline service-account JSON (used by the Action; org-standard secret name) | — |
| `GH_TOKEN` | gh-CLI token | your `gh auth login` / Action `github.token` |

Tab names are the `FAILURES_TAB` / `METRICS_TAB` constants in `lib/sheets.js`.

## Adding a bucket

When a new failure pattern appears, add a rule to `RULES` in `lib/bucketize.js`
(most-specific first; the catch-all stays last) and a regression test in
`test/bucketize.test.js`. That is the maintenance loop — never re-classify by hand.

## Tests

```bash
npm test   # node --test: parser + bucketizer, against real fixture XMLs
```

## Deployment

See `SETUP.md`. The `workflow/flaky-metric.yaml` is the GitHub Action; it + this
node project need to be checked into a repo the Action can run from.
