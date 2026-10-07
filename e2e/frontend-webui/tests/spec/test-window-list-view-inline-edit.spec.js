import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { WEBAPI_BASE_URL, assertRecordIsValid, getFieldData } from '../utils/WebAPIValidation';

/**
 * Inline edit in a top-level list view: the `Test` window's Amount and Quantity columns (de_DE).
 *
 * Real-life case: in a window's list view the user right-clicks an amount or a quantity cell and
 * picks "Feld bearbeiten". The cell opens the same grid cell editor as an included tab. Opening
 * and leaving the editor must not move the layout: the editor takes exactly the box of the static
 * value, and the row height and every column width stay the same. Escape keeps the stored value;
 * Enter saves the typed one.
 *
 * In the `Test` window's list view only the Amount and Quantity columns are editable (no grid
 * element there has a view edit mode set, so the backend default applies).
 *
 * Features tested:
 * - F5010: Order Lines Grid
 */

const TEST_WINDOW_ID = '127';
const TOLERANCE_PX = 1;
const VIEWPORT = { width: 1920, height: 1080 };

const COLUMNS = [
  {
    field: 'T_Amount',
    widgetType: 'Amount',
    stored: 10.5,
    storedText: '10,50',
    typed: '12.75',
    saved: 12.75,
    savedText: '12,75',
  },
  {
    field: 'T_Qty',
    widgetType: 'Quantity',
    stored: 3,
    storedText: '3,00',
    typed: '5',
    saved: 5,
    savedText: '5,00',
  },
];

async function createTestRecord(page, name) {
  const response = await page.request.patch(`${WEBAPI_BASE_URL}/window/${TEST_WINDOW_ID}/NEW`, {
    data: [
      { op: 'replace', path: 'Name', value: name },
      ...COLUMNS.map(({ field, stored }) => ({ op: 'replace', path: field, value: String(stored) })),
    ],
  });
  expect(response.ok(), `creating the Test record: ${response.status()}`).toBeTruthy();
  const body = await response.json();
  const documents = Array.isArray(body) ? body : body.documents;
  const recordId = documents[0].id;
  await assertRecordIsValid(TEST_WINDOW_ID, recordId, 'the new Test record');
  return recordId;
}

async function readStoredValue(recordId, field) {
  const fieldData = await getFieldData(TEST_WINDOW_ID, recordId, field);
  return Number(fieldData?.value);
}

async function measureRow(row) {
  return await row.evaluate((tr) => ({
    rowHeight: tr.getBoundingClientRect().height,
    columns: Array.from(tr.querySelectorAll('td')).map((td) => ({
      cell: td.getAttribute('data-cy') || '(no data-cy)',
      width: td.getBoundingClientRect().width,
    })),
  }));
}

/** the static value (`.cell-text-wrapper`) in display mode, the editor (`.form-group`) in edit mode */
async function measureComponentBox(cell) {
  return await cell.evaluate((td) => {
    const editor = td.querySelector('.form-group');
    const component = editor || td.querySelector(':scope > div:not(.cell-width-keeper) .cell-text-wrapper');
    if (!component) {
      return null;
    }
    const box = component.getBoundingClientRect();
    return { kind: editor ? 'editor' : 'static', width: box.width, height: box.height };
  });
}

function geometryViolations(phase, field, before, now) {
  const violations = [];
  if (!before.component || !now.component) {
    violations.push(`${field} ${phase}: component box not measurable`);
  } else {
    ['width', 'height'].forEach((dimension) => {
      if (Math.abs(now.component[dimension] - before.component[dimension]) > TOLERANCE_PX) {
        violations.push(
          `${field} ${phase}: component ${dimension} ${before.component.kind} ${before.component[dimension]} -> ${now.component.kind} ${now.component[dimension]}`
        );
      }
    });
  }
  if (Math.abs(now.row.rowHeight - before.row.rowHeight) > TOLERANCE_PX) {
    violations.push(`${field} ${phase}: row height ${before.row.rowHeight} -> ${now.row.rowHeight}`);
  }
  before.row.columns.forEach((column, i) => {
    const nowWidth = now.row.columns[i] ? now.row.columns[i].width : NaN;
    if (!(Math.abs(nowWidth - column.width) <= TOLERANCE_PX)) {
      violations.push(`${field} ${phase}: column ${column.cell} width ${column.width} -> ${nowWidth}`);
    }
  });
  return violations;
}

async function measure(row, cell) {
  return { row: await measureRow(row), component: await measureComponentBox(cell) };
}

/** right-click the cell and pick "edit field" (the context-menu entry with the edit icon) */
async function openEditorFromContextMenu(page, cell) {
  await cell.click();
  await cell.click({ button: 'right' });
  await page.locator('.context-menu-item').filter({ has: page.locator('i.meta-icon-edit') }).click();
  const input = cell.locator('.form-group input').first();
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await expect(input).toBeFocused();
  await page.waitForTimeout(300); // let the editor settle before measuring
  return input;
}

test.describe('Test window list view — inline edit of Amount and Quantity (de_DE)', () => {
  test('Edit field from the context menu: layout unchanged, Escape keeps, Enter saves', async ({ page }) => {
    allure.epic('E0500: Sales Orders');
    allure.tag('F5010: Order Lines Grid');
    allure.tag('F5010');
    allure.story('List view — inline edit keeps the layout; Escape restores, Enter saves');
    allure.severity('critical');
    test.setTimeout(180000);

    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language: 'de_DE', firstname: 'E2E', lastname: 'Tester' } } },
    });
    await page.setViewportSize(VIEWPORT);
    await LoginPage.goto();
    await LoginPage.login(masterdata.login.user);
    await DashboardPage.expectVisible();

    const recordName = `ListViewEdit_${Date.now()}`;
    const recordId = await createTestRecord(page, recordName);

    await page.goto(`${FRONTEND_BASE_URL}/window/${TEST_WINDOW_ID}`);
    const row = page.locator('tr').filter({
      has: page.locator('[data-cy="cell-Name"]', { hasText: recordName }),
    });
    await row.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    for (const column of COLUMNS) {
      const cell = row.locator(`[data-cy="cell-${column.field}"]`);

      await test.step(`${column.field}: edit field, type, Escape — layout unchanged, value kept`, async () => {
        await expect(cell).toHaveText(column.storedText);
        const before = await measure(row, cell);

        const input = await openEditorFromContextMenu(page, cell);
        expect(
          await cell.evaluate((td) => td.querySelector('.form-group')?.className || ''),
          `${column.field} opens the ${column.widgetType} grid cell editor`
        ).toContain(`widgetType-${column.widgetType}`);
        const inEdit = await measure(row, cell);

        await input.fill(column.typed);
        await page.keyboard.press('Escape');
        await cell.locator('.form-group').first().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await page.waitForTimeout(500);
        const afterLeave = await measure(row, cell);

        expect(
          [
            ...geometryViolations('while editing', column.field, before, inEdit),
            ...geometryViolations('after leaving', column.field, before, afterLeave),
          ],
          `${column.field}: entering/leaving the editor must not change the component box, the row height or any column width`
        ).toEqual([]);
        await expect(cell, `${column.field} shows its stored value after Escape`).toHaveText(column.storedText);
        expect(await readStoredValue(recordId, column.field), `${column.field} unchanged on the server`).toBe(
          column.stored
        );
      });

      await test.step(`${column.field}: edit field, type, Enter — the typed value is saved`, async () => {
        const input = await openEditorFromContextMenu(page, cell);
        await input.fill(column.typed);
        await page.keyboard.press('Enter');
        await expect
          .poll(() => readStoredValue(recordId, column.field), {
            message: `${column.field} saved on the server`,
            timeout: SLOW_ACTION_TIMEOUT,
          })
          .toBe(column.saved);

        // Enter saves and keeps the editor open; Escape now leaves it on the saved value
        await page.keyboard.press('Escape');
        await cell.locator('.form-group').first().waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
        await expect(cell, `${column.field} shows the saved value`).toHaveText(column.savedText, {
          timeout: SLOW_ACTION_TIMEOUT,
        });
        expect(await readStoredValue(recordId, column.field), `${column.field} still saved after Escape`).toBe(
          column.saved
        );
      });
    }

    await test.step('After a reload the list view shows the saved values', async () => {
      await page.reload();
      await row.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
      for (const column of COLUMNS) {
        await expect(row.locator(`[data-cy="cell-${column.field}"]`)).toHaveText(column.savedText);
      }
    });
  });
});
