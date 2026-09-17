import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_INVOICE_WINDOW_ID } from '../utils/WindowIds';

// C_Invoice.DueDate (AD_Field_ID=712526, AD_Tab_ID=263, AD_Window_ID=167) carries
// AD_Field.IsReadOnly='Y' unconditionally (migration 5805840), so any C_Invoice opened in
// window 167 — drafted is enough — exercises the read-only render. The spec creates its own
// customer + drafted invoice and navigates by id (seed-independent).

test.describe('Invoice DueDate read-only', () => {
  test('Datum Faelligkeit is read-only on invoice window (de_DE)', async ({ page }) => {
    allure.epic('E0340: Invoicing');
    allure.tag('F00765: Due Date Management');
    allure.story('DueDate field is rendered read-only on the invoice form');
    allure.severity('critical');
    allure.description(`
## E0340: Invoicing / F00765: Due Date Management

### Test scenario

Verifies that the field **Datum Fälligkeit** (C_Invoice.DueDate) in tab "Rechnung"
of window "Rechnung" (AD_Window_ID=167) is rendered non-editable by the WebUI.

### Assertion mechanism

The frontend renders Date widgets via DatePicker. When the field is readonly the
WidgetRenderer passes inputProps.disabled=true, which surfaces as the HTML disabled
attribute on the underlying <input>. The test:

1. Creates its own customer and a drafted C_Invoice via REST (seed-independent).
2. Logs in as the WebUI role and navigates to the invoice by id.
3. Opens Advanced Edit and asserts the DueDate input is disabled and its calendar stays closed.
    `);

    test.setTimeout(180000);

    const WEBAPI = (process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api').replace(/\/rest\/api$/, '');
    const REST = `${WEBAPI}/rest/api`;

    // REST login avoids the AlreadyLoggedInException race on the UI login form.
    await test.step('Authenticate via REST (WebUI role)', async () => {
      const sessionResp = await page.request.get(`${REST}/userSession`).catch(() => null);
      const sessionBody = sessionResp ? await sessionResp.json().catch(() => ({})) : {};

      if (sessionBody.loggedIn) {
        console.log(`[STEP 0] Session already active as ${sessionBody.username} (${sessionBody.rolename})`);
      } else {
        const authResp = await page.request.post(`${REST}/login/authenticate`, {
          data: { username: 'metasfresh', password: 'metasfresh' },
        });
        const authBody = await authResp.json().catch(() => ({}));
        console.log('[STEP 0] authenticate loginComplete:', authBody.loginComplete);

        if (authBody.loginComplete === false && authBody.roles && authBody.roles.length > 0) {
          const webUiRole = authBody.roles[0];
          await page.request.post(`${REST}/login/loginComplete`, { data: webUiRole });
          console.log('[STEP 0] loginComplete sent for role:', webUiRole.caption);
        }
      }
    });

    let bpartnerId;
    await test.step('Create a customer via masterdata', async () => {
      const masterdata = await Backend.createMasterdata({
        request: {
          login: { user: { language: 'de_DE' } },
          bpartners: {
            CUSTOMER1: { isCustomer: true, isVendor: false, isSoPriceList: true, name: 'DueDateCustomer' },
          },
        },
      });

      bpartnerId = masterdata.bpartners.CUSTOMER1.id;
      expect(bpartnerId).toBeTruthy();
      console.log(`[STEP 1] Created customer C_BPartner_ID=${bpartnerId}`);
    });

    // A New record + BPartner (cascades pricelist/currency/location) + doctype + payment term
    // is enough to persist a drafted C_Invoice. Window 167 is the Sales Invoice window, so every
    // C_DocTypeTarget_ID option is a sales invoice; ids come from the dropdowns to stay DB-agnostic.
    let invoiceRecordId;
    await test.step('Create a drafted sales invoice via document API', async () => {
      // { data: [] } is the WebUI JSON-patch payload that allocates a fresh NEW document (no field changes yet).
      const newResp = await page.request.patch(`${REST}/window/${SALES_INVOICE_WINDOW_ID}/NEW`, { data: [] });
      const newBody = await newResp.json();
      invoiceRecordId = (newBody.documents || [newBody])[0].id;
      console.log(`[STEP 2] New invoice document ${invoiceRecordId}`);

      const patch = (changes) =>
        page.request.patch(`${REST}/window/${SALES_INVOICE_WINDOW_ID}/${invoiceRecordId}`, { data: changes });

      const firstDropdownKey = async (field) => {
        const resp = await page.request.get(
          `${REST}/window/${SALES_INVOICE_WINDOW_ID}/${invoiceRecordId}/field/${field}/dropdown`
        );
        const values = (await resp.json()).values || [];
        return values.length ? values[0].key : null;
      };

      await patch([{ op: 'replace', path: 'C_BPartner_ID', value: Number(bpartnerId) }]);

      const docTypeKey = await firstDropdownKey('C_DocTypeTarget_ID');
      expect(docTypeKey).toBeTruthy(); // empty C_DocTypeTarget dropdown = unusable setup; fail fast rather than patch id 0
      await patch([{ op: 'replace', path: 'C_DocTypeTarget_ID', value: Number(docTypeKey) }]);

      // An empty patch round-trips the document so the server recomputes and returns its validStatus.
      const reloaded = await (await patch([])).json();
      let valid = (reloaded.documents || [reloaded])[0].validStatus;
      if (!valid || valid.valid !== true) {
        const paymentTermKey = await firstDropdownKey('C_PaymentTerm_ID');
        expect(paymentTermKey).toBeTruthy(); // no payment term available to make the record valid = unusable setup
        const afterPT = await (await patch([{ op: 'replace', path: 'C_PaymentTerm_ID', value: Number(paymentTermKey) }])).json();
        valid = (afterPT.documents || [afterPT])[0].validStatus;
      }

      expect(valid && valid.valid).toBe(true);
      console.log(`[STEP 2] Drafted invoice ${invoiceRecordId} is valid and persisted`);
    });

    await test.step('Open the created invoice record by ID', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${SALES_INVOICE_WINDOW_ID}/${invoiceRecordId}`, {
        timeout: 120000,
      });

      // The REST session cookie should keep the SPA logged in; if it still bounced us back to
      // /login the record never renders — surface that here instead of as a downstream timeout.
      await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });

      await page
        .locator('.header-wrapper, .window-wrapper')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      await page
        .locator('.rotating, .panel-spaced-lg')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      await page.waitForTimeout(500);
      console.log(`[STEP 3] Invoice record ${invoiceRecordId} loaded`);
    });

    // Click the document header so Alt+E / Advanced Edit targets the document, not the line grid.
    await test.step('Focus main document header', async () => {
      const docNoInput = page.locator('.form-field-DocumentNo input').first();
      const hasDocNo = await docNoInput.isVisible().catch(() => false);
      if (hasDocNo) {
        await docNoInput.click({ force: true });
        await page.waitForTimeout(300);
        console.log('[STEP 4] Clicked DocumentNo to focus main document');
      } else {
        const headerSection = page.locator('.header-breadcrumb-dropdown, .document-list-header').first();
        if (await headerSection.isVisible().catch(() => false)) {
          await headerSection.click({ force: true });
          await page.waitForTimeout(300);
        }
        console.log('[STEP 4] Header focus established via fallback');
      }
    });

    // SubHeader is preferred over Alt+E because keyboard focus after programmatic navigation is unreliable.
    await test.step('Open Advanced Edit modal via SubHeader', async () => {
      const moreButton = page.locator('.meta-icon-more');
      await moreButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await moreButton.click();

      await page
        .locator('.subheader-container')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('[STEP 5] SubHeader opened');

      const advEditItem = page.locator('.subheader-item:has(.meta-icon-edit)').first();
      await advEditItem.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await advEditItem.click();

      await page
        .locator('.panel-modal')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await page
        .locator('.panel-modal .form-field-DueDate')
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('[STEP 5] Advanced Edit modal opened');
    });

    await test.step('Assert DueDate field is rendered read-only (disabled)', async () => {
      const dueDateWrapper = page.locator('.panel-modal .form-field-DueDate');
      await dueDateWrapper.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      console.log('[STEP 6] .form-field-DueDate wrapper found in Advanced Edit modal');

      const dueDateInput = dueDateWrapper.locator('input').first();
      await dueDateInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      await expect(dueDateInput).toBeDisabled({ timeout: SLOW_ACTION_TIMEOUT });
      console.log('[STEP 6] DueDate input is disabled [PASS]');

      // A readonly DatePicker is a no-op on click — the calendar must stay closed.
      await dueDateInput.click({ force: true }).catch(() => {});
      await page.waitForTimeout(500);

      const calendarPopup = page.locator('.panel-modal .rdtPicker, .panel-modal .rdtOpen');
      const calendarVisible = await calendarPopup.first().isVisible().catch(() => false);
      expect(calendarVisible).toBe(false);
      console.log('[STEP 6] Calendar popup did NOT open on click [PASS]');

      const screenshot = await page.screenshot();
      allure.attachment('DueDate field (read-only)', screenshot, 'image/png');
    });

    console.log(`[DONE] Invoice ${invoiceRecordId}: DueDate (Datum Faelligkeit) is rendered read-only — PASS`);
  });
});
