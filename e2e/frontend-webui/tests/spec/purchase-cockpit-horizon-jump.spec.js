import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Purchase Cockpit "Jump to Purchase Candidates" — procurement-horizon bound.
 *
 * LOCAL validation ONLY. The Einkaufs-Cockpit window (AD_Window 542101, view
 * RV_PurchaseCockpit) is a non-core customization, so this spec has no home in core CI —
 * it is a one-off proof against a running substrate stack.
 *
 * FEATURE UNDER TEST: the jump WhereClause (AD_Ref_Table 542098) bounds the candidate list
 * to the selected row's procurement horizon (RV_PurchaseCockpit.Beschaffungshorizont), via
 * the per-row UI context token @Beschaffungshorizont@ — so a candidate whose
 * PurchaseDatePromised is beyond "now + horizon days" is not shown. horizon<=0 (or an
 * unresolved token) => no bound.
 *
 * The point of THIS test (beyond the SQL, already proven): confirm @Beschaffungshorizont@
 * actually RESOLVES as a context token in the live jump (the backing AD_Field is active but
 * IsDisplayed='N'). If it did NOT resolve it would default to -1 => no bound => the
 * beyond-horizon candidate would still appear, and this test would fail.
 *
 * SUBSTRATE PRE-CONDITIONS (product 2005599, marker S31234_P; picking group 1000002 =
 * warehouses 1000112+1000113; horizon = 24 days):
 *   - 9900001 (wh 1000112) / 9900002 (wh 1000113) / 9900005 (wh 1000113): PurchaseDatePromised
 *     in the past  -> WITHIN horizon -> must remain.
 *   - 9900004 (wh 1000112): PurchaseDatePromised = now()+60d -> BEYOND the 24d horizon -> must be excluded.
 *   - 9900003 (wh 540008, ungrouped): out of group -> excluded by the group scope (unrelated to horizon).
 */

const COCKPIT_WINDOW_ID = 542101;
const CANDIDATE_WINDOW_ID = 540375;
const PRODUCT_VALUE_MARKER = 'S31234_P';
const JUMP_PROCESS_ID = 'ADP_585624';
const GROUP_KEY = '1000002';

const WITHIN_HORIZON = ['9900001', '9900002', '9900005'];
const BEYOND_HORIZON = '9900004';
const OUT_OF_GROUP = '9900003';

const pagePost = async (page, url, body) =>
  page.evaluate(
    async ({ url, body }) => {
      const r = await fetch(url, { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json', Accept: 'application/json' }, body: JSON.stringify(body) });
      const t = await r.text(); let j = null; try { j = JSON.parse(t); } catch (e) {} return { status: r.status, body: j || t };
    },
    { url, body }
  );
const pageGet = async (page, url) =>
  page.evaluate(async (url) => {
    const r = await fetch(url, { credentials: 'include', headers: { Accept: 'application/json' } });
    const t = await r.text(); let j = null; try { j = JSON.parse(t); } catch (e) {} return { status: r.status, body: j || t };
  }, url);

test('Jump to Purchase Candidates is bounded by the procurement horizon (@Beschaffungshorizont@ resolves)', async ({ page }) => {
  test.setTimeout(120000);

  await test.step('Log in', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/login`);
    const loggedIn = await page.evaluate(async () => {
      const post = async (u, b) => { const r = await fetch(u, { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json', Accept: 'application/json' }, body: JSON.stringify(b) }); const t = await r.text(); let j = null; try { j = JSON.parse(t); } catch (e) {} return j || t; };
      const auth = await post('/rest/api/login/authenticate', { username: 'metasfresh', password: 'metasfresh' });
      const admin = (auth.roles || []).find((r) => /Admin/.test(r.caption)) || auth.roles[0];
      await post('/rest/api/login/loginComplete', admin);
      return (await (await fetch('/rest/api/login/isLoggedIn', { credentials: 'include' })).text()).trim();
    });
    expect(loggedIn).toBe('true');
  });

  const cockpitViewId = await test.step('Open the Purchase Cockpit (unfiltered)', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${COCKPIT_WINDOW_ID}`);
    await page.waitForURL(/viewId=/, { timeout: SLOW_ACTION_TIMEOUT });
    const defaultViewId = new URL(page.url()).searchParams.get('viewId');
    return page.evaluate(async ({ windowId, viewId }) => {
      const r = await fetch(`/rest/api/documentView/${windowId}/${viewId}/filter`, { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json', Accept: 'application/json' }, body: JSON.stringify({ filters: [] }) });
      const j = await r.json(); return j.viewId || viewId;
    }, { windowId: COCKPIT_WINDOW_ID, viewId: defaultViewId });
  });

  const cockpitRowId = await test.step('Locate the GROUPED product row (picking group 1000002)', async () => {
    const rows = await pageGet(page, `/rest/api/documentView/${COCKPIT_WINDOW_ID}/${cockpitViewId}?firstRow=0&pageLength=200`);
    const row = (rows.body.result || []).find((r) => {
      const pg = (r.fieldsByName?.M_Warehouse_PickingGroup_ID || {}).value;
      const isGroup = pg && String(pg.key ?? pg) === GROUP_KEY;
      const prod = String((r.fieldsByName?.ProductValue || {}).value || ((r.fieldsByName?.M_Product_ID || {}).value || {}).caption || '');
      return isGroup && prod.includes(PRODUCT_VALUE_MARKER);
    });
    expect(row, 'grouped cockpit row for the product').toBeTruthy();
    return row.id;
  });

  const candidateIds = await test.step('Jump to Purchase Candidates', async () => {
    const inst = await pagePost(page, `/rest/api/process/${JUMP_PROCESS_ID}`, { processId: JUMP_PROCESS_ID, viewId: cockpitViewId, viewDocumentIds: [cockpitRowId] });
    const start = await pageGet(page, `/rest/api/process/${JUMP_PROCESS_ID}/${inst.body.pinstanceId}/start`);
    const candViewId = start.body.action?.viewId;
    expect(candViewId, 'candidate view id from jump').toBeTruthy();
    const cand = await pageGet(page, `/rest/api/documentView/${CANDIDATE_WINDOW_ID}/${candViewId}?firstRow=0&pageLength=200`);
    const ids = (cand.body.result || []).map((r) => String(r.id));
    console.log(`[horizon-jump] candidate ids returned: ${JSON.stringify(ids)}`);
    return ids;
  });

  await test.step('Assert the horizon bound applied (beyond-horizon candidate excluded)', async () => {
    // in-horizon in-group candidates present
    for (const id of WITHIN_HORIZON) {
      expect(candidateIds, `within-horizon candidate ${id} should be present`).toContain(id);
    }
    // the 60-days-out candidate excluded — this ONLY happens if @Beschaffungshorizont@ resolved (=24)
    expect(candidateIds, `beyond-horizon candidate ${BEYOND_HORIZON} must be excluded by the horizon bound`).not.toContain(BEYOND_HORIZON);
    // out-of-group candidate excluded by the group scope (sanity)
    expect(candidateIds, `out-of-group candidate ${OUT_OF_GROUP} must be excluded by the group scope`).not.toContain(OUT_OF_GROUP);
  });
});
