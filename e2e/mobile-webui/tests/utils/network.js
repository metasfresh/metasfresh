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

/**
 * Lets every request of the given method to the url pattern reach the backend at once, but holds its answer back (a slow network on the way back).
 * @param {{urlPattern: RegExp, delayMs: number, method?: string}} params `method`: only answers to requests of this HTTP method are held
 * @returns {Promise<() => Promise<void>>} waits until every held answer has reached the page, then ends the interception
 */
export const delayResponses = async ({ urlPattern, delayMs, method }) => {
    const pendingHandlers = new Set();

    await page.route(urlPattern, async (route) => {
        const handling = (async () => {
            if (method && route.request().method() !== method) {
                await route.continue();
                return;
            }
            const response = await route.fetch();
            await new Promise((resolve) => setTimeout(resolve, delayMs));
            await route.fulfill({ response });
        })();
        pendingHandlers.add(handling);
        try {
            await handling;
        } finally {
            pendingHandlers.delete(handling);
        }
    });

    return async () => {
        await Promise.all([...pendingHandlers]);
        await page.unroute(urlPattern);
    };
};

/**
 * Swallows every request of the given method to the url pattern: it never reaches the backend and gets no answer (a half-open connection),
 * until the returned function is called. The page's own request timeout is what ends such a request.
 * @param {{urlPattern: RegExp, method?: string}} params `method`: only requests of this HTTP method are swallowed
 * @returns {Promise<() => Promise<void>>} aborts the requests still held, then ends the interception
 */
export const hangRequests = async ({ urlPattern, method }) => {
    const pendingHandlers = new Set();
    const releaseCallbacks = new Set();
    let restored = false;

    await page.route(urlPattern, async (route) => {
        const handling = (async () => {
            if (restored || (method && route.request().method() !== method)) {
                await route.continue();
                return;
            }
            await new Promise((resolve) => releaseCallbacks.add(resolve));
            // the page gave up on the request already (timeout): aborting a finished request may throw, which is fine
            await route.abort().catch(() => {});
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
