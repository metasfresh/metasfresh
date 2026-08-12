import { test } from '../../../../playwright.config';
import { page, FAST_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'HUBulkActionsScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#HUBulkActionsScreen');

// A real operator scans the target locator and, if nothing happens, simply scans again.
// We mirror that: scan the target, and if the bulk-actions screen is still open shortly
// after, scan once more, up to this many attempts.
const SCAN_TARGET_MAX_ATTEMPTS = 5;

export const HUBulkActionsScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    move: async ({ targetLocator }) => await test.step(`${NAME} - Move HU`, async () => {
        await page.getByTestId('toggle-target-scanner-button').tap();
        // Wait for button text to change to "Close scanner" - ensures React re-render complete
        // and useKeyboardBarcodeReader hook has attached its event listener
        await page.getByTestId('toggle-target-scanner-button').getByText('Close scanner').waitFor();

        // The keyboard-scanner hook's window listener attaches asynchronously (useEffect after
        // the React commit), so a single instantaneous scan can be dropped; re-scan until the
        // screen navigates away — mirroring an operator who scans again when nothing happens.
        for (let attempt = 1; attempt <= SCAN_TARGET_MAX_ATTEMPTS; attempt++) {
            await BarcodeScannerComponent.type(targetLocator);

            // Scan accepted → screen navigates away; verify with a bounded wait.
            try {
                await containerElement().waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
                break;
            } catch (e) {
                if (attempt === SCAN_TARGET_MAX_ATTEMPTS) {
                    throw e;
                }
                // still on the bulk-actions screen — the scan was dropped; scan again
            }
        }

        // The bulk move commits via an async REST round-trip (api.moveBulkHUs -> POST /bulk/move);
        // only once it resolves does the frontend navigate home (history.goHome()). Give that
        // transition the VERY_SLOW_ACTION_TIMEOUT (40s) budget — the same budget idiom used for
        // other heavy async-commit flows that return to a list/home screen.
        await ApplicationsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};
