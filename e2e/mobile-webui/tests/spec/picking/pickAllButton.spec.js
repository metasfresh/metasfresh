import { expect } from '@playwright/test';
import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { LAUNCHERS_LIMIT_SYSCONFIGS, createMasterdataRememberingSysconfigs, restoreSysconfigsAfterEach } from '../../utils/cuQtyEntryCommon';

restoreSysconfigsAfterEach(test);

const createMasterdata = async ({
                                    allowQuickPackAll = true,
                                    cuQtyEntryMode,
                                    customers = [{ customer: "customer1" }],
                                    aggregationType = 'sales_order',
                                    secondCustomer = false,
                                } = {}) => {
    const orderLines = [
        { product: 'P1', qty: 10 },
        { product: 'P2', qty: 10 },
        { product: 'P3', qty: 10 },
    ];
    return await createMasterdataRememberingSysconfigs({
        language: 'en_US',
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language: 'en_US', workplace: "workplace1" } },
            mobileConfig: {
                picking: {
                    aggregationType,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    allowQuickPackAll,
                    shipOnCloseLU: false,
                    pickTo: ['CU', 'LU_CU'],
                    allowCompletingPartialPickingJob: false,
                    allowPickingAnyCustomer: false,
                    ...(cuQtyEntryMode ? { cuQtyEntryMode } : {}),
                    customers,
                }
            },
            bpartners: { "customer1": {}, ...(secondCustomer ? { "customer2": {} } : {}) },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            workplaces: { "workplace1": { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                "P1": { price: 1 },
                "P2": { price: 2 },
                "P3": { price: 3 },
            },
            packingInstructions: {
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                "HU3": { product: 'P3', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: orderLines,
                },
                ...(secondCustomer ? {
                    "SO2": {
                        bpartner: 'customer2',
                        warehouse: 'wh',
                        datePromised: '2025-03-01T00:00:00.000+02:00',
                        lines: orderLines,
                    }
                } : {}),
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Pick using Pick All button', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick All button');
    allure.severity('normal');

    const masterdata = await createMasterdata({ allowQuickPackAll: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.clickPickAllButton();
    await PickingJobsListScreen.expectJobButtons([]);

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "10 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu1', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    },
                    P2: {
                        qtyPicked: [{ qtyPicked: "10 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu2', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLineId2' }]
                    },
                    P3: {
                        qtyPicked: [{ qtyPicked: "10 PCE", qtyTUs: 0, qtyLUs: 0, vhu: 'vhu3', tu: '-', lu: '-', processed: true, shipmentLineId: 'shipmentLineId3' }]
                    }
                }
            }
        },
        pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } }, // the queue is empty because LU everything is shipped now
        hus: {
            HU1: { huStatus: 'A', storages: { P1: '990 PCE' } },
            HU2: { huStatus: 'A', storages: { P2: '990 PCE' } },
            HU3: { huStatus: 'A', storages: { P3: '990 PCE' } },
            vhu1: { huStatus: 'E', storages: { P1: '10 PCE' }, bpartner: 'customer1', bpartnerLocation: 'customer1' },
            vhu2: { huStatus: 'E', storages: { P2: '10 PCE' }, bpartner: 'customer1', bpartnerLocation: 'customer1' },
            vhu3: { huStatus: 'E', storages: { P3: '10 PCE' }, bpartner: 'customer1', bpartnerLocation: 'customer1' },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Expect Pick All button hidden when feature is not active', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick All button');
    allure.severity('normal');

    const masterdata = await createMasterdata({ allowQuickPackAll: false });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.expectPickAllButtonHidden();
});

const MSG_QUICK_PACK_ALL_NOT_ALLOWED = "Quick Pack is not allowed";
const MODE_SCAN_ONCE = 'SCAN_ONCE_ENTER_QTY';
const MODE_SCAN_EACH = 'SCAN_EACH_CU';
const MODE_MANUAL = 'MANUAL';

const openJob = async (masterdata, { productJob = false } = {}) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    if (productJob) {
        // one job per product: P1 holds one line per customer (10 + 10)
        return await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 20, productId: masterdata.products.P1.id });
    }
    await PickingJobsListScreen.waitForJobVisible({ documentNo: masterdata.salesOrders.SO1.documentNo, reloadIfMissing: true });
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    return await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
};

const expectQuickPackAllRefused = async ({ pickingJobId, products = ['P1', 'P2', 'P3'] }) => {
    await PickingJobScreen.expectPickAllButtonHiddenAfterQtyAvailable();

    const { status, body } = await PickingJobScreen.postPickAllDirectly({ wfProcessId: `picking-${pickingJobId}` });
    expect(status, `answer: ${body}`).toBeGreaterThanOrEqual(400);
    expect(body).toContain(MSG_QUICK_PACK_ALL_NOT_ALLOWED);

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                docStatus: 'Drafted',
                shipmentSchedules: Object.fromEntries(products.map((product) => [product, { qtyPicked: [] }])),
            }
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('profile mode Scan once: no Quick Pack All button, the direct call is refused, nothing is picked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick All button with CU quantity entry modes');
    allure.severity('normal');

    const masterdata = await createMasterdata({ allowQuickPackAll: true, cuQtyEntryMode: MODE_SCAN_ONCE });
    const { pickingJobId } = await openJob(masterdata);
    await expectQuickPackAllRefused({ pickingJobId });
});

// noinspection JSUnusedLocalSymbols
test('profile Manual, customer record Scan each CU: no Quick Pack All button, the direct call is refused, nothing is picked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick All button with CU quantity entry modes');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        allowQuickPackAll: true,
        cuQtyEntryMode: MODE_MANUAL,
        customers: [{ customer: "customer1", cuQtyEntryMode: MODE_SCAN_EACH }],
    });
    const { pickingJobId } = await openJob(masterdata);
    await expectQuickPackAllRefused({ pickingJobId });
});

// noinspection JSUnusedLocalSymbols
test('mixed job (customer 1 Manual, customer 2 Scan once): no Quick Pack All button, the direct call is refused for the whole job', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick All button with CU quantity entry modes');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        allowQuickPackAll: true,
        aggregationType: 'product',
        secondCustomer: true,
        customers: [
            { customer: "customer1", cuQtyEntryMode: MODE_MANUAL },
            { customer: "customer2", cuQtyEntryMode: MODE_SCAN_ONCE },
        ],
    });
    const { pickingJobId } = await openJob(masterdata, { productJob: true });
    await expectQuickPackAllRefused({ pickingJobId, products: ['P1'] });   // the product job holds the lines of P1 only
});

// noinspection JSUnusedLocalSymbols
test('control: the same mixed two-customer product job with Manual only shows the Quick Pack All button', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick All button with CU quantity entry modes');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        allowQuickPackAll: true,
        aggregationType: 'product',
        secondCustomer: true,
        customers: [{ customer: "customer1" }, { customer: "customer2" }],
    });
    await openJob(masterdata, { productJob: true });
    await PickingJobScreen.expectPickAllButtonVisible();
});
