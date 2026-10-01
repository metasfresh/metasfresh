import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { getFieldData } from '../utils/WebAPIValidation';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation, window 541568), window + quick-input behaviour:
 *  - a revaluation completed after another revaluation of the same product was completed and posted is accepted;
 *    its line shows the values recalculated from the first one's posted cost price;
 *  - a second quick-input line for the same product is refused with a message naming the product, not framed as "Server error";
 *  - a revaluation with a past posting date completes; its line shows qty on hand x (new - old).
 *
 * Runs at the accounting schema's costing level (organization level on the standard test DB);
 * the client level of the customer is pinned by the cucumber feature `cost_revaluation.feature:37-39`.
 */

/** Masterdata: PSTK, a stocked Item with 10 on hand in a warehouse. */
async function createStockedMasterdata(language, productName) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostReval', lastname: 'E2E' } },
      warehouses: { WH: {} },
      products: { PSTK: { name: productName, type: 'Item' } },
      handlingUnits: { HU1: { product: 'PSTK', warehouse: 'WH', qty: 10 } },
    },
  });
}

async function getDate(recordId, columnName) {
  return String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, columnName)).value).substring(0, 10);
}

/** @returns {string} isoDate (yyyy-MM-dd) shifted by the given number of days */
const addDays = (isoDate, days) => {
  const date = new Date(`${isoDate}T00:00:00Z`);
  date.setUTCDate(date.getUTCDate() + days);
  return date.toISOString().substring(0, 10);
};

/** The product as the messages render it: Value_Name. */
const productValueAndName = (md) => `${md.products.PSTK.productCode}_${md.products.PSTK.productName}`;

// The exact translations of the refusal message are checked in cost-revaluation-translations.spec.js.
const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

const allureTags = (story) => {
  allure.epic('E0226: Costing');
  allure.tag('F1500: Costing');
  allure.tag('F1500');
  allure.story(story);
  allure.severity('critical');
};

async function login(md) {
  await LoginPage.goto();
  await LoginPage.login(md.login.user);
  await DashboardPage.expectVisible();
}

/** Create, fill (one line) and complete a revaluation; @returns {Promise<{recordId: string, dateAcct: string}>} */
async function createCompletedRevaluation(productCode, newCostPrice) {
  const recordId = await CostRevaluationPage.createHeader();
  const dateAcct = await getDate(recordId, 'DateAcct');
  await CostRevaluationPage.expectQuickInputOpened();
  await CostRevaluationPage.addLine(productCode, newCostPrice);
  await CostRevaluationPage.complete();
  expect((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus')).value.key).toBe('CO');
  return { recordId, dateAcct };
}

testCases.forEach(({ language, label }) => {
  // eslint-disable-next-line no-unused-vars
  test(`Complete after another revaluation was completed and posted is accepted; the line shows the recalculated values (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allureTags('Complete: a revaluation after another posted revaluation of the product');
    allure.description(`
## F1500: Costing — a revaluation after another posted revaluation

A stocked product (10 on hand) gets a completed and posted Kosten Neubewertung (New cost price 10).
A second Kosten Neubewertung for the same product, New cost price 15, is accepted on Complete (CO);
its line shows the current cost price set by the first one's posting (10), the quantity on hand 10
and the value difference 10 x (15 - 10) = 50.
    `);

    const md = await createStockedMasterdata(language, 'CR_AFTER_POSTED');
    const productCode = md.products.PSTK.productCode;
    await login(md);

    await test.step('First Kosten Neubewertung: New cost price 10, completed and posted', async () => {
      const first = await createCompletedRevaluation(productCode, '10');
      const firstLine = (await CostRevaluationPage.getLines(first.recordId))[0].fieldsByName;
      // A value difference is booked, so the posting writes accounting facts the wait below can see.
      expect(
        Number(firstLine.CurrentCostPrice.value),
        'precondition: the fresh product\'s default cost price differs from 10, so the first revaluation books a difference'
      ).not.toBe(10);
      // The current cost price changes only when the revaluation is posted.
      await CostRevaluationPage.waitUntilPosted(first.recordId);
    });

    const recordId = await CostRevaluationPage.createHeader();
    await CostRevaluationPage.expectQuickInputOpened();
    await CostRevaluationPage.addLine(productCode, '15');
    await CostRevaluationPage.complete();

    expect((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus')).value.key).toBe('CO');
    const lines = await CostRevaluationPage.getLines(recordId);
    expect(lines.length).toBe(1);
    const line = lines[0].fieldsByName;
    expect(Number(line.CurrentCostPrice.value)).toBe(10);
    expect(Number(line.CurrentQty.value)).toBe(10);
    expect(Number(line.NewCostPrice.value)).toBe(15);
    expect(Number(line.DeltaAmt.value)).toBe(50);
    await CostRevaluationPage.showCompletedDocument();
  });

  // eslint-disable-next-line no-unused-vars
  test(`A second quick-input line for the same product is refused with a message naming the product and no 'Server error' prefix (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Quick-input: a second line for the same product is refused');
    allure.description(`
## F1500: Costing — one line per product

A Kosten Neubewertung already has a line for a stocked product. Adding a second line for the same product
through the quick-input is refused with a message naming the product, shown as a plain message (not framed as
"Server error"); the document keeps its one line.
    `);

    // A long product name on purpose: the notification shows a status prefix such as "Server error" only for a
    // message longer than 100 characters (SHOW_READ_MORE_FROM), so the message must be that long for the check to bite.
    const md = await createStockedMasterdata(language, 'CR_DUPLICATE_LINE_FOR_THE_SAME_PRODUCT');
    const productCode = md.products.PSTK.productCode;
    await login(md);

    const recordId = await CostRevaluationPage.createHeader();
    await CostRevaluationPage.expectQuickInputOpened();
    await CostRevaluationPage.addLine(productCode, '10');

    const shownMessage = await CostRevaluationPage.addLineExpectingRefusal(productCode, '15', productValueAndName(md));
    expect(shownMessage.length, 'the refusal message is longer than 100 characters, so the "Server error" check bites').toBeGreaterThan(100);

    const lines = await CostRevaluationPage.getLines(recordId);
    expect(lines.length).toBe(1);
    expect(Number(lines[0].fieldsByName.NewCostPrice.value)).toBe(10);
    expect((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus')).value.key).toBe('DR');
  });

  // eslint-disable-next-line no-unused-vars
  test(`A revaluation with a past posting date completes; its line shows qty on hand x (new - old) (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allureTags('Complete: revaluation with a past posting date');
    allure.description(`
## F1500: Costing — cost revaluation with a past posting date

A stocked product (10 on hand). A Kosten Neubewertung whose Accounting Date (and so its Evaluation Start Date)
lies two days in the past, New cost price 15, completes (CO); its line's value difference is
qty on hand x (new - old).
    `);

    const md = await createStockedMasterdata(language, 'CR_BACKDATED');
    const productCode = md.products.PSTK.productCode;
    await login(md);

    const recordId = await CostRevaluationPage.createHeader();
    const pastDate = addDays(await getDate(recordId, 'DateAcct'), -2);
    await test.step(`Back-date the Accounting Date to ${pastDate} -> Evaluation Start Date follows`, async () => {
      await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', pastDate);
      expect(await getDate(recordId, 'DateAcct')).toBe(pastDate);
      expect(await getDate(recordId, 'EvaluationStartDate')).toBe(pastDate);
    });

    await CostRevaluationPage.expectQuickInputOpened();
    await CostRevaluationPage.addLine(productCode, '15');
    await CostRevaluationPage.complete();

    expect((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus')).value.key).toBe('CO');
    const lines = await CostRevaluationPage.getLines(recordId);
    expect(lines.length).toBe(1);
    const line = lines[0].fieldsByName;
    const qty = Number(line.CurrentQty.value);
    const oldPrice = Number(line.CurrentCostPrice.value);
    const newPrice = Number(line.NewCostPrice.value);
    // The stock on hand is revalued.
    expect(qty).toBe(10);
    expect(newPrice).toBe(15);
    expect(oldPrice).not.toBe(newPrice);
    expect(Number(line.DeltaAmt.value)).toBeCloseTo(qty * (newPrice - oldPrice), 2);
  });
});
