import { test } from "../../../../playwright.config";
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog } from '../../../utils/screens/picking/GetQuantityDialog';

const createMasterdata = async ({ externalBarcode }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "product",
                    allowPickingAnyCustomer: false,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    filterByQRCode: false,
                    anonymousPickHUsOnTheFly: false,
                    customers: [
                        { customer: "customer1" },
                        { customer: "customer2" },
                        { customer: "customer3" },
                    ],
                }
            },
            bpartners: {
                "customer1": { gln: 'random' },
                "customer2": { gln: 'random' },
                "customer3": { gln: 'random' },
            },
            warehouses: {
                "wh": {},
            },
            pickingSlots: {
                slot1: {},
                slot2: {},
                slot3: {},
            },
            products: {
                "P1": { price: 1 },
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4', externalBarcode },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 20, piItemProduct: 'P1_4CU' },
                    ]
                },
                "SO2": {
                    bpartner: 'customer2',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 24, piItemProduct: 'P1_4CU' },
                    ]
                },
                "SO3": {
                    bpartner: 'customer3',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 28, piItemProduct: 'P1_4CU' },
                    ]
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Scan the pick from HU by ExternalBarcode', async ({ page }) => {
    const externalBarcode = "EXT" + Date.now();
    const masterdata = await createMasterdata({ externalBarcode });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    await test.step('Picking job for P1', async () => {
        const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 72 });
        await PickingJobScreen.scanPickFromHU({ qrCode: externalBarcode });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.P1_20x4.luName });

        await test.step('Line 1 - Pick entirely', async () => {
            await PickingJobScreen.clickLineButton({ index: 1 })
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 5 /*TU*/ });
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Line 2 - Pick entirely', async () => {
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
            await PickingJobScreen.clickLineButton({ index: 2 })
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 6 /*TU*/ });
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Line 3 - Pick entirely', async () => {
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot3.qrCode });
            await PickingJobScreen.clickLineButton({ index: 3 })
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 7 /*TU*/ });
        });

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.complete();
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [
                                // TODO find out why is not processed/shipped?!
                                { qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, vhu: 'tu11', tu: 'tu11', lu: 'lu11', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "24 PCE", qtyTUs: 6, qtyLUs: 1, vhu: 'tu12', tu: 'tu12', lu: 'lu12', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "28 PCE", qtyTUs: 7, qtyLUs: 1, vhu: 'tu13', tu: 'tu13', lu: 'lu13', processed: false, shipmentLineId: '-' },
                            ]
                        }
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.P1_HU.qrCode]: { huStatus: 'A', storages: { P1: '8  PCE' } },
                tu11: { huStatus: 'S', storages: { P1: '20 PCE' } },
                tu12: { huStatus: 'S', storages: { P1: '24 PCE' } },
                tu13: { huStatus: 'S', storages: { P1: '28 PCE' } },
            }
        });
    });

    await PickingJobsListScreen.waitForScreen();
});
