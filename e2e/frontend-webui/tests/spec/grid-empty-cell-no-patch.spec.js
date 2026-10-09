import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT, flushPendingUiTasks } from '../utils/common';
import { createMasterdata } from '../utils/OrderLineHarness';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';

/**
 * Leaving an empty text cell without typing sends nothing (de_DE).
 *
 * Real-life case: a user copies a sales order (Alt+W). The copied lines have no description. The user
 * changes a line's quantity, then opens the empty "Beschreibung" cell of that line - by double-click or
 * by Enter - and leaves it again without typing: Escape, Tab or a click somewhere else. Nothing changed,
 * so no change may be sent to the server. Clearing a description that does have text must still be sent.
 *
 * Why the quantity change: an empty value is held as "" after the grid loads, but as null after a line
 * is read again from the server (the server notifies the change and the grid re-reads that line). A
 * fresh Alt+W copy can be in either state depending on timing; changing the quantity puts the line in
 * the re-read state every time, which is the state that used to send "" on leaving the cell.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const DESCRIPTION_COLUMN = 'Description';
const QTY_COLUMN = 'QtyEntered';
const LINE_COUNT = 5;

function trackDescriptionPatches(page) {
  const patches = [];
  page.on('request', (request) => {
    if (request.method() !== 'PATCH') {
      return;
    }
    let operations = [];
    try {
      operations = JSON.parse(request.postData() || '[]');
    } catch (e) {
      return;
    }
    operations
      .filter((operation) => operation.path === DESCRIPTION_COLUMN)
      .forEach((operation) => patches.push({ url: request.url(), value: operation.value }));
  });
  return patches;
}

function isDescriptionPatch(request) {
  return request.method() === 'PATCH' && (request.postData() || '').includes(`"${DESCRIPTION_COLUMN}"`);
}

/**
 * Change the quantity of a line and wait until the grid has read the line again from the server.
 */
async function changeQuantityAndWaitForReread(page, row, quantity) {
  const qtyCell = page.locator(`[data-cy="cell-${QTY_COLUMN}"]`).nth(row);
  await qtyCell.scrollIntoViewIfNeeded();
  await qtyCell.dblclick();
  const editor = qtyCell.locator('input').first();
  await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await editor.fill(String(quantity));
  const reread = page.waitForResponse(
    (response) => response.request().method() === 'GET' && /\/AD_Tab-187\?ids=/.test(response.url()),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await page.keyboard.press('Tab');
  await reread;
  // Tab opened the next cell's editor - close it.
  await page.keyboard.press('Escape');
  await expect(qtyCell).toContainText(String(quantity), { timeout: SLOW_ACTION_TIMEOUT });
  await flushPendingUiTasks(page);
}

test.describe('Sales order-line grid — an empty text cell left without typing sends nothing (de_DE)', () => {
  test('On a copied order, opening and leaving an empty description sends no change; clearing a filled one does', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Order-line grid — leaving an unchanged empty cell sends nothing');
    allure.severity('normal');
    test.setTimeout(300000);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const originalRecordId = await test.step(`Create a sales order with ${LINE_COUNT} lines`, async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
      for (let line = 1; line <= LINE_COUNT; line++) {
        await SalesOrderPage.addOrderLine({
          product: masterdata.products.Product1.productCode,
          quantity: line,
          recordId,
        });
      }
      await expect(page.locator(`[data-cy="cell-${DESCRIPTION_COLUMN}"]`)).toHaveCount(LINE_COUNT, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      return recordId;
    });

    await test.step('Copy the order with Alt+W', async () => {
      await page.locator('body').click({ position: { x: 5, y: 5 } });
      await page.keyboard.press('Alt+W');
      await page.waitForURL((url) => /\/window\/\d+\/\d+/.test(url.pathname) && !url.pathname.endsWith(`/${originalRecordId}`), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await expect(page.locator(`[data-cy="cell-${DESCRIPTION_COLUMN}"]`)).toHaveCount(LINE_COUNT, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });

    const descriptionPatches = trackDescriptionPatches(page);
    const descriptionCell = (row) => page.locator(`[data-cy="cell-${DESCRIPTION_COLUMN}"]`).nth(row);
    const editorOf = (cell) => cell.locator('input, textarea').first();

    // Enter is only left with Escape: Enter on a closed multi-line text cell also types a line break
    // into it, so Tab or a click-away after Enter saves that line break - a separate topic.
    const cases = [
      { row: 0, open: 'Enter', leave: 'Escape' },
      { row: 1, open: 'double-click', leave: 'Escape' },
      { row: 2, open: 'double-click', leave: 'Tab' },
      { row: 3, open: 'double-click', leave: 'click-away' },
    ];

    for (const { row, open, leave } of cases) {
      await test.step(`Line ${row + 1}: open the empty description by ${open}, leave it with ${leave}`, async () => {
        const cell = descriptionCell(row);
        await cell.scrollIntoViewIfNeeded();
        await expect(cell, 'the copied line has no description').toHaveText('');
        await changeQuantityAndWaitForReread(page, row, 10 + row);
        const patchesBefore = descriptionPatches.length;

        if (open === 'Enter') {
          await cell.click();
          await page.keyboard.press('Enter');
        } else {
          await cell.dblclick();
        }
        const editor = editorOf(cell);
        await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        if (leave === 'click-away') {
          await page.getByTestId('tab-AD_Tab-187').click();
        } else {
          await page.keyboard.press(leave);
        }
        await editor.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await flushPendingUiTasks(page);

        expect
          .soft(
            descriptionPatches.slice(patchesBefore),
            `${open} + ${leave} on an empty description must not send a change`
          )
          .toEqual([]);

        // Tab moves on and may open the next cell's editor - close it before the next case.
        if (leave === 'Tab') {
          await page.keyboard.press('Escape');
          await flushPendingUiTasks(page);
        }
      });
    }

    await test.step('Control: clearing a description that has text IS sent', async () => {
      const cell = descriptionCell(LINE_COUNT - 1);
      await cell.scrollIntoViewIfNeeded();

      await cell.dblclick();
      const editor = editorOf(cell);
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await editor.fill('abc');
      let saved = page.waitForResponse((response) => isDescriptionPatch(response.request()), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await page.keyboard.press('Tab');
      await saved;
      await page.keyboard.press('Escape');
      await expect(cell).toContainText('abc', { timeout: SLOW_ACTION_TIMEOUT });

      await cell.dblclick();
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(editor).toHaveValue('abc');
      await editor.fill('');
      const patchesBefore = descriptionPatches.length;
      saved = page.waitForResponse((response) => isDescriptionPatch(response.request()), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await page.keyboard.press('Tab');
      await saved;
      await page.keyboard.press('Escape');

      expect(
        descriptionPatches.slice(patchesBefore).map((patch) => patch.value),
        'clearing a filled description sends the empty value'
      ).toEqual(['']);
    });

    await test.step('After a reload every description is empty', async () => {
      await page.reload();
      const cells = page.locator(`[data-cy="cell-${DESCRIPTION_COLUMN}"]`);
      await expect(cells).toHaveCount(LINE_COUNT, { timeout: SLOW_ACTION_TIMEOUT });
      for (let row = 0; row < LINE_COUNT; row++) {
        await expect(cells.nth(row)).toHaveText('');
      }
    });
  });
});
