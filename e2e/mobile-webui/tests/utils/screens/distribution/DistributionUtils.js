import { page } from '../../common';
import { expect } from '@playwright/test';

export const DistributionUtils = {
    getJobIdFromPageUrl: async () => {
        const currentUrl = await page.url();

        const regex = /\/distribution-(\d+)/;
        const match = currentUrl.match(regex);
        return match ? match[1] : null;
    },

    expectJobId: async ({ distributionJobId }) => {
        await expect(await DistributionUtils.getJobIdFromPageUrl()).toEqual(distributionJobId);
    },

    // Assert a row of the shared `.view-header` table (the job/line header rendered on the
    // distribution job, line and pick-from screens — same DOM everywhere). The caption is matched
    // EXACTLY (`th:text-is`) so a substring caption can't hit the wrong row. `exact` controls the
    // value: true matches the cell text exactly (`td:text-is`), false matches a substring
    // (`td:has-text`) — use false when the value carries trailing content you don't want to pin
    // (e.g. a qty followed by its UOM symbol). The row must exist exactly once AND be visible
    // (painted), not merely present in the DOM.
    expectHeaderProperty: async ({ caption, value, exact }) => {
        const valueSelector = exact ? `td:text-is("${value}")` : `td:has-text("${value}")`;
        const row = page.locator(`tr:has(th:text-is("${caption}")):has(${valueSelector})`);
        await expect(row).toHaveCount(1);
        await expect(row).toBeVisible();
    },
};