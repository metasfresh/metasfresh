# Allure Reports - Definition of Done

## How to Verify
After each CI build completes, check R2 reports at:
```
https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/branches/new-dawn-uat-improve-test-annotations/builds/{VERSION}/
```

Get latest version from: `https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/_metadata/branches.json`

---

## 0. Verify You're Checking the Correct Build (FIRST!)

**Before checking anything else, confirm R2 has the build you just pushed:**

1. **Get the CI build number from your push:**
   ```bash
   gh run list --branch new_dawn_uat_improve_test_annotations --limit 1 --json databaseId,headSha,displayTitle
   ```

2. **Get the latest version on R2:**
   ```bash
   curl -s https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/_metadata/branches.json | \
     jq '.branches[] | select(.name == "new-dawn-uat-improve-test-annotations") | .builds[0]'
   ```

3. **Verify they match:**
   - CI build number (e.g., `28571`) should match R2 version suffix (e.g., `5.175-new-dawn-uat-improve-test-annotations.28571`)
   - If they don't match, either:
     - Build hasn't finished yet
     - `Publish Test Reports to R2` job failed
     - Check: `gh run view {RUN_ID} --json jobs --jq '.jobs[] | select(.name == "Publish Test Reports to R2")'`

4. **Cross-check with commit SHA (optional):**
   ```bash
   # Your latest commit
   git rev-parse --short HEAD

   # Should appear in CI run
   gh run view {RUN_ID} --json headSha --jq '.headSha[:7]'
   ```

**DO NOT proceed with other checks until build version is confirmed!**

---

## 1. Report Titles (environment.json)

Check `widgets/environment.json` in each report:

| Report | URL Path | Expected `Report.Name` |
|--------|----------|------------------------|
| Frontend Playwright | `allure/frontend-webui/` | `Playwright (Frontend WebUI)` |
| Mobile Playwright | `allure/mobile-webui/` | `Playwright (Mobile WebUI)` |
| Cucumber | `allure/cucumber/` | `Cucumber BDD` |
| Backend JUnit | `junit/backend/` | `Backend Unit Tests` |
| Camel JUnit | `junit/camel/` | `Camel Init and Integration Tests` |
| Jest JUnit | `junit/jest/` | `Jest (Frontend Unit Tests)` |

**Verify:** Each environment.json contains proper `Report.Name` value (not concatenated with other fields)

---

## 2. Landing Page Structure

Check: `https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/index.html`

**Expected sections:**
- [ ] **E2E Tests (Playwright)**: Frontend WebUI, Mobile WebUI
- [ ] **Integration Tests (Cucumber)**: Cucumber BDD
- [ ] **Unit Tests**: Jest (Frontend), Backend, Camel
- [ ] NO "Allure Reports" vs "JUnit Reports" distinction

---

## 3. History & Trends

**Prerequisite:** Requires 2+ builds on same branch

**Check in each Allure report:**
- [ ] Trend chart visible on overview page
- [ ] History tab shows previous runs
- [ ] `history/` folder exists in report artifacts

**Verify history artifacts uploaded:**
```bash
gh run view {RUN_ID} --json jobs --jq '.jobs[] | select(.name | contains("Allure")) | .steps[] | select(.name | contains("history"))'
```

---

## 4. Cucumber Epic/Feature Tags

**Check Cucumber Allure report:** `allure/cucumber/index.html`

- [ ] **Behaviors tab** shows Epic hierarchy (E0100, E0225, etc.)
- [ ] **Features** nested under correct Epics
- [ ] Click on a test → Labels section shows:
  - `epic: E0XXX`
  - `feature: F0XXXX`

**Sample features to verify:**
- `report_InventoryValue.feature` → Epic: E0225, Feature: F01000
- `createBPartnerV1.feature` → Epic: E0390, Feature: F00900

---

## 5. Cucumber Executor Distribution

**Understanding (not visible in Allure, but documented):**
- `@ghActions:run_on_executor1-7` - CI parallel distribution tags
- `@flaky` - Explicit tag for flaky tests (17 files)
- `catchall` - Tests NOT matching any executor tag (by exclusion)

**Verify in CI:** All 10 cucumber profiles complete:
- [ ] profile1-7 (executor-based)
- [ ] flaky
- [ ] catchall
- [ ] report

---

## 6. Playwright Test Annotations

**Check Frontend/Mobile Allure reports:**

- [ ] Tests show Epic labels (from `test.info().annotations`)
- [ ] Tests show Feature labels
- [ ] Test descriptions visible
- [ ] Screenshots/videos attached to failed tests

---

## 7. JUnit Reports Content

**For Backend, Camel, Jest reports:**

- [ ] Test count matches expected
- [ ] Failures/errors properly categorized
- [ ] `Test.Type=JUnit` in environment
- [ ] No "ALLURE REPORT UNKNOWN" in title

---

## 8. CI Job Success

All these jobs must pass:
- [ ] `frontend webui test`
- [ ] `mobile test`
- [ ] `test (cucumber) (profile1-7, flaky, catchall, report)`
- [ ] `Generate Cucumber Allure Report`
- [ ] `Publish Test Reports to R2`

---

## Quick Verification Commands

```bash
# Get latest build version
curl -s https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/_metadata/branches.json | jq '.branches[] | select(.name == "new-dawn-uat-improve-test-annotations") | .builds[0].version'

# Check environment.json for a report
curl -s https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/branches/new-dawn-uat-improve-test-annotations/builds/{VERSION}/allure/frontend-webui/widgets/environment.json | jq

# Check CI build status
gh run list --branch new_dawn_uat_improve_test_annotations --limit 1
gh run view {RUN_ID} --json conclusion,jobs --jq '{conclusion, failed: [.jobs[] | select(.conclusion == "failure") | .name]}'
```

---

## Verification Checklist Template

Copy for each verification run:

```
Build: {VERSION}
Run ID: {RUN_ID}
Commit: {SHA}

### 0. Build Verification (DO FIRST!)
- [ ] CI build completed successfully
- [ ] R2 version matches CI build number
- [ ] Commit SHA matches pushed commit

### 1. Report Titles
- [ ] Frontend: Playwright (Frontend WebUI) - NOT concatenated
- [ ] Mobile: Playwright (Mobile WebUI) - NOT concatenated
- [ ] Cucumber: Cucumber BDD
- [ ] Backend: Backend Unit Tests + Test.Type=JUnit
- [ ] Camel: Camel Init and Integration Tests + Test.Type=JUnit
- [ ] Jest: Jest (Frontend Unit Tests) + Test.Type=JUnit

### 2. Landing Page
- [ ] Grouped by framework (E2E/Integration/Unit)
- [ ] No "Allure Reports" vs "JUnit Reports" sections

### 3. History (if 2+ builds on branch)
- [ ] Trend charts visible in Allure reports
- [ ] History tab shows previous runs

### 4. Cucumber Tags
- [ ] Epics visible in Behaviors tab
- [ ] Features nested under correct Epics
- [ ] Sample: report_InventoryValue.feature → E0225/F01000

### 5. CI Jobs
- [ ] frontend webui test: passed
- [ ] mobile test: passed
- [ ] All cucumber profiles: passed
- [ ] Generate Cucumber Allure Report: passed
- [ ] Publish Test Reports to R2: passed
```
