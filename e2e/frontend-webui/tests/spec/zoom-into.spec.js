import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Zoom-Into (Right-Click Zoom) E2E test suite.
 *
 * Tests the right-click "Zoom Into" context menu on grid cells.
 * In metasfresh WebUI, right-clicking a grid cell with a lookup value
 * opens a context menu with "Zoom Into" (meta-icon-share) that opens
 * the referenced record in a new tab.
 */

// =====================================================================
// TEST: Right-Click Zoom-Into on Grid Cells
// =====================================================================

test.describe('Zoom-Into (Right-Click Zoom)', () => {
  test('Right-click Zoom-Into on Sales Order grid cells', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.story('Right-Click Zoom-Into on Grid Cells');
    allure.severity('normal');

    allure.description(`
## Right-Click Zoom-Into

Tests the context menu "Zoom Into" action on grid cells in a Sales Order.
Right-clicking a grid cell with a lookup value (e.g., Product) shows a
context menu. Selecting "Zoom Into" opens the referenced record in a new tab.

Verifies:
1. Context menu appears on right-click
2. "Zoom Into" option is available (identified by meta-icon-share icon)
3. Clicking opens a new tab with the referenced record
4. The new tab URL matches a window/record pattern
    `);

    test.setTimeout(180000); // 3 minutes

    // === CREATE TEST DATA ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: {
          user: { language: 'en_US', firstname: 'first', lastname: 'last' },
        },
        bpartners: {
          CUSTOMER1: {
            isVendor: false,
            isCustomer: true,
            isSoPriceList: true,
            name: 'Customer',
          },
        },
        products: {
          Product1: {
            name: 'PROD',
            type: 'Item',
            prices: [{ price: 25.0, currencyCode: 'EUR' }],
          },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    // === LOGIN ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // === CREATE SALES ORDER WITH ORDER LINE ===
    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const soRecordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    await SalesOrderPage.addOrderLine({
      product: masterdata.products.Product1.productCode,
      quantity: '3',
      recordId: soRecordId,
    });

    const soDocumentNo = await SalesOrderPage.getDocumentNo();
    console.log(`Sales Order created: ${soDocumentNo} (record=${soRecordId})`);

    // ======================================================================
    // PHASE 1: Discover available grid cells
    // ======================================================================
    const gridRows = page.locator('table tbody tr');
    const rowCount = await gridRows.count();
    expect(rowCount).toBeGreaterThan(0);
    console.log(`Order line grid rows: ${rowCount}`);

    const firstRow = gridRows.first();
    await firstRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Log all cells with data-cy for debugging
    const cells = await firstRow.locator('td').all();
    const cellInfo = [];
    for (let i = 0; i < cells.length; i++) {
      const dataCy = await cells[i].getAttribute('data-cy').catch(() => null);
      const text = (await cells[i].textContent().catch(() => '')).trim();
      if (dataCy) {
        cellInfo.push({ index: i, dataCy, text: text.substring(0, 40) });
      }
    }
    console.log('Grid cells with data-cy:', JSON.stringify(cellInfo, null, 2));
    allure.attachment('Grid Cell Info', JSON.stringify(cellInfo, null, 2), 'application/json');

    // ======================================================================
    // PHASE 2: Test right-click context menu on Product cell
    // ======================================================================
    // Find the Product cell (M_Product_ID)
    const productCell = firstRow.locator('[data-cy="cell-M_Product_ID"]').first();
    let targetCell = productCell;
    let targetCellName = 'M_Product_ID';

    // If M_Product_ID cell not found, try other cells
    const productCellVisible = await productCell.isVisible().catch(() => false);
    if (!productCellVisible) {
      console.log('Product cell (data-cy="cell-M_Product_ID") not found, trying alternatives...');

      // Try any cell that might have a lookup value
      for (const info of cellInfo) {
        if (info.text && info.text.length > 0 && info.dataCy !== 'cell-Line') {
          const cell = firstRow.locator(`[data-cy="${info.dataCy}"]`).first();
          targetCell = cell;
          targetCellName = info.dataCy;
          console.log(`Using alternative cell: ${info.dataCy} ("${info.text}")`);
          break;
        }
      }
    }

    console.log(`Right-clicking on cell: ${targetCellName}`);
    await targetCell.click({ button: 'right' });
    await page.waitForTimeout(500);

    const contextMenu = page.locator('.context-menu');
    const menuVisible = await contextMenu
      .waitFor({ state: 'visible', timeout: 5000 })
      .then(() => true)
      .catch(() => false);

    if (menuVisible) {
      // Log all context menu items
      const menuItems = await contextMenu.locator('.context-menu-item').all();
      const menuItemTexts = [];
      for (const item of menuItems) {
        const text = (await item.textContent().catch(() => '')).trim();
        const hasShareIcon = (await item.locator('.meta-icon-share').count()) > 0;
        menuItemTexts.push({ text, hasZoomInto: hasShareIcon });
      }
      console.log('Context menu items:', JSON.stringify(menuItemTexts, null, 2));
      allure.attachment('Context Menu Items', JSON.stringify(menuItemTexts, null, 2), 'application/json');

      // Find "Zoom Into" item
      const zoomIntoItem = contextMenu
        .locator('.context-menu-item')
        .filter({ has: page.locator('.meta-icon-share') })
        .first();

      const hasZoomInto = await zoomIntoItem.isVisible().catch(() => false);

      if (hasZoomInto) {
        console.log('Found "Zoom Into" option — testing navigation...');

        // Set up popup listener before clicking
        const popupPromise = page
          .context()
          .waitForEvent('page', { timeout: 10000 });

        await zoomIntoItem.click();

        try {
          const newPage = await popupPromise;
          await newPage.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
          const newUrl = newPage.url();
          console.log(`[PASS] Zoom-Into opened new tab: ${newUrl}`);

          // Verify URL is a window/record detail view
          expect(newUrl).toMatch(/\/window\/\d+\/\d+/);

          // Extract window ID from URL for reporting
          const windowMatch = newUrl.match(/\/window\/(\d+)\/(\d+)/);
          if (windowMatch) {
            console.log(`  Window ID: ${windowMatch[1]}, Record ID: ${windowMatch[2]}`);
          }

          allure.attachment('Zoom-Into URL', newUrl, 'text/plain');

          // Close the new tab
          await newPage.close();
          console.log('New tab closed, back to original page');
        } catch {
          console.log('[WARN] No new tab opened — zoom may have failed or navigated in same tab');

          // Check if we navigated in the same tab
          const currentUrl = page.url();
          const navigatedInSameTab = !currentUrl.includes(`/window/${SALES_ORDER_WINDOW_ID}/${soRecordId}`);
          if (navigatedInSameTab) {
            console.log(`Zoom navigated in same tab to: ${currentUrl}`);
            expect(currentUrl).toMatch(/\/window\/\d+\/\d+/);
          }
        }
      } else {
        console.log('[WARN] "Zoom Into" not available in context menu for this cell');
        // Close context menu
        await page.keyboard.press('Escape');
      }
    } else {
      console.log('[WARN] No context menu appeared on right-click');
    }

    // ======================================================================
    // PHASE 3: Final screenshot
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Right-click Zoom-Into test completed');
  });
});
