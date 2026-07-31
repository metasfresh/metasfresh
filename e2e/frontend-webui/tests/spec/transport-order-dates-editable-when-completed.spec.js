import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { PURCHASE_ORDER_WINDOW_ID, SHIPPER_WINDOW_ID, TRANSPORT_ORDER_WINDOW_ID } from '../utils/WindowIds';

/** AD_Tab_ID of the "PO Line" tab in the purchase order window. */
const PURCHASE_ORDER_LINE_TAB_ID = 'AD_Tab-293';

/**
 * AD_Process_ID 584645 = C_Order_AddTo_M_ShipperTransportation ("Add Purchase Orders to Transportation
 * Order"), the grid action a procurement worker runs on a completed purchase order to book it onto a
 * transport order. It is a view action, so it must be invoked with a view + selected row ids.
 */
const ADD_PO_TO_TRANSPORT_ORDER_PROCESS_ID = 'ADP_584645';

/**
 * Transport order (M_ShipperTransportation, window 540020 "Transport Auftrag", tab 540096
 * "Speditionslieferung"): B/L Date and ETA must stay editable after the document is completed.
 *
 * A completed transport order carries Processed='Y', and DocumentReadonly#computeFieldReadonly
 * renders EVERY field of a processed document read-only unless its AD_Column.IsAlwaysUpdateable='Y'.
 * The bill of lading is issued by the carrier only after loading, i.e. normally AFTER the transport
 * order is done, so both dates must remain enterable at that point.
 *
 * Scope — this is deliberately the ONLY thing this spec asserts. What the two dates then trigger
 * (M_ShipperTransportation -> C_Order -> C_OrderPaySchedule due-date recomputation) is owned by the
 * cucumber scenarios S30954_1..S30954_3 in purchaseOrderComplexPaymentTerm.feature, which drive the
 * real @ModelChange interceptors on a full stack. Re-asserting that here would duplicate a behaviour
 * that already has a home. What cucumber cannot reach is this read-only gate: its step writes through
 * record.setBLDate(...) + saveRecord, bypassing the WebUI Document layer entirely — only a real
 * browser session exercises it.
 */
test.describe('Transport order — B/L Date and ETA editable while completed', () => {
  test('B/L Date and ETA stay editable on a completed transport order', async ({ page }) => {
    // Same epic/feature labels the S30954 cucumber scenarios carry, so both halves of the coverage
    // land under one heading in the Allure report.
    allure.epic('E0140: Purchasing');
    allure.tag('F00600: Purchase Order');
    allure.tag('F00600');
    allure.tag('F00994: Multiple Levels of Payment');
    allure.tag('F00994');
    allure.story('B/L Date and ETA remain editable after the transport order is completed');
    allure.severity('critical');
    allure.description(`
## Transport order — B/L Date / ETA on a completed document

### Test scenario

1. Creates its own carrier, shipper, vendor, product and purchase order, books the purchase order
   onto a new transport order and completes it (setup via REST — seed-independent). A transport
   order cannot be completed without a shipping package, hence the purchase order.
2. Opens the completed transport order in window ${TRANSPORT_ORDER_WINDOW_ID} in the browser.
3. Asserts **B/L Date** and **ETA** are rendered editable and that a date typed into them is accepted
   and survives a page reload.
4. Asserts, as a control on the very same form, that **Document Date** (DateDoc — not always-updateable)
   IS read-only. Without that control an "everything is editable" regression would pass unnoticed.

### Assertion mechanism

The frontend renders Date widgets via DatePicker; a read-only field surfaces as the HTML \`disabled\`
attribute on the underlying \`<input>\`. So "editable" = input not disabled + the typed value round-trips.
    `);

    test.setTimeout(180000);

    const REST = WEBAPI_BASE_URL;

    // ---------------------------------------------------------------- helpers

    const postJson = async (url, data) => {
      const response = await page.request.post(url, { data });
      if (!response.ok()) {
        throw new Error(`POST ${url} failed: HTTP ${response.status()} ${await response.text()}`);
      }
      // /login/loginComplete answers with an empty body, so do not assume JSON
      const text = await response.text();
      return text ? JSON.parse(text) : {};
    };

    // The window endpoints answer either with a bare document array (GET) or with a
    // { documents: [...] } envelope (PATCH); normalise both to the single root document.
    const firstDocument = (body) => (Array.isArray(body) ? body : body.documents || [body])[0];

    /** WebUI JSON-patch on a root document. `documentId` may be 'NEW' to allocate a fresh record. */
    const patchDocument = async (windowId, documentId, changes) => {
      const response = await page.request.patch(`${REST}/window/${windowId}/${documentId}`, { data: changes });
      if (!response.ok()) {
        throw new Error(
          `PATCH window/${windowId}/${documentId} failed: HTTP ${response.status()} ${await response.text()}`
        );
      }
      return firstDocument(await response.json());
    };

    const getDocument = async (windowId, documentId) => {
      return firstDocument(await (await page.request.get(`${REST}/window/${windowId}/${documentId}`)).json());
    };

    /**
     * The Date widget parses the SESSION LANGUAGE's format and is strict about zero padding
     * ("08/10/2026" is accepted in en_US, "8/10/2026" is silently discarded). The session language is
     * a property of the logged-in user, not of the role we pick, so it is read back from /userSession
     * rather than assumed — and an unmapped language fails loudly here instead of silently feeding the
     * widget an unparsable string and failing later as a confusing "field did not persist".
     */
    const pad = (n) => String(n).padStart(2, '0');
    const DATE_FORMATTERS = {
      en_US: ({ year, month, day }) => `${pad(month)}/${pad(day)}/${year}`,
      de_DE: ({ year, month, day }) => `${pad(day)}.${pad(month)}.${year}`,
      de_CH: ({ year, month, day }) => `${pad(day)}.${pad(month)}.${year}`,
    };
    let sessionLanguage;
    const formatForSession = (date) => {
      const formatter = DATE_FORMATTERS[sessionLanguage];
      if (!formatter) {
        throw new Error(
          `No date format known for session language "${sessionLanguage}" — add it to DATE_FORMATTERS`
        );
      }
      return formatter(date);
    };

    /**
     * Locale-independent date assertion: the WebUI renders dates in the session's language
     * (MM/DD/YYYY, DD.MM.YYYY, ...), so compare the numbers the input shows, not the string.
     */
    const expectInputToShowDate = (inputValue, { year, month, day }) => {
      const numbers = (inputValue.match(/\d+/g) || []).map(Number);
      expect(numbers, `date input value "${inputValue}"`).toEqual(expect.arrayContaining([year, month, day]));
    };

    // ------------------------------------------------------------------ setup

    // REST login avoids the AlreadyLoggedInException race on the UI login form; the session cookie
    // then keeps the SPA logged in for the browser part below.
    await test.step('Authenticate via REST (WebUI role)', async () => {
      const sessionBody = await (await page.request.get(`${REST}/userSession`)).json().catch(() => ({}));
      if (sessionBody.loggedIn) {
        console.log(`[SETUP] Session already active as ${sessionBody.username}`);
        return;
      }
      const authBody = await postJson(`${REST}/login/authenticate`, {
        username: 'metasfresh',
        password: 'metasfresh',
      });
      if (authBody.loginComplete === false && authBody.roles && authBody.roles.length > 0) {
        await postJson(`${REST}/login/loginComplete`, authBody.roles[0]);
        console.log(`[SETUP] loginComplete sent for role: ${authBody.roles[0].caption}`);
      }
    });

    await test.step('Read the session language (drives the date input format)', async () => {
      const session = await (await page.request.get(`${REST}/userSession`)).json();
      expect(session.loggedIn, 'REST session is established').toBe(true);
      sessionLanguage = session.language && session.language.key;
      expect(sessionLanguage, 'session language').toBeTruthy();
      console.log(`[SETUP] Session language is ${sessionLanguage}`);
    });

    let carrierBPartnerId;
    let carrierLocationId;
    let vendorBPartnerId;
    let productId;
    await test.step('Create the carrier, the goods vendor and a product', async () => {
      const masterdata = await Backend.createMasterdata({
        request: {
          bpartners: {
            CARRIER: { name: 'Transport order E2E carrier', isVendor: true, isCustomer: false, isSoPriceList: false },
            VENDOR: { name: 'Transport order E2E vendor', isVendor: true, isCustomer: false, isSoPriceList: false },
          },
          products: {
            PROD1: { name: 'Transport order E2E product', type: 'Item', prices: [{ price: 9.98, currencyCode: 'EUR' }] },
          },
        },
      });

      carrierBPartnerId = masterdata.bpartners.CARRIER.id;
      carrierLocationId = masterdata.bpartners.CARRIER.bpartnerLocationId;
      vendorBPartnerId = masterdata.bpartners.VENDOR.id;
      productId = masterdata.products.PROD1.id;
      expect(carrierBPartnerId).toBeTruthy();
      expect(carrierLocationId).toBeTruthy();
      expect(vendorBPartnerId).toBeTruthy();
      expect(productId).toBeTruthy();
    });

    // The transport order's M_Shipper_ID lookup is restricted to shippers of the chosen shipper
    // partner (AD_Val_Rule "M_Shipper.C_BPartner_ID = @Shipper_BPartner_ID@"), so the spec brings
    // its own shipper instead of depending on whatever the seed happens to contain.
    let shipperId;
    await test.step('Create a shipper for that carrier', async () => {
      shipperId = (await patchDocument(SHIPPER_WINDOW_ID, 'NEW', [])).id;
      const shipper = await patchDocument(SHIPPER_WINDOW_ID, shipperId, [
        { op: 'replace', path: 'Name', value: `Transport order E2E shipper ${Date.now()}` },
        { op: 'replace', path: 'C_BPartner_ID', value: Number(carrierBPartnerId) },
      ]);
      expect(shipper.validStatus && shipper.validStatus.valid, 'shipper is valid').toBe(true);
    });

    // A transport order cannot be completed empty (MMShipperTransportation#prepareIt -> "No Document
    // Lines found"), so it gets its shipping package the way a procurement worker does: by booking a
    // completed purchase order onto it.
    let purchaseOrderId;
    await test.step('Create and complete a purchase order', async () => {
      purchaseOrderId = (await patchDocument(PURCHASE_ORDER_WINDOW_ID, 'NEW', [])).id;
      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, [
        { op: 'replace', path: 'C_BPartner_ID', value: Number(vendorBPartnerId) },
      ]);

      const lineResponse = await page.request.patch(
        `${REST}/window/${PURCHASE_ORDER_WINDOW_ID}/${purchaseOrderId}/${PURCHASE_ORDER_LINE_TAB_ID}/NEW`,
        { data: [] }
      );
      const lineRow = firstDocument(await lineResponse.json());
      const line = firstDocument(
        await (
          await page.request.patch(
            `${REST}/window/${PURCHASE_ORDER_WINDOW_ID}/${purchaseOrderId}/${PURCHASE_ORDER_LINE_TAB_ID}/${lineRow.rowId}`,
            {
              data: [
                { op: 'replace', path: 'M_Product_ID', value: Number(productId) },
                { op: 'replace', path: 'QtyEntered', value: 10 },
              ],
            }
          )
        ).json()
      );
      expect(line.validStatus && line.validStatus.valid, 'purchase order line is valid').toBe(true);

      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, [
        { op: 'replace', path: 'DocAction', value: 'CO' },
      ]);
      const completedPO = await getDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId);
      expect(completedPO.fieldsByName.DocStatus.value.key, 'purchase order DocStatus').toBe('CO');
    });

    let transportOrderId;
    await test.step('Create a transport order and book the purchase order onto it', async () => {
      transportOrderId = (await patchDocument(TRANSPORT_ORDER_WINDOW_ID, 'NEW', [])).id;

      const drafted = await patchDocument(TRANSPORT_ORDER_WINDOW_ID, transportOrderId, [
        { op: 'replace', path: 'Shipper_BPartner_ID', value: Number(carrierBPartnerId) },
        { op: 'replace', path: 'Shipper_Location_ID', value: Number(carrierLocationId) },
        { op: 'replace', path: 'M_Shipper_ID', value: Number(shipperId) },
      ]);
      expect(drafted.validStatus && drafted.validStatus.valid, 'drafted transport order is valid').toBe(true);

      // The process is a view action, so it needs a view of the purchase order window plus the
      // selected row id — invoking it from the single document leaves its query filter empty.
      const view = await postJson(`${REST}/documentView/${PURCHASE_ORDER_WINDOW_ID}`, {
        windowId: String(PURCHASE_ORDER_WINDOW_ID),
        viewType: 'grid',
        filters: [],
      });
      const pinstance = await postJson(`${REST}/process/${ADD_PO_TO_TRANSPORT_ORDER_PROCESS_ID}`, {
        processId: ADD_PO_TO_TRANSPORT_ORDER_PROCESS_ID,
        viewId: view.viewId,
        viewDocumentIds: [purchaseOrderId],
      });
      await page.request.patch(`${REST}/process/${ADD_PO_TO_TRANSPORT_ORDER_PROCESS_ID}/${pinstance.pinstanceId}`, {
        data: [{ op: 'replace', path: 'M_ShipperTransportation_ID', value: Number(transportOrderId) }],
      });
      const processResult = await (
        await page.request.get(`${REST}/process/${ADD_PO_TO_TRANSPORT_ORDER_PROCESS_ID}/${pinstance.pinstanceId}/start`)
      ).json();
      expect(processResult.error, `booking the PO onto the transport order: ${processResult.summary}`).toBeFalsy();
    });

    await test.step('Complete the transport order', async () => {
      await patchDocument(TRANSPORT_ORDER_WINDOW_ID, transportOrderId, [
        { op: 'replace', path: 'DocAction', value: 'CO' },
      ]);

      // The whole point of the test is the PROCESSED state, so make sure the setup really reached it
      // rather than silently leaving a draft that would make every assertion below meaningless.
      const completed = await getDocument(TRANSPORT_ORDER_WINDOW_ID, transportOrderId);
      const docStatus = completed.fieldsByName.DocStatus.value;
      expect(docStatus && docStatus.key, 'transport order DocStatus after Complete').toBe('CO');
      console.log(`[SETUP] Transport order ${transportOrderId} is completed`);
    });

    // --------------------------------------------------------------- the test

    await test.step('Open the completed transport order in the browser', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${TRANSPORT_ORDER_WINDOW_ID}/${transportOrderId}`, {
        timeout: 120000,
      });

      // If the REST session cookie did not carry over, the SPA bounces back to /login and the record
      // never renders — surface that here instead of as a downstream selector timeout.
      await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });

      await page
        .locator('.header-wrapper, .window-wrapper')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page
        .locator('.rotating, .panel-spaced-lg')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await page.locator('.form-field-BLDate').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    });

    const blDateInput = page.locator('.form-field-BLDate input').first();
    const etaInput = page.locator('.form-field-ETA input').first();
    const dateDocInput = page.locator('.form-field-DateDoc input').first();

    await test.step('Control: Document Date IS read-only on the processed document', async () => {
      await dateDocInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(dateDocInput, 'DateDoc is not always-updateable, so it must be disabled').toBeDisabled({
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });

    await test.step('B/L Date is editable', async () => {
      await blDateInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(blDateInput, 'BLDate must be editable on a completed transport order').toBeEnabled({
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });

    await test.step('ETA is editable', async () => {
      await etaInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(etaInput, 'ETA must be editable on a completed transport order').toBeEnabled({
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });

    // "Enabled" alone would still pass if the backend rejected the write, so type real values and
    // prove they survive a reload: that is the full Document-layer write path the migration unblocks.
    const blDate = { year: 2026, month: 8, day: 20 };
    const eta = { year: 2026, month: 8, day: 14 };

    await test.step('Enter a B/L Date and an ETA through the form', async () => {
      for (const [input, date] of [
        [blDateInput, blDate],
        [etaInput, eta],
      ]) {
        await input.click();
        await page.keyboard.press('Control+a');
        // Type it key by key (rather than input.fill) so the widget sees the same events a user
        // produces; formatForSession renders it in the session language's format.
        await page.keyboard.type(formatForSession(date), { delay: 40 });
        await page.keyboard.press('Enter');
        await page.waitForTimeout(1500);
        await page
          .locator('.indicator-pending, .rotating')
          .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
          .catch(() => {});
      }

      const screenshot = await page.screenshot({ fullPage: true });
      allure.attachment('Completed transport order with B/L Date and ETA entered', screenshot, 'image/png');
    });

    await test.step('The entered dates are persisted (survive a reload)', async () => {
      await page.reload({ timeout: 120000 });
      await page.locator('.form-field-BLDate').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await page
        .locator('.rotating, .panel-spaced-lg')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      expectInputToShowDate(await blDateInput.inputValue(), blDate);
      expectInputToShowDate(await etaInput.inputValue(), eta);

      // Still editable after the reload — i.e. the fields did not lock themselves once filled.
      await expect(blDateInput).toBeEnabled();
      await expect(etaInput).toBeEnabled();
    });
  });
});
