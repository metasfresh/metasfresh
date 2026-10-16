import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { Backend } from './Backend';
import { LoginPage } from './pages/LoginPage';
import { DashboardPage } from './pages/DashboardPage';
import { SalesOrderPage } from './pages/SalesOrderPage';
import { InvoiceCandidatePage } from './pages/InvoiceCandidatePage';
import { InvoicePage } from './pages/InvoicePage';
import { FRONTEND_BASE_URL, VERY_SLOW_ACTION_TIMEOUT } from './common';
import { SALES_INVOICE_WINDOW_ID, SALES_ORDER_WINDOW_ID } from './WindowIds';
import { getFieldData, getTabRows } from './WebAPIValidation';
import {
  closeModal,
  completeDocument,
  CONDITIONS_WINDOW_ID,
  createCompletedTransition,
  createRefundConditions,
  createTermForPartner,
  expectDocStatus,
  fillNumber,
  getRefundConfigRows,
  lookupKey,
  openNewIncludedRow,
  REFUND_CONFIG_TAB_ID,
  selectFirstListOption,
  selectListByKey,
  selectLookupByKey,
  setCheckbox,
  waitForNewRecordId,
} from './ContractWindowHelpers';

/**
 * Setup and amount helpers shared by the payment bonus specs ("Abzug bei Zahlung": payment-bonus-allocation.spec.js,
 * payment-bonus-header.spec.js): a customer with shipped and invoiced sales orders, the refund contract deducted at
 * payment, and the amounts of the invoice and of its bonus.
 *
 * Amounts are compared as numbers in cents, so the checks do not depend on the number format of the session.
 */

export const LANGUAGE = 'de_DE';

export const REFUND_PERCENT = 3;
export const GOODS_PRICE = 10;
export const GOODS_QTY = 10;

export const CURRENCY_EUR_ID = 102;
export const INVOICE_LINE_TAB_ID = 270; // Rechnung > Rechnungsposition
export const INVOICE_TAX_TAB = 'AD_Tab-271'; // Rechnung > Rechnungs Steuer
export const DOCTYPE_WINDOW_ID = 135; // Belegart (C_DocType)
export const DOCSUBTYPE_PAYMENT_BONUS_CREDIT_MEMO = 'PB';

// ======================================================================
// Setup
// ======================================================================

/**
 * A customer, a goods product in its own category, a bonus product, and `orders` shipped sales orders of the
 * goods, each invoiced through the invoice candidates; then logs in.
 * @returns the masterdata and the invoices ({ id, documentNo, grandTotalCents })
 */
export async function setUpCustomer(page, { orders }) {
  const salesOrders = {};
  const shipments = {};
  for (let i = 1; i <= orders; i++) {
    salesOrders[`SO${i}`] = { bpartner: 'CUSTOMER', warehouse: 'wh', datePromised: new Date().toISOString(), lines: [{ product: 'GOODS', qty: GOODS_QTY }] };
    shipments[`SH${i}`] = { salesOrder: `SO${i}` };
  }
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
      salesOrders,
      shipments,
    },
  });

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  const invoices = [];
  for (let i = 1; i <= orders; i++) {
    invoices.push(await invoiceSalesOrder(page, masterdata.salesOrders[`SO${i}`].id));
  }
  return { masterdata, invoices };
}

export async function invoiceSalesOrder(page, orderId) {
  return await test.step(`invoice the sales order ${orderId} through its invoice candidates`, async () => {
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
    const invoice = { id, documentNo, grandTotalCents: await getGrandTotalCents(id) };
    console.log(`[INFO] invoice ${JSON.stringify(invoice)}`);
    expect(invoice.grandTotalCents, 'invoice grand total').toBeGreaterThan(0);
    return invoice;
  });
}

/**
 * A completed refund condition with one line deducted at payment (product category, bonus product, percentage),
 * and a completed term of the customer on it, created with "Erzeuge Vertrag", valid from the first of the month.
 */
export async function createDeductedAtPaymentContract(page, { runId, customer, categoryId, bonus }) {
  await test.step('payment bonus contract: condition with one line deducted at payment, and the customer\'s term', async () => {
    const start = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
    const transitionId = await createCompletedTransition(page, { name: `PB transition ${runId}`, fiscalYear: start.getFullYear(), termDurationMonths: 12 });
    const conditionsId = await createRefundConditions(page, { name: `PB conditions ${runId}`, transitionId });
    await addDeductedAtPaymentLine(page, { categoryId, bonus });
    expect(await getRefundConfigRows(conditionsId)).toHaveLength(1);
    await completeDocument(page);
    await expectDocStatus(CONDITIONS_WINDOW_ID, conditionsId, 'CO');
    await createTermForPartner(page, { bpartnerId: customer.id, conditionsId, startDate: start });
  });
}

/** On the open conditions: a refund line of the category, posted to the bonus product, deducted at payment. */
export async function addDeductedAtPaymentLine(page, { categoryId, bonus }) {
  const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
  await selectListByKey(page, modal, 'M_Product_Category_ID', categoryId);
  await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
  await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
  await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
  await setCheckbox(page, modal, 'IsDeductedAtPayment');
  await closeModal(modal);
}

/** The invoice's grand total (not a field of the invoice window): the sum of its tax lines' base and tax amounts. */
export async function getGrandTotalCents(invoiceId) {
  const taxLines = (await getTabRows(SALES_INVOICE_WINDOW_ID, invoiceId, INVOICE_TAX_TAB)) ?? [];
  expect(taxLines.length, `tax lines of invoice ${invoiceId}`).toBeGreaterThan(0);
  return taxLines.reduce((sum, line) => sum + toCents(line.fieldsByName.TaxBaseAmt.value) + toCents(line.fieldsByName.TaxAmt.value), 0);
}

/**
 * The bonus the invoice gets, with VAT on top: REFUND_PERCENT % of the invoice's net goods, plus VAT at the goods' rate.
 * That the bonus product has the goods' tax (the masterdata gives every product the default tax category) is checked on
 * the credit memo of the allocation test.
 * @returns { bonusCents, goodsTaxId }
 */
export async function getExpectedBonus(invoiceId) {
  const taxLines = (await getTabRows(SALES_INVOICE_WINDOW_ID, invoiceId, INVOICE_TAX_TAB)) ?? [];
  expect(taxLines, `invoice ${invoiceId} has one tax, that of the goods`).toHaveLength(1);
  const baseCents = toCents(taxLines[0].fieldsByName.TaxBaseAmt.value);
  const taxCents = toCents(taxLines[0].fieldsByName.TaxAmt.value);
  const netBonusCents = percentOfCents(baseCents, REFUND_PERCENT);
  const bonusTaxCents = (netBonusCents * taxCents) / baseCents;
  expect(Number.isInteger(bonusTaxCents), `VAT of the ${netBonusCents} cents bonus is a whole number of cents`).toBe(true);
  return { bonusCents: netBonusCents + bonusTaxCents, goodsTaxId: lookupKey(taxLines[0].fieldsByName.C_Tax_ID.value) };
}

/** A WebAPI amount (number or numeric string with a dot) in cents. */
export function toCents(value) {
  const cents = Math.round(Number(value) * 100);
  expect(Number.isFinite(cents), `amount "${value}"`).toBe(true);
  return cents;
}

/**
 * An amount as the grid shows it (with the session's grouping and decimal separators, e.g. "1.234,57" or
 * "1,234.57", possibly with a currency) in cents. The decimal separator is the last "," or "." followed by
 * exactly two digits at the end.
 */
export function parseDisplayedAmountToCents(text) {
  const compact = text.replace(/[^0-9,.-]/g, '');
  expect(compact, `displayed amount "${text}" has digits`).toMatch(/[0-9]/);
  const match = compact.match(/^(-?)([0-9.,]*?)(?:[.,]([0-9]{2}))?$/);
  expect(match, `displayed amount "${text}"`).toBeTruthy();
  const [, sign, integerPart, decimals] = match;
  const cents = Number(integerPart.replace(/[.,]/g, '') || '0') * 100 + Number(decimals ?? '0');
  return sign === '-' ? -cents : cents;
}

/** `percent` % of an amount in cents; the test data is chosen so that this needs no rounding. */
export function percentOfCents(cents, percent) {
  const result = (cents * percent) / 100;
  expect(Number.isInteger(result), `${percent} % of ${cents} cents is a whole number of cents`).toBe(true);
  return result;
}
