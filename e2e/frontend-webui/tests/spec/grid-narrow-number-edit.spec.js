import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { createMasterdata } from '../utils/OrderLineHarness';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';

/**
 * Narrow number grid cell: in edit mode the whole value is visible, not only its tail, and the
 * value is unchanged after leaving the cell. A bordered editor (e.g. the LongText `Description`)
 * keeps its inner padding.
 */
const NUMBER_COLUMN = 'PriceEntered';
const BORDERED_TEXT_COLUMN = 'Description';
// The product price seeded by `createMasterdata` is 12.5 -> shown as "12,50" (de_DE).
const EXPECTED_PRICE_DIGITS = /12[.,]50?/;

test.describe('Sales order-line grid — narrow number cell shows its whole value in edit mode (de_DE)', () => {
  test('Activating a narrow number cell shows the whole value, not its tail; the value is unchanged after leaving; a bordered text editor keeps its padding', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — narrow number cell edit display');
    allure.severity('normal');
    allure.description(`
## Whole value visible in a narrow number cell while editing

Activate the \`${NUMBER_COLUMN}\` cell (a narrow number column). The editor's input must hold the
whole value with no horizontal overflow (\`scrollWidth <= clientWidth\`), so nothing is scrolled
out of view. Leaving with Tab must keep the cell's displayed value unchanged. A bordered text
editor (\`${BORDERED_TEXT_COLUMN}\`) must keep its inner padding (text not flush against the border).
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

    const cell = page.locator(`[data-cy="cell-${NUMBER_COLUMN}"]`).first();
    await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    const staticText = ((await cell.textContent()) || '').trim();
    const staticBox = await cell.boundingBox();
    console.log(`[INFO] ${NUMBER_COLUMN} static text "${staticText}", cell width ${staticBox.width}px`);
    expect(staticText, `the ${NUMBER_COLUMN} cell must show the seeded price`).toMatch(EXPECTED_PRICE_DIGITS);

    await test.step('Activate the cell — the whole value fits the editor input (no overflow)', async () => {
      await cell.dblclick();
      const input = cell.locator('.input-body-container input').first();
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const metrics = await input.evaluate((el) => ({
        value: el.value,
        scrollWidth: el.scrollWidth,
        clientWidth: el.clientWidth,
        scrollLeft: el.scrollLeft,
      }));
      console.log(`[INFO] ${NUMBER_COLUMN} editor metrics: ${JSON.stringify(metrics)}`);
      allure.attachment('Editor input metrics', JSON.stringify(metrics, null, 2), 'application/json');

      expect(metrics.value, 'the editor must hold the cell value').toMatch(EXPECTED_PRICE_DIGITS);
      expect(
        metrics.scrollWidth,
        `the value must fit the editor input without scrolling (scrollWidth=${metrics.scrollWidth}, clientWidth=${metrics.clientWidth})`
      ).toBeLessThanOrEqual(metrics.clientWidth);
      expect(metrics.scrollLeft, 'the start of the value must not be scrolled out of view').toBe(0);
    });

    await test.step('Leave with Tab — the displayed value is unchanged', async () => {
      await page.keyboard.press('Tab');
      await cell.locator('.input-body-container').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });

      await expect(cell, 'the displayed value must be unchanged after leaving the editor').toHaveText(staticText);
    });
    await test.step('A bordered text editor keeps its inner padding (text not flush against the border)', async () => {
      const textCell = page.locator(`[data-cy="cell-${BORDERED_TEXT_COLUMN}"]`).first();
      await textCell.dblclick();
      const wrapper = textCell.locator('.input-primary').first();
      await wrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const style = await wrapper.evaluate((el) => {
        const cs = getComputedStyle(el);
        return {
          paddingLeft: parseFloat(cs.paddingLeft),
          paddingRight: parseFloat(cs.paddingRight),
          borderLeft: parseFloat(cs.borderLeftWidth),
        };
      });
      console.log(`[INFO] ${BORDERED_TEXT_COLUMN} editor wrapper: ${JSON.stringify(style)}`);

      expect(style.borderLeft, 'the text editor wrapper is the bordered kind').toBeGreaterThan(0);
      expect(style.paddingLeft, 'the bordered text editor must keep a left inner padding').toBeGreaterThanOrEqual(4);
      expect(style.paddingRight, 'the bordered text editor must keep a right inner padding').toBeGreaterThanOrEqual(4);

      await page.keyboard.press('Escape');
    });
  });
});
