import { expect } from '@playwright/test';
import { test } from '../../../playwright.config';
import { getPage, FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT } from '../common';

/**
 * Page object for the Products Proposal overlay (Produktvorschläge).
 *
 * The overlay is launched from the sales order *order lines* tab as an included-tab top action
 * (AD_Table_Process.WEBUI_IncludedTabTopAction='Y' for AD_Tab_ID=187 / AD_Window_ID=143), keyboard
 * shortcut `Alt-Z` (AD_Table_Process.WEBUI_Shortcut).
 *
 * It is a custom view with windowId `orderProductsProposal`
 * (OrderProductsProposalViewFactory.WINDOW_ID_STRING) opened as a modal overlay
 * (ViewOpenTarget.ModalOverlay -> frontend `SAME_TAB_OVERLAY` -> `openRawModal`), so it renders in the
 * raw-modal panel (`.raw-modal .panel-modal`) and the browser URL keeps pointing at the sales order —
 * `openRawModal` (frontend/src/actions/WindowActions.js) does not push a history entry.
 *
 * Displayed columns (OrderProductsProposalViewFactory#createViewLayout): product, qty, PackDescription,
 * asi, lastShipmentDays ("Tage vergangen"), price, currency, isCampaignPrice. Grid cells carry
 * `data-cy="cell-<fieldName>"` (frontend/src/components/table/TableCell.js).
 */

/** windowId of the overlay view (a string window id, not an AD_Window_ID). */
export const PRODUCT_PROPOSAL_WINDOW_ID = 'orderProductsProposal';

/** Container of the overlay (raw modal panel). */
export const OVERLAY = '.raw-modal .panel-modal';

/** Data rows of the overlay grid. */
export const ROWS = `${OVERLAY} table tbody tr`;

/**
 * The delivery-history filter.
 *
 * It is a frequently-used filter rendered in BUTTON mode (`DocumentFilterInlineRenderMode.BUTTON`),
 * so the filter line shows a button `Filter: <caption>` and the YesNo parameter lives in the
 * dropdown panel that button opens (frontend/src/components/filters/FiltersNotIncluded.js -> FiltersItem).
 *
 * The selectors below are derived from `FILTER_ID` so they cannot drift apart from the backend
 * descriptor (OrderProductsProposalViewFilters.FILTER_ID). The button and the apply button carry
 * `data-testid`s, so no localized caption is used as a selector. They are module-private: the specs
 * drive the filter through `setFilter` / `expectFilterState`, never through the raw selectors.
 */
const FILTER_ID = 'onlyDeliveredFilter';

/** The `Filter: ...` button that opens the filter's parameter panel. */
const FILTER_BUTTON = `${OVERLAY} [data-testid="filter-button-${FILTER_ID}"]`;

/** The opened parameter panel of that filter (FiltersItem's `filter-content filter-<filterId>`). */
const FILTER_PANEL = `${OVERLAY} .filter-content.filter-${FILTER_ID}`;

/**
 * The YesNo parameter inside the panel. The <input> is visually replaced by `.input-checkbox-tick`,
 * so the LABEL is what gets clicked while the INPUT carries the checked state - hence both.
 */
const FILTER_PANEL_LABEL = `${FILTER_PANEL} label.input-checkbox`;
const FILTER_CHECKBOX = `${FILTER_PANEL_LABEL} input[type="checkbox"]`;

/** The panel's Apply button, which is what actually posts the filter. */
const FILTER_APPLY_BUTTON = `${OVERLAY} [data-testid="filter-apply-button"]`;

/**
 * The panel's "clear filter" link. FiltersItem renders it only while the filter IS active
 * (`{isActive && <span className="filter-clear" ...>}`), so its presence doubles as the
 * "is the filter currently applied?" read from inside the open panel.
 */
const FILTER_CLEAR = `${OVERLAY} .filter-menu .filter-controls .filter-clear`;

/**
 * Buttons of the order lines tab's filter line - the included-tab top actions come last.
 *
 * NOTE: consumers select `.last()` here, which assumes the tab's own buttons ("Add new",
 * batch entry) always render BEFORE the included-tab top actions, and that this tab has
 * exactly one top action. Both hold today, but neither is enforced anywhere: if a button
 * is ever added after the top actions, `.last()` silently targets the wrong element with
 * no error. Prefer the `Alt+Z` shortcut path; this is only the fallback.
 */
const TOP_ACTION_BUTTONS = '.table-filter-line .filter-panel-buttons button';

export class ProductProposalPage {
  /**
   * Open the overlay from a sales order that is showing its order lines tab.
   * Uses the `Alt+Z` shortcut, falling back to a click on the top action button.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   */
  static async openFromSalesOrder(page = getPage()) {
    return await test.step('ProductProposalPage - Open overlay from sales order (Alt+Z)', async () => {
      const overlay = page.locator(OVERLAY);

      // Focus the document body so the shortcut is not swallowed by an input
      await page.locator('body').click();
      await page.waitForTimeout(200);

      await page.keyboard.press('Alt+Z');

      const openedByShortcut = await overlay
        .waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT })
        .then(() => true)
        .catch(() => false);

      if (!openedByShortcut) {
        console.log('Alt+Z did not open the overlay - clicking the Products Proposal top action');

        // Language-independent: the included-tab top actions are the LAST buttons rendered in
        // `.filter-panel-buttons` - after "Add new" and the batch-entry toggle
        // (frontend/src/components/table/TableFilter.js). The order lines tab has exactly one top
        // action (Produktvorschläge / AD_Process 541038), so its button is the last one.
        // The caption itself is localized and must not be used as a selector.
        const topAction = page.locator(TOP_ACTION_BUTTONS).last();
        await topAction.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        await topAction.click();
      }

      await this.expectVisible(page);
    });
  }

  /**
   * Assert the overlay is open and its grid has finished loading.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   */
  static async expectVisible(page = getPage()) {
    return await test.step('ProductProposalPage - Expect overlay visible', async () => {
      const overlay = page.locator(OVERLAY);
      await overlay.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      // The product column proves the view layout (not just the modal frame) is rendered...
      await expect(page.locator(`${OVERLAY} th[data-testid="column-product"]`)).toBeVisible({
        timeout: SLOW_ACTION_TIMEOUT,
      });

      // Deliberately NOT also asserting that a data row is present: a filtered view is allowed to be
      // legitimately empty, and a caller that needs rows asserts on them itself (web-first, so it
      // retries). Nor is there a spinner to wait on here - this overlay renders no loading
      // affordance, and `.rotating`/`.indicator-pending` (waited on at this spot previously) are not
      // classes the frontend emits at all: `indicator-pending` exists only as a @keyframes name in
      // frontend/src/assets/css/window-indicator.scss, so that wait resolved on the first poll and
      // protected nothing. What does synchronise a filter change is the round-trip wait in
      // `setFilter`.
    });
  }

  /**
   * Read every row currently rendered in the overlay grid.
   *
   * Returns the two cells the delivery-history specs reason about: the product and the
   * `lastShipmentDays` ("Tage vergangen") value that the filter keys off. Both specs go through this
   * one reader so the cell selectors cannot drift apart between them.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   * @returns {Promise<Array<{product: string, lastShipmentDays: string}>>} Rows in grid order
   */
  static async readRows(page = getPage()) {
    return await test.step('ProductProposalPage - Read overlay rows', async () => {
      const rows = page.locator(ROWS);
      const count = await rows.count();

      const result = [];
      for (let i = 0; i < count; i += 1) {
        const row = rows.nth(i);
        const product = (await row.locator('td[data-cy="cell-product"]').innerText()).trim();
        const lastShipmentDays = (
          await row.locator('td[data-cy="cell-lastShipmentDays"]').innerText()
        ).trim();
        result.push({ product, lastShipmentDays });
      }

      console.log(`Overlay rows (${result.length}): ${JSON.stringify(result)}`);
      return result;
    });
  }

  /**
   * Assert the filter control is present, and whether it currently reads as active.
   *
   * In BUTTON render mode the parameter checkbox only exists while the dropdown panel is open, so
   * "is the filter on?" is read off the button instead: FiltersNotIncluded.js puts `btn-active` on
   * the button exactly when the filter is active and not cleared.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   * @param {boolean} active - expected active state of the filter
   */
  static async expectFilterState(page = getPage(), active = false) {
    return await test.step(`ProductProposalPage - Expect delivery-history filter active: ${active}`, async () => {
      const button = page.locator(FILTER_BUTTON);
      await expect(button).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

      const activeButton = page.locator(`${FILTER_BUTTON}.btn-active`);
      if (active) {
        await expect(activeButton).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
      } else {
        await expect(activeButton).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
      }
    });
  }

  /**
   * Turn the delivery-history filter on or off.
   *
   * BUTTON render mode is not a single toggle - each direction is its own interaction, and both go
   * through the `Filter: ...` button's dropdown panel:
   *  - ON: open the panel, tick the YesNo parameter, press Apply. Ticking alone only changes local
   *    widget state; only Apply posts the filter (FiltersItem#handleApply).
   *  - OFF: open the panel and press "clear filter". Applying an UNTICKED checkbox is deliberately
   *    not used: FiltersItem#handleApply still submits the filter (with value false), leaving it in
   *    the view's active-filter list, whereas clearing removes it - which is what "off" means here,
   *    and what a user does.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   * @param {boolean} on - true to activate the filter, false to deactivate it
   */
  static async setFilter(page = getPage(), on = true) {
    return await test.step(`ProductProposalPage - Set delivery-history filter: ${on}`, async () => {
      const button = page.locator(FILTER_BUTTON);
      await button.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await button.click();

      const panel = page.locator(FILTER_PANEL);
      await panel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const clearLink = page.locator(FILTER_CLEAR);
      const isCurrentlyOn = await clearLink.isVisible();
      if (isCurrentlyOn === on) {
        console.log(`Delivery-history filter already ${on ? 'on' : 'off'} - closing the panel again`);
        await button.click();
        await expect(panel).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
        return;
      }

      if (on) {
        const panelLabel = page.locator(FILTER_PANEL_LABEL).first();
        await panelLabel.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

        const checkbox = page.locator(FILTER_CHECKBOX).first();
        if ((await checkbox.isChecked()) !== true) {
          await panelLabel.click();
        }
        await expect(checkbox).toBeChecked({ timeout: FAST_ACTION_TIMEOUT });
      }

      // Applying (and clearing) runs DocumentListContainer's isNewFilter flow (handleFilterChange ->
      // fetchLayoutAndData -> filterCurrentView): a layout GET, then POST .../filter, then
      // GET .../<viewId>?firstRow=... - and only that last GET repaints the grid, so that is the
      // thing to wait for. Both promises must be created BEFORE the click or a fast response is
      // missed.
      //
      // Neither a spinner wait nor `networkidle` can substitute here:
      //  - this overlay renders NO loading affordance during the round-trip (`.spinner` from
      //    components/app/SpinnerOverlay.js never attaches, and `.rotating`/`.indicator-pending`
      //    are not classes the frontend emits at all - `indicator-pending` exists only as a
      //    @keyframes name in src/assets/css/window-indicator.scss), so a `detached` wait on
      //    either resolves on the very first poll;
      //  - `waitForLoadState('networkidle')` resolves immediately on an already-loaded page and
      //    does not track XHRs started after the call.
      // Measured previously on C_Order_ID=1000030, when the click target was the checkbox itself:
      // with those two waits the row read landed before the repaint in 3 of 8 toggles - filter
      // already applied, grid still showing the old rows. The click target is now the panel's
      // Apply/Clear button, so the figure is historical; the round-trip it describes is unchanged.
      const filterApplied = page.waitForResponse(
        (response) =>
          /\/documentView\/[^/]+\/[^/]+\/filter$/.test(response.url()) &&
          response.request().method() === 'POST',
        { timeout: SLOW_ACTION_TIMEOUT }
      );
      const rowsReloaded = page.waitForResponse(
        (response) =>
          /\/documentView\/[^/]+\/[^/]+\?firstRow=/.test(response.url()) &&
          response.request().method() === 'GET',
        { timeout: SLOW_ACTION_TIMEOUT }
      );

      await page.locator(on ? FILTER_APPLY_BUTTON : FILTER_CLEAR).click();

      const filterResponse = await filterApplied;
      expect(filterResponse.status(), 'the filter round-trip must succeed').toBe(200);
      await rowsReloaded;

      // The panel closes on apply/clear (FiltersItem -> closeFilterMenu), and the button reflects
      // the new state - both are what the next interaction depends on.
      await expect(panel).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
      await this.expectFilterState(page, on);
    });
  }

  /**
   * Type a quantity into the Qty cell of the row of the given product and commit it.
   *
   * Qty is an always-on editor cell (`editor = ViewEditorRenderMode.ALWAYS` on
   * ProductsProposalRow#FIELD_Qty) and the overlay opens with the focus in it
   * (`setFocusOnFieldName(ProductsProposalRow.FIELD_Qty)`). The typed value is only
   * persisted once the cell is blurred, so `Tab` is pressed to commit the edit.
   *
   * NO PERSISTENCE GUARANTEE: this method types and commits, but does not read the value
   * back, so it does not prove the quantity stuck. A spec that depends on the quantity
   * having persisted must assert that itself - e.g. by re-reading the cell, or by checking
   * the resulting order line after the overlay is closed with DONE.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   * @param {string} productName - Product name of the row to edit
   * @param {string|number} qty - Quantity to type
   */
  static async enterQty(page = getPage(), productName, qty) {
    return await test.step(`ProductProposalPage - Enter qty ${qty} for ${productName}`, async () => {
      const row = page
        .locator(ROWS)
        .filter({ has: page.locator('td[data-cy="cell-product"]', { hasText: productName }) })
        .first();

      await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });

      const qtyCell = row.locator('td[data-cy="cell-qty"]');
      const qtyInput = qtyCell.locator('input').first();

      const inputRendered = await qtyInput
        .waitFor({ state: 'attached', timeout: FAST_ACTION_TIMEOUT })
        .then(() => true)
        .catch(() => false);

      if (!inputRendered) {
        // Not in edit mode yet. A click alone only FOCUSES an "always-editor" cell -
        // TableRow#_editProperty (components/table/TableRow.js) only swaps in the input widget
        // on Enter/F2 (`handleKeyDown_Enter`), never on a bare click or dblclick; confirmed by
        // inspecting the cell's DOM before/after each of click, dblclick and click+Enter. So
        // focus the cell, then press Enter to render the input.
        await qtyCell.click();
        await page.keyboard.press('Enter');
        await qtyInput.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });
      }

      // Do NOT click qtyInput here: Enter already rendered the widget with real DOM/browser
      // focus on it (confirmed: outerHTML shows `input-focused` immediately after Enter, and
      // `isEditable()`/`isEnabled()`/`isVisible()` are all true). An extra click on the
      // already-focused input collapses the cell straight back to display mode - the DOM
      // reverts to the plain `cell-text-wrapper` (no `<input>`) in the same tick, so the
      // subsequent `.fill()` waits forever for an `input` that no longer exists (that is the
      // "times out at locator.fill" symptom). Reproduced live: click -> Enter -> input attached,
      // visible/enabled/editable, boundingBox present; one more `.click()` on that same input ->
      // cell reverts to `<div class="cell-text-wrapper quantity-cell"></div>`, input gone.
      // `.fill()` does not click (it focuses + sets the value directly), so it is safe to call
      // right after the input is attached.
      await qtyInput.fill(qty.toString());

      // Blur commits the cell - an uncommitted edit is discarded. The commit is a
      // PATCH .../documentView/<windowId>/<viewId>/<rowId>/edit
      // (ViewRowEditRestController.ENDPOINT, patchRow), which is the only reliable signal that the
      // value reached the server: the cell re-renders optimistically, and the overlay shows no
      // spinner while the request is in flight. Arm the wait BEFORE the blur or a fast response is
      // missed. (The previous wait here - `.rotating, .indicator-pending` detached - was a no-op:
      // the frontend emits neither as a class; `indicator-pending` exists only as a @keyframes
      // name in frontend/src/assets/css/window-indicator.scss.)
      const qtyCommitted = page.waitForResponse(
        (response) =>
          /\/documentView\/[^/]+\/[^/]+\/[^/]+\/edit$/.test(response.url()) &&
          response.request().method() === 'PATCH',
        { timeout: SLOW_ACTION_TIMEOUT }
      );

      await page.keyboard.press('Tab');

      const commitResponse = await qtyCommitted;
      expect(commitResponse.status(), `committing qty ${qty} for ${productName} must succeed`).toBe(200);

      // ... and the committed value must be what is now rendered in the cell.
      await expect(qtyCell).toContainText(qty.toString().replace(/\.0+$/, ''), {
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  }

  /**
   * Close the overlay with its DONE close action, which is what creates the order lines
   * (ViewCloseAction.DONE, allowed by OrderProductsProposalViewFactory#createViewLayout).
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   */
  static async closeWithDone(page = getPage()) {
    return await test.step('ProductProposalPage - Close overlay with DONE', async () => {
      // ModalButton renders data-testid="modal-<name.toLowerCase()>" - language-independent
      const doneButton = page.locator(`${OVERLAY} [data-testid="modal-done"]`);
      await doneButton.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await doneButton.click();

      await page
        .locator(OVERLAY)
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(async () => {
          await expect(page.locator(OVERLAY)).toBeHidden({ timeout: SLOW_ACTION_TIMEOUT });
        });

      // The order lines are created on close - wait for the parent view to settle
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
    });
  }
}
