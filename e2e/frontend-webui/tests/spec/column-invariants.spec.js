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
 * Grid column sizing on the sales order-line grid:
 * - a drag-resized column keeps its width after a reload; at the narrowest, a combobox column
 *   stops at 90px and any other column at 50px;
 * - without a custom width, a column renders within the pixel range of its `td-*` size class.
 *
 * Features tested:
 * - F50000: Resizable Table Columns
 */
const COMBOBOX_FIELD = 'M_Product_ID';
const NON_COMBOBOX_FIELD = 'QtyEntered';
const SECOND_BAND_FIELD = 'Description'; // a LongText-family widget on this grid -> a wider td-* band than QtyEntered
const COMBOBOX_MIN_WIDTH_PX = 90;
const FLAT_MIN_WIDTH_PX = 50;

// `td-*` size-class pixel ranges (table.scss), as literals so a change to them fails this test
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
  // a scrolled-out handle is not hit at its bounding box; scroll it into view first
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

test.describe('Grid column sizing', () => {
  test('A drag-resized width persists across a reload; at the narrowest a combobox column stops at 90px, any other column at 50px', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Column drag-resize persists and stops at the minimum width');
    allure.severity('critical');
    allure.description(`
1. Drag a non-combobox column by a known delta: its width changes by that delta and persists across a reload.
2. Drag it as narrow as possible: it stops at 50px.
3. Drag a combobox column as narrow as possible: it stops at 90px.
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
        'the column width must change by the drag delta'
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

    await test.step('At the narrowest, the non-combobox column stops at 50px', async () => {
      await dragColumn(page, NON_COMBOBOX_FIELD, -600);

      const clampedBox = await page.getByTestId(`column-${NON_COMBOBOX_FIELD}`).boundingBox();
      console.log(`[INFO] ${NON_COMBOBOX_FIELD} width after max-narrow drag: ${clampedBox.width}px`);

      expect(
        clampedBox.width,
        `non-combobox column must clamp at the flat ${FLAT_MIN_WIDTH_PX}px floor`
      ).toBeLessThanOrEqual(FLAT_MIN_WIDTH_PX + 5);
    });

    await test.step('At the narrowest, the combobox column stops at 90px', async () => {
      await dragColumn(page, COMBOBOX_FIELD, -600);

      const clampedBox = await page.getByTestId(`column-${COMBOBOX_FIELD}`).boundingBox();
      console.log(`[INFO] ${COMBOBOX_FIELD} width after max-narrow drag: ${clampedBox.width}px`);

      expect(
        clampedBox.width,
        `combobox column must clamp at ${COMBOBOX_MIN_WIDTH_PX}px, not the flat ${FLAT_MIN_WIDTH_PX}px floor`
      ).toBeGreaterThanOrEqual(COMBOBOX_MIN_WIDTH_PX - 5);
    });
  });

  test('Without a custom width, a column renders within the pixel range of its td-* size class', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Column width within its size-class range');
    allure.severity('normal');
    allure.description(`
Without a custom width, \`${NON_COMBOBOX_FIELD}\` and \`${SECOND_BAND_FIELD}\` (two different size classes)
each render within the range of the \`td-*\` class they carry: td-sm 60-144px, td-md 144-225px,
td-lg 225-350px, td-xl 350-500px, td-xxl 500-800px.
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
