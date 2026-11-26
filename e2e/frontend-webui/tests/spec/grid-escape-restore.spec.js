import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT, flushPendingUiTasks } from '../utils/common';
import { createMasterdata } from '../utils/OrderLineHarness';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';

/**
 * Escape after double-clicking a grid cell keeps the cell's value (de_DE).
 *
 * Real-life case: a user double-clicks the price of an order line ("12,50"), changes their mind
 * and presses Escape. The price must stay 12,50 — no change is sent to the server and no
 * "Erforderliche Felder ausfüllen: Preis" message appears. The value put back is the stored number,
 * not the German display text "12,50", which a number editor rejects.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const PRICE_COLUMN = 'PriceEntered';
const QTY_COLUMN = 'QtyEntered';

function trackLinePatches(page, recordId) {
  const patches = [];
  page.on('request', (request) => {
    if (request.method() === 'PATCH' && request.url().includes(`/${recordId}/`)) {
      patches.push({ url: request.url(), body: request.postData() || '' });
    }
  });
  return patches;
}

test.describe('Sales order-line grid — Escape after double-click keeps the value (de_DE)', () => {
  test('Double-click the price, press Escape: the price is kept and nothing is sent', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — Escape restores the stored value');
    allure.severity('critical');
    test.setTimeout(180000);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({
      product: masterdata.products.Product1.productCode,
      quantity: 3,
      recordId,
    });

    const priceCell = page.locator(`[data-cy="cell-${PRICE_COLUMN}"]`).first();
    const qtyCell = page.locator(`[data-cy="cell-${QTY_COLUMN}"]`).first();
    await expect(priceCell).toContainText('12,50', {
      timeout: SLOW_ACTION_TIMEOUT,
    });

    const linePatches = trackLinePatches(page, recordId);

    for (const [cell, column, expectedText] of [
      [priceCell, PRICE_COLUMN, '12,50'],
      [qtyCell, QTY_COLUMN, '3'],
    ]) {
      await test.step(`${column}: double-click, then Escape`, async () => {
        await cell.dblclick();
        await cell.locator('input').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await page.keyboard.press('Escape');
        await cell.locator('input').first().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await flushPendingUiTasks(page);

        await expect(cell, `${column} must still show its value after Escape`).toContainText(expectedText);
      });
    }

    await test.step('Nothing was sent for the price or the quantity', async () => {
      const offending = linePatches.filter(
        (patch) => patch.body.includes(PRICE_COLUMN) || patch.body.includes(`"${QTY_COLUMN}"`)
      );
      expect(offending, 'Escape must not send a change for the cell it closed').toEqual([]);
    });

    await test.step('After a reload the price and quantity are unchanged', async () => {
      await page.reload();
      await expect(page.locator(`[data-cy="cell-${PRICE_COLUMN}"]`).first()).toContainText('12,50', {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await expect(page.locator(`[data-cy="cell-${QTY_COLUMN}"]`).first()).toContainText('3');
    });
  });
});

test.describe('Sales order-line grid — Enter opens a cell with its stored value (de_DE)', () => {
  test('Enter on a selected quantity cell opens it with the quantity; Escape and Tab keep it', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — Enter opens the editor with the stored value');
    allure.severity('critical');
    test.setTimeout(180000);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.addOrderLine({
      product: masterdata.products.Product1.productCode,
      quantity: 3,
      recordId,
    });

    const qtyCell = page.locator(`[data-cy="cell-${QTY_COLUMN}"]`).first();
    await expect(qtyCell).toContainText('3', { timeout: SLOW_ACTION_TIMEOUT });
    const linePatches = trackLinePatches(page, recordId);
    const editor = qtyCell.locator('input').first();

    for (const leaveKey of ['Escape', 'Tab']) {
      await test.step(`Select the cell, Enter, then ${leaveKey}`, async () => {
        await qtyCell.click();
        await page.keyboard.press('Enter');
        await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(editor, 'the editor opens with the stored quantity').toHaveValue('3');

        await page.keyboard.press(leaveKey);
        await editor.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await expect(qtyCell, `the quantity is still shown after ${leaveKey}`).toContainText('3');
      });
    }

    await test.step('No empty quantity was sent', async () => {
      const clearing = linePatches.filter((patch) => {
        const operations = JSON.parse(patch.body || '[]');
        return operations.some(
          (operation) => operation.path === QTY_COLUMN && (operation.value === null || operation.value === '')
        );
      });
      expect(clearing, 'Enter then Escape/Tab must not clear the quantity').toEqual([]);
    });

    await test.step('After a reload the quantity is unchanged', async () => {
      await page.reload();
      await expect(page.locator(`[data-cy="cell-${QTY_COLUMN}"]`).first()).toContainText('3', {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  });
});
