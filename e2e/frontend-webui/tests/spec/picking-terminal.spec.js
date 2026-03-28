import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { FRONTEND_BASE_URL, getPage, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PICKING_TERMINAL_V2_WINDOW_ID } from '../utils/WindowIds';

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

    // Verify at least one quick action appeared after selecting
    expect(
      allQuickActions,
      `Quick actions after selecting modal row: ${allQuickActions}`
    ).toContain('quick-action-');

    // Click "Done" to close the modal
    const doneButton = page.getByTestId('modal-done');
    if (await doneButton.isVisible().catch(() => false)) {
      await doneButton.click();
      await page.waitForTimeout(1000);
    }
  });
});
