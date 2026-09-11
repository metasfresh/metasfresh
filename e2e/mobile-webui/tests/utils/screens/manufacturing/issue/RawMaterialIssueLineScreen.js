import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { RawMaterialIssueLineScanScreen } from './RawMaterialIssueLineScanScreen';
import { GetQuantityDialog } from '../../picking/GetQuantityDialog';
import { ManufacturingJobScreen } from '../ManufacturingJobScreen';

const NAME = 'RawMaterialIssueLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#RawMaterialIssueLineScreen');

export const RawMaterialIssueLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    // expectOnTheFlyCall: when true, asserts POST issueSchedule/createOnTheFly is actually invoked
    // and succeeds for this scan — i.e. the scanned HU was genuinely NOT part of the job's pre-planned
    // steps, so this really is an on-the-fly issue-schedule creation and not a locally-matched step
    // (which never calls the backend at all). Without this, a test can pass for the wrong reason: it
    // would look identical whether the scan hit a pre-planned step or the on-the-fly endpoint.
    scanQRCode: async ({ qrCode, expectQtyEntered, expectQtyTarget, expectQtyRemaining, qtyEntered, expectOnTheFlyCall = false }) => await test.step(`${NAME} - Scan QR code`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        const onTheFlyResponsePromise = expectOnTheFlyCall
            ? page.waitForResponse((r) => r.url().includes('/issueSchedule/createOnTheFly'), { timeout: SLOW_ACTION_TIMEOUT })
            : null;
        await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
        if (onTheFlyResponsePromise != null) {
            const onTheFlyResponse = await onTheFlyResponsePromise;
            expect(onTheFlyResponse.ok()).toBeTruthy();
        }
        if (expectQtyTarget != null) {
            await GetQuantityDialog.expectUserInfoValue({ captionKey: 'general.QtyToPick_Total', expectedValue: expectQtyTarget });
        }
        if (expectQtyRemaining != null) {
            await GetQuantityDialog.expectUserInfoValue({ captionKey: 'general.QtyToPick', expectedValue: expectQtyRemaining });
        }
        await GetQuantityDialog.fillAndPressDone({ expectQtyEntered, qtyEntered });
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    expectScanButtonVisible: async ({ visible, noIndicators }) => await test.step(`${NAME} - Expect Scan button visible = ${visible}`, async () => {
        const locator = page.getByTestId('scanQRCode-button');
        if (visible) {
            await expect(locator).toBeVisible();
        } else {
            // Button shall not be present at all when readOnly
            await expect(locator).toHaveCount(0);
        }

        if (noIndicators) {
            await expect(locator.getByTestId('indicator')).toHaveCount(0);
            await expect(locator.getByTestId('indicator2')).toHaveCount(0);
        }
    }),

    scanQRCodeExpectError: async ({ qrCode, qtyEntered }) => await test.step(`${NAME} - Scan QR code (expect error, qty=${qtyEntered})`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
        await expectErrorToast(`${NAME} over-issue rejected`, async () => {
            await GetQuantityDialog.fillAndPressDone({ qtyEntered });
            // On success: dialog closes and navigates back to line screen.
            // On error (after fix): dialog stays open → waitToClose hangs → toast wins.
        });
        await GetQuantityDialog.waitForDialog(); // dialog still open — worker was not navigated away
    }),

    // For a code the resolver cannot identify at all (no local step, and the backend on-the-fly
    // lookup fails too) — the error fires DURING resolution, before the quantity dialog ever opens,
    // unlike scanQRCodeExpectError() above which is for the over-issue case (dialog opens fine, the
    // error comes from confirming the entered qty).
    scanQRCodeExpectResolveError: async ({ qrCode, expectedToastText }) => await test.step(`${NAME} - Scan QR code (expect resolve error)`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        await expectErrorToast(`${NAME} unresolved code rejected`, async () => {
            await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
        }, expectedToastText != null ? ({ textContent }) => {
            expect(textContent).toContain(expectedToastText);
        } : undefined);
        // Resolution failed before the qty dialog ever opened — the scan screen is still armed so the
        // operator can simply rescan.
        await RawMaterialIssueLineScanScreen.expectVisible();
    }),

    retypeQtyAndConfirm: async ({ qtyEntered }) => await test.step(`${NAME} - Retype qty=${qtyEntered} and confirm`, async () => {
        await GetQuantityDialog.fillAndPressDone({ qtyEntered });
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await RawMaterialIssueLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

}
