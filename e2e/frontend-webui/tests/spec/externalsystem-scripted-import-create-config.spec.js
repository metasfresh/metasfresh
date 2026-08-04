import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * ExternalSystem scripted-import config — create + SAVE a config that BINDS an endpoint.
 *
 * The scripted-import config (table ExternalSystem_Config_ScriptedImportConversion)
 * is reachable via TWO windows, which must both let an operator create a config and
 * bind an ExternalSystem_Endpoint to it:
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
 * it. This spec drives the FULL create flow on BOTH windows: fill every mandatory
 * field (Suchschlüssel / ExternalSystemValue, Skript-Kennung / ScriptIdentifier,
 * Benutzerimport / AD_User_Import_ID) AND select an endpoint, SAVE the record, then
 * RELOAD from the server and assert the endpoint is persisted (bound) on the saved
 * config — i.e. it proves a real, persisted create-with-endpoint, not just that the
 * picker is visible.
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
// An ACTIVE parent ExternalSystem_Config record used by the dedicated-window test to
// set the config on the root record before binding the endpoint.
const ACTIVE_PARENT_CONFIG_NAME = 'print-to-remote-folder';
// Search term for the mandatory "Benutzerimport" (AD_User_Import_ID) field. The lookup is filtered to
// users that can act as an import user (a valid WebUI auth token) — verified against the live stack, it
// offers only the "metasfresh" login user (the seeded "…, Automatik-Benutzer" system users are NOT
// offered on the faithful/seed DB → searching for them returns "No results found." and the mandatory
// field stayed empty). "metasfresh" is the stable, always-present token-holding user on both the local
// faithful DB and the CI preloaded seed.
const IMPORT_USER_SEARCH = 'metasfresh';
const ROLE_CAPTION_REGEX = /^WebUI, metasfresh, metasfresh AG$/;

/** Escape regex metacharacters so a server-generated value (doc sequence) can be
 * matched literally inside a toHaveValue(RegExp) substring assertion. */
function escapeRegExp(value) {
  return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

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

  const filter = exact ? { hasText: new RegExp(`^${escapeRegExp(optionText)}$`) } : { hasText: optionText };
  const option = page.locator('.input-dropdown-list-option').filter(filter).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await option.click();
  await page.waitForTimeout(1000);
}

/**
 * Select a value from a Lookup (typeahead) widget by typing a search string and
 * clicking the first matching dropdown option. Used for ExternalSystem_ID and the
 * mandatory Benutzerimport (AD_User_Import_ID) field.
 */
async function selectLookupValue(page, fieldName, searchText) {
  const input = page
    .locator(`.form-field-${fieldName} input.input-field, .form-field-${fieldName} input[type="text"]`)
    .first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await page.waitForTimeout(400);
  // Type char-by-char so the React typeahead fires its (debounced, backend-querying) search — a direct
  // .fill() sets the value without the keystroke events a user-lookup needs to populate the option list.
  await input.pressSequentially(searchText, { delay: 60 });
  await page.waitForTimeout(1500);
  const dropdown = page.locator('.input-dropdown-list');
  await dropdown.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // Click the option that actually MATCHES the search, not blindly the first row (which may be a
  // header / a non-matching entry) — the bare "first option" is what left the mandatory field unset.
  const matching = dropdown.locator('.input-dropdown-list-option').filter({ hasText: searchText });
  const target = (await matching.count()) > 0 ? matching.first() : dropdown.locator('.input-dropdown-list-option').first();
  await target.click();
  await page.waitForTimeout(1000);
}

/**
 * Fill a text input field by column name using the form-field CSS class pattern.
 * Mirrored from externalsystem-endpoint-sftp.spec.js — do not modify that file.
 */
async function fillTextField(page, fieldName, value) {
  const field = page.locator(`.form-field-${fieldName} input[type="text"]`);
  await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await field.fill(value);
  // Commit the edit: the WebUI PATCHes a field only on blur, so an uncommitted .fill() never persists.
  await field.press('Tab');
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
 * Fill the mandatory scripted-import config fields (other than the endpoint + the
 * parent config): Suchschlüssel (ExternalSystemValue), Skript-Kennung
 * (ScriptIdentifier) and Benutzerimport (AD_User_Import_ID). Only one such form is
 * open at a time (root form or the child-row "Add new" overlay), so the fields
 * resolve uniquely from the page.
 */
async function fillMandatoryConfigFields(page, externalSystemValue) {
  await fillTextField(page, 'ExternalSystemValue', externalSystemValue);
  await fillTextField(page, 'ScriptIdentifier', 'e2e-scripted-import');
  await selectLookupValue(page, 'AD_User_Import_ID', IMPORT_USER_SEARCH);
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
 * "Add new" child-row action. Returns the saved parent record id (from the URL).
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
  const parentId = page.url().split(`/window/${SCRIPTED_IMPORT_PARENT_WINDOW_ID}/`)[1].split(/[/?#]/)[0];
  expect(parentId, 'parent ExternalSystem_Config must have a persisted id').toMatch(/^\d+$/);
  return parentId;
}

test.describe('ExternalSystem Scripted-Import Config — create + bind endpoint (persisted)', () => {
  test.beforeEach(async ({ page }) => {
    await loginAsWebUI(page);
  });

  test('Parent window 541024 child tab: create + SAVE a config binding an endpoint (persisted)', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00351: EDI ORDERS');
    allure.tag('F00351');
    allure.tag('F4550: Sales Order Candidate (REST API)');
    allure.tag('F4550');
    allure.story('Scripted-Import Config — create + bind endpoint on parent window 541024 (persisted)');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Config_ScriptedImportConversion — create + bind endpoint (Parent window 541024)

The parent "Externes System Konfiguration" window (541024) exposes the
scripted-import config as a child tab (548472) under an ExternalSystem_Config
root record. That tab places the ExternalSystem_Endpoint_ID FK picker (at parity
with the dedicated window 541962), and the legacy free-text EndpointName is no
longer shown. This test creates and SAVES a real config from this window and
asserts the endpoint persists bound after a reload.

1. Seed a fresh SFTP ExternalSystem_Endpoint (window 541967)
2. Create a fresh active parent ExternalSystem_Config record on window 541024
3. Switch to the "Skriptbasierte Importkonvertierung" child tab (548472), add a
   new row, and confirm the endpoint FK picker is present and the legacy
   EndpointName is retired
4. Fill all mandatory fields (Suchschlüssel, Skript-Kennung, Benutzerimport) and
   select the seeded endpoint, then save the row (Done)
5. Reload the parent from the server, reopen the child row, and assert the seeded
   endpoint is still bound (persisted)
    `);

    test.setTimeout(120000);

    const modal = page.locator('.panel-modal');
    const externalSystemValue = `e2e-541024-${Date.now()}`;
    let endpointValue;
    let parentId;

    await test.step('Seed a fresh SFTP endpoint (window 541967)', async () => {
      endpointValue = await createSftpEndpoint(page);
    });

    await test.step('Create a scripted-import parent config on window 541024', async () => {
      parentId = await createParentConfig(page);
    });

    await test.step('Open the Skriptbasierte Importkonvertierung child tab and add a new row', async () => {
      await page.getByTestId(`tab-AD_Tab-${SCRIPTED_IMPORT_CHILD_TAB_ID}`).click();
      await page.waitForTimeout(1500);

      const addNewButton = page.locator('button', { hasText: /^(Add new|Neu)$/i }).first();
      await addNewButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await addNewButton.click();

      await modal.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(1500);
    });

    await test.step('Legacy free-text EndpointName is retired; the endpoint FK picker is present', async () => {
      // Sanity: the new-row form really is the scripted-import child row. ScriptIdentifier
      // is a stable field on this tab. The legacy free-text EndpointName is RETIRED from
      // this tab by the fix, so it must NOT be present here (proves the UI retirement).
      await expect(modal.locator('.form-field-ScriptIdentifier')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expect(modal.locator('.form-field-EndpointName')).toBeHidden();
      // The endpoint FK picker must be placed on window 541024 child tab 548472. This
      // FAILED on pre-fix code (field absent) and passes once the D1 migration places it.
      await expect(
        modal.locator('.form-field-ExternalSystem_Endpoint_ID'),
        'endpoint FK picker must be present on window 541024 child tab 548472'
      ).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Fill all mandatory fields, bind the seeded endpoint, and save the row', async () => {
      await selectListValue(page, 'ExternalSystem_Endpoint_ID', endpointValue, { exact: true });
      await fillMandatoryConfigFields(page, externalSystemValue);

      // The endpoint must be bound in the (still-open) modal before saving.
      const boundInModal = await modal
        .locator('.form-field-ExternalSystem_Endpoint_ID input')
        .first()
        .inputValue();
      expect(boundInModal, 'seeded endpoint must be bound in the child-row form before save').toContain(endpointValue);

      // The mandatory Import User must actually be SELECTED in the modal before saving — a lookup
      // selection that never landed is the defect this test now guards (the row would save invalid,
      // "Fill mandatory fields: Import User", while a bound-endpoint-only check stayed green).
      const importUserInModal = await modal
        .locator('.form-field-AD_User_Import_ID input')
        .first()
        .inputValue();
      expect(importUserInModal, 'Import User must be selected in the child-row form before save').not.toBe('');

      // Save/commit the child row. The WebUI auto-saves each field via PATCH; the modal's
      // Done button commits and closes the "Add new" overlay. Select it by its stable
      // data-testid (Modal.js) — never the localized caption (language-independence rule).
      const doneButton = modal.getByTestId('process-modal-cancel-button').first();
      await doneButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await doneButton.click();
      await page.waitForTimeout(2000);
    });

    await test.step('Reload the parent and assert the endpoint persisted bound on the saved child row', async () => {
      // Reload from the server (defeats the client-side document cache), then reopen the
      // child row and re-read the bound endpoint — this is the persisted-save proof.
      await page.goto(`${FRONTEND_BASE_URL}/window/${SCRIPTED_IMPORT_PARENT_WINDOW_ID}/${parentId}`);
      await page.waitForTimeout(2500);
      await page.getByTestId(`tab-AD_Tab-${SCRIPTED_IMPORT_CHILD_TAB_ID}`).click();
      await page.waitForTimeout(2500);

      const childRow = page.locator('.table-flex-wrapper tbody tr').first();
      await expect(childRow, 'the saved scripted-import child row must survive a reload').toBeVisible({
        timeout: SLOW_ACTION_TIMEOUT,
      });
      // The child grid's "Endpunkt" column shows the bound endpoint on the persisted row.
      await expect(
        childRow,
        'the reloaded child grid row must show the bound endpoint (persisted)'
      ).toContainText(endpointValue, { timeout: SLOW_ACTION_TIMEOUT });

      // OUTCOME check — the create+save produced a VALID, complete record, not just a bound endpoint.
      // The "Fill mandatory fields" banner appears (and survives reload) whenever a mandatory field
      // (e.g. Import User) did not persist; its absence proves the whole record saved. This is the
      // assertion a bound-endpoint-only check lacked (metasfresh-test-integrity: assert the OUTCOME).
      await expect(
        page.getByText(/Fill mandatory fields/i),
        'no mandatory-field error may remain after reload — the saved config must be valid/complete'
      ).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });

      // Reopen the row into its detail form and re-read the bound endpoint — the explicit
      // persisted-save proof (mirrors the reference save test's re-read).
      // Double-click the "Endpunkt" cell specifically (not the row centre): this grid now shows
      // several editable columns, and a double-click on an editable cell starts inline edit
      // (TableCell.onDoubleClick) instead of opening the record — only a non-editable cell lets the
      // event bubble to TableRow → open. The endpoint FK cell is read-only in the grid, so it
      // reliably opens the detail form regardless of column order/count. It's the cell showing the
      // bound endpoint value.
      const endpointCell = childRow.locator('td').filter({ hasText: endpointValue }).first();
      await endpointCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await endpointCell.dblclick();
      const reopenedEndpoint = page.locator('.form-field-ExternalSystem_Endpoint_ID input').first();
      await expect(
        reopenedEndpoint,
        'the seeded endpoint must be persisted (bound) on the saved config after reload'
      ).toHaveValue(new RegExp(escapeRegExp(endpointValue)), { timeout: SLOW_ACTION_TIMEOUT });
    });
  });

  test('Dedicated window 541962: create + SAVE a config binding an endpoint (persisted)', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00351: EDI ORDERS');
    allure.tag('F00351');
    allure.tag('F4550: Sales Order Candidate (REST API)');
    allure.tag('F4550');
    allure.story('Scripted-Import Config — create + bind endpoint on dedicated window 541962 (persisted)');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Config_ScriptedImportConversion — create + bind endpoint (Dedicated window 541962)

Parity coverage: the dedicated "Skriptbasierte Importkonvertierung" window
(541962, root tab 548473) places ExternalSystem_Endpoint_ID. This test creates
and SAVES a real config on that window and asserts the endpoint persists bound
after a reload.

1. Seed a fresh SFTP ExternalSystem_Endpoint (window 541967)
2. Open window 541962, create a new root record; confirm the endpoint FK picker
   is present
3. Set the parent config, select the seeded endpoint, and fill all mandatory
   fields (Suchschlüssel, Skript-Kennung, Benutzerimport)
4. Save the record (URL changes from /NEW to a record id)
5. Reload from the server and assert the seeded endpoint is still bound (persisted)
    `);

    test.setTimeout(120000);

    const externalSystemValue = `e2e-541962-${Date.now()}`;
    let endpointValue;

    await test.step('Seed a fresh SFTP endpoint (window 541967)', async () => {
      endpointValue = await createSftpEndpoint(page);
    });

    await test.step('Open the dedicated scripted-import window 541962 (new record)', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${SCRIPTED_IMPORT_DEDICATED_WINDOW_ID}/NEW`);
      await page.waitForTimeout(2000);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('The "Endpunkt" endpoint FK picker is present on window 541962', async () => {
      await expect(
        page.locator('.form-field-ExternalSystem_Endpoint_ID'),
        'endpoint FK picker must be present on window 541962 root tab 548473'
      ).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Set the parent config, bind the seeded endpoint, and fill all mandatory fields', async () => {
      await selectListValue(page, 'ExternalSystem_Config_ID', ACTIVE_PARENT_CONFIG_NAME);
      await selectListValue(page, 'ExternalSystem_Endpoint_ID', endpointValue, { exact: true });
      await fillMandatoryConfigFields(page, externalSystemValue);

      // Import User must actually be selected before saving (same guard as the parent-window test).
      const importUserSet = await page.locator('.form-field-AD_User_Import_ID input').first().inputValue();
      expect(importUserSet, 'Import User must be selected before save').not.toBe('');
    });

    await test.step('Save the record and assert the endpoint persisted bound after a reload', async () => {
      // Tab out to trigger the final field commit / save.
      await page.keyboard.press('Tab');
      await page.waitForTimeout(2000);

      // The record is created server-side: the URL changes from /NEW to a record id.
      await page.waitForURL(
        (url) => {
          const urlStr = url.toString();
          return urlStr.includes(`/window/${SCRIPTED_IMPORT_DEDICATED_WINDOW_ID}/`) && !urlStr.includes('/NEW');
        },
        { timeout: SLOW_ACTION_TIMEOUT }
      );

      // Reload from the server (defeats the client-side document cache) and re-read the
      // bound endpoint + Suchschlüssel — this is the persisted-save proof.
      await page.reload();
      await page.waitForTimeout(2500);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await expect(
        page.locator('.form-field-ExternalSystem_Endpoint_ID input').first(),
        'the seeded endpoint must be persisted (bound) on the saved config after reload'
      ).toHaveValue(new RegExp(escapeRegExp(endpointValue)), { timeout: SLOW_ACTION_TIMEOUT });
      await expect(
        page.locator('.form-field-ExternalSystemValue input').first(),
        'the Suchschlüssel (ExternalSystemValue) must be persisted after reload'
      ).toHaveValue(externalSystemValue, { timeout: SLOW_ACTION_TIMEOUT });

      // OUTCOME: no mandatory-field error remains after reload — the record saved valid/complete.
      await expect(
        page.getByText(/Fill mandatory fields/i),
        'no mandatory-field error may remain after reload — the saved config must be valid/complete'
      ).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    });
  });
});
