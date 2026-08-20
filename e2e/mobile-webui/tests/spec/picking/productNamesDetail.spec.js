import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

// noinspection JSUnusedLocalSymbols
test('Already-started job still lists its products', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher product names');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
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
                        { field: 'PRODUCT_NAMES', isShowInDetailed: false },
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
                    lines: [
                        { product: 'P1', qty: 5 },
                        { product: 'P2', qty: 3 },
                        { product: 'P3', qty: 2 },
                    ],
                }
            },
        }
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
        { salesOrderId: masterdata.salesOrders.SO1.id, caption, alreadyStarted: false },
    ]);

    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.goBack();

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption, alreadyStarted: true },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('Detail surfaces name the right subject', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher product names');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
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
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    anonymousPickHUsOnTheFly: false,
                    readAttributes: [],
                    // ProductNames configured for the DETAIL surfaces only (isShowInDetailed) — a
                    // configuration this customer will not use on the launcher, exercised here because
                    // the field type is available to any profile, not only the one this customer runs.
                    fields: [
                        { field: 'PRODUCT_NAMES', isShowInDetailed: true },
                    ],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { price: 1 },
                "P2": { price: 1 },
            },
            packingInstructions: {
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    // P1 appears on two separate lines, P2 on one — the job holds two distinct products.
                    lines: [
                        { product: 'P1', qty: 3 },
                        { product: 'P2', qty: 2 },
                        { product: 'P1', qty: 1 },
                    ],
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    // Job header: each of the job's distinct products named once, in first-occurrence (line) order.
    // PickingJobScreen has no expectHeaderProperty of its own (every sibling screen — jobs list, job
    // line, material-receipt line, workplace manager — defines one over the same shared page-global
    // `.view-header` row); reusing PickingJobLineScreen's existing, unmodified implementation here.
    const jobHeaderProductNames = [masterdata.products.P1.productName, masterdata.products.P2.productName].join(", ");
    await PickingJobLineScreen.expectHeaderProperty({ caption: 'Product names (all)', value: jobHeaderProductNames });

    // Opened line (the P2 line, index 2): names only its own product, never the job's other product(s).
    await PickingJobScreen.clickLineButton({ index: 2 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.expectHeaderProperty({ caption: 'Product names (all)', value: masterdata.products.P2.productName });
    await PickingJobLineScreen.goBack();
});
