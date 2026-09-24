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
 * 6. TransportType=LOCAL_FILE -> root location, frequency and filename pattern visible; hidden for HTTP/SFTP
 * 7. LOCAL_FILE root location is mandatory -> the endpoint stays invalid/unsaved until it is filled
 * 8. Switching transport away and back (LOCAL_FILE <-> SFTP) leaves no foreign transport values behind
 * 9. The LOCAL_FILE field labels render in German and in English
 */

const EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID = 541967;

/**
 * Select a value from a List dropdown widget (AD_Reference_ID=17).
 * List widgets render a readonly input — we must click the container to open
 * the dropdown, then click the matching option.
 *
 * An option renders as "<AD_Ref_List.Value>_<localized name>", so pass a RegExp on the Value
 * (e.g. /SSH_KEY/) to stay language-independent; a localized name only matches on the one language.
 */
async function selectListValue(page, fieldName, optionText) {
  const container = page.locator(`.form-field-${fieldName}`);
  await container.locator('input').click();
  await page.waitForTimeout(300);

  const option = page.locator('.input-dropdown-list-option').filter({ hasText: optionText }).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await option.click();
  await page.waitForTimeout(1000);
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
 * True when a WebAPI field value means "not set". The endpoint's columns clear to different empty
 * representations depending on their type — null/absent for the strings, 0 for the numerics, and a
 * list field clears to a null lookup value — so all of those count as cleared.
 */
function emptyish(value) {
  if (value === null || value === undefined || value === '') {
    return true;
  }
  if (typeof value === 'number') {
    return value === 0;
  }
  if (typeof value === 'object') {
    return value.key === null || value.key === undefined || value.key === '';
  }
  return false;
}

/**
 * Fill a text input field by column name using the form-field CSS class pattern.
 */
async function fillTextField(page, fieldName, value) {
  const field = page.locator(`.form-field-${fieldName} input[type="text"]`);
  await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await field.fill(value);
  await page.waitForTimeout(300);
}

/**
 * Fill a numeric input field by column name.
 */
async function fillNumericField(page, fieldName, value) {
  const field = page.locator(`.form-field-${fieldName} input`);
  await field.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await field.fill(value);
  await page.waitForTimeout(300);
}

test.describe('ExternalSystem Endpoint — SFTP Transport', () => {
  test.beforeEach(async ({ page }) => {
    // The ExternalSystem_Endpoint window (541967) is only accessible to the "WebUI"
    // role; the default role (roles[0]) lacks read-write on it. Select the WebUI role
    // explicitly at login so the form loads.
    await page.goto(`${FRONTEND_BASE_URL}/login`);
    await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('input[name="username"]').fill('metasfresh');
    await page.locator('input[name="password"]').fill('metasfresh');
    await page.locator('.btn-meta-success').click();
    await page.waitForTimeout(1200);

    if (page.url().includes('/login')) {
      const roleDropdown = page.locator('.input-dropdown-container .input-field').first();
      if (await roleDropdown.isVisible().catch(() => false)) {
        await roleDropdown.click();
        await page.waitForTimeout(400);
        const roleOption = page
          .locator('.input-dropdown-list-option')
          .filter({ hasText: /^WebUI, metasfresh, metasfresh AG$/ })
          .first();
        await roleOption.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await roleOption.dispatchEvent('mousedown');
        await page.waitForTimeout(300);
        await page.keyboard.press('Escape');
        await page.waitForTimeout(200);
      }
      await page.locator('.btn-meta-success').click();
    }
    await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
  });

  test('TransportType field visibility toggles SFTP/HTTP fields', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
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
    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);

    // Wait for the form to load
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

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
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
    allure.story('SftpAuthType field display logic');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — SftpAuthType Display Logic

Tests that SftpAuthType=PASSWORD shows the Password field,
and SftpAuthType=SSH_KEY shows the SshPrivateKey field.
    `);

    test.setTimeout(120000);

    // Navigate and create new record
    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Set TransportType = SFTP first
    await selectListValue(page, 'TransportType', 'SFTP');

    // --- Test: Set SftpAuthType = PASSWORD ---
    await selectListValue(page, 'SftpAuthType', /PASSWORD/);

    // Password field should be visible
    const passwordField = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]');
    await expect(passwordField).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // SshPrivateKey should NOT be visible
    await expect(page.locator('.form-field-SshPrivateKey')).toBeHidden({ timeout: 3000 });

    // --- Test: Switch to SftpAuthType = SSH_KEY ---
    await selectListValue(page, 'SftpAuthType', /SSH_KEY/);

    // SshPrivateKey should now be visible
    await expect(page.locator('.form-field-SshPrivateKey textarea, .form-field-SshPrivateKey input')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // Password should be hidden
    await expect(page.locator('.form-field-Password')).toBeHidden({ timeout: 3000 });
  });

  test('Create and save full SFTP endpoint configuration', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
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
    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Value field is auto-generated by document sequence (IsUseDocSequence=Y) — skip it

    // Set TransportType = SFTP
    await selectListValue(page, 'TransportType', 'SFTP');

    // Fill mandatory SFTP fields
    await fillTextField(page, 'SftpHost', 'sftp.example.com');
    await fillNumericField(page, 'SftpPort', '22');
    await fillTextField(page, 'SftpUsername', 'testuser');

    // Set SftpAuthType = PASSWORD (mandatory when SFTP)
    await selectListValue(page, 'SftpAuthType', /PASSWORD/);

    // Fill password (mandatory when SFTP + SftpAuthType=PASSWORD)
    const passwordField = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]');
    await passwordField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await passwordField.fill('secret123');
    await page.waitForTimeout(300);

    // Fill remote path (mandatory when SFTP)
    await fillTextField(page, 'SftpRemotePath', '/outbound/edi');

    // Fill optional filename pattern
    await fillTextField(page, 'SftpFilenamePattern', 'export_{timestamp}.json');

    // SFTP inbound-polling settings now live on the endpoint (moved off the scripted-import config).
    await fillNumericField(page, 'SftpPollingIntervalMs', '30000');
    await fillTextField(page, 'ProcessedDirectory', '/inbound/processed');
    await fillTextField(page, 'ErrorDirectory', '/inbound/error');

    // Tab out to trigger save
    await page.keyboard.press('Tab');
    await page.waitForTimeout(2000);

    // Verify the URL changed from /NEW to a record ID (indicating successful save)
    await page.waitForURL(
      (url) => {
        const urlStr = url.toString();
        return urlStr.includes(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/`) && !urlStr.includes('/NEW');
      },
      { timeout: SLOW_ACTION_TIMEOUT }
    );

    // The URL change alone does not prove persistence (a NEW record gets a cached id even when invalid).
    // Assert the record is actually valid/saved via the WebAPI.
    const sftpRecordId = page.url().match(new RegExp(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/(\\d+)`))[1];
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

    // The SFTP inbound-polling settings persist on the endpoint too.
    // (integer field — tolerate any locale grouping separator between "30" and "000")
    await expect(page.locator('.form-field-SftpPollingIntervalMs input')).toHaveValue(/^30[.,\s ]?000$/);
    await expect(page.locator('.form-field-ProcessedDirectory input[type="text"]')).toHaveValue('/inbound/processed');
    await expect(page.locator('.form-field-ErrorDirectory input[type="text"]')).toHaveValue('/inbound/error');
  });

  test('AuthType=OAuth2 reveals OAuth2 token URL + scope + credential fields', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
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

    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

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
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
    allure.story('Full OAuth2 Configuration Flow');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — Full OAuth2 Configuration

Creates a complete HTTP + OAuth2 endpoint and verifies it saves (the mandatory
OAuthTokenUrl is accepted and the record persists).
    `);

    test.setTimeout(120000);

    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Value is auto-generated (IsUseDocSequence=Y) — skip it.
    // The legacy "Art" (Type) field was retired from this window (its AD_Field/AD_UI_Element are
    // deactivated); TransportType is the single transport selector, so don't touch Type.
    await selectListValue(page, 'TransportType', 'HTTP');
    await selectListValue(page, 'AuthType', 'OAuth2');
    // OutboundHttpMethod is mandatory under HTTP and has NO default — must be set or the record stays invalid (never persists)
    await selectListValue(page, 'OutboundHttpMethod', 'POST');

    // Fill the HTTP endpoint URL (mandatory for HTTP; column renamed OutboundHttpEP -> HttpEndPoint)
    await fillTextField(page, 'HttpEndPoint', 'https://dw.example.com/DocuWare/Platform/FileCabinets/abc/Documents');

    // OAuth2 mandatory: token URL; plus the password-grant credentials
    await fillTextField(page, 'OAuthTokenUrl', 'https://dw.example.com/DocuWare/Platform/Identity/connect/token');
    await fillTextField(page, 'OAuthScope', 'docuware.platform');
    await fillTextField(page, 'ClientId', 'docuware.platform.net.client');
    await fillTextField(page, 'LoginUsername', 'svc-user');
    const pwd = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]');
    await pwd.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await pwd.fill('svc-secret');
    await page.waitForTimeout(300);

    // Tab out to trigger save
    await page.keyboard.press('Tab');
    await page.waitForTimeout(2000);

    // URL changes from /NEW to a record ID => saved (mandatory logic satisfied)
    await page.waitForURL(
      (url) => {
        const urlStr = url.toString();
        return urlStr.includes(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/`) && !urlStr.includes('/NEW');
      },
      { timeout: SLOW_ACTION_TIMEOUT }
    );

    // A NEW record is assigned a cached id (URL leaves /NEW) even when validStatus.valid=false, so the
    // URL change alone does NOT prove the row persisted. Assert real persistence via the WebAPI.
    const oauthRecordId = page.url().match(new RegExp(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/(\\d+)`))[1];
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, oauthRecordId, 'after saving the OAuth2 HTTP endpoint');

    // Saved OAuth2 values persist
    await expect(page.locator('.form-field-OAuthTokenUrl input[type="text"]')).toHaveValue('https://dw.example.com/DocuWare/Platform/Identity/connect/token');
    await expect(page.locator('.form-field-OAuthScope input[type="text"]')).toHaveValue('docuware.platform');
  });

  test('TransportType=LOCAL_FILE reveals root location, frequency and filename pattern; HTTP and SFTP hide them', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
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
      await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('Select transport Local File — the three local-file fields appear', async () => {
      await selectListValue(page, 'TransportType', /LOCAL_FILE/);

      await expect(page.locator('.form-field-LocalRootLocation input[type="text"]')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-Frequency input')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-ImportFileNamePattern input[type="text"]')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
    });

    await test.step('Polling frequency comes up pre-filled with its default, not empty', async () => {
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
      await selectListValue(page, 'TransportType', /LOCAL_FILE/);

      await expect(page.locator('.form-field-LocalRootLocation input[type="text"]')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-Frequency input')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
      await expect(page.locator('.form-field-ImportFileNamePattern input[type="text"]')).toBeVisible({ timeout: FAST_ACTION_TIMEOUT });
    });
  });

  test('LOCAL_FILE root location is mandatory — the endpoint stays unsaved until it is filled', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
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

    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    await selectListValue(page, 'TransportType', /LOCAL_FILE/);

    // Tab out without filling the mandatory root location
    await page.keyboard.press('Tab');
    await page.waitForTimeout(2000);

    const recordId = page.url().match(new RegExp(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/(\\d+)`))[1];

    // The document must NOT be valid — the mandatory root location is missing.
    const invalidStatus = await getValidationStatus(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(invalidStatus.valid, `expected the LOCAL_FILE endpoint to be invalid while LocalRootLocation is empty, got: ${JSON.stringify(invalidStatus)}`).toBe(false);
    expect(invalidStatus.missingFields.map((missing) => missing.field)).toContain('LocalRootLocation');

    await saveStill(page, 'endpoint-window-LOCAL_FILE-missing-root-location.png');

    // Filling it makes the record valid and saved.
    await fillTextField(page, 'LocalRootLocation', '/var/metasfresh/import/packzettel');
    await page.keyboard.press('Tab');
    await page.waitForTimeout(2000);

    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after filling the mandatory LocalRootLocation');
    await expect(page.locator('.form-field-LocalRootLocation input[type="text"]')).toHaveValue('/var/metasfresh/import/packzettel');

    await saveStill(page, 'endpoint-window-LOCAL_FILE-saved.png');
  });

  test('Switching transport away and back leaves no foreign transport values behind', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.tag('F15010');
    allure.story('TransportType switch clears foreign transport fields');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — switching TransportType clears the other transports' fields

A saved endpoint that is switched from one transport to another must not keep the previous
transport's configuration around: it is invisible in the window, so it would be a silent
stale configuration on the record.

1. Save a complete LOCAL_FILE endpoint (root location, polling interval, filename pattern)
2. Switch to SFTP and fill its mandatory fields -> the LOCAL_FILE values are gone from the record
3. Switch back to LOCAL_FILE and re-fill the root location -> the record is invalid, because the
   polling interval the switch to SFTP cleared is unset and mandatory for LOCAL_FILE
4. Re-enter the polling interval -> the record is valid again and the SFTP values are gone
    `);

    test.setTimeout(240000);

    await test.step('Save a complete Local File endpoint', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await selectListValue(page, 'TransportType', /LOCAL_FILE/);
      await fillTextField(page, 'LocalRootLocation', '/var/metasfresh/import/packzettel');
      await fillTextField(page, 'ImportFileNamePattern', '{filename}_{timestamp}');
      await page.keyboard.press('Tab');
      await page.waitForTimeout(2000);
    });

    const recordId = page.url().match(new RegExp(`/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/(\\d+)`))[1];
    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after saving the LOCAL_FILE endpoint');

    const localFileRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(localFileRecord.fieldsByName.LocalRootLocation.value).toBe('/var/metasfresh/import/packzettel');
    expect(localFileRecord.fieldsByName.ImportFileNamePattern.value).toBe('{filename}_{timestamp}');

    await test.step('Switch the saved endpoint to SFTP and configure it', async () => {
      await selectListValue(page, 'TransportType', 'SFTP');
      await fillTextField(page, 'SftpHost', 'sftp.example.com');
      await fillNumericField(page, 'SftpPort', '22');
      await fillTextField(page, 'SftpUsername', 'testuser');
      // SSH-key auth on purpose: it needs no field that HTTP also uses, so this step exercises the
      // transport switch on its own. (Password auth works too — the record keeps a password entered in
      // the same save, because SFTP + PASSWORD shows that field — but then the scenario would be
      // testing two things at once.)
      await selectListValue(page, 'SftpAuthType', /SSH_KEY/);
      const sshPrivateKey = page.locator('.form-field-SshPrivateKey textarea, .form-field-SshPrivateKey input');
      await sshPrivateKey.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await sshPrivateKey.fill('-----BEGIN OPENSSH PRIVATE KEY-----');
      await page.waitForTimeout(300);
      await fillTextField(page, 'SftpRemotePath', '/outbound/edi');
      await page.keyboard.press('Tab');
      await page.waitForTimeout(2000);
    });

    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after switching the endpoint to SFTP');

    const sftpRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(sftpRecord.fieldsByName.TransportType.value.key).toBe('SFTP');
    expect(emptyish(sftpRecord.fieldsByName.LocalRootLocation.value), 'LocalRootLocation must be cleared when the endpoint leaves LOCAL_FILE').toBe(true);
    expect(emptyish(sftpRecord.fieldsByName.ImportFileNamePattern.value), 'ImportFileNamePattern must be cleared when the endpoint leaves LOCAL_FILE').toBe(true);
    expect(emptyish(sftpRecord.fieldsByName.Frequency.value), 'Frequency must be cleared when the endpoint leaves LOCAL_FILE').toBe(true);

    await saveStill(page, 'endpoint-window-switched-LOCAL_FILE-to-SFTP.png');

    await test.step('Switch back to Local File and re-enter only the root location', async () => {
      await selectListValue(page, 'TransportType', /LOCAL_FILE/);
      await fillTextField(page, 'LocalRootLocation', '/var/metasfresh/import/packzettel2');
      await page.keyboard.press('Tab');
      await page.waitForTimeout(2000);
    });

    // Nobody typed a polling interval in this step: the switch to SFTP cleared Frequency to SQL NULL, and
    // switching back does not restore it. Frequency is mandatory under LOCAL_FILE, so the record is
    // invalid and the operator is asked for it -- exactly as for LocalRootLocation.
    const backToLocalFileStatus = await getValidationStatus(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(backToLocalFileStatus.valid, `expected the endpoint to be invalid while Frequency is unset, got: ${JSON.stringify(backToLocalFileStatus)}`).toBe(false);
    expect(backToLocalFileStatus.missingFields.map((missing) => missing.field)).toContain('Frequency');

    await saveStill(page, 'endpoint-window-switched-back-to-LOCAL_FILE.png');

    await test.step('Re-enter the polling interval the switch cleared', async () => {
      await fillNumericField(page, 'Frequency', '60000');
      await page.keyboard.press('Tab');
      await page.waitForTimeout(2000);
    });

    await assertRecordIsValid(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID, recordId, 'after re-entering the polling interval');

    const pollableAgainRecord = await getRecordData(String(EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID), recordId);
    expect(pollableAgainRecord.fieldsByName.TransportType.value.key).toBe('LOCAL_FILE');
    expect(pollableAgainRecord.fieldsByName.LocalRootLocation.value).toBe('/var/metasfresh/import/packzettel2');
    expect(pollableAgainRecord.fieldsByName.Frequency.value).toBe(60000);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpHost.value), 'SftpHost must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpUsername.value), 'SftpUsername must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpRemotePath.value), 'SftpRemotePath must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SftpAuthType.value), 'SftpAuthType must be cleared when the endpoint leaves SFTP').toBe(true);
    expect(emptyish(pollableAgainRecord.fieldsByName.SshPrivateKey.value), 'SshPrivateKey must be cleared when the endpoint leaves SFTP').toBe(true);

    await saveStill(page, 'endpoint-window-switched-back-to-LOCAL_FILE-frequency-reentered.png');
  });
});

/**
 * The captions the three LOCAL_FILE fields must render with, per language.
 *
 * "Abfrageintervall (ms)" / "Polling Interval (ms)": the value is a delay in milliseconds BETWEEN
 * two polls, so it is an interval, not a frequency -- and it matches the SFTP transport's sibling
 * field one column over. The caption comes from a dedicated AD_Element reached via
 * AD_Field.AD_Name_ID, so a missed AD_Element_Trl -> AD_Field_Trl propagation shows up here as the
 * other language's text (or the shared core element's "Häufigkeit" / "Frequency").
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
      allure.epic('E1500: External Systems');
      allure.tag('F15010: External System Endpoint');
      allure.tag('F15010');
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

      await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
      await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await selectListValue(page, 'TransportType', /LOCAL_FILE/);

      for (const [fieldName, caption] of Object.entries(captions)) {
        const fieldLabel = page.locator(`.form-field-${fieldName} label.form-control-label`);
        await fieldLabel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await expect(fieldLabel, `${fieldName} caption in ${language}`).toHaveText(caption);
      }

      await saveStill(page, `endpoint-window-transport-LOCAL_FILE-${language}.png`);
    });
  });
});
