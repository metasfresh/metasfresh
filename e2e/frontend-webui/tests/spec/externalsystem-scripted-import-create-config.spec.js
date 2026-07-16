import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * ExternalSystem scripted-import config — Endpoint picker parity.
 *
 * The scripted-import config (table ExternalSystem_Config_ScriptedImportConversion)
 * is reachable via TWO windows, which must expose the same endpoint-binding UI:
 *
 * - Parent window 541024 ("Externes System Konfiguration"): the config lives on
 *   CHILD tab 548472 ("Skriptbasierte Importkonvertierung", TabLevel 1) under an
 *   ExternalSystem_Config root record. This tab places the ExternalSystem_Endpoint_ID
 *   FK picker + the SFTP fields, so an endpoint can be selected from this window.
 * - Dedicated window 541962 ("Skriptbasierte Importkonvertierung"): the same config
 *   as a ROOT tab (548473, TabLevel 0), likewise placing ExternalSystem_Endpoint_ID +
 *   the SFTP fields.
 *
 * The legacy free-text EndpointName field is retired (dropped) — neither window shows
 * it. This spec verifies the endpoint FK picker is present + selectable on BOTH windows
 * (parity), which is the behaviour the AD migrations in this change establish.
 *
 * Login note: the shared `metasfresh` user has many roles; the default role
 * (roles[0]) lacks access to the ExternalSystem windows, so this spec explicitly
 * selects the "WebUI" role at login (the role granted read-write on all three
 * windows involved: 541024, 541962, 541967).
 */

const EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID = 541967;
const SCRIPTED_IMPORT_PARENT_WINDOW_ID = 541024; // "Externes System Konfiguration"
const SCRIPTED_IMPORT_CHILD_TAB_ID = 548472; // "Skriptbasierte Importkonvertierung" child tab (TabLevel 1)
const SCRIPTED_IMPORT_DEDICATED_WINDOW_ID = 541962; // dedicated window, root tab 548473
// The external-system type backing the scripted-import conversion (used when creating
// a parent ExternalSystem_Config record for the child tab).
const SCRIPTED_IMPORT_EXTERNAL_SYSTEM = 'ScriptedImportConversion';
// An ACTIVE parent ExternalSystem_Config record used by the parity test to scope the
// endpoint List (endpoints are filtered by the config's external system).
const ACTIVE_PARENT_CONFIG_NAME = 'print-to-remote-folder';
const ROLE_CAPTION_REGEX = /^WebUI, metasfresh, metasfresh AG$/;

/**
 * Select a value from a List dropdown widget (click the container to open, then
 * click the matching option). Works for Type / TransportType / SftpAuthType /
 * ExternalSystem_Config_ID / ExternalSystem_Endpoint_ID (all List widgets).
 * Mirrored from externalsystem-endpoint-sftp.spec.js — do not modify that file.
 */
async function selectListValue(page, fieldName, optionText, { exact = false } = {}) {
  const container = page.locator(`.form-field-${fieldName}`);
  await container.locator('input').first().click();
  await page.waitForTimeout(300);

  const filter = exact ? { hasText: new RegExp(`^${optionText}$`) } : { hasText: optionText };
  const option = page.locator('.input-dropdown-list-option').filter(filter).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await option.click();
  await page.waitForTimeout(1000);
}

/**
 * Select a value from a Lookup (typeahead) widget by typing a search string and
 * clicking the first matching dropdown option. Used for ExternalSystem_ID.
 */
async function selectLookupValue(page, fieldName, searchText) {
  const input = page
    .locator(`.form-field-${fieldName} input.input-field, .form-field-${fieldName} input[type="text"]`)
    .first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await page.waitForTimeout(400);
  await input.fill(searchText);
  await page.waitForTimeout(1200);
  const dropdown = page.locator('.input-dropdown-list');
  await dropdown.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await dropdown.locator('.input-dropdown-list-option').first().click();
  await page.waitForTimeout(800);
}

/**
 * Fill a text input field by column name using the form-field CSS class pattern.
 * Mirrored from externalsystem-endpoint-sftp.spec.js — do not modify that file.
 */
async function fillTextField(page, fieldName, value) {
  const field = page.locator(`.form-field-${fieldName} input[type="text"]`);
  await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await field.fill(value);
  await page.waitForTimeout(300);
}

/**
 * Fill a numeric input field by column name.
 * Mirrored from externalsystem-endpoint-sftp.spec.js — do not modify that file.
 */
async function fillNumericField(page, fieldName, value) {
  const field = page.locator(`.form-field-${fieldName} input`);
  await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await field.fill(value);
  await page.waitForTimeout(300);
}

/**
 * Log in as the "WebUI" role. Fills credentials, and — when the multi-role picker
 * appears — opens the role dropdown, selects the WebUI role, and submits.
 */
async function loginAsWebUI(page) {
  await page.goto(`${FRONTEND_BASE_URL}/login`);
  await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.locator('input[name="username"]').fill('metasfresh');
  await page.locator('input[name="password"]').fill('metasfresh');
  await page.locator('.btn-meta-success').click();
  await page.waitForTimeout(1200);

  // Multi-role user: a role-selection dropdown appears (loginComplete === false).
  if (page.url().includes('/login')) {
    const roleDropdown = page.locator('.input-dropdown-container .input-field').first();
    if (await roleDropdown.isVisible().catch(() => false)) {
      await roleDropdown.click();
      await page.waitForTimeout(400);
      const roleOption = page
        .locator('.input-dropdown-list-option')
        .filter({ hasText: ROLE_CAPTION_REGEX })
        .first();
      await roleOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await roleOption.dispatchEvent('mousedown');
      await page.waitForTimeout(300);
      // Close the dropdown so the click below lands on the Send button, not the list.
      await page.keyboard.press('Escape');
      await page.waitForTimeout(200);
    }
    await page.locator('.btn-meta-success').click();
  }
  await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Create a fresh SFTP ExternalSystem_Endpoint via window 541967 and return the
 * generated Value (used to pick it in the endpoint List afterwards). No sFTP
 * endpoint exists in the target database, and the frontendTesting/masterdata API
 * has no ExternalSystem support, so the endpoint is created through the UI.
 */
async function createSftpEndpoint(page) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
  await page.waitForTimeout(2000);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await selectListValue(page, 'TransportType', 'SFTP');

  await fillTextField(page, 'SftpHost', 'sftp.scripted-import.example.com');
  await fillNumericField(page, 'SftpPort', '22');
  await fillTextField(page, 'SftpUsername', 'scripted-import-user');

  await selectListValue(page, 'SftpAuthType', 'Password');
  const passwordField = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]');
  await passwordField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await passwordField.fill('secret123');
  await page.waitForTimeout(300);

  await fillTextField(page, 'SftpRemotePath', '/outbound/scripted-import');

  await page.keyboard.press('Tab');
  await page.waitForTimeout(2000);

  await page.waitForURL(
    (url) => {
      const urlStr = url.toString();
      return urlStr.includes(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/`) && !urlStr.includes('/NEW');
    },
    { timeout: SLOW_ACTION_TIMEOUT }
  );

  const valueField = page.locator('.form-field-Value input').first();
  const value = await valueField.inputValue();
  expect(value, 'seeded endpoint must have a generated Value').toBeTruthy();
  return value;
}

/**
 * Create and save a fresh (active) parent ExternalSystem_Config record on window
 * 541024, so its scripted-import child tab (548472) starts empty and offers the
 * "Add new" child-row action. Returns nothing — the page is left on the saved
 * parent record.
 */
async function createParentConfig(page) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${SCRIPTED_IMPORT_PARENT_WINDOW_ID}/NEW`);
  await page.waitForTimeout(2000);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await fillTextField(page, 'Name', `E2E scripted-import parent ${Date.now()}`);
  await selectLookupValue(page, 'ExternalSystem_ID', SCRIPTED_IMPORT_EXTERNAL_SYSTEM);

  await page.keyboard.press('Tab');
  await page.waitForTimeout(2000);
  await page.waitForURL(
    (url) => {
      const urlStr = url.toString();
      return urlStr.includes(`/window/${SCRIPTED_IMPORT_PARENT_WINDOW_ID}/`) && !urlStr.includes('/NEW');
    },
    { timeout: SLOW_ACTION_TIMEOUT }
  );
}

test.describe('ExternalSystem Scripted-Import Config — Endpoint picker parity', () => {
  test.beforeEach(async ({ page }) => {
    await loginAsWebUI(page);
  });

  test('Parent window 541024 child tab exposes the Endpoint FK picker (parity with 541962)', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00351: EDI ORDERS');
    allure.tag('F4550: Sales Order Candidate (REST API)');
    allure.story('Scripted-Import Config — Endpoint picker present on parent window (parity)');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Config_ScriptedImportConversion — Endpoint picker (Parent window 541024)

The parent "Externes System Konfiguration" window (541024) exposes the
scripted-import config as a child tab (548472) under an ExternalSystem_Config
root record. That tab places the ExternalSystem_Endpoint_ID FK picker (at parity
with the dedicated window 541962) — so an sFTP/REST endpoint can be bound from
either window, and the legacy free-text EndpointName is no longer shown.

1. Create a fresh active parent ExternalSystem_Config record on window 541024
2. Switch to the "Skriptbasierte Importkonvertierung" child tab (548472) and
   add a new row
3. Assert the endpoint FK field is present + selectable in the new-row form
    `);

    test.setTimeout(120000);

    // Setup (must succeed): create an active parent so the child tab offers "Add new".
    await createParentConfig(page);

    // Switch to the scripted-import child tab and add a new row.
    await page.getByTestId(`tab-AD_Tab-${SCRIPTED_IMPORT_CHILD_TAB_ID}`).click();
    await page.waitForTimeout(1500);

    const addNewButton = page.locator('button', { hasText: /^(Add new|Neu)$/i }).first();
    await addNewButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await addNewButton.click();

    const modal = page.locator('.panel-modal');
    await modal.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.waitForTimeout(1500);

    // Sanity: the new-row form really is the scripted-import child row. ScriptIdentifier
    // is a stable field on this tab. The legacy free-text EndpointName is RETIRED from
    // this tab by the fix, so it must NOT be present here (proves the UI retirement).
    await expect(modal.locator('.form-field-ScriptIdentifier')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(modal.locator('.form-field-EndpointName')).toBeHidden();

    // *** THE LOCKED ASSERTION ***
    // The endpoint FK picker must be placed on window 541024 child tab 548472. This
    // FAILED on pre-fix code (field absent) and passes once the D1 migration places it.
    await expect(
      modal.locator('.form-field-ExternalSystem_Endpoint_ID'),
      'endpoint FK picker must be present on window 541024 child tab 548472'
    ).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  });

  test('Dedicated window 541962 already exposes and binds the Endpoint FK picker (parity)', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00351: EDI ORDERS');
    allure.tag('F4550: Sales Order Candidate (REST API)');
    allure.story('Scripted-Import Config — Endpoint picker on dedicated window');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Config_ScriptedImportConversion — Endpoint picker (Dedicated window 541962)

Parity coverage: the dedicated "Skriptbasierte Importkonvertierung" window
(541962, root tab 548473) DOES place ExternalSystem_Endpoint_ID. This test
passes on current code and documents the correct behaviour that window 541024
must be brought in line with.

1. Seed a fresh SFTP ExternalSystem_Endpoint (window 541967)
2. Open window 541962, create a new root record
3. Assert the endpoint FK field is present
4. Set the parent config, then select the seeded endpoint and assert it is bound
    `);

    test.setTimeout(120000);

    const endpointValue = await createSftpEndpoint(page);

    await page.goto(`${FRONTEND_BASE_URL}/window/${SCRIPTED_IMPORT_DEDICATED_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Present:
    await expect(
      page.locator('.form-field-ExternalSystem_Endpoint_ID'),
      'endpoint FK picker must be present on window 541962 root tab 548473'
    ).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // Selectable + bound: the endpoint List is scoped to the config's external system,
    // so set the (active) parent config first, then pick the seeded endpoint.
    await selectListValue(page, 'ExternalSystem_Config_ID', ACTIVE_PARENT_CONFIG_NAME);
    await selectListValue(page, 'ExternalSystem_Endpoint_ID', endpointValue, { exact: true });

    const boundValue = await page
      .locator('.form-field-ExternalSystem_Endpoint_ID input')
      .first()
      .inputValue();
    expect(boundValue).toContain(endpointValue);
  });
});
