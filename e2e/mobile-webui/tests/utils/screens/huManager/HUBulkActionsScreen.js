import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'HUBulkActionsScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#HUBulkActionsScreen');

export const HUBulkActionsScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    move: async ({ targetLocator }) => await test.step(`${NAME} - Move HU`, async () => {
        await page.getByTestId('toggle-target-scanner-button').tap();
        // Wait for button text to change to "Close scanner" - ensures React re-render complete
        // and useKeyboardBarcodeReader hook has attached its event listener
        await page.getByTestId('toggle-target-scanner-button').getByText('Close scanner').waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await BarcodeScannerComponent.type(targetLocator);

        // The bulk move commits via an async REST round-trip (api.moveBulkHUs -> POST /bulk/move);
        // only once it resolves does the frontend navigate home (history.goHome()). The home screen
        // appearing IS the commit-confirming, user-visible landing signal — but its single default
        // SLOW_ACTION_TIMEOUT (20s) budget has to absorb that whole round-trip, so under CI load the
        // commit can overshoot 20s and the wait times out (the flake). A transient success toast is
        // NOT usable as an earlier signal here: ScreenToaster dismisses all toasts on the very
        // navigation that follows it (useLocationChange -> toast.dismiss()), so it races the dismissal.
        // Give the transition the VERY_SLOW_ACTION_TIMEOUT (40s) budget instead — the same idiom used
        // for other heavy async-commit-then-return-to-list flows (e.g. PickingJobScreen.complete,
        // ManufacturingJobScreen, InventoryJobScreen). No new signal is invented; the genuinely-slow
        // commit is simply given room to land.
        await ApplicationsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};
