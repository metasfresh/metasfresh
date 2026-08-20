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

    // Own workplace so this test's job list is scoped to jobs it created itself, isolating it
    // from jobs created by other tests sharing the same picking job list.
    const workplace = 'workplace1';

    const masterdata = await Backend.createMasterdata({
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
                    activeWorkplaceRequired: true,
                    considerOnlyJobScheduledToWorkplace: true,
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
                    lines: [
                        { product: 'P1', qty: 5, workplace },
                        { product: 'P2', qty: 3, workplace },
                        { product: 'P3', qty: 2, workplace },
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

    // Own workplace so this test's job list is scoped to jobs it created itself, isolating it
    // from jobs created by other tests sharing the same picking job list.
    const workplace = 'workplace2';

    const masterdata = await Backend.createMasterdata({
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
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    anonymousPickHUsOnTheFly: false,
                    readAttributes: [],
                    activeWorkplaceRequired: true,
                    considerOnlyJobScheduledToWorkplace: true,
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
            workplaces: { [workplace]: {} },
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
                        { product: 'P1', qty: 3, workplace },
                        { product: 'P2', qty: 2, workplace },
                        { product: 'P1', qty: 1, workplace },
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

    // This picking profile is HU-level (pickTo includes LU/TU/CU targets), so scanning the
    // picking slot auto-navigates straight into the first eligible line's scan screen (real app
    // behavior — see PickingMobileApplication.openFirstEligiblePickingLineScanner). Go back to the
    // job screen from there, same as a real operator would, before checking the job header.
    await PickingJobScreen.scanPickingSlot({
        qrCode: masterdata.pickingSlots.slot1.qrCode,
        expectNextScreen: 'PickLineScanScreen',
        gotoPickingJobScreen: true,
    });

    // Job header: each of the job's distinct products named once, in first-occurrence (line) order.
    const jobHeaderProductNames = [masterdata.products.P1.productName, masterdata.products.P2.productName].join(", ");
    await PickingJobScreen.expectHeaderProperty({ caption: 'Product names (all)', value: jobHeaderProductNames });

    // Opened line (the P2 line, index 2): names only its own product, never the job's other product(s).
    await PickingJobScreen.clickLineButton({ index: 2 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.expectHeaderProperty({ caption: 'Product names (all)', value: masterdata.products.P2.productName });
    await PickingJobLineScreen.goBack();
});
