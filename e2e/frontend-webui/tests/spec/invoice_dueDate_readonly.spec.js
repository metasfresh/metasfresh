import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';

/**
 * Playwright spec: C_Invoice.DueDate (Datum Fälligkeit) is rendered READ-ONLY.
 *
 * Validates the UI counterpart of migration 5805840 which sets
 * AD_Field.IsReadOnly='Y' for AD_Field_ID=712526 (DueDate in tab 263, window 167).
 *
 * The field lives in tab "Rechnung" (AD_Tab_ID=263) of window "Rechnung" (167).
 * The frontend renders Date widgets via DatePicker whose inputProps.disabled is
 * set to true when the field's data.readonly===true (WidgetRenderer.js line 171).
 * The resulting <input> therefore carries the HTML disabled attribute.
 *
 * me03 issue: https://github.com/metasfresh/me03/issues/29412
 */

test.describe('Invoice DueDate readonly (migration 5805840)', () => {
  test('Datum Faelligkeit is read-only on invoice window (de_DE)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0340_Invoicing');
    allure.feature('F00700_Invoicing');
    allure.story('DueDate rendered read-only — migration 5805840');
    allure.severity('critical');
    allure.description(`
## E0340_Invoicing / F00700_Invoicing

### Test scenario

Verifies that the field **Datum Fälligkeit** (C_Invoice.DueDate, AD_Field_ID=712526)
in tab "Rechnung" of window "Rechnung" (AD_Window_ID=167) is rendered non-editable
by the WebUI after migration 5805840 set AD_Field.IsReadOnly='Y'.

### Assertion mechanism

The metasfresh frontend renders Date widgets via the DatePicker component. When
the field carries readonly=true in the API response, WidgetRenderer passes
inputProps.disabled=true to DatePicker, which propagates it as the HTML disabled
attribute on the underlying <input> element. The test:

1. Logs in as metasfresh/metasfresh (WebUI role).
2. Navigates to the invoice list (window 167) and opens the first available record.
3. Locates the DueDate widget wrapper via .form-field-DueDate.
4. Asserts the inner <input> carries the disabled attribute (toBeDisabled()).
5. Also asserts the calendar does NOT open on click (readonly guard in DatePicker).

### Related artefacts

- Migration: 5805840_sys_gh29412_C_Invoice_DueDate_readonly.sql
- AD_Field_ID: 712526  AD_Tab_ID: 263  AD_Window_ID: 167
    `);

    test.setTimeout(180000); // 3 minutes — page load can be slow on first navigation

    // =========================================================================
    // STEP 1: Establish an authenticated session via REST, then navigate.
    //
    // We bypass the UI login form entirely and use the REST API directly.
    // This avoids the "AlreadyLoggedInException" race that occurs when the
    // Playwright button-click fires loginComplete after authenticate has
    // already established a session. Using page.request gives us a Playwright
    // request context that shares cookies with the page navigation.
    // =========================================================================
    await test.step('Authenticate via REST (WebUI role)', async () => {
      const WEBAPI = 'http://localhost:8080';

      // Step A: check if there's already a valid session (avoids 500 on loginComplete)
      const sessionResp = await page.request.get(`${WEBAPI}/rest/api/userSession`).catch(() => null);
      const sessionBody = sessionResp ? await sessionResp.json().catch(() => ({})) : {};

      if (sessionBody.loggedIn) {
        console.log(`[STEP 1] Session already active as ${sessionBody.username} (${sessionBody.rolename}) — skipping login`);
      } else {
        // Step B: authenticate
        const authResp = await page.request.post(`${WEBAPI}/rest/api/login/authenticate`, {
          data: { username: 'metasfresh', password: 'metasfresh' },
        });
        const authBody = await authResp.json().catch(() => ({}));
        console.log('[STEP 1] authenticate loginComplete:', authBody.loginComplete);

        // Step C: if role selection needed, pick the WebUI role (roles[0])
        if (authBody.loginComplete === false && authBody.roles && authBody.roles.length > 0) {
          const webUiRole = authBody.roles.find((r) => r.key === '540024_1000000_1000000') || authBody.roles[0];
          await page.request.post(`${WEBAPI}/rest/api/login/loginComplete`, {
            data: webUiRole,
          });
          console.log('[STEP 1] loginComplete sent for role:', webUiRole.caption);
        }
      }

      // Step D: navigate to the invoice list URL so the SPA session is
      // initialised. We use the login page first (it loads faster since it
      // doesn't need auth) and then navigate to the window.
      await page.goto(`${FRONTEND_BASE_URL}/login`, { timeout: 120000 });
      // The SPA should redirect away from /login because we're already logged in
      // (session cookie set by REST calls above). Wait for the redirect.
      await page
        .waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT })
        .catch(async () => {
          // Not redirected — navigate manually to the invoice list
          await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_INVOICE_WINDOW_ID}`, { timeout: 120000 });
        });
      await page
        .locator('.app-content, .document-list-wrapper, .document-list, .login-container')
        .first()
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      console.log('[STEP 1] SPA loaded, URL:', page.url());
    });

    // =========================================================================
    // STEP 2: Open the first accessible invoice record (already on list from Step 1).
    // =========================================================================
    let invoiceRecordId;
    await test.step('Open first invoice record from list', async () => {
      // List was loaded during Step 1 navigation — just wait for it to settle
      await page
        .locator('.document-list-wrapper, .document-list')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      await page
        .locator('.rotating')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      // dblclick first row to open record detail
      const firstRow = page.locator('table tbody tr').first();
      await firstRow.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await firstRow.dblclick();

      await page.waitForURL(
        (url) => /\/window\/167\/\d+/.test(url.toString()),
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      const urlMatch = page.url().match(/\/window\/167\/(\d+)/);
      invoiceRecordId = urlMatch ? urlMatch[1] : 'unknown';

      // Wait for document header panel
      await page
        .locator('.header-wrapper, .window-wrapper')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await page
        .locator('.rotating, .panel-spaced-lg')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      await page.waitForTimeout(500);
      console.log(`[STEP 2] Invoice record ${invoiceRecordId} loaded`);
    });

    // =========================================================================
    // STEP 3: Click on the main document header area to focus the document
    // (not a sub-tab grid row) before pressing Alt+E.
    //
    // After dblclick-open the grid sub-tab has focus. Alt+E then opens the
    // Advanced Edit for a LINE. We need to click a document-level field first.
    // =========================================================================
    await test.step('Focus main document header (scope Alt+E to document)', async () => {
      // The DocumentNo field is always present in the main form and is disabled
      // (read-only on completed invoices) — safe to click without side-effects.
      const docNoInput = page.locator('.form-field-DocumentNo input').first();
      const hasDocNo = await docNoInput.isVisible().catch(() => false);
      if (hasDocNo) {
        await docNoInput.click({ force: true });
        await page.waitForTimeout(300);
        console.log('[STEP 3] Clicked DocumentNo to focus main document');
      } else {
        // Fallback: click the header section wrapper
        const headerSection = page.locator('.header-breadcrumb-dropdown, .document-list-header').first();
        if (await headerSection.isVisible().catch(() => false)) {
          await headerSection.click({ force: true });
          await page.waitForTimeout(300);
        }
        console.log('[STEP 3] Header focus established via fallback');
      }
    });

    // =========================================================================
    // STEP 4: Open the Advanced Edit modal via SubHeader UI button.
    //
    // AD_UI_Element 615817 places DueDate in the "advanced edit" section of
    // the main invoice document (AD_Tab_ID=263). This section is only visible
    // via the Advanced Edit modal (SubHeader → pencil icon, or Alt+E).
    //
    // We prefer the SubHeader click-path over Alt+E because the keyboard
    // shortcut requires document focus which can be unreliable after
    // programmatic navigation.
    // =========================================================================
    await test.step('Open Advanced Edit modal via SubHeader', async () => {
      // Open the SubHeader panel (the "more" / three-dot button)
      const moreButton = page.locator('.meta-icon-more');
      await moreButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await moreButton.click();

      // Wait for the SubHeader container to be open
      await page
        .locator('.subheader-container')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('[STEP 4] SubHeader opened');

      // Click the Advanced Edit button in the SubHeader.
      // SubHeader.js renders MenuItem with id="subheaderNav_{simplifiedCaption}"
      // and class="subheader-item". The edit icon is <i class="meta-icon-edit"/>.
      // We target the subheader-item that contains the meta-icon-edit icon.
      const advEditItem = page.locator('.subheader-item:has(.meta-icon-edit)').first();
      await advEditItem.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await advEditItem.click();

      // Wait for the modal panel to appear
      await page
        .locator('.panel-modal')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page.waitForTimeout(500);
      console.log('[STEP 4] Advanced Edit modal opened');
    });

    // =========================================================================
    // STEP 5: Locate DueDate widget and assert it is disabled
    // =========================================================================
    await test.step('Assert DueDate field is rendered read-only (disabled)', async () => {
      // The RawWidget wrapper gets class "form-field-DueDate" (from widgetFieldsName).
      // Inside it, the DatePicker renders <input class="form-control"> with disabled
      // when inputProps.disabled=true (which WidgetRenderer sets to readonly).
      // The field is scoped inside the Advanced Edit modal.
      const dueDateWrapper = page.locator('.panel-modal .form-field-DueDate');

      // The wrapper must exist in the modal
      await dueDateWrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('[STEP 5] .form-field-DueDate wrapper found in Advanced Edit modal');

      // The input inside the DatePicker carries the disabled attribute
      const dueDateInput = dueDateWrapper.locator('input').first();
      await dueDateInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // PRIMARY ASSERTION: input must be disabled
      await expect(dueDateInput).toBeDisabled({ timeout: SLOW_ACTION_TIMEOUT });
      console.log('[STEP 5] DueDate input is disabled [PASS]');

      // SECONDARY ASSERTION: clicking does NOT open the calendar popup
      // DatePicker.openCalendarIfEditable() is a no-op when isReadonly()==true.
      await dueDateInput.click({ force: true }).catch(() => {});
      await page.waitForTimeout(500);

      // Scope popup check inside the modal to avoid false positives from other widgets
      const calendarPopup = page.locator('.panel-modal .rdtPicker, .panel-modal .rdtOpen');
      const calendarVisible = await calendarPopup.first().isVisible().catch(() => false);
      expect(calendarVisible).toBe(false);
      console.log('[STEP 5] Calendar popup did NOT open on click [PASS]');

      // Screenshot for the Allure report
      const screenshot = await page.screenshot();
      allure.attachment('DueDate field (read-only)', screenshot, 'image/png');
    });

    console.log(
      `[DONE] Invoice ${invoiceRecordId}: DueDate (Datum Faelligkeit) is rendered read-only — PASS`
    );
  });
});
