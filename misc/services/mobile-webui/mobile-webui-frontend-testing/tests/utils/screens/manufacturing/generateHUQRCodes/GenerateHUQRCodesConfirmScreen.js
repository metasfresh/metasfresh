import { FAST_ACTION_TIMEOUT, page } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { ManufacturingJobScreen } from '../ManufacturingJobScreen';

const NAME = 'GenerateHUQRCodesConfirmScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#GenerateHUQRCodesConfirmScreen');

export const GenerateHUQRCodesConfirmScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: FAST_ACTION_TIMEOUT });
    }),

    confirm: async ({ expectNumberOfHUs, expectNumberOfCopies }) => await test.step(`${NAME} - Confirm print`, async () => {
        if (expectNumberOfHUs != null) {
            await expect(page.getByTestId('numberOfHUs-field')).toHaveValue(`${expectNumberOfHUs}`);
        }
        if (expectNumberOfCopies != null) {
            await expect(page.getByTestId('numberOfCopies-field')).toHaveValue(`${expectNumberOfCopies}`);
        }

        const generatedQRCodesPromise = waitForApiResponse({ urlContains: '/manufacturing/generateHUQRCodes' });

        await page.getByTestId('print-button').tap();

        await ManufacturingJobScreen.waitForScreen();

        return generatedQRCodesPromise;
    })
};

const waitForApiResponse = ({ urlContains, timeout = 5000 }) => {
    let resultValue, resultError;
    let resolveCallback = (result) => {
        resultValue = result;
    };
    let rejectCallback = (error) => {
        resultError = error;
    };

    const timeoutRef = setTimeout(() => {
        if (responseHandler) page.removeListener('response', responseHandler);
        rejectCallback(new Error(`Timeout: No response matched URL part "${urlContains}"`));
    }, timeout);

    const responseHandler = async (response) => {
        if (!response.url().includes(urlContains)) return;

        try {
            const responseBody = await response.json();
            resolveCallback(responseBody);
        } catch (e) {
            rejectCallback(e);
        } finally {
            if (timeout) clearTimeout(timeoutRef);
            page.removeListener('response', responseHandler);
        }
    };

    page.on('response', responseHandler);

    return new Promise((resolve, reject) => {
        resolveCallback = resolve;
        rejectCallback = reject;

        if (resultValue) resolve(resultValue);
        if (resultError) reject(resultError);
    });
};