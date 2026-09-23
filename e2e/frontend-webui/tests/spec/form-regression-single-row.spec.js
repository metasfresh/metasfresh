import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';
import { waitForRecordSaved, getFieldData } from '../utils/WebAPIValidation';
import { createMasterdata, gotoOrderList, createNewOrder, selectOrderCustomer } from '../utils/OrderLineHarness';

/**
 * Form-regression guard (BF-F2, TC6) — proves the grid-only scoping of this milestone's changes
 * by construction AND by a real-flow assertion, not by claim alone.
 *
 * BY CONSTRUCTION (code audit): every production file this milestone touched is grid-table-layer
 * only — `frontend/src/components/table/{Table,TableCell,TableHeader,TableRow}.js`,
 * `frontend/src/utils/{columnWidthStorage,tableHelpers}.js`, and `frontend/src/assets/css/table.scss`.
 * None of them is `RawWidget.js` / `RawLookup.js` — the SHARED widget components a single-row
 * detail (Master) form actually renders (`frontend/src/components/widget/`). A single-row form
 * field's `<input>` is never a descendant of `Table`/`TableRow`/`TableCell`, so the object-valued
 * type-guard (grid `Tab`/`Enter` handlers), the numpad-0 activation gate, the layout-jump CSS
 * (scoped to the in-grid editor box), and the combobox width floor (grid `td-*` header/cell
 * classes) have no code path into a form field at all.
 *
 * BY REAL-FLOW ASSERTION (this spec): drives the order HEADER (a single-row/detail form, not a
 * grid) exactly as a user would — type into an empty field, press Enter, type into an
 * already-filled field — and asserts the pre-existing, unchanged RawWidget behaviour
 * (`RawWidget.handleKeyDown`, untouched by this milestone): Enter commits the value via a PATCH
 * and calls `event.preventDefault()`, so focus stays on the SAME field — there is no grid-style
 * cross-cell "advance" (that model does not exist for single-row forms, before or after this
 * milestone), and typing edits in place rather than replacing the field outright.
 *
 * Features tested:
 * - F5010: Order Lines Grid (this milestone's changes stay scoped to it — proven negatively here)
 */
const HEADER_TEXT_FIELD = 'POReference';

test.describe('Single-row detail form regression guard', () => {
  test('A single-row form field: Enter commits without cross-cell advance; typing into a filled field edits in place (AC10, TC6)', async ({
    page,
  }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('Form-regression guard — single-row form unchanged');
    allure.severity('critical');
    allure.description(`
## TC6 — Single-row detail form unchanged (form regression)

1. Open the Sales Order HEADER (a single-row/detail form, not a grid).
2. Type into an empty text field and press Enter — the value must commit (PATCH lands) and
   focus must stay on the SAME field (no grid-style cross-cell advance; single-row forms have no
   such nav model).
3. Type into the now-filled field again — the edit must apply in place (append), not replace/
   clear the field.

Grounded in a code audit (see the header comment in this file): every file this milestone
touched is grid-table-layer only (\`Table\`/\`TableRow\`/\`TableCell\`/\`TableHeader\` +
\`columnWidthStorage\`/\`tableHelpers\` + \`table.scss\`) — \`RawWidget\`/\`RawLookup\`, which a
single-row form actually renders, were not touched.
    `);

    test.setTimeout(120000);

    const masterdata = await createMasterdata();
    allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await gotoOrderList();
    const recordId = await createNewOrder();
    // Fills mandatory header fields (BPartner/Warehouse/Currency/etc. auto-derive from the
    // customer) so the record is VALID and SAVED — required before waitForRecordSaved below
    // (per this suite's "Record Validity Check" rule); irrelevant to what this test asserts.
    await selectOrderCustomer(recordId, masterdata.bpartners.CUSTOMER1.bpartnerCode);

    const headerField = page.locator(`.form-field-${HEADER_TEXT_FIELD} input.input-field`).first();
    await headerField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

    const firstValue = `TC6-${Date.now()}`;

    await test.step('Typing into an empty field and pressing Enter commits it, focus stays on the same field', async () => {
      await headerField.click();
      await headerField.fill(firstValue);

      const patchResponse = page.waitForResponse(
        (response) =>
          response.url().includes(`/window/${SALES_ORDER_WINDOW_ID}/${recordId}`) &&
          response.request().method() === 'PATCH',
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page.keyboard.press('Enter');
      await patchResponse;

      const activeElementInfo = await page.evaluate(() => {
        const el = document.activeElement;
        return { tag: el?.tagName, cls: el?.className };
      });
      const isSameFieldFocused = await headerField.evaluate(
        (el, info) => el.tagName === info.tag && el.className === info.cls,
        activeElementInfo
      );
      console.log(`[INFO] active element after Enter: ${JSON.stringify(activeElementInfo)}`);

      expect(
        isSameFieldFocused,
        'Enter on a single-row form field must NOT advance focus to a different field (no grid-style cross-cell advance exists here)'
      ).toBe(true);

      await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, { maxRetries: 20, retryDelayMs: 1000 });
      const fieldData = await getFieldData(SALES_ORDER_WINDOW_ID, recordId, HEADER_TEXT_FIELD);
      expect(fieldData.value, 'the typed value must be committed to the backend, unchanged').toBe(firstValue);
    });

    await test.step('Typing into the already-filled field edits in place (append), not a full replace', async () => {
      await headerField.click();
      await page.keyboard.press('End');
      await page.keyboard.type('-APPEND');

      const domValue = await headerField.inputValue();
      console.log(`[INFO] field value after typing into a filled field: "${domValue}"`);

      expect(
        domValue,
        'typing into an already-filled field must edit in place (append), not clear/replace it'
      ).toBe(`${firstValue}-APPEND`);

      const patchResponse = page.waitForResponse(
        (response) =>
          response.url().includes(`/window/${SALES_ORDER_WINDOW_ID}/${recordId}`) &&
          response.request().method() === 'PATCH',
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      await page.keyboard.press('Enter');
      await patchResponse;

      await waitForRecordSaved(SALES_ORDER_WINDOW_ID, recordId, { maxRetries: 20, retryDelayMs: 1000 });
      const fieldData = await getFieldData(SALES_ORDER_WINDOW_ID, recordId, HEADER_TEXT_FIELD);
      expect(fieldData.value, 'the appended value must be committed to the backend').toBe(`${firstValue}-APPEND`);
    });
  });
});
