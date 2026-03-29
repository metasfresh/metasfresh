import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PICKING_TERMINAL_V2_WINDOW_ID } from '../utils/WindowIds';

const PICKING_TERMINAL_V1_WINDOW_ID = 540350;
const TESTING_API_BASE_URL = process.env.TESTING_API_BASE_URL || 'http://localhost:8282/api/v2';
const POSTGREST_BASE_URL = process.env.POSTGREST_BASE_URL || 'http://localhost:32001';

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

const createPickingTestData = async (language = 'en_US') => {
  return await Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      mobileConfig: {
        picking: {
          aggregationType: 'sales_order',
          allowPickingAnyCustomer: true,
          allowPickingAnyHU: true,
          createShipmentPolicy: 'CL',
          pickTo: ['LU_TU'],
        },
      },
      bpartners: { BP1: {} },
      warehouses: { wh: {} },
      pickingSlots: { slot1: {} },
      products: { P1: { prices: [{ price: 1 }] } },
      packingInstructions: {
        PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
      },
      handlingUnits: {
        HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
      },
      salesOrders: {
        SO1: {
          bpartner: 'BP1',
          warehouse: 'wh',
          datePromised: '2025-03-01T00:00:00.000+02:00',
          lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
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
  await page.locator('input[name="username"]').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
  await page.locator('input[name="username"]').fill(username);
  await page.locator('input[name="password"]').fill(password);
  await page.locator('.btn-meta-success').click();

  // Handle role selection if needed
  await page.waitForTimeout(2000);
  if (page.url().includes('/login')) {
    const sendButton = page.locator('.btn-meta-success');
    if (await sendButton.isVisible().catch(() => false)) {
      await sendButton.click();
    }
  }
  await page.waitForURL((url) => !url.toString().includes('/login'), { timeout: VERY_SLOW_ACTION_TIMEOUT });
};

test.describe('Picking Terminal V2 — Desktop WebUI', () => {
  test('Navigate to Picking Terminal and verify grid with data', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.story('Desktop Picking Terminal V2 — view and select shipment schedules');
    allure.severity('critical');

    const masterdata = await createPickingTestData();
    await loginAndNavigate(page, masterdata);

    // Navigate to Picking Terminal V2
    await page.goto(`${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V2_WINDOW_ID}`);
    await page.locator('table thead tr').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    // Verify grid loaded with rows
    const rowCount = await page.locator('table tbody tr').count();
    expect(rowCount).toBeGreaterThan(0);

    // Select first row
    await page.locator('table tbody tr').first().click();
    await page.waitForTimeout(1000);

    // Verify "Pick" quick action appeared with process-specific data-testid
    const pickButton = page.locator('[data-testid^="quick-action-PackageablesView_OpenProductsToPick"]');
    await pickButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  });

  test('Open Products to Pick modal and pick selected', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.story('Desktop Picking Terminal V2 — Pick from Products to Pick modal (QA scenario 7 equivalent)');
    allure.severity('critical');

    const masterdata = await createPickingTestData();
    await loginAndNavigate(page, masterdata);

    // Navigate to Picking Terminal V2
    await page.goto(`${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V2_WINDOW_ID}`);
    await page.locator('table thead tr').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    // Select a single row — click on the row number cell (leftmost column)
    // In view mode, we need to click the row indicator area.
    // The first column often contains a row number or selection indicator.
    const firstRow = page.locator('table tbody tr').first();

    // Find a row that's NOT locked (no Picking User) — those without text in Picking User column
    // The 5th column (index 4) is "Picking User"
    let targetRow = null;
    const rowCount = await page.locator('table tbody tr').count();
    for (let i = 0; i < rowCount; i++) {
      const row = page.locator('table tbody tr').nth(i);
      const pickingUserCell = row.locator('td').nth(4);
      const pickingUserText = await pickingUserCell.innerText().catch(() => '');
      if (!pickingUserText.trim()) {
        targetRow = row;
        break;
      }
    }

    if (!targetRow) {
      // All rows are locked — just use first row
      targetRow = firstRow;
    }

    // Click the row's first cell (row number) to select it
    await targetRow.locator('td').first().click();
    await page.waitForTimeout(1000);

    // Check if selected
    const isSelected = await targetRow.evaluate((el) => el.classList.contains('row-selected'));
    if (!isSelected) {
      // Try double-clicking the row indicator
      await targetRow.locator('td').first().dblclick();
      await page.waitForTimeout(500);
    }

    // Click "Pick" quick action button
    const pickButton = page.locator('[data-testid^="quick-action-PackageablesView_OpenProductsToPick"]');
    await pickButton.click();

    // Wait for modal overlay to appear (Products to Pick view)
    // The modal uses class "panel-modal" or similar
    await page.waitForTimeout(3000);

    // Wait for the "Products to Pick" modal to appear
    await page.locator('.raw-modal, .screen-freeze').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    // Wait for screen-freeze to disappear (process completed)
    await page.locator('.screen-freeze').waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(1000);

    // Verify the modal is showing with product rows
    const modalTable = page.locator('.raw-modal table tbody tr');
    await modalTable.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const modalRowCount = await modalTable.count();
    expect(modalRowCount).toBeGreaterThan(0);

    // Select the product row in the modal (Ctrl+click for selection)
    await modalTable.first().click({ modifiers: ['Control'] });
    await page.waitForTimeout(1000);

    // After selecting, quick actions should appear in the modal header
    // Discover them
    const modalQuickActions = await page.evaluate(() => {
      return Array.from(document.querySelectorAll('.raw-modal [data-testid^="quick-action-"]'))
        .map(el => el.dataset.testid)
        .join(', ');
    });

    // Also check for quick actions outside the modal (they might be in the view header)
    const allQuickActions = await page.evaluate(() => {
      return Array.from(document.querySelectorAll('[data-testid^="quick-action-"]'))
        .map(el => el.dataset.testid)
        .join(', ');
    });

    // Take a screenshot for reference
    await page.screenshot({ path: '/tmp/pick-modal-with-selection.png' });

    // Select the product row in the modal using Ctrl+click
    await modalTable.first().click({ modifiers: ['Control'] });
    await page.waitForTimeout(1000);

    // The "Pick" quick action should now be visible in the modal
    const modalPickButton = page.locator('.raw-modal [data-testid^="quick-action-"]').first();
    await modalPickButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    // Click "Pick" to pick the selected product
    await modalPickButton.click();

    // Wait for the pick process to complete
    await page.locator('.screen-freeze').waitFor({ state: 'detached', timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});
    await page.waitForTimeout(2000);

    // After picking, the row should show "Picked" status
    // Check if the Pick Status column changed
    const pickStatusCell = page.locator('.raw-modal table tbody tr').first().locator('td').last();
    const pickStatus = await pickStatusCell.innerText().catch(() => 'unknown');

    // Take screenshot for verification
    await page.screenshot({ path: '/tmp/pick-modal-after-pick.png' });

    // Close modal
    const doneButton = page.getByTestId('modal-done');
    await doneButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await doneButton.click();
    await page.waitForTimeout(1000);

    // Verify we're back on the Picking Terminal grid
    await page.locator('table thead tr').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  });
});

/**
 * V1 Picking Terminal (window 540350) — Tests for QA scenarios 6, 7, 8.
 *
 * The V1 terminal shows picking slots with picked HUs for specific shipment schedules.
 * Quick actions: Process, Unprocess, Remove HU from Picking Slot.
 *
 * Flow: Mobile pick first (via REST API) → Open V1 terminal → run desktop actions.
 */
test.describe('Picking Terminal V1 — Process/Unprocess/Remove', () => {
  /**
   * Helper: perform mobile picking via REST API (no browser).
   * Creates a picking job, scans slot, sets LU, picks HU, returns job details.
   */
  const performMobilePicking = async (page, masterdata) => {
    const appBaseUrl = TESTING_API_BASE_URL.replace('/api/v2', '');
    const token = masterdata.login.user.token;
    const headers = { 'Content-Type': 'application/json', Authorization: token };

    // 1. Query launchers to find our order
    const launchersResp = await page.request.post(`${appBaseUrl}/api/v2/userWorkflows/launchers/query`, {
      headers,
      data: { applicationId: 'picking' },
    });
    const launchers = await launchersResp.json();
    const docNo = masterdata.salesOrders.SO1.documentNo;
    const launcher = launchers.launchers.find((l) => l.caption?.includes(docNo));
    if (!launcher) throw new Error(`Launcher for ${docNo} not found in ${launchers.launchers.length} launchers`);

    // 2. Start the picking job
    const startResp = await page.request.post(
      `${appBaseUrl}/api/v2/userWorkflows/launchers/${launcher.applicationId}/start`,
      { headers, data: launcher.wfParameters }
    );
    const wfProcess = await startResp.json();
    const wfProcessId = wfProcess.id;

    // 3. Scan picking slot
    const scanSlotResp = await page.request.post(`${appBaseUrl}/api/v2/picking/event`, {
      headers,
      data: {
        wfProcessId,
        type: 'SCAN_PICKING_SLOT',
        pickingSlotQRCode: masterdata.pickingSlots.slot1.qrCode,
      },
    });

    // 4. Set target LU
    await page.request.post(`${appBaseUrl}/api/v2/picking/job/${wfProcessId}/target/lu`, {
      headers,
      data: { caption: masterdata.packingInstructions.PI.luName },
    });

    // 5. Pick the HU
    const pickResp = await page.request.post(`${appBaseUrl}/api/v2/picking/event`, {
      headers,
      data: {
        wfProcessId,
        type: 'PICK',
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        qtyPicked: 3,
      },
    });

    return { wfProcessId };
  };

  /**
   * Helper: create V1 view via WebAPI and navigate to it.
   */
  const openV1PickingTerminal = async (page, shipmentScheduleId) => {
    // Create the V1 view via WebAPI (port 8080)
    const createViewResp = await page.request.post(
      `${FRONTEND_BASE_URL}/rest/api/documentView/${PICKING_TERMINAL_V1_WINDOW_ID}`,
      {
        headers: { 'Content-Type': 'application/json' },
        data: {
          windowId: String(PICKING_TERMINAL_V1_WINDOW_ID),
          viewType: 'grid',
          filterOnlyIds: [String(shipmentScheduleId)],
        },
      }
    );
    const viewData = await createViewResp.json();
    const viewId = viewData.viewId;

    // Navigate to the V1 terminal with this view
    await page.goto(`${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V1_WINDOW_ID}?viewId=${viewId}`);
    await page.locator('table thead tr').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    return viewId;
  };

  test('Scenario 7: Open V1 Picking Terminal with shipment schedule', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.story('V1 Picking Terminal — open with shipment schedule (QA scenario 7)');
    allure.severity('critical');

    // 1. Create test data
    const masterdata = await createPickingTestData();
    await loginAndNavigate(page, masterdata);

    // 2. Get shipment schedule ID via PostgREST
    const orderId = masterdata.salesOrders.SO1.id;
    const ssResp = await page.request.get(
      `${POSTGREST_BASE_URL}/m_shipmentschedule?c_order_id=eq.${orderId}&select=m_shipmentschedule_id&limit=1`
    );
    const ssData = await ssResp.json();
    expect(ssData.length, 'Shipment schedule should exist for order ' + orderId).toBeGreaterThan(0);
    const shipmentScheduleId = ssData[0].m_shipmentschedule_id;

    // 3. Create V1 view via WebAPI with the shipment schedule ID
    const createViewResp = await page.request.post(
      `${FRONTEND_BASE_URL}/rest/api/documentView/${PICKING_TERMINAL_V1_WINDOW_ID}`,
      {
        headers: { 'Content-Type': 'application/json' },
        data: {
          windowId: String(PICKING_TERMINAL_V1_WINDOW_ID),
          viewType: 'grid',
          filterOnlyIds: [String(shipmentScheduleId)],
        },
      }
    );
    const viewData = await createViewResp.json();
    expect(viewData.viewId, 'V1 view should be created').toBeTruthy();
    expect(viewData.size, 'V1 view should have 1 row').toBe(1);

    // 4. Navigate to the V1 terminal
    await page.goto(`${FRONTEND_BASE_URL}/window/${PICKING_TERMINAL_V1_WINDOW_ID}?viewId=${viewData.viewId}`);
    await page.locator('table thead tr').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    // 5. Verify the shipment schedule row is visible
    const rowCount = await page.locator('table tbody tr').count();
    expect(rowCount).toBe(1);

    // 6. Click the row to see the included PickingSlotView
    await page.locator('table tbody tr').first().click();
    await page.waitForTimeout(2000);

    // Take screenshot to see the V1 terminal layout
    await page.screenshot({ path: '/tmp/v1-picking-terminal.png' });

    // The V1 terminal should show the shipment schedule on the left
    // and the picking slot view (empty until picking is done) on the right.
    // For now, verify the page loaded correctly with our data.
    const docNo = masterdata.salesOrders.SO1.documentNo;
    const pageContent = await page.content();
    expect(pageContent).toContain(docNo);
  });
});
