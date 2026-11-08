import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { loginWithMasterdataUser } from '../utils/LoginHelper';
import { newUnique } from '../utils/common';
import { columnTextsByFieldName, exportRecordsToExcel, fetchViewGridFieldNames, readXlsx } from '../utils/ExcelParser';
import {
  expectStoredColumnValue,
  openNewWindowDocument,
  savedWindowRecordId,
  typeIntoWindowField,
  untickWindowCheckbox,
} from '../utils/WindowFieldEntry';

/**
 * EMail-Ausgangsserver (SMTP) window (AD_Window_ID=540080, table AD_MailBox) — the SMTP password is a
 * column of the window's grid. A password must never show in clear there: the grid cell and the
 * Excel export ("Download selected", which writes exactly the grid's columns) show `********` for a
 * set password and nothing for an unset one, and the typed password appears in no cell.
 */

const MAILBOX_WINDOW_ID = 540080;
const MASK = '********';

/**
 * Creates a mailbox in the window by typing its fields; returns its record id once it is saved.
 * Without SMTP authorization (`smtpAuthorization: false`) login and password are not mandatory.
 */
async function createMailbox(page, fields, { smtpAuthorization = true } = {}) {
  await openNewWindowDocument(page, MAILBOX_WINDOW_ID);
  if (!smtpAuthorization) {
    await untickWindowCheckbox(page, MAILBOX_WINDOW_ID, 'IsSmtpAuthorization');
  }
  for (const [fieldName, value] of Object.entries(fields)) {
    await typeIntoWindowField(page, MAILBOX_WINDOW_ID, fieldName, page.locator(`.form-field-${fieldName} input`), value);
  }
  const recordId = Number(await savedWindowRecordId(page, MAILBOX_WINDOW_ID));
  expect(recordId, 'the mailbox must be saved').toBeGreaterThan(0);
  return recordId;
}

async function expectStoredPassword(title, recordId, expectedValue) {
  await expectStoredColumnValue(title, { tableName: 'AD_MailBox', recordId, columnName: 'Password', expectedValue });
}

test.describe('EMail-Ausgangsserver (SMTP) — password in the grid and the Excel export', () => {
  test('SMTP password grid cell and Excel cell show ******** or nothing, never the typed password', async ({ page }) => {
    allure.epic('E0182: System Configuration');
    allure.tag('F9111: Reference Type Password');
    allure.tag('F9111');
    allure.story('A password appears in no grid cell and no Excel cell');
    allure.severity('critical');

    allure.description(`
## EMail-Ausgangsserver (SMTP) — the password column of the grid and of its Excel export

The SMTP password (AD_MailBox.Password) is a column of the window's grid, so it is also a column of
the grid's Excel export.

1. Create one mailbox with a typed SMTP password, and one without SMTP authorization and password
2. Both store what was typed (the password, resp. none)
3. Grid: the password cell shows exactly ******** resp. nothing; no cell holds the typed password
4. Excel export of both: the password cell is exactly ******** resp. empty; no cell holds the typed password
    `);

    test.setTimeout(240000);

    const unique = newUnique();
    const typedPassword = `smtp-${unique}`;
    const withPassword = { EMail: `pw-${unique}@example.com`, SMTPHost: 'smtp.example.com', UserName: `user-${unique}`, Password: typedPassword };
    const withoutPassword = { EMail: `nopw-${unique}@example.com`, SMTPHost: 'smtp.example.com' };

    const masterdata = await Backend.createMasterdata({
      request: { login: { user: { language: 'en_US' } } },
    });
    await loginWithMasterdataUser(page, masterdata.login.user);

    const withPasswordId = await test.step('Create a mailbox with a typed password', async () => createMailbox(page, withPassword));
    const withoutPasswordId = await test.step('Create a mailbox without SMTP authorization and password', async () =>
      createMailbox(page, withoutPassword, { smtpAuthorization: false })
    );
    await expectStoredPassword('the mailbox stores the typed password', withPasswordId, typedPassword);
    await expectStoredPassword('the mailbox without a password stores none', withoutPasswordId, null);

    let filePath;
    await test.step('Grid: the password cell shows ******** resp. nothing', async () => {
      filePath = await exportRecordsToExcel(page, MAILBOX_WINDOW_ID, [withPasswordId, withoutPasswordId]);
      const rowWith = page.locator(`tr[data-testid="table-row-${withPasswordId}"]`);
      const rowWithout = page.locator(`tr[data-testid="table-row-${withoutPasswordId}"]`);
      // positive control: the rows are the two test mailboxes
      await expect(rowWith.locator('td[data-cy="cell-EMail"]')).toHaveText(withPassword.EMail);
      await expect(rowWithout.locator('td[data-cy="cell-EMail"]')).toHaveText(withoutPassword.EMail);
      await expect(page.locator('th[data-testid="column-Password"]'), 'the grid has the Password column').toHaveCount(1);
      await expect.soft(rowWith.locator('td[data-cy="cell-Password"]'), 'password cell of the mailbox with a password').toHaveText(MASK);
      await expect.soft(rowWithout.locator('td[data-cy="cell-Password"]'), 'password cell of the mailbox without a password').toHaveText('');
      const gridHtml = await page.locator('table').first().evaluate((table) => table.outerHTML);
      expect.soft(gridHtml.includes(typedPassword), 'the grid (cell texts and titles) must not contain the typed password').toBe(false);
    });

    await test.step('Excel: the password cell is ******** resp. empty', async () => {
      const workbook = await readXlsx(filePath);
      const gridFieldNames = await fetchViewGridFieldNames(page, MAILBOX_WINDOW_ID);
      allure.attachment('Excel export', JSON.stringify({ gridFieldNames, sheets: workbook.sheets }), 'application/json');
      const emails = columnTextsByFieldName(workbook.sheets[0], gridFieldNames, 'EMail');
      const passwords = columnTextsByFieldName(workbook.sheets[0], gridFieldNames, 'Password');
      const passwordByEmail = Object.fromEntries(emails.map((email, index) => [email, passwords[index]]));
      // positive control: the export holds exactly the two test mailboxes
      expect(Object.keys(passwordByEmail).sort(), 'EMail column of the export').toEqual([withPassword.EMail, withoutPassword.EMail].sort());
      expect.soft(passwordByEmail[withPassword.EMail], 'Excel password cell of the mailbox with a password').toBe(MASK);
      expect.soft(passwordByEmail[withoutPassword.EMail], 'Excel password cell of the mailbox without a password').toBe('');
      expect.soft(workbook.allTexts.some((text) => text.includes(typedPassword)), 'no cell of the export may contain the typed password').toBe(false);
    });
  });
});
