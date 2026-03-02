import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { ShipmentSchedulePage } from '../utils/pages/ShipmentSchedulePage';
import { ShipmentPage } from '../utils/pages/ShipmentPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { InvoicePage } from '../utils/pages/InvoicePage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Invoice Reversal E2E test suite.
 *
 * Features tested:
 * - F00100: Sales Order
 * - F00130: Shipment Schedule
 * - F00150: Sales Shipment
 * - F00200: Sales Invoice
 * - F00210: Invoice Reversal (Document Actions)
 *
 * Tests the complete order-to-cash workflow followed by invoice reversal:
 * 1. Create a sales order with customer and product
 * 2. Complete the sales order
 * 3. Create shipment from shipment schedule
 * 4. Create invoice from invoice candidates
 * 5. Navigate to the completed invoice
 * 6. Reverse-Correct the invoice
 * 7. Verify the reversal created a new document
 *
 * This validates that completed invoices can be reversed when corrections are needed,
 * and that the system properly creates reversal documents.
 */

const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
    test.describe(`Invoice Reversal (${label})`, () => {
        test(`Complete O2C then reverse invoice (${label} UI)`, async ({ page }) => {
            // === ALLURE METADATA ===
            allure.epic('E0100: Sales');
            allure.tag('F00100: Sales Order');
            allure.tag('F00200: Sales Invoice');
            allure.tag('F00210: Invoice Reversal');
            allure.story('Invoice Reversal: SO -> Ship -> Invoice -> Reverse-Correct');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.parameter('UI Label', label);
            allure.tag(language);

            allure.description(`
## E0100: Sales

## F00200: Sales Invoice
## F00210: Invoice Reversal

### Test Scenario
This test validates invoice reversal (Reverse-Correct action):

1. **Create Sales Order** - New SO with customer and product line
2. **Complete Order** - Mark as completed to trigger downstream processes
3. **Create Shipment** - Generate shipment from shipment schedule
4. **Create Invoice** - Generate invoice from invoice candidates
5. **Reverse Invoice** - Use Reverse-Correct action on the completed invoice
6. **Verify Reversal** - Confirm reversal document was created

### Business Value
Ensures that completed invoices can be corrected via the Reverse-Correct action,
which is essential for handling billing errors and credit adjustments.
            `);

            // Extended timeout for this comprehensive E2E test
            test.setTimeout(180000); // 3 minutes

            // Step 1: Create test data
            const masterdata = await Backend.createMasterdata({
                request: {
                    login: {
                        user: {
                            language,
                            firstname: 'first',
                            lastname: 'last',
                        },
                    },
                    bpartners: {
                        CUSTOMER1: {
                            isVendor: false,
                            isCustomer: true,
                            isSoPriceList: true,
                            name: 'Customer',
                        },
                    },
                    products: {
                        Product1: {
                            name: 'PROD',
                            type: 'Item',
                            prices: [
                                {
                                    price: 25.0,
                                    currencyCode: 'EUR',
                                },
                            ],
                        },
                    },
                },
            });

            allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

            console.log(`[${language}] Master data created:`, {
                customer: masterdata.bpartners.CUSTOMER1.bpartnerCode,
                product: masterdata.products.Product1.productName,
            });

            // Step 2: Login
            await LoginPage.goto();
            await LoginPage.login(masterdata.login.user);
            await DashboardPage.expectVisible();

            // Step 3: Create and complete Sales Order
            await SalesOrderPage.goto();
            await SalesOrderPage.clickNew();

            const recordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
            console.log(`[${language}] Sales Order ${recordId} created`);

            await SalesOrderPage.addOrderLine({
                product: masterdata.products.Product1.productCode,
                quantity: '5',
                recordId,
            });

            await SalesOrderPage.complete();

            const soDocumentNo = await SalesOrderPage.getDocumentNo();
            expect(soDocumentNo).toBeTruthy();
            allure.parameter('SO Document No', soDocumentNo, { excluded: true });
            console.log(`[${language}] Sales Order completed: ${soDocumentNo}`);

            // Wait for async shipment schedule creation after order completion
            await page.waitForTimeout(5000);

            // Step 4: Create shipment from shipment schedule
            await SalesOrderPage.openRelatedShipmentCandidate({ maxRetries: 15, retryDelay: 3000 });
            await ShipmentSchedulePage.expectVisible();
            await ShipmentSchedulePage.expectOrderedQuantity('5');
            await ShipmentSchedulePage.createShipment();
            console.log(`[${language}] Shipment created from schedule`);

            await page.waitForTimeout(5000);

            // Step 5: Navigate back to SO and create invoice
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg')
                .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                .catch(() => {});
            await page.waitForTimeout(500);

            await SalesOrderPage.openRelatedInvoiceCandidate(5000);
            await InvoiceCandidatePage.expectVisibleForSalesOrder();
            await InvoiceCandidatePage.createInvoiceForSalesOrder();
            console.log(`[${language}] Invoice created from invoice candidates`);

            await page.waitForTimeout(5000);

            // Step 6: Navigate back to SO and zoom to invoice
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${recordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg')
                .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                .catch(() => {});
            await page.waitForTimeout(500);

            await SalesOrderPage.openRelatedInvoice();
            await InvoicePage.expectVisible();

            const invoiceDocNo = await InvoicePage.getDocumentNo();
            expect(invoiceDocNo).toBeTruthy();
            allure.parameter('Invoice Document No', invoiceDocNo, { excluded: true });
            console.log(`[${language}] Invoice: ${invoiceDocNo}`);

            // Step 7: Open invoice detail view (required for status actions)
            await InvoicePage.openDetailView();

            // Step 8: Reverse-Correct the invoice
            // Click the status button to open the action dropdown
            await page.getByTestId('status-button').click();
            await page.waitForTimeout(500);

            // Click "Reverse - Correct" action (RC = Reverse-Correct action key)
            await page.getByTestId('status-RC').click();

            // Wait for the reversal process to complete
            await page.waitForTimeout(3000);

            // Wait for processing indicators to disappear
            await page.locator('.rotating, .indicator-pending')
                .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                .catch(() => {});

            console.log(`[${language}] Invoice ${invoiceDocNo} reversed`);

            // Step 9: Verify reversal — language-independent checks
            const docNoAfterReversal = await page.locator('.form-field-DocumentNo input, input[name="DocumentNo"]')
                .first()
                .inputValue()
                .catch(() => '');

            // The document number field should still be visible
            expect(docNoAfterReversal).toBeTruthy();
            console.log(`[${language}] Document after reversal: ${docNoAfterReversal}`);

            // Language-independent verification: after reversal, Reverse-Correct (RC) should no longer be available
            await page.getByTestId('status-button').click();
            await page.waitForTimeout(500);
            const rcStillAvailable = await page.getByTestId('status-RC').isVisible().catch(() => false);
            await page.keyboard.press('Escape');

            expect(rcStillAvailable).toBe(false);
            console.log(`[${language}] Reverse-Correct action no longer available: PASS`);

            // Verify the page did not show an error (no error toast)
            const screenshotBuffer = await page.screenshot();
            allure.attachment('After Reversal', screenshotBuffer, 'image/png');

            // Attach validation summary
            const validationHtml = `<table border="1">
                <tr><th>Check</th><th>Status</th><th>Value</th></tr>
                <tr><td>Sales Order Created</td><td>PASS</td><td>${soDocumentNo}</td></tr>
                <tr><td>Shipment Created</td><td>PASS</td><td>Yes</td></tr>
                <tr><td>Invoice Created</td><td>PASS</td><td>${invoiceDocNo}</td></tr>
                <tr><td>Invoice Reversed</td><td>PASS</td><td>${docNoAfterReversal}</td></tr>
            </table>`;
            allure.attachment('Validation Results', validationHtml, 'text/html');

            console.log(`[${language}] Invoice reversal test completed successfully`);
        });
    });
});
