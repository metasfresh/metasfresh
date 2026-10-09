import { expect } from '@playwright/test';
import { getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from './common';

/**
 * Open the document-status dropdown of the currently displayed document and return the
 * DocAction keys the server offers right now (e.g. ['RE', 'CL', 'VO'] for a completed order).
 *
 * The dropdown keeps the action list of its previous opening in state and re-fetches it only
 * when the status button gains focus, so the button is blurred first and the fresh
 * `.../field/DocAction/dropdown` response is awaited — the returned keys never describe a stale
 * document state.
 *
 * @param {Object} options
 * @param {number|string} options.windowId - AD_Window_ID of the displayed document
 * @param {number|string} options.documentId - record id of the displayed document
 * @returns {Promise<string[]>} the offered DocAction keys
 */
export const openDocumentStatusMenu = async ({ windowId, documentId }) => {
  const page = getPage();
  const statusButton = page.getByTestId('status-button');

  await statusButton.blur();
  const dropdownLoaded = page.waitForResponse(
    (resp) =>
      resp.request().method() === 'GET' &&
      resp.url().endsWith(`/window/${windowId}/${documentId}/field/DocAction/dropdown`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await statusButton.click();
  const resp = await dropdownLoaded;
  const body = await resp.json();
  return (body.values || []).map((value) => value.key);
};

/**
 * Reactivate the currently displayed completed document through the document-status dropdown
 * and wait until it is back in a completable (Drafted / In Progress) state, i.e. the dropdown
 * offers "Complete" (status-CO) again.
 *
 * Every wait is on a concrete event, never a sleep or a spinner heuristic: the dropdown GET that
 * backs each opening, and the reactivate PATCH on `/window/<windowId>/<documentId>`. Each attempt
 * first checks whether the document is already reactivated (CO offered), so a reactivation that
 * landed after a previous attempt's check is recognised instead of hunting for a vanished RE.
 *
 * @param {Object} options
 * @param {number|string} options.windowId - AD_Window_ID of the displayed document
 * @param {number|string} options.documentId - record id of the displayed document
 * @param {number} [options.maxAttempts=3] - how many times the dropdown is opened at most
 * @param {string} [options.notReactivatableHint] - appended to the failure message when the
 *   dropdown offers neither CO nor RE
 */
export const reactivateDocument = async ({
  windowId,
  documentId,
  maxAttempts = 3,
  notReactivatableHint = '',
}) => {
  const page = getPage();
  let reactivated = false;
  let offeredActions = [];

  for (let attempt = 1; attempt <= maxAttempts && !reactivated; attempt++) {
    offeredActions = await openDocumentStatusMenu({ windowId, documentId });

    if (offeredActions.includes('CO')) {
      await expect(page.getByTestId('status-CO')).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      reactivated = true;
    } else if (offeredActions.includes('RE')) {
      const reOption = page.getByTestId('status-RE');
      await expect(reOption).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

      const reactivatePatch = page.waitForResponse(
        (resp) =>
          resp.request().method() === 'PATCH' &&
          resp.url().endsWith(`/window/${windowId}/${documentId}`) &&
          (resp.request().postData() || '').includes('DocAction'),
        { timeout: VERY_SLOW_ACTION_TIMEOUT }
      );
      await reOption.click();
      const patchResp = await reactivatePatch;
      expect(patchResp.ok(), `reactivate PATCH returned HTTP ${patchResp.status()}`).toBe(true);
    }

    await page.keyboard.press('Escape');
  }

  expect(
    reactivated,
    `document did not reactivate to a Drafted (completable) state after ${maxAttempts} attempts; ` +
      `last offered actions: [${offeredActions.join(', ')}]` +
      (notReactivatableHint ? ` — ${notReactivatableHint}` : '')
  ).toBe(true);
};
