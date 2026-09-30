import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getFieldData, getTabRows, waitForRecordSaved, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
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
 *   3. Vertrags-Übergang (540120) + Vertragsbedingungen (540113): a transition with duration 0
 *      (the contract keeps its entered end date) and conditions of type compensation group; the
 *      settings field is shown only for that type.
 *   4. Geschäftspartner action "Erzeuge Vertrag": a product-less contract term for the partner on
 *      those conditions, with start and end date, opened in Verträge (540359).
 *   5. Auftrag (143): a sales order for the partner; completing it adds the contract group and
 *      its discount line.
 *
 * Besides the flow, the spec asserts the window titles, tab names, field labels and field order
 * the operator sees.
 */

// German captions are asserted on purpose: correctly named windows and fields ARE the subject under
// test here, so the login language is pinned to de_DE. Every expected caption below was read from the
// application dictionary (AD_Window/AD_Tab/AD_Field/AD_Element translations, de_DE).
const LANGUAGE = 'de_DE';

// Windows
const SCHEMA_WINDOW_ID = 540415;
const SETTINGS_WINDOW_ID = 542194;
const TRANSITION_WINDOW_ID = 540120;
const CONDITIONS_WINDOW_ID = 540113;
const CONTRACT_WINDOW_ID = 540359;
const BUSINESS_PARTNER_WINDOW_ID = 123;
const DOCTYPE_WINDOW_ID = 135; // Belegart (C_DocType)
const CALENDAR_WINDOW_ID = 117; // Kalenderjahr und Periode (C_Calendar)

// Business-partner action "Erzeuge Vertrag" and the partner's related-document link to its terms
const CREATE_CONTRACT_PROCESS = 'C_Flatrate_Term_Create_For_BPartners';
const BPARTNER_TO_CONTRACT_TERM_REFERENCE = 'reference-C_Flatrate_Term';

// Tabs (AD_Tab ids)
const SCHEMA_LINE_TAB_ID = 541042; // C_CompensationGroup_SchemaLine
const SETTINGS_DOCTYPE_TAB_ID = 549508; // C_CompensationGroup_ContractSettings_DocType
const CALENDAR_YEAR_TAB_ID = 129; // C_Year
const ORDER_LINE_TAB = 'AD_Tab-187';

// Ref-list keys of C_Flatrate_Conditions.Type_Conditions
const TYPE_CONDITIONS_COMPENSATION_GROUP = 'CompensationGroup';
const TYPE_CONDITIONS_OTHER = 'Subscr';

// Contract transition: duration 0, so the contract keeps the entered end date. Its contract calendar
// must have periods covering the contract; the test picks one of the offered calendars that has the
// contract's year (see pickContractCalendarWithYear).
const DURATION_UNIT_MONTH = 'month';

// Sales-order document type: Standardauftrag (DocBaseType SOO, DocSubType SO)
const DOCTYPE_STANDARD_ORDER_NAME = 'Standardauftrag';
// DocBaseTypes of order doc types
const ORDER_DOC_BASE_TYPES = ['SOO', 'POO'];

// Doc-type search probe: "Rechnung" matches both an order doc type ("Proforma Rechnung", SOO) and
// invoice doc types ("Ausgangsrechnung", ARI). The validation rule must offer only the former.
const DOCTYPE_SEARCH_PROBE = 'Rechnung';
const DOCTYPE_ORDER_MATCHING_PROBE = 'Proforma Rechnung';
const DOCTYPE_INVOICE_MATCHING_PROBE = 'Ausgangsrechnung';

// Expected German captions (de_DE), read from the AD
const DE = {
  schemaWindow: 'Kompensationsgruppe Schema',
  schemaLineTab: 'Kompensationszeilen',
  isAdditive: 'Additiv',
  schemaLineCategory: 'Gilt für Produktkategorie',
  schemaLineProduct: 'Produkt',
  schemaLineSeqNo: 'Reihenfolge',
  schemaLineDiscount: 'Gesamtauftragsrabatt %',

  settingsWindow: 'Kompensationsgruppen-Vertragseinstellungen',
  settingsDocTypeTab: 'Belegarten',
  settingsName: 'Name',
  settingsSchema: 'Kompensationsgruppe Schema',
  settingsDescription: 'Beschreibung',
  settingsDocType: 'Belegart',

  transitionWindow: 'Vertrags-Übergang',
  conditionsWindow: 'Vertragsbedingungen',
  conditionsSettings: 'Einstellungen für Kompensationsgruppen-Verträge',

  createContractEndDate: 'Enddatum',
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
    const transitionName = `CG transition ${runId}`;
    const conditionsName = `CG conditions ${runId}`;

    // The contract runs from the first of the current month to the end of the current year;
    // the sales order (dated today) falls into it.
    const today = new Date();
    const contractStart = new Date(today.getFullYear(), today.getMonth(), 1);
    const contractEnd = new Date(today.getFullYear(), 11, 31);

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

    // ids of records created in the UI, needed again in step 5
    let schemaId;
    let termId;

    // ------------------------------------------------------------------
    // 1. Kompensationsgruppe Schema (540415)
    // ------------------------------------------------------------------
    await test.step('1. Schema window: create an additive schema with a category-limited line', async () => {
      await openNewRecord(page, SCHEMA_WINDOW_ID);
      await expectWindowTitle(page, DE.schemaWindow);

      await fillText(page, page, 'Name', schemaName);
      schemaId = await waitForNewRecordId(page, SCHEMA_WINDOW_ID);

      // IsAdditive: German label, in the flags group directly after the other schema flags
      await expectLabel(page, 'IsAdditive', DE.isAdditive);
      await expectFieldsDirectlyInOrder(page, ['IsActive', 'IsInheritPackingInstruction', 'IsAdditive']);
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
      // the "applies to" category sits directly between the sequence and the discount product
      await expectFieldsDirectlyInOrder(modal, ['SeqNo', 'M_Product_Category_ID', 'M_Product_ID']);

      await selectListByKey(page, modal, 'M_Product_Category_ID', goodsCategoryId);
      await selectLookup(page, modal, 'M_Product_ID', discount.productCode);
      await fillNumber(page, modal, 'CompleteOrderDiscount', DISCOUNT_PERCENT);
      await snap(page, '540415-schema-line');
      await closeModal(modal);

      // the category column is also in the schema-line grid, next to the product
      await expect(page.locator('th[data-testid="column-M_Product_Category_ID"]')).toBeVisible();

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
    let settingsDocTypeId;
    await test.step('2. Settings window: create settings for the schema and list the sales-order doc type', async () => {
      await openNewRecord(page, SETTINGS_WINDOW_ID);
      await expectWindowTitle(page, DE.settingsWindow);

      await expectLabel(page, 'Name', DE.settingsName);
      await expectLabel(page, 'C_CompensationGroup_Schema_ID', DE.settingsSchema);
      await expectLabel(page, 'Description', DE.settingsDescription);
      await expectFieldsDirectlyInOrder(page, ['Name', 'C_CompensationGroup_Schema_ID', 'Description']);

      await fillText(page, page, 'Name', settingsName);
      settingsId = await waitForNewRecordId(page, SETTINGS_WINDOW_ID);
      await selectLookup(page, page, 'C_CompensationGroup_Schema_ID', schemaName);
      await fillText(page, page, 'Description', `Settings created by the WebUI test ${runId}`);
      await waitForRecordSaved(SETTINGS_WINDOW_ID, settingsId, { maxRetries: 20, retryDelayMs: 500 });
      await snap(page, '542194-settings-header');

      await expectTabCaption(page, SETTINGS_DOCTYPE_TAB_ID, DE.settingsDocTypeTab);
      const modal = await openNewIncludedRow(page, SETTINGS_DOCTYPE_TAB_ID);
      await expectLabel(modal, 'C_DocType_ID', DE.settingsDocType);

      // The doc-type search offers only order doc types.
      const offeredOptions = await searchLookupOptions(page, modal, 'C_DocType_ID', DOCTYPE_SEARCH_PROBE);
      const offered = offeredOptions.map((option) => option.caption);
      console.log(`[INFO] doc types offered for "${DOCTYPE_SEARCH_PROBE}": ${offered.join(' | ')}`);
      expect(offered, 'the order doc type matching the probe is offered').toContain(DOCTYPE_ORDER_MATCHING_PROBE);
      expect(offered, 'an invoice doc type is not offered').not.toContain(DOCTYPE_INVOICE_MATCHING_PROBE);
      for (const option of offeredOptions) {
        expect(ORDER_DOC_BASE_TYPES, `offered doc type "${option.caption}" must be an order doc type`)
          .toContain(await getDocBaseType(option.key));
      }
      await page.keyboard.press('Escape');
      await expect(page.locator('.input-dropdown-list')).toHaveCount(0);

      await selectLookup(page, modal, 'C_DocType_ID', DOCTYPE_STANDARD_ORDER_NAME, { exact: true });
      await snap(page, '542194-belegarten-tab');
      await closeModal(modal);

      const docTypes = await getTabRows(SETTINGS_WINDOW_ID, settingsId, `AD_Tab-${SETTINGS_DOCTYPE_TAB_ID}`);
      expect(docTypes, 'exactly one doc type row').toHaveLength(1);
      settingsDocTypeId = lookupKey(docTypes[0].fieldsByName.C_DocType_ID.value);
      expect(await getDocBaseType(settingsDocTypeId), 'the listed doc type is a sales order doc type').toBe('SOO');
    });

    // ------------------------------------------------------------------
    // 3. Vertrags-Übergang (540120) and Vertragsbedingungen (540113)
    // ------------------------------------------------------------------
    let transitionId;
    await test.step('3a. Transition window: create a completed transition with duration 0', async () => {
      await openNewRecord(page, TRANSITION_WINDOW_ID);
      await expectWindowTitle(page, DE.transitionWindow);

      await fillText(page, page, 'Name', transitionName);
      transitionId = await waitForNewRecordId(page, TRANSITION_WINDOW_ID);
      const contractCalendarId = await pickContractCalendarWithYear(transitionId, contractStart.getFullYear());
      await selectListByKey(page, page, 'C_Calendar_Contract_ID', contractCalendarId);
      await fillNumber(page, page, 'TermDuration', 0);
      await selectListByKey(page, page, 'TermDurationUnit', DURATION_UNIT_MONTH);
      await fillNumber(page, page, 'TermOfNotice', 0);
      await selectListByKey(page, page, 'TermOfNoticeUnit', DURATION_UNIT_MONTH);
      await waitForRecordSaved(TRANSITION_WINDOW_ID, transitionId, { maxRetries: 20, retryDelayMs: 500 });
      expect(Number((await getFieldData(TRANSITION_WINDOW_ID, transitionId, 'TermDuration')).value)).toBe(0);

      await completeDocument(page);
      await expectDocStatus(TRANSITION_WINDOW_ID, transitionId, 'CO');
    });

    let conditionsId;
    await test.step('3b. Conditions window: type compensation group shows the settings field', async () => {
      await openNewRecord(page, CONDITIONS_WINDOW_ID);
      await expectWindowTitle(page, DE.conditionsWindow);

      await fillText(page, page, 'Name', conditionsName);
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
      await expectFieldsDirectlyInOrder(page, ['Name', 'Type_Conditions', 'C_CompensationGroup_ContractSettings_ID']);

      await selectListByKey(page, page, 'C_CompensationGroup_ContractSettings_ID', settingsId);
      expect(lookupKey((await getFieldData(CONDITIONS_WINDOW_ID, conditionsId, 'C_CompensationGroup_ContractSettings_ID')).value)).toBe(String(settingsId));
      await selectListByKey(page, page, 'C_Flatrate_Transition_ID', transitionId);
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

      const modal = await openAction(page, CREATE_CONTRACT_PROCESS);
      await selectListByKey(page, modal, 'C_Flatrate_Conditions_ID', conditionsId);
      await fillDate(page, modal, 'StartDate', contractStart);
      await expectLabel(modal, 'EndDate', DE.createContractEndDate);
      await fillDate(page, modal, 'EndDate', contractEnd);
      await expect(modal.locator('.form-field-IsComplete input[type="checkbox"]').first(), 'the term is completed by the action').toBeChecked();
      await snap(page, '123-bpartner-erzeuge-vertrag-parameters');

      const processStarted = page.waitForResponse((r) => r.url().includes('/process/') && r.url().endsWith('/start'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await modal.getByTestId('process-modal-start-button').click();
      expect((await processStarted).ok(), '"Erzeuge Vertrag" must run without error').toBe(true);
      await modal.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });

      // The partner now references its contract term; open it in the Verträge window.
      await openRelatedDocument({
        dataCy: BPARTNER_TO_CONTRACT_TERM_REFERENCE,
        stepName: 'Business partner - open the created contract term',
        maxRetries: 10,
        retryDelay: 2000,
        refreshOnRetry: true,
      });
      termId = await waitForNewRecordId(page, CONTRACT_WINDOW_ID);

      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'Bill_BPartner_ID')).value)).toBe(String(customer.id));
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'C_Flatrate_Conditions_ID')).value)).toBe(String(conditionsId));
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'Type_Conditions')).value)).toBe(TYPE_CONDITIONS_COMPENSATION_GROUP);
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'M_Product_ID')).value), 'product-less term').toBeNull();
      expect(String((await getFieldData(CONTRACT_WINDOW_ID, termId, 'StartDate')).value)).toContain(isoDate(contractStart));
      expect(String((await getFieldData(CONTRACT_WINDOW_ID, termId, 'EndDate')).value), 'the entered end date is kept').toContain(isoDate(contractEnd));
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
        .toBe(settingsDocTypeId);
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
      // the goods line and the discount line form one compensation group, the discount line being its compensation line
      const groupId = lookupKey(discountLine.fieldsByName.C_Order_CompensationGroup_ID.value);
      expect(groupId, 'discount line belongs to a compensation group').toBeTruthy();
      expect(lookupKey(goodsLine.fieldsByName.C_Order_CompensationGroup_ID.value), 'goods line is in the same group').toBe(groupId);
      expect(discountLine.fieldsByName.IsGroupCompensationLine.value).toBe(true);
      expect(goodsLine.fieldsByName.IsGroupCompensationLine.value).toBe(false);
      // ... and that group is the one of this contract, built from this schema
      // (C_Order_CompensationGroup has no window, so it is read through the testing backend)
      await Backend.expect({
        title: 'the order\'s compensation group belongs to the contract and its schema',
        salesOrders: {
          [orderId]: { compensationGroups: [{ flatrateTermId: Number(termId), compensationGroupSchemaId: Number(schemaId) }] },
        },
      });

      // The UI shows both lines in the order-line grid, the discount line with the expected amount.
      // (the order is completed, so the batch-entry toggle SalesOrderPage.goToOrderLineTab relies on is gone)
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}`);
      const orderLineTab = page.getByTestId(`tab-${ORDER_LINE_TAB}`);
      await orderLineTab.click();
      await expect(orderLineTab.locator('a.nav-link')).toHaveClass(/active/);
      const discountRow = page.locator('table tbody tr')
        .filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: discount.productCode }) });
      await expect(discountRow).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(discountRow.locator('[data-cy="cell-LineNetAmt"]')).toContainText('-30,00');
      await discountRow.scrollIntoViewIfNeeded();
      await snap(page, '143-sales-order-discount-group-lines');
      await discountRow.locator('[data-cy="cell-LineNetAmt"]').scrollIntoViewIfNeeded();
      await snap(page, '143-sales-order-discount-group-amount');
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

/** yyyy-MM-dd of a local date */
function isoDate(date) {
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
}

/** Screenshot of the current viewport, attached to the report and kept in the test output folder. */
async function snap(page, name) {
  const buffer = await page.screenshot({ fullPage: false });
  const file = test.info().outputPath(`${name}.png`);
  require('fs').writeFileSync(file, buffer);
  await test.info().attach(name, { body: buffer, contentType: 'image/png' });
  console.log(`[INFO] screenshot ${file}`);
}

/**
 * Run `action` and wait until the change of `fieldName` it triggers has been sent to the backend
 * (the WebUI PATCHes a document / included row / process parameter on each field commit,
 * with a body of [{ op, path: <fieldName>, value }]). With `expectedKey`, only a change to that
 * lookup/list key counts.
 */
async function withFieldCommit(page, fieldName, action, expectedKey = undefined) {
  const committed = page.waitForResponse(
    (response) => {
      const request = response.request();
      if (request.method() !== 'PATCH' || !response.url().includes('/rest/api/')) {
        return false;
      }
      const body = request.postDataJSON();
      return Array.isArray(body) && body.some((change) =>
        change.path === fieldName && (expectedKey === undefined || lookupKey(change.value) === String(expectedKey)));
    },
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await action();
  await committed;
}

async function openNewRecord(page, windowId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}/NEW`);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
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
  // The label is read from the rendered DOM, not via getFieldLabelFromLayout (WebAPIValidation.js),
  // because what is asserted is the caption the operator actually sees on the painted form.
  const label = scope.locator(`.form-field-${fieldName} > label.form-control-label`).first();
  await expect(label, `label of ${fieldName}`).toHaveText(caption, { timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Assert that the given fields render as consecutive element lines of ONE element group, in this
 * order, with no other visible field between them. Lines whose field is hidden by display logic
 * render without a form group and are skipped. Pattern taken from vatid-status-on-bpartner-window.spec.js.
 */
async function expectFieldsDirectlyInOrder(scope, fieldNames) {
  const [first] = fieldNames;
  await scope.locator(`.form-field-${first}`).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // the filter's inner locator must be page-rooted: it is evaluated relative to each candidate group
  const pageRoot = typeof scope.page === 'function' ? scope.page() : scope;
  const group = scope.locator('div.panel.panel-spaced').filter({ has: pageRoot.locator(`.form-field-${first}`) });
  await expect(group, `exactly one element group must hold ${first}`).toHaveCount(1);

  const lineFields = await group.evaluate((groupEl) =>
    Array.from(groupEl.querySelectorAll(':scope > .elements-line'))
      .map((line) =>
        Array.from(line.querySelectorAll('.form-group'))
          .flatMap((formGroup) => Array.from(formGroup.classList).filter((cssClass) => cssClass.startsWith('form-field-')))
          .join(' ')
      )
      .filter((classes) => classes.length > 0)
  );
  const indexOfField = (fieldName) => lineFields.findIndex((classes) => classes.split(' ').includes(`form-field-${fieldName}`));

  const firstIndex = indexOfField(first);
  fieldNames.forEach((fieldName, offset) => {
    expect(
      indexOfField(fieldName),
      `${fieldName} must be visible element line ${offset} after ${first} in the same group (rendered: ${JSON.stringify(lineFields)})`
    ).toBe(firstIndex + offset);
  });
}

async function fillText(page, scope, fieldName, value) {
  const input = scope.locator(`.form-field-${fieldName} input[type="text"], .form-field-${fieldName} textarea`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.fill(value);
  await withFieldCommit(page, fieldName, () => input.press('Tab'));
}

async function fillNumber(page, scope, fieldName, value) {
  const input = scope.locator(`.form-field-${fieldName} input`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.press('ControlOrMeta+a');
  await input.pressSequentially(String(value));
  await withFieldCommit(page, fieldName, () => input.press('Tab'));
}

async function fillDate(page, scope, fieldName, date) {
  const text = `${String(date.getDate()).padStart(2, '0')}.${String(date.getMonth() + 1).padStart(2, '0')}.${date.getFullYear()}`;
  const input = scope.locator(`.form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.press('ControlOrMeta+a');
  await input.fill(text);
  await withFieldCommit(page, fieldName, () => input.press('Tab'));
}

async function setCheckbox(page, fieldName) {
  const checkbox = page.locator(`.form-field-${fieldName} input[type="checkbox"]`).first();
  if (!(await checkbox.isChecked())) {
    await withFieldCommit(page, fieldName, () => page.locator(`.form-field-${fieldName} label.input-checkbox`).first().click());
  }
  await expect(checkbox).toBeChecked();
}

/**
 * Pick the option with the given key in a list field and wait until that key has been committed.
 *
 * The commit is awaited from the moment the dropdown is opened, not only from the option click:
 * a mandatory list that offers a single option selects it by itself as soon as its values have
 * loaded (ListWidget.requestListData, forceSelection), so the PATCH already goes out on the input
 * click, and the option click afterwards sends no second PATCH.
 */
async function selectListByKey(page, scope, fieldName, key) {
  const input = scope.locator(`.form-field-${fieldName} input`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const option = page.locator(`.input-dropdown-list [data-testid="option-${key}"]`).first();
  await withFieldCommit(page, fieldName, async () => {
    await input.click();
    await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await option.click();
  }, key);
  await expect(page.locator('.input-dropdown-list')).toHaveCount(0);
}

/** Type into a lookup field and wait until the typeahead result for the full search text has arrived. */
async function typeIntoLookup(page, scope, fieldName, searchText) {
  const input = scope.locator(`.form-field-${fieldName} input.input-field, .form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.fill('');
  const typeaheadDone = page.waitForResponse(
    (response) => response.url().includes(`/typeahead?query=${encodeURIComponent(searchText)}`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await input.pressSequentially(searchText, { delay: 30 });
  await typeaheadDone;
  const dropdown = page.locator('.input-dropdown-list');
  await dropdown.locator('.input-dropdown-list-option').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return dropdown;
}

/** @returns the options ({ key, caption }) the lookup offers for the given search text */
async function searchLookupOptions(page, scope, fieldName, searchText) {
  const dropdown = await typeIntoLookup(page, scope, fieldName, searchText);
  const options = await dropdown.locator('.input-dropdown-list-option').evaluateAll((elements) =>
    elements.map((element) => ({ testId: element.getAttribute('data-testid'), caption: (element.textContent ?? '').trim() }))
  );
  return options
    .filter((option) => option.testId && option.testId.startsWith('option-') && option.caption.length > 0)
    .map((option) => ({ key: option.testId.substring('option-'.length), caption: option.caption }));
}

async function getDocBaseType(docTypeId) {
  return lookupKey((await getFieldData(DOCTYPE_WINDOW_ID, docTypeId, 'DocBaseType')).value);
}

/**
 * @returns the id of a contract calendar offered on the transition that has the given fiscal year,
 * so that the contract's periods exist without relying on a seeded calendar id
 */
async function pickContractCalendarWithYear(transitionId, fiscalYear) {
  const page = getPage();
  const response = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${TRANSITION_WINDOW_ID}/${transitionId}/field/C_Calendar_Contract_ID/dropdown`,
    { headers: { 'Content-Type': 'application/json' } }
  );
  expect(response.ok(), 'contract calendar dropdown').toBeTruthy();
  const calendarIds = ((await response.json()).values ?? []).map((value) => String(value.key));
  for (const calendarId of calendarIds) {
    const years = await getTabRows(CALENDAR_WINDOW_ID, calendarId, `AD_Tab-${CALENDAR_YEAR_TAB_ID}`);
    if ((years ?? []).some((year) => String(year.fieldsByName?.FiscalYear?.value) === String(fiscalYear))) {
      return calendarId;
    }
  }
  throw new Error(`No offered contract calendar has the fiscal year ${fiscalYear}; offered: ${calendarIds.join(', ')}`);
}

async function selectLookup(page, scope, fieldName, searchText, { exact = false } = {}) {
  const dropdown = await typeIntoLookup(page, scope, fieldName, searchText);
  const option = dropdown.locator('.input-dropdown-list-option').getByText(searchText, { exact }).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await withFieldCommit(page, fieldName, () => option.click());
  await expect(page.locator('.input-dropdown-list')).toHaveCount(0);
}

async function openNewIncludedRow(page, tabId) {
  await page.getByTestId(`tab-AD_Tab-${tabId}`).click();
  // The "add new" button of the included tab has no data-testid; it is the filter-panel button
  // that is not the batch-entry toggle.
  const button = page.locator('.tab-pane .table-filter-line .filter-panel-buttons button:not(.close-batch-entry)').first();
  await button.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await button.click();
  const modal = page.locator('.panel-modal').first();
  await modal.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
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
  await modal.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return modal;
}

async function closeModal(modal) {
  await modal.getByTestId('process-modal-cancel-button').first().click();
  await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
}

async function completeDocument(page) {
  await page.getByTestId('status-button').click();
  const co = page.getByTestId('status-CO');
  await co.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await withFieldCommit(page, 'DocAction', () => co.click());
}

async function expectDocStatus(windowId, recordId, docStatus) {
  await expect.poll(async () => lookupKey((await getFieldData(windowId, recordId, 'DocStatus')).value), {
    message: `DocStatus of ${windowId}/${recordId}`,
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  }).toBe(docStatus);
}
