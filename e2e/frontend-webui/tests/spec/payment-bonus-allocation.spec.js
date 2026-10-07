import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PAYMENT_WINDOW_ID, SALES_INVOICE_WINDOW_ID, SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { DOCTYPE_AR_RECEIPT } from '../utils/DocTypeIds';
import { getFieldData, getTabRows, waitForRecordSaved, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
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
  openNewRecord,
  openRecord,
  recordRejectedSaveReasons,
  REFUND_CONFIG_TAB_ID,
  selectFirstListOption,
  selectListByKey,
  selectLookupByKey,
  setCheckbox,
  waitForNewRecordId,
  withFieldCommit,
} from '../utils/ContractWindowHelpers';

/**
 * Payment bonus ("Abzug bei Zahlung"): a refund condition whose single refund line is deducted at payment
 * lets the customer deduct the bonus when paying a sales invoice.
 *
 *   - Vertragsbedingungen (540113), tab Rückvergütung: such a condition has exactly one refund line; a second
 *     one is rejected with AD_Message 545906.
 *   - Zahlung-Zuordnung (the payment allocation view, launched from a payment): the Zahlungsbonus column of the
 *     invoice is pre-filled with the bonus (percentage of the invoice's net goods, plus VAT); allocating creates a
 *     credit memo "Zahlungsbonus-Gutschrift" (DocSubType PB) over the bonus that references the invoice and
 *     is allocated against it, so the invoice ends up paid.
 *   - When the bonus is above the open amount, the column is pre-filled with 0 and a note says why; a bonus
 *     entered above the open amount keeps the note, and the action "Zahlung-Zuordnung" is disabled with that
 *     note as its reason.
 *
 * Amounts are typed with a dot as decimal separator: the grid amount editor is an <input type="number">,
 * which drops a decimal comma (a known limitation of the editor, not of this feature).
 * Amounts shown in the grid are compared as numbers, so the check does not depend on the number format.
 */

const LANGUAGE = 'de_DE';

const REFUND_PERCENT = 3;
const GOODS_PRICE = 10;
const GOODS_QTY = 10;
const OPEN_AFTER_FIRST_PAYMENT_CENTS = 200; // the first payment leaves 2.00 open

const CURRENCY_EUR_ID = 102;
const ALLOCATION_WINDOW_ID = 205; // Zuordnung (C_AllocationHdr)
const ALLOCATION_LINE_TAB = 'AD_Tab-349'; // its lines (C_AllocationLine)
const INVOICE_ALLOCATION_TAB = 'AD_Tab-684'; // Rechnung > Zuordnung (C_AllocationLine)
const INVOICE_LINE_TAB_ID = 270; // Rechnung > Rechnungsposition
const INVOICE_TAX_TAB = 'AD_Tab-271'; // Rechnung > Rechnungs Steuer
const DOCTYPE_WINDOW_ID = 135; // Belegart (C_DocType)
const DOCSUBTYPE_PAYMENT_BONUS_CREDIT_MEMO = 'PB';

test.describe('Payment bonus: a refund deducted at payment, applied in the payment allocation view', () => {
  test.beforeEach(async ({ page }) => {
    allure.epic('E0170: Contract Management');
    allure.severity('critical');
    page.setDefaultTimeout(60 * 1000);
    // leaving an included-row modal whose row the server did not save asks "Do you really want to leave?"
    page.on('dialog', async (dialog) => {
      console.log(`[INFO] native ${dialog.type()} dialog accepted: ${dialog.message()}`);
      await dialog.accept();
    });
  });

  test('a condition deducted at payment has a single refund line; a second one is rejected', async ({ page }) => {
    allure.story('Refund condition deducted at payment: single refund line');
    test.setTimeout(5 * 60 * 1000);
    const runId = Date.now();
    const { masterdata } = await setUpCustomer(page, { orders: 0 });
    const categoryId = masterdata.productCategories.GOODS_CATEGORY.id;
    const bonus = masterdata.products.BONUS;

    const transitionId = await createCompletedTransition(page, { name: `PB transition ${runId}`, fiscalYear: new Date().getFullYear(), termDurationMonths: 12 });
    const conditionsId = await createRefundConditions(page, { name: `PB conditions ${runId}`, transitionId });

    await test.step('a refund line deducted at payment is saved', async () => {
      await addDeductedAtPaymentLine(page, { categoryId, bonus });
      const rows = await getRefundConfigRows(conditionsId);
      expect(rows, 'exactly one refund line').toHaveLength(1);
      expect(rows[0].fieldsByName.IsDeductedAtPayment.value).toBe(true);
      expect(Number(rows[0].fieldsByName.RefundPercent.value)).toBe(REFUND_PERCENT);
    });

    await test.step('a second refund line is rejected with the message', async () => {
      // the rejected saves are recorded from the start; the last one carries the reason the dialog has to show
      // (that this reason is AD_Message 545906 is pinned by C_Flatrate_RefundConfig_Test)
      const rejectedSaves = recordRejectedSaveReasons(page, CONDITIONS_WINDOW_ID);
      const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
      await selectListByKey(page, modal, 'M_Product_Category_ID', categoryId);
      await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
      await fillNumber(page, modal, 'MinQty', 100);
      await fillNumber(page, modal, 'RefundPercent', 5);
      await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
      rejectedSaves.stop();
      const reason = rejectedSaves.reasons[rejectedSaves.reasons.length - 1];
      expect(reason, 'the rejecting save carries a reason').toBeTruthy();

      await expect(modal.locator('.window-indicator-container .bar.error')).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(modal.locator('.window-indicator-container .message-bar .text'), 'the dialog shows the server\'s reason').toHaveText(reason, { timeout: SLOW_ACTION_TIMEOUT });
      await closeModal(modal); // the native "leave?" dialog is accepted

      const rows = await getRefundConfigRows(conditionsId);
      expect(rows, 'still exactly one refund line').toHaveLength(1);
      expect(Number(rows[0].fieldsByName.MinQty.value)).toBe(0);
    });
  });

  test('the payment allocation view pre-fills the bonus, and allocating creates the Zahlungsbonus credit memo; the invoice is paid', async ({ page }) => {
    allure.story('Payment allocation: pre-filled payment bonus, credit memo, invoice paid');
    test.setTimeout(10 * 60 * 1000);
    const runId = Date.now();
    const { masterdata, invoices } = await setUpCustomer(page, { orders: 1 });
    const customer = masterdata.bpartners.CUSTOMER;
    const bonus = masterdata.products.BONUS;
    const [invoice] = invoices;
    const { bonusCents, goodsTaxId } = await getExpectedBonus(invoice.id);

    await createDeductedAtPaymentContract(page, { runId, customer, categoryId: masterdata.productCategories.GOODS_CATEGORY.id, bonus });

    // the customer pays the invoice minus the bonus
    const paymentId = await createReceipt(page, { customer, amountCents: invoice.grandTotalCents - bonusCents });

    await test.step('Zahlung-Zuordnung: the Zahlungsbonus of the invoice is pre-filled with the bonus', async () => {
      await openPaymentAllocationView(page, paymentId);
      const row = invoiceRow(page, invoice.documentNo);
      await expect(row).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      expect(await cellCents(row, 'openAmt'), 'open amount').toBe(invoice.grandTotalCents);
      expect(await cellCents(row, 'paymentBonusAmt'), 'pre-filled Zahlungsbonus').toBe(bonusCents);
      expect((await cellText(row, 'paymentBonusNote')), 'no note when the bonus can be deducted').toBe('');
    });

    await test.step('mark the invoice and allocate', async () => {
      const row = invoiceRow(page, invoice.documentNo);
      await markInvoiceForAllocation(page, row);
      await expectQuickAction(page, 'PaymentsView_Allocate', { disabled: false });
      const allocated = page.waitForResponse((r) => r.url().includes('/process/') && r.url().endsWith('/start'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await clickQuickAction(page, 'PaymentsView_Allocate');
      expect((await allocated).ok(), 'Zahlung-Zuordnung runs without error').toBe(true);
      await expect(invoiceRow(page, invoice.documentNo), 'the paid invoice leaves the view').toHaveCount(0, { timeout: VERY_SLOW_ACTION_TIMEOUT });
    });

    await test.step('the invoice is paid, by the payment and by the Zahlungsbonus credit memo', async () => {
      await expect.poll(async () => (await getFieldData(SALES_INVOICE_WINDOW_ID, invoice.id, 'IsPaid')).value, {
        message: 'invoice IsPaid',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).toBe(true);

      const allocationLines = (await getTabRows(SALES_INVOICE_WINDOW_ID, invoice.id, INVOICE_ALLOCATION_TAB)) ?? [];
      console.log(`[INFO] allocation lines of invoice ${invoice.id}: ${JSON.stringify(allocationLines.map((l) => [lookupKey(l.fieldsByName.C_Payment_ID?.value), l.fieldsByName.Amount?.value]))}`);
      const paymentLine = allocationLines.find((l) => lookupKey(l.fieldsByName.C_Payment_ID?.value) === String(paymentId));
      const bonusLine = allocationLines.find((l) => !lookupKey(l.fieldsByName.C_Payment_ID?.value));
      expect(paymentLine, 'allocation line of the payment').toBeTruthy();
      expect(toCents(paymentLine.fieldsByName.Amount.value)).toBe(invoice.grandTotalCents - bonusCents);
      expect(bonusLine, 'allocation line of the credit memo').toBeTruthy();
      expect(toCents(bonusLine.fieldsByName.Amount.value)).toBe(bonusCents);

      // the other line of that allocation is the credit memo
      const allocationId = lookupKey(bonusLine.fieldsByName.C_AllocationHdr_ID.value);
      const allocationRows = (await getTabRows(ALLOCATION_WINDOW_ID, allocationId, ALLOCATION_LINE_TAB)) ?? [];
      const creditMemoLine = allocationRows.find((l) => lookupKey(l.fieldsByName.C_Invoice_ID?.value) !== String(invoice.id));
      expect(creditMemoLine, 'the credit memo is allocated against the invoice').toBeTruthy();
      expect(toCents(creditMemoLine.fieldsByName.Amount.value)).toBe(-bonusCents);
      const creditMemoId = lookupKey(creditMemoLine.fieldsByName.C_Invoice_ID.value);

      const creditMemo = await getFields(SALES_INVOICE_WINDOW_ID, creditMemoId, ['DocStatus', 'IsPaid', 'C_DocTypeTarget_ID', 'C_BPartner_ID']);
      expect(lookupKey(creditMemo.DocStatus)).toBe('CO');
      expect(creditMemo.IsPaid).toBe(true);
      expect(await getGrandTotalCents(creditMemoId), 'credit memo over the bonus').toBe(bonusCents);
      const creditMemoTaxIds = ((await getTabRows(SALES_INVOICE_WINDOW_ID, creditMemoId, INVOICE_TAX_TAB)) ?? []).map((l) => lookupKey(l.fieldsByName.C_Tax_ID.value));
      expect(creditMemoTaxIds, 'the bonus has the VAT of the goods, as the expected bonus assumes').toEqual([goodsTaxId]);
      expect(lookupKey(creditMemo.C_BPartner_ID)).toBe(String(customer.id));
      const docTypeId = lookupKey(creditMemo.C_DocTypeTarget_ID);
      expect(lookupKey((await getFieldData(DOCTYPE_WINDOW_ID, docTypeId, 'DocBaseType')).value)).toBe('ARC');
      expect(lookupKey((await getFieldData(DOCTYPE_WINDOW_ID, docTypeId, 'DocSubType')).value), 'Zahlungsbonus-Gutschrift').toBe(DOCSUBTYPE_PAYMENT_BONUS_CREDIT_MEMO);

      // Ref_Invoice_ID is a field of the advanced edit only
      expect(lookupKey(await getAdvancedFieldValue(SALES_INVOICE_WINDOW_ID, creditMemoId, 'Ref_Invoice_ID')), 'credit memo references the invoice').toBe(String(invoice.id));

      // the credit memo, opened in the WebUI, has one line with the bonus product
      await openRecord(page, SALES_INVOICE_WINDOW_ID, creditMemoId);
      await page.getByTestId(`tab-AD_Tab-${INVOICE_LINE_TAB_ID}`).click();
      const productCells = page.locator('.tab-pane td[data-cy="cell-M_Product_ID"]');
      await expect(productCells).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
      await expect(productCells.first()).toContainText(bonus.productName);
    });
  });

  test('a bonus above the open amount: pre-filled 0 with a note, and an entered bonus disables Zahlung-Zuordnung with that note as reason', async ({ page }) => {
    allure.story('Payment allocation: payment bonus above the open amount');
    test.setTimeout(10 * 60 * 1000);
    const runId = Date.now();
    const { masterdata, invoices } = await setUpCustomer(page, { orders: 1 });
    const customer = masterdata.bpartners.CUSTOMER;
    const [invoice] = invoices;
    const { bonusCents } = await getExpectedBonus(invoice.id);
    const openCents = OPEN_AFTER_FIRST_PAYMENT_CENTS;
    expect(openCents, 'what is left open is less than the bonus').toBeLessThan(bonusCents);

    await test.step('before any payment bonus contract exists, a first payment leaves less than the bonus open', async () => {
      const firstPaymentId = await createReceipt(page, { customer, amountCents: invoice.grandTotalCents - openCents });
      await openPaymentAllocationView(page, firstPaymentId);
      const row = invoiceRow(page, invoice.documentNo);
      expect(await cellText(row, 'paymentBonusAmt'), 'no bonus without a contract: the cell is empty').toBe('');
      await markInvoiceForAllocation(page, row);
      await expectQuickAction(page, 'PaymentsView_Allocate', { disabled: false });
      const allocated = page.waitForResponse((r) => r.url().includes('/process/') && r.url().endsWith('/start'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
      await clickQuickAction(page, 'PaymentsView_Allocate');
      expect((await allocated).ok(), 'Zahlung-Zuordnung runs without error').toBe(true);
      await expect.poll(async () => getAllocatedCents(invoice.id), {
        message: 'amount allocated to the invoice by the first payment',
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      }).toBe(invoice.grandTotalCents - openCents);
    });

    await createDeductedAtPaymentContract(page, { runId, customer, categoryId: masterdata.productCategories.GOODS_CATEGORY.id, bonus: masterdata.products.BONUS });
    const paymentId = await createReceipt(page, { customer, amountCents: openCents });

    let note;
    await test.step('Zahlung-Zuordnung: the Zahlungsbonus is pre-filled with 0, and a note says why', async () => {
      await openPaymentAllocationView(page, paymentId);
      const row = invoiceRow(page, invoice.documentNo);
      expect(await cellCents(row, 'openAmt'), 'open amount').toBe(openCents);
      expect(await cellCents(row, 'paymentBonusAmt'), 'pre-filled Zahlungsbonus').toBe(0);
      note = await cellText(row, 'paymentBonusNote');
      expect(note, 'note why there is no bonus').not.toBe('');
    });

    await test.step('the bonus entered by hand keeps the note, and Zahlung-Zuordnung is disabled with it as reason', async () => {
      const row = invoiceRow(page, invoice.documentNo);
      await markInvoiceForAllocation(page, row);
      await enterPaymentBonus(page, row, bonusCents);
      await expect.poll(async () => cellCents(row, 'paymentBonusAmt'), { message: 'entered Zahlungsbonus', timeout: SLOW_ACTION_TIMEOUT }).toBe(bonusCents);
      expect(await cellText(row, 'paymentBonusNote'), 'the note stays').toBe(note);

      const reason = await expectQuickAction(page, 'PaymentsView_Allocate', { disabled: true });
      expect(reason, 'the reason is the note').toBe(note);
    });

    await test.step('nothing more was allocated: the invoice is still open', async () => {
      expect(await getAllocatedCents(invoice.id)).toBe(invoice.grandTotalCents - openCents);
      expect((await getFieldData(SALES_INVOICE_WINDOW_ID, invoice.id, 'IsPaid')).value).toBe(false);
    });
  });
});

// ======================================================================
// Setup
// ======================================================================

/**
 * A customer, a goods product in its own category, a bonus product, and `orders` shipped sales orders of the
 * goods, each invoiced through the invoice candidates; then logs in.
 * @returns the masterdata and the invoices ({ id, documentNo, grandTotalCents })
 */
async function setUpCustomer(page, { orders }) {
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

async function invoiceSalesOrder(page, orderId) {
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
async function createDeductedAtPaymentContract(page, { runId, customer, categoryId, bonus }) {
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
async function addDeductedAtPaymentLine(page, { categoryId, bonus }) {
  const modal = await openNewIncludedRow(page, REFUND_CONFIG_TAB_ID);
  await selectListByKey(page, modal, 'M_Product_Category_ID', categoryId);
  await selectLookupByKey(page, modal, 'Bonus_Product_ID', bonus.productCode, bonus.id);
  await fillNumber(page, modal, 'RefundPercent', REFUND_PERCENT);
  await selectFirstListOption(page, modal, 'C_InvoiceSchedule_ID');
  await setCheckbox(page, modal, 'IsDeductedAtPayment');
  await closeModal(modal);
}

/** Zahlung (195): a completed receipt of the customer in EUR over the given amount. @returns the payment id */
async function createReceipt(page, { customer, amountCents }) {
  return await test.step(`Zahlung: a receipt of ${amountCents / 100} EUR`, async () => {
    await openNewRecord(page, PAYMENT_WINDOW_ID);
    const paymentId = await waitForNewRecordId(page, PAYMENT_WINDOW_ID);
    await selectListByKey(page, page, 'C_DocType_ID', DOCTYPE_AR_RECEIPT);
    await selectLookupByKey(page, page, 'C_BPartner_ID', customer.bpartnerCode, customer.id);
    await selectFirstListOption(page, page, 'C_BP_BankAccount_ID');
    await selectListByKey(page, page, 'C_Currency_ID', CURRENCY_EUR_ID);
    await fillNumber(page, page, 'PayAmt', (amountCents / 100).toFixed(2));
    await waitForRecordSaved(PAYMENT_WINDOW_ID, paymentId, { maxRetries: 20, retryDelayMs: 500 });
    expect(toCents((await getFieldData(PAYMENT_WINDOW_ID, paymentId, 'PayAmt')).value)).toBe(amountCents);
    await completeDocument(page);
    await expectDocStatus(PAYMENT_WINDOW_ID, paymentId, 'CO');
    return paymentId;
  });
}

// ======================================================================
// Payment allocation view
// ======================================================================

async function openPaymentAllocationView(page, paymentId) {
  await openRecord(page, PAYMENT_WINDOW_ID, paymentId);
  await page.locator('.meta-icon-more').first().click();
  await page.locator('.subheader-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.getByTestId('action-PaymentView_Launcher_FromPayment_SingleDocument').click();
  await page.locator('td[data-cy="cell-paymentBonusAmt"]').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
}

function invoiceRow(page, documentNo) {
  return page.locator('tr').filter({ has: page.locator('td[data-cy="cell-documentNo"]', { hasText: documentNo }) })
    .filter({ has: page.locator('td[data-cy="cell-paymentBonusAmt"]') });
}

/** The text a grid cell shows; for a cell that is being edited, the value of its editor. */
async function cellText(row, columnName) {
  const cell = row.locator(`td[data-cy="cell-${columnName}"]`);
  await cell.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
  const editor = cell.locator('input');
  if ((await editor.count()) > 0) {
    return (await editor.first().inputValue()).trim();
  }
  return (await cell.innerText()).trim();
}

async function cellCents(row, columnName) {
  return parseDisplayedAmountToCents(await cellText(row, columnName));
}

/** Select the invoice row and run "Auswahl für Zuordnung" (InvoicesView_MarkPreparedForAllocation). */
async function markInvoiceForAllocation(page, row) {
  // the first click into a grid may only focus it, so the click is repeated until the row is selected
  await expect(async () => {
    await row.locator('td[data-cy="cell-currencyCodeString"]').click();
    await expect(row).toHaveClass(/row-selected/, { timeout: 2000 });
  }).toPass({ timeout: SLOW_ACTION_TIMEOUT });
  await expectQuickAction(page, 'InvoicesView_MarkPreparedForAllocation', { disabled: false });
  await clickQuickAction(page, 'InvoicesView_MarkPreparedForAllocation');
  // once marked, the row offers to undo the mark instead
  await expectQuickAction(page, 'InvoicesView_UnMarkPreparedForAllocation', { disabled: false });
}

/** Type the bonus into the row's Zahlungsbonus cell (with a dot as decimal separator) and wait until it is sent. */
async function enterPaymentBonus(page, row, cents) {
  const cell = row.locator('td[data-cy="cell-paymentBonusAmt"]');
  await cell.dblclick();
  const input = cell.locator('input').first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.fill((cents / 100).toFixed(2));
  await withFieldCommit(page, 'paymentBonusAmt', () => input.press('Enter'));
}

/**
 * Open the quick-actions dropdowns of the view until the given action is listed, and wait until it is
 * (not) disabled; the actions are recomputed after each selection or edit.
 * @returns the reason shown for a disabled action, without the parentheses
 */
async function expectQuickAction(page, processValue, { disabled }) {
  let reason = null;
  await expect.poll(async () => {
    try {
      const item = await openQuickActionsDropdownListing(page, processValue);
      if (!item) {
        return 'not listed';
      }
      const isDisabled = (await item.getAttribute('class')).includes('quick-actions-item-disabled');
      reason = isDisabled ? (await item.locator('small').innerText()).trim().replace(/^\((.*)\)$/s, '$1') : null;
      return isDisabled ? 'disabled' : 'enabled';
    } finally {
      // also when reading the item failed midway, so that the next attempt starts with a closed dropdown
      await closeQuickActionsDropdown(page);
    }
  }, { message: `quick action ${processValue}`, timeout: VERY_SLOW_ACTION_TIMEOUT, intervals: [1000] }).toBe(disabled ? 'disabled' : 'enabled');
  return reason;
}

async function clickQuickAction(page, processValue) {
  const item = await openQuickActionsDropdownListing(page, processValue);
  expect(item, `quick action ${processValue} is listed`).toBeTruthy();
  await item.click();
}

/** @returns the dropdown item of the action, with its dropdown left open; null if no dropdown lists it */
async function openQuickActionsDropdownListing(page, processValue) {
  const toggles = page.getByTestId('quick-action-dropdown-toggle');
  const count = await toggles.count();
  for (let i = 0; i < count; i++) {
    await toggles.nth(i).click();
    await page.locator('.quick-actions-dropdown').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const item = page.getByTestId(`quick-action-${processValue}`);
    if ((await item.count()) > 0) {
      return item.first();
    }
    await closeQuickActionsDropdown(page);
  }
  return null;
}

async function closeQuickActionsDropdown(page) {
  if ((await page.locator('.quick-actions-dropdown').count()) > 0) {
    // a click outside the dropdown closes it; a click on the label of its own actions bar does not unselect the rows
    // (the bar is marked js-not-unselect), while a click elsewhere in the page would
    await page.locator('.js-not-unselect').filter({ has: page.locator('.quick-actions-dropdown') }).locator('.action-label').first().click();
    await expect(page.locator('.quick-actions-dropdown')).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
  }
}

// ======================================================================
// Amounts
// ======================================================================

/** @returns the fields' values of a record, by field name */
async function getFields(windowId, recordId, fieldNames) {
  const result = {};
  for (const fieldName of fieldNames) {
    result[fieldName] = (await getFieldData(windowId, recordId, fieldName)).value;
  }
  return result;
}

/** @returns the value of a field that only the advanced edit of the record shows */
async function getAdvancedFieldValue(windowId, recordId, fieldName) {
  const response = await getPage().request.get(`${WEBAPI_BASE_URL}/window/${windowId}/${recordId}?advanced=true`);
  expect(response.ok(), `advanced record data of ${windowId}/${recordId}`).toBe(true);
  const field = (await response.json())[0]?.fieldsByName?.[fieldName];
  expect(field, `field ${fieldName} of ${windowId}/${recordId} (advanced)`).toBeTruthy();
  return field.value;
}

/** The invoice's grand total (not a field of the invoice window): the sum of its tax lines' base and tax amounts. */
async function getGrandTotalCents(invoiceId) {
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
async function getExpectedBonus(invoiceId) {
  const taxLines = (await getTabRows(SALES_INVOICE_WINDOW_ID, invoiceId, INVOICE_TAX_TAB)) ?? [];
  expect(taxLines, `invoice ${invoiceId} has one tax, that of the goods`).toHaveLength(1);
  const baseCents = toCents(taxLines[0].fieldsByName.TaxBaseAmt.value);
  const taxCents = toCents(taxLines[0].fieldsByName.TaxAmt.value);
  const netBonusCents = percentOfCents(baseCents, REFUND_PERCENT);
  const bonusTaxCents = (netBonusCents * taxCents) / baseCents;
  expect(Number.isInteger(bonusTaxCents), `VAT of the ${netBonusCents} cents bonus is a whole number of cents`).toBe(true);
  return { bonusCents: netBonusCents + bonusTaxCents, goodsTaxId: lookupKey(taxLines[0].fieldsByName.C_Tax_ID.value) };
}

/** The sum of the invoice's allocation lines (payments and credit memos allocated to it). */
async function getAllocatedCents(invoiceId) {
  const lines = (await getTabRows(SALES_INVOICE_WINDOW_ID, invoiceId, INVOICE_ALLOCATION_TAB)) ?? [];
  return lines.reduce((sum, line) => sum + toCents(line.fieldsByName.Amount.value), 0);
}

/** A WebAPI amount (number or numeric string with a dot) in cents. */
function toCents(value) {
  const cents = Math.round(Number(value) * 100);
  expect(Number.isFinite(cents), `amount "${value}"`).toBe(true);
  return cents;
}

/**
 * An amount as the grid shows it (with the session's grouping and decimal separators, e.g. "1.234,57" or
 * "1,234.57", possibly with a currency) in cents. The decimal separator is the last "," or "." followed by
 * exactly two digits at the end.
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

/** `percent` % of an amount in cents; the test data is chosen so that this needs no rounding. */
function percentOfCents(cents, percent) {
  const result = (cents * percent) / 100;
  expect(Number.isInteger(result), `${percent} % of ${cents} cents is a whole number of cents`).toBe(true);
  return result;
}
