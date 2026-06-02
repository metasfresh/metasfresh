import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import * as path from 'node:path';
import * as fs from 'node:fs';

// Window 540415: Compensation Group Schema
const COMPENSATION_GROUP_SCHEMA_WINDOW_ID = 540415;

// Column added in F00127.1
const WITHOUT_CHARGE_COLUMN = 'IsWithoutCharge';

// Screenshots destination (relative to repo root in ai-work)
const SCREENSHOTS_DIR = path.resolve(__dirname, '..', '..', '..', '..', '..', 'ai-work', '29558', 'screenshots');

/**
 * Save a screenshot both as a Playwright buffer attached to Allure
 * and as a file under ai-work/29558/screenshots/.
 */
async function saveScreenshot(page, filename) {
  const buffer = await page.screenshot({ fullPage: false });
  allure.attachment(filename, buffer, 'image/png');

  if (fs.existsSync(SCREENSHOTS_DIR)) {
    fs.writeFileSync(path.join(SCREENSHOTS_DIR, filename), buffer);
    console.log(`[PASS] Screenshot saved: ${path.join(SCREENSHOTS_DIR, filename)}`);
  } else {
    console.log(`[WARN] Screenshots dir not found, skipping file save: ${SCREENSHOTS_DIR}`);
  }
}

/**
 * Navigate to the Compensation Group Schema window, open the first record,
 * and click the Template Lines tab (AD_Tab-544005).
 */
async function navigateToTemplateLinesTab(page) {
  // Navigate to the window list view
  await page.goto(`${FRONTEND_BASE_URL}/window/${COMPENSATION_GROUP_SCHEMA_WINDOW_ID}`);
  await page.locator('.document-list-wrapper, .document-list').waitFor({
    state: 'visible',
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  });

  // Open the first record
  const firstRow = page.locator('table tbody tr').first();
  await firstRow.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
  await firstRow.dblclick();

  // Wait for detail view URL pattern
  await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });

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

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await navigateToTemplateLinesTab(page);

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

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE' } },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await navigateToTemplateLinesTab(page);

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
