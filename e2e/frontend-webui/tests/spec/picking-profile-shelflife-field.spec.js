import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { getValidationStatus } from '../utils/WebAPIValidation';
import { SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Mobile UI Kommissionierprofil (MobileUI_UserProfile_Picking) window —
 * shelf-life-warning field.
 *
 * Verifies that the `IsWarnShelfLifeUndercut` YesNo checkbox:
 * - is rendered in the picking-profile detail view
 * - can be toggled on and saved via the standard PATCH flow
 * - persists after a page reload (end-state assertion)
 *
 * AD_Window_ID = 541743 (the picking profile is a per-client singleton record).
 * Column = MobileUI_UserProfile_Picking.IsWarnShelfLifeUndercut
 *
 * The flag lives here (not on C_Workplace) — this is the mandatory
 * window-design-rules evidence test for the MHD-Kontrolle bei Kommissionierung feature.
 */

const PICKING_PROFILE_WINDOW_ID = 541743;

// Selector for the checkbox — derived from the DB column name via the
// `.form-field-{ColumnName}` wrapper pattern (RawWidget.js).
// Language-invariant: selects by column name, not by caption text.
const SHELF_LIFE_CHECKBOX = '.form-field-IsWarnShelfLifeUndercut input[type="checkbox"]';
const SHELF_LIFE_FIELD_WRAPPER = '.form-field-IsWarnShelfLifeUndercut';

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

test.describe('Mobile UI Kommissionierprofil window — shelf-life-warning field', () => {
  testCases.forEach(({ language, label }) => {
    test(`IsWarnShelfLifeUndercut checkbox renders, toggles, and persists (${label})`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0105: Picking');
      allure.tag('F00230: MobileUI Picking');
      allure.tag('F00230');
      allure.story('Picking-profile shelf-life-warning field renders and persists');
      allure.severity('normal');
      allure.description(`
## Picking-profile shelf-life-warning field — AD layout smoke test

Verifies that the \`IsWarnShelfLifeUndercut\` (YesNo) checkbox on the
Mobile UI Kommissionierprofil window (AD_Window_ID = ${PICKING_PROFILE_WINDOW_ID}) renders,
accepts a toggle from false to true, saves via the standard PATCH, and survives a page reload.

Language under test: ${language}.
      `);

      // 1. Provision test data — a login user of the given language.
      //    pickTo keeps at least one pick-to structure on the profile
      //    (MobileConfigPickingCommand rejects a profile with none).
      //    The profile is a per-client singleton shared across scenarios, so this test
      //    does NOT assume an initial checkbox value — it reads, toggles, and asserts the flip.
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language } },
          mobileConfig: {
            picking: {
              pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
            },
          },
        },
      });

      // 2. Login — URL-based check (STOMP keeps the network active, so networkidle never settles).
      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();

      // 3. Open the picking-profile window and its single "default" record.
      let profileRecordId;
      await test.step('Open the picking-profile record', async () => {
        await MasterWindowPage.goto(PICKING_PROFILE_WINDOW_ID);

        // The window is a per-client singleton — open the one row into the detail view.
        await page.locator('.table-row').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
        await page.locator('.table-row').first().dblclick();

        // Detail URL carries the record id: /window/541743/{recordId}
        await page.waitForURL(new RegExp(`/window/${PICKING_PROFILE_WINDOW_ID}/\\d+`), {
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });
        const match = page.url().match(new RegExp(`/window/${PICKING_PROFILE_WINDOW_ID}/(\\d+)`));
        profileRecordId = match && match[1];
        expect(profileRecordId, 'picking-profile record id must be resolvable from the detail URL').toBeTruthy();

        await page
          .locator('.panel-bordered, .header-breadcrumb-name')
          .first()
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      });

      // Mandatory: the record must be valid, else field changes are silently discarded.
      const validationStatus = await getValidationStatus(PICKING_PROFILE_WINDOW_ID, profileRecordId);
      expect(
        validationStatus.valid,
        `Picking-profile record must be valid before toggling; missing: ${JSON.stringify(validationStatus.missingFields)}`
      ).toBe(true);

      // 4. Assert the checkbox renders; capture its current value (singleton — no assumption).
      const fieldWrapper = page.locator(SHELF_LIFE_FIELD_WRAPPER);
      const checkbox = page.locator(SHELF_LIFE_CHECKBOX);
      let initiallyChecked;
      await test.step('Assert IsWarnShelfLifeUndercut checkbox renders', async () => {
        await fieldWrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(checkbox).toBeVisible();
        initiallyChecked = await checkbox.isChecked();
      });

      // 5. Toggle the checkbox and await the PATCH save (auto-save on change); assert it flipped.
      await test.step('Toggle checkbox and await PATCH save', async () => {
        const patchResponsePromise = page.waitForResponse(
          (resp) =>
            resp.url().includes(`/rest/api/window/${PICKING_PROFILE_WINDOW_ID}/${profileRecordId}`) &&
            resp.request().method() === 'PATCH',
          { timeout: SLOW_ACTION_TIMEOUT }
        );

        // Click the wrapping label (the tick <div> intercepts pointer events on the raw input).
        await fieldWrapper.locator('.input-checkbox').click();

        const patchResponse = await patchResponsePromise;
        expect(patchResponse.ok()).toBeTruthy();
        await expect(checkbox).toBeChecked({ checked: !initiallyChecked });
      });

      // 6. Reload and assert the toggled value persisted (end-state assertion).
      await test.step('Reload and assert persisted value', async () => {
        await page.reload();

        await page
          .locator('.panel-bordered, .header-breadcrumb-name')
          .first()
          .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

        await fieldWrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(checkbox).toBeVisible();
        await expect(checkbox).toBeChecked({ checked: !initiallyChecked });
      });
    });
  });
});
