import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';
import { expectErrorToast } from '../../utils/common';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';

const createMasterdata = async () => {
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
                    pickWithNewLU: true,
                    allowNewTU: true,
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
        await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 72 });
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
            await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: 5/*TU*/, qtyEntered: 1/*TU*/, qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND });
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
    });

    await PickingJobsListScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('Filter by EAN13', async ({ page }) => {
    const masterdata = await createMasterdata();

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
});