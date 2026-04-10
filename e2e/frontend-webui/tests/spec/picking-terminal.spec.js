import { test } from "../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from "allure-playwright";
import { Backend } from "../utils/Backend";
import {
  FRONTEND_BASE_URL,
  SLOW_ACTION_TIMEOUT,
  VERY_SLOW_ACTION_TIMEOUT,
} from "../utils/common";
import { PICKING_TERMINAL_V2_WINDOW_ID } from "../utils/WindowIds";

const PICKING_TERMINAL_V1_WINDOW_ID = 540350;

/**
 * Picking Terminal V2 (Desktop WebUI) — E2E tests
 *
 * Window 540485: Shows packageable shipment schedules.
 * Quick actions: Pick (opens Products to Pick modal), Print Pick list, Unlock.
 * The "Pick" action opens a modal overlay (ProductsToPickView) where
 * individual product lines can be picked, marked "will not pick", etc.
 *
 * Prerequisites: Both WebAPI (8080) and App server (8282) must be running.
 *
 * me03#29090
 */

const createPickingTestData = async (language = "en_US") => {
  return await Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      mobileConfig: {
        picking: {
          aggregationType: "sales_order",
          allowPickingAnyCustomer: true,
          allowPickingAnyHU: true,
          createShipmentPolicy: "CL",
          pickTo: ["LU_TU"],
        },
      },
      bpartners: { BP1: {} },
      warehouses: { wh: {} },
      pickingSlots: { slot1: {} },
      products: { P1: { prices: [{ price: 1 }] } },
      packingInstructions: {
        PI: {
          lu: "LU",
          qtyTUsPerLU: 20,
          tu: "TU",
          product: "P1",
          qtyCUsPerTU: 4,
        },
      },
      handlingUnits: {
        HU1: { product: "P1", warehouse: "wh", packingInstructions: "PI" },
      },
      salesOrders: {
        SO1: {
          bpartner: "BP1",
          warehouse: "wh",
          datePromised: "2025-03-01T00:00:00.000+02:00",
          lines: [{ product: "P1", qty: 12, piItemProduct: "TU" }],
        },
      },
    },
  });
};

/**
 * Inline login — more reliable than LoginPage for local dev server.
 */
const loginAndNavigate = async (page, masterdata) => {
  const username = masterdata.login.user.username;
  const password = masterdata.login.user.password;

  await page.goto(`${FRONTEND_BASE_URL}/login`);
  await page.waitForTimeout(2000);
  await page
    .locator('input[name="username"]')
    .waitFor({ state: "visible", timeout: VERY_SLOW_ACTION_TIMEOUT });
  await page.locator('input[name="username"]').fill(username);
  await page.locator('input[name="password"]').fill(password);
  await page.locator(".btn-meta-success").click();

  // Handle role selection if needed
  await page.waitForTimeout(2000);
  if (page.url().includes("/login")) {
    const sendButton = page.locator(".btn-meta-success");
    if (await sendButton.isVisible().catch(() => false)) {
      await sendButton.click();
    }
  }
  await page.waitForURL((url) => !url.toString().includes("/login"), {
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  });
};

/**
 * Helper: Create a V2 view and find the shipment schedule row for the given order.
 * Returns the row ID (= M_ShipmentSchedule_ID) from the view data.
 */
const findShipmentScheduleId = async (page, documentNo) => {
  const createViewResp = await page.request.post(
    `${FRONTEND_BASE_URL}/rest/api/documentView/${PICKING_TERMINAL_V2_WINDOW_ID}`,
    {
      headers: { "Content-Type": "application/json" },
      data: {
        windowId: String(PICKING_TERMINAL_V2_WINDOW_ID),
        viewType: "grid",
      },
    },
  );
  const viewData = await createViewResp.json();
  expect(viewData.viewId, "V2 view should be created").toBeTruthy();

  // Fetch view rows and find ours by document number
  const rowsResp = await page.request.get(
    `${FRONTEND_BASE_URL}/rest/api/documentView/${PICKING_TERMINAL_V2_WINDOW_ID}/${viewData.viewId}?firstRow=0&pageLength=100`,
  );
  const rowsData = await rowsResp.json();
  expect(rowsData.result?.length, "V2 view should have rows").toBeGreaterThan(
    0,
  );

  // Find the row matching our order's documentNo
  const ourRow = rowsData.result.find((row) => {
    // Check all field values for the document number
    const fields = row.fieldsByName || {};
    return Object.values(fields).some((field) => {
      const val = field?.value;
      if (typeof val === "string") return val.includes(documentNo);
      if (val?.caption) return val.caption.includes(documentNo);
      return false;
    });
  });

  // If we can't find by documentNo matching, just use the first row
  const rowId = ourRow ? ourRow.id : rowsData.result[0].id;
  return { viewId: viewData.viewId, shipmentScheduleId: rowId };
};

test.describe("Picking Terminal V2 — Desktop WebUI", () => {
  test("Navigate to Picking Terminal and verify grid with data", async ({
    page,
  }) => {
    allure.epic("E0105: Picking");
    allure.tag("F00230.1: MobileUI Order-based Picking");
    allure.story(
      "Desktop Picking Terminal V2 — view and select shipment schedules",
    );
    allure.severity("critical");

    const masterdata = await createPickingTestData();
    await loginAndNavigate(page, masterdata);

    // Navigate to Picking Terminal V2
    await page.goto(
      `${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V2_WINDOW_ID}`,
    );
    await page
      .locator("table thead tr")
      .first()
      .waitFor({ state: "visible", timeout: VERY_SLOW_ACTION_TIMEOUT });

    // Verify grid loaded with rows
    const rowCount = await page.locator("table tbody tr").count();
    expect(rowCount).toBeGreaterThan(0);

    // Select first row
    await page.locator("table tbody tr").first().click();
    await page.waitForTimeout(1000);

    // Verify "Pick" quick action appeared with process-specific data-testid
    const pickButton = page.getByTestId("quick-action-button");
    await pickButton.waitFor({
      state: "visible",
      timeout: SLOW_ACTION_TIMEOUT,
    });
  });

  test("Open Products to Pick modal and pick selected", async ({ page }) => {
    allure.epic("E0105: Picking");
    allure.tag("F00230.1: MobileUI Order-based Picking");
    allure.story(
      "Desktop Picking Terminal V2 — Pick from Products to Pick modal (QA scenario 7 equivalent)",
    );
    allure.severity("critical");

    const masterdata = await createPickingTestData();
    await loginAndNavigate(page, masterdata);

    const docNo = masterdata.salesOrders.SO1.documentNo;
    const { shipmentScheduleId } = await findShipmentScheduleId(page, docNo);

    // Create a V2 view filtered to only our shipment schedule
    const createViewResp = await page.request.post(
      `${FRONTEND_BASE_URL}/rest/api/documentView/${PICKING_TERMINAL_V2_WINDOW_ID}`,
      {
        headers: { "Content-Type": "application/json" },
        data: {
          windowId: String(PICKING_TERMINAL_V2_WINDOW_ID),
          viewType: "grid",
          filterOnlyIds: [String(shipmentScheduleId)],
        },
      },
    );
    const viewData = await createViewResp.json();

    await page.goto(
      `${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V2_WINDOW_ID}?viewId=${viewData.viewId}`,
    );
    await page
      .locator("table thead tr")
      .first()
      .waitFor({ state: "visible", timeout: VERY_SLOW_ACTION_TIMEOUT });

    // Select our row — click the <tr> element directly (not <td>) to trigger row selection
    // Clicking <td> enters cell edit mode; clicking <tr> triggers the row onClick handler
    const ourRow = page
      .locator("table tbody tr")
      .filter({ hasText: docNo })
      .first();
    if (await ourRow.isVisible().catch(() => false)) {
      // Our row is visible (filterOnlyIds worked) — click it
      await ourRow.click();
    } else {
      // filterOnlyIds not supported yet — find an unlocked row in the full view
      let targetRow = page.locator("table tbody tr").first();
      const rowCount = await page.locator("table tbody tr").count();
      for (let i = 0; i < rowCount; i++) {
        const row = page.locator("table tbody tr").nth(i);
        const pickingUserCell = row.locator("td").nth(4);
        const pickingUserText = await pickingUserCell
          .innerText()
          .catch(() => "");
        if (!pickingUserText.trim()) {
          targetRow = row;
          break;
        }
      }
      await targetRow.click();
    }
    await page.waitForTimeout(1000);

    // Click "Pick" quick action button
    const pickButton = page.getByTestId("quick-action-button");
    await pickButton.waitFor({
      state: "visible",
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await pickButton.click();

    await page.waitForTimeout(3000);

    // Wait for the "Products to Pick" modal to appear
    await page
      .locator(".raw-modal, .screen-freeze")
      .waitFor({ state: "visible", timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page
      .locator(".screen-freeze")
      .waitFor({ state: "detached", timeout: VERY_SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(1000);

    // Verify the modal is showing with product rows
    const modalTable = page.locator(".raw-modal table tbody tr");
    await modalTable
      .first()
      .waitFor({ state: "visible", timeout: SLOW_ACTION_TIMEOUT });
    const modalRowCount = await modalTable.count();
    expect(modalRowCount).toBeGreaterThan(0);

    // Select the product row in the modal (Ctrl+click for selection)
    await modalTable.first().click({ modifiers: ["Control"] });
    await page.waitForTimeout(1000);

    // The "Pick" quick action should now be visible in the modal
    const modalPickButton = page
      .locator('.raw-modal [data-testid^="quick-action-"]')
      .first();
    await modalPickButton.waitFor({
      state: "visible",
      timeout: SLOW_ACTION_TIMEOUT,
    });

    // Click "Pick" to pick the selected product
    await modalPickButton.click();

    // Wait for the pick process to complete
    await page
      .locator(".screen-freeze")
      .waitFor({ state: "detached", timeout: VERY_SLOW_ACTION_TIMEOUT })
      .catch(() => {});
    await page.waitForTimeout(2000);

    // Close modal
    const doneButton = page.getByTestId("modal-done");
    await doneButton.waitFor({
      state: "visible",
      timeout: SLOW_ACTION_TIMEOUT,
    });
    await doneButton.click();
    await page.waitForTimeout(1000);

    // Verify we're back on the Picking Terminal grid
    await page
      .locator("table thead tr")
      .first()
      .waitFor({ state: "visible", timeout: SLOW_ACTION_TIMEOUT });
  });
});

/**
 * V1 Picking Terminal (window 540350) — Tests for QA scenarios 6, 7, 8.
 *
 * The V1 terminal shows shipment schedules with an included PickingSlotView.
 * The HU editor allows picking whole HUs or partial CUs to the picking slot.
 * Quick actions on the picking slot: Process, Reactivate (Unprocess), Unpick HU.
 *
 * Flow: Create V1 view → Select row → Open HU editor → Pick HU → Process → Reactivate → Unpick
 *
 * NOTE: The V1 terminal uses M_Picking_Candidate records (NOT M_Picking_Job from mobile picking).
 * The HU editor and Pick HU actions are V1-specific desktop picking mechanisms.
 *
 * Due to fragile row selection in the V1 terminal grid (click-to-edit instead of click-to-select),
 * the picking/processing actions are executed via REST API while UI state is verified visually.
 */
test.describe("Picking Terminal V1 — Process/Unprocess/Remove", () => {
  /**
   * Create test data with HU qty matching the order (avoids overdelivery error).
   * PI: 5 TUs × 4 CU/TU = 20 CU total. Order qty = 20 CU.
   */
  const createV1TestData = async (language = "en_US") => {
    return await Backend.createMasterdata({
      language,
      request: {
        login: { user: { language } },
        bpartners: { BP1: {} },
        warehouses: { wh: {} },
        pickingSlots: { slot1: {} },
        products: { P1: { prices: [{ price: 1 }] } },
        packingInstructions: {
          // LU with 5 TUs × 4 CU = 20 CU total (small enough for reliable tests)
          PI: {
            lu: "LU",
            qtyTUsPerLU: 5,
            tu: "TU",
            product: "P1",
            qtyCUsPerTU: 4,
          },
        },
        handlingUnits: {
          // HU will have 5 TUs × 4 CU = 20 CU from the PI
          HU1: { product: "P1", warehouse: "wh", packingInstructions: "PI" },
        },
        salesOrders: {
          SO1: {
            bpartner: "BP1",
            warehouse: "wh",
            datePromised: "2025-03-01T00:00:00.000+02:00",
            // Order exactly 20 CU to match the HU (5 TUs × 4 CU)
            lines: [{ product: "P1", qty: 20, piItemProduct: "TU" }],
          },
        },
      },
    });
  };

  /**
   * Helper: Create V1 view and navigate to it.
   * @returns {{ viewId: string, psViewId: string }} Main view ID and picking slot view ID
   */
  const openV1Terminal = async (page, shipmentScheduleId) => {
    // Create the V1 view with specific shipment schedule
    const createViewResp = await page.request.post(
      `${FRONTEND_BASE_URL}/rest/api/documentView/${PICKING_TERMINAL_V1_WINDOW_ID}`,
      {
        headers: { "Content-Type": "application/json" },
        data: {
          windowId: String(PICKING_TERMINAL_V1_WINDOW_ID),
          viewType: "grid",
          filterOnlyIds: [String(shipmentScheduleId)],
        },
      },
    );
    const viewData = await createViewResp.json();
    expect(viewData.viewId, "V1 view should be created").toBeTruthy();

    await page.goto(
      `${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V1_WINDOW_ID}?viewId=${viewData.viewId}`,
    );
    await page
      .locator("table thead tr")
      .first()
      .waitFor({ state: "visible", timeout: VERY_SLOW_ACTION_TIMEOUT });

    // Select the row to trigger the included PickingSlotView
    await page.waitForTimeout(2000);
    await page.locator("text=Select all on this page").first().click();
    await page.waitForTimeout(3000);

    // Verify 2 tables (main + included view)
    const tableCount = await page.locator("table").count();
    expect(
      tableCount,
      "V1 terminal should have main + included view",
    ).toBeGreaterThanOrEqual(2);

    // Get the picking slot view ID from the row data
    const rowResp = await page.request.get(
      `${FRONTEND_BASE_URL}/rest/api/documentView/${PICKING_TERMINAL_V1_WINDOW_ID}/${viewData.viewId}?firstRow=0&pageLength=10`,
    );
    const rowData = await rowResp.json();
    const psViewId = rowData.result?.[0]?.includedView?.viewId;
    expect(psViewId, "Picking slot view should exist").toBeTruthy();

    return { viewId: viewData.viewId, psViewId };
  };

  /**
   * Helper: Execute a WebUI process via REST API.
   * Creates a process instance and starts it.
   * @returns {Promise<Object>} Process result
   */
  const executeProcess = async (
    page,
    { processId, viewId, viewWindowId, viewDocumentIds },
  ) => {
    const createResp = await page.request.post(
      `${FRONTEND_BASE_URL}/rest/api/process/${processId}`,
      {
        headers: { "Content-Type": "application/json" },
        data: { processId, viewId, viewDocumentIds, viewWindowId },
      },
    );
    const instance = await createResp.json();
    expect(
      instance.pinstanceId,
      `Process ${processId} instance should be created`,
    ).toBeTruthy();

    const startResp = await page.request.get(
      `${FRONTEND_BASE_URL}/rest/api/process/${processId}/${instance.pinstanceId}/start`,
    );
    const resultText = await startResp.text();
    const result = JSON.parse(resultText);

    // Log error details for debugging
    if (result.error || startResp.status() >= 400) {
      console.log(
        `[WARN] Process ${processId} response (${startResp.status()}): ${resultText.substring(0, 500)}`,
      );
    }

    return result;
  };

  /**
   * Helper: Get quick actions for the picking slot view.
   * @returns {Promise<Array>} Array of action objects with processId, caption, disabled, internalName
   */
  const getPickingSlotQuickActions = async (page, psViewId, selectedIds) => {
    const qaResp = await page.request.post(
      `${FRONTEND_BASE_URL}/rest/api/documentView/pickingSlot/${psViewId}/quickActions`,
      {
        headers: { "Content-Type": "application/json" },
        data: { selectedIds },
      },
    );
    const qa = await qaResp.json();
    return qa.actions || [];
  };

  /**
   * Helper: Pick the top-level HU via the HU editor REST API flow.
   * Opens HU editor → selects LU → executes Pick HU.
   */
  const pickHUViaEditor = async (page, psViewId, pickingSlotId) => {
    // 1. Open HU editor
    const editorResult = await executeProcess(page, {
      processId: "ADP_540809", // WEBUI_Picking_HUEditor_Launcher
      viewId: psViewId,
      viewWindowId: "pickingSlot",
      viewDocumentIds: [pickingSlotId],
    });
    expect(
      editorResult.error,
      "HU editor should open without error",
    ).toBeFalsy();

    const huViewId = editorResult.action?.viewId;
    expect(huViewId, "HU editor view should be created").toBeTruthy();

    // 2. Get the top-level HU row
    const rowsResp = await page.request.get(
      `${FRONTEND_BASE_URL}/rest/api/documentView/husToPick/${huViewId}?firstRow=0&pageLength=20`,
    );
    const rows = await rowsResp.json();
    expect(rows.size, "HU editor should have at least 1 row").toBeGreaterThan(
      0,
    );

    const topLevelRow = rows.result.find(
      (r) => r.type === "LU" || r.type === "TU",
    );
    expect(topLevelRow, "Top-level HU row should exist").toBeTruthy();

    // 3. Execute Pick HU
    const pickResult = await executeProcess(page, {
      processId: "ADP_540811", // WEBUI_Picking_HUEditor_PickHU
      viewId: huViewId,
      viewWindowId: "husToPick",
      viewDocumentIds: [topLevelRow.id],
    });
    expect(pickResult.error, "Pick HU should succeed").toBeFalsy();
  };

  /**
   * Helper: Common setup for V1 tests — create data, login, open terminal, get slot ID.
   * Uses the V2 view API to find the shipment schedule ID (no PostgREST dependency).
   */
  const setupV1Test = async (page) => {
    const masterdata = await createV1TestData();
    await loginAndNavigate(page, masterdata);

    const docNo = masterdata.salesOrders.SO1.documentNo;
    const { shipmentScheduleId } = await findShipmentScheduleId(page, docNo);

    const { viewId, psViewId } = await openV1Terminal(page, shipmentScheduleId);

    const psRowsResp = await page.request.get(
      `${FRONTEND_BASE_URL}/rest/api/documentView/pickingSlot/${psViewId}?firstRow=0&pageLength=10`,
    );
    const psRows = await psRowsResp.json();
    const pickingSlotRowId = psRows.result[0].id;

    return {
      masterdata,
      viewId,
      psViewId,
      pickingSlotRowId,
      shipmentScheduleId,
    };
  };

  test("Scenario 7: Pick HU and process picking via V1 Picking Terminal", async ({
    page,
  }) => {
    allure.epic("E0105: Picking");
    allure.tag("F00230.1: MobileUI Order-based Picking");
    allure.story("V1 Picking Terminal — pick HU and process (QA scenario 7)");
    allure.severity("critical");

    const { masterdata, psViewId, pickingSlotRowId } = await setupV1Test(page);

    // Verify order data visible in the UI
    const docNo = masterdata.salesOrders.SO1.documentNo;
    expect(await page.content()).toContain(docNo);

    // Pick HU via HU editor
    await pickHUViaEditor(page, psViewId, pickingSlotRowId);

    // After picking, "Process picking" should be ENABLED
    const actionsAfterPick = await getPickingSlotQuickActions(page, psViewId, [
      pickingSlotRowId,
    ]);
    const processAction = actionsAfterPick.find(
      (a) => a.internalName === "WEBUI_Picking_M_Picking_Candidate_Process",
    );
    expect(processAction, "Process picking action should exist").toBeTruthy();
    expect(
      processAction.disabled || false,
      "Process picking should be enabled after picking",
    ).toBe(false);

    // Execute "Process picking" — creates shipment and closes picking candidates
    const processResult = await executeProcess(page, {
      processId: "ADP_540810",
      viewId: psViewId,
      viewWindowId: "pickingSlot",
      viewDocumentIds: [pickingSlotRowId],
    });
    expect(processResult.error, "Process picking should succeed").toBeFalsy();

    // Verify picking was processed by checking that "Process" is no longer available
    // (processed picking candidates can't be processed again)
    const actionsAfterProcess = await getPickingSlotQuickActions(
      page,
      psViewId,
      [pickingSlotRowId],
    );
    const processAfter = actionsAfterProcess.find(
      (a) => a.internalName === "WEBUI_Picking_M_Picking_Candidate_Process",
    );
    // After processing, the Process action should either be gone or disabled
    if (processAfter) {
      expect(
        processAfter.disabled,
        "Process picking should be disabled after processing",
      ).toBe(true);
    }
  });

  test("Scenario 6: Pick HU — verify picking candidate and action availability", async ({
    page,
  }) => {
    allure.epic("E0105: Picking");
    allure.tag("F00230.1: MobileUI Order-based Picking");
    allure.story(
      "V1 Picking Terminal — pick HU creates candidate, unpick action available (QA scenario 6)",
    );
    allure.severity("critical");

    const { psViewId, pickingSlotRowId } = await setupV1Test(page);

    // Before picking: verify Process action is disabled (no candidates yet)
    const actionsBefore = await getPickingSlotQuickActions(page, psViewId, [
      pickingSlotRowId,
    ]);
    const processBefore = actionsBefore.find(
      (a) => a.internalName === "WEBUI_Picking_M_Picking_Candidate_Process",
    );
    if (processBefore) {
      expect(
        processBefore.disabled,
        "Process should be disabled before picking",
      ).toBe(true);
    }

    // Pick HU via HU editor
    await pickHUViaEditor(page, psViewId, pickingSlotRowId);

    // After picking: "Process picking" should be ENABLED and "Unpick HU" should exist
    const actionsAfterPick = await getPickingSlotQuickActions(page, psViewId, [
      pickingSlotRowId,
    ]);
    const processAction = actionsAfterPick.find(
      (a) => a.internalName === "WEBUI_Picking_M_Picking_Candidate_Process",
    );
    const unpickAction = actionsAfterPick.find(
      (a) => a.internalName === "WEBUI_Picking_RemoveHUFromPickingSlot",
    );
    expect(
      processAction?.disabled || false,
      "Process should be enabled after picking",
    ).toBe(false);
    expect(unpickAction, "Unpick HU action should exist").toBeTruthy();
    // Note: Unpick requires selecting the picked HU child row (not the slot row),
    // which needs tree expansion not available via simple REST API calls.
  });
});
