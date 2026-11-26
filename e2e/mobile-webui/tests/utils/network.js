import { page } from "./common";

/**
 * Delays every request of the given method to the url pattern (a slow backend).
 * @param {{urlPattern: RegExp, delayMs: number, method?: string}} params `method`: only requests of this HTTP method are delayed
 * @returns {Promise<() => Promise<void>>} restores the normal speed: requests still waiting are let through at once,
 * then the interception ends (a handler still sleeping when the route is removed would fail with "Route is already handled!")
 */
export const delayRequests = async ({ urlPattern, delayMs, method }) => {
    const pendingHandlers = new Set();
    const releaseCallbacks = new Set();
    let restored = false;

    await page.route(urlPattern, async (route) => {
        const handling = (async () => {
            if (!restored && (!method || route.request().method() === method)) {
                await new Promise((resolve) => {
                    const timer = setTimeout(resolve, delayMs);
                    releaseCallbacks.add(() => {
                        clearTimeout(timer);
                        resolve();
                    });
                });
            }
            await route.continue();
        })();
        pendingHandlers.add(handling);
        try {
            await handling;
        } finally {
            pendingHandlers.delete(handling);
        }
    });

    return async () => {
        restored = true;
        releaseCallbacks.forEach((release) => release());
        releaseCallbacks.clear();
        await Promise.all([...pendingHandlers]);
        await page.unroute(urlPattern);
    };
};
