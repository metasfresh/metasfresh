import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  createMasterdata,
  gotoOrderList,
  createNewOrder,
  selectOrderCustomer,
  addOrderLine,
} from '../utils/OrderLineHarness';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';

/**
 * Column-invariant regression guard (BF-B5, TC9 + TC10) — proving the layout-jump CSS fix and
 * the combobox width floor did NOT break the two pre-existing column-sizing invariants:
 *
 * - AC19 (TC9): manual drag-resize still works and PERSISTS across a reload, above the floor
 *   the only new lower bound is the combobox ~210px clamp; a non-combobox column still clamps
 *   at the flat 50px floor.
 * - AC20 (TC10): the WidgetSize S/M/L(/XL/XXL) `td-*` size-class bands still render within their
 *   configured pixel ranges for non-combobox columns.
 *
 * The extreme-clamp mechanics themselves (combobox ~210px / non-combobox 50px, plus the
 * open-editor-no-overlap check) are already exercised in depth by `combobox-min-width.spec.js`
 * — this spec's clamp assertions are a light, non-duplicated re-affirmation alongside the
 * genuinely new coverage here: persistence across a reload, and the size-class bands.
 *
 * Targets the order-line grid (core window/tab, via `OrderLineHarness`) — see
 * `combobox-min-width.spec.js` for why an embedded/inline tab grid is used instead of a
 * top-level list view (double-click there opens the record instead of the cell editor).
 *
 * Features tested:
 * - F50000: Resizable Table Columns
 */
const COMBOBOX_FIELD = 'M_Product_ID';
const NON_COMBOBOX_FIELD = 'QtyEntered';
const SECOND_BAND_FIELD = 'Description'; // a LongText-family widget on this grid -> a wider td-* band than QtyEntered
const COMBOBOX_MIN_WIDTH_PX = 210;
const FLAT_MIN_WIDTH_PX = 50;

// `td-*` band pixel ranges as defined in `table.scss` (`:249-284`) — asserted here as fixed
// literals (not imported from source) so this test independently proves the CSS wasn't touched.
const SIZE_BANDS_PX = {
  'td-sm': [60, 144],
  'td-md': [144, 225],
  'td-lg': [225, 350],
  'td-xl': [350, 500],
  'td-xxl': [500, 800],
};

async function seedOrderLineGrid(page) {
  const masterdata = await createMasterdata();

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  await gotoOrderList();
  const recordId = await createNewOrder();
  await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
  await addOrderLine(recordId, { productCode: masterdata.products.Product1.productCode, quantity: 1 });

  await page
    .locator(`[data-cy="cell-${COMBOBOX_FIELD}"]`)
    .first()
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  return { masterdata, recordId };
}

async function dragColumn(page, fieldName, deltaPx) {
  const handle = page.getByTestId(`resize-handle-${fieldName}`);
  await handle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // Off-screen (scrolled-out) columns don't hit-test at their unscrolled boundingBox — see
  // combobox-min-width.spec.js's `dragResizeAsNarrowAsPossible` for the same fix + root cause.
  await handle.scrollIntoViewIfNeeded();

  const handleBox = await handle.boundingBox();
  const startX = handleBox.x + handleBox.width / 2;
  const startY = handleBox.y + handleBox.height / 2;

  await page.mouse.move(startX, startY);
  await page.mouse.down();
  await page.mouse.move(startX + deltaPx, startY, { steps: 15 });
  await page.mouse.up();
  await page.waitForTimeout(300);
}

test.describe('Column-invariant regression guard', () => {
  test('Manual drag-resize persists across a reload; combobox and non-combobox clamps hold (AC19)', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Column drag-resize persists + clamps preserved');
    allure.severity('critical');
    allure.description(`
## TC9 — Manual drag-resize preserved (measured)

1. Drag a non-combobox column by a known delta; the new width persists across a page reload
   (not merely visible immediately after the drag).
2. Above the floor, both a combobox and a non-combobox column still resize freely; at the
   extreme the non-combobox column clamps at the flat 50px floor and the combobox column at
   ~210px — the only new lower bound introduced by this milestone.
    `);

    test.setTimeout(120000);

    await seedOrderLineGrid(page);
    const orderUrl = page.url();

    const baselineBox = await page.getByTestId(`column-${NON_COMBOBOX_FIELD}`).boundingBox();
    const DRAG_DELTA_PX = 40;

    await test.step('Drag-resize by a known delta changes the width by ~the drag delta', async () => {
      await dragColumn(page, NON_COMBOBOX_FIELD, DRAG_DELTA_PX);

      const afterDragBox = await page.getByTestId(`column-${NON_COMBOBOX_FIELD}`).boundingBox();
      console.log(
        `[INFO] ${NON_COMBOBOX_FIELD} width baseline=${baselineBox.width}px, after +${DRAG_DELTA_PX}px drag=${afterDragBox.width}px`
      );

      expect(
        Math.abs(afterDragBox.width - (baselineBox.width + DRAG_DELTA_PX)),
        'the column width must change by ~the drag delta — resize must not be disabled/blocked'
      ).toBeLessThanOrEqual(3);
    });

    await test.step('The resized width persists across a reload (not just immediately after the drag)', async () => {
      const beforeReloadBox = await page.getByTestId(`column-${NON_COMBOBOX_FIELD}`).boundingBox();

      await page.goto(orderUrl);
      await page
        .locator(`[data-cy="cell-${COMBOBOX_FIELD}"]`)
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const afterReloadBox = await page.getByTestId(`column-${NON_COMBOBOX_FIELD}`).boundingBox();
      console.log(
        `[INFO] ${NON_COMBOBOX_FIELD} width before reload=${beforeReloadBox.width}px, after reload=${afterReloadBox.width}px`
      );

      expect(
        Math.abs(afterReloadBox.width - beforeReloadBox.width),
        'a manually resized column width must persist across a reload'
      ).toBeLessThanOrEqual(2);
    });

    await test.step('At the extreme, the non-combobox column still clamps at the flat 50px floor', async () => {
      await dragColumn(page, NON_COMBOBOX_FIELD, -600);

      const clampedBox = await page.getByTestId(`column-${NON_COMBOBOX_FIELD}`).boundingBox();
      console.log(`[INFO] ${NON_COMBOBOX_FIELD} width after max-narrow drag: ${clampedBox.width}px`);

      expect(
        clampedBox.width,
        `non-combobox column must clamp at the flat ${FLAT_MIN_WIDTH_PX}px floor`
      ).toBeLessThanOrEqual(FLAT_MIN_WIDTH_PX + 5);
    });

    await test.step('At the extreme, the combobox column still clamps at ~210px (the only new lower bound)', async () => {
      await dragColumn(page, COMBOBOX_FIELD, -600);

      const clampedBox = await page.getByTestId(`column-${COMBOBOX_FIELD}`).boundingBox();
      console.log(`[INFO] ${COMBOBOX_FIELD} width after max-narrow drag: ${clampedBox.width}px`);

      expect(
        clampedBox.width,
        `combobox column must clamp at ~${COMBOBOX_MIN_WIDTH_PX}px, not the flat ${FLAT_MIN_WIDTH_PX}px floor`
      ).toBeGreaterThanOrEqual(COMBOBOX_MIN_WIDTH_PX - 5);
    });
  });

  test('WidgetSize td-* bands render within their configured pixel ranges for non-combobox columns (AC20)', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('WidgetSize S/M/L bands preserved');
    allure.severity('normal');
    allure.description(`
## TC10 — WidgetSize td-* bands preserved (measured)

At default (no custom width), a non-combobox column's rendered width must fall within its
computed size-class's band (\`table.scss\`): td-sm 60-144px, td-md 144-225px, td-lg 225-350px,
td-xl 350-500px, td-xxl 500-800px. This test discovers each field's ACTUAL rendered \`td-*\`
class (rather than assuming one) and asserts its width against that class's own range — proving
the band system itself still applies its configured ranges, whichever band a given field falls
into (\`${NON_COMBOBOX_FIELD}\` and \`${SECOND_BAND_FIELD}\` land in different bands on this grid).
    `);

    test.setTimeout(120000);

    await seedOrderLineGrid(page);

    for (const field of [NON_COMBOBOX_FIELD, SECOND_BAND_FIELD]) {
      await test.step(`${field} renders within its computed td-* band`, async () => {
        const th = page.getByTestId(`column-${field}`);
        const className = (await th.getAttribute('class')) || '';
        const sizeClass = Object.keys(SIZE_BANDS_PX).find((cls) => className.split(/\s+/).includes(cls));
        expect(sizeClass, `${field}'s header must carry one of the td-* size classes (got "${className}")`).toBeTruthy();

        const box = await th.boundingBox();
        const [min, max] = SIZE_BANDS_PX[sizeClass];
        console.log(`[INFO] ${field}: class=${sizeClass}, width=${box.width}px, expected band=[${min},${max}]`);

        expect(box.width, `${field} (${sizeClass}) must render within [${min},${max}]px`).toBeGreaterThanOrEqual(min);
        expect(box.width, `${field} (${sizeClass}) must render within [${min},${max}]px`).toBeLessThanOrEqual(max);
      });
    }
  });
});
