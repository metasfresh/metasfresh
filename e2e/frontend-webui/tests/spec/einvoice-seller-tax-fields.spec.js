import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

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
 *   1. Appear in the window's tab layout (visible = not excluded from layout)
 *   2. Are editable (readonly: false in the layout)
 *
 * Data-independence: this test verifies the AD_Field layout via the WebAPI
 * /rest/api/window/{windowId}/layout endpoint. Window layout is pure metadata
 * (AD_Field/AD_UI_Element configuration) and does not depend on any C_BPartner
 * records being present — making this test safe on a clean CI seed DB.
 *
 * Window 540676 has isinsertrecord=N and a WhereClause (ad_orgbp_id IS NOT NULL)
 * that filters to org-BPartner records not present in the CI seed DB, so
 * /window/540676/NEW and navigating to a hardcoded record ID are both non-options.
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

/**
 * Fetch the window layout from the WebAPI.
 * Returns the parsed JSON layout for the window's root tab.
 *
 * The /window/{id}/layout endpoint is available to any authenticated session
 * and returns field visibility/readonly metadata regardless of whether records exist.
 */
async function fetchWindowLayout(page, windowId) {
  const url = `${WEBAPI_BASE_URL}/window/${windowId}/layout`;
  console.log(`[INFO] Fetching layout from: ${url}`);
  const resp = await page.request.get(url);
  if (!resp.ok()) {
    const body = await resp.text();
    throw new Error(`Layout fetch failed: HTTP ${resp.status()} — ${body.substring(0, 300)}`);
  }
  return resp.json();
}

/**
 * Collect all {element, field} pairs from a window layout JSON.
 *
 * Actual structure (verified against /rest/api/window/540676/layout):
 *   sections[] → columns[] → elementGroups[] → elementsLine[] → elements[] → fields[]
 *
 * - Each field entry is: { field: "<ColumnName>", caption, emptyText, ... }
 *   ("field" is the column name string, NOT an object)
 * - Editability attributes (readonly, viewEditorRenderMode) live on the ELEMENT,
 *   not on the field. A missing "readonly" key means editable.
 *
 * Returns an array of { fieldName, element } objects so callers can check
 * both presence (fieldName in layout) and editability (element.readonly).
 */
function collectLayoutFields(layout) {
  const results = [];
  for (const section of layout.sections || []) {
    for (const column of section.columns || []) {
      for (const elementGroup of column.elementGroups || []) {
        // The actual key is "elementsLine", not "elements"
        for (const elementsLine of elementGroup.elementsLine || []) {
          for (const element of elementsLine.elements || []) {
            for (const field of element.fields || []) {
              // field.field is the ColumnName string (e.g. "VATaxID")
              results.push({ fieldName: field.field, element });
            }
          }
        }
      }
    }
  }
  return results;
}

test.describe('Organisation Stammdaten — seller tax-identification fields (E-Invoicing)', () => {
  test(
    'VATaxID, TaxID and CommercialRegisterNumber are in the window layout and editable',
    async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0340: Invoicing');
      allure.feature('F00751: e-Invoicing Germany');
      allure.tag('F00751: e-Invoicing Germany');
      allure.tag('F00751');
      allure.story('Seller tax fields in window layout on Organisation Stammdaten window 540676');
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

### Test Approach — Data-Independent Layout Verification
Window 540676 has \`isinsertrecord=N\` and a WhereClause that filters to org-BPartner
records not present in the CI seed DB. The test therefore verifies the field layout
via the WebAPI \`/rest/api/window/{windowId}/layout\` endpoint, which returns
AD_Field metadata without requiring any records.

### Test Steps
1. Create fresh test user, login
2. Reset webui metadata cache (so new field layout is active without server restart)
3. Fetch the window layout via /rest/api/window/540676/layout
4. Assert each field is present in the layout (not excluded from the tab)
5. Assert each field has readonly: false (editable)
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

      // === STEP 4: Fetch window layout ===
      // The layout endpoint returns the field definitions for the tab — this is pure metadata
      // and works regardless of whether any C_BPartner records exist in the DB.
      const layout = await fetchWindowLayout(page, ORG_MASTER_WINDOW_ID);

      allure.attachment('Window Layout JSON', JSON.stringify(layout, null, 2), 'application/json');
      console.log('[INFO] Window layout fetched successfully');

      // Collect all field names from the layout structure
      const layoutFields = collectLayoutFields(layout);
      console.log(`[INFO] Layout contains ${layoutFields.length} field entries`);

      // === STEP 5: Assert all three fields are in the layout and editable ===
      const FIELDS = [
        {
          columnName: 'VATaxID',
          label: 'USt-IdNr / VAT identifier',
        },
        {
          columnName: 'TaxID',
          label: 'Steuernummer',
        },
        {
          columnName: 'CommercialRegisterNumber',
          label: 'Handelsregisternr',
        },
      ];

      for (const field of FIELDS) {
        await test.step(`Assert field ${field.columnName} (${field.label}) is in layout and editable`, async () => {
          // Find the field in the layout by column name
          const entry = layoutFields.find((f) => f.fieldName === field.columnName);

          expect(
            entry,
            `Field ${field.columnName} must appear in the window 540676 layout (check AD_Field.IsDisplayed and AD_UI_Element visibility for tab 541852)`
          ).toBeDefined();

          // Assert the field is editable.
          // Editability is indicated by the ABSENCE of readonly:true on the element.
          // (The layout omits the readonly key when the field is editable;
          //  readonly:true is set explicitly only for truly read-only fields.)
          const isReadonly = entry.element.readonly === true;
          expect(
            isReadonly,
            `Field ${field.columnName} must NOT be readonly (element.readonly must not be true)`
          ).toBe(false);

          console.log(`[PASS] ${field.columnName} (${field.label}) — present in layout, element.readonly=${entry.element.readonly}`);
        });
      }

      console.log('[INFO] All three seller tax fields verified — present in layout and editable');
    }
  );
});
