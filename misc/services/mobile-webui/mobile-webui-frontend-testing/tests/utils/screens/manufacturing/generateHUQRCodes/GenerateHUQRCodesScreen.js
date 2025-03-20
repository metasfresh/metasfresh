import { FAST_ACTION_TIMEOUT, page } from '../../../common';
import { test } from '../../../../../playwright.config';
import { GenerateHUQRCodesConfirmScreen } from './GenerateHUQRCodesConfirmScreen';

const NAME = 'GenerateHUQRCodesScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#GenerateHUQRCodesScreen');

export const GenerateHUQRCodesScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: FAST_ACTION_TIMEOUT });
    }),

    print: async ({ piTestId, expectNumberOfHUs, expectNumberOfCopies }) => await test.step(`${NAME} - Print ${piTestId}`, async () => {
        await page.getByTestId(piTestId).tap();
        await GenerateHUQRCodesConfirmScreen.waitForScreen();
        return await GenerateHUQRCodesConfirmScreen.confirm({ expectNumberOfHUs, expectNumberOfCopies });
    }),

};