import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { HUMoveScreen } from './HUMoveScreen';

export const HUManagerScreen = {
    waitForScreen: async () => await test.step('Wait for HU Manager screen', async () => {
        await page.locator('#HUManagerScreen').waitFor();
        // await page.getByTestId('huinfo-table').waitFor();
    }),

    expectVisible: async () => await test.step('Expecting HU Manager screen displayed', async () => {
        expect(page.locator('#HUManagerScreen')).toBeVisible();
    }),

    waitForHUInfoPanel: async () => await test.step('Wait for HU Info Panel', async () => {
        await page.getByTestId('huinfo-table').waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    scanHUQRCode: async ({ huQRCode }) => await test.step(`Scan HU QR Code`, async () => {
        await HUManagerScreen.expectVisible();

        console.log('Scanning QR code:\n' + huQRCode);
        await page.type('#input-text', huQRCode);

        await HUManagerScreen.waitForHUInfoPanel();
    }),

    expectHUInfoValue: async ({ name, expectedValue }) => await test.step(`Expecting HU Info Value`, async () => {
        expect(page.getByTestId(name)).toHaveText(expectedValue);
    }),

    move: async ({ qrCode }) => await test.step(`Move HU`, async () => {
        await page.getByTestId('move-button').tap();
        await HUMoveScreen.waitForScreen();
        await HUMoveScreen.move({ qrCode });
    }),

};