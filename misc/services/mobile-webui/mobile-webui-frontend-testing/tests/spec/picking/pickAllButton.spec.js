import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

const createMasterdata = async ({ allowQuickPackAll = true } = {}) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: "workplace1" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    allowQuickPackAll,
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
                    lines: [
                        { product: 'P1', qty: 10 },
                        { product: 'P2', qty: 10 },
                        { product: 'P3', qty: 10 },
                    ]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Pick using Pick All button', async ({ page }) => {
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
            vhu1: { huStatus: 'E', storages: { P1: '10 PCE' } },
            vhu2: { huStatus: 'E', storages: { P2: '10 PCE' } },
            vhu3: { huStatus: 'E', storages: { P3: '10 PCE' } },
        }
    });

});

// noinspection JSUnusedLocalSymbols
test('Expect Pick All button hidden when feature is not active', async ({ page }) => {
    const masterdata = await createMasterdata({ allowQuickPackAll: false });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.expectPickAllButtonHidden();

});
