import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { BusinessPartnerPage } from '../utils/pages/BusinessPartnerPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  createMasterdata,
  gotoOrderList,
  createNewOrder,
  selectOrderCustomer,
  addOrderLine,
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
 * moves the column. A short GLN value ("04012345") keeps the GLN Text column narrower than a
 * default-sized text input, the case that widened it.
 *
 * While editing, every visible editor control (input, textarea, switch, button) must also lie inside its cell, so
 * the editor really uses the column's width instead of spilling over its neighbour.
 *
 * The list of widget types actually exercised is asserted too, so a fixture change can not make
 * this spec silently test fewer editor types.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const TOLERANCE_PX = 1;
const VIEWPORT = { width: 1920, height: 1080 };

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
 * the editor (`.form-group-table`) in edit mode. The invisible width keeper is not part of it.
 */
async function measureComponentBox(cell) {
  return await cell.evaluate((td) => {
    const editor = td.querySelector('.form-group-table');
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
  const editor = cell.locator('.form-group-table');
  const opened = await editor
    .first()
    .waitFor({ state: 'visible', timeout: 2000 })
    .then(() => true)
    .catch(() => false);
  if (!opened) {
    return null;
  }
  const className = await cell.evaluate((td) => td.querySelector('.form-group-table')?.className || '');
  // let the editor settle (typeahead list, focus) before measuring
  await page.waitForTimeout(400);
  const match = className.match(/widgetType-([A-Za-z]+)/);
  return match ? match[1] : 'unknown';
}

async function assertEveryEditorKeepsGeometry(page, row, expectedWidgetTypes, label) {
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
        td.querySelectorAll(
          '.form-group-table input, .form-group-table textarea, .form-group-table .input-slider, .form-group-table button'
        )
      )
        .map((control) => ({ control, box: control.getBoundingClientRect() }))
        .filter(({ box }) => box.width > 0 && box.height > 0)
        .filter(({ box }) => box.left < cellBox.left - tolerance || box.right > cellBox.right + tolerance)
        .map(
          ({ control, box }) =>
            `${control.tagName.toLowerCase()}.${control.className} spans ${box.left}..${box.right}, cell ${cellBox.left}..${cellBox.right}`
        );
    }, TOLERANCE_PX);

    await page.keyboard.press('Escape');
    await cell
      .locator('.form-group-table')
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

test.describe('Grid editors keep the row height and every column width (de_DE)', () => {
  // 1920: the grid has room and every column takes its natural content width.
  // 1280: the grid overflows horizontally and columns sit near their band minimums.
  for (const viewport of [VIEWPORT, { width: 1280, height: 720 }]) {
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
});
