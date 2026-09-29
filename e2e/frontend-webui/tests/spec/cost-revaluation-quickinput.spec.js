import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_LINE_TAB_ID, COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { PRODUCT_COST_M_COST_TAB_ID, PRODUCT_COST_WINDOW_ID } from '../utils/pages/ProductCostPage';
import { getFieldData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation) per-product quick-input E2E suite.
 *
 * Desktop WebUI, window 541568. Proves the manual cost-adjustment quick-input:
 *  - quick-input a per-product line for a stocked product that has a current cost, Complete (posts the value difference).
 *  - the quick-input product picker offers stocked/eligible products (incl. a stocked product with no
 *    cost record) and NOT non-stocked products.
 *  - seed a cost for a stocked product that has NO cost record (seeded when the line is added; Complete sets the
 *    entered, fractional price), with the provisional-price hint shown on the New cost price field.
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

/**
 * Masterdata for the per-product adjustment: PSTK, a stocked Item with 10 on hand in a warehouse.
 */
async function createStockedMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostReval', lastname: 'E2E' } },
      warehouses: { WH: {} },
      products: { PSTK: { name: 'CR_STOCKED', type: 'Item' } },
      handlingUnits: { HU1: { product: 'PSTK', warehouse: 'WH', qty: 10 } },
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

/**
 * The moving-average (average purchase-order price) cost element of the standard setup. Selected by its name:
 * M_CostElement.Name is master data without translations, so it is the same in every login language.
 */
const MOVING_AVERAGE_PO_COST_ELEMENT_NAME = 'Bestellpreis Durchschnitt';

/** A fractional price on purpose: the quick-input must accept decimals, not only whole numbers. */
const SEED_COST_PRICE = 12.35;

/**
 * The product's cost records (M_Cost rows), read from the Product Cost (Produktkosten, window 344) cost tab.
 * @returns {Promise<{costElementId: string, costElementName: string, currentCostPrice: number, currentQty: number}[]>}
 */
async function getProductCosts(page, productId) {
  const response = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${PRODUCT_COST_WINDOW_ID}/${productId}/${PRODUCT_COST_M_COST_TAB_ID}`
  );
  expect(response.status()).toBe(200);
  return ((await response.json()).result || []).map((row) => ({
    costElementId: row.fieldsByName.M_CostElement_ID && String(row.fieldsByName.M_CostElement_ID.value.key),
    costElementName: row.fieldsByName.M_CostElement_ID && row.fieldsByName.M_CostElement_ID.value.caption,
    currentCostPrice: Number(row.fieldsByName.CurrentCostPrice.value),
    currentQty: Number(row.fieldsByName.CurrentQty && row.fieldsByName.CurrentQty.value),
  }));
}

/** The M_CostElement_ID of the cost element with the given name, read from the default cost rows of a product that has them. */
async function getCostElementId(page, productIdWithCosts, costElementName) {
  const costElementIds = [
    ...new Set(
      (await getProductCosts(page, productIdWithCosts)).filter((c) => c.costElementName === costElementName).map((c) => c.costElementId)
    ),
  ];
  expect(costElementIds, `exactly one cost element named ${costElementName}`).toHaveLength(1);
  return costElementIds[0];
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
  test(`Quick-input manual cost adjustment for a stocked product with a current cost (${label})`, async ({ page }) => {
    test.setTimeout(240000);
    allureTags('Quick-input: per-product cost adjustment (Run + Complete)');
    allure.description(`
## F1500: Costing — manual per-product cost adjustment (Kosten Neubewertung)

A stocked product (10 on hand) is first given a current cost price of 10 with one Kosten Neubewertung.
A second Kosten Neubewertung then adds ONE line via the per-product quick-input with New cost price 15
and is completed. Verifies the quick-input offers Product + New cost price, the line shows the current
cost 10, the quantity on hand 10 and the value difference 10 x (15 - 10) = 50, and the document is
completed and posted.
    `);

    const md = await createStockedMasterdata(language);
    const productCode = md.products.PSTK.productCode;

    // The adjustment is posted the day after the setup: its revaluation window starts at its own Accounting Date,
    // so the stock received and the setup revaluation of the previous day are not replayed again.
    let adjustmentDate = null;
    await test.step('Setup: stock of 10 with a current cost price of 10 (first Kosten Neubewertung)', async () => {
      const setupRecordId = await loginAndCreateHeader(md);
      const serverDate = String((await getFieldData(COST_REVAL_WINDOW_ID, setupRecordId, 'DateAcct')).value).substring(0, 10);
      const nextDay = new Date(`${serverDate}T00:00:00Z`);
      nextDay.setUTCDate(nextDay.getUTCDate() + 1);
      adjustmentDate = nextDay.toISOString().substring(0, 10);
      await CostRevaluationPage.expectQuickInputOpened();
      await CostRevaluationPage.addLine(productCode, '10');
      await CostRevaluationPage.complete();
      const setupDocStatus = await getFieldData(COST_REVAL_WINDOW_ID, setupRecordId, 'DocStatus');
      expect(setupDocStatus.value.key).toBe('CO');
    });

    const recordId = await CostRevaluationPage.createHeader();
    console.log(`[quick-input] header record ${recordId}`);
    const headerDate = String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct')).value).substring(0, 10);
    if (headerDate !== adjustmentDate) {
      await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', adjustmentDate);
    }

    await CostRevaluationPage.expectQuickInputOpened();

    // Quick-input offers exactly Product + New cost price
    await expect(CostRevaluationPage.quickInputField('M_Product_ID')).toBeVisible();
    await expect(CostRevaluationPage.quickInputField('NewCostPrice')).toBeVisible();

    await CostRevaluationPage.addLine(productCode, '15');

    // Exactly one line exists (assert via WebAPI, language-independent)
    const rows = await CostRevaluationPage.getLines(recordId);
    console.log('[quick-input] line count=' + rows.length);
    expect(rows.length).toBe(1);
    const line = rows[0].fieldsByName;
    console.log(
      `[quick-input] CurrentCostPrice=${JSON.stringify(line.CurrentCostPrice.value)} CurrentQty=${JSON.stringify(line.CurrentQty.value)}` +
        ` NewCostPrice=${JSON.stringify(line.NewCostPrice.value)} DeltaAmt=${JSON.stringify(line.DeltaAmt && line.DeltaAmt.value)}`
    );
    expect(Number(line.NewCostPrice.value)).toBe(15);
    // Derived from the product's live cost: current cost 10 for the 10 on hand
    expect(Number(line.CurrentCostPrice.value)).toBe(10);
    expect(Number(line.CurrentQty.value)).toBe(10);

    await CostRevaluationPage.complete();

    // Completed (CO) and posted; the value difference 10 x (15 - 10) = 50 is on the line and in the accounting facts
    const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
    console.log('[quick-input] DocStatus=' + JSON.stringify(docStatus.value));
    expect(docStatus.value.key).toBe('CO');
    const facts = await CostRevaluationPage.waitUntilPosted(recordId);
    const factAmounts = facts.map((fact) => ({
      recordId: String(fact.Record_ID.value),
      account: fact.AccountConceptualName && fact.AccountConceptualName.value,
      dr: Number(fact.AmtAcctDr.value),
      cr: Number(fact.AmtAcctCr.value),
    }));
    console.log('[quick-input] accounting facts=' + JSON.stringify(factAmounts));
    expect(factAmounts.every((fact) => fact.recordId === String(recordId))).toBe(true);
    // Posted with the value difference 50: balanced, and the product asset account is debited by 50
    expect(factAmounts.reduce((sum, fact) => sum + fact.dr, 0)).toBe(50);
    expect(factAmounts.reduce((sum, fact) => sum + fact.cr, 0)).toBe(50);
    expect(factAmounts.filter((fact) => fact.account === 'P_Asset_Acct').map((fact) => fact.dr - fact.cr)).toEqual([50]);
    const completedRows = await CostRevaluationPage.getLines(recordId);
    console.log('[quick-input] completed DeltaAmt=' + JSON.stringify(completedRows[0].fieldsByName.DeltaAmt.value));
    expect(Number(completedRows[0].fieldsByName.DeltaAmt.value)).toBe(50);
    await CostRevaluationPage.showCompletedDocument();
  });

  // eslint-disable-next-line no-unused-vars
  test(`Evaluation Start Date follows the Accounting Date unless set by hand (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: Evaluation Start Date follows the Accounting Date');
    allure.description(`
## F1500: Costing — Evaluation Start Date follows the Accounting Date (UI)

On a new Kosten Neubewertung header the Evaluation Start Date defaults to the Accounting Date.
Changing the Accounting Date in the UI moves a defaulted Evaluation Start Date along with it.
An Evaluation Start Date the user set by hand is NOT overwritten by a later Accounting Date change.
    `);

    const md = await createMasterdata(language);
    const recordId = await loginAndCreateHeader(md);
    console.log(`[dates] header record ${recordId}`);

    // Derive the dates from the server-defaulted posting date, not from the test runner's clock.
    const initialDateAcct = String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct')).value).substring(0, 10);
    const [y, m, d] = initialDateAcct.split('-');
    // Stay inside the header's month (open period); days that differ from the initial Accounting Date.
    const otherDay = (day) => `${y}-${m}-${String(day).padStart(2, '0')}`;
    const dateAcct1 = otherDay(Number(d) === 15 ? 14 : 15);
    const manualEvalStart = otherDay(Number(d) === 5 ? 6 : 5);
    const dateAcct2 = otherDay(Number(d) === 20 ? 21 : 20);

    await test.step(`Change Accounting Date to ${dateAcct1} -> Evaluation Start Date follows`, async () => {
      await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', dateAcct1);
      const dateAcct = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct');
      const evalStart = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate');
      console.log(`[dates] DateAcct=${JSON.stringify(dateAcct.value)} EvaluationStartDate=${JSON.stringify(evalStart.value)}`);
      expect(String(dateAcct.value)).toContain(dateAcct1);
      expect(String(evalStart.value)).toContain(dateAcct1);
      await expect(CostRevaluationPage.headerDateInput('EvaluationStartDate')).toHaveValue(
        await CostRevaluationPage.headerDateInput('DateAcct').inputValue()
      );
      await CostRevaluationPage.showHeaderDates();
    });

    await test.step(`Set Evaluation Start Date by hand to ${manualEvalStart}`, async () => {
      await CostRevaluationPage.typeHeaderDate(recordId, 'EvaluationStartDate', manualEvalStart);
      const evalStart = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate');
      console.log(`[dates] manual EvaluationStartDate=${JSON.stringify(evalStart.value)}`);
      expect(String(evalStart.value)).toContain(manualEvalStart);
    });

    await test.step(`Change Accounting Date to ${dateAcct2} -> hand-set Evaluation Start Date is kept`, async () => {
      await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', dateAcct2);
      const dateAcct = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct');
      const evalStart = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate');
      console.log(`[dates] DateAcct=${JSON.stringify(dateAcct.value)} EvaluationStartDate=${JSON.stringify(evalStart.value)}`);
      expect(String(dateAcct.value)).toContain(dateAcct2);
      expect(String(evalStart.value)).toContain(manualEvalStart);
      await CostRevaluationPage.showHeaderDates();
    });
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
    await CostRevaluationPage.expectQuickInputOpened();

    await test.step('Picker offers a stocked product that has a current cost', async () => {
      await CostRevaluationPage.expectProductOffered(md.products.PHAS.productCode);
    });

    await test.step('Picker offers a stocked product with NO cost record (costless)', async () => {
      await CostRevaluationPage.expectProductOffered(md.products.PSEED.productCode);
    });

    await test.step('Picker excludes a non-stocked (Service) product', async () => {
      await CostRevaluationPage.expectProductNotOffered(md.products.PSVC.productCode);
    });

    console.log('[picker] stocked (has-cost + costless) offered; non-stocked excluded');
  });

  // eslint-disable-next-line no-unused-vars
  test(`Seed-cost path: stocked product with no cost record, with provisional hint (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Quick-input: seed a cost for a stocked product with no cost record');
    allure.description(`
## F1500: Costing — seed-cost path (stocked product with no cost record)

Picks a STOCKED product that has NO M_Cost row and quick-inputs a fractional New cost price (12.35).
Adding the line seeds the missing cost row at qty 0; completing the document sets the product's current
cost price to the entered price, without any accounting facts (nothing on hand, so no value difference).
The header uses the moving-average cost element (Bestellpreis Durchschnitt), under which the New cost price
field's provisional-price hint applies (provisional until the first goods receipt).
    `);

    const md = await createMasterdata(language);
    const seedProductCode = md.products.PSEED.productCode;
    const seedProductId = md.products.PSEED.id;

    const recordId = await loginAndCreateHeader(md);
    console.log(`[seed] header record ${recordId}`);

    // Moving-average costing: the cost element is chosen on the header while it has no lines.
    const averagePOCostElementId = await getCostElementId(page, md.products.PHAS.id, MOVING_AVERAGE_PO_COST_ELEMENT_NAME);
    await CostRevaluationPage.selectCostElement(averagePOCostElementId);
    const costElement = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'M_CostElement_ID');
    console.log('[seed] M_CostElement_ID=' + JSON.stringify(costElement.value));
    expect(String(costElement.value.key)).toBe(averagePOCostElementId);

    // Precondition: the product really has no cost record yet.
    expect(await getProductCosts(page, seedProductId)).toEqual([]);

    await CostRevaluationPage.expectQuickInputOpened();

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

    // A fractional price: the quick-input must submit it as typed (not blocked by the number input's step).
    await test.step(`Enter New cost price ${SEED_COST_PRICE} + add the seed line`, async () => {
      await CostRevaluationPage.submitLine(SEED_COST_PRICE);
    });

    // Adding the line seeded the missing cost row (qty 0) and created the line with NewCostPrice = typed value.
    const rows = await CostRevaluationPage.getLines(recordId);
    console.log(
      '[seed] lines=' +
        JSON.stringify(rows.map((r) => ({ NewCostPrice: r.fieldsByName.NewCostPrice.value, CurrentQty: r.fieldsByName.CurrentQty.value })))
    );
    expect(rows.length).toBe(1);
    expect(Number(rows[0].fieldsByName.NewCostPrice.value)).toBe(SEED_COST_PRICE);
    expect(Number(rows[0].fieldsByName.CurrentQty.value)).toBe(0);
    const seededCosts = await getProductCosts(page, seedProductId);
    console.log('[seed] cost rows after adding the line=' + JSON.stringify(seededCosts));
    expect(seededCosts.length).toBeGreaterThan(0);

    await CostRevaluationPage.complete();
    const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
    console.log('[seed] DocStatus=' + JSON.stringify(docStatus.value));
    expect(docStatus.value.key).toBe('CO');

    // End result: the product's moving-average current cost price is now the entered price.
    // Posting the document sets it (in the same transaction as the document's accounting facts), so once it shows,
    // the document is posted.
    await expect
      .poll(
        async () => {
          const costsAfterComplete = await getProductCosts(page, seedProductId);
          console.log('[seed] cost rows after Complete=' + JSON.stringify(costsAfterComplete));
          return costsAfterComplete
            .filter((c) => c.costElementId === averagePOCostElementId)
            .map((c) => c.currentCostPrice);
        },
        { message: 'the posted document sets the moving-average current cost price', timeout: 30000 }
      )
      .toEqual([SEED_COST_PRICE]);

    // Nothing on hand, so no value difference: the posted document booked no accounting facts.
    // The per-product test above is the positive control that the same read returns the facts of a posted revaluation.
    await CostRevaluationPage.expectNoAccountingFacts(recordId);
    await CostRevaluationPage.showCompletedDocument();
  });
});
