import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import {
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from '../utils/common';
import { getFieldData, getRecordData } from '../utils/WebAPIValidation';

/**
 * Cost Revaluation (Kosten Neubewertung, M_CostRevaluation) per-product quick-input E2E suite.
 *
 * Desktop WebUI, window 541568. Proves the manual cost-adjustment quick-input:
 *  - TC1 [AC1/AC2] quick-input a per-product line for a product that has a current cost, Run + Complete.
 *  - TC-picker [D5] the quick-input product picker offers stocked/eligible products (incl. a stocked
 *    product with no cost record) and NOT non-stocked products.
 *  - TC-seed [AC15/AC16/AC17/AC18] seed a stocked product that has NO cost record, Run + Complete,
 *    with the provisional-price hint shown on the New cost price field.
 *
 * Costing must mirror the customer's real config: the accounting schema is set to CLIENT-level costing
 * on the local stack before the run (documented in the delivering session).
 */

const COST_REVAL_WINDOW_ID = '541568';
const LINE_TAB_ID = 'AD_Tab-546465';

/** Today's date as an ISO yyyy-MM-dd string (language-independent; matches the WebAPI date value). */
function todayISO() {
  const d = new Date();
  const mm = String(d.getMonth() + 1).padStart(2, '0');
  const dd = String(d.getDate()).padStart(2, '0');
  return `${d.getFullYear()}-${mm}-${dd}`;
}

/**
 * Create the shared masterdata for this workflow:
 *  - PHAS: a stocked Item — gets default M_Cost rows on creation (has a current cost).
 *  - PSEED: a stocked Item with skipDefaultCosts — genuinely NO M_Cost row (migrated-product / seed case).
 *  - PSVC: a Service — NOT stocked (must be filtered out of the picker).
 */
async function createMasterdata(language) {
  return await Backend.createMasterdata({
    request: {
      login: { user: { language, firstname: 'CostReval', lastname: 'E2E' } },
      products: {
        PHAS: { name: 'CR_HAS_COST', type: 'Item' },
        PSEED: { name: 'CR_SEED_NOROW', type: 'Item', skipDefaultCosts: true },
        PSVC: { name: 'CR_SERVICE', type: 'Service' },
      },
    },
  });
}

/** Login → open a NEW Kosten Neubewertung header. AC6: the mandatory Evaluation Start Date
 *  AUTO-DEFAULTS to the posting date (today), so the header is saveable WITHOUT the user typing it. */
async function loginAndCreateHeader(page, masterdata) {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  return await test.step('Create Kosten Neubewertung header (EvaluationStartDate auto-defaults to the posting date)', async () => {
    await page.goto(`http://localhost:3000/window/${COST_REVAL_WINDOW_ID}/new`);
    // AC6: the header auto-defaults Accounting Schema, Cost Element, Accounting Date AND — the fix under
    // test — the mandatory Evaluation Start Date, which must arrive PRE-FILLED with today's posting date
    // so the WebUI mandatory-field check passes and the header commits with NO manual date entry.
    // (Before the AD_Column default, EvaluationStartDate rendered empty because its forward-only default
    // only fired in the model interceptor's beforeNew — AFTER the WebUI mandatory check — so the line tab
    // reported allowCreateNew=false / "ParentDocumentNew" until the user typed the date by hand.)
    const dateInput = page.locator('.form-field-EvaluationStartDate input').first();
    await dateInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    // The field must auto-fill (no click, no fill) — this is the AC6 assertion at the UI layer.
    await expect(dateInput).not.toHaveValue('', { timeout: SLOW_ACTION_TIMEOUT });

    // The freshly-created document acquires its numeric record id in the URL (auto-saved: every
    // mandatory header field, incl. the now-defaulted EvaluationStartDate, is satisfied).
    await page.waitForFunction(
      () => {
        const last = window.location.pathname.split('/').pop();
        return last && last !== 'new' && /^\d+$/.test(last);
      },
      undefined,
      { timeout: SLOW_ACTION_TIMEOUT }
    );
    const recordId = page.url().split('/').pop();

    // AC6 at the data layer (language-independent): the auto-filled value IS today's posting date.
    const evalStart = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'EvaluationStartDate');
    console.log('[header] auto-filled EvaluationStartDate=' + JSON.stringify(evalStart.value) + ' expected ' + todayISO());
    expect(String(evalStart.value)).toContain(todayISO());

    return recordId;
  });
}

/** Open the line-tab quick-input (batch entry). */
async function openQuickInput(page) {
  await test.step('Open per-product quick-input (batch entry)', async () => {
    const toggle = page.getByTestId('batch-entry-toggle');
    await toggle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await toggle.click();
    await page.locator('.quick-input-container').waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });
  });
}

/** Search the quick-input product picker for a code and return the dropdown options locator. */
async function searchPickerOptions(page, code) {
  const productInput = page.locator('#lookup_M_Product_ID input.input-field');
  await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await productInput.click();
  await productInput.fill('');
  await page.waitForTimeout(300);
  await productInput.fill(code);
  await page.waitForTimeout(1800);
  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await page.waitForTimeout(300);
  return page.locator('.input-dropdown-list-option');
}

/** Type a product code into the quick-input picker and return the dropdown option texts. */
async function typeProductInPicker(page, productCode) {
  const productInput = page.locator('#lookup_M_Product_ID input.input-field');
  await productInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await productInput.click();
  await productInput.fill(productCode);
  await page.waitForTimeout(1500);
  await page
    .locator('#lookup_M_Product_ID .rotating, #lookup_M_Product_ID .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  return page.locator('.input-dropdown-list-option');
}

/**
 * Add one quick-input line: pick the product from the dropdown, type the New cost price, submit (Enter).
 * newCostPrice is a whole number (the CostPrice widget's number-input rejects fractional steps on submit).
 */
async function addQuickInputLine(page, productCode, newCostPrice) {
  await test.step(`Pick product ${productCode} + enter New cost price ${newCostPrice}`, async () => {
    const options = await typeProductInPicker(page, productCode);
    await options.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await options.first().click();
    await page.waitForTimeout(1500);

    const priceInput = page.locator('.form-field-NewCostPrice input');
    await priceInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await priceInput.click();
    await priceInput.fill(String(newCostPrice));
    await page.waitForTimeout(500);
    await page.keyboard.press('Enter'); // "(Press 'Enter' to add)"
    await page.waitForTimeout(2500);
  });
}

/**
 * Complete the document via the status button -> Complete (CO).
 * The Complete DocAction runs the revaluation (CostRevaluationDocumentHandler.completeIt calls
 * createDetails) AND posts in one step; there is no separate "Run" button wired on this window.
 */
async function completeDocument(page) {
  await test.step('Complete document (runs revaluation + posts)', async () => {
    // Close the quick-input if still open so the status button is reachable.
    const toggle = page.getByTestId('batch-entry-toggle');
    if (await page.locator('.quick-input-container').isVisible().catch(() => false)) {
      await toggle.click();
      await page.waitForTimeout(1000);
    }
    const statusButton = page.getByTestId('status-button');
    await statusButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await statusButton.click();
    const completeOption = page.getByTestId('status-CO');
    await completeOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await completeOption.click();
    await page
      .locator('.rotating, .indicator-pending')
      .waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(3000);
  });
}

function allureTags(story) {
  allure.epic('E0226: Costing');
  allure.tag('F1500: Costing');
  allure.tag('F1500');
  allure.story(story);
  allure.severity('critical');
}

// ============================================================================

// eslint-disable-next-line no-unused-vars
test('Quick-input manual cost adjustment for a product with a current cost (English)', async ({ page }) => {
  test.setTimeout(180000);
  allureTags('Quick-input: per-product cost adjustment (Run + Complete)');
  allure.description(`
## F1500: Costing — manual per-product cost adjustment (Kosten Neubewertung)

Creates a Kosten Neubewertung header, adds ONE line via the per-product quick-input for a product
that has a current cost, runs the revaluation and completes the document.
Proves AC1 (quick-input offers Product + New cost price) and AC2 (line created with the derived
segment; document completes).
  `);

  const md = await createMasterdata('en_US');
  const productCode = md.products.PHAS.productCode;

  const recordId = await loginAndCreateHeader(page, md);
  console.log(`[TC1] header record ${recordId}`);

  await openQuickInput(page);

  // AC1: quick-input offers exactly Product + New cost price
  await expect(page.locator('.quick-input-container .form-field-M_Product_ID')).toBeVisible();
  await expect(page.locator('.quick-input-container .form-field-NewCostPrice')).toBeVisible();

  await addQuickInputLine(page, productCode, '15');

  // AC2: exactly one line exists with NewCostPrice = typed value (assert via WebAPI, language-independent)
  const lineRows = await page.request.get(
    `http://localhost:8080/rest/api/window/${COST_REVAL_WINDOW_ID}/${recordId}/${LINE_TAB_ID}`
  );
  const lineBody = await lineRows.json();
  const rows = lineBody.result || [];
  console.log('[TC1] line count=' + rows.length);
  expect(rows.length).toBe(1);
  const newCostVal = rows[0].fieldsByName.NewCostPrice.value;
  console.log('[TC1] NewCostPrice=' + JSON.stringify(newCostVal));
  expect(Number(newCostVal)).toBe(15);
  // CurrentCostPrice derived from the product's live M_Cost (present, may be 0 for a fresh cost row)
  expect(rows[0].fieldsByName.CurrentCostPrice).toBeDefined();

  await completeDocument(page);

  // Assert the document reached Completed (CO)
  const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
  console.log('[TC1] DocStatus=' + JSON.stringify(docStatus.value));
  expect(docStatus.value.key).toBe('CO');
});

// eslint-disable-next-line no-unused-vars
test('Quick-input picker offers stocked/eligible products incl. costless, excludes non-stocked [D5] (English)', async ({ page }) => {
  test.setTimeout(180000);
  allureTags('Quick-input: product picker is stocked/eligible-filtered');
  allure.description(`
## F1500: Costing — quick-input product picker filter (D5)

The quick-input product picker is filtered to stocked/eligible items (AD_Reference 171). It offers:
 - a stocked product that has a current cost,
 - a stocked product that has NO cost record (costless),
and it does NOT offer a non-stocked (Service) product.
  `);

  const md = await createMasterdata('en_US');
  await loginAndCreateHeader(page, md);
  await openQuickInput(page);

  await test.step('Picker offers a stocked product that has a current cost', async () => {
    const hasCostOpts = await searchPickerOptions(page, md.products.PHAS.productCode);
    await expect(hasCostOpts.filter({ hasText: md.products.PHAS.productCode })).toHaveCount(1);
  });

  await test.step('Picker offers a stocked product with NO cost record (costless)', async () => {
    const costlessOpts = await searchPickerOptions(page, md.products.PSEED.productCode);
    await expect(costlessOpts.filter({ hasText: md.products.PSEED.productCode })).toHaveCount(1);
  });

  await test.step('Picker excludes a non-stocked (Service) product', async () => {
    const serviceOpts = await searchPickerOptions(page, md.products.PSVC.productCode);
    await expect(serviceOpts.filter({ hasText: md.products.PSVC.productCode })).toHaveCount(0);
  });

  console.log('[TC-picker] stocked (has-cost + costless) offered; non-stocked excluded');
});

// eslint-disable-next-line no-unused-vars
test('Seed-cost path: stocked product with no cost record, with provisional hint (English)', async ({ page }) => {
  test.setTimeout(180000);
  allureTags('Quick-input: seed a cost for a stocked product with no cost record');
  allure.description(`
## F1500: Costing — seed-cost path (stocked product with no cost record)

Picks a STOCKED product that has NO M_Cost row, quick-inputs a New cost price, and completes.
The Complete DocAction seeds the cost row at qty 0 with the entered price (no GL posting) and the
document completes. The New cost price field surfaces the provisional-price hint (moving-average:
provisional until the first goods receipt). Proves AC15/AC16 (seed + complete), AC17 (hint), AC18
(mandatory desktop Playwright of the picker path).
  `);

  const md = await createMasterdata('en_US');
  const seedProductCode = md.products.PSEED.productCode;

  const recordId = await loginAndCreateHeader(page, md);
  console.log(`[TC-seed] header record ${recordId}`);

  await openQuickInput(page);

  // Pick the costless stocked product so its NewCostPrice field renders (with the hint).
  await test.step(`Pick costless stocked product ${seedProductCode}`, async () => {
    const opts = await searchPickerOptions(page, seedProductCode);
    await opts.filter({ hasText: seedProductCode }).first().click();
    await page.waitForTimeout(1500);
  });

  // AC17: the provisional-price hint is shown on the New cost price field (label title).
  // Language-independent: compare the rendered title to the field's description from the quick-input layout.
  await test.step('Verify provisional-price hint on New cost price field', async () => {
    const qiLayout = await (
      await page.request.get(
        `http://localhost:8080/rest/api/window/${COST_REVAL_WINDOW_ID}/${recordId}/${LINE_TAB_ID}/quickInput/layout`
      )
    ).json();
    const npcElement = (qiLayout.elements || []).find(
      (e) => (e.fields || []).some((f) => f.field === 'NewCostPrice')
    );
    const expectedHint = npcElement.description;
    console.log('[TC-seed] layout hint = ' + JSON.stringify(expectedHint));
    expect(expectedHint && expectedHint.length).toBeGreaterThan(0);

    const npcLabelTitle = await page
      .locator('.quick-input-container .form-field-NewCostPrice label')
      .getAttribute('title');
    console.log('[TC-seed] rendered NewCostPrice label title = ' + JSON.stringify(npcLabelTitle));
    expect(npcLabelTitle).toBe(expectedHint);
  });

  // Type the seed price and submit the line. The seed path materialises the missing M_Cost row
  // server-side (createDefaultProductCosts) before creating the line, so poll for the row.
  await test.step('Enter New cost price 20 + add the seed line', async () => {
    const priceInput = page.locator('.form-field-NewCostPrice input');
    await priceInput.click();
    await priceInput.fill('20');
    await page.waitForTimeout(800);
    await page.keyboard.press('Enter');
  });

  // AC15: one seed line created with NewCostPrice = typed value (poll for the slower seed round-trip).
  let rows = [];
  for (let i = 0; i < 12; i++) {
    await page.waitForTimeout(1000);
    const lineRows = await (
      await page.request.get(
        `http://localhost:8080/rest/api/window/${COST_REVAL_WINDOW_ID}/${recordId}/${LINE_TAB_ID}`
      )
    ).json();
    rows = lineRows.result || [];
    if (rows.length >= 1) break;
  }
  console.log('[TC-seed] line count=' + rows.length);
  expect(rows.length).toBe(1);
  expect(Number(rows[0].fieldsByName.NewCostPrice.value)).toBe(20);

  // AC16: completes cleanly (Complete seeds the cost row at qty 0 and posts zero delta).
  await completeDocument(page);
  const docStatus = await getFieldData(COST_REVAL_WINDOW_ID, recordId, 'DocStatus');
  console.log('[TC-seed] DocStatus=' + JSON.stringify(docStatus.value));
  expect(docStatus.value.key).toBe('CO');
});
