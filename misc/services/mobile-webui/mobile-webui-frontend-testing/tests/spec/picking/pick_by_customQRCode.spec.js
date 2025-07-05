import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';

let masterdata = null;

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickWithNewLU: false,
                    allowNewTU: true,
                    allowCompletingPartialPickingJob: true,
                    showLastPickedBestBeforeDateForLines: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BOM": {
                    randomValue: {
                        size: 4,
                        isIncludeDigits: true,
                    },
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    },
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }]
                },
            },
            packingInstructions: {
                "BOM_PI": { tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 1000 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'BOM', qty: 12, piItemProduct: 'TU' }]
                }
            },
            customQRCodeFormats: [
                {
                    name: 'my custom code',
                    parts: [
                        { startPosition: 1, endPosition: 4, type: 'PRODUCT_CODE' },
                        { startPosition: 5, endPosition: 10, type: 'WEIGHT_KG' },
                        { startPosition: 11, endPosition: 18, type: 'LOT' },
                        { startPosition: 19, endPosition: 24, type: 'PRODUCTION_DATE', dateFormat: 'yyMMdd' },
                        { startPosition: 25, endPosition: 30, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
                    ],
                }
            ],
        }
    })
}

// noinspection JSUnusedLocalSymbols
const manufactureBOMs = async ({ catchWeightQRCode, count }) => await test.step(`Manufacture ${count} HUs for ${catchWeightQRCode}`, async () => {
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();

    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    const result = [];

    for (let i = 1; i <= count; i++) {
        const bomQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.BOM_PI.tuPITestId, numberOfHUs: 1 });

        await test.step(`Receive #${i}: ${catchWeightQRCode}`, async () => {
            await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
            await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: bomQRCode });
            // NOTE: the product code in the QR code is not validated because, at the moment, product codes from any type of barcode are not being validated.
            await MaterialReceiptLineScreen.receiveQty({
                catchWeightQRCode,
                expectGoBackToJob: false
            });
            await MaterialReceiptLineScreen.goBack();
            await ManufacturingJobScreen.waitForScreen();
        });

        result.push(bomQRCode);
    }

    await ManufacturingJobScreen.goBack();

    await ManufacturingJobsListScreen.goBack();

    return result;
});

// noinspection JSUnusedLocalSymbols
test('Pick using custom QR code', async ({ page }) => {
    masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);

    const catchWeightQRCode = `${masterdata.products.BOM.productCode}09999900000123250403260410`;
    const [mfgQRCode1, mfgQRCode2, mfgQRCode3, mfgQRCode4, mfgQRCode5] = await manufactureBOMs({
        catchWeightQRCode,
        count: 5
    });
    await Backend.expect({
        hus: {
            [mfgQRCode1]: {
                huStatus: 'A',
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '99.999', HU_QRCode: catchWeightQRCode },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '99.999', 'HU_BestBeforeDate': '2026-04-10', 'ProductionDate': '2025-04-03', 'Lot-Nummer': '123' } },
                ]
            },
            [mfgQRCode2]: {
                huStatus: 'A',
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '99.999', HU_QRCode: catchWeightQRCode },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '99.999', 'HU_BestBeforeDate': '2026-04-10', 'ProductionDate': '2025-04-03', 'Lot-Nummer': '123' } },
                ]
            },
            [mfgQRCode3]: {
                huStatus: 'A',
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '99.999', HU_QRCode: catchWeightQRCode },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '99.999', 'HU_BestBeforeDate': '2026-04-10', 'ProductionDate': '2025-04-03', 'Lot-Nummer': '123' } },
                ]
            },
            [mfgQRCode4]: {
                huStatus: 'A',
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '99.999', HU_QRCode: catchWeightQRCode },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '99.999', 'HU_BestBeforeDate': '2026-04-10', 'ProductionDate': '2025-04-03', 'Lot-Nummer': '123' } },
                ]
            },
            [mfgQRCode5]: {
                huStatus: 'A',
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '99.999', HU_QRCode: catchWeightQRCode },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '99.999', 'HU_BestBeforeDate': '2026-04-10', 'ProductionDate': '2025-04-03', 'Lot-Nummer': '123' } },
                ]
            },
        }
    });

    await test.step(`Pick`, async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen' });
        await PickLineScanScreen.pickHU({
            qrCode: catchWeightQRCode,
            // catchWeightQRCode,
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '99.999 kg' });
        await Backend.expect({
            hus: {
                [mfgQRCode1]: {
                    huStatus: 'S',
                    storages: { 'BOM': '1 PCE' },
                    attributes: { 'WeightNet': '99.999', HU_QRCode: catchWeightQRCode },
                    cus: [
                        { qty: '1 PCE', attributes: { 'WeightNet': '99.999', 'HU_BestBeforeDate': '2026-04-10', 'ProductionDate': '2025-04-03', 'Lot-Nummer': '123' } },
                    ]
                },
            }
        });


                await PickingJobScreen.complete();
    });
});
