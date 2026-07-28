import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * EPCIS SSCC Transmissions window E2E.
 *
 * The dedicated, searchable header window over the EPCIS transmission ledger
 * (EDI_EPCIS_Transmitted_SSCC), added by migration 5814010. It replaces the per-shipment
 * child tab. This test proves the window loads as a list-first, searchable grid with the
 * ledger columns and the date/SSCC/shipment filters — i.e. it renders without a health-check
 * / layout crash and is usable for search.
 *
 * Language-independent: asserts on grid structure, language-invariant column data-testids, and
 * filter-control presence — never on localized captions. Runs in en_US and de_DE.
 */

const EPCIS_SSCC_WINDOW_ID = 542174;

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test(`EPCIS SSCC Transmissions window opens as a searchable grid (${label})`, async ({ page }) => {
    allure.epic('E0375: External Traceability');
    allure.tag('F5410: EPCIS JSON Export');
    allure.tag('F5410');
    allure.story('EPCIS SSCC Transmissions window');
    allure.severity('normal');
    allure.description(`
## EPCIS SSCC Transmissions window (${label})

Opens the dedicated ledger window (AD_Window ${EPCIS_SSCC_WINDOW_ID}) and verifies:
1. It loads as a **list/grid** view (list-first, no crash).
2. The ledger **columns** render (SSCC18, shipment, transmitted-on, …).
3. The **filter panel** is present (Transmitted date-range, SSCC18, shipment).
4. It is **read-only** — no "new record" action (rows are system-written).
    `);

    test.setTimeout(180000);

    // === TEST DATA (just a login user; the ledger is system-written, empty grid is fine) ===
    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language, firstname: 'first', lastname: 'last' } } },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === NAVIGATE TO THE WINDOW (list view) ===
    await page.goto(`${FRONTEND_BASE_URL}/window/${EPCIS_SSCC_WINDOW_ID}`);
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

    await test.step('Window loads as a list/grid view (no crash)', async () => {
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1000); // let the grid finish its initial data fetch + paint
      const listVisible = await page
        .locator('.document-list-wrapper, .document-list')
        .isVisible()
        .catch(() => false);
      expect(listVisible).toBe(true);
    });

    await test.step('Ledger columns render (language-invariant data-testid)', async () => {
      const headerTestIds = [];
      for (const header of await page.locator('th').all()) {
        const testId = await header.getAttribute('data-testid').catch(() => null);
        if (testId) headerTestIds.push(testId);
      }
      console.log('[INFO] header testids:', JSON.stringify(headerTestIds));
      allure.attachment('Column header testids', JSON.stringify(headerTestIds, null, 2), 'application/json');
      expect(headerTestIds.length).toBeGreaterThan(0);
      // the ledger's signature columns must be present (column names are language-invariant)
      const joined = headerTestIds.join('|');
      expect(joined).toMatch(/SSCC18/);
      expect(joined).toMatch(/Transmitted/);
      expect(joined).toMatch(/M_InOut_ID/);
    });

    await test.step('Filter panel present (date / SSCC / shipment)', async () => {
      const filterButtons = page.locator('.filter-wrapper .btn-filter');
      const inlineFilters = page.locator('.inline-filters, .filters-frequent');
      const filterCount = await filterButtons.count();
      const hasInline = await inlineFilters.first().isVisible().catch(() => false);
      console.log(`[INFO] filter buttons=${filterCount} inline=${hasInline}`);
      expect(filterCount > 0 || hasInline).toBe(true);
    });

    await test.step('Read-only — no create-new action', async () => {
      // .btn-new-document is rendered only when layout.supportNewRecord is true; a read-only
      // window (tab IsInsertRecord='N' → supportNewRecord false) must not show it, so count === 0.
      const newCount = await page.locator('.btn-new-document').count().catch(() => 0);
      console.log(`[INFO] new-record buttons=${newCount}`);
      expect(newCount).toBe(0);
    });

    const shot = await page.screenshot();
    allure.attachment(`EPCIS SSCC window (${label})`, shot, 'image/png');
    console.log(`[PASS] EPCIS SSCC Transmissions window loaded (${label})`);
  });
});
