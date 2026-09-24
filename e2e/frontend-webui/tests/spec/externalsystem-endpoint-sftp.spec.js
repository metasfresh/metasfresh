import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { assertRecordIsValid, getRecordData, getValidationStatus } from '../utils/WebAPIValidation';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import * as path from 'node:path';
import * as fs from 'node:fs';

/**
 * ExternalSystem_Endpoint — Transport Type E2E test suite.
 *
 * Tests the TransportType field display/mandatory logic on the
 * ExternalSystem_Endpoint window (AD_Window_ID=541967), across the HTTP, SFTP
 * and LOCAL_FILE transports:
 *
 * 1. TransportType=HTTP -> SFTP fields hidden, HTTP fields visible; and vice versa
 * 2. SftpAuthType=PASSWORD -> Password field visible; SftpAuthType=SSH_KEY -> SshPrivateKey field visible
 * 3. Create and save a full SFTP endpoint configuration
 * 4. AuthType=OAuth2 -> OAuth2 token URL + scope + credential fields visible
 * 5. Create and save a full OAuth2 HTTP endpoint configuration
 * 6. AuthType=OAuth (v1) -> the Password field is shown, and the endpoint saves without one
 * 7. TransportType=LOCAL_FILE -> root location, polling interval and filename pattern visible; hidden for HTTP/SFTP
 * 8. LOCAL_FILE root location is mandatory -> the endpoint stays invalid/unsaved until it is filled
 * 9. Switching transport away and back (LOCAL_FILE <-> SFTP) leaves no foreign transport values behind
 * 10. Switching HTTP + Basic -> SFTP + PASSWORD keeps the password, because both configurations show it
 * 11. The LOCAL_FILE field labels render in German and in English
 */

const EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID = 541967;

/**
 * The AuthType AD_Ref_List value for OAuth version 1 — "OAuth" and "OAuth2" are two distinct values
 * of that list, so naming this one keeps the scenario from being read as the OAuth2 one.
 */
const OAUTH_V1 = 'OAuth';

/**
 * The WebUI's stored save state — `pending` from just before a field's PATCH goes out until just
 * after its response is merged — unaffected by the document's validity, unlike the save bar's
 * colour, which reads `error` throughout while a persisted record is invalid.
 *
 * It is one slot, not one per request: a view fetch (DocumentListContainer.getData) drives it too,
 * and the draft-creating PATCH .../NEW never sets it at all. That is harmless only because this
 * spec opens the window directly on /NEW, never through a grid, and awaits that response
 * separately — a spec that reaches the window through its view must not reuse this wait.
 */
const SAVE_SETTLED = '.window-indicator-container[data-save-state="saved"]';

/**
 * The response to the PATCH the WebUI issues for `fieldName` on this window's document.
 *
 * The listener has to be armed BEFORE the action that commits the field, or a fast response is
 * missed and the wait runs into its timeout. The payload filter (`"path":"<ColumnName>"`) keeps it
 * from settling on a different field's PATCH, and skips the empty-payload PATCH .../NEW that only
 * creates the draft.
 */
function endpointFieldPatch(page, fieldName) {
  return page.waitForResponse(
    (response) =>
      response.request().method() === 'PATCH' &&
      response.url().includes(`/rest/api/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/`) &&
      (response.request().postData() || '').includes(`"path":"${fieldName}"`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
}

/**
 * Run `commit` — the action that makes the WebUI send `fieldName`'s value — and return once the
 * server has answered and the answer has been merged into the form: the response is awaited first,
 * and the field PATCH returns {@link SAVE_SETTLED}'s state to `saved` only after merging it.
 * A value typed while the previous response is still in flight is re-rendered away before React sees
 * it and is then never patched at all, so no field is entered until the one before it is through.
 */
async function commitField(page, fieldName, commit) {
  const patched = endpointFieldPatch(page, fieldName);
  await commit();
  const response = await patched;
  expect(response.ok(), `the WebUI's PATCH of ${fieldName} was rejected: HTTP ${response.status()}`).toBe(true);
  await page.locator(SAVE_SETTLED).waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Open the window on a brand-new record and wait until its draft document exists.
 *
 * Opening `/NEW` makes the WebUI PATCH `.../NEW` with an empty payload; that response is what carries
 * the draft's id and its field data. Typing before it lands would patch a document the form does not
 * have yet.
 */
async function openNewEndpoint(page) {
  const draftCreated = page.waitForResponse(
    (response) =>
      response.request().method() === 'PATCH' &&
      response.url().includes(`/rest/api/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
  await draftCreated;
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * The record id the WebUI put in the URL once the draft was given one. A NEW record is given one
 * even while it is invalid, so the id alone does not prove the row persisted — assert that via the
 * WebAPI.
 */
async function savedRecordId(page) {
  await page.waitForURL(
    (url) => {
      const urlStr = url.toString();
      return urlStr.includes(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/`) && !urlStr.includes('/NEW');
    },
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  return page.url().match(new RegExp(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/(\\d+)`))[1];
}

/**
 * Select a value from a List dropdown by its AD_Ref_List **Value**: the input is readonly, so click
 * the container to open the list, then the option carrying that Value as its test id. The rendered
 * caption is unusable — it is localized, and a server in developer mode prefixes it with `<Value>_`.
 */
async function selectListValue(page, fieldName, optionValue) {
  const container = page.locator(`.form-field-${fieldName}`);
  await container.locator('input').click();

  const option = page.getByTestId(`option-${optionValue}`);
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await commitField(page, fieldName, () => option.click());
}

/**
 * Directory the per-transport stills are written to, on top of being attached to the Allure
 * report. Left unset (the default, and always so in CI) only the Allure attachment is produced;
 * point it at a directory to also collect the stills as files for a review.
 */
const STILLS_DIR = process.env.WINDOW_EVIDENCE_DIR || '';

/**
 * Capture the rendered window as a still: always attached to the Allure report, and additionally
 * written to STILLS_DIR when that is configured.
 */
async function saveStill(page, filename) {
  const buffer = await page.screenshot({ fullPage: false });
  allure.attachment(filename, buffer, 'image/png');

  if (STILLS_DIR) {
    fs.mkdirSync(STILLS_DIR, { recursive: true });
    fs.writeFileSync(path.join(STILLS_DIR, filename), buffer);
  }
}

/**
 * True when a WebAPI field value means "not set", i.e. null, absent, an empty string or a lookup with
 * no `key`; anything else does not count as cleared.
 *
 * Ask it only about a column with no `AD_Column.DefaultValue`. Hiding such a column is what clears it
 * to SQL NULL; hiding one that HAS a default puts that default back instead, which is an equality
 * check against the value, not this.
 */
function emptyish(value) {
  if (value === null || value === undefined || value === '') {
    return true;
  }
  if (typeof value === 'object') {
    return value.key === null || value.key === undefined || value.key === '';
  }
  return false;
}

/**
 * Type `value` into `field` and hand it to the server.
 *
 * `fill()` only dispatches `input`, which these widgets answer with a local state update; the PATCH
 * comes from the blur. {@link commitField} wraps both so the response wait is armed before either
 * can fire.
 */
async function fillFieldLocator(page, fieldName, field, value) {
  await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await commitField(page, fieldName, async () => {
    await field.fill(value);
    await field.blur();
  });
}

/**
 * Fill a text input field by column name using the form-field CSS class pattern.
 */
async function fillTextField(page, fieldName, value) {
  return fillFieldLocator(page, fieldName, page.locator(`.form-field-${fieldName} input[type="text"]`), value);
}

/**
 * Fill a numeric input field by column name.
 */
async function fillNumericField(page, fieldName, value) {
  return fillFieldLocator(page, fieldName, page.locator(`.form-field-${fieldName} input`), value);
}

/**
 * Fill the Password field. It renders as a text or a password input depending on the widget's
 * reveal state, so both are accepted.
 */
async function fillPasswordField(page, value) {
  const field = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]');
  return fillFieldLocator(page, 'Password', field, value);
}

/**
 * Fill the SSH private key. It renders as a textarea or an input depending on the widget type.
 */
async function fillSshPrivateKeyField(page, value) {
  const field = page.locator('.form-field-SshPrivateKey textarea, .form-field-SshPrivateKey input');
  return fillFieldLocator(page, 'SshPrivateKey', field, value);
}

test.describe('ExternalSystem Endpoint — SFTP Transport', () => {
  test.beforeEach(async ({ page }) => {
    // Of the roles this login offers, only "WebUI" has read-write access to the
    // ExternalSystem_Endpoint window (541967) — pick it explicitly rather than relying on the
    // dropdown's default ordering.
    await page.goto(`${FRONTEND_BASE_URL}/login`);
    await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('input[name="username"]').fill('metasfresh');
    await page.locator('input[name="password"]').fill('metasfresh');
    await page.locator('.btn-meta-success').click();

    // Either the login is through, or the server came back asking which role to use. Wait for
    // whichever of the two actually happens instead of guessing how long the round-trip takes.
    await page.waitForFunction(
      () =>
        !window.location.href.includes('/login') ||
        !!document.querySelector('.input-dropdown-container .input-field'),
      null,
      { timeout: SLOW_ACTION_TIMEOUT }
    );

    if (page.url().includes('/login')) {
      const roleDropdown = page.locator('.input-dropdown-container .input-field').first();
      if (await roleDropdown.isVisible().catch(() => false)) {
        await roleDropdown.click();
        const roleOption = page
          .locator('.input-dropdown-list-option')
          .filter({ hasText: /^WebUI, metasfresh, metasfresh AG$/ })
          .first();
        await roleOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await roleOption.dispatchEvent('mousedown');
        await page.keyboard.press('Escape');
        // The option list is gone once the pick has been taken and the dropdown closed.
        await page.locator('.input-dropdown-list-option').first().waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
      }
      await page.locator('.btn-meta-success').click();
    }
    await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
  });

  test('TransportType field visibility toggles SFTP/HTTP fields', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('TransportType field display logic');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — TransportType Display Logic

Tests that selecting TransportType=SFTP shows SFTP-specific fields
and hides HTTP fields, and vice versa.

1. Navigate to ExternalSystem_Endpoint window
2. Create a new record
3. Set TransportType=SFTP -> verify SFTP fields visible, HTTP fields hidden
4. Set TransportType=HTTP -> verify HTTP fields visible, SFTP fields hidden
    `);

    test.setTimeout(120000);

    // Navigate to the ExternalSystem_Endpoint window and create a new record
    await openNewEndpoint(page);

    // --- Test 1: Set TransportType = SFTP ---
    await selectListValue(page, 'TransportType', 'SFTP');

    // Verify SFTP fields are now visible
    await expect(page.locator('.form-field-SftpHost input[type="text"]')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(page.locator('.form-field-SftpPort input')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-SftpUsername input[type="text"]')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-SftpAuthType')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-SftpRemotePath input[type="text"]')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-SftpFilenamePattern input[type="text"]')).toBeVisible({ timeout: 5000 });

    // HTTP-only fields should be hidden when TransportType=SFTP
    await expect(page.locator('.form-field-ContentType')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-HttpEndPoint')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-OutboundHttpMethod')).toBeHidden({ timeout: 3000 });

    // --- Test 2: Set TransportType = HTTP ---
    await selectListValue(page, 'TransportType', 'HTTP');

    // HTTP fields should be visible
    await expect(page.locator('.form-field-ContentType')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(page.locator('.form-field-HttpEndPoint')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-OutboundHttpMethod')).toBeVisible({ timeout: 5000 });

    // SFTP fields should be hidden
    await expect(page.locator('.form-field-SftpHost')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-SftpPort')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-SftpUsername')).toBeHidden({ timeout: 3000 });
  });

  test('SftpAuthType toggles Password vs SSH key fields', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('SftpAuthType field display logic');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — SftpAuthType Display Logic

Tests that SftpAuthType=PASSWORD shows the Password field,
and SftpAuthType=SSH_KEY shows the SshPrivateKey field.
    `);

    test.setTimeout(120000);

    // Navigate and create new record
    await openNewEndpoint(page);

    // Set TransportType = SFTP first
    await selectListValue(page, 'TransportType', 'SFTP');

    // --- Test: Set SftpAuthType = PASSWORD ---
    await selectListValue(page, 'SftpAuthType', 'PASSWORD');

    // Password field should be visible
    const passwordField = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]');
    await expect(passwordField).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // SshPrivateKey should NOT be visible
    await expect(page.locator('.form-field-SshPrivateKey')).toBeHidden({ timeout: 3000 });

    // --- Test: Switch to SftpAuthType = SSH_KEY ---
    await selectListValue(page, 'SftpAuthType', 'SSH_KEY');

    // SshPrivateKey should now be visible
    await expect(page.locator('.form-field-SshPrivateKey textarea, .form-field-SshPrivateKey input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // Password should be hidden
    await expect(page.locator('.form-field-Password')).toBeHidden({ timeout: 3000 });
  });

  test('Create and save full SFTP endpoint configuration', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('Full SFTP Configuration Flow');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — Full SFTP Configuration

Creates a complete SFTP endpoint with all mandatory fields filled:
1. Navigate to window, create new record
2. Set Value, Type, TransportType=SFTP
3. Fill all SFTP fields (host, port, username, auth, password, remote path, filename)
4. Verify record saves successfully (URL changes from /NEW to record ID)
5. Verify saved field values persist
    `);

    test.setTimeout(120000);

    // Navigate and create new record
    await openNewEndpoint(page);

    // Value field is auto-generated by document sequence (IsUseDocSequence=Y) — skip it

    // Set TransportType = SFTP
    await selectListValue(page, 'TransportType', 'SFTP');

    // Fill mandatory SFTP fields
    await fillTextField(page, 'SftpHost', 'sftp.example.com');
    await fillNumericField(page, 'SftpPort', '22');
    await fillTextField(page, 'SftpUsername', 'testuser');

    // Set SftpAuthType = PASSWORD (mandatory when SFTP)
    await selectListValue(page, 'SftpAuthType', 'PASSWORD');

    // Fill password (mandatory when SFTP + SftpAuthType=PASSWORD)
    await fillPasswordField(page, 'secret123');

    // Fill remote path (mandatory when SFTP)
    await fillTextField(page, 'SftpRemotePath', '/outbound/edi');

    // Fill optional filename pattern
    await fillTextField(page, 'SftpFilenamePattern', 'export_{timestamp}.json');

    // The SFTP polling interval, plus the transport-agnostic archive directories.
    await fillNumericField(page, 'SftpPollingIntervalMs', '30000');
    await fillTextField(page, 'ProcessedDirectory', '/inbound/processed');
    await fillTextField(page, 'ErrorDirectory', '/inbound/error');

    const sftpRecordId = await savedRecordId(page);
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, sftpRecordId, 'after saving the SFTP endpoint');

    // Verify the saved field values are still present
    const sftpHostField = page.locator('.form-field-SftpHost input[type="text"]');
    const sftpUsernameField = page.locator('.form-field-SftpUsername input[type="text"]');
    const remotePathField = page.locator('.form-field-SftpRemotePath input[type="text"]');
    const filenamePatternField = page.locator('.form-field-SftpFilenamePattern input[type="text"]');

    await expect(sftpHostField).toHaveValue('sftp.example.com');
    await expect(sftpUsernameField).toHaveValue('testuser');
    await expect(remotePathField).toHaveValue('/outbound/edi');
    await expect(filenamePatternField).toHaveValue('export_{timestamp}.json');

    // The polling interval and the transport-agnostic archive directories persist too.
    // (integer field — tolerate any locale grouping separator between "30" and "000")
    await expect(page.locator('.form-field-SftpPollingIntervalMs input')).toHaveValue(/^30[.,\s ]?000$/);
    await expect(page.locator('.form-field-ProcessedDirectory input[type="text"]')).toHaveValue('/inbound/processed');
    await expect(page.locator('.form-field-ErrorDirectory input[type="text"]')).toHaveValue('/inbound/error');
  });

  test('AuthType=OAuth2 reveals OAuth2 token URL + scope + credential fields', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('AuthType OAuth2 display logic');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — AuthType=OAuth2 Display Logic

Tests that for an HTTP endpoint, selecting AuthType=OAuth2 reveals the OAuth2
token-endpoint URL + optional scope and the reused credential fields, and that
switching to a non-OAuth2 auth type (Token) hides the OAuth2-specific fields.

1. New record, TransportType=HTTP
2. AuthType=OAuth2 -> OAuthTokenUrl, OAuthScope, ClientId, LoginUsername, Password, IsFileUpload visible
3. AuthType=Token -> OAuthTokenUrl + OAuthScope hidden (AuthType-gated)
    `);

    test.setTimeout(120000);

    await openNewEndpoint(page);

    // HTTP transport, then OAuth2 auth
    await selectListValue(page, 'TransportType', 'HTTP');
    await selectListValue(page, 'AuthType', 'OAuth2');

    // OAuth2-specific fields appear
    await expect(page.locator('.form-field-OAuthTokenUrl input[type="text"]')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(page.locator('.form-field-OAuthScope input[type="text"]')).toBeVisible({ timeout: 5000 });

    // Reused credential fields show for OAuth2 (password grant needs client id + user + password)
    await expect(page.locator('.form-field-ClientId input[type="text"]')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-LoginUsername input[type="text"]')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-Password input')).toBeVisible({ timeout: 5000 });

    // IsFileUpload is HTTP-gated, so visible here
    await expect(page.locator('.form-field-IsFileUpload')).toBeVisible({ timeout: 5000 });

    // Switch to a non-OAuth2 HTTP auth type -> OAuth2 fields hide (proves AuthType-gating, not just HTTP-gating)
    await selectListValue(page, 'AuthType', 'Token');
    await expect(page.locator('.form-field-OAuthTokenUrl')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-OAuthScope')).toBeHidden({ timeout: 3000 });
  });

  test('Create and save full OAuth2 HTTP endpoint configuration', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('Full OAuth2 Configuration Flow');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — Full OAuth2 Configuration

Creates a complete HTTP + OAuth2 endpoint and verifies it saves (the mandatory
OAuthTokenUrl is accepted and the record persists).
    `);

    test.setTimeout(120000);

    await openNewEndpoint(page);

    // Value is auto-generated (IsUseDocSequence=Y) — skip it.
    // Type's AD_Field and AD_UI_Element are both inactive on this window; TransportType is the
    // single transport selector, so don't touch Type.
    await selectListValue(page, 'TransportType', 'HTTP');
    await selectListValue(page, 'AuthType', 'OAuth2');
    // OutboundHttpMethod is mandatory under HTTP and has NO default — must be set or the record stays invalid (never persists)
    await selectListValue(page, 'OutboundHttpMethod', 'POST');

    // HttpEndPoint is mandatory for HTTP.
    await fillTextField(page, 'HttpEndPoint', 'https://dw.example.com/DocuWare/Platform/FileCabinets/abc/Documents');

    // OAuth2 mandatory: token URL; plus the password-grant credentials
    await fillTextField(page, 'OAuthTokenUrl', 'https://dw.example.com/DocuWare/Platform/Identity/connect/token');
    await fillTextField(page, 'OAuthScope', 'docuware.platform');
    await fillTextField(page, 'ClientId', 'docuware.platform.net.client');
    await fillTextField(page, 'LoginUsername', 'svc-user');
    await fillPasswordField(page, 'svc-secret');

    const oauthRecordId = await savedRecordId(page);
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, oauthRecordId, 'after saving the OAuth2 HTTP endpoint');

    // Saved OAuth2 values persist
    await expect(page.locator('.form-field-OAuthTokenUrl input[type="text"]')).toHaveValue('https://dw.example.com/DocuWare/Platform/Identity/connect/token');
    await expect(page.locator('.form-field-OAuthScope input[type="text"]')).toHaveValue('docuware.platform');
  });

  test('AuthType=OAuth shows the password, and does not demand one', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('AuthType OAuth display logic');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — AuthType=OAuth (v1) shows the Password field

The route that prepares an OAuth request builds its token request from client id, client secret,
username AND password, so an HTTP + OAuth endpoint needs somewhere to put a password. The window
shows the field for that combination — and only shows it: Password's MandatoryLogic covers
HTTP + Basic, SFTP + PASSWORD and HTTP + OAuth2, not HTTP + OAuth, because the token request omits
a blank credential rather than failing.

1. New record, TransportType=HTTP, AuthType=OAuth -> the Password field is on screen
2. Fill only what HTTP demands, no password -> the endpoint is valid and saved
3. Type a password -> it reaches the record, so the field is not merely decorative
    `);

    test.setTimeout(180000);

    await openNewEndpoint(page);

    await selectListValue(page, 'TransportType', 'HTTP');
    // OAuth v1, NOT OAuth2 — see OAUTH_V1.
    await selectListValue(page, 'AuthType', OAUTH_V1);
    // Mandatory under HTTP and without a default — the record can never persist unless they are set.
    await selectListValue(page, 'OutboundHttpMethod', 'POST');
    await fillTextField(page, 'HttpEndPoint', 'https://example.com/api/packzettel');

    // The field the token request needs is on screen for this configuration ...
    await expect(page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]'))
      .toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    const oauthV1RecordId = await savedRecordId(page);

    // ... and nobody typed a password, yet the endpoint is complete: shown, not mandatory.
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, oauthV1RecordId, 'after configuring an HTTP + OAuth endpoint with no password');

    const oauthV1Record = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), oauthV1RecordId);
    expect(oauthV1Record.fieldsByName.AuthType.value.key, 'the scenario must be running on OAuth v1, not OAuth2').toBe(OAUTH_V1);
    expect(emptyish(oauthV1Record.fieldsByName.Password.value), 'no password was typed, so the record must carry none').toBe(true);

    await saveStill(page, 'endpoint-window-HTTP-OAuth-password-shown.png');

    // A password typed into that field does reach the record — the field is editable, not just drawn.
    await fillPasswordField(page, 'oauth-secret');
    const withPassword = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), oauthV1RecordId);
    expect(withPassword.fieldsByName.Password.value, 'the password an OAuth endpoint types must be stored').toBe('oauth-secret');
  });

  test('TransportType=LOCAL_FILE reveals root location, polling interval and filename pattern; HTTP and SFTP hide them', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('TransportType LOCAL_FILE display logic');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — TransportType=LOCAL_FILE Display Logic

The three local-file fields are gated by DisplayLogic on TransportType, so they must be
visible for LOCAL_FILE and hidden for every other transport.

1. New record, TransportType=LOCAL_FILE -> LocalRootLocation, Frequency, ImportFileNamePattern visible
2. Frequency carries its column default instead of coming up empty
3. TransportType=HTTP -> all three hidden, the HTTP fields visible
4. TransportType=SFTP -> all three hidden, the SFTP fields visible
5. Back to LOCAL_FILE -> all three visible again
    `);

    test.setTimeout(180000);

    await test.step('Open a new External System Endpoint', async () => {
      await openNewEndpoint(page);
    });

    await test.step('Select transport Local File — the three local-file fields appear', async () => {
      await selectListValue(page, 'TransportType', 'LOCAL_FILE');

      await expect(page.locator('.form-field-LocalRootLocation input[type="text"]')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-Frequency input')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-ImportFileNamePattern input[type="text"]')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
    });

    await test.step('The polling interval comes up pre-filled with its default, not empty', async () => {
      // Frequency defaults from AD_Column.DefaultValue rather than coming up empty
      // (integer widget — tolerate any locale grouping separator between "60" and "000").
      await expect(page.locator('.form-field-Frequency input')).toHaveValue(/^60[.,\s ]?000$/);
      await saveStill(page, 'endpoint-window-transport-LOCAL_FILE.png');
    });

    await test.step('Switch to transport HTTP — the local-file fields disappear', async () => {
      await selectListValue(page, 'TransportType', 'HTTP');

      await expect(page.locator('.form-field-LocalRootLocation')).toBeHidden({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-Frequency')).toBeHidden({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-ImportFileNamePattern')).toBeHidden({ timeout: FAST_ACTION_TIMEOUT });
      // ...and the HTTP fields are the ones on screen
      await expect(page.locator('.form-field-HttpEndPoint')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });

      await saveStill(page, 'endpoint-window-transport-HTTP.png');
    });

    await test.step('Switch to transport SFTP — the local-file fields stay hidden', async () => {
      await selectListValue(page, 'TransportType', 'SFTP');

      await expect(page.locator('.form-field-LocalRootLocation')).toBeHidden({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-Frequency')).toBeHidden({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-ImportFileNamePattern')).toBeHidden({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-SftpHost')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });

      await saveStill(page, 'endpoint-window-transport-SFTP.png');
    });

    await test.step('Back to Local File — the three fields are shown again', async () => {
      await selectListValue(page, 'TransportType', 'LOCAL_FILE');

      await expect(page.locator('.form-field-LocalRootLocation input[type="text"]')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-Frequency input')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-ImportFileNamePattern input[type="text"]')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
    });
  });

  test('LOCAL_FILE root location is mandatory — the endpoint stays unsaved until it is filled', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('LOCAL_FILE mandatory logic');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — LocalRootLocation MandatoryLogic

LocalRootLocation carries MandatoryLogic @TransportType/X@='LOCAL_FILE', so a local-file
endpoint with no directory to poll must not become a valid, saved record.

1. New record, TransportType=LOCAL_FILE, LocalRootLocation left empty -> the WebAPI reports
   the document invalid and names the missing field
2. Fill LocalRootLocation -> the record becomes valid and persists
    `);

    test.setTimeout(180000);

    await openNewEndpoint(page);

    await selectListValue(page, 'TransportType', 'LOCAL_FILE');

    // Nothing else is entered: the transport is set, the mandatory root location is not.
    const recordId = await savedRecordId(page);

    // The document must NOT be valid — the mandatory root location is missing.
    const invalidStatus = await getValidationStatus(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(invalidStatus.valid, `expected the LOCAL_FILE endpoint to be invalid while LocalRootLocation is empty, got: ${JSON.stringify(invalidStatus)}`).toBe(false);
    expect(invalidStatus.missingFields.map((missing) => missing.field)).toContain('LocalRootLocation');

    await saveStill(page, 'endpoint-window-LOCAL_FILE-missing-root-location.png');

    // Filling it makes the record valid and saved.
    await fillTextField(page, 'LocalRootLocation', '/var/metasfresh/import/packzettel');

    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after filling the mandatory LocalRootLocation');
    await expect(page.locator('.form-field-LocalRootLocation input[type="text"]')).toHaveValue('/var/metasfresh/import/packzettel');

    await saveStill(page, 'endpoint-window-LOCAL_FILE-saved.png');
  });

  test('Switching transport away and back leaves no foreign transport values behind', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('TransportType switch clears foreign transport fields');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — switching TransportType clears the other transports' fields

A saved endpoint that is switched from one transport to another must not keep the previous
transport's configuration around: it is invisible in the window, so it would be a silent
stale configuration on the record.

1. Save a complete LOCAL_FILE endpoint: root location, filename pattern, and a 5-second polling
   interval the operator types over the column's default
2. Switch to SFTP and fill its mandatory fields -> the values the operator typed under LOCAL_FILE are
   gone from the record, the polling interval back at the column default nobody typed
3. Switch back to LOCAL_FILE and re-fill the root location -> the record is valid straight away: the
   polling interval is pre-filled with that same default, exactly as on a newly created endpoint
4. Override the pre-filled polling interval -> it takes, and the SFTP values are gone
    `);

    test.setTimeout(240000);

    await test.step('Save a complete Local File endpoint', async () => {
      await openNewEndpoint(page);

      await selectListValue(page, 'TransportType', 'LOCAL_FILE');
      await fillTextField(page, 'LocalRootLocation', '/var/metasfresh/import/packzettel');
      await fillTextField(page, 'ImportFileNamePattern', '{filename}_{timestamp}');
      // over the 60000 the column default pre-filled, so that what the transport switch leaves behind
      // below can only be the default and not this value left untouched
      await fillNumericField(page, 'Frequency', '5000');
    });

    const recordId = await savedRecordId(page);
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after saving the LOCAL_FILE endpoint');

    const localFileRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(localFileRecord.fieldsByName.LocalRootLocation.value).toBe('/var/metasfresh/import/packzettel');
    expect(localFileRecord.fieldsByName.ImportFileNamePattern.value).toBe('{filename}_{timestamp}');
    expect(localFileRecord.fieldsByName.Frequency.value).toBe(5000);

    await test.step('Switch the saved endpoint to SFTP and configure it', async () => {
      await selectListValue(page, 'TransportType', 'SFTP');
      await fillTextField(page, 'SftpHost', 'sftp.example.com');
      await fillNumericField(page, 'SftpPort', '22');
      await fillTextField(page, 'SftpUsername', 'testuser');
      // SSH-key auth on purpose: it needs no field that HTTP also uses, so this step exercises the
      // transport switch on its own. (Password auth works too — the record keeps a password entered in
      // the same save, because SFTP + PASSWORD shows that field — but then the scenario would be
      // testing two things at once.)
      await selectListValue(page, 'SftpAuthType', 'SSH_KEY');
      await fillSshPrivateKeyField(page, '-----BEGIN OPENSSH PRIVATE KEY-----');
      await fillTextField(page, 'SftpRemotePath', '/outbound/edi');
    });

    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after switching the endpoint to SFTP');

    const sftpRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(sftpRecord.fieldsByName.TransportType.value.key).toBe('SFTP');
    expect(emptyish(sftpRecord.fieldsByName.LocalRootLocation.value), 'LocalRootLocation must be cleared when the endpoint leaves LOCAL_FILE').toBe(true);
    expect(emptyish(sftpRecord.fieldsByName.ImportFileNamePattern.value), 'ImportFileNamePattern must be cleared when the endpoint leaves LOCAL_FILE').toBe(true);
    // Frequency carries an AD_Column.DefaultValue, so leaving LOCAL_FILE resets it to that value rather
    // than clearing it: the 5-second interval typed above is gone, 60000 is what a new record would show.
    // 60000 here can only be the reset, never the typed value surviving.
    expect(sftpRecord.fieldsByName.Frequency.value, 'Frequency must be reset to its column default when the endpoint leaves LOCAL_FILE').toBe(60000);

    await saveStill(page, 'endpoint-window-switched-LOCAL_FILE-to-SFTP.png');

    await test.step('Switch back to Local File and re-enter only the root location', async () => {
      await selectListValue(page, 'TransportType', 'LOCAL_FILE');
      await fillTextField(page, 'LocalRootLocation', '/var/metasfresh/import/packzettel2');
    });

    // Nobody typed a polling interval in this step, and none is asked for: Frequency stands at the
    // AD_Column.DefaultValue the switch to SFTP put back, which is also what the window pre-fills on a
    // newly created LOCAL_FILE endpoint. Its MandatoryLogic is therefore already satisfied.
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after switching the endpoint back to LOCAL_FILE');
    const backToLocalFileRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(backToLocalFileRecord.fieldsByName.Frequency.value, 'Frequency must come back pre-filled with its column default').toBe(60000);
    await expect(page.locator('.form-field-Frequency input')).toHaveValue(/^60[.,\s ]?000$/);

    await saveStill(page, 'endpoint-window-switched-back-to-LOCAL_FILE.png');

    await test.step('Override the pre-filled polling interval', async () => {
      await fillNumericField(page, 'Frequency', '30000');
    });

    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after overriding the polling interval');

    const pollableAgainRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(pollableAgainRecord.fieldsByName.TransportType.value.key).toBe('LOCAL_FILE');
    expect(pollableAgainRecord.fieldsByName.LocalRootLocation.value).toBe('/var/metasfresh/import/packzettel2');
    expect(pollableAgainRecord.fieldsByName.Frequency.value, 'a pre-filled default must still be overridable by the operator').toBe(30000);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpHost.value), 'SftpHost must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpUsername.value), 'SftpUsername must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpRemotePath.value), 'SftpRemotePath must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpAuthType.value), 'SftpAuthType must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SshPrivateKey.value), 'SshPrivateKey must be cleared when the endpoint leaves SFTP').toBe(true);

    await saveStill(page, 'endpoint-window-switched-back-to-LOCAL_FILE-interval-overridden.png');
  });

  test('Switching an HTTP endpoint to SFTP password authentication keeps the password', async ({ page }) => {
    allure.epic('E0292: EDI');
    allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
    allure.tag('F00380');
    allure.story('A field the new configuration still shows keeps its value');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — a still-visible field keeps its value across a transport switch

Password is the one field shown by more than one transport: HTTP + Basic, HTTP + OAuth,
HTTP + OAuth2 and SFTP + PASSWORD. Switching an endpoint between two of those configurations
must therefore NOT take the password away — the operator can still see the field, and a value
they cannot see is the only thing the clearing exists to prevent.

1. Save a complete HTTP + Basic endpoint, password included
2. Switch to SFTP and pick PASSWORD authentication, filling the SFTP settings
3. The password is still on the record and the field is still on screen — the endpoint is usable
4. Switch the SFTP authentication to SSH_KEY -> now the window hides the password, so it is cleared
    `);

    test.setTimeout(240000);

    await test.step('Save a complete HTTP + Basic endpoint with a password', async () => {
      await openNewEndpoint(page);

      await selectListValue(page, 'TransportType', 'HTTP');
      await selectListValue(page, 'AuthType', 'Basic');
      await selectListValue(page, 'OutboundHttpMethod', 'POST');
      await fillTextField(page, 'HttpEndPoint', 'https://example.com/api/orders');
      await fillTextField(page, 'LoginUsername', 'svc-user');
      await fillPasswordField(page, 'shared-secret');
    });

    const recordId = await savedRecordId(page);
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after saving the HTTP + Basic endpoint');
    expect((await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId)).fieldsByName.Password.value).toBe('shared-secret');

    await test.step('Switch the saved endpoint to SFTP with password authentication', async () => {
      await selectListValue(page, 'TransportType', 'SFTP');
      await selectListValue(page, 'SftpAuthType', 'PASSWORD');
      await fillTextField(page, 'SftpHost', 'sftp.example.com');
      await fillNumericField(page, 'SftpPort', '22');
      await fillTextField(page, 'SftpUsername', 'sftpuser');
      await fillTextField(page, 'SftpRemotePath', '/outbound/edi');
    });

    // The window still shows the password field under SFTP + PASSWORD ...
    await expect(page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]'))
      .toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    // ... so the value must still be there. Nobody re-typed it in this step.
    const sftpRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(sftpRecord.fieldsByName.TransportType.value.key).toBe('SFTP');
    expect(sftpRecord.fieldsByName.Password.value, 'the password must survive a switch into a configuration that still shows it').toBe('shared-secret');
    // ... and with it the endpoint is complete, not left invalid pending a re-typed password
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after switching the endpoint to SFTP + PASSWORD');
    // the HTTP-only settings are gone, though
    expect(emptyish(sftpRecord.fieldsByName.HttpEndPoint.value), 'HttpEndPoint must be cleared when the endpoint leaves HTTP').toBe(true);
    expect(emptyish(sftpRecord.fieldsByName.AuthType.value), 'AuthType must be cleared when the endpoint leaves HTTP').toBe(true);
    expect(emptyish(sftpRecord.fieldsByName.LoginUsername.value), 'LoginUsername must be cleared when the endpoint leaves HTTP').toBe(true);

    await saveStill(page, 'endpoint-window-switched-HTTP-to-SFTP-password-kept.png');

    // The SSH key has to be entered in this step: it is mandatory under SFTP + SSH_KEY, so without it
    // the record is invalid, the WebUI never saves it, and the interceptor never runs at all.
    await test.step('Switch the SFTP authentication to SSH key', async () => {
      await selectListValue(page, 'SftpAuthType', 'SSH_KEY');
      await fillSshPrivateKeyField(page, '-----BEGIN OPENSSH PRIVATE KEY-----');
    });

    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after switching the endpoint to SFTP + SSH_KEY');

    // SFTP + SSH_KEY hides the password, so now — and only now — it is taken away
    await expect(page.locator('.form-field-Password')).toBeHidden({ timeout: SLOW_ACTION_TIMEOUT });
    const sshKeyRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(emptyish(sshKeyRecord.fieldsByName.Password.value), 'the password must be cleared once the window stops showing it').toBe(true);

    await saveStill(page, 'endpoint-window-switched-SFTP-to-ssh-key.png');
  });
});

/**
 * The captions the three LOCAL_FILE fields must render with, per language. "Abfrageintervall (ms)" /
 * "Polling Interval (ms)": the value is a delay in milliseconds between two polls, so it is an
 * interval, not a frequency, and it matches the SFTP transport's sibling field one column over. That
 * caption comes from a dedicated AD_Element via AD_Field.AD_Name_ID, so a missed translation
 * propagation shows up here as the other language's text.
 */
const LOCAL_FILE_LABEL_CASES = [
  {
    language: 'de_DE',
    label: 'German',
    captions: {
      LocalRootLocation: 'Lokales Stammverzeichnis',
      Frequency: 'Abfrageintervall (ms)',
      ImportFileNamePattern: 'Import-Dateinamensmuster',
    },
  },
  {
    language: 'en_US',
    label: 'English',
    captions: {
      LocalRootLocation: 'Local Root Location',
      Frequency: 'Polling Interval (ms)',
      ImportFileNamePattern: 'Import Filename Pattern',
    },
  },
];

test.describe('ExternalSystem Endpoint — LOCAL_FILE field labels per language', () => {
  LOCAL_FILE_LABEL_CASES.forEach(({ language, label, captions }) => {
    test(`LOCAL_FILE fields render their ${label} labels`, async ({ page }) => {
      allure.epic('E0292: EDI');
      allure.tag('F00380: ExternalSystem Scripted-Import-Processor');
      allure.tag('F00380');
      allure.story('TransportType field display logic');
      allure.severity('normal');

      allure.description(`
## ExternalSystem_Endpoint — LOCAL_FILE field captions (${label})

Selecting TransportType=LOCAL_FILE reveals the root location, polling interval and filename
pattern. This asserts each one renders with its ${label} caption, so a missing or half-applied
translation is caught instead of being eyeballed on a screenshot.
      `);

      // A fresh single-role user carries the language on its own record, which is what the
      // WebUI session reads -- there is no live language switch.
      const masterdata = await Backend.createMasterdata({
        request: { login: { user: { language } } },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      await LoginPage.expectLoggedIn();

      await openNewEndpoint(page);

      await selectListValue(page, 'TransportType', 'LOCAL_FILE');

      for (const [fieldName, caption] of Object.entries(captions)) {
        const fieldLabel = page.locator(`.form-field-${fieldName} label.form-control-label`);
        await fieldLabel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(fieldLabel, `${fieldName} caption in ${language}`).toHaveText(caption);
      }

      await saveStill(page, `endpoint-window-transport-LOCAL_FILE-${language}.png`);
    });
  });
});
