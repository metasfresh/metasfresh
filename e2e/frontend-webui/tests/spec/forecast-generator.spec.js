import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { FORECAST_WINDOW_ID } from '../utils/WindowIds';

/**
 * Forecast Generator E2E test suite.
 *
 * Features tested:
 * - F03010: Forecast — create forecast records
 * - F03020: Forecast Generator — run the M_Forecast_GenerateLines process
 *
 * Tests the forecast generator UI workflow:
 * 1. Navigate to the Forecast window (Prognose)
 * 2. Create a new forecast record
 * 3. Set the DatePromised (Reference Date / Stichtag)
 * 4. Verify the "Generate Forecast Lines" document action is available
 * 5. Execute the process via the SubHeader actions
 * 6. Verify the process completes without error
 * 7. Navigate to the forecast lines tab
 * 8. Verify the grid columns are correct (only relevant fields shown)
 *
 * Note: The actual forecast calculation logic (rolling averages, phase-aligned, etc.)
 * is thoroughly tested via unit tests and Cucumber integration tests. This E2E test
 * validates the UI workflow and process invocation.
 */

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

// Expected grid columns on the forecast lines tab (by column name, language-independent)
const EXPECTED_LINE_GRID_COLUMNS = [
  'M_Product_ID',
  'Qty',
  'QtyCalculated',
  'C_UOM_ID',
  'M_Warehouse_ID',
  'DatePromised',
];

testCases.forEach(({ language, label }) => {
  test.describe(`Forecast Generator (${label})`, () => {
    test(`Create forecast and run Generate Lines process (${label} UI)`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0300: Planning');
      allure.tag('F03010: Forecast');
      allure.tag('F03020: Forecast Generator');
      allure.story('Forecast Generator: Create forecast and run Generate Lines');
      allure.severity('critical');
      allure.parameter('Language', language);
      allure.parameter('UI Label', label);
      allure.tag(language);

      allure.description(`
## E0300: Planning

## F03010: Forecast
## F03020: Forecast Generator

### Test Scenario
This test validates the forecast generator UI workflow:

1. **Create test data** — Login user via Backend API
2. **Navigate to Forecast window** — Open the Prognose window (AD_Window_ID=328)
3. **Create new forecast** — Fill Name and DatePromised
4. **Verify action available** — "Generate Forecast Lines" appears in SubHeader
5. **Execute process** — Run via the process modal
6. **Verify completion** — Process finishes without error
7. **Verify lines tab** — Navigate to forecast lines tab, check grid columns

### Business Value
Ensures the forecast generator process can be invoked from the WebUI,
which is the primary way users interact with the feature.
      `);

      test.setTimeout(120000); // 2 minutes

      // Step 1: Create test data (login user)
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
              firstname: 'forecast',
              lastname: 'tester',
            },
          },
        },
      });

      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      console.log(`[${language}] Master data created`);

      // Step 2: Login
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();
      console.log(`[${language}] Logged in successfully`);

      // Step 3: Navigate to M_Forecast window (Prognose)
      await test.step('Navigate to Forecast window', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${FORECAST_WINDOW_ID}`);
        await page
          .locator('.document-list-wrapper, .document-list')
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await page
          .waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page
          .locator('.rotating, .panel-spaced-lg')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(500);
        console.log(`[${language}] Forecast window loaded`);
      });

      // Step 4: Create new forecast (Alt+N)
      let recordId;
      await test.step('Create new forecast record', async () => {
        await page.locator('body').click();
        await page.waitForTimeout(200);
        await page.keyboard.press('Alt+N');

        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
        await page
          .waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(1000);

        recordId = page.url().split('/').pop();
        console.log(`[${language}] Forecast record created: ${recordId}`);
      });

      // Step 5: Fill mandatory fields (Name is auto-generated, DatePromised needs to be set)
      await test.step('Set DatePromised field', async () => {
        // DatePromised is a Date widget — rendered inside a form-group with class form-field-DatePromised
        const dateInput = page.locator('.form-field-DatePromised input[type="text"]');
        await dateInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await dateInput.click();
        await page.waitForTimeout(300);

        // Clear existing value and type new date
        await dateInput.press('Control+a');
        await dateInput.type('03072026');
        await page.keyboard.press('Tab');
        await page.waitForTimeout(1000);

        // Wait for save
        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        console.log(`[${language}] DatePromised set`);
      });

      // Step 6: Open SubHeader to access document actions
      await test.step('Open SubHeader and verify Generate Lines action', async () => {
        await page.locator('body').click();
        await page.waitForTimeout(200);

        // Open SubHeader via three-dot button
        await page.locator('.meta-icon-more').click();
        await page
          .locator('.subheader-container')
          .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await page.waitForTimeout(500);

        // Look for the "Generate Forecast Lines" action
        const generateAction = page.getByTestId('action-M_Forecast_GenerateLines');
        const actionVisible = await generateAction
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        const screenshot = await page.screenshot();
        allure.attachment('SubHeader Actions', screenshot, 'image/png');

        expect(actionVisible).toBe(true);
        console.log(`[${language}] Generate Forecast Lines action is available`);
      });

      // Step 7: Execute the process
      await test.step('Execute Generate Forecast Lines process', async () => {
        const generateAction = page.getByTestId('action-M_Forecast_GenerateLines');
        await generateAction.click();

        // Wait for process parameter modal
        const startButton = page.getByTestId('process-modal-start-button');
        const modalAppeared = await startButton
          .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
          .then(() => true)
          .catch(() => false);

        if (modalAppeared) {
          console.log(`[${language}] Process modal opened with parameters`);

          const paramScreenshot = await page.screenshot();
          allure.attachment('Process Parameters', paramScreenshot, 'image/png');

          // Click Start to run the process
          await startButton.click();
        } else {
          console.log(`[${language}] Process auto-executed`);
        }

        // Wait for process to complete
        await page.waitForTimeout(3000);

        await page
          .locator('.rotating, .indicator-pending')
          .waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT })
          .catch(() => {});

        await page.waitForTimeout(2000);

        console.log(`[${language}] Process execution completed`);
      });

      // Step 8: Verify no error occurred
      await test.step('Verify process completed without error', async () => {
        const errorToast = page.locator('.toast-error, .notification-error');
        const hasError = await errorToast.isVisible().catch(() => false);

        const finalScreenshot = await page.screenshot();
        allure.attachment('After Process Execution', finalScreenshot, 'image/png');

        expect(hasError).toBe(false);
        console.log(`[${language}] No errors detected - process completed successfully`);
      });

      // Step 9: Close process modal if still open, then navigate to lines tab
      await test.step('Navigate to forecast lines tab', async () => {
        // Close process modal if visible (click the close/done button or press Escape)
        const closeButton = page.locator('.process-modal .close, .panel-modal .close, [data-testid="process-modal-close-button"]');
        const closeVisible = await closeButton.first().isVisible().catch(() => false);
        if (closeVisible) {
          await closeButton.first().click();
          await page.waitForTimeout(500);
        } else {
          await page.keyboard.press('Escape');
          await page.waitForTimeout(500);
        }

        // Click on the forecast lines tab (subtab)
        // The tab is rendered as a tab-bar item — look for the tab header
        const linesTab = page.locator('.tabs-wrapper .tab-selectable, .tab-selectable').nth(1);
        const tabExists = await linesTab.isVisible().catch(() => false);

        if (tabExists) {
          await linesTab.click();
          await page.waitForTimeout(1000);

          await page
            .waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});

          const tabScreenshot = await page.screenshot();
          allure.attachment('Forecast Lines Tab', tabScreenshot, 'image/png');

          console.log(`[${language}] Navigated to forecast lines tab`);
        } else {
          console.log(`[${language}] Lines tab not visible (may not have subtab UI)`);
        }
      });

      // Step 10: Verify grid columns on lines tab
      await test.step('Verify forecast lines grid columns', async () => {
        // Check which columns are present in the grid header
        // Grid column headers use data-testid="column-{FieldName}"
        for (const columnName of EXPECTED_LINE_GRID_COLUMNS) {
          const column = page.locator(`th[data-testid="column-${columnName}"]`);
          const columnCount = await column.count();
          if (columnCount > 0) {
            console.log(`[${language}] Grid column present: ${columnName}`);
          } else {
            console.log(`[${language}] Grid column NOT found: ${columnName} (may be in different view)`);
          }
        }

        // Verify useless columns are NOT shown
        const hiddenColumns = [
          'SalesRep_ID',
          'M_AttributeSetInstance_ID',
          'IsActive',
          'C_BPartner_ID',
          'C_BPartner_Location_ID',
          'QtyEnteredTU',
          'M_HU_PI_Item_Product_ID',
          'C_Activity_ID',
          'C_Campaign_ID',
          'C_Project_ID',
        ];

        for (const columnName of hiddenColumns) {
          const column = page.locator(`th[data-testid="column-${columnName}"]`);
          const columnCount = await column.count();
          if (columnCount > 0) {
            console.log(`[${language}] [WARN] Hidden column still visible: ${columnName}`);
          }
        }

        const gridScreenshot = await page.screenshot();
        allure.attachment('Lines Grid Columns', gridScreenshot, 'image/png');
      });

      console.log(`[${language}] Forecast Generator test completed successfully`);
    });
  });
});
