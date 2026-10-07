import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT, flushPendingUiTasks, waitForTypeaheadAnswer } from '../utils/common';
import { createMasterdata } from '../utils/OrderLineHarness';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';

/**
 * Cell editing on the sales order-line grid (de_DE): the grid shows its columns, a Lookup cell
 * keeps its saved value whichever way it is left, and numpad-0 opens a cell's editor.
 * Test data is created per run through the masterdata API.
 */

// M_Product_ID is the only Lookup (combobox) column among these
const GRID_COLUMNS = [
  'M_Product_ID',
  'QtyEntered',
  'C_UOM_ID',
  'PriceEntered',
  'M_AttributeSetInstance_ID',
  'Discount',
  'PriceActual',
  'LineNetAmt',
  'C_Tax_ID',
  'Description',
];
const COMBOBOX_COLUMN = 'M_Product_ID';

test.describe('Sales order-line grid — columns (de_DE)', () => {
  test('Editable order-line grid renders with a saved order line and its expected columns', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — columns');
    allure.severity('critical');
    allure.description(`
Create an order with one line: the order-line grid shows every expected column, and the Lookup
(combobox) cell opens its editor as a dropdown.
    `);

    test.setTimeout(180000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();

    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: 1, recordId });

    await test.step('Order-line grid renders every expected column', async () => {
      for (const column of GRID_COLUMNS) {
        await expect(
          page.getByTestId(`column-${column}`),
          `grid column ${column} must render`
        ).toBeVisible();
      }
    });

    await test.step('Order-line grid has at least one saved order line', async () => {
      const rows = page.locator('table tbody tr');
      await rows.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      expect(await rows.count(), 'grid must have at least one saved order line').toBeGreaterThan(0);
    });

    await test.step('The combobox (Search/Lookup) cell opens its editor as a dropdown', async () => {
      const comboboxCell = page.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`).first();
      await comboboxCell.dblclick();

      // scoped to the cell: the order header's Lookup fields have the same class
      const editor = comboboxCell.locator('.input-dropdown-container').first();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Escape');
    });

    const screenshotBuffer = await page.screenshot();
    allure.attachment('Order-line grid (de_DE)', screenshotBuffer, 'image/png');
  });
});

test.describe('Sales order-line grid — leaving a Lookup cell; numpad-0 (de_DE)', () => {
  test('A Lookup cell left with Tab, then reopened, shows its saved value; numpad-0 opens a cell editor', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — leaving a Lookup cell; numpad-0');
    allure.severity('critical');
    allure.description(`
1. Open the Lookup (product) cell and leave it with Tab without choosing a value: the cell shows
   its saved product, never the text "undefined". Open it again and close it with Escape: same.
2. Press numpad-\`0\` (\`{key:'0', keyCode:96}\`) on a cell: its editor opens.
    `);

    test.setTimeout(180000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();

    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: 1, recordId });

    const productCode = masterdata.products.Product1.productCode;

    await test.step('Activate the Lookup cell and Tab away without selecting a new value', async () => {
      const productCell = page.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`).first();
      await productCell.dblclick();
      // scoped to the cell: the order header's Lookup fields have the same class
      await productCell
        .locator('.input-dropdown-container')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Tab');
      await productCell
        .locator('.input-dropdown-container')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      await flushPendingUiTasks(page);
    });

    await test.step('No "undefined" after navigating away from the Lookup cell', async () => {
      const productCell = page.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`).first();
      const cellText = (await productCell.textContent()) || '';
      console.log(`[INFO] ${COMBOBOX_COLUMN} cell text after Tab-away: "${cellText}"`);

      expect(
        cellText,
        'Lookup cell must not render the literal "undefined" after Tab-away'
      ).not.toContain('undefined');
      expect(
        cellText,
        'Lookup cell must still show its saved product code after Tab-away'
      ).toContain(productCode);
    });

    await test.step('Navigate back to the Lookup cell — still no "undefined"', async () => {
      const productCell = page.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`).first();
      await productCell.dblclick();
      await productCell
        .locator('.input-dropdown-container')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.keyboard.press('Escape');
      await productCell
        .locator('.input-dropdown-container')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      await flushPendingUiTasks(page);

      const cellTextAfterReopen = (await productCell.textContent()) || '';
      console.log(`[INFO] ${COMBOBOX_COLUMN} cell text after nav-back: "${cellTextAfterReopen}"`);

      expect(
        cellTextAfterReopen,
        'Lookup cell must not render "undefined" after navigating back to it'
      ).not.toContain('undefined');
      expect(
        cellTextAfterReopen,
        'Lookup cell must still show its saved product code after navigating back'
      ).toContain(productCode);
    });

    await test.step('Numpad-0 activates a grid cell editor', async () => {
      const qtyCell = page.locator('[data-cy="cell-QtyEntered"]').first();
      await qtyCell.click();

      // Playwright's press('Numpad0') sends NumLock-off "Insert"; dispatch a NumLock-on numpad-0
      // (key '0', keyCode 96) instead
      await page.evaluate(() => {
        const el = document.activeElement;
        const event = new KeyboardEvent('keydown', {
          key: '0',
          code: 'Numpad0',
          bubbles: true,
          cancelable: true,
        });
        Object.defineProperty(event, 'keyCode', { get: () => 96 });
        Object.defineProperty(event, 'which', { get: () => 96 });
        el.dispatchEvent(event);
      });

      await qtyCell
        .locator('.input-body-container')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Escape');
    });
  });
});

/**
 * Typing a text that matches nothing into a filled Lookup cell and leaving it (Tab, or a click on
 * another cell) keeps the saved value, also when repeated; reopening the editor shows the saved
 * value too.
 */
test.describe('Sales order-line grid — re-editing a filled Lookup cell (de_DE)', () => {
  test('Typing a non-matching text into a filled Lookup cell and leaving it, repeatedly, keeps the saved value; re-opening never shows "undefined"', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — re-editing a filled Lookup cell');
    allure.severity('critical');
    allure.description(`
Three times (Tab, click on another cell, Tab): activate the saved \`${COMBOBOX_COLUMN}\` cell, type
a text that matches no product, leave the cell. Afterwards the cell must still show the saved
product (never the typed text, never "undefined"), and re-opening its editor must not show the
literal "undefined".
    `);

    test.setTimeout(180000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();

    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: 1, recordId });

    const productCode = masterdata.products.Product1.productCode;
    const productCell = page.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`).first();
    const NON_MATCHING_TEXT = 'zz9nomatch';

    for (const [round, leaveBy] of [
      [1, 'Tab'],
      [2, 'click on another cell'],
      [3, 'Tab'],
    ]) {
      await test.step(`Round ${round}: type a non-matching text into the filled Lookup cell, leave by ${leaveBy}`, async () => {
        await productCell.dblclick();
        const editorInput = productCell.locator('.input-dropdown-container input').first();
        await editorInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        // the typed text is added to the cell's text: the query ends with it
        const answered = waitForTypeaheadAnswer(page, (query) => query.endsWith(NON_MATCHING_TEXT));
        answered.catch(() => {}); // awaited below; avoids an unhandled rejection if typing fails first
        await editorInput.pressSequentially(NON_MATCHING_TEXT, { delay: 30 });
        await answered;
        await expect(page.locator('.input-dropdown-list .input-dropdown-list-header')).toBeVisible({
          timeout: SLOW_ACTION_TIMEOUT,
        });
        if (leaveBy === 'Tab') {
          await page.keyboard.press('Tab');
        } else {
          await page.locator('[data-cy="cell-QtyEntered"]').first().click();
        }

        await productCell
          .locator('.input-dropdown-container')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await flushPendingUiTasks(page);

        const cellText = (await productCell.textContent()) || '';
        console.log(`[INFO] round ${round} (${leaveBy}): ${COMBOBOX_COLUMN} cell text "${cellText}"`);
        expect(cellText, `round ${round}: the cell must not render the literal "undefined"`).not.toContain('undefined');
        expect(cellText, `round ${round}: the cell must not render the typed raw text`).not.toContain(NON_MATCHING_TEXT);
        expect(cellText, `round ${round}: the cell must still show its saved product`).toContain(productCode);
      });
    }

    await test.step('Re-open the Lookup editor — it must not show "undefined"', async () => {
      await productCell.dblclick();
      const editorInput = productCell.locator('.input-dropdown-container input').first();
      await editorInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const editorValue = await editorInput.inputValue();
      console.log(`[INFO] ${COMBOBOX_COLUMN} editor value on re-open: "${editorValue}"`);
      expect(editorValue, 'the re-opened Lookup editor must not show the literal "undefined"').not.toContain('undefined');
      expect(editorValue, 'the re-opened Lookup editor must not show the typed raw text').not.toContain(NON_MATCHING_TEXT);

      await page.keyboard.press('Escape');
    });
  });
});
