import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { expectErrorToast } from '../../utils/common';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';

const createMasterdata = async ({
                                    showLastPickedBestBeforeDateForLines,
                                    productValuePrefix,
                                    productRandomValue,
                                    customQRCodeFormats = []
                                } = {}) => {
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
                    valuePrefix: productValuePrefix,
                    randomValue: productRandomValue,
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
            customQRCodeFormats,
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Manual', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        switchToManualInput: true,
        qtyEntered: '7',
        catchWeight: '0.789',
        qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '7 Stk', qtyPickedCatchWeight: '789 g' });

    // await PickingJobScreen.complete();
    // await Backend.expect({
    //     pickings: {
    //         [pickingJobId]: {
    //             shipmentSchedules: {
    //                 P1: {
    //                     qtyPicked: [
    //                         { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
    //                         { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu2', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
    //                         { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu3', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
    //                         { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu4', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
    //                         { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu5', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
    //                     ]
    //                 }
    //             }
    //         }
    //     }
    // });
});

// noinspection JSUnusedLocalSymbols
test('Leich+Mehl', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
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
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '5 Stk', qtyPickedCatchWeight: '505 g' });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
                            { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu2', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
                            { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu3', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
                            { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu4', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
                            { qtyPicked: "1 PCE", catchWeight: "0.101 KGM", qtyTUs: 1, qtyLUs: 1, vhuId: '-', tu: 'tu5', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' },
                        ]
                    }
                }
            }
        }
    });
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

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            '019731187634181131030075201527080910501',
        ],
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '7.52 kg' });

    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('EAN13 with prefix 28', async ({ page }) => {
    const masterdata = await createMasterdata({ productValuePrefix: '00027' });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            '2800027002616',
        ],
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '261 g' });

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

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode: [
            '2948882005745',
        ],
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '574 g' });

    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
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
test('Custom QR code format', async ({ page }) => {
    const masterdata = await createMasterdata({
        productRandomValue: {
            size: 4,
            isIncludeDigits: true,
        },
        customQRCodeFormats: [
            {
                name: 'my custom code',
                parts: [
                    { startPosition: 1, endPosition: 4, type: 'PRODUCT_CODE' },
                    { startPosition: 5, endPosition: 10, type: 'WEIGHT_KG' },
                    { startPosition: 11, endPosition: 18, type: 'LOT' },
                    { startPosition: 19, endPosition: 24, type: 'IGNORE' },
                    { startPosition: 25, endPosition: 30, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
                ],
            }
        ]
    });

    const catchWeightQRCode = `${masterdata.products.P1.productCode}09999900000123250403260410`;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        catchWeightQRCode,
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '99.999 kg' });

    await PickingJobScreen.complete();
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
