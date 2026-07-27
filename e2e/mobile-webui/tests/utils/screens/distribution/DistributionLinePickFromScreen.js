import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { GetQuantityDialog } from '../picking/GetQuantityDialog';
import { DistributionUtils } from './DistributionUtils';
import { DistributionJobsListScreen } from './DistributionJobsListScreen';
import { DistributionJobScreen } from './DistributionJobScreen';

const NAME = 'DistributionLinePickFromScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#DistributionLinePickFromScreen');

export const DistributionLinePickFromScreen = {
        waitForScreen: async () => await test.step(`${NAME} - Wait for Screen`, async () => {
            await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        }),

        expectJobId: async ({ distributionJobId }) => await test.step(`${NAME} - Expect jobId=${distributionJobId}`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();
            await DistributionUtils.expectJobId({ distributionJobId });
        }),

        // Assert a row of the job header table that this screen renders after auto-advance. `exact`
        // (default true) matches the value EXACTLY so that e.g. locator "L1" is not satisfied by a
        // stale "L10" value — exactly the wrong/leftover-header class of bug this test guards. Pass
        // `exact: false` only when the rendered value legitimately carries trailing content you do
        // not want to pin (e.g. a qty followed by its UOM symbol). See DistributionUtils for the
        // shared header-row assertion.
        expectHeaderProperty: async ({ caption, value, exact = true }) => await test.step(`${NAME} - Check header property '${caption}'='${value}'${exact ? '' : ' (substring)'}`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();
            await DistributionUtils.expectHeaderProperty({ caption, value, exact });
        }),

        // The screen renders EXACTLY ONE of "Scan HU" / "Scan product" (a switch, not overlapping
        // panels — see ScanHUAndGetQtyComponent's progressStatus), so asserting the product-scan
        // input is `visible` also proves the HU-scan input is NOT the one showing. Used to guard
        // the auto-advance-from-the-same-staging-LU scenario: after auto-advancing to the next
        // order, the screen must be ready for the product scan directly — not sit in "Scan HU"
        // state (which would misroute the operator's next scan, the product GTIN, into the HU slot).
        expectProductScanReady: async () => await test.step(`${NAME} - Expect ready for PRODUCT scan (HU carried forward, no re-scan needed)`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();
            await expect(page.getByTestId('scanProductCode-input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        }),

        // Mirror image of expectProductScanReady: the screen renders EXACTLY ONE of "Scan HU" /
        // "Scan product", so asserting the HU-scan input is `visible` also proves the product-scan
        // input is NOT the one showing. Used to guard the auto-advance case where the next order has
        // NO pre-allocated move plan (allowPickingAnyHU=true): the operator must be asked to scan the
        // source HU, because the app cannot know which HU the next order will be served from.
        expectHUScanReady: async () => await test.step(`${NAME} - Expect ready for HU scan (nothing carried forward, operator must scan the source HU)`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();
            await expect(page.getByTestId('scanHUBarcode-input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        }),

        // Explicit negative companion to expectHUScanReady. The mutual exclusion is implied by the
        // component's progressStatus switch, but stating it costs one fast assertion and makes the
        // failure mode obvious if that switch is ever turned into overlapping panels. FAST timeout:
        // by the time this runs the HU-scan input is already visible, so the product input can only
        // be absent — a slow timeout here would just burn budget on a genuine failure.
        expectProductScanNotReady: async () => await test.step(`${NAME} - Expect NOT ready for product scan`, async () => {
            // waitForScreen first, like every other assertion in this file: without it a caller that
            // does NOT pair this with expectHUScanReady would happily "pass" against a screen that
            // never rendered at all (absent input == count 0).
            await DistributionLinePickFromScreen.waitForScreen();
            await expect(page.getByTestId('scanProductCode-input')).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
        }),

        scanHUToMove: async ({ huQRCode, productScannedCode, expectQuantityDialog = true, expectedQtyToMove, expectNextScreen }) => await test.step(`${NAME} - Scan HU to move`, async () => {
            await DistributionLinePickFromScreen.waitForScreen();

            if (huQRCode) {
                await DistributionLinePickFromScreen.typeHUQRCode(huQRCode);
            }

            await DistributionLinePickFromScreen.typeProductCode(productScannedCode);

            if (expectQuantityDialog) {
                await DistributionLinePickFromScreen.fillQuantityDialog({
                    expectedQtyToMove,
                });
            }

            if (!expectNextScreen || expectNextScreen === 'DistributionJobScreen') {
                await DistributionJobScreen.waitForScreen();
            } else if (expectNextScreen === 'DistributionJobsListScreen') {
                await DistributionJobsListScreen.waitForScreen();
            } else if (expectNextScreen === 'DistributionLinePickFromScreen') {
                await DistributionLinePickFromScreen.waitForScreen();
            } else {
                throw new Error(`Invalid expectNextScreen: ${expectNextScreen}`);
            }
        }),

        typeHUQRCode: async (qrCode) => await test.step(`${NAME} - Type HU QR Code`, async () => {
            await BarcodeScannerComponent.type({ scannedCode: qrCode, testId: 'scanHUBarcode-input' });
        }),

        typeProductCode: async (productScannedCode) => await test.step(`${NAME} - Type Product Scanned Code: ${productScannedCode}`, async () => {
            await BarcodeScannerComponent.type({ scannedCode: productScannedCode, testId: 'scanProductCode-input' });
        }),

        goBackToJobScreen: async () => await test.step(`${NAME} - Go back to job screen`, async () => {
            await page.locator(ID_BACK_BUTTON).tap();
            await DistributionJobScreen.waitForScreen();
        }),

        fillQuantityDialog: async ({ qtyToMove, expectedQtyToMove, expectedError }) => await test.step(`${NAME} - Fill Quantity Dialog`, async () => {
            await GetQuantityDialog.fillAndPressDone({
                qtyEntered: qtyToMove,
                expectQtyEntered: expectedQtyToMove,
                expectedError,
            });
        }),
    }
;
