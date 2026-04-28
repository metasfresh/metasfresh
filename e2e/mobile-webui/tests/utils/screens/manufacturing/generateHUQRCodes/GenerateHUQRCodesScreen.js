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

    print: async ({ piTestId, expectNumberOfHUs, numberOfHUs, expectNumberOfCopies }) => await test.step(`${NAME} - Print ${piTestId}`, async () => {
        await page.getByTestId(piTestId)
            .first() // NOTE: in the case of LUs, it might be duplicate buttons in case there are many TUs in the same LU
            .tap();
        await GenerateHUQRCodesConfirmScreen.waitForScreen();
        return await GenerateHUQRCodesConfirmScreen.confirm({ expectNumberOfHUs, numberOfHUs, expectNumberOfCopies });
    }),

};