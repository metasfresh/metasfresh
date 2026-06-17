import { test } from '../../../../playwright.config';
import { page, FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from '../../common';
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
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    move: async ({ targetLocator }) => await test.step(`${NAME} - Move HU`, async () => {
        await page.getByTestId('toggle-target-scanner-button').tap();
        // Wait for button text to change to "Close scanner" - ensures React re-render complete
        await page.getByTestId('toggle-target-scanner-button').getByText('Close scanner').waitFor({ timeout: SLOW_ACTION_TIMEOUT });

        // Scan the target locator until the move fires (the screen navigates away).
        // The keyboard-scanner hook attaches its window listener in a useEffect that runs
        // AFTER the React commit that shows "Close scanner", so a single instantaneous scan
        // can race ahead of the listener and be dropped — the move never fires and the
        // bulk-actions screen stays open. A real operator who sees nothing happen just scans
        // again; we do the same: re-scan while the bulk-actions screen is still present.
        for (let attempt = 1; attempt <= SCAN_TARGET_MAX_ATTEMPTS; attempt++) {
            await BarcodeScannerComponent.type(targetLocator);

            // Scan consumed => the bulk-actions screen navigates away. Check with a short
            // bounded wait; if it's gone we're done, otherwise re-scan.
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

        await ApplicationsListScreen.waitForScreen();
    }),
};
