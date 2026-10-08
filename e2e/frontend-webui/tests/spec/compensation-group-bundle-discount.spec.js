import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getFieldData, getTabRows } from '../utils/WebAPIValidation';

/**
 * F00102 — Compensation group: a bundle ("Handelsstückliste") with a percent discount.
 *
 * The bundle product carries a compensation-group schema with three priced goods and one 3 % discount
 * line. Entering the bundle product in a sales order through the order-line quick input explodes the
 * schema into the three goods lines plus the discount line. The discount is 3 % of the goods' sum.
 *
 * The user then changes the quantity of one goods line in the order-line grid: the discount line is
 * recomputed on the new goods sum. Completing the order keeps that discount.
 *
 *   Desk  1 x 100,00 = 100,00
 *   Chair 1 x  50,00 =  50,00   -> changed to 3 x 50,00 = 150,00
 *   Lamp  1 x  20,00 =  20,00
 *   discount 3 % of 170,00 = -5,10   -> 3 % of 270,00 = -8,10
 */

const ORDER_LINE_TAB = 'AD_Tab-187';
const DISCOUNT_PERCENT = 3;
const GOODS = {
  DESK: { name: 'Desk', price: 100 },
  CHAIR: { name: 'Chair', price: 50 },
  LAMP: { name: 'Lamp', price: 20 },
};
const CHAIR_NEW_QTY = 3;

test.describe('Compensation group bundle with a percent discount (F00102)', () => {
  test('bundle via quick input: discount line on the goods sum, recomputed on a quantity change, kept on completion', async ({ page }) => {
    allure.epic('E0100: Sales');
    allure.tag('F00102: Compensation Group');
    allure.tag('F00100: Sales Order');
    allure.story('Bundle schema with a percent discount line, exploded by the order-line quick input');
    allure.severity('critical');

    // masterdata + one order created, edited and completed in the UI
    test.setTimeout(180000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'first', lastname: 'last' } },
        bpartners: {
          CUSTOMER: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'BundleCustomer' },
        },
        products: {
          // the bundle ("trigger") product: entering it in the quick input explodes its schema
          BUNDLE: {
            name: 'OfficeSet',
            type: 'Item',
            isStocked: false,
            compensationGroupSchema: 'officeSetSchema',
            prices: [{ price: 1, currencyCode: 'EUR' }],
          },
          DESK: { name: GOODS.DESK.name, type: 'Item', isStocked: false, prices: [{ price: GOODS.DESK.price, currencyCode: 'EUR' }] },
          CHAIR: { name: GOODS.CHAIR.name, type: 'Item', isStocked: false, prices: [{ price: GOODS.CHAIR.price, currencyCode: 'EUR' }] },
          LAMP: { name: GOODS.LAMP.name, type: 'Item', isStocked: false, prices: [{ price: GOODS.LAMP.price, currencyCode: 'EUR' }] },
          // the discount line's product; its price is replaced by the computed discount
          DISCOUNT: { name: 'BundleDiscount', type: 'Item', isStocked: false, prices: [{ price: 1, currencyCode: 'EUR' }] },
        },
        warehouses: { wh: {} },
        compensationGroupSchemas: {
          officeSetSchema: {
            name: 'OfficeSet 3 %',
            templateLines: [
              { product: 'DESK', qty: 1 },
              { product: 'CHAIR', qty: 1 },
              { product: 'LAMP', qty: 1 },
            ],
            compensationLines: [{ product: 'DISCOUNT', percentage: DISCOUNT_PERCENT }],
          },
        },
      },
    });
    const products = masterdata.products;
    const productIds = {
      desk: String(products.DESK.id),
      chair: String(products.CHAIR.id),
      lamp: String(products.LAMP.id),
      discount: String(products.DISCOUNT.id),
    };

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    let orderId;
    await test.step('1. Sales order: enter the bundle product in the order-line quick input', async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      orderId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER.bpartnerCode);

      // single-shot quick input (no retry): a retry would explode the schema a second time
      await SalesOrderPage.openQuickEntryAndSelectProduct({ product: products.BUNDLE.productCode, recordId: orderId });
      await SalesOrderPage.submitQuickEntryLine({ quantity: 1 });
    });

    await test.step('2. The schema is exploded into the three goods lines and the 3 % discount line', async () => {
      const lines = await waitForOrderLines(orderId, 4);
      const byProduct = indexByProduct(lines);
      expect(Object.keys(byProduct).sort(), 'one line per goods product plus the discount line')
        .toEqual(Object.values(productIds).sort());

      expectGoodsLine(byProduct[productIds.desk], 1, 100);
      expectGoodsLine(byProduct[productIds.chair], 1, 50);
      expectGoodsLine(byProduct[productIds.lamp], 1, 20);
      expect(byProduct[productIds.discount].fieldsByName.IsGroupCompensationLine.value, 'the discount product is on the compensation line').toBe(true);
      expectDiscountLine(lines, 170, -5.1);
      expectOneGroup(lines);

      await expectGridRow(page, products.DESK.productCode, '100,00');
      await expectGridRow(page, products.CHAIR.productCode, '50,00');
      await expectGridRow(page, products.LAMP.productCode, '20,00');
      await expectGridRow(page, products.DISCOUNT.productCode, '-5,10');
      await snapProductsAndAmounts(page, products.DISCOUNT.productCode, '01-order-lines-after-explosion');
    });

    await test.step(`3. The user changes the Chair quantity to ${CHAIR_NEW_QTY} in the order-line grid`, async () => {
      const qtyCell = gridRow(page, products.CHAIR.productCode).locator('[data-cy="cell-QtyEntered"]').first();
      await qtyCell.dblclick();
      const input = qtyCell.locator('input').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await input.fill(String(CHAIR_NEW_QTY));

      const qtySaved = page.waitForResponse(
        (response) => response.request().method() === 'PATCH'
          && response.url().includes(`/window/${SALES_ORDER_WINDOW_ID}/${orderId}/${ORDER_LINE_TAB}/`)
          && (response.request().postData() || '').includes('QtyEntered'),
        { timeout: SLOW_ACTION_TIMEOUT },
      );
      await page.keyboard.press('Tab');
      expect((await qtySaved).ok(), 'the quantity change is saved').toBe(true);
    });

    await test.step('4. The discount line is recomputed on the new goods sum', async () => {
      // what the user sees: the grid refreshes the discount line by itself
      await expectGridRow(page, products.CHAIR.productCode, '150,00');
      await expectGridRow(page, products.DISCOUNT.productCode, '-8,10');

      const lines = await getTabRows(SALES_ORDER_WINDOW_ID, orderId, ORDER_LINE_TAB);
      const byProduct = indexByProduct(lines);
      expectGoodsLine(byProduct[productIds.chair], CHAIR_NEW_QTY, 150);
      expectDiscountLine(lines, 270, -8.1);
      await snapProductsAndAmounts(page, products.DISCOUNT.productCode, '02-order-lines-after-recompute');
    });

    await test.step('5. Completing the order keeps the discount', async () => {
      await SalesOrderPage.complete();
      await expect.poll(async () => lookupKey((await getFieldData(SALES_ORDER_WINDOW_ID, orderId, 'DocStatus')).value), {
        message: 'the order is completed',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).toBe('CO');

      const lines = await getTabRows(SALES_ORDER_WINDOW_ID, orderId, ORDER_LINE_TAB);
      expect(lines, 'three goods lines and the discount line').toHaveLength(4);
      const byProduct = indexByProduct(lines);
      expectGoodsLine(byProduct[productIds.desk], 1, 100);
      expectGoodsLine(byProduct[productIds.chair], CHAIR_NEW_QTY, 150);
      expectGoodsLine(byProduct[productIds.lamp], 1, 20);
      expectDiscountLine(lines, 270, -8.1);
      expectOneGroup(lines);

      await page.reload();
      await page.getByTestId(`tab-${ORDER_LINE_TAB}`).click();
      await expectGridRow(page, products.DISCOUNT.productCode, '-8,10');
      await snapProductsAndAmounts(page, products.DISCOUNT.productCode, '03-order-lines-after-completion');
    });
  });
});

/** The order lines, once there are `count` of them (the quick input saves them in its own request). */
async function waitForOrderLines(orderId, count) {
  let lines = [];
  await expect.poll(async () => {
    lines = await getTabRows(SALES_ORDER_WINDOW_ID, orderId, ORDER_LINE_TAB);
    return lines.length;
  }, { message: `the order has ${count} lines`, timeout: VERY_SLOW_ACTION_TIMEOUT }).toBe(count);
  return lines;
}

function indexByProduct(lines) {
  const byProduct = {};
  for (const line of lines) {
    byProduct[lookupKey(line.fieldsByName.M_Product_ID.value)] = line;
  }
  return byProduct;
}

function expectGoodsLine(line, qty, lineNetAmt) {
  expect(line, 'goods line').toBeTruthy();
  const fields = line.fieldsByName;
  expect(fields.IsGroupCompensationLine.value, 'goods line is not the compensation line').toBe(false);
  expect(Number(fields.QtyEntered.value), 'goods line qty').toBeCloseTo(qty, 2);
  expect(Number(fields.LineNetAmt.value), 'goods line net amount').toBeCloseTo(lineNetAmt, 2);
}

/**
 * The discount line is a 3 % discount on `baseAmt`, and `baseAmt` is the sum of the goods lines.
 * (GroupCompensationBaseAmt itself is not a field of the order-line tab, so the base is checked
 * through the goods lines it is made of.)
 */
function expectDiscountLine(lines, baseAmt, lineNetAmt) {
  const goodsLines = lines.filter((l) => l.fieldsByName.IsGroupCompensationLine.value === false);
  const goodsSum = goodsLines.reduce((sum, l) => sum + Number(l.fieldsByName.LineNetAmt.value), 0);
  expect(goodsSum, 'discount base = sum of the goods lines').toBeCloseTo(baseAmt, 2);

  const discountLines = lines.filter((l) => l.fieldsByName.IsGroupCompensationLine.value === true);
  expect(discountLines, 'exactly one discount line').toHaveLength(1);
  const fields = discountLines[0].fieldsByName;
  expect(lookupKey(fields.GroupCompensationType.value), 'compensation type Discount').toBe('D');
  expect(lookupKey(fields.GroupCompensationAmtType.value), 'compensation amount type Percent').toBe('P');
  expect(Number(fields.GroupCompensationPercentage.value), 'discount percentage').toBeCloseTo(DISCOUNT_PERCENT, 2);
  expect(Number(fields.LineNetAmt.value), `discount = -${DISCOUNT_PERCENT} % of ${baseAmt}`).toBeCloseTo(lineNetAmt, 2);
}

/** All lines belong to one compensation group. */
function expectOneGroup(lines) {
  const groupIds = new Set(lines.map((line) => lookupKey(line.fieldsByName.C_Order_CompensationGroup_ID.value)));
  expect([...groupIds], 'all lines are in one compensation group').toHaveLength(1);
  expect([...groupIds][0], 'the lines have a compensation group').toBeTruthy();
}

function gridRow(page, productCode) {
  return page.locator('table tbody tr')
    .filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: productCode }) });
}

/** The order-line grid shows one row of the product, with the given net amount. */
async function expectGridRow(page, productCode, lineNetAmtText) {
  const row = gridRow(page, productCode);
  await expect(row, `one grid row of ${productCode}`).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
  await expect(row.locator('[data-cy="cell-LineNetAmt"]'), `net amount of ${productCode}`)
    .toContainText(lineNetAmtText, { timeout: SLOW_ACTION_TIMEOUT });
}

function lookupKey(value) {
  if (value === null || value === undefined) {
    return null;
  }
  return typeof value === 'object' ? String(value.key) : String(value);
}

/** Two screenshots of the order-line grid: scrolled to the products, then to the net amounts. */
async function snapProductsAndAmounts(page, discountProductCode, name) {
  const discountRow = gridRow(page, discountProductCode);
  await discountRow.locator('[data-cy="cell-M_Product_ID"]').scrollIntoViewIfNeeded();
  await snap(page, `${name}-products`);
  await discountRow.locator('[data-cy="cell-LineNetAmt"]').scrollIntoViewIfNeeded();
  await snap(page, `${name}-amounts`);
}

/** Screenshot of the current viewport, attached to the report and kept in the test output folder. */
async function snap(page, name) {
  const buffer = await page.screenshot({ fullPage: false });
  const file = test.info().outputPath(`${name}.png`);
  require('fs').writeFileSync(file, buffer);
  await test.info().attach(name, { body: buffer, contentType: 'image/png' });
  console.log(`[INFO] screenshot ${file}`);
}
