import { expect } from '@playwright/test';

/**
 * Tracks, inside the page, the XHRs the app has sent but not yet finished handling, so a screen object
 * can wait for the app's background queries to land before it taps something whose position those
 * answers can still change. The mobile app talks to the backend through axios, i.e. XMLHttpRequest.
 *
 * Why it exists: some screens fire queries when they mount and grow new buttons from the answers.
 * The picking job screen asks `has-closed-lu` and, once that says yes, inserts "Reopen LU" above the
 * other buttons, pushing everything below it - "Complete" included - down. A tap picks its point first
 * and the click follows the touch a little later; when the answer is rendered in between, the click
 * hits whatever moved under the old point instead of "Complete", and the confirmation dialog never
 * opens (flaky cases 58 / 95 / 103 / 175, seen in CI traces).
 *
 * Tracked in the page rather than via Playwright's request events: a request counts as done only once
 * the app's own `loadend` handler has run. That handler resolves axios, and the microtasks it queues
 * (the app's state update; React 17 re-renders synchronously there) run before the next `loadend`
 * listener - which is ours, because it is registered at send(), after axios assigned its handler.
 * Playwright's `requestfinished` only says the bytes arrived, not that the app has rendered them.
 *
 * Armed by the page fixture (playwright.config.js) before the test's first navigation, so the script
 * is in place in every document the test opens. Not in setCurrentPage: browserless harness tests
 * (tests/harness) install a fake page through it, which has no addInitScript.
 */

/** Arm the in-page XHR tracking on `page`. Called by the page fixture (playwright.config.js). */
export const trackInFlightXhr = async (page) => {
    await page.addInitScript(() => {
        if (window.__e2eXhrInFlight) return;
        const inFlight = new Set();
        window.__e2eXhrInFlight = { urls: () => [...inFlight].map((entry) => entry.url) };

        const originalOpen = XMLHttpRequest.prototype.open;
        const originalSend = XMLHttpRequest.prototype.send;
        XMLHttpRequest.prototype.open = function (method, url, ...rest) {
            this.__e2eUrl = String(url);
            return originalOpen.call(this, method, url, ...rest);
        };
        XMLHttpRequest.prototype.send = function (...args) {
            const entry = { url: this.__e2eUrl };
            inFlight.add(entry);
            this.addEventListener('loadend', () => inFlight.delete(entry));
            try {
                return originalSend.apply(this, args);
            } catch (error) {
                inFlight.delete(entry); // never sent, so no loadend will ever remove it
                throw error;
            }
        };
    });
};

// Lets the page run what is already scheduled: effects that fire queries on mount, and painting what
// an answer has just rendered. Two frames, because work scheduled during one frame runs by the next.
// Capped, because a page that is not being painted (e.g. a background tab in a headed run) never runs
// requestAnimationFrame callbacks and would otherwise hold this until the test timeout.
const FLUSH_CAP_MS = 500;
const flushPageRendering = async (page) => {
    await page.evaluate((capMs) => new Promise((resolve) => {
        setTimeout(resolve, capMs);
        requestAnimationFrame(() => requestAnimationFrame(resolve));
    }), FLUSH_CAP_MS);
};

/**
 * Wait until no XHR of `page` whose URL matches `urlPattern` is in flight - i.e. the app has handled
 * every such answer - and the page has painted the result. Fails (rather than passing vacuously) when
 * the tracking script is not installed in the current document.
 */
export const waitForNoInFlightXhr = async (page, { urlPattern, timeout }) => {
    // First let a screen that has just mounted fire its queries, so they are counted below.
    await flushPageRendering(page);

    await expect.poll(
        async () => {
            const urls = await page.evaluate(() => window.__e2eXhrInFlight?.urls() ?? null);
            // Without tracking the wait would pass vacuously and silently stop protecting the tap after it.
            return urls === null ? 'in-flight XHR tracking is not installed in this document' : urls.filter((url) => urlPattern.test(url));
        },
        { message: `XHRs matching ${urlPattern} still in flight`, timeout, intervals: [50, 100, 250] }
    ).toEqual([]);

    await flushPageRendering(page);
};
