import { page } from "./common";

/**
 * Delays every request of the given method to the url pattern (a slow backend).
 * @param {{urlPattern: RegExp, delayMs: number, method?: string}} params `method`: only requests of this HTTP method are delayed
 * @returns {Promise<() => Promise<void>>} restores the normal speed
 */
export const delayRequests = async ({ urlPattern, delayMs, method }) => {
    await page.route(urlPattern, async (route) => {
        if (!method || route.request().method() === method) {
            await new Promise((resolve) => setTimeout(resolve, delayMs));
        }
        await route.continue();
    });
    return async () => await page.unroute(urlPattern);
};
