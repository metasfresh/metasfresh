import { test } from "../../../playwright.config";
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { page } from "../../utils/common";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

/**
 * Covers the block-layout switch on the PRODUCT_NAMES picking-launcher field: with the switch on, a
 * job-list entry renders each product name on its own line (no separator character between them),
 * the shape does not depend on how many products the order holds, and the caption picks up the
 * left-aligned multiline styling only when it actually spans several lines.
 *
 * Modeled on productNames.spec.js / productNamesDetail.spec.js (same masterdata idiom); those two
 * specs are untouched and keep exercising the switch-off (today's) behavior.
 */
const createMasterdata = async ({ isBlockLayout, lines, workplace }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US", workplace },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                    // Scope the job list to this test's own workplace so it never sees jobs created
                    // by other tests sharing the same picking job list (each test gets its own
                    // distinct workplace — see the `workplace` param below).
                    activeWorkplaceRequired: true,
                    considerOnlyJobScheduledToWorkplace: true,
                    fields: [
                        { field: 'DOCUMENT_NO' },
                        { field: 'CUSTOMER' },
                        { field: 'PRODUCT_NAMES', isShowInDetailed: false, isBlockLayout },
                        { field: 'QTY_TO_DELIVER' },
                    ],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            workplaces: { [workplace]: {} },
            products: {
                "P1": { price: 1 },
                "P2": { price: 1 },
                "P3": { price: 1 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: lines.map(line => ({ ...line, workplace })),
                }
            },
        }
    });
}

// The multiline styling hook lives on the span wrapping the caption text (ButtonWithIndicator.jsx),
// scoped by the launcher's own data-salesorderid attribute (same identity WFLauncherButton exposes
// for PickingJobsListScreen's locateJobButtons).
const captionSpanLocator = (salesOrderId) =>
    page.locator(`.wflauncher-button[data-salesorderid="${salesOrderId}"] .caption-btn .row span`);

const CAPTION_MULTILINE_CLASS = /(^|\s)caption-multiline(\s|$)/;

// noinspection JSUnusedLocalSymbols
test('Multi-product order lists all product names one per line', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher block layout');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        isBlockLayout: true,
        lines: [
            { product: 'P1', qty: 5 },
            { product: 'P2', qty: 3 },
            { product: 'P3', qty: 2 },
        ],
        workplace: 'workplace1',
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    // Playwright's toHaveText normalizes whitespace (a line break included) but not other
    // characters, so a "\n" join here still distinguishes "no separator" from a literal ", "/" | ":
    // an actual comma or pipe in the rendered text would not match this expectation.
    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
    ].join(" | ") + "\n" + [
        masterdata.products.P1.productName,
        masterdata.products.P2.productName,
        masterdata.products.P3.productName,
    ].join("\n");

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption },
    ]);

    // AC7/TC9: a caption spanning several lines carries the multiline styling hook.
    await expect(captionSpanLocator(masterdata.salesOrders.SO1.id)).toHaveClass(CAPTION_MULTILINE_CLASS);
});

// noinspection JSUnusedLocalSymbols
test('Single-product order renders the same block shape', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher block layout');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        isBlockLayout: true,
        lines: [
            { product: 'P1', qty: 5 },
        ],
        workplace: 'workplace2',
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    // Same block shape as the multi-product entry above — the entry shape does not depend on how
    // many products the order holds (AC4): the single product still gets its own line, on both
    // sides of which the " | " separator becomes a line break instead.
    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
    ].join(" | ") + "\n" + masterdata.products.P1.productName + "\n" + "5 Stk";

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption },
    ]);

    // AC7/TC9: still multiline (the block plus its neighbours span several lines), same as TC1.
    await expect(captionSpanLocator(masterdata.salesOrders.SO1.id)).toHaveClass(CAPTION_MULTILINE_CLASS);
});

// noinspection JSUnusedLocalSymbols
test('Switch off keeps a single-line caption without the multiline styling', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher block layout');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        isBlockLayout: false,
        lines: [
            { product: 'P1', qty: 5 },
            { product: 'P2', qty: 3 },
            { product: 'P3', qty: 2 },
        ],
        workplace: 'workplace3',
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
        [masterdata.products.P1.productName, masterdata.products.P2.productName, masterdata.products.P3.productName].join(", "),
    ].join(" | ");

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption },
    ]);

    // AC7/TC9: a single-line caption does not carry the multiline styling hook.
    await expect(captionSpanLocator(masterdata.salesOrders.SO1.id)).not.toHaveClass(CAPTION_MULTILINE_CLASS);
});
