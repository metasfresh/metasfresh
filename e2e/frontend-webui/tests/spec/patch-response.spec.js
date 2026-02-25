/**
 * PATCH Response Handling Test Suite
 *
 * Tests that the frontend correctly handles PATCH responses in race-condition
 * scenarios. Covers three fix commits on branch new_dawn_uat_FixStalePatchResponse:
 *
 * 1. Stale PATCH response after navigation — response for record A must not
 *    overwrite record B if the user navigated away before the response arrived.
 *
 * 2. Normal editing regression — editing a field on the same record still works.
 *
 * 3. Modal edit → master sync — after editing in advanced edit modal and closing,
 *    the same field must remain editable on the master record.
 *
 * PR: metasfresh#22445
 */
import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import {
  FRONTEND_BASE_URL,
  SLOW_ACTION_TIMEOUT,
  FAST_ACTION_TIMEOUT,
} from '../utils/common';

const TEST_WINDOW_ID = 127;
const USERNAME = 'metasfresh';
const PASSWORD = 'metasfresh';

/**
 * Login helper — uses inline login like bookmark-star.spec.js.
 */
async function login(page) {
  await page.goto(`${FRONTEND_BASE_URL}/login`);
  await page
    .locator('.login-container')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await page.locator('input[name="username"]').fill(USERNAME);
  await page.locator('input[name="password"]').fill(PASSWORD);
  await page.locator('.btn-meta-success').click();

  // Handle role selection if needed
  await page.waitForTimeout(1000);
  if (page.url().includes('/login')) {
    const sendButton = page.locator('.btn-meta-success');
    if (await sendButton.isVisible()) {
      await sendButton.click();
    }
  }

  await page.waitForURL((url) => !url.toString().includes('/login'), {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await page
    .locator('.app-content, .dashboard')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Create a new Test record and return its ID.
 */
async function createTestRecord(page) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${TEST_WINDOW_ID}/new`);
  await page.waitForURL(/\/window\/127\/\d+/, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await page.waitForTimeout(1000);

  const url = page.url();
  const match = url.match(/\/window\/127\/(\d+)/);
  return match ? match[1] : null;
}

/**
 * Navigate to an existing Test record by ID.
 */
async function gotoRecord(page, recordId) {
  await page.goto(
    `${FRONTEND_BASE_URL}/window/${TEST_WINDOW_ID}/${recordId}`
  );
  await page.waitForLoadState('networkidle').catch(() => {});
  await page.waitForTimeout(1000);
}

/**
 * Get the Name field input locator.
 */
function getNameInput(page, scope = '') {
  const prefix = scope ? `${scope} ` : '';
  return page
    .locator(
      `${prefix}#lookup_Name input.input-field, ${prefix}.form-field-Name input.input-field`
    )
    .first();
}

/**
 * Get the Description field input locator.
 */
function getDescriptionInput(page, scope = '') {
  const prefix = scope ? `${scope} ` : '';
  return page
    .locator(
      `${prefix}#lookup_Description input.input-field, ${prefix}.form-field-Description input.input-field`
    )
    .first();
}

/**
 * Set a text field value and tab to trigger save.
 */
async function setTextField(page, inputLocator, value) {
  await inputLocator.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await inputLocator.click();
  await page.keyboard.press('Control+a');
  await inputLocator.fill(value);
  await page.keyboard.press('Tab');
  await page.waitForTimeout(500);
}

/**
 * Wait for save indicator to complete.
 */
async function waitForSave(page) {
  await page
    .locator('.rotating, .indicator-pending')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
}

test.describe('PATCH Response Handling', () => {
  test.beforeEach(async ({ page }) => {
    await login(page);
  });

  // =========================================================================
  // Test 1: Normal field editing on the same record works (regression test)
  // =========================================================================
  test('Normal field editing on same record works', async ({ page }) => {
    allure.severity('critical');
    allure.description(
      'Regression test: editing a text field on a record and tabbing out ' +
        'must save the value. Verifies that the stale-PATCH guard does not ' +
        'accidentally discard valid PATCH responses.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Set Name field and wait for save', async () => {
      const nameInput = getNameInput(page);
      await setTextField(page, nameInput, 'PatchTest Normal Edit');
      await waitForSave(page);
    });

    await test.step('Reload and verify persistence', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const nameInput = getNameInput(page);
      const value = await nameInput.inputValue();
      expect(value).toBe('PatchTest Normal Edit');
    });

    await test.step('Edit Description and wait for save', async () => {
      const descInput = getDescriptionInput(page);
      await setTextField(page, descInput, 'Edited description');
      await waitForSave(page);
    });

    await test.step('Reload and verify Description persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const descInput = getDescriptionInput(page);
      const value = await descInput.inputValue();
      expect(value).toBe('Edited description');
    });
  });

  // =========================================================================
  // Test 2: Stale PATCH response is discarded after navigation
  // =========================================================================
  test('Stale PATCH response is discarded after navigation', async ({
    page,
  }) => {
    allure.severity('critical');
    allure.description(
      'When a PATCH request is in-flight for record A and the user navigates ' +
        'to record B before the response arrives, the response must be discarded. ' +
        'Record B must keep its own values. Uses page.route() to delay PATCH responses.'
    );

    // Create two records with distinct names
    const recordIdA = await createTestRecord(page);
    console.log(`Created record A: ${recordIdA}`);

    await test.step('Set Name on record A', async () => {
      const nameInput = getNameInput(page);
      await setTextField(page, nameInput, 'Record A');
      await waitForSave(page);
    });

    const recordIdB = await createTestRecord(page);
    console.log(`Created record B: ${recordIdB}`);

    await test.step('Set Name on record B', async () => {
      const nameInput = getNameInput(page);
      await setTextField(page, nameInput, 'Record B');
      await waitForSave(page);
    });

    // Navigate to record A
    await gotoRecord(page, recordIdA);

    await test.step(
      'Intercept PATCH, edit Description on A, navigate to B',
      async () => {
        // Set up a route handler to delay the PATCH response for record A
        let fulfillPatch = null;
        const patchDelayPromise = new Promise((resolve) => {
          fulfillPatch = resolve;
        });

        const patchUrlPattern = new RegExp(
          `/rest/api/window/${TEST_WINDOW_ID}/${recordIdA}$`
        );

        await page.route(
          (url) => patchUrlPattern.test(url.pathname),
          async (route) => {
            const method = route.request().method();
            if (method === 'PATCH') {
              // Forward the request to the server but hold the response
              const response = await route.fetch();
              console.log(
                'Intercepted PATCH response for record A, holding...'
              );

              // Wait until we release it
              await patchDelayPromise;
              console.log('Releasing delayed PATCH response for record A');

              await route.fulfill({ response });
            } else {
              await route.continue();
            }
          }
        );

        // Edit the Description field on record A (triggers PATCH on blur)
        const descInput = getDescriptionInput(page);
        await descInput.waitFor({ state: 'visible' });
        await descInput.click();
        await page.keyboard.press('Control+a');
        await descInput.fill('Edited on A while delayed');
        await page.keyboard.press('Tab');

        // Give the PATCH request time to be sent and intercepted
        await page.waitForTimeout(500);

        // Navigate to record B while PATCH is still in-flight
        await page.goto(
          `${FRONTEND_BASE_URL}/window/${TEST_WINDOW_ID}/${recordIdB}`
        );
        await page.waitForURL(
          new RegExp(`/window/${TEST_WINDOW_ID}/${recordIdB}`),
          { timeout: SLOW_ACTION_TIMEOUT }
        );
        await page.waitForTimeout(1000);

        // Now release the delayed PATCH response
        fulfillPatch();
        // Give the response time to be processed (or discarded)
        await page.waitForTimeout(1000);
      }
    );

    await test.step(
      'Verify record B still shows its own Name (not corrupted)',
      async () => {
        const nameInput = getNameInput(page);
        const nameValue = await nameInput.inputValue();
        console.log(
          `Record B Name after stale PATCH release: "${nameValue}"`
        );
        expect(nameValue).toBe('Record B');
      }
    );

    // Clean up route interception
    await page.unroute(
      (url) => url.pathname.includes('/rest/api/window')
    );

    await test.step(
      'Verify record A saved the edit (navigate back)',
      async () => {
        await gotoRecord(page, recordIdA);

        const descInput = getDescriptionInput(page);
        const descValue = await descInput.inputValue();
        console.log(
          `Record A Description after navigation back: "${descValue}"`
        );
        // The PATCH was sent to the server and fulfilled, so the value should persist
        expect(descValue).toBe('Edited on A while delayed');
      }
    );
  });

  // =========================================================================
  // Test 3: Modal edit syncs to master; field remains editable after close
  // =========================================================================
  test('Modal edit syncs to master, field remains editable after close', async ({
    page,
  }) => {
    allure.severity('critical');
    allure.description(
      'When a field is edited in the advanced edit modal and the modal is closed, ' +
        'the same field must remain editable on the master record. Without the fix, ' +
        'the field became stuck because the master scope only received an optimistic ' +
        'partial update and never got the full server response.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Set initial Name on master', async () => {
      const nameInput = getNameInput(page);
      await setTextField(page, nameInput, 'Before Modal Edit');
      await waitForSave(page);
    });

    await test.step('Open advanced edit modal (Alt+E)', async () => {
      await page.keyboard.press('Alt+e');

      // Wait for the modal to appear
      await page
        .locator('.panel-modal')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('Advanced edit modal opened');
    });

    await test.step('Edit Name field in the modal', async () => {
      // Fields inside the modal are in .panel-modal-content
      const modalNameInput = getNameInput(page, '.panel-modal-content');
      await setTextField(page, modalNameInput, 'Edited In Modal');

      // Wait for the PATCH to complete
      await waitForSave(page);
      console.log('Name edited in modal');
    });

    await test.step('Close the modal', async () => {
      // Press Escape to close
      await page.keyboard.press('Escape');

      // Wait for modal to close
      await page
        .locator('.panel-modal')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {
          // Modal might use hidden instead of detached
          return page
            .locator('.panel-modal')
            .waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT })
            .catch(() => {});
        });
      await page.waitForTimeout(1000);
      console.log('Modal closed');
    });

    await test.step(
      'Verify Name on master shows the modal edit value',
      async () => {
        const nameInput = getNameInput(page);
        const nameValue = await nameInput.inputValue();
        console.log(
          `Name on master after modal close: "${nameValue}"`
        );
        expect(nameValue).toBe('Edited In Modal');
      }
    );

    await test.step(
      'Edit the same Name field on master (must not be stuck)',
      async () => {
        // This is the key assertion: without the fix, this field
        // could not be edited because the master scope had stale
        // field metadata
        const nameInput = getNameInput(page);

        // Intercept the PATCH to verify it actually fires (not blocked)
        const patchPromise = page.waitForResponse(
          (response) =>
            response.url().includes('/rest/api/window') &&
            response.request().method() === 'PATCH' &&
            response.status() === 200,
          { timeout: SLOW_ACTION_TIMEOUT }
        );

        await setTextField(page, nameInput, 'Edited On Master After Modal');

        // Wait for the PATCH response
        const patchResponse = await patchPromise;
        console.log('PATCH fired and returned 200 from master edit');

        await waitForSave(page);
      }
    );

    await test.step('Reload and verify final value persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const nameInput = getNameInput(page);
      const finalName = await nameInput.inputValue();
      console.log(`Name after reload: "${finalName}"`);
      expect(finalName).toBe('Edited On Master After Modal');
    });
  });
});
