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
  // Click the dropdown container to open it (use .first() — field may appear in both header and detail sections)
  const container = page.locator(`.form-field-${fieldName}`).first();
  await container.locator('input').first().click();
  await page.waitForTimeout(300);

  // Find and click the option matching the text
  const option = page.locator('.input-dropdown-list-option').filter({ hasText: optionText }).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await option.click();
  await page.waitForTimeout(1000);
}

test.describe('ExternalSystem Endpoint — SFTP Transport', () => {
  test.beforeEach(async ({ page }) => {
    // Login with default credentials (metasfresh/metasfresh)
    await page.goto(`${FRONTEND_BASE_URL}/login`);
    await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('input[name="username"]').fill('metasfresh');
    await page.locator('input[name="password"]').fill('metasfresh');
    await page.locator('.btn-meta-success').click();

    // Handle role selection
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
3. Set TransportType=SFTP -> verify SFTP fields visible
4. Set TransportType=HTTP -> verify HTTP fields visible, SFTP hidden
    `);

    test.setTimeout(120000);

    // Navigate to the ExternalSystem_Endpoint window and create a new record
    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);

    // Wait for the form to load
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // --- Test 1: Set TransportType = SFTP ---
    await selectListValue(page, 'TransportType', 'SFTP');

    // Verify SFTP fields are now visible (use .first() — fields may appear in both header and detail sections)
    const sftpHostField = page.locator('.form-field-SftpHost input[type="text"]').first();
    await expect(sftpHostField).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    const sftpPortField = page.locator('.form-field-SftpPort input').first();
    await expect(sftpPortField).toBeVisible({ timeout: 5000 });

    const sftpUsernameField = page.locator('.form-field-SftpUsername input[type="text"]').first();
    await expect(sftpUsernameField).toBeVisible({ timeout: 5000 });

    const sftpAuthTypeField = page.locator('.form-field-SftpAuthType').first();
    await expect(sftpAuthTypeField).toBeVisible({ timeout: 5000 });

    const sftpRemotePathField = page.locator('.form-field-SftpRemotePath input[type="text"]').first();
    await expect(sftpRemotePathField).toBeVisible({ timeout: 5000 });

    const sftpFilenamePatternField = page.locator('.form-field-SftpFilenamePattern input[type="text"]').first();
    await expect(sftpFilenamePatternField).toBeVisible({ timeout: 5000 });

    // HTTP-only fields should be hidden when TransportType=SFTP
    const contentTypeField = page.locator('.form-field-ContentType').first();
    await expect(contentTypeField).toBeHidden({ timeout: 3000 });

    // --- Test 2: Set TransportType = HTTP ---
    await selectListValue(page, 'TransportType', 'HTTP');

    // HTTP field should be visible
    await expect(contentTypeField).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // SFTP fields should be hidden
    await expect(sftpHostField).toBeHidden({ timeout: 3000 });
    await expect(sftpPortField).toBeHidden({ timeout: 3000 });
    await expect(sftpUsernameField).toBeHidden({ timeout: 3000 });
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

    // Password field should be visible (display logic: TransportType=SFTP & SftpAuthType=PASSWORD)
    const passwordField = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]').first();
    await expect(passwordField).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // SshPrivateKey should NOT be visible
    const sshKeyField = page.locator('.form-field-SshPrivateKey textarea, .form-field-SshPrivateKey input').first();
    await expect(sshKeyField).toBeHidden({ timeout: 3000 });

    // --- Test: Switch to SftpAuthType = SSH_KEY ---
    await selectListValue(page, 'SftpAuthType', 'SSH Key');

    // SshPrivateKey should now be visible
    await expect(sshKeyField).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

    // Password should be hidden (display logic requires SftpAuthType=PASSWORD)
    await expect(passwordField).toBeHidden({ timeout: 3000 });
  });

  test('Create and save full SFTP endpoint configuration', async ({ page }) => {
    allure.epic('E1500: External Systems');
    allure.tag('F15010: External System Endpoint');
    allure.story('Full SFTP Configuration Flow');
    allure.severity('critical');

    allure.description(`
## ExternalSystem_Endpoint — Full SFTP Configuration

Creates a complete SFTP endpoint with all fields filled:
1. Navigate to window, create new record
2. Set Value, TransportType=SFTP
3. Fill all SFTP fields (host, port, username, auth, remote path, filename)
4. Verify record saves successfully
    `);

    test.setTimeout(120000);

    // Navigate and create new record
    await page.goto(`${FRONTEND_BASE_URL}/window/${EXTERNAL_SYSTEM_ENDPOINT_WINDOW_ID}/NEW`);
    await page.waitForTimeout(2000);
    await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Fill Value field
    const valueField = page.locator('.form-field-Value input[type="text"]').first();
    if (await valueField.isVisible({ timeout: 3000 }).catch(() => false)) {
      await valueField.fill('sftp-e2e-full-' + Date.now());
      await page.waitForTimeout(500);
    }

    // Set TransportType = SFTP
    await selectListValue(page, 'TransportType', 'SFTP');

    // Fill SFTP fields (use .first() — fields may appear in both header and detail sections)
    const sftpHostField = page.locator('.form-field-SftpHost input[type="text"]').first();
    await sftpHostField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await sftpHostField.fill('sftp.example.com');
    await page.waitForTimeout(300);

    const sftpPortField = page.locator('.form-field-SftpPort input').first();
    await sftpPortField.fill('22');
    await page.waitForTimeout(300);

    const sftpUsernameField = page.locator('.form-field-SftpUsername input[type="text"]').first();
    await sftpUsernameField.fill('testuser');
    await page.waitForTimeout(300);

    // Set SftpAuthType = PASSWORD
    await selectListValue(page, 'SftpAuthType', 'Password');

    // Fill password
    const passwordField = page.locator('.form-field-Password input[type="text"], .form-field-Password input[type="password"]').first();
    await passwordField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await passwordField.fill('secret123');
    await page.waitForTimeout(300);

    // Fill remote path
    const remotePathField = page.locator('.form-field-SftpRemotePath input[type="text"]').first();
    await remotePathField.fill('/outbound/edi');
    await page.waitForTimeout(300);

    // Fill filename pattern
    const filenamePatternField = page.locator('.form-field-SftpFilenamePattern input[type="text"]').first();
    await filenamePatternField.fill('export_{timestamp}.json');
    await page.waitForTimeout(300);

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
    await expect(sftpHostField).toHaveValue('sftp.example.com');
    await expect(sftpUsernameField).toHaveValue('testuser');
    await expect(remotePathField).toHaveValue('/outbound/edi');
    await expect(filenamePatternField).toHaveValue('export_{timestamp}.json');
  });
});
