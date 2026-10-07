import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_INVOICE_WINDOW_ID, SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';

/**
 * Grid in-row amount editor — a decimal typed with the user's own decimal separator must be stored as typed.
 *
 * Regression guard: in a German session, typing "3,57" into an Amount cell of a grid view stored 357.
 * The amount editor was a browser <input type="number">, which silently drops the comma keystroke, so the
 * PATCH carried "357". The editor is now a text input and the typed text is converted to the dot-decimal
 * the backend expects, using the separators of the user's session locale.
 *
 * Grid: the payment-allocation view opened from a sales invoice, column "discountAmt" (Amount widget,
 * edited in-row). One invoice serves both sessions: the German test creates it, the English test reuses it
 * (each opens its own allocation view, so the edits do not interfere). The invoice is created through the
 * invoice-candidate UI, because the `invoices` masterdata request times out waiting for the invoice.
 * The tests therefore run serially: the English tests need the German one's invoice and are skipped when it fails
 * (also when only they are selected with --grep). Every allocation view starts with a zero discount.
 */
const PAYMENT_ALLOCATION_FROM_INVOICE_ACTION = 'PaymentView_Launcher_From_C_Invoice_SingleDocument';
const EXPECTED_AMOUNT = 3.57;
/** How the grid renders EXPECTED_AMOUNT and a zero amount with the separators of each session language */
const DISPLAYED_AMOUNT = { de_DE: { expected: '3,57', zero: '0,00' }, en_US: { expected: '3.57', zero: '0.00' } };

const setupAllure = (language) => {
  allure.epic('E0294: Frontend WebUI');
  allure.tag('F50000: Frontend WebUI');
  allure.tag('F50000');
  allure.tag(language);
  allure.story('Decimal separator in amount editors');
  allure.severity('critical');
};

const loginAs = async (language, extraMasterdata = {}) => {
  const masterdata = await Backend.createMasterdata({
    request: { login: { user: { language, firstname: 'first', lastname: 'last' } }, ...extraMasterdata },
  });
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await LoginPage.expectLoggedIn();
  return masterdata;
};

const openAllocationViewOfInvoice = async (page, invoiceId) => {
  await test.step('open the payment allocation view from the sales invoice', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_INVOICE_WINDOW_ID}/${invoiceId}`);
    await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('.meta-icon-more').click();
    await page.locator('.subheader-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.getByTestId(`action-${PAYMENT_ALLOCATION_FROM_INVOICE_ACTION}`).click();
  });
};

/** Types into the invoice row's discount cell, presses Enter and returns the PATCH sent and the server's answer */
const typeIntoDiscountCell = async (page, { invoiceDocumentNo, typed }) => {
  const invoiceRow = page.locator('tr', { hasText: invoiceDocumentNo }).first();
  const discountCell = invoiceRow.locator('[data-cy="cell-discountAmt"]');
  await discountCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  let patchRequest = null;
  let patchResponse = null;
  await test.step(`type ${typed} into the discount amount cell and press Enter`, async () => {
    await invoiceRow.locator('[data-cy="cell-documentNo"]').click(); // select the row
    await discountCell.dblclick(); // on a selected row, a double click opens the in-row editor
    const input = discountCell.locator('input');
    await expect(input).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await input.press('Control+a');
    await input.pressSequentially(typed, { delay: 80 });

    // what the user typed is what the editor holds (a browser number input would show 357 for 3,57)
    expect(await input.inputValue(), 'text in the amount editor after typing').toBe(typed);

    const isDiscountPatch = (req) => req.method() === 'PATCH' && (req.postData() || '').includes('discountAmt');
    const patchRequested = page.waitForRequest(isDiscountPatch, { timeout: SLOW_ACTION_TIMEOUT });
    const patchResponded = page.waitForResponse((resp) => isDiscountPatch(resp.request()), {
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await input.press('Enter');
    patchRequest = JSON.parse((await patchRequested).postData());
    patchResponse = await patchResponded;
  });

  return { invoiceRow, discountCell, patchRequest, patchResponse };
};

/** Asserts the PATCH, the server's row and the grid cell all hold EXPECTED_AMOUNT */
const expectDiscountStored = async ({ invoiceRow, discountCell, patchRequest, patchResponse }, language) => {
  expect(patchRequest, 'PATCH sent for discountAmt').toContainEqual(
    expect.objectContaining({ path: 'discountAmt', value: '3.57' })
  );

  expect(patchResponse.status(), 'PATCH response status').toBe(200);
  const body = await patchResponse.json();
  const rows = Array.isArray(body) ? body : [body];
  const storedValues = rows.map((row) => row?.fieldsByName?.discountAmt?.value).filter((value) => value !== undefined);
  expect(storedValues.length, 'discountAmt returned by the server').toBeGreaterThan(0);
  expect(Number(storedValues[storedValues.length - 1]), 'discountAmt stored by the server').toBe(EXPECTED_AMOUNT);

  await test.step('the grid cell shows the stored amount', async () => {
    await expectDisplayedDiscount({ invoiceRow, discountCell }, DISPLAYED_AMOUNT[language].expected);
  });
};

const expectDisplayedDiscount = async ({ invoiceRow, discountCell }, expectedText) => {
  await invoiceRow.locator('[data-cy="cell-documentNo"]').click(); // leave the editor
  await expect(discountCell).toHaveText(expectedText, { timeout: SLOW_ACTION_TIMEOUT });
};

test.describe.serial('Grid in-row amount editor - decimal separator of the session', () => {
  const invoice = { id: null, documentNo: null };

  test('German session: 3,57 typed into a grid amount cell is stored as 3.57', async ({ page }) => {
    setupAllure('de_DE');
    test.setTimeout(300000); // order -> shipment -> invoice chain, then the allocation view
    await page.setViewportSize({ width: 2400, height: 1000 }); // the allocation view's grids are wide

    const masterdata = await loginAs('de_DE', {
      bpartners: {
        CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
      },
      products: {
        Product1: { name: 'PROD', type: 'Item', prices: [{ price: 50.0, currencyCode: 'EUR' }] },
      },
      warehouses: { wh: {} },
      salesOrders: {
        SO1: {
          bpartner: 'CUSTOMER1',
          warehouse: 'wh',
          datePromised: '2026-03-01T00:00:00.000+01:00',
          lines: [{ product: 'Product1', qty: 2 }],
        },
      },
      shipments: { SH1: { salesOrder: 'SO1', complete: true } }, // the invoice rule invoices what was delivered
    });

    await test.step('invoice the delivered sales order', async () => {
      const salesOrderUrl = `${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${masterdata.salesOrders.SO1.id}`;
      await page.goto(salesOrderUrl);
      await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await SalesOrderPage.openRelatedInvoiceCandidate({ maxRetries: 15, retryDelay: 3000, refreshOnRetry: true });
      await InvoiceCandidatePage.expectVisibleForSalesOrder();
      await InvoiceCandidatePage.createInvoiceForSalesOrder();

      await page.goto(salesOrderUrl);
      await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await SalesOrderPage.openRelatedInvoice({ maxRetries: 15, retryDelay: 3000, refreshOnRetry: true });
      await InvoicePage.expectVisible();
      invoice.documentNo = await InvoicePage.getDocumentNo();
      await InvoicePage.openDetailView();
      await expect(page).toHaveURL(new RegExp(`/window/${SALES_INVOICE_WINDOW_ID}/\\d+`));
      invoice.id = page.url().match(new RegExp(`/window/${SALES_INVOICE_WINDOW_ID}/(\\d+)`))[1];
    });

    await openAllocationViewOfInvoice(page, invoice.id);
    const edit = await typeIntoDiscountCell(page, { invoiceDocumentNo: invoice.documentNo, typed: '3,57' });
    await expectDiscountStored(edit, 'de_DE');
  });

  test('English session: 3.57 is stored as 3.57', async ({ page }) => {
    setupAllure('en_US');
    test.setTimeout(120000);
    await page.setViewportSize({ width: 2400, height: 1000 });
    await loginAs('en_US');

    await openAllocationViewOfInvoice(page, invoice.id);
    const edit = await typeIntoDiscountCell(page, { invoiceDocumentNo: invoice.documentNo, typed: '3.57' });
    await expectDiscountStored(edit, 'en_US');
  });

  test('English session: 1,5 (a comma that is no valid grouping) is rejected, never stored as 15', async ({ page }) => {
    setupAllure('en_US');
    test.setTimeout(120000);
    await page.setViewportSize({ width: 2400, height: 1000 });
    await loginAs('en_US');

    await openAllocationViewOfInvoice(page, invoice.id);
    const edit = await typeIntoDiscountCell(page, { invoiceDocumentNo: invoice.documentNo, typed: '1,5' });

    // sent as typed, so that the backend rejects it ...
    expect(edit.patchRequest, 'PATCH sent for discountAmt').toContainEqual(
      expect.objectContaining({ path: 'discountAmt', value: '1,5' })
    );
    // ... which an editable view answers with the unchanged row plus the error (HTTP 200, see ViewRowEditRestController)
    expect(edit.patchResponse.status(), 'PATCH response status').toBe(200);
    const body = await edit.patchResponse.json();
    const row = Array.isArray(body) ? body[0] : body;
    expect(row.error, 'the row edit reports an error').toBeTruthy();
    expect(row.fieldsByName?.discountAmt?.value, 'discountAmt kept by the server').toBe('0');

    // and the grid shows the unchanged amount
    await expectDisplayedDiscount(edit, DISPLAYED_AMOUNT.en_US.zero);
  });
});
