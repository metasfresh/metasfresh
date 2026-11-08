import ExcelJS from 'exceljs';
import { expect } from '@playwright/test';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from './common';
import { WEBAPI_BASE_URL } from './WebAPIValidation';

/**
 * Reads an xlsx file (e.g. a WebUI "Download selected" export) into plain cell texts.
 *
 * Only xlsx is supported: exceljs cannot read the old binary xls format. The WebUI exports in the
 * format of sysconfig `de.metas.excel.DefaultFileExtension` (default xlsx).
 *
 * @param {string} filePath - path of the downloaded file
 * @returns {Promise<{ sheets: { name: string, rows: string[][] }[], allTexts: string[] }>}
 *   `rows` holds each row's cell texts in column order (empty cells as ''), `allTexts` every
 *   non-empty cell text of the workbook
 */
export async function readXlsx(filePath) {
  const workbook = new ExcelJS.Workbook();
  await workbook.xlsx.readFile(filePath);

  const sheets = [];
  const allTexts = [];
  workbook.eachSheet((worksheet) => {
    const rows = [];
    worksheet.eachRow({ includeEmpty: false }, (row) => {
      const texts = [];
      for (let column = 1; column <= worksheet.columnCount; column++) {
        const text = cellText(row.getCell(column));
        texts.push(text);
        if (text !== '') {
          allTexts.push(text);
        }
      }
      rows.push(texts);
    });
    sheets.push({ name: worksheet.name, rows });
  });
  return { sheets, allTexts };
}

/**
 * The field names of a window's list-view (grid) layout, in column order.
 *
 * The WebUI Excel export (`ViewExcelExporter`) writes one column per element of this layout, in the
 * same order, and only the header row carries the (localized) caption. So a column is found by its
 * field name (the DB ColumnName) through this list, never by its caption.
 *
 * @param {import('@playwright/test').Page} page - logged-in page (its session cookie is used)
 * @param {number|string} windowId
 * @returns {Promise<string[]>}
 */
export async function fetchViewGridFieldNames(page, windowId) {
  const response = await page.request.get(`${WEBAPI_BASE_URL}/documentView/${windowId}/layout?viewType=grid`);
  if (!response.ok()) {
    throw new Error(`grid layout of window ${windowId}: HTTP ${response.status()}`);
  }
  const layout = await response.json();
  return (layout.elements || []).map((element) => element.fields?.[0]?.field ?? null);
}

/**
 * The texts of the column of `fieldName` below the header row of an exported sheet.
 *
 * @param {{ rows: string[][] }} sheet - a sheet from readXlsx; its first row is the header row
 * @param {(string|null)[]} gridFieldNames - from fetchViewGridFieldNames for the exported window
 * @param {string} fieldName - DB ColumnName of the column
 * @returns {string[]}
 */
export function columnTextsByFieldName(sheet, gridFieldNames, fieldName) {
  const columnIndex = gridFieldNames.indexOf(fieldName);
  if (columnIndex < 0) {
    throw new Error(`${fieldName} is not a column of the grid layout (${gridFieldNames.join(', ')})`);
  }
  const [header, ...rows] = sheet.rows;
  if (!header || header.length !== gridFieldNames.length) {
    throw new Error(`the export has ${header ? header.length : 0} columns, the grid layout ${gridFieldNames.length}`);
  }
  return rows.map((row) => row[columnIndex] ?? '');
}

/**
 * Selects exactly the given grid rows: the first one with a plain click, the others with Ctrl/Cmd+click.
 * The WebUI selects a table's first row by itself when the rows arrive or change and no selection is left
 * (frontend `reducers/tables.js`, `CREATE_TABLE` / `UPDATE_TABLE`), and a plain click on the only selected row deselects it (frontend `Table.handleClick`),
 * so the first row is clicked only when it is not selected yet.
 *
 * @param {import('@playwright/test').Locator[]} rows - visible grid rows (`tr[data-testid="table-row-<id>"]`)
 */
export async function selectRows(rows) {
  expect(rows.length, 'selectRows needs at least one row').toBeGreaterThan(0);
  const [firstRow, ...otherRows] = rows;
  const isFirstRowSelected = await firstRow.evaluate((el) => el.classList.contains('row-selected'));
  if (!isFirstRowSelected) {
    await firstRow.click();
  }
  for (const row of otherRows) {
    await row.click({ modifiers: ['ControlOrMeta'] });
  }
  for (const row of rows) {
    await expect(row, 'every row must be selected').toHaveClass(/row-selected/);
  }
}

/**
 * Opens the window's list view on exactly the given records, selects all of them and downloads them
 * through the actions menu's Excel export ("Download selected").
 *
 * The list view is opened on a view of only these ids, because a window's list is paged and holds
 * every other run's records too.
 *
 * @param {import('@playwright/test').Page} page - logged-in page
 * @param {number|string} windowId
 * @param {number[]} recordIds - the records to export; each one must be visible in the view
 * @returns {Promise<string>} path of the downloaded file
 */
export async function exportRecordsToExcel(page, windowId, recordIds) {
  const viewResponse = await page.request.post(`${WEBAPI_BASE_URL}/documentView/${windowId}`, {
    data: { windowId: String(windowId), viewType: 'grid', filterOnlyIds: recordIds },
  });
  expect(viewResponse.ok(), `view of window ${windowId} records ${recordIds}: HTTP ${viewResponse.status()}`).toBe(true);
  const { viewId } = await viewResponse.json();
  await page.goto(`${FRONTEND_BASE_URL}/window/${windowId}?viewId=${viewId}`);

  const rows = recordIds.map((recordId) => page.locator(`tr[data-testid="table-row-${recordId}"]`));
  for (const row of rows) {
    await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  }
  await selectRows(rows);

  // the actions menu shortcut keeps the selection (a click outside the table may clear it)
  await page.keyboard.press('Alt+1');
  const exportLink = page.locator('.subheader-container a.subheader-item[href*="/export/excel"]');
  await exportLink.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // the export link names the selected rows in its selectedIds parameter (comma-separated)
  const href = await exportLink.getAttribute('href');
  const selectedIds = new URL(href, FRONTEND_BASE_URL).searchParams.get('selectedIds');
  expect(selectedIds, `the export link must carry selectedIds: ${href}`).not.toBeNull();
  expect(selectedIds.split(',').map(Number).sort((a, b) => a - b), 'the export must hold exactly the selected records').toEqual(
    recordIds.map(Number).sort((a, b) => a - b)
  );
  const download = page.waitForEvent('download', { timeout: SLOW_ACTION_TIMEOUT });
  await exportLink.click();
  return await (await download).path();
}

function cellText(cell) {
  const value = cell.value;
  if (value === null || value === undefined) {
    return '';
  }
  if (typeof value === 'object') {
    if (Array.isArray(value.richText)) {
      return value.richText.map((part) => part.text).join('');
    }
    if (value.text !== undefined) {
      return String(value.text);
    }
    if (value.result !== undefined) {
      return String(value.result);
    }
    if (value instanceof Date) {
      return value.toISOString();
    }
  }
  return String(value);
}
