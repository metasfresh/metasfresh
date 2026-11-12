import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { getFieldData } from '../utils/WebAPIValidation';
import { EvidenceCapture } from '../utils/EvidenceCapture';
import { POS_INVOICE_CARD_SETTLEMENT_WINDOW_ID as WINDOW_ID } from '../utils/WindowIds';

/**
 * Back-office window "POS invoice card settlements" (C_POS_InvoiceCardSettlement, window 542201): the card
 * settlements of open invoices that the till creates. A back-office user finds settlements in the grid, narrows it
 * by status, opens one, and works on a stuck one with the three processes (retry booking, mark resolved manually,
 * mark refunded), each offered only for the statuses it applies to.
 *
 * The settlements are created in the statuses the real flow reaches only through a SumUp transaction and an
 * asynchronous booking (see the `posInvoiceCardSettlements` masterdata: SU and CR are booked by the production
 * booking, so they carry their payment; PE, UN and AP are not offered, the background SumUp sweep changes them).
 * Everything asserted is identified by
 * ColumnName / process value / record id, never by a localized text, so the spec is run in German (the customer's
 * language) here and does not depend on it.
 *
 * Set EVIDENCE_DIR (together with UAT_CAPTURE=1, a 1920x1080 window) to also save one still per surface, see
 * EvidenceCapture.
 */

const LANGUAGE = 'de_DE';

const PROCESS_RETRY_BOOKING = 'C_POS_InvoiceCardSettlement_RetryBooking';
const PROCESS_MARK_RESOLVED = 'C_POS_InvoiceCardSettlement_MarkResolvedManually';
const PROCESS_MARK_REFUNDED = 'C_POS_InvoiceCardSettlement_MarkRefunded';
const ALL_PROCESSES = [PROCESS_RETRY_BOOKING, PROCESS_MARK_RESOLVED, PROCESS_MARK_REFUNDED];

const EXPECTED_GRID_COLUMNS = [
  'C_Invoice_ID',
  'C_BPartner_ID',
  'Amount',
  'C_Currency_ID',
  'Status',
  'IsAbandoned',
  'ErrorMsg',
  'C_Payment_ID',
  'SUMUP_Transaction_ID',
  'Created',
  'Cashier_ID',
  'C_POS_ID',
  'Note',
  'AD_Org_ID',
];

/** Settlement id -> its row in the grid. */
const gridRow = (page, id) => page.locator(`tbody tr[data-testid="table-row-${id}"]`);

/** A cell of the settlement's grid row, by the column's ColumnName. */
const cell = (page, id, columnName) => gridRow(page, id).locator(`[data-cy="cell-${columnName}"]`);

/** Waits until the element is visible AND inside the viewport, as a reader of a screenshot needs it. */
async function expectInViewport(locator, { wholeWidth = true } = {}) {
  await locator.scrollIntoViewIfNeeded();
  await expect(locator).toBeVisible();
  await expect(async () => {
    const box = await locator.boundingBox();
    const viewport = locator.page().viewportSize();
    expect(box, 'bounding box').not.toBeNull();
    expect(box.x).toBeGreaterThanOrEqual(0);
    expect(box.y).toBeGreaterThanOrEqual(0);
    // a grid row is wider than the window: its start must be in view, the columns are asserted on their own
    expect(wholeWidth ? box.x + box.width : box.x + 1).toBeLessThanOrEqual(viewport.width);
    expect(box.y + box.height).toBeLessThanOrEqual(viewport.height);
  }).toPass({ timeout: 5000 });
}

/**
 * Opens the header's actions menu (the "..." button, then the Actions list) and returns this window's processes in it.
 * A single-row view hides a process that does not apply to the status; a grid shows it greyed out ("disabled").
 *
 * @returns {Promise<{enabled: string[], disabled: string[]}>} process values, sorted
 */
async function openActionsAndListProcesses(page) {
  await page.locator('.header-left-side .btn-square.btn-header').click();
  await page.locator('.subheader-container [data-testid^="action-"]').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
  // the list is loaded asynchronously: wait until it stopped changing, so the menu is painted completely
  const actionItems = page.locator('.subheader-container [data-testid^="action-"]');
  let previousCount = -1;
  await expect(async () => {
    const count = await actionItems.count();
    const isStable = count === previousCount;
    previousCount = count;
    expect(isStable, 'the actions list is still loading').toBe(true);
  }).toPass({ timeout: SLOW_ACTION_TIMEOUT, intervals: [400] });
  const items = await page.locator('.subheader-container [data-testid^="action-"]').evaluateAll((els) =>
    els.map((el) => ({ value: el.getAttribute('data-testid').substring('action-'.length), disabled: el.classList.contains('subheader-item-disabled') }))
  );
  const processes = items.filter((item) => ALL_PROCESSES.includes(item.value));
  return {
    enabled: processes.filter((item) => !item.disabled).map((item) => item.value).sort(),
    disabled: processes.filter((item) => item.disabled).map((item) => item.value).sort(),
  };
}

async function closeActionsMenu(page) {
  await page.keyboard.press('Escape');
  await expect(page.locator('.subheader-container')).toBeHidden();
}

async function openSingleRow(page, id) {
  await page.goto(`${FRONTEND_BASE_URL}/window/${WINDOW_ID}/${id}`);
  await page.locator('.form-field-Status').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
}

async function statusKeyOf(id) {
  const field = await getFieldData(WINDOW_ID, id, 'Status');
  return field.value && typeof field.value === 'object' ? field.value.key : field.value;
}

test('a back-office user finds card settlements by status and works on them with the processes that fit the status', async ({ page }) => {
  allure.epic('E0500: Point of Sale (POS)');
  allure.tag('F18030: POS Checkout');
  allure.tag('F18030');
  allure.story('POS invoice card settlements window');
  allure.severity('normal');
  test.setTimeout(420000);

  // The customer's real case is one sales invoice per till payment attempt. SU pays the first invoice, so a second
  // settlement of it (CR) is customer credit, as in the real flow; the unique blocking index allows one blocking
  // settlement (PE/UN/AP/BE, not abandoned) per invoice, hence BE has an invoice of its own (AB is not blocking).
  const run = Date.now().toString(36);
  const invoiceOf = (salesOrder) => ({ salesOrder });
  const order = { bpartner: 'C1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', paymentTerm: '1000', lines: [{ product: 'P1', qty: 1 }] };
  const masterdata = await Backend.createMasterdata({
    request: {
      login: { user: { language: LANGUAGE } },
      bpartners: { C1: { isSoPriceList: true, invoiceRule: 'I' } },
      products: { P1: { prices: [{ price: 10.0, taxCategory: 'Reduced' }] } },
      warehouses: { wh: {} },
      posTerminals: { T1: { priceListCurrency: 'EUR', isTaxIncluded: true, paymentMethods: ['CASH', 'CARD'] } },
      salesOrders: { SO_PAID: order, SO_BE: order },
      invoices: { INV_PAID: invoiceOf('SO_PAID'), INV_BE: invoiceOf('SO_BE') },
      posInvoiceCardSettlements: {
        SU: { posTerminal: 'T1', invoice: 'INV_PAID', cashier: 'user', status: 'SU' },
        CR: { posTerminal: 'T1', invoice: 'INV_PAID', cashier: 'user', status: 'CR', note: `E2E ${run} credit refund pending` },
        // AB before BE: a settlement is created as PE, which the blocking index refuses next to a BE one of the same invoice
        AB: { posTerminal: 'T1', invoice: 'INV_BE', cashier: 'user', status: 'AB', isAbandoned: true },
        BE: { posTerminal: 'T1', invoice: 'INV_BE', cashier: 'user', status: 'BE', errorMsg: `E2E ${run} booking failed`, bookingAttempts: 2 },
      },
    },
  });
  allure.attachment('Test Data', JSON.stringify(masterdata, null, 2), 'application/json');
  const ids = Object.fromEntries(Object.entries(masterdata.posInvoiceCardSettlements).map(([name, settlement]) => [name, settlement.id]));
  const SETTLEMENTS = ['CR', 'SU', 'BE', 'AB'];

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  // the fixture created each settlement in the status its name says; the booked ones carry the payment the booking created
  for (const name of SETTLEMENTS) {
    expect(await statusKeyOf(ids[name]), `status of the ${name} settlement`).toBe(name);
  }
  for (const name of ['SU', 'CR']) {
    expect((await getFieldData(WINDOW_ID, ids[name], 'C_Payment_ID')).value, `payment of the ${name} settlement`).toBeTruthy();
  }
  expect((await getFieldData(WINDOW_ID, ids.BE, 'C_Payment_ID')).value, 'a booking error without payment').toBeFalsy();

  let crStatusCaption;
  let abStatusCaption;

  await test.step('The grid lists the settlements with all its columns', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${WINDOW_ID}`);
    await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    for (const settlementName of SETTLEMENTS) {
      await expectInViewport(gridRow(page, ids[settlementName]), { wholeWidth: false });
    }

    const headers = await page.locator('th[data-testid^="column-"]').evaluateAll((els) => els.map((el) => el.getAttribute('data-testid').substring('column-'.length)));
    expect(headers).toEqual(EXPECTED_GRID_COLUMNS);

    crStatusCaption = (await cell(page, ids.CR, 'Status').innerText()).trim();
    expect(crStatusCaption).not.toBe('');
    abStatusCaption = (await cell(page, ids.AB, 'Status').innerText()).trim();
    const beStatusCaption = (await cell(page, ids.BE, 'Status').innerText()).trim();
    expect(beStatusCaption, 'the grid shows each settlement\'s own status').not.toBe(crStatusCaption);

    await EvidenceCapture.still(page, 'de_DE-01a-grid-columns-left.png', [page.locator('th[data-testid="column-Status"]'), page.locator('th[data-testid="column-IsAbandoned"]'), ...SETTLEMENTS.map((name) => cell(page, ids[name], 'Status'))], { inset: true });
    // the columns right of the window's width: scroll the grid to its end
    await page.locator('th[data-testid="column-Note"]').scrollIntoViewIfNeeded();
    await page.locator('th[data-testid="column-AD_Org_ID"]').scrollIntoViewIfNeeded();
    await EvidenceCapture.still(page, 'de_DE-01b-grid-columns-right.png', [
      page.locator('th[data-testid="column-C_POS_ID"]'),
      page.locator('th[data-testid="column-Note"]'),
      page.locator('th[data-testid="column-AD_Org_ID"]'),
      cell(page, ids.CR, 'Note'),
    ], { inset: true });
  });

  await test.step('The Status filter narrows the grid to the settlements in that status', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${WINDOW_ID}`);
    await gridRow(page, ids.CR).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });

    await page.locator('.filter-wrapper .btn-filter').first().click();
    await page.locator('.filter-option-default').click();
    const statusField = page.locator('.filters-overlay .form-field-Status .select-dropdown');
    await statusField.click();
    const option = page.locator('.input-dropdown-list .input-dropdown-list-option').getByText(crStatusCaption, { exact: true });
    await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    // the status list also offers the settlements released as abandoned (AB); the still shows that entry
    const abOption = page.locator('.input-dropdown-list .input-dropdown-list-option').getByText(abStatusCaption, { exact: true });
    await expect(abOption).toHaveCount(1);
    await EvidenceCapture.still(page, 'de_DE-02a-filter-status-list.png', [abOption], { inset: true });
    await option.click();
    await EvidenceCapture.still(page, 'de_DE-02b-filter-status-selected.png', [page.locator('.filters-overlay .form-field-Status')], { inset: true });
    await page.locator('[data-testid="filter-apply-button"]').click();

    // narrowed: the BE, AB and SU settlements are gone, the CR one stays, and every shown row carries that status
    await gridRow(page, ids.BE).waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    await expectInViewport(gridRow(page, ids.CR), { wholeWidth: false });
    await expect(gridRow(page, ids.AB)).toHaveCount(0);
    await expect(gridRow(page, ids.SU)).toHaveCount(0);
    const shownStatuses = await page.locator('tbody tr[data-testid^="table-row-"] [data-cy="cell-Status"]').evaluateAll((els) => [...new Set(els.map((el) => el.textContent.trim()))]);
    expect(shownStatuses).toEqual([crStatusCaption]);
    expect(await page.locator('tbody tr[data-testid^="table-row-"]').count(), 'rows after the filter').toBeGreaterThan(0);
    await EvidenceCapture.still(page, 'de_DE-02c-filtered-grid.png', [page.locator('.filter-wrapper .btn-filter'), cell(page, ids.CR, 'Status')]);
  });

  await test.step('A settlement opens in the single-row view with its fields', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${WINDOW_ID}`);
    await gridRow(page, ids.CR).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    await gridRow(page, ids.CR).dblclick();
    await page.waitForURL(new RegExp(`/window/${WINDOW_ID}/${ids.CR}$`), { timeout: SLOW_ACTION_TIMEOUT });
    for (const field of ['C_Invoice_ID', 'C_BPartner_ID', 'Amount', 'C_Currency_ID', 'Status', 'Note', 'C_POS_ID', 'Cashier_ID', 'IsAbandoned']) {
      await expectInViewport(page.locator(`.form-field-${field}`).first());
    }
    await EvidenceCapture.still(page, 'de_DE-03-single-row.png', [
      page.locator('.form-field-Status').first(),
      page.locator('.form-field-Note').first(),
      page.locator('.form-field-IsAbandoned').first(),
    ]);
  });

  await test.step('Advanced edit (Alt+E) opens the settlement in a modal with its fields', async () => {
    await page.keyboard.press('Alt+e');
    const modal = page.locator('.panel-modal');
    await modal.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    for (const field of ['C_Invoice_ID', 'Amount', 'Status', 'Note', 'C_POS_ID', 'Cashier_ID', 'IsAbandoned']) {
      await expectInViewport(modal.locator(`.form-field-${field}`).first());
    }
    // the note the fixture stored is what the modal shows
    await expect(modal.locator('.form-field-Note textarea').first()).toHaveValue(`E2E ${run} credit refund pending`);
    await EvidenceCapture.still(page, 'de_DE-04-advanced-edit.png', [modal.locator('.form-field-Status').first(), modal.locator('.form-field-Note').first(), modal.locator('.form-field-IsAbandoned').first()]);
    await page.keyboard.press('Escape');
    await expect(modal).toBeHidden({ timeout: SLOW_ACTION_TIMEOUT });
  });

  await test.step('Each status offers only the processes that apply to it', async () => {
    const expectedByStatus = {
      CR: [PROCESS_MARK_REFUNDED],
      BE: [PROCESS_RETRY_BOOKING, PROCESS_MARK_RESOLVED],
      SU: [],
      AB: [],
    };
    for (const name of ['CR', 'BE', 'SU', 'AB']) {
      // no background process changes a settlement in these statuses; assert it right before the processes are read
      expect(await statusKeyOf(ids[name]), `status of the ${name} settlement`).toBe(name);
      await openSingleRow(page, ids[name]);
      const offered = await openActionsAndListProcesses(page);
      expect(offered.enabled, `processes offered for a ${name} settlement`).toEqual([...expectedByStatus[name]].sort());
      expect(offered.disabled, `a single-row view hides the processes that do not apply to a ${name} settlement`).toEqual([]);
      // the offered processes; with none offered, the whole actions list shows that none of them is there
      const highlighted = offered.enabled.length
        ? offered.enabled.map((value) => page.locator(`.subheader-container [data-testid="action-${value}"]`))
        : [page.locator('.subheader-container ul', { has: page.locator('[data-testid^="action-"]') })];
      await EvidenceCapture.still(page, `de_DE-05-${name}-actions-menu.png`, highlighted, { inset: offered.enabled.length === 0 });
      await closeActionsMenu(page);
    }
  });

  await test.step('In the grid, the selected settlement offers the processes of its status', async () => {
    await page.goto(`${FRONTEND_BASE_URL}/window/${WINDOW_ID}`);
    const row = gridRow(page, ids.BE);
    await row.waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    await expect(async () => {
      await row.click();
      await expect(row).toHaveClass(/row-selected/, { timeout: 2000 });
    }).toPass({ timeout: SLOW_ACTION_TIMEOUT });
    const offered = await openActionsAndListProcesses(page);
    // the grid keeps the process that does not apply in the list, greyed out: it cannot be started
    expect(offered.enabled).toEqual([PROCESS_RETRY_BOOKING, PROCESS_MARK_RESOLVED].sort());
    expect(offered.disabled).toEqual([PROCESS_MARK_REFUNDED]);
    await EvidenceCapture.still(page, 'de_DE-06-grid-BE-actions-menu.png', [
      page.locator(`.subheader-container [data-testid="action-${PROCESS_RETRY_BOOKING}"]`),
      page.locator(`.subheader-container [data-testid="action-${PROCESS_MARK_RESOLVED}"]`),
      page.locator(`.subheader-container [data-testid="action-${PROCESS_MARK_REFUNDED}"]`),
    ]);
    await closeActionsMenu(page);
  });

  await test.step('A process dialog asks for the Note and is cancelled without running the process', async () => {
    expect(await statusKeyOf(ids.BE), 'status of the BE settlement before the process is opened').toBe('BE');
    await openSingleRow(page, ids.BE);
    await openActionsAndListProcesses(page);
    await page.locator(`.subheader-container [data-testid="action-${PROCESS_MARK_RESOLVED}"]`).click();
    const noteField = page.locator('.panel-modal .form-field-Note');
    await noteField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await expectInViewport(noteField);
    await EvidenceCapture.still(page, 'de_DE-07-process-dialog-note.png', [noteField, page.locator('[data-testid="process-modal-cancel-button"]')]);
    await page.locator('[data-testid="process-modal-cancel-button"]').click();
    await expect(page.locator('.panel-modal')).toBeHidden({ timeout: SLOW_ACTION_TIMEOUT });
    // end result: cancelling ran nothing, the settlement still waits in its status
    expect(await statusKeyOf(ids.BE)).toBe('BE');
  });
});
