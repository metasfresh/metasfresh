# metasfresh Frontend Web UI - Playwright Tests

E2E tests for the metasfresh frontend web application using Playwright.

## Overview

This test suite provides end-to-end testing for the metasfresh web UI, following the same patterns established in the mobile-webui tests but adapted for desktop browsers.

**Features:**
- ✅ Desktop Chrome browser testing
- ✅ Page Object Model architecture
- ✅ Automatic error detection (error toasts)
- ✅ Backend API integration for test data
- ✅ Trace recording and video capture
- ✅ HTML and JUnit reporting

## Prerequisites

Before running the tests, ensure you have:

1. **Node.js** >= 18.0.0
2. **metasfresh backend** running with `frontend.testing=Y` system config
3. **Frontend web UI** running (default: http://localhost:3000)
4. **Backend API** accessible (default: http://localhost:8080/rest/api)

### Enable Backend Test API

Run this SQL to enable the test API:

```sql
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, Name, Value, Description)
VALUES (0, 0, 'frontend.testing', 'Y', 'Enable frontend testing API')
ON CONFLICT (Name) DO UPDATE SET Value = 'Y';
```

Verify the endpoint is accessible:

```bash
curl -X POST http://localhost:8080/rest/api/v2/frontendTesting \
  -H "Content-Type: application/json" \
  -d '{"login": {"user": {}}}'
```

## Installation

```bash
cd frontend-webui-testing

# Install dependencies
npm install

# Install Chromium browser
npx playwright install chromium
```

## Running Tests

```bash
# Run all tests (headless)
npm test

# Run with visible browser
npm run test:headed

# Run in UI mode (interactive)
npm run test:ui

# Run with debugging
npm run test:debug

# View HTML report
npm run test:report
```

## Configuration

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `FRONTEND_BASE_URL` | `http://localhost:3000` | Frontend web UI base URL (React dev server) |
| `TESTING_API_BASE_URL` | `http://localhost:8282/api/v2` | Testing API URL (app-server with /frontendTesting endpoint) |
| `CI` | `false` | Enable CI mode (2 retries on failure) |

### Port Configuration

**Important:** The metasfresh setup uses different ports for different services:
- **Frontend (React)**: http://localhost:3000 - Web UI development server
- **Backend API**: http://localhost:8080/rest/api - API for web frontend
- **App Server**: http://localhost:8282 - Mobile UI and testing API (/frontendTesting endpoint)

**Both port 8080 and 8282 must be running for the tests to work!**

### Example: Custom URLs

```bash
export FRONTEND_BASE_URL=http://localhost:3000
export TESTING_API_BASE_URL=http://localhost:8282/api/v2
npm test
```

## Test Structure

```
tests/
├── spec/                          # Test specifications
│   ├── login.spec.js              # Login/authentication tests
│   ├── business-partner.spec.js   # Business Partner window tests
│   └── product.spec.js            # Product window tests
└── utils/                         # Test utilities
    ├── common.js                  # Shared constants and helpers
    ├── Backend.js                 # Backend API client
    ├── pages/                     # Page Object Model
    │   ├── LoginPage.js           # Login page automation
    │   ├── DashboardPage.js       # Dashboard page
    │   └── MasterWindowPage.js    # Master window (document lists)
    └── components/                # Component helpers
        └── ErrorToast.js          # Error toast detection
```

## Writing Tests

### Example: Basic Login Test

```javascript
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';

test('Login with test user', async ({ page }) => {
  // Create test user via backend API
  const response = await Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US' } },
    },
  });

  // Perform login
  await LoginPage.goto();
  await LoginPage.login(response.login.user);

  // Verify success
  await DashboardPage.expectVisible();
});
```

### Example: Window Navigation Test

```javascript
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';

test('View Business Partner window', async ({ page }) => {
  // Create test data
  const masterdata = await Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US' } },
      bpartners: { BP1: { name: 'Test Partner' } },
    },
  });

  // Login and navigate
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await MasterWindowPage.goto(123); // Business Partner window ID

  // Verify window loaded
  await MasterWindowPage.expectWindowLoaded();
  await MasterWindowPage.waitForTableData();
});
```

## Test Data Strategy

Tests use a **hybrid approach** for test data:

1. **Create per test**: Each test creates its own transactional data (orders, invoices, etc.)
2. **Shared reference data**: Reference data (products, warehouses) can be reused
3. **Backend API**: All test data is created via `/api/v2/frontendTesting` endpoint

This ensures test isolation while maintaining reasonable execution speed.

## Page Object Model

The tests follow a Page Object Model pattern:

- **Pages** (`tests/utils/pages/`): Represent application pages with navigation and interaction methods
- **Components** (`tests/utils/components/`): Reusable UI components (modals, toasts, etc.)
- **Backend** (`tests/utils/Backend.js`): API client for test data creation

Each page object provides:
- Navigation methods (`goto()`)
- Interaction methods (`login()`, `clickRow()`)
- Verification methods (`expectVisible()`, `expectWindowLoaded()`)

## Automatic Error Detection

The test framework automatically watches for error toasts during test execution:

```javascript
// Wrapped test steps automatically detect error toasts
await step('Perform action', async () => {
  // If an error toast appears, the test fails immediately
  await someAction();
});
```

To explicitly expect an error:

```javascript
import { expectErrorToast } from '../utils/common';

await expectErrorToast('Should show error', async () => {
  await invalidAction();
});
```

## Debugging

### Visual Debugging

```bash
# Run in headed mode
npm run test:headed

# Run in UI mode (interactive)
npm run test:ui

# Run specific test with debugging
npx playwright test --debug tests/spec/login.spec.js
```

### Trace Viewer

Traces are recorded for all test runs:

```bash
# View trace for failed test
npx playwright show-trace test-results/failed-test/trace.zip

# Or view from HTML report
npm run test:report
```

### Logging

Tests include console logging for key actions:

```javascript
console.log('Created master data:', masterdata);
console.log('Successfully logged in as', username);
```

## CI/CD Integration

The test suite is configured for CI/CD:

- Sequential execution (1 worker) for test isolation
- 2 retries on failure in CI mode
- JUnit XML reports in `playwright-report/junit/`
- HTML reports in `playwright-report/html/`
- Video and trace capture on failure

### GitHub Actions Example

```yaml
- name: Run Playwright Tests
  run: |
    cd frontend-webui-testing
    npm ci
    npx playwright install --with-deps chromium
    npm test
  env:
    CI: true
    FRONTEND_BASE_URL: http://app-test:8080
```

## Troubleshooting

### Tests fail with "frontend.testing API not enabled"

Ensure the backend system config is set:

```sql
UPDATE AD_SysConfig SET Value = 'Y' WHERE Name = 'frontend.testing';
```

### Tests fail with "Cannot find window.config"

The frontend may not be fully loaded. Check that:
- Frontend is running at `FRONTEND_BASE_URL`
- No JavaScript errors in console
- `config.js` is properly loaded

### Network timeout errors

Increase timeout in `playwright.config.js`:

```javascript
timeout: 180000, // 3 minutes
```

Or adjust timeouts in `tests/utils/common.js`.

## Extending the Tests

### Adding New Page Objects

1. Create file in `tests/utils/pages/`
2. Export a class with static methods
3. Wrap methods in `test.step()` for reporting
4. Use `getPage()` to access the page object

### Adding New Tests

1. Create file in `tests/spec/`
2. Import required page objects and utilities
3. Use `Backend.createMasterdata()` for test data
4. Follow AAA pattern (Arrange, Act, Assert)

### Adding More Browsers

Edit `playwright.config.js`:

```javascript
projects: [
  { name: 'Desktop Chrome', use: { ...devices['Desktop Chrome'] } },
  { name: 'Firefox', use: { ...devices['Desktop Firefox'] } },
  { name: 'Safari', use: { ...devices['Desktop Safari'] } },
],
```

## Resources

- [Playwright Documentation](https://playwright.dev)
- [Mobile WebUI Tests](../misc/services/mobile-webui/mobile-webui-frontend-testing/) - Similar test structure
- [metasfresh Documentation](https://docs.metasfresh.org)

## License

GPL-2.0
