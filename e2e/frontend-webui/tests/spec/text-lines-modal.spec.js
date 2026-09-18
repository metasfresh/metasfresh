import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SalesOrderPage } from '../utils/pages/SalesOrderPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL, getTabRows } from '../utils/WebAPIValidation';
import { SALES_ORDER_WINDOW_ID } from '../utils/WindowIds';

/**
 * The text-lines modal, opened from a button on the sales order line tab: a merged list of the
 * order's article lines (read-only) and free-text lines (inline-editable), with quick actions to
 * insert, move and delete a text line.
 *
 * Selectors are language-independent throughout: the launcher button is resolved by its
 * `internalName` via the same top-actions endpoint the frontend itself calls (never by its
 * localized caption), grid rows by the row id the view assigns (`table-row-<id>`, where an
 * article row's id is `A<C_OrderLine_ID>` and a text row's is `T<C_Doc_TextLine_ID>`), quick
 * actions by their AD_Process internal name (`quick-action-<internalName>`), and a text line's
 * scope by its reference-list code (`D` = whole document, `F` = belongs with the following lines),
 * which the view reports as the field's `key` and the scope dropdown carries as `option-<code>`.
 * The grid cell itself paints only the localized caption, so where a rendered scope is asserted the
 * expected caption is taken from the very option that was clicked, never hardcoded.
 *
 * Most assertions below read the row back through the view's own REST endpoint after the UI
 * action that produced it -- never the widget the click just touched -- so a change that renders
 * in the grid but never reaches the server would fail these tests. The scope step is the deliberate
 * exception: what it is pinning is that the dropdown the user opens is *populated at all* and that
 * picking a value paints it, which only the rendered grid can answer, so it asserts against the DOM
 * and proves persistence by reopening the modal rather than by querying the endpoint.
 */

const ORDER_LINE_TAB_ID = 'AD_Tab-187';
const DOC_TEXT_LINES_WINDOW_ID = 'docTextLines';
const LAUNCHER_INTERNAL_NAME = 'WEBUI_Order_DocTextLines_Launcher';

const QUICK_ACTION = {
  insertAbove: 'WEBUI_DocTextLines_InsertAbove',
  delete: 'WEBUI_DocTextLines_Delete',
  moveUp: 'WEBUI_DocTextLines_MoveUp',
  moveDown: 'WEBUI_DocTextLines_MoveDown',
};

const SCOPE = {
  wholeDocument: 'D',
  followingLines: 'F',
};

/**
 * Opens the modal from the order line tab's top action, resolving the button by the
 * `internalName` the topActions endpoint reports for it -- never by its (localized) caption.
 * Returns the id of the view the modal just created; every reopen of the modal creates a new one.
 */
async function openTextLinesModal(page, orderId) {
  const topActionsResponse = await page.request.get(
    `${WEBAPI_BASE_URL}/window/${SALES_ORDER_WINDOW_ID}/${orderId}/${ORDER_LINE_TAB_ID}/topActions`
  );
  expect(topActionsResponse.ok()).toBeTruthy();
  const { actions } = await topActionsResponse.json();
  const launcherIndex = actions.findIndex((action) => action.internalName === LAUNCHER_INTERNAL_NAME);
  expect(launcherIndex, 'the text-lines launcher must be a top action of the order line tab').toBeGreaterThanOrEqual(0);

  // TableFilter.js renders exactly the topActions array (in that order) as plain buttons with no
  // data-testid, but each one DOES carry a (possibly empty) title attribute -- the one DOM trait
  // that distinguishes them from their neighbours in the same toolbar, which carry neither: the
  // batch-entry toggle has a data-testid instead, and the "Add new" button has neither.
  const topActionButtons = page.locator('.filter-panel-buttons button[title]');
  await expect(topActionButtons).toHaveCount(actions.length);

  const [viewResponse] = await Promise.all([
    page.waitForResponse(
      (response) =>
        response.url().includes(`/documentView/${DOC_TEXT_LINES_WINDOW_ID}/`) &&
        !response.url().includes('/layout') &&
        response.request().method() === 'GET',
      { timeout: SLOW_ACTION_TIMEOUT }
    ),
    topActionButtons.nth(launcherIndex).click(),
  ]);

  return new URL(viewResponse.url()).pathname.split('/').pop();
}

/** Closes the modal via its own Done button -- every edit already auto-saved on entry. */
async function closeTextLinesModal(page) {
  await page.getByTestId('modal-done').click();
}

/** Reads the modal's current rows straight from the view's REST endpoint -- the persisted state, not the DOM. */
async function getTextLinesRows(page, viewId) {
  const response = await page.request.get(
    `${WEBAPI_BASE_URL}/documentView/${DOC_TEXT_LINES_WINDOW_ID}/${viewId}?firstRow=0&pageLength=50`
  );
  expect(response.ok()).toBeTruthy();
  const body = await response.json();
  return body.result.map((row) => ({
    id: row.id,
    line: Number(row.fieldsByName.line?.value),
    textLine: row.fieldsByName.textLine?.value,
    scope: row.fieldsByName.textLineScope?.value?.key ?? null,
  }));
}

/**
 * Reads the row order as actually painted on screen, in DOM order -- the same identifiers
 * `getTextLinesRows` reads from the REST endpoint, but taken from the rendered grid itself, so an
 * ordering claim can be tied to what a reviewer watching the recording would see move.
 */
async function getRenderedRowIds(page) {
  const testids = await page.locator('[data-testid^="table-row-"]').evaluateAll((elements) =>
    elements.map((element) => element.getAttribute('data-testid'))
  );
  return testids.filter((testid) => /^table-row-[AT]\d+$/.test(testid)).map((testid) => testid.replace('table-row-', ''));
}

/** Selects a row in the modal by its view row id, tolerating a row that is already selected. */
async function selectRow(page, rowId) {
  const row = page.getByTestId(`table-row-${rowId}`);
  // a click toggles selection, so a row that already carries the CSS class from an earlier step
  // must be deselected first -- otherwise this call's own click would be the one that clears it,
  // and the caller would go on to invoke a quick action against no selection at all
  const alreadySelected = ((await row.getAttribute('class')) ?? '').includes('row-selected');
  if (alreadySelected) {
    await row.click();
    await expect(row).not.toHaveClass(/row-selected/);
  }
  await Promise.all([
    page.waitForResponse((r) => r.url().includes('/quickActions') && r.request().method() === 'POST', {
      timeout: SLOW_ACTION_TIMEOUT,
    }),
    row.click(),
  ]);
  await expect(row).toHaveClass(/row-selected/);
}

/**
 * Opens the row's quick-actions and returns the internal names of every action currently offered
 * for the selection -- the dropdown lists exactly the actions the view judged applicable, whether
 * or not one of them also renders as the always-visible primary button.
 */
async function getOfferedQuickActions(page) {
  await page.getByTestId('quick-action-dropdown-toggle').click();
  const items = page.locator('.quick-actions-item');
  const names = await items.evaluateAll((elements) =>
    elements.map((element) => element.getAttribute('data-testid').replace('quick-action-', ''))
  );
  // clicking any dropdown item closes it again; since we only read here, close it explicitly by
  // clicking a neutral element -- never Escape, which cancels the whole modal, not just the dropdown
  await page.locator('.panel-modal-header-title').click();
  return names;
}

/**
 * Awaits the view's own refresh (the GET the frontend issues once the process it started has
 * actually run) rather than a fixed delay, so a caller never reads the view before the action it
 * just triggered has taken effect server-side.
 */
async function waitForViewRefresh(page, viewId, triggerAction) {
  const [response] = await Promise.all([
    page.waitForResponse(
      (r) =>
        r.url().includes(`/documentView/${DOC_TEXT_LINES_WINDOW_ID}/${viewId}`) &&
        !r.url().includes('/layout') &&
        r.request().method() === 'GET',
      { timeout: SLOW_ACTION_TIMEOUT }
    ),
    triggerAction(),
  ]);
  return response;
}

/** Invokes one quick action on the currently selected row via the dropdown, by its internal name. */
async function invokeQuickAction(page, viewId, internalName) {
  await page.getByTestId('quick-action-dropdown-toggle').click();
  await waitForViewRefresh(page, viewId, () => page.getByTestId(`quick-action-${internalName}`).click());
}

/** A text row's scope cell, as painted in the grid. */
function scopeCellOf(page, rowId) {
  return page.getByTestId(`table-row-${rowId}`).locator('[data-cy="cell-textLineScope"]');
}

/**
 * Opens a text row's scope cell for editing and returns the dropdown's options as a
 * `{ <reference-list code>: <rendered caption> }` map, read from the list the browser actually
 * painted. Leaves the dropdown open so the caller can pick one of the options it just read.
 *
 * The options come from a `.../edit/textLineScope/dropdown` GET the view has to answer itself; a
 * view that cannot answer it yields an empty list here rather than an error the user would see, so
 * the caller asserting on this map is what makes that failure visible.
 *
 * Only the entries the BACKEND supplied are returned. `RawList` appends one entry of its own to
 * every non-mandatory list widget -- the "clear value" row, which carries `key: null` and therefore
 * renders as `option-null` -- and that entry is a property of the widget, not of the reference list,
 * so counting it would make this map describe the frontend rather than the view's answer. It cannot
 * mask an unanswered dropdown either: `RawList` only builds the list (clear row included) once the
 * backend list is non-empty, so a view that cannot answer yields no options at all.
 */
async function openScopeDropdown(page, rowId) {
  const scopeCell = scopeCellOf(page, rowId);
  await scopeCell.dblclick();

  // the list renders in a portal pinned to the window, not inside the cell, so it is located on the
  // page; the toggle that opens it IS inside the cell
  const dropdownList = page.locator('.input-dropdown-list');
  try {
    // entering edit mode focuses the widget, which requests and opens the list by itself
    await dropdownList.waitFor({ state: 'visible', timeout: 2000 });
  } catch (notOpenedOnFocus) {
    await scopeCell.locator('.input-dropdown-container').click();
    await dropdownList.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  }

  return dropdownList.locator('[data-testid^="option-"]').evaluateAll((elements) =>
    Object.fromEntries(
      elements
        .map((element) => [
          element.getAttribute('data-testid').replace('option-', ''),
          element.textContent.trim(),
        ])
        .filter(([key]) => key !== 'null')
    )
  );
}

/**
 * Picks one option out of the scope dropdown left open by {@link openScopeDropdown}, waits for the
 * edit to reach the server, and takes the cell back out of edit mode so its painted value can be
 * read -- clicking the modal's own title, never Escape, which would cancel the whole modal.
 */
async function selectOpenScopeOption(page, { viewId, scopeKey }) {
  await Promise.all([
    page.waitForResponse(
      (response) =>
        response.url().includes(`/documentView/${DOC_TEXT_LINES_WINDOW_ID}/${viewId}/`) &&
        response.url().endsWith('/edit') &&
        response.request().method() === 'PATCH',
      { timeout: SLOW_ACTION_TIMEOUT }
    ),
    page.locator(`.input-dropdown-list [data-testid="option-${scopeKey}"]`).click(),
  ]);

  await page.locator('.panel-modal-header-title').click();
}

/**
 * Types into a text row's own text cell by opening its inline editor (a double-click on the cell)
 * and typing real keystrokes into it -- replacing whatever the cell already held.
 *
 * `text` may contain a literal newline (`\n`), which is sent as a real Shift+Enter keystroke
 * rather than the plain Enter that would otherwise submit the field.
 */
async function typeIntoTextLineRow(page, { rowId, text, viewId }) {
  const anchorCell = page.getByTestId(`table-row-${rowId}`).locator('[data-cy="cell-textLine"]');
  await anchorCell.dblclick();

  const editor = page.locator('textarea.input-field');
  await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // a double-click only selects a word, not the whole (possibly already non-empty) field -- clear
  // it first so re-editing a row replaces its value rather than inserting alongside it
  await editor.selectText();
  await page.keyboard.press('Delete');

  const lines = text.split('\n');
  for (let i = 0; i < lines.length; i++) {
    if (i > 0) {
      await page.keyboard.press('Shift+Enter');
    }
    await editor.pressSequentially(lines[i]);
  }

  await Promise.all([
    page.waitForResponse(
      (response) =>
        response.url().includes(`/documentView/${DOC_TEXT_LINES_WINDOW_ID}/${viewId}/`) &&
        response.url().endsWith('/edit') &&
        response.request().method() === 'PATCH',
      { timeout: SLOW_ACTION_TIMEOUT }
    ),
    page.locator('.panel-modal-header-title').click(),
  ]);
}

test.describe('Sales order text lines modal', () => {
  test('add, position, move and delete a text line among article lines', async ({ page }) => {
    allure.epic('E0100: Sales');
    allure.tag('F00144: Free Text Above Order Lines');
    allure.tag('F00144');
    allure.story('Text lines modal: insert, move, edit, change scope, delete');
    allure.severity('critical');

    allure.description(`
Drives the text-lines modal end to end from the sales order line tab:
1. On an order with no lines at all, the add action creates a text line whose scope defaults to
   "whole document" -- nothing stands above it.
2. Adding the order's article lines afterwards leaves that text line standing above both of them,
   its scope unchanged.
3. Selecting an article line and inserting above it places a new text line between two article
   lines without renumbering either one; its default scope is "belongs with the following lines",
   since an article line stands above it.
4. Moving that line up past the preceding article line, then back down, does not recompute its
   stored scope, and round-trips it to exactly where it started.
5. Selecting the last row in the list -- an article line -- offers only the insert-above action;
   moving the text line itself past the last article line, so that it becomes the last row, makes
   move-down (but not insert-above/delete/move-up) stop being offered on it.
6. Editing a text line's content, then deleting it, leaves every article line and every other text
   line unchanged.
7. Opening the surviving line's scope dropdown offers both scopes, and picking the other one paints
   it in the grid and survives a reopen of the modal.
    `);

    test.setTimeout(180000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US', firstname: 'text', lastname: 'lines' } },
        bpartners: {
          CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
        },
        products: {
          Product1: { name: 'Article One', type: 'Item', prices: [{ price: 10.0, currencyCode: 'EUR' }] },
          Product2: { name: 'Article Two', type: 'Item', prices: [{ price: 20.0, currencyCode: 'EUR' }] },
        },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const orderId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    let textLineOnEmptyOrderId;
    await test.step('On an order with no lines at all, invoke add', async () => {
      const viewId = await openTextLinesModal(page, orderId);

      const rowsBefore = await getTextLinesRows(page, viewId);
      expect(rowsBefore).toHaveLength(0);

      // with the view empty, no row can be selected, and the only action -- insert-above --
      // renders directly as the primary quick-action button
      await waitForViewRefresh(page, viewId, () => page.getByTestId('quick-action-button').click());

      const rows = await getTextLinesRows(page, viewId);
      expect(rows).toHaveLength(1);
      expect(rows[0].scope).toBe(SCOPE.wholeDocument);
      textLineOnEmptyOrderId = rows[0].id;

      await closeTextLinesModal(page);
    });

    let orderLine1Id;
    let orderLine2Id;
    await test.step('Add the two article lines', async () => {
      await SalesOrderPage.addOrderLine({
        product: masterdata.products.Product1.productCode,
        quantity: '5',
        recordId: orderId,
      });
      await SalesOrderPage.addOrderLine({
        product: masterdata.products.Product2.productCode,
        quantity: '3',
        recordId: orderId,
      });

      const orderLines = (await getTabRows(SALES_ORDER_WINDOW_ID, orderId, ORDER_LINE_TAB_ID)).sort(
        (a, b) => a.fieldsByName.Line.value - b.fieldsByName.Line.value
      );
      expect(orderLines).toHaveLength(2);
      [orderLine1Id, orderLine2Id] = orderLines.map((row) => row.rowId);
    });

    let viewId;
    let textLineBetweenId;
    await test.step('Reopen the modal: the text line still stands above both article lines', async () => {
      viewId = await openTextLinesModal(page, orderId);

      const rows = await getTextLinesRows(page, viewId);
      expect(rows.map((r) => r.id)).toEqual([
        textLineOnEmptyOrderId,
        `A${orderLine1Id}`,
        `A${orderLine2Id}`,
      ]);
      expect(rows[0].scope).toBe(SCOPE.wholeDocument);

      // article rows are shown read-only, for orientation only -- their positional/quantitative
      // columns are never in edit mode, unlike a text row's own text/scope cells
      for (const cy of ['cell-line', 'cell-product', 'cell-qty']) {
        const cellClass = await page
          .getByTestId(`table-row-A${orderLine2Id}`)
          .locator(`[data-cy="${cy}"]`)
          .getAttribute('class');
        expect(cellClass).toContain('cell-disabled');
      }
    });

    await test.step('Insert a text line above the second article line', async () => {
      await selectRow(page, `A${orderLine2Id}`);
      // an article row only ever offers insert-above -- it renders directly as the primary button
      await waitForViewRefresh(page, viewId, () => page.getByTestId('quick-action-button').click());

      const rows = await getTextLinesRows(page, viewId);
      expect(rows.map((r) => r.id)).toEqual([
        textLineOnEmptyOrderId,
        `A${orderLine1Id}`,
        expect.stringMatching(/^T\d+$/),
        `A${orderLine2Id}`,
      ]);
      textLineBetweenId = rows[2].id;

      // neither article line was renumbered
      expect(rows.find((r) => r.id === `A${orderLine1Id}`).line).toBe(10);
      expect(rows.find((r) => r.id === `A${orderLine2Id}`).line).toBe(20);
      // an article line stands above the new one, so it defaults to "belongs with the following lines"
      expect(rows[2].scope).toBe(SCOPE.followingLines);
    });

    await test.step('Type text into the new row', async () => {
      await typeIntoTextLineRow(page, {
        rowId: textLineBetweenId,
        text: 'Sortimentsware',
        viewId,
      });

      const rows = await getTextLinesRows(page, viewId);
      expect(rows.find((r) => r.id === textLineBetweenId).textLine).toBe('Sortimentsware');
    });

    await test.step('Move the new line up past the preceding article line: its scope does not change', async () => {
      await selectRow(page, textLineBetweenId);
      await invokeQuickAction(page, viewId, QUICK_ACTION.moveUp);

      const expectedOrder = [textLineOnEmptyOrderId, textLineBetweenId, `A${orderLine1Id}`, `A${orderLine2Id}`];
      const rows = await getTextLinesRows(page, viewId);
      expect(rows.map((r) => r.id)).toEqual(expectedOrder);
      // this is the point the move visibly reorders the list -- tie the claim to what is actually
      // painted on screen, not only to the REST endpoint the assertions above already read
      expect(await getRenderedRowIds(page)).toEqual(expectedOrder);
      // moving does not recompute the stored scope -- an article line no longer precedes it, but
      // the naive "no article above -> whole document" default is not re-applied
      expect(rows.find((r) => r.id === textLineBetweenId).scope).toBe(SCOPE.followingLines);
      // neither article line's own position was touched by the move
      expect(rows.find((r) => r.id === `A${orderLine1Id}`).line).toBe(10);
      expect(rows.find((r) => r.id === `A${orderLine2Id}`).line).toBe(20);
    });

    await test.step('Move it back down past the same article line: the round trip lands it exactly where it started', async () => {
      await selectRow(page, textLineBetweenId);
      await invokeQuickAction(page, viewId, QUICK_ACTION.moveDown);

      const rows = await getTextLinesRows(page, viewId);
      expect(rows.map((r) => r.id)).toEqual([
        textLineOnEmptyOrderId,
        `A${orderLine1Id}`,
        textLineBetweenId,
        `A${orderLine2Id}`,
      ]);
      expect(rows.find((r) => r.id === textLineBetweenId).scope).toBe(SCOPE.followingLines);
      expect(rows.find((r) => r.id === `A${orderLine1Id}`).line).toBe(10);
      expect(rows.find((r) => r.id === `A${orderLine2Id}`).line).toBe(20);
    });

    await test.step('Selecting the last row: the only action offered is insert-above', async () => {
      await selectRow(page, `A${orderLine2Id}`);
      const offered = await getOfferedQuickActions(page);
      expect(offered).toEqual([QUICK_ACTION.insertAbove]);
    });

    await test.step('A text line that is itself the last row: move-down is not offered', async () => {
      // push the same line past the last article line, so it becomes the very last row -- the
      // walkthrough's own last row is an article, which is exactly why this case is otherwise
      // never exercised through the UI
      await selectRow(page, textLineBetweenId);
      await invokeQuickAction(page, viewId, QUICK_ACTION.moveDown);

      const rows = await getTextLinesRows(page, viewId);
      expect(rows.map((r) => r.id)).toEqual([textLineOnEmptyOrderId, `A${orderLine1Id}`, `A${orderLine2Id}`, textLineBetweenId]);

      await selectRow(page, textLineBetweenId);
      const offered = await getOfferedQuickActions(page);
      expect(offered).not.toContain(QUICK_ACTION.moveDown);
      // it is still a genuine text row with a predecessor -- these remain available
      expect(offered).toEqual(expect.arrayContaining([QUICK_ACTION.insertAbove, QUICK_ACTION.delete, QUICK_ACTION.moveUp]));
    });

    await test.step('Edit the text, then delete the line', async () => {
      await typeIntoTextLineRow(page, {
        rowId: textLineBetweenId,
        text: 'Sortimentsware (revised)',
        viewId,
      });
      let rows = await getTextLinesRows(page, viewId);
      expect(rows.find((r) => r.id === textLineBetweenId).textLine).toBe('Sortimentsware (revised)');

      await selectRow(page, textLineBetweenId);
      await invokeQuickAction(page, viewId, QUICK_ACTION.delete);

      rows = await getTextLinesRows(page, viewId);
      expect(rows.map((r) => r.id)).toEqual([textLineOnEmptyOrderId, `A${orderLine1Id}`, `A${orderLine2Id}`]);
      // the surviving text line and both article lines are exactly as they were before the delete
      expect(rows.find((r) => r.id === textLineOnEmptyOrderId).textLine).toBe('');
      expect(rows.find((r) => r.id === textLineOnEmptyOrderId).scope).toBe(SCOPE.wholeDocument);
      expect(rows.find((r) => r.id === `A${orderLine1Id}`).line).toBe(10);
      expect(rows.find((r) => r.id === `A${orderLine2Id}`).line).toBe(20);
    });

    await test.step('Open the surviving line\'s scope dropdown and pick the other scope', async () => {
      // the row left standing by the delete above still carries the scope it was created with
      expect((await getTextLinesRows(page, viewId)).find((r) => r.id === textLineOnEmptyOrderId).scope).toBe(
        SCOPE.wholeDocument
      );

      const options = await openScopeDropdown(page, textLineOnEmptyOrderId);
      // the whole point of this step: the dropdown the user just opened has to be populated by the
      // view itself. A view that cannot answer for this field leaves the list empty, and the scope
      // column is then not editable at all -- which no assertion that reads the endpoint would notice,
      // because the stored value is perfectly fine either way.
      expect(Object.keys(options).sort()).toEqual([SCOPE.followingLines, SCOPE.wholeDocument].sort());

      // capture the caption off the option that is about to be clicked, so the expected cell text is
      // whatever this instance's reference list is translated to rather than a hardcoded string
      const expectedCaption = options[SCOPE.followingLines];
      await selectOpenScopeOption(page, { viewId, scopeKey: SCOPE.followingLines });

      await expect(scopeCellOf(page, textLineOnEmptyOrderId)).toHaveText(expectedCaption);

      // reopen the modal and read the cell again -- the picked value has to come back from the
      // database, not from the view instance that the click itself updated
      await closeTextLinesModal(page);
      viewId = await openTextLinesModal(page, orderId);
      await expect(scopeCellOf(page, textLineOnEmptyOrderId)).toHaveText(expectedCaption);
    });

    await closeTextLinesModal(page);
  });

  test('a multi-line value typed with Shift+Enter survives the round trip', async ({ page }) => {
    allure.epic('E0100: Sales');
    allure.tag('F00144: Free Text Above Order Lines');
    allure.tag('F00144');
    allure.story('Text lines modal: multi-line text via a real keystroke');
    allure.severity('critical');

    allure.description(`
Types a genuine two-line value into a text line's own field via a real Shift+Enter keystroke (not
a fill of a pre-built string), then rereads it through the view's own REST endpoint -- proving both
that the field is a real multi-line control and that Shift+Enter is exempt from the Enter-submits
path, through actual browser interaction rather than a direct API write.
    `);

    test.setTimeout(120000);

    const masterdata = await Backend.createMasterdata({
      request: {
        login: { user: { language: 'en_US', firstname: 'text', lastname: 'lines' } },
        bpartners: {
          CUSTOMER1: { isVendor: false, isCustomer: true, isSoPriceList: true, name: 'Customer' },
        },
      },
    });

    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    await SalesOrderPage.goto();
    await SalesOrderPage.clickNew();
    const orderId = await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

    const viewId = await openTextLinesModal(page, orderId);

    // insert one text line into the empty list
    await waitForViewRefresh(page, viewId, () => page.getByTestId('quick-action-button').click());
    const lineId = (await getTextLinesRows(page, viewId))[0].id;

    await typeIntoTextLineRow(page, { rowId: lineId, text: 'Line one\nLine two', viewId });

    const rows = await getTextLinesRows(page, viewId);
    expect(rows.find((r) => r.id === lineId).textLine).toBe('Line one\nLine two');

    // reread through a freshly opened view -- not the one the edit itself just populated -- so the
    // assertion covers the persisted record, not an in-memory copy of the view that made the edit
    await closeTextLinesModal(page);
    const reopenedViewId = await openTextLinesModal(page, orderId);
    const rowsAfterReopen = await getTextLinesRows(page, reopenedViewId);
    expect(rowsAfterReopen.find((r) => r.id === lineId).textLine).toBe('Line one\nLine two');

    await closeTextLinesModal(page);
  });
});
