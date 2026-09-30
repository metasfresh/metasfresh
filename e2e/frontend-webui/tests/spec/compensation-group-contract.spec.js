import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getFieldData, getTabRows, waitForRecordSaved } from '../utils/WebAPIValidation';
import { openRelatedDocument } from '../utils/DocumentReferences';

/**
 * Compensation-group contract, created end to end through the WebUI.
 *
 * A contract of type "compensation group" adds a compensation group with its discount line(s)
 * to a sales order when the order is completed - provided the order matches the contract term
 * on the invoice partner (Bill_BPartner_ID), the order document type and the validity dates.
 *
 * The operator path covered here:
 *   1. Kompensationsgruppe Schema (540415): a schema with IsAdditive and a schema line that is
 *      limited to a product category (M_Product_Category_ID = "applies to" category).
 *   2. Kompensationsgruppen-Vertragseinstellungen (542194): settings pointing at the schema;
 *      the Belegarten tab lists the order doc type (search field limited to order doc types).
 *   3. Vertragsbedingungen (540113): conditions of type compensation group; the settings field
 *      is shown only for that type.
 *   4. Geschäftspartner action "Erzeuge Vertrag": a product-less contract term for the partner on
 *      those conditions, opened in Verträge (540359).
 *   5. Auftrag (143): a sales order for the partner; completing it adds the contract group and
 *      its discount line.
 *
 * Besides the flow, the spec asserts the window titles, tab names, field labels and field order
 * the operator sees. The spec runs as a de_DE user; every expected German caption below was read
 * from the application dictionary (AD_Window/AD_Tab/AD_Field/AD_Element translations, de_DE) of
 * the local database - they are the window-design contract of this feature, not guesses.
 */

const LANGUAGE = 'de_DE';

// Windows
const SCHEMA_WINDOW_ID = 540415;
const SETTINGS_WINDOW_ID = 542194;
const CONDITIONS_WINDOW_ID = 540113;
const CONTRACT_WINDOW_ID = 540359;
const BUSINESS_PARTNER_WINDOW_ID = 123;

// Business-partner action "Erzeuge Vertrag" and the partner's related-document link to its terms
const CREATE_CONTRACT_PROCESS = 'C_Flatrate_Term_Create_For_BPartners';
const BPARTNER_TO_CONTRACT_TERM_REFERENCE = 'reference-C_Flatrate_Term';

// Tabs (AD_Tab ids)
const SCHEMA_LINE_TAB_ID = 541042; // C_CompensationGroup_SchemaLine
const SETTINGS_DOCTYPE_TAB_ID = 549508; // C_CompensationGroup_ContractSettings_DocType
const ORDER_LINE_TAB = 'AD_Tab-187';

// Ref-list keys of C_Flatrate_Conditions.Type_Conditions
const TYPE_CONDITIONS_COMPENSATION_GROUP = 'CompensationGroup';
const TYPE_CONDITIONS_OTHER = 'Subscr';

// Conditions can only be completed with a completed contract transition. The seeded, completed
// transition "1 Jahr, autom. verlängern" (1 year) matches the one-year term created in step 4.
const TRANSITION_ONE_YEAR_ID = 1000003;

// Sales-order document type: Standardauftrag (DocBaseType SOO, DocSubType SO)
const DOCTYPE_STANDARD_ORDER_ID = 1000030;
const DOCTYPE_STANDARD_ORDER_NAME = 'Standardauftrag';

// Doc-type search probe: "Rechnung" matches both an order doc type ("Proforma Rechnung", SOO) and
// invoice doc types ("Ausgangsrechnung", ARI). The validation rule must offer only the former.
const DOCTYPE_SEARCH_PROBE = 'Rechnung';
const DOCTYPE_ORDER_MATCHING_PROBE = 'Proforma Rechnung';
const DOCTYPE_INVOICE_MATCHING_PROBE = 'Ausgangsrechnung';
// Names of all active order doc types (DocBaseType SOO / POO)
const ORDER_DOCTYPE_NAMES = [
  'Kostenvoranschlag', 'Abrufauftrag', 'Proforma Rechnung', 'Rahmenauftrag', 'Auftrag auf Kommission',
  'Angebot', 'Vorauskasseauftrag', 'Return Material Authorization', 'Standardauftrag',
  'Anfrage', 'Bestellvermittlung', 'Abrufbestellung', 'Rahmenbestellung', 'Bestellung', 'Lieferanten RMA',
];

// Expected German captions (de_DE), read from the AD
const DE = {
  schemaWindow: 'Kompensationsgruppe Schema',
  schemaLineTab: 'Kompensationszeilen',
  isAdditive: 'Additiv',
  schemaLineCategory: 'Gilt für Produkt Kategorie',
  schemaLineProduct: 'Produkt',
  schemaLineSeqNo: 'Reihenfolge',
  schemaLineDiscount: 'Gesamtauftragsrabatt %',

  settingsWindow: 'Kompensationsgruppen-Vertragseinstellungen',
  settingsDocTypeTab: 'Belegarten',
  settingsName: 'Name',
  settingsSchema: 'Kompensationsgruppe Schema',
  settingsDescription: 'Beschreibung',
  settingsDocType: 'Belegart',

  conditionsWindow: 'Vertragsbedingungen',
  conditionsSettings: 'Einstellungen für Kompensationsgruppen-Verträge',
};

// Business values
const GOODS_PRICE = 1000;
const DISCOUNT_PERCENT = 3;
const EXPECTED_DISCOUNT_AMOUNT = -(GOODS_PRICE * DISCOUNT_PERCENT) / 100; // -30

test.describe('Compensation-group contract — create through the WebUI and complete an order', () => {
  test('creates schema, settings, conditions and contract, then completing a sales order adds the discount group', async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.story('Compensation-group contract created through the WebUI adds the discount group on order completion');
    allure.severity('critical');
    test.setTimeout(10 * 60 * 1000);
    page.setDefaultTimeout(60 * 1000);

    const runId = Date.now();
    const schemaName = `CG schema ${runId}`;
    const settingsName = `CG settings ${runId}`;
    const conditionsName = `CG conditions ${runId}`;

    // ------------------------------------------------------------------
    // Master data: a customer, a goods product in its own category, and a
    // not-stocked discount product (the schema-line product val rule) in the same category.
    // ------------------------------------------------------------------
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        productCategories: { GOODS_CATEGORY: {} },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        products: {
          GOODS: { productCategory: 'GOODS_CATEGORY', prices: [{ price: GOODS_PRICE }] },
          DISCOUNT: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
        },
      },
    });
    const customer = masterdata.bpartners.CUSTOMER;
    const goods = masterdata.products.GOODS;
    const discount = masterdata.products.DISCOUNT;
    const goodsCategoryId = masterdata.productCategories.GOODS_CATEGORY.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // ------------------------------------------------------------------
    // 1. Kompensationsgruppe Schema (540415)
    // ------------------------------------------------------------------
    await test.step('1. Schema window: create an additive schema with a category-limited line', async () => {
      await openNewRecord(page, SCHEMA_WINDOW_ID);
      await expectWindowTitle(page, DE.schemaWindow);

      await fillText(page, 'Name', schemaName);
      const schemaId = await waitForNewRecordId(page, SCHEMA_WINDOW_ID);

      // IsAdditive: German label, placed in the flags group right after the other schema flags
      await expectLabel(page, 'IsAdditive', DE.isAdditive);
      await expectFieldOrder(page, ['IsActive', 'IsInheritPackingInstruction', 'IsAdditive']);
      await setCheckbox(page, 'IsAdditive');
      await waitForRecordSaved(SCHEMA_WINDOW_ID, schemaId, { maxRetries: 20, retryDelayMs: 500 });
      expect((await getFieldData(SCHEMA_WINDOW_ID, schemaId, 'IsAdditive')).value).toBe(true);
      await snap(page, '540415-schema-header');

      // Schema line tab
      await expectTabCaption(page, SCHEMA_LINE_TAB_ID, DE.schemaLineTab);
      const modal = await openNewIncludedRow(page, SCHEMA_LINE_TAB_ID);

      await expectLabel(modal, 'SeqNo', DE.schemaLineSeqNo);
      await expectLabel(modal, 'M_Product_Category_ID', DE.schemaLineCategory);
      await expectLabel(modal, 'M_Product_ID', DE.schemaLineProduct);
      await expectLabel(modal, 'CompleteOrderDiscount', DE.schemaLineDiscount);
      // the "applies to" category sits directly above the discount product
      await expectFieldOrder(modal, ['SeqNo', 'M_Product_Category_ID', 'M_Product_ID']);

      await selectListByKey(page, modal, 'M_Product_Category_ID', goodsCategoryId);
      await selectLookup(page, modal, 'M_Product_ID', discount.productCode);
      await fillNumber(page, modal, 'CompleteOrderDiscount', DISCOUNT_PERCENT);
      await snap(page, '540415-schema-line');
      await closeModal(page, modal);

      const lines = await getTabRows(SCHEMA_WINDOW_ID, schemaId, `AD_Tab-${SCHEMA_LINE_TAB_ID}`);
      expect(lines, 'exactly one schema line').toHaveLength(1);
      expect(lookupKey(lines[0].fieldsByName.M_Product_Category_ID.value)).toBe(String(goodsCategoryId));
      expect(lookupKey(lines[0].fieldsByName.M_Product_ID.value)).toBe(String(discount.id));
      expect(Number(lines[0].fieldsByName.CompleteOrderDiscount.value)).toBe(DISCOUNT_PERCENT);
    });

    // ------------------------------------------------------------------
    // 2. Kompensationsgruppen-Vertragseinstellungen (542194)
    // ------------------------------------------------------------------
    let settingsId;
    await test.step('2. Settings window: create settings for the schema and list the sales-order doc type', async () => {
      await openNewRecord(page, SETTINGS_WINDOW_ID);
      await expectWindowTitle(page, DE.settingsWindow);

      await expectLabel(page, 'Name', DE.settingsName);
      await expectLabel(page, 'C_CompensationGroup_Schema_ID', DE.settingsSchema);
      await expectLabel(page, 'Description', DE.settingsDescription);
      await expectFieldOrder(page, ['Name', 'C_CompensationGroup_Schema_ID', 'Description']);

      await fillText(page, 'Name', settingsName);
      settingsId = await waitForNewRecordId(page, SETTINGS_WINDOW_ID);
      await selectLookup(page, page, 'C_CompensationGroup_Schema_ID', schemaName);
      await fillText(page, 'Description', `Settings created by the WebUI test ${runId}`);
      await waitForRecordSaved(SETTINGS_WINDOW_ID, settingsId, { maxRetries: 20, retryDelayMs: 500 });
      await snap(page, '542194-settings-header');

      await expectTabCaption(page, SETTINGS_DOCTYPE_TAB_ID, DE.settingsDocTypeTab);
      const modal = await openNewIncludedRow(page, SETTINGS_DOCTYPE_TAB_ID);
      await expectLabel(modal, 'C_DocType_ID', DE.settingsDocType);

      // The doc-type search offers only order doc types.
      const offered = await searchLookupOptions(page, modal, 'C_DocType_ID', DOCTYPE_SEARCH_PROBE);
      console.log(`[INFO] doc types offered for "${DOCTYPE_SEARCH_PROBE}": ${offered.join(' | ')}`);
      expect(offered, 'the order doc type matching the probe is offered').toContain(DOCTYPE_ORDER_MATCHING_PROBE);
      expect(offered, 'an invoice doc type is not offered').not.toContain(DOCTYPE_INVOICE_MATCHING_PROBE);
      for (const name of offered) {
        expect(ORDER_DOCTYPE_NAMES, `offered doc type "${name}" must be an order doc type`).toContain(name);
      }
      await page.keyboard.press('Escape');

      await selectLookup(page, modal, 'C_DocType_ID', DOCTYPE_STANDARD_ORDER_NAME, { exact: true });
      await snap(page, '542194-belegarten-tab');
      await closeModal(page, modal);

      const docTypes = await getTabRows(SETTINGS_WINDOW_ID, settingsId, `AD_Tab-${SETTINGS_DOCTYPE_TAB_ID}`);
      expect(docTypes, 'exactly one doc type row').toHaveLength(1);
      expect(lookupKey(docTypes[0].fieldsByName.C_DocType_ID.value)).toBe(String(DOCTYPE_STANDARD_ORDER_ID));
    });

    // ------------------------------------------------------------------
    // 3. Vertragsbedingungen (540113)
    // ------------------------------------------------------------------
    let conditionsId;
    await test.step('3. Conditions window: type compensation group shows the settings field', async () => {
      await openNewRecord(page, CONDITIONS_WINDOW_ID);
      await expectWindowTitle(page, DE.conditionsWindow);

      await fillText(page, 'Name', conditionsName);
      conditionsId = await waitForNewRecordId(page, CONDITIONS_WINDOW_ID);

      // another contract type: the settings field stays hidden
      await selectListByKey(page, page, 'Type_Conditions', TYPE_CONDITIONS_OTHER);
      await expect(page.locator('.form-field-Type_Conditions input').first()).not.toHaveValue('');
      await expect(page.locator('.form-field-C_CompensationGroup_ContractSettings_ID')).toHaveCount(0);
      await snap(page, '540113-conditions-other-type-field-hidden');

      // compensation-group type: the settings field is shown, labelled, and directly after the type
      await selectListByKey(page, page, 'Type_Conditions', TYPE_CONDITIONS_COMPENSATION_GROUP);
      await expect(page.locator('.form-field-C_CompensationGroup_ContractSettings_ID')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expectLabel(page, 'C_CompensationGroup_ContractSettings_ID', DE.conditionsSettings);
      await expectFieldOrder(page, ['Name', 'Type_Conditions', 'C_CompensationGroup_ContractSettings_ID']);

      await selectListByKey(page, page, 'C_CompensationGroup_ContractSettings_ID', settingsId);
      expect(lookupKey((await getFieldData(CONDITIONS_WINDOW_ID, conditionsId, 'C_CompensationGroup_ContractSettings_ID')).value)).toBe(String(settingsId));
      await selectListByKey(page, page, 'C_Flatrate_Transition_ID', TRANSITION_ONE_YEAR_ID);
      await waitForRecordSaved(CONDITIONS_WINDOW_ID, conditionsId, { maxRetries: 20, retryDelayMs: 500 });
      await snap(page, '540113-conditions-compensation-group-field-shown');

      await completeDocument(page);
      await expectDocStatus(CONDITIONS_WINDOW_ID, conditionsId, 'CO');
    });

    // ------------------------------------------------------------------
    // 4. Contract term: "Erzeuge Vertrag" on the business partner, then Verträge (540359)
    // ------------------------------------------------------------------
    // A new partner has no C_Flatrate_Data yet, so the Verträge window cannot offer it as
    // Vertragspartner; the WebUI path for a new partner is the partner action "Erzeuge Vertrag"
    // (C_Flatrate_Term_Create_For_BPartners), which creates (and completes) the term.
    await test.step('4. Business partner: create the product-less contract term and complete it', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${customer.id}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1000);

      const today = new Date();
      const startDate = new Date(today.getFullYear(), today.getMonth(), 1);
      const modal = await openAction(page, CREATE_CONTRACT_PROCESS);
      await selectListByKey(page, modal, 'C_Flatrate_Conditions_ID', conditionsId);
      await fillDate(page, modal, 'StartDate', startDate);
      await expect(modal.locator('.form-field-IsComplete input[type="checkbox"]').first(), 'the term is completed by the action').toBeChecked();
      await snap(page, '123-bpartner-create-contract-parameters');
      await modal.getByTestId('process-modal-start-button').click();
      await modal.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.waitForTimeout(2000);

      // The partner now references its contract term; open it in the Verträge window.
      await openRelatedDocument({
        dataCy: BPARTNER_TO_CONTRACT_TERM_REFERENCE,
        stepName: 'Business partner - open the created contract term',
        maxRetries: 10,
        retryDelay: 2000,
        refreshOnRetry: true,
      });
      await page.waitForURL(new RegExp(`/window/${CONTRACT_WINDOW_ID}/\\d+`), { timeout: SLOW_ACTION_TIMEOUT });
      const termId = await waitForNewRecordId(page, CONTRACT_WINDOW_ID);

      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'Bill_BPartner_ID')).value)).toBe(String(customer.id));
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'C_Flatrate_Conditions_ID')).value)).toBe(String(conditionsId));
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'Type_Conditions')).value)).toBe(TYPE_CONDITIONS_COMPENSATION_GROUP);
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'M_Product_ID').catch(() => ({ value: null }))).value), 'product-less term').toBeNull();
      await expectDocStatus(CONTRACT_WINDOW_ID, termId, 'CO');
      await snap(page, '540359-contract-term');
    });

    // ------------------------------------------------------------------
    // 5. Sales order: completion adds the compensation group and its discount line
    // ------------------------------------------------------------------
    await test.step('5. Sales order: completing it adds the contract group with the discount line', async () => {
      await SalesOrderPage.goto();
      await SalesOrderPage.clickNew();
      const orderId = await SalesOrderPage.selectCustomer(customer.bpartnerCode);

      expect(lookupKey((await getFieldData(SALES_ORDER_WINDOW_ID, orderId, 'C_DocTypeTarget_ID')).value))
        .toBe(String(DOCTYPE_STANDARD_ORDER_ID));
      expect(lookupKey((await getFieldData(SALES_ORDER_WINDOW_ID, orderId, 'Bill_BPartner_ID')).value))
        .toBe(String(customer.id));

      await SalesOrderPage.addOrderLine({ product: goods.productCode, quantity: 1, recordId: orderId });
      await SalesOrderPage.complete();
      await expectDocStatus(SALES_ORDER_WINDOW_ID, orderId, 'CO');

      const lines = await getTabRows(SALES_ORDER_WINDOW_ID, orderId, ORDER_LINE_TAB);
      const describe = (l) => `${lookupKey(l.fieldsByName.M_Product_ID?.value)} net=${l.fieldsByName.LineNetAmt?.value}`;
      console.log(`[INFO] order lines after completion: ${lines.map(describe).join('; ')}`);
      expect(lines, 'goods line + discount line').toHaveLength(2);

      const goodsLine = lines.find((l) => lookupKey(l.fieldsByName.M_Product_ID.value) === String(goods.id));
      const discountLine = lines.find((l) => lookupKey(l.fieldsByName.M_Product_ID.value) === String(discount.id));
      expect(goodsLine, 'goods line').toBeTruthy();
      expect(discountLine, 'discount line added on completion').toBeTruthy();
      expect(Number(discountLine.fieldsByName.LineNetAmt.value)).toBeCloseTo(EXPECTED_DISCOUNT_AMOUNT, 2);

      // The UI shows both lines in the order-line grid, the discount line with the expected amount.
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}`);
      await SalesOrderPage.goToOrderLineTab();
      const discountRow = page.locator('table tbody tr')
        .filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: discount.productCode }) });
      await expect(discountRow).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(discountRow.locator('[data-cy="cell-LineNetAmt"]')).toContainText('-30,00');
      await snap(page, '143-sales-order-discount-group');
    });
  });
});

// ======================================================================
// Helpers
// ======================================================================

/** Extract the key of a lookup/list JSON value ({ key, caption }) or return the plain value as string. */
function lookupKey(value) {
  if (value === null || value === undefined) {
    return null;
  }
  return typeof value === 'object' ? String(value.key) : String(value);
}

/** Screenshot of the current viewport, attached to the report and kept in the test output folder. */
async function snap(page, name) {
  await page.waitForTimeout(500);
  const buffer = await page.screenshot({ fullPage: false });
  const file = test.info().outputPath(`${name}.png`);
  require('fs').writeFileSync(file, buffer);
  await test.info().attach(name, { body: buffer, contentType: 'image/png' });
  console.log(`[INFO] screenshot ${file}`);
}

async function openNewRecord(page, windowId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}/NEW`);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(1000);
}

async function waitForNewRecordId(page, windowId) {
  await page.waitForURL(new RegExp(`/window/${windowId}/\\d+`), { timeout: SLOW_ACTION_TIMEOUT });
  return page.url().split(`/window/${windowId}/`)[1].split(/[/?#]/)[0];
}

async function expectWindowTitle(page, caption) {
  await expect(page.locator('.header-breadcrumb')).toContainText(caption, { timeout: SLOW_ACTION_TIMEOUT });
}

async function expectTabCaption(page, tabId, caption) {
  await expect(page.getByTestId(`tab-AD_Tab-${tabId}`)).toHaveText(caption, { timeout: SLOW_ACTION_TIMEOUT });
}

async function expectLabel(scope, fieldName, caption) {
  const label = scope.locator(`.form-field-${fieldName} > label.form-control-label`).first();
  await expect(label, `label of ${fieldName}`).toHaveText(caption, { timeout: SLOW_ACTION_TIMEOUT });
}

/** Assert the given fields are rendered in this order (document order of their form groups). */
async function expectFieldOrder(scope, fieldNames) {
  const positions = [];
  for (const fieldName of fieldNames) {
    const group = scope.locator(`.form-field-${fieldName}`).first();
    await group.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    positions.push(await group.evaluate((el) => {
      const all = Array.from(document.querySelectorAll('.form-group'));
      return all.indexOf(el);
    }));
  }
  const sorted = [...positions].sort((a, b) => a - b);
  expect(positions, `field order ${fieldNames.join(' < ')}`).toEqual(sorted);
}

async function fillText(page, fieldName, value) {
  const input = page.locator(`.form-field-${fieldName} input[type="text"], .form-field-${fieldName} textarea`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.fill(value);
  await input.press('Tab');
  await page.waitForTimeout(800);
}

async function fillNumber(page, scope, fieldName, value) {
  const input = scope.locator(`.form-field-${fieldName} input`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.press('ControlOrMeta+a');
  await input.pressSequentially(String(value));
  await input.press('Tab');
  await page.waitForTimeout(800);
}

async function fillDate(page, scope, fieldName, date) {
  const dd = String(date.getDate()).padStart(2, '0');
  const mm = String(date.getMonth() + 1).padStart(2, '0');
  const text = `${dd}.${mm}.${date.getFullYear()}`;
  const input = scope.locator(`.form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.press('ControlOrMeta+a');
  await input.fill(text);
  await input.press('Tab');
  await page.waitForTimeout(800);
}

async function setCheckbox(page, fieldName) {
  const checkbox = page.locator(`.form-field-${fieldName} input[type="checkbox"]`).first();
  if (!(await checkbox.isChecked())) {
    await page.locator(`.form-field-${fieldName} label.input-checkbox`).first().click();
  }
  await expect(checkbox).toBeChecked();
  await page.waitForTimeout(800);
}

async function selectListByKey(page, scope, fieldName, key) {
  const input = scope.locator(`.form-field-${fieldName} input`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  const option = page.locator(`.input-dropdown-list [data-testid="option-${key}"]`).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await option.click();
  await page.locator('.input-dropdown-list').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await page.waitForTimeout(1000);
}

async function typeIntoLookup(page, scope, fieldName, searchText) {
  const input = scope.locator(`.form-field-${fieldName} input.input-field, .form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.fill('');
  await input.pressSequentially(searchText, { delay: 30 });
  await page.waitForTimeout(1500);
  const dropdown = page.locator('.input-dropdown-list');
  await dropdown.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return dropdown;
}

async function searchLookupOptions(page, scope, fieldName, searchText) {
  const dropdown = await typeIntoLookup(page, scope, fieldName, searchText);
  const texts = await dropdown.locator('.input-dropdown-list-option').allTextContents();
  return texts.map((t) => t.trim()).filter((t) => t.length > 0);
}

async function selectLookup(page, scope, fieldName, searchText, { exact = false } = {}) {
  const dropdown = await typeIntoLookup(page, scope, fieldName, searchText);
  const option = dropdown.locator('.input-dropdown-list-option').getByText(searchText, { exact }).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await option.click();
  await dropdown.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await page.keyboard.press('Tab');
  await page.waitForTimeout(1000);
}

async function openNewIncludedRow(page, tabId) {
  await page.getByTestId(`tab-AD_Tab-${tabId}`).click();
  await page.waitForTimeout(1000);
  // The "add new" button of the included tab has no data-testid; it is the filter-panel button
  // that is not the batch-entry toggle.
  const button = page.locator('.tab-pane .table-filter-line .filter-panel-buttons button:not(.close-batch-entry)').first();
  await button.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await button.click();
  const modal = page.locator('.panel-modal').first();
  await modal.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(1000);
  return modal;
}

/** Open a document action (process) from the three-dot menu and return its parameter modal. */
async function openAction(page, processValue) {
  await page.locator('.meta-icon-more').first().click();
  await page.locator('.subheader-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const action = page.getByTestId(`action-${processValue}`);
  await action.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await action.click();
  const modal = page.locator('.panel-modal').first();
  await modal.getByTestId('process-modal-start-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(1000);
  return modal;
}

async function closeModal(page, modal) {
  await modal.getByTestId('process-modal-cancel-button').first().click();
  await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await page.waitForTimeout(1000);
}

async function completeDocument(page) {
  await page.getByTestId('status-button').click();
  const co = page.getByTestId('status-CO');
  await co.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await co.click();
  await page.waitForTimeout(3000);
}

async function expectDocStatus(windowId, recordId, docStatus) {
  await expect.poll(async () => lookupKey((await getFieldData(windowId, recordId, 'DocStatus')).value), {
    message: `DocStatus of ${windowId}/${recordId}`,
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  }).toBe(docStatus);
}
