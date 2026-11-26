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
 * Why the quantity change: an empty value is held as "" after the grid loads the tab, but as null after
 * a line is read again from the server (the server notifies a change and the grid re-reads that line
 * with GET .../AD_Tab-187?ids=...). Only the null state used to send "" on leaving the cell. On a fresh
 * Alt+W copy, which state the lines end up in depends on whether that re-read arrives after the tab
 * load: some copies were null, others "" (in which case leaving the cell sent nothing even before the
 * fix). A case without the quantity change would therefore not fail reliably without the fix, so every
 * case first changes the quantity, which re-reads the line every time - also a real user path.
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
 * Scroll a grid cell into view. The grid re-renders its rows whenever a line is read again from the
 * server, so the cell found by the locator can be replaced while it is being scrolled ("Element is not
 * attached to the DOM"). Retry until the scroll lands on the current element.
 */
async function scrollCellIntoView(cell) {
  await expect(async () => {
    await cell.scrollIntoViewIfNeeded({ timeout: 2000 });
  }).toPass({ timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Change the quantity of a line and wait until the grid has read the line again from the server.
 */
async function changeQuantityAndWaitForReread(page, row, quantity) {
  const qtyCell = page.locator(`[data-cy="cell-${QTY_COLUMN}"]`).nth(row);
  await scrollCellIntoView(qtyCell);
  await qtyCell.dblclick();
  const editor = qtyCell.locator('input').first();
  await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await editor.fill(String(quantity));
  const reread = page
    .waitForResponse(
      (response) => response.request().method() === 'GET' && /\/AD_Tab-187\?ids=/.test(response.url()),
      { timeout: SLOW_ACTION_TIMEOUT }
    )
    .catch((error) => {
      throw new Error(`the grid did not re-read line ${row + 1} after its quantity change: ${error.message}`);
    });
  await page.keyboard.press('Tab');
  await reread;
  // Tab opened the next cell's editor - close it.
  await page.keyboard.press('Escape');
  await expect(qtyCell).toContainText(String(quantity), { timeout: SLOW_ACTION_TIMEOUT });
  await flushPendingUiTasks(page);
}

test.describe('Sales order-line grid — an empty text cell left without typing sends nothing (de_DE)', () => {
  test('Copied order: leaving an empty description sends nothing; clearing a filled one does', async ({ page }) => {
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
      const isCopy = (url) =>
        /\/window\/\d+\/\d+/.test(url.pathname) && !url.pathname.endsWith(`/${originalRecordId}`);
      await page.waitForURL(isCopy, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator(`[data-cy="cell-${DESCRIPTION_COLUMN}"]`)).toHaveCount(LINE_COUNT, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      // The copied lines may still be read again from the server - let the grid finish rendering.
      await flushPendingUiTasks(page);
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
        await scrollCellIntoView(cell);
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
      await scrollCellIntoView(cell);

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
