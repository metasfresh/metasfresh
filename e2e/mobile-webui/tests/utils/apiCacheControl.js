import { page } from './common';
import { test } from '../../playwright.config';
import { expect } from '@playwright/test';

const NAME = 'ApiCacheControl';

/** Matched anywhere in the path, so a deployment that serves the API under a prefix (`/app/api/v2/...`) is covered too. */
const API_PATH_MARKER = '/api/v2/';

const apiPathOf = (url) => {
    const { pathname } = new URL(url);
    const markerAt = pathname.indexOf(API_PATH_MARKER);
    return markerAt < 0 ? null : pathname.substring(markerAt);
};

/**
 * Records the `Cache-Control` the app-server sent on every `/api/v2` response the app received.
 *
 * The app reads its operator context over these endpoints (`/api/v2/workplace`, `/api/v2/workstation`).
 * A response that says nothing about caching may be stored and re-served by the device's browser, and the
 * screen then keeps showing the operator context the app was opened with - the defect the app-server's
 * no-store filter (`de.metas.server.config.WebConfig#apiNoStoreCacheControlFilter`) exists to prevent.
 *
 * Only the app's own traffic is seen: `page.request` calls (e.g. `Backend`) are not page network events.
 */
export const ApiCacheControl = {
    startRecording: () => {
        // every DISTINCT value seen per path, not the last one: an endpoint the app calls repeatedly must not be
        // able to hide one bad response behind a later good one
        const cacheControlsByPath = new Map();

        page.on('response', (response) => {
            const path = apiPathOf(response.url());
            if (path != null) {
                const cacheControls = cacheControlsByPath.get(path) ?? new Set();
                cacheControls.add(response.headers()['cache-control'] ?? null);
                cacheControlsByPath.set(path, cacheControls);
            }
        });

        return {
            /**
             * @param including paths that MUST have been answered during the flow - without them the
             *        assertion would also pass on a recording that never captured anything.
             */
            expectNoApiResponseIsCacheable: async ({ including }) => await test.step(`${NAME} - Expect every ${API_PATH_MARKER}* response to be no-store`, async () => {
                expect([...cacheControlsByPath.keys()]).toEqual(expect.arrayContaining(including));

                // as a map, so a failure names the offending endpoints and what they sent instead
                const cacheable = [...cacheControlsByPath]
                    .map(([path, cacheControls]) => [path, [...cacheControls].filter((cacheControl) => cacheControl !== 'no-store')])
                    .filter(([, offending]) => offending.length > 0);
                expect(Object.fromEntries(cacheable)).toEqual({});
            }),
        };
    },
};
