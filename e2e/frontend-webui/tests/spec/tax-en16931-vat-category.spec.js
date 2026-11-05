import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { TAX_WINDOW_ID } from '../utils/WindowIds';
import { assertRecordIsValid, getFieldData, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * E-Invoicing — EN16931 VAT Category field on Tax window (C_Tax).
 *
 * Features tested:
 * - Field EN16931VATCategory on AD_Window 137 (Steuersatz / Tax), tab 174 (Steuer / Tax)
 * - AD_Field 781226, list-type widget backed by UNTDID 5305 reference list
 *
 * Test scenario:
 * 1. Login with a fresh test user (single-role → no role-selection step)
 * 2. Reset the webui metadata cache so the newly-added field is visible
 * 3. Navigate to Tax record 540010 (OSS CY 19%, currently EN16931VATCategory=S)
 * 4. Assert the EN16931 VAT Category field is present and is a List widget
 * 5. Assert the record is valid before editing (assertRecordIsValid guard)
 * 6. Read current value; pick a different one to change; verify persistence via WebAPI
 * 7. Restore the original value to leave the DB in a clean state
 */

/**
 * Tax record used as test subject.
 * OSS CY 19% — an existing, active record with a known initial EN16931VATCategory=S.
 * Using an existing record (not NEW) because C_Tax has many mandatory fields and the
 * test goal is purely to verify the List widget, not the record-creation flow.
 */
const TEST_TAX_RECORD_ID = 540010;

/**
 * All seven UNTDID 5305 / EN16931 VAT category codes supported by the field.
 * data-testid attributes used on the dropdown options: option-{code}.
 * Labels match the AD_Reference list values rendered by the frontend.
 */
const VAT_CATEGORY = {
  S: { testid: 'option-S', label: 'Standard rate' },
  Z: { testid: 'option-Z', label: 'Zero rated goods' },
  E: { testid: 'option-E', label: 'Exempt from tax' },
  AE: { testid: 'option-AE', label: 'VAT Reverse Charge' },
  K: { testid: 'option-K', label: 'intra-community supply' },
  G: { testid: 'option-G', label: 'Free export item' },
  O: { testid: 'option-O', label: 'Services outside scope' },
};

/**
 * Reset the webui metadata cache via the cache/reset endpoint.
 * This is required whenever a new AD_Field is added to ensure the
 * running webui picks up the new layout without a server restart.
 *
 * Uses the env-driven WEBAPI_BASE_URL constant from WebAPIValidation
 * to stay consistent with all other WebAPI calls in this suite.
 */
async function resetWebuiCache(page) {
  console.log('[INFO] Resetting webui metadata cache...');
  const resp = await page.request.get(`${WEBAPI_BASE_URL}/cache/reset`);
  const status = resp.status();
  const body = await resp.text();
  console.log(`[INFO] Cache reset response: HTTP ${status}`);
  if (status !== 200) {
    throw new Error(`Cache reset failed: HTTP ${status} — ${body.substring(0, 200)}`);
  }
  const logCount = (body.match(/invalidate/g) || []).length;
  console.log(`[INFO] Cache entries invalidated (mentions of 'invalidate': ${logCount})`);
}

test.describe('Tax window — EN16931 VAT Category field (E-Invoicing)', () => {
  test('EN16931 VAT Category field is present, editable, and persists correctly', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0340: Invoicing');
    allure.feature('F00751: e-Invoicing Germany');
    allure.tag('F00751: e-Invoicing Germany');
    allure.tag('F00751');
    allure.story('List widget EN16931VATCategory on Tax window renders and saves');
    allure.severity('critical');
    allure.tag('Tax');
    allure.tag('EN16931');
    allure.description(`
## E-Invoicing — EN16931 VAT Category on Tax window

### Feature
New field **EN16931VATCategory** on C_Tax (AD_Window 137 Steuersatz, tab 174 Steuer).
The field exposes the UNTDID 5305 / EN16931 VAT category code used in e-invoice XML.

### Test Steps
1. Create fresh test user and login
2. Reset webui metadata cache (so newly-added field is visible without server restart)
3. Navigate to Tax record ${TEST_TAX_RECORD_ID} (OSS CY 19%, EN16931VATCategory=S)
4. Assert EN16931 VAT Category field is present and is a List/dropdown widget
5. Assert record is valid (assertRecordIsValid guard — saves must not be lost)
6. Read current value; choose a different known code to change to (round-trip stays idempotent)
7. Change value via dropdown; wait for auto-save
8. Read back via WebAPI — assert persisted value matches what was selected
9. Restore original value and verify
    `);

    test.setTimeout(120000); // 2 minutes

    // === STEP 1: Create fresh test user ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: {
          user: {
            language: 'en_US',
            firstname: 'E2E',
            lastname: 'TaxVAT',
          },
        },
      },
    });

    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
    console.log(`[INFO] Test user created: ${masterdata.login.user.username}`);

    // === STEP 2: Login ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();
    console.log('[INFO] Logged in successfully');

    // === STEP 3: Reset webui metadata cache ===
    // Must be done after login (endpoint requires authentication).
    // Needed because EN16931VATCategory is a newly-added field and the running
    // webui may have a cached layout that pre-dates the migration.
    await resetWebuiCache(page);

    // === STEP 4: Navigate to the Tax record ===
    await page.goto(`${FRONTEND_BASE_URL}/window/${TAX_WINDOW_ID}/${TEST_TAX_RECORD_ID}`);

    // Wait for detail view to load
    await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.locator('.rotating, .indicator-pending')
      .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});

    console.log('[INFO] Tax record loaded. URL:', page.url());

    const screenshotInitial = await page.screenshot();
    allure.attachment('Tax Record — Initial State', screenshotInitial, 'image/png');

    // === STEP 5: Assert record is valid before editing (MANDATORY guard) ===
    // Per CLAUDE.md: "If valid: false, changes made in the UI WILL NOT BE SAVED!"
    await assertRecordIsValid(String(TAX_WINDOW_ID), String(TEST_TAX_RECORD_ID), 'before editing EN16931VATCategory');

    // === STEP 6: Assert EN16931 VAT Category field is present and is a List widget ===
    await test.step('Assert EN16931 VAT Category field is present as a List widget', async () => {
      // The field renders inside a div with class form-field-EN16931VATCategory
      // and widgetType-List (set by the frontend's RawWidget renderer).
      const fieldContainer = page.locator('.form-field-EN16931VATCategory');
      await fieldContainer.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Confirm it is rendered as a List widget (has the widgetType-List class)
      const hasListClass = await fieldContainer.evaluate((el) =>
        el.classList.contains('widgetType-List')
      );
      expect(hasListClass, 'EN16931 VAT Category field must be a List widget').toBe(true);

      console.log('[PASS] EN16931 VAT Category field present and rendered as List widget');
    });

    // === STEP 7: Read current value and determine which value to set ===
    // The test is designed to be idempotent: it reads the current value, picks a
    // different one to set, verifies the change persisted, then restores the original.
    // We use the WebAPI (authoritative) rather than parsing the input value string.
    let originalValueKey;

    await test.step('Read current EN16931VATCategory value via WebAPI', async () => {
      const fieldData = await getFieldData(
        String(TAX_WINDOW_ID),
        String(TEST_TAX_RECORD_ID),
        'EN16931VATCategory'
      );
      console.log('[INFO] WebAPI field data (initial):', JSON.stringify(fieldData));

      // List widgets return value as { key, caption }
      originalValueKey = String(fieldData.value?.key ?? fieldData.value ?? '');
      console.log(`[INFO] Original value key: "${originalValueKey}"`);

      // The field must display a known VAT category code — catches DB drift early
      expect(
        Object.keys(VAT_CATEGORY),
        `Current value "${originalValueKey}" must be one of the known VAT category codes: ${Object.keys(VAT_CATEGORY).join(', ')}`
      ).toContain(originalValueKey);

      allure.parameter('Original Value', originalValueKey);
    });

    // Pick a different value to set (if current is S, set AE; otherwise set S)
    const targetKey = originalValueKey === 'S' ? 'AE' : 'S';
    const targetCategory = VAT_CATEGORY[targetKey];
    console.log(`[INFO] Will change from "${originalValueKey}" to "${targetKey}"`);
    allure.parameter('Target Value', targetKey);

    // === STEP 8: Change value to the target category via dropdown ===
    await test.step(`Change value to ${targetKey} (${targetCategory.label})`, async () => {
      const fieldInput = page.locator('.form-field-EN16931VATCategory input.input-field');

      // Click to open the dropdown
      await fieldInput.click();

      // Wait for dropdown list to appear (no bare sleep — waitFor is the anchor)
      const dropdownList = page.locator('.input-dropdown-list');
      await dropdownList.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // Select target by stable data-testid — language-independent
      const targetOption = page.locator(`[data-testid="${targetCategory.testid}"]`);
      const optCount = await targetOption.count();
      expect(optCount, `Option ${targetCategory.testid} must be present in dropdown`).toBeGreaterThan(0);

      await targetOption.click();

      // Wait for dropdown to close
      await dropdownList.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Press Tab to confirm selection and trigger auto-save
      await page.keyboard.press('Tab');

      console.log(`[INFO] Selected ${targetKey} (${targetCategory.label})`);
    });

    // === STEP 9: Wait for auto-save ===
    await test.step('Wait for auto-save to complete', async () => {
      await page.locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      console.log('[INFO] Auto-save complete');
    });

    // === STEP 10: Read back via WebAPI and assert persisted value is the target ===
    await test.step(`Read back via WebAPI — assert persisted value is ${targetKey}`, async () => {
      const fieldData = await getFieldData(
        String(TAX_WINDOW_ID),
        String(TEST_TAX_RECORD_ID),
        'EN16931VATCategory'
      );

      console.log('[INFO] WebAPI field data (after change):', JSON.stringify(fieldData));
      // For List widgets the API returns value as an object { key: "AE", caption: "..." }
      const persistedKey = String(fieldData.value?.key ?? fieldData.value ?? '');
      allure.parameter('Persisted Value Key (WebAPI)', persistedKey);

      expect(
        persistedKey,
        `WebAPI must return the persisted value key ${targetKey}`
      ).toBe(targetKey);

      console.log(`[PASS] Persisted value via WebAPI is ${targetKey} (${targetCategory.label})`);
    });

    const screenshotAfterChange = await page.screenshot();
    allure.attachment(`Tax Record — After Setting ${targetKey}`, screenshotAfterChange, 'image/png');

    // === STEP 11: Restore original value ===
    await test.step(`Restore original value ${originalValueKey}`, async () => {
      const fieldInput = page.locator('.form-field-EN16931VATCategory input.input-field');
      await fieldInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await fieldInput.click();

      const dropdownList = page.locator('.input-dropdown-list');
      await dropdownList.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const originalCategory = VAT_CATEGORY[originalValueKey];
      const origOption = page.locator(`[data-testid="${originalCategory.testid}"]`);
      await origOption.click();

      await dropdownList.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page.keyboard.press('Tab');

      await page.locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // Verify restored via WebAPI (authoritative)
      const fieldDataRestored = await getFieldData(
        String(TAX_WINDOW_ID),
        String(TEST_TAX_RECORD_ID),
        'EN16931VATCategory'
      );
      const restoredKey = String(fieldDataRestored.value?.key ?? fieldDataRestored.value ?? '');
      expect(restoredKey, `Restored value must be ${originalValueKey}`).toBe(originalValueKey);
      console.log(`[PASS] Value restored to ${originalValueKey}`);
    });

    const screenshotFinal = await page.screenshot();
    allure.attachment('Tax Record — Final State (Restored)', screenshotFinal, 'image/png');

    console.log('[INFO] Test completed successfully');
  });
});
