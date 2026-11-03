import { FAST_ACTION_TIMEOUT, ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../common';
import { test } from '../../../../playwright.config';
import { ManufacturingJobScreen } from './ManufacturingJobScreen';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';
import { BarcodeScannerComponent } from '../../components/BarcodeScannerComponent';
import { OperatorContextErrorPanel } from '../../components/OperatorContextErrorPanel';
import { captionStartsWithField } from '../../launcherCaption';

const NAME = 'ManufacturingJobsListScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersScreen');
// The caption starts with the documentNo (`<documentNo> | <product> | <qty> | <date>`); match it as that
// leading field, never as a substring - see launcherCaption.js.
/** @returns {import('@playwright/test').Locator} */
const jobButton = ({ documentNo }) => page.locator('.wflauncher-button').filter({ hasText: captionStartsWithField(documentNo) });

export const ManufacturingJobsListScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    // The jobs list itself asks for a workstation when the Production app is configured to require
    // one and the operator has none assigned yet: the screen renders its own scanner instead of the
    // job buttons.
    expectAsksForWorkstation: async () => await test.step(`${NAME} - Expect the screen to ask for a workstation`, async () => {
        await ManufacturingJobsListScreen.expectVisible();
        await BarcodeScannerComponent.expectAttached({});
    }),

    expectDoesNotAskForWorkstation: async () => await test.step(`${NAME} - Expect the screen NOT to ask for a workstation`, async () => {
        await ManufacturingJobsListScreen.expectVisible();
        await BarcodeScannerComponent.expectNotAttached({});
    }),

    // Scan without asserting the job list takes over — for scenarios where the assign is expected to
    // fail (e.g. the connection dropped), so the scanner does NOT give way to the job list.
    typeWorkstationQRCode: async (qrCode) => await test.step(`${NAME} - Scan workstation QR '${qrCode}'`, async () => {
        await BarcodeScannerComponent.type(qrCode);
    }),

    scanWorkstation: async (qrCode) => await test.step(`${NAME} - Scan workstation QR '${qrCode}' and wait for the job list`, async () => {
        await ManufacturingJobsListScreen.typeWorkstationQRCode(qrCode);
        // The scanner disappears once the workstation is assigned and the job list takes over.
        await BarcodeScannerComponent.expectNotAttached({ timeout: SLOW_ACTION_TIMEOUT });
        await ManufacturingJobsListScreen.waitForScreen();
    }),

    expectJobNotListed: async ({ documentNo }) => await test.step(`${NAME} - Expect job '${documentNo}' NOT listed`, async () => {
        await expect(jobButton({ documentNo }))
            .toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    // When the operator's workplace/workstation cannot be read — or assigned from a scan — because
    // the connection dropped, the screen must say so and offer a retry, instead of silently showing
    // no workplace at all.
    expectConnectionErrorPanel: async () => await test.step(`${NAME} - Expect operator-context connection error panel`, async () => {
        await OperatorContextErrorPanel.expectVisible();
    }),

    expectNoConnectionErrorPanel: async () => await test.step(`${NAME} - Expect no operator-context connection error panel`, async () => {
        await OperatorContextErrorPanel.expectNotVisible();
    }),

    retryLoadingOperatorContext: async () => await test.step(`${NAME} - Retry loading workplace/workstation`, async () => {
        await OperatorContextErrorPanel.tapRetry();
        await ManufacturingJobsListScreen.waitForScreen();
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await ManufacturingJobsListScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ApplicationsListScreen.waitForScreen();
    }),

    // Test-setup check: a launcher whose caption contains `text` anywhere (plain substring match) is listed exactly once.
    expectJobButtonContainingText: async ({ text }) => await test.step(`${NAME} - Expect one job button containing '${text}'`, async () => {
        await expect(page.locator('.wflauncher-button').filter({ hasText: text })).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    // Test-setup check: how many listed launchers contain `text` anywhere in their caption (plain substring match).
    countJobButtonsContainingText: async ({ text }) => await test.step(`${NAME} - Count job buttons containing '${text}'`, async () => {
        return await page.locator('.wflauncher-button').filter({ hasText: text }).count();
    }),

    startJob: async ({ documentNo }) => await test.step(`${NAME} - Start job by documentNo ${documentNo}`, async () => {
        await jobButton({ documentNo }).tap();
        await ManufacturingJobScreen.waitForScreen();
        return {
            jobId: await ManufacturingJobsListScreen.getJobId(),
        }
    }),

    getJobId: async () => {
        const currentUrl = await page.url();

        const regex = /\/mfg-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

};
