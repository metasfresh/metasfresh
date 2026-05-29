import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

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
    await page.goto(`${FRONTEND_BASE_URL}/login`);
    await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('input[name="username"]').fill('metasfresh');
    await page.locator('input[name="password"]').fill('metasfresh');
    await page.locator('.btn-meta-success').click();

    await page.waitForTimeout(1000);
    if (page.url().includes('/login')) {
      const sendButton = page.locator('.btn-meta-success');
      if (await sendButton.isVisible()) {
        await sendButton.click();
      }
    }
    await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
  });

  test('TransportType field visibility toggles SFTP/HTTP fields', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
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
    await expect(page.locator('.form-field-OutboundHttpEP')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-OutboundHttpMethod')).toBeHidden({ timeout: 3000 });

    // --- Test 2: Set TransportType = HTTP ---
    await selectListValue(page, 'TransportType', 'HTTP');

    // HTTP fields should be visible
    await expect(page.locator('.form-field-ContentType')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(page.locator('.form-field-OutboundHttpEP')).toBeVisible({ timeout: 5000 });
    await expect(page.locator('.form-field-OutboundHttpMethod')).toBeVisible({ timeout: 5000 });

    // SFTP fields should be hidden
    await expect(page.locator('.form-field-SftpHost')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-SftpPort')).toBeHidden({ timeout: 3000 });
    await expect(page.locator('.form-field-SftpUsername')).toBeHidden({ timeout: 3000 });
  });

  test('SftpAuthType toggles Password vs SSH key fields', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
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

    // Set Type = HTTP (only available option for endpoint type)
    await selectListValue(page, 'Type', 'HTTP');

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

    // Verify the saved field values are still present
    const sftpHostField = page.locator('.form-field-SftpHost input[type="text"]');
    const sftpUsernameField = page.locator('.form-field-SftpUsername input[type="text"]');
    const remotePathField = page.locator('.form-field-SftpRemotePath input[type="text"]');
    const filenamePatternField = page.locator('.form-field-SftpFilenamePattern input[type="text"]');

    await expect(sftpHostField).toHaveValue('sftp.example.com');
    await expect(sftpUsernameField).toHaveValue('testuser');
    await expect(remotePathField).toHaveValue('/outbound/edi');
    await expect(filenamePatternField).toHaveValue('export_{timestamp}.json');
  });
});
