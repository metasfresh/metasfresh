import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { PURCHASE_ORDER_WINDOW_ID, RECEIPT_LOGISTICS_WINDOW_ID } from '../utils/WindowIds';

/** AD_Tab_ID of "Bestellposition" (purchase order line) in the purchase order window. */
const PURCHASE_ORDER_LINE_TAB_ID = 'AD_Tab-293';

/**
 * AD_Process.Value of the receipt-logistics window's default quick action and its fallback — the two
 * captions {@code data-testid="quick-action-button"} can show. Both live on the same AD_Table_Process
 * row set that puts the receive actions on the receipt-logistics grid.
 */
const HUS_VOREINST_INTERNAL_NAME = 'WEBUI_RV_ReceiptLogistics_ReceiveHUs_UsingDefaults';

/** AD_Process.Value of the multi-row receive — reachable from the action menu only. */
const MULTI_ROW_RECEIVE_INTERNAL_NAME = 'WEBUI_RV_ReceiptLogistics_Generate_M_InOuts';

/**
 * The receipt-logistics window's quick-action default and its fallback, and the multi-row receive being
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
test.describe('Receipt logistics — quick-action default and its fallback', () => {
  test('a row with a packing instruction defaults to "HUs annehmen Voreinst.", a row without one falls back to "CUs annehmen", and the multi-row receive stays menu-only', async ({
    page,
  }) => {
    allure.epic('E0360: Transport (Extralogistik)');
    allure.tag('F29050: Delivery Planning');
    allure.story('Receipt-logistics quick-action default with its fallback');
    allure.severity('critical');
    allure.description(`
## Receipt logistics — quick-action default and its fallback

1. Creates one purchase order with two lines against the SAME vendor — one product carries a packing
   instruction (an \`M_HU_PI_Item_Product\`), the other does not — and completes it, producing two
   UNPLANNED receipt-schedule rows on window ${RECEIPT_LOGISTICS_WINDOW_ID}.
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
            name: `Receipt logistics quick-action vendor ${Date.now()}`,
            isVendor: true,
            isCustomer: false,
          },
        },
        products: {
          PACKED: { name: `RL packed product ${Date.now()}`, type: 'Item' },
          UNPACKED: { name: `RL unpacked product ${Date.now()}`, type: 'Item' },
        },
        // Gives PACKED product a default LU/TU configuration (M_HU_PI_Item_Product) — the ONE thing
        // that makes "HUs annehmen Voreinst." resolve. UNPACKED gets none, which is the natural,
        // no-setup-needed state that makes its default reject internally.
        packingInstructions: {
          RL_TU: { tu: 'RL_TU_PI', product: 'PACKED', qtyCUsPerTU: 10 },
        },
      },
    });

    const vendorId = masterdata.bpartners.VENDOR.id;
    const packedProductId = masterdata.products.PACKED.id;
    const unpackedProductId = masterdata.products.UNPACKED.id;
    const packedProductName = masterdata.products.PACKED.name;
    const unpackedProductName = masterdata.products.UNPACKED.name;
    expect(vendorId).toBeTruthy();
    expect(packedProductId).toBeTruthy();
    expect(unpackedProductId).toBeTruthy();

    const REST = FRONTEND_BASE_URL.replace(/:3000$/, ':8080') + '/rest/api';

    const firstDocument = (body) => (Array.isArray(body) ? body : body.documents || [body])[0];
    const patchDocument = async (windowId, documentId, tabId, rowId, changes) => {
      const path = tabId
        ? `${REST}/window/${windowId}/${documentId}/${tabId}/${rowId}`
        : `${REST}/window/${windowId}/${documentId}`;
      const response = await page.request.patch(path, { data: changes });
      if (!response.ok()) {
        throw new Error(`PATCH ${path} failed: HTTP ${response.status()} ${await response.text()}`);
      }
      return firstDocument(await response.json());
    };

    // === Authenticate via REST (session cookie carries over to the browser part below) — same
    // 'metasfresh'/'metasfresh' admin credentials delivery-instruction-qty-sync.spec.js uses for its
    // window-PATCH setup, independent of the per-test login user created above for the browser part.
    await test.step('Authenticate via REST', async () => {
      const sessionBody = await (await page.request.get(`${REST}/userSession`)).json().catch(() => ({}));
      if (!sessionBody.loggedIn) {
        const authResponse = await page.request.post(`${REST}/login/authenticate`, {
          data: { username: 'metasfresh', password: 'metasfresh' },
        });
        const authBody = await authResponse.json();
        if (authBody.loginComplete === false && authBody.roles && authBody.roles.length > 0) {
          await page.request.post(`${REST}/login/loginComplete`, { data: authBody.roles[0] });
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

      for (const productId of [packedProductId, unpackedProductId]) {
        const lineResponse = await page.request.patch(
          `${REST}/window/${PURCHASE_ORDER_WINDOW_ID}/${purchaseOrderId}/${PURCHASE_ORDER_LINE_TAB_ID}/NEW`,
          { data: [] }
        );
        const lineRow = firstDocument(await lineResponse.json());
        await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, PURCHASE_ORDER_LINE_TAB_ID, lineRow.rowId, [
          { op: 'replace', path: 'M_Product_ID', value: Number(productId) },
          { op: 'replace', path: 'QtyEntered', value: 5 },
        ]);
      }

      await patchDocument(PURCHASE_ORDER_WINDOW_ID, purchaseOrderId, null, null, [
        { op: 'replace', path: 'DocAction', value: 'CO' },
      ]);
    });

    // === Login the browser session and open the receipt-logistics window ===
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await page.goto(`${FRONTEND_BASE_URL}/window/${RECEIPT_LOGISTICS_WINDOW_ID}`);
    await page.waitForLoadState('networkidle', { timeout: VERY_SLOW_ACTION_TIMEOUT }).catch(() => {});

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

      // NO `if (await ...isVisible())` around either assertion. An assertion that runs only when its own
      // precondition happens to hold cannot fail, and that is not a hypothetical here: the action-menu half
      // used to guard on `[data-testid="toggle-actions"], .actions-toggle, [data-testid="actions-btn"]`,
      // none of which occurs anywhere in `frontend/src`, so the block never executed and the step reported
      // green having asserted nothing. Both openers are waited for instead, so a missing one fails loudly.

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
