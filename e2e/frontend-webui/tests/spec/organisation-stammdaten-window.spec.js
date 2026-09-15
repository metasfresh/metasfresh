import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';

/**
 * Organisation Stammdaten window (AD_Window 540676) — displayed-field layout checks.
 *
 * This is the home for layout/visibility assertions on window 540676 "Organisation Stammdaten"
 * (tab 541852 "Geschäftspartner", table C_BPartner, WhereClause: ad_orgbp_id IS NOT NULL — the
 * organisation's own business-partner master data). It is intentionally NOT e-Invoicing-specific:
 * extend the EXPECTED_DISPLAYED_FIELDS list below whenever a future issue makes additional
 * C_BPartner columns visible in this window.
 *
 * Why a layout-endpoint test (data-independent):
 *   Window 540676 has isinsertrecord=N and a WhereClause (ad_orgbp_id IS NOT NULL) that filters to
 *   org-BPartner rows not present in the CI seed DB, so /window/540676/NEW and navigating to a
 *   hardcoded record are both non-options. We assert against the WebAPI
 *   /rest/api/window/{id}/layout endpoint, which returns the AD_Field/AD_UI_Element layout as pure
 *   metadata — no records required.
 *
 * Language independence:
 *   Every assertion is on the C_BPartner DB ColumnName (language-invariant), never on a UI caption.
 *   The test runs in both en_US and de_DE to prove the login + layout flow is language-independent.
 *
 * Editability is NOT asserted: the /layout endpoint carries no per-record readonly flag (and
 * defaults every text element to viewEditorRenderMode='never'). Field editability is enforced at
 * the AD level (AD_Field.IsReadOnly='N') and verified by the window-designer.
 *
 * Current fields (added by me03 #30508, migration 5809330 — e-Invoicing seller identifiers).
 */

const ORG_MASTER_WINDOW_ID = 540676;

/**
 * Columns expected to be DISPLAYED in window 540676 / tab 541852.
 *
 * EXTENSION POINT — future issues that make a C_BPartner column visible in this window append it
 * here (with the source issue + migration for traceability). `columnName` MUST be the C_BPartner
 * DB ColumnName (language-invariant — this is what the test asserts on); `note` is a free-text
 * English annotation only and is NEVER asserted (so it cannot introduce a language dependency).
 */
const EXPECTED_DISPLAYED_FIELDS = [
  { columnName: 'VATaxID', note: 'VAT identifier (USt-IdNr) — me03 #30508, migration 5809330' },
  { columnName: 'TaxID', note: 'Tax registration (Steuernummer) — me03 #30508, migration 5809330' },
  { columnName: 'CommercialRegisterNumber', note: 'Commercial register number — me03 #30508, migration 5809330' },
];

const LANGUAGES = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

/**
 * Reset the WebUI metadata cache after login.
 * Required because field-visibility migrations may not be reflected in a running webui's
 * session-cached layout.
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
 * The /window/{id}/layout endpoint is available to any authenticated session and returns the
 * field visibility/layout metadata regardless of whether records exist.
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
 * Collect all field (ColumnName) names from a window layout JSON.
 *
 * Actual structure (verified against /rest/api/window/540676/layout):
 *   sections[] → columns[] → elementGroups[] → elementsLine[] → elements[] → fields[]
 *
 * - Each field entry is: { field: "<ColumnName>", caption, emptyText, ... }
 *   ("field" is the column name string, NOT an object).
 * - We collect field.field (the language-invariant ColumnName), never caption (language-specific).
 *
 * Returns an array of the ColumnName strings present in the layout, so callers can check
 * presence (a field name in the array == displayed in the tab).
 */
function collectLayoutFields(layout) {
  const fieldNames = [];
  for (const section of layout.sections || []) {
    for (const column of section.columns || []) {
      for (const elementGroup of column.elementGroups || []) {
        // The actual key is "elementsLine", not "elements"
        for (const elementsLine of elementGroup.elementsLine || []) {
          for (const element of elementsLine.elements || []) {
            for (const field of element.fields || []) {
              // field.field is the ColumnName string (e.g. "VATaxID")
              fieldNames.push(field.field);
            }
          }
        }
      }
    }
  }
  return fieldNames;
}

test.describe('Organisation Stammdaten window (540676) — displayed fields', () => {
  LANGUAGES.forEach(({ language, label }) => {
    test(`Expected C_BPartner fields are displayed in the window 540676 layout (${label})`, async ({ page }) => {
      // === ALLURE METADATA ===
      allure.epic('E0340: Invoicing');
      allure.feature('F00751: e-Invoicing Germany');
      allure.tag('F00751: e-Invoicing Germany');
      allure.tag('F00751');
      allure.story('Displayed fields on Organisation Stammdaten window 540676');
      allure.severity('critical');
      allure.tag('OrganisationStammdaten');
      allure.description(`
## Organisation Stammdaten (Window 540676) — Displayed Fields

### Scope
Generic layout check for window 540676 "Organisation Stammdaten" (tab 541852 "Geschäftspartner",
table C_BPartner). NOT e-Invoicing-specific — the \`EXPECTED_DISPLAYED_FIELDS\` list is the
extension point for future issues that make additional C_BPartner columns visible here.

### Currently expected columns (me03 #30508, migration 5809330 — e-Invoicing seller identifiers)
- **VATaxID** (USt-IdNr) — already shown, now confirmed
- **TaxID** (Steuernummer) — newly shown
- **CommercialRegisterNumber** (Handelsregisternr) — newly added

### Test approach — data-independent layout verification
Window 540676 has \`isinsertrecord=N\` and a WhereClause that filters to org-BPartner records not
present in the CI seed DB. The test verifies the field layout via the WebAPI
\`/rest/api/window/{windowId}/layout\` endpoint, which returns AD_Field metadata without records.

### Language independence
Assertions are on the C_BPartner **DB ColumnName** (language-invariant), never a UI caption. Runs
in both en_US and de_DE to prove the login + layout flow is language-independent.

### Test steps
1. Create fresh test user (this language), login
2. Reset webui metadata cache (so field-visibility migrations are reflected without a restart)
3. Fetch the window layout via /rest/api/window/540676/layout
4. Assert each expected column is present in the layout (not excluded from the tab)
      `);

      test.setTimeout(120000); // 2 minutes

      // === STEP 1: Create test user (in this language) ===
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,
              firstname: 'E2E',
              lastname: 'OrgMaster',
            },
          },
        },
      });

      allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
      console.log(`[INFO] Test user created (${label}): ${masterdata.login.user.username}`);

      // === STEP 2: Login ===
      // Inline login pattern (avoids the waitForResponse race on the role-selection step).
      // All selectors below are language-independent (CSS class / name attribute, never text).
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
      console.log(`[INFO] Logged in successfully (${label})`);

      // === STEP 3: Reset webui metadata cache ===
      // Must be done after login (endpoint requires authentication).
      await resetWebuiCache(page);

      // === STEP 4: Fetch window layout ===
      // Pure metadata — works regardless of whether any C_BPartner records exist.
      const layout = await fetchWindowLayout(page, ORG_MASTER_WINDOW_ID);

      allure.attachment('Window Layout JSON', JSON.stringify(layout, null, 2), 'application/json');
      console.log('[INFO] Window layout fetched successfully');

      const layoutFields = collectLayoutFields(layout);
      console.log(`[INFO] Layout contains ${layoutFields.length} field entries`);

      // === STEP 5: Assert each expected column is displayed in the window layout ===
      for (const field of EXPECTED_DISPLAYED_FIELDS) {
        await test.step(`Assert column ${field.columnName} is displayed in the window 540676 layout`, async () => {
          // Presence = the (language-invariant) ColumnName appears among the layout's displayed fields
          const isPresent = layoutFields.includes(field.columnName);

          expect(
            isPresent,
            `Column ${field.columnName} must appear in the window 540676 layout `
              + `(check AD_Field.IsDisplayed and AD_UI_Element visibility for tab 541852). `
              + `Context: ${field.note}`
          ).toBe(true);

          console.log(`[PASS] ${field.columnName} — present in window 540676 layout (${label})`);
        });
      }

      console.log(`[INFO] All ${EXPECTED_DISPLAYED_FIELDS.length} expected columns displayed in window 540676 layout (${label})`);
    });
  });
});
