import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { MasterWindowPage } from '../utils/pages/MasterWindowPage';
import { getRecordData } from '../utils/WebAPIValidation';
import { FRONTEND_BASE_URL, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';

/**
 * Cost Revaluation window ("Kosten Neubewertung") — gh31743: surface the document's Posted status
 * and its posting-error link, read-only, in a new "posted" element group above the org+client group.
 * AD_Window_ID = 541568, header tab AD_Tab_ID = 546464 (table M_CostRevaluation).
 *
 * Migration 5820740 adds, purely additively (no DDL, no behaviour change):
 *   * AD_UI_ElementGroup "posted" (right AD_UI_Column, directly above the trailing org+client group),
 *     holding two read-only fields in order:
 *       - Posted                (AD_Column 584135) DisplayLogic @Processed/N@='Y'          — shown once processed;
 *                               ALSO a list/grid column (IsDisplayedGrid='Y', SeqNoGrid 65, Org kept last).
 *       - PostingError_Issue_ID (AD_Column 584136) DisplayLogic @PostingError_Issue_ID/0@>0 — shown only on a
 *                               posting error; grid-hidden.
 *
 * ---------------------------------------------------------------------------------------------------
 * TEST-LAYER RATIONALE (metasfresh-test-integrity / metasfresh-window-design-rules)
 * ---------------------------------------------------------------------------------------------------
 * This is a desktop-WebUI layout/visibility change, so the faithful primary layer is a frontend-webui
 * Playwright E2E. What it proves, all through language-invariant identifiers, run in en_US + de_DE:
 *
 *   (A) UI — Posted is a NEW grid column of the 541568 list view  (th[data-testid="column-Posted"]),
 *       and PostingError_Issue_ID stays grid-hidden.                                      [AC10]
 *   (B) UI — on a NEW (drafted, Processed='N') record, neither field renders on the detail form
 *       (.form-field-Posted / .form-field-PostingError_Issue_ID are absent from the DOM), i.e. the
 *       DisplayLogic gates hold: nothing is surfaced prematurely.                         [AC3/AC4 gate]
 *   (C) record data (the very payload the form renders from — getRecordData().fieldsByName) — both
 *       fields exist on the tab and are read-only, and are display-gated off on the drafted record:
 *       readonly=true, displayed=false.                                                   [AC3/AC4 read-only]
 *   (D) window layout — the "posted" element group holds Posted then PostingError_Issue_ID and sits
 *       directly above the org+client group in the right column.                          [AC1/AC2 placement]
 *
 * WHY the "posted-group-RENDERED-on-a-processed-doc" and the error-link paths are covered structurally,
 * not by driving them on screen:
 *   Both DisplayLogics only fire on a *completed* M_CostRevaluation (Processed='Y'), and the error link
 *   additionally needs a *failed* posting (PostingError_Issue_ID > 0). Completing an M_CostRevaluation is
 *   a real costing document flow: CostRevaluationDocumentHandler.completeIt requires active lines, which
 *   CostRevaluationService seeds from stocked products carrying current costs for the target cost element
 *   + acct schema (see switchToMovingAverageInvoice.feature). None of that costing masterdata is exposed
 *   by the frontend-testing masterdata API (Backend.createMasterdata), and reaching into the DB to fake a
 *   processed/errored row is forbidden for e2e data (e2e/CLAUDE.md) and would not exist on core CI anyway.
 *   Fabricating a processed state the real system can't produce here would be an unfaithful test
 *   (CLAUDE.md "a feature isn't done without a real-life-aligned test flow"), so the rendered-when-processed
 *   appearance + read-only is instead pinned by (C) the read-only flags on the field payload and (D) the
 *   layout placement — together with the window-designer render check (de+en) and the DB verification the
 *   migration ships with. A live UAT on the target instance (which carries real processed cost
 *   revaluations) remains the issue's on-a-processed-doc acceptance evidence.
 */

const COST_REVALUATION_WINDOW_ID = 541568;

// Language-invariant selectors.
// Grid column header: TH carries data-testid="column-<ColumnName>" (language-independent).
const POSTED_GRID_COLUMN = 'th[data-testid="column-Posted"]';
const POSTING_ERROR_GRID_COLUMN = 'th[data-testid="column-PostingError_Issue_ID"]';
// Detail-form field wrapper: RawWidget renders `.form-field-<ColumnName>`; a DisplayLogic-hidden field
// is dropped from the DOM entirely, so its wrapper is ABSENT (count 0), not merely not-visible.
const POSTED_FORM_WRAPPER = '.form-field-Posted';
const POSTING_ERROR_FORM_WRAPPER = '.form-field-PostingError_Issue_ID';

const WEBAPI_BASE_URL = process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

/** Flatten a window-layout element group to its ordered list of ColumnNames. */
function groupFieldNames(group) {
  const names = [];
  for (const line of group.elementsLine || []) {
    for (const el of line.elements || []) {
      for (const f of el.fields || []) {
        if (f.field) names.push(f.field);
      }
    }
  }
  return names;
}

test.describe('Cost Revaluation window — Posted status + posting-error surfaced read-only (gh31743)', () => {
  testCases.forEach(({ language, label }) => {
    test(`Posted status + posting-error are surfaced read-only on window 541568 (${label})`, async ({
      page,
    }) => {
      allure.epic('E0226: Costing');
      allure.tag('F1500: Costing');
      allure.tag('F1500'); // Standalone tag for code-only filtering
      allure.story(
        'Cost Revaluation — Posted status is a grid column and, with PostingError_Issue_ID, a read-only "posted" group above org+client'
      );
      allure.severity('normal');
      allure.description(`
## Cost Revaluation (AD_Window ${COST_REVALUATION_WINDOW_ID}) — Posted status + posting-error surfaced read-only

gh31743. Verifies that migration 5820740 surfaces, read-only:
- **Posted** as a NEW **grid column** of the list view (and grid-hidden **PostingError_Issue_ID**),
- both fields **display-gated off** on a drafted (not-yet-processed) record's detail form,
- both fields **read-only** on the record payload the form renders from,
- a **"posted" element group** holding Posted then PostingError_Issue_ID directly above org+client.

Language under test: ${language}.
      `);

      // 1. Provision a login user of the given language and log in.
      const masterdata = await Backend.createMasterdata({
        request: { login: { user: { language } } },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);
      // URL-based readiness check: STOMP keeps the socket active, so a networkidle-based
      // dashboard check (DashboardPage.expectVisible) never settles here.
      await LoginPage.expectLoggedIn();

      // 2. (A) List view: Posted is a grid column; PostingError_Issue_ID is NOT.
      await test.step('List view: Posted is a grid column, PostingError is grid-hidden', async () => {
        await MasterWindowPage.goto(COST_REVALUATION_WINDOW_ID);

        // Header renders independently of whether any data rows exist.
        await expect(page.locator(POSTED_GRID_COLUMN)).toBeVisible({
          timeout: VERY_SLOW_ACTION_TIMEOUT,
        });
        await expect(page.locator(POSTING_ERROR_GRID_COLUMN)).toHaveCount(0);
        console.log('[PASS] Posted grid column present; PostingError grid-hidden');
      });

      // 3. Open a NEW Cost Revaluation record (auto-saved as drafted, Processed='N').
      await page.goto(`${FRONTEND_BASE_URL}/window/${COST_REVALUATION_WINDOW_ID}/NEW`);
      await page.waitForURL(new RegExp(`/window/${COST_REVALUATION_WINDOW_ID}/\\d+`), {
        timeout: VERY_SLOW_ACTION_TIMEOUT,
      });
      const recordId = page.url().split('/').pop().split('?')[0];
      console.log(`[INFO] new M_CostRevaluation record id = ${recordId}`);

      // The RevaluationSource field is always shown → use it as the "detail form is loaded" anchor.
      await page
        .locator('.form-field-RevaluationSource')
        .waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

      // 4. (B) Detail form of the drafted record: neither field is rendered (DisplayLogic gate holds).
      await test.step('Drafted record: Posted & PostingError not rendered (DisplayLogic gate)', async () => {
        await expect(page.locator(POSTED_FORM_WRAPPER)).toHaveCount(0);
        await expect(page.locator(POSTING_ERROR_FORM_WRAPPER)).toHaveCount(0);
        console.log('[PASS] Both fields display-gated off on the drafted detail form');
      });

      // 5. (C) Record payload: both fields exist on the tab, are read-only, and are display-gated off.
      await test.step('Record payload: Posted & PostingError are read-only and gated off', async () => {
        const record = await getRecordData(String(COST_REVALUATION_WINDOW_ID), recordId);
        const fields = record.fieldsByName || {};

        const posted = fields['Posted'];
        expect(posted, 'Posted must be present on the M_CostRevaluation tab').toBeTruthy();
        expect(posted.readonly, 'Posted must be read-only').toBe(true);
        expect(
          posted.displayed,
          "Posted must be display-gated off on a drafted (Processed='N') record"
        ).toBe(false);

        const postingError = fields['PostingError_Issue_ID'];
        expect(
          postingError,
          'PostingError_Issue_ID must be present on the M_CostRevaluation tab'
        ).toBeTruthy();
        expect(postingError.readonly, 'PostingError_Issue_ID must be read-only').toBe(true);
        expect(
          postingError.displayed,
          'PostingError_Issue_ID must be display-gated off when no posting error exists'
        ).toBe(false);
        console.log('[PASS] Posted & PostingError read-only=true, displayed=false on the payload');
      });

      // 6. (D) Window layout: the "posted" group holds Posted then PostingError_Issue_ID and sits
      //    directly above the org+client group in the same (right) column.
      await test.step('Layout: "posted" group holds both fields directly above org+client', async () => {
        const response = await page.request.get(
          `${WEBAPI_BASE_URL}/window/${COST_REVALUATION_WINDOW_ID}/layout`
        );
        expect(response.ok(), 'window layout must load').toBe(true);
        const layout = await response.json();

        let postedGroup = null;
        let postedGroupIdx = -1;
        let orgGroupIdx = -1;

        // Find the posted group's column, then resolve the org+client group's index within that
        // SAME column — one order-independent pass per column.
        for (const section of layout.sections || []) {
          for (const column of section.columns || []) {
            const groups = column.elementGroups || [];
            const pIdx = groups.findIndex((g) => {
              const names = groupFieldNames(g);
              return names.includes('Posted') && names.includes('PostingError_Issue_ID');
            });
            if (pIdx === -1) continue;
            postedGroup = groups[pIdx];
            postedGroupIdx = pIdx;
            orgGroupIdx = groups.findIndex((g) => groupFieldNames(g).includes('AD_Org_ID'));
          }
        }

        expect(postedGroup, 'a "posted" group holding Posted + PostingError_Issue_ID must exist').toBeTruthy();
        // Order within the group: Posted first, then PostingError_Issue_ID.
        expect(groupFieldNames(postedGroup)).toEqual(['Posted', 'PostingError_Issue_ID']);
        // Placement: directly above the org+client group in the same column.
        expect(orgGroupIdx, 'org+client group must be in the same column as the posted group').toBeGreaterThan(-1);
        expect(
          orgGroupIdx,
          'the "posted" group must sit directly above the org+client group'
        ).toBe(postedGroupIdx + 1);
        console.log('[PASS] "posted" group [Posted, PostingError_Issue_ID] directly above org+client');
      });

      console.log(`[PASS] gh31743 Cost Revaluation Posted-status surfacing verified (${label})`);
    });
  });
});
