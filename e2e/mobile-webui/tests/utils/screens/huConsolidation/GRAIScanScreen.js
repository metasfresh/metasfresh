import { attachScreenshot, expectErrorToast, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT, step } from '../../common';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { ErrorToast } from '../../dialogs/ErrorToast';
import { HUConsolidationJobScreen } from './HUConsolidationJobScreen';

const NAME = 'GRAIScanScreen';
/** data-testid of the scanner input on this screen (same id as the picking slot screen's scanner) */
const GRAI_SCANNER_TESTID = 'grai-scanner';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#GRAIScanScreen');

export const GRAIScanScreen = {
    waitForScreen: async () => await step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await BarcodeScannerComponent.expectAttached({ testId: GRAI_SCANNER_TESTID });
        // The scanner's keyboard listener is attached in a passive effect: wait two frames (paint + effects) before the first scan.
        await page.evaluate(() => new Promise((resolve) => requestAnimationFrame(() => requestAnimationFrame(resolve))));
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await BarcodeScannerComponent.expectAttached({ testId: GRAI_SCANNER_TESTID });
    }),

    /** Expect the screen's header caption (same text in de_DE and en_US: "Scan (GRAI)"). */
    expectHeaderCaption: async ({ caption }) => await test.step(`${NAME} - Expect header caption '${caption}'`, async () => {
        await expect(containerElement().locator('.app-header .app-caption')).toHaveText(caption, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Expect the browser URL to be the scan screen route of a job: .../huConsolidation/.../scanGRAI/<activityId> */
    expectUrlIsScanScreenRoute: async () => await test.step(`${NAME} - Expect the URL to be the scan screen route`, async () => {
        await expect(page).toHaveURL(/\/huConsolidation\/.+\/scanGRAI\/[^/?#]+(?:[?#].*)?$/, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectNotVisible: async () => await test.step(`${NAME} - Expect screen to be gone`, async () => {
        await expect(containerElement()).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** Expect the screen to have closed by itself (no GRAI crate left in the slots): the job screen is shown again. */
    expectClosedByItself: async () => await step(`${NAME} - Expect the screen to return to the job screen by itself`, async () => {
        await GRAIScanScreen.expectNotVisible();
        await HUConsolidationJobScreen.waitForScreen();
    }),

    /** Leave the scan screen with the Back button: the job screen is shown again. */
    goBack: async () => await step(`${NAME} - Go back to the job screen`, async () => {
        await GRAIScanScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await HUConsolidationJobScreen.waitForScreen();
    }),

    /**
     * Scan a crate's GRAI (keyboard events, as a hardware scanner) and wait until the scan was answered and the scanner is idle again,
     * so the next scan is not swallowed by the scanner that is still busy with this one.
     * The screen shows no result: the effect is checked on the job screen after Back.
     * @param {string} grai canonical dot form or GS1 AI 8003 form
     */
    scanGRAI: async ({ grai }) => await step(`${NAME} - Scan GRAI: ${grai}`, async () => {
        // listener right before the action that fires the call
        const answered = page.waitForResponse(
            (resp) => resp.url().includes('/consolidateByScan') && resp.request().method() === 'POST',
            { timeout: SLOW_ACTION_TIMEOUT }
        );
        await BarcodeScannerComponent.type({ scannedCode: grai, testId: GRAI_SCANNER_TESTID });
        await answered;
        // the scanner shows its spinner while it processes a scan (not present when the screen already closed itself)
        await containerElement().locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /**
     * Scan a GRAI that the backend rejects: expects exactly one error toast containing the given message (the toast is closed).
     * @param {{grai: string, message: string, screenshotName?: string}} params message = expected text of the toast (in the user's language);
     *   screenshotName = attach a screenshot while the toast is shown
     */
    scanGRAIExpectError: async ({ grai, message, screenshotName }) => await test.step(`${NAME} - Scan GRAI ${grai}, expect error "${message}"`, async () => {
        await expectErrorToast(`${NAME} scan ${grai} rejected`, async () => {
            await GRAIScanScreen.scanGRAI({ grai });
        }, async ({ textContent }) => {
            expect(textContent).toContain(message);
            if (screenshotName) {
                await ErrorToast.expectFullyShown();
                await attachScreenshot(screenshotName);
            }
        });
    }),
};
