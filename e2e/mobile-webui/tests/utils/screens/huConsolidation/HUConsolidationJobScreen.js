import { page, SLOW_ACTION_TIMEOUT, step, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { HUConsolidationJobsListScreen } from './HUConsolidationJobsListScreen';
import { SelectHUConsolidationTargetScreen } from './SelectHUConsolidationTargetScreen';
import { PickingSlotScreen } from './PickingSlotScreen';
import { Backend } from '../Backend';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';

const NAME = 'HUConsolidationJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const HUConsolidationJobScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    getJobId: async () => {
        const currentUrl = await page.url();

        const regex = /\/huConsolidation-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    /**
     * Read the consolidation job's current target LU global QR code from the live WFProcess.
     *
     * Call this AFTER at least one TU has been consolidated: the job's target switches from
     * "new LU" to the freshly-created existing LU on the first consolidate (see the module's
     * "target mutates new→existing LU" gotcha), so the returned QR code identifies the real
     * target LU. Use it as an `hus` matcher in Backend.expect to assert the consolidated TUs
     * are now parented under that LU (the actual end result of consolidating).
     *
     * @returns {Promise<string>} the target LU's global QR code string
     */
    getCurrentTargetLUQRCode: async () => await step(`${NAME} - Get current target LU QR code`, async () => {
        // The WFProcess endpoint needs the FULL id ("huConsolidation-<n>", per WFProcessId.ofString),
        // not the bare numeric id getJobId() returns.
        const currentUrl = await page.url();
        const match = currentUrl.match(/\/(huConsolidation-\d+)/);
        const wfProcessId = match ? match[1] : null;
        if (!wfProcessId) {
            throw new Error('Could not determine the consolidation wfProcessId from the current URL: ' + currentUrl);
        }

        const wfProcess = await Backend.getWFProcess({ wfProcessId });
        const activity = wfProcess?.activities?.find(a => a.componentType === 'huConsolidation/consolidate');
        const luQRCode = activity?.componentProps?.job?.currentTarget?.luQRCode;
        if (!luQRCode) {
            throw new Error('No current target LU QR code found in the consolidation WFProcess:\n' + JSON.stringify(wfProcess, null, 2));
        }
        return luQRCode;
    }),

    clickLUTargetButton: async () => await step(`${NAME} - Click LU target button`, async () => {
        await page.getByTestId('targetLU-button').tap();
    }),
    setTargetLU: async ({ lu, qrCode }) => await step(`${NAME} - Set target LU to ${lu ?? qrCode}`, async () => {
        await HUConsolidationJobScreen.clickLUTargetButton();
        await SelectHUConsolidationTargetScreen.waitForScreen();

        if (lu != null) {
            await SelectHUConsolidationTargetScreen.clickLUButton({ lu });
            await HUConsolidationJobScreen.waitForScreen();
        } else if (qrCode != null) {
            await SelectHUConsolidationTargetScreen.scanQRCode({ qrCode });
        } else {
            throw new Error("No LU or QR code specified.")
        }
    }),
    closeTargetLU: async () => await step(`${NAME} - Close target LU`, async () => {
        await HUConsolidationJobScreen.clickLUTargetButton();
        await SelectHUConsolidationTargetScreen.clickCloseTargetButton();
        await HUConsolidationJobScreen.waitForScreen();
    }),
    printTargetLabel: async () => await step(`${NAME} - Print target label`, async () => {
        await HUConsolidationJobScreen.clickLUTargetButton();
        await SelectHUConsolidationTargetScreen.clickPrintLabelButton();
        await SelectHUConsolidationTargetScreen.goBack();
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

