import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Purchase Cockpit (Einkaufs-Cockpit) — warehouse picking-group grain + group-scoped jump.
 *
 * LOCAL validation ONLY. The Einkaufs-Cockpit window (AD_Window_ID=542101, view
 * RV_PurchaseCockpit) is a customer-specific customization, so this spec has NO home in core
 * frontend-webui CI — it is a one-off proof against a running substrate stack.
 *
 * WHAT THIS PROVES:
 *   (a) The cockpit grid carries the warehouse picking-group column
 *       (RV_PurchaseCockpit.M_Warehouse_PickingGroup_ID): the grouped product row renders a
 *       visible, non-empty picking-group cell, and the grid view layout exposes an
 *       element/field for M_Warehouse_PickingGroup_ID.
 *   (b) The group-scoped "Jump to Purchase Candidates" process (REST id ADP_585624) is
 *       warehouse-picking-group aware: jumping from a GROUPED cockpit row
 *       (M_Warehouse_PickingGroup_ID = 1000002) opens the candidate view (window 540375)
 *       filtered to the candidates of ALL warehouses of that group — here warehouses
 *       1000112 AND 1000113 — while the candidate of the UNGROUPED warehouse 540008 is
 *       excluded (it does not belong to the group).
 *   (c) When an ungrouped cockpit row for warehouse 540008 exists, jumping from it returns
 *       only that single warehouse's candidate. (The cockpit view is demand-driven; if no
 *       ungrouped row for 540008 is rendered, (c) is logged and skipped — (b) is the primary
 *       proof.)
 *
 * This is READ-ONLY: it exercises the jump but never runs "Create Purchase Orders", so the
 * seeded OPEN candidates stay intact.
 *
 * SUBSTRATE PRE-CONDITIONS (seeded via SQL on the throwaway substrate, NOT by this spec):
 *   - product 2005599 (value marker S31234_P) shows on the cockpit under picking group
 *     1000002 = warehouses 1000112 (BFT_W1) + 1000113 (BFT_W2); warehouse 540008 is ungrouped.
 *   - OPEN, prepared, jumpable purchase candidates (Processed='N', IsPrepared='Y',
 *     QtyToPurchase>0): 9900004 (wh 1000112) + 9900005 (wh 1000113) in group 1000002,
 *     and 9900003 (wh 540008) ungrouped — the out-of-group control.
 *
 * The jump is driven through the REAL process REST endpoint the WebUI itself uses, from
 * inside the already-open Cockpit page — so the whole app->webapi path is exercised.
 * All selectors/assertions are language-INVARIANT (data-cy / DB ColumnName / REST field
 * names / ID keys) — never localized UI text.
 */

const COCKPIT_WINDOW_ID = 542101;
const CANDIDATE_WINDOW_ID = 540375; // WEBUI_PurchaseCandidatesView opened by the jump
const PRODUCT_VALUE_MARKER = 'S31234_P'; // product 2005599 on the cockpit row
const JUMP_PROCESS_ID = 'ADP_585624'; // RV_PurchaseCockpit_JumpToPurchaseCandidate
const PICKING_GROUP_ID = '1000002'; // the grouped picking group under test
const GROUP_WAREHOUSE_IDS = ['1000112', '1000113']; // both warehouses of group 1000002
const UNGROUPED_WAREHOUSE_ID = '540008'; // ungrouped control warehouse

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

// resolve a field's ID key from a documentView row (fieldsByName.<field>.value.key)
const fieldKey = (row, fieldName) => {
  const v = ((row.fieldsByName || {})[fieldName] || {}).value;
  if (v && typeof v === 'object') return v.key != null ? String(v.key) : null;
  return v != null ? String(v) : null;
};

// jump from a cockpit row via ADP_585624 and return the resulting candidate view id
const jumpToCandidates = async (page, cockpitViewId, cockpitRowId) => {
  const jumpCreate = await pagePost(page, `/rest/api/process/${JUMP_PROCESS_ID}`, {
    processId: JUMP_PROCESS_ID, viewId: cockpitViewId, viewDocumentIds: [cockpitRowId],
  });
  const jumpStart = await pageGet(page, `/rest/api/process/${JUMP_PROCESS_ID}/${jumpCreate.body.pinstanceId}/start`);
  const candidateViewId = jumpStart.body.action?.viewId;
  expect(candidateViewId, `candidate view id from jump (start=${JSON.stringify(jumpStart.body)})`).toBeTruthy();
  return candidateViewId;
};

// read the warehouse-ID set (from M_WarehousePO_ID) of a candidate view
const readCandidateWarehouseIds = async (page, candidateViewId) => {
  const cand = await pageGet(page, `/rest/api/documentView/${CANDIDATE_WINDOW_ID}/${candidateViewId}?firstRow=0&pageLength=200`);
  const rows = cand.body.result || [];
  return rows.map((r) => fieldKey(r, 'M_WarehousePO_ID')).filter((x) => x != null);
};

test('Purchase Cockpit picking-group column renders and drives a group-scoped candidate jump', async ({ page }) => {
  test.setTimeout(90000);
  allure.tag('F00655: Einkaufs-Cockpit');
  allure.tag('F00655');

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
  //          then open that unfiltered view. ----
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
    console.log(`[cockpit-pg] Cockpit view (unfiltered): ${viewId}`);
    return viewId;
  });

  // ---- 3a) the picking-group column renders on the grouped product row ----
  await test.step('Verify the picking-group column renders on the product row', async () => {
    const productCell = page.locator(`tbody tr td[data-cy="cell-M_Product_ID"]:has-text("${PRODUCT_VALUE_MARKER}")`);
    await productCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const productRow = page.locator('tbody tr', { has: page.locator(`td[data-cy="cell-M_Product_ID"]:has-text("${PRODUCT_VALUE_MARKER}")`) });
    const pickingGroupCell = productRow.locator('td[data-cy="cell-M_Warehouse_PickingGroup_ID"]');
    await pickingGroupCell.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const pgText = ((await pickingGroupCell.textContent()) || '').trim();
    console.log(`[cockpit-pg] Rendered picking-group cell text: "${pgText}"`);
    expect(pgText.length, 'picking-group cell has a non-empty value').toBeGreaterThan(0);
  });

  // ---- 3b) corroborate the column exists in the grid view layout (REST) ----
  await test.step('Corroborate the picking-group field in the grid view layout', async () => {
    const layout = await pageGet(page, `/rest/api/documentView/${COCKPIT_WINDOW_ID}/layout?viewType=grid&viewDataType=grid`);
    const elements = layout.body.elements || [];
    const layoutFields = elements.flatMap((e) => (e.fields || []).map((f) => f.field));
    console.log(`[cockpit-pg] Grid layout fields: ${JSON.stringify(layoutFields)}`);
    expect(layoutFields, 'grid view layout exposes M_Warehouse_PickingGroup_ID').toContain('M_Warehouse_PickingGroup_ID');
  });

  // ---- 4) locate the GROUPED cockpit row (M_Warehouse_PickingGroup_ID = 1000002) ----
  const groupedRowId = await test.step('Locate the grouped cockpit row (picking group 1000002)', async () => {
    const cockpitRows = await pageGet(page, `/rest/api/documentView/${COCKPIT_WINDOW_ID}/${cockpitViewId}?firstRow=0&pageLength=200`);
    const rows = cockpitRows.body.result || [];
    const groupedRow = rows.find((r) =>
      fieldKey(r, 'M_Warehouse_PickingGroup_ID') === PICKING_GROUP_ID &&
      String((r.fieldsByName?.ProductValue || {}).value || '').includes(PRODUCT_VALUE_MARKER)
    );
    expect(groupedRow, `grouped cockpit row for the product (picking group ${PICKING_GROUP_ID})`).toBeTruthy();
    console.log(`[cockpit-pg] Grouped cockpit row id: ${groupedRow.id}`);
    return groupedRow.id;
  });

  // ---- 5) the crux: group-scoped jump returns BOTH in-group warehouses, excludes the ungrouped one ----
  await test.step('Jump from the grouped row and assert the group-scoped candidate set', async () => {
    const candidateViewId = await jumpToCandidates(page, cockpitViewId, groupedRowId);
    console.log(`[cockpit-pg] Grouped jump candidate view: ${candidateViewId}`);
    const warehouseIds = await readCandidateWarehouseIds(page, candidateViewId);
    const uniqueWarehouseIds = [...new Set(warehouseIds)].sort();
    console.log(`[cockpit-pg] Grouped jump candidate warehouses: ${JSON.stringify(uniqueWarehouseIds)}`);

    // BOTH in-group warehouses present ...
    for (const wh of GROUP_WAREHOUSE_IDS) {
      expect(warehouseIds, `in-group warehouse ${wh} present in grouped jump`).toContain(wh);
    }
    // ... and the ungrouped warehouse's candidate is ABSENT ...
    expect(warehouseIds, `ungrouped warehouse ${UNGROUPED_WAREHOUSE_ID} excluded from grouped jump`).not.toContain(UNGROUPED_WAREHOUSE_ID);
    // ... i.e. the candidate set is exactly the group's two warehouses.
    expect(uniqueWarehouseIds, 'grouped jump warehouse set').toEqual([...GROUP_WAREHOUSE_IDS].sort());
  });

  // ---- 6) if an ungrouped cockpit row for wh 540008 exists, jumping from it returns only that warehouse ----
  await test.step('Jump from the ungrouped row and assert the single-warehouse candidate set (if present)', async () => {
    const cockpitRows = await pageGet(page, `/rest/api/documentView/${COCKPIT_WINDOW_ID}/${cockpitViewId}?firstRow=0&pageLength=200`);
    const rows = cockpitRows.body.result || [];
    const ungroupedRow = rows.find((r) => {
      const grp = fieldKey(r, 'M_Warehouse_PickingGroup_ID');
      const wh = fieldKey(r, 'M_Warehouse_ID');
      const grouped = grp != null && grp !== '' && Number(grp) > 0;
      return !grouped && wh === UNGROUPED_WAREHOUSE_ID;
    });
    if (!ungroupedRow) {
      console.log('[cockpit-pg] No ungrouped cockpit row for warehouse 540008 rendered (demand-driven view) — skipping single-warehouse jump; the grouped jump is the primary proof.');
      return;
    }
    console.log(`[cockpit-pg] Ungrouped cockpit row id: ${ungroupedRow.id}`);
    const candidateViewId = await jumpToCandidates(page, cockpitViewId, ungroupedRow.id);
    const warehouseIds = await readCandidateWarehouseIds(page, candidateViewId);
    const uniqueWarehouseIds = [...new Set(warehouseIds)].sort();
    console.log(`[cockpit-pg] Ungrouped jump candidate warehouses: ${JSON.stringify(uniqueWarehouseIds)}`);
    expect(uniqueWarehouseIds, 'ungrouped jump returns only warehouse 540008').toEqual([UNGROUPED_WAREHOUSE_ID]);
    for (const wh of GROUP_WAREHOUSE_IDS) {
      expect(warehouseIds, `in-group warehouse ${wh} absent from ungrouped jump`).not.toContain(wh);
    }
  });
});
