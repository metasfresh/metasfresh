import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { COST_REVAL_WINDOW_ID, CostRevaluationPage } from '../utils/pages/CostRevaluationPage';
import { PRODUCT_COST_M_COST_TAB_ID, PRODUCT_COST_WINDOW_ID } from '../utils/pages/ProductCostPage';
import { getFieldData, getRecordData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation) per-product quick-input E2E suite.
 *
 * Desktop WebUI, window 541568. Proves the window and its manual cost-adjustment quick-input:
 *  - quick-input a per-product line for a stocked product that has a current cost: the line shows the current
 *    values and the value difference, which follows an edit of the line's new cost price; the line fields' hints;
 *    Complete.
 *  - a new header presets the cost element of the accounting schema's costing method, offers the processes by
 *    their names, and keeps the Evaluation Start Date equal to the Accounting Date (read-only for Manual).
 *  - the quick-input product picker offers stocked/eligible products (incl. a stocked product with no
 *    cost record) and NOT non-stocked products.
 *  - seed a cost for a stocked product that has NO cost record (seeded when the line is added), with the
 *    provisional-price hint shown on the New cost price field.
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

/** Accounting schema (Buchführungs-Schema) and cost element (Kostenart) windows, read for the expected preset. */
const ACCT_SCHEMA_WINDOW_ID = '125';
const COST_ELEMENT_WINDOW_ID = '343';

/** @returns {Promise<{key: string, caption: string}[]>} the header's Cost Element dropdown options */
async function getCostElementOptions(page, recordId) {
  const response = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${COST_REVAL_WINDOW_ID}/${recordId}/field/M_CostElement_ID/dropdown`
  );
  expect(response.status()).toBe(200);
  return ((await response.json()).values || []).map((v) => ({ key: String(v.key), caption: v.caption }));
}

function allureTags(story) {
  allure.epic('E0226: Costing');
  allure.tag('F1500: Costing');
  allure.tag('F1500');
  allure.story(story);
  allure.severity('critical');
}

// ============================================================================

// Expected texts are read from the WebAPI layout in the login language; the exact translations are checked in
// cost-revaluation-translations.spec.js.
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

A stocked product (10 on hand) is first given a current cost price of 10 with one Kosten Neubewertung (completed
and posted). A second Kosten Neubewertung then adds ONE line via the per-product quick-input with New cost price 15.
Verifies the quick-input offers Product + New cost price, the line shows the current cost 10, the quantity on hand 10
and the value difference 10 x (15 - 10) = 50; editing the line's New cost price to 16 makes the value difference
10 x (16 - 10) = 60. The line values (current cost price, current quantity, value difference) share one hint, shown
on the grid columns and in the line's single-row view; captions and hints are the window layout's, in the login
language. The action menu offers the revaluation process by its name. The document is completed with these values.
    `);

    const md = await createStockedMasterdata(language);
    const productCode = md.products.PSTK.productCode;

    await test.step('Setup: stock of 10 with a current cost price of 10 (first Kosten Neubewertung, posted)', async () => {
      const setupRecordId = await loginAndCreateHeader(md);
      await CostRevaluationPage.expectQuickInputOpened();
      await CostRevaluationPage.addLine(productCode, '10');
      const setupLine = (await CostRevaluationPage.getLines(setupRecordId))[0].fieldsByName;
      // A value difference is booked, so the posting writes accounting facts the wait below can see.
      expect(
        Number(setupLine.CurrentCostPrice.value),
        'precondition: the fresh product\'s default cost price differs from 10, so the setup revaluation books a difference'
      ).not.toBe(10);
      await CostRevaluationPage.complete();
      const setupDocStatus = await getFieldData(COST_REVAL_WINDOW_ID, setupRecordId, 'DocStatus');
      expect(setupDocStatus.value.key).toBe('CO');
      // The current cost price changes only when the revaluation is posted.
      await CostRevaluationPage.waitUntilPosted(setupRecordId);
    });

    const recordId = await CostRevaluationPage.createHeader();
    await CostRevaluationPage.expectQuickInputOpened();

    // Quick-input offers exactly Product + New cost price
    await expect(CostRevaluationPage.quickInputField('M_Product_ID')).toBeVisible();
    await expect(CostRevaluationPage.quickInputField('NewCostPrice')).toBeVisible();

    await CostRevaluationPage.addLine(productCode, '15');

    // Exactly one line exists (assert via WebAPI, language-independent)
    const rows = await CostRevaluationPage.getLines(recordId);
    expect(rows.length).toBe(1);
    const rowId = String(rows[0].rowId);
    const line = rows[0].fieldsByName;
    expect(Number(line.NewCostPrice.value)).toBe(15);
    // Derived from the product's live cost: current cost 10 for the 10 on hand
    expect(Number(line.CurrentCostPrice.value)).toBe(10);
    expect(Number(line.CurrentQty.value)).toBe(10);
    // The value difference is the line's own qty x (new - current) right away: 10 x (15 - 10)
    expect(Number(line.DeltaAmt.value)).toBe(50);

    // The window layout's texts in the login language: the line values (current cost price, current quantity,
    // value difference) share one hint.
    const lineLayout = await CostRevaluationPage.lineLayoutTexts();
    const lineValuesHint = lineLayout.grid.DeltaAmt.description;
    expect(lineValuesHint, 'the line values have a hint').toBeTruthy();
    expect(lineLayout.grid.CurrentCostPrice.description).toBe(lineValuesHint);
    for (const columnName of ['CurrentCostPrice', 'CurrentQty', 'DeltaAmt']) {
      expect(lineLayout.singleRow[columnName].description, columnName).toBe(lineValuesHint);
    }

    await test.step('The line grid shows the value difference and the hint on the line values', async () => {
      await expect(CostRevaluationPage.lineCell(rowId, 'DeltaAmt')).toBeVisible();
      const deltaAmtHeader = await CostRevaluationPage.lineColumnHeader('DeltaAmt');
      expect(deltaAmtHeader.caption).toBeTruthy();
      expect(deltaAmtHeader.caption).toBe(lineLayout.grid.DeltaAmt.caption);
      expect(deltaAmtHeader.hint).toBe(lineValuesHint);
      expect((await CostRevaluationPage.lineColumnHeader('CurrentCostPrice')).hint).toBe(lineValuesHint);
    });

    await CostRevaluationPage.editLineNewCostPrice(rowId, '16');
    const editedLine = (await CostRevaluationPage.getLines(recordId))[0].fieldsByName;
    expect(Number(editedLine.NewCostPrice.value)).toBe(16);
    // Follows the edit at once: 10 x (16 - 10)
    expect(Number(editedLine.DeltaAmt.value)).toBe(60);

    await test.step('The line\'s single-row view shows the hint on the line values', async () => {
      await CostRevaluationPage.openLine(rowId);
      for (const columnName of ['CurrentCostPrice', 'CurrentQty', 'DeltaAmt']) {
        expect((await CostRevaluationPage.openLineFieldLabel(columnName)).hint, columnName).toBe(lineValuesHint);
      }
      expect((await CostRevaluationPage.openLineFieldLabel('DeltaAmt')).caption).toBe(lineLayout.singleRow.DeltaAmt.caption);
      await CostRevaluationPage.closeLine();
    });

    await test.step('With a line, the action menu offers the revaluation process by its name', async () => {
      const expectedCaption = (await CostRevaluationPage.actionCaptionsFromWebAPI(recordId)).M_CostRevaluation_Run;
      expect(expectedCaption, 'the revaluation process has a name').toBeTruthy();
      const captions = await CostRevaluationPage.actionCaptions(['M_CostRevaluation_Run']);
      expect(captions.M_CostRevaluation_Run).toBe(expectedCaption);
    });

    await CostRevaluationPage.complete();

    // Completed (CO) with the line's values: 10 on hand at 10, new price 16, value difference 60
    const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
    expect(docStatus.value.key).toBe('CO');
    const completedLine = (await CostRevaluationPage.getLines(recordId))[0].fieldsByName;
    expect(Number(completedLine.CurrentCostPrice.value)).toBe(10);
    expect(Number(completedLine.CurrentQty.value)).toBe(10);
    expect(Number(completedLine.DeltaAmt.value)).toBe(60);
    await CostRevaluationPage.showCompletedDocument();
  });

  // eslint-disable-next-line no-unused-vars
  test(`Evaluation Start Date always equals the Accounting Date and is read-only (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: Evaluation Start Date equals the Accounting Date');
    allure.description(`
## F1500: Costing — Evaluation Start Date equals the Accounting Date (UI)

On a new (Manual) Kosten Neubewertung header the Evaluation Start Date is the Accounting Date and cannot be edited.
Changing the Accounting Date in the UI moves the Evaluation Start Date along with it, every time.
    `);

    const md = await createMasterdata(language);
    const recordId = await loginAndCreateHeader(md);

    await test.step('Evaluation Start Date is read-only', async () => {
      await expect(CostRevaluationPage.headerDateInput('EvaluationStartDate')).toBeDisabled();
    });

    // Derive the dates from the server-defaulted posting date, not from the test runner's clock.
    const initialDateAcct = String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct')).value).substring(0, 10);
    const [y, m, d] = initialDateAcct.split('-');
    // Stay inside the header's month (open period); days that differ from the initial Accounting Date and each other.
    const otherDay = (day) => `${y}-${m}-${String(day).padStart(2, '0')}`;
    const dateAcct1 = otherDay(Number(d) === 15 ? 14 : 15);
    const dateAcct2 = otherDay(Number(d) === 20 ? 21 : 20);

    for (const dateAcct of [dateAcct1, dateAcct2]) {
      await test.step(`Change Accounting Date to ${dateAcct} -> Evaluation Start Date follows`, async () => {
        await CostRevaluationPage.typeHeaderDate(recordId, 'DateAcct', dateAcct);
        expect(String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct')).value)).toContain(dateAcct);
        expect(String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate')).value)).toContain(dateAcct);
        await expect(CostRevaluationPage.headerDateInput('EvaluationStartDate')).toHaveValue(
          await CostRevaluationPage.headerDateInput('DateAcct').inputValue()
        );
        await expect(CostRevaluationPage.headerDateInput('EvaluationStartDate')).toBeDisabled();
        await CostRevaluationPage.showHeaderDates();
      });
    }
  });

  // eslint-disable-next-line no-unused-vars
  test(`A new header shows the preset cost element, the Manual source and the line creation process (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: preset cost element, Manual source, line creation process');
    allure.description(`
## F1500: Costing — a new Kosten Neubewertung header

Before anything is typed, a new header shows:
 - the Cost Element (Kostenart) preset to the active material cost element of the accounting schema's costing method,
 - the Revaluation Source Manual,
 - the line creation process in the action menu, by its name.
    `);

    const md = await createMasterdata(language);
    const recordId = await loginAndCreateHeader(md);

    await test.step('Cost element preset = the active material cost element of the schema\'s costing method', async () => {
      const acctSchemaId = String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'C_AcctSchema_ID')).value.key);
      const costingMethod = (await getFieldData(ACCT_SCHEMA_WINDOW_ID, acctSchemaId, 'CostingMethod')).value.key;
      const expected = [];
      for (const option of await getCostElementOptions(page, recordId)) {
        const element = (await getRecordData(COST_ELEMENT_WINDOW_ID, String(option.key))).fieldsByName;
        if (
          element.CostingMethod.value.key === costingMethod &&
          element.CostElementType.value.key === 'M' &&
          element.IsActive.value === true
        ) {
          expected.push(option);
        }
      }
      expect(expected, `exactly one active material cost element for costing method ${costingMethod}`).toHaveLength(1);
      const costElement = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'M_CostElement_ID');
      expect(costElement.value && String(costElement.value.key), 'the preset cost element').toBe(String(expected[0].key));
      await expect(page.locator('.form-field-M_CostElement_ID input').first()).toHaveValue(expected[0].caption);
    });

    await test.step('Revaluation Source shows Manual', async () => {
      const revaluationSource = (await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'RevaluationSource')).value;
      expect(revaluationSource.key).toBe('Manual');
      expect(revaluationSource.caption).toBeTruthy();
      expect(await CostRevaluationPage.revaluationSourceText()).toBe(revaluationSource.caption);
    });

    await test.step('The action menu offers the line creation process by its name', async () => {
      // "Run Revaluation" is offered only once the document has lines: checked in the quick-input test.
      const expectedCaption = (await CostRevaluationPage.actionCaptionsFromWebAPI(recordId)).M_CostRevaluation_CreateLines;
      expect(expectedCaption, 'the line creation process has a name').toBeTruthy();
      const captions = await CostRevaluationPage.actionCaptions(['M_CostRevaluation_CreateLines']);
      expect(captions.M_CostRevaluation_CreateLines).toBe(expectedCaption);
    });
  });

  // eslint-disable-next-line no-unused-vars
  test(`Switching the source back to Manual before save puts the Evaluation Start Date back to the Accounting Date (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Header: Evaluation Start Date follows the source switch to Manual');
    allure.description(`
## F1500: Costing — switching the Revaluation Source to Manual

On a new header switched to Copy from cost element, the Evaluation Start Date (the cut-off date) is editable and set
to another day. The header is not saved yet (the cost element to copy from is still empty). Switching the source back
to Manual sets the Evaluation Start Date to the Accounting Date again and makes it read-only.
    `);

    const md = await createMasterdata(language);
    const recordId = await loginAndCreateHeader(md);
    const dateAcct = String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DateAcct')).value).substring(0, 10);
    const [y, m, d] = dateAcct.split('-');
    const otherDay = `${y}-${m}-${Number(d) === 5 ? '06' : '05'}`;

    await test.step(`Copy from cost element: Evaluation Start Date editable, set to ${otherDay}`, async () => {
      await CostRevaluationPage.selectRevaluationSource('CopyFromCostElement');
      await expect(CostRevaluationPage.headerDateInput('EvaluationStartDate')).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
      await CostRevaluationPage.typeHeaderDate(recordId, 'EvaluationStartDate', otherDay);
      expect(String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate')).value)).toContain(otherDay);
    });

    await test.step(`Back to Manual: Evaluation Start Date = Accounting Date ${dateAcct}, read-only`, async () => {
      await CostRevaluationPage.selectRevaluationSource('Manual');
      const revaluationSource = (await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'RevaluationSource')).value;
      expect(revaluationSource.key).toBe('Manual');
      expect(await CostRevaluationPage.revaluationSourceText()).toBe(revaluationSource.caption);
      expect(String((await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate')).value)).toContain(dateAcct);
      await expect(CostRevaluationPage.headerDateInput('EvaluationStartDate')).toHaveValue(
        await CostRevaluationPage.headerDateInput('DateAcct').inputValue()
      );
      await expect(CostRevaluationPage.headerDateInput('EvaluationStartDate')).toBeDisabled({ timeout: SLOW_ACTION_TIMEOUT });
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

  });

  // eslint-disable-next-line no-unused-vars
  test(`Seed-cost path: stocked product with no cost record, with provisional hint (${label})`, async ({ page }) => {
    test.setTimeout(180000);
    allureTags('Quick-input: seed a cost for a stocked product with no cost record');
    allure.description(`
## F1500: Costing — seed-cost path (stocked product with no cost record)

Picks a STOCKED product that has NO M_Cost row and quick-inputs a fractional New cost price (12.35).
Adding the line seeds the missing cost row at qty 0, and the document completes. The header uses the
moving-average cost element (Bestellpreis Durchschnitt), under which the New cost price field shows the
provisional-price hint of the quick-input layout (not the field's generic description).
    `);

    const md = await createMasterdata(language);
    const seedProductCode = md.products.PSEED.productCode;
    const seedProductId = md.products.PSEED.id;

    const recordId = await loginAndCreateHeader(md);

    // Moving-average costing: the cost element is chosen on the header while it has no lines.
    const averagePOCostElementId = await getCostElementId(page, md.products.PHAS.id, MOVING_AVERAGE_PO_COST_ELEMENT_NAME);
    await CostRevaluationPage.selectCostElement(averagePOCostElementId);
    const costElement = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'M_CostElement_ID');
    expect(String(costElement.value.key)).toBe(averagePOCostElementId);

    // Precondition: the product really has no cost record yet.
    expect(await getProductCosts(page, seedProductId)).toEqual([]);

    await CostRevaluationPage.expectQuickInputOpened();

    // Pick the costless stocked product so its NewCostPrice field renders (with the hint).
    await test.step(`Pick costless stocked product ${seedProductCode}`, async () => {
      await CostRevaluationPage.pickProduct(seedProductCode);
    });

    // The provisional-price hint is shown on the New cost price field (label title).
    // The quick-input layout carries the hint as the field description; the label renders it as its title.
    await test.step('Verify provisional-price hint on New cost price field', async () => {
      const provisionalPriceHint = (await CostRevaluationPage.quickInputLayoutTexts(recordId)).NewCostPrice.description;
      expect(provisionalPriceHint, 'the quick-input New cost price has a hint').toBeTruthy();
      // A hint of its own, not the New cost price field's generic description shown on the line
      expect(provisionalPriceHint).not.toBe((await CostRevaluationPage.lineLayoutTexts()).singleRow.NewCostPrice.description);

      const npcLabelTitle = await CostRevaluationPage.hoverNewCostPriceHint();
      expect(npcLabelTitle).toBe(provisionalPriceHint);
    });

    // A fractional price: the quick-input must submit it as typed (not blocked by the number input's step).
    await test.step(`Enter New cost price ${SEED_COST_PRICE} + add the seed line`, async () => {
      await CostRevaluationPage.submitLine(SEED_COST_PRICE);
    });

    // Adding the line seeded the missing cost row (qty 0) and created the line with NewCostPrice = typed value.
    const rows = await CostRevaluationPage.getLines(recordId);
    expect(rows.length).toBe(1);
    expect(Number(rows[0].fieldsByName.NewCostPrice.value)).toBe(SEED_COST_PRICE);
    expect(Number(rows[0].fieldsByName.CurrentQty.value)).toBe(0);
    const seededCosts = await getProductCosts(page, seedProductId);
    expect(seededCosts.length).toBeGreaterThan(0);

    await CostRevaluationPage.complete();
    const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
    expect(docStatus.value.key).toBe('CO');

    await CostRevaluationPage.showCompletedDocument();
  });
});
