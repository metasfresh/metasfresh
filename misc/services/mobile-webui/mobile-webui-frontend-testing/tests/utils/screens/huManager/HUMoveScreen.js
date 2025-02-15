import { test } from '../../../../playwright.config';
import { page } from '../../common';
import { HUManagerScreen } from './HUManagerScreen';
import { expect } from '@playwright/test';

export const HUMoveScreen = {
    waitForScreen: async () => await test.step('Wait for Move screen', async () => {
        await page.locator('#HUMoveScreen').waitFor();
    }),

    expectVisible: async () => await test.step('Expecting Move screen displayed', async () => {
        expect(page.locator('#HUMoveScreen')).toBeVisible();
    }),

    move: async ({ qrCode }) => await test.step(`Move HU`, async () => {
        await HUMoveScreen.expectVisible();
        await page.type('#input-text', qrCode);
        await HUManagerScreen.waitForScreen();
        await HUManagerScreen.waitForHUInfoPanel();
    }),

};
