import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

/**
 * Covers the EXTERNAL_SYSTEM picking-launcher field type: the launcher caption and the
 * job-detail header show the external system's Name, the value survives STARTING the job (the two
 * halves of the launcher list read from different sources), and an order that came in through no
 * external system degrades to the remaining fields with no placeholder.
 */
const createMasterdata = async ({ workplace, externalSystem, showInDetailed = false }) => {
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
                    // Scope the job list to this test's own workplace so it never sees jobs created by
                    // other tests sharing the same picking job list (each test gets its own workplace).
                    activeWorkplaceRequired: true,
                    considerOnlyJobScheduledToWorkplace: true,
                    fields: [
                        { field: 'DOCUMENT_NO' },
                        { field: 'CUSTOMER' },
                        { field: 'EXTERNAL_SYSTEM', isShowInDetailed: showInDetailed },
                    ],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            workplaces: { [workplace]: {} },
            products: { "P1": { price: 1 } },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    externalSystem,
                    lines: [{ product: 'P1', qty: 5, workplace }],
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Launcher caption shows the external system name', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher external system');
    allure.severity('normal');

    const masterdata = await createMasterdata({ workplace: 'workplace1', externalSystem: 'Shopware6' });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    // the NAME ("Shopware 6"), not the "Shopware6" Value code — asserted off masterdata, not a literal
    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
        masterdata.salesOrders.SO1.externalSystemName,
    ].join(" | ");

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption, alreadyStarted: false },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('The external system survives starting the job, and reaches the detail header', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher external system');
    allure.severity('normal');

    const masterdata = await createMasterdata({ workplace: 'workplace2', externalSystem: 'Shopware6', showInDetailed: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
        masterdata.salesOrders.SO1.externalSystemName,
    ].join(" | ");

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption, alreadyStarted: false },
    ]);

    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    // the open job reads M_Picking_Job, a different source than the launcher's M_Packageable_V
    await PickingJobScreen.expectHeaderProperty({
        caption: 'External System',
        value: masterdata.salesOrders.SO1.externalSystemName,
    });

    await PickingJobScreen.goBack();

    // and the started entry in the list still carries it
    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption, alreadyStarted: true },
    ]);
});

// noinspection JSUnusedLocalSymbols
test('An order with no external system shows no placeholder', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking launcher external system');
    allure.severity('normal');

    const masterdata = await createMasterdata({ workplace: 'workplace3', externalSystem: null });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    const caption = [
        masterdata.salesOrders.SO1.documentNo,
        masterdata.bpartners.BP1.bpartnerCode,
    ].join(" | ");

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, caption, alreadyStarted: false },
    ]);
});
