import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT, step, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
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

const NAME = 'PickingJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');
const ACTIVITY_ID_ScanPickFromHU = 'scanPickFromHU'; // keep in sync with PickingMobileApplication.ACTIVITY_ID_ScanPickFromHU
const ACTIVITY_ID_ScanPickingSlot = 'scanPickingSlot'; // keep in sync with PickingMobileApplication.ACTIVITY_ID_ScanPickingSlot

export const PickingJobScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    getPickingJobId: async () => {
        const currentUrl = await page.url();

        const regex = /\/picking-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    scanPickFromHU: async ({ qrCode }) => await step(`${NAME} - Scan pick from HU ${qrCode}`, async () => {
        const button = page.getByTestId(`scan-activity-${ACTIVITY_ID_ScanPickFromHU}-button`);
        await button.waitFor();
        await expect(button).toBeEnabled();
        await button.tap();
        await PickFromHUScanScreen.waitForScreen();
        await PickFromHUScanScreen.typeQRCode(qrCode);
        await PickingJobScreen.waitForScreen();
        await button.locator('.indicator-green').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
    }),

    clickPickingSlotButton: async () => await step(`${NAME} - Click Picking Slot button`, async () => {
        const button = pickingSlotButton();
        await button.waitFor();
        await expect(button).toBeEnabled();
        await button.tap();

        await PickingSlotScanScreen.waitForScreen();
    }),

    expectPickingSlotButtonGreen: async () => await step(`${NAME} - Expect Picking Slot button to be green`, async () => {
        const button = pickingSlotButton();
        await button.waitFor();
        await button.locator('.indicator-green').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
    }),

    scanPickingSlot: async ({ qrCode, expectScanHUScreen }) => await step(`${NAME} - Scan picking slot ${qrCode}`, async () => {
        await PickingJobScreen.clickPickingSlotButton();
        await PickingSlotScanScreen.typeQRCode(qrCode);

        if (expectScanHUScreen) {
            await PickLineScanScreen.waitForScreen();
        } else {
            await PickingJobScreen.waitForScreen();
            await PickingJobScreen.expectPickingSlotButtonGreen();
        }
    }),

    clickLUTargetButton: async () => await step(`${NAME} - Click LU target button`, async () => {
        await page.getByTestId('targetLU-button').tap();
    }),
    setTargetLU: async ({ lu }) => await step(`${NAME} - Set target LU to ${lu}`, async () => {
        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetLUScreen.waitForScreen();
        await SelectPickTargetLUScreen.clickLUButton({ lu });
        await PickingJobScreen.waitForScreen();
    }),
    closeTargetLU: async () => await step(`${NAME} - Close target LU`, async () => {
        await PickingJobScreen.clickLUTargetButton();
        await SelectPickTargetLUScreen.clickCloseTargetButton();
        await PickingJobScreen.waitForScreen();
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
        await PickingJobScreen.waitForScreen();
    }),

    pickHU: async ({ qrCode, switchToManualInput, qtyEntered, expectQtyEntered, catchWeight, catchWeightQRCode, qtyNotFoundReason }) => await step(`${NAME} - Scan HU and Pick`, async () => {
        await page.locator('#scanQRCode-button').tap(); // click Scan QR Code button
        await PickingJobScanHUScreen.waitForScreen();
        await PickingJobScanHUScreen.typeQRCode(qrCode);
        await GetQuantityDialog.fillAndPressDone({ switchToManualInput, expectQtyEntered, qtyEntered, catchWeight, catchWeightQRCode, qtyNotFoundReason });
        await PickingJobScreen.waitForScreen();
    }),

    clickLineButton: async ({ index }) => await step(`${NAME} - Click line ${index}`, async () => {
        await locateLineButton({ index }).tap();
        //await PickingJobLineScreen.waitForScreen();
    }),

    expectLineButton: async ({ index, qtyPicked, qtyPickedCatchWeight, qtyToPick, color }) => await step(`${NAME} - Expect line button at index ${index}`, async () => {
        const lineButton = locateLineButton({ index });

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
                const indicator = lineButton.locator(`[data-testid="line-0-${index - 1}-button-Indicator"]`);
                await expect(indicator).toHaveClass(`indicator-${color}`);
            });
        }
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
};

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