import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { loginWithMasterdataUser } from '../utils/LoginHelper';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../utils/common';
import { DateWidget } from '../utils/widgets/DateWidget';

/**
 * Regression test: creating (or editing) a Price List Version whose ValidFrom collides with an existing one
 * must not break the WebUI (the Price List Schema dropdown must still resolve), and the server's friendly
 * rejection reason must be READABLE INSIDE the input dialog while it is still open — then clear again once
 * the input is corrected or the dialog is closed.
 *
 * Bug: creating a new Price List Version (PLV) whose ValidFrom collides with an existing PLV on the same
 * price list fails the INSERT on the unique index `validfromuniqueindexonpricelist`. Before the fix, the
 * WebUI document cache evicted the error-state root price-list document that still owned the unsaved new
 * in-memory child PLV — so the subsequent GET for the `M_DiscountSchema_ID` (Preislisten-Schema) dropdown
 * returned HTTP 404 (DocumentNotFoundException) and the user was blocked.
 *
 * Fix: DocumentCollection no longer evicts a root that owns an unsaved new included document, so after the
 * failed save the document survives and the dropdown resolves normally.
 *
 * Reproduction data is already present in the seed DB: price list 2008396 ("Testpreise Kunden (Deutschland)")
 * has a PLV dated 2015-01-01, and the unique index + friendly ErrorMsg are deployed. Creating a second PLV
 * dated 2015-01-01 on that price list reproduces the exact failing path.
 */

const WEBAPI_BASE_URL = process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';
const PRICE_LIST_WINDOW_ID = 540321;
const PRICE_LIST_RECORD_ID = 2008396; // "Testpreise Kunden (Deutschland)" — existing PLV dated 2015-01-01
const PLV_TAB_ID = 'AD_Tab-540777';   // Versionen (M_PriceList_Version) included tab
const COLLIDING_DATE = '01/01/2015';  // en_US MM/DD/YYYY — collides with the existing PLV's ValidFrom
const PLV_SEED_VALIDFROM = '2015-01-01'; // the seed PLV's ValidFrom as returned in the row JSON (ISO)

const plvTabRowsUrl = () =>
  `${WEBAPI_BASE_URL}/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/${PLV_TAB_ID}`;

const isPlvPathResponse = (response) => {
  const url = response.url();
  return (
    url.includes(`/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/`) &&
    url.includes(`/${PLV_TAB_ID}/`)
  );
};

// ---------------------------------------------------------------------------
// Network layer — record what the backend returned on the PLV document path
// ---------------------------------------------------------------------------

/**
 * Attach a response listener that records EVERY response on the PLV document path (PATCH saves + GET
 * re-fetches) in arrival order, each tagged with its server-side saveStatus. This is the single source of
 * truth both tests assert against — the failed save, whether it is a user-validation (friendly) rejection,
 * whether the post-failure GET still carries the error (the carve-out), and any 404 (the old bug symptom).
 *
 * Returns `{ recordedResponses, notFoundUrls, currentSeq }` — the two arrays fill live as responses
 * arrive; `currentSeq()` returns the running arrival counter so a test can snapshot a "before this step"
 * boundary and later filter by `entry.seq > snapshot` (robust to responses resolving out of arrival order,
 * unlike slicing by array position). Each entry: `{ seq, method, status, isError, isUserValidationError, reason, isSaved }`.
 */
const recordPlvPathResponses = (page) => {
  const recordedResponses = [];
  const notFoundUrls = [];
  let arrivalSequence = 0;

  page.on('response', async (response) => {
    if (!isPlvPathResponse(response)) {
      return;
    }
    const method = response.request().method();
    const status = response.status();
    const seq = ++arrivalSequence;

    if (status === 404) {
      notFoundUrls.push(`${status} ${response.url()}`);
      recordedResponses.push({ seq, method, status, isError: null, isUserValidationError: null, reason: null, isSaved: null });
      return;
    }
    try {
      const body = await response.json();
      const documents = Array.isArray(body) ? body : body.documents || [body];
      for (const document of documents) {
        if (document && document.saveStatus) {
          recordedResponses.push({
            seq,
            method,
            status,
            isError: !!document.saveStatus.error,
            isUserValidationError: document.saveStatus.exception?.userFriendlyError === true,
            reason: document.saveStatus.reason || null,
            isSaved: !!document.saveStatus.saved,
          });
        }
      }
    } catch (nonJsonResponse) {
      /* non-JSON (lookup/typeahead) — not a document, ignore */
    }
  });

  return { recordedResponses, notFoundUrls, currentSeq: () => arrivalSequence };
};

const failedSaveResponses = (recordedResponses) =>
  recordedResponses.filter((r) => r.method === 'PATCH' && r.isError === true);

// ---------------------------------------------------------------------------
// Test-data layer
// ---------------------------------------------------------------------------

/**
 * A unique, far-future ValidFrom that cannot collide with the seed 2015-01-01 PLV, today, or a PLV left by a
 * prior local run (each call picks a different random date in ~2044..2099). Format MM/DD/YYYY (en_US).
 */
const uniqueFarFutureValidFrom = () => {
  const date = new Date(Date.UTC(2099, 11, 31));
  date.setUTCDate(date.getUTCDate() - Math.floor(Math.random() * 20000));
  const month = String(date.getUTCMonth() + 1).padStart(2, '0');
  const day = String(date.getUTCDate()).padStart(2, '0');
  return `${month}/${day}/${date.getUTCFullYear()}`;
};

// ---------------------------------------------------------------------------
// Fixture layer — bring the shared price list back to its clean seed state
// ---------------------------------------------------------------------------

/**
 * Normalise the target price list to its clean, CI-equivalent state: exactly the seed 2015-01-01 PLV.
 * Call this in every test AFTER login and BEFORE touching the included tab — all tests here depend on it,
 * so it must run at the start of each (every test leaves at least one residual non-seed PLV row behind that
 * the next one must clear, and any test can inherit a wedge from a prior local run or a manual UAT).
 *
 * A prior colliding run — or the manual UAT of this very fix — can leave a NON-seed PLV row cached with a
 * KEPT user-validation error (an unsaved in-memory edit to a colliding date). That kept error is precisely
 * the behaviour under test, so a plain webapi cache reset does NOT evict it (the carve-out keeps
 * user-validation errors) — verified: resetByTable left the wedge in place. Such a wedged row sets
 * allowCreateNew=false and hides the included-tab "Add new" button, blocking the run. Deleting every row
 * that is not the CLEAN seed 2015 row — via the same WebUI rows endpoint the frontend uses (trailing slash
 * + orderBy), so cached in-memory-errored rows are visible, unlike the plain rows GET — discards the wedge
 * and restores the clean state. On a fresh seed DB (a clean CI shard) the GET returns only the seed row, so
 * the loop deletes nothing; within this file every test leaves a persisted far-future PLV row behind, which
 * the next test's call then removes — so the loop does real work on the normal in-file run, not only on a
 * wedged stack.
 * NOTE: price list 2008396 ("Testpreise Kunden") is a dedicated automated-test fixture — this loop deletes
 * every PLV on it that is not the clean 2015 seed; do not create unrelated PLVs there.
 */
const normalizePriceListToSeed = async (page) => {
  const isCleanSeedRow = (row) => {
    const validFrom = String((((row.fieldsByName || {}).ValidFrom) || {}).value || '');
    return validFrom.startsWith(PLV_SEED_VALIDFROM) && (row.saveStatus || {}).error !== true;
  };

  const rowsResponse = await page.request.get(`${plvTabRowsUrl()}/?orderBy=-ValidFrom`);
  expect(rowsResponse.ok(), `PLV rows GET should succeed (HTTP ${rowsResponse.status()})`).toBe(true);
  const rowsBody = await rowsResponse.json();
  const existingRows = Array.isArray(rowsBody) ? rowsBody : rowsBody.result || rowsBody.documents || [];

  // Guard the self-heal: the clean 2015 seed PLV MUST be recognized before we delete anything. If a future
  // backend change alters the row-value shape (adds a time/zone component, changes the envelope),
  // isCleanSeedRow would stop matching the seed and the loop below would delete the shared fixture all
  // tests depend on — with no failure pinpointing the cause. Fail loud here instead, before any delete.
  expect(
    existingRows.some(isCleanSeedRow),
    'the clean 2015 seed PLV must be recognized before self-heal — else the delete loop would remove the shared fixture'
  ).toBe(true);

  for (const row of existingRows) {
    const rowId = String(row.rowId != null ? row.rowId : row.id);
    if (!rowId || rowId === 'undefined' || isCleanSeedRow(row)) {
      continue;
    }
    const deleteResponse = await page.request.delete(`${plvTabRowsUrl()}/${rowId}`);
    expect(deleteResponse.ok(), `deleting residue/wedged PLV row ${rowId} should succeed (HTTP ${deleteResponse.status()})`).toBe(true);
  }
};

// ---------------------------------------------------------------------------
// UI layer — drive the price-list window / its included PLV tab
// ---------------------------------------------------------------------------

const loginAndOpenCleanPriceList = async (page) => {
  // Login as the admin masterdata user (sees the seed price lists). The simple helper handles the
  // multi-role chooser by re-clicking — LoginPage.login waits for the wrong endpoint here.
  await loginWithMasterdataUser(page, { username: 'metasfresh', password: 'metasfresh' });
  await DashboardPage.expectVisible();
  // Clear any wedge from a prior run: without this a residual errored PLV sets allowCreateNew=false and the
  // "Add new" button never appears → the run times out. On a fresh CI DB this is a no-op.
  await normalizePriceListToSeed(page);
  await page.goto(`${FRONTEND_BASE_URL}/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}`);
  await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await waitForSpinnersToSettle(page);
};

const waitForSpinnersToSettle = (page) =>
  page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

/** Click the included PLV tab's "Add new" and wait for the new row's detail (ValidFrom field) to render. */
const addNewPlvRow = async (page) => {
  // Language-independent selector (stable button classes, NOT the localized "Add new" caption) — covers
  // both the inline-tab and the table-filter renderers; excludes the batch-entry toggle.
  const addNewButton = page
    .locator('.inlinetab-action-button button, .table-filter-line .filter-panel-buttons button.btn-distance:not(.close-batch-entry)')
    .first();
  await addNewButton.scrollIntoViewIfNeeded();
  await addNewButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await addNewButton.click();

  await page
    .waitForURL(new RegExp(`/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/${PLV_TAB_ID}/\\d+`), { timeout: SLOW_ACTION_TIMEOUT })
    .catch(() => {});
  await waitForSpinnersToSettle(page);
  await page.locator('.form-field-ValidFrom input').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
};

const setValidFrom = (validFrom) => DateWidget.setValue('ValidFrom', validFrom);

/** Open the Preislisten-Schema (M_DiscountSchema_ID) dropdown; resolve true if its list opens (no 404). */
const openSchemaDropdownAndAwaitList = async (page) => {
  const schemaInput = page
    .locator('#lookup_M_DiscountSchema_ID input, .form-field-M_DiscountSchema_ID input')
    .first();
  await schemaInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await schemaInput.click();
  return page
    .locator('.input-dropdown-list')
    .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
    .then(() => true)
    .catch(() => false);
};

// ---------------------------------------------------------------------------
// UI layer — the save-error surface (the Indicator) inside the record dialog
// ---------------------------------------------------------------------------

// The record dialog renders its own Indicator inside its panel — `<div class="panel panel-modal
// panel-modal-primary">` (Modal.renderPanel) — and the Indicator renders the ERROR state as
// `<div class="bar error">` plus the reason as `<span class="text">` inside
// `.window-indicator-container .message-bar` (IndicatorState.ERROR === 'error'; see
// components/app/Indicator.js). All three are structural classes — no localized text involved.
//
// Scoping to `.panel-modal` is what makes an assertion mean "IN the dialog": the master window
// renders the very same `.window-indicator-container` markup in its Header, so an unscoped,
// page-wide locator would be satisfied by the master window's indicator just as well. (Today the
// master Header's Indicator happens not to be rendered while a modal is open — MasterWindow passes
// `modalHidden={!modal.visible}` to Container, which forwards it as Header's `showIndicator` — but
// that is an implementation detail of another component; scoping pins the requirement instead of
// depending on it.)
const modalErrorIndicator = (page) => page.locator('.panel-modal .window-indicator-container .bar.error');
const modalErrorReasonText = (page) => page.locator('.panel-modal .window-indicator-container .message-bar .text');
const openDialogPanel = (page) => page.locator('.panel-modal');
/** Every save-error surface on the page — the dialog's AND the master window's Header indicator. */
const anyErrorIndicator = (page) => page.locator('.window-indicator-container .bar.error');

/**
 * The user-visible half of the fix: the friendly rejection reason must be READABLE INSIDE the input
 * dialog WHILE IT IS STILL OPEN (the customer's complaint was that it appeared only *after* the
 * dialog closed, too briefly to read).
 *
 * `expectedReason` is the reason the SERVER actually returned on the rejecting save, captured from
 * the response recorder — never a hardcoded localized literal. So the assertion pins the exact text
 * the user reads while staying language-independent.
 */
const expectRejectionReasonShownInOpenDialog = async (page, expectedReason) => {
  expect(
    expectedReason,
    'the rejecting save must carry a non-empty friendly reason — there would be nothing to display otherwise',
  ).toBeTruthy();
  await expect(openDialogPanel(page), 'the input dialog must still be OPEN while the reason is asserted').toHaveCount(1);
  await expect(modalErrorIndicator(page), 'the error indicator must be shown INSIDE the still-open input dialog').toBeVisible({
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await expect(
    modalErrorReasonText(page),
    'the friendly reason text must be READABLE inside the still-open input dialog (not merely an error-coloured bar)',
  ).toHaveText(expectedReason, { timeout: SLOW_ACTION_TIMEOUT });
};

/** The dialog is still open, but the rejecting condition is gone — its error surface must be empty. */
const expectErrorSurfaceClearedInOpenDialog = async (page) => {
  await expect(openDialogPanel(page), 'the input dialog must still be OPEN for this check').toHaveCount(1);
  await expect(modalErrorIndicator(page), 'the error indicator must clear once the corrected save succeeds').toHaveCount(0, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
  await expect(modalErrorReasonText(page), 'the reason text must disappear once the corrected save succeeds').toHaveCount(0, {
    timeout: SLOW_ACTION_TIMEOUT,
  });
};

/** Close the dialog, accepting the "abandon changes?" confirm the pending rejected edit triggers. */
const closeDialogAbandoningChanges = async (page) => {
  page.on('dialog', (dialog) => dialog.accept());
  // Language-invariant Done/close button of the record modal (Modal.js — data-testid).
  const doneButton = page.locator('[data-testid="process-modal-cancel-button"]').first();
  await doneButton.click();
  await doneButton.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  await waitForSpinnersToSettle(page);
};

test.describe('PriceListVersion — colliding ValidFrom', () => {
  test('colliding new PLV: the friendly reason is shown IN the open dialog, clears on correction, AND the Preislisten-Schema dropdown still resolves (no 404)', async ({ page }) => {
    allure.epic('E0260: Pricing');
    allure.tag('F32070: Price List Copy using Price List Schema');
    allure.tag('F32070');
    allure.story('Creating a PLV with a duplicate ValidFrom shows the reason in the dialog and must not break the document (no 404 on the schema dropdown)');
    allure.severity('critical');
    allure.description(`
After a colliding-ValidFrom PLV save fails on the unique index:
1. the friendly translated reason must be READABLE INSIDE the input dialog while it is still open
   (the reported bug: the reason surfaced only after the dialog closed, too briefly to read),
2. correcting the date to a free value must make the save succeed and the reason disappear, and
3. the \`Preislisten-Schema\` (M_DiscountSchema_ID) dropdown must still open — before the fix it
   returned HTTP 404 (DocumentNotFoundException) because the document-cache evicted the root owning
   the unsaved new child PLV.
    `);

    test.setTimeout(120000);

    const { recordedResponses, notFoundUrls, currentSeq } = recordPlvPathResponses(page);

    await loginAndOpenCleanPriceList(page);
    await addNewPlvRow(page);

    // Set the colliding ValidFrom → auto-save → fails on the unique index. Wait deterministically for the
    // failed save to arrive (no fixed sleep): the recorder captures the server-side saveStatus error.
    await setValidFrom(COLLIDING_DATE);
    await expect
      .poll(() => failedSaveResponses(recordedResponses).length, {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'colliding PLV save should fail server-side with the duplicate-date error',
      })
      .toBeGreaterThan(0);

    // ON SCREEN, IN THE OPEN DIALOG: the friendly reason the server returned must be readable right
    // there, while the dialog is still open — the half of the bug the user actually experiences.
    // Gate on the post-failure re-fetch GET having landed FIRST (the frontend reacts to the
    // staleRootDocument websocket event and re-GETs the document): that is the exact moment at which
    // the old behaviour replaced the errored document with a clean one and the on-screen reason
    // vanished. Asserting only *before* it would be satisfiable by the ~1s pre-fix flash.
    const rejectingSave = failedSaveResponses(recordedResponses).pop();
    await expect
      .poll(() => recordedResponses.some((r) => r.seq > rejectingSave.seq && r.method === 'GET' && r.isError !== null), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the post-failure re-fetch GET on the PLV path should arrive before the on-screen reason is asserted',
      })
      .toBeTruthy();
    await expectRejectionReasonShownInOpenDialog(page, rejectingSave.reason);

    // THE FIX UNDER TEST: open the Preislisten-Schema dropdown. Before the fix this triggered a 404
    // (DocumentNotFoundException) on the evicted document; now the list resolves.
    const dropdownOpened = await openSchemaDropdownAndAwaitList(page);

    const failedSaves = failedSaveResponses(recordedResponses);
    console.log(`[plv-collision] failed saves: ${JSON.stringify(failedSaves)}`);
    allure.attachment('Failed PLV saves', JSON.stringify(failedSaves, null, 2), 'application/json');

    // (a) the bug condition was actually hit: the colliding save failed server-side with a USER-VALIDATION
    //     (friendly) rejection. Asserted via the language-invariant userFriendlyError flag, not localized text.
    expect(
      failedSaves.some((r) => r.isUserValidationError === true),
      `the colliding new PLV save must fail with a user-validation (friendly) error. Failed saves: ${JSON.stringify(failedSaves)}`,
    ).toBe(true);
    // (b) THE FIX: the document was NOT evicted → no 404 on the PLV document path (the exact symptom).
    expect(notFoundUrls, `no 404 on the PLV document path (the colliding-ValidFrom bug). Captured: ${JSON.stringify(notFoundUrls)}`).toEqual([]);
    // (c) user-visible proof: the Preislisten-Schema dropdown still opens after the failed save.
    expect(dropdownOpened, 'the Preislisten-Schema dropdown opens (document not lost)').toBe(true);

    // (d) THE ERROR CLEARS ON CORRECTION: the displayed reason must not outlive the rejecting
    //     condition. Correct ValidFrom to a free, unique date -> the save succeeds -> the dialog's
    //     error surface (bar + reason text) goes away, without closing the dialog.
    const correctedValidFrom = uniqueFarFutureValidFrom();
    const beforeCorrectionSeq = currentSeq();
    await setValidFrom(correctedValidFrom);
    await expect
      .poll(
        () =>
          recordedResponses.some(
            (r) => r.seq > beforeCorrectionSeq && r.method === 'PATCH' && r.isSaved === true && r.isError === false,
          ),
        {
          timeout: SLOW_ACTION_TIMEOUT,
          message: `correcting ValidFrom to the free date ${correctedValidFrom} should save successfully`,
        },
      )
      .toBeTruthy();
    await expectErrorSurfaceClearedInOpenDialog(page);
  });

  test('edit-existing PLV to a colliding date: the USER-VALIDATION error PERSISTS (not self-heal-evicted), its reason is shown IN the open dialog, and it clears when the dialog is closed', async ({ page }) => {
    allure.epic('E0260: Pricing');
    allure.tag('F32070: Price List Copy using Price List Schema');
    allure.tag('F32070');
    allure.story('Editing an already-persisted PLV to a duplicate ValidFrom keeps the rejection (error not evicted / not reverted)');
    allure.severity('critical');
    allure.description(`
Carve-out regression: editing an ALREADY-PERSISTED Price List Version to a colliding ValidFrom is a
user-fixable business rejection (unique-index violation). The document-cache self-heal MUST NOT evict the
errored root on the child invalidation that follows — so the error and the rejected value are KEPT. Before
the fix the root was evicted, so the stale-triggered re-fetch returned a CLEAN saveStatus and the on-screen
error flashed ~1s then reverted. This test proves the failing edit's PATCH reports \`saveStatus.error===true\`
AND the subsequent GET on the PLV document path STILL reports \`saveStatus.error===true\` (document not evicted),
with no 404 on that path. On screen it proves the friendly reason is readable INSIDE the still-open dialog, and
that closing the dialog clears the error surface again (kept-visible must not mean stuck).
    `);

    test.setTimeout(120000);

    const { recordedResponses, notFoundUrls, currentSeq } = recordPlvPathResponses(page);

    await loginAndOpenCleanPriceList(page);
    await addNewPlvRow(page);

    // Give ourselves an ALREADY-PERSISTED PLV to then edit into a collision: set a unique future ValidFrom
    // and wait for it to save successfully (persisted, no error). Boundaries are snapshotted via currentSeq()
    // and filtered by entry.seq (not array position) so overlapping responses resolving out of order can't
    // mis-attribute an entry to the wrong step.
    const persistedValidFrom = uniqueFarFutureValidFrom();
    const beforePersistSeq = currentSeq();
    await setValidFrom(persistedValidFrom);
    await expect
      .poll(() => recordedResponses.some((r) => r.seq > beforePersistSeq && r.method === 'PATCH' && r.isSaved === true && r.isError === false), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: `the future-dated PLV (${persistedValidFrom}) should save successfully (persisted, no error)`,
      })
      .toBeTruthy();

    // Now EDIT that persisted PLV's ValidFrom to a colliding date (the edit-existing path the fix targets).
    const beforeEditSeq = currentSeq();
    await setValidFrom(COLLIDING_DATE);

    // PRIMARY PROOF (a): the failing edit's PATCH reports a server-side error.
    await expect
      .poll(() => recordedResponses.some((r) => r.seq > beforeEditSeq && r.method === 'PATCH' && r.isError === true), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the colliding edit should fail server-side with the duplicate-date error',
      })
      .toBeTruthy();
    const failingEditPatch = recordedResponses.filter((r) => r.seq > beforeEditSeq && r.method === 'PATCH' && r.isError === true).pop();

    // PRIMARY PROOF (b) — THE CARVE-OUT: the document is NOT evicted. The stale-triggered re-fetch after the
    // failed save (frontend reacts to the websocket staleRootDocument event) issues a GET on the PLV document
    // path that STILL carries error===true. Before the fix this GET returned a clean (evicted-and-rebuilt)
    // document with error===false, and the on-screen error reverted.
    await expect
      .poll(() => recordedResponses.some((r) => r.seq > failingEditPatch.seq && r.method === 'GET' && r.isError === true), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the post-failure re-fetch GET on the PLV path must STILL report error===true (document not evicted)',
      })
      .toBeTruthy();

    console.log(`[plv-edit-collision] responses: ${JSON.stringify(recordedResponses)}`);
    allure.attachment('PLV-path responses (arrival order)', JSON.stringify(recordedResponses, null, 2), 'application/json');
    const postFailureGets = recordedResponses.filter((r) => r.seq > failingEditPatch.seq && r.method === 'GET' && r.isError !== null);

    // SECONDARY PROOF (on screen): the user actually SEES the friendly reason, inside the still-open dialog
    // (the user-visible half of the bug, "nicht oder nur sehr kurz angezeigt"). Persistence (no revert) is
    // proven deterministically by assertion (b): the post-failure GET STILL reports error===true, so no clean
    // document arrives to revert the render. The locators auto-wait, so these are hard assertions that still
    // tolerate render timing.
    await page.waitForLoadState('networkidle', { timeout: FAST_ACTION_TIMEOUT }).catch(() => {});
    await expectRejectionReasonShownInOpenDialog(page, failingEditPatch.reason);

    // (a) bug condition hit: the colliding edit failed server-side with a USER-VALIDATION (friendly) rejection.
    expect(
      failingEditPatch.isUserValidationError,
      `the colliding edit must fail with a user-validation (friendly) error. Failing PATCH: ${JSON.stringify(failingEditPatch)}`,
    ).toBe(true);
    // (b) THE CARVE-OUT: at least one post-failure GET on the PLV path retained error===true (not evicted).
    expect(
      postFailureGets.some((r) => r.isError === true),
      `the post-failure GET on the PLV path must retain error===true (carve-out: document not evicted). Post-failure GETs: ${JSON.stringify(postFailureGets)}`,
    ).toBe(true);
    // (c) the exact old symptom must be absent: no 404 on the PLV document path.
    expect(notFoundUrls, `no 404 on the PLV document path. Captured: ${JSON.stringify(notFoundUrls)}`).toEqual([]);

    // (d) THE ERROR CLEARS WHEN THE DIALOG IS CLOSED: kept-visible must not mean stuck. Closing the
    //     dialog (abandoning the rejected edit) must leave NO error surface anywhere — neither the
    //     dialog's (it is gone) nor the master window's Header indicator, which takes over rendering
    //     once the dialog closes. toHaveCount(0) auto-retries, so it tolerates the brief moment (~0.5s,
    //     observed) in which the closing dialog's error state is still mirrored on the master indicator.
    await closeDialogAbandoningChanges(page);
    await expect(openDialogPanel(page), 'the input dialog must be closed').toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    await expect(
      anyErrorIndicator(page),
      'closing the dialog must clear the error surface everywhere (dialog gone, master window indicator not in error)',
    ).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
  });

  test('abandon after a persisted PLV colliding edit: the persisted row STAYS in the parent grid without reload (DB row intact)', async ({ page }) => {
    allure.epic('E0260: Pricing');
    allure.tag('F32070: Price List Copy using Price List Schema');
    allure.tag('F32070');
    allure.story('Abandoning a failed colliding edit of an already-persisted PLV must NOT drop the persisted row from the parent grid');
    allure.severity('critical');
    allure.description(`
After a NEW Price List Version auto-saves (real DB row), the user edits its ValidFrom to a colliding
date -> the save fails with a KEPT user-validation error -> the user presses Done and accepts
"abandon changes". The already-persisted row must REMAIN in the parent included-tab grid WITHOUT a
browser reload (the DB row is never deleted): abandoning re-queries the persisted row and reverts the
grid row to its DB value in place, rather than dropping the persisted row client-side. This test
asserts: (a) the row still exists server-side (DB intact), (b) it stays in the parent grid without a
reload, showing its reverted DB value.
    `);

    test.setTimeout(120000);

    const { recordedResponses, notFoundUrls, currentSeq } = recordPlvPathResponses(page);

    await loginAndOpenCleanPriceList(page);
    await addNewPlvRow(page);

    // (1) Persist a NEW PLV at a unique far-future date (auto-save). This is the row that must survive.
    const persistedValidFrom = uniqueFarFutureValidFrom();
    const persistedYear = persistedValidFrom.slice(-4); // locale-independent anchor (year digits)
    // MM/DD/YYYY (en_US widget input) -> YYYY-MM-DD (the ISO shape the rows GET returns in fieldsByName.ValidFrom.value).
    const [pMonth, pDay, pYear] = persistedValidFrom.split('/');
    const persistedValidFromIso = `${pYear}-${pMonth}-${pDay}`;
    const beforePersistSeq = currentSeq();
    await setValidFrom(persistedValidFrom);
    await expect
      .poll(() => recordedResponses.some((r) => r.seq > beforePersistSeq && r.method === 'PATCH' && r.isSaved === true && r.isError === false), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: `the future-dated PLV (${persistedValidFrom}) should save successfully (persisted, no error)`,
      })
      .toBeTruthy();

    // The DB row is identified by its unique far-future ValidFrom (this build does NOT expose the child rowId
    // in the modal URL — the URL stays /window/540321/2008396). We assert the DB row survives by matching this
    // ValidFrom in the rows GET below; the colliding edit is rejected, so the DB keeps this far-future value.
    const validFromOf = (row) => String((((row.fieldsByName || {}).ValidFrom) || {}).value || '');

    // (2) Edit that persisted PLV to a COLLIDING date → save fails with a KEPT user-validation error.
    const beforeEditSeq = currentSeq();
    await setValidFrom(COLLIDING_DATE);
    await expect
      .poll(() => recordedResponses.some((r) => r.seq > beforeEditSeq && r.method === 'PATCH' && r.isError === true), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the colliding edit should fail server-side with the duplicate-date error',
      })
      .toBeTruthy();
    const failingEditPatch = recordedResponses.filter((r) => r.seq > beforeEditSeq && r.method === 'PATCH' && r.isError === true).pop();
    expect(
      failingEditPatch.isUserValidationError,
      `the colliding edit must fail with a user-validation (friendly) error. Failing PATCH: ${JSON.stringify(failingEditPatch)}`,
    ).toBe(true);

    // The new PLV is created/edited in a record MODAL; while it is open the parent included-tab grid row
    // reflects the in-memory (colliding) edit, and the modal overlays it — so the far-future value is only
    // observable in the parent grid AFTER the modal closes. Hence all grid assertions are made post-Done.
    const gridRows = () => page.locator('.table tbody tr, table tbody tr');
    // Scope the year match to the ValidFrom cell (data-cy="cell-<ColumnName>") rather than the whole
    // row, so an incidental 4-digit match elsewhere in the row can't satisfy the locator.
    const seedYear = PLV_SEED_VALIDFROM.slice(0, 4);
    const plvGridRowsWithYear = () =>
      page.locator(
        `.table tbody tr:has([data-cy="cell-ValidFrom"]:has-text("${persistedYear}")), table tbody tr:has([data-cy="cell-ValidFrom"]:has-text("${persistedYear}"))`
      );
    const plvGridRowsWithSeed = () =>
      page.locator(
        `.table tbody tr:has([data-cy="cell-ValidFrom"]:has-text("${seedYear}")), table tbody tr:has([data-cy="cell-ValidFrom"]:has-text("${seedYear}"))`
      );

    // (3) Press Done and ACCEPT the abandon-changes confirm (dirty window modal → window.confirm dialog).
    //     The helper also waits for the modal to close, so the parent included-tab grid is the queried DOM.
    //     `Modal.closeModal()` deliberately does NOT await `MasterWindow.closeModalCallback`, so the
    //     abandon's server-side `POST .../discardChanges` is still IN FLIGHT once the modal is gone. Until
    //     it completes, the WebUI rows endpoint still reports the row's rejected in-memory ValidFrom, so
    //     the rows read in (a) below would race it and see the colliding date instead of the reverted DB
    //     value. Await that round-trip explicitly — the deterministic boundary, not a sleep.
    const discardChangesResponse = page.waitForResponse(
      (response) => response.url().includes('/discardChanges') && response.request().method() === 'POST',
      { timeout: SLOW_ACTION_TIMEOUT },
    );
    await closeDialogAbandoningChanges(page);
    await discardChangesResponse;

    // ============ ASSERT THE FIXED (DESIRED) BEHAVIOR ============
    // (a) DATA IS INTACT: a direct rows GET on the PLV tab still returns the persisted row (DB row never deleted,
    //     and it keeps its far-future ValidFrom because the colliding edit was rejected — the abandon reverts the
    //     document server-side but never deletes the DB row).
    const rowsResponse = await page.request.get(`${plvTabRowsUrl()}/?orderBy=-ValidFrom`);
    expect(rowsResponse.ok(), `PLV rows GET should succeed (HTTP ${rowsResponse.status()})`).toBe(true);
    const rowsBody = await rowsResponse.json();
    const rows = Array.isArray(rowsBody) ? rowsBody : rowsBody.result || rowsBody.documents || [];
    expect(
      rows.some((r) => validFromOf(r).startsWith(persistedValidFromIso)),
      `the persisted PLV row (ValidFrom ${persistedValidFromIso}) must still exist server-side (data not lost). Row ValidFroms: ${JSON.stringify(rows.map(validFromOf))}`,
    ).toBe(true);

    // Positive control: the parent grid IS rendered (the untouched 2015 seed row shows). This makes a 0-count on
    // the far-future row below mean "row removed", not "grid never rendered / locator wrong".
    await expect
      .poll(() => plvGridRowsWithSeed().count(), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the parent PLV grid must render (seed 2015 row present) after the modal closes',
      })
      .toBeGreaterThan(0);
    console.log(`[plv-abandon-grid] grid rows after Done: ${JSON.stringify(await gridRows().allTextContents())}`);
    console.log(`[plv-abandon-grid] persisted far-future ValidFrom: ${persistedValidFrom} (ISO ${persistedValidFromIso}), grid rows carrying year ${persistedYear}: ${await plvGridRowsWithYear().count()}`);

    // (b) GRID RETAINS THE ROW: without any reload the parent grid MUST still show the persisted row —
    //     MasterWindow.closeModalCallback re-queries the row and reverts it in place instead of removing it
    //     client-side.
    await expect
      .poll(() => plvGridRowsWithYear().count(), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: `the persisted far-future PLV row (year ${persistedYear}) must STAY in the grid after Done, with no reload (fix under test)`,
      })
      .toBeGreaterThan(0);

    // Regression guard for the sibling colliding-date fix: no 404 on the PLV document path throughout.
    expect(notFoundUrls, `no 404 on the PLV document path. Captured: ${JSON.stringify(notFoundUrls)}`).toEqual([]);
  });
});
