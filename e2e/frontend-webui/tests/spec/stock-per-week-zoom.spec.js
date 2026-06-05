import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  waitForSpinnersToDisappear,
} from '../utils/DocumentReferences';

/**
 * "Bestand pro Woche" (Stock per week) order-line zoom — E2E (me03 25618 / F19100).
 *
 * Validates the read-only weekly-stock window (AD_Window_ID 542159) reached via the
 * AD_RelationType zoom (540499, InternalName C_OrderLine_MD_Stock_PerWeek) from a
 * sales-order line. The zoom filters MD_Stock_PerWeek_V to the line's product +
 * resolved storage warehouse (MD_getStockWarehouse), anchored at week(DatePromised).
 *
 * KEY ASSERTIONS (the open question this test answers):
 *  - the zoom link surfaces on a C_OrderLine and navigates to window 542159
 *  - the weekly grid actually RENDERS ROWS in the WebUI
 *  - the WebUI handles row identity for the synthetic row_number PK
 *    (MD_Stock_PerWeek_V_ID) — rows are selectable, no error toast
 *  - the window is read-only (no New / no editable cells)
 */

// data-cy is derived from AD_RelationType.InternalName: reference-<InternalName>
const STOCK_PER_WEEK_REFERENCE_DATA_CY = 'reference-C_OrderLine_MD_Stock_PerWeek';
const STOCK_PER_WEEK_WINDOW_ID = 542159;
const SO_LINE_TAB = '#AD_Tab-187'; // C_OrderLine tab in the Sales Order window (143)

// German grid headers expected on the weekly-stock window (de_DE session).
const EXPECTED_GRID_HEADERS = [
  'Produkt',
  'Lager',
  'Wochenbeginn',
  'Erwartete Lieferungen',
  'Erwartete Wareneingänge',
  'Verfügbar (ATP)',
];

test.describe('Stock per week (Bestand pro Woche) — order-line zoom', () => {
  test('Zoom from a sales-order line opens the read-only weekly-stock grid with rows', async ({ page }) => {
    allure.epic('E0155: Material Disposition');
    allure.tag('F19100: Stock per week');
    allure.tag('F19100');
    allure.story('Order-line zoom → Bestand pro Woche (read-only weekly grid)');
    allure.severity('critical');
    allure.description(`
## Bestand pro Woche — order-line zoom

Creates a sales order with one line, completes it (which drives the dispo engine to
create MD_Candidate records → MD_Stock_PerWeek_V rows), then zooms from the order line
to the read-only weekly-stock window via the AD_RelationType document reference.

Verifies the zoom opens window ${STOCK_PER_WEEK_WINDOW_ID}, the weekly grid renders rows,
the synthetic row_number PK behaves in the WebUI (rows selectable, no error), and the
window is read-only.
    `);

    test.setTimeout(240000); // 4 minutes — SO completion + async dispo + zoom retries

    // === CREATE TEST DATA (de_DE so the grid shows German headers) ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'first', lastname: 'last' } },
        bpartners: {
          CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
        },
        products: {
          Product1: { name: 'PROD', type: 'Item', prices: [{ price: 30.0, currencyCode: 'EUR' }] },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === CREATE + COMPLETE SALES ORDER WITH ONE LINE ===
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const soRecordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({
      product: masterdata.products.Product1.productCode,
      quantity: '8',
      recordId: soRecordId,
    });
    await SalesOrderPage.complete();
    const soDocumentNo = await SalesOrderPage.getDocumentNo();
    console.log(`Sales Order completed: ${soDocumentNo} (record=${soRecordId})`);

    // Give the dispo engine time to materialise MD_Candidate rows for product+warehouse.
    await page.waitForTimeout(8000);

    // === OPEN THE ZOOM via right-click on the order line row, retrying for async dispo ===
    // NOTE: The C_OrderLine_MD_Stock_PerWeek relation type is sourced from C_OrderLine (table 260).
    // The Alt+6 document references panel only fetches references for the header document (C_Order).
    // Row-level references — including this zoom — are exposed via the right-click context menu on
    // the order line row, which calls /window/{wId}/{docId}/{tabId}/{rowId}/references/sse.
    // After the frontend change that adds data-cy="reference-<InternalName>" to ContextMenuItem,
    // we can find the zoom link by its data-cy in the context menu.
    //
    // IMPORTANT: TableContextMenu.handleReferenceClick calls window.open(url, '_blank'), so the
    // weekly-stock grid opens in a NEW browser tab — the current tab's URL stays on the SO record.
    // We must intercept the new page via page.context().waitForEvent('page') around the click.
    const referenceLink = page.locator(`[data-cy="${STOCK_PER_WEEK_REFERENCE_DATA_CY}"]`);

    // weekPage is set once the reference click triggers the new-tab open.
    let weekPage = null;

    await test.step('Open Bestand pro Woche zoom from the order line (right-click context menu)', async () => {
      // Make sure the C_OrderLine tab is active first.
      const tab = page.locator(SO_LINE_TAB);
      if (await tab.isVisible().catch(() => false)) {
        await tab.click();
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      }

      const maxAttempts = 12;
      for (let attempt = 1; attempt <= maxAttempts; attempt++) {
        // Right-click the first order line row to open the context menu.
        const firstLine = page.locator('table tbody tr').first();
        await firstLine.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await firstLine.click({ button: 'right' }).catch(() => {});
        await page.waitForTimeout(800); // give the SSE references a moment to stream

        // Wait for spinners in the context menu to disappear.
        await waitForSpinnersToDisappear();
        await page.waitForTimeout(500);

        // Collect visible reference links in the context menu for debugging.
        const refs = await page.locator('[data-cy^="reference-"]').all();
        const refNames = await Promise.all(refs.map((r) => r.getAttribute('data-cy').catch(() => '?')));
        console.log(`[attempt ${attempt}] context-menu references: ${refNames.join(', ')}`);

        if (await referenceLink.isVisible().catch(() => false)) {
          // The reference click opens the grid in a new tab (_blank). Intercept it.
          const newPagePromise = page.context().waitForEvent('page', { timeout: VERY_SLOW_ACTION_TIMEOUT });
          await referenceLink.click();
          weekPage = await newPagePromise;
          await weekPage.waitForLoadState('domcontentloaded', { timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});
          await weekPage.waitForTimeout(1500);
          return;
        }

        // Reference not yet visible — close context menu and retry after page refresh.
        await page.keyboard.press('Escape');
        if (attempt < maxAttempts) {
          await page.keyboard.press('F5');
          await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
          await page.waitForTimeout(2500);
        }
      }
      throw new Error(
        `Zoom reference "${STOCK_PER_WEEK_REFERENCE_DATA_CY}" never appeared in the order-line context menu.`
      );
    });

    // All remaining assertions run on the new tab (weekPage).
    expect(weekPage, 'A new browser tab must have opened for the weekly-stock grid').not.toBeNull();

    // === ASSERT: the new tab is on the weekly-stock window 542159 ===
    await test.step('Verify the new tab URL contains window 542159', async () => {
      await weekPage.waitForURL(new RegExp(`/window/${STOCK_PER_WEEK_WINDOW_ID}(\\?|/|$)`), {
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });
      await weekPage.locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      console.log(`New-tab URL: ${weekPage.url()}`);
    });

    // === ASSERT: grid renders rows (the row_number PK works in the WebUI) ===
    await test.step('Verify the weekly grid renders rows', async () => {
      const rows = weekPage.locator('table tbody tr');
      await rows.first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      const rowCount = await rows.count();
      console.log(`Weekly-stock grid rows: ${rowCount}`);
      expect(rowCount).toBeGreaterThan(0);

      // No error toast must have appeared (PK/row-identity handling is sound).
      const errorToast = weekPage.locator('.notification-error, .toast-error');
      expect(await errorToast.count().catch(() => 0)).toBe(0);
    });

    // === ASSERT: row identity works — first row is selectable ===
    await test.step('Verify row identity (select a row, no error)', async () => {
      const firstRow = weekPage.locator('table tbody tr').first();
      await firstRow.click();
      await weekPage.waitForTimeout(500);
      // selecting must not raise an error toast
      const errorToast = weekPage.locator('.notification-error, .toast-error');
      expect(await errorToast.count().catch(() => 0)).toBe(0);
    });

    // === ASSERT: expected German grid headers are present ===
    await test.step('Verify German grid headers', async () => {
      const headerText = (await weekPage.locator('table thead').first().innerText().catch(() => '')) || '';
      const allHeaders = (await weekPage.locator('.column-name, th').allInnerTexts().catch(() => [])).join(' | ');
      const haystack = `${headerText} | ${allHeaders}`;
      console.log(`Grid headers seen: ${haystack}`);
      for (const expected of EXPECTED_GRID_HEADERS) {
        expect(haystack).toContain(expected);
      }
    });

    // === ASSERT: window is read-only (no New action) ===
    await test.step('Verify the window is read-only', async () => {
      // The weekly-stock view is read-only — no "New" button must be present.
      const newButton = weekPage.getByTestId('window-new');
      const hasNew = await newButton.isVisible().catch(() => false);
      expect(hasNew).toBe(false);
    });

    const screenshot = await weekPage.screenshot();
    allure.attachment('Bestand pro Woche zoom — final', screenshot, 'image/png');
    console.log('Stock-per-week order-line zoom test completed');
  });
});
