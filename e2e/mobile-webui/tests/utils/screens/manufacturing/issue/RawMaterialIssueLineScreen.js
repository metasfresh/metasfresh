import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { RawMaterialIssueLineScanScreen } from './RawMaterialIssueLineScanScreen';
import { GetQuantityDialog } from '../../picking/GetQuantityDialog';
import { ManufacturingJobScreen } from '../ManufacturingJobScreen';

const NAME = 'RawMaterialIssueLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#RawMaterialIssueLineScreen');

/** @returns {import('@playwright/test').Locator} */
const locateStepButtons = () => containerElement().locator('[data-testid^="step-"][data-testid$="-button"]');

/**
 * The displayable part of an HU QR code (`HU#1#{"id":"<base>-<suffix>",...}`) — the short number the step
 * caption shows (see HUQRCodeUniqueId.getDisplayableSuffix / toQRCodeDisplayable in the mobile frontend).
 */
const huDisplayableSuffix = (huQRCode) => {
    const json = huQRCode.substring(huQRCode.indexOf('{'));
    const id = JSON.parse(json).id;
    return id.substring(id.indexOf('-') + 1);
};

/** @returns {import('@playwright/test').Locator} */
const locateStepButtonForHU = ({ huQRCode }) => {
    const suffix = huDisplayableSuffix(huQRCode);
    return locateStepButtons().filter({ has: page.locator('.caption-btn > .rows > .row > span', { hasText: new RegExp(` - ${suffix}\\s*$`) }) });
};

export const RawMaterialIssueLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    // Opens the scan screen only, leaving the GetQuantityDialog interaction to the caller — used
    // when the caller needs to assert on the dialog (e.g. a reason's caption, or the confirmation
    // prompt) before/instead of the all-in-one `scanQRCode` closing it.
    openScanScreen: async () => await test.step(`${NAME} - Open scan screen`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
    }),

    scanQRCode: async ({ qrCode, expectQtyEntered, expectQtyTarget, expectQtyRemaining, qtyEntered }) => await test.step(`${NAME} - Scan QR code`, async () => {
        await page.getByTestId('scanQRCode-button').tap();
        await RawMaterialIssueLineScanScreen.waitForScreen();
        await RawMaterialIssueLineScanScreen.typeQRCode(qrCode);
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

    retypeQtyAndConfirm: async ({ qtyEntered }) => await test.step(`${NAME} - Retype qty=${qtyEntered} and confirm`, async () => {
        await GetQuantityDialog.fillAndPressDone({ qtyEntered });
        await RawMaterialIssueLineScreen.waitForScreen();
    }),

    expectStepsCount: async ({ count }) => await test.step(`${NAME} - Expect ${count} step(s)`, async () => {
        await expect(locateStepButtons()).toHaveCount(count);
        if (count > 0) {
            await expect(locateStepButtons().first()).toBeVisible();
        }
    }),

    /**
     * Assert the issue step planned on the given HU — the step is identified the way the operator sees it, by
     * the HU's displayable QR suffix at the end of the step caption ("<locator> - <suffix>").
     */
    expectStepForHU: async ({ huQRCode, qtyToIssue, qtyIssued }) => await test.step(`${NAME} - Expect step for HU ...${huDisplayableSuffix(huQRCode)}`, async () => {
        const stepButton = locateStepButtonForHU({ huQRCode });
        await expect(stepButton).toHaveCount(1);
        await expect(stepButton).toBeVisible();

        const stepInfo = stepButton.locator('.picking-row-info');
        if (qtyToIssue !== undefined) {
            await expect(stepInfo).toHaveAttribute('data-qtytarget', qtyToIssue);
        }
        if (qtyIssued !== undefined) {
            await expect(stepInfo).toHaveAttribute('data-qtycurrent', qtyIssued);
        }
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await RawMaterialIssueLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

}
