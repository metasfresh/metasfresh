import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { expectErrorToast } from '../../utils/common';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';

const createMasterdata = async ({ showLastPickedBestBeforeDateForLines } = {}) => {
    return await Backend.createMasterdata({
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
                    pickWithNewLU: true,
                    allowNewTU: true,
                    allowCompletingPartialPickingJob: true,
                    showLastPickedBestBeforeDateForLines: showLastPickedBestBeforeDateForLines ?? false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: {
                "wh": {},
            },
            pickingSlots: {
                slot1: {},
            },
            products: {
                "P1": {
                    valuePrefix: '00027', // important for EAN13 barcodes
                    gtin: '97311876341811',
                    ean13ProductCode: '4888',
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }]
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 100 }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Leich+Mehl', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
        ],
    });
    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('GS1', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            '019731187634181131030075201527080910501',
        ],
    });
    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('EAN13 with prefix 28', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            '2800027002616',
        ],
    });
    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('EAN13 with prefix 28 and not matching product', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await expectErrorToast('Not matching EAN13 product code', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            catchWeightQRCode: [
                '2899999002618',
            ],
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('EAN13 with prefix 29', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            '2948882005745',
        ],
    });
    await PickingJobScreen.complete();
});

test('EAN13 with prefix 29 and not matching product', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await expectErrorToast('Not matching EAN13 product code', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            catchWeightQRCode: [
                '2999992005743',
            ],
        });
    });
});


// noinspection JSUnusedLocalSymbols
test('Check Last BestBeforeDate is displayed when MobileUIPickingProfile.ShowLastPickedBestBeforeDateForLines = Y', async ({ page }) => {
    const masterdata = await createMasterdata({ showLastPickedBestBeforeDateForLines: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            'LMQ#1#0.101#08.11.2025#500',
        ],
    });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.expectHeaderProperty({
        caption: "Last Best Before Date",
        value: "11/08/2025",
    });
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            'LMQ#1#0.101#09.11.2025#500',
        ],
    });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.expectHeaderProperty({
        caption: "Last Best Before Date",
        value: "11/09/2025",
    });
});
