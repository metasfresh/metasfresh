import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { createMasterdata, ORDER_LINE_TAB_ID } from '../utils/OrderLineHarness';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';

/**
 * Review follow-ups of the grid Date cell fix (code-review round).
 *
 * Same documented proxy as grid-date-cell-keyboard.spec.js: the order-line grid gets an injected
 * editable Date column, the cell PATCH is echoed.
 *
 * - Ctrl/Shift-click on a Date cell is multi-select: it must not enter edit / open the calendar
 * - a typed date is saved when the cell is left by a click outside (not only by Tab)
 * - clearing the typed text is pending like any typing: Escape keeps the stored value, Tab saves the clearing
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const DATE_FIELD = 'GridDateTestMhd';
const STORED_WIRE_VALUE = '2026-11-20T00:00:00.000+01:00'; // shown as 20.11.2026 in the de_DE session
const STORED_DISPLAY_VALUE = '20.11.2026';
const NEIGHBOUR_FIELD = 'Description'; // the column the Date column is cloned from; sits directly left of it

/**
 * Inject an editable grid Date column `field` into the order-line grid and keep its value in the
 * returned `state` (the route handlers read and write it, so a row refetch shows the latest value).
 */
async function serveOrderLinesWithDateColumn(page, { field, caption, initialValue }) {
  const state = { value: initialValue, patches: [] };

  await page.route(
    (url) => new RegExp(`/window/${SALES_ORDER_WINDOW_ID}(/${ORDER_LINE_TAB_ID})?/layout$`).test(url.pathname), // window + order-line tab layout, not the quick-input layout
    async (route) => {
      const response = await route.fetch();
      const layout = await response.json();
      const addDateColumn = (node) => {
        if (Array.isArray(node)) {
          const index = node.findIndex(
            (element) => element && Array.isArray(element.fields) && element.fields[0]?.field === NEIGHBOUR_FIELD
          );
          if (index >= 0 && !node.some((element) => element?.fields?.[0]?.field === field)) {
            const dateColumn = JSON.parse(JSON.stringify(node[index]));
            dateColumn.widgetType = 'Date';
            dateColumn.caption = caption;
            dateColumn.multilineText = false;
            dateColumn.fields = [{ ...dateColumn.fields[0], field, caption }];
            node.splice(index + 1, 0, dateColumn);
          }
          node.forEach(addDateColumn);
        } else if (node && typeof node === 'object') {
          Object.values(node).forEach(addDateColumn);
        }
      };
      addDateColumn(layout);
      await route.fulfill({ response, json: layout });
    }
  );

  const dateFieldData = (value) => ({
    field,
    value,
    readonly: false,
    mandatory: false,
    displayed: true,
    widgetType: 'Date',
    validStatus: { valid: true, initialValue: true, fieldName: field },
    viewEditorRenderMode: 'always',
  });

  await page.route(
    (url) => new RegExp(`/window/${SALES_ORDER_WINDOW_ID}/\\d+/${ORDER_LINE_TAB_ID}(/|$)`).test(url.pathname),
    async (route) => {
      const request = route.request();
      if (request.method() === 'PATCH') {
        const body = request.postData() || '';
        if (!body.includes(`"${field}"`)) {
          return route.continue();
        }
        const [change] = JSON.parse(body);
        state.patches.push(change);
        state.value = change.value;
        const [, , , , , rowId] = new URL(request.url()).pathname.split('/'); // /rest/api/window/<win>/<doc>/<tab>/<row>
        const documentId = new URL(request.url()).pathname.split('/')[5];
        return route.fulfill({
          json: {
            documents: [
              {
                windowId: SALES_ORDER_WINDOW_ID,
                id: documentId,
                tabId: ORDER_LINE_TAB_ID,
                tabid: ORDER_LINE_TAB_ID,
                rowId: new URL(request.url()).pathname.split('/')[7],
                fieldsByName: { [field]: { ...dateFieldData(state.value), viewEditorRenderMode: undefined } },
                saveStatus: { saved: true, presentInDatabase: true, hasChanges: false, deleted: false, error: false },
                websocketEndpoint: `/document/${SALES_ORDER_WINDOW_ID}/${documentId}`,
              },
            ],
          },
        });
      }
      if (request.method() !== 'GET') {
        return route.continue();
      }
      const response = await route.fetch();
      let body;
      try {
        body = await response.json();
      } catch (notJson) {
        return route.fulfill({ response });
      }
      const addDateValue = (node) => {
        if (Array.isArray(node)) {
          node.forEach(addDateValue);
        } else if (node && typeof node === 'object') {
          if (node.fieldsByName && typeof node.fieldsByName === 'object' && node.fieldsByName.QtyEntered) {
            node.fieldsByName[field] = dateFieldData(state.value);
          }
          Object.values(node).forEach(addDateValue);
        }
      };
      addDateValue(body);
      return route.fulfill({ response, json: body });
    }
  );

  return state;
}

/** the calendar-day a posted PATCH value denotes, as DD.MM.YYYY in the user's time zone */
function patchedDay(patchValue) {
  const date = new Date(patchValue);
  const parts = new Intl.DateTimeFormat('de-DE', {
    timeZone: 'Europe/Berlin',
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  }).formatToParts(date);
  const part = (type) => parts.find((p) => p.type === type).value;
  return `${part('day')}.${part('month')}.${part('year')}`;
}

/** create an order with one line, show the grid with the injected Date column, return its row */
async function openOrderLineGrid(page, state, field) {
  const masterdata = await createMasterdata('de_DE');
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
  await page.reload();

  const row = page
    .locator('table tbody tr')
    .filter({ has: page.locator('[data-cy="cell-QtyEntered"]') })
    .first();
  await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const dateCell = row.locator(`td[data-cy="cell-${field}"]`);
  await dateCell.scrollIntoViewIfNeeded();
  await expect(dateCell, 'the injected Date column is part of the grid').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  return { row, dateCell };
}

/** keyboard only: select the cell left of the Date cell, then Tab onto the Date cell (typing then edits it) */
async function tabIntoDateCell(page, row, dateCell) {
  const neighbour = row.locator(`td[data-cy="cell-${NEIGHBOUR_FIELD}"]`);
  await neighbour.click();
  await expect(neighbour, 'the neighbour cell has focus before Tabbing').toBeFocused();
  await page.keyboard.press('Tab');
  // the Date cell is now the selected grid cell; typing into it starts the edit (type-to-edit)
  await expect(dateCell, 'Tab moves the keyboard focus onto the Date cell').toBeFocused();
}

const dateCellText = (dateCell) => dateCell.locator('.cell-text-wrapper').first();

const calendar = (page) => page.locator('.rdtPicker');

async function openGrid(page, initialValue) {
  await page.setViewportSize({ width: 1920, height: 1080 });
  const state = await serveOrderLinesWithDateColumn(page, { field: DATE_FIELD, caption: 'MHD', initialValue });
  const { row, dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);
  return { state, row, dateCell };
}

test.describe('Grid Date cell - review follow-ups (de_DE)', () => {
  test.beforeEach(() => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid Date cell - review follow-ups');
    allure.severity('critical');
  });

  for (const modifier of ['Control', 'Shift']) {
    test(`${modifier}-click on a Date cell is multi-select: no edit, no calendar`, async ({ page }) => {
      test.setTimeout(300000);
      const { state, dateCell } = await openGrid(page, STORED_WIRE_VALUE);

      await dateCell.click({ modifiers: [modifier] });
      await page.waitForTimeout(1500); // the calendar opens after a 100 ms timer; give it time to (wrongly) appear

      await expect(dateCell.locator('input'), `${modifier}-click does not open the cell editor`).toHaveCount(0);
      await expect(calendar(page), `${modifier}-click does not open the calendar`).toBeHidden();
      expect(state.patches, 'nothing is sent').toHaveLength(0);
    });
  }

  for (const [label, leave] of [
    ['a click on another cell', (page, row) => row.locator(`td[data-cy="cell-${NEIGHBOUR_FIELD}"]`).click()],
    ['a click on an empty page area', (page) => page.mouse.click(1900, 1050)],
  ]) {
    test(`typed date is saved when the cell is left by ${label}`, async ({ page }) => {
      test.setTimeout(300000);
      const { state, row, dateCell } = await openGrid(page, STORED_WIRE_VALUE);

      await tabIntoDateCell(page, row, dateCell);
      await page.keyboard.type('03.12.2026', { delay: 50 });
      await expect(dateCell.locator('input').first(), 'the typed date is in the input').toHaveValue('03.12.2026');
      await leave(page, row);

      await expect
        .poll(() => state.patches.length, { message: 'a PATCH carries the typed date', timeout: 10000 })
        .toBe(1);
      expect(patchedDay(state.patches[0].value), 'the PATCH denotes the typed day').toBe('03.12.2026');
      await expect(dateCellText(dateCell), 'the cell shows the typed date').toHaveText('03.12.2026');
    });
  }

  test('a calendar pick after typing overrides the typed date', async ({ page }) => {
    test.setTimeout(300000);
    const { state, dateCell } = await openGrid(page, STORED_WIRE_VALUE);

    await dateCell.dblclick(); // pointer entry: the cell editor and its calendar open
    await expect(calendar(page), 'a pointer action opens the calendar').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    const input = dateCell.locator('input').first();
    await input.press('Control+A');
    await page.keyboard.type('15.12.2026', { delay: 50 }); // typed, not yet committed
    await expect(input, 'the typed date is in the input').toHaveValue('15.12.2026');

    // pick day 25: differs from the typed day (15) and from the stored day (20), whichever month the calendar shows
    await calendar(page).locator('td.rdtDay:not(.rdtOld):not(.rdtNew)', { hasText: /^25$/ }).click();

    await expect.poll(() => state.patches.length, { message: 'a PATCH is sent', timeout: 10000 }).toBeGreaterThan(0);
    await page.waitForTimeout(1500); // a stale typed date would be patched over the pick on blur
    const lastPatch = state.patches[state.patches.length - 1];
    expect(patchedDay(lastPatch.value), 'the last PATCH denotes the picked day, not the typed 15.12.2026').toMatch(
      /^25\./
    );
    expect(patchedDay(state.value), 'the stored value is the picked day').toMatch(/^25\./);
  });

  test('clearing the text then Escape keeps the stored value and sends nothing', async ({ page }) => {
    test.setTimeout(300000);
    const { state, row, dateCell } = await openGrid(page, STORED_WIRE_VALUE);

    await tabIntoDateCell(page, row, dateCell);
    await page.keyboard.type('03.12.2026', { delay: 50 }); // starts the edit with a typed date
    await page.keyboard.press('Control+A');
    await page.keyboard.press('Backspace');
    await expect(dateCell.locator('input').first(), 'the text is cleared').toHaveValue('');
    await page.keyboard.press('Escape');

    await expect(dateCellText(dateCell), 'the stored value is kept after Escape').toHaveText(STORED_DISPLAY_VALUE);
    await page.waitForTimeout(1500);
    expect(state.patches, 'clearing + Escape sends no change').toHaveLength(0);
  });

  test('clearing the text then Tab saves the clearing', async ({ page }) => {
    test.setTimeout(300000);
    const { state, row, dateCell } = await openGrid(page, STORED_WIRE_VALUE);

    await tabIntoDateCell(page, row, dateCell);
    await page.keyboard.type('03.12.2026', { delay: 50 });
    await page.keyboard.press('Control+A');
    await page.keyboard.press('Backspace');
    await page.keyboard.press('Tab');

    await expect
      .poll(() => state.patches.length, { message: 'a PATCH carries the cleared value', timeout: 10000 })
      .toBe(1);
    expect(state.patches[0].value, 'the PATCH clears the date').toBeNull();
  });
});
