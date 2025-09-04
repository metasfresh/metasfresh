import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { HUConsolidationJobScreen } from './HUConsolidationJobScreen';
import { expect } from '@playwright/test';

const NAME = 'PickingSlotScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickingSlotScreen');

export const PickingSlotScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await PickingSlotScreen.waitNotLoading();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    waitLoading: async () => await test.step(`${NAME} - Wait for screen to start loading`, async () => {
        await page.locator('.loading').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    waitNotLoading: async () => await test.step(`${NAME} - Wait for screen not loading`, async () => {
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingSlotScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await HUConsolidationJobScreen.waitForScreen();
    }),

    clickConsolidateAllButton: async () => await test.step(`${NAME} - Click Consolidate All button`, async () => {
        await page.getByTestId('consolidateAll-button').tap();
        await HUConsolidationJobScreen.waitForScreen();
    }),

    clickConsolidateHUButton: async ({ huId }) => await test.step(`${NAME} - Click Consolidate huId=${huId} button`, async () => {
        if (!huId) throw Error("huId not provided");
        
        const button = page.getByTestId(`consolidate-${huId}-button`);
        await button.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT })
        await button.tap();
        await PickingSlotScreen.waitNotLoading();
    }),
};