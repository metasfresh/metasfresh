import { randomBytes } from 'node:crypto';
import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { loginWithMasterdataUser } from '../utils/LoginHelper';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, flushPendingUiTasks } from '../utils/common';
import { WEBAPI_BASE_URL } from '../utils/WebAPIValidation';
import { columnTextsByFieldName, fetchViewGridFieldNames, readXlsx } from '../utils/ExcelParser';

/**
 * Shipper window (Lieferweg, 142) — carrier secrets are password fields.
 *
 * The carrier tabs of the shipper window hold the credentials of the DHL, nShift, DPD and GO!
 * interfaces. Those secrets must never reach the browser, the grid or an Excel export in clear
 * text, and saving a record must never overwrite them with the mask `********`:
 *
 * - the secret inputs are password inputs
 * - a grid column of a secret shows `********` (set) or nothing (not set); the Excel export carries no secret
 * - no window REST response or websocket frame carries a typed secret
 * - saving another field, or clicking into a secret and leaving it, keeps the stored secret
 * - typing replaces the secret with exactly the typed text (also texts with stars); clearing stores NULL
 * - a PATCH with the mask `********` keeps the stored secret
 *
 * One shipper (no gateway) is created per test; all carrier tabs of window 142 are always shown,
 * so each carrier config is created in its own tab and the secrets are typed through the UI.
 * Typing = click + key presses + leave the field (never fill()): fill() replaces the content and
 * would hide a missing select-on-focus.
 */

const SHIPPER_WINDOW_ID = 142; // Lieferweg / Shipper, table M_Shipper
const MASK = '********';

/** Carrier tabs of window 142, their table, secret fields and the fields a new record needs. */
const CARRIERS = {
  dhl: {
    tabId: 'AD_Tab-542055',
    tableName: 'DHL_Shipper_Config',
    gridMarkerColumn: 'applicationID',
    otherField: 'UserName',
    mandatory: {},
  },
  nshift: {
    tabId: 'AD_Tab-548455',
    tableName: 'Carrier_Config',
    gridMarkerColumn: 'Base_url',
    otherField: 'UserName',
    mandatory: {},
  },
  dpd: {
    tabId: 'AD_Tab-542101',
    tableName: 'DPD_Shipper_Config',
    gridMarkerColumn: 'LoginApiUrl',
    otherField: 'DelisID',
    mandatory: { LoginApiUrl: 'https://dpd.example.invalid/login' },
  },
  go: {
    tabId: 'AD_Tab-540982',
    tableName: 'GO_Shipper_Config',
    gridMarkerColumn: 'GO_URL',
    otherField: 'GO_RequestSenderId',
    mandatory: {
      GO_AuthUsername: 'go-user',
      GO_URL: 'https://go.example.invalid',
      GO_RequestUsername: 'go-request-user',
      GO_RequestSenderId: 'go-sender',
    },
  },
};

const newUnique = () => `${Date.now().toString(36)}${randomBytes(2).toString('hex')}`;

/** Synthetic, obviously fake secret values, unique per test. */
const newSecrets = (unique) => ({
  dhl: { ApplicationToken: `pw-dhl-token-${unique}`, Signature: `pw-dhl-sig-${unique}` },
  nshift: { Password: `pw-nshift-pw-${unique}`, Client_Secret: `pw-nshift-cs-${unique}` },
  dpd: { DelisPassword: `pw-dpd-pw-${unique}` },
  go: { GO_AuthPassword: `pw-go-pw-${unique}` },
});

function labelTest() {
  allure.epic('E0182: System Configuration');
  allure.tag('F9111: Reference Type Password');
  allure.tag('F9111');
  allure.story('Shipper window carrier secrets');
  allure.severity('critical');
}

/** Resolves on the PATCH of `fieldName` in the given tab (record view, advanced edit or grid). */
function waitForFieldPatch(page, tabId, fieldName) {
  return page.waitForResponse(
    (response) =>
      response.request().method() === 'PATCH' &&
      response.url().includes(`/${tabId}/`) &&
      (response.request().postData() || '').includes(`"path":"${fieldName}"`),
    { timeout: SLOW_ACTION_TIMEOUT }
  );
}

/** Types like a user: click into the input, press the keys, leave the field; waits for the PATCH. */
async function typeAndLeave(page, input, text, { tabId, fieldName }) {
  const patched = waitForFieldPatch(page, tabId, fieldName);
  await input.click();
  await input.pressSequentially(text);
  await input.press('Tab');
  const response = await patched;
  expect(response.ok(), `PATCH of ${fieldName} failed: HTTP ${response.status()}`).toBe(true);
}

/** Creates the shipper (no gateway), logs in and opens it. */
async function createShipperAndOpen(page) {
  const masterdata = await Backend.createMasterdata({
    request: {
      login: { user: { language: 'en_US', firstname: 'Password', lastname: 'Shipper' } },
      shippers: { shipper: { name: 'PwFieldsShipper' } },
    },
  });
  const shipperId = masterdata.shippers.shipper.shipperId;
  const shipperName = masterdata.shippers.shipper.name;
  expect(shipperId, 'masterdata must report the id of the created shipper').toBeTruthy();

  // A modal left with an invalid new record asks "Do you really want to leave?" - fail loudly, never confirm silently.
  const dialogs = [];
  page.on('dialog', async (dialog) => {
    dialogs.push(dialog.message());
    await dialog.dismiss();
  });

  await loginWithMasterdataUser(page, masterdata.login.user);
  await openShipper(page, shipperId);
  return { shipperId, shipperName, dialogs };
}

async function openShipper(page, shipperId) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${SHIPPER_WINDOW_ID}/${shipperId}`);
  await page.locator(`[data-testid="tab-${CARRIERS.dhl.tabId}"]`).waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

function tabPane(page) {
  return page.locator('.tab-content .tab-pane.active');
}

async function openTab(page, carrier) {
  await page.locator(`[data-testid="tab-${carrier.tabId}"]`).click();
  // A tab's own grid column tells its grid apart from the previous tab's (clicking the active tab reloads nothing).
  await tabPane(page).locator(`th[data-testid="column-${carrier.gridMarkerColumn}"]`).waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await tabPane(page).locator('[data-testid="add-new-record"]').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/** The tab grid's only row; returns its record id. */
async function onlyRowId(page) {
  const rows = tabPane(page).locator('tbody tr[data-testid^="table-row-"]');
  await expect(rows, 'the carrier tab must hold exactly one config record').toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
  const testId = await rows.first().getAttribute('data-testid');
  return Number(testId.replace('table-row-', ''));
}

async function closeModal(page) {
  const modal = page.locator('.panel-modal');
  await modal.getByTestId('process-modal-cancel-button').first().click();
  await modal.waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Creates the carrier's config record in its tab: "Add new", type the mandatory fields and the
 * given secrets, close. Returns the record id.
 */
async function createCarrierConfig(page, carrierKey, secrets) {
  const carrier = CARRIERS[carrierKey];
  return await test.step(`Create the ${carrierKey} config with its secrets`, async () => {
    await openTab(page, carrier);
    await tabPane(page).locator('[data-testid="add-new-record"]').click();
    const modal = page.locator('.panel-modal');
    await modal.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    for (const [fieldName, value] of Object.entries({ ...carrier.mandatory, ...secrets })) {
      const input = modal.locator(`.form-field-${fieldName} input`);
      await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await typeAndLeave(page, input, value, { tabId: carrier.tabId, fieldName });
    }
    await closeModal(page);
    return await onlyRowId(page);
  });
}

/** Opens the tab's only record in the advanced-edit modal (row context menu). */
async function openRecordView(page, carrierKey) {
  const carrier = CARRIERS[carrierKey];
  await openTab(page, carrier);
  await onlyRowId(page);
  const row = tabPane(page).locator('tbody tr[data-testid^="table-row-"]').first();
  await row.click();
  await row.locator('td[data-cy^="cell-"]').first().click({ button: 'right' });
  const contextMenu = page.locator('.context-menu.context-menu-open');
  await contextMenu.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  await contextMenu
    .locator('.context-menu-item')
    .filter({ has: page.locator('i.meta-icon-edit') })
    .filter({ has: page.locator('span.tooltip-inline') })
    .click();
  const modal = page.locator('.panel-modal');
  await modal.locator(`.form-field-${carrier.otherField} input`).waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  return modal;
}

/** server-side stored-value expectation; the answer is pass/fail only and never reveals the stored value. */
async function expectStored(title, entries) {
  const records = {};
  entries.forEach(({ tableName, recordId, columnName, expectedValue }) => {
    records[`${tableName}.${columnName}#${recordId}`] = { tableName, recordId, columnName, expectedValue };
  });
  await Backend.expect({ title, records });
}

test.describe('Shipper window — carrier secrets are password fields', () => {
  test('DHL application token, DHL password and nShift client secret are password inputs', async ({ page }) => {
    labelTest();
    test.setTimeout(180000);
    const { dialogs } = await createShipperAndOpen(page);

    await test.step('DHL record view', async () => {
      await openTab(page, CARRIERS.dhl);
      await tabPane(page).locator('[data-testid="add-new-record"]').click();
      const modal = page.locator('.panel-modal');
      await modal.locator('.form-field-ApplicationToken input').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect.soft(modal.locator('.form-field-ApplicationToken input'), 'DHL ApplicationToken').toHaveAttribute('type', 'password');
      await expect.soft(modal.locator('.form-field-Signature input'), 'DHL Signature (Kennwort)').toHaveAttribute('type', 'password');
      await closeModal(page);
    });

    await test.step('nShift record view', async () => {
      await openTab(page, CARRIERS.nshift);
      await tabPane(page).locator('[data-testid="add-new-record"]').click();
      const modal = page.locator('.panel-modal');
      await modal.locator('.form-field-Client_Secret input').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      await expect.soft(modal.locator('.form-field-Client_Secret input'), 'nShift Client_Secret').toHaveAttribute('type', 'password');
      await closeModal(page);
    });
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  test('grid: a secret column shows the mask or nothing, a secret outside the grid appears in no cell', async ({ page }) => {
    labelTest();
    test.setTimeout(300000);
    const { shipperId, dialogs } = await createShipperAndOpen(page);
    const secrets = newSecrets(newUnique());

    // DHL Signature stays unset: its grid cell must then be empty.
    await createCarrierConfig(page, 'dhl', { ApplicationToken: secrets.dhl.ApplicationToken });
    await createCarrierConfig(page, 'nshift', secrets.nshift);
    await createCarrierConfig(page, 'dpd', secrets.dpd);
    await createCarrierConfig(page, 'go', secrets.go);
    const typedSecrets = [secrets.dhl.ApplicationToken, ...Object.values(secrets.nshift), secrets.dpd.DelisPassword, secrets.go.GO_AuthPassword];

    await openShipper(page, shipperId);

    // in grid = Y per the window layout: DHL ApplicationToken + Signature (fallback), nShift Password, DPD DelisPassword
    const inGrid = [
      { carrierKey: 'dhl', column: 'ApplicationToken', expected: MASK },
      { carrierKey: 'dhl', column: 'Signature', expected: '' },
      { carrierKey: 'nshift', column: 'Password', expected: MASK },
      { carrierKey: 'dpd', column: 'DelisPassword', expected: MASK },
    ];
    // in grid = N per the window layout: nShift Client_Secret, GO GO_AuthPassword
    const notInGrid = [
      { carrierKey: 'nshift', column: 'Client_Secret' },
      { carrierKey: 'go', column: 'GO_AuthPassword' },
    ];

    for (const carrierKey of Object.keys(CARRIERS)) {
      await test.step(`${carrierKey} tab grid`, async () => {
        await openTab(page, CARRIERS[carrierKey]);
        await onlyRowId(page);
        for (const { column, expected } of inGrid.filter((entry) => entry.carrierKey === carrierKey)) {
          await expect.soft(tabPane(page).locator(`th[data-testid="column-${column}"]`), `${carrierKey} grid has the ${column} column`).toHaveCount(1);
          await expect.soft(tabPane(page).locator(`td[data-cy="cell-${column}"]`), `${carrierKey} ${column} cell`).toHaveText(expected);
        }
        for (const { column } of notInGrid.filter((entry) => entry.carrierKey === carrierKey)) {
          await expect.soft(tabPane(page).locator(`th[data-testid="column-${column}"]`), `${carrierKey} grid has no ${column} column`).toHaveCount(0);
        }
        const gridHtml = await tabPane(page).locator('table').evaluate((table) => table.outerHTML);
        for (const secret of typedSecrets) {
          expect.soft(gridHtml.includes(secret), `${carrierKey} grid (cell texts and titles) must not contain a typed secret`).toBe(false);
        }
      });
    }
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  test('Excel: the shipper export carries no typed secret; carrier tab grids offer no export', async ({ page }) => {
    labelTest();
    test.setTimeout(300000);
    const { shipperId, shipperName, dialogs } = await createShipperAndOpen(page);
    const secrets = newSecrets(newUnique());

    await createCarrierConfig(page, 'dhl', secrets.dhl);
    await createCarrierConfig(page, 'nshift', secrets.nshift);
    await createCarrierConfig(page, 'dpd', secrets.dpd);
    await createCarrierConfig(page, 'go', secrets.go);
    const typedSecrets = Object.values(secrets).flatMap((carrierSecrets) => Object.values(carrierSecrets));

    // The export item of the actions menu; the list-view step below proves this selector finds it when offered.
    const exportLink = page.locator('.subheader-container a.subheader-item[href*="/export/excel"]');

    await test.step('Carrier tab grids offer no Excel export (export runs on window views only)', async () => {
      await openShipper(page, shipperId);
      for (const carrierKey of Object.keys(CARRIERS)) {
        await openTab(page, CARRIERS[carrierKey]);
        await onlyRowId(page);
        const row = tabPane(page).locator('tbody tr[data-testid^="table-row-"]').first();
        await expect(async () => {
          await row.click();
          await expect(row).toHaveClass(/row-selected/, { timeout: 2000 });
        }).toPass({ timeout: SLOW_ACTION_TIMEOUT });
        await page.keyboard.press('Alt+1');
        await page.locator('.subheader-container').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
        // positive control: the menu rendered its items - the "about this document" item is offered on every record view
        await expect(
          page.locator('.subheader-container .subheader-item').filter({ has: page.locator('i.meta-icon-more') }),
          `${carrierKey}: the actions menu must show its items`
        ).toHaveCount(1);
        await expect(exportLink, `${carrierKey}: the actions menu with a tab row selected offers no Excel export`).toHaveCount(0);
        await page.keyboard.press('Escape');
        await page.locator('.subheader-container').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
      }
    });

    let filePath;
    await test.step('Export the shipper from the shipper list view', async () => {
      // The shipper list is paged and holds every other run's shippers: open a view of the test shipper only.
      const viewResponse = await page.request.post(`${WEBAPI_BASE_URL}/documentView/${SHIPPER_WINDOW_ID}`, {
        data: { windowId: String(SHIPPER_WINDOW_ID), viewType: 'grid', filterOnlyIds: [shipperId] },
      });
      expect(viewResponse.ok(), `view of the test shipper: HTTP ${viewResponse.status()}`).toBe(true);
      const { viewId } = await viewResponse.json();
      await page.goto(`${FRONTEND_BASE_URL}/window/${SHIPPER_WINDOW_ID}?viewId=${viewId}`);
      const row = page.locator(`tr[data-testid="table-row-${shipperId}"]`);
      await row.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      // A click right after the grid renders does not always land as a selection - assert it, retrying.
      await expect(async () => {
        await row.click();
        await expect(row, 'the test shipper must be selected for "Download selected"').toHaveClass(/row-selected/, { timeout: 2000 });
      }).toPass({ timeout: SLOW_ACTION_TIMEOUT });
      // the actions menu shortcut keeps the selection (a click outside the table may clear it)
      await page.keyboard.press('Alt+1');
      await exportLink.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      expect(await exportLink.getAttribute('href'), 'the export must hold the test shipper').toContain(String(shipperId));
      const download = page.waitForEvent('download', { timeout: SLOW_ACTION_TIMEOUT });
      await exportLink.click();
      filePath = await (await download).path();
    });

    await test.step('The export holds the shipper and no typed secret', async () => {
      const workbook = await readXlsx(filePath);
      allure.attachment('Excel export sheets', JSON.stringify(workbook.sheets.map((sheet) => ({ name: sheet.name, rows: sheet.rows.length }))), 'application/json');
      const gridFieldNames = await fetchViewGridFieldNames(page, SHIPPER_WINDOW_ID);
      expect(columnTextsByFieldName(workbook.sheets[0], gridFieldNames, 'Name'), 'Name column of the export').toEqual([shipperName]);
      for (const secret of typedSecrets) {
        expect(workbook.allTexts.some((text) => text.includes(secret)), 'the export must not contain a typed secret').toBe(false);
      }
      // The shipper grid has no password column (the secrets live on the carrier tabs), so "no typed secret" is the whole check.
    });
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  test('no window REST response and no websocket frame carries a typed secret', async ({ page }) => {
    labelTest();
    test.setTimeout(300000);
    const { shipperId, dialogs } = await createShipperAndOpen(page);
    const secrets = newSecrets(newUnique());
    await createCarrierConfig(page, 'dhl', secrets.dhl);
    await createCarrierConfig(page, 'nshift', secrets.nshift);
    await createCarrierConfig(page, 'dpd', secrets.dpd);
    await createCarrierConfig(page, 'go', secrets.go);
    const typedSecrets = Object.values(secrets).flatMap((carrierSecrets) => Object.values(carrierSecrets));

    // Listen from here on: the secrets are stored; the typing above legitimately sent them.
    const bodies = [];
    const pendingBodies = [];
    // Only STOMP MESSAGE frames carry data; SockJS framing ("o" open, "h" heartbeat) and the STOMP
    // CONNECTED frame do not. SockJS wraps STOMP frames in a JSON array: a["MESSAGE\n...
    const isStompMessage = (text) => /(^|\["|",")MESSAGE\\n/.test(text) || /^MESSAGE\n/.test(text);
    const observed = { windowResponses: 0, stompMessages: 0 };
    page.on('response', (response) => {
      const url = response.url();
      // /stomp/ = the SockJS endpoint: on an HTTP fallback transport its messages arrive as responses
      const isWindow = url.includes('/rest/api/window/');
      const isStomp = url.includes('/stomp/');
      if (!isWindow && !isStomp) {
        return;
      }
      pendingBodies.push(
        response
          .text()
          .then((text) => {
            bodies.push({ source: `${response.request().method()} ${url}`, text });
            if (isWindow) {
              observed.windowResponses++;
            } else if (isStompMessage(text)) {
              observed.stompMessages++;
            }
          })
          .catch(() => {})
      );
    });
    page.on('websocket', (webSocket) => {
      webSocket.on('framereceived', ({ payload }) => {
        const text = String(payload);
        bodies.push({ source: `websocket ${webSocket.url()}`, text });
        if (isStompMessage(text)) {
          observed.stompMessages++;
        }
      });
    });

    await test.step('Open the shipper and every carrier tab', async () => {
      await openShipper(page, shipperId);
      for (const carrierKey of Object.keys(CARRIERS)) {
        await openTab(page, CARRIERS[carrierKey]);
        await onlyRowId(page);
      }
    });

    for (const carrierKey of Object.keys(CARRIERS)) {
      await test.step(`Open the ${carrierKey} record view, edit another field, save`, async () => {
        const modal = await openRecordView(page, carrierKey);
        const carrier = CARRIERS[carrierKey];
        await typeAndLeave(page, modal.locator(`.form-field-${carrier.otherField} input`), '-x', {
          tabId: carrier.tabId,
          fieldName: carrier.otherField,
        });
        await closeModal(page);
      });
    }

    await flushPendingUiTasks(page);
    await Promise.all(pendingBodies);
    // positive controls: "no secret seen" means something only if the traffic was captured
    expect(observed.windowResponses, 'window REST responses must have been observed').toBeGreaterThan(0);
    expect(observed.stompMessages, 'STOMP MESSAGE frames (websocket or SockJS transport) must have been observed').toBeGreaterThan(0);
    console.log(`[INFO] observed traffic: ${JSON.stringify(observed)}`);
    const leaks = bodies
      .filter(({ text }) => typedSecrets.some((secret) => text.includes(secret)))
      .map(({ source }) => source);
    expect(leaks, 'responses/frames that carry a typed secret').toEqual([]);
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  test('saving another field, or clicking into a secret and leaving it, keeps the stored secrets', async ({ page }) => {
    labelTest();
    test.setTimeout(180000);
    const { dialogs } = await createShipperAndOpen(page);
    const secrets = newSecrets(newUnique());
    const recordId = await createCarrierConfig(page, 'dhl', secrets.dhl);
    const stored = (title) =>
      expectStored(title, [
        { tableName: 'DHL_Shipper_Config', recordId, columnName: 'ApplicationToken', expectedValue: secrets.dhl.ApplicationToken },
        { tableName: 'DHL_Shipper_Config', recordId, columnName: 'Signature', expectedValue: secrets.dhl.Signature },
      ]);

    await test.step('Edit another field and save', async () => {
      const modal = await openRecordView(page, 'dhl');
      await typeAndLeave(page, modal.locator('.form-field-UserName input'), 'pw-user', { tabId: CARRIERS.dhl.tabId, fieldName: 'UserName' });
      await closeModal(page);
      await stored('secrets unchanged after saving another field');
      // positive control: the other field was saved (UserName starts empty)
      await expectStored('the other field was saved', [
        { tableName: 'DHL_Shipper_Config', recordId, columnName: 'UserName', expectedValue: 'pw-user' },
      ]);
    });

    await test.step('Click into each secret and leave it without typing', async () => {
      const modal = await openRecordView(page, 'dhl');
      for (const fieldName of ['ApplicationToken', 'Signature']) {
        const input = modal.locator(`.form-field-${fieldName} input`);
        await input.click();
        await input.press('Tab');
      }
      await flushPendingUiTasks(page);
      await closeModal(page);
      await stored('secrets unchanged after click in + out');
    });
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  test('typing a new secret stores exactly the typed text', async ({ page }) => {
    labelTest();
    test.setTimeout(180000);
    const { dialogs } = await createShipperAndOpen(page);
    const unique = newUnique();
    const secrets = newSecrets(unique);
    const recordId = await createCarrierConfig(page, 'dhl', secrets.dhl);

    const typed = `pw-dhl-token-new-${unique}`;
    const modal = await openRecordView(page, 'dhl');
    await typeAndLeave(page, modal.locator('.form-field-ApplicationToken input'), typed, { tabId: CARRIERS.dhl.tabId, fieldName: 'ApplicationToken' });
    await closeModal(page);
    await expectStored('typed value stored exactly', [
      { tableName: 'DHL_Shipper_Config', recordId, columnName: 'ApplicationToken', expectedValue: typed },
    ]);
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  test('clearing a secret stores NULL', async ({ page }) => {
    labelTest();
    test.setTimeout(180000);
    const { dialogs } = await createShipperAndOpen(page);
    const secrets = newSecrets(newUnique());
    const recordId = await createCarrierConfig(page, 'dhl', secrets.dhl);

    const modal = await openRecordView(page, 'dhl');
    const input = modal.locator('.form-field-ApplicationToken input');
    const patched = waitForFieldPatch(page, CARRIERS.dhl.tabId, 'ApplicationToken');
    await input.click();
    await input.press('ControlOrMeta+a');
    await input.press('Backspace');
    await input.press('Tab');
    expect((await patched).ok(), 'PATCH of the cleared ApplicationToken').toBe(true);
    await closeModal(page);
    await expectStored('cleared secret stored as NULL', [
      { tableName: 'DHL_Shipper_Config', recordId, columnName: 'ApplicationToken', expectedValue: null },
    ]);
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  for (const starValue of ['*', 'ab*c', '*********']) {
    test(`stars: typing "${starValue}" stores exactly that text`, async ({ page }) => {
      labelTest();
      test.setTimeout(180000);
      const { dialogs } = await createShipperAndOpen(page);
      const secrets = newSecrets(newUnique());
      const recordId = await createCarrierConfig(page, 'dhl', secrets.dhl);

      const modal = await openRecordView(page, 'dhl');
      await typeAndLeave(page, modal.locator('.form-field-ApplicationToken input'), starValue, { tabId: CARRIERS.dhl.tabId, fieldName: 'ApplicationToken' });
      await closeModal(page);
      await expectStored(`"${starValue}" stored exactly`, [
        { tableName: 'DHL_Shipper_Config', recordId, columnName: 'ApplicationToken', expectedValue: starValue },
      ]);
      expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
    });
  }

  test('a PATCH with the mask keeps the stored secrets', async ({ page }) => {
    labelTest();
    test.setTimeout(240000);
    const { shipperId, dialogs } = await createShipperAndOpen(page);
    const secrets = newSecrets(newUnique());
    const dhlRecordId = await createCarrierConfig(page, 'dhl', secrets.dhl);
    const nshiftRecordId = await createCarrierConfig(page, 'nshift', { Client_Secret: secrets.nshift.Client_Secret });

    // Sent directly, not typed: typed through the UI the mask cannot reach the server unchanged.
    // The same PATCH also sets UserName: its stored value proves the PATCH was persisted, so
    // "secret unchanged" cannot hold merely because nothing was saved.
    const userName = `pw-patch-user-${newUnique()}`;
    const patchWithMask = async (carrierKey, recordId, fieldNames) => {
      const response = await page.request.patch(
        `${WEBAPI_BASE_URL}/window/${SHIPPER_WINDOW_ID}/${shipperId}/${CARRIERS[carrierKey].tabId}/${recordId}`,
        {
          data: [
            ...fieldNames.map((fieldName) => ({ op: 'replace', path: fieldName, value: MASK })),
            { op: 'replace', path: 'UserName', value: userName },
          ],
        }
      );
      expect(response.ok(), `PATCH with the mask: HTTP ${response.status()}`).toBe(true);
    };
    await patchWithMask('dhl', dhlRecordId, ['ApplicationToken', 'Signature']);
    await patchWithMask('nshift', nshiftRecordId, ['Client_Secret']);

    await expectStored('secrets unchanged and the rest of the PATCH stored', [
      { tableName: 'DHL_Shipper_Config', recordId: dhlRecordId, columnName: 'UserName', expectedValue: userName },
      { tableName: 'Carrier_Config', recordId: nshiftRecordId, columnName: 'UserName', expectedValue: userName },
      { tableName: 'DHL_Shipper_Config', recordId: dhlRecordId, columnName: 'ApplicationToken', expectedValue: secrets.dhl.ApplicationToken },
      { tableName: 'DHL_Shipper_Config', recordId: dhlRecordId, columnName: 'Signature', expectedValue: secrets.dhl.Signature },
      { tableName: 'Carrier_Config', recordId: nshiftRecordId, columnName: 'Client_Secret', expectedValue: secrets.nshift.Client_Secret },
    ]);
    expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
  });

  test.describe('grid inline edit (DHL ApplicationToken cell)', () => {
    async function openCellEditor(page) {
      await openTab(page, CARRIERS.dhl);
      await onlyRowId(page);
      const cell = tabPane(page).locator('td[data-cy="cell-ApplicationToken"]');
      await cell.dblclick();
      const editor = cell.locator('input');
      await editor.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
      return editor;
    }

    test('grid: clicking into the secret cell and leaving it keeps the stored secret', async ({ page }) => {
      labelTest();
      test.setTimeout(180000);
      const { shipperId, dialogs } = await createShipperAndOpen(page);
      const secrets = newSecrets(newUnique());
      const recordId = await createCarrierConfig(page, 'dhl', secrets.dhl);
      await openShipper(page, shipperId);

      const editor = await openCellEditor(page);
      await editor.press('Tab');
      await page.keyboard.press('Escape');
      await flushPendingUiTasks(page);
      await expectStored('secret unchanged after entering and leaving the grid cell', [
        { tableName: 'DHL_Shipper_Config', recordId, columnName: 'ApplicationToken', expectedValue: secrets.dhl.ApplicationToken },
      ]);
      expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
    });

    test('grid: typing into the secret cell stores exactly the typed text', async ({ page }) => {
      labelTest();
      test.setTimeout(180000);
      const unique = newUnique();
      const { shipperId, dialogs } = await createShipperAndOpen(page);
      const secrets = newSecrets(unique);
      const recordId = await createCarrierConfig(page, 'dhl', secrets.dhl);
      await openShipper(page, shipperId);

      const typed = `pw-grid-token-${unique}`;
      const editor = await openCellEditor(page);
      const patched = waitForFieldPatch(page, CARRIERS.dhl.tabId, 'ApplicationToken');
      await editor.pressSequentially(typed);
      await editor.press('Tab');
      expect((await patched).ok(), 'PATCH of the grid cell').toBe(true);
      await page.keyboard.press('Escape');
      await expectStored('typed grid value stored exactly', [
        { tableName: 'DHL_Shipper_Config', recordId, columnName: 'ApplicationToken', expectedValue: typed },
      ]);
      expect(dialogs, 'no leave-confirmation may come up').toEqual([]);
    });
  });
});
