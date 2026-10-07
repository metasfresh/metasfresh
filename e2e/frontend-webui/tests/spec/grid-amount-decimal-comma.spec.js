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
 * Grid in-row amount editor — numbers are read and edited the way the user types them, with either separator.
 *
 * Regression guard: in a German session, typing "3,57" into an Amount cell of a grid view stored 357, because the
 * amount editor was a browser <input type="number">, which silently drops the comma keystroke. Now:
 * - both the comma and the dot are read as the decimal separator ("3,57" and "3.57" are both 3.57, in either session);
 * - there is no thousands grouping on input ("1.000" is the decimal 1, not 1000);
 * - the typed value is normalized to a dot-decimal before it is patched (the backend parses a dot-decimal);
 * - only a text that is no number (which only a paste can bring in) is refused, with an error notification that says why;
 * - a field that is opened and left untouched is not patched.
 *
 * Grid: the payment-allocation view opened from a sales invoice, column "discountAmt" (Amount widget, edited in-row).
 * One invoice serves all tests: the first German test creates it (through the invoice-candidate UI, because the
 * `invoices` masterdata request times out waiting for the invoice), the others reuse it. The tests therefore run
 * serially and are skipped when the first one fails (also when only they are selected with --grep). Every test opens
 * its own allocation view, which starts with a zero discount.
 */
const PAYMENT_ALLOCATION_FROM_INVOICE_ACTION = 'PaymentView_Launcher_From_C_Invoice_SingleDocument';

/** How the grid renders an amount with the separators of each session language */
const DISPLAYED = {
  de_DE: { '3.57': '3,57', '1.000': '1,00' },
  en_US: { '3.57': '3.57', '1.5': '1.50' },
};

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

const isDiscountPatch = (req) => req.method() === 'PATCH' && (req.postData() || '').includes('discountAmt');

const discountCellOf = (page, invoiceDocumentNo) => {
  const invoiceRow = page.locator('tr', { hasText: invoiceDocumentNo }).first();
  return { invoiceRow, discountCell: invoiceRow.locator('[data-cy="cell-discountAmt"]') };
};

/** Opens the in-row editor of the invoice row's discount cell and returns its input */
const openDiscountEditor = async ({ invoiceRow, discountCell }) => {
  await discountCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await invoiceRow.locator('[data-cy="cell-documentNo"]').click(); // select the row
  await discountCell.dblclick(); // on a selected row, a double click opens the in-row editor
  const input = discountCell.locator('input');
  await expect(input).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  return input;
};

const typeIntoEditor = async (input, typed) => {
  await input.press('Control+a');
  await input.pressSequentially(typed, { delay: 80 });
  // what the user typed is what the editor holds (a browser number input would show 357 for 3,57)
  expect(await input.inputValue(), 'text in the amount editor after typing').toBe(typed);
};

/** Types a valid amount, presses Enter and asserts the PATCH, the server's row and the grid cell */
const typeAndExpectStored = async (page, { invoiceDocumentNo, typed, patched, language }) => {
  const cell = discountCellOf(page, invoiceDocumentNo);

  await test.step(`type ${typed}: stored as ${patched}`, async () => {
    const input = await openDiscountEditor(cell);
    await typeIntoEditor(input, typed);

    const patchRequested = page.waitForRequest(isDiscountPatch, { timeout: SLOW_ACTION_TIMEOUT });
    const patchResponded = page.waitForResponse((resp) => isDiscountPatch(resp.request()), {
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await input.press('Enter');

    expect(JSON.parse((await patchRequested).postData()), 'PATCH sent for discountAmt').toContainEqual(
      expect.objectContaining({ path: 'discountAmt', value: patched })
    );
    const patchResponse = await patchResponded;
    expect(patchResponse.status(), 'PATCH response status').toBe(200);
    const body = await patchResponse.json();
    const row = Array.isArray(body) ? body[0] : body;
    expect(row.error, 'error of the row edit').toBeUndefined();
    expect(Number(row.fieldsByName?.discountAmt?.value), 'discountAmt stored by the server').toBe(Number(patched));

    await expectDisplayedDiscount(cell, DISPLAYED[language][patched]);
  });
};

/**
 * Simulates pasting text into the decimal input by firing the same "input" event (inputType "insertFromPaste") the
 * browser fires on a real paste. A letter typed key-by-key is swallowed by the decimal input, so a paste is the only
 * way a text that is no number reaches the field — and the way it is refused.
 */
const pasteIntoInput = async (input, text) => {
  await input.evaluate((el, value) => {
    const valueSetter = Object.getOwnPropertyDescriptor(
      window.HTMLInputElement.prototype,
      'value'
    ).set;
    valueSetter.call(el, value);
    el.dispatchEvent(
      new InputEvent('input', {
        bubbles: true,
        cancelable: true,
        inputType: 'insertFromPaste',
        data: value,
      })
    );
  }, text);
};

/**
 * Pastes a text that is no number into the in-row editor and asserts it is refused: a visible error, the editor stays
 * open and focused showing the kept amount (the stored amount it reverts to), and no PATCH
 */
const pasteAndExpectRefused = async (page, { invoiceDocumentNo, pasted, keptEditText, keptDisplayed }) => {
  const cell = discountCellOf(page, invoiceDocumentNo);

  await test.step(`paste ${pasted} (a text that is no number): refused with a visible error, the amount is kept`, async () => {
    const patches = [];
    const collectPatch = (req) => isDiscountPatch(req) && patches.push(req.postData());
    page.on('request', collectPatch);

    const input = await openDiscountEditor(cell);
    await pasteIntoInput(input, pasted);

    // the error names what was pasted, which is no localized text
    await expect(page.locator('.notification-item.error', { hasText: pasted }).first()).toBeVisible({
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await expect(input, 'the editor shows the kept amount again').toHaveValue(keptEditText);
    await expect(input, 'the editor keeps the focus').toBeFocused();
    await expectDisplayedDiscount(cell, keptDisplayed);

    page.off('request', collectPatch);
    expect(patches, 'PATCHes sent for discountAmt').toEqual([]);
  });
};

/** Opens the discount editor and leaves it without typing: nothing must be patched */
const focusAndLeaveUntouched = async (page, { invoiceDocumentNo, expectedEditText, keptDisplayed }) => {
  const cell = discountCellOf(page, invoiceDocumentNo);

  await test.step('open the discount editor and leave it untouched: nothing is patched', async () => {
    const input = await openDiscountEditor(cell);
    // edit mode shows the stored amount with the session's decimal separator and no grouping
    await expect(input).toHaveValue(expectedEditText);

    // a PATCH would be sent right when the editor is left; none must come within a few seconds
    const patchRequested = page.waitForRequest(isDiscountPatch, { timeout: 3000 }).catch(() => null);
    await expectDisplayedDiscount(cell, keptDisplayed); // leaves the editor
    expect(await patchRequested, 'PATCH sent for the untouched discountAmt').toBeNull();
  });
};

const expectDisplayedDiscount = async ({ invoiceRow, discountCell }, expectedText) => {
  await invoiceRow.locator('[data-cy="cell-documentNo"]').click(); // leave the editor
  await expect(discountCell).toHaveText(expectedText, { timeout: SLOW_ACTION_TIMEOUT });
};

test.describe.serial('Grid in-row amount editor - separators of the session', () => {
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
    const invoiceDocumentNo = invoice.documentNo;
    await typeAndExpectStored(page, { invoiceDocumentNo, typed: '3,57', patched: '3.57', language: 'de_DE' });
    await focusAndLeaveUntouched(page, { invoiceDocumentNo, expectedEditText: '3,57', keptDisplayed: '3,57' });
  });

  test('German session: the dot is a decimal separator too (3.57, 1.000 accepted); a text that is no number is refused with a visible error', async ({ page }) => {
    setupAllure('de_DE');
    test.setTimeout(120000);
    await page.setViewportSize({ width: 2400, height: 1000 });
    await loginAs('de_DE');

    await openAllocationViewOfInvoice(page, invoice.id);
    const invoiceDocumentNo = invoice.documentNo;
    // a dotted decimal is accepted (the dot is the decimal separator too); "1.000" is the decimal 1, not grouped to 1000
    await typeAndExpectStored(page, { invoiceDocumentNo, typed: '1.000', patched: '1.000', language: 'de_DE' });
    await typeAndExpectStored(page, { invoiceDocumentNo, typed: '3.57', patched: '3.57', language: 'de_DE' });
    // only a text that is no number is refused: the paste shows a visible error and the stored amount is kept, no PATCH
    await pasteAndExpectRefused(page, {
      invoiceDocumentNo,
      pasted: '3,5a',
      keptEditText: '3,57',
      keptDisplayed: DISPLAYED.de_DE['3.57'],
    });
    await focusAndLeaveUntouched(page, { invoiceDocumentNo, expectedEditText: '3,57', keptDisplayed: DISPLAYED.de_DE['3.57'] });
  });

  test('English session: the dot and the comma are both decimal separators (3.57 and 1,5 are accepted)', async ({ page }) => {
    setupAllure('en_US');
    test.setTimeout(120000);
    await page.setViewportSize({ width: 2400, height: 1000 });
    await loginAs('en_US');

    await openAllocationViewOfInvoice(page, invoice.id);
    const invoiceDocumentNo = invoice.documentNo;
    await typeAndExpectStored(page, { invoiceDocumentNo, typed: '3.57', patched: '3.57', language: 'en_US' });
    await typeAndExpectStored(page, { invoiceDocumentNo, typed: '1,5', patched: '1.5', language: 'en_US' });
  });
});

test.describe('Quick input - separators of the session', () => {
  test('German session: an unparseable quantity is refused, a decimal-comma quantity adds the line', async ({ page }) => {
    setupAllure('de_DE');
    test.setTimeout(180000);

    const masterdata = await loginAs('de_DE', {
      bpartners: {
        CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
      },
      products: {
        Product1: { name: 'PROD', type: 'Item', prices: [{ price: 50.0, currencyCode: 'EUR' }] },
      },
    });

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
    await SalesOrderPage.openQuickEntryAndSelectProduct({
      product: masterdata.products.Product1.productCode,
      recordId,
    });

    await test.step('paste a text that is no number into the quantity: refused with an error, nothing is added', async () => {
      const completeRequested = page
        .waitForRequest((req) => req.url().includes('/quickInput/') && req.url().endsWith('/complete'), {
          timeout: 5000,
        })
        .catch(() => null);
      // a letter typed key-by-key is swallowed by the decimal input; a paste is what brings a non-number in, and it is refused
      const quantityInput = page.locator('.quick-input-container .widgetType-Quantity input');
      await quantityInput.click();
      await pasteIntoInput(quantityInput, '3,5a');

      await expect(page.locator('.notification-item.error', { hasText: '3,5a' }).first()).toBeVisible({
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await expect(quantityInput, 'the quantity does not take the pasted text').not.toHaveValue('3,5a');
      expect(await completeRequested, 'request adding the order line').toBeNull();
    });

    await test.step('type 1,5 and press Enter: 1.5 is sent and the line is added', async () => {
      const quantityPatched = page.waitForRequest(
        (req) =>
          req.method() === 'PATCH' &&
          req.url().includes('/quickInput/') &&
          (req.postData() || '').includes('Qty'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      const completeRequested = page.waitForRequest(
        (req) => req.url().includes('/quickInput/') && req.url().endsWith('/complete'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      const quantityInput = page.locator('.quick-input-container .widgetType-Quantity input');
      await quantityInput.click();
      await quantityInput.press('ControlOrMeta+a');
      await quantityInput.pressSequentially('1,5', { delay: 80 });
      await quantityInput.press('Enter');
      const quantityChange = (await quantityPatched).postDataJSON().find((change) => /Qty/.test(change.path));
      expect(quantityChange.value, 'the quantity sent to the backend').toEqual('1.5');
      await completeRequested;
    });

    // the product's unit (PCE) has no decimals, so the backend stores the line with quantity 2: only the line count is
    // checked here, the quantity sent is checked above
    await test.step('the order has the one line typed validly', async () => {
      await page.getByTestId('batch-entry-toggle').click(); // close the quick input
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
      await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('.table-flex-wrapper table').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.table-flex-wrapper table tbody tr [data-cy="cell-M_Product_ID"]')).toHaveCount(1);
    });
  });
});
