import ExcelJS from 'exceljs';

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
 * Finds a header row containing `headerText` and returns the texts of that column in the rows below it.
 *
 * @param {{ rows: string[][] }} sheet
 * @param {string} headerText
 * @returns {string[] | null} the column's texts, or null when no row has such a header
 */
export function columnTexts(sheet, headerText) {
  for (let rowIndex = 0; rowIndex < sheet.rows.length; rowIndex++) {
    const columnIndex = sheet.rows[rowIndex].indexOf(headerText);
    if (columnIndex >= 0) {
      return sheet.rows.slice(rowIndex + 1).map((row) => row[columnIndex] ?? '');
    }
  }
  return null;
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
