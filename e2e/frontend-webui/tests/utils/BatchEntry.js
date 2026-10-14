import { expect } from '@playwright/test';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from './common';

/**
 * Add one line to the currently open document's included tab through batch entry (quick input).
 *
 * The line is entered exactly once - there is no retry. Success is decided by the backend's answer to
 * `POST .../{tabId}/quickInput/{id}/complete` (it must be 2xx) and then by the grid row of the line that
 * request created becoming visible. A non-2xx answer fails right away with the status and the server's
 * error message, e.g. `complete returned 500: Qty shall be greater than zero` when the Qty never reached
 * the backend.
 *
 * Callers must make sure the tab allows new records first (see `waitForTabAllowsNew`).
 *
 * @param {Object} params
 * @param {string} params.tabId - included tab the batch entry belongs to, as used in the REST path (e.g. 'AD_Tab-187')
 * @param {string} params.product - product code or name; the lookup option containing this text is picked
 * @param {string|number} params.quantity - quantity to enter
 */
export const addLineViaBatchEntry = async ({ tabId, product, quantity }) => {
  const page = getPage();

  const batchEntryToggle = page.getByTestId('batch-entry-toggle');
  await batchEntryToggle.scrollIntoViewIfNeeded();
  await batchEntryToggle.click();

  // The fields render only after the quick-input instance was created and its layout loaded
  // (TableQuickInput.initQuickInput), so a visible product field means the form is ready for typing.
  const quickInput = page.locator('.quick-input-container');
  const productInput = quickInput.locator('#lookup_M_Product_ID input.input-field');
  await expect(productInput).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
  await productInput.click();
  await productInput.fill(product);

  const productOption = page.locator('.input-dropdown-list-option').getByText(product).first();
  await expect(productOption, `No lookup option for product "${product}"`).toBeVisible({
    timeout: SLOW_ACTION_TIMEOUT,
  });
  const productPatched = page.waitForResponse((response) => isQuickInputPatchOf(response, tabId, 'M_Product_ID'), {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  productPatched.catch(() => {}); // avoid an unhandled rejection if the click itself throws; awaited below
  await productOption.click();
  const productPatch = await productPatched;
  expect(productPatch.ok(), `Batch entry: setting product "${product}" returned ${productPatch.status()}`).toBe(true);

  const quantityInput = quickInput.getByRole('spinbutton');
  await quantityInput.click();
  await quantityInput.fill(String(quantity));

  const completed = page.waitForResponse((response) => isQuickInputComplete(response, tabId), {
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  });
  completed.catch(() => {}); // avoid an unhandled rejection if the key press itself throws; awaited below
  // After a successful complete the form starts a new quick-input session (TableQuickInput.onSubmit ->
  // initQuickInput). Closing batch entry before that answered would leave it half-initialised.
  const reinitialized = page.waitForResponse((response) => isQuickInputCreate(response, tabId), {
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  });
  reinitialized.catch(() => {}); // not awaited if complete fails
  await page.keyboard.press('Enter');
  const completeResponse = await completed;
  const completeError = completeResponse.ok() ? '' : await serverErrorMessage(completeResponse);
  expect(
    completeResponse.ok(),
    `Batch entry of ${product} x ${quantity}: complete returned ${completeResponse.status()}: ${completeError}`
  ).toBe(true);

  // The response lists the line(s) the backend created; wait for the grid to show them.
  const createdRowIds = (await completeResponse.json()).map((line) => line.rowId).filter(Boolean);
  expect(createdRowIds.length, `Batch entry of ${product} x ${quantity}: complete created no line`).toBeGreaterThan(0);
  for (const rowId of createdRowIds) {
    await expect(page.getByTestId(`table-row-${rowId}`)).toBeVisible({ timeout: VERY_SLOW_ACTION_TIMEOUT });
  }
  const createdRows = page.locator(createdRowIds.map((rowId) => `[data-testid="table-row-${rowId}"]`).join(', '));
  await expect(
    createdRows.filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: product }) }).first(),
    `Batch entry: the created line does not show product "${product}"`
  ).toBeVisible();

  await reinitialized;
  await batchEntryToggle.click();
  await expect(quickInput).toBeHidden({ timeout: SLOW_ACTION_TIMEOUT });
};

/** True for the WebUI's PATCH of the given field of a quick input of the given tab. */
const isQuickInputPatchOf = (response, tabId, fieldName) => {
  const request = response.request();
  if (request.method() !== 'PATCH' || !new URL(request.url()).pathname.includes(`/${tabId}/quickInput/`)) {
    return false;
  }
  let body;
  try {
    body = request.postDataJSON();
  } catch {
    return false; // not a JSON body, so not a field PATCH
  }
  return Array.isArray(body) && body.some((change) => change.path === fieldName);
};

/** True for `POST .../{tabId}/quickInput/{quickInputId}/complete`. */
const isQuickInputComplete = (response, tabId) => {
  const request = response.request();
  const path = new URL(request.url()).pathname;
  return request.method() === 'POST' && path.includes(`/${tabId}/quickInput/`) && path.endsWith('/complete');
};

/** True for `POST .../{tabId}/quickInput`, which starts a new quick-input session. */
const isQuickInputCreate = (response, tabId) => {
  const request = response.request();
  return request.method() === 'POST' && new URL(request.url()).pathname.endsWith(`/${tabId}/quickInput`);
};

/** The `message` of the WebUI's JSON error body (see WebuiExceptionHandler), else the raw body text. */
const serverErrorMessage = async (response) => {
  const text = await response.text().catch(() => '');
  try {
    return JSON.parse(text).message ?? text;
  } catch {
    return text;
  }
};
