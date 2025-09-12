import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';
import { expectErrorToast } from '../../utils/common';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { generateEAN13 } from '../../utils/ean13';

const createMasterdata = async ({ filterByQRCode, anonymousPickHUsOnTheFly, displayPickingSlotSuggestions, P1_ean13ProductCode } = {}) => {
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
                    filterByQRCode: filterByQRCode ?? false,
                    anonymousPickHUsOnTheFly: anonymousPickHUsOnTheFly ?? false,
                    displayPickingSlotSuggestions,
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
                "P1": { price: 1, ean13ProductCode: P1_ean13ProductCode },
                "P2": { price: 1, bpartners: [{ bpartner: "customer1", cu_ean: '7617027667203' }] },
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4, tu_ean: '7617027667210' },
                "P2_7x3": { lu: "LU", qtyTUsPerLU: 7, tu: "P2_3CU", product: "P2", qtyCUsPerTU: 3 },
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4' },
                "P2_HU": { product: 'P2', warehouse: 'wh', packingInstructions: 'P2_7x3' },
                "P2_HU_2": { product: 'P2', warehouse: 'wh', packingInstructions: 'P2_7x3' },
                "P2_HU_3": { product: 'P2', warehouse: 'wh', packingInstructions: 'P2_7x3' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 20, piItemProduct: 'P1_4CU' },
                        { product: 'P2', qty: 21, piItemProduct: 'P2_3CU' },
                    ]
                },
                "SO2": {
                    bpartner: 'customer2',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 24, piItemProduct: 'P1_4CU' },
                        { product: 'P2', qty: 18, piItemProduct: 'P2_3CU' },
                    ]
                },
                "SO3": {
                    bpartner: 'customer3',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 28, piItemProduct: 'P1_4CU' },
                        { product: 'P2', qty: 15, piItemProduct: 'P2_3CU' },
                    ]
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Product based aggregation', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    await test.step('Picking job for P1', async () => {
        const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 72 });
        await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
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
                                { qtyPicked: "20 PCE", qtyTUs: 5, qtyLUs: 1, vhuId: 'tu11', tu: 'tu11', lu: 'lu11', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "24 PCE", qtyTUs: 6, qtyLUs: 1, vhuId: 'tu12', tu: 'tu12', lu: 'lu12', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "28 PCE", qtyTUs: 7, qtyLUs: 1, vhuId: 'tu13', tu: 'tu13', lu: 'lu13', processed: false, shipmentLineId: '-' },
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

    await test.step('Picking job for P2', async () => {
        await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 54 });
        await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P2_HU.qrCode });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.P2_7x3.luName });

        await test.step('Line 1 - Pick entirely', async () => {
            await PickingJobScreen.clickLineButton({ index: 1 })
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 7 /*TU*/ });
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Line 2 - Pick entirely, but fail because not enough qty', async () => {
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot2.qrCode });
            await PickingJobScreen.clickLineButton({ index: 2 })
            await expectErrorToast('Not enough TUs found', async () => {
                await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 6 /*TU*/ });
                await PickingJobScreen.waitForScreen();
            })
            await GetQuantityDialog.clickCancel();
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Line 2 - First scan a new pick from HU and then pick entirely again', async () => {
            await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P2_HU_2.qrCode });
            await PickingJobScreen.clickLineButton({ index: 2 })
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 6 /*TU*/ });
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Line 3 - partial pick the renaming qty from current pick from HU', async () => {
            await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot3.qrCode });
            await PickingJobScreen.clickLineButton({ index: 3 })
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 1/*TU*/, qtyEntered: 1/*TU*/, qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND });
            await PickingJobScreen.waitForScreen();
        });

        await test.step('Line 3 - Scan a new pick from HU and then pick entirely again', async () => {
            await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P2_HU_3.qrCode });
            await PickingJobScreen.clickLineButton({ index: 3 })
            await PickingJobLineScreen.waitForScreen();
            await PickingJobLineScreen.clickPickHUButton();
            await GetQuantityDialog.waitForDialog();
            await GetQuantityDialog.fillAndPressDone({
                expectQtyEntered: 0/*TU*/, // FIXME: is this OK to have zero here (because on previous pick we marked these 4 TUs as NotFound... because there was no other way to move forward)  
                qtyEntered: 4/*TU*/
            });
            await PickingJobLineScreen.waitForScreen();
            await PickingJobLineScreen.goBack();
        });

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.complete();

        /* TODO: to be fixed in some other issue
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P2: {
                            qtyPicked: [
                                // TODO find out why is not processed/shipped?!
                                { qtyPicked: "21 PCE", qtyTUs: 7, qtyLUs: 1, vhuId: 'tu21', tu: 'tu21', lu: 'P2_HU', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "18 PCE", qtyTUs: 6, qtyLUs: 1, vhuId: 'tu22', tu: 'tu22', lu: 'lu21', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "3 PCE", qtyTUs: 1, qtyLUs: 1, vhuId: 'tu23', tu: 'tu23', lu: 'P2_HU_2', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "12 PCE", qtyTUs: 4, qtyLUs: 1, vhuId: 'tu24', tu: 'tu24', lu: 'P2_HU_2', processed: false, shipmentLineId: '-' },
                            ]
                        }
                    }
                }
            },
            hus: {
                'P2_HU': { huStatus: 'S', storages: { P2: '21 PCE' } },
                'P2_HU_2': { huStatus: 'S', storages: { P2: '15 PCE' } },
                'P2_HU_3': { huStatus: 'A', storages: { P2: '18 PCE' } },
            }
        });
         */
    });

    await PickingJobsListScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('Filter by EAN13', async ({ page }) => {
    const P1_ean13 = generateEAN13();
    const masterdata = await createMasterdata({ filterByQRCode: true, P1_ean13ProductCode: P1_ean13.productCode });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.expectJobButtons([
        { qtyToDeliver: 72, productId: masterdata.products.P1.id },
        { qtyToDeliver: 54, productId: masterdata.products.P2.id },
    ]);

    await test.step('Filter by TU EAN (associated to P1)', async () => {
        await PickingJobsListScreen.filterByQRCode('7617027667210');
        await PickingJobsListScreen.expectJobButtons([
            { qtyToDeliver: 72, productId: masterdata.products.P1.id },
        ])
    });

    await test.step('Clear filter', async () => {
        await PickingJobsListScreen.clearQRCodeFilter();
        await PickingJobsListScreen.expectJobButtons([
            { qtyToDeliver: 72, productId: masterdata.products.P1.id },
            { qtyToDeliver: 54, productId: masterdata.products.P2.id },
        ]);
    });

    await test.step('Filter by CU EAN (associated to P2 and customer1)', async () => {
        await PickingJobsListScreen.filterByQRCode('7617027667203');
        await PickingJobsListScreen.expectJobButtons([
            { qtyToDeliver: 21, productId: masterdata.products.P2.id },
        ]);
    });

    await test.step('Filter by EAN13 Product Code', async () => {
        await PickingJobsListScreen.filterByQRCode(P1_ean13.ean13);
        await PickingJobsListScreen.expectJobButtons([
            { qtyToDeliver: 72, productId: masterdata.products.P1.id },
        ]);
    });
});

// noinspection JSUnusedLocalSymbols
test('Anonymous pick HUs on the fly', async ({ page }) => {
    const masterdata = await createMasterdata({ anonymousPickHUsOnTheFly: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.expectJobButtons([
        { qtyToDeliver: 72, productId: masterdata.products.P1.id },
        { qtyToDeliver: 54, productId: masterdata.products.P2.id },
    ]);

    await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 72 });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.P1_20x4.luName });

    // NOTE: we are not scanning the pick from HU, because we want to pick on the fly

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
});

