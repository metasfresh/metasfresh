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
        await button.locator('.indicator-color-green').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
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
        await button.locator('.indicator-color-green').waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT });
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

    pickHU: async ({ qrCode, isScanDirectly, expectedPickDirectly, switchToManualInput, qtyEntered, expectQtyEntered, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason }) => await step(`${NAME} - Scan HU and Pick`, async () => {
        if (isScanDirectly) {
            await BarcodeScannerComponent.type(qrCode);
        } else {
            await page.locator('#scanQRCode-button').tap(); // click the Scan QR Code button
            await PickingJobScanHUScreen.waitForScreen();
            await PickingJobScanHUScreen.typeQRCode(qrCode);
        }

        if (expectedPickDirectly) {
            await PickingJobScreen.waitForScreen();
        } else {
            await GetQuantityDialog.fillAndPressDone({ switchToManualInput, expectQtyEntered, qtyEntered, catchWeight, catchWeightQRCode, qtyNotFoundReason, expectQtyNotFoundReason });
            await PickingJobScreen.waitForScreen();
        }
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
                await indicator.waitFor({ state: 'attached' });
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

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingJobScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobsListScreen.waitForScreen();
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

const pickAllButton = () => page.getByTestId('pickAll-button');
