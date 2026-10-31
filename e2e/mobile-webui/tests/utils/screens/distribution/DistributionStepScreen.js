import { test } from '../../../../playwright.config';
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { DistributionStepDropToScreen } from './DistributionStepDropToScreen';
import { DistributionLineScreen } from './DistributionLineScreen';
import { UnpickDialog } from '../../dialogs/UnpickDialog';

const NAME = 'DistributionStepScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionStepScreen');

export const DistributionStepScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    scanDropToLocator: async ({ dropToLocatorQRCode }) => await test.step(`${NAME} - Scan Drop To Locator`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.getByTestId('scanDropToLocator-button').tap();
        await DistributionStepDropToScreen.waitForScreen();
        await DistributionStepDropToScreen.typeQRCode(dropToLocatorQRCode);
        await DistributionStepScreen.waitForScreen();
    }),

    unpick: async () => await test.step(`${NAME} - Unpick`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.getByTestId('unpick-button').tap();
        await UnpickDialog.waitForDialog();
        await UnpickDialog.clickSkipScanningTargetHUButton();
        await DistributionLineScreen.waitForScreen();
    }),

    // Opens the unpick dialog and leaves it open on the target-HU scan stage. Split out from
    // unpickToTarget so a test can assert what happens between the scan and the commit (e.g. a
    // rejected code must leave the dialog usable) — the decomposition pattern in
    // e2e/mobile-webui/CLAUDE.md § "Decomposing Helper Methods for Assertions".
    openUnpickDialog: async () => await test.step(`${NAME} - Open unpick dialog`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.getByTestId('unpick-button').tap();
        await UnpickDialog.waitForDialog();
    }),

    // Unpick, returning the moved HU onto an explicit target HU identified by any supported label
    // (a metasfresh global QR code, a legacy ExternalBarcode, or the plain M_HU.Value) instead of
    // skipping to the floor.
    unpickToTarget: async ({ targetHUQRCode }) => await test.step(`${NAME} - Unpick to target HU`, async () => {
        await DistributionStepScreen.openUnpickDialog();
        await UnpickDialog.scanTargetHU(targetHUQRCode);
        await DistributionLineScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await DistributionStepScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await DistributionLineScreen.waitForScreen();
    }),
};