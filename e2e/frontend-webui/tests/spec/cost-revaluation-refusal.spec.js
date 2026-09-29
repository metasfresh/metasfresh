import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { getFieldData } from '../utils/WebAPIValidation';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation, window 541568), Complete:
 *  - a revaluation whose Evaluation Start Date is on or before the Accounting Date of another completed revaluation
 *    of the same product is refused, with a translated message naming the product and that date, and stays Drafted;
 *  - a revaluation while an earlier completed revaluation of the product is not posted yet is refused likewise;
 *  - a back-dated revaluation (starting before the product's stock receipt) completes and books qty x (new - old).
 *
 * Expects the accounting schema to use CLIENT-level costing.
 */

const ACCT_ENABLED_SYSCONFIG = 'org.adempiere.acct.Enabled';

/** Masterdata: PSTK, a stocked Item with 10 on hand in a warehouse (optionally with sysconfigs applied first). */
async function createStockedMasterdata(language, productName, sysconfigs) {
  return await Backend.createMasterdata({
    request: {
      ...(sysconfigs ? { sysconfigs } : {}),
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

/** The product as the refusal messages render it: Value_Name. */
const productValueAndName = (md) => `${md.products.PSTK.productCode}_${md.products.PSTK.productName}`;

const testCases = [
  {
    language: 'en_US',
    label: 'English',
    // yyyy-MM-dd -> MM/dd/yyyy
    formatDate: (isoDate) => {
      const [y, m, d] = isoDate.split('-');
      return `${m}/${d}/${y}`;
    },
    // AD_Message CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported
    laterRevaluationMessage: (product, date) =>
      `Product ${product} already has a later cost revaluation dated ${date}. ` +
      'A cost revaluation cannot start before or on the same day as another revaluation of the same product. ' +
      `Please choose a later evaluation start date, after ${date}.`,
    // AD_Message M_CostRevaluation.EarlierRevaluationNotPosted
    notPostedMessage: (product, date) =>
      `The cost revaluation of product ${product} dated ${date} is completed but not posted yet. ` +
      'Please wait until it is posted (or fix its posting error), then try again.',
  },
  {
    language: 'de_DE',
    label: 'German',
    // yyyy-MM-dd -> dd.MM.yyyy
    formatDate: (isoDate) => {
      const [y, m, d] = isoDate.split('-');
      return `${d}.${m}.${y}`;
    },
    laterRevaluationMessage: (product, date) =>
      `Für das Produkt ${product} gibt es bereits eine spätere Kosten Neubewertung vom ${date}. ` +
      'Eine Kosten Neubewertung kann nicht vor oder am selben Tag wie eine andere Neubewertung desselben Produkts beginnen. ' +
      `Bitte ein späteres Startdatum der Bewertung wählen, nach dem ${date}.`,
    notPostedMessage: (product, date) =>
      `Für das Produkt ${product} ist die Kosten Neubewertung vom ${date} fertiggestellt, aber noch nicht gebucht. ` +
      'Bitte warten, bis sie gebucht ist (oder ihren Buchungsfehler beheben), und dann erneut versuchen.',
  },
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

testCases.forEach(({ language, label, formatDate, laterRevaluationMessage, notPostedMessage }) => {
  // eslint-disable-next-line no-unused-vars
  test(`Completing a revaluation that starts on or before another revaluation of the product is refused (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allureTags('Complete: refused when another revaluation of the product is in the way');
    allure.description(`
## F1500: Costing — a cost revaluation cannot restate another one

A stocked product (10 on hand) gets a completed Kosten Neubewertung (New cost price 10).
A second Kosten Neubewertung for the same product, whose Evaluation Start Date is set to the first one's
Accounting Date, is refused on Complete with the translated message naming the product and that date, and stays Drafted.
    `);

    const md = await createStockedMasterdata(language, 'CR_REFUSAL');
    const productCode = md.products.PSTK.productCode;
    await login(md);

    let first = null;
    await test.step('First Kosten Neubewertung: New cost price 10, completed', async () => {
      first = await createCompletedRevaluation(productCode, '10');
    });

    const recordId = await CostRevaluationPage.createHeader();
    await test.step(`Set the Evaluation Start Date to the first one's Accounting Date ${first.dateAcct}`, async () => {
      // Set explicitly: the default (the new header's posting date) would move past it if the day changed in between.
      if ((await getDate(recordId, 'EvaluationStartDate')) !== first.dateAcct) {
        await CostRevaluationPage.typeHeaderDate(recordId, 'EvaluationStartDate', first.dateAcct);
      }
      expect(await getDate(recordId, 'EvaluationStartDate')).toBe(first.dateAcct);
    });

    await CostRevaluationPage.expectQuickInputOpened();
    await CostRevaluationPage.addLine(productCode, '15');

    await test.step('Try to complete the second one -> refused, naming the product and the first one\'s date', async () => {
      await CostRevaluationPage.completeExpectingRefusal(
        laterRevaluationMessage(productValueAndName(md), formatDate(first.dateAcct))
      );
    });

    expect((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus')).value.key).toBe('DR');
  });

  // eslint-disable-next-line no-unused-vars
  test(`Completing a revaluation while an earlier one of the product is not posted yet is refused (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allureTags('Complete: refused while an earlier revaluation of the product is not posted');
    allure.description(`
## F1500: Costing — wait for the earlier revaluation to be posted

With accounting switched off (sysconfig ${ACCT_ENABLED_SYSCONFIG}=N), a completed Kosten Neubewertung of a stocked product
stays not posted. A later Kosten Neubewertung of the same product (starting the day after) is refused on Complete
with the translated message naming the product and the unposted one's date, and stays Drafted.
    `);

    // Accounting is on by default; it is switched back on in finally even if creating the masterdata fails halfway,
    // so a failure here cannot leave accounting off for the next specs of the shard (or for a retry).
    try {
      const md = await createStockedMasterdata(language, 'CR_NOT_POSTED', { [ACCT_ENABLED_SYSCONFIG]: 'N' });
      const productCode = md.products.PSTK.productCode;
      await login(md);

      let first = null;
      await test.step('First Kosten Neubewertung: completed, not posted (accounting is off)', async () => {
        first = await createCompletedRevaluation(productCode, '10');
        // Posted is not a field of every window layout (e.g. the CI database's); the refusal below names the not-posted state itself
        expect((await getFieldData(COST_REVAL_WINDOW_ID, first.recordId, 'DocStatus')).value.key).toBe('CO');
      });

      const recordId = await CostRevaluationPage.createHeader();
      const laterDate = addDays(first.dateAcct, 1);
      await test.step(`Second one dated ${laterDate}, after the first one`, async () => {
        if ((await getDate(recordId, 'DateAcct')) !== laterDate) {
          await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', laterDate);
        }
        expect(await getDate(recordId, 'EvaluationStartDate')).toBe(laterDate);
      });

      await CostRevaluationPage.expectQuickInputOpened();
      await CostRevaluationPage.addLine(productCode, '15');

      await test.step('Try to complete the second one -> refused, naming the product and the unposted one\'s date', async () => {
        await CostRevaluationPage.completeExpectingRefusal(notPostedMessage(productValueAndName(md), formatDate(first.dateAcct)));
      });

      expect((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus')).value.key).toBe('DR');
    } finally {
      try {
        await Backend.createMasterdata({ request: { sysconfigs: { [ACCT_ENABLED_SYSCONFIG]: 'Y' } } });
      } catch (error) {
        console.error(`Could not switch ${ACCT_ENABLED_SYSCONFIG} back on`, error); // must not hide the test's own failure
      }
    }
  });

  // eslint-disable-next-line no-unused-vars
  test(`A back-dated revaluation, starting before the stock receipt, completes and books qty x (new - old) (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allureTags('Complete: back-dated revaluation');
    allure.description(`
## F1500: Costing — back-dated cost revaluation

A stocked product (10 received today). A Kosten Neubewertung whose Accounting Date and Evaluation Start Date lie
two days before the receipt, New cost price 15, completes (CO); its line's value difference is qty x (new - old).
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
    // The stock received after the start date is revalued.
    expect(qty).toBe(10);
    expect(newPrice).toBe(15);
    expect(oldPrice).not.toBe(newPrice);
    expect(Number(line.DeltaAmt.value)).toBeCloseTo(qty * (newPrice - oldPrice), 2);
  });
});
