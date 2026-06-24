import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import {
    FRONTEND_BASE_URL,
    SLOW_ACTION_TIMEOUT,
    VERY_SLOW_ACTION_TIMEOUT,
} from '../utils/common';
import { RESOURCE_WINDOW_ID } from '../utils/WindowIds';

/**
 * Resource LotNumberCode field E2E test.
 *
 * Validates the new optional `S_Resource.LotNumberCode` field that selects a
 * per-resource lot-number sequence at manufacturing receipt:
 *   1. Login with a test user
 *   2. Navigate to the Resource window
 *   3. Open an existing Resource record
 *   4. Verify the "Lot-Nummer Code" field renders and is editable
 *   5. Set a unique value, save (Tab), reload the page
 *   6. Verify the value persisted after reload
 *   7. Restore the original value (leave no test residue)
 *
 * This is the WebUI-side proof that the AD metadata added for the field
 * (AD_Field 781245 / AD_UI_Element on window 236, tab 414) renders a usable,
 * persisting text input — complementing the backend DBFunctionSequenceNoProvider
 * unit + cucumber coverage.
 */

const LOT_FIELD = '.form-field-LotNumberCode input';

test.describe('Resource — LotNumberCode field', () => {
    test('LotNumberCode field renders, is editable, and persists after reload', async ({ page }) => {
        allure.epic('Manufacturing');
        allure.story('Per-resource lot-number sequence');
        allure.severity('normal');
        allure.description(`
### Scenario
Verify the optional **Lot-Nummer Code** (\`S_Resource.LotNumberCode\`) field on the
Resource window: it renders, accepts input, and the value survives a save + reload.

### Business value
The field lets a manufacturing resource opt into a custom lot-number sequence at
receipt time. If it does not render or persist, the feature cannot be configured.
        `);

        test.setTimeout(90000);

        // Step 1: test user
        const masterdata = await Backend.createMasterdata({
            request: { login: { user: { language: 'en_US', firstname: 'first', lastname: 'last' } } },
        });
        console.log(`Test user created: ${masterdata.login.user.username}`);

        // Step 2: login
        await LoginPage.goto();
        await LoginPage.login(masterdata.login.user);
        // login() already waits for the redirect off /login; assert it deterministically.
        // Intentionally NOT waiting for the dashboard to reach 'networkidle' — the
        // dashboard's STOMP/websocket + KPI polling keep the network active, so
        // networkidle never settles locally. The Resource-window load below waits on a
        // deterministic DOM signal (the document list) instead.
        await LoginPage.expectLoggedIn();

        // Step 3: navigate to the Resource window
        await page.goto(`${FRONTEND_BASE_URL}/window/${RESOURCE_WINDOW_ID}`);
        await page.locator('.document-list-wrapper, .document-list').waitFor({
            state: 'visible',
            timeout: VERY_SLOW_ACTION_TIMEOUT,
        });
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.locator('.rotating, .panel-spaced-lg')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        await page.waitForTimeout(500);

        // Step 4: open the first existing Resource record (double-click opens detail)
        const firstRow = page.locator('.table tbody tr, table tbody tr').first();
        await firstRow.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await firstRow.dblclick();
        await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
        await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
        await page.locator('.rotating, .indicator-pending')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        await page.waitForTimeout(1000);

        const recordId = page.url().split('/').pop();
        console.log(`Opened Resource record: ${recordId}`);

        // Step 5: the LotNumberCode field renders and is editable
        const lotInput = page.locator(LOT_FIELD).first();
        await lotInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(lotInput).toBeEditable();

        const originalValue = await lotInput.inputValue();
        console.log(`Original LotNumberCode value: "${originalValue}"`);

        // Step 6: set a unique value and save (Tab triggers the field save).
        // LotNumberCode is a short per-resource code — VARCHAR(10) / AD FieldLength 10
        // (e.g. a production-line code). Keep the value within 10 chars so the field's
        // maxLength does not truncate it.
        const uniqueValue = `L${Date.now().toString().slice(-8)}`; // 9 chars, unique per run
        await lotInput.click();
        await lotInput.fill(uniqueValue);
        await page.keyboard.press('Tab');
        await page.waitForTimeout(2000);
        await page.locator('.rotating, .indicator-pending')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        console.log(`Set LotNumberCode to: ${uniqueValue}`);

        // Step 7: reload and verify persistence
        await page.reload();
        await page.locator(LOT_FIELD).first().waitFor({
            state: 'visible',
            timeout: VERY_SLOW_ACTION_TIMEOUT,
        });
        await page.waitForTimeout(500);
        const reloadedValue = await page.locator(LOT_FIELD).first().inputValue();
        expect(reloadedValue).toBe(uniqueValue);
        console.log(`After reload, LotNumberCode = "${reloadedValue}" (persisted)`);

        const screenshot = await page.screenshot();
        allure.attachment('Resource LotNumberCode persisted', screenshot, 'image/png');

        const validationHtml = `<table border="1">
            <tr><th>Check</th><th>Status</th><th>Value</th></tr>
            <tr><td>Field renders + editable</td><td>PASS</td><td>${LOT_FIELD}</td></tr>
            <tr><td>Value set</td><td>PASS</td><td>${uniqueValue}</td></tr>
            <tr><td>Persisted after reload</td><td>PASS</td><td>${reloadedValue}</td></tr>
        </table>`;
        allure.attachment('Validation Results', validationHtml, 'text/html');

        // Step 8: restore the original value so the test leaves no residue
        const lotInputRestore = page.locator(LOT_FIELD).first();
        await lotInputRestore.click();
        await lotInputRestore.fill(originalValue);
        await page.keyboard.press('Tab');
        await page.waitForTimeout(1500);
        await page.locator('.rotating, .indicator-pending')
            .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
            .catch(() => {});
        console.log(`Restored original LotNumberCode value: "${originalValue}"`);
    });
});
