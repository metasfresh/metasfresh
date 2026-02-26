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

/**
 * Get a numeric field input locator.
 */
function getNumericInput(page, fieldName, scope = '') {
  const prefix = scope ? `${scope} ` : '';
  return page
    .locator(
      `${prefix}#lookup_${fieldName} input, ${prefix}.form-field-${fieldName} input`
    )
    .first();
}

/**
 * Set a numeric field value and tab to trigger save.
 */
async function setNumericField(page, fieldName, value, scope = '') {
  const input = getNumericInput(page, fieldName, scope);
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await page.keyboard.press('Control+a');
  await input.fill(value.toString());
  await page.keyboard.press('Tab');
  await page.waitForTimeout(500);
}

/**
 * Get the current value of a numeric field as a number.
 * Handles locale-specific decimal separators (de_DE vs en_US).
 */
async function getNumericValue(page, fieldName, scope = '') {
  const input = getNumericInput(page, fieldName, scope);
  const strValue = await input.inputValue();
  if (!strValue || strValue.trim() === '') return null;

  // Determine locale format by checking which separator appears last
  const lastDot = strValue.lastIndexOf('.');
  const lastComma = strValue.lastIndexOf(',');

  let normalized;
  if (lastComma > lastDot) {
    // Comma is decimal separator (de_DE): "1.234,56" or "99,99"
    normalized = strValue.replace(/\./g, '').replace(',', '.');
  } else {
    // Dot is decimal separator (en_US): "1,234.56" or "99.99"
    normalized = strValue.replace(/,/g, '');
  }

  const parsed = parseFloat(normalized);
  return isNaN(parsed) ? null : parsed;
}

/**
 * Toggle a boolean switch/checkbox (IsActive, Processed, etc.).
 * Uses JavaScript click on the hidden checkbox input because Playwright's
 * click on the styled wrapper doesn't toggle the actual checkbox.
 */
async function toggleSwitch(page, fieldName, scope = '') {
  const prefix = scope ? `${scope} ` : '';
  const container = page
    .locator(`${prefix}#lookup_${fieldName}, ${prefix}.form-field-${fieldName}`)
    .first();
  const checkbox = container.locator('input[type="checkbox"]').first();
  await checkbox.evaluate((el) => el.click());
  await page.waitForTimeout(500);
}

/**
 * Get the current state of a boolean switch/checkbox.
 */
async function getSwitchState(page, fieldName, scope = '') {
  const prefix = scope ? `${scope} ` : '';
  const container = page
    .locator(`${prefix}#lookup_${fieldName}, ${prefix}.form-field-${fieldName}`)
    .first();
  const checkbox = container.locator('input[type="checkbox"]').first();
  return await checkbox.isChecked();
}

/**
 * Set a lookup/table-direct field by selecting from a dropdown.
 * Handles both readonly (Table Direct / dropdown) and editable (Search / typeahead) inputs.
 */
async function setLookupField(page, fieldName, searchText, scope = '') {
  const prefix = scope ? `${scope} ` : '';
  const container = page
    .locator(`${prefix}#lookup_${fieldName}, ${prefix}.form-field-${fieldName}`)
    .first();
  const input = container.locator('input').first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  const isReadonly = (await input.getAttribute('readonly')) !== null;

  if (isReadonly) {
    // Table Direct / dropdown: click to open, then select option by text
    await input.click();
  } else {
    // Search / typeahead: clear and type to search
    await input.click();
    await page.keyboard.press('Control+a');
    await input.fill(searchText);
    await page.waitForTimeout(500);
  }

  // Wait for dropdown to appear
  await page.locator('.input-dropdown-list').waitFor({
    state: 'visible',
    timeout: SLOW_ACTION_TIMEOUT,
  });

  // Click the matching option
  await page
    .locator('.input-dropdown-list .input-dropdown-list-option')
    .getByText(searchText)
    .first()
    .click();

  // Wait for dropdown to close
  await page
    .locator('.input-dropdown-list')
    .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});

  await page.keyboard.press('Tab');
  await page.waitForTimeout(500);
}

/**
 * Get the current display value of a lookup/table-direct field.
 */
async function getLookupValue(page, fieldName, scope = '') {
  const prefix = scope ? `${scope} ` : '';
  const container = page
    .locator(`${prefix}#lookup_${fieldName}, ${prefix}.form-field-${fieldName}`)
    .first();
  const input = container.locator('input').first();
  return await input.inputValue();
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

  // =========================================================================
  // Test 4: Modal edit → close → revert to original value on master
  // =========================================================================
  test('Modal edit then revert to original value on master', async ({
    page,
  }) => {
    allure.severity('critical');
    allure.description(
      'After editing a field in the advanced edit modal and closing it, ' +
        'the user sets the same field on the master back to its ORIGINAL value ' +
        '(before the modal edit). Without the fix, the widget\'s cachedValue was ' +
        'never updated from the full server response, so shouldPatch() saw the ' +
        '"new" value as equal to the stale cachedValue and refused to fire the ' +
        'PATCH — the field appeared to accept input but nothing was saved.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Set initial Name on master', async () => {
      const nameInput = getNameInput(page);
      await setTextField(page, nameInput, 'Original Name');
      await waitForSave(page);
    });

    await test.step('Open advanced edit modal (Alt+E)', async () => {
      await page.keyboard.press('Alt+e');

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('Advanced edit modal opened');
    });

    await test.step('Edit Name field in the modal to a different value', async () => {
      const modalNameInput = getNameInput(page, '.panel-modal-content');
      await setTextField(page, modalNameInput, 'Changed In Modal');

      await waitForSave(page);
      console.log('Name changed in modal to "Changed In Modal"');
    });

    await test.step('Close the modal', async () => {
      await page.keyboard.press('Escape');

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {
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
        console.log(`Name on master after modal close: "${nameValue}"`);
        expect(nameValue).toBe('Changed In Modal');
      }
    );

    await test.step(
      'Set Name back to original value (must trigger PATCH)',
      async () => {
        // KEY ASSERTION: setting the field back to "Original Name"
        // must fire a PATCH. Without the fix, cachedValue was still
        // "Original Name" (never updated by the full server response),
        // so shouldPatch() returned false and no PATCH was sent.
        const nameInput = getNameInput(page);

        const patchPromise = page.waitForResponse(
          (response) =>
            response.url().includes('/rest/api/window') &&
            response.request().method() === 'PATCH' &&
            response.status() === 200,
          { timeout: SLOW_ACTION_TIMEOUT }
        );

        await setTextField(page, nameInput, 'Original Name');

        const patchResponse = await patchPromise;
        console.log(
          'PATCH fired and returned 200 when reverting to original value'
        );

        await waitForSave(page);
      }
    );

    await test.step('Reload and verify original value persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const nameInput = getNameInput(page);
      const finalName = await nameInput.inputValue();
      console.log(`Name after reload: "${finalName}"`);
      expect(finalName).toBe('Original Name');
    });
  });

  // =========================================================================
  // Test 5: Numeric field editing on same record works
  // =========================================================================
  test('Numeric field editing on same record works', async ({ page }) => {
    allure.severity('critical');
    allure.description(
      'Regression test: editing numeric fields (Integer, Amount, Qty) on a record ' +
        'and tabbing out must save the values. Verifies that componentDidUpdate ' +
        'JSON.stringify comparison and shouldPatch/equalsByValue work for numeric types.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Set T_Integer=42 and wait for save', async () => {
      await setNumericField(page, 'T_Integer', 42);
      await waitForSave(page);
    });

    await test.step('Set T_Amount=99.99 and wait for save', async () => {
      await setNumericField(page, 'T_Amount', 99.99);
      await waitForSave(page);
    });

    await test.step('Set T_Qty=50 and wait for save', async () => {
      await setNumericField(page, 'T_Qty', 50);
      await waitForSave(page);
    });

    await test.step('Reload and verify all numeric fields persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const intValue = await getNumericValue(page, 'T_Integer');
      console.log(`T_Integer after reload: ${intValue}`);
      expect(intValue).toBe(42);

      const amtValue = await getNumericValue(page, 'T_Amount');
      console.log(`T_Amount after reload: ${amtValue}`);
      expect(amtValue).toBeCloseTo(99.99, 1);

      const qtyValue = await getNumericValue(page, 'T_Qty');
      console.log(`T_Qty after reload: ${qtyValue}`);
      expect(qtyValue).toBe(50);
    });
  });

  // =========================================================================
  // Test 6: Stale PATCH response is discarded after navigation (numeric)
  // =========================================================================
  test('Stale PATCH response is discarded after navigation (numeric fields)', async ({
    page,
  }) => {
    allure.severity('critical');
    allure.description(
      'When a PATCH for a numeric field is in-flight for record A and the user ' +
        'navigates to record B, the response must be discarded. Tests the guard ' +
        'with numeric field values instead of text.'
    );

    // Create two records with distinct T_Integer values
    const recordIdA = await createTestRecord(page);
    console.log(`Created record A: ${recordIdA}`);

    await test.step('Set T_Integer=42 on record A', async () => {
      await setNumericField(page, 'T_Integer', 42);
      await waitForSave(page);
    });

    const recordIdB = await createTestRecord(page);
    console.log(`Created record B: ${recordIdB}`);

    await test.step('Set T_Integer=77 on record B', async () => {
      await setNumericField(page, 'T_Integer', 77);
      await waitForSave(page);
    });

    // Navigate to record A
    await gotoRecord(page, recordIdA);

    await test.step(
      'Intercept PATCH, edit T_Integer on A, navigate to B',
      async () => {
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
              const response = await route.fetch();
              console.log(
                'Intercepted PATCH response for record A (numeric), holding...'
              );
              await patchDelayPromise;
              console.log('Releasing delayed PATCH response for record A (numeric)');
              await route.fulfill({ response });
            } else {
              await route.continue();
            }
          }
        );

        // Edit T_Integer on record A (triggers PATCH on blur)
        const intInput = getNumericInput(page, 'T_Integer');
        await intInput.waitFor({ state: 'visible' });
        await intInput.click();
        await page.keyboard.press('Control+a');
        await intInput.fill('999');
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
      'Verify record B still shows T_Integer=77 (not corrupted)',
      async () => {
        const intValue = await getNumericValue(page, 'T_Integer');
        console.log(
          `Record B T_Integer after stale PATCH release: ${intValue}`
        );
        expect(intValue).toBe(77);
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

        const intValue = await getNumericValue(page, 'T_Integer');
        console.log(
          `Record A T_Integer after navigation back: ${intValue}`
        );
        expect(intValue).toBe(999);
      }
    );
  });

  // =========================================================================
  // Test 7: Multiple rapid field edits on same record
  // =========================================================================
  test('Multiple rapid field edits on same record', async ({ page }) => {
    allure.severity('critical');
    allure.description(
      'Editing multiple fields in rapid succession (Description + T_Integer) ' +
        'on the same record must save both values. Verifies the guard allows ' +
        'both PATCHes (same docId) and componentDidUpdate resets per-widget independently.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Rapidly edit Description and T_Integer', async () => {
      // Edit Description (fill + Tab, don't wait for save)
      const descInput = getDescriptionInput(page);
      await descInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await descInput.click();
      await page.keyboard.press('Control+a');
      await descInput.fill('Rapid edit test');
      await page.keyboard.press('Tab');

      // Immediately edit T_Integer (no waitForSave between)
      const intInput = getNumericInput(page, 'T_Integer');
      await intInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await intInput.click();
      await page.keyboard.press('Control+a');
      await intInput.fill('123');
      await page.keyboard.press('Tab');

      // Now wait for all saves to complete
      await waitForSave(page);
      await page.waitForTimeout(1000);
    });

    await test.step('Reload and verify both fields persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const descInput = getDescriptionInput(page);
      const descValue = await descInput.inputValue();
      console.log(`Description after reload: "${descValue}"`);
      expect(descValue).toBe('Rapid edit test');

      const intValue = await getNumericValue(page, 'T_Integer');
      console.log(`T_Integer after reload: ${intValue}`);
      expect(intValue).toBe(123);
    });
  });

  // =========================================================================
  // Test 8: Boolean switch toggle + modal sync
  // =========================================================================
  test('Boolean switch toggle and modal sync', async ({ page }) => {
    allure.severity('critical');
    allure.description(
      'Toggling a boolean (IsActive) in the advanced edit modal and closing must ' +
        'sync the value to the master view. Then toggling it back on the master ' +
        'must fire a PATCH. Tests modal sync with boolean values and ' +
        'componentDidUpdate with true/false comparison.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Verify IsActive defaults to true', async () => {
      const state = await getSwitchState(page, 'IsActive');
      console.log(`IsActive default: ${state}`);
      expect(state).toBe(true);
    });

    await test.step('Open advanced edit modal (Alt+E)', async () => {
      await page.keyboard.press('Alt+e');

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('Advanced edit modal opened');
    });

    await test.step('Toggle IsActive=false in the modal', async () => {
      await toggleSwitch(page, 'IsActive', '.panel-modal-content');
      await waitForSave(page);

      const modalState = await getSwitchState(page, 'IsActive', '.panel-modal-content');
      console.log(`IsActive in modal after toggle: ${modalState}`);
      expect(modalState).toBe(false);
    });

    await test.step('Close the modal', async () => {
      await page.keyboard.press('Escape');

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {
          return page
            .locator('.panel-modal')
            .waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT })
            .catch(() => {});
        });
      await page.waitForTimeout(1000);
      console.log('Modal closed');
    });

    await test.step(
      'Verify IsActive on master shows false (synced from modal)',
      async () => {
        const masterState = await getSwitchState(page, 'IsActive');
        console.log(`IsActive on master after modal close: ${masterState}`);
        expect(masterState).toBe(false);
      }
    );

    await test.step(
      'Toggle IsActive back to true on master (must fire PATCH)',
      async () => {
        const patchPromise = page.waitForResponse(
          (response) =>
            response.url().includes('/rest/api/window') &&
            response.request().method() === 'PATCH' &&
            response.status() === 200,
          { timeout: SLOW_ACTION_TIMEOUT }
        );

        await toggleSwitch(page, 'IsActive');

        const patchResponse = await patchPromise;
        console.log('PATCH fired and returned 200 for boolean toggle on master');

        await waitForSave(page);
      }
    );

    await test.step('Reload and verify IsActive=true persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const finalState = await getSwitchState(page, 'IsActive');
      console.log(`IsActive after reload: ${finalState}`);
      expect(finalState).toBe(true);
    });
  });

  // =========================================================================
  // Test 9: Lookup field (C_Currency_ID) edit in modal syncs to master
  // =========================================================================
  test('Lookup field edit in modal syncs to master (C_Currency_ID)', async ({
    page,
  }) => {
    allure.severity('critical');
    allure.description(
      'Editing a lookup field (C_Currency_ID) in the advanced edit modal and closing ' +
        'must sync the object-typed value {key, caption} to the master. Then changing ' +
        'the value on the master must fire a PATCH. Tests componentDidUpdate ' +
        'JSON.stringify comparison for complex object values and modal sync.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Open advanced edit modal (Alt+E)', async () => {
      await page.keyboard.press('Alt+e');

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('Advanced edit modal opened');
    });

    await test.step('Set C_Currency_ID=USD in modal', async () => {
      await setLookupField(page, 'C_Currency_ID', 'USD', '.panel-modal-content');
      await waitForSave(page);

      const modalValue = await getLookupValue(page, 'C_Currency_ID', '.panel-modal-content');
      console.log(`C_Currency_ID in modal: "${modalValue}"`);
      expect(modalValue).toContain('USD');
    });

    await test.step('Close the modal', async () => {
      await page.keyboard.press('Escape');

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {
          return page
            .locator('.panel-modal')
            .waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT })
            .catch(() => {});
        });
      await page.waitForTimeout(1000);
      console.log('Modal closed');
    });

    await test.step(
      'Verify C_Currency_ID on master shows USD (synced from modal)',
      async () => {
        const masterValue = await getLookupValue(page, 'C_Currency_ID');
        console.log(
          `C_Currency_ID on master after modal close: "${masterValue}"`
        );
        expect(masterValue).toContain('USD');
      }
    );

    await test.step(
      'Change C_Currency_ID to CHF on master (must fire PATCH)',
      async () => {
        const patchPromise = page.waitForResponse(
          (response) =>
            response.url().includes('/rest/api/window') &&
            response.request().method() === 'PATCH' &&
            response.status() === 200,
          { timeout: SLOW_ACTION_TIMEOUT }
        );

        await setLookupField(page, 'C_Currency_ID', 'CHF');

        const patchResponse = await patchPromise;
        console.log('PATCH fired and returned 200 for lookup change on master');

        await waitForSave(page);
      }
    );

    await test.step('Reload and verify C_Currency_ID=CHF persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const finalValue = await getLookupValue(page, 'C_Currency_ID');
      console.log(`C_Currency_ID after reload: "${finalValue}"`);
      expect(finalValue).toContain('CHF');
    });
  });

  // =========================================================================
  // Test 10: Stale modal PATCH syncs to master when modal closes before response
  // =========================================================================
  test('Stale modal PATCH syncs to master when modal closes before response', async ({
    page,
  }) => {
    allure.severity('critical');
    allure.description(
      'When a PATCH is in-flight from the modal and the modal is closed before ' +
        'the response arrives, the response must be synced to the master view ' +
        'for the same docId. Tests the modal guard branch: !modal.visible → ' +
        'discard from modal scope → sync to master if same docId.'
    );

    const recordId = await createTestRecord(page);
    console.log(`Created Test record: ${recordId}`);

    await test.step('Set initial Description on master', async () => {
      const descInput = getDescriptionInput(page);
      await setTextField(page, descInput, 'Before Modal PATCH');
      await waitForSave(page);
    });

    await test.step('Open advanced edit modal (Alt+E)', async () => {
      await page.keyboard.press('Alt+e');

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('Advanced edit modal opened');
    });

    await test.step(
      'Intercept PATCH, edit Description in modal, close modal',
      async () => {
        let fulfillPatch = null;
        const patchDelayPromise = new Promise((resolve) => {
          fulfillPatch = resolve;
        });

        const patchUrlPattern = new RegExp(
          `/rest/api/window/${TEST_WINDOW_ID}/${recordId}$`
        );

        let interceptedPatch = false;
        await page.route(
          (url) => patchUrlPattern.test(url.pathname),
          async (route) => {
            const method = route.request().method();
            if (method === 'PATCH' && !interceptedPatch) {
              interceptedPatch = true;
              const response = await route.fetch();
              console.log(
                'Intercepted modal PATCH response, holding...'
              );
              await patchDelayPromise;
              console.log('Releasing delayed modal PATCH response');
              await route.fulfill({ response });
            } else {
              await route.continue();
            }
          }
        );

        // Edit Description in the modal
        const modalDescInput = getDescriptionInput(page, '.panel-modal-content');
        await modalDescInput.waitFor({ state: 'visible' });
        await modalDescInput.click();
        await page.keyboard.press('Control+a');
        await modalDescInput.fill('Edited In Modal While Delayed');
        await page.keyboard.press('Tab');

        // Give the PATCH request time to be sent and intercepted
        await page.waitForTimeout(500);

        // Close the modal WHILE the PATCH is still in-flight
        await page.keyboard.press('Escape');
        await page
          .locator('.panel-modal')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {
            return page
              .locator('.panel-modal')
              .waitFor({ state: 'hidden', timeout: FAST_ACTION_TIMEOUT })
              .catch(() => {});
          });
        await page.waitForTimeout(500);
        console.log('Modal closed while PATCH still in-flight');

        // Release the delayed PATCH response
        fulfillPatch();
        // Give the response time to be processed
        await page.waitForTimeout(1000);
      }
    );

    await test.step(
      'Verify Description on master shows the modal-edited value',
      async () => {
        const descInput = getDescriptionInput(page);
        const descValue = await descInput.inputValue();
        console.log(
          `Description on master after delayed modal PATCH: "${descValue}"`
        );
        expect(descValue).toBe('Edited In Modal While Delayed');
      }
    );

    // Clean up route interception
    await page.unroute(
      (url) => url.pathname.includes('/rest/api/window')
    );

    await test.step('Reload and verify persisted', async () => {
      await page.keyboard.press('F5');
      await page.waitForLoadState('networkidle').catch(() => {});
      await page.waitForTimeout(1000);

      const descInput = getDescriptionInput(page);
      const descValue = await descInput.inputValue();
      console.log(`Description after reload: "${descValue}"`);
      expect(descValue).toBe('Edited In Modal While Delayed');
    });
  });
});
