import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

// Window 540415: Compensation Group Schema
const COMPENSATION_GROUP_SCHEMA_WINDOW_ID = 540415;

// Column added in F00127.1
const WITHOUT_CHARGE_COLUMN = 'IsWithoutCharge';

/**
 * Attach a screenshot to the Allure report.
 */
async function saveScreenshot(page, filename) {
  allure.attachment(filename, await page.screenshot({ fullPage: false }), 'image/png');
}

/**
 * Create a login user plus a Compensation Group Schema of its own (one template line), so the test does not
 * depend on schema records left behind by other specs.
 */
async function createMasterdataWithSchema(language) {
  return Backend.createMasterdata({
    request: {
      login: { user: { language } },
      products: {
        P1: { name: 'WithoutChargeCol', type: 'Item', isStocked: false },
      },
      compensationGroupSchemas: {
        schema: { name: 'WithoutChargeCol schema', templateLines: [{ product: 'P1', qty: 1 }] },
      },
    },
  });
}

/**
 * Open the given Compensation Group Schema record and click the Template Lines tab (AD_Tab-544005).
 */
async function navigateToTemplateLinesTab(page, schemaId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${COMPENSATION_GROUP_SCHEMA_WINDOW_ID}/${schemaId}`);

  // Template Lines tab: data-testid is tab-AD_Tab-544005 (verified from live WebAPI
  // GET /rest/api/window/540415/layout — tabId=AD_Tab-544005, caption=Template Lines).
  // This is the first included tab in window 540415.
  const templateLinesTab = page.locator('[data-testid="tab-AD_Tab-544005"]');
  await templateLinesTab.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await templateLinesTab.click();
  console.log('[PASS] Clicked Template Lines tab (tab-AD_Tab-544005)');
}

test.describe('Compensation Group Schema — Template Lines "Without Charge" column', () => {
  test.setTimeout(120000); // 2 minutes

  //
  // TC-1: English (en_US) — column header "Without Charge"
  //
  test('EN: "Without Charge" column visible in Template Lines tab', async ({ page }) => {
    allure.epic('E0040: Sales Order');
    allure.tag('F00127.1: Bundle Single Price');
    allure.story('IsWithoutCharge column in Compensation Group Schema Template Lines');
    allure.severity('normal');

    const masterdata = await createMasterdataWithSchema('en_US');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await navigateToTemplateLinesTab(page, masterdata.compensationGroupSchemas.schema.id);

    // Assert the IsWithoutCharge column header is present in the grid
    const col = page.locator(`th[data-testid="column-${WITHOUT_CHARGE_COLUMN}"]`);
    const colCount = await col.count();
    console.log(`[INFO] IsWithoutCharge column count (en_US): ${colCount}`);

    // Log all column headers for debugging
    const allHeaders = await page.locator('th').allTextContents();
    console.log('[INFO] All headers (en_US):', allHeaders.join(' | '));

    // The column must exist in the DOM (may be off-screen for high SeqNoGrid)
    expect(colCount).toBeGreaterThan(0);

    // Also verify the English label text appears somewhere in the header row
    const allHeaderText = allHeaders.join(' | ');
    expect(allHeaderText).toContain('Without Charge');

    await saveScreenshot(page, 'templateLine-grid-en.png');
  });

  //
  // TC-2: German (de_DE) — column header "Ohne Berechnung"
  //
  test('DE: "Ohne Berechnung" column visible in Template Lines tab', async ({ page }) => {
    allure.epic('E0040: Sales Order');
    allure.tag('F00127.1: Bundle Single Price');
    allure.story('IsWithoutCharge column in Compensation Group Schema Template Lines');
    allure.severity('normal');

    const masterdata = await createMasterdataWithSchema('de_DE');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await navigateToTemplateLinesTab(page, masterdata.compensationGroupSchemas.schema.id);

    // Assert the IsWithoutCharge column header is present in the grid
    const col = page.locator(`th[data-testid="column-${WITHOUT_CHARGE_COLUMN}"]`);
    const colCount = await col.count();
    console.log(`[INFO] IsWithoutCharge column count (de_DE): ${colCount}`);

    // Log all column headers for debugging
    const allHeaders = await page.locator('th').allTextContents();
    console.log('[INFO] All headers (de_DE):', allHeaders.join(' | '));

    // The column must exist in the DOM
    expect(colCount).toBeGreaterThan(0);

    // Also verify the German label text appears somewhere in the header row
    const allHeaderText = allHeaders.join(' | ');
    expect(allHeaderText).toContain('Ohne Berechnung');

    await saveScreenshot(page, 'templateLine-grid-de.png');
  });
});
