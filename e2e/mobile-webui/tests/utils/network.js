import { page } from './common';
import { test } from '../../playwright.config';

// Warehouse WiFi drops out routinely, and the handheld then gets NO http response at all —
// as opposed to the server answering 4xx/5xx. These helpers reproduce exactly that
// network-layer failure for a given endpoint, so a screen's offline behaviour can be driven.

export const simulateConnectionLossFor = async (urlPattern) => await test.step(`Simulate connection loss for '${urlPattern}'`, async () => {
    await page.route(urlPattern, (route) => route.abort('failed'));
});

export const restoreConnectionFor = async (urlPattern) => await test.step(`Restore connection for '${urlPattern}'`, async () => {
    await page.unroute(urlPattern);
});
