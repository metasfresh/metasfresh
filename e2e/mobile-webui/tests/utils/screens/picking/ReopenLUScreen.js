import { page, SLOW_ACTION_TIMEOUT, ID_BACK_BUTTON } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from '@playwright/test';

const NAME = 'ReopenLUScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReopenLUScreen');
/** @returns {import('@playwright/test').Locator} */
const luButtons = () => containerElement().locator('button.hu-button');

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

    clickFirstEnabledLU: async () => await test.step(`${NAME} - Click the first enabled LU`, async () => {
        await luButtons().and(page.locator(':enabled')).first().tap();
    }),

    expectErrorToast: async () => await test.step(`${NAME} - Expect an error message`, async () => {
        await expect(page.locator('.Toastify__toast--error')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
    }),
};
