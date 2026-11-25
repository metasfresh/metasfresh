import { step, page, SLOW_ACTION_TIMEOUT } from "../common";

const NAME = 'LoadingSpinner';
/** The scope of the layout check of a spinner */
export const LOADING_SCOPE = '.loading';

const containerElement = () => page.locator('.loading').first();

export const LoadingSpinner = {
    waitToShow: async () => await step(`${NAME} - Wait to show`, async () => {
        await containerElement().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    }),

    /** The spinner icon; for layout checks (see utils/layout.js) */
    icon: () => page.locator('.loading-icon').first(),
};
