import { test } from '../../../../playwright.config';
import { page } from '../../common';
import { HUManagerScreen } from './HUManagerScreen';
import { expect } from '@playwright/test';

const NAME = 'HUMoveScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#HUMoveScreen');

export const HUMoveScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        expect(containerElement()).toBeVisible();
    }),

    move: async ({ qrCode }) => await test.step(`${NAME} - Move HU`, async () => {
        await HUMoveScreen.expectVisible();
        await page.type('#input-text', qrCode);
        await HUManagerScreen.waitForScreen();
        //await HUManagerScreen.waitForHUInfoPanel(); // note: in master we are directed to scan another HU
    }),

};
