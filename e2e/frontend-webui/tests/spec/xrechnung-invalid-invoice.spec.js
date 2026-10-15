import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * XRechnung invalid-invoice E2E test (F00751 e-Invoicing Germany).
 *
 * Validates that COMPLETING a sales invoice whose generated XRechnung e-invoice FAILS KoSIT /
 * EN 16931 schematron validation is vetoed, and the completion error the user sees carries the
 * per-assertion DETAIL (rule id + message + XPath location) — not only the bare rule-id list.
 *
 * Reference case: an XRechnung recipient (EInvoiceType=X) with NO Leitweg-ID / Buyer reference
 * fails rule BR-DE-15 (BT-10), so the completion error contains the message substrings
 * "BT-10" / "Buyer reference" and the location substring "CrossIndustryInvoice".
 *
 * The assertion substrings are language-invariant (rule id / schematron CII tokens / XPath), so the
 * test runs in both en_US and de_DE to prove the full page-object flow is language-independent.
 */

const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

const PRODUCT_NAME = 'Testprodukt';

testCases.forEach(({ language, label }) => {
    test.describe(`XRechnung invalid invoice (${label})`, () => {
        // eslint-disable-next-line no-unused-vars
        test(`Completing an XRechnung invoice missing the Leitweg-ID surfaces the enriched validation error (${label})`, async ({ page }) => {
            allure.epic('E0340: Invoicing');
            allure.tag('F00751: e-Invoicing Germany');
            allure.tag('F00751');
            allure.story('XRechnung invoice missing Leitweg-ID: completion vetoed, error carries ruleId + message + location');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);

            test.setTimeout(180000);

            // BR-DE-conformant seller (contact with email + phone, VAT id, IBAN, DE address) +
            // an XRechnung buyer that is MISSING the Leitweg-ID. Mirrors the ZUGFeRD happy-path
            // masterdata (zugferd-invoice.spec.js) but the buyer has eInvoiceType='X' and NO
            // eInvoiceBuyerReference, so the generated XRechnung fails BR-DE-15 (BT-10 Buyer
            // reference). This minimal buyer also lacks an electronic address, so PEPPOL-EN16931-R010
            // fires too; the assertions below pin BR-DE-15 by its rule id.
            const masterdata = await Backend.createMasterdata({
                request: {
                    bpartners: {
                        seller: {
                            name: 'Muster GmbH',
                            vatTaxId: 'DE136695976',
                            isCustomer: false,
                            isSoPriceList: true,
                            locations: {
                                sellerLoc: { city: 'Berlin', postal: '10115', address1: 'Musterstrasse 1', countryCode: 'DE' },
                            },
                            contacts: {
                                sellerContact: {
                                    firstName: 'Max',
                                    lastName: 'Mustermann',
                                    email: 'max.mustermann@muster.de',
                                    phone: '+49 30 1234567',
                                    isDefaultContact: true,
                                },
                            },
                            bankAccounts: {
                                sellerBank: { iban: 'DE89370400440532013000', currencyCode: 'EUR' },
                            },
                        },
                        buyer: {
                            name: 'Kaeufer AG',
                            isCustomer: true,
                            isSoPriceList: true,
                            isEInvoiceRecipeint: true,
                            eInvoiceType: 'X',
                            // NO eInvoiceBuyerReference -> fails BR-DE-15 (BT-10 Buyer reference).
                            vatTaxId: 'DE811569869',
                            locations: {
                                buyerLoc: { city: 'Hamburg', postal: '20095', address1: 'Kaeuferweg 5', countryCode: 'DE' },
                            },
                        },
                    },
                    orgSeller: { bpartnerIdentifier: 'seller', bpartnerLocationIdentifier: 'sellerLoc' },
                    products: {
                        product: { name: PRODUCT_NAME, type: 'Item', prices: [{ price: 100.0, currencyCode: 'EUR' }] },
                    },
                    login: { user: { language } },
                },
            });

            allure.attachment('Masterdata', JSON.stringify(masterdata, null, 2), 'application/json');
            console.log(`[${language}] Masterdata created. buyer:`, masterdata.bpartners.buyer.bpartnerCode);

            await LoginPage.goto();
            await LoginPage.login(masterdata.login.user);
            await DashboardPage.expectVisible();

            // Sales order -> complete
            await SalesOrderPage.goto();
            await SalesOrderPage.clickNew();
            const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.buyer.bpartnerCode);
            await SalesOrderPage.addOrderLine({ product: masterdata.products.product.productCode, quantity: '1', recordId });
            await SalesOrderPage.complete();
            await SalesOrderPage.closePrintModal().catch(() => {});

            // Shipment
            await SalesOrderPage.openRelatedShipmentCandidate();
            await ShipmentSchedulePage.expectVisible();
            await ShipmentSchedulePage.createShipment();
            await page.waitForTimeout(5000);

            // Invoice candidate -> create invoice (async; invalid buyer => completion will be vetoed)
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await SalesOrderPage.openRelatedInvoiceCandidate({ retryDelay: 5000 });
            await InvoiceCandidatePage.expectVisibleForSalesOrder();

            // Create the invoice but DO NOT auto-complete it: uncheck IsCompleteInvoices in the
            // "Create invoice" process modal. Auto-completion would veto on the invalid e-invoice
            // and roll the whole invoice back (no document to show). A DRAFT invoice lets us then
            // complete it manually in the UI and capture the user-facing completion error.
            await test.step('Create invoice from candidate without auto-completing (IsCompleteInvoices unchecked)', async () => {
                // Select all ICs (compensation-group safety; harmless in the single-IC case).
                const selectAllBtn = page.locator('[title="Alt+A"]').first();
                if (await selectAllBtn.isVisible({ timeout: 2000 }).catch(() => false)) {
                    await selectAllBtn.click();
                } else {
                    await page.keyboard.press('Alt+a');
                }
                await page.waitForTimeout(300);

                await page.getByTestId('quick-action-dropdown-toggle').click();
                await page.locator('.quick-actions-dropdown').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
                await page.getByTestId('quick-action-C_Invoice_Candidate_EnqueueSelectionForInvoicing').click();

                const modal = page.locator('.panel-modal, .modal-content').first();
                await modal.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
                await page.waitForTimeout(500);

                const completeCheckbox = modal.locator('.form-field-IsCompleteInvoices input[type="checkbox"]').first();
                await completeCheckbox.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
                if (await completeCheckbox.isChecked()) {
                    await modal.locator('.form-field-IsCompleteInvoices label.input-checkbox').first().click();
                }
                await expect(completeCheckbox, 'IsCompleteInvoices must be unchecked to get a draft invoice').not.toBeChecked();

                await page.getByTestId('process-modal-start-button').click();
                await modal.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});
            });
            await page.waitForTimeout(5000);

            // Navigate to the (expected DRAFT) invoice generated from the order.
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await SalesOrderPage.openRelatedInvoice({ refreshOnRetry: true, maxRetries: 10, retryDelay: 3000 });
            await InvoicePage.expectVisible();
            await InvoicePage.openDetailView();

            // Evidence: the invoice generated from the candidate is a DRAFT (not auto-completed).
            const statusText = await page.getByTestId('status-button').innerText().catch(() => '(no status-button)');
            console.log(`[${language}] Invoice document status before completing:`, statusText);

            // Attempt to COMPLETE the invalid invoice. Completion is vetoed (AFTER_COMPLETE) and the
            // enriched error is shown in the WebUI ERROR NOTIFICATION (not a toast); the long
            // per-assertion detail sits behind "(read more)".
            await page.getByTestId('status-button').click();
            await page.getByTestId('status-CO').click();

            const errorNotification = page.locator('.notification-handler .notification-item.error').last();
            await errorNotification.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            const readMore = errorNotification.locator('.notification-content u.pointer');
            if (await readMore.isVisible().catch(() => false)) {
                await readMore.click();
            }
            const notificationContent = errorNotification.locator('.notification-content');
            const errorText = (await notificationContent.innerText()).trim();
            console.log(`[${language}] COMPLETION ERROR NOTIFICATION:\n${errorText}`);
            allure.attachment('completion-error-notification', errorText, 'text/plain');

            // The per-assertion detail (rule id + message + XPath location) is what the completion
            // error now surfaces; before, it carried only the bare rule-id list. Assert the full
            // triple for the BR-DE-15 (missing Leitweg-ID) assertion: its rule id, its message
            // substrings (BT-10 / Buyer reference), and that a CII XPath location is present.
            // The buyer here is a minimal XRechnung recipient, so it also fails PEPPOL-EN16931-R010
            // (buyer electronic address) — hence the rule-id check pins the assertion we mean.
            await expect(notificationContent, 'error must carry the rule id').toContainText('BR-DE-15');
            await expect(notificationContent, 'error must carry the assertion message (BT-10)').toContainText('BT-10');
            await expect(notificationContent, 'error must carry the assertion message (Buyer reference)').toContainText('Buyer reference');
            await expect(notificationContent, 'error must carry the CII XPath location').toContainText('CrossIndustryInvoice');
        });
    });
});
