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

      const editor = page.locator('.input-dropdown-container').first();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page.keyboard.press('Escape');
    });

    const screenshotBuffer = await page.screenshot();
    allure.attachment('Order-line grid (de_DE)', screenshotBuffer, 'image/png');
  });
});
