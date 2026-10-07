import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT, flushPendingUiTasks, waitForTypeaheadAnswer } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { createMasterdata } from '../utils/OrderLineHarness';

/**
 * Enter on a filled Lookup whose typed text matches nothing puts the previous value back (de_DE).
 *
 * Real-life case: an order line has product P1. The user starts typing in the product field, the
 * text finds nothing ("Keine Ergebnisse gefunden."), and they press Enter. The product must stay
 * P1, as when they leave with Tab or a click; it is not cleared ("Erforderliche Felder ausfüllen:
 * Produkt").
 *
 * Covered in every layout that uses the shared Lookup: the order-line grid, the order-line form
 * opened with Alt+E ("Erweiterte Erfassung"), and three Lookup fields of the order header form
 * (pricing system, input data source, organisation).
 * A deliberate clear (delete the text, leave the field) clears it.
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const NON_MATCHING_TEXT = 'qzzx9nomatch';

function trackPatches(page, recordId) {
  const patches = [];
  page.on('request', (request) => {
    if (request.method() === 'PATCH' && request.url().includes(`/${recordId}`)) {
      patches.push({ url: request.url(), body: request.postData() || '' });
    }
  });
  return patches;
}

function patchesFor(patches, field) {
  return patches.filter((patch) => patch.body.includes(`"${field}"`));
}

/** PATCHes that would clear the field: its value sent as null or empty */
function clearingPatchesFor(patches, field) {
  return patchesFor(patches, field).filter((patch) => {
    const operations = JSON.parse(patch.body);
    return operations.some(
      (operation) => operation.path === field && (operation.value === null || operation.value === '')
    );
  });
}

/** Replace the focused Lookup's text with `text` and wait until its list shows "no results". */
async function typeNonMatchingText(page, input) {
  const answered = waitForTypeaheadAnswer(page, NON_MATCHING_TEXT);
  await input.click();
  await page.keyboard.press('ControlOrMeta+a');
  await input.pressSequentially(NON_MATCHING_TEXT, { delay: 20 });
  await answered;
  await expect(page.locator('.input-dropdown-list .input-dropdown-list-header')).toBeVisible({
    timeout: SLOW_ACTION_TIMEOUT,
  });
}

async function seedOrderWithLine(page) {
  const masterdata = await createMasterdata('de_DE');
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();
  const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
  await SalesOrderPage.addOrderLine({
    product: masterdata.products.Product1.productCode,
    quantity: 1,
    recordId,
  });
  return {
    masterdata,
    recordId,
    productCode: masterdata.products.Product1.productCode,
  };
}

/**
 * Give an empty header Lookup a value (the first entry its typeahead offers) through the WebAPI, then
 * reload so the form shows it.
 */
async function fillAnyValueAndReload(page, recordId, field) {
  const typeahead = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}/field/${field}/typeahead?query=%20`
  );
  expect(typeahead.ok(), `${field} typeahead: ${typeahead.status()}`).toBeTruthy();
  const [firstValue] = (await typeahead.json()).values || [];
  expect(firstValue, `${field} offers at least one value`).toBeTruthy();

  const patch = await page.request.patch(`${WEBAPI_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`, {
    data: [{ op: 'replace', path: field, value: firstValue }],
  });
  expect(patch.ok(), `setting ${field}: ${patch.status()}`).toBeTruthy();

  await page.reload();
  await expect(page.locator(`#lookup_${field} input.input-field`).first()).toHaveValue(firstValue.caption, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
}

/** type the text into a focused Lookup input (replacing its content) and press Enter */
async function typeNonMatchingAndPressEnter(page, input) {
  await typeNonMatchingText(page, input);
  // Enter must reach THIS Lookup holding the typed text: a field that moves the focus on (or
  // never takes the text) sends the keys to another field.
  await expect(input, 'the Lookup keeps the focus while typing').toBeFocused();
  await expect(input, 'the Lookup holds the typed text').toHaveValue(NON_MATCHING_TEXT);
  await page.keyboard.press('Enter');
  await expect(page.locator('.input-dropdown-list'), 'Enter closes the list').toHaveCount(0);
  await flushPendingUiTasks(page);
}

/** Open the sales order list's "Standard" filter panel and return it. */
async function openStandardFilter(page) {
  await page.locator('.filters-not-frequent button.toggle-filters').click();
  await page.locator('.filters-not-frequent .filter-option-default').click();
  const panel = page.locator('.filters-not-frequent .filter-widget');
  await panel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return panel;
}

test.describe('Lookup — Enter with text that matches nothing keeps the previous value (de_DE)', () => {
  test('Order-line grid: the product is kept', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Lookup — Enter on a non-matching text restores the previous value');
    allure.severity('critical');
    test.setTimeout(240000);

    const { recordId, productCode } = await seedOrderWithLine(page);
    const patches = trackPatches(page, recordId);
    const productCell = page.locator('[data-cy="cell-M_Product_ID"]').first();

    await test.step('Grid: type a non-matching text into the product cell and press Enter', async () => {
      await productCell.dblclick();
      const editorInput = productCell.locator('.input-dropdown-container input').first();
      await editorInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });

      await typeNonMatchingAndPressEnter(page, editorInput);

      await expect(editorInput, 'the editor shows the previous product again').toHaveValue(new RegExp(productCode));
      expect(patchesFor(patches, 'M_Product_ID'), 'no change is sent for the product').toEqual([]);

      await page.keyboard.press('Escape');
      await expect(productCell).toContainText(productCode);
    });

    await test.step('After a reload the order line still has its product', async () => {
      await page.reload();
      await expect(page.locator('[data-cy="cell-M_Product_ID"]').first()).toContainText(productCode, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  });

  test('Alt+E line form ("Erweiterte Erfassung"): the product is kept', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Lookup — Enter on a non-matching text restores the previous value');
    allure.severity('critical');
    test.setTimeout(240000);

    const { recordId, productCode } = await seedOrderWithLine(page);
    const patches = trackPatches(page, recordId);

    await test.step('Alt+E line form: type a non-matching text into the product field and press Enter', async () => {
      // Open the line's "Erweiterte Erfassung" through the row's context menu - the entry that carries
      // the Alt+E shortcut. (The bare Alt+E key opens the HEADER's form whenever the focus is not on the
      // grid, so the menu is the deterministic way.)
      const lineCell = page.locator('[data-cy="cell-QtyEntered"]').first();
      await lineCell.click({ button: 'right' });
      await page
        .locator('.context-menu-item')
        .filter({ has: page.locator('.tooltip-inline', { hasText: /\+E$/ }) })
        .click();
      const modal = page.locator('.panel-modal');
      const productInput = modal.locator('#lookup_M_Product_ID input.input-field');
      await productInput.waitFor({
        state: 'visible',
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await expect(productInput).toHaveValue(new RegExp(productCode));

      await typeNonMatchingAndPressEnter(page, productInput);

      await expect(productInput, 'the form field shows the previous product again').toHaveValue(
        new RegExp(productCode)
      );
      expect(patchesFor(patches, 'M_Product_ID'), 'no change is sent for the product').toEqual([]);

      await page.keyboard.press('Escape');
      await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('After a reload the order line still has its product', async () => {
      await page.reload();
      await expect(page.locator('[data-cy="cell-M_Product_ID"]').first()).toContainText(productCode, {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  });

  test('Order header form: three Lookup fields keep their value; emptying a field clears it', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Lookup — Enter on a non-matching text restores the previous value');
    allure.severity('critical');
    test.setTimeout(240000);

    const { recordId } = await seedOrderWithLine(page);
    await fillAnyValueAndReload(page, recordId, 'AD_InputDataSource_ID');
    const patches = trackPatches(page, recordId);

    for (const field of ['M_PricingSystem_ID', 'AD_InputDataSource_ID', 'AD_Org_ID']) {
      await test.step(`Header ${field}: non-matching text + Enter keeps the value`, async () => {
        const input = page.locator(`#lookup_${field} input.input-field`).first();
        await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        const previous = await input.inputValue();
        expect(previous, `${field} must be filled before the check`).not.toBe('');

        await typeNonMatchingAndPressEnter(page, input);

        await expect(input, `${field} shows its previous value again`).toHaveValue(previous);
        expect(patchesFor(patches, field), `no change is sent for ${field}`).toEqual([]);
        await page.keyboard.press('Tab');
        await expect(input, `${field} is left`).not.toBeFocused();
      });
    }

    await test.step('Header M_PricingSystem_ID: emptying the text and leaving the field clears it', async () => {
      // The deliberate clear: delete the text and leave the field (Tab). Empty text + Enter does
      // not clear: Enter picks the highlighted first list entry.
      const input = page.locator('#lookup_M_PricingSystem_ID input.input-field').first();
      await input.click();
      await page.keyboard.press('ControlOrMeta+a');
      await page.keyboard.press('Backspace');
      const clearSent = page.waitForRequest(
        (request) =>
          request.method() === 'PATCH' &&
          request.url().includes(`/${recordId}`) &&
          (request.postData() || '').includes('"M_PricingSystem_ID"'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page.keyboard.press('Tab');
      await clearSent;

      expect(clearingPatchesFor(patches, 'M_PricingSystem_ID'), 'the deliberate clear is sent as empty').toHaveLength(1);
    });
  });

  test('View filter, before and after applying it: the picked partner is kept', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Lookup — Enter on a non-matching text restores the previous value');
    allure.severity('critical');
    test.setTimeout(240000);

    const masterdata = await createMasterdata('de_DE');
    const customerCode = masterdata.bpartners.CUSTOMER1.bpartnerCode;
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();
    await SalesOrderPage.goto();

    const panel = await openStandardFilter(page);
    const partnerInput = panel.locator('#lookup_C_BPartner_ID input.input-field');

    await test.step('Pick the customer in the filter (not applied)', async () => {
      await partnerInput.click();
      await partnerInput.pressSequentially(customerCode, { delay: 20 });
      const option = page.locator('.input-dropdown-list-option').filter({ hasText: customerCode }).first();
      await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await option.click();
      await expect(partnerInput).toHaveValue(new RegExp(customerCode));
    });

    await test.step('Non-matching text + Enter shows the picked customer again', async () => {
      await typeNonMatchingAndPressEnter(page, partnerInput);
      await expect(partnerInput, 'the filter field shows the picked customer again').toHaveValue(
        new RegExp(customerCode)
      );
    });

    await test.step('Non-matching text + Tab shows the picked customer again', async () => {
      await typeNonMatchingText(page, partnerInput);
      await page.keyboard.press('Tab');
      await expect(partnerInput, 'the filter field shows the picked customer again').toHaveValue(
        new RegExp(customerCode)
      );
    });

    await test.step('Applied filter: non-matching text + Enter shows the customer again', async () => {
      await panel.getByTestId('filter-apply-button').click();
      await panel.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });

      await page.locator('.filters-not-frequent button.toggle-filters').click();
      await panel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(partnerInput, 'the applied filter shows the customer').toHaveValue(new RegExp(customerCode));

      await typeNonMatchingAndPressEnter(page, partnerInput);
      await expect(partnerInput, 'the applied filter shows the customer again').toHaveValue(
        new RegExp(customerCode)
      );
    });
  });
});
