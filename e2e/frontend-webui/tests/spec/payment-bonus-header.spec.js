import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PAYMENT_WINDOW_ID, SALES_INVOICE_WINDOW_ID, SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { DOCTYPE_AR_RECEIPT } from '../utils/DocTypeIds';
import { getFieldData, getTabRows, waitForRecordSaved } from '../utils/WebAPIValidation';
import {
  closeModal,
  completeDocument,
  createCompletedTransition,
  createRefundConditions,
  createTermForPartner,
  CONDITIONS_WINDOW_ID,
  expectDocStatus,
  fillNumber,
  lookupKey,
  openNewIncludedRow,
  openNewRecord,
  openRecord,
  REFUND_CONFIG_TAB_ID,
  selectFirstListOption,
  selectListByKey,
  selectLookupByKey,
  setCheckbox,
  waitForNewRecordId,
  getRefundConfigRows,
} from '../utils/ContractWindowHelpers';

/**
 * Payment bonus ("Abzug bei Zahlung") in the payment window "Zahlung" (195), header path:
 * picking "Rechnung" of a customer who has a payment bonus contract (condition with a refund line deducted at
 * payment) pre-fills "Zahlungsbonus" (the callout of the payment window), sets "Zahlungsbetrag" to the open
 * amount minus the bonus and leaves "Abschreibung Rechnung" at 0. Completing the payment books the
 * "Zahlungsbonus-Gutschrift", which the read-only field "Zahlungsbonus-Gutschrift" then shows.
 *
 * The data setup is that of payment-bonus-allocation.spec.js (which covers the payment allocation view);
 * the helpers are repeated here because a spec file cannot share them without being loaded as a spec.
 *
 * All assertions read the rendered fields of the window (the inputs, by column name: language independent),
 * the credit memo is read back from the invoice window as the end result.
 * Amounts shown in the fields are compared as numbers (cents), so the check does not depend on the number format.
 *
 * Set PAYMENT_BONUS_EVIDENCE_DIR to also write the per-surface stills (full window, URL banner, fields highlighted).
 */

const LANGUAGE = 'de_DE';

const REFUND_PERCENT = 3;
const GOODS_PRICE = 10;
const GOODS_QTY = 10;

const CURRENCY_EUR_ID = 102;
const INVOICE_LINE_TAB_ID = 270;
const INVOICE_TAX_TAB = 'AD_Tab-271'; // Rechnung > Rechnungs Steuer
const DOCTYPE_WINDOW_ID = 135; // Belegart (C_DocType)
const DOCSUBTYPE_PAYMENT_BONUS_CREDIT_MEMO = 'PB';

test.describe('Payment bonus: the payment window pre-fills and books the bonus', () => {
  test.beforeEach(async ({ page }) => {
    allure.epic('E0170: Contract Management');
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
    const { masterdata, invoice } = await setUpCustomerWithInvoice(page);
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
// Setup (as in payment-bonus-allocation.spec.js)
// ======================================================================

/** A customer, a goods product in its own category, a bonus product, one shipped and invoiced sales order; logged in. */
async function setUpCustomerWithInvoice(page) {
  const masterdata = await Backend.createMasterdata({
    request: {
      login: { user: { language: LANGUAGE } },
      bpartners: { CUSTOMER: { isCustomer: true, isVendor: false } },
      warehouses: { wh: {} },
      productCategories: { GOODS_CATEGORY: {} },
      products: {
        GOODS: { productCategory: 'GOODS_CATEGORY', prices: [{ price: GOODS_PRICE }] },
        BONUS: { type: 'Service', prices: [{ price: 1 }] },
      },
      salesOrders: { SO1: { bpartner: 'CUSTOMER', warehouse: 'wh', datePromised: new Date().toISOString(), lines: [{ product: 'GOODS', qty: GOODS_QTY }] } },
      shipments: { SH1: { salesOrder: 'SO1' } },
    },
  });

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  const orderId = masterdata.salesOrders.SO1.id;
  const invoice = await test.step(`invoice the sales order ${orderId} through its invoice candidates`, async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}`);
    await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    await SalesOrderPage.openRelatedInvoiceCandidate({ maxRetries: 10, retryDelay: 3000, refreshOnRetry: true });
    await InvoiceCandidatePage.expectVisibleForSalesOrder();
    await InvoiceCandidatePage.createInvoiceForSalesOrder();

    await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}`);
    await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    await SalesOrderPage.openRelatedInvoice({ maxRetries: 15, retryDelay: 3000, refreshOnRetry: true });
    await InvoicePage.expectVisible();
    await InvoicePage.openDetailView();
    const id = await waitForNewRecordId(page, SALES_INVOICE_WINDOW_ID);
    await expectDocStatus(SALES_INVOICE_WINDOW_ID, id, 'CO');
    const documentNo = String((await getFieldData(SALES_INVOICE_WINDOW_ID, id, 'DocumentNo')).value);
    const created = { id, documentNo, grandTotalCents: await getGrandTotalCents(id) };
    console.log(`[INFO] invoice ${JSON.stringify(created)}`);
    expect(created.grandTotalCents, 'invoice grand total').toBeGreaterThan(0);
    return created;
  });
  return { masterdata, invoice };
}

/** A completed refund condition with one line deducted at payment, and a completed term of the customer on it. */
async function createDeductedAtPaymentContract(page, { runId, customer, categoryId, bonus }) {
  await test.step('payment bonus contract: condition with one line deducted at payment, and the customer\'s term', async () => {
    const start = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
    const transitionId = await createCompletedTransition(page, { name: `PB transition ${runId}`, fiscalYear: start.getFullYear(), termDurationMonths: 12 });
    const conditionsId = await createRefundConditions(page, { name: `PB conditions ${runId}`, transitionId });
    const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
    await selectListByKey(page, modal, 'M_Product_Category_ID', categoryId);
    await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
    await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
    await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
    await setCheckbox(page, modal, 'IsDeductedAtPayment');
    await closeModal(modal);
    expect(await getRefundConfigRows(conditionsId)).toHaveLength(1);
    await completeDocument(page);
    await expectDocStatus(CONDITIONS_WINDOW_ID, conditionsId, 'CO');
    await createTermForPartner(page, { bpartnerId: customer.id, conditionsId, startDate: start });
  });
}

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

/** A WebAPI amount (number or numeric string with a dot) in cents. */
function toCents(value) {
  const cents = Math.round(Number(value) * 100);
  expect(Number.isFinite(cents), `amount "${value}"`).toBe(true);
  return cents;
}

/**
 * An amount as the form shows it (with the session's grouping and decimal separators, e.g. "1.234,57" or "1,234.57").
 * The decimal separator is the last "," or "." followed by exactly two digits at the end.
 */
function parseDisplayedAmountToCents(text) {
  const compact = text.replace(/[^0-9,.-]/g, '');
  expect(compact, `displayed amount "${text}" has digits`).toMatch(/[0-9]/);
  const match = compact.match(/^(-?)([0-9.,]*?)(?:[.,]([0-9]{2}))?$/);
  expect(match, `displayed amount "${text}"`).toBeTruthy();
  const [, sign, integerPart, decimals] = match;
  const cents = Number(integerPart.replace(/[.,]/g, '') || '0') * 100 + Number(decimals ?? '0');
  return sign === '-' ? -cents : cents;
}

/** The invoice's grand total (not a field of the invoice window): the sum of its tax lines' base and tax amounts. */
async function getGrandTotalCents(invoiceId) {
  const taxLines = (await getTabRows(SALES_INVOICE_WINDOW_ID, invoiceId, INVOICE_TAX_TAB)) ?? [];
  expect(taxLines.length, `tax lines of invoice ${invoiceId}`).toBeGreaterThan(0);
  return taxLines.reduce((sum, line) => sum + toCents(line.fieldsByName.TaxBaseAmt.value) + toCents(line.fieldsByName.TaxAmt.value), 0);
}

/**
 * The bonus the invoice gets, with VAT on top: REFUND_PERCENT % of the invoice's net goods, plus VAT at the goods' rate.
 * @returns { bonusCents, goodsTaxId }
 */
async function getExpectedBonus(invoiceId) {
  const taxLines = (await getTabRows(SALES_INVOICE_WINDOW_ID, invoiceId, INVOICE_TAX_TAB)) ?? [];
  expect(taxLines, `invoice ${invoiceId} has one tax, that of the goods`).toHaveLength(1);
  const baseCents = toCents(taxLines[0].fieldsByName.TaxBaseAmt.value);
  const taxCents = toCents(taxLines[0].fieldsByName.TaxAmt.value);
  const netBonusCents = (baseCents * REFUND_PERCENT) / 100;
  expect(Number.isInteger(netBonusCents), `${REFUND_PERCENT} % of ${baseCents} cents is a whole number of cents`).toBe(true);
  const bonusTaxCents = (netBonusCents * taxCents) / baseCents;
  expect(Number.isInteger(bonusTaxCents), `VAT of the ${netBonusCents} cents bonus is a whole number of cents`).toBe(true);
  return { bonusCents: netBonusCents + bonusTaxCents, goodsTaxId: lookupKey(taxLines[0].fieldsByName.C_Tax_ID.value) };
}
