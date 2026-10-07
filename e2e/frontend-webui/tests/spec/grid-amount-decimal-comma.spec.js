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
 * Grid in-row amount editor — numbers are read and edited with the separators of the user's session.
 *
 * Regression guard: in a German session, typing "3,57" into an Amount cell of a grid view stored 357, because the
 * amount editor was a browser <input type="number">, which silently drops the comma keystroke. Now:
 * - the session's decimal separator is the decimal separator ("3,57" in German, "3.57" in English);
 * - the other separator only groups thousands in valid groups of three ("1.000" in German is 1000);
 * - anything else ("3.57" in German, "1,5" in English) is not patched and an error notification says why;
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
  de_DE: { '3.57': '3,57', 1000: '1.000,00' },
  en_US: { '3.57': '3.57' },
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
 * Types an invalid amount, presses the key (Enter, Tab or ArrowDown) and asserts it is refused: a visible error, the
 * editor stays open and focused showing the kept amount, no PATCH - also not when the editor is left afterwards
 */
const typeAndExpectRefused = async (page, { invoiceDocumentNo, typed, key = 'Enter', keptEditText, keptDisplayed }) => {
  const cell = discountCellOf(page, invoiceDocumentNo);

  await test.step(`type ${typed} and press ${key}: refused with a visible error, the amount is kept`, async () => {
    const patches = [];
    const collectPatch = (req) => isDiscountPatch(req) && patches.push(req.postData());
    page.on('request', collectPatch);

    const input = await openDiscountEditor(cell);
    await typeIntoEditor(input, typed);
    await input.press(key);

    // the error names what was typed, which is no localized text
    await expect(page.locator('.notification-item.error', { hasText: typed }).first()).toBeVisible({
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

  test('German session: 1.000 is 1000, 3.57 is refused on Enter, Tab and ArrowDown with a visible error', async ({ page }) => {
    setupAllure('de_DE');
    test.setTimeout(120000);
    await page.setViewportSize({ width: 2400, height: 1000 });
    await loginAs('de_DE');

    await openAllocationViewOfInvoice(page, invoice.id);
    const invoiceDocumentNo = invoice.documentNo;
    await typeAndExpectStored(page, { invoiceDocumentNo, typed: '1.000', patched: '1000', language: 'de_DE' });
    for (const [typed, key] of [
      ['3.57', 'Enter'],
      ['2.5', 'Tab'],
      ['4.5', 'ArrowDown'],
    ]) {
      await typeAndExpectRefused(page, {
        invoiceDocumentNo,
        typed,
        key,
        keptEditText: '1000',
        keptDisplayed: DISPLAYED.de_DE[1000],
      });
    }
    await focusAndLeaveUntouched(page, { invoiceDocumentNo, expectedEditText: '1000', keptDisplayed: '1.000,00' });
  });

  test('English session: 3.57 is stored as 3.57, 1,5 is refused with a visible error', async ({ page }) => {
    setupAllure('en_US');
    test.setTimeout(120000);
    await page.setViewportSize({ width: 2400, height: 1000 });
    await loginAs('en_US');

    await openAllocationViewOfInvoice(page, invoice.id);
    const invoiceDocumentNo = invoice.documentNo;
    await typeAndExpectStored(page, { invoiceDocumentNo, typed: '3.57', patched: '3.57', language: 'en_US' });
    await typeAndExpectRefused(page, {
      invoiceDocumentNo,
      typed: '1,5',
      keptEditText: '3.57',
      keptDisplayed: DISPLAYED.en_US['3.57'],
    });
  });
});

test.describe('Quick input - separators of the session', () => {
  test('German session: a refused quantity adds no order line, a valid one does', async ({ page }) => {
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

    await test.step('type 1.5 into the quantity and press Enter: refused, nothing is added', async () => {
      const completeRequested = page
        .waitForRequest((req) => req.url().includes('/quickInput/') && req.url().endsWith('/complete'), {
          timeout: 5000,
        })
        .catch(() => null);
      // no input type: the quantity is a number input in the old bundle, a text input in the new one
      const quantityInput = page.locator('.quick-input-container .widgetType-Quantity input');
      await quantityInput.click();
      await quantityInput.press('ControlOrMeta+a');
      await quantityInput.pressSequentially('1.5', { delay: 80 });
      await quantityInput.press('Enter');

      await expect(page.locator('.notification-item.error', { hasText: '1.5' }).first()).toBeVisible({
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await expect(quantityInput, 'the quantity no longer holds the refused text').not.toHaveValue('1.5');
      expect(await completeRequested, 'request adding the order line').toBeNull();
    });

    await test.step('press Enter again without typing: still refused, told again, nothing is added', async () => {
      const refusalNotification = page.locator('.notification-item.error', { hasText: '1.5' });
      await expect(refusalNotification).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT }); // the first toast is gone
      const completeRequested = page
        .waitForRequest((req) => req.url().includes('/quickInput/') && req.url().endsWith('/complete'), {
          timeout: 5000,
        })
        .catch(() => null);

      await page.locator('.quick-input-container .widgetType-Quantity input').press('Enter');

      await expect(refusalNotification.first()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      expect(await completeRequested, 'request adding the order line').toBeNull();
    });

    await test.step('type 1,5 and press Enter: the line is added', async () => {
      const completeRequested = page.waitForRequest(
        (req) => req.url().includes('/quickInput/') && req.url().endsWith('/complete'),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      const quantityInput = page.locator('.quick-input-container .widgetType-Quantity input');
      await quantityInput.click();
      await quantityInput.press('ControlOrMeta+a');
      await quantityInput.pressSequentially('1,5', { delay: 80 });
      await quantityInput.press('Enter');
      await completeRequested;
    });

    await test.step('the order has the one line typed validly', async () => {
      await page.getByTestId('batch-entry-toggle').click(); // close the quick input
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
      await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('.table-flex-wrapper table').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.table-flex-wrapper table tbody tr [data-cy="cell-M_Product_ID"]')).toHaveCount(1);
      await expect(page.locator('.table-flex-wrapper table tbody tr [data-cy="cell-QtyEntered"]')).toHaveText(/^1,5/);
    });
  });
});
