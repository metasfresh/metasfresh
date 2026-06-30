import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { getValidationStatus } from '../utils/WebAPIValidation';
import { SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT, FRONTEND_BASE_URL } from '../utils/common';

/**
 * Workplace (Arbeitsplatz) window — shelf-life-warning field.
 *
 * Verifies that the `IsWarnShelfLifeUndercut` YesNo checkbox:
 * - is rendered in the Workplace detail view
 * - can be toggled on and saved via the standard PATCH flow
 * - persists after a page reload (end-state assertion)
 *
 * AD_Window_ID = 541744
 * Column = C_Workplace.IsWarnShelfLifeUndercut
 */

const WORKPLACE_WINDOW_ID = 541744;

// Selector for the checkbox — derived from the DB column name via the
// `.form-field-{ColumnName}` wrapper pattern (RawWidget.js).
// Language-invariant: selects by column name, not by caption text.
const SHELF_LIFE_CHECKBOX = '.form-field-IsWarnShelfLifeUndercut input[type="checkbox"]';
const SHELF_LIFE_FIELD_WRAPPER = '.form-field-IsWarnShelfLifeUndercut';

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

test.describe('Workplace window — shelf-life-warning field', () => {
  testCases.forEach(({ language, label }) => {
    test(`IsWarnShelfLifeUndercut checkbox renders, toggles, and persists (${label})`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0105: Picking');
      allure.tag('F00230: MobileUI Picking');
      allure.tag('F00230');
      allure.story('Workplace shelf-life-warning field renders and persists');
      allure.severity('normal');
      allure.description(`
## Workplace shelf-life-warning field — AD layout smoke test

Verifies that the new \`IsWarnShelfLifeUndercut\` (YesNo) checkbox on the
Workplace (Arbeitsplatz) window (AD_Window_ID = 541744) renders correctly,
accepts a toggle from false to true, saves via the standard PATCH, and
survives a page reload.

This is the mandatory window-design-rules evidence test for the
MHD-Kontrolle bei Kommissionierung feature (C_Workplace.IsWarnShelfLifeUndercut).
Language under test: ${language}.
      `);

      // 1. Provision test data — create a workplace with the flag set to false.
      //    A warehouse is required by WorkplaceCreateCommand.
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language } },
          warehouses: { WH1: {} },
          workplaces: {
            WP1: {
              warehouse: 'WH1',
              warnShelfLifeUndercut: false,
            },
          },
        },
      });

      // Extract the workplace record ID from the context map.
      // The masterdata API registers it as "de.metas.workplace.WorkplaceId:<key>".
      const workplaceId = masterdata.context['de.metas.workplace.WorkplaceId:WP1'];
      expect(workplaceId).toBeTruthy();
      console.log(`[INFO] Created workplace WP1 with ID=${workplaceId}, warnShelfLifeUndercut=false (${language})`);

      // 2. Login — use URL-based check to avoid networkidle wait (STOMP keeps network active).
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      // expectLoggedIn() checks URL-only; DashboardPage.expectVisible() waits for networkidle
      // which never settles on a metasfresh dashboard due to STOMP/WebSocket polling.
      await LoginPage.expectLoggedIn();

      // 3. Navigate directly to the Workplace detail record.
      //    URL pattern: /window/{windowId}/{recordId}
      await test.step('Navigate to Workplace detail view', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${WORKPLACE_WINDOW_ID}/${workplaceId}`);

        // Wait for the detail form to load (record-specific URL confirms navigation)
        await page.waitForURL(`**\/window\/${WORKPLACE_WINDOW_ID}\/${workplaceId}`, {
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });

        // Wait for form content to be visible
        await page
          .locator('.panel-bordered, .header-breadcrumb-name')
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      });

      // Mandatory: assert the record is valid before attempting to modify it.
      // If valid: false, field changes are silently discarded and the PATCH does not persist.
      const validationStatus = await getValidationStatus(WORKPLACE_WINDOW_ID, workplaceId);
      expect(
        validationStatus.valid,
        `Workplace record must be valid before toggling IsWarnShelfLifeUndercut; missing: ${JSON.stringify(validationStatus.missingFields)}`
      ).toBe(true);

      // 4. Assert the checkbox field is visible.
      await test.step('Assert IsWarnShelfLifeUndercut checkbox is visible', async () => {
        const fieldWrapper = page.locator(SHELF_LIFE_FIELD_WRAPPER);
        await fieldWrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        const checkbox = page.locator(SHELF_LIFE_CHECKBOX);
        await expect(checkbox).toBeVisible();

        // Initial value must be false (we created the record with warnShelfLifeUndercut=false)
        await expect(checkbox).not.toBeChecked();
        console.log('[INFO] Checkbox is visible and initially unchecked — as expected');
      });

      // 5. Toggle the checkbox to true and await the PATCH response.
      //    Per playwright-run skill: await the save response (PATCH), not a blind sleep.
      //    Per selectors-and-gotchas.md: click the <label> wrapping the checkbox, not the
      //    <input> directly — metasfresh renders the checkbox with a covering <div> overlay.
      await test.step('Toggle checkbox to true and await PATCH save', async () => {
        const fieldWrapper = page.locator(SHELF_LIFE_FIELD_WRAPPER);

        // Wait for the PATCH confirming the field change was persisted.
        // The PATCH fires on field change in metasfresh (auto-save on blur/change).
        const patchResponsePromise = page.waitForResponse(
          (resp) =>
            resp.url().includes(`/rest/api/window/${WORKPLACE_WINDOW_ID}/${workplaceId}`) &&
            resp.request().method() === 'PATCH',
          { timeout: SLOW_ACTION_TIMEOUT }
        );

        // Click the `.input-checkbox` label inside the field wrapper.
        // The Checkbox.js component renders:
        //   <label class="input-checkbox">
        //     <input type="checkbox" onChange={updateCheckedState} />
        //     <div class="input-checkbox-tick" />
        //   </label>
        // Clicking the label triggers the input's onChange handler (the div tick
        // intercepts pointer events but the label's own click bubbles through).
        await fieldWrapper.locator('.input-checkbox').click();

        const patchResponse = await patchResponsePromise;
        expect(patchResponse.ok()).toBeTruthy();
        console.log(`[INFO] PATCH response: ${patchResponse.status()} ${patchResponse.url()}`);
        console.log('[INFO] Checkbox toggled and PATCH response received');
      });

      // 6. Reload the page and assert the value persisted (end-state assertion).
      await test.step('Reload and assert persisted value', async () => {
        await page.reload();

        // Wait for the detail form to re-render after reload
        await page
          .locator('.panel-bordered, .header-breadcrumb-name')
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

        const fieldWrapper = page.locator(SHELF_LIFE_FIELD_WRAPPER);
        await fieldWrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        const checkbox = page.locator(SHELF_LIFE_CHECKBOX);
        await expect(checkbox).toBeVisible();
        await expect(checkbox).toBeChecked();

        console.log('[INFO] Checkbox is checked after reload — value persisted');
      });
    });
  });
});
