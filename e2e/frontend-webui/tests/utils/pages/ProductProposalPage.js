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
 * The delivery-history flag filter in the overlay's inline (frequent-used) filter line.
 *
 * The <input> is visually replaced by `.input-checkbox-tick`, so the LABEL is what gets clicked
 * while the INPUT is what carries the checked state - hence both constants. `FILTER_CHECKBOX` is
 * derived from `FILTER_LABEL` (not written out a second time) so `setFilter` and any spec asserting
 * on the control cannot drift apart when the markup changes.
 */
export const FILTER_LABEL = `${OVERLAY} .filters-frequent .inline-filters label.input-checkbox`;
export const FILTER_CHECKBOX = `${FILTER_LABEL} input[type="checkbox"]`;

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

      // Wait for the view's own spinners to settle before anything is read
      await page
        .locator(`${OVERLAY} .rotating, ${OVERLAY} .indicator-pending`)
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});

      // The product column proves the view layout (not just the modal frame) is rendered
      await expect(page.locator(`${OVERLAY} th[data-testid="column-product"]`)).toBeVisible({
        timeout: SLOW_ACTION_TIMEOUT,
      });
    });
  }

  /**
   * Read the product names of all rows currently rendered in the overlay grid.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   * @returns {Promise<string[]>} Product names in grid order
   */
  static async getRowProductNames(page = getPage()) {
    return await test.step('ProductProposalPage - Get row product names', async () => {
      const productCells = page.locator(`${ROWS} td[data-cy="cell-product"]`);
      const count = await productCells.count();

      const names = [];
      for (let i = 0; i < count; i += 1) {
        const text = await productCells.nth(i).innerText();
        names.push(text.trim());
      }

      console.log(`Overlay rows (${names.length}): ${JSON.stringify(names)}`);
      return names;
    });
  }

  /**
   * Count the rows currently rendered in the overlay grid.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   * @returns {Promise<number>} Number of rows
   */
  static async getRowCount(page = getPage()) {
    return await test.step('ProductProposalPage - Get row count', async () => {
      return await page.locator(ROWS).count();
    });
  }

  /**
   * Turn the delivery-history filter on or off.
   *
   * The filter is a frequently-used, inline-rendered (`INLINE_PARAMETERS`) `YesNo` filter parameter, so
   * the frontend renders it via InlineFilterItem -> Checkbox as
   * `.filter-wrapper.filters-frequent .inline-filters label.input-checkbox input[type="checkbox"]`
   * (frontend/src/components/filters/FiltersNotIncluded.js, components/widget/Checkbox.js). Toggling the
   * checkbox patches the filter parameter and re-applies the filter, which reloads the view.
   *
   * @param {import('@playwright/test').Page} page - Playwright page
   * @param {boolean} on - true to activate the filter, false to deactivate it
   */
  static async setFilter(page = getPage(), on = true) {
    return await test.step(`ProductProposalPage - Set delivery-history filter: ${on}`, async () => {
      const filterLabel = page.locator(FILTER_LABEL).first();
      const checkbox = filterLabel.locator('input[type="checkbox"]');

      await checkbox.waitFor({ state: 'attached', timeout: SLOW_ACTION_TIMEOUT });

      const isChecked = await checkbox.isChecked();
      if (isChecked === on) {
        console.log(`Delivery-history filter already ${on ? 'on' : 'off'} - nothing to toggle`);
        return;
      }

      await filterLabel.click();

      // The toggle triggers a filter patch + view reload
      await page
        .locator(`${OVERLAY} .rotating, ${OVERLAY} .indicator-pending`)
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
      await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

      await expect(checkbox).toBeChecked({ checked: on, timeout: SLOW_ACTION_TIMEOUT });
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
        .waitFor({ state: 'visible', timeout: FAST_ACTION_TIMEOUT })
        .then(() => true)
        .catch(() => false);

      if (!inputRendered) {
        // Not in edit mode yet - a click on the cell renders the always-on editor
        await qtyCell.click();
        await qtyInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      }

      await qtyInput.click();
      await qtyInput.fill(qty.toString());

      // Blur commits the cell - an uncommitted edit is discarded
      await page.keyboard.press('Tab');

      await page
        .locator(`${OVERLAY} .rotating, ${OVERLAY} .indicator-pending`)
        .waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT })
        .catch(() => {});
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
