import ExcelJS from 'exceljs';
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
