import { page, SLOW_ACTION_TIMEOUT, ID_BACK_BUTTON } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from '@playwright/test';

const NAME = 'ReopenLUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReopenLUScreen');
/** @returns {import('@playwright/test').Locator} */
const luButtons = () => containerElement().locator('button.hu-button');
/** the closed-LU list call of the screen */
const CLOSED_LUS_ROUTE = /\/picking\/job\/[^/]+\/closed-lu(\?|$)/;

export const ReopenLUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Asserts how many closed LUs are listed, how many of them are tappable and how many are greyed out. */
    expectClosedLUs: async ({ total, enabled, disabled }) => await test.step(`${NAME} - Expect ${total} closed LU(s): ${enabled} enabled, ${disabled} disabled`, async () => {
        await expect(luButtons()).toHaveCount(total, { timeout: SLOW_ACTION_TIMEOUT });
        await expect(luButtons().and(page.locator(':enabled'))).toHaveCount(enabled);
        await expect(luButtons().and(page.locator(':disabled'))).toHaveCount(disabled);
    }),

    /** Asserts the (single) greyed-out LU row that carries the given status label. */
    expectDisabledLUWithLabel: async ({ label }) => await test.step(`${NAME} - Expect a disabled LU with label "${label}"`, async () => {
        const row = luButtons().filter({ hasText: label });
        await expect(row).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
        await expect(row).toBeDisabled();
    }),

    /** Asserts that the (single) tappable LU row carries the given LU name. */
    expectEnabledLUWithName: async ({ name }) => await test.step(`${NAME} - Expect the enabled LU to be "${name}"`, async () => {
        await expect(luButtons().and(page.locator(':enabled'))).toContainText(name, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickFirstEnabledLU: async () => await test.step(`${NAME} - Click the first enabled LU`, async () => {
        await luButtons().and(page.locator(':enabled')).first().tap();
    }),

    expectErrorToast: async () => await test.step(`${NAME} - Expect an error message`, async () => {
        await expect(page.locator('.Toastify__toast--error')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Makes the closed-LU list call fail (HTTP 500) on the test page only; no production change. */
    simulateClosedLUsLoadFailure: async () => await test.step(`${NAME} - Simulate closed LU list load failure`, async () => {
        await page.route(CLOSED_LUS_ROUTE, route => route.fulfill({
            status: 500,
            contentType: 'application/json',
            body: JSON.stringify({ error: 'simulated closed-lu failure' }),
        }));
    }),

    stopSimulatingClosedLUsLoadFailure: async () => await test.step(`${NAME} - Stop simulating closed LU list load failure`, async () => {
        await page.unroute(CLOSED_LUS_ROUTE);
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
    }),
};
