import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from '@playwright/test';

const NAME = 'SelectPickTargetScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#SelectPickTargetScreen');
// Exact name, not a substring: e.g. "LU_<timestamp>" is a substring of "XY_LU_<timestamp>" when both are created in the same millisecond.
/** @returns {import('@playwright/test').Locator} */
const luButton = ({ lu }) => page.getByRole('button', { name: lu, exact: true });

export const SelectPickTargetLUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickLUButton: async ({ lu }) => await test.step(`${NAME} - Click LU button`, async () => {
        await luButton({ lu }).tap();
    }),

    /** Checks the offered pallet types (LU packing instructions) by their names. */
    expectLUButtons: async ({ offered = [], notOffered = [] }) => await test.step(`${NAME} - Expect LU buttons offered=${offered} notOffered=${notOffered}`, async () => {
        // The offered ones first: once they are visible, the list is rendered and the absence of the others is meaningful.
        for (const lu of offered) {
            await expect(luButton({ lu })).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
        }
        for (const lu of notOffered) {
            await expect(luButton({ lu })).toHaveCount(0);
        }
    }),

    clickCloseTargetButton: async () => await test.step(`${NAME} - Click Close Target LU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};