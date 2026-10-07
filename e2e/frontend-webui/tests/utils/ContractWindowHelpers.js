import { expect } from '@playwright/test';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from './common';
import { getFieldData, getTabRows, waitForRecordSaved, WEBAPI_BASE_URL } from './WebAPIValidation';
import { openRelatedDocument } from './DocumentReferences';

/**
 * Form helpers for the contract windows (Vertrags-Übergang, Vertragsbedingungen, Verträge) and the
 * included tabs and process modals they open. Every helper selects by ColumnName / data-testid only,
 * and every field edit waits until the WebUI has sent the change to the backend.
 */

export const TRANSITION_WINDOW_ID = 540120; // Vertrags-Übergang (C_Flatrate_Transition)
export const CONDITIONS_WINDOW_ID = 540113; // Vertragsbedingungen (C_Flatrate_Conditions)
export const CONTRACT_WINDOW_ID = 540359; // Verträge (C_Flatrate_Term)
const CALENDAR_WINDOW_ID = 117; // Kalenderjahr und Periode (C_Calendar)
const CALENDAR_YEAR_TAB_ID = 129; // C_Year

export const REFUND_CONFIG_TAB_ID = 541106; // Vertragsbedingungen > Rückvergütung (C_Flatrate_RefundConfig)
export const REFUND_PACKING_OPTION_TAB_ID = 549512; // Vertragsbedingungen > Rückvergütung Verpackungsoption

export const TYPE_CONDITIONS_REFUND = 'Refund';
const DURATION_UNIT_MONTH = 'month';

/** Business-partner action "Erzeuge Vertrag" and the partner's related-document link to its terms */
export const CREATE_CONTRACT_PROCESS = 'C_Flatrate_Term_Create_For_BPartners';
export const BPARTNER_TO_CONTRACT_TERM_REFERENCE = 'reference-C_Flatrate_Term';

/** Extract the key of a lookup/list JSON value ({ key, caption }) or return the plain value as string. */
export function lookupKey(value) {
  if (value === null || value === undefined) {
    return null;
  }
  return typeof value === 'object' ? String(value.key) : String(value);
}

/** yyyy-MM-dd of a local date */
export function isoDate(date) {
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
}

/**
 * Run `action` and wait until the change of `fieldName` it triggers has been sent to the backend
 * (the WebUI PATCHes a document / included row / process parameter on each field commit,
 * with a body of [{ op, path: <fieldName>, value }]). With `expectedKey`, only a change to that
 * lookup/list key counts. Returns the PATCH response.
 */
export async function withFieldCommit(page, fieldName, action, expectedKey = undefined) {
  const committed = page.waitForResponse((response) => isFieldPatch(response, fieldName, expectedKey), { timeout: SLOW_ACTION_TIMEOUT });
  await action();
  return await committed;
}

/**
 * Records, in order, the reason of every rejected save (PATCH) in the given window, as the server returned it,
 * so that a spec can compare the message shown in the dialog with it instead of with a localized text.
 * `stop()` stops recording and resolves once every response received so far has been read, so `reasons` is then complete.
 * @returns { reasons, stop }
 */
export function recordRejectedSaveReasons(page, windowId) {
  const reasons = [];
  const pendingReads = [];
  const readReason = async (response) => {
    try {
      const body = await response.json();
      if (!response.ok()) {
        reasons.push(body?.message || null);
        return;
      }
      const documents = Array.isArray(body) ? body : body.documents || [body];
      for (const document of documents) {
        if (document?.saveStatus?.error) {
          reasons.push(document.saveStatus.reason || null);
        }
      }
    } catch (nonJsonResponse) {
      /* not a document response */
    }
  };
  const listener = (response) => {
    if (response.request().method() === 'PATCH' && response.url().includes(`/window/${windowId}/`)) {
      pendingReads.push(readReason(response));
    }
  };
  page.on('response', listener);
  return {
    reasons,
    stop: async () => {
      page.off('response', listener);
      await Promise.all(pendingReads);
    },
  };
}

export async function openNewRecord(page, windowId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}/NEW`);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
}

export async function openRecord(page, windowId, recordId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}/${recordId}`);
  await page.locator('.form-group').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
}

export async function waitForNewRecordId(page, windowId) {
  await page.waitForURL(new RegExp(`/window/${windowId}/\\d+`), { timeout: SLOW_ACTION_TIMEOUT });
  return page.url().split(`/window/${windowId}/`)[1].split(/[/?#]/)[0];
}

export async function fillText(page, scope, fieldName, value) {
  const input = scope.locator(`.form-field-${fieldName} input[type="text"], .form-field-${fieldName} textarea`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.fill(value);
  await withFieldCommit(page, fieldName, () => input.press('Tab'));
}

/** Type a number (with a dot as decimal separator) into a number field and commit it. */
export async function fillNumber(page, scope, fieldName, value) {
  const input = scope.locator(`.form-field-${fieldName} input`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.press('ControlOrMeta+a');
  await input.pressSequentially(String(value));
  return await withFieldCommit(page, fieldName, () => input.press('Tab'));
}

/** Type a date and commit it. The date widget reads a text with dashes as yyyy-MM-dd in every language. */
export async function fillDate(page, scope, fieldName, date) {
  const text = isoDate(date);
  const input = scope.locator(`.form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.press('ControlOrMeta+a');
  await input.fill(text);
  await withFieldCommit(page, fieldName, () => input.press('Tab'));
}

export async function setCheckbox(page, scope, fieldName) {
  const checkbox = scope.locator(`.form-field-${fieldName} input[type="checkbox"]`).first();
  if (!(await checkbox.isChecked())) {
    await withFieldCommit(page, fieldName, () => scope.locator(`.form-field-${fieldName} label.input-checkbox`).first().click());
  }
  await expect(checkbox).toBeChecked();
}

/**
 * Pick the option with the given key in a list field and wait until that key has been committed.
 */
export async function selectListByKey(page, scope, fieldName, key) {
  return await selectOptionByKey(page, scope.locator(`.form-field-${fieldName} input`).first(), fieldName, key);
}

/**
 * Pick the option with the given key in one part of a composed lookup (e.g. the location part of the
 * partner lookup), whose input is rendered as #lookup_<fieldName>, and wait until that key has been committed.
 */
export async function selectLookupPartByKey(page, fieldName, key) {
  return await selectOptionByKey(page, page.locator(`#lookup_${fieldName} input.input-field`).first(), fieldName, key);
}

/**
 * A field that offers a single option selects it by itself as soon as its values have loaded
 * (ListWidget.requestListData, forceSelection): then the PATCH already goes out on the input click and the
 * dropdown may close again before the option can be clicked. So the commit is awaited from the input
 * click on, and the option is clicked only if it shows up before that commit.
 */
async function selectOptionByKey(page, input, fieldName, key) {
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const option = page.locator(`.input-dropdown-list [data-testid="option-${key}"]`).first();
  let committed = false;
  const response = await withFieldCommit(page, fieldName, async () => {
    const autoCommit = page.waitForResponse((r) => isFieldPatch(r, fieldName, key), { timeout: SLOW_ACTION_TIMEOUT })
      .then(() => { committed = true; }, () => {});
    await input.click();
    const optionShown = option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT }).then(() => true, () => false);
    await Promise.race([autoCommit, optionShown]);
    if (!committed) {
      await option.click();
    }
  }, key);
  if (committed && (await page.locator('.input-dropdown-list').count()) > 0) {
    // the auto-selected value is committed, but the dropdown may stay open; leave the field
    // (Escape would close an enclosing modal)
    await input.press('Tab');
  }
  await expect(page.locator('.input-dropdown-list')).toHaveCount(0);
  return response;
}

function isFieldPatch(response, fieldName, key) {
  const request = response.request();
  if (request.method() !== 'PATCH' || !response.url().includes('/rest/api/')) {
    return false;
  }
  const body = request.postDataJSON();
  return Array.isArray(body) && body.some((change) => change.path === fieldName && (key === undefined || lookupKey(change.value) === String(key)));
}

/**
 * Open a list field and pick its first offered option.
 * @returns the key of the picked option
 */
export async function selectFirstListOption(page, scope, fieldName) {
  const input = scope.locator(`.form-field-${fieldName} input`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  const option = page.locator('.input-dropdown-list [data-testid^="option-"]').first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const key = (await option.getAttribute('data-testid')).substring('option-'.length);
  await withFieldCommit(page, fieldName, () => option.click(), key);
  await expect(page.locator('.input-dropdown-list')).toHaveCount(0);
  return key;
}

/** Type into a lookup field and wait until the typeahead result for the full search text has arrived. */
async function typeIntoLookup(page, scope, fieldName, searchText) {
  const input = scope.locator(`.form-field-${fieldName} input.input-field, .form-field-${fieldName} input[type="text"]`).first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await input.click();
  await input.fill('');
  const typeaheadDone = page.waitForResponse(
    (response) => response.url().includes(`/typeahead?query=${encodeURIComponent(searchText)}`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await input.pressSequentially(searchText, { delay: 30 });
  await typeaheadDone;
  const dropdown = page.locator('.input-dropdown-list');
  await dropdown.locator('.input-dropdown-list-option').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return dropdown;
}

/** Search a lookup field and pick the option with the given key. */
export async function selectLookupByKey(page, scope, fieldName, searchText, key) {
  const dropdown = await typeIntoLookup(page, scope, fieldName, searchText);
  const option = dropdown.locator(`[data-testid="option-${key}"]`).first();
  await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await withFieldCommit(page, fieldName, () => option.click(), key);
  await expect(page.locator('.input-dropdown-list')).toHaveCount(0);
}

/** Open the "add new" modal of an included tab and return it. */
export async function openNewIncludedRow(page, tabId) {
  await page.getByTestId(`tab-AD_Tab-${tabId}`).click();
  // The "add new" button of the included tab has no data-testid; it is the filter-panel button
  // that is not the batch-entry toggle.
  const button = page.locator('.tab-pane .table-filter-line .filter-panel-buttons button:not(.close-batch-entry)').first();
  await button.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await button.click();
  const modal = page.locator('.panel-modal').first();
  await modal.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return modal;
}

/** Close an included-row modal (its "Bestätigen" button). */
export async function closeModal(modal) {
  await modal.getByTestId('process-modal-cancel-button').first().click();
  await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
}

/** Open a document action (process) from the three-dot menu and return its parameter modal. */
export async function openAction(page, processValue) {
  await page.locator('.meta-icon-more').first().click();
  await page.locator('.subheader-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const action = page.getByTestId(`action-${processValue}`);
  await action.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await action.click();
  const modal = page.locator('.panel-modal').first();
  await modal.getByTestId('process-modal-start-button').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await modal.locator('.form-group').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return modal;
}

export async function completeDocument(page) {
  await page.getByTestId('status-button').click();
  const co = page.getByTestId('status-CO');
  await co.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await withFieldCommit(page, 'DocAction', () => co.click());
}

export async function expectDocStatus(windowId, recordId, docStatus) {
  await expect.poll(async () => lookupKey((await getFieldData(windowId, recordId, 'DocStatus')).value), {
    message: `DocStatus of ${windowId}/${recordId}`,
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  }).toBe(docStatus);
}

/**
 * @returns the id of a contract calendar offered on the transition that has the given fiscal year,
 * so that the contract's periods exist without relying on a seeded calendar id
 */
async function pickContractCalendarWithYear(transitionId, fiscalYear) {
  const page = getPage();
  const response = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${TRANSITION_WINDOW_ID}/${transitionId}/field/C_Calendar_Contract_ID/dropdown`,
    { headers: { 'Content-Type': 'application/json' } }
  );
  expect(response.ok(), 'contract calendar dropdown').toBeTruthy();
  const calendarIds = ((await response.json()).values ?? []).map((value) => String(value.key));
  for (const calendarId of calendarIds) {
    const years = await getTabRows(CALENDAR_WINDOW_ID, calendarId, `AD_Tab-${CALENDAR_YEAR_TAB_ID}`);
    if ((years ?? []).some((year) => String(year.fieldsByName?.FiscalYear?.value) === String(fiscalYear))) {
      return calendarId;
    }
  }
  throw new Error(`No offered contract calendar has the fiscal year ${fiscalYear}; offered: ${calendarIds.join(', ')}`);
}

/**
 * Vertrags-Übergang (540120): a completed transition of `termDurationMonths` months, whose contract
 * calendar has periods in `fiscalYear`.
 * @returns the transition id
 */
export async function createCompletedTransition(page, { name, fiscalYear, termDurationMonths }) {
  await openNewRecord(page, TRANSITION_WINDOW_ID);
  await fillText(page, page, 'Name', name);
  const transitionId = await waitForNewRecordId(page, TRANSITION_WINDOW_ID);
  const contractCalendarId = await pickContractCalendarWithYear(transitionId, fiscalYear);
  await selectListByKey(page, page, 'C_Calendar_Contract_ID', contractCalendarId);
  await fillNumber(page, page, 'TermDuration', termDurationMonths);
  await selectListByKey(page, page, 'TermDurationUnit', DURATION_UNIT_MONTH);
  await fillNumber(page, page, 'TermOfNotice', 0);
  await selectListByKey(page, page, 'TermOfNoticeUnit', DURATION_UNIT_MONTH);
  await waitForRecordSaved(TRANSITION_WINDOW_ID, transitionId, { maxRetries: 20, retryDelayMs: 500 });
  await completeDocument(page);
  await expectDocStatus(TRANSITION_WINDOW_ID, transitionId, 'CO');
  return transitionId;
}

/**
 * Vertragsbedingungen (540113): new conditions of type refund on the given transition, saved but not completed.
 * @returns the conditions id
 */
export async function createRefundConditions(page, { name, transitionId }) {
  await openNewRecord(page, CONDITIONS_WINDOW_ID);
  await fillText(page, page, 'Name', name);
  const conditionsId = await waitForNewRecordId(page, CONDITIONS_WINDOW_ID);
  await selectListByKey(page, page, 'Type_Conditions', TYPE_CONDITIONS_REFUND);
  await selectListByKey(page, page, 'C_Flatrate_Transition_ID', transitionId);
  await waitForRecordSaved(CONDITIONS_WINDOW_ID, conditionsId, { maxRetries: 20, retryDelayMs: 500 });
  return conditionsId;
}

/** @returns the refund lines (C_Flatrate_RefundConfig) of the conditions, read back from the server */
export async function getRefundConfigRows(conditionsId) {
  return (await getTabRows(CONDITIONS_WINDOW_ID, conditionsId, `AD_Tab-${REFUND_CONFIG_TAB_ID}`)) ?? [];
}

/**
 * Geschäftspartner (123) action "Erzeuge Vertrag": a completed contract term of the partner on the
 * given conditions, starting at `startDate`, opened in Verträge (540359).
 * @returns the id of the created term
 */
export async function createTermForPartner(page, { bpartnerId, conditionsId, startDate }) {
  await openRecord(page, 123, bpartnerId);
  const modal = await openAction(page, CREATE_CONTRACT_PROCESS);
  await selectListByKey(page, modal, 'C_Flatrate_Conditions_ID', conditionsId);
  await fillDate(page, modal, 'StartDate', startDate);
  await expect(modal.locator('.form-field-IsComplete input[type="checkbox"]').first(), 'the term is completed by the action').toBeChecked();

  const processStarted = page.waitForResponse((r) => r.url().includes('/process/') && r.url().endsWith('/start'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
  await modal.getByTestId('process-modal-start-button').click();
  expect((await processStarted).ok(), '"Erzeuge Vertrag" must run without error').toBe(true);
  await modal.waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT });

  await openRelatedDocument({
    dataCy: BPARTNER_TO_CONTRACT_TERM_REFERENCE,
    stepName: 'Business partner - open the created contract term',
    maxRetries: 10,
    retryDelay: 2000,
    refreshOnRetry: true,
  });
  const termId = await waitForNewRecordId(page, CONTRACT_WINDOW_ID);
  await expectDocStatus(CONTRACT_WINDOW_ID, termId, 'CO');
  return termId;
}
