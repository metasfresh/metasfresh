# Allure Reports - Definition of Done

> Last verified: NOT YET FULLY VERIFIED

## Report Types

There are **6 reports** in two categories:

| Report | Type | Action Used | Has History | Has Executor Info |
|--------|------|-------------|-------------|-------------------|
| Frontend WebUI | Allure | `generate-allure-report` | YES | YES |
| Mobile WebUI | Allure | `generate-allure-report` | YES | YES |
| Cucumber | Allure | `generate-allure-report` | YES | YES |
| Backend | JUnit→Allure | `generate-junit-html` | NO | NO |
| Camel | JUnit→Allure | `generate-junit-html` | NO | NO |
| Jest | JUnit→Allure | `generate-junit-html` | NO | NO |

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

## 2. Report Names (ALL 6 Reports)

Each report's `widgets/environment.json` must contain correct `Report.Name`:

| Report | URL Path | Expected Report.Name | Check |
|--------|----------|---------------------|-------|
| Frontend | `allure/frontend-webui/` | `Playwright (Frontend WebUI)` | [ ] |
| Mobile | `allure/mobile-webui/` | `Playwright (Mobile WebUI)` | [ ] |
| Cucumber | `allure/cucumber/` | `Cucumber BDD` | [ ] |
| Backend | `junit/backend/` | `Backend Unit Tests` | [ ] |
| Camel | `junit/camel/` | `Camel Init and Integration Tests` | [ ] |
| Jest | `junit/jest/` | `Jest (Frontend Unit Tests)` | [ ] |

**Verify command:**
```bash
VERSION="5.175-new-dawn-uat-improve-test-annotations.XXXXX"
BASE="https://pub-c55eb97d86224a4e951c219dfc2057b8.r2.dev/branches/new-dawn-uat-improve-test-annotations/builds/$VERSION"

for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ==="
  curl -s "$BASE/$path/widgets/environment.json" | jq '.[] | select(.name == "Report.Name") | .values[0]'
done
```

**Additional for JUnit reports:**
- [ ] Backend: `Test.Type=JUnit` present
- [ ] Camel: `Test.Type=JUnit` present
- [ ] Jest: `Test.Type=JUnit` present

---

## 3. History & Trends (Allure Reports Only)

**Only applies to:** Frontend, Mobile, Cucumber (the 3 Allure-native reports)

**Prerequisite:** Requires 2+ successful builds on same branch

| Report | widgets/history-trend.json | Check |
|--------|---------------------------|-------|
| Frontend | Should have 2+ entries | [ ] |
| Mobile | Should have 2+ entries | [ ] |
| Cucumber | Should have 2+ entries | [ ] |

**Verify command:**
```bash
for path in allure/frontend-webui allure/mobile-webui allure/cucumber; do
  echo "=== $path ==="
  curl -s "$BASE/$path/widgets/history-trend.json" | jq 'length'
done
```

**Note:** JUnit reports (Backend, Camel, Jest) do NOT have history support - this is expected.

---

## 4. Executor Info (Allure Reports Only)

**Only applies to:** Frontend, Mobile, Cucumber

Each report's `widgets/executors.json` must show GitHub Actions build info:

| Report | Check |
|--------|-------|
| Frontend: `name: "GitHub Actions"` | [ ] |
| Frontend: `buildName` matches CI run number | [ ] |
| Frontend: `buildUrl` links to correct run | [ ] |
| Mobile: `name: "GitHub Actions"` | [ ] |
| Mobile: `buildName` matches CI run number | [ ] |
| Mobile: `buildUrl` links to correct run | [ ] |
| Cucumber: `name: "GitHub Actions"` | [ ] |
| Cucumber: `buildName` matches CI run number | [ ] |
| Cucumber: `buildUrl` links to correct run | [ ] |

**Verify command:**
```bash
for path in allure/frontend-webui allure/mobile-webui allure/cucumber; do
  echo "=== $path ==="
  curl -s "$BASE/$path/widgets/executors.json" | jq '.[0] | {name, buildName, buildUrl}'
done
```

**Note:** JUnit reports do NOT get executor info - this is expected.

**Known Issue:** Cucumber environment.json may show `executor.profile: profileN` - this is test execution metadata, NOT Allure executor info. Should be removed in future cleanup.

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

## 1. Report Names (ALL 6)
- [ ] Frontend: "Playwright (Frontend WebUI)"
- [ ] Mobile: "Playwright (Mobile WebUI)"
- [ ] Cucumber: "Cucumber BDD"
- [ ] Backend: "Backend Unit Tests" + Test.Type=JUnit
- [ ] Camel: "Camel Init and Integration Tests" + Test.Type=JUnit
- [ ] Jest: "Jest (Frontend Unit Tests)" + Test.Type=JUnit

## 2. History (Allure reports only, requires 2+ builds)
- [ ] Frontend: 2+ history entries
- [ ] Mobile: 2+ history entries
- [ ] Cucumber: 2+ history entries

## 3. Executor Info (Allure reports only)
- [ ] Frontend: GitHub Actions with correct build #
- [ ] Mobile: GitHub Actions with correct build #
- [ ] Cucumber: GitHub Actions with correct build #

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

# Check all report names
for path in allure/frontend-webui allure/mobile-webui allure/cucumber junit/backend junit/camel junit/jest; do
  echo "=== $path ===" && curl -s "$BASE/$path/widgets/environment.json" | jq '.[] | select(.name == "Report.Name")'
done

# Check history counts (Allure only)
for path in allure/frontend-webui allure/mobile-webui allure/cucumber; do
  echo "=== $path ===" && curl -s "$BASE/$path/widgets/history-trend.json" | jq 'length'
done

# Check executor info (Allure only)
for path in allure/frontend-webui allure/mobile-webui allure/cucumber; do
  echo "=== $path ===" && curl -s "$BASE/$path/widgets/executors.json" | jq '.[0]'
done
```
