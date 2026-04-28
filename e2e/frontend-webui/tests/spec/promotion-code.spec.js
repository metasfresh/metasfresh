import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { InvoiceCandidatePage } from '../utils/pages/InvoiceCandidatePage';
import { AdvancedEdit } from '../utils/AdvancedEdit';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Promotion Code E2E test suite.
 *
 * Features tested:
 * - F00100: Sales Order
 * - F00250: Promotion Code (Aktionskennzeichen)
 *
 * Tests the promotion code lifecycle:
 * 1. Create a promotion code via UI (window 542105)
 * 2. Create a sales order and set promo code via Advanced Edit (Alt+E)
 * 3. Complete order and verify promo code persisted via Advanced Edit
 * 4. Navigate to invoice candidates and verify promo code propagated
 *
 * Note: Full O2C propagation (IC -> Invoice) is tested by the Cucumber test.
 * The E2E test focuses on UI creation and Advanced Edit integration.
 */

const PROMOTION_CODE_WINDOW_ID = 542105;

const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
    test.describe(`Promotion Code (${label})`, () => {
        test(`Create promo code via UI and set on Sales Order (${label})`, async ({ page }) => {
            // === ALLURE METADATA ===
            allure.epic('E0100: Sales');
            allure.tag('F00100: Sales Order');
            allure.tag('F00250: Promotion Code');
            allure.story('Promotion Code: Create via UI -> SO -> IC');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.tag(language);

            allure.description(`
## F00250: Promotion Code

### Test Scenario
This test validates promotion code creation via UI and propagation to Invoice Candidates:

1. **Create Promotion Code** - New promotion code via the Aktionskennzeichen window
2. **Create Sales Order** - SO with customer, product, and the promotion code (set via Advanced Edit)
3. **Complete Order** - Verify promo code persisted (read via Advanced Edit)
4. **Check Invoice Candidates** - Verify promo code propagated to IC (read via Advanced Edit)

### Business Value
Ensures promotion codes created via the UI can be set on sales orders
via the Advanced Edit modal and propagate correctly to invoice candidates.
Full O2C chain (IC -> Invoice) is covered by the Cucumber test.
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
            const valueInput = page
                .locator('#lookup_Value input.input-field, .form-field-Value input.input-field')
                .first();
            await valueInput.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await valueInput.click();
            await valueInput.fill(promoValue);
            await page.keyboard.press('Tab');
            await page.waitForTimeout(500);

            // Fill Name field
            const nameInput = page
                .locator('#lookup_Name input.input-field, .form-field-Name input.input-field')
                .first();
            await nameInput.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await nameInput.click();
            await nameInput.fill(`Test Promotion ${promoValue}`);
            await page.keyboard.press('Tab');
            await page.waitForTimeout(500);

            // Fill Description field (new column)
            const descInput = page
                .locator(
                    '#lookup_Description input.input-field, .form-field-Description input.input-field, #lookup_Description textarea'
                )
                .first();
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

            // Step 4: Create Sales Order
            await SalesOrderPage.goto();
            await SalesOrderPage.clickNew();

            const soRecordId = await SalesOrderPage.selectCustomer(
                masterdata.bpartners.CUSTOMER1.bpartnerCode
            );
            console.log(`[${language}] Sales Order ${soRecordId} created`);

            // Step 5: Set promotion code via Advanced Edit (Alt+E)
            await AdvancedEdit.open();

            // Verify the C_PromotionCode_ID field is visible in Advanced Edit
            const promoFieldVisible = await AdvancedEdit.isFieldVisible('C_PromotionCode_ID');
            expect(promoFieldVisible).toBeTruthy();
            console.log(`[${language}] C_PromotionCode_ID field visible in Advanced Edit`);

            // Set the promotion code using dropdown selection
            await AdvancedEdit.setListField('C_PromotionCode_ID', promoValue);

            // Verify the value was set by reading it back
            const setPromoValue = await AdvancedEdit.getListFieldValue('C_PromotionCode_ID');
            expect(setPromoValue).toContain(promoValue);
            console.log(`[${language}] C_PromotionCode_ID set to: ${setPromoValue}`);

            const screenshotAdvEdit = await page.screenshot();
            allure.attachment('Advanced Edit - Promo Code Set', screenshotAdvEdit, 'image/png');

            await AdvancedEdit.close();

            // Step 6: Add order line
            await SalesOrderPage.addOrderLine({
                product: masterdata.products.Product1.productCode,
                quantity: '10',
                recordId: soRecordId,
            });

            // Step 7: Complete order
            await SalesOrderPage.complete();

            const soDocumentNo = await SalesOrderPage.getDocumentNo();
            expect(soDocumentNo).toBeTruthy();
            console.log(`[${language}] Sales Order completed: ${soDocumentNo}`);

            const screenshotSO = await page.screenshot();
            allure.attachment('Sales Order Completed', screenshotSO, 'image/png');

            // Step 8: Verify promotion code persisted on completed SO via Advanced Edit
            await AdvancedEdit.open();

            const soPromoValue = await AdvancedEdit.getListFieldValue('C_PromotionCode_ID');
            expect(soPromoValue).toContain(promoValue);
            console.log(`[${language}] SO C_PromotionCode_ID verified after completion: ${soPromoValue}`);

            // Also verify the field is now readonly (order is completed)
            const soPromoReadonly = await AdvancedEdit.isFieldReadonly('C_PromotionCode_ID');
            console.log(`[${language}] SO C_PromotionCode_ID readonly after completion: ${soPromoReadonly}`);

            const screenshotSOVerify = await page.screenshot();
            allure.attachment('SO Advanced Edit - Promo Code Verified', screenshotSOVerify, 'image/png');

            await AdvancedEdit.close();

            // Wait for async processing (invoice candidate creation)
            await page.waitForTimeout(5000);

            // Step 9: Navigate to invoice candidates to verify propagation
            await SalesOrderPage.openRelatedInvoiceCandidate(5000);
            await InvoiceCandidatePage.expectVisibleForSalesOrder();

            const screenshotIC = await page.screenshot();
            allure.attachment('Invoice Candidates List', screenshotIC, 'image/png');

            // Step 10: Open first IC row and verify promo code propagated
            const firstRow = page.locator('.table-flex-wrapper table tbody tr, table tbody tr').first();
            const rowVisible = await firstRow.isVisible().catch(() => false);
            expect(rowVisible).toBeTruthy();
            await firstRow.dblclick();
            await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
            await page.waitForTimeout(1000);

            // Verify promo code on IC via Advanced Edit
            await AdvancedEdit.open();

            const icPromoFieldVisible = await AdvancedEdit.isFieldVisible('C_PromotionCode_ID');
            expect(icPromoFieldVisible).toBeTruthy();
            const icPromoValue = await AdvancedEdit.getListFieldValue('C_PromotionCode_ID');
            expect(icPromoValue).toContain(promoValue);
            console.log(`[${language}] IC C_PromotionCode_ID verified: ${icPromoValue}`);

            // Pause so the video clearly shows the promo code on the IC
            await page.waitForTimeout(3000);

            const screenshotICDetail = await page.screenshot();
            allure.attachment('IC Advanced Edit - Promo Code', screenshotICDetail, 'image/png');

            await AdvancedEdit.close();
            await page.waitForTimeout(1000);

            console.log(`[${language}] Promotion code E2E test completed successfully`);
        });
    });
});
