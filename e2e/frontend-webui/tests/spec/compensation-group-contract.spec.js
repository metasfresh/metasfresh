import { expect, request as playwrightRequest } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { getDocumentReferences, getFieldData, getTabRows, getWindowLayout, waitForRecordSaved, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
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
 *   6. Verträge (540359): a second term for the partner, entered directly in the window (on
 *      conditions whose transition has a duration, so the end date is computed); it saves and
 *      gets its DocumentNo from the sequence.
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

// Contract change action "Vertrag/ Abo ändern" (C_Flatrate_Term_Change) and its action list keys
const CHANGE_CONTRACT_PROCESS = 'C_Flatrate_Term_Change';
const CHANGE_ACTION_CANCEL = 'CA'; // "Kündigen"
const CHANGE_ACTION_SINGLE_PERIOD = 'VO'; // "Einzelne Periode stornieren"
// Tab "Wechselkonditionen" of the transition (C_Contract_Change): the action "Kündigen" needs a status change to "Qu"
const TRANSITION_CHANGE_TAB_ID = 540422;
const CONTRACT_CHANGE_ACTION_STATUS_CHANGE = 'ST';
const CONTRACT_STATUS_CANCELLED = 'Qu';
const DURATION_UNIT_DAY = 'day';

// Tabs (AD_Tab ids)
const SCHEMA_LINE_TAB_ID = 541042; // C_CompensationGroup_SchemaLine
const SETTINGS_DOCTYPE_TAB_ID = 549508; // C_CompensationGroup_ContractSettings_DocType
const CALENDAR_YEAR_TAB_ID = 129; // C_Year
const ORDER_LINE_TAB = 'AD_Tab-187';
// Auftrag Kompensationsgruppe (C_Order_CompensationGroup) and its order-lines tab
const ORDER_COMPENSATION_GROUP_WINDOW_ID = 542196;
const ORDER_COMPENSATION_GROUP_LINES_TAB_ID = 549518;
// The lines tab reads a view with only order-line columns; these seven need order context and are not shown
const ORDER_COMPENSATION_GROUP_LINES_HIDDEN_COLUMNS = [
  'M_Warehouse_ID', 'M_HU_PI_Item_Product_ID', 'C_Charge_ID', 'C_Flatrate_Term_ID', 'C_Flatrate_Conditions_ID', 'C_BPartner_Vendor_ID', 'M_LU_HU_PI_ID',
];
const ORDER_COMPENSATION_GROUP_LINES_SHOWN_COLUMNS = [
  'Line', 'M_Product_ID', 'QtyEntered', 'C_UOM_ID', 'PriceEntered', 'Price_UOM_ID', 'LineNetAmt', 'GroupCompensationType', 'GroupCompensationAmtType',
];
// related-document windows left out of the comparison: their entries are not generated for a view-backed row (change log, event log, issues)
const RELATED_DOCUMENTS_LEFT_OUT_WINDOW_IDS = ['270', '540394', '363'];
// related-document windows an order line reaches only through the generic zoom of its key column C_OrderLine_ID
// (AD_Column.IsGenericZoomOrigin): Lieferdisposition (M_ShipmentSchedule) for a sales line, Wareneingangsdisposition
// (M_ReceiptSchedule) for a purchase line; their records are created async after the order is completed
const SHIPMENT_SCHEDULE_WINDOW_ID = 500221;
const RECEIPT_SCHEDULE_WINDOW_ID = 540196;
// R-REL: the two invoice-candidate entries of an order line ("Rechnungsdisposition" sales, "Rechnungsdisposition Einkauf" purchase)
// come from relation types 540221 / 540275 (matched by AD_Table_ID + Record_ID of the order line). A migration with a
// time-zone-dependent condition leaves them inactive on a UTC database, so there the entries (and a compare without them) are
// vacuous. The test therefore activates them for its run (window "Relationsart", System Administrator role) and puts them back.
// The relation types of the view row (540514 sales, 540515 purchase) copy the original's IsActive when their migration runs,
// i.e. they are inactive on such a database too, so they are activated with the originals; they do not exist before that migration.
const RELATION_TYPES_WINDOW_ID = 53102;
const INVOICE_CANDIDATE_RELATION_TYPE_IDS = [540221, 540275, 540514, 540515];
const INVOICE_CANDIDATE_SALES_CAPTION = 'Rechnungsdisposition';
const INVOICE_CANDIDATE_PURCHASE_CAPTION = 'Rechnungsdisposition Einkauf';
// R-REL compare: the order line's related documents must stay unchanged this long before they count as complete
const STABLE_RELATED_DOCUMENTS_MS = 1000;
// Bestellung (purchase order) and its line tab
const PURCHASE_ORDER_WINDOW_ID = 181;
const PURCHASE_ORDER_LINE_TAB_ID = 293;
const PURCHASE_ORDER_LINE_TAB_ID_PATH = `AD_Tab-${PURCHASE_ORDER_LINE_TAB_ID}`;
// Menu (AD_Menu ids): folder "Kompensationsgruppen" under "Vertrieb", right after "Auftrag", holding these four entries
const MENU_SALES_FOLDER = '1000010';
const MENU_SALES_ORDER = '1000040';
const MENU_COMPENSATION_GROUPS_FOLDER = '542368';
const MENU_COMPENSATION_GROUPS_ENTRIES = [
  { nodeId: '542367', type: 'window', elementId: '542196' }, // Auftrag Kompensationsgruppe
  { nodeId: '541040', type: 'window', elementId: '540415' }, // Kompensationsgruppe Schema
  { nodeId: '541726', type: 'window', elementId: '541169' }, // Kompensationsgruppe Schema Kategorie
  { nodeId: '542364', type: 'window', elementId: '542194' }, // Kompensationsgruppen-Vertragseinstellungen
];
// Related Documents entry of a contract to its order compensation groups (data-cy: the target window's InternalName)
const CONTRACT_TO_ORDER_COMPENSATION_GROUP_REFERENCE = 'reference-C_Order_CompensationGroup';
// Ref-list keys of C_CompensationGroup_SchemaLine.Type
const SCHEMA_LINE_TYPE_CONTRACT = 'F';
const SCHEMA_LINE_TYPE_REVENUE_BREAKS = 'R';

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
  schemaLinePackingCategory: 'Packmittel-Kategorie',
  schemaLineProduct: 'Produkt',
  schemaLineSeqNo: 'Reihenfolge',
  schemaLineDiscount: 'Gesamtauftragsrabatt %',

  settingsWindow: 'Kompensationsgruppen-Vertragseinstellungen',
  settingsDocTypeTab: 'Belegarten',
  settingsName: 'Name',
  settingsSchema: 'Kompensationsgruppe Schema',
  settingsDescription: 'Beschreibung',
  settingsDocType: 'Belegart',
  settingsPassOnServiceCompany: 'Servicegebühr von', // short enough to show uncut in the header

  transitionWindow: 'Vertrags-Übergang',
  conditionsWindow: 'Vertragsbedingungen',
  conditionsSettings: 'Einstellungen für Kompensationsgruppen-Verträge',
  conditionsPassOnServiceCompany: 'Servicegebühr von',

  createContractEndDate: 'Enddatum',

  salesFolder: 'Vertrieb',
  compensationGroupsFolder: 'Kompensationsgruppen',
  orderCompensationGroupWindow: 'Auftrag Kompensationsgruppe',
  orderCompensationGroupOrder: 'Auftrag/Bestellung', // the group's order may be a sales or a purchase order
  orderCompensationGroupContract: 'Vertrag',
  orderCompensationGroupLinesTab: 'Auftragsposition',
  orderCompensationGroupLineNo: 'Zeile Nr.', // C_OrderLine.Line on the order-lines tab
  compensationTypeDiscount: 'Rabatt', // C_OrderLine.GroupCompensationType D
  compensationAmtTypePercent: 'Prozent', // C_OrderLine.GroupCompensationAmtType P

  contractMasterStartDate: 'Vertragspartner seit',
  contractMasterEndDate: 'Vertragspartner bis',
  orderLineConditionsForNewContract: 'Vertragsbedingungen neuer Vertrag', // C_OrderLine.C_Flatrate_Conditions_ID
  orderLineCallOffContract: 'Abrufvertrag', // C_OrderLine.C_Flatrate_Term_ID

  schemaLineCondition: 'Bedingung', // C_CompensationGroup_SchemaLine.Type
  schemaLineTypeContract: 'Vertrag',
  schemaLineTypeRevenueBreaks: 'Umsatzstaffel',
  schemaLineRevenueFrom: 'Umsatz ab', // C_CompensationGroup_SchemaLine.BreakValue
  schemaLineContractConditions: 'Vertragsbedingungen', // C_CompensationGroup_SchemaLine.C_Flatrate_Conditions_ID
};

// The same captions in en_US (AD_*_Trl en_US), for the English pass of the order compensation group test
const EN = {
  compensationGroupsFolder: 'Compensation Groups',
  orderCompensationGroupWindow: 'Order Compensation Group',
  orderCompensationGroupOrder: 'Sales/Purchase Order',
  orderCompensationGroupContract: 'Contract',
  orderCompensationGroupLinesTab: 'Order Line',
  orderCompensationGroupLineNo: 'Line No',
  compensationTypeDiscount: 'Discount',
  compensationAmtTypePercent: 'Percent',

  contractMasterStartDate: 'Master Start Date',
  contractMasterEndDate: 'Master End Date',
  orderLineConditionsForNewContract: 'Conditions for New Contract',
  orderLineCallOffContract: 'Call-off Contract',

  schemaLineCondition: 'Condition',
  schemaLineTypeContract: 'Contract',
  schemaLineTypeRevenueBreaks: 'Revenue Breaks',
  schemaLineRevenueFrom: 'Revenue From',
  schemaLineContractConditions: 'Contract Conditions',
};

// Business values
const GOODS_PRICE = 1000;
const DISCOUNT_PERCENT = 3;
const EXPECTED_DISCOUNT_AMOUNT = -(GOODS_PRICE * DISCOUNT_PERCENT) / 100; // -30

test.describe('Compensation-group contract — create through the WebUI and complete an order', () => {
  // puts the relation types back after the test that activated them (also when it failed)
  let restoreRelationTypes = null;
  test.afterEach(async () => {
    if (restoreRelationTypes) {
      const restore = restoreRelationTypes;
      restoreRelationTypes = null;
      await restore();
    }
  });

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
        productCategories: { GOODS_CATEGORY: {}, CARTON_CATEGORY: {} },
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
    const cartonCategoryId = masterdata.productCategories.CARTON_CATEGORY.id;

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
      await expectLabel(modal, 'M_Product_Category_PackingMaterial_ID', DE.schemaLinePackingCategory);
      await expectLabel(modal, 'M_Product_ID', DE.schemaLineProduct);
      await expectLabel(modal, 'CompleteOrderDiscount', DE.schemaLineDiscount);
      // the "applies to" category and the packing-material category sit directly between the sequence and the discount product
      await expectFieldsDirectlyInOrder(modal, ['SeqNo', 'M_Product_Category_ID', 'M_Product_Category_PackingMaterial_ID', 'M_Product_ID']);

      await selectListByKey(page, modal, 'M_Product_Category_ID', goodsCategoryId);
      await selectListByKey(page, modal, 'M_Product_Category_PackingMaterial_ID', cartonCategoryId);
      await selectLookup(page, modal, 'M_Product_ID', discount.productCode);
      await fillNumber(page, modal, 'CompleteOrderDiscount', DISCOUNT_PERCENT);
      await snap(page, '540415-schema-line');
      await closeModal(modal);

      // the category column is also in the schema-line grid, next to the product
      await expect(page.locator('th[data-testid="column-M_Product_Category_ID"]')).toBeVisible();
      await expect(page.locator('th[data-testid="column-M_Product_Category_PackingMaterial_ID"]')).toBeVisible();

      const lines = await getTabRows(SCHEMA_WINDOW_ID, schemaId, `AD_Tab-${SCHEMA_LINE_TAB_ID}`);
      expect(lines, 'exactly one schema line').toHaveLength(1);
      expect(lookupKey(lines[0].fieldsByName.M_Product_Category_ID.value)).toBe(String(goodsCategoryId));
      expect(lookupKey(lines[0].fieldsByName.M_Product_Category_PackingMaterial_ID.value)).toBe(String(cartonCategoryId));
      expect(lookupKey(lines[0].fieldsByName.M_Product_ID.value)).toBe(String(discount.id));
      expect(Number(lines[0].fieldsByName.CompleteOrderDiscount.value)).toBe(DISCOUNT_PERCENT);

      // The sales order in step 5 has no packing instruction, so a line limited to a packing-material category
      // would (correctly) give it no discount. The rest of this flow covers the unfiltered line: clear the field again.
      // (the schema-line grid edits in place: double-click the cell, then its clear icon)
      const packingCell = page.locator('table tbody tr').first().locator('[data-cy="cell-M_Product_Category_PackingMaterial_ID"]');
      await packingCell.dblclick();
      await withFieldCommit(page, 'M_Product_Category_PackingMaterial_ID',
        () => page.locator('table tbody tr').first().locator('.input-icon:has(.meta-icon-close-alt)').click());
      const clearedLines = await getTabRows(SCHEMA_WINDOW_ID, schemaId, `AD_Tab-${SCHEMA_LINE_TAB_ID}`);
      expect(lookupKey(clearedLines[0].fieldsByName.M_Product_Category_PackingMaterial_ID?.value), 'packing-material category cleared again').toBeNull();
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
      await expectLabel(page, 'PassOn_InvoiceProcessingServiceCompany_ID', DE.settingsPassOnServiceCompany);
      await expectLabelNotCutOff(page, 'PassOn_InvoiceProcessingServiceCompany_ID');

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
      await expectLabel(page, 'PassOn_InvoiceProcessingServiceCompany_ID', DE.conditionsPassOnServiceCompany);
      await expectLabelNotCutOff(page, 'PassOn_InvoiceProcessingServiceCompany_ID');

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
      // (read through the testing backend; the window "Auftrag Kompensationsgruppe" is covered by the test 'order compensation groups of two contracts')
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

    // ------------------------------------------------------------------
    // 6. Verträge (540359): a term entered directly in the window saves and gets its number
    //    from the sequence. DocumentNo is mandatory and filled on save, never by the operator.
    //    The partner has contract data now (from step 4), so the window offers it. The end date
    //    is read-only there and computed from the transition, so these conditions use a
    //    transition with a duration (step 3's has none: its terms keep an entered end date).
    // ------------------------------------------------------------------
    let yearTransitionId;
    await test.step('6a. Transition window: a completed transition of 12 months', async () => {
      await openNewRecord(page, TRANSITION_WINDOW_ID);
      await fillText(page, page, 'Name', `${transitionName} 12M`);
      yearTransitionId = await waitForNewRecordId(page, TRANSITION_WINDOW_ID);
      const contractCalendarId = await pickContractCalendarWithYear(yearTransitionId, contractStart.getFullYear());
      await selectListByKey(page, page, 'C_Calendar_Contract_ID', contractCalendarId);
      await fillNumber(page, page, 'TermDuration', 12);
      await selectListByKey(page, page, 'TermDurationUnit', DURATION_UNIT_MONTH);
      await fillNumber(page, page, 'TermOfNotice', 0);
      await selectListByKey(page, page, 'TermOfNoticeUnit', DURATION_UNIT_MONTH);
      await waitForRecordSaved(TRANSITION_WINDOW_ID, yearTransitionId, { maxRetries: 20, retryDelayMs: 500 });
      await completeDocument(page);
      await expectDocStatus(TRANSITION_WINDOW_ID, yearTransitionId, 'CO');
    });

    let yearConditionsId;
    await test.step('6b. Conditions window: compensation-group conditions with the 12-month transition', async () => {
      await openNewRecord(page, CONDITIONS_WINDOW_ID);
      await fillText(page, page, 'Name', `${conditionsName} 12M`);
      yearConditionsId = await waitForNewRecordId(page, CONDITIONS_WINDOW_ID);
      await selectListByKey(page, page, 'Type_Conditions', TYPE_CONDITIONS_COMPENSATION_GROUP);
      await selectListByKey(page, page, 'C_CompensationGroup_ContractSettings_ID', settingsId);
      await selectListByKey(page, page, 'C_Flatrate_Transition_ID', yearTransitionId);
      await waitForRecordSaved(CONDITIONS_WINDOW_ID, yearConditionsId, { maxRetries: 20, retryDelayMs: 500 });
      await completeDocument(page);
      await expectDocStatus(CONDITIONS_WINDOW_ID, yearConditionsId, 'CO');
    });

    await test.step('6c. Verträge: a new term entered in the window saves with a sequence document number', async () => {
      const flatrateDataId = lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'C_Flatrate_Data_ID')).value);
      const billLocationId = lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'Bill_Location_ID')).value);
      const firstTermDocumentNo = String((await getFieldData(CONTRACT_WINDOW_ID, termId, 'DocumentNo')).value);

      await openNewRecord(page, CONTRACT_WINDOW_ID);
      const newTermId = await waitForNewRecordId(page, CONTRACT_WINDOW_ID);

      await selectListByKey(page, page, 'C_Flatrate_Data_ID', flatrateDataId);
      await selectListByKey(page, page, 'C_Flatrate_Conditions_ID', yearConditionsId);
      // the contract data set the invoice partner (callout); its location is the second part of that lookup
      await selectLookupPartByKey(page, 'Bill_Location_ID', billLocationId);
      await fillDate(page, page, 'StartDate', contractStart);

      await waitForRecordSaved(CONTRACT_WINDOW_ID, newTermId, { maxRetries: 20, retryDelayMs: 500 });
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, newTermId, 'Bill_BPartner_ID')).value)).toBe(String(customer.id));

      const documentNo = String((await getFieldData(CONTRACT_WINDOW_ID, newTermId, 'DocumentNo')).value ?? '');
      console.log(`[INFO] new term ${newTermId}: DocumentNo=${documentNo} (term of step 4: ${firstTermDocumentNo})`);
      // the sequence may carry a prefix/suffix, so only what a missing number would look like is excluded
      expect(documentNo.trim(), 'DocumentNo is filled').not.toBe('');
      expect(documentNo, 'DocumentNo is not the former default "0"').not.toBe('0');
      const hasPreliminaryMarkers = documentNo.trim().startsWith('<') && documentNo.trim().endsWith('>');
      expect(hasPreliminaryMarkers, `DocumentNo "${documentNo}" is not a preliminary "<...>" number`).toBe(false);
      expect(documentNo, 'each term gets its own number').not.toBe(firstTermDocumentNo);

      // the new term is deliberately left drafted: completing it would hit MSG_CompensationGroup_OverlappingTerm (same partner, doc types and period as the step-4 term)
      // the saved term, reloaded, shows that number to the operator
      await page.goto(`${FRONTEND_BASE_URL}/window/${CONTRACT_WINDOW_ID}/${newTermId}`);
      await expect(page.locator('.form-field-DocumentNo input').first()).toHaveValue(documentNo, { timeout: SLOW_ACTION_TIMEOUT });
      await snap(page, '540359-term-entered-in-window');
    });
  });

  /**
   * Early end of a contract: the first contract is ended with the contract action "Kündigen" (end date X) and a
   * follow-up contract with another schema starts on X + 1. The overlap check must accept the follow-up, and each
   * order gets the discount of the contract that is valid on its order date.
   */
  test('ends a contract early and a follow-up contract takes over from the next day', async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.story('Early end of a compensation-group contract with a follow-up contract');
    allure.severity('critical');
    test.setTimeout(20 * 60 * 1000);
    page.setDefaultTimeout(60 * 1000);

    const runId = Date.now();
    const x = contractReferenceDay();
    const contractAStart = addDays(x, -7);
    const contractAEnd = addDays(x, 30);
    const contractBStart = addDays(x, 1);
    const contractBEnd = endOfYear(x);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        productCategories: { GOODS_CATEGORY: {} },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        products: {
          GOODS: { productCategory: 'GOODS_CATEGORY', prices: [{ price: GOODS_PRICE }] },
          DISCOUNT_A: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
          DISCOUNT_B: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
        },
      },
    });
    const customer = masterdata.bpartners.CUSTOMER;
    const goods = masterdata.products.GOODS;
    const goodsCategoryId = masterdata.productCategories.GOODS_CATEGORY.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    let transitionId;
    let conditionsA;
    let conditionsB;
    await test.step('1. Setup: transition, and two schemas (3 % and 5 %) with their settings and conditions', async () => {
      transitionId = await createTransition(page, `CG transition ${runId}`, x.getFullYear(), { allowCancellation: true });
      conditionsA = await createConditions(page, {
        schemaName: `CG early A ${runId}`, goodsCategoryId, additive: false,
        lines: [{ discountProduct: masterdata.products.DISCOUNT_A, percent: 3 }],
        transitionId, name: `CG conditions early A ${runId}`,
      });
      conditionsB = await createConditions(page, {
        schemaName: `CG early B ${runId}`, goodsCategoryId, additive: false,
        lines: [{ discountProduct: masterdata.products.DISCOUNT_B, percent: 5 }],
        transitionId, name: `CG conditions early B ${runId}`,
      });
    });

    let termA;
    await test.step('2. Contract A (3 %) from X - 7 to X + 30', async () => {
      termA = await createContractTerm(page, customer, conditionsA, contractAStart, contractAEnd);
      expect(String((await getFieldData(CONTRACT_WINDOW_ID, termA, 'EndDate')).value)).toContain(isoDate(contractAEnd));
    });

    await test.step('3. Contract action "Kündigen" ends contract A on X', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${CONTRACT_WINDOW_ID}/${termA}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      const modal = await openAction(page, CHANGE_CONTRACT_PROCESS);
      // The action list already shows "Kündigen"; a value that is already selected sends no save,
      // so another value is selected first.
      await selectListByKey(page, modal, 'Action', CHANGE_ACTION_SINGLE_PERIOD);
      await selectListByKey(page, modal, 'Action', CHANGE_ACTION_CANCEL);
      await fillDate(page, modal, 'EventDate', x);
      await snap(page, '540359-kuendigen-parameters');
      const processStarted = page.waitForResponse((r) => r.url().includes('/process/') && r.url().endsWith('/start'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await modal.getByTestId('process-modal-start-button').click();
      expect((await processStarted).ok(), '"Vertrag/ Abo ändern" must run without error').toBe(true);
      await modal.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await expect.poll(async () => String((await getFieldData(CONTRACT_WINDOW_ID, termA, 'EndDate')).value), {
        message: 'contract A ends on X', timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).toContain(isoDate(x));
    });

    await test.step('4. Contract B (5 %) from X + 1 is accepted', async () => {
      await createContractTerm(page, customer, conditionsB, contractBStart, contractBEnd, { openTerm: false });
    });

    await test.step('5. Order dated X gets the 3 % discount line, order dated X + 1 the 5 % discount line', async () => {
      const orderX = await completeSalesOrder(page, customer, goods, x);
      const linesX = await getTabRows(SALES_ORDER_WINDOW_ID, orderX, ORDER_LINE_TAB);
      expect(linesX, 'order X: goods line + one discount line').toHaveLength(2);
      const discountX = lineOfProduct(linesX, masterdata.products.DISCOUNT_A);
      expect(discountX, 'order X: the 3 % discount line').toBeTruthy();
      expect(Number(discountX.fieldsByName.LineNetAmt.value)).toBeCloseTo(-30, 2);
      await expectOrderGridAmount(page, orderX, masterdata.products.DISCOUNT_A, '-30,00');
      await snap(page, '143-order-x-3-percent');

      const orderX1 = await completeSalesOrder(page, customer, goods, addDays(x, 1));
      const linesX1 = await getTabRows(SALES_ORDER_WINDOW_ID, orderX1, ORDER_LINE_TAB);
      expect(linesX1, 'order X + 1: goods line + one discount line').toHaveLength(2);
      const discountX1 = lineOfProduct(linesX1, masterdata.products.DISCOUNT_B);
      expect(discountX1, 'order X + 1: the 5 % discount line').toBeTruthy();
      expect(Number(discountX1.fieldsByName.LineNetAmt.value)).toBeCloseTo(-50, 2);
      await expectOrderGridAmount(page, orderX1, masterdata.products.DISCOUNT_B, '-50,00');
      await snap(page, '143-order-x1-5-percent');
    });
  });


  /**
   * The order compensation groups of two contracts, seen in the window "Auftrag Kompensationsgruppe" and its
   * neighbours: zoom from an order line into its group, the group's order-lines tab, and the "Vertrag" filter that
   * tells the two contracts' groups apart, the menu folder that holds the window, and the contract's Related Documents
   * entry to its groups; plus the German captions of the contract's master dates, of the order-line contract columns
   * (sales and purchase order) and of the schema-line condition fields, and finally the same captions in English.
   */
  test('order compensation groups of two contracts: group window, contract filter and contract captions', async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.story('Order compensation group window: zoom from the order line, order lines, contract filter, captions');
    allure.severity('normal');
    test.setTimeout(20 * 60 * 1000);
    page.setDefaultTimeout(60 * 1000);

    const runId = Date.now();
    const x = contractReferenceDay();

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        productCategories: { GOODS_CATEGORY: {} },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        products: {
          GOODS: { productCategory: 'GOODS_CATEGORY', prices: [{ price: GOODS_PRICE }] },
          DISCOUNT_A: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
          DISCOUNT_B: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
        },
      },
    });
    restoreRelationTypes = await activateInvoiceCandidateRelationTypes();
    const customer = masterdata.bpartners.CUSTOMER;
    const goods = masterdata.products.GOODS;
    const goodsCategoryId = masterdata.productCategories.GOODS_CATEGORY.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    let conditionsA;
    let conditionsB;
    await test.step('1. Setup: transition, and two schemas (3 % and 5 %) with their settings and conditions', async () => {
      const transitionId = await createTransition(page, `CG transition ${runId}`, x.getFullYear());
      conditionsA = await createConditions(page, {
        schemaName: `CG groups A ${runId}`, goodsCategoryId, additive: false,
        lines: [{ discountProduct: masterdata.products.DISCOUNT_A, percent: 3 }],
        transitionId, name: `CG conditions groups A ${runId}`,
      });
      conditionsB = await createConditions(page, {
        schemaName: `CG groups B ${runId}`, goodsCategoryId, additive: false,
        lines: [{ discountProduct: masterdata.products.DISCOUNT_B, percent: 5 }],
        transitionId, name: `CG conditions groups B ${runId}`,
      });
    });

    let termA;
    await test.step('2. Contract A (3 %) from X - 7 to X, contract B (5 %) from X + 1; the master dates carry German labels', async () => {
      termA = await createContractTerm(page, customer, conditionsA, addDays(x, -7), x);
      // the start / end of the whole contract relationship carry German labels
      await expectLabel(page, 'MasterStartDate', DE.contractMasterStartDate);
      await expectLabel(page, 'MasterEndDate', DE.contractMasterEndDate);
      await page.locator('.form-field-MasterStartDate').first().scrollIntoViewIfNeeded();
      await snap(page, '540359-contract-master-dates');
      await createContractTerm(page, customer, conditionsB, addDays(x, 1), endOfYear(x), { openTerm: false });
    });

    let orderX;
    let groupX;
    let groupX1;
    await test.step('3. One order per contract, each with its compensation group', async () => {
      orderX = await completeSalesOrder(page, customer, goods, x);
      const discountX = lineOfProduct(await getTabRows(SALES_ORDER_WINDOW_ID, orderX, ORDER_LINE_TAB), masterdata.products.DISCOUNT_A);
      expect(discountX, 'order X: the 3 % discount line').toBeTruthy();
      const orderX1 = await completeSalesOrder(page, customer, goods, addDays(x, 1));
      const discountX1 = lineOfProduct(await getTabRows(SALES_ORDER_WINDOW_ID, orderX1, ORDER_LINE_TAB), masterdata.products.DISCOUNT_B);
      expect(discountX1, 'order X + 1: the 5 % discount line').toBeTruthy();

      groupX = lookupKey(discountX.fieldsByName.C_Order_CompensationGroup_ID.value);
      groupX1 = lookupKey(discountX1.fieldsByName.C_Order_CompensationGroup_ID.value);
      expect(groupX, 'order X has a compensation group').toBeTruthy();
      expect(groupX1, 'order X + 1 has a compensation group').toBeTruthy();
    });

    let groupPage;
    // ------------------------------------------------------------------
    // 4. Auftrag Kompensationsgruppe (542196): the read-only window of the order compensation groups
    // ------------------------------------------------------------------
    await test.step('4a. Order line: "Zoom into" its compensation group opens the order compensation group window', async () => {
      await expectOrderGridAmount(page, orderX, masterdata.products.DISCOUNT_A, '-30,00');
      // the order line's own contract columns: conditions of a contract to be created, and the call-off contract
      await expect(page.locator('th[data-testid="column-C_Flatrate_Conditions_ID"]')).toHaveText(DE.orderLineConditionsForNewContract);
      await expect(page.locator('th[data-testid="column-C_Flatrate_Term_ID"]')).toHaveText(DE.orderLineCallOffContract);
      const discountRow = page.locator('table tbody tr')
        .filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: masterdata.products.DISCOUNT_A.productCode }) });
      const groupCell = discountRow.locator('[data-cy="cell-C_Order_CompensationGroup_ID"]');
      await groupCell.scrollIntoViewIfNeeded();
      // "Zoom into" opens the target in a new browser tab
      groupPage = await zoomIntoFromCell(page, groupCell, async () => {
        await snap(page, '143-order-line-zoom-into-group');
        await page.locator('th[data-testid="column-C_Flatrate_Term_ID"]').scrollIntoViewIfNeeded();
        await snap(page, '143-order-line-contract-columns');
      });
      await groupPage.waitForURL(new RegExp(`/window/${ORDER_COMPENSATION_GROUP_WINDOW_ID}/${groupX}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await groupPage.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      await expectWindowTitle(groupPage, DE.orderCompensationGroupWindow);
      await expectLabel(groupPage, 'C_Order_ID', DE.orderCompensationGroupOrder);
      await expectLabel(groupPage, 'C_Flatrate_Term_ID', DE.orderCompensationGroupContract);
      await expectFieldsDirectlyInOrder(groupPage, ['Name', 'C_Order_ID', 'C_Flatrate_Term_ID', 'C_CompensationGroup_Schema_ID']);
      expect(lookupKey((await getFieldData(ORDER_COMPENSATION_GROUP_WINDOW_ID, groupX, 'C_Order_ID')).value)).toBe(String(orderX));
      expect(lookupKey((await getFieldData(ORDER_COMPENSATION_GROUP_WINDOW_ID, groupX, 'C_Flatrate_Term_ID')).value)).toBe(String(termA));
      // read-only: the fields cannot be edited
      await expect(groupPage.locator('.form-field-Name input').first()).toBeDisabled();
      await snap(groupPage, '542196-order-compensation-group');
    });

    await test.step('4b. Sub-tab "Auftragsposition": the group\'s order lines, the discount line with "Rabatt" / "Prozent"', async () => {
      await expectTabCaption(groupPage, ORDER_COMPENSATION_GROUP_LINES_TAB_ID, DE.orderCompensationGroupLinesTab);
      const linesTab = groupPage.getByTestId(`tab-AD_Tab-${ORDER_COMPENSATION_GROUP_LINES_TAB_ID}`);
      await linesTab.click();
      await expect(linesTab.locator('a.nav-link')).toHaveClass(/active/);

      const lines = await getTabRows(ORDER_COMPENSATION_GROUP_WINDOW_ID, groupX, `AD_Tab-${ORDER_COMPENSATION_GROUP_LINES_TAB_ID}`);
      expect(lines, 'goods line + discount line of order X').toHaveLength(2);

      await expect(groupPage.locator('th[data-testid="column-Line"]')).toHaveText(DE.orderCompensationGroupLineNo);
      const rows = groupPage.locator('table tbody tr');
      await expect(rows).toHaveCount(2, { timeout: SLOW_ACTION_TIMEOUT });
      const discountRow = rows.filter({ has: groupPage.locator('[data-cy="cell-M_Product_ID"]', { hasText: masterdata.products.DISCOUNT_A.productCode }) });
      await expect(discountRow).toHaveCount(1);
      await expect(discountRow.locator('[data-cy="cell-LineNetAmt"]')).toContainText('-30,00');
      // (the list cells show "<value>_<name>", e.g. "D_Rabatt", as in the sales order's line grid)
      await expect(discountRow.locator('[data-cy="cell-GroupCompensationType"]')).toContainText(DE.compensationTypeDiscount);
      await expect(discountRow.locator('[data-cy="cell-GroupCompensationAmtType"]')).toContainText(DE.compensationAmtTypePercent);
      await discountRow.locator('[data-cy="cell-GroupCompensationAmtType"]').scrollIntoViewIfNeeded();
      await snap(groupPage, '542196-order-lines-tab');

      // sorted by line no.: the tab's default sort, and the rows as shown
      const layout = await getWindowLayout(ORDER_COMPENSATION_GROUP_WINDOW_ID);
      const linesTabLayout = (layout.tabs ?? []).find((tab) => String(tab.tabId).endsWith(String(ORDER_COMPENSATION_GROUP_LINES_TAB_ID)));
      expect(linesTabLayout, 'layout of the order-lines tab').toBeTruthy();
      // (the WebUI appends the key column as tie-breaker)
      expect(linesTabLayout.defaultOrderBys?.[0], 'order-lines tab: default sort by line no., ascending').toEqual({ fieldName: 'Line', ascending: true });
      const lineNos = (await rows.locator('[data-cy="cell-Line"]').allTextContents()).map((text) => Number(text.replace(/\D/g, '')));
      expect(lineNos, 'line numbers shown').toHaveLength(2);
      expect(lineNos, 'rows in line-no. order').toEqual([...lineNos].sort((a, b) => a - b));

      // The tab shows only the order-line columns of its view; the 7 columns that need order context stay hidden
      for (const hidden of ORDER_COMPENSATION_GROUP_LINES_HIDDEN_COLUMNS) {
        await expect(groupPage.locator(`th[data-testid="column-${hidden}"]`), `column ${hidden} not shown`).toHaveCount(0);
      }
      // all shown fields are read-only
      const discountLineRow = lineOfProduct(lines, masterdata.products.DISCOUNT_A);
      expect(discountLineRow, 'discount line row on the tab').toBeTruthy();
      for (const shown of ORDER_COMPENSATION_GROUP_LINES_SHOWN_COLUMNS) {
        expect(discountLineRow.fieldsByName[shown], `field ${shown} present on the row`).toBeTruthy();
        expect(discountLineRow.fieldsByName[shown].readonly, `field ${shown} read-only`).toBe(true);
      }
      // the values shown equal those of the same order line in the sales order window
      const salesOrderLines = await getTabRows(SALES_ORDER_WINDOW_ID, orderX, ORDER_LINE_TAB);
      expectValuesEqualOrderLines(lines, salesOrderLines);
      // zoom on the product opens that product
      const productPage = await zoomIntoFromCell(groupPage, discountRow.locator('[data-cy="cell-M_Product_ID"]'));
      await productPage.waitForURL(new RegExp(`/window/\\d+/${masterdata.products.DISCOUNT_A.id}$`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await productPage.close();

      // Related documents: the related documents of a line row on this tab equal those of the same order line in the
      // sales order window (windows 270, 540394, 363 left out on both sides). The sales order window is the expected side.
      // (a tab row's `id` is the parent document's id; the row's own key is `rowId`)
      const lineRowId = String(discountLineRow.rowId);
      const orderLineRow = (await getTabRows(SALES_ORDER_WINDOW_ID, orderX, ORDER_LINE_TAB)).find((row) => String(row.rowId) === lineRowId);
      expect(orderLineRow, 'the same C_OrderLine in the sales order window').toBeTruthy();
      await expectRelatedDocumentsEqualOrderLine({
        readExpected: () => getDocumentReferences(SALES_ORDER_WINDOW_ID, orderX, ORDER_LINE_TAB, lineRowId),
        readActual: () => getDocumentReferences(ORDER_COMPENSATION_GROUP_WINDOW_ID, groupX, `AD_Tab-${ORDER_COMPENSATION_GROUP_LINES_TAB_ID}`, lineRowId),
        requiredTargetWindowId: SHIPMENT_SCHEDULE_WINDOW_ID,
        requiredCaption: INVOICE_CANDIDATE_SALES_CAPTION,
        what: 'the sales line row',
      });

      // Single view (Advanced edit) of a line row of the sales group: the same nine fields, read-only
      await expectLineRowSingleView(groupPage, discountRow, masterdata.products.DISCOUNT_A, 'sales-discount');
    });

    await test.step('4c. List: filter "Vertrag" narrows the groups to the ones of that contract', async () => {
      // unfiltered: all order compensation groups, sorted by order, newest first
      // (other groups may exist on the DB, so the sort and the size are checked on the backend's view, not on row positions)
      const unfiltered = await readOrderCompensationGroupView(groupPage);
      expect(unfiltered.size, 'both orders\' groups and possibly others are listed').toBeGreaterThanOrEqual(2);
      // (the view sorts by the order column as the list shows it, i.e. by the order's document number, descending: a purchase
      // order has its own number sequence, so its group sorts by that number, not by the order id. All digit-only captions are
      // compared with each other, across lengths and number sequences (e.g. purchase '820471' above sales '0050', '9999' above
      // '10000'), and captions with other characters with those of the same shape; see expectOrderNosDescending for why that
      // is the database's order and which pairs are left out)
      const orderNos = unfiltered.rows.map((row) => String(row.fieldsByName.C_Order_ID?.value?.caption));
      expectOrderNosDescending(orderNos);
      // the two sales orders of this test get consecutive numbers of the same zero-padded sequence: order X + 1 (created
      // after order X) gets the later number of the same length, so its group is listed above the group of order X (all rows
      // of the view are read, so the position does not depend on how many other groups exist)
      const orderNoOfGroup = (groupId) => String(unfiltered.rows.find((row) => String(row.id) === String(groupId))?.fieldsByName.C_Order_ID?.value?.caption);
      for (const [name, groupId] of [['X', groupX], ['X + 1', groupX1]]) {
        expect(orderNoOfGroup(groupId), `order ${name}: digit-only document number, so its group is in the sort check above`).toMatch(/^\d+$/);
      }
      expect(orderNoOfGroup(groupX1).length, `order X + 1 (${orderNoOfGroup(groupX1)}) and order X (${orderNoOfGroup(groupX)}): numbers of the same length, else the sequence rolled over a digit and the position check below does not apply`).toBe(orderNoOfGroup(groupX).length);
      const listedIds = unfiltered.rows.map((row) => String(row.id));
      expect(listedIds, 'the group of order X is listed').toContain(String(groupX));
      expect(listedIds, 'the group of order X + 1 is listed').toContain(String(groupX1));
      expect(listedIds.indexOf(String(groupX1)), 'the group of order X + 1 (newer) is listed above the group of order X').toBeLessThan(listedIds.indexOf(String(groupX)));

      await groupPage.goto(`${FRONTEND_BASE_URL}/window/${ORDER_COMPENSATION_GROUP_WINDOW_ID}`);
      const rowOf = (groupId) => groupPage.getByTestId(`table-row-${groupId}`);
      await expect(groupPage.locator('table tbody tr').first()).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
      await snap(groupPage, '542196-list-unfiltered');

      const termADocumentNo = String((await getFieldData(CONTRACT_WINDOW_ID, termA, 'DocumentNo')).value);
      // "Filter" opens the filter panel of the selection columns (the "default" filter, the window's only one)
      await groupPage.locator('.filters-not-frequent button.toggle-filters').click();
      const filterPanel = groupPage.locator('.filter-default');
      await filterPanel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const filterFields = await filterPanel.evaluate((panel) => Array.from(panel.querySelectorAll('.form-group[class*="form-field-"]'))
        .map((formGroup) => Array.from(formGroup.classList).find((cssClass) => cssClass.startsWith('form-field-')).substring('form-field-'.length)));
      expect(filterFields, 'filter fields: name, order, contract, schema, organisation').toEqual(['Name', 'C_Order_ID', 'C_Flatrate_Term_ID', 'C_CompensationGroup_Schema_ID', 'AD_Org_ID']);
      await expectLabel(filterPanel, 'C_Flatrate_Term_ID', DE.orderCompensationGroupContract);
      const contractInput = filterPanel.locator('.form-field-C_Flatrate_Term_ID input').first();
      await contractInput.click();
      await contractInput.pressSequentially(termADocumentNo, { delay: 30 });
      const contractOption = groupPage.locator(`.input-dropdown-list [data-testid="option-${termA}"]`);
      await contractOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await contractOption.click();
      await expect(groupPage.locator('.input-dropdown-list')).toHaveCount(0);
      await snap(groupPage, '542196-filter-vertrag');
      await groupPage.getByTestId('filter-apply-button').click();

      await expect(groupPage.locator('table tbody tr'), 'only the group of contract A').toHaveCount(1, { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await expect(rowOf(groupX)).toBeVisible();
      await expect(rowOf(groupX1)).toHaveCount(0);
      await snap(groupPage, '542196-list-filtered-by-contract');
      await groupPage.close();
    });

    await test.step('4d. Menu: folder "Kompensationsgruppen" under "Vertrieb", right after "Auftrag", with its four entries', async () => {
      // depth 2: a sub-folder is listed only together with its own children
      const salesFolder = await getMenuNode(page, MENU_SALES_FOLDER, 2);
      expect(salesFolder.caption).toBe(DE.salesFolder);
      const salesChildIds = (salesFolder.children ?? []).map((child) => String(child.nodeId));
      expect(salesChildIds.indexOf(MENU_COMPENSATION_GROUPS_FOLDER), 'the folder comes right after "Auftrag"')
        .toBe(salesChildIds.indexOf(MENU_SALES_ORDER) + 1);

      const folder = await getMenuNode(page, MENU_COMPENSATION_GROUPS_FOLDER);
      expect(folder.caption).toBe(DE.compensationGroupsFolder);
      expect(String(folder.parentId), 'parent folder').toBe(MENU_SALES_FOLDER);
      const entries = (folder.children ?? [])
        .filter((child) => child.type !== 'newRecord')
        .map((child) => ({ nodeId: String(child.nodeId), type: child.type, elementId: String(child.elementId) }));
      expect(entries, 'the four entries, in this order').toEqual(MENU_COMPENSATION_GROUPS_ENTRIES);
      // (a window entry's caption may carry the internal name in parentheses)
      expect(folder.children.find((child) => String(child.nodeId) === MENU_COMPENSATION_GROUPS_ENTRIES[0].nodeId).caption)
        .toContain(DE.orderCompensationGroupWindow);
    });

    await test.step('4e. Contract A: Related Documents lead to its order compensation group', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${CONTRACT_WINDOW_ID}/${termA}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await openRelatedDocument({
        dataCy: CONTRACT_TO_ORDER_COMPENSATION_GROUP_REFERENCE,
        stepName: 'Contract A - open its order compensation groups',
        maxRetries: 5,
        retryDelay: 2000,
      });
      // the contract has one group, which is opened
      await page.waitForURL(new RegExp(`/window/${ORDER_COMPENSATION_GROUP_WINDOW_ID}/${groupX}`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await expectWindowTitle(page, DE.orderCompensationGroupWindow);
      await snap(page, '540359-related-order-compensation-group');
    });

    // ------------------------------------------------------------------
    // 4f/4g. The same window for a PURCHASE group: created the way an operator creates it (a contract on the vendor,
    // then a completed purchase order that matches it)
    // ------------------------------------------------------------------
    let purchaseOrderId;
    let purchaseTermId;
    let purchaseGoods;
    let purchaseDiscount;
    await test.step('4f. Purchase side: vendor, contract on a purchase-order doc type, completed purchase order with its compensation group', async () => {
      // a second masterdata call: the first call's price list is a sales price list, so its vendor would have no purchase price
      const purchaseMasterdata = await Backend.createMasterdata({
        request: {
          productCategories: { P_CATEGORY: {} },
          bpartners: { VENDOR: { isVendor: true, isCustomer: false, isSoPriceList: false } },
          products: {
            P_GOODS: { productCategory: 'P_CATEGORY', prices: [{ price: GOODS_PRICE }] },
            P_DISCOUNT: { type: 'Item', isStocked: false, productCategory: 'P_CATEGORY', prices: [{ price: 1 }] },
          },
        },
      });
      const vendor = purchaseMasterdata.bpartners.VENDOR;
      purchaseGoods = purchaseMasterdata.products.P_GOODS;
      purchaseDiscount = purchaseMasterdata.products.P_DISCOUNT;

      // the draft purchase order shows which purchase-order doc type the stack defaults to; the contract settings must list exactly that one
      await PurchaseOrderPage.goto();
      await PurchaseOrderPage.clickNew();
      purchaseOrderId = await PurchaseOrderPage.selectBusinessPartner(vendor.bpartnerCode);
      const docType = (await getFieldData(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, 'C_DocTypeTarget_ID')).value;
      expect(docType, 'the draft purchase order has a target document type').toBeTruthy();
      expect(await getDocBaseType(docType.key), 'its document base type').toBe('POO');

      const transitionId = await createTransition(page, `CG transition P ${runId}`, x.getFullYear());
      const conditionsP = await createConditions(page, {
        schemaName: `CG groups P ${runId}`, goodsCategoryId: purchaseMasterdata.productCategories.P_CATEGORY.id, additive: false,
        lines: [{ discountProduct: purchaseDiscount, percent: DISCOUNT_PERCENT }],
        transitionId, name: `CG conditions groups P ${runId}`, docTypeName: docType.caption,
      });
      purchaseTermId = await createContractTerm(page, vendor, conditionsP, addDays(x, -7), x);

      await completePurchaseOrder(page, purchaseOrderId, purchaseGoods, x);
      const lines = await getTabRows(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, PURCHASE_ORDER_LINE_TAB_ID_PATH);
      expect(lineOfProduct(lines, purchaseDiscount), 'purchase order: the 3 % discount line').toBeTruthy();
    });

    await test.step('4g. Purchase group, opened from the vendor contract: order-lines tab (grid and single view), related documents', async () => {
      const purchaseLines = await getTabRows(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, PURCHASE_ORDER_LINE_TAB_ID_PATH);
      expect(purchaseLines, 'goods line + discount line of the purchase order').toHaveLength(2);
      expect(lineOfProduct(purchaseLines, purchaseDiscount), 'the purchase order carries the discount line').toBeTruthy();

      // The purchase order's line grid has no compensation-group column (its layout lists none), so the group is reached the way an
      // operator reaches it from the purchase side: the vendor contract's Related Documents entry to its order compensation groups
      await page.goto(`${FRONTEND_BASE_URL}/window/${CONTRACT_WINDOW_ID}/${purchaseTermId}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await openRelatedDocument({
        dataCy: CONTRACT_TO_ORDER_COMPENSATION_GROUP_REFERENCE,
        stepName: 'Purchase contract - open its order compensation groups',
        maxRetries: 5,
        retryDelay: 2000,
      });
      await page.waitForURL(new RegExp(`/window/${ORDER_COMPENSATION_GROUP_WINDOW_ID}/\\d+`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      const purchaseGroupId = page.url().split(`/window/${ORDER_COMPENSATION_GROUP_WINDOW_ID}/`)[1].split(/[/?#]/)[0];
      const purchaseGroupPage = page;
      await purchaseGroupPage.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await expectWindowTitle(purchaseGroupPage, DE.orderCompensationGroupWindow);
      expect(lookupKey((await getFieldData(ORDER_COMPENSATION_GROUP_WINDOW_ID, purchaseGroupId, 'C_Order_ID')).value), 'the group belongs to the purchase order').toBe(String(purchaseOrderId));
      expect(lookupKey((await getFieldData(ORDER_COMPENSATION_GROUP_WINDOW_ID, purchaseGroupId, 'C_Flatrate_Term_ID')).value), 'and to the vendor contract').toBe(String(purchaseTermId));
      await expect(purchaseGroupPage.locator('.form-field-Name input').first()).toBeDisabled();

      // sub-tab "Auftragsposition": the group's rows, the nine column labels, sort, read-only
      await expectTabCaption(purchaseGroupPage, ORDER_COMPENSATION_GROUP_LINES_TAB_ID, DE.orderCompensationGroupLinesTab);
      const linesTab = purchaseGroupPage.getByTestId(`tab-AD_Tab-${ORDER_COMPENSATION_GROUP_LINES_TAB_ID}`);
      await linesTab.click();
      await expect(linesTab.locator('a.nav-link')).toHaveClass(/active/);
      const lines = await getTabRows(ORDER_COMPENSATION_GROUP_WINDOW_ID, purchaseGroupId, `AD_Tab-${ORDER_COMPENSATION_GROUP_LINES_TAB_ID}`);
      expect(lines, 'goods line + discount line of the purchase order').toHaveLength(2);
      const rows = purchaseGroupPage.locator('table tbody tr');
      await expect(rows).toHaveCount(2, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(purchaseGroupPage.locator('th[data-testid="column-Line"]')).toHaveText(DE.orderCompensationGroupLineNo);
      for (const shown of ORDER_COMPENSATION_GROUP_LINES_SHOWN_COLUMNS) {
        await expect(purchaseGroupPage.locator(`th[data-testid="column-${shown}"]`), `column ${shown} shown`).toHaveCount(1);
      }
      for (const hidden of ORDER_COMPENSATION_GROUP_LINES_HIDDEN_COLUMNS) {
        await expect(purchaseGroupPage.locator(`th[data-testid="column-${hidden}"]`), `column ${hidden} not shown`).toHaveCount(0);
      }
      const discountRow = rows.filter({ has: purchaseGroupPage.locator('[data-cy="cell-M_Product_ID"]', { hasText: purchaseDiscount.productCode }) });
      const goodsRow = rows.filter({ has: purchaseGroupPage.locator('[data-cy="cell-M_Product_ID"]', { hasText: purchaseGoods.productCode }) });
      await expect(discountRow).toHaveCount(1);
      await expect(goodsRow).toHaveCount(1);
      await expect(goodsRow.locator('[data-cy="cell-LineNetAmt"]')).toContainText('1.000,00');
      await expect(discountRow.locator('[data-cy="cell-LineNetAmt"]')).toContainText('-30,00');
      await expect(discountRow.locator('[data-cy="cell-GroupCompensationType"]')).toContainText(DE.compensationTypeDiscount);
      await expect(discountRow.locator('[data-cy="cell-GroupCompensationAmtType"]')).toContainText(DE.compensationAmtTypePercent);
      await snap(purchaseGroupPage, '542196-purchase-order-lines-tab');
      const layout = await getWindowLayout(ORDER_COMPENSATION_GROUP_WINDOW_ID);
      const linesTabLayout = (layout.tabs ?? []).find((tab) => String(tab.tabId).endsWith(String(ORDER_COMPENSATION_GROUP_LINES_TAB_ID)));
      expect(linesTabLayout?.defaultOrderBys?.[0], 'order-lines tab: default sort by line no., ascending').toEqual({ fieldName: 'Line', ascending: true });
      const lineNos = (await rows.locator('[data-cy="cell-Line"]').allTextContents()).map((text) => Number(text.replace(/\D/g, '')));
      expect(lineNos, 'line numbers shown').toHaveLength(2);
      expect(lineNos, 'rows in line-no. order').toEqual([...lineNos].sort((a, b) => a - b));
      for (const line of lines) {
        for (const shown of ORDER_COMPENSATION_GROUP_LINES_SHOWN_COLUMNS) {
          expect(line.fieldsByName[shown], `field ${shown} present on the row`).toBeTruthy();
          expect(line.fieldsByName[shown].readonly, `field ${shown} read-only`).toBe(true);
        }
      }

      // zoom on the product opens that product
      const purchaseProductPage = await zoomIntoFromCell(purchaseGroupPage, goodsRow.locator('[data-cy="cell-M_Product_ID"]'));
      await purchaseProductPage.waitForURL(new RegExp(`/window/\\d+/${purchaseGoods.id}$`), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await purchaseProductPage.close();

      // the values shown equal those of the same order line in the purchase order window
      // (the purchase order's line tab shows neither group column: its two fields have no UI element, so its rows do not carry
      // them; these seven are compared with the purchase order window)
      expectValuesEqualOrderLines(lines, purchaseLines, ORDER_COMPENSATION_GROUP_LINES_SHOWN_COLUMNS.filter((column) => !column.startsWith('GroupCompensation')));
      // the two group columns, checked on both rows against the values of the group created in 4f (no purchase window shows
      // them): the 3 % discount line is a discount (D) in percent (P), the goods line carries neither
      const purchaseDiscountLine = lineOfProduct(lines, purchaseDiscount);
      expect(purchaseDiscountLine, 'discount line row on the tab').toBeTruthy();
      expect(lookupKey(purchaseDiscountLine.fieldsByName.GroupCompensationType.value), 'discount row: GroupCompensationType').toBe('D');
      expect(lookupKey(purchaseDiscountLine.fieldsByName.GroupCompensationAmtType.value), 'discount row: GroupCompensationAmtType').toBe('P');
      const purchaseGoodsLine = lineOfProduct(lines, purchaseGoods);
      expect(purchaseGoodsLine, 'goods line row on the tab').toBeTruthy();
      expect(lookupKey(purchaseGoodsLine.fieldsByName.GroupCompensationType.value) || null, 'goods row: no GroupCompensationType').toBeNull();
      expect(lookupKey(purchaseGoodsLine.fieldsByName.GroupCompensationAmtType.value) || null, 'goods row: no GroupCompensationAmtType').toBeNull();

      // single view of both line rows
      await expectLineRowSingleView(purchaseGroupPage, goodsRow, purchaseGoods, 'purchase-goods');
      await expectLineRowSingleView(purchaseGroupPage, discountRow, purchaseDiscount, 'purchase-discount');

      // R-REL: the related documents of the goods line row equal those of the same order line in the purchase order window
      const goodsRowId = String(purchaseGoodsLine.rowId);
      expect(purchaseLines.map((row) => String(row.rowId)), 'the same C_OrderLine in the purchase order window').toContain(goodsRowId);
      await expectRelatedDocumentsEqualOrderLine({
        readExpected: () => getDocumentReferences(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, PURCHASE_ORDER_LINE_TAB_ID_PATH, goodsRowId),
        readActual: () => getDocumentReferences(ORDER_COMPENSATION_GROUP_WINDOW_ID, purchaseGroupId, `AD_Tab-${ORDER_COMPENSATION_GROUP_LINES_TAB_ID}`, goodsRowId),
        requiredTargetWindowId: RECEIPT_SCHEDULE_WINDOW_ID,
        requiredCaption: INVOICE_CANDIDATE_PURCHASE_CAPTION,
        what: 'the purchase goods line row',
      });
    });

    await test.step('5. Schema window: the line condition fields read "Bedingung" (Vertrag / Umsatzstaffel), "Umsatz ab", "Vertragsbedingungen"; no organisation on the lines', async () => {
      const schemaId = lookupKey((await getFieldData(ORDER_COMPENSATION_GROUP_WINDOW_ID, groupX, 'C_CompensationGroup_Schema_ID')).value);
      await page.goto(`${FRONTEND_BASE_URL}/window/${SCHEMA_WINDOW_ID}/${schemaId}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.getByTestId(`tab-AD_Tab-${SCHEMA_LINE_TAB_ID}`).click();
      await expect(page.locator('th[data-testid="column-Type"]')).toHaveText(DE.schemaLineCondition, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('th[data-testid="column-BreakValue"]')).toHaveText(DE.schemaLineRevenueFrom);
      await expect(page.locator('th[data-testid="column-C_Flatrate_Conditions_ID"]')).toHaveText(DE.schemaLineContractConditions);
      await expect(page.locator('th[data-testid="column-AD_Org_ID"]'), 'no organisation column on the schema lines').toHaveCount(0);
      await expect(page.locator('th[data-testid="column-AD_Client_ID"]'), 'no client column on the schema lines').toHaveCount(0);
      await page.locator('th[data-testid="column-Type"]').scrollIntoViewIfNeeded();
      await snap(page, '540415-schema-lines-grid');

      // a new line (cancelled again): the condition offers "Vertrag" and "Umsatzstaffel" and shows the matching field
      const modal = await openNewIncludedRow(page, SCHEMA_LINE_TAB_ID);
      await expectLabel(modal, 'Type', DE.schemaLineCondition);
      await expect(modal.locator('.form-field-AD_Org_ID'), 'no organisation in the schema line form').toHaveCount(0);
      await expect(modal.locator('.form-field-AD_Client_ID'), 'no client in the schema line form').toHaveCount(0);
      await modal.locator('.form-field-Type input').first().click();
      await expect(page.locator('.input-dropdown-list [data-testid="option-F"]')).toContainText(DE.schemaLineTypeContract);
      await expect(page.locator('.input-dropdown-list [data-testid="option-R"]')).toContainText(DE.schemaLineTypeRevenueBreaks);
      await snap(page, '540415-schema-line-condition-options');
      await page.keyboard.press('Escape');
      await selectListByKey(page, modal, 'Type', SCHEMA_LINE_TYPE_REVENUE_BREAKS);
      await expectLabel(modal, 'BreakValue', DE.schemaLineRevenueFrom);
      await snap(page, '540415-schema-line-revenue-breaks');
      await selectListByKey(page, modal, 'Type', SCHEMA_LINE_TYPE_CONTRACT);
      await expectLabel(modal, 'C_Flatrate_Conditions_ID', DE.schemaLineContractConditions);
      await snap(page, '540415-schema-line-contract');
      // the incomplete line is discarded: closing asks "Do you really want to leave?"
      page.once('dialog', (dialog) => dialog.accept());
      await closeModal(modal);
    });

    await test.step('6. Purchase order lines (last: the new order stays unsaved): the contract columns carry the same captions', async () => {
      await openNewRecord(page, PURCHASE_ORDER_WINDOW_ID);
      const purchaseLinesTab = page.getByTestId(`tab-AD_Tab-${PURCHASE_ORDER_LINE_TAB_ID}`);
      await purchaseLinesTab.click();
      const conditionsHeader = page.locator('th[data-testid="column-C_Flatrate_Conditions_ID"]');
      await expect(conditionsHeader).toHaveText(DE.orderLineConditionsForNewContract, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('th[data-testid="column-C_Flatrate_Term_ID"]')).toHaveText(DE.orderLineCallOffContract);
      await conditionsHeader.scrollIntoViewIfNeeded();
      await snap(page, '181-purchase-order-line-contract-columns');
    });

    await test.step('7. en_US: the same captions in English', async () => {
      const english = await Backend.createMasterdata({ request: { login: { user: { language: 'en_US' } } } });
      await page.context().clearCookies();
      await LoginPage.goto();
      await LoginPage.login(english.login.user);
      await DashboardPage.expectVisible();

      expect((await getMenuNode(page, MENU_COMPENSATION_GROUPS_FOLDER)).caption).toBe(EN.compensationGroupsFolder);

      // order compensation group window and its order-lines tab
      await page.goto(`${FRONTEND_BASE_URL}/window/${ORDER_COMPENSATION_GROUP_WINDOW_ID}/${groupX}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await expectWindowTitle(page, EN.orderCompensationGroupWindow);
      await expectLabel(page, 'C_Order_ID', EN.orderCompensationGroupOrder);
      await expectLabel(page, 'C_Flatrate_Term_ID', EN.orderCompensationGroupContract);
      await expectTabCaption(page, ORDER_COMPENSATION_GROUP_LINES_TAB_ID, EN.orderCompensationGroupLinesTab);
      await page.getByTestId(`tab-AD_Tab-${ORDER_COMPENSATION_GROUP_LINES_TAB_ID}`).click();
      await expect(page.locator('th[data-testid="column-Line"]')).toHaveText(EN.orderCompensationGroupLineNo, { timeout: SLOW_ACTION_TIMEOUT });
      const discountRow = page.locator('table tbody tr')
        .filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: masterdata.products.DISCOUNT_A.productCode }) });
      await expect(discountRow).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(discountRow.locator('[data-cy="cell-GroupCompensationType"]')).toContainText(EN.compensationTypeDiscount);
      await expect(discountRow.locator('[data-cy="cell-GroupCompensationAmtType"]')).toContainText(EN.compensationAmtTypePercent);
      await snap(page, '542196-order-lines-tab-en');

      // contract master dates
      await page.goto(`${FRONTEND_BASE_URL}/window/${CONTRACT_WINDOW_ID}/${termA}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await expectLabel(page, 'MasterStartDate', EN.contractMasterStartDate);
      await expectLabel(page, 'MasterEndDate', EN.contractMasterEndDate);
      await page.locator('.form-field-MasterStartDate').first().scrollIntoViewIfNeeded();
      await snap(page, '540359-contract-master-dates-en');

      // the sales order line's contract columns
      await expectOrderGridAmount(page, orderX, masterdata.products.DISCOUNT_A, '-30.00');
      await expect(page.locator('th[data-testid="column-C_Flatrate_Conditions_ID"]')).toHaveText(EN.orderLineConditionsForNewContract);
      await expect(page.locator('th[data-testid="column-C_Flatrate_Term_ID"]')).toHaveText(EN.orderLineCallOffContract);
      await page.locator('th[data-testid="column-C_Flatrate_Term_ID"]').scrollIntoViewIfNeeded();
      await snap(page, '143-order-line-contract-columns-en');

      // schema lines: the condition fields and the condition values
      const schemaId = lookupKey((await getFieldData(ORDER_COMPENSATION_GROUP_WINDOW_ID, groupX, 'C_CompensationGroup_Schema_ID')).value);
      await page.goto(`${FRONTEND_BASE_URL}/window/${SCHEMA_WINDOW_ID}/${schemaId}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page.getByTestId(`tab-AD_Tab-${SCHEMA_LINE_TAB_ID}`).click();
      await expect(page.locator('th[data-testid="column-Type"]')).toHaveText(EN.schemaLineCondition, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('th[data-testid="column-BreakValue"]')).toHaveText(EN.schemaLineRevenueFrom);
      await expect(page.locator('th[data-testid="column-C_Flatrate_Conditions_ID"]')).toHaveText(EN.schemaLineContractConditions);
      const modal = await openNewIncludedRow(page, SCHEMA_LINE_TAB_ID);
      await expectLabel(modal, 'Type', EN.schemaLineCondition);
      await modal.locator('.form-field-Type input').first().click();
      await expect(page.locator('.input-dropdown-list [data-testid="option-F"]')).toContainText(EN.schemaLineTypeContract);
      await expect(page.locator('.input-dropdown-list [data-testid="option-R"]')).toContainText(EN.schemaLineTypeRevenueBreaks);
      await snap(page, '540415-schema-line-condition-options-en');
      await page.keyboard.press('Escape');
      page.once('dialog', (dialog) => dialog.accept());
      await closeModal(modal);
    });
  });

  /**
   * "Erzeuge Vertrag" for a partner that already has an overlapping compensation-group contract on the same
   * order document type: the contract must not be created, and the operator must be told on screen why -
   * which contract it clashes with, for which invoice partner and in which period.
   */
  test('creating a contract that overlaps an existing one tells the operator why it was refused', async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.story('Overlapping compensation-group contract is refused with a visible reason');
    allure.severity('critical');
    test.setTimeout(20 * 60 * 1000);
    page.setDefaultTimeout(60 * 1000);

    const runId = Date.now();
    const x = contractReferenceDay();
    const existingStart = x;
    const existingEnd = addDays(x, 30);
    const overlappingStart = addDays(x, 5);
    const overlappingEnd = addDays(x, 60);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        productCategories: { GOODS_CATEGORY: {} },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        products: {
          DISCOUNT: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
        },
      },
    });
    const customer = masterdata.bpartners.CUSTOMER;
    const goodsCategoryId = masterdata.productCategories.GOODS_CATEGORY.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    let conditionsId;
    let existingTerm;
    let existingDocumentNo;
    await test.step('1. Setup: conditions "3 %" and an active contract from X to X + 30', async () => {
      const transitionId = await createTransition(page, `CG transition ${runId}`, x.getFullYear());
      conditionsId = await createConditions(page, {
        schemaName: `CG overlap ${runId}`, goodsCategoryId, additive: false,
        lines: [{ discountProduct: masterdata.products.DISCOUNT, percent: DISCOUNT_PERCENT }],
        transitionId, name: `CG conditions overlap ${runId}`,
      });
      existingTerm = await createContractTerm(page, customer, conditionsId, existingStart, existingEnd);
      existingDocumentNo = String((await getFieldData(CONTRACT_WINDOW_ID, existingTerm, 'DocumentNo')).value);
      expect(existingDocumentNo, 'the existing contract has a document no').toBeTruthy();
      expect(await countContractsOfPartner(page, customer.id)).toBe(1);
      expect(String((await getFieldData(CONTRACT_WINDOW_ID, existingTerm, 'EndDate')).value), 'the existing contract ends on X + 30').toContain(isoDate(existingEnd));
      await expectDocStatus(CONTRACT_WINDOW_ID, existingTerm, 'CO');
    });

    await test.step('2. "Erzeuge Vertrag" with the same conditions from X + 5 is refused with a visible reason', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${customer.id}`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      const modal = await openAction(page, CREATE_CONTRACT_PROCESS);
      await selectListByKey(page, modal, 'C_Flatrate_Conditions_ID', conditionsId);
      await fillDate(page, modal, 'StartDate', overlappingStart);
      await fillDate(page, modal, 'EndDate', overlappingEnd);
      const processStarted = page.waitForResponse((r) => r.url().includes('/process/') && r.url().endsWith('/start'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await modal.getByTestId('process-modal-start-button').click();
      await processStarted;
      await snap(page, '540460-overlap-refused');

      // The refusal reason is shown in the error notification (the long text sits behind "(read more)"):
      // it names the clashing contract, the invoice partner and the period.
      const reason = page.locator('.notification-item.error .notification-content').first();
      await expect(reason, 'an error notification shows the refusal').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      const readMore = reason.getByText('(read more)');
      if (await readMore.isVisible()) {
        await readMore.click();
      }
      await expect(reason, 'the clashing contract\'s document no is shown').toContainText(existingDocumentNo);
      await expect(reason, 'the invoice partner is shown').toContainText(customer.bpartnerCode);
      await expect(reason, 'the clashing contract\'s start is shown').toContainText(germanDate(existingStart));
      await expect(reason, 'the clashing contract\'s end is shown').toContainText(germanDate(existingEnd));
    });

    await test.step('3. No second contract exists for the partner', async () => {
      expect(await countContractsOfPartner(page, customer.id)).toBe(1);
    });
  });

  /**
   * Additive schema with two lines on one base: both percentages apply to the same goods amount
   * (3,15 % and 0,25 % of 1 000,00 = -31,50 and -2,50), not one after the other (compounding would give -2,42).
   */
  test('an additive schema with two lines applies both percentages to the same base', async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.story('Additive compensation schema with two discount lines on one order');
    allure.severity('critical');
    test.setTimeout(20 * 60 * 1000);
    page.setDefaultTimeout(60 * 1000);

    const runId = Date.now();
    const today = contractReferenceDay();
    const contractStart = new Date(today.getFullYear(), today.getMonth(), 1);
    const contractEnd = endOfYear(today);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        productCategories: { GOODS_CATEGORY: {} },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        products: {
          GOODS: { productCategory: 'GOODS_CATEGORY', prices: [{ price: GOODS_PRICE }] },
          DISCOUNT_1: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
          DISCOUNT_2: { type: 'Item', isStocked: false, productCategory: 'GOODS_CATEGORY', prices: [{ price: 1 }] },
        },
      },
    });
    const customer = masterdata.bpartners.CUSTOMER;
    const goods = masterdata.products.GOODS;
    const goodsCategoryId = masterdata.productCategories.GOODS_CATEGORY.id;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    let conditionsId;
    await test.step('1. Setup: additive schema with two lines on one product category, settings, conditions', async () => {
      const transitionId = await createTransition(page, `CG transition ${runId}`, contractStart.getFullYear());
      conditionsId = await createConditions(page, {
        schemaName: `CG additive ${runId}`, goodsCategoryId, additive: true,
        lines: [
          { discountProduct: masterdata.products.DISCOUNT_1, percent: 3.15 },
          { discountProduct: masterdata.products.DISCOUNT_2, percent: 0.25 },
        ],
        transitionId, name: `CG conditions additive ${runId}`,
      });
    });

    await test.step('2. Contract for the partner', async () => {
      await createContractTerm(page, customer, conditionsId, contractStart, contractEnd);
    });

    await test.step('3. One order: -31,50 and -2,50 on the same 1 000,00 goods amount', async () => {
      const orderId = await completeSalesOrder(page, customer, goods, today);
      const lines = await getTabRows(SALES_ORDER_WINDOW_ID, orderId, ORDER_LINE_TAB);
      expect(lines, 'goods line + two discount lines').toHaveLength(3);
      const discount1 = lineOfProduct(lines, masterdata.products.DISCOUNT_1);
      const discount2 = lineOfProduct(lines, masterdata.products.DISCOUNT_2);
      expect(discount1, '3,15 % discount line').toBeTruthy();
      expect(discount2, '0,25 % discount line').toBeTruthy();
      expect(Number(discount1.fieldsByName.LineNetAmt.value)).toBeCloseTo(-31.5, 2);
      expect(Number(discount2.fieldsByName.LineNetAmt.value), 'additive: 0,25 % of 1 000,00, not of the reduced amount').toBeCloseTo(-2.5, 2);
      await expectOrderGridAmount(page, orderId, masterdata.products.DISCOUNT_1, '-31,50');
      await expectOrderGridAmount(page, orderId, masterdata.products.DISCOUNT_2, '-2,50');
      await snap(page, '143-additive-two-discount-lines');
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

/** dd.MM.yyyy, the date format of the de_DE login */
function germanDate(date) {
  const pad = (n) => String(n).padStart(2, '0');
  return `${pad(date.getDate())}.${pad(date.getMonth() + 1)}.${date.getFullYear()}`;
}

/**
 * Number of contract terms whose invoice partner is `partnerId`, read from the backend's contract view
 * (the rendered grid is not counted: it is filled after the container is visible).
 */
async function countContractsOfPartner(page, partnerId) {
  const viewResponse = await page.request.post(`${WEBAPI_BASE_URL}/documentView/${CONTRACT_WINDOW_ID}`, {
    data: { documentType: String(CONTRACT_WINDOW_ID), viewType: 'grid', filters: [] },
    headers: { 'Content-Type': 'application/json' },
  });
  expect(viewResponse.ok(), 'the contract view opens').toBe(true);
  const view = await viewResponse.json();
  const rowsResponse = await page.request.get(`${WEBAPI_BASE_URL}/documentView/${CONTRACT_WINDOW_ID}/${view.viewId}?firstRow=0&pageLength=${Math.max(view.size, 1)}`);
  expect(rowsResponse.ok(), 'the contract view rows are read').toBe(true);
  const rows = (await rowsResponse.json()).result ?? [];
  return rows.filter((row) => lookupKey(row.fieldsByName?.Bill_BPartner_ID?.value) === String(partnerId)).length;
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
 * Assert the label is painted in full: the label cuts overflowing text off with an ellipsis
 * (overflow: hidden), so a cut-off label is wider (scrollWidth) than its visible box (clientWidth).
 */
async function expectLabelNotCutOff(scope, fieldName) {
  const label = scope.locator(`.form-field-${fieldName} > label.form-control-label`).first();
  const { scrollWidth, clientWidth } = await label.evaluate((el) => ({ scrollWidth: el.scrollWidth, clientWidth: el.clientWidth }));
  expect(scrollWidth, `label of ${fieldName} is cut off (text ${scrollWidth}px, visible ${clientWidth}px)`).toBeLessThanOrEqual(clientWidth);
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

/**
 * Type a date and leave the field.
 *
 * The commit is awaited from before the text is typed, not only from the Tab: the date widget
 * PATCHes as soon as the typed text parses as a valid date (DatePicker.handleDateChange, on the
 * input's change), so the PATCH already goes out during `fill`, and the blur on Tab sends no
 * second PATCH. Armed only after `fill`, the wait misses a response that arrives first.
 */
async function fillDate(page, scope, fieldName, date) {
  const text = `${String(date.getDate()).padStart(2, '0')}.${String(date.getMonth() + 1).padStart(2, '0')}.${date.getFullYear()}`;
  const input = scope.locator(`.form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.press('ControlOrMeta+a');
  await withFieldCommit(page, fieldName, async () => {
    await input.fill(text);
    await input.press('Tab');
  });
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

/**
 * Pick the option with the given key in one part of a composed lookup (e.g. the location part
 * of the partner lookup), whose input is rendered as #lookup_<fieldName>, and wait until that key
 * has been committed. When the part offers a single option, the WebUI selects it by itself on the
 * input click and closes the dropdown, so the option is clicked only if the dropdown stays open.
 */
async function selectLookupPartByKey(page, fieldName, key) {
  const input = page.locator(`#lookup_${fieldName} input.input-field`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const option = page.locator(`.input-dropdown-list [data-testid="option-${key}"]`).first();
  let committed = false;
  await withFieldCommit(page, fieldName, async () => {
    const commit = page.waitForResponse((response) => {
      const request = response.request();
      return request.method() === 'PATCH' && Array.isArray(request.postDataJSON())
        && request.postDataJSON().some((change) => change.path === fieldName && lookupKey(change.value) === String(key));
    }, { timeout: SLOW_ACTION_TIMEOUT }).then(() => { committed = true; }, () => {});
    await input.click();
    const optionShown = option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT }).then(() => true, () => false);
    await Promise.race([commit, optionShown]);
    if (!committed) {
      await option.click();
    }
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

// ----------------------------------------------------------------------
// Setup helpers for the contract tests below the first one (all through the WebUI)
// ----------------------------------------------------------------------

/** Today, but never the last day of the year, so that "the next day" is in the same fiscal year. */
function contractReferenceDay() {
  const today = new Date();
  const lastDay = new Date(today.getFullYear(), 11, 31);
  return today >= lastDay ? new Date(today.getFullYear(), 11, 30) : new Date(today.getFullYear(), today.getMonth(), today.getDate());
}

/** The date `days` after `date`, clamped to the end of its year (the transition calendar covers that year only). */
function addDays(date, days) {
  const result = new Date(date.getFullYear(), date.getMonth(), date.getDate() + days);
  const yearEnd = endOfYear(date);
  const yearStart = new Date(date.getFullYear(), 0, 1);
  return result > yearEnd ? yearEnd : result < yearStart ? yearStart : result;
}

function endOfYear(date) {
  return new Date(date.getFullYear(), 11, 31);
}

/** Transition with duration 0 (the contract keeps its entered end date), completed. @returns its id */
async function createTransition(page, name, fiscalYear, { allowCancellation = false } = {}) {
  await openNewRecord(page, TRANSITION_WINDOW_ID);
  await fillText(page, page, 'Name', name);
  const transitionId = await waitForNewRecordId(page, TRANSITION_WINDOW_ID);
  const contractCalendarId = await pickContractCalendarWithYear(transitionId, fiscalYear);
  await selectListByKey(page, page, 'C_Calendar_Contract_ID', contractCalendarId);
  await fillNumber(page, page, 'TermDuration', 0);
  await selectListByKey(page, page, 'TermDurationUnit', DURATION_UNIT_MONTH);
  await fillNumber(page, page, 'TermOfNotice', 0);
  await selectListByKey(page, page, 'TermOfNoticeUnit', DURATION_UNIT_MONTH);
  await waitForRecordSaved(TRANSITION_WINDOW_ID, transitionId, { maxRetries: 20, retryDelayMs: 500 });
  if (allowCancellation) {
    // tab "Wechselkonditionen": a status change to "Gekündigt" without a notice period, as the action "Kündigen" requires
    const modal = await openNewIncludedRow(page, TRANSITION_CHANGE_TAB_ID);
    await selectListByKey(page, modal, 'Action', CONTRACT_CHANGE_ACTION_STATUS_CHANGE);
    await selectListByKey(page, modal, 'ContractStatus', CONTRACT_STATUS_CANCELLED);
    // the deadline already shows 0 (no notice period); the unit is chosen
    await selectListByKey(page, modal, 'DeadLineUnit', DURATION_UNIT_DAY);
    await closeModal(modal);
    const rows = await getTabRows(TRANSITION_WINDOW_ID, transitionId, `AD_Tab-${TRANSITION_CHANGE_TAB_ID}`);
    expect(rows, 'one change condition row').toHaveLength(1);
  }
  await completeDocument(page);
  await expectDocStatus(TRANSITION_WINDOW_ID, transitionId, 'CO');
  return transitionId;
}

/**
 * Schema (with its lines), settings (listing the given order doc type, default the standard sales order) and completed conditions of
 * type compensation group. Percentages are typed with a decimal point, because a decimal comma is dropped
 * by the number fields (known WebUI behaviour).
 * @returns the id of the conditions
 */
async function createConditions(page, { schemaName, goodsCategoryId, additive, lines, transitionId, name, docTypeName = DOCTYPE_STANDARD_ORDER_NAME }) {
  // schema
  await openNewRecord(page, SCHEMA_WINDOW_ID);
  await fillText(page, page, 'Name', schemaName);
  const schemaId = await waitForNewRecordId(page, SCHEMA_WINDOW_ID);
  if (additive) {
    await setCheckbox(page, 'IsAdditive');
    await waitForRecordSaved(SCHEMA_WINDOW_ID, schemaId, { maxRetries: 20, retryDelayMs: 500 });
  }
  let seqNo = 10;
  for (const line of lines) {
    const modal = await openNewIncludedRow(page, SCHEMA_LINE_TAB_ID);
    await fillNumber(page, modal, 'SeqNo', seqNo);
    await selectListByKey(page, modal, 'M_Product_Category_ID', goodsCategoryId);
    await selectLookup(page, modal, 'M_Product_ID', line.discountProduct.productCode);
    await fillNumber(page, modal, 'CompleteOrderDiscount', line.percent);
    await closeModal(modal);
    seqNo += 10;
  }
  const schemaLines = await getTabRows(SCHEMA_WINDOW_ID, schemaId, `AD_Tab-${SCHEMA_LINE_TAB_ID}`);
  expect(schemaLines, `schema ${schemaName} has its lines`).toHaveLength(lines.length);
  expect(schemaLines.map((l) => Number(l.fieldsByName.CompleteOrderDiscount.value)).sort())
    .toEqual(lines.map((l) => l.percent).sort());

  // settings
  await openNewRecord(page, SETTINGS_WINDOW_ID);
  await fillText(page, page, 'Name', `${name} settings`);
  const settingsId = await waitForNewRecordId(page, SETTINGS_WINDOW_ID);
  await selectLookup(page, page, 'C_CompensationGroup_Schema_ID', schemaName);
  await waitForRecordSaved(SETTINGS_WINDOW_ID, settingsId, { maxRetries: 20, retryDelayMs: 500 });
  const docTypeModal = await openNewIncludedRow(page, SETTINGS_DOCTYPE_TAB_ID);
  await selectLookup(page, docTypeModal, 'C_DocType_ID', docTypeName, { exact: true });
  await closeModal(docTypeModal);

  // conditions
  await openNewRecord(page, CONDITIONS_WINDOW_ID);
  await fillText(page, page, 'Name', name);
  const conditionsId = await waitForNewRecordId(page, CONDITIONS_WINDOW_ID);
  await selectListByKey(page, page, 'Type_Conditions', TYPE_CONDITIONS_COMPENSATION_GROUP);
  await expect(page.locator('.form-field-C_CompensationGroup_ContractSettings_ID')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  await selectListByKey(page, page, 'C_CompensationGroup_ContractSettings_ID', settingsId);
  await selectListByKey(page, page, 'C_Flatrate_Transition_ID', transitionId);
  await waitForRecordSaved(CONDITIONS_WINDOW_ID, conditionsId, { maxRetries: 20, retryDelayMs: 500 });
  await completeDocument(page);
  await expectDocStatus(CONDITIONS_WINDOW_ID, conditionsId, 'CO');
  return conditionsId;
}

/**
 * Business-partner action "Erzeuge Vertrag" for the conditions, with the given dates.
 * With `openTerm` (default) the partner's single contract term is opened and its id returned.
 */
async function createContractTerm(page, customer, conditionsId, start, end, { openTerm = true } = {}) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${customer.id}`);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
  const modal = await openAction(page, CREATE_CONTRACT_PROCESS);
  await selectListByKey(page, modal, 'C_Flatrate_Conditions_ID', conditionsId);
  await fillDate(page, modal, 'StartDate', start);
  await fillDate(page, modal, 'EndDate', end);
  await expect(modal.locator('.form-field-IsComplete input[type="checkbox"]').first(), 'the term is completed by the action').toBeChecked();
  const processStarted = page.waitForResponse((r) => r.url().includes('/process/') && r.url().endsWith('/start'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
  await modal.getByTestId('process-modal-start-button').click();
  expect((await processStarted).ok(), '"Erzeuge Vertrag" must run without error').toBe(true);
  await modal.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });
  if (!openTerm) {
    return null;
  }
  await openRelatedDocument({
    dataCy: BPARTNER_TO_CONTRACT_TERM_REFERENCE,
    stepName: 'Business partner - open the created contract term',
    maxRetries: 10,
    retryDelay: 2000,
    refreshOnRetry: true,
  });
  const termId = await waitForNewRecordId(page, CONTRACT_WINDOW_ID);
  await expectDocStatus(CONTRACT_WINDOW_ID, termId, 'CO');
  return termId;
}

/** A menu node (AD_Menu id) with its children down to `depth`, read from the backend's menu tree of the logged-in user. */
async function getMenuNode(page, nodeId, depth = 1) {
  const response = await page.request.get(`${WEBAPI_BASE_URL}/menu/node/${nodeId}?depth=${depth}`);
  expect(response.ok(), `menu node ${nodeId}`).toBe(true);
  return await response.json();
}

/** The unfiltered grid view of the order compensation group window, read from the backend: { size, rows (all of the view's rows) } */
async function readOrderCompensationGroupView(page) {
  const viewResponse = await page.request.post(`${WEBAPI_BASE_URL}/documentView/${ORDER_COMPENSATION_GROUP_WINDOW_ID}`, {
    data: { documentType: String(ORDER_COMPENSATION_GROUP_WINDOW_ID), viewType: 'grid', filters: [] },
    headers: { 'Content-Type': 'application/json' },
  });
  expect(viewResponse.ok(), 'the order compensation group view opens').toBe(true);
  const view = await viewResponse.json();
  const rowsResponse = await page.request.get(`${WEBAPI_BASE_URL}/documentView/${ORDER_COMPENSATION_GROUP_WINDOW_ID}/${view.viewId}?firstRow=0&pageLength=${Math.max(view.size, 1)}`);
  expect(rowsResponse.ok(), 'the order compensation group view rows are read').toBe(true);
  return { size: view.size, rows: (await rowsResponse.json()).result ?? [] };
}

/** Creates a sales order for the customer with one goods line, dated `date`, completes it. @returns the order id */
async function completeSalesOrder(page, customer, goods, date) {
  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  const orderId = await SalesOrderPage.selectCustomer(customer.bpartnerCode);
  await fillDateIfChanged(page, page, 'DateOrdered', date);
  await SalesOrderPage.addOrderLine({ product: goods.productCode, quantity: 1, recordId: orderId });
  await SalesOrderPage.complete();
  await expectDocStatus(SALES_ORDER_WINDOW_ID, orderId, 'CO');
  return orderId;
}

/**
 * Completes the draft purchase order `orderId` (created earlier, so that its document type is known): sets the date, adds
 * one goods line, completes it.
 */
async function completePurchaseOrder(page, orderId, goods, date) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${PURCHASE_ORDER_WINDOW_ID}/${orderId}`);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
  await fillDateIfChanged(page, page, 'DateOrdered', date);
  await PurchaseOrderPage.addOrderLine({ product: goods.productCode, quantity: 1, recordId: orderId });
  await PurchaseOrderPage.complete();
  await expectDocStatus(PURCHASE_ORDER_WINDOW_ID, orderId, 'CO');
}

/** Like fillDate, but sends nothing when the field already shows the date (a PATCH would never come). */
async function fillDateIfChanged(page, scope, fieldName, date) {
  const text = `${String(date.getDate()).padStart(2, '0')}.${String(date.getMonth() + 1).padStart(2, '0')}.${date.getFullYear()}`;
  const input = scope.locator(`.form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  if ((await input.inputValue()).trim().startsWith(text)) {
    return;
  }
  await fillDate(page, scope, fieldName, date);
}

/**
 * Every row of the group's order-lines tab shows, in each of the given columns (default: the nine), the value of the same
 * C_OrderLine (same row key) in the order window.
 * (This does NOT catch a view column mapped from a sibling order-line column with the same value in the fixture: the test
 * lines have C_UOM_ID = Price_UOM_ID and PriceEntered = PriceActual. That 1:1 mapping is guarded by the view DDL, a plain
 * `SELECT ol.<column>`, not by this check.)
 */
function expectValuesEqualOrderLines(groupLines, orderLines, columns = ORDER_COMPENSATION_GROUP_LINES_SHOWN_COLUMNS) {
  expect(groupLines.length, 'the group has order lines to compare').toBeGreaterThan(0);
  expect(columns.length, 'columns to compare').toBeGreaterThan(0);
  for (const line of groupLines) {
    const orderLine = orderLines.find((row) => String(row.rowId) === String(line.rowId));
    expect(orderLine, `the same C_OrderLine (${line.rowId}) in the order window`).toBeTruthy();
    for (const column of columns) {
      // both rows must carry the column, else a missing column on both sides would compare undefined with undefined and pass
      expect(line.fieldsByName[column], `order line ${line.rowId}: group row carries ${column}`).toBeTruthy();
      expect(orderLine.fieldsByName[column], `order line ${line.rowId}: order window row carries ${column}`).toBeTruthy();
      expect(line.fieldsByName[column].value, `order line ${line.rowId}: value of ${column} equals the order window's`).toEqual(orderLine.fieldsByName[column].value);
    }
  }
}

/** The row of `lines` (order-line rows) whose product is `product` (goods or discount). */
function lineOfProduct(lines, product) {
  return lines.find((l) => lookupKey(l.fieldsByName.M_Product_ID.value) === String(product.id));
}

/**
 * Right-click a grid cell and choose "Zoom into" from the context menu; the target opens in a new browser tab, which is
 * returned. `beforeZoom` (optional) runs with the menu open, before the item is clicked.
 */
async function zoomIntoFromCell(page, cell, beforeZoom) {
  await cell.click();
  await cell.click({ button: 'right' });
  const contextMenu = page.locator('.context-menu');
  await contextMenu.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const zoomInto = contextMenu.locator('.context-menu-item').filter({ has: page.locator('.meta-icon-share') }).first();
  if (beforeZoom) {
    await beforeZoom();
  }
  const popupOpened = page.context().waitForEvent('page', { timeout: SLOW_ACTION_TIMEOUT });
  await zoomInto.click();
  return popupOpened;
}

/** The discount line, as the operator sees it in the order-line grid. */
async function expectOrderGridAmount(page, orderId, discountProduct, amountText) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}`);
  const orderLineTab = page.getByTestId(`tab-${ORDER_LINE_TAB}`);
  await orderLineTab.click();
  await expect(orderLineTab.locator('a.nav-link')).toHaveClass(/active/);
  const row = page.locator('table tbody tr')
    .filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: discountProduct.productCode }) });
  await expect(row).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
  await expect(row.locator('[data-cy="cell-LineNetAmt"]')).toContainText(amountText);
  await row.scrollIntoViewIfNeeded();
}

/**
 * Single view of a line row on the group's order-lines tab: select the row, open it with Alt+E ("Erweiterte Erfassung").
 * It shows exactly the nine order-line fields of the tab, in order, all read-only, and none of the seven that need order context.
 */
async function expectLineRowSingleView(page, row, product, label) {
  // a context menu left open by an earlier right-click (e.g. "Zoom into") would intercept the click
  await page.mouse.click(2, 2);
  await expect(page.locator('.context-menu')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
  await row.locator('[data-cy="cell-M_Product_ID"]').click();
  await page.keyboard.press('Alt+e');
  const modal = page.locator('.panel-modal').first();
  await modal.locator('.form-field-Line').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const fields = await modal.locator('.form-group[class*="form-field-"]').evaluateAll((groups) => groups.map((group) => ({
    name: Array.from(group.classList).find((cssClass) => cssClass.startsWith('form-field-')).substring('form-field-'.length),
    enabledInputs: group.querySelectorAll('input:not([disabled])').length,
    inputs: group.querySelectorAll('input').length,
  })));
  expect(fields.map((field) => field.name), 'single view: the nine order-line fields, in order').toEqual(ORDER_COMPENSATION_GROUP_LINES_SHOWN_COLUMNS);
  for (const field of fields) {
    expect(field.inputs, `single view: field ${field.name} has an input`).toBeGreaterThan(0);
    expect(field.enabledInputs, `single view: field ${field.name} is read-only`).toBe(0);
  }
  for (const hidden of ORDER_COMPENSATION_GROUP_LINES_HIDDEN_COLUMNS) {
    await expect(modal.locator(`.form-field-${hidden}`), `single view: field ${hidden} not shown`).toHaveCount(0);
  }
  await expectLabel(modal, 'Line', DE.orderCompensationGroupLineNo);
  await expect(modal.locator('.form-field-M_Product_ID input').first()).toHaveValue(new RegExp(product.productCode));
  await snap(page, `order-line-single-view-${label}`);
  await page.keyboard.press('Escape');
  await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
}

test.describe('Order compensation group window: window health', () => {
  test('window health check of the order compensation group window reports no missing context variables', async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.story('Order compensation group window: window health check without known-missing entries');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({ request: { login: { user: { language: LANGUAGE } } } });
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // POST /window/health for the window, with the context-variable check on
    const response = await page.request.post(`${WEBAPI_BASE_URL}/window/health`, {
      data: { onlyAdWindowIds: [ORDER_COMPENSATION_GROUP_WINDOW_ID], checkContextVariables: true },
    });
    expect(response.ok(), `HTTP ${response.status()} from /window/health`).toBe(true);
    const health = await response.json();
    const missing = health.contextVariables?.missing ?? {};
    const offending = Object.keys(missing).filter((key) => key.includes(String(ORDER_COMPENSATION_GROUP_WINDOW_ID)) || key.includes(String(ORDER_COMPENSATION_GROUP_LINES_TAB_ID)));
    expect(offending, 'no 542196 / 549518 key in contextVariables.missing').toEqual([]);
    expect(health.countErrors, 'window health countErrors').toBe(0);
  });
});

/**
 * Step 4c sort check: the order document numbers (captions of C_Order_ID) of the order compensation group view, in list
 * order, are in the order the database gives for `ORDER BY C_Order.DocumentNo DESC`.
 * - All captions made of digits only are compared with each other, across lengths and number sequences. For such strings
 *   the database's libc collation orders character by character, as the JS compare below does (e.g. '9999' above '10000',
 *   purchase '820471' above sales '0050'). Within one number sequence and one length a later number is also a later order
 *   id, so only the pairs across sequences or across lengths (a digit rollover, '9999' / '10000') tell "sorted by document
 *   number" from "sorted by C_Order_ID"; those pairs are kept.
 * - Captions with any other character are compared with the others of the same shape (digits masked: 'SO-0012' and
 *   'SO-0047' are both 'SO-0000'): same length, same non-digit characters at the same places, so the libc collation, which
 *   ignores punctuation at its first level and is case-insensitive there, decides by the first differing digit, as the JS
 *   compare does. Only pairs of different shapes (or a non-digit caption against a digit-only one) are left out: for them
 *   a JS compare is not the database's order (de_DE.UTF-8: 'A-2' sorts between 'A1' and 'a3').
 * The test's own orders are checked to be digit-only, so they are always in the compared set.
 */
function expectOrderNosDescending(orderNos) {
  const descending = (a, b) => (a < b ? 1 : a > b ? -1 : 0);
  const digitOnlyOrderNos = orderNos.filter((caption) => /^\d+$/.test(caption));
  expect(digitOnlyOrderNos.length, 'at least the test orders have digit-only document numbers').toBeGreaterThanOrEqual(2);
  expect(digitOnlyOrderNos, 'digit-only order document numbers, all lengths together: descending').toEqual([...digitOnlyOrderNos].sort(descending));
  const otherOrderNosByShape = new Map();
  for (const caption of orderNos.filter((c) => !/^\d+$/.test(c))) {
    const shape = caption.replace(/\d/g, '0');
    otherOrderNosByShape.set(shape, [...(otherOrderNosByShape.get(shape) ?? []), caption]);
  }
  for (const [shape, sameShape] of otherOrderNosByShape) {
    expect(sameShape, `order document numbers of shape '${shape}': descending`).toEqual([...sameShape].sort(descending));
  }
}

// (compared by what the user sees: caption, target window, count; the internal name of a generic entry carries the
// name of its source table, which differs between the line table and its view)
/**
 * Activates the invoice-candidate relation types (INVOICE_CANDIDATE_RELATION_TYPE_IDS, see there) through the WebUI window
 * "Relationsart" as System Administrator, in a request context of their own (the page's session stays as it is).
 * Returns the function that puts every changed one back to inactive. A relation type that does not exist is skipped.
 */
async function activateInvoiceCandidateRelationTypes() {
  const api = await playwrightRequest.newContext();
  try {
    const authResponse = await api.post(`${WEBAPI_BASE_URL}/login/authenticate`, { data: { username: 'metasfresh', password: 'metasfresh' } });
    const auth = await authResponse.json();
    const systemRole = (auth.roles ?? []).find((role) => String(role.roleId) === '0');
    expect(systemRole, 'the test user has the System Administrator role').toBeTruthy();
    expect((await api.post(`${WEBAPI_BASE_URL}/login/loginComplete`, { data: systemRole })).ok(), 'login as System Administrator').toBe(true);

    const setActive = async (relationTypeId, isActive) => {
      const response = await api.patch(`${WEBAPI_BASE_URL}/window/${RELATION_TYPES_WINDOW_ID}/${relationTypeId}`, { data: [{ op: 'replace', path: 'IsActive', value: isActive }] });
      expect(response.ok(), `relation type ${relationTypeId}: set IsActive=${isActive}, HTTP ${response.status()}`).toBe(true);
    };
    const changed = [];
    // puts back every relation type changed so far, also when one of them fails
    const restore = async () => {
      const failures = [];
      for (const relationTypeId of changed) {
        try {
          await setActive(relationTypeId, false);
        } catch (error) {
          failures.push(`${relationTypeId}: ${error.message}`);
        }
      }
      await api.dispose();
      expect(failures, 'relation types put back to inactive').toEqual([]);
    };
    try {
      for (const relationTypeId of INVOICE_CANDIDATE_RELATION_TYPE_IDS) {
        const response = await api.get(`${WEBAPI_BASE_URL}/window/${RELATION_TYPES_WINDOW_ID}/${relationTypeId}`);
        if (!response.ok()) {
          // not there (the relation types of the view row exist only with their migration); any other status is a failure
          expect([404],`relation type ${relationTypeId}: read, HTTP ${response.status()}`).toContain(response.status());
          continue;
        }
        const wasActive = (await response.json())[0].fieldsByName.IsActive.value === true;
        if (!wasActive) {
          await setActive(relationTypeId, true);
          changed.push(relationTypeId);
        }
      }
    } catch (error) {
      await restore();
      throw error;
    }
    return restore;
  } catch (error) {
    await api.dispose();
    throw error;
  }
}

function relatedDocumentsComparable(references) {
  return references
    .filter((reference) => !RELATED_DOCUMENTS_LEFT_OUT_WINDOW_IDS.includes(String(reference.targetWindowId)))
    .map((reference) => `${reference.caption}|${reference.targetWindowId}|${reference.documentsCount}`)
    .sort();
}

/**
 * R-REL: the related documents of a line row on the group's order-lines tab equal those of the same order line in the
 * order window (the expected side), windows 270, 540394, 363 left out on both sides. Each read is a complete references
 * stream (getDocumentReferences fails without its COMPLETED event). The entries of the order line come from async work
 * after the order is completed, so the poll accepts the comparison only when
 * - the expected list contains the entry of `requiredTargetWindowId`, the schedule that the line reaches only through the
 *   generic zoom of its key column C_OrderLine_ID (a list read before that entry exists, e.g. only "Bestand pro Woche",
 *   is never accepted, so a view row that loses the generic zoom fails here), and
 * - the expected list has stayed the same for at least STABLE_RELATED_DOCUMENTS_MS (1 s) over successive reads. The other
 *   entries of the line (e.g. "Material Cockpit Detail") arrived 40-230 ms after the schedule in local runs, so a list
 *   read in that gap changes again before it counts as stable (a longer gap would still slip through: best effort), and
 * - both lists show the entry `requiredCaption` (the invoice-candidate entry; the test has activated its relation types), and
 * - the actual list equals it.
 */
async function expectRelatedDocumentsEqualOrderLine({ readExpected, readActual, requiredTargetWindowId, requiredCaption, what }) {
  let previousExpected = null;
  let expectedUnchangedSince = 0;
  await expect.poll(async () => {
    const expectedReferences = await readExpected();
    const actual = relatedDocumentsComparable(await readActual());
    const expected = relatedDocumentsComparable(expectedReferences);
    const hasRequired = expectedReferences.some((reference) => String(reference.targetWindowId) === String(requiredTargetWindowId));
    // by name, on both sides, before the lists are compared: the order line and the view row each list the invoice-candidate entry
    const lacksRequiredCaption = {
      orderLine: !expected.some((entry) => entry.startsWith(`${requiredCaption} (#`)),
      lineRowOfView: !actual.some((entry) => entry.startsWith(`${requiredCaption} (#`)),
    };
    if (previousExpected === null || JSON.stringify(previousExpected) !== JSON.stringify(expected)) {
      expectedUnchangedSince = Date.now();
    }
    const isStable = Date.now() - expectedUnchangedSince >= STABLE_RELATED_DOCUMENTS_MS;
    previousExpected = expected;
    return hasRequired && isStable && !lacksRequiredCaption.orderLine && !lacksRequiredCaption.lineRowOfView && JSON.stringify(actual) === JSON.stringify(expected)
      ? 'equal'
      : JSON.stringify({ hasRequired, isStable, requiredCaption, lacksRequiredCaption, expected, actual });
  }, {
    message: `related documents of ${what} (without windows 270, 540394, 363) equal those of the order line, read once the order line lists window ${requiredTargetWindowId} and its list is unchanged for ${STABLE_RELATED_DOCUMENTS_MS} ms; both lists show ${requiredCaption}`,
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  }).toBe('equal');
}
