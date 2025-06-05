import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { HUConsolidationJobScreen } from './HUConsolidationJobScreen';

const NAME = 'PickingSlotScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#PickingSlotScreen');

export const PickingSlotScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),
    
    clickConsolidateAllButton: async () => await test.step(`${NAME} - Click Consolidate All button`, async () => {
        await page.getByTestId('consolidateAll-button').tap();
        await HUConsolidationJobScreen.waitForScreen();
    }),
};