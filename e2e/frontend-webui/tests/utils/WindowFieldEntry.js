import { expect } from '@playwright/test';
import { Backend } from './Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from './common';

/**
 * Field entry on a window's record view, the way a user does it, with every field's PATCH awaited.
 *
 * Each helper takes the window id, because the PATCH that commits a field is recognised by the
 * window's REST path plus the field's name in the payload.
 */

/**
 * The response to the PATCH the WebUI issues for `fieldName` on a document of window `windowId`.
 *
 * The listener has to be armed BEFORE the action that commits the field, or a fast response is
 * missed and the wait runs into its timeout. The payload filter (`"path":"<ColumnName>"`) keeps it
 * from settling on a different field's PATCH, and skips the empty-payload PATCH .../NEW that only
 * creates the draft.
 */
export function windowFieldPatch(page, windowId, fieldName) {
  return page.waitForResponse(
    (response) =>
      response.request().method() === 'PATCH' &&
      response.url().includes(`/rest/api/window/${windowId}/`) &&
      (response.request().postData() || '').includes(`"path":"${fieldName}"`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
}

/**
 * Run `commit` — the action that makes the WebUI send `fieldName`'s value — and return once the
 * server has answered it; fails when the server rejected the PATCH.
 */
export async function commitWindowField(page, windowId, fieldName, commit) {
  const patched = windowFieldPatch(page, windowId, fieldName);
  await commit();
  const response = await patched;
  expect(response.ok(), `the WebUI's PATCH of ${fieldName} was rejected: HTTP ${response.status()}`).toBe(true);
  return response;
}

/**
 * Types `value` into `input` key by key, the way a user does: click into it, press the keys, leave
 * it with Tab. Waits for the server's answer to the field's PATCH.
 */
export async function typeIntoWindowField(page, windowId, fieldName, input, value) {
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await commitWindowField(page, windowId, fieldName, async () => {
    await input.click();
    await input.pressSequentially(value);
    await input.press('Tab');
  });
}

/** Unticks a ticked checkbox field the way a user does (click on its box); waits for the PATCH. */
export async function untickWindowCheckbox(page, windowId, fieldName) {
  const checkbox = page.locator(`.form-field-${fieldName} input[type="checkbox"]`).first();
  await expect(checkbox, `${fieldName} starts ticked`).toBeChecked({ timeout: SLOW_ACTION_TIMEOUT });
  await commitWindowField(page, windowId, fieldName, () => page.locator(`.form-field-${fieldName} label.input-checkbox`).first().click());
  await expect(checkbox, `${fieldName} is unticked`).not.toBeChecked();
}

/**
 * Open window `windowId` on a brand-new record and wait until its draft document exists.
 *
 * Opening `/NEW` makes the WebUI PATCH `.../NEW` with an empty payload; that response is what carries
 * the draft's id and its field data. Typing before it lands would patch a document the form does not
 * have yet.
 */
export async function openNewWindowDocument(page, windowId) {
  const draftCreated = page.waitForResponse(
    (response) => response.request().method() === 'PATCH' && response.url().includes(`/rest/api/window/${windowId}/NEW`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}/NEW`);
  await draftCreated;
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * The record id the WebUI put in the URL once the draft was given one. A NEW record is given one
 * even while it is invalid, so the id alone does not prove the row persisted — assert that via the
 * WebAPI or Backend.expect.
 */
export async function savedWindowRecordId(page, windowId) {
  await page.waitForURL(
    (url) => {
      const urlStr = url.toString();
      return urlStr.includes(`/window/${windowId}/`) && !urlStr.includes('/NEW');
    },
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  return page.url().match(new RegExp(`/window/${windowId}/(\\d+)`))[1];
}

/**
 * B2 stored-value expectation: the stored column holds `expectedValue` (`null` = NULL). The answer is
 * pass/fail only; the stored value is never read back.
 */
export async function expectStoredColumnValue(title, { tableName, recordId, columnName, expectedValue }) {
  await Backend.expect({
    title,
    records: {
      [columnName.toLowerCase()]: { tableName, recordId: Number(recordId), columnName, expectedValue },
    },
  });
}
