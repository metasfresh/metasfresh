# Frontend WebUI — Playwright E2E Tests

Playwright end-to-end regression tests for the metasfresh WebUI frontend.

## CI Status

**These tests are NOT executed in CI.** The CI pipeline (`cicd.yaml`) does not include a job for Playwright E2E tests — only Jest unit tests run automatically. These tests must be run locally before merging shortcut-related or UI-interaction changes.

## Prerequisites

- Node.js 18+
- A running metasfresh stack: frontend (port 3000) + WebAPI backend (port 8080)
- Chromium (installed via Playwright)

## Setup

```bash
cd e2e/frontend-webui/

# Install dependencies
npm install

# Install Playwright browsers
npx playwright install chromium

# Copy and edit environment config
cp .env.example .env
# Edit .env if your frontend runs on a different port
```

## Running Tests

```bash
# Run all tests (headless)
FRONTEND_BASE_URL=http://localhost:3000 npx playwright test

# Run with visible browser
npx playwright test --headed

# Run a specific test file
npx playwright test tests/spec/keyboard-shortcuts.spec.js

# Run a specific test by name
npx playwright test --grep="PageDown"
```

## Test Suites

### keyboard-shortcuts.spec.js

9 regression tests for the keyboard shortcut infrastructure (`ShortcutProvider` + `Shortcut` components). Covers:

| # | Test | Shortcut |
|---|------|----------|
| 1 | ALT+E opens Advanced Edit, ALT+ENTER confirms | `Alt+E` then `Alt+Enter` |
| 2 | ALT+E opens Advanced Edit, Escape cancels | `Alt+E` then `Escape` |
| 3 | Shortcuts work after re-open (no stale handlers) | Re-mount cycle |
| 4 | ALT+1 opens SubHeader/Actions menu | `Alt+1` |
| 5 | ALT+3 opens Inbox | `Alt+3` |
| 6 | ALT+4 opens User dropdown | `Alt+4` |
| 7 | Escape closes each menu after opening | `Escape` |
| 8 | Shortcuts survive UPDATE_HOTKEYS (table filter regression) | `Alt+E` after filter |
| 9 | PageDown/PageUp pagination | `PageDown` / `PageUp` |

## Test Results

- **Videos**: Recorded automatically in `test-results/` (gitignored)
- **Screenshots**: Captured on failure
- **Allure reports**: Generated in `allure-results/` (gitignored)

## Configuration

| File | Purpose |
|------|---------|
| `playwright.config.js` | Test runner config (timeouts, reporters, browser args) |
| `.env` / `.env.example` | `FRONTEND_BASE_URL` setting |
| `tests/utils/common.js` | Shared timeout constants, error detection |
| `tests/utils/components/ErrorToast.js` | Toast notification detection |
