import { test } from '../../../../playwright.config';
import { page } from '../../common';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';

// noinspection JSUnusedGlobalSymbols
export const DISPOSAL_REASON_NOT_FOUND = 'N';
export const DISPOSAL_REASON_DAMAGED = 'D';

const NAME = 'HUDisposalScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#HUDisposalScreen');

export const HUDisposalScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    dispose: async ({ reason }) => await test.step(`${NAME} - Dispose HU`, async () => {
        if (!reason) throw Error("disposal reason not provided");

        await HUDisposalScreen.expectVisible();
        await page.getByTestId(`qty-reason-radio-${reason}`).tap();
        await page.getByTestId('dispose-button').tap();
        await ApplicationsListScreen.waitForScreen(); // not sure if is right to get here after disposal, but atm this is how behaves
    }),

};
