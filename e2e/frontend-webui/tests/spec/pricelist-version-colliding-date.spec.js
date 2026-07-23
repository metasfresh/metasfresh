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
  const isCleanSeedRow = (r) => {
    const validFrom = String((((r.fieldsByName || {}).ValidFrom) || {}).value || '');
    return validFrom.startsWith(PLV_SEED_VALIDFROM) && (r.saveStatus || {}).error !== true;
  };
  const rowsResp = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/${PLV_TAB_ID}/?orderBy=-ValidFrom`
  );
  expect(rowsResp.ok(), `PLV rows GET should succeed (HTTP ${rowsResp.status()})`).toBe(true);
  const rowsBody = await rowsResp.json();
  const existingRows = Array.isArray(rowsBody) ? rowsBody : rowsBody.result || rowsBody.documents || [];
  // Guard the self-heal: the clean 2015 seed PLV MUST be recognized before we delete anything. If a
  // future backend change alters the row-value shape (adds a time/zone component, changes the envelope),
  // isCleanSeedRow would stop matching the seed and the loop below would delete the shared fixture both
  // tests depend on — with no failure pinpointing the cause. Fail loud here instead, before any delete.
  expect(
    existingRows.some(isCleanSeedRow),
    'the clean 2015 seed PLV must be recognized before self-heal — else the delete loop would remove the shared fixture'
  ).toBe(true);
  for (const r of existingRows) {
    const rid = String(r.rowId != null ? r.rowId : r.id);
    if (!rid || rid === 'undefined' || isCleanSeedRow(r)) {
      continue;
    }
    const del = await page.request.delete(
      `${WEBAPI_BASE_URL}/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/${PLV_TAB_ID}/${rid}`
    );
    expect(del.ok(), `deleting residue/wedged PLV row ${rid} should succeed (HTTP ${del.status()})`).toBe(true);
  }
};

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

    // --- Network instrumentation on the PLV document path ---
    //   notFoundResponses      : any 404 (the bug signature — the dropdown/document GET that failed before the fix)
    //   saveErrorReasons       : the localized reason(s) of the failed save — for debug/allure context only
    //   saveErrorFriendlyFlags : the language-invariant saveStatus.exception.userFriendlyError of each failed
    //                            save — a true here proves the failure is a USER-VALIDATION (friendly) rejection
    //                            (the bug condition), asserted instead of the localized reason text
    const notFoundResponses = [];
    const saveErrorReasons = [];
    const saveErrorFriendlyFlags = [];
    page.on('response', async (resp) => {
      const u = resp.url();
      if (!u.includes(`/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/`) || !u.includes(`/${PLV_TAB_ID}/`)) {
        return;
      }
      if (resp.status() === 404) {
        notFoundResponses.push(`${resp.status()} ${u}`);
        return;
      }
      if (resp.request().method() === 'PATCH') {
        try {
          const body = await resp.json();
          const docs = Array.isArray(body) ? body : body.documents || [];
          for (const d of docs) {
            if (d && d.saveStatus && d.saveStatus.error) {
              saveErrorReasons.push(d.saveStatus.reason || '(no reason)');
              saveErrorFriendlyFlags.push(d.saveStatus.exception?.userFriendlyError === true);
            }
          }
        } catch (e) {
          /* non-JSON response — ignore */
        }
      }
    });

    // 1. Login (admin sees the seed price lists). Use the simple helper that handles the
    //    multi-role chooser by re-clicking — LoginPage.login waits for the wrong endpoint here.
    await loginWithMasterdataUser(page, { username: 'metasfresh', password: 'metasfresh' });
    await DashboardPage.expectVisible();

    // 1b. Normalise the target price list to the clean seed state (clears any wedge — see helper).
    //     Without this, a residual errored PLV from a prior run sets allowCreateNew=false and the
    //     "Add new" button below never appears → the run times out. On a fresh CI DB this is a no-op.
    await normalizePriceListToSeed(page);

    // 2. Open the price list record directly
    await page.goto(`${FRONTEND_BASE_URL}/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}`);
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

    // 3. The "PriceList Version" included tab is shown inline. Create a new PLV row via its "Add new" button.
    //    Language-independent selector (stable button classes, NOT the localized "Add new" caption) —
    //    covers both the inline-tab and the table-filter renderers; excludes the batch-entry toggle.
    const addNewBtn = page
      .locator('.inlinetab-action-button button, .table-filter-line .filter-panel-buttons button.btn-distance:not(.close-batch-entry)')
      .first();
    await addNewBtn.scrollIntoViewIfNeeded();
    await addNewBtn.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await addNewBtn.click();

    // 4. Wait for the new included-row detail to render (ValidFrom field present)
    await page
      .waitForURL(new RegExp(`/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/${PLV_TAB_ID}/\\d+`), { timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.locator('.form-field-ValidFrom input').first()
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // 5. Set the colliding ValidFrom → auto-save → fails on the unique index.
    await DateWidget.setValue('ValidFrom', COLLIDING_DATE);
    // Deterministically wait for the failed save to arrive (no fixed sleep): the response listener
    // records the server-side saveStatus error from the PLV PATCH.
    await expect
      .poll(() => saveErrorReasons.length, {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'colliding PLV save should fail server-side with the duplicate-date error',
      })
      .toBeGreaterThan(0);

    // 6. The colliding save fails server-side — captured via saveErrorReasons from the PATCH response.
    //    In THIS new-record flow we assert the failure at the network level (saveStatus) plus the no-404
    //    dropdown proof, rather than scraping the in-dialog indicator: on a brand-new row the modal auto-saves
    //    the default ValidFrom on open, so the on-screen indicator here is awkward to pin to the colliding
    //    edit specifically. The persistence of the on-screen error itself is NOT transient after the fix —
    //    that persistent-error behaviour (the user keeps seeing the rejection instead of it flashing ~1s and
    //    reverting) is proven directly by the "edit-existing" test below, where the errored document is kept
    //    in the cache and the post-failure re-fetch still carries the error.
    console.log(`[plv-collision] save errors: ${JSON.stringify(saveErrorReasons)}`);
    allure.attachment('Save error reason(s)', JSON.stringify(saveErrorReasons, null, 2), 'application/json');

    // 7. THE FIX UNDER TEST: open the Preislisten-Schema (M_DiscountSchema_ID) dropdown.
    //    Before the fix this triggered a 404 (DocumentNotFoundException) on the evicted document.
    const schemaInput = page
      .locator('#lookup_M_DiscountSchema_ID input, .form-field-M_DiscountSchema_ID input')
      .first();
    await schemaInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await schemaInput.click();

    // The dropdown list resolving (rather than a 404) is the user-visible proof of the fix
    const dropdownVisible = await page
      .locator('.input-dropdown-list')
      .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT })
      .then(() => true)
      .catch(() => false);

    // --- Assertions ---
    // (a) the bug condition was actually hit: the colliding save failed server-side with a USER-VALIDATION
    //     (friendly) rejection. Asserted via the language-invariant userFriendlyError flag, not the localized
    //     reason text (reasons kept in the message for debug context only).
    expect(
      saveErrorFriendlyFlags.some((f) => f === true),
      `the colliding new PLV save must fail with a user-validation (friendly) error. Reasons seen: ${JSON.stringify(saveErrorReasons)}`,
    ).toBe(true);
    // (b) THE FIX: the document was NOT evicted → no 404 on the PLV document path (the exact symptom)
    expect(notFoundResponses, `no 404 on the PLV document path (the colliding-ValidFrom bug). Captured: ${JSON.stringify(notFoundResponses)}`).toEqual([]);
    // (c) user-visible proof: the Preislisten-Schema dropdown still opens after the failed save
    expect(dropdownVisible, 'the Preislisten-Schema dropdown opens (document not lost)').toBe(true);
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

    // --- Network instrumentation on the PLV document path ---
    // Records EVERY response on the PLV path (PATCH saves + GET re-fetches) in arrival order, with the
    // server-side saveStatus.error of each returned document — so we can prove the post-failure GET retained
    // the error (the carve-out) rather than returning a clean, evicted-and-rebuilt document.
    const events = [];      // { seq, method, status, error, userFriendlyError, reason, saved }
    const notFoundResponses = [];
    let seq = 0;
    page.on('response', async (resp) => {
      const u = resp.url();
      if (!u.includes(`/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/`) || !u.includes(`/${PLV_TAB_ID}/`)) {
        return;
      }
      const method = resp.request().method();
      const status = resp.status();
      const s = ++seq;
      if (status === 404) {
        notFoundResponses.push(`${status} ${u}`);
        events.push({ seq: s, method, status, error: null, reason: null, saved: null });
        return;
      }
      try {
        const body = await resp.json();
        const arr = Array.isArray(body) ? body : body.documents || [body];
        for (const d of arr) {
          if (d && d.saveStatus) {
            events.push({
              seq: s,
              method,
              status,
              error: !!d.saveStatus.error,
              userFriendlyError: d.saveStatus.exception?.userFriendlyError === true,
              reason: d.saveStatus.reason || null,
              saved: !!d.saveStatus.saved,
            });
          }
        }
      } catch (e) {
        /* non-JSON (lookup/typeahead) — not a document, ignore */
      }
    });

    // A unique, far-future ValidFrom that cannot collide with the seed 2015-01-01 PLV, today, or a PLV left
    // by a prior local run (each run picks a different random date in ~2044..2099). MM/DD/YYYY (en_US).
    const d = new Date(Date.UTC(2099, 11, 31));
    d.setUTCDate(d.getUTCDate() - Math.floor(Math.random() * 20000));
    const uniqueFutureDate = `${String(d.getUTCMonth() + 1).padStart(2, '0')}/${String(d.getUTCDate()).padStart(2, '0')}/${d.getUTCFullYear()}`;

    // 1. Login.
    await loginWithMasterdataUser(page, { username: 'metasfresh', password: 'metasfresh' });
    await DashboardPage.expectVisible();

    // 1b. Normalise the target price list to the clean seed state (clears any wedge — see helper).
    await normalizePriceListToSeed(page);

    // 2. Open the price list record.
    await page.goto(`${FRONTEND_BASE_URL}/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}`);
    await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

    // 3. Add-new a PLV row.
    const addNewBtn = page
      .locator('.inlinetab-action-button button, .table-filter-line .filter-panel-buttons button.btn-distance:not(.close-batch-entry)')
      .first();
    await addNewBtn.scrollIntoViewIfNeeded();
    await addNewBtn.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await addNewBtn.click();

    await page
      .waitForURL(new RegExp(`/window/${PRICE_LIST_WINDOW_ID}/${PRICE_LIST_RECORD_ID}/${PLV_TAB_ID}/\\d+`), { timeout: SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.locator('.form-field-ValidFrom input').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // 3b. Set the unique future ValidFrom and wait for the row to SAVE successfully (persisted, no error).
    //    This gives us an ALREADY-PERSISTED PLV to then edit into a collision.
    const beforeSaveSeq = seq;
    await DateWidget.setValue('ValidFrom', uniqueFutureDate);
    await expect
      .poll(() => events.some((e) => e.seq > beforeSaveSeq && e.method === 'PATCH' && e.saved === true && e.error === false), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: `the future-dated PLV (${uniqueFutureDate}) should save successfully (persisted, no error)`,
      })
      .toBeTruthy();

    // 4. Now EDIT that persisted PLV's ValidFrom to a colliding date (the edit-existing path the fix targets).
    const beforeEditSeq = seq;
    await DateWidget.setValue('ValidFrom', COLLIDING_DATE);

    // 5. PRIMARY PROOF (a): the failing edit's PATCH reports a server-side user-validation error.
    await expect
      .poll(() => events.some((e) => e.seq > beforeEditSeq && e.method === 'PATCH' && e.error === true), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the colliding edit should fail server-side with the duplicate-date error',
      })
      .toBeTruthy();
    const failingPatch = events.filter((e) => e.seq > beforeEditSeq && e.method === 'PATCH' && e.error === true).pop();

    // 6. PRIMARY PROOF (b) — THE CARVE-OUT: the document is NOT evicted. The stale-triggered re-fetch after
    //    the failed save (frontend reacts to the websocket staleRootDocument event) issues a GET on the PLV
    //    document path that STILL carries saveStatus.error===true. Before the fix this GET returned a clean
    //    (evicted-and-rebuilt) document with error===false, and the on-screen error reverted.
    await expect
      .poll(() => events.some((e) => e.seq > failingPatch.seq && e.method === 'GET' && e.error === true), {
        timeout: SLOW_ACTION_TIMEOUT,
        message: 'the post-failure re-fetch GET on the PLV path must STILL report error===true (document not evicted)',
      })
      .toBeTruthy();

    console.log(`[plv-edit-collision] events: ${JSON.stringify(events)}`);
    allure.attachment('PLV-path responses (arrival order)', JSON.stringify(events, null, 2), 'application/json');

    const postFailureGets = events.filter((e) => e.seq > failingPatch.seq && e.method === 'GET' && e.error !== null);

    // 7. SECONDARY PROOF (on screen): the window error indicator is shown AND stays shown after the network
    //    settles. This is the user-visible half of the bug ("nicht oder nur sehr kurz angezeigt") — the error
    //    must NOT flash and revert. Language-invariant selector: the Indicator component renders the ERROR
    //    state as `<div class="bar error">` inside `.window-indicator-container` (IndicatorState.ERROR ===
    //    'error'; see components/app/Indicator.js) — no localized text involved. toBeVisible auto-waits, so
    //    this is a hard assertion that still tolerates render timing (no soft catch).
    await page.waitForLoadState('networkidle', { timeout: FAST_ACTION_TIMEOUT }).catch(() => {});
    // Persistence (no revert) is proven deterministically by assertion (b) below: the post-failure GET STILL
    // reports error===true, so no clean document ever arrives to revert the render. Here we assert the user
    // actually SEES the error indicator (the on-screen half of the bug), after the network has settled.
    const errorIndicator = page.locator('.window-indicator-container .bar.error').first();
    await expect(errorIndicator, 'the on-screen window error indicator must be shown after the failed edit').toBeVisible({
      timeout: SLOW_ACTION_TIMEOUT,
    });

    // --- Assertions ---
    // (a) bug condition hit: the colliding edit failed server-side with a USER-VALIDATION (friendly) rejection.
    //     Asserted via the language-invariant userFriendlyError flag, not the localized reason (kept for debug).
    expect(
      failingPatch.userFriendlyError,
      `the colliding edit must fail with a user-validation (friendly) error. Failing PATCH: ${JSON.stringify(failingPatch)}`,
    ).toBe(true);
    // (b) THE CARVE-OUT: at least one post-failure GET on the PLV path retained error===true (not evicted / not reverted).
    expect(
      postFailureGets.some((e) => e.error === true),
      `the post-failure GET on the PLV path must retain error===true (carve-out: document not evicted). Post-failure GETs: ${JSON.stringify(postFailureGets)}`,
    ).toBe(true);
    // (c) the exact old symptom must be absent: no 404 on the PLV document path.
    expect(notFoundResponses, `no 404 on the PLV document path. Captured: ${JSON.stringify(notFoundResponses)}`).toEqual([]);
  });
});
