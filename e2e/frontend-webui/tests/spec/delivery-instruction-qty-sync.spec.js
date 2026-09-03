import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import {
  DELIVERY_INSTRUCTION_WINDOW_ID,
  DELIVERY_PLANNING_WINDOW_ID,
  SALES_ORDER_WINDOW_ID,
  SHIPPER_WINDOW_ID,
} from '../utils/WindowIds';

/** AD_Tab_ID of the "Auftragsposition" tab in the sales order window. */
const SALES_ORDER_LINE_TAB_ID = 'AD_Tab-187';

/** AD_Tab_ID of the "Versandpaket" (M_ShippingPackage) tab of the Lieferanweisungen window. */
const SHIPPING_PACKAGE_TAB_ID = 'AD_Tab-546736';

/**
 * AD_Process_ID 585176 = M_Delivery_Planning_GenerateDeliveryInstruction
 * ("Lieferanweisung je Lieferplanung erzeugen") — the grid action that turns a delivery planning into
 * a delivery instruction (M_ShipperTransportation) carrying one M_ShippingPackage per planning, linked
 * by an M_Delivery_Planning_Alloc row. It is a view action, so it needs a view + selected row ids.
 */
const GENERATE_DELIVERY_INSTRUCTION_PROCESS_ID = 'ADP_585176';

/**
 * TC11 — a delivery instruction line must show the planning's CURRENT quantity, with no manual reload.
 *
 * Task Q14 made all four quantity figures on M_ShippingPackage derived (ColumnSQL through
 * M_Delivery_Planning_Alloc) instead of physical copies written at generation time. That makes the value
 * CURRENT in the database, which is only half of TC11: the figures live on AD_Tab 546736 — tabLevel 1,
 * an INCLUDED document of the Lieferanweisungen window (541657), not an IView — so making them appear
 * without an F5 additionally needs a cache-invalidation request the WebUI's DocumentCollection can
 * route, i.e. one naming the document's ROOT record (M_ShipperTransportation).
 *
 * Scope — this spec asserts exactly that second half, and it is the ONLY test of it:
 *  - that the four columns are derived (the value is right once re-read) is covered by the cucumber
 *    scenario @Id:S31789_TC_Q14_ShippingPackageMirrorsPlanningQuantities, which asserts through the DB
 *    and can never see a browser's cached document;
 *  - that the value ARRIVES in an open browser without a reload can only be exercised here.
 * The final control step re-reads the same cell after an explicit reload, so a failure of the main
 * assertion is unambiguously "the tab did not refresh" rather than "the column is not derived".
 */
test.describe('Delivery instruction line — quantities follow the planning without a manual reload', () => {
  test('editing the planning updates the Versandpaket row of an open delivery instruction', async ({ page }) => {
    allure.epic('E0210: Logistics');
    allure.tag('F02160: Delivery Planning');
    allure.tag('F02160');
    allure.story('A delivery instruction line shows the planning current quantity with no manual reload');
    allure.severity('critical');
    allure.description(`
## Delivery instruction line — quantity sync without a reload (TC11)

### Test scenario

1. Creates its own customer, product and carrier/shipper (\`IsCreateDeliveryPlanning='Y'\`, the gate
   \`DeliveryPlanningService#isAutoCreateEnabled\` reads), then creates and completes a sales order line
   carrying that shipper — completion generates the \`M_Delivery_Planning\` asynchronously.
2. Generates a delivery instruction from that planning (process ${GENERATE_DELIVERY_INSTRUCTION_PROCESS_ID}),
   which creates the \`M_ShippingPackage\` plus the \`M_Delivery_Planning_Alloc\` the derivation reads through.
3. Opens the delivery instruction in window ${DELIVERY_INSTRUCTION_WINDOW_ID} in the browser and reads
   **Geplante Verlademenge** (\`PlannedLoadedQuantity\`) off the Versandpaket row — expects the order quantity.
4. Changes the PLANNING's \`PlannedLoadedQuantity\` through a separate REST call (what a second browser
   tab or a colleague does) and asserts the already-open Versandpaket row shows the new figure
   **without any page reload**.
5. Control: reloads the page and asserts the same new figure — proving the derivation itself works, so
   step 4 failing means the refresh path, not the column, is broken.

### Assertion mechanism

Included-tab rows render as \`td[data-cy="cell-<ColumnName>"]\`; the cell text is compared numerically
(the grid renders quantities in the session's number format).
    `);

    test.setTimeout(300000);

    const REST = WEBAPI_BASE_URL;

    const ORDERED_QTY = 10;
    const EDITED_PLANNED_LOAD_QTY = 6;

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

    /** Numbers only — the grid renders "10", "10.00" or "10,00" depending on the session's format. */
    const cellNumber = (text) => Number(String(text).replace(/[^\d,.-]/g, '').replace(/\./g, '').replace(',', '.'));

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

    let customerBPartnerId;
    let carrierBPartnerId;
    let productId;
    await test.step('Create the customer, the carrier partner and a product', async () => {
      const masterdata = await Backend.createMasterdata({
        request: {
          bpartners: {
            CUSTOMER: {
              name: 'Delivery instruction qty sync customer',
              isVendor: false,
              isCustomer: true,
              isSoPriceList: true,
            },
            CARRIER: {
              name: 'Delivery instruction qty sync carrier',
              isVendor: true,
              isCustomer: false,
              isSoPriceList: false,
            },
          },
          products: {
            PROD1: {
              name: 'Delivery instruction qty sync product',
              type: 'Item',
              prices: [{ price: 12.5, currencyCode: 'EUR' }],
            },
          },
        },
      });

      customerBPartnerId = masterdata.bpartners.CUSTOMER.id;
      carrierBPartnerId = masterdata.bpartners.CARRIER.id;
      productId = masterdata.products.PROD1.id;
      expect(customerBPartnerId).toBeTruthy();
      expect(carrierBPartnerId).toBeTruthy();
      expect(productId).toBeTruthy();
    });

    // The auto-create gate is the SHIPPER's own IsCreateDeliveryPlanning flag
    // (DeliveryPlanningService#isAutoCreateEnabled), so the spec brings its own shipper with the flag
    // set rather than depending on whatever the seed happens to carry.
    let shipperId;
    await test.step('Create a shipper that creates delivery plannings', async () => {
      shipperId = (await patchDocument(SHIPPER_WINDOW_ID, 'NEW', [])).id;
      const shipper = await patchDocument(SHIPPER_WINDOW_ID, shipperId, [
        { op: 'replace', path: 'Name', value: `Delivery instruction qty sync shipper ${Date.now()}` },
        { op: 'replace', path: 'C_BPartner_ID', value: Number(carrierBPartnerId) },
        { op: 'replace', path: 'IsCreateDeliveryPlanning', value: true },
      ]);
      expect(shipper.validStatus && shipper.validStatus.valid, 'shipper is valid').toBe(true);
    });

    let salesOrderId;
    await test.step('Create and complete a sales order line carrying that shipper', async () => {
      salesOrderId = (await patchDocument(SALES_ORDER_WINDOW_ID, 'NEW', [])).id;
      await patchDocument(SALES_ORDER_WINDOW_ID, salesOrderId, [
        { op: 'replace', path: 'C_BPartner_ID', value: Number(customerBPartnerId) },
      ]);

      const lineResponse = await page.request.patch(
        `${REST}/window/${SALES_ORDER_WINDOW_ID}/${salesOrderId}/${SALES_ORDER_LINE_TAB_ID}/NEW`,
        { data: [] }
      );
      const lineRow = firstDocument(await lineResponse.json());
      const line = firstDocument(
        await (
          await page.request.patch(
            `${REST}/window/${SALES_ORDER_WINDOW_ID}/${salesOrderId}/${SALES_ORDER_LINE_TAB_ID}/${lineRow.rowId}`,
            {
              data: [
                { op: 'replace', path: 'M_Product_ID', value: Number(productId) },
                { op: 'replace', path: 'QtyEntered', value: ORDERED_QTY },
                { op: 'replace', path: 'M_Shipper_ID', value: Number(shipperId) },
              ],
            }
          )
        ).json()
      );
      expect(line.validStatus && line.validStatus.valid, 'sales order line is valid').toBe(true);

      await patchDocument(SALES_ORDER_WINDOW_ID, salesOrderId, [{ op: 'replace', path: 'DocAction', value: 'CO' }]);
      const completed = await getDocument(SALES_ORDER_WINDOW_ID, salesOrderId);
      expect(completed.fieldsByName.DocStatus.value.key, 'sales order DocStatus').toBe('CO');
    });

    // The planning is created by the async workpackage the order completion enqueues, so it is polled
    // for rather than assumed present. A missing planning here means the auto-create gate did not
    // fire — surfaced now, not later as an empty delivery-instruction view.
    let deliveryPlanningId;
    let deliveryPlanningViewId;
    await test.step('Wait for the delivery planning the completion generated', async () => {
      // The window carries no filter fields (AD_Field.IsFilterField is 'N' on every field of tab
      // 546674), so the view is created unfiltered and the row is identified by the product this spec
      // created for itself — unique on the stack, so the match is exact rather than "the newest row".
      await expect
        .poll(
          async () => {
            const view = await postJson(`${REST}/documentView/${DELIVERY_PLANNING_WINDOW_ID}`, {
              windowId: String(DELIVERY_PLANNING_WINDOW_ID),
              viewType: 'grid',
            });
            deliveryPlanningViewId = view.viewId;
            const rows = await (
              await page.request.get(
                `${REST}/documentView/${DELIVERY_PLANNING_WINDOW_ID}/${view.viewId}?firstRow=0&pageLength=500`
              )
            ).json();
            const ownRow = (rows.result || []).find(
              (row) => String(row.fieldsByName?.M_Product_ID?.value?.key) === String(productId)
            );
            deliveryPlanningId = ownRow && ownRow.id;
            return deliveryPlanningId || null;
          },
          {
            message: 'a delivery planning is generated for the completed order line',
            timeout: 60000,
            intervals: [2000],
          }
        )
        .not.toBeNull();
      console.log(`[SETUP] Delivery planning ${deliveryPlanningId} in view ${deliveryPlanningViewId}`);
    });

    let deliveryInstructionId;
    await test.step('Generate the delivery instruction from that planning', async () => {
      const pinstance = await postJson(`${REST}/process/${GENERATE_DELIVERY_INSTRUCTION_PROCESS_ID}`, {
        processId: GENERATE_DELIVERY_INSTRUCTION_PROCESS_ID,
        viewId: deliveryPlanningViewId,
        viewDocumentIds: [deliveryPlanningId],
      });
      const processResult = await (
        await page.request.get(
          `${REST}/process/${GENERATE_DELIVERY_INSTRUCTION_PROCESS_ID}/${pinstance.pinstanceId}/start`
        )
      ).json();
      expect(processResult.error, `generating the delivery instruction: ${processResult.summary}`).toBeFalsy();

      const planning = await getDocument(DELIVERY_PLANNING_WINDOW_ID, deliveryPlanningId);
      const instructionField = planning.fieldsByName.M_ShipperTransportation_ID;
      deliveryInstructionId = instructionField && instructionField.value && instructionField.value.key;
      expect(deliveryInstructionId, 'the planning now points at a delivery instruction').toBeTruthy();
      console.log(`[SETUP] Delivery instruction ${deliveryInstructionId}`);
    });

    // --------------------------------------------------------------- the test

    const plannedLoadCell = page
      .locator(`td[data-cy="cell-PlannedLoadedQuantity"]`)
      .first();

    await test.step('Open the delivery instruction and read the Versandpaket row', async () => {
      await page.goto(`${FRONTEND_BASE_URL}/window/${DELIVERY_INSTRUCTION_WINDOW_ID}/${deliveryInstructionId}`, {
        timeout: 120000,
      });

      // If the REST session cookie did not carry over, the SPA bounces back to /login and the record
      // never renders — surface that here instead of as a downstream selector timeout.
      await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: SLOW_ACTION_TIMEOUT });
      await page
        .locator('.header-wrapper, .window-wrapper')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      const shippingPackageTab = page.locator(`[data-testid="tab-${SHIPPING_PACKAGE_TAB_ID}"]`);
      await shippingPackageTab.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await shippingPackageTab.click();
      await page
        .locator('.rotating, .indicator-pending')
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      await plannedLoadCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      expect(
        cellNumber(await plannedLoadCell.textContent()),
        'the line mirrors the planning planned load at generation time'
      ).toBe(ORDERED_QTY);

      allure.attachment(
        'Versandpaket row before the planning is edited',
        await page.screenshot({ fullPage: true }),
        'image/png'
      );
    });

    await test.step('Change the planning planned load from another session', async () => {
      // A plain REST PATCH on the planning window is exactly what a second browser tab does: it never
      // touches the page opened above, so anything the page then shows had to be pushed to it.
      const planning = await patchDocument(DELIVERY_PLANNING_WINDOW_ID, deliveryPlanningId, [
        { op: 'replace', path: 'PlannedLoadedQuantity', value: EDITED_PLANNED_LOAD_QTY },
      ]);
      expect(planning.validStatus && planning.validStatus.valid, 'edited planning is valid').toBe(true);
    });

    // THE assertion of this spec. No page.reload() above it, deliberately.
    await test.step('The open Versandpaket row shows the new figure with NO manual reload', async () => {
      await expect
        .poll(async () => cellNumber(await plannedLoadCell.textContent()), {
          message:
            'the already-open delivery instruction must pick the planning new planned load up on its own ' +
            '(no F5) — a stale value here means the invalidation never reached AD_Tab 546736',
          timeout: 30000,
          intervals: [1000],
        })
        .toBe(EDITED_PLANNED_LOAD_QTY);

      allure.attachment(
        'Versandpaket row after the planning was edited elsewhere',
        await page.screenshot({ fullPage: true }),
        'image/png'
      );
    });

    // Control: separates "the tab did not refresh" from "the column is not derived at all". If THIS
    // one fails too, the defect is in the derivation, not in the invalidation.
    await test.step('Control: an explicit reload shows the same new figure', async () => {
      await page.reload({ timeout: 120000 });
      await page
        .locator('.header-wrapper, .window-wrapper')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      const shippingPackageTab = page.locator(`[data-testid="tab-${SHIPPING_PACKAGE_TAB_ID}"]`);
      await shippingPackageTab.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await shippingPackageTab.click();
      await plannedLoadCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      expect(
        cellNumber(await plannedLoadCell.textContent()),
        'the derived column itself is correct — so a failure above is the refresh path'
      ).toBe(EDITED_PLANNED_LOAD_QTY);
    });
  });
});
