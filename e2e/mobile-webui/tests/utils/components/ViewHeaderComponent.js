import { page, SLOW_ACTION_TIMEOUT } from '../common';
import { test } from '../../../playwright.config';
import { expect } from '@playwright/test';

const NAME = 'ViewHeaderComponent';

// The app-wide header table rendered by containers/ViewHeader.jsx — one <tr> per entry:
// <th>caption</th><td>value</td>. It carries the operator's session-global context
// (Workplace / Workstation) on every screen that publishes header entries.
/** @returns {import('@playwright/test').Locator} */
const valueElement = (caption) => page.locator(`table.view-header tr:has(th:text-is("${caption}")) td`);

export const ViewHeaderComponent = {
    // Asserts the header row for `caption` is painted and reads exactly `value`.
    // toHaveText (not a :has-text substring match) so a STALE value fails instead of
    // silently passing, and `visible` so the operator really sees it on screen.
    expectProperty: async ({ caption, value, timeout = SLOW_ACTION_TIMEOUT }) => await test.step(`${NAME} - Expect header '${caption}' = '${value}'`, async () => {
        await valueElement(caption).waitFor({ state: 'visible', timeout });
        await expect(valueElement(caption)).toHaveText(value, { timeout });
    }),
};
