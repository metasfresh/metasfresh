import { expect } from '@playwright/test';
import fs from 'fs';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FAST_ACTION_TIMEOUT, FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { waitForRecordSaved } from '../utils/WebAPIValidation';
import { LookupWidget } from '../utils/widgets/LookupWidget';
import { BooleanWidget } from '../utils/widgets/BooleanWidget';
import { ListWidget } from '../utils/widgets/ListWidget';
import { NumericWidget } from '../utils/widgets/NumericWidget';
import { AdvancedEdit } from '../utils/AdvancedEdit';
import { openReferencesPanel, waitForReferences, waitForSpinnersToDisappear } from '../utils/DocumentReferences';

/**
 * Compensation-group calibration rules, TC1 "concept example".
 *
 * A calibration rule scales the quantity of a compensation-group component (a template line of a menu) for a
 * customer or a business-partner group; the quantities are calibrated when the menu is added through the order
 * quick input. Everything is driven and asserted through the rendered UI; the database cross-check reads the
 * order lines through the testing backend.
 *
 *   Part 1  rule window "Kalibrierungsregeln": rules in SeqNo order under a filter, a rule created through the
 *           window (rejected until customer or group is set), a 3-decimal factor entered and re-read
 *   Part 2  orders through the quick input: Kindergarten (calibrated), Krankenhaus (no rule, group hidden),
 *           a quotation for Kindergarten, Kita B (3-decimal factor on the line)
 *   Part 3  a used rule cannot be deleted (deactivate-instead message); a deactivated rule still shows by name on
 *           its line; the rule's related-documents panel lists the sales order and opens the SALES order window
 *
 * The sales-order window is never addressed by id: core uses window 143, customer instances override it. The order
 * is opened through the menu entry "Neuer Auftrag" and the window id is read from the URL afterwards.
 *
 * German captions are asserted on purpose: the translated captions and messages ARE the subject here, so the login
 * language is pinned to de_DE (same approach as compensation-group-contract.spec.js).
 */

const LANGUAGE = 'de_DE';

// Quotation document type (C_DocType "Angebot", DocBaseType SOO / DocSubType ON), selected by its key. The id is the one
// the core migrations themselves reference for this doc type (e.g. 5463190_cli_gh1595_translating_doctypes.sql), so it
// exists on the core database image too.
const DOCTYPE_QUOTATION_ID = 1000027;

// The calibration rule window (core AD_Window, allocated centrally, so the same id on every database)
const RULES_WINDOW_ID = 542195;

// Captions / messages (de_DE), read from the application dictionary.
// Exception to the language-independence rule of e2e/frontend-webui/CLAUDE.md, on purpose: the translated AD_Message
// texts (and the menu entry, which carries no language-neutral identifier) are the subject of the checks, the login
// language is pinned to de_DE.
const MENU_NEW_ORDER = 'Neuer Auftrag';
const MSG_PARTNER_OR_GROUP_REQUIRED = 'Kunde oder Geschäftspartnergruppe muss gesetzt sein.';
const MSG_USED_DEACTIVATE_INSTEAD =
    'Die Kalibrierungsregel wird in Auftragspositionen verwendet und kann nicht gelöscht werden. Bitte deaktivieren Sie sie stattdessen.';
const CALIBRATION_CAPTIONS = ['Kalibrierfaktor', 'Kalibrierungsregel', 'Menge unkalibriert'];

// data-cy of the Alt+6 related-documents entry: rule -> sales orders (AD_RelationType.InternalName)
const RULE_TO_SALES_ORDERS_REFERENCE = 'reference-C_CompensationGroup_CalibrationRule_to_C_Order_SO';

// Fields of the calibration group of an order line (advanced edit)
const FIELD_FACTOR = 'GroupCompensationCalibrationFactor';
const FIELD_RULE = 'C_CompensationGroup_CalibrationRule_ID';
const FIELD_UNCALIBRATED = 'GroupCompensationQtyEnteredUncalibrated';

test.describe('Compensation group calibration', () => {
    // fail an action at once instead of waiting for the test timeout
    test.use({ actionTimeout: SLOW_ACTION_TIMEOUT });

    test('TC1 concept example', async ({ page }) => {
        allure.epic('E0100: Sales');
        allure.tag('Compensation group calibration');
        allure.story('TC1 concept example: rules scale the menu components of an order');
        allure.severity('critical');

        // masterdata + 4 orders + 3 rules entered in the window + delete/deactivate + related documents
        test.setTimeout(600000);

        // ============================================================
        // Masterdata (UOM precisions as on the customer system: PCE 0, GRM 2)
        // ============================================================
        const masterdata = await Backend.createMasterdata({
            request: {
                login: { user: { language: LANGUAGE, firstname: 'first', lastname: 'last' } },
                uoms: { PCE: { precision: 0 }, GRM: { precision: 2 } },
                bpartners: {
                    KINDERGARTEN: { name: 'Kindergarten' },
                    KRANKENHAUS: { name: 'Krankenhaus' },
                    KITA_B: { name: 'Kita B' },
                },
                products: {
                    MENUE1: {
                        name: 'Menue1',
                        type: 'Item',
                        isStocked: false,
                        compensationGroupSchema: 'schema_menue1',
                        prices: [{ price: 5, currencyCode: 'EUR' }],
                    },
                    REIS: { name: 'Reis', type: 'Item', uom: 'GRM', prices: [{ price: 1, currencyCode: 'EUR' }] },
                    HAEHNCHEN: { name: 'Haehnchen', type: 'Item', uom: 'GRM', prices: [{ price: 1, currencyCode: 'EUR' }] },
                },
                warehouses: { wh: {} },
                compensationGroupSchemas: {
                    schema_menue1: {
                        name: 'Menue1 schema',
                        templateLines: [
                            { product: 'REIS', qty: 200, uom: 'GRM' },
                            { product: 'HAEHNCHEN', qty: 100, uom: 'GRM' },
                        ],
                    },
                },
                // rule 10 via masterdata; rule 20 and the Kita B rule are entered through the window below
                calibrationRules: {
                    RULE10: { seqNo: 10, bpartner: 'KINDERGARTEN', product: 'REIS', factor: 0.5 },
                },
            },
        });
        const kindergarten = masterdata.bpartners.KINDERGARTEN.bpartnerCode;
        const krankenhaus = masterdata.bpartners.KRANKENHAUS.bpartnerCode;
        const kitaB = masterdata.bpartners.KITA_B.bpartnerCode;
        const menu = masterdata.products.MENUE1.productCode;
        const reis = masterdata.products.REIS.productCode;
        const haehnchen = masterdata.products.HAEHNCHEN.productCode;
        const rule10Id = String(masterdata.calibrationRules.RULE10.id);

        await LoginPage.goto();
        await LoginPage.login(masterdata.login.user);
        await DashboardPage.expectVisible();

        // ============================================================
        // Part 1: the rule window
        // ============================================================
        let rulesWindowUrl;
        let rulesWindowId;
        let rule20Id;
        let rule30Id;

        await test.step('Part 1: open the rule window; rule 10 listed under a customer filter', async () => {
            rulesWindowId = String(RULES_WINDOW_ID);
            rulesWindowUrl = `${FRONTEND_BASE_URL}/window/${rulesWindowId}`;
            await page.goto(rulesWindowUrl);
            await expectGridLoaded(page);

            await filterRulesByCustomer(page, kindergarten);
            const rule10Row = page.getByTestId(`table-row-${rule10Id}`);
            await expect(rule10Row).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
            await expect(page.locator('table tbody tr')).toHaveCount(1);
            await expect(rule10Row.locator('[data-cy="cell-SeqNo"]')).toHaveText('10');
            await expect(rule10Row.locator('[data-cy="cell-M_Product_ID"]')).toContainText(reis);
            await expect(rule10Row.locator('[data-cy="cell-GroupCompensationCalibrationFactor"]')).toHaveText(/^0[.,]5$/);
        });

        await test.step('Part 1: a rule without customer and group is rejected; rule 20 entered through the window', async () => {
            await newRuleThroughWindow(page, rulesWindowUrl);
            await expect(page.getByText(MSG_PARTNER_OR_GROUP_REQUIRED)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

            // product and factor alone do not help: still rejected
            await LookupWidget.setValue('M_Product_ID', haehnchen);
            await NumericWidget.setValue(FIELD_FACTOR, '0.6');
            await expect(page.getByText(MSG_PARTNER_OR_GROUP_REQUIRED)).toBeVisible();

            // with the customer it is accepted
            await NumericWidget.setValue('SeqNo', 20);
            await LookupWidget.setValue('C_BPartner_ID', kindergarten);
            await expect(page.getByText(MSG_PARTNER_OR_GROUP_REQUIRED)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
            rule20Id = recordIdFromUrl(page);
            await waitForRecordSaved(rulesWindowId, rule20Id, { maxRetries: 20, retryDelayMs: 1000 });
            await snap(page, 'rule-20-detail');
        });

        await test.step('Part 1: a 3-decimal factor (Kita B, Reis, 0,667) is entered and re-read as 0,667', async () => {
            await newRuleThroughWindow(page, rulesWindowUrl);
            await NumericWidget.setValue('SeqNo', 30);
            await LookupWidget.setValue('C_BPartner_ID', kitaB);
            await LookupWidget.setValue('M_Product_ID', reis);
            await NumericWidget.setValue(FIELD_FACTOR, '0.667');
            rule30Id = recordIdFromUrl(page);
            await waitForRecordSaved(rulesWindowId, rule30Id, { maxRetries: 20, retryDelayMs: 1000 });

            // re-read: the detail after a reload and the grid row both show 3 decimals
            await page.reload();
            await expect(page.locator(`.form-field-${FIELD_FACTOR} input`)).toHaveValue(/^0[.,]667$/, { timeout: SLOW_ACTION_TIMEOUT });
            await page.goto(rulesWindowUrl);
            await expectGridLoaded(page);
            await filterRulesByCustomer(page, kitaB);
            await expect(page.getByTestId(`table-row-${rule30Id}`).locator(`[data-cy="cell-${FIELD_FACTOR}"]`))
                .toHaveText(/^0[.,]667$/, { timeout: SLOW_ACTION_TIMEOUT });
        });

        await test.step('Part 1: under the customer filter the rules show in SeqNo order', async () => {
            await page.goto(rulesWindowUrl);
            await expectGridLoaded(page);
            await filterRulesByCustomer(page, kindergarten);
            const rows = page.locator('table tbody tr');
            await expect(rows).toHaveCount(2, { timeout: SLOW_ACTION_TIMEOUT });
            await expect(rows.nth(0).locator('[data-cy="cell-SeqNo"]')).toHaveText('10');
            await expect(rows.nth(0).locator('[data-cy="cell-M_Product_ID"]')).toContainText(reis);
            await expect(rows.nth(1).locator('[data-cy="cell-SeqNo"]')).toHaveText('20');
            await expect(rows.nth(1).locator('[data-cy="cell-M_Product_ID"]')).toContainText(haehnchen);
            await expect(rows.nth(1).locator(`[data-cy="cell-${FIELD_FACTOR}"]`)).toHaveText(/^0[.,]6$/);
            await snap(page, 'rules-grid-customer-filter');
        });

        // ============================================================
        // Part 2: orders through the quick input
        // ============================================================
        let orderWindowId;
        let kindergartenOrderId;

        await test.step('Part 2: order for Kindergarten, Menue1 qty 1 -> Reis 100 g, Haehnchen 60 g', async () => {
            const order = await createOrder(page, kindergarten);
            orderWindowId = order.windowId;
            kindergartenOrderId = order.orderId;
            await addMenuThroughQuickInput(page, menu, 1);

            await expectLineQty(page, reis, 100);
            await expectLineQty(page, haehnchen, 60);

            await expectNoCalibrationGridColumn(page);

            // advanced edit: the calibration group shows, read-only
            await expectCalibrationGroup(page, reis, { factor: '0.5', rulePrefix: '10', uncalibrated: '200' });
            await expectCalibrationGroup(page, haehnchen, { factor: '0.6', rulePrefix: '20', uncalibrated: '100' });

            await Backend.expect({
                title: 'Kindergarten order lines: calibrated quantities, factors, rules, uncalibrated quantities',
                salesOrders: {
                    [kindergartenOrderId]: {
                        lines: [
                            { product: 'REIS', qtyEntered: 100, calibrationFactor: 0.5, calibrationRule: 'RULE10', qtyEnteredUncalibrated: 200 },
                            { product: 'HAEHNCHEN', qtyEntered: 60, calibrationFactor: 0.6, qtyEnteredUncalibrated: 100 },
                        ],
                    },
                },
            });
        });

        await test.step('Part 2: order for Krankenhaus (no rule) -> Reis 200 g, Haehnchen 100 g, calibration group hidden', async () => {
            const order = await createOrder(page, krankenhaus);
            await addMenuThroughQuickInput(page, menu, 1);

            await expectLineQty(page, reis, 200);
            await expectLineQty(page, haehnchen, 100);
            await expectCalibrationGroupHidden(page, reis);
            await expectCalibrationGroupHidden(page, haehnchen);

            await Backend.expect({
                title: 'Krankenhaus order lines: today\'s quantities',
                salesOrders: {
                    [order.orderId]: {
                        lines: [
                            { product: 'REIS', qtyEntered: 200 },
                            { product: 'HAEHNCHEN', qtyEntered: 100 },
                        ],
                    },
                },
            });
        });

        await test.step('Part 2: quotation for Kindergarten -> Reis 100 g, calibration group shown in the line advanced edit', async () => {
            const order = await createOrder(page, kindergarten, { docTypeId: DOCTYPE_QUOTATION_ID });
            expect(order.windowId, 'a quotation is a document of the sales-order window').toBe(orderWindowId);
            await addMenuThroughQuickInput(page, menu, 1);

            await expectLineQty(page, reis, 100);
            await expectCalibrationGroup(page, reis, { factor: '0.5', rulePrefix: '10', uncalibrated: '200' });

            await Backend.expect({
                title: 'Kindergarten quotation lines',
                salesOrders: {
                    [order.orderId]: {
                        lines: [{ product: 'REIS', qtyEntered: 100, calibrationFactor: 0.5, calibrationRule: 'RULE10', qtyEnteredUncalibrated: 200 }],
                    },
                },
            });
        });

        await test.step('Part 2: order for Kita B -> Reis 133,40 g, the advanced edit shows 0,667', async () => {
            const order = await createOrder(page, kitaB);
            await addMenuThroughQuickInput(page, menu, 1);

            await expectLineQty(page, reis, 133.4);
            await expectCalibrationGroup(page, reis, { factor: '0.667', rulePrefix: '30', uncalibrated: '200' });

            await Backend.expect({
                title: 'Kita B order line: 3-decimal factor',
                salesOrders: {
                    [order.orderId]: {
                        lines: [{ product: 'REIS', qtyEntered: 133.4, calibrationFactor: 0.667, qtyEnteredUncalibrated: 200 }],
                    },
                },
            });
        });

        // ============================================================
        // Part 3: used rule, deactivated rule, related documents
        // ============================================================
        await test.step('Part 3: deleting the used rule 20 is refused with the deactivate-instead message', async () => {
            await page.goto(`${rulesWindowUrl}/${rule20Id}`);
            await page.locator(`.form-field-${FIELD_FACTOR}`).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await page.locator('body').click();
            await page.keyboard.press('Alt+D');
            const prompt = page.locator('.modal-content, .prompt-shadow, .confirmation-dialog').first();
            await prompt.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
            await prompt.locator('.btn-meta-primary, .btn-meta-success').first().click();
            await expect(page.getByText(MSG_USED_DEACTIVATE_INSTEAD)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
            await snap(page, 'rule-20-delete-refused');
            // the rule is still there
            await page.goto(`${rulesWindowUrl}/${rule20Id}`);
            await expect(page.locator(`.form-field-${FIELD_FACTOR} input`)).toHaveValue(/^0[.,]6$/, { timeout: SLOW_ACTION_TIMEOUT });
        });

        await test.step('Part 3: deactivated rule 20 still shows by name on its Haehnchen line', async () => {
            await page.goto(`${rulesWindowUrl}/${rule20Id}`);
            await page.locator('.form-field-IsActive').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            await BooleanWidget.setFalse('IsActive');
            await waitForRecordSaved(rulesWindowId, rule20Id, { maxRetries: 20, retryDelayMs: 1000 });
            await page.reload();
            await expect(page.locator('.form-field-IsActive input[type="checkbox"]')).not.toBeChecked({ timeout: SLOW_ACTION_TIMEOUT });

            await page.goto(`${FRONTEND_BASE_URL}/window/${orderWindowId}/${kindergartenOrderId}`);
            await expectLineQty(page, haehnchen, 60);
            // the line shows the rule's name, not "<id>"
            await expectCalibrationGroup(page, haehnchen, { factor: '0.6', rulePrefix: '20', uncalibrated: '100', snapshotName: 'order-line-deactivated-rule' });
        });

        await test.step('Part 3: the related-documents panel of rule 10 lists the sales order and opens the sales-order window', async () => {
            await page.goto(`${rulesWindowUrl}/${rule10Id}`);
            await page.locator(`.form-field-${FIELD_FACTOR}`).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            expect(await openReferencesPanel(), 'related-documents panel opens').toBe(true);
            await waitForSpinnersToDisappear();
            expect(await waitForReferences(), 'references loaded').toBe(true);
            const reference = page.locator(`[data-cy="${RULE_TO_SALES_ORDERS_REFERENCE}"]`);
            await expect(reference).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
            await reference.click();
            await page.waitForURL(/\/window\/\d+/, { timeout: SLOW_ACTION_TIMEOUT });
            // the SALES order window (Auftrag), not the purchase one. Note: the relation's target window comes from the
            // reference's own window setting and may differ from the menu's window on an instance that overrides it.
            expect(windowIdFromUrl(page), 'related documents open the sales-order window').toBe(orderWindowId);
            await expectGridLoaded(page);
            // exactly the order (the quotation of the same customer is a different record)
            await expect(page.getByTestId(`table-row-${kindergartenOrderId}`)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
            await snap(page, 'rule-10-related-sales-orders');
        });
    });
});

// ======================================================================
// Helpers
// ======================================================================

let orderCounter = 0;

/**
 * Open a window through the navigation menu (Alt+2): type the caption, click the entry with exactly that caption.
 * Menu entries carry no language-neutral identifier, and the order window id differs per instance (core 143,
 * customer override windows), so the caption (de_DE, see above) is the only handle.
 */
async function openMenuEntry(page, caption) {
    await page.locator('body').click();
    await page.keyboard.press('Alt+2');
    const overlay = page.locator('.menu-overlay');
    await overlay.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await overlay.locator('input.input-field, input[type="text"]').first().fill(caption);
    const item = overlay.locator('.js-menu-item').filter({ has: page.locator('.menu-overlay-link', { hasText: new RegExp(`^${caption}$`) }) }).first();
    await item.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await item.click();
}

async function expectGridLoaded(page) {
    await page.locator('.document-list-wrapper, .document-list').waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.locator('.rotating, .panel-spaced-lg').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
}

/** Filter the rule grid (its default filter) by customer, using the customer's code. */
async function filterRulesByCustomer(page, bpartnerCode) {
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
async function newRuleThroughWindow(page, rulesWindowUrl) {
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

function windowIdFromUrl(page) {
    const match = page.url().match(/\/window\/(\d+)/);
    if (!match) {
        throw new Error(`No window id in URL ${page.url()}`);
    }
    return match[1];
}

function recordIdFromUrl(page) {
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
async function createOrder(page, bpartnerCode, { docTypeId } = {}) {
    const previousUrl = page.url();
    await openMenuEntry(page, MENU_NEW_ORDER);
    // a different record than the one we come from (that URL would match the pattern at once)
    await page.waitForURL((url) => /\/window\/\d+\/\d+/.test(url.pathname) && url.href !== previousUrl, { timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('#lookup_C_BPartner_ID input').first().waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
    const windowId = windowIdFromUrl(page);
    const orderId = recordIdFromUrl(page);

    if (docTypeId) {
        await ListWidget.setByValue('C_DocTypeTarget_ID', String(docTypeId));
    }
    await LookupWidget.setValue('C_BPartner_ID', bpartnerCode);
    await waitForRecordSaved(windowId, orderId, { maxRetries: 20, retryDelayMs: 1000 });
    return { windowId, orderId };
}

/** Add the menu product through the order-line quick input (batch entry) and wait for the group's lines. */
async function addMenuThroughQuickInput(page, productCode, quantity) {
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

    const qtyField = page.locator('.quick-input-container').getByRole('spinbutton');
    await qtyField.waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await qtyField.click();
    await qtyField.fill(String(quantity));
    await expect(qtyField).toHaveValue(String(quantity));
    await page.keyboard.press('Enter');

    // the schema expansion creates the component lines; wait for them (no retry: that would duplicate them)
    await expect(page.locator('table tbody tr')).toHaveCount(2, { timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.locator('.rotating, .indicator-pending').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});

    // reload: what the grid shows is what is stored
    await page.reload();
    await expect(page.locator('table tbody tr')).toHaveCount(2, { timeout: VERY_SLOW_ACTION_TIMEOUT });
    await page.locator('table tbody tr').first().scrollIntoViewIfNeeded();
    await snap(page, `order-lines-after-quick-input-${++orderCounter}`);
}

function lineRow(page, productCode) {
    return page.locator('table tbody tr').filter({ has: page.locator('[data-cy="cell-M_Product_ID"]', { hasText: productCode }) });
}

/** "1.234,50" (de_DE grid text) -> 1234.5 */
function parseGermanNumber(text) {
    return Number(text.replace(/\./g, '').replace(',', '.'));
}

async function expectLineQty(page, productCode, expectedQty) {
    const cell = lineRow(page, productCode).locator('[data-cy="cell-QtyEntered"]');
    await expect(cell).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    const actual = parseGermanNumber((await cell.innerText()).trim());
    expect(actual, `Menge of the ${productCode} line`).toBeCloseTo(expectedQty, 2);
}

/** The calibration data is not a grid column. */
async function expectNoCalibrationGridColumn(page) {
    const headers = await page.locator('table thead th').allInnerTexts();
    for (const caption of CALIBRATION_CAPTIONS) {
        expect(headers, `grid column "${caption}"`).not.toContain(caption);
    }
    await expect(page.locator(`[data-cy="cell-${FIELD_FACTOR}"], [data-cy="cell-${FIELD_RULE}"], [data-cy="cell-${FIELD_UNCALIBRATED}"]`)).toHaveCount(0);
}

/** Open the advanced edit (row context menu, "Alt+E" entry) of the line of the product. */
async function openLineAdvancedEdit(page, productCode) {
    await lineRow(page, productCode).click({ button: 'right' });
    await page.locator('.context-menu-item').filter({ hasText: 'Alt+E' }).click();
    await page.locator('.panel-modal-content .form-field-QtyEntered input').waitFor({ state: 'visible', timeout: SLOW_ACTION_TIMEOUT });
    await page.locator('.panel-modal-content .rotating').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT }).catch(() => {});
}

/**
 * The calibration group of the line's advanced edit shows factor, rule (by its name, which starts with its SeqNo)
 * and uncalibrated quantity, all read-only; the UOM editor is rendered once.
 */
async function expectCalibrationGroup(page, productCode, { factor, rulePrefix, uncalibrated, snapshotName }) {
    await openLineAdvancedEdit(page, productCode);
    const modal = page.locator('.panel-modal-content');
    const factorInput = modal.locator(`.form-field-${FIELD_FACTOR} input`);
    await expect(factorInput).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
    await expect(factorInput).toHaveValue(new RegExp(`^${factor.replace('.', '[.,]')}$`));
    await expect(factorInput).toBeDisabled();

    const ruleInput = modal.locator(`.form-field-${FIELD_RULE} input`).first();
    await expect(ruleInput).toHaveValue(new RegExp(`^${rulePrefix}_.+`));
    await expect(ruleInput).toBeDisabled();

    const uncalibratedInput = modal.locator(`.form-field-${FIELD_UNCALIBRATED} input`).first();
    await expect(uncalibratedInput).toHaveValue(new RegExp(`^${uncalibrated}([.,]0+)?$`));
    await expect(uncalibratedInput).toBeDisabled();

    await expect(modal.locator('.form-field-C_UOM_ID.widgetType-List')).toHaveCount(1);
    await modal.locator(`.form-field-${FIELD_UNCALIBRATED}`).scrollIntoViewIfNeeded();
    await snap(page, snapshotName ?? `advanced-edit-${productCode.split('_')[0].toLowerCase()}-calibrated`);
    await AdvancedEdit.close();
}

/** With no rule matched the calibration group is not shown. */
async function expectCalibrationGroupHidden(page, productCode) {
    await openLineAdvancedEdit(page, productCode);
    const modal = page.locator('.panel-modal-content');
    for (const field of [FIELD_FACTOR, FIELD_RULE, FIELD_UNCALIBRATED]) {
        await expect(modal.locator(`.form-field-${field}`)).toHaveCount(0);
    }
    await snap(page, `advanced-edit-${productCode.split('_')[0].toLowerCase()}-uncalibrated`);
    await AdvancedEdit.close();
}

async function snap(page, name) {
    const buffer = await page.screenshot({ fullPage: false });
    fs.writeFileSync(test.info().outputPath(`${name}.png`), buffer);
    await test.info().attach(name, { body: buffer, contentType: 'image/png' });
    console.log(`[INFO] screenshot ${name}`);
}
