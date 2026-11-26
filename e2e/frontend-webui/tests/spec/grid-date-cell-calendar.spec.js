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
 * Calendar of a grid Date cell (MHD column on the order-line grid).
 *
 * Same injected-column fixture as grid-date-cell-keyboard.spec.js (a documented proxy for the real
 * the MHD column: it exercises the shared grid Date cell editor, TableCell -> DatePicker).
 * The browser clock is fixed to 2026-10-21, before anything is loaded, so "today" is October while
 * the stored value 20.11.2026 is November: a calendar that opens on today's month is distinguishable
 * from one that opens on the value's month.
 *
 * Scenarios (REQUIREMENTS.md section 5):
 * - TC3: the calendar opened on a cell holding 20.11.2026 shows November 2026, not today's month.
 *   The value is injected as the display string "20.11.2026" (not ISO): it does not parse to a valid
 *   moment, so react-datetime has no selectedDate and opens on today's month (the customer's symptom).
 *   A clean ISO value opens on the value's month on the unfixed code, so it could not be RED.
 * - TC5b (keyboard spec) is a regression guard that passes on the unfixed code by design.
 * - TC4a: entering the cell by keyboard (Tab) does NOT open the calendar, and typing works
 * - TC4b: a single pointer click on the static date cell DOES open the calendar
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

/** the calendar-day a posted PATCH value denotes, as DD.MM.YYYY in the customer's time zone */
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

const FIXED_NOW = new Date('2026-10-21T10:00:00');
const calendar = (page) => page.locator('.rdtPicker');

test.describe('Grid Date cell - calendar (de_DE, browser clock fixed to 2026-10-21)', () => {
  test.beforeEach(() => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid Date cell - calendar');
    allure.severity('critical');
  });

  test('TC3: the calendar opened on a cell holding 20.11.2026 shows the value month (November 2026)', async ({
    page,
  }) => {
    test.setTimeout(300000);
    await page.clock.setFixedTime(FIXED_NOW); // before navigating: moment reads Date.now
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: STORED_DISPLAY_VALUE, // display string, see header
    });
    const { dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);

    // pointer: a double click enters edit mode today; either way a pointer action opens the calendar
    await dateCell.dblclick();
    await expect(calendar(page), 'a pointer action opens the calendar').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(
      calendar(page).locator('.rdtSwitch'),
      'the calendar shows the month of the stored value (November 2026), not the month of today (October 2026)'
    ).toContainText(/(November|Nov)/i);
  });

  test('TC4a: entering the cell by keyboard does not open the calendar, and typing works', async ({ page }) => {
    test.setTimeout(300000);
    await page.clock.setFixedTime(FIXED_NOW);
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: STORED_WIRE_VALUE,
    });
    const { row, dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);

    await tabIntoDateCell(page, row, dateCell);
    await page.keyboard.type('03.12.2026', { delay: 50 });
    await page.waitForTimeout(500); // longer than the calendar's 100 ms auto-open timer
    await expect(calendar(page), 'no calendar pops up while the user types the date').toBeHidden();
    await expect(dateCell.locator('input').first(), 'the date was typed into the open text input').toHaveValue(
      '03.12.2026'
    );
  });

  test('TC6: the calendar icon of the open grid Date editor does not overlap the date text', async ({ page }) => {
    test.setTimeout(300000);
    await page.clock.setFixedTime(FIXED_NOW);
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: STORED_WIRE_VALUE,
    });
    const { dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);

    await dateCell.dblclick();
    const input = dateCell.locator('.datepicker input.js-input-field').first();
    const icon = dateCell.locator('.datepicker .meta-icon-calendar').first();
    await expect(input, 'the grid Date editor is open').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(icon, 'the calendar icon is shown next to the input').toBeVisible();

    const inputBox = await input.boundingBox();
    const iconBox = await icon.boundingBox();
    const paddingRight = await input.evaluate((el) => parseFloat(getComputedStyle(el).paddingRight));
    const textRightEdge = inputBox.x + inputBox.width - paddingRight; // the date text never extends past this
    expect(
      iconBox.x,
      `the calendar icon (left edge ${iconBox.x}) starts at or after the right edge of the input's text area (${textRightEdge})`
    ).toBeGreaterThanOrEqual(textRightEdge - 1);
  });

  test('TC4b: a single pointer click on the static date cell opens the calendar', async ({ page }) => {
    test.setTimeout(300000);
    await page.clock.setFixedTime(FIXED_NOW);
    await page.setViewportSize({ width: 1920, height: 1080 });
    const state = await serveOrderLinesWithDateColumn(page, {
      field: DATE_FIELD,
      caption: 'MHD',
      initialValue: STORED_WIRE_VALUE,
    });
    const { dateCell } = await openOrderLineGrid(page, state, DATE_FIELD);
    await expect(calendar(page), 'the calendar is closed while the cell is static').toBeHidden();

    await dateCell.click();
    await expect(calendar(page), 'one pointer click opens the calendar').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  });
});
