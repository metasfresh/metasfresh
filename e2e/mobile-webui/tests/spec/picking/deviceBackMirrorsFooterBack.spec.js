import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { pressDeviceBack } from "../../utils/common";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: "workplace1" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['CU', 'LU_CU'],
                    allowCompletingPartialPickingJob: false,
                    allowPickingAnyCustomer: false,
                    customers: [
                        { customer: "customer1" },
                    ],
                }
            },
            bpartners: { "customer1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                "P1": { price: 1 },
            },
            packingInstructions: {
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 10 },
                    ]
                }
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Device/browser Back navigates like the footer Back button', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Device/browser Back replays the in-app footer Back navigation');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    //
    // Log in and go two screens deep: Home -> Picking jobs list -> a picking job.
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.waitForScreen();

    //
    // Establish the footer Back target of the job screen: it goes back to the jobs list.
    await PickingJobScreen.goBack();
    await PickingJobsListScreen.waitForScreen();

    //
    // Re-open the job, then press the device/browser Back: it must do the SAME thing as the footer
    // Back button — go back to the jobs list.
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.waitForScreen();
    await pressDeviceBack();
    await PickingJobsListScreen.waitForScreen();

    //
    // Device/browser Back from the jobs list goes Home (the app list) — again exactly like the
    // footer Back button on that screen (the jobs list declares its back target as Home).
    await pressDeviceBack();
    await ApplicationsListScreen.expectVisible();
});
