import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Escape after the attribute popup ("Merkmale") of an order line saved new attributes keeps them.
 *
 * Real-life case: on an order line the user opens the attribute popup from the grid cell, types an
 * attribute value, clicks outside the popup and, a moment later, presses Escape. By then the grid
 * has re-read the line with the new attributes. Escape must not put back the attributes from before
 * the edit: the cell shows the new value without a reload, and after a reload too.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const ATTRIBUTE_COLUMN = 'M_AttributeSetInstance_ID';

test.describe('Sales order-line grid — Escape after the attribute popup saved', () => {
  test('Escape a moment after the popup saved a new attribute value: the cell keeps showing it', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — attribute popup editing');
    allure.severity('normal');
    test.setTimeout(180000);

    const runId = Date.now();
    const attributeText = `E2EEsc${runId}`;
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE', firstname: 'E2E', lastname: 'Tester' } },
        bpartners: {
          CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
        },
        productCategories: {
          CAT1: { attributeSetName: 'ESC_SET' },
        },
        attributes: {
          TEXT_ATTR: {
            value: `E2EESCT${runId}`,
            name: `E2E Escape Text ${runId}`,
            attributeValueType: 'STRING',
            isInstanceAttribute: true,
            attributeSetNames: ['ESC_SET'],
          },
        },
        products: {
          Product1: {
            name: 'ESCPROD',
            type: 'Item',
            productCategory: 'CAT1',
            prices: [{ price: 12.5, currencyCode: 'EUR' }],
          },
        },
      },
    });
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({
      product: masterdata.products.Product1.productCode,
      quantity: 1,
      recordId,
    });

    const cell = page.locator(`[data-cy="cell-${ATTRIBUTE_COLUMN}"]`).first();
    await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const cellEditor = cell.locator('.form-group.widgetType-ProductAttributes');
    const popup = page.locator('.attributes-dropdown');
    expect((await cell.textContent()).trim(), 'the attributes before the edit').not.toContain(attributeText);

    await test.step('Type an attribute value into the popup of the grid cell', async () => {
      await cell.dblclick();
      await cell.locator('.attributes-in-table button').click();
      await popup.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const textInput = popup.locator('.form-group input').first();
      await textInput.click();
      await textInput.fill(attributeText);
      await page.keyboard.press('Tab');
    });

    await test.step('Click outside the popup: the attributes are saved and the line is re-read', async () => {
      const attributesSaved = page.waitForResponse(
        (response) =>
          response.request().method() === 'PATCH' &&
          response.url().includes(`/${recordId}/`) &&
          (response.request().postData() || '').includes(ATTRIBUTE_COLUMN),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      // the grid re-reads the changed line after the save; a user pressing Escape a moment later
      // presses it after this read
      const lineReRead = page.waitForResponse(
        (response) =>
          response.request().method() === 'GET' &&
          response.url().includes(`/${recordId}/AD_Tab-`) &&
          response.url().includes('ids='),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      // the document header's label: a neutral spot outside the popup and the grid
      await page.locator('.panel-primary .form-control-label').first().click();
      await popup.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      await attributesSaved;
      await lineReRead;

      await expect(cellEditor, 'the cell editor stays open after the popup closed').toHaveCount(1);
      await expect(cell.locator('.attributes-in-table button')).toContainText(attributeText);
    });

    await test.step('Escape leaves the cell, which shows the new attribute value without a reload', async () => {
      await page.keyboard.press('Escape');
      await expect(cellEditor).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(cell, 'Escape keeps the saved attributes, not the ones from before the edit').toContainText(
        attributeText,
        { timeout: SLOW_ACTION_TIMEOUT }
      );
    });

    await test.step('Opened again from the server, the cell still shows the new attribute value', async () => {
      // a fresh tab of the same session: the draft order arms the "leave page?" prompt, which
      // blocks a reload of the current tab
      const reopened = await page.context().newPage();
      await reopened.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
      const reloadedCell = reopened.locator(`[data-cy="cell-${ATTRIBUTE_COLUMN}"]`).first();
      await expect(reloadedCell).toContainText(attributeText, { timeout: SLOW_ACTION_TIMEOUT });
    });
  });
});
