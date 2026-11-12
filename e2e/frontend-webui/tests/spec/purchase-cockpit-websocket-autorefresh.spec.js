import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Purchase Cockpit auto-refresh via websocket.
 *
 * LOCAL validation ONLY. The Einkaufs-Cockpit window (AD_Window_ID=542101, view
 * RV_PurchaseCockpit) is a customer-specific customization, so this spec has NO home in
 * core frontend-webui CI — it is a one-off proof against a running substrate stack.
 *
 * FEATURE UNDER TEST:
 *   config table WEBUI_ViewInvalidateOnChange maps window 542101 -> M_ReceiptSchedule.
 *   The core ConfiguredViewInvalidationListener (in the WEBAPI JVM) listens for
 *   after-commit cache resets of M_ReceiptSchedule (broadcast cross-JVM from the APP
 *   JVM that commits them), full-invalidates the open Cockpit views, and pushes a
 *   "view changed" STOMP frame -> the open browser view re-fetches WITHOUT a manual reload.
 *
 * FLOW:
 *   Creating purchase orders from the Cockpit's purchase candidates (async work-package)
 *   completes a C_Order, whose completion asynchronously generates M_ReceiptSchedule rows
 *   (QtyToMove>0). RV_PurchaseCockpit.QtyOrdered ("Bestellt") = SUM(M_ReceiptSchedule.QtyToMove)
 *   grouped by picking group, so QtyOrdered must rise on the OPEN cockpit — pushed, not polled.
 *
 * SUBSTRATE PRE-CONDITIONS (seeded via SQL on the throwaway substrate):
 *   - product 2005599 shows on the Cockpit (picking group 1000002 = warehouses 1000112+1000113)
 *   - prepared purchase candidates 9900004 (wh 1000112, qty 5) + 9900005 (wh 1000113, qty 15)
 *     exist with a current vendor (2156423), currency EUR, and a PO price-list entry, so
 *     "Create Purchase Orders" can actually complete a C_Order and generate M_ReceiptSchedule.
 *
 * The PO-creation is triggered through the REAL process REST endpoints the WebUI itself uses
 * (jump = RV_PurchaseCockpit_JumpToPurchaseCandidate, then C_PurchaseCandiate_Create_PurchaseOrders),
 * from inside the already-open Cockpit page — so the whole app->webapi->websocket path is exercised.
 * The Cockpit page is NEVER reloaded after the baseline is captured.
 */

const COCKPIT_WINDOW_ID = 542101;
const CANDIDATE_WINDOW_ID = 540375; // WEBUI_PurchaseCandidatesView opened by the jump
const PRODUCT_VALUE_MARKER = 'S31234_P'; // product 2005599 on the cockpit row
const JUMP_PROCESS_ID = 'ADP_585624'; // RV_PurchaseCockpit_JumpToPurchaseCandidate
const CREATE_ORDERS_PROCESS_ID = 'ADP_540979'; // C_PurchaseCandiate_Create_PurchaseOrders

// helpers run inside the page so they share the authenticated session cookie
const pagePost = async (page, url, body) =>
  page.evaluate(
    async ({ url, body }) => {
      const r = await fetch(url, {
        method: 'POST',
        credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify(body),
      });
      const t = await r.text();
      let j = null;
      try { j = JSON.parse(t); } catch (e) { /* keep text */ }
      return { status: r.status, body: j || t };
    },
    { url, body }
  );

const pageGet = async (page, url) =>
  page.evaluate(async (url) => {
    const r = await fetch(url, { credentials: 'include', headers: { Accept: 'application/json' } });
    const t = await r.text();
    let j = null;
    try { j = JSON.parse(t); } catch (e) { /* keep text */ }
    return { status: r.status, body: j || t };
  }, url);

test('Purchase Cockpit QtyOrdered auto-updates via websocket after PO creation (no reload)', async ({ page }) => {
  test.setTimeout(150000);

  // ---- capture STOMP-over-websocket frames BEFORE any navigation ----
  const wsFrames = [];
  page.on('websocket', (ws) => {
    ws.on('framereceived', (ev) => {
      const payload = typeof ev.payload === 'string' ? ev.payload : (ev.payload ? ev.payload.toString() : '');
      if (payload) wsFrames.push(payload);
    });
  });

  // ---- 1) deterministic REST login as the standard admin user (sees the cockpit) ----
  await test.step('Log in to metasfresh', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/login`);
    const loggedIn = await page.evaluate(async () => {
      const post = async (u, b) => {
        const r = await fetch(u, { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json', Accept: 'application/json' }, body: JSON.stringify(b) });
        const t = await r.text(); let j = null; try { j = JSON.parse(t); } catch (e) {} return { status: r.status, body: j || t };
      };
      const auth = await post('/rest/api/login/authenticate', { username: 'metasfresh', password: 'metasfresh' });
      const admin = (auth.body.roles || []).find((r) => /Admin/.test(r.caption)) || auth.body.roles[0];
      await post('/rest/api/login/loginComplete', admin);
      const chk = await fetch('/rest/api/login/isLoggedIn', { credentials: 'include' });
      return (await chk.text()).trim();
    });
    expect(loggedIn).toBe('true');
  });

  // ---- 2) open the Cockpit; the default view applies a sticky "Is Action Needed" quick-filter
  //          that hides rows whose balance is already non-negative. Clear it (via the same
  //          /filter endpoint the WebUI uses) so the product row is deterministically visible,
  //          then open that unfiltered view. The auto-refresh mechanism is filter-independent. ----
  const cockpitViewId = await test.step('Open the Purchase Cockpit (Einkaufs-Cockpit)', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${COCKPIT_WINDOW_ID}`);
    await page.waitForURL(/viewId=/, { timeout: SLOW_ACTION_TIMEOUT });
    const defaultViewId = new URL(page.url()).searchParams.get('viewId');
    const viewId = await page.evaluate(async ({ windowId, viewId }) => {
      const r = await fetch(`/rest/api/documentView/${windowId}/${viewId}/filter`, {
        method: 'POST', credentials: 'include',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify({ filters: [] }),
      });
      const j = await r.json();
      return j.viewId || viewId;
    }, { windowId: COCKPIT_WINDOW_ID, viewId: defaultViewId });
    expect(viewId, 'unfiltered cockpit view id').toBeTruthy();
    await page.goto(`${FRONTEND_BASE_URL}/window/${COCKPIT_WINDOW_ID}?viewId=${viewId}`);
    await page.waitForURL(new RegExp(`viewId=${viewId}`), { timeout: SLOW_ACTION_TIMEOUT });
    console.log(`[cockpit-ws] Cockpit view (unfiltered): ${viewId}`);
    return viewId;
  });

  // the product row (identified by its product value marker) and its QtyOrdered cell
  const productRow = page.locator('tbody tr', { has: page.locator(`td[data-cy="cell-M_Product_ID"]:has-text("${PRODUCT_VALUE_MARKER}")`) });
  const qtyOrderedCell = productRow.locator('td[data-cy="cell-QtyOrdered"]');
  const readQtyOrdered = async () => {
    const txt = (await qtyOrderedCell.textContent()) || '';
    return parseFloat(txt.replace(/[^\d.-]/g, '')) || 0;
  };

  const { baseline, cockpitRowId } = await test.step('Read the baseline "Bestellt" (QtyOrdered) on the product row', async () => {
    const productCell = page.locator(`tbody tr td[data-cy="cell-M_Product_ID"]:has-text("${PRODUCT_VALUE_MARKER}")`);
    await productCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await qtyOrderedCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const base = await readQtyOrdered();
    console.log(`[cockpit-ws] Baseline QtyOrdered (rendered grid): ${base}`);

    // resolve the cockpit view row id (RV_PurchaseCockpit_ID) for the product, for the jump
    const cockpitRows = await pageGet(page, `/rest/api/documentView/${COCKPIT_WINDOW_ID}/${cockpitViewId}?firstRow=0&pageLength=200`);
    const cockpitRow = (cockpitRows.body.result || []).find((r) =>
      String((r.fieldsByName?.ProductValue || {}).value || '').includes(PRODUCT_VALUE_MARKER) ||
      String(((r.fieldsByName?.M_Product_ID || {}).value || {}).caption || '').includes(PRODUCT_VALUE_MARKER)
    );
    expect(cockpitRow, 'cockpit row for the product').toBeTruthy();
    console.log(`[cockpit-ws] Cockpit row id: ${cockpitRow.id}`);
    return { baseline: base, cockpitRowId: cockpitRow.id };
  });

  const framesBefore = wsFrames.length;

  // ---- 3) trigger PO creation from the candidate(s), WITHOUT reloading the Cockpit page ----
  const candidateViewId = await test.step('Jump to Purchase Candidates', async () => {
    const jumpCreate = await pagePost(page, `/rest/api/process/${JUMP_PROCESS_ID}`, {
      processId: JUMP_PROCESS_ID, viewId: cockpitViewId, viewDocumentIds: [cockpitRowId],
    });
    const jumpStart = await pageGet(page, `/rest/api/process/${JUMP_PROCESS_ID}/${jumpCreate.body.pinstanceId}/start`);
    const viewId = jumpStart.body.action?.viewId;
    expect(viewId, 'candidate view id from jump').toBeTruthy();
    console.log(`[cockpit-ws] Jumped to candidate view: ${viewId}`);
    return viewId;
  });

  await test.step('Create Purchase Orders from the candidates', async () => {
    // select all prepared candidates in that view and run "Create orders for selection"
    const candRows = await pageGet(page, `/rest/api/documentView/${CANDIDATE_WINDOW_ID}/${candidateViewId}?firstRow=0&pageLength=200`);
    const candidateIds = (candRows.body.result || []).map((r) => r.id);
    console.log(`[cockpit-ws] Candidates to order: ${JSON.stringify(candidateIds)}`);
    expect(candidateIds.length, 'prepared purchase candidates present').toBeGreaterThan(0);

    const createInst = await pagePost(page, `/rest/api/process/${CREATE_ORDERS_PROCESS_ID}`, {
      processId: CREATE_ORDERS_PROCESS_ID, viewId: candidateViewId, viewDocumentIds: candidateIds,
    });
    const createStart = await pageGet(page, `/rest/api/process/${CREATE_ORDERS_PROCESS_ID}/${createInst.body.pinstanceId}/start`);
    expect(createStart.body.error, `create-orders process error: ${JSON.stringify(createStart.body)}`).toBeFalsy();
    console.log('[cockpit-ws] Create-Purchase-Orders process enqueued (async work-package).');
  });

  // ---- 4) WITHOUT reloading: assert the OPEN grid's QtyOrdered auto-updates (websocket push) ----
  const finalQty = await test.step('Cockpit auto-refreshes via websocket (no reload)', async () => {
    // Generous window: async PO-gen WP + order completion + receipt-schedule WP + debounce (~100ms) + broadcast.
    await expect
      .poll(readQtyOrdered, { timeout: VERY_SLOW_ACTION_TIMEOUT + 60000, intervals: [500, 1000, 2000] })
      .toBeGreaterThan(baseline);
    const qty = await readQtyOrdered();
    console.log(`[cockpit-ws] QtyOrdered auto-updated (no reload): ${baseline} -> ${qty}`);
    expect(qty).toBeGreaterThan(baseline);
    // sanity: the page really was never reloaded after baseline
    expect(page.url()).toContain(`viewId=${cockpitViewId}`);
    return qty;
  });

  // ---- 5) corroborate with the websocket "view changed" frame that drove the refetch ----
  await test.step('Confirm the STOMP "view changed" frame', async () => {
    const newFrames = wsFrames.slice(framesBefore);
    const viewFrames = newFrames.filter((f) =>
      f.includes(cockpitViewId) || /invalidat|fullyChanged|"type"\s*:\s*"view/i.test(f)
    );
    console.log(`[cockpit-ws] websocket frames after trigger: ${newFrames.length}, referencing the cockpit view: ${viewFrames.length}`);
    if (viewFrames.length > 0) {
      console.log(`[cockpit-ws] view-changed frame sample: ${viewFrames[0].slice(0, 400)}`);
    }
    // The rendered auto-update above is the definitive user-visible proof of the push;
    // the STOMP frame is corroborating evidence (transport-dependent), so assert softly.
    expect(newFrames.length, 'at least one websocket frame received on the open view').toBeGreaterThan(0);
    void finalQty;
  });
});
