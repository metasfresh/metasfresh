import { expect } from '@playwright/test';
import fs from 'fs';
import { test } from '../../playwright.config';
import { FAST_ACTION_TIMEOUT, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from './common';
import { getFieldData, waitForRecordSaved } from './WebAPIValidation';
import { LookupWidget } from './widgets/LookupWidget';
import { ListWidget } from './widgets/ListWidget';
import { AdvancedEdit } from './AdvancedEdit';

/**
 * Shared helpers of the compensation-group calibration specs (TC1 concept example, TC2 mock case): rule window,
 * order creation through the menu, quick input, order-line advanced edit with the calibration group.
 *
 * German captions are used on purpose (the specs pin the login language to de_DE, the translated captions and
 * messages are the subject): the menu entry "Neuer Auftrag" carries no language-neutral identifier.
 * When EVIDENCE_DIR is set, every snap() screenshot is also written there.
 */

export const MENU_NEW_ORDER = 'Neuer Auftrag';
export const CALIBRATION_CAPTIONS = ['Kalibrierfaktor', 'Kalibrierungsregel', 'Menge unkalibriert'];

// Fields of the calibration group of an order line (advanced edit)
export const FIELD_FACTOR = 'GroupCompensationCalibrationFactor';
export const FIELD_RULE = 'C_CompensationGroup_CalibrationRule_ID';
export const FIELD_UNCALIBRATED = 'GroupCompensationQtyEnteredUncalibrated';

let orderCounter = 0;

/**
 * Open a window through the navigation menu (Alt+2): type the caption, click the entry with exactly that caption.
 * Menu entries carry no language-neutral identifier, and the order window id differs per instance (core 143,
 * customer override windows), so the caption (de_DE, see above) is the only handle.
 */
export async function openMenuEntry(page, caption) {
    await page.locator('body').click();
    await page.keyboard.press('Alt+2');
    const overlay = page.locator('.menu-overlay');
    await overlay.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await overlay.locator('input.input-field, input[type="text"]').first().fill(caption);
    const item = overlay.locator('.js-menu-item').filter({ has: page.locator('.menu-overlay-link', { hasText: new RegExp(`^${caption}$`) }) }).first();
    await item.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await item.click();
}

export async function expectGridLoaded(page) {
    await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
}

/** Filter the rule grid (its default filter) by customer, using the customer's code. */
export async function filterRulesByCustomer(page, bpartnerCode) {
    await page.locator('.filters-not-frequent button.toggle-filters').click();
    const overlay = page.locator('.filters-overlay');
    const customerInput = overlay.locator('#lookup_C_BPartner_ID input').first();
    if (!(await customerInput.isVisible().catch(() => false))) {
        // the filter list offers the default filter; with it already active its form is shown directly
        await overlay.locator('li.filter-option-default').click();
    }
    await customerInput.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await customerInput.click();
    await customerInput.fill(bpartnerCode);
    await page.locator('.input-dropdown-list-option').filter({ hasText: bpartnerCode }).first().click();
    await page.getByTestId('filter-apply-button').click();
    await overlay.waitFor({ state: 'hidden', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
}

/** New (empty) rule through the rule window's grid (Alt+N; the shortcut is retried while the grid is still wiring up). */
export async function newRuleThroughWindow(page, rulesWindowUrl) {
    await page.goto(rulesWindowUrl);
    await expectGridLoaded(page);
    await page.locator('table tbody tr').first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    for (let attempt = 1; ; attempt++) {
        await page.locator('body').click({ position: { x: 5, y: 5 } });
        await page.keyboard.press('Alt+N');
        try {
            await page.waitForURL(/\/window\/\d+\/\d+/, { timeout: FAST_ACTION_TIMEOUT });
            break;
        } catch (e) {
            if (attempt >= 3) {
                throw e;
            }
        }
    }
    await page.locator('.form-field-SeqNo').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

export function windowIdOfPath(pathname) {
    const match = pathname.match(/\/window\/(\d+)/);
    return match ? match[1] : null;
}

export function windowIdFromUrl(page) {
    const windowId = windowIdOfPath(new URL(page.url()).pathname);
    if (!windowId) {
        throw new Error(`No window id in URL ${page.url()}`);
    }
    return windowId;
}

export function recordIdFromUrl(page) {
    const match = page.url().match(/\/window\/\d+\/(\d+)/);
    if (!match) {
        throw new Error(`No record id in URL ${page.url()}`);
    }
    return match[1];
}

/**
 * New order for the customer through the menu entry "Neuer Auftrag" (the window id is whatever this instance
 * opens). With `docTypeId`, the order's document type is changed to it (e.g. the quotation type).
 */
export async function createOrder(page, bpartnerCode, { docTypeId } = {}) {
    const previousUrl = page.url();
    await openMenuEntry(page, MENU_NEW_ORDER);
    // a different record than the one we come from (that URL would match the pattern at once)
    await page.waitForURL((url) => /\/window\/\d+\/\d+/.test(url.pathname) && url.href !== previousUrl, { timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('#lookup_C_BPartner_ID input').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    const windowId = windowIdFromUrl(page);
    const orderId = recordIdFromUrl(page);

    // No Tab after the pick: the customer lookup walks on by itself (see below), a Tab would race that walk.
    await LookupWidget.setValue('C_BPartner_ID', bpartnerCode, { triggerSave: false });
    // Picking the customer is only the first step of the customer lookup: it then confirms the customer's only
    // location on its own (a second PATCH, C_BPartner_Location_ID) and, once that answer is in, moves the caret on to
    // the contact field. On a loaded host that answer comes late, and the caret move then steals the focus from
    // whatever the test clicked meanwhile: the quick-input product field drops the code typed into it on blur, its
    // typeahead searches " " and the product option never shows. So wait until the caret has arrived in the contact field.
    await expect(page.locator('#lookup_AD_User_ID :focus'), 'customer lookup done: caret moved on to the contact field')
        .toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
    await waitForRecordSaved(windowId, orderId, { maxRetries: 20, retryDelayMs: 1000 });
    if (docTypeId) {
        await ListWidget.setByValue('C_DocTypeTarget_ID', String(docTypeId));
        await waitForRecordSaved(windowId, orderId, { maxRetries: 20, retryDelayMs: 1000 });
        const docTypeField = await getFieldData(windowId, orderId, 'C_DocTypeTarget_ID');
        expect(String(docTypeField.value && docTypeField.value.key), 'target document type of the new order').toBe(String(docTypeId));
    }
    return { windowId, orderId };
}

/**
 * Add the product through the order-line quick input (batch entry) and wait until the order has `expectedRows` lines
 * in total (a menu product expands to its group's lines). Default: 2, a menu on an empty order.
 */
export async function addMenuThroughQuickInput(page, productCode, quantity, { expectedRows = 2 } = {}) {
    const batchToggle = page.getByTestId('batch-entry-toggle');
    await batchToggle.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await batchToggle.click();

    const productField = page.locator('.quick-input-container #lookup_M_Product_ID input');
    await productField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await productField.fill(productCode);
    // picking the product is sent to the quick-input document (PATCH .../quickInput/<id>); Enter earlier submits nothing
    const quickInputCreated = page.waitForResponse(
        (response) => response.request().method() === 'PATCH' && /\/quickInput\/\d+$/.test(response.url()),
        { timeout: SLOW_ACTION_TIMEOUT }
    );
    await page.locator('.input-dropdown-list-option').filter({ hasText: productCode }).first().click();
    await quickInputCreated;
    // the selected product is shown in the quick-input form
    await expect.poll(
        async () => (await page.locator('.quick-input-container #lookup_M_Product_ID input').evaluateAll((inputs) => inputs.map((input) => input.value).join(' '))),
        { timeout: SLOW_ACTION_TIMEOUT, message: 'selected product shown in the quick input' }
    ).toContain(productCode);

    const qtyField = page.locator('.quick-input-container input[inputmode="decimal"]'); // the quantity is a text input with inputmode=decimal, not a number input
    await qtyField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await qtyField.click();
    await qtyField.fill(String(quantity));
    await expect(qtyField).toHaveValue(String(quantity));
    await page.keyboard.press('Enter');

    // the schema expansion creates the component lines; wait for them (no retry: that would duplicate them)
    await expect(page.locator('table tbody tr')).toHaveCount(expectedRows, { timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

    // reload: what the grid shows is what is stored
    await page.reload();
    await expect(page.locator('table tbody tr')).toHaveCount(expectedRows, { timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.locator('table tbody tr').first().scrollIntoViewIfNeeded();
    await snap(page, `order-lines-after-quick-input-${++orderCounter}`);
}

export function lineRow(page, productCode) {
    return page.locator('table tbody tr').filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: productCode }) });
}

/** "1.234,50" (de_DE grid text) -> 1234.5 */
export function parseGermanNumber(text) {
    return Number(text.replace(/\./g, '').replace(',', '.'));
}

export async function expectLineQty(page, productCode, expectedQty) {
    const cell = lineRow(page, productCode).locator('[data-cy="cell-QtyEntered"]');
    await expect(cell).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    const actual = parseGermanNumber((await cell.innerText()).trim());
    expect(actual, `Menge of the ${productCode} line`).toBeCloseTo(expectedQty, 2);
}

/** The calibration data is not a grid column. */
export async function expectNoCalibrationGridColumn(page) {
    const headers = await page.locator('table thead th').allInnerTexts();
    for (const caption of CALIBRATION_CAPTIONS) {
        expect(headers, `grid column "${caption}"`).not.toContain(caption);
    }
    await expect(page.locator(`[data-cy="cell-${FIELD_FACTOR}"], [data-cy="cell-${FIELD_RULE}"], [data-cy="cell-${FIELD_UNCALIBRATED}"]`)).toHaveCount(0);
}

/** Open the advanced edit (row context menu, "Alt+E" entry) of the line of the product. */
export async function openLineAdvancedEdit(page, productCode) {
    await lineRow(page, productCode).click({ button: 'right' });
    await page.locator('.context-menu-item').filter({ hasText: 'Alt+E' }).click();
    await page.locator('.panel-modal-content .form-field-QtyEntered input').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('.panel-modal-content .rotating').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
}

/**
 * The calibration group of the line's advanced edit shows factor, rule (by its name, which starts with its SeqNo)
 * and uncalibrated quantity, and nothing else. The factor of a calibrated line of a draft document is editable here
 * (read-only logic: no rule and no uncalibrated qty); rule and uncalibrated quantity are read-only (no unit widget; the line unit is shown once, outside it).
 * With `factorEditable: false` the factor is expected read-only too (e.g. a completed document).
 */
export async function expectCalibrationGroup(page, productCode, { factor, rulePrefix, uncalibrated, snapshotName, factorEditable = true }) {
    await openLineAdvancedEdit(page, productCode);
    const modal = page.locator('.panel-modal-content');
    const factorInput = modal.locator(`.form-field-${FIELD_FACTOR} input`);
    await expect(factorInput).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(factorInput).toHaveValue(new RegExp(`^${factor.replace('.', '[.,]')}$`));
    if (factorEditable) {
        await expect(factorInput).toBeEnabled();
    } else {
        await expect(factorInput).toBeDisabled();
    }

    const ruleInput = modal.locator(`.form-field-${FIELD_RULE} input`).first();
    await expect(ruleInput).toHaveValue(new RegExp(`^${rulePrefix}_.+`));
    await expect(ruleInput).toBeDisabled();

    const uncalibratedInput = modal.locator(`.form-field-${FIELD_UNCALIBRATED} input`).first();
    await expect(uncalibratedInput).toHaveValue(new RegExp(`^${uncalibrated}([.,]0+)?$`));
    await expect(uncalibratedInput).toBeDisabled();

    const calibrationGroup = modal.locator('.panel-spaced').filter({ has: page.locator(`.form-field-${FIELD_FACTOR}`) });
    await expect(calibrationGroup).toHaveCount(1);
    await expect(calibrationGroup.locator('.form-group')).toHaveCount(3);
    for (const field of [FIELD_FACTOR, FIELD_RULE, FIELD_UNCALIBRATED]) {
        await expect(calibrationGroup.locator(`.form-field-${field}`)).toHaveCount(1);
    }
    await expect(calibrationGroup.locator('.form-field-C_UOM_ID')).toHaveCount(0);
    await expect(modal.locator('.form-field-C_UOM_ID')).toHaveCount(1);
    await modal.locator(`.form-field-${FIELD_UNCALIBRATED}`).scrollIntoViewIfNeeded();
    await snap(page, snapshotName ?? `advanced-edit-${productCode.split('_')[0].toLowerCase()}-calibrated`);
    await AdvancedEdit.close();
}

/**
 * Type a new Kalibrierfaktor into the advanced edit of the line of the product ('' blanks it), commit it with Tab and
 * wait for the save response. Accepted: the Menge of the advanced edit shows `expectedQty`. Rejected (`rejected: true`):
 * the save fails (not OK, user-friendly error flag in the JSON) and exactly one error notification shows (language-independent, no text match). Then close and reload, so the grid shows what is stored.
 */
export async function setCalibrationFactor(page, productCode, factor, { expectedQty, rejected = false, snapshotName } = {}) {
    await openLineAdvancedEdit(page, productCode);
    const modal = page.locator('.panel-modal-content');
    const factorInput = modal.locator(`.form-field-${FIELD_FACTOR} input`);
    await expect(factorInput).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
    await factorInput.click();
    await page.keyboard.press('Control+a');
    await factorInput.fill(String(factor));
    const saveResponse = page.waitForResponse(
        (response) => response.request().method() === 'PATCH' && /\/window\/\d+\/\d+\//.test(response.url())
            && (response.request().postData() || '').includes(FIELD_FACTOR),
        { timeout: SLOW_ACTION_TIMEOUT }
    );
    await page.keyboard.press('Tab');
    const response = await saveResponse;
    let errorNotification = null;
    if (rejected) {
        expect(response.ok(), `factor "${factor}" on the ${productCode} line is refused`).toBe(false);
        // a user-friendly save error is shown as an error notification (frontend App.js), not as a field error
        const body = await response.json();
        expect(body.userFriendlyError, `the refusal of factor "${factor}" is a user-friendly error`).toBe(true);
        errorNotification = page.locator('.notification-handler .notification-item.error');
        await expect(errorNotification).toHaveCount(1, { timeout: SLOW_ACTION_TIMEOUT });
    } else {
        expect(response.ok(), `factor "${factor}" on the ${productCode} line is saved`).toBe(true);
        const qtyInput = modal.locator('.form-field-QtyEntered input');
        await expect.poll(async () => Number((await qtyInput.inputValue()).replace(',', '.')), {
            timeout: SLOW_ACTION_TIMEOUT,
            message: `Menge of the ${productCode} line after factor "${factor}"`,
        }).toBeCloseTo(expectedQty, 2);
    }
    await snap(page, snapshotName ?? `advanced-edit-${productCode.split('_')[0].toLowerCase()}-factor-${factor === '' ? 'empty' : factor}`);
    if (errorNotification) {
        await errorNotification.locator('.meta-icon-close-1').click();
        await expect(errorNotification).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
    }
    await AdvancedEdit.close();
    await page.reload();
    await lineRow(page, productCode).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/** With no rule matched the calibration group is not shown. */
export async function expectCalibrationGroupHidden(page, productCode) {
    await openLineAdvancedEdit(page, productCode);
    const modal = page.locator('.panel-modal-content');
    for (const field of [FIELD_FACTOR, FIELD_RULE, FIELD_UNCALIBRATED]) {
        await expect(modal.locator(`.form-field-${field}`)).toHaveCount(0);
    }
    await snap(page, `advanced-edit-${productCode.split('_')[0].toLowerCase()}-uncalibrated`);
    await AdvancedEdit.close();
}

export async function snap(page, name) {
    const buffer = await page.screenshot({ fullPage: false });
    fs.writeFileSync(test.info().outputPath(`${name}.png`), buffer);
    if (process.env.EVIDENCE_DIR) {
        fs.mkdirSync(process.env.EVIDENCE_DIR, { recursive: true });
        fs.writeFileSync(`${process.env.EVIDENCE_DIR}/${name}.png`, buffer);
    }
    await test.info().attach(name, { body: buffer, contentType: 'image/png' });
    console.log(`[INFO] screenshot ${name}`);
}

/**
 * Type a new Menge into the line of the product (advanced edit), wait for the saved response, close, and reload so
 * the grid shows what is stored.
 */
export async function setLineQty(page, productCode, quantity) {
    await openLineAdvancedEdit(page, productCode);
    const qtyInput = page.locator('.panel-modal-content .form-field-QtyEntered input');
    await qtyInput.click();
    await page.keyboard.press('Control+a');
    await qtyInput.fill(String(quantity));
    const saved = page.waitForResponse(
        (response) => response.request().method() === 'PATCH' && /\/window\/\d+\/\d+\//.test(response.url()) && response.ok(),
        { timeout: SLOW_ACTION_TIMEOUT }
    );
    await page.keyboard.press('Tab');
    await saved;
    await AdvancedEdit.close();
    await page.reload();
    await lineRow(page, productCode).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Type a new Menge INLINE in the grid (the clerk's usual path): double-click the line's Menge cell, type, confirm with
 * Enter, wait for the saved response, and reload so the grid shows what is stored.
 */
export async function setLineQtyInline(page, productCode, quantity) {
    const cell = lineRow(page, productCode).locator('[data-cy="cell-QtyEntered"]');
    await cell.dblclick();
    const input = cell.locator('input');
    await input.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await input.fill(String(quantity));
    const saved = page.waitForResponse(
        (response) => response.request().method() === 'PATCH' && /\/window\/\d+\/\d+\//.test(response.url()) && response.ok(),
        { timeout: SLOW_ACTION_TIMEOUT }
    );
    await page.keyboard.press('Enter');
    await saved;
    await page.reload();
    await lineRow(page, productCode).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/** The numeric value of the field shown in the open advanced edit (de_DE format, e.g. "1.500,5" -> 1500.5). */
async function advancedEditQty(modal, fieldName) {
    return Number((await modal.locator(`.form-field-${fieldName} input`).first().inputValue()).replace(/\./g, '').replace(',', '.'));
}

/**
 * Pick the Gebinde (packing instruction, M_HU_PI_Item_Product_ID) of the line of the product in its advanced edit,
 * by the TU name, and wait for the save response. The line then shows `expectedQtyTU` / `expectedQty`
 * (the packing callout recomputes Menge from the Gebindemenge). Then close and reload, so the grid shows what is stored.
 */
export async function setLinePackingInstruction(page, productCode, tuName, { expectedQtyTU, expectedQty } = {}) {
    await openLineAdvancedEdit(page, productCode);
    const modal = page.locator('.panel-modal-content');
    const field = modal.locator('.form-field-M_HU_PI_Item_Product_ID');
    await field.locator('input').first().click();
    const option = page.locator('.input-dropdown-list .input-dropdown-list-option').filter({ hasText: tuName }).first();
    await option.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    const saveResponse = page.waitForResponse(
        (response) => response.request().method() === 'PATCH' && /\/window\/\d+\/\d+\//.test(response.url())
            && (response.request().postData() || '').includes('M_HU_PI_Item_Product_ID'),
        { timeout: SLOW_ACTION_TIMEOUT }
    );
    await option.click();
    const response = await saveResponse;
    expect(response.ok(), `Gebinde "${tuName}" on the ${productCode} line is saved`).toBe(true);
    await expect(field.locator('input').first()).toHaveValue(new RegExp(tuName));
    if (expectedQtyTU !== undefined) {
        await expect.poll(() => advancedEditQty(modal, 'QtyEnteredTU'), { timeout: SLOW_ACTION_TIMEOUT, message: `Gebindemenge of the ${productCode} line` })
            .toBeCloseTo(expectedQtyTU, 2);
    }
    if (expectedQty !== undefined) {
        await expect.poll(() => advancedEditQty(modal, 'QtyEntered'), { timeout: SLOW_ACTION_TIMEOUT, message: `Menge of the ${productCode} line` })
            .toBeCloseTo(expectedQty, 2);
    }
    await snap(page, `advanced-edit-${productCode.split('_')[0].toLowerCase()}-packing-instruction`);
    await AdvancedEdit.close();
    await page.reload();
    await lineRow(page, productCode).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}

/**
 * Type a new Gebindemenge (QtyEnteredTU) into the advanced edit of the line of the product, commit it with Tab and wait
 * for the save response. The packing callout sets Menge = Gebindemenge x CUs per TU (`expectedQty`); on a calibrated
 * line the factor follows that Menge (`expectedFactor`). Then close and reload, so the grid shows what is stored.
 */
export async function setLineQtyTU(page, productCode, qtyTU, { expectedQty, expectedFactor }) {
    await openLineAdvancedEdit(page, productCode);
    const modal = page.locator('.panel-modal-content');
    const qtyTUInput = modal.locator('.form-field-QtyEnteredTU input').first();
    await expect(qtyTUInput).toBeEnabled({ timeout: SLOW_ACTION_TIMEOUT });
    await qtyTUInput.click();
    await page.keyboard.press('Control+a');
    await qtyTUInput.fill(String(qtyTU));
    const saveResponse = page.waitForResponse(
        (response) => response.request().method() === 'PATCH' && /\/window\/\d+\/\d+\//.test(response.url())
            && (response.request().postData() || '').includes('QtyEnteredTU'),
        { timeout: SLOW_ACTION_TIMEOUT }
    );
    await page.keyboard.press('Tab');
    const response = await saveResponse;
    expect(response.ok(), `Gebindemenge ${qtyTU} on the ${productCode} line is saved`).toBe(true);
    await expect.poll(() => advancedEditQty(modal, 'QtyEntered'), { timeout: SLOW_ACTION_TIMEOUT, message: `Menge of the ${productCode} line after Gebindemenge ${qtyTU}` })
        .toBeCloseTo(expectedQty, 2);
    await expect.poll(() => advancedEditQty(modal, FIELD_FACTOR), { timeout: SLOW_ACTION_TIMEOUT, message: `Kalibrierfaktor of the ${productCode} line after Gebindemenge ${qtyTU}` })
        .toBeCloseTo(expectedFactor, 2);
    await snap(page, `advanced-edit-${productCode.split('_')[0].toLowerCase()}-qty-tu-${qtyTU}`);
    await AdvancedEdit.close();
    await page.reload();
    await lineRow(page, productCode).first().waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
}
