import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { MaterialReceiptLineScreen } from './MaterialReceiptLineScreen';
import { ReceiptReceiveTargetScreen } from './ReceiptReceiveTargetScreen';

const NAME = 'ReceiptNewHUScreen';
const NO_GEBINDE_GUIDANCE_TESTID = 'receive-no-gebinde-guidance';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#ReceiptNewHUScreen');

export const ReceiptNewHUScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    expectNotVisible: async () => await test.step(`${NAME} - Expect screen NOT to be displayed`, async () => {
        await expect(containerElement()).toHaveCount(0);
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

    expectLUTargetVisible: async ({ luPIItemTestId }) => await test.step(`${NAME} - Expect LU target "${luPIItemTestId}" offered`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.getByTestId(luPIItemTestId)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectTUTargetVisible: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Expect TU target "${tuPIItemProductTestId}" offered`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.getByTestId(tuPIItemProductTestId)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    goBackToLineScreen: async () => await test.step(`${NAME} - Go back (expecting the receive line)`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    goBackToReceiveTargetScreen: async () => await test.step(`${NAME} - Go back (expecting the receive target chooser)`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ReceiptReceiveTargetScreen.waitForScreen();
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

    // A target structure that is switched off by configuration must NOT be reported as
    // "no receiving Gebinde available": that message tells the operator to fix the master data.
    expectNoGebindeGuidanceNotVisible: async () => await test.step(`${NAME} - Expect NO no-Gebinde guidance message`, async () => {
        await ReceiptNewHUScreen.expectVisible();
        await expect(page.getByTestId(NO_GEBINDE_GUIDANCE_TESTID)).toHaveCount(0);
    }),
};
