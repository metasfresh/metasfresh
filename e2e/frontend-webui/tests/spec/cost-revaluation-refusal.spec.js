import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { getFieldData } from '../utils/WebAPIValidation';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation, window 541568): a revaluation whose Evaluation Start Date
 * is on or before the Accounting Date of another completed revaluation of the same product is refused on Complete,
 * with a translated message, and stays Drafted.
 *
 * Expects the accounting schema to use CLIENT-level costing.
 */

/** Masterdata: PSTK, a stocked Item with 10 on hand in a warehouse. */
async function createStockedMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostReval', lastname: 'E2E' } },
      warehouses: { WH: {} },
      products: { PSTK: { name: 'CR_REFUSAL', type: 'Item' } },
      handlingUnits: { HU1: { product: 'PSTK', warehouse: 'WH', qty: 10 } },
    },
  });
}

async function getDate(recordId, columnName) {
  return String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, columnName)).value).substring(0, 10);
}

const testCases = [
  // A fragment of the translated AD_Message CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported
  { language: 'en_US', label: 'English', expectedMessage: 'already has a later cost revaluation dated' },
  { language: 'de_DE', label: 'German', expectedMessage: 'gibt es bereits eine spätere Kosten Neubewertung vom' },
];

testCases.forEach(({ language, label, expectedMessage }) => {
  // eslint-disable-next-line no-unused-vars
  test(`Completing a revaluation that starts on or before another revaluation of the product is refused (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allure.epic('E0226: Costing');
    allure.tag('F1500: Costing');
    allure.tag('F1500');
    allure.story('Complete: refused when another revaluation of the product is in the way');
    allure.severity('critical');
    allure.description(`
## F1500: Costing — a cost revaluation cannot restate another one

A stocked product (10 on hand) gets a completed Kosten Neubewertung (New cost price 10).
A second Kosten Neubewertung for the same product, whose Evaluation Start Date is on the first one's
Accounting Date, is refused on Complete with the translated message, and stays Drafted.
    `);

    const md = await createStockedMasterdata(language);
    const productCode = md.products.PSTK.productCode;

    await LoginPage.goto();
    await LoginPage.login(md.login.user);
    await DashboardPage.expectVisible();

    let firstDateAcct = null;
    await test.step('First Kosten Neubewertung: New cost price 10, completed', async () => {
      const firstRecordId = await CostRevaluationPage.createHeader();
      firstDateAcct = await getDate(firstRecordId, 'DateAcct');
      await CostRevaluationPage.expectQuickInputOpened();
      await CostRevaluationPage.addLine(productCode, '10');
      await CostRevaluationPage.complete();
      expect((await getFieldData(COST_REVAL_WINDOW_ID, firstRecordId, 'DocStatus')).value.key).toBe('CO');
    });

    const recordId = await CostRevaluationPage.createHeader();
    console.log(`[refusal] second header record ${recordId}`);
    // Precondition: the second revaluation starts on or before the first one's Accounting Date.
    const evaluationStartDate = await getDate(recordId, 'EvaluationStartDate');
    console.log(`[refusal] first DateAcct=${firstDateAcct} second EvaluationStartDate=${evaluationStartDate}`);
    expect(evaluationStartDate <= firstDateAcct).toBe(true);

    await CostRevaluationPage.expectQuickInputOpened();
    await CostRevaluationPage.addLine(productCode, '15');

    await test.step('Try to complete the second one -> refused with the translated message', async () => {
      await CostRevaluationPage.completeExpectingRefusal(expectedMessage);
    });

    const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
    console.log('[refusal] DocStatus=' + JSON.stringify(docStatus.value));
    expect(docStatus.value.key).toBe('DR');
  });
});
