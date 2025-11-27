import { test } from "../../../../playwright.config";
import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step, VERY_FAST_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from "../../common";
import { DistributionLineScreen } from './DistributionLineScreen';
import { YesNoDialog } from '../../dialogs/YesNoDialog';
import { DistributionJobsListScreen } from './DistributionJobsListScreen';
import { DistributionDropAllToScreen } from './DistributionDropAllToScreen';
import { expect } from '@playwright/test';
import { expectClasses } from '../../expectations';

const NAME = 'DistributionJobScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFProcessScreen');

export const DistributionJobScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    clickLineButton: async ({ index }) => await test.step(`${NAME} - Click line ${index}`, async () => {
        await lineButtonLocator({ index }).tap({ timeout: FAST_ACTION_TIMEOUT });
        await DistributionLineScreen.waitForScreen();
    }),

    expectLineButton: async ({ index, qtyToPick, qtyPicked, color }) => await step(`${NAME} - Expect line button at index ${index}`, async () => {
        const lineButton = lineButtonLocator({ index });

        if (qtyToPick !== undefined) {
            await expectLineButtonAttribute({ lineButton, attribute: 'data-qtytarget', value: qtyToPick });
        }
        if (qtyPicked !== undefined) {
            await expectLineButtonAttribute({ lineButton, attribute: 'data-qtycurrent', value: qtyPicked });
        }
        if (color !== undefined) {
            await expectClasses({
                locator: lineButton.locator(`[data-testid="indicator"]`),
                expectedClasses: `indicator-color-${color}`
            });
        }
    }),

    complete: async () => await test.step(`${NAME} - Complete`, async () => {
        await clickCompleteButton();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await DistributionJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    }),

    expectDropAllButton: async ({ enabled }) => await test.step(`${NAME} - Expect Drop All button`, async () => {
        const dropAllButton = dropAllButtonLocator();

        if (enabled != null) {
            if (enabled) {
                await expect(dropAllButton).toBeEnabled({ timeout: VERY_FAST_ACTION_TIMEOUT });
            } else {
                await expect(dropAllButton).toBeDisabled({ timeout: VERY_FAST_ACTION_TIMEOUT });
            }
        }
    }),

    dropAllTo: async ({ dropToLocatorQRCode }) => await test.step(`${NAME} - Drop All To ${dropToLocatorQRCode}`, async () => {
        const dropAllButton = dropAllButtonLocator();
        await expect(dropAllButton).toBeEnabled({ timeout: VERY_FAST_ACTION_TIMEOUT });
        await dropAllButton.tap();
        await DistributionDropAllToScreen.waitForScreen();
        await DistributionDropAllToScreen.typeQRCode(dropToLocatorQRCode);
        await DistributionJobScreen.waitForScreen();
    }),

    abort: async () => await step(`${NAME} - Abort`, async () => {
        await page.locator('#abort-button').tap();
        await YesNoDialog.waitForDialog();
        await YesNoDialog.clickYesButton();
        await DistributionJobsListScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await DistributionJobsListScreen.waitForScreen();
    }),

};

const lineButtonLocator = ({ index }) => {
    return page.getByTestId(`line-${index}-button`);
};

const expectLineButtonAttribute = async ({ lineButton, attribute, value }) => await step(`${NAME} - Expect line button attribute ${attribute}='${value}'`, async () => {
    const lineButtonInfo = lineButton.locator('.picking-row-info');
    await expect(lineButtonInfo).toHaveAttribute(attribute, value);
});

const dropAllButtonLocator = () => {
    return page.getByTestId('scanDropToLocator-button');
};

const clickCompleteButton = async () => await test.step(`${NAME} - Click Complete button`, async () => {
    await page.locator('#last-confirm-button').tap();
});

