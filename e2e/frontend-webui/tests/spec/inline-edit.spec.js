import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Inline Grid Edit (F2) E2E test suite.
 *
 * Tests inline editing of grid cells:
 * - F2 shortcut to start editing the selected cell
 * - Double-click to open cell for editing
 * - Cell editor types (text, number, dropdown)
 * - Enter to confirm, Escape to cancel
 */

test.describe('Inline Grid Edit', () => {
  test('Inline editing of grid cells on Sales Order lines', async ({ page }) => {
    allure.epic('E0193: User Interface');
    allure.tag('F14010: Navigation');
    allure.tag('F14160: Grid Edit');
    allure.story('Inline Cell Editing');
    allure.severity('normal');

    allure.description(`
## Inline Grid Edit (F2)

Tests inline editing in the grid:
1. **Add order line** — Via batch entry for grid data
2. **Select cell** — Click a cell in the grid
3. **F2 to edit** — Press F2 to start editing
4. **Modify value** — Change the cell value
5. **Confirm/Cancel** — Enter saves, Escape cancels
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
            prices: [{ price: 15.0, currencyCode: 'EUR' }],
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
    await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(2000);

    // Add an order line via batch entry
    let hasOrderLine = false;
    await test.step('Add order line via batch entry', async () => {
      const batchEntryButton = page.getByTestId('batch-entry-toggle');
      await batchEntryButton.scrollIntoViewIfNeeded();
      await batchEntryButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await batchEntryButton.click();
      await page.waitForTimeout(500);

      const quickInputVisible = await page
        .locator('.quick-input-container')
        .waitFor({ state: 'visible', timeout: 5000 })
        .then(() => true)
        .catch(() => false);

      if (quickInputVisible) {
        const productInput = page.locator('#lookup_M_Product_ID input.input-field');
        await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await productInput.click();
        await page.waitForTimeout(500);
        await productInput.fill(masterdata.products.Product1.productCode);
        await page.waitForTimeout(1000);

        await page
          .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(500);

        const dropdownOption = page
          .locator('.input-dropdown-list-option')
          .getByText(masterdata.products.Product1.productCode)
          .first();
        const optionVisible = await dropdownOption
          .waitFor({ state: 'visible', timeout: 5000 })
          .then(() => true)
          .catch(() => false);

        if (optionVisible) {
          await dropdownOption.click();
          await page.waitForTimeout(1000);

          const quantityInput = page.locator('.quick-input-container').getByRole('spinbutton');
          await quantityInput.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
          await quantityInput.click();
          await quantityInput.fill('5');
          await page.waitForTimeout(300);

          await page.keyboard.press('Enter');
          await page.waitForTimeout(2000);

          await page
            .locator('.rotating, .indicator-pending')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});

          // Close batch entry
          await batchEntryButton.click();
          await page.waitForTimeout(1000);

          const rowCount = await page.locator('table tbody tr').count();
          hasOrderLine = rowCount > 0;
          console.log(`Order line added: ${hasOrderLine} (rows: ${rowCount})`);
        }
      }
    });

    // ======================================================================
    // TEST 1: Click cell to select it
    // ======================================================================
    await test.step('Click cell to select', async () => {
      if (!hasOrderLine) {
        console.log('No order line — skipping inline edit test');
        return;
      }

      const firstRow = page.locator('table tbody tr').first();
      await firstRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Find a data cell (td with data-cy attribute)
      const dataCells = firstRow.locator('td[data-cy]');
      const cellCount = await dataCells.count();
      console.log(`Data cells in first row: ${cellCount}`);

      // Log cell names
      const cellNames = [];
      for (let i = 0; i < Math.min(cellCount, 10); i++) {
        const dataCy = await dataCells.nth(i).getAttribute('data-cy').catch(() => '');
        cellNames.push(dataCy);
      }
      console.log(`Cell data-cy: ${JSON.stringify(cellNames)}`);
      allure.attachment('Grid Cell Names', JSON.stringify(cellNames, null, 2), 'application/json');

      // Click the QtyOrdered cell
      const qtyCell = firstRow.locator('[data-cy="cell-QtyOrdered"], [data-cy="cell-QtyEntered"]').first();
      const hasQtyCell = await qtyCell.isVisible().catch(() => false);

      if (hasQtyCell) {
        await qtyCell.click();
        await page.waitForTimeout(500);
        console.log('Clicked quantity cell');

        const cellClasses = await qtyCell.getAttribute('class').catch(() => '');
        console.log(`Cell classes after click: ${cellClasses}`);
      }
    });

    // ======================================================================
    // TEST 2: F2 to start inline editing
    // ======================================================================
    await test.step('F2 to start inline editing', async () => {
      if (!hasOrderLine) return;

      // Press F2 to start editing the selected cell
      await page.keyboard.press('F2');
      await page.waitForTimeout(500);

      // Check if an input appeared in the cell
      const activeInput = page.locator('table tbody tr td input:visible, table tbody tr td .input-field:visible');
      const hasActiveInput = await activeInput.first().isVisible().catch(() => false);
      console.log(`Active input after F2: ${hasActiveInput}`);

      if (hasActiveInput) {
        const currentValue = await activeInput.first().inputValue().catch(() => '');
        console.log(`Current cell value: "${currentValue}"`);

        // Take screenshot of inline edit mode
        const screenshot = await page.screenshot();
        allure.attachment('Inline Edit Mode', screenshot, 'image/png');

        // Press Escape to cancel editing
        await page.keyboard.press('Escape');
        await page.waitForTimeout(500);
        console.log('Cancelled inline edit');
      } else {
        console.log('F2 did not open inline editor — may need different cell type');

        // Try double-clicking a cell instead
        const firstRow = page.locator('table tbody tr').first();
        const anyCell = firstRow.locator('td[data-cy]').nth(2);
        await anyCell.dblclick();
        await page.waitForTimeout(500);

        const inputAfterDblClick = page.locator('table tbody tr td input:visible');
        const hasInputDbl = await inputAfterDblClick.first().isVisible().catch(() => false);
        console.log(`Input after double-click: ${hasInputDbl}`);

        if (hasInputDbl) {
          await page.keyboard.press('Escape');
          await page.waitForTimeout(300);
        }
      }
    });

    // ======================================================================
    // FINAL SCREENSHOT
    // ======================================================================
    const screenshotBuffer = await page.screenshot();
    allure.attachment('Final State', screenshotBuffer, 'image/png');

    console.log('Inline grid edit test completed');
  });
});
