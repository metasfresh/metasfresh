import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PAYMENT_WINDOW_ID, SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';
import { DOCTYPE_AR_RECEIPT } from '../utils/DocTypeIds';
import { getFieldData, getTabRows, waitForRecordSaved } from '../utils/WebAPIValidation';
import {
  completeDocument,
  expectDocStatus,
  lookupKey,
  openNewRecord,
  openRecord,
  selectFirstListOption,
  selectListByKey,
  selectLookupByKey,
  waitForNewRecordId,
} from '../utils/ContractWindowHelpers';
import {
  createDeductedAtPaymentContract,
  CURRENCY_EUR_ID,
  DOCSUBTYPE_PAYMENT_BONUS_CREDIT_MEMO,
  DOCTYPE_WINDOW_ID,
  getExpectedBonus,
  getGrandTotalCents,
  INVOICE_TAX_TAB,
  parseDisplayedAmountToCents,
  setUpCustomer,
  toCents,
} from '../utils/PaymentBonusHelpers';

/**
 * Payment bonus ("Abzug bei Zahlung") in the payment window "Zahlung" (195), header path:
 * picking "Rechnung" of a customer who has a payment bonus contract (condition with a refund line deducted at
 * payment) pre-fills "Zahlungsbonus" (the callout of the payment window), sets "Zahlungsbetrag" to the open
 * amount minus the bonus and leaves "Abschreibung Rechnung" at 0. Completing the payment books the
 * "Zahlungsbonus-Gutschrift", which the read-only field "Zahlungsbonus-Gutschrift" then shows.
 *
 * The data setup is shared with payment-bonus-allocation.spec.js (the payment allocation view) through
 * ../utils/PaymentBonusHelpers.js.
 *
 * All assertions read the rendered fields of the window (the inputs, by column name: language independent),
 * the credit memo is read back from the invoice window as the end result.
 * Amounts shown in the fields are compared as numbers (cents), so the check does not depend on the number format.
 *
 * Set PAYMENT_BONUS_EVIDENCE_DIR to also write the per-surface stills (full window, URL banner, fields highlighted).
 */

test.describe('Payment bonus: the payment window pre-fills and books the bonus', () => {
  test.beforeEach(async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.feature('F2000: Contract Management');
    allure.severity('critical');
    page.setDefaultTimeout(60 * 1000);
    page.on('dialog', async (dialog) => {
      console.log(`[INFO] native ${dialog.type()} dialog accepted: ${dialog.message()}`);
      await dialog.accept();
    });
  });

  test('picking the invoice pre-fills Zahlungsbonus and the Zahlungsbetrag; completing books the Zahlungsbonus-Gutschrift', async ({ page }) => {
    allure.story('Payment window: pre-filled payment bonus, credit memo booked on completion');
    test.setTimeout(10 * 60 * 1000);
    const runId = Date.now();
    const { masterdata, invoices } = await setUpCustomer(page, { orders: 1 });
    const [invoice] = invoices;
    const customer = masterdata.bpartners.CUSTOMER;
    const bonus = masterdata.products.BONUS;
    const { bonusCents, goodsTaxId } = await getExpectedBonus(invoice.id);
    const openCents = invoice.grandTotalCents;

    await createDeductedAtPaymentContract(page, { runId, customer, categoryId: masterdata.productCategories.GOODS_CATEGORY.id, bonus });

    let paymentId;
    await test.step('Zahlung: a new receipt of the customer', async () => {
      await openNewRecord(page, PAYMENT_WINDOW_ID);
      paymentId = await waitForNewRecordId(page, PAYMENT_WINDOW_ID);
      await selectListByKey(page, page, 'C_DocType_ID', DOCTYPE_AR_RECEIPT);
      await selectLookupByKey(page, page, 'C_BPartner_ID', customer.bpartnerCode, customer.id);
      await selectFirstListOption(page, page, 'C_BP_BankAccount_ID');
      await selectListByKey(page, page, 'C_Currency_ID', CURRENCY_EUR_ID);
      await waitForRecordSaved(PAYMENT_WINDOW_ID, paymentId, { maxRetries: 20, retryDelayMs: 500 });
      await expect(page.locator('.form-field-PaymentBonusAmt'), 'no Zahlungsbonus without Rechnung').toHaveCount(0);
    });

    await test.step('Rechnung picked: Zahlungsbonus is pre-filled, Zahlungsbetrag = open - bonus, Abschreibung Rechnung 0', async () => {
      await selectLookupByKey(page, page, 'C_Invoice_ID', invoice.documentNo, invoice.id);

      await expect.poll(async () => fieldCents(page, 'PaymentBonusAmt'), { message: 'pre-filled Zahlungsbonus', timeout: SLOW_ACTION_TIMEOUT }).toBe(bonusCents);
      await expect.poll(async () => fieldCents(page, 'PayAmt'), { message: 'Zahlungsbetrag = open - bonus', timeout: SLOW_ACTION_TIMEOUT }).toBe(openCents - bonusCents);
      expect(await fieldCents(page, 'DiscountAmt'), 'Skonto').toBe(0);
      await expect(page.locator('.form-field-PaymentBonus_CreditMemo_ID'), 'no credit memo before completion').toHaveCount(0);
      await evidenceStill(page, 'payment-header-1-rechnung-picked-bonus-prefilled', ['PaymentBonusAmt', 'PayAmt', 'DiscountAmt']);

      // "Abschreibung Rechnung" is a field of the advanced edit
      await openAdvancedEdit(page);
      expect(await fieldCents(page.locator('.panel-modal'), 'WriteOffAmt'), 'Abschreibung Rechnung').toBe(0);
      expect(await fieldCents(page.locator('.panel-modal'), 'PaymentBonusAmt'), 'Zahlungsbonus in the advanced edit').toBe(bonusCents);
      await evidenceStill(page, 'payment-header-1b-advanced-edit-abschreibung-0', ['WriteOffAmt']);
      await closeAdvancedEdit(page);
    });

    await test.step('the draft is saved with the pre-filled amounts', async () => {
      await waitForRecordSaved(PAYMENT_WINDOW_ID, paymentId, { maxRetries: 20, retryDelayMs: 500 });
      expect(toCents((await getFieldData(PAYMENT_WINDOW_ID, paymentId, 'PaymentBonusAmt')).value)).toBe(bonusCents);
      expect(toCents((await getFieldData(PAYMENT_WINDOW_ID, paymentId, 'PayAmt')).value)).toBe(openCents - bonusCents);
    });

    await test.step('completion books the Zahlungsbonus-Gutschrift, and its read-only field is filled', async () => {
      await completeDocument(page);
      await expectDocStatus(PAYMENT_WINDOW_ID, paymentId, 'CO');

      // the field appears once the payment is completed; the completed payment is reloaded to show it as the user sees it
      await openRecord(page, PAYMENT_WINDOW_ID, paymentId);
      const creditMemoField = page.locator('.form-field-PaymentBonus_CreditMemo_ID');
      await expect(creditMemoField, 'Zahlungsbonus-Gutschrift is shown').toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      const creditMemoText = (await creditMemoField.locator('input').first().inputValue()).trim();
      await expect(creditMemoField.locator('input').first(), 'Zahlungsbonus-Gutschrift is read-only').toBeDisabled();
      expect(creditMemoText, 'Zahlungsbonus-Gutschrift is filled').not.toBe('');
      expect(await fieldCents(page, 'PaymentBonusAmt'), 'Zahlungsbonus stays').toBe(bonusCents);
      expect(await fieldCents(page, 'PayAmt'), 'Zahlungsbetrag stays').toBe(openCents - bonusCents);
      await evidenceStill(page, 'payment-header-2-completed-credit-memo-filled', ['PaymentBonusAmt', 'PaymentBonus_CreditMemo_ID']);
      await openAdvancedEdit(page);
      expect(await fieldCents(page.locator('.panel-modal'), 'WriteOffAmt'), 'Abschreibung Rechnung stays 0').toBe(0);
      await closeAdvancedEdit(page);

      // the end result: the credit memo the field shows is the Zahlungsbonus credit memo over the bonus of the invoice
      const creditMemoId = lookupKey((await getFieldData(PAYMENT_WINDOW_ID, paymentId, 'PaymentBonus_CreditMemo_ID')).value);
      expect(creditMemoId, 'credit memo id').toBeTruthy();
      expect(creditMemoText, 'the field shows the document number of the credit memo').toContain(
        String((await getFieldData(SALES_INVOICE_WINDOW_ID, creditMemoId, 'DocumentNo')).value),
      );
      expect(lookupKey((await getFieldData(SALES_INVOICE_WINDOW_ID, creditMemoId, 'DocStatus')).value)).toBe('CO');
      expect(await getGrandTotalCents(creditMemoId), 'credit memo over the bonus').toBe(bonusCents);
      const creditMemoTaxIds = ((await getTabRows(SALES_INVOICE_WINDOW_ID, creditMemoId, INVOICE_TAX_TAB)) ?? []).map((l) => lookupKey(l.fieldsByName.C_Tax_ID.value));
      expect(creditMemoTaxIds, 'the bonus has the VAT of the goods, as the expected bonus assumes').toEqual([goodsTaxId]);
      const docTypeId = lookupKey((await getFieldData(SALES_INVOICE_WINDOW_ID, creditMemoId, 'C_DocTypeTarget_ID')).value);
      expect(lookupKey((await getFieldData(DOCTYPE_WINDOW_ID, docTypeId, 'DocSubType')).value), 'Zahlungsbonus-Gutschrift').toBe(DOCSUBTYPE_PAYMENT_BONUS_CREDIT_MEMO);
    });

    await test.step('the invoice is paid, by the payment and by the credit memo', async () => {
      await expect.poll(async () => (await getFieldData(SALES_INVOICE_WINDOW_ID, invoice.id, 'IsPaid')).value, {
        message: 'invoice IsPaid',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).toBe(true);
    });
  });
});

// ======================================================================
// Evidence stills
// ======================================================================

/**
 * When PAYMENT_BONUS_EVIDENCE_DIR is set: a full-window still with the URL burned into the page (bottom banner) and
 * the given fields outlined. Nothing else of the page is altered.
 */
async function evidenceStill(page, name, highlightedFields) {
  const dir = process.env.PAYMENT_BONUS_EVIDENCE_DIR;
  if (!dir) {
    return;
  }
  const scope = (await page.locator('.panel-modal').count()) > 0 ? page.locator('.panel-modal') : page;
  for (const fieldName of highlightedFields) {
    const field = scope.locator(`.form-field-${fieldName}`).first();
    await field.scrollIntoViewIfNeeded();
    await field.evaluate((el) => {
      el.style.outline = '3px solid #2ecc40';
      el.style.outlineOffset = '2px';
    });
  }
  await page.evaluate(() => {
    let banner = document.getElementById('__uatUrlBanner');
    if (!banner) {
      banner = document.createElement('div');
      banner.id = '__uatUrlBanner';
      banner.style.cssText = 'position:fixed;bottom:0;left:0;right:0;z-index:2147483647;background:#111;color:#fff;font:12px monospace;padding:3px 8px;pointer-events:none';
      document.body.appendChild(banner);
    }
    banner.textContent = location.href;
  });
  await page.screenshot({ path: `${dir}/${name}.png`, fullPage: true });
  await page.evaluate(() => document.getElementById('__uatUrlBanner')?.remove());
}

// ======================================================================
// Amounts
// ======================================================================

/** Open the advanced edit of the payment (sub header menu) and wait for its modal. */
async function openAdvancedEdit(page) {
  await page.locator('.meta-icon-more').first().click();
  const item = page.locator('.subheader-item:has(.meta-icon-edit)').first();
  await item.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await item.click();
  await page.locator('.panel-modal').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

async function closeAdvancedEdit(page) {
  await page.keyboard.press('Escape');
  await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
}

/** The amount a field of the form (of the page, or of the given scope) shows (its input, or its text when read-only), in cents. */
async function fieldCents(scope, fieldName) {
  const field = scope.locator(`.form-field-${fieldName}`).first();
  await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const input = field.locator('input');
  const text = (await input.count()) > 0 ? await input.first().inputValue() : await field.innerText();
  return parseDisplayedAmountToCents(text.trim());
}

