import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page } from "../../common";
import { GetDocumentNoDialog } from "../../dialogs/GetDocumentNoDialog";
import { PickingJobsListScreen, PickingJobsListScreen as PickingJobListScreen } from './PickingJobsListScreen';
import { expect } from '@playwright/test';

const NAME = 'PickingJobsListFiltersScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersFiltersScreen');

export const PickingJobsListFiltersScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor();
    }),

    waitLoadingDone: async () => await test.step(`${NAME} - Wait for loading done`, async () => {
        await page.locator('.loading').waitFor({ state: 'detached' });
    }),

    filterByDocumentNo: async (documentNo) => await test.step(`${NAME} - Filter by documentNo ${documentNo}`, async () => {
        await page.locator('#filterByDocumentNo-button').tap();
        await GetDocumentNoDialog.waitForDialog();
        await GetDocumentNoDialog.typeDocumentNo(documentNo);
        await GetDocumentNoDialog.clickOKButton();
    }),

    clickOnlyQtyAvailableButton: async () => await test.step(`${NAME} - Filter by qty available`, async () => {
        await page.locator('#filterByQtyAvailableAtPickFromLocator-button').tap();
    }),

    expectFacets: async ({ groupId, facets }) => await test.step(`${NAME} - Expect group ${groupId} to have ${facets.length} facets`, async () => {
        await PickingJobsListFiltersScreen.waitLoadingDone();

        const groupElement = page.locator(`[data-testid="${groupId}"]`);
        await expect(groupElement).toBeVisible();

        const buttons = groupElement.locator('button');
        await expect(buttons).toHaveCount(facets.length);

        for (let i = 0; i < facets.length; i++) {
            const facet = facets[i];
            const facetButton = groupElement.locator(`button[data-testid="${facet.facetId}"]`);
            await expect(facetButton).toBeVisible();

            if (facet?.isChecked != null) {
                const checkIcon = facetButton.locator('.fa-check');
                if (facet.isChecked) {
                    await expect(checkIcon).toHaveCount(1);
                    await expect(checkIcon).toBeVisible();
                } else {
                    await expect(checkIcon).toHaveCount(0);
                }
            }
        }
    }),

    clickFacet: async ({ facetId }) => await test.step(`${NAME} - Click facet ${facetId}`, async () => {
        const facetButton = page.locator(`button[data-testid="${facetId}"]`);
        await facetButton.waitFor({ state: 'attached' });
        await facetButton.tap();
    }),

    clickShowResults: async () => await test.step(`${NAME} - Click Show results`, async () => {
        await page.locator('#showResults').tap();
        await PickingJobListScreen.waitForScreen();
    }),

    expectShowResults: async ({ hitCount }) => await test.step(`${NAME} - Expect show results`, async () => {
        await PickingJobsListFiltersScreen.waitLoadingDone();
        
        if(hitCount != null)
        {
            await expect(page.locator('#showResults')).toHaveAttribute('data-hitcount', String(hitCount));
        }
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingJobsListFiltersScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobsListScreen.waitForScreen();
    }),

};