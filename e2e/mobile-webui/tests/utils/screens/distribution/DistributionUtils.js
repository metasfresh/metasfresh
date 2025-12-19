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
};