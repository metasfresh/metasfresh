/**
 * Reusable helpers for testing read-only view windows (grid-based).
 *
 * Designed for windows like "Purchase & Sales Overview", "Material Cockpit V2",
 * and any other read-only window based on a database view.
 *
 * Usage:
 *   import { navigateToViewWindow, assertColumnsPresent, ... } from '../utils/view-validation/ViewWindowHelper';
 */

import { expect } from '@playwright/test';
import { getPage, FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../common';

/**
 * Navigate to a view window and wait for the grid to load.
 * @param {number} windowId - AD_Window_ID
 */
export async function navigateToViewWindow(windowId) {
  if (!windowId || typeof windowId !== 'number') {
    throw new Error(`navigateToViewWindow: windowId must be a number, got ${JSON.stringify(windowId)}. Check that the constant is exported from WindowIds.js`);
  }
  const page = getPage();
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}`);

  // Wait for grid container
  await page.locator('.document-list-wrapper, .document-list').waitFor({
    state: 'visible',
    timeout: VERY_SLOW_ACTION_TIMEOUT,
  });

  // Wait for network to settle
  await page.waitForLoadState('networkidle', { timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

  // Wait for loading spinners to disappear
  await page.locator('.indicator-pending').waitFor({
    state: 'detached',
    timeout: SLOW_ACTION_TIMEOUT,
  }).catch(() => {});
}

/**
 * Assert that all expected column headers are present in the DOM.
 * Checks DOM presence (count > 0), NOT visibility (columns may be off-screen).
 *
 * @param {string[]} expectedColumns - Array of FieldName values (e.g., 'DocumentNo', 'M_Product_ID')
 * @returns {Promise<{present: string[], missing: string[]}>}
 */
export async function assertColumnsPresent(expectedColumns) {
  const page = getPage();
  const present = [];
  const missing = [];

  for (const col of expectedColumns) {
    const count = await page.locator(`th[data-testid="column-${col}"]`).count();
    if (count > 0) {
      present.push(col);
    } else {
      missing.push(col);
    }
  }

  return { present, missing };
}

/**
 * Discover all column headers currently in the DOM.
 * Supports both data-testid (new_dawn) and plain th (keen_hawk) selectors.
 * @returns {Promise<Array<{testId: string, caption: string}>>}
 */
export async function discoverColumns() {
  const page = getPage();

  // Try data-testid first (new_dawn frontend)
  let headers = await page.locator('th[data-testid^="column-"]').all();
  if (headers.length > 0) {
    const result = [];
    for (const header of headers) {
      const testId = await header.getAttribute('data-testid');
      const caption = await header.locator('.th-caption').textContent().catch(() => '');
      result.push({ testId, caption: caption.trim() });
    }
    return result;
  }

  // Fallback: plain th cells (keen_hawk frontend)
  headers = await page.locator('table thead th, table thead td').all();
  const result = [];
  for (const header of headers) {
    const text = (await header.textContent().catch(() => '')).trim();
    if (text) {
      result.push({ testId: `column-${text}`, caption: text });
    }
  }
  return result;
}

/**
 * Discover all facet filter buttons in the filter bar.
 * @returns {Promise<Array<{index: number, caption: string, active: boolean}>>}
 */
export async function discoverFacetFilters() {
  const page = getPage();
  const filterButtons = page.locator('.filter-wrapper .btn-filter');
  const count = await filterButtons.count();
  const result = [];

  for (let i = 0; i < count; i++) {
    const button = filterButtons.nth(i);
    const text = (await button.textContent().catch(() => '')).trim();
    const active = await button.evaluate((el) => el.classList.contains('btn-active'));
    result.push({ index: i, caption: text, active });
  }

  return result;
}

/**
 * Click a facet filter button by index, wait briefly, then close it.
 * Verifies the filter UI opens without errors.
 *
 * @param {number} filterIndex - 0-based index of the filter button
 * @returns {Promise<boolean>} True if filter opened successfully
 */
export async function exerciseFacetFilter(filterIndex) {
  const page = getPage();
  const filterButton = page.locator('.filter-wrapper .btn-filter').nth(filterIndex);

  await filterButton.click();
  await page.waitForTimeout(500);

  // Check if a filter menu or widget appeared
  const menuVisible = await page.locator('.filter-menu, .filter-widget, .filters-overlay').first()
    .waitFor({ state: 'visible', timeout: 3000 })
    .then(() => true)
    .catch(() => false);

  // Close by pressing Escape
  await page.keyboard.press('Escape');
  await page.waitForTimeout(300);

  return menuVisible;
}

/**
 * Assert that grid cells in key columns are populated (not empty).
 *
 * @param {string[]} columnNames - Column names to check (e.g., 'DocumentNo', 'C_BPartner_ID')
 * @param {number} maxRows - Max rows to check (default: 5)
 * @returns {Promise<{populated: number, empty: number, details: Array}>}
 */
export async function assertGridCellsPopulated(columnNames, maxRows = 5) {
  const page = getPage();
  const gridRows = page.locator('table tbody tr');
  const rowCount = await gridRows.count();
  const checkRows = Math.min(rowCount, maxRows);

  let populated = 0;
  let empty = 0;
  const details = [];

  for (let r = 0; r < checkRows; r++) {
    const row = gridRows.nth(r);
    for (const col of columnNames) {
      const cell = row.locator(`td[data-cy="cell-${col}"]`);
      const cellCount = await cell.count();
      if (cellCount === 0) {
        empty++;
        details.push({ row: r, column: col, value: '<no cell>' });
        continue;
      }
      const text = (await cell.textContent().catch(() => '')).trim();
      if (text) {
        populated++;
      } else {
        empty++;
      }
      details.push({ row: r, column: col, value: text || '<empty>' });
    }
  }

  return { populated, empty, details };
}

/**
 * Get the total number of visible grid rows.
 * @returns {Promise<number>}
 */
export async function getGridRowCount() {
  const page = getPage();

  // Try standard table rows first, then flex-based grid rows
  let rows = page.locator('table tbody tr');
  await rows.first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
  let count = await rows.count();

  if (count === 0) {
    // Fallback: some views use div-based rows
    rows = page.locator('.table-flex-wrapper .table-flex-row, .document-list-table tr');
    count = await rows.count();
  }

  return count;
}

/**
 * Get total item count from pagination footer (language-independent).
 * Parses "Total items 63" / "Gesamt 63" from the pagination area.
 *
 * @returns {Promise<number>} Total item count, or 0 if not found
 */
export async function getTotalItemCount() {
  const page = getPage();
  const paginationText = await page.locator('.pagination-wrapper, .document-list-footer').first().textContent().catch(() => '');
  const match = paginationText.match(/(\d+)\s*(?:«|$)/);
  if (!match) {
    const match2 = paginationText.match(/(?:Total items|Gesamt)\s*(\d+)/i);
    return match2 ? parseInt(match2[1]) : 0;
  }
  return parseInt(match[1]);
}
