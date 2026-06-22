import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { pressDeviceBack, mashDeviceBack } from "../../utils/common";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { SelectPickTargetLUScreen } from "../../utils/screens/picking/SelectPickTargetLUScreen";
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

// `page` param is required even though unused here: it triggers the playwright.config `page` fixture,
// which calls setCurrentPage() so the screen objects' module-global `page` is wired up.
// noinspection JSUnusedLocalSymbols
test('Device/browser Back is a pure no-op; only the footer Back navigates', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Device/browser Back does nothing — the operator never leaves the app and stays on the current screen');
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
    // The device/browser Back button does NOTHING on the job screen: the job screen stays put.
    // Press it twice — the reported failure was that the SECOND press dropped the operator out of
    // the app entirely. It must remain a no-op on every press.
    await pressDeviceBack();
    await PickingJobScreen.waitForScreen();
    await pressDeviceBack();
    await PickingJobScreen.waitForScreen();

    //
    // The on-screen footer Back button still navigates — back to the jobs list.
    await PickingJobScreen.goBack();
    await PickingJobsListScreen.waitForScreen();

    //
    // The device/browser Back button is likewise a no-op on the jobs list: still the jobs list,
    // never out of the app — again, two presses.
    await pressDeviceBack();
    await PickingJobsListScreen.waitForScreen();
    await pressDeviceBack();
    await PickingJobsListScreen.waitForScreen();

    //
    // The footer Back button on the jobs list navigates Home (the app list).
    await PickingJobsListScreen.goBack();
    await ApplicationsListScreen.expectVisible();
});

// `page` param is required even though unused here — see the note on the first test.
// noinspection JSUnusedLocalSymbols
test('Rapidly mashing device/browser Back stays put and never leaves the app', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Hammering the device/browser Back button many times in a row never moves the screen or drops the operator out of the app');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    //
    // Log in and go deep: Home -> Picking jobs list -> a picking job -> the "Neues LU" / select-target screen.
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.clickLUTargetButton();
    await SelectPickTargetLUScreen.waitForScreen();
    await SelectPickTargetLUScreen.expectTitle('Select Target');

    //
    // Mash the device/browser Back button rapidly — the reported failure was that several quick presses
    // popped through into earlier screens and eventually out of the PWA. It must stay a no-op: still the
    // SAME screen, still inside the app, and the title bar must NOT revert (it used to fall back to the
    // app caption "Picking").
    await mashDeviceBack(12);
    await SelectPickTargetLUScreen.waitForScreen();
    await SelectPickTargetLUScreen.expectTitle('Select Target');

    //
    // The footer Back button still works after the mashing — back to the job screen.
    await SelectPickTargetLUScreen.goBack();
    await PickingJobScreen.waitForScreen();

    //
    // Mash again on the job screen — still a no-op, still in the app.
    await mashDeviceBack(12);
    await PickingJobScreen.waitForScreen();
});
