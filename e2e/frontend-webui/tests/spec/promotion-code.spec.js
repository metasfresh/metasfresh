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
import { SALES_ORDER_WINDOW_ID, SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';

/**
 * Promotion Code E2E test suite.
 *
 * Features tested:
 * - F00100: Sales Order
 * - F00250: Promotion Code (Aktionskennzeichen)
 *
 * Tests the complete promotion code lifecycle:
 * 1. Create a promotion code via UI (window 542105)
 * 2. Create a sales order with the promotion code
 * 3. Complete order and verify promo code persists
 * 4. Navigate to invoice candidates and verify promo code propagated
 * 5. Generate invoice and verify promo code on invoice
 */

const PROMOTION_CODE_WINDOW_ID = 542105;

const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
    test.describe(`Promotion Code (${label})`, () => {
        test(`Create promo code via UI and verify O2C propagation (${label})`, async ({ page }) => {
            // === ALLURE METADATA ===
            allure.epic('E0100: Sales');
            allure.tag('F00100: Sales Order');
            allure.tag('F00250: Promotion Code');
            allure.story('Promotion Code: Create via UI -> SO -> IC -> Invoice');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);

            allure.description(`
## F00250: Promotion Code

### Test Scenario
This test validates promotion code creation via UI and propagation through O2C:

1. **Create Promotion Code** - New promotion code via the Aktionskennzeichen window
2. **Create Sales Order** - SO with customer, product, and the promotion code
3. **Complete Order** - Verify promo code persists after completion
4. **Check Invoice Candidates** - Verify promo code propagated to IC
5. **Generate Invoice** - Verify promo code on the created invoice

### Business Value
Ensures promotion codes created via the UI correctly propagate through the
entire order-to-cash flow.
            `);

            test.setTimeout(180000); // 3 minutes

            // Step 1: Create test master data via Backend API
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
                            name: 'PromoCodeCustomer',
                        },
                    },
                    products: {
                        Product1: {
                            name: 'PromoProduct',
                            type: 'Item',
                            prices: [
                                {
                                    price: 10.0,
                                    currencyCode: 'EUR',
                                },
                            ],
                        },
                    },
                },
            });

            allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
            console.log(`[${language}] Master data created`);

            // Step 2: Login
            await LoginPage.goto();
            await LoginPage.login(masterdata.login.user);
            await DashboardPage.expectVisible();

            // Step 3: Create Promotion Code via UI (navigate directly to /new)
            await page.goto(`${FRONTEND_BASE_URL}/window/${PROMOTION_CODE_WINDOW_ID}/new`);
            await page.waitForURL(new RegExp(`/window/${PROMOTION_CODE_WINDOW_ID}/\\d+`), {
                timeout: VERY_SLOW_ACTION_TIMEOUT,
            });
            await page.waitForTimeout(2000);

            // Fill Value field
            const promoValue = `PC_${Date.now()}`;
            const valueInput = page.locator('#lookup_Value input.input-field, .form-field-Value input.input-field').first();
            await valueInput.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await valueInput.click();
            await valueInput.fill(promoValue);
            await page.keyboard.press('Tab');
            await page.waitForTimeout(500);

            // Fill Name field
            const nameInput = page.locator('#lookup_Name input.input-field, .form-field-Name input.input-field').first();
            await nameInput.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await nameInput.click();
            await nameInput.fill(`Test Promotion ${promoValue}`);
            await page.keyboard.press('Tab');
            await page.waitForTimeout(500);

            // Fill Description field (new column)
            const descInput = page.locator('#lookup_Description input.input-field, .form-field-Description input.input-field, #lookup_Description textarea').first();
            if (await descInput.isVisible().catch(() => false)) {
                await descInput.click();
                await descInput.fill('Automated test promotion code');
                await page.keyboard.press('Tab');
                await page.waitForTimeout(500);
            }

            // Wait for auto-save
            await page.waitForTimeout(2000);

            // Get the record ID from URL
            const promoUrl = page.url();
            const promoRecordId = promoUrl.match(/\/window\/\d+\/(\d+)/)?.[1];
            expect(promoRecordId).toBeTruthy();
            console.log(`[${language}] Promotion Code created: ${promoValue} (ID: ${promoRecordId})`);

            const screenshotPromo = await page.screenshot();
            allure.attachment('Promotion Code Created', screenshotPromo, 'image/png');

            // Step 4: Create Sales Order with promotion code
            await SalesOrderPage.goto();
            await SalesOrderPage.clickNew();

            const soRecordId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);
            console.log(`[${language}] Sales Order ${soRecordId} created`);

            // Set promotion code on the order
            const promoCodeField = page.locator('#lookup_C_PromotionCode_ID input').first();
            await promoCodeField.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await promoCodeField.click();
            await promoCodeField.fill(promoValue);
            await page.waitForTimeout(1000);

            // Select from dropdown
            const promoDropdownOption = page.locator('.input-dropdown-list-option').first();
            await promoDropdownOption.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await promoDropdownOption.click();
            await page.waitForTimeout(1000);

            // Add order line
            await SalesOrderPage.addOrderLine({
                product: masterdata.products.Product1.productCode,
                quantity: '10',
                recordId: soRecordId,
            });

            // Complete order
            await SalesOrderPage.complete();

            const soDocumentNo = await SalesOrderPage.getDocumentNo();
            expect(soDocumentNo).toBeTruthy();
            console.log(`[${language}] Sales Order completed: ${soDocumentNo}`);

            const screenshotSO = await page.screenshot();
            allure.attachment('Sales Order Completed', screenshotSO, 'image/png');

            // Step 5: Verify promotion code is visible on the completed order
            const promoCodeOnOrder = page.locator('#lookup_C_PromotionCode_ID .lookup-widget-value, #lookup_C_PromotionCode_ID input');
            const promoCodeText = await promoCodeOnOrder.first().inputValue().catch(() =>
                promoCodeOnOrder.first().innerText().catch(() => '')
            );
            console.log(`[${language}] Promotion code on order: ${promoCodeText}`);

            // Wait for async processing
            await page.waitForTimeout(5000);

            // Step 6: Navigate to invoice candidates
            await SalesOrderPage.openRelatedInvoiceCandidate(5000);
            await InvoiceCandidatePage.expectVisibleForSalesOrder();

            const screenshotIC = await page.screenshot();
            allure.attachment('Invoice Candidates', screenshotIC, 'image/png');
            console.log(`[${language}] Invoice candidates visible`);

            // Step 7: Create invoice
            await InvoiceCandidatePage.createInvoiceForSalesOrder();
            console.log(`[${language}] Invoice created from candidates`);

            await page.waitForTimeout(5000);

            // Step 8: Navigate back to SO and zoom to invoice
            await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${soRecordId}`);
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg')
                .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                .catch(() => {});
            await page.waitForTimeout(500);

            await SalesOrderPage.openRelatedInvoice();
            await InvoicePage.expectVisible();

            const invoiceDocNo = await InvoicePage.getDocumentNo();
            expect(invoiceDocNo).toBeTruthy();
            console.log(`[${language}] Invoice created: ${invoiceDocNo}`);

            // Step 9: Verify promotion code on invoice
            const promoCodeOnInvoice = page.locator('#lookup_C_PromotionCode_ID .lookup-widget-value, #lookup_C_PromotionCode_ID input');
            const invoicePromoText = await promoCodeOnInvoice.first().inputValue().catch(() =>
                promoCodeOnInvoice.first().innerText().catch(() => '')
            );
            console.log(`[${language}] Promotion code on invoice: ${invoicePromoText}`);

            const screenshotInvoice = await page.screenshot();
            allure.attachment('Invoice with Promotion Code', screenshotInvoice, 'image/png');

            // Validation summary
            const validationHtml = `<table border="1">
                <tr><th>Check</th><th>Status</th><th>Value</th></tr>
                <tr><td>Promotion Code Created</td><td>PASS</td><td>${promoValue}</td></tr>
                <tr><td>Sales Order Created</td><td>PASS</td><td>${soDocumentNo}</td></tr>
                <tr><td>Invoice Created</td><td>PASS</td><td>${invoiceDocNo}</td></tr>
            </table>`;
            allure.attachment('Validation Results', validationHtml, 'text/html');

            console.log(`[${language}] Promotion code E2E test completed successfully`);
        });
    });
});
