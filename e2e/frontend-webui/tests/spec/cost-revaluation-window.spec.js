import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { getFieldData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation, window 541568), window + quick-input behaviour:
 *  - a revaluation completed after another revaluation of the same product was completed and posted is accepted;
 *    its line shows the values recalculated from the first one's posted cost price;
 *  - a second quick-input line for the same product is refused with a message naming the product, not framed as "Server error";
 *  - a revaluation with a past posting date completes; its line shows qty on hand x (new - old);
 *  - the status menu of a completed manual revaluation offers Stornieren only until it is posted, then only Schliessen.
 *
 * Runs at the accounting schema's costing level (organization level on the standard test DB);
 * the client level of the customer is pinned by the cucumber feature `cost_revaluation.feature:36-38`.
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

/** @returns {string} the key of a list field's value (e.g. Posted 'Y'), or the plain value */
const fieldKey = (field) => (field.value && typeof field.value === 'object' ? field.value.key : field.value);

/**
 * The status menu (DocAction dropdown) the WebUI shows for the document, as the user opening it gets it.
 * @returns {Promise<string[]>} the offered DocAction keys, sorted
 */
async function getStatusMenuKeys(page, recordId) {
  const response = await page.request.get(`${WEBAPI_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/${recordId}/field/DocAction/dropdown`);
  expect(response.ok(), 'GET the status menu (DocAction dropdown)').toBe(true);
  return ((await response.json()).values || []).map((value) => value.key).sort();
}

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

  // eslint-disable-next-line no-unused-vars
  test(`The status menu of a completed manual revaluation offers only Close once it is posted (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allureTags('Status menu: a completed Kosten Neubewertung offers only actions that succeed');
    allure.description(`
## F1500: Costing — status menu of a completed Kosten Neubewertung

A stocked product (10 on hand) gets a Kosten Neubewertung (source Manual) with a New cost price that differs from
its current cost price, and it is completed.

- Right after Complete the user opens the status menu. Reaktivieren (Reactivate) is never offered on a completed
  revaluation. Stornieren (Void) is offered only as long as no line has its revaluation cost detail yet; the posting
  creates the cost detail before it sets Posted = Y, so whether this first read still shows Stornieren depends on timing.
- Once the posting has finished (accounting facts exist, Posted = Y), the status menu offers only Schliessen (Close):
  Stornieren is gone because the line is booked.

The second read goes through the same lookup the first one primed, so when the first read showed Stornieren, the
second one proves the menu follows the posting instead of serving the cached answer.
    `);

    const md = await createStockedMasterdata(language, 'CR_STATUS_MENU');
    const productCode = md.products.PSTK.productCode;
    await login(md);

    const { recordId } = await createCompletedRevaluation(productCode, '10');
    const line = (await CostRevaluationPage.getLines(recordId))[0].fieldsByName;
    expect(
      Number(line.CurrentCostPrice.value),
      'precondition: the fresh product\'s cost price differs from 10, so the posting books a difference'
    ).not.toBe(10);

    await test.step('Right after Complete: the user opens the status menu (primes its lookup); Reaktivieren is not offered', async () => {
      const posted = fieldKey(await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'Posted'));
      const keys = await getStatusMenuKeys(page, recordId);
      console.log(`[INFO] right after Complete: Posted=${posted}, status menu=${JSON.stringify(keys)}`);
      // Not asserted: whether Stornieren is offered here. It is offered until the posting has created the line's cost detail,
      // which happens before Posted turns Y, so "Posted is not Y" does not imply "Stornieren is offered".
      test.info().annotations.push({
        type: 'status menu right after Complete',
        description: `Posted=${posted}, offered=${JSON.stringify(keys)}`,
      });
      expect(keys, 'Reaktivieren is never offered on a completed revaluation').not.toContain('RE');
    });

    await test.step('Wait until the document is posted', async () => {
      await CostRevaluationPage.waitUntilPosted(recordId);
      await expect
        .poll(async () => fieldKey(await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'Posted')), {
          message: `Kosten Neubewertung ${recordId} shows Posted = Y`,
          timeout: 2 * VERY_SLOW_ACTION_TIMEOUT, // posting is asynchronous, see CostRevaluationPage.waitUntilPosted
        })
        .toBe('Y');
    });

    await test.step('Posted: the status menu offers only Schliessen', async () => {
      const keys = await getStatusMenuKeys(page, recordId);
      console.log(`[INFO] posted: status menu=${JSON.stringify(keys)}`);
      expect(keys).toEqual(['CL']);
    });
  });
});
