import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { BusinessPartnerPage } from '../utils/pages/BusinessPartnerPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import {
  createMasterdata,
  gotoOrderList,
  createNewOrder,
  selectOrderCustomer,
  addOrderLine,
  ORDER_LINE_TAB_ID,
} from '../utils/OrderLineHarness';

/**
 * Geometry invariant for EVERY grid editor type: entering edit mode on a cell and leaving it
 * again changes neither the cell's component box (the editor occupies exactly the box the static
 * value occupied: same width and height), nor the row height, nor the width of ANY column.
 *
 * Walks every cell of the first row of two real grids — the sales order-line grid and the
 * Business Partner > Address grid — opens each editable cell (double-click), measures, leaves it
 * (Escape), measures again. Component box, row height and every column width must stay within 1px (sub-pixel
 * rounding) of the static layout, both while editing and after leaving.
 *
 * Measured at 1920px wide, where the grid is not width-constrained and every column takes its
 * natural content width — the layout in which an editor wider (or narrower) than the static value
 * moves the column. Also measured at 1280px (columns near their band minimums) and at 900px,
 * below the md breakpoint (991px): there the 42px desktop row height does not apply and a row is
 * only as tall as its 26px content plus padding, so the static and editor boxes are measured
 * without the row floor that would otherwise absorb a height difference. A short GLN value ("04012345") keeps the GLN Text column narrower than a
 * default-sized text input, the case that widened it.
 *
 * While editing, every visible editor control (input, textarea, switch, button) must also lie inside its cell, so
 * the editor really uses the column's width and the row's height instead of spilling over a neighbour.
 *
 * The list of widget types actually exercised is asserted too, so a fixture change can not make
 * this spec silently test fewer editor types.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const TOLERANCE_PX = 1;
/**
 * A vertically centred chip is offset from the box centre by 0 (or by 0.5px when the height
 * difference is odd); a real layout cause (a margin, a padding) moves it by at least 1px.
 */
const CENTRING_TOLERANCE_PX = 0.5;
const VIEWPORT = { width: 1920, height: 1080 };
/** below the md breakpoint (frontend variables.scss `$breakpoint-md: 991px`) */
const NARROW_VIEWPORT = { width: 900, height: 900 };
/** the desktop row height (table.scss, `.desktop .table td` inside the md media query) */
const DESKTOP_ROW_HEIGHT_PX = 42;

const ORDER_LINE_EXPECTED_WIDGET_TYPES = [
  'Composed', // Product (Search/Lookup + note), Price
  'ProductAttributes',
  'Quantity',
  'List',
  'Number',
  'YesNo',
  'Amount',
  'CostPrice',
  'LongText',
  'Lookup',
];
const ADDRESS_EXPECTED_WIDGET_TYPES = ['Text', 'Switch', 'YesNo'];

async function measureRow(row) {
  return await row.evaluate((tr) => ({
    rowHeight: tr.getBoundingClientRect().height,
    columns: Array.from(tr.querySelectorAll('td')).map((td) => ({
      cell: td.getAttribute('data-cy') || '(no data-cy)',
      width: td.getBoundingClientRect().width,
    })),
  }));
}

/**
 * The cell's component box: the visible static presentation (`.cell-text-wrapper`) in display mode,
 * the editor (its `.form-group` root) in edit mode. The invisible width keeper is not part of it.
 */
async function measureComponentBox(cell) {
  return await cell.evaluate((td) => {
    const editor = td.querySelector('.form-group');
    const component = editor || td.querySelector(':scope > div:not(.cell-width-keeper) .cell-text-wrapper');
    if (!component) {
      return null;
    }
    const box = component.getBoundingClientRect();
    return { kind: editor ? 'editor' : 'static', width: box.width, height: box.height };
  });
}

function compareComponentBox(phase, activatedCell, before, now) {
  if (!before || !now) {
    return [`${activatedCell} ${phase}: component box not measurable (${JSON.stringify({ before, now })})`];
  }
  const violations = [];
  ['width', 'height'].forEach((dimension) => {
    if (Math.abs(now[dimension] - before[dimension]) > TOLERANCE_PX) {
      violations.push(
        `${activatedCell} ${phase}: component ${dimension} ${before.kind} ${before[dimension]} -> ${now.kind} ${now[dimension]}`
      );
    }
  });
  return violations;
}

function compareGeometry(phase, activatedCell, before, now) {
  const violations = [];
  if (Math.abs(now.rowHeight - before.rowHeight) > TOLERANCE_PX) {
    violations.push(`${activatedCell} ${phase}: row height ${before.rowHeight} -> ${now.rowHeight}`);
  }
  before.columns.forEach((column, i) => {
    const nowWidth = now.columns[i] ? now.columns[i].width : NaN;
    if (!(Math.abs(nowWidth - column.width) <= TOLERANCE_PX)) {
      violations.push(`${activatedCell} ${phase}: column ${column.cell} width ${column.width} -> ${nowWidth}`);
    }
  });
  return violations;
}

/** returns the editor's widget type, or null when the cell did not open an editor (read-only) */
async function openEditor(page, cell) {
  await cell.dblclick();
  const editor = cell.locator('.form-group');
  const opened = await editor
    .first()
    .waitFor({ state: 'visible', timeout: 2000 })
    .then(() => true)
    .catch(() => false);
  if (!opened) {
    return null;
  }
  const className = await cell.evaluate((td) => td.querySelector('.form-group')?.className || '');
  // let the editor settle (typeahead list, focus) before measuring
  await page.waitForTimeout(400);
  const match = className.match(/widgetType-([A-Za-z]+)/);
  return match ? match[1] : 'unknown';
}

/** leaves the cell's editor; Escape by default (in a modal, Escape would close the whole modal) */
async function leaveWithEscape(page) {
  await page.keyboard.press('Escape');
}

async function assertEveryEditorKeepsGeometry(page, row, expectedWidgetTypes, label, { leave = leaveWithEscape } = {}) {
  const cellIds = await row.evaluate((tr) =>
    Array.from(tr.querySelectorAll('td[data-cy]')).map((td) => td.getAttribute('data-cy'))
  );

  const violations = [];
  const exercised = {};

  for (const cellId of cellIds) {
    const cell = row.locator(`[data-cy="${cellId}"]`);
    await cell.scrollIntoViewIfNeeded();
    const before = await measureRow(row);
    const componentBefore = await measureComponentBox(cell);

    const widgetType = await openEditor(page, cell);
    if (!widgetType) {
      continue; // read-only cell: no editor, nothing to measure
    }
    const inEdit = await measureRow(row);
    const componentInEdit = await measureComponentBox(cell);
    const controlsOutsideCell = await cell.evaluate((td, tolerance) => {
      const cellBox = td.getBoundingClientRect();
      return Array.from(
        td.querySelectorAll('.form-group input, .form-group textarea, .form-group .input-slider, .form-group button')
      )
        .map((control) => ({ control, box: control.getBoundingClientRect() }))
        .filter(({ box }) => box.width > 0 && box.height > 0)
        .filter(
          ({ box }) =>
            box.left < cellBox.left - tolerance ||
            box.right > cellBox.right + tolerance ||
            box.top < cellBox.top - tolerance ||
            box.bottom > cellBox.bottom + tolerance
        )
        .map(
          ({ control, box }) =>
            `${control.tagName.toLowerCase()}.${control.className} spans x ${box.left}..${box.right} y ${box.top}..${box.bottom}, cell x ${cellBox.left}..${cellBox.right} y ${cellBox.top}..${cellBox.bottom}`
        );
    }, TOLERANCE_PX);

    await leave(page);
    await cell
      .locator('.form-group')
      .first()
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(400);
    const afterLeave = await measureRow(row);
    const componentAfterLeave = await measureComponentBox(cell);

    exercised[cellId] = widgetType;
    const cellViolations = [
      ...controlsOutsideCell.map((detail) => `${cellId} while editing: editor control outside the cell: ${detail}`),
      ...compareComponentBox('while editing', cellId, componentBefore, componentInEdit),
      ...compareComponentBox('after leaving', cellId, componentBefore, componentAfterLeave),
      ...compareGeometry('while editing', cellId, before, inEdit),
      ...compareGeometry('after leaving', cellId, before, afterLeave),
    ];
    console.log(
      `[GEOMETRY:${label}] ${cellId} (${widgetType}): ${cellViolations.length ? cellViolations.join('; ') : 'unchanged'}`
    );
    violations.push(...cellViolations);
  }

  allure.attachment(`${label} — exercised editors`, JSON.stringify(exercised, null, 2), 'application/json');
  allure.attachment(`${label} — violations`, JSON.stringify(violations, null, 2), 'application/json');

  const exercisedTypes = [...new Set(Object.values(exercised))];
  expect(
    expectedWidgetTypes.filter((type) => !exercisedTypes.includes(type)),
    `every expected editor type must have been opened on the ${label} grid (exercised: ${exercisedTypes.join(', ')})`
  ).toEqual([]);

  expect(
    violations,
    `${label}: entering/leaving edit mode must not change the component box, the row height or any column width`
  ).toEqual([]);
}

/**
 * Serve the sales order layout with the order line's Description column flagged multi-line
 * (`multilineText`, 3 lines) — exactly what AD_UI_Element.IsMultiLine='Y' on that grid element
 * produces. A selected row of a grid with such a column is extended: every cell's static value
 * gets 3 x 20px of height. No included tab of the seed DB carries IsMultiLine, so the AD setting is
 * applied to the layout response instead of to the database.
 */
async function serveOrderLineDescriptionAsMultiline(page) {
  await page.route(
    (url) => url.pathname.includes(`/window/${SALES_ORDER_WINDOW_ID}`) && url.pathname.endsWith('/layout'),
    async (route) => {
      const response = await route.fetch();
      const layout = await response.json();
      const flag = (node) => {
        if (Array.isArray(node)) {
          node.forEach(flag);
        } else if (node && typeof node === 'object') {
          if (Array.isArray(node.fields) && node.fields[0] && node.fields[0].field === 'Description') {
            node.multilineText = true;
            node.multilineTextLines = 3;
          }
          Object.values(node).forEach(flag);
        }
      };
      flag(layout);
      await route.fulfill({ response, json: layout });
    }
  );
}

const LABELS_FIELD = 'GeometryTestLabels';

/**
 * Serve the order-line grid with one extra Labels column (`GeometryTestLabels`, one label
 * "Kundenbindung"), cloned from the Description column. Labels is a real grid widget type
 * (an AD_UI_Element of type Labels) but no included tab of the seed DB shows one with data, so the
 * column is added to the layout and row responses instead.
 */
async function serveOrderLinesWithLabelsColumn(page) {
  await page.route(
    (url) => url.pathname.includes(`/window/${SALES_ORDER_WINDOW_ID}`) && url.pathname.endsWith('/layout'),
    async (route) => {
      const response = await route.fetch();
      const layout = await response.json();
      const addLabelsColumn = (node) => {
        if (Array.isArray(node)) {
          const index = node.findIndex(
            (element) => element && Array.isArray(element.fields) && element.fields[0]?.field === 'Description'
          );
          if (index >= 0 && !node.some((element) => element?.fields?.[0]?.field === LABELS_FIELD)) {
            const labelsColumn = JSON.parse(JSON.stringify(node[index]));
            labelsColumn.widgetType = 'Labels';
            labelsColumn.caption = 'Labels';
            labelsColumn.fields = [{ ...labelsColumn.fields[0], field: LABELS_FIELD, caption: 'Labels' }];
            node.splice(index + 1, 0, labelsColumn);
          }
          node.forEach(addLabelsColumn);
        } else if (node && typeof node === 'object') {
          Object.values(node).forEach(addLabelsColumn);
        }
      };
      addLabelsColumn(layout);
      await route.fulfill({ response, json: layout });
    }
  );
  await page.route(
    (url) => new RegExp(`/window/${SALES_ORDER_WINDOW_ID}/\\d+/${ORDER_LINE_TAB_ID}(/|$)`).test(url.pathname),
    async (route) => {
      if (route.request().method() !== 'GET') {
        return route.continue();
      }
      const response = await route.fetch();
      let body;
      try {
        body = await response.json();
      } catch (notJson) {
        return route.fulfill({ response });
      }
      const addLabelsValue = (node) => {
        if (Array.isArray(node)) {
          node.forEach(addLabelsValue);
        } else if (node && typeof node === 'object') {
          if (node.fieldsByName && typeof node.fieldsByName === 'object' && node.fieldsByName.QtyEntered) {
            node.fieldsByName[LABELS_FIELD] = {
              field: LABELS_FIELD,
              value: { values: [{ key: 'L1', caption: 'Kundenbindung' }] },
              readonly: true,
              displayed: true,
            };
          }
          Object.values(node).forEach(addLabelsValue);
        }
      };
      addLabelsValue(body);
      return route.fulfill({ response, json: body });
    }
  );
}

/** open the order's text-lines modal (a grid with inline-editable text rows) and add one text row */
async function openTextLinesModalWithOneTextRow(page, orderId) {
  const topActionsResponse = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}/${ORDER_LINE_TAB_ID}/topActions`
  );
  expect(topActionsResponse.ok()).toBeTruthy();
  const { actions } = await topActionsResponse.json();
  const launcherIndex = actions.findIndex((action) => action.internalName === 'WEBUI_Order_DocTextLines_Launcher');
  expect(launcherIndex, 'the text-lines launcher must be a top action of the order line tab').toBeGreaterThanOrEqual(0);
  await page.locator('.filter-panel-buttons button[title]').nth(launcherIndex).click();
  await page.locator('.panel-modal').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await Promise.all([
    page.waitForResponse(
      (response) =>
        response.url().includes('/documentView/docTextLines/') &&
        !response.url().includes('/layout') &&
        response.request().method() === 'GET',
      { timeout: SLOW_ACTION_TIMEOUT }
    ),
    page.getByTestId('quick-action-button').click(),
  ]);
  const textRow = page.locator('.panel-modal [data-testid^="table-row-T"]').first();
  await textRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return textRow;
}

/** in a modal, Escape closes the whole modal: leave the cell by clicking the modal's title instead */
async function leaveByClickingModalTitle(page) {
  await page.locator('.panel-modal-header-title').click();
}

test.describe('Grid editors keep the row height and every column width (de_DE)', () => {
  // 1920: the grid has room and every column takes its natural content width.
  // 1280: the grid overflows horizontally and columns sit near their band minimums.
  // 900: below the md breakpoint - no 42px desktop row height, the row is as tall as its content.
  for (const viewport of [VIEWPORT, { width: 1280, height: 720 }, NARROW_VIEWPORT]) {
    test(`Order-line grid (${viewport.width}px): opening and leaving each editor changes neither the row height nor any column width`, async ({
      page,
    }) => {
      allure.epic('E0500: Sales Orders');
      allure.tag('F5010: Order Lines Grid');
      allure.tag('F5010');
      allure.story('Grid — no layout change when entering/leaving an editor');
      allure.severity('critical');
      test.setTimeout(300000);
      await page.setViewportSize(viewport);

      const masterdata = await createMasterdata('de_DE');
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await gotoOrderList();
      const recordId = await createNewOrder();
      await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
      await addOrderLine(recordId, {
        productCode: masterdata.products.Product1.productCode,
        quantity: 1,
      });

      const row = page
        .locator('table tbody tr')
        .filter({ has: page.locator('[data-cy="cell-QtyEntered"]') })
        .first();
      await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      if (viewport === NARROW_VIEWPORT) {
        // guard: this run must really measure the narrow layout, not the desktop one
        const rowHeight = await row.evaluate((tr) => tr.getBoundingClientRect().height);
        console.log(`[GEOMETRY:order-line ${viewport.width}px] static row height ${rowHeight}`);
        expect(rowHeight, 'below the md breakpoint the 42px desktop row height does not apply').toBeLessThan(
          DESKTOP_ROW_HEIGHT_PX
        );
      }

      await assertEveryEditorKeepsGeometry(
        page,
        row,
        ORDER_LINE_EXPECTED_WIDGET_TYPES,
        `order-line ${viewport.width}px`
      );
    });
  }

  test('Business Partner Address grid: opening and leaving each editor changes neither the row height nor any column width', async ({
    page,
  }) => {
    allure.epic('E0100: Business Partners');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid — no layout change when entering/leaving an editor');
    allure.severity('critical');
    test.setTimeout(300000);
    await page.setViewportSize(VIEWPORT);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await BusinessPartnerPage.gotoRecord(masterdata.bpartners.CUSTOMER1.id);
    await BusinessPartnerPage.clickTab(BusinessPartnerPage.TAB_IDS.LOCATION);

    const glnCell = page.locator('table tbody tr td[data-cy="cell-GLN"]').first();
    await glnCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    await test.step('Give the address a short GLN (narrower than a default-size text input)', async () => {
      await glnCell.dblclick();
      await glnCell.locator('input').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.keyboard.type('04012345');
      const patch = page.waitForResponse(
        (response) => response.request().method() === 'PATCH' && response.url().includes('/window/'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page.keyboard.press('Tab');
      await patch;
      await page.keyboard.press('Escape');
      await BusinessPartnerPage.gotoRecord(masterdata.bpartners.CUSTOMER1.id);
      await BusinessPartnerPage.clickTab(BusinessPartnerPage.TAB_IDS.LOCATION);
      await expect(page.locator('table tbody tr td[data-cy="cell-GLN"]').first()).toContainText('04012345', {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });

    const row = page
      .locator('table tbody tr')
      .filter({ has: page.locator('[data-cy="cell-GLN"]') })
      .first();

    await assertEveryEditorKeepsGeometry(page, row, ADDRESS_EXPECTED_WIDGET_TYPES, 'address');
  });

  test('Order-line grid, extended multi-line row: each editor occupies the extended static box; row height and column widths unchanged', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid — no layout change when entering/leaving an editor');
    allure.severity('critical');
    test.setTimeout(300000);
    await page.setViewportSize(VIEWPORT);
    await serveOrderLineDescriptionAsMultiline(page);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await gotoOrderList();
    const recordId = await createNewOrder();
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await addOrderLine(recordId, {
      productCode: masterdata.products.Product1.productCode,
      quantity: 1,
    });

    const row = page
      .locator('table tbody tr')
      .filter({ has: page.locator('[data-cy="cell-QtyEntered"]') })
      .first();
    await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    await test.step('Selecting the row extends it (multi-line column)', async () => {
      await row.locator('[data-cy="cell-Line"]').click();
      await expect(row.locator('.cell-text-wrapper.extended').first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      const rowHeight = await row.evaluate((tr) => tr.getBoundingClientRect().height);
      expect(rowHeight, 'an extended row is taller than a normal 42px row').toBeGreaterThan(60);
      // the extended static text keeps its normal line height: a 3-line (60px) cell shows 3 lines of
      // text, not 2 lines spaced like a single-line cell (26px)
      const lineHeight = await row
        .locator('[data-cy="cell-Description"] .cell-text-wrapper.extended')
        .evaluate((wrapper) => getComputedStyle(wrapper).lineHeight);
      expect(lineHeight, 'line height of the extended multi-line text').not.toBe('26px');
    });

    await assertEveryEditorKeepsGeometry(page, row, ['Quantity', 'LongText', 'List'], 'order-line extended row');
  });

  test('Grid inside a modal (order text lines): opening and leaving each editor changes neither the component box, the row height nor any column width', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid — no layout change when entering/leaving an editor');
    allure.severity('critical');
    test.setTimeout(300000);
    await page.setViewportSize(VIEWPORT);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await gotoOrderList();
    const recordId = await createNewOrder();
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);

    const textRow = await openTextLinesModalWithOneTextRow(page, recordId);

    await assertEveryEditorKeepsGeometry(page, textRow, ['LongText', 'List'], 'text-lines modal', {
      leave: leaveByClickingModalTitle,
    });
  });

  test('Order-line grid with a Labels column: the label chip keeps its size, centred; editing a neighbour changes no geometry', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Grid — no layout change when entering/leaving an editor');
    allure.severity('critical');
    test.setTimeout(300000);
    await page.setViewportSize(VIEWPORT);
    await serveOrderLinesWithLabelsColumn(page);

    const masterdata = await createMasterdata('de_DE');
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await gotoOrderList();
    const recordId = await createNewOrder();
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await addOrderLine(recordId, {
      productCode: masterdata.products.Product1.productCode,
      quantity: 1,
    });
    await page.reload();

    const row = page
      .locator('table tbody tr')
      .filter({ has: page.locator('[data-cy="cell-QtyEntered"]') })
      .first();
    await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const labelsCell = row.locator(`[data-cy="cell-${LABELS_FIELD}"]`);
    await labelsCell.scrollIntoViewIfNeeded();
    const chip = labelsCell.locator('.labels-label');
    await expect(chip).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    const measureChip = async () =>
      labelsCell.evaluate((td) => {
        const wrapper = td.querySelector('.cell-text-wrapper');
        const chipBox = td.querySelector('.labels-label').getBoundingClientRect();
        const wrapperBox = wrapper.getBoundingClientRect();
        return {
          chipHeight: chipBox.height,
          chipCentre: chipBox.top + chipBox.height / 2,
          wrapperHeight: wrapperBox.height,
          wrapperCentre: wrapperBox.top + wrapperBox.height / 2,
          wrapperOverflows: wrapper.scrollHeight > wrapper.clientHeight + 1,
        };
      });

    await test.step('The chip keeps its own height, centred in the 26px static box, nothing clipped', async () => {
      const box = await measureChip();
      console.log(`[GEOMETRY:labels] ${JSON.stringify(box)}`);
      expect(box.chipHeight, 'the label chip keeps its own height (not stretched to the 26px box)').toBeLessThan(26);
      expect(Math.abs(box.chipCentre - box.wrapperCentre), 'the chip is vertically centred').toBeLessThanOrEqual(
        CENTRING_TOLERANCE_PX
      );
      expect(box.wrapperOverflows, 'nothing of the label cell is clipped').toBe(false);
    });

    await test.step('Opening and leaving a neighbour editor changes no geometry in the labels row', async () => {
      const before = await measureRow(row);
      const qtyCell = row.locator('[data-cy="cell-QtyEntered"]');
      await qtyCell.scrollIntoViewIfNeeded();
      await openEditor(page, qtyCell);
      const inEdit = await measureRow(row);
      await page.keyboard.press('Escape');
      await page.waitForTimeout(400);
      const afterLeave = await measureRow(row);
      expect([
        ...compareGeometry('while editing', 'cell-QtyEntered', before, inEdit),
        ...compareGeometry('after leaving', 'cell-QtyEntered', before, afterLeave),
      ]).toEqual([]);
    });
  });
});
