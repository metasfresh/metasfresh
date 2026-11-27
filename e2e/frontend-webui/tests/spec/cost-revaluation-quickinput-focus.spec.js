import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { expectFocusHeld, expectFocusOn } from '../utils/QuickInputFocus';

/**
 * Cost Revaluation (Kosten Neubewertung, window 541568) lines tab: it adds lines by quick entry only, so the quick
 * entry opens by itself. Such a quick entry gives its first field (Product) the cursor, so the user can type the
 * product right away:
 *  - for the next line, after a line was added with Enter;
 *  - when a document that already has lines is opened.
 * It does not take the cursor from a header field the user works in: see cost-revaluation-callouts.spec.js.
 */

const PRODUCT_INPUT = '.quick-input-container #lookup_M_Product_ID input.input-field';

/** How long the cursor must stay in the first field once it got there. */
const FOCUS_HOLD_MILLIS = 1500;

async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostRevalFocus', lastname: 'E2E' } },
      warehouses: { WH: {} },
      products: {
        P1: { name: 'CRF_STOCKED1', type: 'Item' },
        P2: { name: 'CRF_STOCKED2', type: 'Item' },
      },
      handlingUnits: {
        HU1: { product: 'P1', warehouse: 'WH', qty: 10 },
        HU2: { product: 'P2', warehouse: 'WH', qty: 10 },
      },
    },
  });
}

/** The line grid's rows. */
const GRID_ROWS = '.table-flex-wrapper [data-testid^="table-row-"]';

/**
 * Delays the answer of the next quick input request (the one that follows an added line) until the grid shows the
 * given number of lines. This is the order on a slow network: the grid shows the new line before the next quick input
 * arrives, and the grid may take the cursor when its first line arrives (Table.componentDidUpdate).
 */
async function holdNextQuickInputUntilLinesShown(page, lineCount) {
  const isNewQuickInputRequest = (url) => /\/quickInput\/?$/.test(new URL(url).pathname);
  let isHeld = false;
  await page.route(isNewQuickInputRequest, async (route) => {
    if (isHeld || route.request().method() !== 'POST') {
      return route.fallback();
    }
    isHeld = true;
    const response = await route.fetch();
    await page.waitForFunction((args) => document.querySelectorAll(args.selector).length >= args.lineCount, {
      selector: GRID_ROWS,
      lineCount,
    });
    await route.fulfill({ response });
  });
}

function allureTags(story) {
  allure.epic('E0226: Costing');
  allure.tag('F1500: Costing');
  allure.tag('F1500');
  allure.tag('F50000: Frontend WebUI');
  allure.tag('F50000');
  allure.story(story);
  allure.severity('normal');
}

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  // eslint-disable-next-line no-unused-vars
  test(`The quick entry that opens by itself gives Product the cursor for the next line and on a document with lines (${label})`, async ({
    page,
  }) => {
    test.setTimeout(240000);
    allureTags('Quick-input: the first field gets the cursor');
    allure.description(`
## F1500: Costing — the per-product quick entry gives Product the cursor

The lines tab of Kosten Neubewertung adds lines by quick entry only, so the quick entry opens by itself.
After a line was added with Enter, the cursor is in Product for the next line. When the document, which now has a
line, is opened again, the cursor is in Product as well. In both cases it stays there.
    `);

    const md = await createMasterdata(language);
    await LoginPage.goto();
    await LoginPage.login(md.login.user);
    await DashboardPage.expectVisible();
    const recordId = await CostRevaluationPage.createHeader();
    await page.locator('.quick-input-container').waitFor({ state: 'visible' });

    await test.step('The next quick input arrives only after the grid shows the added line (as on a slow network)', async () => {
      await holdNextQuickInputUntilLinesShown(page, 1);
    });
    await CostRevaluationPage.addLine(md.products.P1.productCode, '15');

    await test.step('After Enter added the line, the cursor is in Product for the next line', async () => {
      const productInput = page.locator(PRODUCT_INPUT);
      await expectFocusOn(productInput, 'next line');
      await expectFocusHeld(productInput, FOCUS_HOLD_MILLIS, 'next line');
    });

    await CostRevaluationPage.reloadDocument(recordId);

    await test.step('The document has a line: the quick entry opens by itself with the cursor in Product', async () => {
      const productInput = page.locator(PRODUCT_INPUT);
      await expectFocusOn(productInput, 'document with lines');
      await expectFocusHeld(productInput, FOCUS_HOLD_MILLIS, 'document with lines');
    });

    await test.step('The second line is entered from there by keyboard', async () => {
      await CostRevaluationPage.addLine(md.products.P2.productCode, '20');
      const lines = await CostRevaluationPage.getLines(recordId);
      test.expect(lines.length).toBe(2);
    });
  });
});
