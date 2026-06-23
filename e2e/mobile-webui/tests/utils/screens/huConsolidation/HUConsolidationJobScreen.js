import { page, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT, step, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { HUConsolidationJobsListScreen } from './HUConsolidationJobsListScreen';
import { SelectHUConsolidationTargetScreen } from './SelectHUConsolidationTargetScreen';
import { PickingSlotScreen } from './PickingSlotScreen';
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

    /**
     * Assert the Complete button is disabled.
     * This is the GRAI completion gate: when GRAIRequired=Y and the required GRAI count on
     * the target LU is unfilled, WFProcessScreen sets isUserEditable=false on ConfirmActivity,
     * which renders #last-confirm-button as disabled.
     */
    expectCompleteDisabled: async () => await step(`${NAME} - Expect Complete button disabled`, async () => {
        await expect(page.locator('#last-confirm-button')).toBeDisabled({ timeout: FAST_ACTION_TIMEOUT });
    }),

    /**
     * Assert the Complete button is enabled.
     * True when graiScanEnabled=false, or when graiAssignedCount >= graiExpectedCount.
     */
    expectCompleteEnabled: async () => await step(`${NAME} - Expect Complete button enabled`, async () => {
        await expect(page.locator('#last-confirm-button')).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Assert the "GRAI scannen" action button is visible.
     * Shown after a target LU is set, when the job has graiScanEnabled=true.
     */
    expectGraiScanButtonVisible: async () => await step(`${NAME} - Expect GRAI scan action button visible`, async () => {
        await expect(page.getByTestId('grai-scan-action-button')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Assert the "GRAI scannen" action button is NOT visible.
     * Expected when graiScanEnabled=false (GRAIRequired=No or no target set).
     */
    expectGraiScanButtonNotVisible: async () => await step(`${NAME} - Expect GRAI scan action button NOT visible`, async () => {
        await expect(page.getByTestId('grai-scan-action-button')).not.toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
    }),

    /**
     * Tap the "GRAI scannen" action button to open the GRAI capture screen.
     * Precondition: the button is visible (target LU set, graiScanEnabled=true).
     */
    clickGraiScanButton: async () => await step(`${NAME} - Click GRAI scan action button`, async () => {
        await page.getByTestId('grai-scan-action-button').tap();
    }),
};

