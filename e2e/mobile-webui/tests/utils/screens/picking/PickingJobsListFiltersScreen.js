import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from "../../common";
import { GetDocumentNoDialog } from "../../dialogs/GetDocumentNoDialog";
import { PickingJobsListScreen, PickingJobsListScreen as PickingJobListScreen } from './PickingJobsListScreen';
import { expect } from '@playwright/test';

const NAME = 'PickingJobsListFiltersScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#WFLaunchersFiltersScreen');

export const PickingJobsListFiltersScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
    }),

    waitLoadingDone: async () => await test.step(`${NAME} - Wait for loading done`, async () => {
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
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

    expectFacets: async (expectationsArray) => await test.step(`${NAME} - Expect to have ${expectationsArray.length} facets`, async () => {
        await PickingJobsListFiltersScreen.waitLoadingDone();

        const buttons = page.locator('.group').locator('button');
        await expect(buttons).toHaveCount(expectationsArray.length);

        for (let i = 0; i < expectationsArray.length; i++) {
            let expectation = expectationsArray[i];

            await expectFacet({
                name: `${i + 1}/${expectationsArray.length} - ${expectation.facetId}`,
                button: buttons.nth(i),
                expectation
            });
        }
    }),

    clickFacet: async ({ facetId }) => await test.step(`${NAME} - Click facet ${facetId}`, async () => {
        const facetButton = page.locator(`button[data-testid="${facetId}"]`);
        await facetButton.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
        await facetButton.tap();
    }),

    clickShowResults: async () => await test.step(`${NAME} - Click Show results`, async () => {
        await page.locator('#showResults').tap();
        await PickingJobListScreen.waitForScreen();
    }),

    expectShowResults: async ({ hitCount }) => await test.step(`${NAME} - Expect show results`, async () => {
        await PickingJobsListFiltersScreen.waitLoadingDone();

        if (hitCount != null) {
            await expect(page.locator('#showResults')).toHaveAttribute('data-hitcount', String(hitCount));
        }
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await PickingJobsListFiltersScreen.waitForScreen();
        await page.locator(ID_BACK_BUTTON).tap();
        await PickingJobsListScreen.waitForScreen();
    }),

};

//
//
//
//
//

const expectFacet = async ({ name, button, expectation }) => await test.step(`Expect facet button ${name}`, async () => {
    await expect(button).toBeVisible();

    if (expectation.facetId != null) {
        await expect(button).toHaveAttribute('data-testid', expectation.facetId);
    }

    if (expectation.isChecked != null) {
        const checkIcon = button.locator('.fa-check');
        if (expectation.isChecked) {
            await expect(checkIcon).toHaveCount(1);
            await expect(checkIcon).toBeVisible();
        } else {
            await expect(checkIcon).toHaveCount(0);
        }
    }
});