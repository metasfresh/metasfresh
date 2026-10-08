import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { assertRecordIsValid } from '../utils/WebAPIValidation';

/**
 * ExternalSystem_Endpoint — SFTP Transport Type E2E test suite.
 *
 * Tests the TransportType field display/mandatory logic on the
 * ExternalSystem_Endpoint window (AD_Window_ID=541967):
 *
 * 1. TransportType=HTTP -> SFTP fields hidden, HTTP fields visible
 * 2. TransportType=SFTP -> SFTP fields visible, HTTP fields hidden
 * 3. SftpAuthType=PASSWORD -> Password field visible
 * 4. SftpAuthType=SSH_KEY -> SshPrivateKey field visible
 * 5. Create and save a full SFTP endpoint configuration
 */

const EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID = 541967;

/**
 * Select a value from a List dropdown widget (AD_Reference_ID=17).
 * List widgets render a readonly input — we must click the container to open
 * the dropdown, then click the matching option.
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
    await selectListValue(page, 'SftpAuthType', 'Password');

    // Password field should be visible
    const passwordField = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]');
    await expect(passwordField).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // SshPrivateKey should NOT be visible
    await expect(page.locator('.form-field-SshPrivateKey')).toBeHidden({ timeout: 3000 });

    // --- Test: Switch to SftpAuthType = SSH_KEY ---
    await selectListValue(page, 'SftpAuthType', 'SSH Key');

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
    await selectListValue(page, 'SftpAuthType', 'Password');

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
});
