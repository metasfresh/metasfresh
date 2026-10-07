import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { SLOW_ACTION_TIMEOUT } from '../utils/common';
import {
    addMenuThroughQuickInput,
    createOrder,
    expectCalibrationGroup,
    expectCalibrationGroupHidden,
    expectLineQty,
    expectNoCalibrationGridColumn,
    lineRow,
    setLineQty,
} from '../utils/compensationGroupCalibration';

/**
 * Compensation-group calibration, TC2 "mock case": the customer's mock replayed 1:1.
 *
 * A school menu (1 PCE template line, the menu line itself) with the dessert component Rice_Pudding 0,25 LTR; the
 * customer School_A (BP group Group_Standard) has a customer rule with factor 0,8. The clerk enters 1.500 menus
 * through the order quick input:
 *
 *   - the menu line is not calibrated (no factor, no stored rule, calibration group hidden)
 *   - Rice_Pudding is 1.500 x 0,25 x 0,8 = 300 LTR, uncalibrated 375 LTR; factor, rule and uncalibrated quantity
 *     are shown read-only in the line's advanced edit
 *   - the clerk types 375 as the line's Menge (the mock's value): factor 0,8, rule 10 and uncalibrated 375 stay
 *   - the menu line's Menge 1.500 -> 1.600 leaves Rice_Pudding untouched (no automatic rescale)
 *   - a manually added line has no calibration group; no calibration column in the grid
 *
 * Everything is driven through the rendered UI; the stored lines are cross-checked through the testing backend.
 * The sales-order window is opened through the menu (never addressed by id). German captions are used on purpose
 * (see utils/compensationGroupCalibration.js): the login language is pinned to de_DE.
 */

const LANGUAGE = 'de_DE';

test.describe('Compensation group calibration - mock case', () => {
    // fail an action at once instead of waiting for the test timeout
    test.use({ actionTimeout: SLOW_ACTION_TIMEOUT });

    test('TC2 mock case', async ({ page }) => {
        allure.epic('E0100: Sales');
        allure.tag('Compensation group calibration');
        allure.story('TC2 mock case: calibrated component, per-order override, uncalibrated menu and manual lines');
        allure.severity('critical');

        // masterdata + 1 order with quick input, 2 line edits, a manual line, 4 advanced edits
        test.setTimeout(600000);

        // ============================================================
        // Masterdata (UOM precisions as on the customer system: PCE 0, LTR 2)
        // ============================================================
        const masterdata = await Backend.createMasterdata({
            request: {
                login: { user: { language: LANGUAGE, firstname: 'first', lastname: 'last' } },
                uoms: { PCE: { precision: 0 }, LTR: { precision: 2 } },
                bpGroups: { GROUP_STANDARD: { name: 'Group_Standard' } },
                bpartners: { SCHOOL_A: { name: 'School_A', bpGroup: 'GROUP_STANDARD' } },
                products: {
                    MENU_WED_DESSERT: {
                        name: 'Menu_Wed_Dessert',
                        type: 'Item',
                        isStocked: false,
                        uom: 'PCE',
                        compensationGroupSchema: 'schema_menu_wed_dessert',
                        prices: [{ price: 5, currencyCode: 'EUR' }],
                    },
                    RICE_PUDDING: { name: 'Rice_Pudding', type: 'Item', uom: 'LTR', prices: [{ price: 2, currencyCode: 'EUR' }] },
                    MANUAL_ITEM: { name: 'Manual_Item', type: 'Item', prices: [{ price: 1, currencyCode: 'EUR' }] },
                },
                warehouses: { wh: {} },
                compensationGroupSchemas: {
                    schema_menu_wed_dessert: {
                        name: 'Menu_Wed_Dessert schema',
                        templateLines: [
                            { product: 'MENU_WED_DESSERT', qty: 1, uom: 'PCE' },
                            { product: 'RICE_PUDDING', qty: 0.25, uom: 'LTR' },
                        ],
                    },
                },
                // rule 10: the customer School_A (the customer is in BP group Group_Standard, which holds many customers)
                calibrationRules: { RULE10: { seqNo: 10, bpartner: 'SCHOOL_A', factor: 0.8 } },
            },
        });
        const schoolA = masterdata.bpartners.SCHOOL_A.bpartnerCode;
        const menu = masterdata.products.MENU_WED_DESSERT.productCode;
        const rice = masterdata.products.RICE_PUDDING.productCode;
        const manualItem = masterdata.products.MANUAL_ITEM.productCode;

        await LoginPage.goto();
        await LoginPage.login(masterdata.login.user);
        await DashboardPage.expectVisible();

        let orderId;

        await test.step('quick input: 1.500 menus -> menu line 1.500 (group hidden), Rice_Pudding 300 LTR (0,8 / rule 10 / 375, read-only)', async () => {
            const order = await createOrder(page, schoolA);
            orderId = order.orderId;
            await addMenuThroughQuickInput(page, menu, 1500);

            await expectLineQty(page, menu, 1500);
            await expectLineQty(page, rice, 300);
            await expectNoCalibrationGridColumn(page);

            // the menu line itself is not calibrated: rule 10 matches the customer, but a menu line is skipped
            await expectCalibrationGroupHidden(page, menu);
            // the component shows factor, rule (by name) and uncalibrated quantity, all read-only
            await expectCalibrationGroup(page, rice, { factor: '0.8', rulePrefix: '10', uncalibrated: '375', snapshotName: 'advanced-edit-rice-pudding-calibrated' });

            await Backend.expect({
                title: 'Mock case: menu line not calibrated, Rice_Pudding 300 LTR at factor 0,8 (uncalibrated 375)',
                salesOrders: {
                    [orderId]: {
                        lines: [
                            { product: 'MENU_WED_DESSERT', qtyEntered: 1500, calibrated: false },
                            { product: 'RICE_PUDDING', qtyEntered: 300, calibrationFactor: 0.8, calibrationRule: 'RULE10', qtyEnteredUncalibrated: 375 },
                        ],
                    },
                },
            });
        });

        await test.step('the clerk types Menge 375 on Rice_Pudding (the mock\'s value): factor 0,8, rule 10, uncalibrated 375 stay', async () => {
            await setLineQty(page, rice, 375);

            await expectLineQty(page, rice, 375);
            await expectCalibrationGroup(page, rice, { factor: '0.8', rulePrefix: '10', uncalibrated: '375', snapshotName: 'advanced-edit-rice-pudding-after-override' });

            await Backend.expect({
                title: 'Mock case: line Menge overridden to 375, calibration data unchanged',
                salesOrders: {
                    [orderId]: {
                        lines: [{ product: 'RICE_PUDDING', qtyEntered: 375, calibrationFactor: 0.8, calibrationRule: 'RULE10', qtyEnteredUncalibrated: 375 }],
                    },
                },
            });
        });

        await test.step('menu line Menge 1.500 -> 1.600 leaves Rice_Pudding at 375', async () => {
            await setLineQty(page, menu, 1600);

            await expectLineQty(page, menu, 1600);
            await expectLineQty(page, rice, 375);

            await Backend.expect({
                title: 'Mock case: no automatic rescale of the component',
                salesOrders: {
                    [orderId]: {
                        lines: [
                            { product: 'MENU_WED_DESSERT', qtyEntered: 1600, calibrated: false },
                            { product: 'RICE_PUDDING', qtyEntered: 375, calibrationFactor: 0.8, calibrationRule: 'RULE10', qtyEnteredUncalibrated: 375 },
                        ],
                    },
                },
            });
        });

        await test.step('a manually added line: calibration group hidden; no calibration column in the grid', async () => {
            await addMenuThroughQuickInput(page, manualItem, 7, { expectedRows: 3 });

            await expectLineQty(page, manualItem, 7);
            await expectCalibrationGroupHidden(page, manualItem);
            await expectNoCalibrationGridColumn(page);
            // the other lines are untouched
            await expect(lineRow(page, rice)).toHaveCount(1);
            await expectLineQty(page, rice, 375);

            await Backend.expect({
                title: 'Mock case: manual line not calibrated',
                salesOrders: {
                    [orderId]: {
                        lines: [{ product: 'MANUAL_ITEM', qtyEntered: 7, calibrated: false }],
                    },
                },
            });
        });
    });
});
