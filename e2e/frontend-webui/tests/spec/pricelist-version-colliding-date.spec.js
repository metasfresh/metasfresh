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
    //   notFoundResponses : any 404 (the bug signature — the dropdown/document GET that failed before the fix)
    //   saveErrorReasons  : the failed colliding save (unique-index INSERT failure) — proves the bug condition was hit
    const notFoundResponses = [];
    const saveErrorReasons = [];
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
    // (a) the bug condition was actually hit: the colliding save failed server-side with the friendly duplicate-date error
    expect(saveErrorReasons.join(' | '), 'the colliding new PLV save failed on the unique index (friendly duplicate-date error)').toMatch(/Datum|date|Version/i);
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
    const events = [];      // { seq, method, status, error, reason, saved }
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

    // 1b. Self-heal the shared local webapi document cache before we start. A PRIOR colliding run (this
    //     test, or the new-record test above running first in the same file) leaves the root price-list
    //     document cached in a KEPT user-validation error state (that is exactly the fix under test — such
    //     errors are no longer self-healed away). A left-over errored root hides the included-tab "Add new"
    //     button, so the next run cannot start. A full cache invalidation evicts a persisted-error root
    //     (the unsaved-new-included carve-out does not apply — there is no unsaved child here), giving a
    //     clean starting document. On CI (clean seed cache) this is a harmless no-op.
    for (const tableName of ['M_PriceList', 'M_PriceList_Version']) {
      const reset = await page.request.get(`${WEBAPI_BASE_URL}/cache/resetByTable?tableName=${tableName}`);
      expect(reset.ok(), `webapi cache reset for ${tableName} should succeed (HTTP ${reset.status()})`).toBe(true);
    }

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

    const plvRowId = (page.url().match(new RegExp(`/${PLV_TAB_ID}/(\\d+)`)) || [])[1];
    expect(plvRowId, 'the add-new PLV row id must be resolvable from the URL').toBeTruthy();

    // 3. Set the unique future ValidFrom and wait for the row to SAVE successfully (persisted, no error).
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

    // 7. SECONDARY PROOF (best-effort, soft): the on-screen error indicator is still shown in the detail after
    //    the network settles. Kept soft so a rendering-timing quirk never flakes the network-level proof.
    await page.waitForLoadState('networkidle', { timeout: FAST_ACTION_TIMEOUT }).catch(() => {});
    const onScreenErrorVisible = await page
      .locator('.indicator-error, .form-field-ValidFrom .input-error, .meta-modal-header .indicator-error')
      .first()
      .isVisible()
      .catch(() => false);
    console.log(`[plv-edit-collision] on-screen error indicator visible (soft): ${onScreenErrorVisible}`);
    allure.attachment('On-screen error indicator (soft check)', String(onScreenErrorVisible), 'text/plain');

    // --- Assertions ---
    // (a) bug condition hit: the colliding edit failed server-side with the friendly duplicate-date reason.
    expect(failingPatch.reason || '', 'the colliding edit failed on the unique index (friendly duplicate-date error)').toMatch(/Datum|date|Version/i);
    // (b) THE CARVE-OUT: at least one post-failure GET on the PLV path retained error===true (not evicted / not reverted).
    expect(
      postFailureGets.some((e) => e.error === true),
      `the post-failure GET on the PLV path must retain error===true (carve-out: document not evicted). Post-failure GETs: ${JSON.stringify(postFailureGets)}`,
    ).toBe(true);
    // (c) the exact old symptom must be absent: no 404 on the PLV document path.
    expect(notFoundResponses, `no 404 on the PLV document path. Captured: ${JSON.stringify(notFoundResponses)}`).toEqual([]);
  });
});
