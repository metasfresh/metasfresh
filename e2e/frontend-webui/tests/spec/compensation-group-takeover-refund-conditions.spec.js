import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { getIncludedRecordData, getTabRows, waitForRecordSaved } from '../utils/WebAPIValidation';
import {
  closeModal,
  CONDITIONS_WINDOW_ID,
  createCompletedTransition,
  createRefundConditions,
  fillNumber,
  fillText,
  lookupKey,
  openNewIncludedRow,
  openNewRecord,
  openRecord,
  recordRejectedSaveReasons,
  selectListByKey,
  selectLookupByKey,
  waitForNewRecordId,
} from '../utils/ContractWindowHelpers';

/**
 * Kompensationsgruppen-Vertragseinstellungen (542194), tab "Übernahme aus dem Verkaufsauftrag" (549509).
 *
 * A take-over record carries
 *   - "Nachkommastellen der Summe" (PercentagePrecision): the decimals the summed percentage of the take-over line
 *     is rounded to; default 2, shown in the grid and in the record's form;
 *   - "Rückvergütungsbedingungen (Kunde)": a Labels list of contract conditions whose search offers only conditions
 *     of type refund (Rückvergütung). One refund condition may be listed on at most one take-over record of the same
 *     settings; listing it on a second one is refused with a message and not saved. The refused value stays in the form
 *     (with the message) until the operator removes it; then the record saves without it.
 *
 * Every record is created by the test (unique names per run); every stored value is read back from the server.
 * The take-over record's form is opened from the row's context menu ("advanced edit" of that row): the Alt+E chord
 * belongs to the window's header document and would open the settings' form instead.
 *
 * German captions are asserted on purpose (the captions are part of the change), so the login language is de_DE.
 */

const LANGUAGE = 'de_DE';

const SCHEMA_WINDOW_ID = 540415; // Kompensationsgruppe Schema
const SETTINGS_WINDOW_ID = 542194; // Kompensationsgruppen-Vertragseinstellungen
const TAKEOVER_TAB_ID = 549509; // Übernahme aus dem Verkaufsauftrag (C_CompensationGroup_ContractSettings_TakeOver)

const TYPE_CONDITIONS_SUBSCRIPTION = 'Subscr'; // Abonnement: a contract type that is not refund
const ON_TERM_EXTEND_CALCULATE_PRICE = 'Ca'; // OnFlatrateTermExtend, the column default

// the Labels field of the customer refund conditions (Labels_ + the labels tab's InternalName)
const REFUND_CONDITIONS_FIELD = 'Labels_C_CompensationGroup_ContractSettings_TakeOver_RefundCond';

// AD_Message of the uniqueness rule (one refund condition per settings)
const NOT_UNIQUE_AD_MESSAGE = 'ContractCompensationGroup_TakeOverRefundCondNotUnique';

const DEFAULT_DECIMALS = 2;
const CHANGED_DECIMALS = 3;

// de_DE captions, from AD_Tab_Trl / AD_Element_Trl / AD_Message (de_DE)
const DE = {
  takeOverTab: 'Übernahme aus dem Verkaufsauftrag',
  decimals: 'Nachkommastellen der Summe',
  refundConditions: 'Rückvergütungsbedingungen (Kunde)',
  notUniqueMessage: (conditionName) =>
    `Die Rückvergütungsbedingung ${conditionName} ist bereits in einem anderen Übernahme-Datensatz dieser Einstellungen eingetragen.`,
};

test.describe('Compensation-group contract settings: take-over decimals and customer refund conditions', () => {
  test('decimals default to 2, refund conditions are offered and stored, and one condition is refused on a second take-over record', async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.feature('F2070: Compensation Group Contract');
    allure.story('Take-over record: decimals of the summed percentage and customer refund conditions');
    allure.severity('critical');
    test.setTimeout(8 * 60 * 1000);
    page.setDefaultTimeout(60 * 1000);
    // leaving a form with a change the server has not saved asks "Do you really want to leave?"
    page.on('dialog', async (dialog) => {
      console.log(`[INFO] native ${dialog.type()} dialog accepted: ${dialog.message()}`);
      await dialog.accept();
    });

    // one token per run: every name contains it, and every search types it, so no other run's data is offered
    const token = `TO${Date.now()}`;
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: LANGUAGE } },
        bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
        warehouses: { wh: {} },
        productCategories: { FRUIT: {}, VEGETABLES: {} },
        products: {
          DISCOUNT: { type: 'Item', isStocked: false, productCategory: 'FRUIT', prices: [{ price: 1 }] },
        },
      },
    });
    const fruitCategoryId = String(masterdata.productCategories.FRUIT.id);
    const vegetablesCategoryId = String(masterdata.productCategories.VEGETABLES.id);
    const discount = masterdata.products.DISCOUNT;

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const conditions = await test.step('Vertragsbedingungen: two refund conditions and one subscription condition of this run', async () => {
      const transitionId = await createCompletedTransition(page, { name: `Transition ${token}`, fiscalYear: new Date().getFullYear(), termDurationMonths: 12 });
      const refundA = { name: `RV A 1 % ${token}` };
      refundA.id = await createRefundConditions(page, { name: refundA.name, transitionId });
      const refundB = { name: `RV B 0,5 % ${token}` };
      refundB.id = await createRefundConditions(page, { name: refundB.name, transitionId });
      const subscription = { name: `Abo ${token}` };
      subscription.id = await createSubscriptionConditions(page, { name: subscription.name, transitionId });
      return { refundA, refundB, subscription };
    });

    const settingsId = await test.step('Vertragseinstellungen: settings with two take-over records (Früchte, Gemüse)', async () => {
      const schemaId = await createSchema(page, `Schema ${token}`);
      await openNewRecord(page, SETTINGS_WINDOW_ID);
      await fillText(page, page, 'Name', `Einstellungen ${token}`);
      const id = await waitForNewRecordId(page, SETTINGS_WINDOW_ID);
      await selectLookupByKey(page, page, 'C_CompensationGroup_Schema_ID', `Schema ${token}`, schemaId);
      await waitForRecordSaved(SETTINGS_WINDOW_ID, id, { maxRetries: 20, retryDelayMs: 500 });

      await expect(page.getByTestId(`tab-AD_Tab-${TAKEOVER_TAB_ID}`)).toHaveText(DE.takeOverTab, { timeout: SLOW_ACTION_TIMEOUT });
      for (const categoryId of [fruitCategoryId, vegetablesCategoryId]) {
        const modal = await openNewIncludedRow(page, TAKEOVER_TAB_ID);
        await selectListByKey(page, modal, 'M_Product_Category_ID', categoryId);
        await selectLookupByKey(page, modal, 'M_Product_ID', discount.productCode, discount.id);
        await closeModal(modal);
      }
      return id;
    });

    const takeOvers = await test.step('the grid shows the new column "Nachkommastellen der Summe" with the default 2 on both records', async () => {
      const rows = await getTakeOverRows(settingsId);
      expect(rows, 'two take-over records').toHaveLength(2);
      // each record is addressed by its id (server) and by its category caption (grid row)
      const byCategory = Object.fromEntries(rows.map((row) => [
        lookupKey(row.fieldsByName.M_Product_Category_ID.value),
        { id: String(row.rowId), category: row.fieldsByName.M_Product_Category_ID.value.caption },
      ]));
      for (const row of rows) {
        expect(Number(row.fieldsByName.PercentagePrecision.value), 'stored default').toBe(DEFAULT_DECIMALS);
      }

      await openRecord(page, SETTINGS_WINDOW_ID, settingsId);
      await page.getByTestId(`tab-AD_Tab-${TAKEOVER_TAB_ID}`).click();
      await expect(page.locator('th[data-testid="column-PercentagePrecision"]')).toContainText(DE.decimals, { timeout: SLOW_ACTION_TIMEOUT });
      const cells = page.locator('.tab-pane td[data-cy="cell-PercentagePrecision"]');
      await expect(cells).toHaveCount(2, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(cells.nth(0)).toHaveText(String(DEFAULT_DECIMALS));
      await expect(cells.nth(1)).toHaveText(String(DEFAULT_DECIMALS));
      return { fruit: byCategory[fruitCategoryId], vegetables: byCategory[vegetablesCategoryId] };
    });

    await test.step('Früchte: set the decimals to 3; the refund search offers only the refund conditions; add both', async () => {
      const modal = await test.step('open the Früchte take-over record: both new fields are shown', async () => {
        const form = await openTakeOverForm(page, takeOvers.fruit);
        await expect(labelOf(form, 'PercentagePrecision')).toHaveText(DE.decimals);
        await expect(labelOf(form, REFUND_CONDITIONS_FIELD)).toHaveText(DE.refundConditions);
        return form;
      });
      const refundField = refundConditionsField(modal);

      await test.step('set the decimals to 3', async () => {
        await fillNumber(page, modal, 'PercentagePrecision', CHANGED_DECIMALS);
      });

      await test.step('search the refund conditions for the run token: only the two refund conditions are offered', async () => {
        const offered = await searchRefundConditions(page, refundField, token);
        // all three conditions of this run carry the token: the search offers exactly the two refund ones
        expect([...offered].sort(), 'exactly the two refund conditions of this run are offered').toEqual([conditions.refundA.id, conditions.refundB.id].sort());
        expect(offered, 'the subscription condition is not offered').not.toContain(conditions.subscription.id);
        await expect(page.getByTestId(`option-${conditions.subscription.id}`)).toHaveCount(0);
      });

      await test.step(`add ${conditions.refundA.name}`, async () => {
        await addLabel(page, refundField, conditions.refundA);
      });

      await test.step(`add ${conditions.refundB.name}`, async () => {
        // the widget searches again only for a changed text, so the second condition is searched by its name
        await searchRefundConditions(page, refundField, conditions.refundB.name);
        await addLabel(page, refundField, conditions.refundB);
        await expect(refundField.locator('.labels-label')).toHaveCount(2);
      });

      await test.step('confirm the form', async () => {
        await closeModal(modal);
      });
    });

    await test.step('after a reload: Früchte shows 3 in the grid and both refund conditions in its form', async () => {
      const fruit = await getIncludedRecordData(SETTINGS_WINDOW_ID, settingsId, `AD_Tab-${TAKEOVER_TAB_ID}`, takeOvers.fruit.id);
      expect(Number(fruit.fieldsByName.PercentagePrecision.value)).toBe(CHANGED_DECIMALS);
      expect((await getStoredRefundConditionIds(settingsId, takeOvers.fruit.id)).sort(), 'Früchte stores both refund conditions')
        .toEqual([conditions.refundA.id, conditions.refundB.id].sort());

      await page.reload();
      await page.getByTestId(`tab-AD_Tab-${TAKEOVER_TAB_ID}`).click();
      const fruitRow = takeOverRow(page, takeOvers.fruit);
      await expect(fruitRow.locator('td[data-cy="cell-PercentagePrecision"]')).toHaveText(String(CHANGED_DECIMALS), { timeout: SLOW_ACTION_TIMEOUT });
      await expect(takeOverRow(page, takeOvers.vegetables).locator('td[data-cy="cell-PercentagePrecision"]')).toHaveText(String(DEFAULT_DECIMALS));

      const modal = await openTakeOverForm(page, takeOvers.fruit);
      const chips = refundConditionsField(modal).locator('.labels-label');
      await expect(chips).toHaveCount(2, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(chips.nth(0)).toContainText(conditions.refundA.name);
      await expect(chips.nth(1)).toContainText(conditions.refundB.name);
      await closeModal(modal);
    });

    await test.step('Gemüse: the same refund condition is refused with the German message and is not saved', async () => {
      const modal = await test.step('open the Gemüse take-over record', async () => await openTakeOverForm(page, takeOvers.vegetables));
      const refundField = refundConditionsField(modal);
      const rejectedSaves = recordRejectedSaveReasons(page, SETTINGS_WINDOW_ID);
      await test.step(`add ${conditions.refundA.name}, already listed on Früchte`, async () => {
        await searchRefundConditions(page, refundField, token);
        await addLabel(page, refundField, conditions.refundA);
      });
      await rejectedSaves.stop();
      expect(rejectedSaves.reasons.length, 'the save is refused').toBeGreaterThan(0);

      const message = DE.notUniqueMessage(conditions.refundA.name);
      expect(rejectedSaves.reasons[rejectedSaves.reasons.length - 1], 'the server refuses with the message').toBe(message);
      await expect(modal.locator('.window-indicator-container .bar.error')).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(modal.locator('.window-indicator-container .message-bar .text')).toHaveText(message, { timeout: SLOW_ACTION_TIMEOUT });

      // The refused value stays in the form (and in the server's copy of the record) until the operator removes it;
      // the server reports it as an unsaved change refused by this rule.
      const vegetables = await getIncludedRecordData(SETTINGS_WINDOW_ID, settingsId, `AD_Tab-${TAKEOVER_TAB_ID}`, takeOvers.vegetables.id);
      expect(vegetables.saveStatus, 'the change is not saved').toMatchObject({ saved: false, hasChanges: true, error: true });
      expect(vegetables.saveStatus.exception?.attributes?.AD_Message, 'refused by the uniqueness rule').toBe(NOT_UNIQUE_AD_MESSAGE);
    });

    await test.step('Gemüse: after removing the refused condition the record saves without refund conditions; Früchte keeps both', async () => {
      const modal = page.locator('.panel-modal').first();
      const refundField = refundConditionsField(modal);
      const chip = refundField.locator('.labels-label', { hasText: conditions.refundA.name });
      const patched = page.waitForResponse((r) => r.request().method() === 'PATCH' && r.url().includes(`/window/${SETTINGS_WINDOW_ID}/`), { timeout: SLOW_ACTION_TIMEOUT });
      await chip.locator('.labels-label-remove').click();
      await patched;
      await expect(refundField.locator('.labels-label')).toHaveCount(0);
      await expect(modal.locator('.window-indicator-container .bar.error')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });

      const vegetables = await getIncludedRecordData(SETTINGS_WINDOW_ID, settingsId, `AD_Tab-${TAKEOVER_TAB_ID}`, takeOvers.vegetables.id);
      expect(vegetables.saveStatus, 'Gemüse is saved').toMatchObject({ saved: true, hasChanges: false });
      expect(await getStoredRefundConditionIds(settingsId, takeOvers.vegetables.id), 'Gemüse has no refund condition').toEqual([]);
      expect((await getStoredRefundConditionIds(settingsId, takeOvers.fruit.id)).sort(), 'Früchte still has both refund conditions')
        .toEqual([conditions.refundA.id, conditions.refundB.id].sort());
      await closeModal(modal);

      await page.reload();
      const reopened = await openTakeOverForm(page, takeOvers.vegetables);
      await expect(refundConditionsField(reopened).locator('.labels-label')).toHaveCount(0);
      await expect(reopened.locator('.window-indicator-container .bar.error')).toHaveCount(0);
      await closeModal(reopened);
    });
  });
});

/** Kompensationsgruppe Schema (540415): a saved schema with a name; returns its id. */
async function createSchema(page, name) {
  await openNewRecord(page, SCHEMA_WINDOW_ID);
  await fillText(page, page, 'Name', name);
  const schemaId = await waitForNewRecordId(page, SCHEMA_WINDOW_ID);
  await waitForRecordSaved(SCHEMA_WINDOW_ID, schemaId, { maxRetries: 20, retryDelayMs: 500 });
  return schemaId;
}

/** Vertragsbedingungen (540113): saved (not completed) subscription conditions. */
async function createSubscriptionConditions(page, { name, transitionId }) {
  await openNewRecord(page, CONDITIONS_WINDOW_ID);
  await fillText(page, page, 'Name', name);
  const conditionsId = await waitForNewRecordId(page, CONDITIONS_WINDOW_ID);
  await selectListByKey(page, page, 'Type_Conditions', TYPE_CONDITIONS_SUBSCRIPTION);
  await selectListByKey(page, page, 'C_Flatrate_Transition_ID', transitionId);
  // mandatory for a subscription ("Bei Vertragsverlängung")
  await selectListByKey(page, page, 'OnFlatrateTermExtend', ON_TERM_EXTEND_CALCULATE_PRICE);
  await waitForRecordSaved(CONDITIONS_WINDOW_ID, conditionsId, { maxRetries: 20, retryDelayMs: 500 });
  return conditionsId;
}

async function getTakeOverRows(settingsId) {
  return (await getTabRows(SETTINGS_WINDOW_ID, settingsId, `AD_Tab-${TAKEOVER_TAB_ID}`)) ?? [];
}

/** The grid row of a take-over record, found by its (per-run) product category. */
function takeOverRow(page, takeOver) {
  return page.locator('.tab-pane table tbody tr').filter({ has: page.locator('td[data-cy="cell-M_Product_Category_ID"]', { hasText: takeOver.category }) });
}

function labelOf(scope, fieldName) {
  return scope.locator(`.form-field-${fieldName} > label.form-control-label`).first();
}

/**
 * Open the take-over row's own form: select the row and pick "advanced edit" in its context menu.
 * The menu item is the one with the edit icon and a shortcut hint (its caption is translated).
 * The form is verified to be the take-over record's via PercentagePrecision, a field the settings header does not have.
 */
async function openTakeOverForm(page, takeOver) {
  await page.getByTestId(`tab-AD_Tab-${TAKEOVER_TAB_ID}`).click();
  const row = takeOverRow(page, takeOver);
  await expect(row).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
  await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await row.locator('td[data-cy^="cell-"]').first().click();
  await row.locator('td[data-cy^="cell-"]').first().click({ button: 'right' });
  const contextMenu = page.locator('.context-menu.context-menu-open');
  await contextMenu.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const advancedEditItem = contextMenu
    .locator('.context-menu-item')
    .filter({ has: page.locator('i.meta-icon-edit') })
    .filter({ has: page.locator('span.tooltip-inline') });
  await expect(advancedEditItem).toHaveCount(1);
  await advancedEditItem.click();

  const modal = page.locator('.panel-modal').first();
  await modal.locator('.form-field-PercentagePrecision').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return modal;
}

/** The "Rückvergütungsbedingungen (Kunde)" Labels widget: its field is named after the internal name of the hidden labels tab. */
function refundConditionsField(modal) {
  return modal.locator(`.form-field-${REFUND_CONDITIONS_FIELD}`).first();
}

/**
 * Open the Labels widget and search it for the given text, typed in one go (so no partial-text results are shown);
 * returns the keys of the offered options as the server returned them.
 */
async function searchRefundConditions(page, field, searchText) {
  const wrapper = field.locator('.labels').first();
  const input = field.locator('.labels-input').first();
  const dropdownLoaded = page.waitForResponse((r) => r.url().includes('/dropdown') && r.request().method() === 'GET', { timeout: SLOW_ACTION_TIMEOUT });
  await wrapper.click();
  await dropdownLoaded;
  const typeahead = page.waitForResponse((r) => {
    const url = new URL(r.url());
    return url.pathname.endsWith('/typeahead') && url.searchParams.get('query') === searchText;
  }, { timeout: SLOW_ACTION_TIMEOUT });
  await input.evaluate((element, text) => { element.textContent = text; }, searchText);
  await input.press('End'); // the widget searches on key-up with the input's whole text
  const values = ((await (await typeahead).json()).values ?? []).map((value) => String(value.key));
  const options = page.locator('.input-dropdown-list .input-dropdown-list-option[data-testid^="option-"]');
  await expect(options.first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  return values;
}

/** Pick the offered option of the given condition and wait until the Labels change is sent to the server. */
async function addLabel(page, field, condition) {
  const option = page.locator(`.input-dropdown-list [data-testid="option-${condition.id}"]`).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const patched = page.waitForResponse((r) => r.request().method() === 'PATCH' && JSON.stringify(r.request().postDataJSON() ?? '').includes(`"values"`), { timeout: SLOW_ACTION_TIMEOUT });
  await option.dispatchEvent('mousedown');
  await patched;
  await expect(field.locator('.labels-label', { hasText: condition.name })).toHaveCount(1);
}

/** Refund conditions stored for the take-over record (read from the server). */
async function getStoredRefundConditionIds(settingsId, takeOverId) {
  const record = await getIncludedRecordData(SETTINGS_WINDOW_ID, settingsId, `AD_Tab-${TAKEOVER_TAB_ID}`, takeOverId);
  const labelsField = record.fieldsByName[REFUND_CONDITIONS_FIELD];
  expect(labelsField, `the take-over record has the field ${REFUND_CONDITIONS_FIELD}`).toBeDefined();
  return (labelsField.value?.values ?? []).map((value) => String(value.key));
}
