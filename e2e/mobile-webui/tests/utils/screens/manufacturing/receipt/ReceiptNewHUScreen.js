import { page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { MaterialReceiptLineScreen } from './MaterialReceiptLineScreen';

const NAME = 'ReceiptNewHUScreen';
const NO_GEBINDE_GUIDANCE_TESTID = 'receive-no-gebinde-guidance';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReceiptNewHUScreen');

export const ReceiptNewHUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickLUTarget: async ({ luPIItemTestId }) => await test.step(`${NAME} - Click LU target "${luPIItemTestId}"`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await page.getByTestId(luPIItemTestId).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    clickTUTarget: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Click TU target "${tuPIItemProductTestId}"`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await page.getByTestId(tuPIItemProductTestId).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    expectTUTargetNotPresent: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Expect TU target "${tuPIItemProductTestId}" not present`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.getByTestId(tuPIItemProductTestId)).toHaveCount(0);
    }),

    // Precondition for the dead-end: no receiving target (TU or LU) is offered.
    // Target buttons carry a data-testid; the footer Back/Home use id selectors and the
    // guidance message is excluded, so this counts only selectable targets.
    expectNoTargetsOffered: async () => await test.step(`${NAME} - Expect no receiving targets offered`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.locator(`#ReceiptNewHUScreen [data-testid]:not([data-testid="${NO_GEBINDE_GUIDANCE_TESTID}"])`))
            .toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Guidance shown when no receiving Gebinde (TU or LU target) can be offered.
    // Asserts on a stable data-testid (language-independent), never the translated text.
    expectNoGebindeGuidanceVisible: async () => await test.step(`${NAME} - Expect no-Gebinde guidance message`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.getByTestId(NO_GEBINDE_GUIDANCE_TESTID)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),
};
