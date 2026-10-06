import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
  createMasterdata,
  gotoOrderList,
  createNewOrder,
  selectOrderCustomer,
  addOrderLine,
} from '../utils/OrderLineHarness';

/**
 * Enter on a filled Lookup whose typed text matches nothing puts the previous value back (de_DE).
 *
 * Real-life case: an order line has product P1. The user starts typing in the product field, the
 * text finds nothing ("Keine Ergebnisse gefunden."), and they press Enter. The product must stay
 * P1 — exactly what already happens when they leave with Tab or a click — instead of being
 * cleared ("Erforderliche Felder ausfüllen: Produkt").
 *
 * Covered in every layout that uses the shared Lookup: the order-line grid, the order-line form
 * opened with Alt+E ("Erweiterte Erfassung"), and three Lookup fields of the order header form.
 * A deliberate clear (delete the text, leave the field) still clears.
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

async function waitForTypeaheadToSettle(page) {
  await page.waitForTimeout(1200);
  await page
    .locator('.input-dropdown-container .rotating, .input-dropdown-container .spinner')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await page.waitForTimeout(300);
}

async function seedOrderWithLine(page) {
  const masterdata = await createMasterdata('de_DE');
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  await gotoOrderList();
  const recordId = await createNewOrder();
  await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);
  await addOrderLine(recordId, {
    productCode: masterdata.products.Product1.productCode,
    quantity: 1,
  });
  return {
    masterdata,
    recordId,
    productCode: masterdata.products.Product1.productCode,
  };
}

/** type the text into a focused Lookup input (replacing its content) and press Enter */
async function typeNonMatchingAndPressEnter(page, input) {
  await input.click();
  await page.keyboard.press('ControlOrMeta+a');
  await input.pressSequentially(NON_MATCHING_TEXT, { delay: 20 });
  await waitForTypeaheadToSettle(page);
  await page.keyboard.press('Enter');
  await page.waitForTimeout(1500); // a PATCH, if any, is sent right away
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

  test('Order header form: three Lookup fields keep their value; emptying a field still clears it', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Lookup — Enter on a non-matching text restores the previous value');
    allure.severity('critical');
    test.setTimeout(240000);

    const { recordId } = await seedOrderWithLine(page);
    const patches = trackPatches(page, recordId);

    for (const field of ['M_PricingSystem_ID', 'C_BPartner_Location_ID', 'AD_Org_ID']) {
      await test.step(`Header ${field}: non-matching text + Enter keeps the value`, async () => {
        const input = page.locator(`#lookup_${field} input.input-field`).first();
        await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        const previous = await input.inputValue();
        expect(previous, `${field} must be filled before the check`).not.toBe('');

        await typeNonMatchingAndPressEnter(page, input);

        await expect(input, `${field} shows its previous value again`).toHaveValue(previous);
        // A Lookup whose typeahead ignores the typed text (e.g. the partner's locations) may re-send
        // the SAME value; what must never be sent is an empty value.
        expect(clearingPatchesFor(patches, field), `${field} is not cleared`).toEqual([]);
        await page.keyboard.press('Tab');
        await page.waitForTimeout(500);
      });
    }

    await test.step('Header M_PricingSystem_ID: emptying the text and leaving the field still clears it', async () => {
      // The deliberate clear: delete the text and leave the field (Tab). Note: empty text + Enter
      // does not clear today - Enter picks the highlighted first entry of the list; unchanged here.
      const input = page.locator('#lookup_M_PricingSystem_ID input.input-field').first();
      await input.click();
      await page.keyboard.press('ControlOrMeta+a');
      await page.keyboard.press('Backspace');
      await waitForTypeaheadToSettle(page);
      await page.keyboard.press('Tab');
      await page.waitForTimeout(1500);

      expect(clearingPatchesFor(patches, 'M_PricingSystem_ID'), 'the deliberate clear is sent as empty').toHaveLength(1);
    });
  });
});
