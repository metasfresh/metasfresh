import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';

/**
 * Grid in-row amount editor — a decimal typed with the user's own decimal separator must be stored as typed.
 *
 * Regression guard: in a German session, typing "3,57" into an Amount cell of a grid view stored 357.
 * The amount editor was a browser <input type="number">, which silently drops the comma keystroke, so the
 * PATCH carried "357". The editor is now a text input and the typed text is converted to the dot-decimal
 * the backend expects, using the decimal separator of the user's session locale.
 *
 * Grid: the payment-allocation view opened from a sales invoice, column "discountAmt" (Amount widget,
 * edited in-row). An English session types the dot-decimal form of the same amount, which must keep working.
 */
const testCases = [
  { language: 'de_DE', label: 'German', typed: '3,57' },
  { language: 'en_US', label: 'English', typed: '3.57' },
];

const PAYMENT_ALLOCATION_FROM_INVOICE_ACTION = 'PaymentView_Launcher_From_C_Invoice_SingleDocument';
const EXPECTED_AMOUNT = 3.57;

testCases.forEach(({ language, label, typed }) => {
  test.describe('Grid in-row amount editor', () => {
    test(`typing ${typed} into a grid amount cell stores ${EXPECTED_AMOUNT} (${label})`, async ({ page }) => {
      allure.epic('E0294: Frontend WebUI');
      allure.tag('F50000: Frontend WebUI');
      allure.tag(language);
      allure.story('Decimal separator in amount editors');
      allure.severity('critical');

      test.setTimeout(300000); // order -> shipment -> invoice chain, then the allocation view
      await page.setViewportSize({ width: 2400, height: 1000 }); // the allocation view's grids are wide

      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language, firstname: 'first', lastname: 'last' } },
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
        },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();

      let invoiceDocumentNo;
      await test.step('invoice the delivered sales order', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${masterdata.salesOrders.SO1.id}`);
        await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await SalesOrderPage.openRelatedInvoiceCandidate({ maxRetries: 15, retryDelay: 3000, refreshOnRetry: true });
        await InvoiceCandidatePage.expectVisibleForSalesOrder();
        await InvoiceCandidatePage.createInvoiceForSalesOrder();

        await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${masterdata.salesOrders.SO1.id}`);
        await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await SalesOrderPage.openRelatedInvoice({ maxRetries: 15, retryDelay: 3000, refreshOnRetry: true });
        await InvoicePage.expectVisible();
        invoiceDocumentNo = await InvoicePage.getDocumentNo();
        await InvoicePage.openDetailView();
      });

      await test.step('open the payment allocation view from the sales invoice', async () => {
        await page.getByTestId('status-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.meta-icon-more').click();
        await page.locator('.subheader-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await page.getByTestId(`action-${PAYMENT_ALLOCATION_FROM_INVOICE_ACTION}`).click();
      });

      const invoiceRow = page.locator('tr', { hasText: invoiceDocumentNo }).first();
      const discountCell = invoiceRow.locator('[data-cy="cell-discountAmt"]');
      await discountCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      let patchRequest = null;
      let patchResponseBody = null;
      await test.step(`type ${typed} into the discount amount cell and press Enter`, async () => {
        await invoiceRow.locator('[data-cy="cell-documentNo"]').click(); // select the row
        await discountCell.dblclick(); // on a selected row, a double click opens the in-row editor
        const input = discountCell.locator('input');
        await expect(input).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await input.press('Control+a');
        await input.pressSequentially(typed, { delay: 80 });

        // what the user typed is what the editor holds (a browser number input would show 357 here)
        expect(await input.inputValue(), 'text in the amount editor after typing').toBe(typed);

        const isDiscountPatch = (req) => req.method() === 'PATCH' && (req.postData() || '').includes('discountAmt');
        const patchRequested = page.waitForRequest(isDiscountPatch, { timeout: SLOW_ACTION_TIMEOUT });
        const patchResponded = page.waitForResponse((resp) => isDiscountPatch(resp.request()), {
          timeout: SLOW_ACTION_TIMEOUT,
        });
        await input.press('Enter');
        patchRequest = JSON.parse((await patchRequested).postData());
        patchResponseBody = await (await patchResponded).json();
      });

      // the PATCH carries the dot-decimal the backend expects
      expect(patchRequest, 'PATCH sent for discountAmt').toContainEqual(
        expect.objectContaining({ path: 'discountAmt', value: '3.57' })
      );

      // the row the server returns holds the typed amount, not 357
      const rows = Array.isArray(patchResponseBody) ? patchResponseBody : [patchResponseBody];
      const storedValues = rows
        .map((row) => row?.fieldsByName?.discountAmt?.value)
        .filter((value) => value !== undefined);
      expect(storedValues.length, 'discountAmt returned by the server').toBeGreaterThan(0);
      expect(Number(storedValues[storedValues.length - 1]), 'discountAmt stored by the server').toBe(EXPECTED_AMOUNT);

      // and the grid shows it: the cell renders the server value with the session's separators
      await test.step('the grid cell shows the stored amount', async () => {
        await invoiceRow.locator('[data-cy="cell-documentNo"]').click(); // leave the editor
        await expect
          .poll(async () => parseDisplayedAmount((await discountCell.innerText()).trim(), language), {
            timeout: SLOW_ACTION_TIMEOUT,
          })
          .toBe(EXPECTED_AMOUNT);
      });
    });
  });
});

/** Parses a grid cell's amount text, formatted with the separators of the session language */
const parseDisplayedAmount = (text, language) => {
  const isDecimalComma = language.startsWith('de');
  const normalized = isDecimalComma
    ? text.replace(/\./g, '').replace(',', '.')
    : text.replace(/,/g, '');
  return Number(normalized.replace(/[^0-9.-]/g, ''));
};
