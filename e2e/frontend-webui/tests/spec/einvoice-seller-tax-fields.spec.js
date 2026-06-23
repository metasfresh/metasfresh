import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { assertRecordIsValid, WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * E-Invoicing — Seller tax-identification fields on Organisation Stammdaten window.
 *
 * Window 540676 "Organisation Stammdaten", tab 541852 "Geschäftspartner" (C_BPartner).
 * Migration 5809330 on branch deep_tundra_release_30508_EInvoiceFieldMapping made three
 * C_BPartner columns visible in this tab:
 *   - VATaxID             (USt-IdNr / VAT identifier)  — was already shown
 *   - TaxID               (Steuernummer)                — newly shown
 *   - CommercialRegisterNumber (Handelsregisternr)      — newly added field
 *
 * Test verifies that all three fields:
 *   1. Are rendered in the detail form (visible)
 *   2. Are editable (the underlying <input> is NOT disabled/readonly)
 *
 * me03 #30508
 *
 * Features tested:
 * - F00751: e-Invoicing Germany
 */

/**
 * Window 540676 — Organisation Stammdaten
 * AD_Tab 541852 "Geschäftspartner" — table C_BPartner (WhereClause: ad_orgbp_id is not null)
 */
const ORG_MASTER_WINDOW_ID = 540676;

/**
 * C_BPartner_ID 2155894 "Muster GmbH" — an org-BPartner record (ad_orgbp_id IS NOT NULL)
 * that already carries VATaxID and TaxID values.  CommercialRegisterNumber is empty (NULL),
 * which makes it a good test target: we can assert the field is present even when blank.
 * The record is NOT modified by this test.
 */
const TEST_BPARTNER_ID = 2155894;

/**
 * Reset the WebUI metadata cache after login.
 * Required because migration 5809330 added/changed field visibility — a running webui
 * may have a stale layout in its session cache.
 */
async function resetWebuiCache(page) {
  console.log('[INFO] Resetting webui metadata cache...');
  const resp = await page.request.get(`${WEBAPI_BASE_URL}/cache/reset`);
  const status = resp.status();
  if (status !== 200) {
    const body = await resp.text();
    throw new Error(`Cache reset failed: HTTP ${status} — ${body.substring(0, 200)}`);
  }
  console.log(`[INFO] Cache reset response: HTTP ${status}`);
}

test.describe('Organisation Stammdaten — seller tax-identification fields (E-Invoicing)', () => {
  test(
    'VATaxID, TaxID and CommercialRegisterNumber are visible and editable on Geschäftspartner tab',
    async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0340: Invoicing');
      allure.feature('F00751: e-Invoicing Germany');
      allure.tag('F00751: e-Invoicing Germany');
      allure.tag('F00751');
      allure.story('Seller tax fields visible and editable on Organisation Stammdaten window 540676');
      allure.severity('critical');
      allure.tag('e-Invoicing');
      allure.tag('OrganisationStammdaten');
      allure.description(`
## E-Invoicing — Seller Tax Fields on Organisation Stammdaten (Window 540676)

### Feature
Migration 5809330 makes the following **C_BPartner** columns visible in window 540676
tab "Geschäftspartner" (AD_Tab 541852):
- **VATaxID** (USt-IdNr / VAT identifier) — already shown, now confirmed
- **TaxID** (Steuernummer) — newly shown
- **CommercialRegisterNumber** (Handelsregisternr) — newly added

### Test Steps
1. Create fresh test user, login
2. Reset webui metadata cache (so new field layout is active without server restart)
3. Navigate directly to the org-BPartner record in window 540676
4. Assert each field container is visible in the form (.form-field-VATaxID etc.)
5. Assert the input inside each field is enabled (NOT disabled / NOT readonly)
      `);

      test.setTimeout(120000); // 2 minutes

      // === STEP 1: Create test user ===
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language: 'en_US',
              firstname: 'E2E',
              lastname: 'TaxFields',
            },
          },
        },
      });

      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      console.log(`[INFO] Test user created: ${masterdata.login.user.username}`);

      // === STEP 2: Login ===
      // Use inline login pattern (avoids waitForResponse race on role-selection step).
      // Documented in CLAUDE.md § "Default Login (metasfresh/metasfresh) — Role Selection".
      await page.goto(`${FRONTEND_BASE_URL}/login`);
      await page.locator('.login-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.locator('input[name="username"]').fill(masterdata.login.user.username);
      await page.locator('input[name="password"]').fill(masterdata.login.user.password);
      await page.locator('.btn-meta-success').click();
      // Handle role selection — wait briefly, then click Send again if still on login
      await page.waitForTimeout(1000);
      if (page.url().includes('/login')) {
        const sendButton = page.locator('.btn-meta-success');
        if (await sendButton.isVisible()) { await sendButton.click(); }
      }
      await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
      // Verify dashboard loaded — content visible (skip networkidle: WebSockets keep it pending)
      await page.locator('.app-content, .dashboard').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('[INFO] Logged in successfully');

      // === STEP 3: Reset webui metadata cache ===
      // Must be done after login (endpoint requires authentication).
      await resetWebuiCache(page);

      // === STEP 4: Navigate directly to the org-BPartner record in window 540676 ===
      // Window 540676 "Organisation Stammdaten" — the Geschäftspartner tab (541852)
      // shows C_BPartner records where ad_orgbp_id IS NOT NULL.
      // Record 2155894 "Muster GmbH" satisfies this condition.
      await page.goto(`${FRONTEND_BASE_URL}/window/${ORG_MASTER_WINDOW_ID}/${TEST_BPARTNER_ID}`);

      // Wait for the detail view to fully load
      await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      console.log('[INFO] Record loaded. URL:', page.url());

      const screenshotInitial = await page.screenshot();
      allure.attachment('Geschaeftspartner Detail — Initial State', screenshotInitial, 'image/png');

      // === STEP 5: Assert record is valid (MANDATORY guard per CLAUDE.md) ===
      // If valid=false, any UI changes would not be saved.  We are not editing in this
      // test, but the guard confirms the record loaded correctly.
      await assertRecordIsValid(
        String(ORG_MASTER_WINDOW_ID),
        String(TEST_BPARTNER_ID),
        'before asserting field visibility'
      );
      console.log('[INFO] Record is valid');

      // === STEP 6: Assert all three fields are visible and editable ===
      const FIELDS = [
        {
          columnName: 'VATaxID',
          label: 'USt-IdNr / VAT identifier',
          selector: '.form-field-VATaxID',
        },
        {
          columnName: 'TaxID',
          label: 'Steuernummer',
          selector: '.form-field-TaxID',
        },
        {
          columnName: 'CommercialRegisterNumber',
          label: 'Handelsregisternr',
          selector: '.form-field-CommercialRegisterNumber',
        },
      ];

      for (const field of FIELDS) {
        await test.step(`Assert field ${field.columnName} (${field.label}) is visible and editable`, async () => {
          // 1. Field container must be visible in the form
          const fieldContainer = page.locator(field.selector);
          await fieldContainer.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
          await expect(fieldContainer, `Field ${field.columnName} container must be visible`).toBeVisible();

          // 2. The input inside the field must be enabled (not disabled and not readonly)
          //    String/Text fields render as <input type="text"> inside the form-field wrapper.
          const input = fieldContainer.locator('input').first();
          await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

          const isDisabled = await input.isDisabled();
          expect(
            isDisabled,
            `Field ${field.columnName} input must NOT be disabled (field must be editable)`
          ).toBe(false);

          // Also check the readonly attribute (some fields are shown but not editable)
          const isReadonly = await input.getAttribute('readonly');
          expect(
            isReadonly,
            `Field ${field.columnName} input must NOT have readonly attribute`
          ).toBeNull();

          console.log(`[PASS] ${field.columnName} (${field.label}) — visible and editable`);
        });
      }

      const screenshotFinal = await page.screenshot();
      allure.attachment('Geschaeftspartner Detail — Fields Verified', screenshotFinal, 'image/png');

      console.log('[INFO] All three seller tax fields verified — visible and editable');
    }
  );
});
