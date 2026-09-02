import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { BUSINESS_PARTNER_WINDOW_ID } from '../utils/WindowIds';
import { getIncludedRecordData } from '../utils/WebAPIValidation';

/**
 * Business Partner window (AD_Window_ID = 123) -> "Print Format" tab
 * (AD_Tab-540653, over table C_BP_PrintFormat) -- the two per-partner print
 * config flags:
 *
 *   - IsDropShip  ("Abweichende Lieferadresse" / "Different shipping address")
 *   - IsAutoPrint ("Sofort drucken"           / "Print immediately")
 *
 * Both columns use AD_Reference 17 (List) over value-set 319 (_YesNo), so they
 * render as List dropdowns (options none / N_No / Y_Yes), NOT Yes-No switches.
 *
 * The test verifies, for a fresh C_BP_PrintFormat row created through the real
 * UI flow (open BPartner -> Print Format tab -> "Add new"):
 *   1. both fields RENDER  -- as grid columns and as form-field widgets in the row form,
 *   2. both are EDITABLE   -- their List dropdown opens and a value can be picked,
 *   3. IsAutoPrint and IsDropShip toggle to "Yes" SAVE via the standard PATCH, and
 *   4. the value PERSISTS  -- asserted against the system of record via the WebAPI
 *      (fieldsByName.IsAutoPrint.value.key === 'Y') after a full page reload.
 *
 * Selectors are language-invariant (data-testid / data-cy / DB ColumnName /
 * structural class); the "Yes" option is picked by its `data-test-id`, which begins with the
 * language-invariant AD_Ref_List VALUE key ("Y"). The persisted value is verified
 * language-invariantly via the WebAPI value key ("Y").
 */

const PRINT_FORMAT_TAB = 'AD_Tab-540653'; // C_BP_PrintFormat included tab of window 123

// A List (_YesNo) widget renders its options at document level (tethered) as
// `.input-dropdown-list-option`. Each option carries data-test-id = `${key}${caption}`
// (SelectionDropdown.renderOption), so it ALWAYS begins with the AD_Ref_List VALUE key
// ("Y" for Yes, "N" for No) — a truly language-invariant handle. This matters beyond
// language: the *visible caption* also varies with developer mode — MLookupFactory renders
// list captions as "Value_Name" (e.g. "Y_Yes") when de.metas.adempiere.debug is on and as
// the plain translated name (e.g. "Yes" / "Ja") when off (the CI/production default). Only
// the data-test-id key prefix is stable across BOTH, so selecting by it — mirroring
// cost-revaluation-copyfrom-field.spec.js — is the correct, config-independent approach.
const YES_OPTION_BY_KEY =
  '.input-dropdown-list .input-dropdown-list-option[data-test-id^="Y"]';

/**
 * Open the List dropdown of `.form-field-<column>` inside the currently open row
 * form, pick the "Yes" option, and await the resulting PATCH on the C_BP_PrintFormat
 * row. Asserts the PATCH targets THIS column and succeeds. Returns the PATCH URL
 * (carries the included-row id: /window/123/{bpId}/AD_Tab-540653/{rowId}).
 */
async function setListFieldToYes(page, columnName) {
  return await test.step(`Set ${columnName} = Yes and await PATCH`, async () => {
    const wrapper = page.locator(`.form-field-${columnName}`);
    await wrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Open the dropdown.
    await wrapper.locator('.input-dropdown-container').first().click();

    const patchPromise = page.waitForResponse(
      (resp) =>
        new RegExp(`/rest/api/window/${BUSINESS_PARTNER_WINDOW_ID}/\\d+/${PRINT_FORMAT_TAB}/\\d+`).test(resp.url()) &&
        resp.request().method() === 'PATCH' &&
        (resp.request().postData() || '').includes(`"${columnName}"`),
      { timeout: SLOW_ACTION_TIMEOUT }
    );

    await page.locator(YES_OPTION_BY_KEY).first().click();

    const patch = await patchPromise;
    expect(patch.ok(), `PATCH of ${columnName} must succeed`).toBeTruthy();

    // The widget now holds a value — the container drops its `.input-empty` class once a value
    // is set (RawList: `'input-empty': !value`). This is language- and developer-mode-invariant
    // (the displayed caption text differs across both; whether a value is present does not).
    await expect(wrapper.locator('.input-dropdown-container').first()).not.toHaveClass(
      /input-empty/
    );

    // Wait for the document-tethered option list to unmount before returning, so a
    // back-to-back call opening the next field's dropdown cannot resolve the global
    // `.input-dropdown-list-option` selector against this (still-detaching) list —
    // a CI-only race under slow paint. Mirrors cost-revaluation-copyfrom-field.spec.js.
    await page
      .locator('.input-dropdown-list')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});

    return patch.url();
  });
}

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

test.describe('Business Partner Print Format tab — IsDropShip & IsAutoPrint config fields', () => {
  testCases.forEach(({ language, label }) => {
    test(`IsDropShip & IsAutoPrint render, are editable, and persist (${label})`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0390: Masterdata Partner');
      allure.tag('F00900: Business Partner');
      allure.tag('F00900');
      allure.story('Business Partner Print Format tab — IsDropShip & IsAutoPrint fields');
      allure.severity('normal');
      allure.description(`
## Business Partner "Print Format" tab — IsDropShip & IsAutoPrint

Verifies the two C_BP_PrintFormat config flags on the Business Partner window
(AD_Window_ID = ${BUSINESS_PARTNER_WINDOW_ID}), Print Format tab (${PRINT_FORMAT_TAB}):
they render (grid columns + row-form widgets), are editable (List dropdown),
toggle-save, and persist (asserted via the WebAPI value key after a full reload).

Language under test: ${language}.
      `);

      // 1. Provision a single-role login user + one business partner to hang the
      //    print-format row off. createMasterdata returns the C_BPartner_ID.
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language } },
          bpartners: { BP1: { name: `PrintFormatFields ${label}` } },
        },
      });
      const bpartnerId = masterdata.bpartners.BP1.id;
      expect(bpartnerId, 'created C_BPartner_ID must be present').toBeTruthy();

      // 2. Login.
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();

      // 3. Open the BPartner detail and switch to the Print Format tab.
      await test.step('Open BPartner and switch to Print Format tab', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${bpartnerId}`);
        const tab = page.locator(`[data-testid="tab-${PRINT_FORMAT_TAB}"]`);
        await tab.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await tab.click();
        // Grid header renders once the tab activates.
        await page
          .locator('[data-testid="column-IsAutoPrint"]')
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      });

      // 4. Assert BOTH fields render as columns of the Print Format grid.
      await test.step('Assert both fields render as grid columns', async () => {
        await expect(page.locator('[data-testid="column-IsDropShip"]')).toBeVisible();
        await expect(page.locator('[data-testid="column-IsAutoPrint"]')).toBeVisible();
      });

      // 5. Create a new print-format row (opens the row form in a modal).
      await test.step('Add a new C_BP_PrintFormat row', async () => {
        // "Add new" is the create button in the tab's filter-panel toolbar. Exclude the
        // batch-entry-toggle sibling (`.close-batch-entry`) so selection does not depend on
        // source order. (A dedicated data-testid on the button would be more robust still, but
        // that lives in the shared TableFilter component, out of scope for this spec change.)
        await page
          .locator('.filter-panel-buttons button:not(.close-batch-entry)')
          .first()
          .click();
        await page
          .locator('.panel-modal')
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      });

      // 6. Assert BOTH fields render as editable widgets in the row form.
      await test.step('Assert both fields render as row-form widgets', async () => {
        await expect(page.locator('.form-field-IsDropShip')).toBeVisible();
        await expect(page.locator('.form-field-IsAutoPrint')).toBeVisible();
        // widgetType-List => rendered as the List dropdown these fields were defined with.
        await expect(page.locator('.form-field-IsDropShip.widgetType-List')).toBeVisible();
        await expect(page.locator('.form-field-IsAutoPrint.widgetType-List')).toBeVisible();
      });

      // 7. Toggle both fields to "Yes" via their dropdowns; each auto-saves via PATCH.
      const patchUrl = await setListFieldToYes(page, 'IsAutoPrint');
      await setListFieldToYes(page, 'IsDropShip');

      // Resolve the included-row id from the PATCH URL for the system-of-record check.
      const rowMatch = patchUrl.match(
        new RegExp(`/window/${BUSINESS_PARTNER_WINDOW_ID}/${bpartnerId}/${PRINT_FORMAT_TAB}/(\\d+)`)
      );
      const rowId = rowMatch && rowMatch[1];
      expect(rowId, 'C_BP_PrintFormat row id must be resolvable from the PATCH URL').toBeTruthy();

      // 8. Close the row modal.
      await test.step('Close the row form', async () => {
        await page.locator('[data-testid="process-modal-cancel-button"]').click();
        await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      });

      // 9. The saved values show in the grid cells (renders the persisted value). The cell text is
      //    the List caption, which varies by language and developer mode, so assert the cell is
      //    populated; the exact value is checked language-invariantly via the WebAPI read-back below.
      await test.step('Assert saved values show in the grid', async () => {
        await expect(page.locator('[data-cy="cell-IsAutoPrint"]').first()).toHaveText(/\S/);
        await expect(page.locator('[data-cy="cell-IsDropShip"]').first()).toHaveText(/\S/);
      });

      // 10. Persistence — reload the whole window, re-open the tab, and assert the
      //     value survived (end-state assertion against the system of record).
      await test.step('Reload and assert persistence (WebAPI + grid)', async () => {
        await page.goto(`${FRONTEND_BASE_URL}/window/${BUSINESS_PARTNER_WINDOW_ID}/${bpartnerId}`);
        await page.locator(`[data-testid="tab-${PRINT_FORMAT_TAB}"]`).click();
        await page
          .locator('[data-cy="cell-IsAutoPrint"]')
          .first()
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await expect(page.locator('[data-cy="cell-IsAutoPrint"]').first()).toHaveText(/\S/);
        await expect(page.locator('[data-cy="cell-IsDropShip"]').first()).toHaveText(/\S/);

        // System of record: read the included row back via the WebAPI. The value key
        // ('Y') is language-invariant, so this is the authoritative persistence proof.
        const record = await getIncludedRecordData(
          BUSINESS_PARTNER_WINDOW_ID,
          bpartnerId,
          PRINT_FORMAT_TAB,
          rowId
        );
        expect(record.fieldsByName.IsAutoPrint.value.key).toBe('Y');
        expect(record.fieldsByName.IsDropShip.value.key).toBe('Y');
      });
    });
  });
});
