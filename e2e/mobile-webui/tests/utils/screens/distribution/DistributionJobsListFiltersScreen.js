import { test } from "../../../../playwright.config";
import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from "../../common";
import { DistributionJobsListScreen } from "./DistributionJobsListScreen";
import { expect } from "@playwright/test";

export const DistributionJobsListFiltersScreen = {
    waitForScreen: async () => await test.step('Wait for distribution jobs list filters screen', async () => {
        await page.locator('#WFLaunchersFiltersScreen').waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await DistributionJobsListFiltersScreen.waitLoadComplete();
    }),

    waitLoadComplete: async () => await test.step('Wait for load complete', async () => {
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    filterByFacetId: async ({ facetId, expectHitCount }) => await test.step(`Filter by facet ${facetId}`, async () => {
        const facetButton = page.locator(`[data-testid="${facetId}"]`);
        await facetButton.tap();
        await DistributionJobsListFiltersScreen.waitLoadComplete();

        const showResultsButton = page.locator('#showResults');
        if (expectHitCount != null) {
            await expect(showResultsButton).toHaveAttribute("data-hitcount", String(expectHitCount));
        }

        await showResultsButton.tap();
        await DistributionJobsListScreen.waitForScreen();
    }),

    // Reads the facet-group DOM the launcher renders (see FacetGroup.jsx / Facet.jsx): a group is a
    // `[data-testid=<groupId>]` container with a `.caption` div, holding one button per facet chip
    // (`data-testid=<facetId>`, `data-hitcount=<hitCount>`). These two are read-only lookups — a real
    // worker identifies a group/chip by what is WRITTEN on it, not by an id — so they never toggle
    // selection; call `filterByFacetId` (above) to actually select a chip once its id is known.

    expectGroupOffered: async ({ groupId, caption }) => await test.step(`Expect facet group "${groupId}" offered, captioned "${caption}"`, async () => {
        const group = page.locator(`[data-testid="${groupId}"]`);
        await group.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(group.locator('.caption')).toHaveText(caption);
    }),

    // Absence counterpart to expectGroupOffered above: a group with zero facets is never rendered at
    // all (DistributionFacetsCollection only builds a group for a groupId it actually collected a
    // facet for), so "not offered" means the group container never attaches to the DOM.
    expectGroupNotOffered: async ({ groupId }) => await test.step(`Expect facet group "${groupId}" NOT offered`, async () => {
        await expect(page.locator(`[data-testid="${groupId}"]`)).toHaveCount(0);
    }),

    getFacetIdByCaptionContains: async ({ groupId, captionContains, expectHitCount }) => await test.step(`Get facet id in group "${groupId}" captioned like "${captionContains}"`, async () => {
        const chip = page.locator(`[data-testid="${groupId}"] button`).filter({ hasText: captionContains });
        await chip.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        if (expectHitCount != null) {
            await expect(chip).toHaveAttribute('data-hitcount', String(expectHitCount));
        }
        return await chip.getAttribute('data-testid');
    }),

    // Leaves the filters screen without changing the active selection (the "not now" path — mirrors
    // the header back-arrow every screen offers, as opposed to `filterByFacetId`'s "showResults" apply).
    cancel: async () => await test.step('Cancel filters (no changes)', async () => {
        await page.locator(ID_BACK_BUTTON).tap();
        await DistributionJobsListScreen.waitForScreen();
    }),

};
