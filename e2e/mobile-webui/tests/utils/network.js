import { page } from './common';
import { test } from '../../playwright.config';
import { expect } from '@playwright/test';

// Warehouse WiFi drops out routinely, and the handheld then gets NO http response at all —
// as opposed to the server answering 4xx/5xx. These helpers reproduce exactly that
// network-layer failure for a given endpoint, so a screen's offline behaviour can be driven.

export const simulateConnectionLossFor = async (urlPattern) => await test.step(`Simulate connection loss for '${urlPattern}'`, async () => {
    await page.route(urlPattern, (route) => route.abort('failed'));
});

export const restoreConnectionFor = async (urlPattern) => await test.step(`Restore connection for '${urlPattern}'`, async () => {
    await page.unroute(urlPattern);
});

// The handheld's Android WebView caches a GET whose response carries no cache directives, and then
// answers the operator-context reads from its own disk — the screen freezes on whatever workplace /
// workstation was true when the app was first opened, and no amount of re-entering the app fixes it.
// The only durable guard is that the responses themselves forbid storing, so assert exactly that on
// the real end-to-end responses (the servlet filter that sets it also has its own unit test).
//
// Records every response for exactly the endpoint at `path`, from install time on. Install BEFORE the
// navigation that triggers the reads; responses that arrive later are still recorded.
// Matched on the exact URL pathname, not a substring, so a future sibling endpoint that merely shares
// the prefix (`/api/v2/workplaceManager`) cannot be swept silently into the same assertion set.
export const recordResponsesFor = (path) => {
    const responses = [];
    const handler = (response) => {
        let pathname;
        try {
            pathname = new URL(response.url()).pathname;
        } catch {
            return; // not an absolute URL — cannot be the endpoint under test
        }
        if (pathname === path) {
            responses.push({
                url: response.url(),
                status: response.status(),
                cacheControl: response.headers()['cache-control'] ?? null,
            });
        }
    };
    page.on('response', handler);

    return {
        stopRecording: () => page.removeListener('response', handler),

        // Asserts the endpoint was actually reached (a silent zero-response recording would make this
        // guard vacuous) and that every response it produced forbids storing.
        expectAllForbidStoring: async () => await test.step(`Expect every '${path}' response to forbid storing`, async () => {
            expect(responses.length, `no response recorded for '${path}' — the endpoint was never reached`).toBeGreaterThan(0);
            for (const { url, status, cacheControl } of responses) {
                expect(cacheControl, `Cache-Control of ${status} ${url}`).not.toBeNull();
                expect(cacheControl.toLowerCase(), `Cache-Control of ${status} ${url}`).toContain('no-store');
            }
        }),
    };
};
