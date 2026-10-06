import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { getFieldData, getTabRows, waitForRecordSaved } from '../utils/WebAPIValidation';
import {
  closeModal,
  completeDocument,
  CONDITIONS_WINDOW_ID,
  CONTRACT_WINDOW_ID,
  createCompletedTransition,
  createRefundConditions,
  createTermForPartner,
  expectDocStatus,
  fillDate,
  fillNumber,
  getRefundConfigRows,
  lookupKey,
  openNewIncludedRow,
  openNewRecord,
  openRecord,
  REFUND_CONFIG_TAB_ID,
  REFUND_PACKING_OPTION_TAB_ID,
  selectFirstListOption,
  selectListByKey,
  selectLookupByKey,
  selectLookupPartByKey,
  setCheckbox,
  TYPE_CONDITIONS_REFUND,
  waitForNewRecordId,
  withFieldCommit,
} from '../utils/ContractWindowHelpers';

/**
 * Refund contracts (Rückvergütung) set up in the WebUI.
 *
 * Vertragsbedingungen (540113), tab "Rückvergütung" (C_Flatrate_RefundConfig):
 *   - a refund line can be based on a product category instead of a product; then the bonus product
 *     (the product of the credit memo line) is mandatory;
 *   - the bonus goes to the invoice partner (default) or to the shipment partner (BonusRecipient);
 *   - with "filter by packaging" (IsPackingOptionFiltered) only the packing materials listed in the new
 *     tab "Rückvergütung Verpackungsoption" count;
 *   - all refund lines of one condition share one product category (AD_Message 545897).
 * Verträge (540359): a refund term needs no product; entered in the window it saves and gets its
 * DocumentNo from the sequence.
 *
 * Every value is asserted on the record read back from the server, and the rendered form/grid is checked
 * through ColumnNames / data-testids only. The rejection message is compared with the AD_Message text in
 * the login language (see MSG_SAME_PRODUCT_CATEGORY).
 */

const LANGUAGE = 'de_DE';

// Ref-list keys
const BONUS_RECIPIENT_INVOICE_PARTNER = 'I'; // default
const BONUS_RECIPIENT_SHIPMENT_PARTNER = 'S';
const REFUND_MODE_TIERED = 'T'; // default
const REFUND_BASE_PERCENTAGE = 'P'; // default

// AD_Message 545897 (de.metas.constracts.refund.C_Flatrate_RefundConfig_SameProductCategory), texts from
// migration 5827940. The message itself is under test, so its text is asserted; it cannot be read through the
// WebUI instead (the test role has no access to the system window "Meldung", and the error the WebUI receives
// carries only the translated text), hence one text per login language.
const MSG_SAME_PRODUCT_CATEGORY = {
  de_DE: 'Alle Rückvergütungszeilen einer Vertragsbedingung müssen dieselbe Produktkategorie haben.',
  en_US: 'All refund lines of a contract condition must have the same product category.',
};

const REFUND_PERCENT = 3;

test.describe('Refund contracts: refund lines in Vertragsbedingungen and product-less refund terms in Verträge', () => {
  test.beforeEach(async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.severity('critical');
    test.setTimeout(6 * 60 * 1000);
    page.setDefaultTimeout(60 * 1000);
    // leaving an included-row modal whose row the server did not save asks "Do you really want to leave?"
    page.on('dialog', async (dialog) => {
      console.log(`[INFO] native ${dialog.type()} dialog accepted: ${dialog.message()}`);
      await dialog.accept();
    });
  });

  test('a category-based refund line with bonus product, shipment-partner recipient and packaging filter is saved, with its packing option', async ({ page }) => {
    allure.story('Refund line: category base, bonus product, bonus recipient and packaging filter');
    const runId = Date.now();
    const packingMaterialKey = `PM${runId}`;
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        productCategories: { GOODS_CATEGORY: {} },
        products: {
          GOODS: { productCategory: 'GOODS_CATEGORY', prices: [{ price: 10 }] },
          BONUS: { type: 'Service', prices: [{ price: 1 }] },
          [packingMaterialKey]: { prices: [{ price: 1 }] },
        },
        // a TU packing instruction of the goods with a packing material, so that the material exists
        packingInstructions: { GOODS_PI: { tu: 'GOODS_TU', product: 'GOODS', qtyCUsPerTU: 10, tuPackingMaterial: packingMaterialKey } },
      },
    });
    const goodsCategoryId = masterdata.productCategories.GOODS_CATEGORY.id;
    const bonus = masterdata.products.BONUS;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const transitionId = await test.step('Vertrags-Übergang: a completed 12-month transition', async () =>
      await createCompletedTransition(page, { name: `Refund transition ${runId}`, fiscalYear: new Date().getFullYear(), termDurationMonths: 12 }));

    let conditionsId;
    await test.step('Vertragsbedingungen: refund conditions show the refund and packaging-option tabs', async () => {
      conditionsId = await createRefundConditions(page, { name: `Refund conditions ${runId}`, transitionId });
      await openRecord(page, CONDITIONS_WINDOW_ID, conditionsId);
      await expect(page.getByTestId(`tab-AD_Tab-${REFUND_CONFIG_TAB_ID}`)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.getByTestId(`tab-AD_Tab-${REFUND_PACKING_OPTION_TAB_ID}`)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Rückvergütung: a line with category, bonus product, shipment partner and packaging filter', async () => {
      const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
      // without a product, the bonus product is mandatory and the product is not
      await expect(modal.locator('.form-field-Bonus_Product_ID .input-mandatory'), 'Bonusprodukt is mandatory without a product').toHaveCount(1);
      await expect(modal.locator('.form-field-M_Product_ID .input-mandatory'), 'Produkt is not mandatory').toHaveCount(0);

      await selectListByKey(page, modal, 'M_Product_Category_ID', goodsCategoryId);
      await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
      await selectListByKey(page, modal, 'BonusRecipient', BONUS_RECIPIENT_SHIPMENT_PARTNER);
      await setCheckbox(page, modal, 'IsPackingOptionFiltered');
      await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
      await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
      await closeModal(modal);
    });

    let refundConfigId;
    await test.step('the line is saved with the entered values', async () => {
      const rows = await getRefundConfigRows(conditionsId);
      expect(rows, 'exactly one refund line').toHaveLength(1);
      const line = rows[0].fieldsByName;
      refundConfigId = String(rows[0].rowId);
      expect(lookupKey(line.M_Product_ID?.value), 'no product').toBeNull();
      expect(lookupKey(line.M_Product_Category_ID.value)).toBe(String(goodsCategoryId));
      expect(lookupKey(line.Bonus_Product_ID.value)).toBe(String(bonus.id));
      expect(lookupKey(line.BonusRecipient.value)).toBe(BONUS_RECIPIENT_SHIPMENT_PARTNER);
      expect(line.IsPackingOptionFiltered.value).toBe(true);
      expect(lookupKey(line.RefundBase.value)).toBe(REFUND_BASE_PERCENTAGE);
      expect(Number(line.RefundPercent.value)).toBe(REFUND_PERCENT);

      // the new settings are columns of the refund grid, and the row shows the bonus product
      for (const column of ['M_Product_Category_ID', 'Bonus_Product_ID', 'BonusRecipient', 'IsPackingOptionFiltered']) {
        await expect(page.locator(`th[data-testid="column-${column}"]`), `grid column ${column}`).toHaveCount(1);
      }
      await expect(page.locator('.tab-pane td[data-cy="cell-Bonus_Product_ID"]').first()).toContainText(bonus.productName);
    });

    await test.step('Rückvergütung Verpackungsoption: a packing material for the filtered line', async () => {
      const modal = await openNewIncludedRow(page, REFUND_PACKING_OPTION_TAB_ID);
      await selectListByKey(page, modal, 'C_Flatrate_RefundConfig_ID', refundConfigId);

      const input = modal.locator('.form-field-M_HU_PackingMaterial_ID input').first();
      await input.click();
      // the packing material is named after the per-run product key of the masterdata request
      const option = page.locator('.input-dropdown-list .input-dropdown-list-option').filter({ hasText: `${packingMaterialKey}_` }).first();
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const packingMaterialId = (await option.getAttribute('data-testid')).substring('option-'.length);
      await withFieldCommit(page, 'M_HU_PackingMaterial_ID', () => option.click(), packingMaterialId);
      await closeModal(modal);

      const options = await getTabRows(CONDITIONS_WINDOW_ID, conditionsId, `AD_Tab-${REFUND_PACKING_OPTION_TAB_ID}`);
      expect(options, 'exactly one packing option').toHaveLength(1);
      expect(lookupKey(options[0].fieldsByName.C_Flatrate_RefundConfig_ID.value)).toBe(refundConfigId);
      expect(lookupKey(options[0].fieldsByName.M_HU_PackingMaterial_ID.value)).toBe(packingMaterialId);
    });

    await test.step('after a reload, the refund grid still shows the line', async () => {
      await page.reload();
      await page.getByTestId(`tab-AD_Tab-${REFUND_CONFIG_TAB_ID}`).click();
      await expect(page.locator('.tab-pane td[data-cy="cell-Bonus_Product_ID"]')).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.tab-pane td[data-cy="cell-Bonus_Product_ID"]').first()).toContainText(bonus.productName);
    });
  });

  test('a refund line without product needs a bonus product, and all refund lines of a condition share one product category', async ({ page }) => {
    allure.story('Refund line without product: bonus product mandatory; one product category per condition');
    const runId = Date.now();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        productCategories: { CATEGORY_A: {}, CATEGORY_B: {} },
        products: { BONUS: { type: 'Service', prices: [{ price: 1 }] } },
      },
    });
    const categoryAId = String(masterdata.productCategories.CATEGORY_A.id);
    const categoryBId = String(masterdata.productCategories.CATEGORY_B.id);
    const bonus = masterdata.products.BONUS;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const transitionId = await createCompletedTransition(page, { name: `Refund transition ${runId}`, fiscalYear: new Date().getFullYear(), termDurationMonths: 12 });
    const conditionsId = await createRefundConditions(page, { name: `Refund conditions ${runId}`, transitionId });

    await test.step('a line with neither product nor bonus product is not saved', async () => {
      const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
      await selectListByKey(page, modal, 'M_Product_Category_ID', categoryAId);
      await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
      await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
      await expect(modal.locator('.form-field-Bonus_Product_ID .input-mandatory'), 'Bonusprodukt is mandatory without a product').toHaveCount(1);
      await expect(modal.locator('.form-field-M_Product_ID .input-mandatory'), 'Produkt is not mandatory').toHaveCount(0);
      await closeModal(modal); // the native "leave?" dialog is accepted

      expect(await getRefundConfigRows(conditionsId), 'the incomplete line is not saved').toHaveLength(0);
    });

    await test.step('a line with a category and a bonus product, but no product, is saved', async () => {
      const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
      await selectListByKey(page, modal, 'M_Product_Category_ID', categoryAId);
      await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
      await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
      await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
      await expect(modal.locator('.window-indicator-container .bar.error')).toHaveCount(0);
      await closeModal(modal);

      const rows = await getRefundConfigRows(conditionsId);
      expect(rows, 'exactly one refund line').toHaveLength(1);
      const line = rows[0].fieldsByName;
      expect(lookupKey(line.M_Product_ID?.value), 'no product').toBeNull();
      expect(lookupKey(line.M_Product_Category_ID.value)).toBe(categoryAId);
      expect(lookupKey(line.Bonus_Product_ID.value)).toBe(String(bonus.id));
      expect(lookupKey(line.BonusRecipient.value), 'default recipient').toBe(BONUS_RECIPIENT_INVOICE_PARTNER);
      expect(line.IsPackingOptionFiltered.value, 'packaging filter off by default').toBe(false);
      expect(lookupKey(line.RefundMode.value), 'default refund mode').toBe(REFUND_MODE_TIERED);
    });

    await test.step('a second line of another product category is rejected with the message', async () => {
      const expectedMessage = MSG_SAME_PRODUCT_CATEGORY[LANGUAGE];
      const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
      await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
      await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
      await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
      await selectListByKey(page, modal, 'M_Product_Category_ID', categoryBId);

      const message = modal.locator('.window-indicator-container .message-bar .text');
      await expect(modal.locator('.window-indicator-container .bar.error')).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(message).toContainText(expectedMessage, { timeout: SLOW_ACTION_TIMEOUT });
      await closeModal(modal); // the native "leave?" dialog is accepted

      const rows = await getRefundConfigRows(conditionsId);
      expect(rows, 'still exactly one refund line').toHaveLength(1);
      expect(lookupKey(rows[0].fieldsByName.M_Product_Category_ID.value)).toBe(categoryAId);
    });

    await test.step('the conditions with the product-less line complete', async () => {
      await openRecord(page, CONDITIONS_WINDOW_ID, conditionsId);
      await completeDocument(page);
      await expectDocStatus(CONDITIONS_WINDOW_ID, conditionsId, 'CO');
    });
  });

  test('a refund term without product, entered in Verträge, saves with a document number from the sequence', async ({ page }) => {
    allure.story('Refund term without product entered in Verträge');
    const runId = Date.now();
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        productCategories: { GOODS_CATEGORY: {} },
        products: { BONUS: { type: 'Service', prices: [{ price: 1 }] } },
      },
    });
    const customer = masterdata.bpartners.CUSTOMER;
    const bonus = masterdata.products.BONUS;
    const contractStart = new Date(new Date().getFullYear(), new Date().getMonth(), 1);

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const transitionId = await createCompletedTransition(page, { name: `Refund transition ${runId}`, fiscalYear: contractStart.getFullYear(), termDurationMonths: 12 });
    const conditionsId = await createRefundConditions(page, { name: `Refund conditions ${runId}`, transitionId });
    await test.step('completed refund conditions with one category-based line', async () => {
      const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
      await selectListByKey(page, modal, 'M_Product_Category_ID', masterdata.productCategories.GOODS_CATEGORY.id);
      await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
      await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
      await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
      await closeModal(modal);
      expect(await getRefundConfigRows(conditionsId)).toHaveLength(1);
      await completeDocument(page);
      await expectDocStatus(CONDITIONS_WINDOW_ID, conditionsId, 'CO');
    });

    // A new partner has no contract data (C_Flatrate_Data) yet, so Verträge cannot offer it as
    // Vertragspartner; "Erzeuge Vertrag" creates the data together with a first term.
    const firstTermId = await test.step('Geschäftspartner "Erzeuge Vertrag": a first refund term, without product', async () => {
      const termId = await createTermForPartner(page, { bpartnerId: customer.id, conditionsId, startDate: contractStart });
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'M_Product_ID')).value), 'product-less term').toBeNull();
      return termId;
    });

    await test.step('Verträge: a refund term entered in the window, without product, saves with a sequence number', async () => {
      const flatrateDataId = lookupKey((await getFieldData(CONTRACT_WINDOW_ID, firstTermId, 'C_Flatrate_Data_ID')).value);
      const billLocationId = lookupKey((await getFieldData(CONTRACT_WINDOW_ID, firstTermId, 'Bill_Location_ID')).value);
      const firstTermDocumentNo = String((await getFieldData(CONTRACT_WINDOW_ID, firstTermId, 'DocumentNo')).value);

      await openNewRecord(page, CONTRACT_WINDOW_ID);
      const termId = await waitForNewRecordId(page, CONTRACT_WINDOW_ID);
      await selectListByKey(page, page, 'C_Flatrate_Data_ID', flatrateDataId);
      await selectListByKey(page, page, 'C_Flatrate_Conditions_ID', conditionsId);
      // the contract data set the invoice partner (callout); its location is the second part of that lookup
      await selectLookupPartByKey(page, 'Bill_Location_ID', billLocationId);
      await fillDate(page, page, 'StartDate', contractStart);

      // a refund term needs no product: the field is not mandatory, and the term saves without it
      await expect(page.locator('.form-field-M_Product_ID .input-mandatory'), 'Produkt is not mandatory for a refund term').toHaveCount(0);
      await waitForRecordSaved(CONTRACT_WINDOW_ID, termId, { maxRetries: 20, retryDelayMs: 500 });

      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'Type_Conditions')).value)).toBe(TYPE_CONDITIONS_REFUND);
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'Bill_BPartner_ID')).value)).toBe(String(customer.id));
      expect(lookupKey((await getFieldData(CONTRACT_WINDOW_ID, termId, 'M_Product_ID')).value), 'product-less term').toBeNull();

      const documentNo = String((await getFieldData(CONTRACT_WINDOW_ID, termId, 'DocumentNo')).value ?? '').trim();
      console.log(`[INFO] term ${termId}: DocumentNo=${documentNo} (first term: ${firstTermDocumentNo})`);
      // the sequence may carry a prefix/suffix, so only what a missing number would look like is excluded
      expect(documentNo, 'DocumentNo is filled').not.toBe('');
      expect(documentNo, 'DocumentNo is not the former default "0"').not.toBe('0');
      expect(documentNo.startsWith('<') && documentNo.endsWith('>'), `DocumentNo "${documentNo}" is not a preliminary "<...>" number`).toBe(false);
      expect(documentNo, 'each term gets its own number').not.toBe(firstTermDocumentNo);

      // the saved term, reloaded, shows that number
      await openRecord(page, CONTRACT_WINDOW_ID, termId);
      await expect(page.locator('.form-field-DocumentNo input').first()).toHaveValue(documentNo, { timeout: SLOW_ACTION_TIMEOUT });
    });
  });
});
