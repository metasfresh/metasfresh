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
 * Keyboard entry into a grid Date cell (MHD column on the order-line grid).
 *
 * The order-line grid of the seed DB has no editable Date column, so the column under test is
 * injected: the window `/layout` response gets a cloned column with `widgetType: 'Date'`, the row
 * responses get a value for it, and the cell PATCH is answered by echoing the posted value. This is
 * a documented proxy: it exercises the shared grid Date cell editor (TableCell -> DatePicker), which
 * is the code the fix changes. True DB persistence of the real MHD column is verified at live UAT.
 *
 * Scenarios:
 * - Tab into an EMPTY Date cell, type DD.MM.YYYY -> accepted, PATCHed, shown after blur
 * - Tab into a cell holding 20.11.2026, type a new date -> REPLACES (not appended), PATCHed
 * - Escape (after typing) and, separately, Enter (without typing) keep the stored value
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

test.describe('Grid Date cell - keyboard entry (de_DE)', () => {
  test.beforeEach(() => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid Date cell - keyboard entry');
    allure.severity('critical');
  });

  test('Tab into an empty Date cell, type a date -> accepted, PATCHed, shown after blur', async ({ page }) => {
    test.setTimeout(300000);
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: null,
    });
    const { row, dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);

    await tabIntoDateCell(page, row, dateCell);
    await page.keyboard.type('05.11.2026', { delay: 50 });
    await expect(dateCell.locator('input').first(), 'the typed date is in the input').toHaveValue('05.11.2026', { timeout: 5000 });
    await page.keyboard.press('Tab'); // leave the cell: the value is saved on blur

    await expect.poll(() => state.patches.length, { message: 'a PATCH carries the typed date', timeout: 10000 }).toBe(1);
    expect(patchedDay(state.patches[0].value), 'the PATCH denotes the typed day').toBe('05.11.2026');
    await expect(dateCellText(dateCell), 'the cell shows the date after blur').toHaveText('05.11.2026');
  });

  test('Tab into a cell holding 20.11.2026, type a new date -> replaces (not appends), PATCHed', async ({
    page,
  }) => {
    test.setTimeout(300000);
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: STORED_WIRE_VALUE,
    });
    const { row, dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);
    await expect(dateCellText(dateCell), 'the stored value is shown').toHaveText(STORED_DISPLAY_VALUE);

    await tabIntoDateCell(page, row, dateCell);
    await page.keyboard.type('03.12.2026', { delay: 50 });
    await expect(
      dateCell.locator('input').first(),
      'the typed date REPLACES the stored one (not appended to 20.11.2026)'
    ).toHaveValue('03.12.2026');
    await page.keyboard.press('Tab');

    await expect.poll(() => state.patches.length, { message: 'a PATCH carries the new date', timeout: 10000 }).toBe(1);
    expect(patchedDay(state.patches[0].value), 'the PATCH denotes the typed day').toBe('03.12.2026');
    await expect(dateCellText(dateCell), 'the cell shows the new date after blur').toHaveText('03.12.2026');
  });

  test('Escape after typing keeps the stored value', async ({ page }) => {
    test.setTimeout(300000);
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: STORED_WIRE_VALUE,
    });
    const { row, dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);

    await tabIntoDateCell(page, row, dateCell);
    await page.keyboard.type('03.12.2026', { delay: 50 });
    await expect(dateCell.locator('input').first(), 'something was typed').toHaveValue('03.12.2026');
    await page.keyboard.press('Escape');

    await expect(dateCellText(dateCell), 'the stored value is kept after Escape').toHaveText(STORED_DISPLAY_VALUE);
    await page.waitForTimeout(1500); // give an (unwanted) in-flight PATCH time to arrive before asserting none was sent
    expect(state.patches, 'Escape sends no change').toHaveLength(0);
  });

  test('Enter without typing keeps the stored value', async ({ page }) => {
    test.setTimeout(300000);
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: STORED_WIRE_VALUE,
    });
    const { row, dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);

    await tabIntoDateCell(page, row, dateCell);
    await page.keyboard.press('Enter');

    await expect(dateCellText(dateCell), 'the stored value is kept after Enter').toHaveText(STORED_DISPLAY_VALUE);
    await page.waitForTimeout(1500); // give an (unwanted) in-flight PATCH time to arrive before asserting none was sent
    expect(
      state.patches.filter((patch) => patchedDay(patch.value) !== STORED_DISPLAY_VALUE),
      'Enter changes nothing'
    ).toHaveLength(0);
  });
});
