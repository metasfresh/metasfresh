import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  createMasterdata,
  gotoOrderList,
  createNewOrder,
  selectOrderCustomer,
  addOrderLine,
} from '../utils/OrderLineHarness';

/**
 * Thin E2E harness for the core Sales Order Line grid.
 *
 * HARNESS ONLY: login + open + seed test steps. The reusable helpers themselves (masterdata,
 * navigation, seeding) live in `../utils/OrderLineHarness` — later legs (e.g. the layout-jump
 * geometry spec) import them from there rather than duplicating them or importing this
 * `.spec.js` file (which would re-register this file's own `test()` cases wherever imported).
 *
 * Targets the core Sales Order window (see `SALES_ORDER_WINDOW_ID`) / Order Line tab — the
 * window every plain-core customer opens, present on the core `-preloaded` CI image. All test
 * data (user, customer, product with a resolvable price) is created fresh per run via the
 * Backend masterdata API, so this spec needs no customer-specific seed data.
 */

// Order-line grid columns this harness checks render. M_Product_ID is the only Search/Lookup
// (combobox) column among these.
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

test.describe('Sales order-line grid — thin bugfix E2E harness (de_DE)', () => {
  test('Editable order-line grid renders with a saved order line and its expected columns', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — thin bugfix E2E harness');
    allure.severity('critical');
    allure.description(`
## Thin bugfix E2E harness

Login (fresh masterdata user, de_DE) -> open the core Sales Order window -> create a new order ->
select the seeded customer -> add one order line via batch entry, leaving it SAVED -> smoke-assert
the order-line grid renders every expected column, including the Search/Lookup (combobox) column,
and that its cell editor opens as a combobox.

This is the reusable harness (login/open/seed) that later legs extend with their own defect
assertions in this same file.
    `);

    test.setTimeout(180000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await gotoOrderList();

    const recordId = await createNewOrder();
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await addOrderLine(recordId, { productCode: masterdata.products.Product1.productCode, quantity: 1 });

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

      // Scoped to the cell itself — a bare page-wide `.input-dropdown-container` also matches
      // every Lookup field on the order HEADER form (e.g. C_BPartner_ID), so `.first()` would
      // pass vacuously even if the grid cell's own editor never opened.
      const editor = comboboxCell.locator('.input-dropdown-container').first();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Escape');
    });

    const screenshotBuffer = await page.screenshot();
    allure.attachment('Order-line grid (de_DE)', screenshotBuffer, 'image/png');
  });
});

/**
 * Defect-assertion leg (BF-H1b): the "undefined" standalone type-guard (AC12) and the
 * numpad-0 activation fix (AC14), driven against the real order-line grid seeded by the
 * harness above.
 *
 * The third BF-H1b defect — no row-height/column-width jump on cell activation (AC15) — is
 * NOT duplicated here: it already has its own dedicated geometry spec,
 * `grid-no-layout-jump.spec.js` (DE + EN), which asserts it more precisely than a smoke
 * check in this file could.
 */
test.describe('Sales order-line grid — bugfix defect assertions (de_DE)', () => {
  test('No literal "undefined" after navigating away from and back to the Lookup cell; numpad-0 activates a cell', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — bugfix defect assertions');
    allure.severity('critical');
    allure.description(`
## Defect assertions (AC12, AC14)

1. Activate the Lookup (Search/Product) cell, then Tab away **without** selecting a new value —
   the object-valued type-guard must skip the raw-text row-write, so the cell keeps showing its
   saved product code, never the literal string "undefined". Re-activating the cell and
   navigating away again ("and back") must be equally clean.
2. Numpad-\`0\` (\`{key:'0', keyCode:96}\`) must activate a grid cell's editor, the same as
   main-row \`0\`.

Layout-jump (AC15) is covered separately by \`grid-no-layout-jump.spec.js\` — not duplicated here.
    `);

    test.setTimeout(180000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await gotoOrderList();

    const recordId = await createNewOrder();
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await addOrderLine(recordId, { productCode: masterdata.products.Product1.productCode, quantity: 1 });

    const productCode = masterdata.products.Product1.productCode;

    await test.step('Activate the Lookup cell and Tab away without selecting a new value', async () => {
      const productCell = page.locator(`[data-cy="cell-${COMBOBOX_COLUMN}"]`).first();
      await productCell.dblclick();
      // Scoped to the cell itself — a page-wide `.input-dropdown-container` also matches every
      // Lookup field on the order HEADER form (always present), which would make both the
      // "visible" wait vacuous and the "detached" wait below never resolve.
      await productCell
        .locator('.input-dropdown-container')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Tab');
      await productCell
        .locator('.input-dropdown-container')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await page.waitForTimeout(500);
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
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await page.waitForTimeout(500);

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

      // Playwright's `keyboard.press('Numpad0')` emulates NumLock OFF (produces "Insert",
      // keyCode 45), not a real numpad-0 press (key '0', keyCode 96). Dispatch the exact
      // physical-numpad-0 event shape directly on the focused cell — the real defect this
      // leg reproduces (AC14) is specifically keyCode 96, per the reporter's numpad key.
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
      await page.waitForTimeout(300);

      await qtyCell
        .locator('.input-body-container')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Escape');
    });
  });
});

/**
 * "undefined" regression on the committed-value re-edit gesture (AC12).
 *
 * The reported sighting: a FILLED Lookup cell is re-edited, a non-matching text is typed, the
 * user leaves the cell, and does that 2-3 times — the cell then shows the saved value with the
 * typed raw text appended, and re-opening the editor shows the literal "undefined". The
 * mechanism is the grid nav layer writing the editor's raw string over the Lookup's
 * `{key,caption}` value on Tab (`TableRow.handleKeyDown_Tab`); the object-valued guard
 * (`TableRow.isObjectValuedWidget`) skips that write. This leg drives exactly that gesture
 * (Tab, a click on another cell, Tab), then re-opens the editor.
 *
 * Enter is deliberately not part of this gesture: Enter on a Lookup with no matching result
 * clears the cell, on the base branch exactly as with this change — that resolve-or-revert
 * behaviour is a separate topic, not the "undefined" defect guarded here.
 */
test.describe('Sales order-line grid — no "undefined" after re-editing a filled Lookup cell (de_DE)', () => {
  test('Typing a non-matching text into a filled Lookup cell and leaving it, repeatedly, keeps the saved value; re-opening never shows "undefined"', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — no "undefined" on Lookup re-edit');
    allure.severity('critical');
    allure.description(`
## Re-edit a filled Lookup cell (AC12)

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

    await gotoOrderList();

    const recordId = await createNewOrder();
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await addOrderLine(recordId, { productCode: masterdata.products.Product1.productCode, quantity: 1 });

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

        await editorInput.pressSequentially(NON_MATCHING_TEXT, { delay: 30 });
        await page.waitForTimeout(800); // let the typeahead query answer "no results"
        if (leaveBy === 'Tab') {
          await page.keyboard.press('Tab');
        } else {
          await page.locator('[data-cy="cell-QtyEntered"]').first().click();
        }

        await productCell
          .locator('.input-dropdown-container')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
        await page.waitForTimeout(500);

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
