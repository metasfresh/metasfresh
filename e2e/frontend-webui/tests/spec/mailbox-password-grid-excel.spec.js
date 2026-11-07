import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { loginWithMasterdataUser } from '../utils/LoginHelper';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT } from '../utils/common';
import { columnTextsByFieldName, exportRecordsToExcel, fetchViewGridFieldNames, readXlsx } from '../utils/ExcelParser';

/**
 * EMail-Ausgangsserver (SMTP) window (AD_Window_ID=540080, table AD_MailBox) — the SMTP password is a
 * column of the window's grid. A password must never show in clear there: the grid cell and the
 * Excel export ("Download selected", which writes exactly the grid's columns) show `********` for a
 * set password and nothing for an unset one, and the typed password appears in no cell.
 */

const MAILBOX_WINDOW_ID = 540080;
const MASK = '********';

const newUnique = () => `${Date.now().toString(36)}${Math.random().toString(36).slice(2, 6)}`;

/** The response to the PATCH the WebUI issues for `fieldName` on this window's document. */
function waitForFieldPatch(page, fieldName) {
  return page.waitForResponse(
    (response) =>
      response.request().method() === 'PATCH' &&
      response.url().includes(`/rest/api/window/${MAILBOX_WINDOW_ID}/`) &&
      (response.request().postData() || '').includes(`"path":"${fieldName}"`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
}

/** Types like a user: click into the input, press the keys, leave the field; waits for the PATCH. */
async function typeAndLeave(page, fieldName, text) {
  const input = page.locator(`.form-field-${fieldName} input`);
  await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  const patched = waitForFieldPatch(page, fieldName);
  await input.click();
  await input.pressSequentially(text);
  await input.press('Tab');
  const response = await patched;
  expect(response.ok(), `PATCH of ${fieldName} failed: HTTP ${response.status()}`).toBe(true);
}

/** Unticks a checkbox field the way a user does (click on its box); waits for the PATCH. */
async function untick(page, fieldName) {
  const checkbox = page.locator(`.form-field-${fieldName} input[type="checkbox"]`).first();
  await expect(checkbox, `${fieldName} starts ticked`).toBeChecked({ timeout: SLOW_ACTION_TIMEOUT });
  const patched = waitForFieldPatch(page, fieldName);
  await page.locator(`.form-field-${fieldName} label.input-checkbox`).first().click();
  const response = await patched;
  expect(response.ok(), `PATCH of ${fieldName} failed: HTTP ${response.status()}`).toBe(true);
  await expect(checkbox, `${fieldName} is unticked`).not.toBeChecked();
}

/**
 * Creates a mailbox in the window by typing its fields; returns its record id once it is saved.
 * Without SMTP authorization (`smtpAuthorization: false`) login and password are not mandatory.
 */
async function createMailbox(page, fields, { smtpAuthorization = true } = {}) {
  const draftCreated = page.waitForResponse(
    (response) => response.request().method() === 'PATCH' && response.url().includes(`/rest/api/window/${MAILBOX_WINDOW_ID}/NEW`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
  await page.goto(`${FRONTEND_BASE_URL}/window/${MAILBOX_WINDOW_ID}/NEW`);
  await draftCreated;
  if (!smtpAuthorization) {
    await untick(page, 'IsSmtpAuthorization');
  }
  for (const [fieldName, value] of Object.entries(fields)) {
    await typeAndLeave(page, fieldName, value);
  }
  await page.waitForURL((url) => !url.toString().includes('/NEW'), { timeout: SLOW_ACTION_TIMEOUT });
  const recordId = Number(page.url().match(new RegExp(`/window/${MAILBOX_WINDOW_ID}/(\\d+)`))[1]);
  expect(recordId, 'the mailbox must be saved').toBeGreaterThan(0);
  return recordId;
}

/** B2 stored-value expectation; the answer is pass/fail only and never reveals the stored value. */
async function expectStoredPassword(title, recordId, expectedValue) {
  await Backend.expect({
    title,
    records: { password: { tableName: 'AD_MailBox', recordId, columnName: 'Password', expectedValue } },
  });
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
