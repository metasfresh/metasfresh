import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PURCHASE_ORDER_WINDOW_ID, RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID } from '../utils/WindowIds';

/** AD_Tab_ID of "Bestellposition" (purchase order line) in the purchase order window. */
const PURCHASE_ORDER_LINE_TAB_ID = 'AD_Tab-293';

/**
 * AD_Process.Value of the receipt-disposition delivery-planning window's default quick action and its fallback — the two
 * captions {@code data-testid="quick-action-button"} can show. Both live on the same AD_Table_Process
 * row set that puts the receive actions on the receipt-disposition delivery-planning grid.
 */
const HUS_VOREINST_INTERNAL_NAME = 'WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveHUs_UsingDefaults';

/** AD_Process.Value of the multi-row receive — reachable from the action menu only. */
const MULTI_ROW_RECEIVE_INTERNAL_NAME = 'WEBUI_RV_ReceiptDisposition_DeliveryPlanning_Generate_M_InOuts';

/**
 * {@code M_HU_PI_Item_Product_ID} of the virtual "No Packing Item" row — the hard-coded id every
 * {@code M_HU_PI_Item_Product_ID} field defaults to, and what
 * {@code ReceiptScheduleDocumentLUTUConfigurationHandler#getM_HU_PI_Item_Product} falls back to. A receipt
 * schedule carrying it resolves to the VIRTUAL TU packing instruction with infinite CU capacity, so
 * {@code HUPackingInfoFormatter} formats nothing and the HU-defaults receive rejects with "no default
 * LU/TU configuration" — i.e. this id is exactly the "unpacked" state.
 */
const NO_PACKING_ITEM_ID = 101;

/**
 * The receipt-disposition delivery-planning window's quick-action default and its fallback, and the multi-row receive being
 * reachable only from the action menu.
 *
 * Two receipt-schedule rows, same purchase order, differing only in ONE thing: whether the product has a
 * packing instruction (an `M_HU_PI_Item_Product`) — the one condition that makes the HU-default receive
 * action reject ("no default LU/TU configuration"), mirroring the receipt-schedule window's own default
 * exactly.
 *
 * - The row for the packed product must show "HUs annehmen Voreinst." as the one-click default
 *   (`[data-testid="quick-action-button"]`).
 * - The interesting case: the row for the UNPACKED product must still show a one-click default — the
 *   default action genuinely hides itself (it must not appear even disabled in the quick-actions
 *   dropdown), so the platform's own quick-action-first sort promotes "CUs annehmen".
 * - The multi-row receive ("Wareneingangsdispo zu Wareneingang") must be absent from the quick-actions
 *   dropdown on a multi-row selection, and present in the action menu.
 *
 * Neither row is planned (no shipper carries `IsCreateDeliveryPlanning`) — planned-vs-unplanned is an
 * orthogonal axis and irrelevant to which quick action a row offers, which is governed by the packing
 * instruction alone.
 */
test.describe('Receipt-disposition delivery-planning — quick-action default and its fallback', () => {
  test('a row with a packing instruction defaults to "HUs annehmen Voreinst.", a row without one falls back to "CUs annehmen", and the multi-row receive stays menu-only', async ({
    page,
    request,
  }) => {
    allure.epic('E0360: Transport (Extralogistik)');
    allure.tag('F29050: Delivery Planning');
    allure.story('Receipt-disposition delivery-planning quick-action default with its fallback');
    allure.severity('critical');
    allure.description(`
## Receipt-disposition delivery-planning — quick-action default and its fallback

1. Creates one purchase order with two lines against the SAME vendor — one product carries a packing
   instruction (an \`M_HU_PI_Item_Product\`), the other does not — and completes it, producing two
   UNPLANNED receipt-schedule rows on window ${RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID}.
2. Selecting the packed-product row: the quick-action button reads "HUs annehmen Voreinst.".
3. Selecting the unpacked-product row: the quick-action button reads "CUs annehmen" (the fallback), and
   opening the quick-actions dropdown never shows "HUs annehmen Voreinst." at all — it hides itself
   rather than merely disabling.
4. Selecting BOTH rows: the multi-row receive ("Wareneingangsdispo zu Wareneingang") is absent from the
   quick-actions dropdown and present in the action menu (Alt+... / actions panel).
    `);

    test.setTimeout(180000);

    // === Create masterdata: a vendor, and two products — one WITH a packing instruction, one without ===
    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'de_DE' } },
        bpartners: {
          VENDOR: {
            name: `RL quick-action vendor ${Date.now()}`,
            isVendor: true,
            isCustomer: false,
            isSoPriceList: false, // Purchase price list — see receipt.spec.js / partial-receipt.spec.js
          },
        },
        products: {
          // Both need a price on the vendor's purchase price list: completing the order prices every
          // line, so a product without one fails the PATCH with ProductNotOnPriceListException.
          PACKED: {
            name: `RL packed product ${Date.now()}`,
            type: 'Item',
            prices: [{ price: 10.0, currencyCode: 'EUR' }],
          },
          UNPACKED: {
            name: `RL unpacked product ${Date.now()}`,
            type: 'Item',
            prices: [{ price: 10.0, currencyCode: 'EUR' }],
          },
        },
        // Gives PACKED product a packing instruction (an M_HU_PI_Item_Product) — the ONE thing that
        // makes "HUs annehmen Voreinst." resolve, once it is also LINKED to the order line below.
        // UNPACKED gets none, which is the natural, no-setup-needed state that makes its default
        // reject internally.
        packingInstructions: {
          RL_TU: { tu: 'RL_TU_PI', product: 'PACKED', qtyCUsPerTU: 10 },
        },
      },
    });

    const vendorId = masterdata.bpartners.VENDOR.id;
    const packedProductId = masterdata.products.PACKED.id;
    const unpackedProductId = masterdata.products.UNPACKED.id;
    // `productName`, not `name` — JsonCreateProductResponse carries {id, productCode, productName}.
    // Reading `.name` yielded undefined and the grid locator then silently searched for the literal
    // text "undefined", so the step failed 40s later on a row that never existed rather than here.
    const packedProductName = masterdata.products.PACKED.productName;
    const unpackedProductName = masterdata.products.UNPACKED.productName;
    expect(vendorId).toBeTruthy();
    expect(packedProductId).toBeTruthy();
    expect(unpackedProductId).toBeTruthy();
    expect(packedProductName, 'the packed product name the grid rows are matched on').toBeTruthy();
    expect(unpackedProductName, 'the unpacked product name the grid rows are matched on').toBeTruthy();

    // The `M_HU_PI_Item_Product_ID` of the packing instruction this run just created, taken from the
    // masterdata response — never hard-coded, because every run creates a fresh row. The response exposes
    // that id in exactly one place: `tuPIItemProductTestId`, a frontend test id of the form
    // `tuPIItemProduct-<M_HU_PI_Item_Product_ID>`
    // (JsonPackingInstructionsResponse <- MaterialReceiptActivityHandler#extractNewTUTargetTestId).
    const packedPackingInstructionsId = Number(
      String(masterdata.packingInstructions.RL_TU.tuPIItemProductTestId).replace(/^tuPIItemProduct-/, '')
    );
    expect(
      packedPackingInstructionsId,
      'the M_HU_PI_Item_Product id of the packed product\'s packing instruction, parsed out of the ' +
        'masterdata response\'s tuPIItemProductTestId'
    ).toBeGreaterThan(0);
    expect(
      packedPackingInstructionsId,
      'the created packing instruction is a real one, not the virtual "No Packing Item" row'
    ).not.toBe(NO_PACKING_ITEM_ID);

    const REST = FRONTEND_BASE_URL.replace(/:3000$/, ':8080') + '/rest/api';

    const firstDocument = (body) => (Array.isArray(body) ? body : body.documents || [body])[0];
    const patchDocument = async (windowId, documentId, tabId, rowId, changes) => {
      const path = tabId
        ? `${REST}/window/${windowId}/${documentId}/${tabId}/${rowId}`
        : `${REST}/window/${windowId}/${documentId}`;
      const response = await request.patch(path, { data: changes });
      if (!response.ok()) {
        throw new Error(`PATCH ${path} failed: HTTP ${response.status()} ${await response.text()}`);
      }
      return firstDocument(await response.json());
    };

    // === Authenticate the SETUP session via REST — the same 'metasfresh'/'metasfresh' admin
    // credentials delivery-instruction-qty-sync.spec.js uses for its window-PATCH setup.
    //
    // On the `request` FIXTURE, not on `page.request`: this test drives the browser as the per-test
    // de_DE user (its German quick-action captions are the assertion), and `page.request` shares the
    // browser context's cookie jar, so authenticating there logged the browser in as `metasfresh` and
    // the UI login then died on the form's "User already logged in". The `request` fixture is an
    // isolated APIRequestContext with its own jar, which makes the setup session genuinely
    // independent of the browser session — what the two-user split intended all along. Every REST
    // setup call below therefore goes through `request`, every UI interaction through `page`.
    await test.step('Authenticate the REST setup session', async () => {
      const sessionBody = await (await request.get(`${REST}/userSession`)).json().catch(() => ({}));
      if (!sessionBody.loggedIn) {
        const authResponse = await request.post(`${REST}/login/authenticate`, {
          data: { username: 'metasfresh', password: 'metasfresh' },
        });
        const authBody = await authResponse.json();
        if (authBody.loginComplete === false && authBody.roles && authBody.roles.length > 0) {
          await request.post(`${REST}/login/loginComplete`, { data: authBody.roles[0] });
        }
      }
    });

    // === Create ONE purchase order with two lines, one per product, and complete it ===
    let purchaseOrderId;
    await test.step('Create and complete a purchase order with a packed and an unpacked line', async () => {
      purchaseOrderId = (await patchDocument(PURCHASE_ORDER_WINDOW_ID, 'NEW', null, null, [])).id;
      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, null, null, [
        { op: 'replace', path: 'C_BPartner_ID', value: Number(vendorId) },
      ]);

      // Each line is created AND filled by ONE checked PATCH against rowId 'NEW': WindowRestController
      // -> DocumentCollection#forDocumentWritable creates the included row and applies the events to it
      // within the same execution, so no row id ever has to travel back to the test.
      //
      // Creating the row first and reading its `rowId` out of that response is what used to fail here,
      // and only for the SECOND line: saving line 1 stales the root document, the next request
      // refreshes it and re-marks its included tabs stale, and then
      // DocumentChangesCollector#streamOrderedDocumentChanges DROPS the change event of every included
      // row of a staled tab (isStaleDocumentChanges) — by design, because the frontend re-reads a
      // staled tab instead of trusting the response. The creation therefore answers HTTP 200 carrying
      // the ROOT document only, `rowId` reads as undefined, and the follow-up PATCH goes to
      // `.../AD_Tab-293/undefined` -> HTTP 500. A PATCH response is simply not a place to read a new
      // included row's id from.
      //
      // The packing instruction is set EXPLICITLY on the packed line, and deliberately not on the other.
      // Nothing derives it here: picking a product's default packing instruction is a WebUI batch-entry /
      // quick-input behaviour (`PackingItemProductFieldHelper` off `IOrderLineQuickInput`), and these lines
      // are created by PATCHing the plain order-line tab, which runs none of it. Without this op BOTH lines
      // keep `M_HU_PI_Item_Product_ID = 101` ("No Packing Item"), the receipt schedule copies that id
      // verbatim at creation (`HUReceiptScheduleProducer#updateFromOrderline`, once and only for a
      // just-created schedule), and the "packed" row then offers exactly the same quick action as the
      // unpacked one — the distinction this whole test is about, silently gone.
      for (const line of [
        { productId: packedProductId, packingInstructionsId: packedPackingInstructionsId },
        { productId: unpackedProductId, packingInstructionsId: null },
      ]) {
        await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, PURCHASE_ORDER_LINE_TAB_ID, 'NEW', [
          { op: 'replace', path: 'M_Product_ID', value: Number(line.productId) },
          { op: 'replace', path: 'QtyEntered', value: 5 },
          ...(line.packingInstructionsId
            ? [{ op: 'replace', path: 'M_HU_PI_Item_Product_ID', value: line.packingInstructionsId }]
            : []),
        ]);
      }

      // Both lines really landed — asserted on the TAB'S OWN ROWS, not on a PATCH response, so a row
      // creation the backend silently refuses fails right here instead of surfacing later as a
      // one-row grid or a completion error.
      const lineRows = (
        await (
          await request.get(
            `${REST}/window/${PURCHASE_ORDER_WINDOW_ID}/${purchaseOrderId}/${PURCHASE_ORDER_LINE_TAB_ID}`
          )
        ).json()
      ).result;
      expect(
        lineRows.map((row) => String(row.fieldsByName.M_Product_ID.value.key)).sort(),
        'the products of the created purchase order lines'
      ).toEqual([String(packedProductId), String(unpackedProductId)].sort());
      for (const lineRow of lineRows) {
        expect(lineRow.saveStatus && lineRow.saveStatus.saved, `order line ${lineRow.rowId} is saved`).toBe(true);
        expect(lineRow.validStatus && lineRow.validStatus.valid, `order line ${lineRow.rowId} is valid`).toBe(true);
      }

      // The packing instruction really landed on the packed line, and only there. Asserted rather than
      // assumed: the packed-vs-unpacked split is the single variable the quick-action expectations below
      // rest on, and a PATCH op the backend quietly drops (or applies to the wrong row) would leave both
      // lines identical while every later step still reported green.
      const packingInstructionIdByProductId = new Map(
        lineRows.map((row) => [
          String(row.fieldsByName.M_Product_ID.value.key),
          Number(row.fieldsByName.M_HU_PI_Item_Product_ID.value.key),
        ])
      );
      expect(
        packingInstructionIdByProductId.get(String(packedProductId)),
        'the packed order line carries the packing instruction created for its product'
      ).toBe(packedPackingInstructionsId);
      expect(
        packingInstructionIdByProductId.get(String(unpackedProductId)),
        'the unpacked order line carries no packing instruction — the virtual "No Packing Item" row, which ' +
          'is what makes its HU-defaults receive reject'
      ).toBe(NO_PACKING_ITEM_ID);

      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, null, null, [
        { op: 'replace', path: 'DocAction', value: 'CO' },
      ]);
      const completed = firstDocument(
        await (await request.get(`${REST}/window/${PURCHASE_ORDER_WINDOW_ID}/${purchaseOrderId}`)).json()
      );
      expect(completed.fieldsByName.DocStatus.value.key, 'purchase order DocStatus').toBe('CO');
    });

    // === Wait for the receipt-schedule rows the completion creates ASYNCHRONOUSLY ===
    // Completing the order only ENQUEUES the receipt-schedule creation (de.metas.async), so the rows
    // are not there when the PATCH returns — measured against this stack: the view is still empty on
    // the first poll and holds both rows a few seconds later. The window's grid is queried once when
    // the page loads and never re-queried, so navigating too early leaves the row missing for the
    // whole test however long a locator waits afterwards. Hence a wait on the CONDITION here, never a
    // sleep and never a locator timeout standing in for one.
    //
    // Polled through the window's own view because the tab carries no filter field at all
    // (AD_Field.IsFilterField is unset on every field of tab 549491), so the rows cannot be narrowed
    // server-side: a fresh view per attempt (a view is a snapshot), read NEWEST FIRST via
    // ?orderBy=-RV_ReceiptDisposition_DeliveryPlanning_ID and paged to the end, matched on THIS
    // order's C_Order_ID. Same shape as delivery-instruction-qty-sync.spec.js's wait for its
    // delivery planning.
    const ownReceiptDispositionRowCount = async () => {
      const view = await (
        await request.post(`${REST}/documentView/${RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID}`, {
          data: { windowId: String(RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID), viewType: 'grid' },
        })
      ).json();

      const PAGE_LENGTH = 500;
      let ownRows = 0;
      for (let firstRow = 0; ; firstRow += PAGE_LENGTH) {
        const rowsPage = await (
          await request.get(
            `${REST}/documentView/${RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID}/${view.viewId}` +
              `?firstRow=${firstRow}&pageLength=${PAGE_LENGTH}&orderBy=-RV_ReceiptDisposition_DeliveryPlanning_ID`
          )
        ).json();
        const rows = rowsPage.result || [];
        ownRows += rows.filter(
          (row) => String(row.fieldsByName?.C_Order_ID?.value?.key) === String(purchaseOrderId)
        ).length;
        if (rows.length < PAGE_LENGTH) {
          return ownRows;
        }
      }
    };

    await test.step('wait for the two receipt-schedule rows the completion creates asynchronously', async () => {
      await expect
        .poll(ownReceiptDispositionRowCount, {
          message:
            `both receipt-schedule rows of purchase order ${purchaseOrderId} show up on window ` +
            `${RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID}. Fewer than two means the async ` +
            `receipt-schedule creation the completion enqueued never finished — on a freshly started ` +
            `stack that is SysConfig de.metas.async.Async_InitDelayMillis, which idles the processors ` +
            `for 3 minutes unless lowered (launch-stack.sh sets it to 2s)`,
          timeout: 90000,
          intervals: [1000, 2000],
        })
        .toBe(2);
    });

    // === Login the browser session and open the receipt-disposition delivery-planning window ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    // No `waitForLoadState('networkidle').catch(() => {})` here: the SPA holds a websocket open, so
    // networkidle is not a state this page reliably reaches, and swallowing its timeout hid whatever
    // else went wrong at navigation. The grid row waited for in the first step below is the real
    // condition — the data it needs is already guaranteed by the poll above.
    await page.goto(`${FRONTEND_BASE_URL}/window/${RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID}`);

    const rowForProduct = (productName) => page.locator(`table tbody tr:has-text("${productName}")`).first();

    await test.step('the packed-product row defaults to "HUs annehmen Voreinst."', async () => {
      const row = rowForProduct(packedProductName);
      await row.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      await row.click();

      const quickActionButton = page.locator('[data-testid="quick-action-button"]');
      await expect(quickActionButton).toBeVisible();
      await expect(quickActionButton).toHaveText('HUs annehmen Voreinst.');
    });

    await test.step('the interesting case: the unpacked-product row falls back to "CUs annehmen", and the HU default genuinely hides itself', async () => {
      const row = rowForProduct(unpackedProductName);
      await row.click();

      const quickActionButton = page.locator('[data-testid="quick-action-button"]');
      await expect(quickActionButton).toBeVisible();
      await expect(quickActionButton).toHaveText('CUs annehmen');

      // Open the dropdown of the OTHER quick actions and assert the HU default is not merely disabled
      // in there — it must be ABSENT ("the HU default genuinely hides itself").
      await page.locator('[data-testid="quick-action-dropdown-toggle"]').click();
      await expect(page.locator(`[data-testid="quick-action-${HUS_VOREINST_INTERNAL_NAME}"]`)).toHaveCount(0);
      await page.locator('[data-testid="quick-action-dropdown-toggle"]').click(); // close
    });

    await test.step('the multi-row receive is reachable from the action menu only', async () => {
      await rowForProduct(packedProductName).click();
      await rowForProduct(unpackedProductName).click({ modifiers: ['Control'] });

      // Both assertions run UNCONDITIONALLY. An assertion reached only when its own precondition happens to
      // hold cannot fail, and that is not a hypothetical here: the action-menu half used to be guarded on a
      // visibility check over three speculative test ids, none of which any component in `frontend/src`
      // renders, so the block never executed and the step reported green having asserted nothing. Each
      // opener is waited for instead, so an affordance that disappears fails the step loudly.

      // Quick-actions dropdown: the multi-row receive must never appear here (WEBUI_ViewQuickAction='N').
      const dropdownToggle = page.locator('[data-testid="quick-action-dropdown-toggle"]');
      await dropdownToggle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await dropdownToggle.click();
      await expect(page.locator(`[data-testid="quick-action-${MULTI_ROW_RECEIVE_INTERNAL_NAME}"]`)).toHaveCount(0);
      await dropdownToggle.click(); // close

      // Action menu: the header's "..." button (`.meta-icon-more`, Header.js) opens the subheader panel
      // (`.subheader-container`), whose entries carry `data-testid="action-<internalName>"`
      // (Actions.js). The multi-row receive must be present there (WEBUI_ViewAction='Y').
      const actionsToggle = page.locator('.meta-icon-more').first();
      await actionsToggle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await actionsToggle.click();
      await page
        .locator('.subheader-container')
        .first()
        .waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect(page.locator(`[data-testid="action-${MULTI_ROW_RECEIVE_INTERNAL_NAME}"]`)).toBeVisible();
    });
  });
});
