import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT } from '../common';
import { test } from '../../../playwright.config';
import { expect } from '@playwright/test';

const NAME = 'OperatorContextErrorPanel';

// The panel WFLaunchersScreen (misc/services/mobile-webui/mobile-webui-frontend/src/containers/
// wfLaunchersScreen/WFLaunchersScreen.jsx) renders instead of the job list when the operator's
// workplace/workstation could not be READ or ASSIGNED because the connection dropped. Every
// launchers-based app (manufacturing, picking, distribution, ...) renders that same screen, so this
// belongs to a component shared by their screen objects rather than to any one of them.
/** @returns {import('@playwright/test').Locator} */
const panelElement = () => page.getByTestId('operator-context-error-panel');
/** @returns {import('@playwright/test').Locator} */
const retryButtonElement = () => page.getByTestId('operator-context-error-retry');

export const OperatorContextErrorPanel = {
    expectVisible: async () => await test.step(`${NAME} - Expect the panel to be displayed`, async () => {
        await expect(panelElement()).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectNotVisible: async () => await test.step(`${NAME} - Expect no panel`, async () => {
        await expect(panelElement()).toHaveCount(0, { timeout: FAST_ACTION_TIMEOUT });
    }),

    tapRetry: async () => await test.step(`${NAME} - Tap retry`, async () => {
        await retryButtonElement().tap();
    }),
};
