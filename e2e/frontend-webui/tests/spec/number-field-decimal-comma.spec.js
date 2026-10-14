import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../utils/common';

const LOGIN_USERNAME = 'metasfresh';
const LOGIN_PASSWORD = 'metasfresh';

// Payment Term (C_PaymentTerm): its header field "Discount" is a Number field, which renders the CostPrice widget
const PAYMENT_TERM_WINDOW_ID = 141;
const FIELD = 'Discount';

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

/** Creates a new payment term (ALT+N) and returns its record id */
async function createNewPaymentTerm(page) {
  await page.goto(`/window/${PAYMENT_TERM_WINDOW_ID}`, { waitUntil: 'load' });
  await page.locator('.document-list-wrapper').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await page.waitForTimeout(1000); // let the shortcut handlers register after navigation
  await page.keyboard.press('Alt+n');
  await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
  await page.locator(`.form-field-${FIELD} input`).waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return page.url().match(new RegExp(`/window/${PAYMENT_TERM_WINDOW_ID}/(\\d+)`))[1];
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

const isFieldPatch = (req) =>
  req.method() === 'PATCH' && (req.postData() || '').includes(`"${FIELD}"`);

/**
 * Types (key by key) or pastes (one input event, like the browser's paste) a value into the field, leaves it and
 * asserts what is sent and what the server stored
 */
async function enterAndExpectStored(page, { entered, how, shown, patched }) {
  await test.step(`${how} ${entered}: sent and stored as ${patched}`, async () => {
    const input = page.locator(`.form-field-${FIELD} input`);
    await showCaption(page, `${FIELD}: ${how} "${entered}" -> expected ${patched}`);
    await input.scrollIntoViewIfNeeded();
    await input.click();
    await input.press('ControlOrMeta+a');
    if (how === 'paste') {
      await input.fill(entered);
    } else {
      await input.pressSequentially(entered, { delay: 150 }); // slow enough to follow on the test video
    }
    await expect(input, 'text in the field after entering').toHaveValue(shown);

    const patchRequested = page.waitForRequest(isFieldPatch, { timeout: SLOW_ACTION_TIMEOUT });
    const patchResponded = page.waitForResponse((resp) => isFieldPatch(resp.request()), {
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await input.press('Tab'); // leaving the field patches it

    expect(JSON.parse((await patchRequested).postData()), `PATCH sent for ${FIELD}`).toContainEqual(
      expect.objectContaining({ path: FIELD, value: patched })
    );
    const patchResponse = await patchResponded;
    expect(patchResponse.status(), 'PATCH response status').toBe(200);
    const body = await patchResponse.json();
    const storedField = body.documents?.[0]?.fieldsByName?.[FIELD];
    expect(storedField, `${FIELD} in the PATCH response`).toBeDefined();
    expect(storedField.widgetType, `widget type of ${FIELD}`).toBe('Number');
    expect(Number(storedField.value), `${FIELD} stored by the server`).toBe(Number(patched));
    await showCaption(page, `${FIELD}: ${how} "${entered}" -> stored by the server: ${storedField.value}`);
    await page.waitForTimeout(1500); // hold the stored value on the test video
  });
}

// what the user enters -> what the field shows -> what is patched; the field normalises while the user types,
// so a typed value is shown with a decimal point. Consecutive values differ: an unchanged value is not patched.
const ENTRIES = [
  { entered: '2,5', how: 'type', shown: '2.5', patched: '2.5' }, // DE/AT decimal comma
  { entered: '-12,35', how: 'type', shown: '-12.35', patched: '-12.35' }, // with a minus sign
  { entered: '1.500,50', how: 'type', shown: '1500.50', patched: '1500.50' }, // DE thousands dot + decimal comma
  { entered: "1'250,75", how: 'type', shown: '1250.75', patched: '1250.75' }, // CH thousands apostrophe + decimal comma
  { entered: '1,500.50', how: 'paste', shown: '1500.50', patched: '1500.50' }, // EN thousands comma + decimal point
  { entered: '3.75', how: 'type', shown: '3.75', patched: '3.75' }, // a decimal point still works
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
});
