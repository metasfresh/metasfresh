import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  createMasterdata,
  gotoOrderList,
  createNewOrder,
  selectOrderCustomer,
  addOrderLine,
} from '../utils/OrderLineHarness';

/**
 * Geometry regression: entering edit mode on a grid cell must not change the row's or the
 * cell's rendered height/width. Reuses the thin bugfix E2E harness (login/open-order/add-line)
 * that also backs `auftragsposition-bugfix.spec.js` — the reusable helpers live in
 * `../utils/OrderLineHarness`; this file only adds the geometry-specific leg, in both languages
 * (specs must be language-independent).
 *
 * The measured column is `QtyEntered` (a plain numeric grid cell, not the Search/Lookup
 * combobox column) — the reported defect is generic to ANY grid cell entering edit mode, not
 * specific to the combobox floor covered elsewhere.
 */

const GEOMETRY_COLUMN = 'QtyEntered';
// Sub-pixel rendering rounding is the only expected source of difference; anything above this
// is the reported row-height/column-width jump, not noise.
const TOLERANCE_PX = 1;

const testCases = [
  { language: 'de_DE', label: 'German' },
  { language: 'en_US', label: 'English' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`Sales order-line grid — no layout jump on cell edit (${label})`, () => {
    test(`Entering edit mode on a grid cell does not change row/cell height or width (${label})`, async ({
      page,
    }) => {
      allure.epic('E0500: Sales Orders');
      allure.tag('F5010: Order Lines Grid');
      allure.tag('F5010');
      allure.story('Order-line grid — no layout jump on cell edit');
      allure.severity('critical');
      allure.description(`
## No layout jump on cell activation

Capture the row's and the \`${GEOMETRY_COLUMN}\` cell's \`getBoundingClientRect()\` while the grid
line renders static, activate the cell (enter edit mode), then re-capture. Row height AND column
width must be unchanged (within ${TOLERANCE_PX}px of sub-pixel rounding).
      `);

      test.setTimeout(180000);

      const masterdata = await createMasterdata(language);
      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await DashboardPage.expectVisible();

      await gotoOrderList();

      const recordId = await createNewOrder();
      await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
      await addOrderLine(recordId, { productCode: masterdata.products.Product1.productCode, quantity: 1 });

      const cell = page.locator(`[data-cy="cell-${GEOMETRY_COLUMN}"]`).first();
      await cell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const row = cell.locator('xpath=ancestor::tr[1]');

      const { rowRectBefore, cellRectBefore } = await test.step(
        'Capture static row + cell geometry',
        async () => {
          const rowRectBefore = await row.boundingBox();
          const cellRectBefore = await cell.boundingBox();
          expect(rowRectBefore, 'row must be measurable before activation').not.toBeNull();
          expect(cellRectBefore, 'cell must be measurable before activation').not.toBeNull();
          return { rowRectBefore, cellRectBefore };
        }
      );

      const { rowRectAfter, cellRectAfter } = await test.step(
        'Activate the cell (enter edit mode) and re-capture geometry',
        async () => {
          await cell.dblclick();
          await cell
            .locator('.input-body-container')
            .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

          const rowRectAfter = await row.boundingBox();
          const cellRectAfter = await cell.boundingBox();
          expect(rowRectAfter, 'row must be measurable after activation').not.toBeNull();
          expect(cellRectAfter, 'cell must be measurable after activation').not.toBeNull();

          return { rowRectAfter, cellRectAfter };
        }
      );

      // AC16: the operator relies on native browser focus to see the active cell — the
      // geometry fix must not trade the jump for a hidden focus ring. An element's OWN
      // `overflow` never clips its OWN outline (only a container can clip a descendant's), so
      // this walks from the focused editor's PARENT up to the cell: none of those wrapping
      // elements (the ones the height/padding pin touches) may clip via `overflow:hidden`/`clip`.
      await test.step('Focus ring is not clipped by the trimmed editor box', async () => {
        const clippedBy = await cell.evaluate((td) => {
          const active = document.activeElement;
          if (!active || !td.contains(active)) return 'no-active-element-in-cell';
          for (let el = active.parentElement; el && el !== td.parentElement; el = el.parentElement) {
            const overflow = getComputedStyle(el).overflow;
            if (overflow === 'hidden' || overflow === 'clip') {
              return `${el.tagName}.${el.className || ''} has overflow:${overflow}`;
            }
          }
          return null;
        });
        expect(
          clippedBy,
          'no wrapping element between the focused editor and the cell may clip via overflow:hidden/clip'
        ).toBeNull();
      });

      await page.keyboard.press('Escape');

      allure.attachment(
        'Geometry (before/after activation)',
        JSON.stringify({ rowRectBefore, rowRectAfter, cellRectBefore, cellRectAfter }, null, 2),
        'application/json'
      );
      console.log(
        `[GEOMETRY:${label}] row height ${rowRectBefore.height}->${rowRectAfter.height}, ` +
          `row width ${rowRectBefore.width}->${rowRectAfter.width}, ` +
          `cell height ${cellRectBefore.height}->${cellRectAfter.height}, ` +
          `cell width ${cellRectBefore.width}->${cellRectAfter.width}`
      );

      await test.step('Row height + width unchanged on cell activation', async () => {
        expect(
          Math.abs(rowRectAfter.height - rowRectBefore.height),
          `row height must not change on cell activation (before=${rowRectBefore.height}, after=${rowRectAfter.height})`
        ).toBeLessThanOrEqual(TOLERANCE_PX);
        expect(
          Math.abs(rowRectAfter.width - rowRectBefore.width),
          `row width must not change on cell activation (before=${rowRectBefore.width}, after=${rowRectAfter.width})`
        ).toBeLessThanOrEqual(TOLERANCE_PX);
      });

      await test.step('Cell height + width unchanged on cell activation', async () => {
        expect(
          Math.abs(cellRectAfter.height - cellRectBefore.height),
          `cell height must not change on cell activation (before=${cellRectBefore.height}, after=${cellRectAfter.height})`
        ).toBeLessThanOrEqual(TOLERANCE_PX);
        expect(
          Math.abs(cellRectAfter.width - cellRectBefore.width),
          `cell width must not change on cell activation (before=${cellRectBefore.width}, after=${cellRectAfter.width})`
        ).toBeLessThanOrEqual(TOLERANCE_PX);
      });
    });
  });
});
