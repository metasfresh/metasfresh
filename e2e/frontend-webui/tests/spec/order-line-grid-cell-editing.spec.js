import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT, flushPendingUiTasks, waitForTypeaheadAnswer, waitForWebFonts } from '../utils/common';
import { VIEWPORT, compareGeometry, measureRow } from '../utils/GridGeometry';
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

/**
 * The product Lookup cell's clear button ("x") and choosing a product again, in a grid whose
 * columns have their automatic width (no width stored by the user): neither changes any column
 * width, and the cleared (mandatory, red-bordered) editor's content stays inside its border.
 */
test.describe('Sales order-line grid — clearing and refilling a Lookup cell (de_DE)', () => {
  test('Clearing the product Lookup cell with its "x" and choosing the product again changes no column width; the editor content stays inside the red border', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — clearing a Lookup cell');
    allure.severity('critical');
    allure.description(`
Fresh browser, so no column width is stored and every column has its automatic width.
1. Open the product cell's editor, click its clear button ("x"): every column keeps its width, and
   the empty mandatory editor's content fits inside its red border.
2. Choose the same product again: every column still keeps its width.
    `);

    test.setTimeout(180000);
    await page.setViewportSize(VIEWPORT);

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
    const row = page.locator('table tbody tr').first();
    const productCell = row.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`);
    const editor = productCell.locator('.input-dropdown-container').first();
    let before;

    await test.step('No column width is stored: the grid uses automatic widths', async () => {
      const storedWidthKeys = await page.evaluate(() =>
        Object.keys(window.localStorage).filter((key) => key.startsWith('columnWidths_'))
      );
      expect(storedWidthKeys, 'a fresh browser has no stored column width').toEqual([]);
    });

    await test.step('Open the product cell editor', async () => {
      await waitForWebFonts(page);
      before = await measureRow(row);
      await test.info().attach('grid row before editing', { body: await row.screenshot(), contentType: 'image/png' });
      await productCell.dblclick();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await flushPendingUiTasks(page);
      expect.soft(compareGeometry('editor open', COMBOBOX_COLUMN, before, await measureRow(row))).toEqual([]);
    });

    await test.step('Clear the product with the "x": no column width changes; the content fits inside the red border', async () => {
      await productCell.locator('.meta-icon-close-alt').click();
      await expect(editor, 'the cleared mandatory editor shows the red border').toHaveClass(/input-mandatory/);
      await flushPendingUiTasks(page);

      expect.soft(compareGeometry('after clear', COMBOBOX_COLUMN, before, await measureRow(row))).toEqual([]);

      // every painted (non-transparent) box of the editor stays inside its red border
      const overflowing = await editor.evaluate((container) => {
        const outer = container.getBoundingClientRect();
        const cs = getComputedStyle(container);
        const innerTop = outer.top + parseFloat(cs.borderTopWidth);
        const innerBottom = outer.bottom - parseFloat(cs.borderBottomWidth);
        return Array.from(container.querySelectorAll('*'))
          .filter((el) => !el.closest('.input-dropdown-list'))
          .filter((el) => getComputedStyle(el).backgroundColor !== 'rgba(0, 0, 0, 0)')
          .map((el) => ({ el: `${el.tagName}.${el.className}`, box: el.getBoundingClientRect() }))
          .filter(({ box }) => box.height > 0 && (box.top < innerTop - 0.5 || box.bottom > innerBottom + 0.5))
          .map(({ el, box }) => `${el}: ${box.top}..${box.bottom} outside the border's inner edge ${innerTop}..${innerBottom}`);
      });
      await test.info().attach('grid row after clear', { body: await row.screenshot(), contentType: 'image/png' });
      expect.soft(overflowing, 'no painted box of the cleared editor covers its red border').toEqual([]);
    });

    await test.step('Choose the same product again: no column width changes', async () => {
      const input = editor.locator('input').first();
      const answered = waitForTypeaheadAnswer(page, productCode);
      await input.pressSequentially(productCode, { delay: 20 });
      await answered;
      const option = page.locator('.input-dropdown-list-option').filter({ hasText: productCode }).first();
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await option.click();
      await expect(productCell).toContainText(productCode, { timeout: SLOW_ACTION_TIMEOUT });
      await flushPendingUiTasks(page);

      await test.info().attach('grid row after re-select', { body: await row.screenshot(), contentType: 'image/png' });
      expect.soft(compareGeometry('after re-select', COMBOBOX_COLUMN, before, await measureRow(row))).toEqual([]);
    });
  });
});
