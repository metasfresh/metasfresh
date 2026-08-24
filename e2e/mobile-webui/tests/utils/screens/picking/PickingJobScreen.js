import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step, VERY_FAST_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { SelectPickTargetLUScreen } from "./SelectPickTargetLUScreen";
import { PickingJobScanHUScreen } from "./PickingJobScanHUScreen";
import { PickingSlotScanScreen } from "./PickingSlotScanScreen";
import { GetQuantityDialog } from "./GetQuantityDialog";
import { YesNoDialog } from "../../dialogs/YesNoDialog";
import { PickingJobsListScreen } from "./PickingJobsListScreen";
import { SelectPickTargetTUScreen } from './SelectPickTargetTUScreen';
import { PickFromHUScanScreen } from './PickFromHUScanScreen';
import { expect } from '@playwright/test';
import { PickLineScanScreen } from './PickLineScanScreen';
import { PickingJobLineScreen } from './PickingJobLineScreen';
import { test } from '../../../../playwright.config';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { ConfirmActivityErrorPanel } from '../../components/ConfirmActivityErrorPanel';

const NAME = 'PickingJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');
const ACTIVITY_ID_ScanPickFromHU = 'scanPickFromHU'; // keep in sync with PickingMobileApplication.ACTIVITY_ID_ScanPickFromHU
const ACTIVITY_ID_ScanPickingSlot = 'scanPickingSlot'; // keep in sync with PickingMobileApplication.ACTIVITY_ID_ScanPickingSlot

export const PickingJobScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    // The job header and the line header are rendered by the same page-global markup, so this
    // delegates rather than duplicating the locator — mirroring how DistributionJobScreen defers
    // to DistributionUtils. Gives job-header assertions a call site on the screen they belong to.
    expectHeaderProperty: async ({ caption, value }) => await step(`${NAME} - Check header property '${caption}'='${value}'`, async () => {
        await PickingJobLineScreen.expectHeaderProperty({ caption, value });
    }),

    getPickingJobId: async () => {
        const currentUrl = await page.url();

        const regex = /\/picking-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    scanPickFromHU: async ({ qrCode }) => await step(`${NAME} - Scan pick from HU ${qrCode}`, async () => {
        const button = page.getByTestId(`scan-activity-${ACTIVITY_ID_ScanPickFromHU}-button`);
        await button.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(button).toBeEnabled();
        await button.tap();
        await PickFromHUScanScreen.waitForScreen();
        await PickFromHUScanScreen.typeQRCode(qrCode);
        await PickingJobScreen.waitForScreen();
        await button.locator('.indicator-color-green').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
    }),

    clickPickingSlotButton: async () => await step(`${NAME} - Click Picking Slot button`, async () => {
        const button = pickingSlotButton();
        await button.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(button).toBeEnabled();
        await button.tap();

        await PickingSlotScanScreen.waitForScreen();
    }),

    expectPickingSlotButtonGreen: async () => await step(`${NAME} - Expect Picking Slot button to be green`, async () => {
        const button = pickingSlotButton();
        await button.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await button.locator('.indicator-color-green').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
    }),

    expectPickingSlotButtonNotVisible: async () => await step(`${NAME} - Expect Picking Slot button not present`, async () => {
        await pickingSlotButton().waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
    }),

    scanPickingSlot: async ({ qrCode, expectNextScreen, gotoPickingJobScreen }) => await step(`${NAME} - Scan picking slot ${qrCode}`, async () => {
        await PickingJobScreen.clickPickingSlotButton();
        await PickingSlotScanScreen.typeQRCode(qrCode);

        if (!expectNextScreen || expectNextScreen === 'PickingJobScreen') {
            await PickingJobScreen.waitForScreen();
            await PickingJobScreen.expectPickingSlotButtonGreen();
        } else if (expectNextScreen === 'PickLineScanScreen') {
            await PickLineScanScreen.waitForScreen();
            if (gotoPickingJobScreen) {
                await step('Go back from PickLineScanScreen to PickingJobScreen', async () => {
                    await PickLineScanScreen.goBack();
                    await PickingJobLineScreen.goBack();
                });
            }
        } else if (expectNextScreen === 'PickingSlotScanScreen') {
            await PickingSlotScanScreen.waitForScreen();
            await PickingSlotScanScreen.waitForInputFieldToGetEmpty();
            if (gotoPickingJobScreen) {
                throw new Error("GO back from PickingSlotScanScreen to PickingJobScreen is not implemented yet.");
            }
        } else {
            throw new Error(`Invalid expectNextScreen: ${expectNextScreen}`);
        }
    }),

    clickReopenLUButton: async () => await step(`${NAME} - Click Reopen LU button`, async () => {
        await page.getByTestId('reopenLU-button').tap();
    }),

    clickLUTargetButton: async () => await step(`${NAME} - Click LU target button`, async () => {
        await page.getByTestId('targetLU-button').tap();
    }),
    setTargetLU: async ({ lu }) => await step(`${NAME} - Set target LU to ${lu}`, async () => {
        if (!lu) throw new Error("No LU specified.");

        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetLUScreen.waitForScreen();
        await SelectPickTargetLUScreen.clickLUButton({ lu });
        await PickingJobScreen.waitForScreen();
    }),
    closeTargetLU: async () => await step(`${NAME} - Close target LU`, async () => {
        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetLUScreen.clickCloseTargetButton();
        // After close, the app may stay on SelectPickTargetScreen briefly while the API processes.
        // Wait for either PickingJobScreen (if goBack fires) or loading to settle, then go back if needed.
        try {
            await PickingJobScreen.waitForScreen();
        } catch (e) {
            // goBack() didn't fire or was too slow — navigate back manually
            await page.locator(ID_BACK_BUTTON).tap();
            await PickingJobScreen.waitForScreen();
        }
    }),

    clickTUTargetButton: async () => await step(`${NAME} - Click TU target button`, async () => {
        await page.getByTestId('targetTU-button').tap();
    }),
    setTargetTU: async ({ tu }) => await step(`${NAME} - Set target TU to ${tu}`, async () => {
        await PickingJobScreen.clickTUTargetButton();
        await SelectPickTargetTUScreen.waitForScreen();
        await SelectPickTargetTUScreen.clickTUButton({ tu });
        await PickingJobScreen.waitForScreen();
    }),
    closeTargetTU: async () => await step(`${NAME} - Close target TU`, async () => {
        await PickingJobScreen.clickTUTargetButton();
        await SelectPickTargetTUScreen.clickCloseTargetButton();
        try {
            await PickingJobScreen.waitForScreen();
        } catch (e) {
            await page.locator(ID_BACK_BUTTON).tap();
            await PickingJobScreen.waitForScreen();
        }
    }),

    clickAdviseCarrier: async () => await step(`${NAME} - Click advise carrier button`, async () => {
        const button = page.getByTestId('advise-carrier-button');
        await button.waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(button).toBeEnabled();
        await button.tap();
        await PickingJobScreen.waitForScreen();
    }),

    expectAdviseCarrierButtonVisible: async () => await step(`${NAME} - Expect advise carrier button visible`, async () => {
        await page.getByTestId('advise-carrier-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectCarrierProductCaption: async ({ caption }) => await step(`${NAME} - Expect carrier product caption contains '${caption}'`, async () => {
        // The current carrier product now renders as a detail line inside the advise-carrier button.
        const detail = page.getByTestId('carrier-product-caption');
        await detail.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(detail).toContainText(caption);
    }),

    pickHU: async ({
                       qrCode,
                       isScanDirectly,
                       expectedPickDirectly,
                       expectNextScreen,
                       switchToManualInput, qtyEntered, expectQtyEntered, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason,
                       expectedError
                   }) => await step(`${NAME} - Scan HU and Pick`, async () => {
        if (isScanDirectly) {
            await BarcodeScannerComponent.type(qrCode);
        } else {
            await page.locator('#scanQRCode-button').tap(); // click the Scan QR Code button
            await PickingJobScanHUScreen.waitForScreen();
            await PickingJobScanHUScreen.typeQRCode(qrCode);
        }

        if (!expectedPickDirectly) {
            // expectedError: the qty-dialog Done press fires the picking/event POST which the backend
            // rejects (e.g. a life-cycle-blocked product) — asserted as an error toast.
            await GetQuantityDialog.fillAndPressDone({ switchToManualInput, expectQtyEntered, qtyEntered, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason, expectedError });
        }

        if (expectedError) {
            // A REJECTED pick leaves the operator exactly where they were — the qty dialog stays open so
            // the quantity can be corrected or the pick cancelled. Verified from the trace: after the 422
            // on POST /picking/event the app renders the toast and issues no further request, and
            // #WFProcessScreen is not in the DOM. So there is no next screen to wait for here; waiting
            // for one would time out.
            return;
        }

        if (!expectNextScreen || expectNextScreen === 'PickingJobScreen') {
            await PickingJobScreen.waitForScreen();
        } else if (expectNextScreen === 'PickingJobsListScreen') {
            await PickingJobsListScreen.waitForScreen();
        } else {
            throw new Error(`Invalid expectNextScreen: ${expectNextScreen}`);
        }
    }),

    expectLineCaption: async ({ index, caption }) => await step(`${NAME} - Expect line ${index} caption '${caption}'`, async () => {
        const lineButton = locateLineButton({ index });
        const captionElement = lineButton.locator('.caption-btn .row span').first();
        await expect(captionElement).toContainText(caption);
    }),

    clickLineButton: async ({ index }) => await step(`${NAME} - Click line ${index}`, async () => {
        await locateLineButton({ index }).tap();
        //await PickingJobLineScreen.waitForScreen();
    }),

    expectLineButton: async ({ index, qtyPicked, qtyPickedCatchWeight, qtyToPick, color, waitForColor }) => await step(`${NAME} - Expect line button at index ${index}`, async () => {
        const lineButton = locateLineButton({ index });

        if (waitForColor !== undefined) {
            await step(`${NAME} - Waiting until line button color='${waitForColor}'`, async () => {
                const expectedClassName = `indicator-color-${waitForColor}`;
                const indicator = lineButton.locator(`[data-testid="indicator"].${expectedClassName}`);
                await indicator.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
            });
        }

        if (qtyPicked !== undefined) {
            await expectLineButtonAttribute({ lineButton, attribute: 'data-qtycurrent', value: qtyPicked });
        }
        if (qtyPickedCatchWeight !== undefined) {
            await expectLineButtonAttribute({ lineButton, attribute: 'data-qtycurrentcatchweight', value: qtyPickedCatchWeight });
        }
        if (qtyToPick !== undefined) {
            await expectLineButtonAttribute({ lineButton, attribute: 'data-qtytarget', value: qtyToPick });
        }

        if (color !== undefined) {
            await step(`${NAME} - Expect line button color='${color}'`, async () => {
                const expectedClassName = `indicator-color-${color}`;
                const indicator = lineButton.locator(`[data-testid="indicator"]`);
                const classes = await indicator.getAttribute('class');
                await expect(classes).toContain(expectedClassName);
            });
        }
    }),

    // per-line available-qty display, gated by the picking profile's IsShowQtyAvailableForLines flag.
    // `qtyAvailable` is the full expected text, e.g. 'Verfügbar: 10 Stk' / 'Verfügbar: 0 Stk'.
    expectLineQtyAvailable: async ({ index, qtyAvailable }) => await step(`${NAME} - Expect line ${index} qty available '${qtyAvailable}'`, async () => {
        const lineButton = locateLineButton({ index });
        const qtyAvailableElement = lineButton.getByTestId('picking-line-qty-available');
        await qtyAvailableElement.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
        await expect(qtyAvailableElement).toHaveText(qtyAvailable);
    }),

    // when the flag is off, no qty-available element must be rendered on the line at all.
    expectLineQtyAvailableNotVisible: async ({ index }) => await step(`${NAME} - Expect line ${index} qty available not shown`, async () => {
        const lineButton = locateLineButton({ index });
        await lineButton.getByTestId('picking-line-qty-available').waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
    }),

    clickPickAllButton: async () => await step(`${NAME} - Click Pick All button`, async () => {
        const button = pickAllButton();
        await button.tap();
        await button.waitFor({ state: 'attached', timeout: VERY_FAST_ACTION_TIMEOUT });
        await PickingJobsListScreen.waitForScreen();
    }),

    expectPickAllButtonHidden: async () => await step(`${NAME} - Expect Pick All button to be hidden`, async () => {
        let button = page.getByTestId('pickAll-button');
        await button.waitFor({ state: 'detached', timeout: VERY_FAST_ACTION_TIMEOUT });
    }),

    abort: async () => await step(`${NAME} - Abort`, async () => {
        await page.locator('#abort-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await PickingJobsListScreen.waitForScreen();
    }),

    complete: async () => await step(`${NAME} - Complete`, async () => {
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await PickingJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),

    completeExpectingNetworkError: async () => await step(`${NAME} - Complete, expect network-error retry panel`, async () => {
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await ConfirmActivityErrorPanel.waitForPanel();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingJobScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobsListScreen.waitForScreen();
    }),

    clickUnpickItem: async () => await step(`${NAME} - Click "Unpack item"`, async () => {
        await page.getByTestId('unpick-item-button').tap();
        // The unpick panel replaces the job screen; wait for the job-screen scan button to be gone so
        // its scanner is unmounted before we scan into the unpick scanner (otherwise the product scan
        // can land in the still-mounted job scanner).
        await page.getByTestId('unpick-item-button').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await BarcodeScannerComponent.expectAttached({ testId: 'unpick-product-scanner', timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Scans the product GTIN; the backend resolves it (job-scoped) to the packed product and the qty
    // dialog opens with default = packed qty. Re-scans if the scan landed in the mount-race window
    // before the freshly-mounted scanner armed its keydown listener — a real operator simply scans
    // again when nothing registers.
    scanProductToUnpick: async ({ scannedCode }) => await step(`${NAME} - Scan product GTIN '${scannedCode}'`, async () => {
        const qtyDialog = page.locator('.get-qty-dialog');
        const maxScanAttempts = 3;
        for (let attempt = 1; attempt <= maxScanAttempts; attempt++) {
            await BarcodeScannerComponent.type({ scannedCode, testId: 'unpick-product-scanner' });
            try {
                await qtyDialog.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
                return;
            } catch (e) {
                if (attempt === maxScanAttempts) {
                    throw e;
                }
                await BarcodeScannerComponent.expectAttached({ testId: 'unpick-product-scanner', timeout: SLOW_ACTION_TIMEOUT });
            }
        }
    }),

    expectDefaultQtyToUnpick: async ({ qty }) => await step(`${NAME} - Expect default qty to unpick '${qty}'`, async () => {
        await GetQuantityDialog.expectQtyEntered(qty);
    }),

    enterQtyToUnpick: async ({ qty }) => await step(`${NAME} - Enter qty to unpick '${qty}'`, async () => {
        await GetQuantityDialog.typeQtyEntered(qty);
        await GetQuantityDialog.clickDone({});
        await BarcodeScannerComponent.expectAttached({ testId: 'unpick-target-hu-scanner', timeout: SLOW_ACTION_TIMEOUT });
    }),

    scanTargetHUAndCommit: async ({ qrCode }) => await step(`${NAME} - Scan target HU and commit`, async () => {
        await BarcodeScannerComponent.type({ scannedCode: qrCode, testId: 'unpick-target-hu-scanner' });
        await PickingJobScreen.waitForScreen();
    }),

    skipTargetHUAndCommit: async () => await step(`${NAME} - Skip target HU (to floor) and commit`, async () => {
        await page.getByTestId('unpick-skip-to-floor').tap();
        await PickingJobScreen.waitForScreen();
    }),

    unpickItem: async ({ scannedCode, qty, targetHUQRCode, expectDefaultQty }) => await step(`${NAME} - Unpack ${qty} of '${scannedCode}' into target HU`, async () => {
        await PickingJobScreen.clickUnpickItem();
        await PickingJobScreen.scanProductToUnpick({ scannedCode });
        if (expectDefaultQty != null) {
            await PickingJobScreen.expectDefaultQtyToUnpick({ qty: expectDefaultQty });
        }
        await PickingJobScreen.enterQtyToUnpick({ qty });
        await PickingJobScreen.scanTargetHUAndCommit({ qrCode: targetHUQRCode });
    }),

    unpickItemToFloor: async ({ scannedCode, qty, expectDefaultQty }) => await step(`${NAME} - Unpack ${qty} of '${scannedCode}' to the floor (skip target HU)`, async () => {
        await PickingJobScreen.clickUnpickItem();
        await PickingJobScreen.scanProductToUnpick({ scannedCode });
        if (expectDefaultQty != null) {
            await PickingJobScreen.expectDefaultQtyToUnpick({ qty: expectDefaultQty });
        }
        await PickingJobScreen.enterQtyToUnpick({ qty });
        await PickingJobScreen.skipTargetHUAndCommit();
    }),

    // Drives the unpick panel up to (but not through) the target-HU scan: open the panel, scan the
    // product GTIN, enter the qty -> the panel is now on the SCAN_TARGET stage (the target-HU scanner
    // is armed). Stops here so the caller can drive the target-HU submit itself (e.g. under a network
    // fault) and assert on the in-between state.
    unpickAdvanceToTargetStage: async ({ scannedCode, qty, expectDefaultQty }) => await step(`${NAME} - Unpack ${qty} of '${scannedCode}', advance to target-HU scan stage`, async () => {
        await PickingJobScreen.clickUnpickItem();
        await PickingJobScreen.scanProductToUnpick({ scannedCode });
        if (expectDefaultQty != null) {
            await PickingJobScreen.expectDefaultQtyToUnpick({ qty: expectDefaultQty });
        }
        await PickingJobScreen.enterQtyToUnpick({ qty });
    }),

    // Scans a code at the target-HU stage when the submit (the picking/event POST) is expected to FAIL:
    // either a transient network failure (no HTTP response) or a server rejection (4xx/5xx, e.g. the
    // operator mis-scans the product GTIN they are holding instead of a target HU). The scan fires and is
    // submitted, but the unpick does NOT commit and the panel must NOT close. Only commits the scan — the
    // caller asserts the error toast and the still-open panel via expectOnTargetScanStage(); the caller's
    // test.step / expectErrorToast label carries which failure mode is under test.
    scanCodeAtTargetStageNoCommit: async ({ scannedCode }) => await step(`${NAME} - Scan '${scannedCode}' at target-HU stage (submit failure expected, no commit)`, async () => {
        await BarcodeScannerComponent.type({ scannedCode, testId: 'unpick-target-hu-scanner' });
    }),

    // Asserts the unpick panel is still on the SCAN_TARGET stage: the target-HU scanner is still armed
    // (the panel did not close back to the job screen). This is the submit-failure invariant — a failed
    // submit (transient network OR a server rejection) keeps the panel open so the operator can simply
    // scan again.
    expectOnTargetScanStage: async () => await step(`${NAME} - Expect still on target-HU scan stage`, async () => {
        await BarcodeScannerComponent.expectAttached({ testId: 'unpick-target-hu-scanner', timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Negative path: scans a code that does not resolve to a product packed in this job. The single
    // error surface is the scanner's toast; wrap the call site in expectErrorToast(...) to assert it.
    scanProductCodeToUnpick: async ({ scannedCode }) => await step(`${NAME} - Scan product code '${scannedCode}' (no advance expected)`, async () => {
        await BarcodeScannerComponent.type({ scannedCode, testId: 'unpick-product-scanner' });
    }),

    expectOnProductScanStage: async () => await step(`${NAME} - Expect still on product-scan stage`, async () => {
        await BarcodeScannerComponent.expectAttached({ testId: 'unpick-product-scanner', timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.locator('.get-qty-dialog')).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    closeUnpickItem: async () => await step(`${NAME} - Close "Unpack item"`, async () => {
        await page.getByTestId('unpick-close-button').tap();
        await PickingJobScreen.waitForScreen();
    }),

    // Simulates a transient network failure on the unpick submit by aborting the picking/event POST at
    // the network layer (no HTTP response). With no axiosError.response, the unpick panel treats this as
    // a recoverable failure -> toasts the error and stays on the SCAN_TARGET stage. Pair with
    // unblockUnpickSubmit() to release the fault before the retry.
    blockUnpickSubmit: async () => await step(`${NAME} - Block picking/event (simulate network fault on unpick submit)`, async () => {
        await page.route(UNPICK_SUBMIT_ROUTE, route => route.abort('failed'));
    }),

    unblockUnpickSubmit: async () => await step(`${NAME} - Unblock picking/event (release network fault)`, async () => {
        await page.unroute(UNPICK_SUBMIT_ROUTE);
    }),
};

// The picking/event POST that the unpick submit (postStepPartiallyUnPicked) fires.
const UNPICK_SUBMIT_ROUTE = '**/picking/event';

//
//
//

const pickingSlotButton = () => {
    return page.locator(`#scan-activity-${ACTIVITY_ID_ScanPickingSlot}-button`);
}

const locateLineButton = ({ index }) => {
    return page.locator(`#line-0-${index - 1}-button`);
};

const expectLineButtonAttribute = async ({ lineButton, attribute, value }) => await step(`${NAME} - Expect line button attribute ${attribute}='${value}'`, async () => {
    const lineButtonInfo = lineButton.locator('.picking-row-info');
    await expect(lineButtonInfo).toHaveAttribute(attribute, value);
});

const pickAllButton = () => page.getByTestId('pickAll-button');
