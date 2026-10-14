import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

const LOGIN_USERNAME = 'metasfresh';
const LOGIN_PASSWORD = 'metasfresh';

// Payment Term (C_PaymentTerm): its header field "Discount" is a Number field, which renders the CostPrice widget
const PAYMENT_TERM_WINDOW_ID = 141;
const FIELD = 'Discount';
// its "Schedule" tab (C_PaySchedule) shows "Percentage", also a Number field, as a grid column
const GRID_PERCENTAGE_CELL = '.table-flex-wrapper [data-cy="cell-Percentage"]';

/**
 * Number / CostPrice fields — a decimal comma is sent to the backend as a decimal point.
 *
 * Regression guard: the CostPrice widget (used for the Number and CostPrice widget types) is a single
 * <input type="text">, which passes "2,5" through unchanged; the backend BigDecimal parser rejected it with a
 * NumberFormatException, so DE/AT users could not enter a decimal with the comma. Now the widget normalises the
 * typed value to a dot-decimal before it is patched.
 */

/** Login flow — same as the other specs of this suite (each spec stays self-contained) */
async function loginAsMetasfresh(page) {
  await page.goto('/logout', { waitUntil: 'load' });
  await page.goto('/', { waitUntil: 'load' });

  await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

  await page.locator('input[name="username"]').fill(LOGIN_USERNAME);
  await page.locator('input[name="password"]').fill(LOGIN_PASSWORD);
  await page.locator('.login-container .btn-meta-success').click();

  // Click through role-selection screen if it appears.
  try {
    const roleSubmitBtn = page.locator('.login-container .btn-meta-success:visible');
    await roleSubmitBtn.waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT });
    await roleSubmitBtn.click();
  } catch {
    // No role selection needed.
  }

  await page.locator('.dashboard').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

const isPatchOf = (fieldName) => (req) =>
  req.method() === 'PATCH' && (req.postData() || '').includes(`"${fieldName}"`);

/** The document of a PATCH response: the changed fields and whether the document is saved */
const patchedDocument = async (patchResponse) => (await patchResponse.json()).documents?.[0];

const recordIdOf = (page) => page.url().match(new RegExp(`/window/${PAYMENT_TERM_WINDOW_ID}/(\\d+)`))[1];

/** Reads the payment term from the webapi, with its save status */
async function loadDocument(page, recordId) {
  const response = await page.request.get(`${WEBAPI_BASE_URL}/window/${PAYMENT_TERM_WINDOW_ID}/${recordId}`);
  expect(response.status(), 'status of reading the payment term').toBe(200);
  const body = await response.json();
  return Array.isArray(body) ? body[0] : body.documents?.[0] ?? body;
}

/**
 * Creates a new payment term (ALT+N) and fills its only mandatory field without a default (Name), so that every
 * later change is saved
 */
async function createNewPaymentTerm(page) {
  await test.step('create a new payment term with a name', async () => {
    await page.goto(`/window/${PAYMENT_TERM_WINDOW_ID}`, { waitUntil: 'load' });
    await page.locator('.document-list-wrapper').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.waitForTimeout(1000); // let the shortcut handlers register after navigation
    await page.keyboard.press('Alt+n');
    await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });

    const nameInput = page.locator('.form-field-Name input');
    await nameInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const namePatched = page.waitForResponse((resp) => isPatchOf('Name')(resp.request()), {
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await nameInput.fill(`decimal comma ${Date.now()}`);
    await nameInput.press('Tab'); // leaving the field patches it
    const document = await patchedDocument(await namePatched);
    expect(document.saveStatus?.saved, `payment term saved (${document.saveStatus?.reason})`).toBe(true);
  });
}

/**
 * Shows a caption bar at the bottom of the page for the test video: the field turns a comma into a decimal point
 * on the keystroke, so without it the video would not show what was typed. Clicks pass through it.
 */
async function showCaption(page, text) {
  await page.evaluate((captionText) => {
    let bar = document.getElementById('__e2eCaption');
    if (!bar) {
      bar = document.createElement('div');
      bar.id = '__e2eCaption';
      bar.style.cssText =
        'position:fixed;bottom:0;left:0;right:0;z-index:99999;pointer-events:none;' +
        'background:#222;color:#fff;font:20px monospace;padding:8px 16px';
      document.body.appendChild(bar);
    }
    bar.textContent = captionText;
  }, text);
}

const isFieldPatch = isPatchOf(FIELD);

/**
 * Types (key by key) or pastes (one input event, like the browser's paste) a value into the field, leaves it and
 * asserts what is sent and what the server stored
 */
async function enterAndExpectStored(page, { entered, how, patched, confirmKey = 'Tab' }) {
  await test.step(`${how} ${entered} + ${confirmKey}: sent and stored as ${patched}`, async () => {
    const input = page.locator(`.form-field-${FIELD} input`);
    await showCaption(page, `${FIELD}: ${how} "${entered}" + ${confirmKey} -> expected ${patched}`);
    await input.scrollIntoViewIfNeeded();
    await input.click();
    await input.press('ControlOrMeta+a');
    if (how === 'paste') {
      await input.fill(entered);
    } else {
      await input.pressSequentially(entered, { delay: 150 }); // slow enough to follow on the test video
    }
    // the field keeps what the user entered; it is converted only when it is sent
    await expect(input, 'text in the field after entering').toHaveValue(entered);

    const patchRequested = page.waitForRequest(isFieldPatch, { timeout: SLOW_ACTION_TIMEOUT });
    const patchResponded = page.waitForResponse((resp) => isFieldPatch(resp.request()), {
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await input.press(confirmKey); // Tab (leaving the field) and Enter both patch it

    expect(JSON.parse((await patchRequested).postData()), `PATCH sent for ${FIELD}`).toContainEqual(
      expect.objectContaining({ path: FIELD, value: patched })
    );
    const patchResponse = await patchResponded;
    expect(patchResponse.status(), 'PATCH response status').toBe(200);
    const document = await patchedDocument(patchResponse);
    const storedField = document?.fieldsByName?.[FIELD];
    expect(storedField, `${FIELD} in the PATCH response`).toBeDefined();
    expect(storedField.widgetType, `widget type of ${FIELD}`).toBe('Number');
    expect(Number(storedField.value), `${FIELD} in the PATCH response`).toBe(Number(patched));

    // the PATCH response carries only what changed; the document read back says whether it is saved
    const saved = await loadDocument(page, recordIdOf(page));
    expect(saved.saveStatus?.saved, `payment term saved (${saved.saveStatus?.reason})`).toBe(true);
    expect(saved.saveStatus?.hasChanges, 'unsaved changes of the payment term').toBe(false);
    expect(Number(saved.fieldsByName?.[FIELD]?.value), `${FIELD} stored by the server`).toBe(Number(patched));
    await showCaption(page, `${FIELD}: ${how} "${entered}" + ${confirmKey} -> stored by the server: ${storedField.value}`);
    await page.waitForTimeout(1500); // hold the stored value on the test video
  });
}

// what the user enters (typed key by key or pasted) -> what is patched. Consecutive values differ: an unchanged value
// is not patched.
const ENTRIES = [
  { entered: '2,5', how: 'type', patched: '2.5' }, // DE/AT decimal comma
  { entered: '-12,35', how: 'type', patched: '-12.35' }, // with a minus sign
  { entered: '1.500,50', how: 'type', patched: '1500.50' }, // DE thousands dot + decimal comma
  { entered: "1'250,75", how: 'type', patched: '1250.75' }, // CH thousands apostrophe + decimal comma
  { entered: '1,500.50', how: 'type', patched: '1500.50' }, // EN thousands comma + decimal point
  { entered: '2,000.25', how: 'paste', patched: '2000.25' }, // the same, pasted
  { entered: '7,25', how: 'type', patched: '7.25', confirmKey: 'Enter' }, // confirmed with Enter instead of Tab
  { entered: '3.75', how: 'type', patched: '3.75' }, // a decimal point still works
];

test.describe('Number field - decimal comma', () => {
  test.beforeEach(async ({ page }) => {
    await loginAsMetasfresh(page);
  });

  test('a decimal comma entered into a Number field is stored as a decimal point', async ({ page }) => {
    await createNewPaymentTerm(page);

    for (const entry of ENTRIES) {
      await enterAndExpectStored(page, entry);
    }
  });

  test('a decimal comma typed into a grid cell (Number column) is stored as a decimal point', async ({ page }) => {
    await createNewPaymentTerm(page);

    await test.step('add a schedule line with percentage 100', async () => {
      await page.locator('.filter-panel-buttons button:not(.close-batch-entry)').first().click(); // "Add new"
      const percentageInput = page.locator('.panel-modal .form-field-Percentage input');
      await percentageInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      const percentagePatched = page.waitForResponse((resp) => isPatchOf('Percentage')(resp.request()), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await percentageInput.click();
      await percentageInput.press('ControlOrMeta+a');
      await percentageInput.pressSequentially('100');
      await percentageInput.press('Tab');
      await percentagePatched;
      await page.keyboard.press('Alt+Enter'); // "Done"
      await page.locator('.panel-modal').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      await page.reload(); // the grid shows the new line after a reload
      await page.locator(GRID_PERCENTAGE_CELL).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    await test.step('type 2,5 into the percentage cell + Enter: sent and stored as 2.5', async () => {
      const cell = page.locator(GRID_PERCENTAGE_CELL).first();
      await showCaption(page, 'Schedule line, Percentage cell: type "2,5" + Enter -> expected 2.5');
      await cell.click(); // select the row
      await cell.dblclick(); // on a selected row, a double click opens the in-row editor
      const input = cell.locator('input');
      await expect(input).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      await input.press('ControlOrMeta+a');
      await input.pressSequentially('2,5', { delay: 150 }); // slow enough to follow on the test video
      await expect(input, 'text in the cell editor after typing').toHaveValue('2,5');

      const patchRequested = page.waitForRequest(isPatchOf('Percentage'), { timeout: SLOW_ACTION_TIMEOUT });
      const patchResponded = page.waitForResponse((resp) => isPatchOf('Percentage')(resp.request()), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
      await input.press('Enter');

      expect(JSON.parse((await patchRequested).postData()), 'PATCH sent for Percentage').toContainEqual(
        expect.objectContaining({ path: 'Percentage', value: '2.5' })
      );
      const patchResponse = await patchResponded;
      expect(patchResponse.status(), 'PATCH response status').toBe(200);
      const line = await patchedDocument(patchResponse);
      expect(Number(line?.fieldsByName?.Percentage?.value), 'Percentage stored by the server').toBe(2.5);

      // the server's answer replaces the typed text: the cell holds the stored number, not "2,5"
      await expect(input, 'text in the cell editor after the server answered').toHaveValue('2.5');
      await showCaption(page, `Schedule line, Percentage cell: type "2,5" + Enter -> stored by the server: ${line.fieldsByName.Percentage.value}`);
      await page.waitForTimeout(1500); // hold the stored value on the test video
    });
  });
});
