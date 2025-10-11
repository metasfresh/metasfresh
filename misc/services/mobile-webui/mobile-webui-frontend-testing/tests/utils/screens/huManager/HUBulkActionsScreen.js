import { test } from '../../../../playwright.config';
import { page } from '../../common';
import { expect } from '@playwright/test';
import { ApplicationsListScreen } from '../ApplicationsListScreen';

const NAME = 'HUBulkActionsScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#HUBulkActionsScreen');

export const HUBulkActionsScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    move: async ({ targetLocator }) => await test.step(`${NAME} - Move HU`, async () => {
        await page.getByTestId('toggle-target-scanner-button').tap();
        await page.type('#input-text', targetLocator);

        await ApplicationsListScreen.waitForScreen();
    }),
};
