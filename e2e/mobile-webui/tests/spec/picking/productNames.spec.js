import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

/**
 * Covers the PRODUCT_NAMES picking-launcher field type on the job LIST: a multi-product order's
 * caption lists every product name (joined by ", ", no quantity), a single-product order looks
 * exactly like before, and the existing PRODUCT_NAME field type stays untouched for a multi-product
 * order (blank, as before this feature existed).
 */
const createMasterdata = async ({ productField, lines }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
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
                    fields: [
                        { field: 'DOCUMENT_NO' },
                        { field: 'CUSTOMER' },
                        { field: productField, isShowInDetailed: false },
                        { field: 'QTY_TO_DELIVER' },
                    ],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
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
                    lines,
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Multi-product order lists all product names', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher product names');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        productField: 'PRODUCT_NAMES',
        lines: [
            { product: 'P1', qty: 5 },
            { product: 'P2', qty: 3 },
            { product: 'P3', qty: 2 },
        ],
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
});

// noinspection JSUnusedLocalSymbols
test('Single-product order looks unchanged', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher product names');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        productField: 'PRODUCT_NAMES',
        lines: [
            { product: 'P1', qty: 5 },
        ],
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
        masterdata.products.P1.productName,
        "5 Stk",
    ].join(" | ");

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('The existing PRODUCT_NAME field type is untouched', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher product names');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        productField: 'PRODUCT_NAME',
        lines: [
            { product: 'P1', qty: 5 },
            { product: 'P2', qty: 3 },
            { product: 'P3', qty: 2 },
        ],
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
    ].join(" | ");

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption },
    ]);
});
