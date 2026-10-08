import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { FRONTEND_BASE_URL, SLOW_ACTION_TIMEOUT, VERY_SLOW_ACTION_TIMEOUT } from '../utils/common';
import { waitForRecordSaved } from '../utils/WebAPIValidation';
import { LookupWidget } from '../utils/widgets/LookupWidget';
import { BooleanWidget } from '../utils/widgets/BooleanWidget';
import { NumericWidget } from '../utils/widgets/NumericWidget';
import {
    FIELD_FACTOR,
    addMenuThroughQuickInput,
    createOrder,
    expectCalibrationGroup,
    expectCalibrationGroupHidden,
    expectGridLoaded,
    expectLineQty,
    expectNoCalibrationGridColumn,
    filterRulesByCustomer,
    newRuleThroughWindow,
    recordIdFromUrl,
    snap,
    windowIdFromUrl,
    windowIdOfPath,
} from '../utils/compensationGroupCalibration';
import { openReferencesPanel, waitForReferences, waitForReferencesComplete } from '../utils/DocumentReferences';

/**
 * Compensation-group calibration rules, TC1 "concept example".
 *
 * A calibration rule scales the quantity of a compensation-group component (a template line of a menu) for a
 * customer or a business-partner group; the quantities are calibrated when the menu is added through the order
 * quick input. Everything is driven and asserted through the rendered UI; the database cross-check reads the
 * order lines through the testing backend.
 *
 *   Part 1  rule window "Kalibrierungsregeln": rules in SeqNo order under a filter, a rule created through the
 *           window (rejected until customer or group is set), a decimal percent factor (66,7) entered and re-read
 *   Part 2  orders through the quick input: Kindergarten (calibrated), Krankenhaus (no rule, group hidden),
 *           a quotation for Kindergarten, Kita B (decimal percent factor on the line)
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
const MSG_PARTNER_OR_GROUP_REQUIRED = 'Kunde oder Geschäftspartnergruppe muss gesetzt sein.';
const MSG_USED_DEACTIVATE_INSTEAD =
    'Die Kalibrierungsregel wird in Auftragspositionen verwendet und kann nicht gelöscht werden. Bitte deaktivieren Sie sie stattdessen.';

// data-cy of the Alt+6 related-documents entry: rule -> sales orders (AD_RelationType.InternalName)
const RULE_TO_SALES_ORDERS_REFERENCE = 'reference-C_CompensationGroup_CalibrationRule_to_C_Order_SO';

test.describe('Compensation group calibration', () => {
    // fail an action at once instead of waiting for the test timeout
    test.use({ actionTimeout: SLOW_ACTION_TIMEOUT });

    test('TC1 concept example', async ({ page }) => {
        allure.epic('E0100: Sales');
        allure.feature('F00127: Compensation Groups');
        allure.tag('F00127: Compensation Groups');
        allure.tag('F00127');
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
                    KITA_C: { name: 'Kita C' },
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
                    RULE10: { seqNo: 10, bpartner: 'KINDERGARTEN', product: 'REIS', factor: 50 },
                },
            },
        });
        const kindergarten = masterdata.bpartners.KINDERGARTEN.bpartnerCode;
        const krankenhaus = masterdata.bpartners.KRANKENHAUS.bpartnerCode;
        const kitaB = masterdata.bpartners.KITA_B.bpartnerCode;
        const kitaC = masterdata.bpartners.KITA_C.bpartnerCode;
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
            await expect(rule10Row.locator('[data-cy="cell-GroupCompensationCalibrationFactor"]')).toHaveText(/^50$/);
        });

        await test.step('Part 1: a rule without customer and group is rejected; rule 20 entered through the window', async () => {
            await newRuleThroughWindow(page, rulesWindowUrl);
            await expect(page.getByText(MSG_PARTNER_OR_GROUP_REQUIRED)).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });

            // product and factor alone do not help: still rejected
            await LookupWidget.setValue('M_Product_ID', haehnchen);
            await NumericWidget.setValue(FIELD_FACTOR, '60');
            await expect(page.getByText(MSG_PARTNER_OR_GROUP_REQUIRED)).toBeVisible();

            // with the customer it is accepted
            await NumericWidget.setValue('SeqNo', 20);
            await LookupWidget.setValue('C_BPartner_ID', kindergarten);
            await expect(page.getByText(MSG_PARTNER_OR_GROUP_REQUIRED)).toHaveCount(0, { timeout: SLOW_ACTION_TIMEOUT });
            rule20Id = recordIdFromUrl(page);
            await waitForRecordSaved(rulesWindowId, rule20Id, { maxRetries: 20, retryDelayMs: 1000 });
            await snap(page, 'rule-20-detail');
        });

        await test.step('Part 1: a decimal percent factor (Kita B, Reis, 66,7 %) is entered and re-read as 66,7', async () => {
            await newRuleThroughWindow(page, rulesWindowUrl);
            await NumericWidget.setValue('SeqNo', 30);
            await LookupWidget.setValue('C_BPartner_ID', kitaB);
            await LookupWidget.setValue('M_Product_ID', reis);
            await NumericWidget.setValue(FIELD_FACTOR, '66.7');
            rule30Id = recordIdFromUrl(page);
            await waitForRecordSaved(rulesWindowId, rule30Id, { maxRetries: 20, retryDelayMs: 1000 });

            // re-read: the detail after a reload and the grid row both show 3 decimals
            await page.reload();
            await expect(page.locator(`.form-field-${FIELD_FACTOR} input`)).toHaveValue(/^66[.,]7$/, { timeout: SLOW_ACTION_TIMEOUT });
            await page.goto(rulesWindowUrl);
            await expectGridLoaded(page);
            await filterRulesByCustomer(page, kitaB);
            await expect(page.getByTestId(`table-row-${rule30Id}`).locator(`[data-cy="cell-${FIELD_FACTOR}"]`))
                .toHaveText(/^66[.,]7$/, { timeout: SLOW_ACTION_TIMEOUT });
        });

        await test.step('Part 1: a new rule opens with factor 100 and keeps it when saved without touching the factor (Kita C)', async () => {
            await newRuleThroughWindow(page, rulesWindowUrl);
            await expect(page.locator(`.form-field-${FIELD_FACTOR} input`)).toHaveValue(/^100$/, { timeout: SLOW_ACTION_TIMEOUT });

            await NumericWidget.setValue('SeqNo', 40);
            await LookupWidget.setValue('C_BPartner_ID', kitaC);
            await LookupWidget.setValue('M_Product_ID', reis);
            const kitaCRuleId = recordIdFromUrl(page);
            await waitForRecordSaved(rulesWindowId, kitaCRuleId, { maxRetries: 20, retryDelayMs: 1000 });

            // read back from the server: the saved rule carries factor 100
            await page.goto(rulesWindowUrl);
            await expectGridLoaded(page);
            await filterRulesByCustomer(page, kitaC);
            await expect(page.getByTestId(`table-row-${kitaCRuleId}`).locator(`[data-cy="cell-${FIELD_FACTOR}"]`))
                .toHaveText(/^100$/, { timeout: SLOW_ACTION_TIMEOUT });
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
            await expect(rows.nth(1).locator(`[data-cy="cell-${FIELD_FACTOR}"]`)).toHaveText(/^60$/);
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
            await expectCalibrationGroup(page, reis, { factor: '50', rulePrefix: '10', uncalibrated: '200' });
            await expectCalibrationGroup(page, haehnchen, { factor: '60', rulePrefix: '20', uncalibrated: '100' });

            await Backend.expect({
                title: 'Kindergarten order lines: calibrated quantities, factors, rules, uncalibrated quantities',
                salesOrders: {
                    [kindergartenOrderId]: {
                        lines: [
                            { product: 'REIS', qtyEntered: 100, calibrationFactor: 50, calibrationRule: 'RULE10', qtyEnteredUncalibrated: 200 },
                            { product: 'HAEHNCHEN', qtyEntered: 60, calibrationFactor: 60, qtyEnteredUncalibrated: 100 },
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
                title: 'Krankenhaus order lines: today\'s quantities, no rule matched: no factor, no rule, no uncalibrated quantity',
                salesOrders: {
                    [order.orderId]: {
                        lines: [
                            { product: 'REIS', qtyEntered: 200, calibrated: false },
                            { product: 'HAEHNCHEN', qtyEntered: 100, calibrated: false },
                        ],
                    },
                },
            });
        });

        await test.step('Part 2: quotation for Kindergarten -> Reis 100 g, calibration group shown in the line advanced edit', async () => {
            const order = await createOrder(page, kindergarten, { docTypeId: DOCTYPE_QUOTATION_ID });
            expect(order.windowId, 'a quotation is a document of the sales-order window').toBe(orderWindowId);
            await snap(page, 'quotation-header-doc-type');
            await addMenuThroughQuickInput(page, menu, 1);

            await expectLineQty(page, reis, 100);
            await expectCalibrationGroup(page, reis, { factor: '50', rulePrefix: '10', uncalibrated: '200', snapshotName: 'quotation-advanced-edit-reis-calibrated' });

            await Backend.expect({
                title: 'Kindergarten quotation lines',
                salesOrders: {
                    [order.orderId]: {
                        lines: [{ product: 'REIS', qtyEntered: 100, calibrationFactor: 50, calibrationRule: 'RULE10', qtyEnteredUncalibrated: 200 }],
                    },
                },
            });
        });

        await test.step('Part 2: order for Kita B -> Reis 133,40 g, the advanced edit shows 66,7', async () => {
            const order = await createOrder(page, kitaB);
            await addMenuThroughQuickInput(page, menu, 1);

            await expectLineQty(page, reis, 133.4);
            await expectCalibrationGroup(page, reis, { factor: '66.7', rulePrefix: '30', uncalibrated: '200' });

            await Backend.expect({
                title: 'Kita B order line: decimal percent factor',
                salesOrders: {
                    [order.orderId]: {
                        lines: [{ product: 'REIS', qtyEntered: 133.4, calibrationFactor: 66.7, qtyEnteredUncalibrated: 200 }],
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
            await expect(page.locator(`.form-field-${FIELD_FACTOR} input`)).toHaveValue(/^60$/, { timeout: SLOW_ACTION_TIMEOUT });
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
            await expectCalibrationGroup(page, haehnchen, { factor: '60', rulePrefix: '20', uncalibrated: '100', snapshotName: 'order-line-deactivated-rule' });
        });

        await test.step('Part 3: the related-documents panel of rule 10 lists the sales order and opens the sales-order window', async () => {
            await page.goto(`${rulesWindowUrl}/${rule10Id}`);
            await page.locator(`.form-field-${FIELD_FACTOR}`).waitFor({ state: 'visible', timeout: VERY_SLOW_ACTION_TIMEOUT });
            expect(await openReferencesPanel(), 'related-documents panel opens').toBe(true);
            // the references stream in (SSE); wait for the complete list, not for the first entry within a fixed 8 s
            await waitForReferencesComplete();
            expect(await waitForReferences(), 'references loaded').toBe(true);
            const reference = page.locator(`[data-cy="${RULE_TO_SALES_ORDERS_REFERENCE}"]`);
            await expect(reference).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
            await reference.click();
            // the rule's own URL matches /window/<id> as well: wait until the page has left the rules window
            await page.waitForURL((url) => windowIdOfPath(url.pathname) !== rulesWindowId, { timeout: SLOW_ACTION_TIMEOUT });
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
