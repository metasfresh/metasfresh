import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { loginWithMasterdataUser } from '../utils/LoginHelper';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, FAST_ACTION_TIMEOUT } from '../utils/common';
import { DateWidget } from '../utils/widgets/DateWidget';

/**
 * Regression test: creating a new Price List Version whose ValidFrom collides with an existing one
 * must not break the WebUI (the Price List Schema dropdown must still resolve).
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
 * Call this in every test AFTER login and BEFORE touching the included tab — both tests here depend on it,
 * so it must run at the start of each (the first test leaves a residual errored row the second must clear,
 * and either test can inherit a wedge from a prior local run).
 *
 * A prior colliding run — or the manual UAT of this very fix — can leave a NON-seed PLV row cached with a
 * KEPT user-validation error (an unsaved in-memory edit to a colliding date). That kept error is precisely
 * the behaviour under test, so a plain webapi cache reset does NOT evict it (the carve-out keeps
 * user-validation errors) — verified: resetByTable left the wedge in place. Such a wedged row sets
 * allowCreateNew=false and hides the included-tab "Add new" button, blocking the run. Deleting every row
 * that is not the CLEAN seed 2015 row — via the same WebUI rows endpoint the frontend uses (trailing slash
 * + orderBy), so cached in-memory-errored rows are visible, unlike the plain rows GET — discards the wedge
 * and restores the clean state. On a fresh seed DB (a clean CI shard) the GET returns only the seed row, so
 * the loop deletes nothing; within this file the first test leaves a residual same-date errored row, which
 * the second test's call then removes — so the loop does real work on the normal in-file run, not only on a
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
  // isCleanSeedRow would stop matching the seed and the loop below would delete the shared fixture both
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

// The Indicator component renders the ERROR state as `<div class="bar error">` inside
// `.window-indicator-container` (IndicatorState.ERROR === 'error'; see components/app/Indicator.js) —
// a language-invariant selector, no localized text involved.
const windowErrorIndicator = (page) => page.locator('.window-indicator-container .bar.error').first();

test.describe('PriceListVersion — colliding ValidFrom', () => {
  test('colliding new PLV: friendly error AND the Preislisten-Schema dropdown still resolves (no 404)', async ({ page }) => {
    allure.epic('E0260: Pricing');
    allure.tag('F32070: Price List Copy using Price List Schema');
    allure.tag('F32070');
    allure.story('Creating a PLV with a duplicate ValidFrom must not break the document (no 404 on the schema dropdown)');
    allure.severity('critical');
    allure.description(`
After a colliding-ValidFrom PLV save fails on the unique index, the
\`Preislisten-Schema\` (M_DiscountSchema_ID) dropdown must still open — before the fix it returned
HTTP 404 (DocumentNotFoundException) because the document-cache evicted the root owning the unsaved
new child PLV.
    `);

    test.setTimeout(120000);

    const { recordedResponses, notFoundUrls } = recordPlvPathResponses(page);

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
  });

  test('edit-existing PLV to a colliding date: the USER-VALIDATION error PERSISTS — the document is NOT self-heal-evicted', async ({ page }) => {
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
with no 404 on that path.
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

    // SECONDARY PROOF (on screen): the user actually SEES the error indicator (the user-visible half of the
    // bug, "nicht oder nur sehr kurz angezeigt"). Persistence (no revert) is proven deterministically by
    // assertion (b): the post-failure GET STILL reports error===true, so no clean document arrives to revert
    // the render. toBeVisible auto-waits, so this is a hard assertion that still tolerates render timing.
    await page.waitForLoadState('networkidle', { timeout: FAST_ACTION_TIMEOUT }).catch(() => {});
    await expect(windowErrorIndicator(page), 'the on-screen window error indicator must be shown after the failed edit').toBeVisible({
      timeout: SLOW_ACTION_TIMEOUT,
    });

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
  });
});
