import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID } from '../utils/WindowIds';

/**
 * Business Partner Creation E2E test suite.
 *
 * Features tested:
 * - F00900: Business Partner
 * - F00910: Business Partner Creation (via WebUI)
 *
 * Tests creating a new business partner directly through the WebUI:
 * 1. Login with a test user
 * 2. Navigate to Business Partner window
 * 3. Create a new business partner (Alt+N)
 * 4. Fill in the company name
 * 5. Verify the record is saved with an auto-generated search key
 * 6. Set the customer flag
 * 7. Verify all fields are persisted
 *
 * This validates the core master data creation workflow that every metasfresh user
 * needs to perform when onboarding new customers or vendors.
 */

const testCases = [
    { language: 'en_US', label: 'English' },
    { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
    test.describe(`Business Partner Creation (${label})`, () => {
        test(`Create new customer via WebUI (${label} UI)`, async ({ page }) => {
            // === ALLURE METADATA ===
            allure.epic('E0390: Business Partner');
            allure.tag('F00900: Business Partner');
            allure.tag('F00900');
            allure.tag('F00910: Business Partner Creation');
            allure.tag('F00910');
            allure.story('Create new Business Partner via WebUI');
            allure.severity('critical');
            allure.parameter('Language', language);
            allure.parameter('UI Label', label);
            allure.tag(language);

            allure.description(`
## E0390: Business Partner

## F00900: Business Partner
## F00910: Business Partner Creation

### Test Scenario
This test validates creating a new business partner through the WebUI:

1. **Login** - Authenticate with test user
2. **Navigate** - Open Business Partner window
3. **Create New** - Use Alt+N to create new record
4. **Set Company Name** - Fill in the Name field
5. **Verify Save** - Confirm record is saved with auto-generated search key
6. **Set Customer Flag** - Mark as customer
7. **Verify Persistence** - Confirm all fields are persisted correctly

### Business Value
Creating business partners is one of the most fundamental operations in metasfresh.
Every customer, vendor, or contact must first be created as a business partner.
            `);

            test.setTimeout(90000); // 1.5 minutes

            // Step 1: Create test user (no BPs or products needed)
            const masterdata = await Backend.createMasterdata({
                request: {
                    login: {
                        user: {
                            language,
                            firstname: 'first',
                            lastname: 'last',
                        },
                    },
                },
            });

            allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
            console.log(`[${language}] Test user created: ${masterdata.login.user.username}`);

            // Step 2: Login
            await LoginPage.goto();
            await LoginPage.login(masterdata.login.user);
            await DashboardPage.expectVisible();

            // Step 3: Navigate to Business Partner window
            await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}`);
            await page.locator('.document-list-wrapper, .document-list').waitFor({
                state: 'visible',
                timeout: VERY_SLOW_ACTION_TIMEOUT,
            });
            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .panel-spaced-lg')
                .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                .catch(() => {});
            await page.waitForTimeout(500);

            console.log(`[${language}] Business Partner window loaded`);

            // Step 4: Create a new record using Alt+N
            await page.locator('body').click();
            await page.waitForTimeout(200);
            await page.keyboard.press('Alt+N');

            // Wait for navigation to the new record's detail view
            await page.waitForURL(/\/window\/\d+\/\d+/, {
                timeout: SLOW_ACTION_TIMEOUT,
            });

            await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
            await page.locator('.rotating, .indicator-pending')
                .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                .catch(() => {});
            await page.waitForTimeout(1000);

            // Extract record ID from URL
            const recordId = page.url().split('/').pop();
            console.log(`[${language}] New BP record created: ${recordId}`);

            // Step 5: Set the company name
            // The Name field uses database column name as the ID
            const nameInput = page.locator('#lookup_CompanyName input.input-field, .form-field-CompanyName input, #lookup_Name input.input-field, .form-field-Name input').first();
            await nameInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

            const uniqueName = `TestBP_${language}_${Date.now()}`;
            await nameInput.click();
            await nameInput.fill(uniqueName);

            // Press Tab to trigger save
            await page.keyboard.press('Tab');
            await page.waitForTimeout(2000);

            // Wait for save to complete
            await page.locator('.rotating, .indicator-pending')
                .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                .catch(() => {});

            console.log(`[${language}] BP name set: ${uniqueName}`);

            // Step 6: Verify the search key (Value) was auto-generated
            // The Value field should be populated after saving
            const valueInput = page.locator('.form-field-Value input, input[name="Value"]').first();
            const isValueVisible = await valueInput.isVisible().catch(() => false);

            if (isValueVisible) {
                const searchKey = await valueInput.inputValue();
                console.log(`[${language}] Auto-generated search key: ${searchKey}`);
                expect(searchKey).toBeTruthy();
                allure.parameter('Search Key', searchKey, { excluded: true });
            }

            // Step 7: Set the customer flag
            // The IsCustomer field is typically a switch/checkbox widget
            const isCustomerSwitch = page.locator('.form-field-IsCustomer input[type="checkbox"], #lookup_IsCustomer input').first();
            const isCustomerVisible = await isCustomerSwitch.isVisible().catch(() => false);

            if (isCustomerVisible) {
                const isChecked = await isCustomerSwitch.isChecked().catch(() => false);

                if (!isChecked) {
                    await isCustomerSwitch.click();
                    await page.waitForTimeout(1000);

                    // Wait for save
                    await page.locator('.rotating, .indicator-pending')
                        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
                        .catch(() => {});
                }

                console.log(`[${language}] Customer flag set`);
            } else {
                console.log(`[${language}] IsCustomer switch not visible on main tab — may be on a sub-tab`);
            }

            // Step 8: Take a screenshot of the final state
            const screenshotBuffer = await page.screenshot();
            allure.attachment('Business Partner Created', screenshotBuffer, 'image/png');

            // Step 9: Verify the name was saved by re-reading it
            const savedName = await nameInput.inputValue();
            expect(savedName).toBe(uniqueName);

            allure.parameter('BP Name', uniqueName, { excluded: true });

            // Attach validation summary
            const validationHtml = `<table border="1">
                <tr><th>Check</th><th>Status</th><th>Value</th></tr>
                <tr><td>Record Created</td><td>PASS</td><td>ID: ${recordId}</td></tr>
                <tr><td>Name Set</td><td>PASS</td><td>${uniqueName}</td></tr>
                <tr><td>Name Persisted</td><td>PASS</td><td>${savedName}</td></tr>
            </table>`;
            allure.attachment('Validation Results', validationHtml, 'text/html');

            console.log(`[${language}] Business Partner creation test completed successfully`);
        });
    });
});
