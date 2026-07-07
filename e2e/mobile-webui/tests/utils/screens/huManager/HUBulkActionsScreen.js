import { test } from '../../../../playwright.config';
import { page, SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { NotificationToast } from '../../dialogs/NotificationToast';

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

        // The bulk move commits via an async REST round-trip (api.moveBulkHUs); only in its .then()
        // does the frontend show the success toast and THEN navigate home (goHome()). Wait for that
        // commit-confirming, user-visible toast FIRST — it gives the (CI-load-sensitive) round-trip its
        // own timeout budget — before waiting for the home-screen transition, instead of cramming the
        // slow commit + navigation + websocket refetch into ApplicationsListScreen.waitForScreen()'s
        // single budget (which is what made it overshoot SLOW_ACTION_TIMEOUT and flake).
        // See e2e/mobile-webui/CLAUDE.md § "async-commit race" / "Assert what the user SEES".
        await NotificationToast.waitToPopup();

        await ApplicationsListScreen.waitForScreen();
    }),
};
