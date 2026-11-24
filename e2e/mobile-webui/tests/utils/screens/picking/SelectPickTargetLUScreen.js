import { page, SLOW_ACTION_TIMEOUT } from "../../common";
import { test } from "../../../../playwright.config";
import { expect } from '@playwright/test';

const NAME = 'SelectPickTargetScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#SelectPickTargetScreen');

export const SelectPickTargetLUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickLUButton: async ({ lu }) => await test.step(`${NAME} - Click LU button`, async () => {
        await page.locator('button').filter({ hasText: lu }).tap();
    }),

    /** Checks the offered pallet types (LU packing instructions) by their names. */
    expectLUButtons: async ({ offered = [], notOffered = [] }) => await test.step(`${NAME} - Expect LU buttons offered=${offered} notOffered=${notOffered}`, async () => {
        // Buttons are matched by text (substring); the testing API makes packing-instruction names unique (name + timestamp).
        // The offered ones first: once they are visible, the list is rendered and the absence of the others is meaningful.
        for (const lu of offered) {
            await expect(page.locator('button').filter({ hasText: lu })).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
        }
        for (const lu of notOffered) {
            await expect(page.locator('button').filter({ hasText: lu })).toHaveCount(0);
        }
    }),

    clickCloseTargetButton: async () => await test.step(`${NAME} - Click Close Target LU button`, async () => {
        await page.locator('#CloseTarget-button').tap();
    }),
};