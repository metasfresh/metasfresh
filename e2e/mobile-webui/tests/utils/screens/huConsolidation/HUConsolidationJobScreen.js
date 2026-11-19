import { expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step, VERY_SLOW_ACTION_TIMEOUT } from '../../common';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { HUConsolidationJobsListScreen } from './HUConsolidationJobsListScreen';
import { SelectHUConsolidationTargetScreen } from './SelectHUConsolidationTargetScreen';
import { PickingSlotScreen } from './PickingSlotScreen';
import { Backend } from '../Backend';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';

const NAME = 'HUConsolidationJobScreen';
/** data-testid of the "Scan (GRAI)" button on the job screen */
const SCAN_GRAI_BUTTON_TESTID = 'scanGRAI-button';
/** data-testid of the GRAI scanner input opened by the "Scan (GRAI)" button (same input id as the slot screen's scanner) */
const GRAI_SCANNER_TESTID = 'grai-scanner';
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

    /**
     * Read the consolidation job's current target LU global QR code from the live WFProcess.
     *
     * Call this AFTER at least one TU has been consolidated: the job's target switches from
     * "new LU" to the freshly-created existing LU on the first consolidate (see the module's
     * "target mutates new→existing LU" gotcha), so the returned QR code identifies the real
     * target LU. Use it as an `hus` matcher in Backend.expect to assert the consolidated TUs
     * are now parented under that LU (the actual end result of consolidating).
     *
     * @returns {Promise<string>} the target LU's global QR code string
     */
    getCurrentTargetLUQRCode: async () => await step(`${NAME} - Get current target LU QR code`, async () => {
        // The WFProcess endpoint needs the FULL id ("huConsolidation-<n>", per WFProcessId.ofString),
        // not the bare numeric id getJobId() returns.
        const currentUrl = await page.url();
        const match = currentUrl.match(/\/(huConsolidation-\d+)/);
        const wfProcessId = match ? match[1] : null;
        if (!wfProcessId) {
            throw new Error('Could not determine the consolidation wfProcessId from the current URL: ' + currentUrl);
        }

        const wfProcess = await Backend.getWFProcess({ wfProcessId });
        const activity = wfProcess?.activities?.find(a => a.componentType === 'huConsolidation/consolidate');
        const luQRCode = activity?.componentProps?.job?.currentTarget?.luQRCode;
        if (!luQRCode) {
            throw new Error('No current target LU QR code found in the consolidation WFProcess:\n' + JSON.stringify(wfProcess, null, 2));
        }
        return luQRCode;
    }),

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

    /** Tap the job-level "Scan (GRAI)" button (opens the scanner on the job screen). */
    clickScanGRAIButton: async () => await step(`${NAME} - Click Scan (GRAI) button`, async () => {
        await page.getByTestId(SCAN_GRAI_BUTTON_TESTID).tap();
        // An operator sees the scan strip appear before pulling the trigger of the gun (well over 100 ms). Playwright would type
        // within the same millisecond as the tap: the first open of the scanner registers its window key listener a few ms after
        // the input is attached (measured: a scan typed < 30 ms after the first tap is lost, 30 ms or more never), which a person cannot hit.
        // So wait until the strip is shown and two animation frames have passed, as a person would.
        await BarcodeScannerComponent.expectAttached({ testId: GRAI_SCANNER_TESTID });
        await page.evaluate(() => new Promise((resolve) => requestAnimationFrame(() => requestAnimationFrame(resolve))));
    }),

    /**
     * Scan a crate's GRAI on the job screen (types into the scanner's manual input, as PickingSlotScreen.scanGRAI).
     * @param {string} grai canonical dot form or GS1 AI 8003 form
     */
    scanGRAI: async ({ grai }) => await step(`${NAME} - Scan GRAI: ${grai}`, async () => {
        await BarcodeScannerComponent.type({ scannedCode: grai, testId: GRAI_SCANNER_TESTID });
    }),

    /**
     * Scan a GRAI that the backend rejects: expects exactly one error toast containing the given message (the toast is closed).
     * @param {{grai: string, message: string}} params message = expected text of the toast (in the user's language)
     */
    scanGRAIExpectError: async ({ grai, message }) => await test.step(`${NAME} - Scan GRAI ${grai}, expect error "${message}"`, async () => {
        await expectErrorToast(`${NAME} scan ${grai} rejected`, async () => {
            await HUConsolidationJobScreen.scanGRAI({ grai });
        }, ({ textContent }) => {
            expect(textContent).toContain(message);
        });
    }),

    /**
     * Expect the "Scan (GRAI)" button to be shown or hidden; when shown, optionally also its disabled state and its caption.
     * @param {{visible: boolean, disabled?: boolean, caption?: string}} params
     */
    expectScanGRAIButton: async ({ visible, disabled, caption }) => await test.step(`${NAME} - Expect Scan (GRAI) button visible=${visible} disabled=${disabled} caption=${caption}`, async () => {
        const button = page.getByTestId(SCAN_GRAI_BUTTON_TESTID);
        if (!visible) {
            await expect(button).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
            return;
        }
        await expect(button).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        if (disabled != null) {
            if (disabled) await expect(button).toBeDisabled();
            else await expect(button).toBeEnabled();
        }
        if (caption != null) {
            await expect(button).toHaveText(caption, { timeout: SLOW_ACTION_TIMEOUT });
        }
    }),

    /**
     * Type a GRAI by hand: tap "Enter manually" in the scanner, type the code key by key into the visible manual input
     * (real key events, as a person on the soft keyboard), press Enter.
     * @param {string} grai
     */
    typeGRAIByHand: async ({ grai }) => await step(`${NAME} - Type GRAI by hand: ${grai}`, async () => {
        await page.getByTestId('barcode-scanner-enter-manually').tap();
        const input = page.getByTestId('manual-entry-input');
        await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await input.focus();
        await input.pressSequentially(grai);
        await input.press('Enter');
    }),

    /** In manual entry: tap "Enter manually" (without typing); the scanner switches to the manual input. */
    openManualEntry: async () => await step(`${NAME} - Open manual entry`, async () => {
        await page.getByTestId('barcode-scanner-enter-manually').tap();
        await page.getByTestId('manual-entry-input').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** In manual entry: tap "back to scanner"; the strip with the scan prompt is shown again. */
    backToScanner: async () => await step(`${NAME} - Back to scanner`, async () => {
        await page.getByTestId('barcode-scanner-back-to-scanner').tap();
        await BarcodeScannerComponent.expectAttached({ testId: GRAI_SCANNER_TESTID });
    }),

    /** Bounding boxes (x, y, width, height in CSS px) of the job screen parts, for the layout checks on a phone-size screen. */
    getLayoutBoxes: async ({ pickingSlotIds }) => await step(`${NAME} - Read layout boxes`, async () => {
        const box = async (locator) => {
            const b = await locator.boundingBox();
            if (!b) throw new Error('Element has no bounding box: ' + locator);
            return b;
        };
        const slots = {};
        for (const id of pickingSlotIds) {
            slots[id] = await box(page.locator(`[data-pickingslotid="${id}"]`));
        }
        return {
            viewport: page.viewportSize(),
            targetLUButton: await box(page.getByTestId('targetLU-button')),
            scanner: await box(page.locator('.barcode-scanner')),
            slots,
            complete: await box(page.locator('#last-confirm-button')),
        };
    }),

    /** Expect the scanner's idle caption (the line next to the barcode icon) to contain the text. */
    expectScannerCaption: async ({ text }) => await test.step(`${NAME} - Expect scanner caption to contain '${text}'`, async () => {
        await expect(page.locator('.barcode-scanner .scan-prompt-text-idle')).toContainText(text, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Expect the job screen's GRAI scanner to be open (its input is attached to the DOM; the input is not painted) / closed. */
    expectScannerOpen: async (open) => await test.step(`${NAME} - Expect GRAI scanner open=${open}`, async () => {
        if (open) await BarcodeScannerComponent.expectAttached({ testId: GRAI_SCANNER_TESTID });
        else await BarcodeScannerComponent.expectNotAttached({ testId: GRAI_SCANNER_TESTID });
    }),

    /** Expect the picking slot row caption to show the given HU count, e.g. "slot1 (2 HUs)" / "(1 HU)". */
    expectPickingSlotHUCount: async ({ pickingSlotId, countHUs }) => await test.step(`${NAME} - Expect slot ${pickingSlotId} to hold ${countHUs} HU(s)`, async () => {
        const unit = countHUs === 1 ? 'HU' : 'HUs';
        const slotRow = page.locator(`[data-pickingslotid="${pickingSlotId}"]`);
        await expect(slotRow).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(slotRow)
            .toContainText(`(${countHUs} ${unit})`, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Expect the pallet (target LU) button caption to no longer show the pallet TYPE name, i.e. it shows the open pallet's QR code. */
    expectTargetLUCaptionNotContains: async ({ text }) => await test.step(`${NAME} - Expect target LU caption not to contain '${text}'`, async () => {
        await expect(page.getByTestId('targetLU-button')).not.toContainText(text, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Expect the pallet (target LU) button caption to start with the given text ("Neues Gebinde" / "New LU" / "Offenes Gebinde: ..." / "Current: ..."). */
    expectTargetLUCaptionStartsWith: async ({ text }) => await test.step(`${NAME} - Expect target LU caption to start with '${text}'`, async () => {
        await expect(page.getByTestId('targetLU-button')).toHaveText(new RegExp('^\\s*' + text.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')), { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Expect the Fertigstellen ("Complete") button and the Rückgängig ("Abort") button captions. */
    expectCompleteAndAbortCaptions: async ({ complete, abort }) => await test.step(`${NAME} - Expect captions '${complete}' / '${abort}'`, async () => {
        await expect(page.locator('#last-confirm-button')).toContainText(complete, { timeout: SLOW_ACTION_TIMEOUT });
        await expect(page.locator('#abort-button')).toContainText(abort, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Leave the job screen with the Back button (the job stays started), back to the job list. */
    goBack: async () => await step(`${NAME} - Go back to the job list`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await HUConsolidationJobsListScreen.waitForScreen();
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

    /**
     * Tap Rückgängig / Abort and confirm with Ja, when the server refuses (a real pallet is the current target):
     * the job screen stays, no job list.
     */
    abortRefused: async () => await step(`${NAME} - Abort (refused, stays on the job screen)`, async () => {
        await page.locator('#abort-button').tap();
        await YesNoDialog.waitForDialog();
        // listener right before the action that fires the call: wait for the abort answer, so a navigation that
        // follows an accepted abort cannot be missed by the checks below
        const abortAnswered = page.waitForResponse(
            (resp) => resp.url().includes('/wfProcess/') && resp.url().endsWith('/abort') && resp.request().method() === 'POST',
            { timeout: SLOW_ACTION_TIMEOUT }
        );
        await YesNoDialog.clickYesButton();
        await abortAnswered;
        await expect(page.locator('#WFLaunchersScreen')).toHaveCount(0);
        await HUConsolidationJobScreen.waitForScreen();
        await HUConsolidationJobScreen.expectVisible();
    }),

    complete: async () => await step(`${NAME} - Complete`, async () => {
        await page.locator('#last-confirm-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await HUConsolidationJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),
};

