import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { loginWithMasterdataUser } from '../utils/LoginHelper';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
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
    //    NOTE: as of this fix the WebUI "Add new" overlay does NOT surface that message on screen (the save
    //    fails silently); that display gap is tracked separately. This test asserts only what the eviction
    //    fix guarantees: the document survives (no 404) and the schema dropdown still resolves.
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
});
