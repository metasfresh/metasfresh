import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { VENDOR_INVOICE_WINDOW_ID } from '../utils/WindowIds';

const SLOW = SLOW_ACTION_TIMEOUT;
const WEBAPI = (process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api').replace(/\/rest\/api$/, '');
const REST = `${WEBAPI}/rest/api`;

// Target the CORE purchase-invoice window 183 "Eingangsrechnung" (C_Invoice.PO_Window_ID=183),
// invoice-line tab 291 (C_InvoiceLine). This is the standard window shipped by core migrations and
// used by plain-core customers that have no purchase-invoice override window. The per-line GL-account
// override field C_ElementValue_Override_ID lives on tab 291 (IsDisplayed='Y', IsReadOnly='N',
// ReadOnlyLogic '@Processed@=Y' — editable while the invoice is a DRAFT). Everything (document
// CRUD via REST, the UI set/save/reload, and the grid-column assertion) runs against window 183, so
// the spec is valid on the generic core preloaded DB that core CI runs against.
const CRUD_WIN = VENDOR_INVOICE_WINDOW_ID; // Eingangsrechnung — core PO-invoice window (document CRUD + UI + grid)
const CRUD_LINE_TAB = 291; // C_InvoiceLine tab on window 183

/**
 * Per-line GL account override set directly on a DRAFT purchase invoice line (F01010.4).
 *
 * Verifies the C_ElementValue_Override_ID field on the purchase-invoice line is editable in
 * the WebUI and persists: a GL account picked in the field survives a page reload AND is
 * present in the system of record (read back via REST on the line). The draft invoice + a
 * product line are created via the WebUI document REST API (deterministically draft — the
 * field is read-only once the invoice is completed); the override itself is set through the
 * UI to prove UI editability. Language-independent: selects only on DB ColumnName.
 */
test.describe('Vendor Invoice line - GL account override (F01010.4)', () => {
  test('C_ElementValue_Override_ID is editable and persists on a draft purchase invoice line', async ({ page }) => {
    test.setTimeout(180_000);
    allure.epic('E0340: Invoicing');
    allure.tag('F01010.4: Invoice Accounting Overrides');
    allure.tag('F01010.4');
    allure.story('Set the per-line GL account override on a draft purchase invoice line');

    global.currentPage = page;

    // --- master data: a vendor + a product on a purchase price list ---
    const md = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US' } },
        bpartners: { VENDOR: { isVendor: true, isCustomer: false, isSoPriceList: false } },
        products: { PRODUCT: { type: 'Item', prices: [{ price: 10.0, currencyCode: 'EUR' }] } },
      },
    });
    const vendorId = md.bpartners.VENDOR.id;
    const productId = md.products.PRODUCT.id;
    expect(vendorId).toBeTruthy();
    expect(productId).toBeTruthy();

    await LoginPage.goto();
    await LoginPage.login(md.login.user);
    await LoginPage.expectLoggedIn();

    // --- helpers on the document REST API (session cookie shared with the browser context) ---
    // Document CRUD, the UI edit, and the grid assertion all run against core window 183 (CRUD_WIN).
    const firstDropdownKey = async (recordId, field) => {
      const resp = await page.request.get(`${REST}/window/${CRUD_WIN}/${recordId}/field/${field}/dropdown`);
      const values = (await resp.json()).values || [];
      return values.length ? values[0].key : null;
    };
    const patchDoc = async (recordId, changes) => {
      const r = await page.request.patch(`${REST}/window/${CRUD_WIN}/${recordId}`, { data: changes });
      return await r.json();
    };

    // --- create a DRAFT purchase invoice header ---
    let invoiceId;
    await test.step('Create a drafted purchase invoice via document API', async () => {
      const newBody = await (await page.request.patch(`${REST}/window/${CRUD_WIN}/NEW`, { data: [] })).json();
      invoiceId = (newBody.documents || [newBody])[0].id;
      expect(invoiceId).toBeTruthy();

      await patchDoc(invoiceId, [{ op: 'replace', path: 'C_BPartner_ID', value: Number(vendorId) }]);
      const docTypeKey = await firstDropdownKey(invoiceId, 'C_DocTypeTarget_ID');
      expect(docTypeKey, 'a purchase-invoice C_DocTypeTarget option must exist').toBeTruthy();
      await patchDoc(invoiceId, [{ op: 'replace', path: 'C_DocTypeTarget_ID', value: Number(docTypeKey) }]);

      let reloaded = await patchDoc(invoiceId, []);
      let valid = (reloaded.documents || [reloaded])[0].validStatus;
      if (!valid || valid.valid !== true) {
        const ptKey = await firstDropdownKey(invoiceId, 'C_PaymentTerm_ID');
        if (ptKey) {
          const after = await patchDoc(invoiceId, [{ op: 'replace', path: 'C_PaymentTerm_ID', value: Number(ptKey) }]);
          valid = (after.documents || [after])[0].validStatus;
        }
      }
      expect(valid && valid.valid, `drafted invoice must be valid (reason: ${valid && valid.reason})`).toBe(true);
      console.log(`[STEP] drafted invoice ${invoiceId} valid=${valid && valid.valid}`);
    });

    // --- create an invoice line (product) via document API on the C_InvoiceLine tab ---
    let lineId;
    let crudLineTabId;

    // GET the line row's document from the system of record (base window 183). The single-row GET
    // returns a bare array of documents; the line doc carries the row's fieldsByName + validStatus.
    const getLineDoc = async () => {
      const r = await page.request.get(`${REST}/window/${CRUD_WIN}/${invoiceId}/${crudLineTabId}/${lineId}`);
      const body = await r.json();
      const arr = Array.isArray(body) ? body : body.documents || [body];
      return arr.find((d) => String(d.rowId) === String(lineId)) || arr[0] || {};
    };
    // Read-only oracle for the override field on the line record.
    const getLineOverrideField = async () => {
      const doc = await getLineDoc();
      return (doc.fieldsByName || {})['C_ElementValue_Override_ID'] || {};
    };

    await test.step('Create an invoice line (product) via document API', async () => {
      // The REST tab path is the layout's tabId (not necessarily the raw AD_Tab_ID). Discover it.
      const layout = await (await page.request.get(`${REST}/window/${CRUD_WIN}/layout`)).json();
      const lineTab = (layout.tabs || []).find(
        (t) => t.tableName === 'C_InvoiceLine' || /InvoiceLine/i.test(t.internalName || '')
      );
      expect(lineTab, 'invoice-line tab present in base-window layout').toBeTruthy();
      crudLineTabId = lineTab.tabId;
      console.log(`[STEP] CRUD line tabId=${crudLineTabId}`);

      const newLineResp = await page.request.patch(
        `${REST}/window/${CRUD_WIN}/${invoiceId}/${crudLineTabId}/NEW`,
        { data: [] }
      );
      const newLineBody = await newLineResp.json();
      const docs = newLineBody.documents || [newLineBody];
      // In the WebUI document API a child row is addressed by (parent id, tabId, rowId): the doc's
      // own `id` is the PARENT invoice id, while the new line's id is in `rowId`.
      const lineDoc = docs.find((d) => d.rowId) || docs[0];
      // Assert the new line actually carries its own `rowId` — never silently fall back to the parent
      // `id`, which would make every later line PATCH target the header. Do NOT assert lineId !==
      // invoiceId: C_Invoice_ID and C_InvoiceLine_ID are independent per-table sequences that
      // legitimately coincide on a fresh DB (both start at 1000000), so numeric id-inequality is a false
      // invariant that fails purely on test-ordering luck (e.g. when this spec runs first in its shard).
      expect(lineDoc.rowId, 'the new invoice-line document must carry its own rowId').toBeTruthy();
      lineId = lineDoc.rowId;
      console.log(`[STEP] invoice line rowId=${lineId} (parent invoice ${invoiceId})`);

      const patchLine = (changes) =>
        page.request.patch(`${REST}/window/${CRUD_WIN}/${invoiceId}/${crudLineTabId}/${lineId}`, { data: changes });
      await patchLine([{ op: 'replace', path: 'M_Product_ID', value: Number(productId) }]);
      await patchLine([{ op: 'replace', path: 'QtyEntered', value: 1 }]);

      // Confirm the line is valid (product + qty + auto-computed price) in the system of record
      // before we drive the UI. GET the line row directly (an empty PATCH returns no documents).
      const lineDocRead = await getLineDoc();
      const lineValid = lineDocRead && lineDocRead.validStatus;
      const productVal = lineDocRead && (lineDocRead.fieldsByName || {})['M_Product_ID'];
      console.log(
        `[STEP] invoice line ${lineId} valid=${lineValid && lineValid.valid} product=${productVal && JSON.stringify(productVal.value)}`
      );
      // The line must carry the product (the concrete "valid line" invariant this feature needs);
      // a doc-level validStatus is asserted too when the API returns one.
      expect(productVal && productVal.value, 'line must carry the product').toBeTruthy();
      if (lineValid) {
        expect(lineValid.valid, `line must be valid (reason: ${lineValid.reason})`).toBe(true);
      }
    });

    // Set the override on the LINE row through its Advanced Edit modal (the reliable way to edit a
    // subtable lookup field). The frontend wires Advanced Edit for a subtable row via its right-click
    // context menu (containers/Table.handleAdvancedEdit → modal with rowId=selected line,
    // isAdvanced=true); the window-level Alt+E targets the HEADER instead. So: right-click the line
    // row (auto-selects it + opens .context-menu-open) and click the Advanced Edit item
    // (.meta-icon-edit). The field then renders inside `.panel-modal` as
    // #lookup_C_ElementValue_Override_ID. Language-independent (route ids + DB ColumnName +
    // structural classes only).
    const overrideLookupSel = '.panel-modal #lookup_C_ElementValue_Override_ID';
    const openLineDetail = async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${CRUD_WIN}/${invoiceId}`, { timeout: 120000 });
      await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW });
      await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW }).catch(() => {});

      // Right-click the line row → its context menu; click the Advanced Edit item.
      const lineRow = page.locator('.table-flex-wrapper table tbody tr, table tbody tr').first();
      await lineRow.waitFor({ state: 'visible', timeout: SLOW });
      await lineRow.click({ button: 'right' });
      const advEdit = page.locator('.context-menu-open .context-menu-item:has(.meta-icon-edit)').first();
      await advEdit.waitFor({ state: 'visible', timeout: SLOW });
      await advEdit.click();

      await page.locator('.panel-modal').waitFor({ state: 'visible', timeout: SLOW });
      await page.locator('.panel-modal-content').waitFor({ state: 'visible', timeout: SLOW }).catch(() => {});

      const hasOverride = await page
        .locator(`${overrideLookupSel} input.input-field`)
        .isVisible()
        .catch(() => false);
      console.log(`[DIAG] line advanced-edit modal has override field = ${hasOverride}`);
      await page.locator(`${overrideLookupSel} input.input-field`).waitFor({ state: 'visible', timeout: SLOW });
    };

    // --- open the line detail in the UI and set the GL override there ---
    await test.step('Open the invoice line in the UI and set the override', async () => {
      await openLineDetail();
      console.log(`[STEP] line detail URL: ${page.url()}`);

      const overrideLookup = page.locator(overrideLookupSel);
      const overrideInput = overrideLookup.locator('input.input-field');
      await overrideInput.waitFor({ state: 'visible', timeout: SLOW });
      // Frame the override field in the MIDDLE of the modal BEFORE opening the lookup, so both the
      // field and its dropdown render on-screen and the value is not covered by the bottom-centred
      // caption (the field is the LAST field in the tall Advanced-Edit modal). Post-production only.
      await overrideLookup.scrollIntoViewIfNeeded();
      await overrideInput.evaluate((el) => el.scrollIntoView({ block: 'center' }));
      await page.waitForTimeout(600);
      await overrideInput.click();
      await overrideLookup.locator('.rotating, .spinner').waitFor({ state: 'detached', timeout: SLOW }).catch(() => {});
      // GL account values are numeric — a digit returns options on a real chart of accounts
      await overrideInput.fill('1');
      await page.waitForTimeout(700);
      await overrideLookup.locator('.rotating, .spinner').waitFor({ state: 'detached', timeout: SLOW }).catch(() => {});
      const firstOption = page.locator('.input-dropdown-list-option').first();
      await firstOption.waitFor({ state: 'visible', timeout: SLOW });
      const pickedText = (await firstOption.innerText()).trim();
      expect(pickedText.length).toBeGreaterThan(0);
      await firstOption.click();
      await page.locator('.input-dropdown-list').waitFor({ state: 'detached', timeout: SLOW }).catch(() => {});
      // Frame the just-picked named value in the middle of the modal for a beat so the video shows
      // the line field carrying the account BEFORE it is saved/reloaded (the "set" half).
      await overrideInput.evaluate((el) => el.scrollIntoView({ block: 'center' }));
      await page.waitForTimeout(1500);
    });

    // --- save (blur), await persistence of the LINE, then assert on reload + in the system of record ---
    let savedValue;
    await test.step('Save the line and read back the stored value', async () => {
      // The lookup option-click already persists the field (the WebUI PATCHes the line on selection,
      // not on blur), so there is no separate save-on-Tab PATCH to await. Blur to commit any pending
      // edit, then assert the END RESULT loudly: poll the system of record until the override lands
      // (fails the step if it never persists — no silent green). This is the authoritative save check.
      await page.keyboard.press('Tab');
      let field;
      await expect
        .poll(
          async () => {
            field = await getLineOverrideField();
            return !!(field && field.value);
          },
          { timeout: SLOW, message: 'C_ElementValue_Override_ID must persist on the line record' }
        )
        .toBe(true);
      console.log(`[STEP] REST line C_ElementValue_Override_ID value=${JSON.stringify(field.value)}`);

      savedValue = (await page.locator(`${overrideLookupSel} input.input-field`).inputValue()).trim();
      expect(savedValue.length, 'override input must show a picked value after save').toBeGreaterThan(0);
    });

    await test.step('Reload the line detail and assert the override persisted', async () => {
      await openLineDetail();
      const reloadedLookup = page.locator(overrideLookupSel);
      const reloadedInput = reloadedLookup.locator('input.input-field');
      await expect(reloadedInput).toHaveValue(savedValue, { timeout: SLOW });
      // Frame the persisted value in the middle of the modal for a beat so the video clearly shows
      // it survived the reload (not covered by the bottom-centred caption).
      await reloadedInput.evaluate((el) => el.scrollIntoView({ block: 'center' }));
      await page.waitForTimeout(1500);

      // And re-confirm in the system of record after the reload.
      const field = await getLineOverrideField();
      expect(field.value, 'override still present in the system of record after reload').toBeTruthy();
    });

    // --- GRID visibility (THE change under test, migration 5813050) ---
    // The override field now renders as a COLUMN in the purchase-invoice LINE grid of the live
    // PO-invoice window 183 "Eingangsrechnung" (C_Invoice.PO_Window_ID=183), tab 291 C_InvoiceLine:
    // AD_UI_Element 652327 IsDisplayedGrid='Y', SeqNoGrid=60 (the WebUI grid renders from
    // AD_UI_Element). Open window 183's invoice header — its included C_InvoiceLine tab renders as a
    // grid — and assert the override column header is present AND the created line's cell shows the
    // account picked above (demonstrates the flag change in successful action, against real data).
    // Language-independent: the grid column header/cell are keyed on the DB ColumnName —
    // TableHeader emits th[data-testid="column-<ColumnName>"]; cells carry [data-cy="cell-<ColumnName>"].
    await test.step('Override column shows in the window-183 invoice-line grid with the set value', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${CRUD_WIN}/${invoiceId}`, { timeout: 120000 });
      await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW });
      await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW }).catch(() => {});

      // The included C_InvoiceLine tab grid header carries the new override column.
      const overrideHeader = page.locator('th[data-testid="column-C_ElementValue_Override_ID"]');
      await overrideHeader.waitFor({ state: 'visible', timeout: SLOW });
      await expect(overrideHeader, 'override column header present in the line grid').toBeVisible();

      // The created line's cell for the override column shows the account set above.
      const overrideCell = page
        .locator('.table-flex-wrapper table tbody tr, table tbody tr')
        .first()
        .locator('[data-cy="cell-C_ElementValue_Override_ID"]');
      await overrideCell.waitFor({ state: 'visible', timeout: SLOW });
      await overrideCell.scrollIntoViewIfNeeded();
      const cellText = (await overrideCell.innerText()).trim();
      console.log(`[STEP] line grid override cell = "${cellText}" (set value was "${savedValue}")`);
      expect(cellText.length, 'override grid cell must display the set account (value visible in grid)').toBeGreaterThan(0);
      expect(cellText, 'override grid cell shows the account picked on the line').toContain(savedValue);
      // Frame the grid cell for a beat so the video shows the account rendered in the grid column.
      await page.waitForTimeout(1200);
    });
  });
});
