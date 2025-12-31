# Allure Reports - Definition of Done

> Last verified: NOT YET FULLY VERIFIED

## Report Types

There are **6 reports**, all using Allure format:

| Report | Type | Action Used | Has History | Has Executor Info |
|--------|------|-------------|-------------|-------------------|
| Frontend WebUI | Allure | `generate-allure-report` | YES | YES |
| Mobile WebUI | Allure | `generate-allure-report` | YES | YES |
| Cucumber | Allure | `generate-allure-report` | YES | YES |
| Backend | JUnit→Allure | `generate-junit-html` | YES | YES |
| Camel | JUnit→Allure | `generate-junit-html` | YES | YES |
| Jest | JUnit→Allure | `generate-junit-html` | YES | YES |

---

## 0. Build Verification (DO FIRST!)

Before checking anything else, confirm R2 has the build you just pushed.

```bash
# Get CI build number
gh run list --branch new_dawn_uat_improve_test_annotations --limit 1 --json databaseId,headSha,displayTitle

# Get R2 version
curl -s https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/_metadata/branches.json | \
  jq '.branches[] | select(.name == "new-dawn-uat-improve-test-annotations") | .builds[0]'
```

- [ ] CI build number matches R2 version suffix
- [ ] Overall build conclusion is `success`

**DO NOT proceed until build version is confirmed!**

---

## 1. All Test Jobs Must Pass

```bash
gh run view {RUN_ID} --json conclusion,jobs --jq '{
  conclusion,
  failed: [.jobs[] | select(.conclusion == "failure") | .name]
}'
```

- [ ] `frontend webui test` - passed
- [ ] `mobile test` - passed
- [ ] `test (cucumber)` - ALL profiles passed (profile1-7, flaky, catchall, report)
- [ ] `Generate Cucumber Allure Report` - passed
- [ ] `Publish Test Reports to R2` - passed
- [ ] **Overall conclusion: success**

If any job fails, DoD cannot be verified until a clean build passes.

---

## 2. Report Names - VISUAL Verification (ALL 6 Reports)

**CRITICAL: You MUST open each report in a browser and verify the heading visually.**

The Report.Name must appear in TWO places:
1. **Main heading** at top of report (e.g., "Backend Unit Tests 12/31/2025...")
2. **Environment section** under "Report.Name"

| Report | URL Path | Expected Heading Text | Check Heading | Check Environment |
|--------|----------|----------------------|---------------|-------------------|
| Frontend | `allure/frontend-webui/` | `Playwright (Frontend WebUI)` | [ ] | [ ] |
| Mobile | `allure/mobile-webui/` | `Playwright (Mobile WebUI)` | [ ] | [ ] |
| Cucumber | `allure/cucumber/` | `Cucumber BDD` | [ ] | [ ] |
| Backend | `junit/backend/` | `Backend Unit Tests` | [ ] | [ ] |
| Camel | `junit/camel/` | `Camel Init and Integration Tests` | [ ] | [ ] |
| Jest | `junit/jest/` | `Jest (Frontend Unit Tests)` | [ ] | [ ] |

**Visual verification steps:**
1. Open each report URL in browser: `$BASE/{path}/index.html`
2. Wait for page to fully load (loading indicators disappear)
3. Check the **main heading** at top shows the expected text (NOT "Allure Report unknown")
4. Check the **Environment section** shows `Report.Name: {expected}`

**API verification (supplements visual check, does NOT replace it):**
```bash
VERSION="5.175-new-dawn-uat-improve-test-annotations.XXXXX"
BASE="https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/branches/new-dawn-uat-improve-test-annotations/builds/$VERSION"

for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ==="
  curl -s "$BASE/$path/widgets/environment.json" | jq '.[] | select(.name == "Report.Name") | .values[0]'
done
```

**Additional for JUnit reports:**
- [ ] Backend: `Test.Type=JUnit` present in Environment
- [ ] Camel: `Test.Type=JUnit` present in Environment
- [ ] Jest: `Test.Type=JUnit` present in Environment

---

## 3. History & Trends (ALL 6 Reports)

**Prerequisite:** Requires 2+ successful builds on same branch

| Report | widgets/history-trend.json | Check |
|--------|---------------------------|-------|
| Frontend | Should have 2+ entries | [ ] |
| Mobile | Should have 2+ entries | [ ] |
| Cucumber | Should have 2+ entries | [ ] |
| Backend | Should have 2+ entries | [ ] |
| Camel | Should have 2+ entries | [ ] |
| Jest | Should have 2+ entries | [ ] |

**Verify command:**
```bash
for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ==="
  curl -s "$BASE/$path/widgets/history-trend.json" | jq 'length'
done
```

**Visual verification:** Open each report and check the "Trend" section shows a chart (not "There is nothing to show").

---

## 4. Executor Info (ALL 6 Reports)

Each report's `widgets/executors.json` must show GitHub Actions build info:

| Report | name | buildName | buildUrl | Check |
|--------|------|-----------|----------|-------|
| Frontend | GitHub Actions | #XXXXX | correct run URL | [ ] |
| Mobile | GitHub Actions | #XXXXX | correct run URL | [ ] |
| Cucumber | GitHub Actions | #XXXXX | correct run URL | [ ] |
| Backend | GitHub Actions | #XXXXX | correct run URL | [ ] |
| Camel | GitHub Actions | #XXXXX | correct run URL | [ ] |
| Jest | GitHub Actions | #XXXXX | correct run URL | [ ] |

**Verify command:**
```bash
for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ==="
  curl -s "$BASE/$path/widgets/executors.json" | jq '.[0] | {name, buildName, buildUrl}'
done
```

**Visual verification:** Open each report and check the "Executors" section shows "GitHub Actions #XXXXX" (not "There is no information about tests executors").

**Additional check for Cucumber:**
- [ ] Environment section does NOT contain `executor.profile` (this was legacy noise, now removed)

```bash
# Should return empty (no executor.profile entries)
curl -s "$BASE/allure/cucumber/widgets/environment.json" | jq '.[] | select(.name | contains("executor"))'
```

---

## 5. Landing Page Structure

Check: `https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/index.html`

- [ ] **E2E Tests (Playwright)**: Shows Frontend WebUI, Mobile WebUI
- [ ] **Integration Tests (Cucumber)**: Shows Cucumber BDD
- [ ] **Unit Tests**: Shows Jest, Backend, Camel
- [ ] NO separate "Allure Reports" vs "JUnit Reports" sections

---

## Verification Checklist Template

```
Build: {VERSION}
Run ID: {RUN_ID}
Date: {DATE}

## 0. Build Verification
- [ ] CI build completed with conclusion: success
- [ ] R2 version matches CI build number
- [ ] All test jobs passed (no failures)

## 1. Report Names - VISUAL (ALL 6) - OPEN EACH IN BROWSER!
Heading shows correct name (not "Allure Report unknown"):
- [ ] Frontend: "Playwright (Frontend WebUI)" in heading
- [ ] Mobile: "Playwright (Mobile WebUI)" in heading
- [ ] Cucumber: "Cucumber BDD" in heading
- [ ] Backend: "Backend Unit Tests" in heading
- [ ] Camel: "Camel Init and Integration Tests" in heading
- [ ] Jest: "Jest (Frontend Unit Tests)" in heading

Environment section shows Report.Name:
- [ ] All 6 reports have correct Report.Name in Environment section
- [ ] JUnit reports (Backend, Camel, Jest) have Test.Type=JUnit

## 2. History (ALL 6, requires 2+ builds)
- [ ] Frontend: 2+ history entries, Trend chart visible
- [ ] Mobile: 2+ history entries, Trend chart visible
- [ ] Cucumber: 2+ history entries, Trend chart visible
- [ ] Backend: 2+ history entries, Trend chart visible
- [ ] Camel: 2+ history entries, Trend chart visible
- [ ] Jest: 2+ history entries, Trend chart visible

## 3. Executor Info (ALL 6)
- [ ] Frontend: "GitHub Actions #XXXXX" in Executors section
- [ ] Mobile: "GitHub Actions #XXXXX" in Executors section
- [ ] Cucumber: "GitHub Actions #XXXXX" in Executors section
- [ ] Backend: "GitHub Actions #XXXXX" in Executors section
- [ ] Camel: "GitHub Actions #XXXXX" in Executors section
- [ ] Jest: "GitHub Actions #XXXXX" in Executors section
- [ ] Cucumber: NO executor.profile in Environment

## 4. Landing Page
- [ ] Grouped by framework type
- [ ] All 6 reports accessible
```

---

## Quick Commands

```bash
# Set version
VERSION="5.175-new-dawn-uat-improve-test-annotations.XXXXX"
BASE="https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/branches/new-dawn-uat-improve-test-annotations/builds/$VERSION"

# Check all report names (API - also verify visually!)
for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ===" && curl -s "$BASE/$path/widgets/environment.json" | jq '.[] | select(.name == "Report.Name")'
done

# Check history counts (ALL 6)
for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ===" && curl -s "$BASE/$path/widgets/history-trend.json" | jq 'length'
done

# Check executor info (ALL 6)
for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ===" && curl -s "$BASE/$path/widgets/executors.json" | jq '.[0]'
done

# Check for executor.profile noise in Cucumber (should be empty)
curl -s "$BASE/allure/cucumber/widgets/environment.json" | jq '.[] | select(.name | contains("executor"))'
```
