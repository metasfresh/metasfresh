import { page, SLOW_ACTION_TIMEOUT, step, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { HUConsolidationJobsListScreen } from './HUConsolidationJobsListScreen';
import { SelectHUConsolidationTargetScreen } from './SelectHUConsolidationTargetScreen';
import { PickingSlotScreen } from './PickingSlotScreen';

const NAME = 'HUConsolidationJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const HUConsolidationJobScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    getJobId: async () => {
        const currentUrl = await page.url();

        const regex = /\/huConsolidation-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    clickLUTargetButton: async () => await step(`${NAME} - Click LU target button`, async () => {
        await page.getByTestId('targetLU-button').tap();
    }),
    setTargetLU: async ({ lu }) => await step(`${NAME} - Set target LU to ${lu}`, async () => {
        await HUConsolidationJobScreen.clickLUTargetButton();
        await SelectHUConsolidationTargetScreen.waitForScreen();
        await SelectHUConsolidationTargetScreen.clickLUButton({ lu });
        await HUConsolidationJobScreen.waitForScreen();
    }),
    closeTargetLU: async () => await step(`${NAME} - Close target LU`, async () => {
        await HUConsolidationJobScreen.clickLUTargetButton();
        await SelectHUConsolidationTargetScreen.clickCloseTargetButton();
        await HUConsolidationJobScreen.waitForScreen();
    }),

    clickPickingSlot: async ({ pickingSlotId }) => await step(`${NAME} - Click picking slot`, async () => {
        await page.locator(`[data-pickingslotid="${pickingSlotId}"]`).tap();
        await PickingSlotScreen.waitForScreen();
    }),

    consolidateAll: async ({ pickingSlotId }) => await step(`${NAME} - Consolidate All`, async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId });
        await PickingSlotScreen.clickConsolidateAllButton();
    }),

    consolidate: async ({ pickingSlotId, huId }) => await step(`${NAME} - Consolidate ${huId}`, async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId });
        await PickingSlotScreen.clickConsolidateHUButton({ huId });
    }),

    abort: async () => await step(`${NAME} - Abort`, async () => {
        await page.locator('#abort-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await HUConsolidationJobsListScreen.waitForScreen();
    }),

    complete: async () => await step(`${NAME} - Complete`, async () => {
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await HUConsolidationJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};

