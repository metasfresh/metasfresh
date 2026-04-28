import { FAST_ACTION_TIMEOUT, page, SLOW_ACTION_TIMEOUT, step } from "../common";

const NAME = 'ConfirmActivityErrorPanel';
const containerElement = () => page.getByTestId('confirm-activity-error-panel');

export const ConfirmActivityErrorPanel = {
    waitForPanel: async () => await step(`${NAME} - Wait for panel`, async () => {
        await containerElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    waitForPanelDetached: async () => await step(`${NAME} - Wait for panel detached`, async () => {
        await containerElement().waitFor({ state: 'detached', timeout: FAST_ACTION_TIMEOUT });
    }),

    clickRetry: async () => await step(`${NAME} - Click Retry`, async () => {
        await page.getByTestId('confirm-activity-error-retry').tap();
    }),

    clickCancel: async () => await step(`${NAME} - Click Cancel`, async () => {
        await page.getByTestId('confirm-activity-error-cancel').tap();
    }),
};
