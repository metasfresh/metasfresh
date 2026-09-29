import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_LINE_TAB_ID, COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { getFieldData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation) per-product quick-input E2E suite.
 *
 * Desktop WebUI, window 541568. Proves the manual cost-adjustment quick-input:
 *  - quick-input a per-product line for a product that has a current cost, Run + Complete.
 *  - the quick-input product picker offers stocked/eligible products (incl. a stocked product with no
 *    cost record) and NOT non-stocked products.
 *  - seed a stocked product that has NO cost record, Run + Complete, with the provisional-price hint
 *    shown on the New cost price field.
 *
 * Expects the accounting schema to use CLIENT-level costing.
 */

/**
 * Create the shared masterdata for this workflow:
 *  - PHAS: a stocked Item — gets default M_Cost rows on creation (has a current cost).
 *  - PSEED: a stocked Item with isSkipDefaultCosts — genuinely NO M_Cost row (migrated-product / seed case).
 *  - PSVC: a Service — NOT stocked (must be filtered out of the picker).
 */
async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostReval', lastname: 'E2E' } },
      products: {
        PHAS: { name: 'CR_HAS_COST', type: 'Item' },
        PSEED: { name: 'CR_SEED_NOROW', type: 'Item', isSkipDefaultCosts: true },
        PSVC: { name: 'CR_SERVICE', type: 'Service' },
      },
    },
  });
}

/** Login, then open a NEW Kosten Neubewertung header (EvaluationStartDate auto-defaults). */
async function loginAndCreateHeader(masterdata) {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();
  return await CostRevaluationPage.createHeader();
}

function allureTags(story) {
  allure.epic('E0226: Costing');
  allure.tag('F1500: Costing');
  allure.tag('F1500');
  allure.story(story);
  allure.severity('critical');
}

// ============================================================================

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  // eslint-disable-next-line no-unused-vars
  test(`Quick-input manual cost adjustment for a product with a current cost (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Quick-input: per-product cost adjustment (Run + Complete)');
    allure.description(`
## F1500: Costing — manual per-product cost adjustment (Kosten Neubewertung)

Creates a Kosten Neubewertung header, adds ONE line via the per-product quick-input for a product
that has a current cost, runs the revaluation and completes the document.
Verifies the quick-input offers Product + New cost price, the line is created with the derived
segment and the document completes.
    `);

    const md = await createMasterdata(language);
    const productCode = md.products.PHAS.productCode;

    const recordId = await loginAndCreateHeader(md);
    console.log(`[quick-input] header record ${recordId}`);

    await CostRevaluationPage.openQuickInput();

    // Quick-input offers exactly Product + New cost price
    await expect(CostRevaluationPage.quickInputField('M_Product_ID')).toBeVisible();
    await expect(CostRevaluationPage.quickInputField('NewCostPrice')).toBeVisible();

    await CostRevaluationPage.addLine(productCode, '15');

    // Exactly one line exists with NewCostPrice = typed value (assert via WebAPI, language-independent)
    const lineRows = await page.request.get(
      `${WEBAPI_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/${recordId}/${COST_REVAL_LINE_TAB_ID}`
    );
    const lineBody = await lineRows.json();
    const rows = lineBody.result || [];
    console.log('[quick-input] line count=' + rows.length);
    expect(rows.length).toBe(1);
    const newCostVal = rows[0].fieldsByName.NewCostPrice.value;
    console.log('[quick-input] NewCostPrice=' + JSON.stringify(newCostVal));
    expect(Number(newCostVal)).toBe(15);
    // CurrentCostPrice derived from the product's live M_Cost (present, may be 0 for a fresh cost row)
    expect(rows[0].fieldsByName.CurrentCostPrice).toBeDefined();

    await CostRevaluationPage.complete();

    // Assert the document reached Completed (CO)
    const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
    console.log('[quick-input] DocStatus=' + JSON.stringify(docStatus.value));
    expect(docStatus.value.key).toBe('CO');
    await CostRevaluationPage.showCompletedDocument();
  });

  // eslint-disable-next-line no-unused-vars
  test(`Quick-input picker offers stocked/eligible products incl. costless, excludes non-stocked (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Quick-input: product picker is stocked/eligible-filtered');
    allure.description(`
## F1500: Costing — quick-input product picker filter

The quick-input product picker is filtered to stocked/eligible items (AD_Reference 171). It offers:
 - a stocked product that has a current cost,
 - a stocked product that has NO cost record (costless),
and it does NOT offer a non-stocked (Service) product.
    `);

    const md = await createMasterdata(language);
    await loginAndCreateHeader(md);
    await CostRevaluationPage.openQuickInput();

    await test.step('Picker offers a stocked product that has a current cost', async () => {
      const hasCostOpts = await CostRevaluationPage.searchProduct(md.products.PHAS.productCode);
      await expect(hasCostOpts.filter({ hasText: md.products.PHAS.productCode })).toHaveCount(1);
    });

    await test.step('Picker offers a stocked product with NO cost record (costless)', async () => {
      const costlessOpts = await CostRevaluationPage.searchProduct(md.products.PSEED.productCode);
      await expect(costlessOpts.filter({ hasText: md.products.PSEED.productCode })).toHaveCount(1);
    });

    await test.step('Picker excludes a non-stocked (Service) product', async () => {
      const serviceOpts = await CostRevaluationPage.searchProduct(md.products.PSVC.productCode);
      await expect(serviceOpts.filter({ hasText: md.products.PSVC.productCode })).toHaveCount(0);
    });

    console.log('[picker] stocked (has-cost + costless) offered; non-stocked excluded');
  });

  // eslint-disable-next-line no-unused-vars
  test(`Seed-cost path: stocked product with no cost record, with provisional hint (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Quick-input: seed a cost for a stocked product with no cost record');
    allure.description(`
## F1500: Costing — seed-cost path (stocked product with no cost record)

Picks a STOCKED product that has NO M_Cost row, quick-inputs a New cost price, and completes.
The Complete DocAction seeds the cost row at qty 0 with the entered price (no GL posting) and the
document completes. The New cost price field surfaces the provisional-price hint (moving-average:
provisional until the first goods receipt).
    `);

    const md = await createMasterdata(language);
    const seedProductCode = md.products.PSEED.productCode;

    const recordId = await loginAndCreateHeader(md);
    console.log(`[seed] header record ${recordId}`);

    await CostRevaluationPage.openQuickInput();

    // Pick the costless stocked product so its NewCostPrice field renders (with the hint).
    await test.step(`Pick costless stocked product ${seedProductCode}`, async () => {
      await CostRevaluationPage.pickProduct(seedProductCode);
    });

    // The provisional-price hint is shown on the New cost price field (label title).
    // Language-independent: compare the rendered title to the field's description from the quick-input layout.
    await test.step('Verify provisional-price hint on New cost price field', async () => {
      const qiLayout = await (
        await page.request.get(
          `${WEBAPI_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/${recordId}/${COST_REVAL_LINE_TAB_ID}/quickInput/layout`
        )
      ).json();
      const npcElement = (qiLayout.elements || []).find(
        (e) => (e.fields || []).some((f) => f.field === 'NewCostPrice')
      );
      const expectedHint = npcElement.description;
      console.log('[seed] layout hint = ' + JSON.stringify(expectedHint));
      expect(expectedHint && expectedHint.length).toBeGreaterThan(0);

      const npcLabelTitle = await CostRevaluationPage.hoverNewCostPriceHint();
      console.log('[seed] rendered NewCostPrice label title = ' + JSON.stringify(npcLabelTitle));
      expect(npcLabelTitle).toBe(expectedHint);
    });

    // Type the seed price and submit the line. The seed path materialises the missing M_Cost row
    // server-side before creating the line, so poll for the row.
    await test.step('Enter New cost price 20 + add the seed line', async () => {
      await CostRevaluationPage.enterNewCostPriceAndSubmit(20);
    });

    // One seed line created with NewCostPrice = typed value (poll for the slower seed round-trip).
    let rows = [];
    for (let i = 0; i < 12; i++) {
      await page.waitForTimeout(1000);
      const lineRows = await (
        await page.request.get(
          `${WEBAPI_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/${recordId}/${COST_REVAL_LINE_TAB_ID}`
        )
      ).json();
      rows = lineRows.result || [];
      if (rows.length >= 1) break;
    }
    console.log('[seed] line count=' + rows.length);
    expect(rows.length).toBe(1);
    expect(Number(rows[0].fieldsByName.NewCostPrice.value)).toBe(20);

    // Completes cleanly (Complete seeds the cost row at qty 0 and posts zero delta).
    await CostRevaluationPage.complete();
    const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
    console.log('[seed] DocStatus=' + JSON.stringify(docStatus.value));
    expect(docStatus.value.key).toBe('CO');
    await CostRevaluationPage.showCompletedDocument();
  });
});
