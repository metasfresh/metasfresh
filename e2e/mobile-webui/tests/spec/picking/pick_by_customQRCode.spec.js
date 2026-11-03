import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { expect } from '@playwright/test';
import { expectErrorToast } from '../../utils/common';

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
                    pickTo: ['TU'],
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
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick using custom QR code');
    allure.severity('normal');

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

//
// ===== Custom label with a GTIN part =====
// The label carries the article's GTIN instead of its product code. Picking must check that GTIN
// against the picking line's product (like it does for a GS1 GTIN), and the partial unpick must
// find the product by it.
//
// Label layout: GTIN 1-14, LOT 15-19, BEST_BEFORE_DATE 20-25 (yyMMdd), WEIGHT_KG 26-31.
//   e.g. '02001234567893' + 'L2311' + '271231' + '099999'  (weight 99.999 kg, same weight shape as the product-code format above)
//

// Unique per run (the strict GTIN lookup must find exactly one product; the DB keeps products of earlier runs).
let _gtinSeq = 0;
const uniqueGtin14 = (prefix) => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');
    return `${prefix}${seq}${ts}`; // 1 + 2 + 11 = 14 digits
};
// 14 digits: a stored GTIN is a leading 0 + a 13-digit EAN (leading zero must survive).
const uniqueGtin14Padded = () => uniqueGtin14('0');
const gtinLabel = (gtin) => `${gtin}L2311271231099999`;

const createGtinMasterdata = async ({ expectedGtin, otherGtin }) => {
    const base = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
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
                // the product of the picking line; its stored GTIN is the one the label must carry
                "BOM": {
                    gtin: expectedGtin,
                    randomValue: { size: 4, isIncludeDigits: true },
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
                // another article, not in the picking job
                "OTHER": { gtin: otherGtin, prices: [{ price: 1 }] },
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
                    name: 'my gtin label',
                    parts: [
                        { startPosition: 1, endPosition: 14, type: 'GTIN' },
                        { startPosition: 15, endPosition: 19, type: 'LOT' },
                        { startPosition: 20, endPosition: 25, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
                        { startPosition: 26, endPosition: 31, type: 'WEIGHT_KG' },
                    ],
                }
            ],
        }
    });
    masterdata = base;
    return base;
};

// Receives one BOM HU with the given label (the label is read by the manufacturing receipt) and checks what the receipt stored.
const receiveHUWithLabel = async ({ label }) => {
    const [huQRCode] = await manufactureBOMs({ catchWeightQRCode: label, count: 1 });
    await Backend.expect({
        hus: {
            [huQRCode]: {
                huStatus: 'A',
                storages: { 'BOM': '1 PCE' },
                attributes: { 'WeightNet': '99.999', HU_QRCode: label },
                cus: [
                    { qty: '1 PCE', attributes: { 'WeightNet': '99.999', 'HU_BestBeforeDate': '2027-12-31', 'Lot-Nummer': 'L2311' } },
                ]
            },
        }
    });
    return huQRCode;
};

const startPickingJobAtScanScreen = async () => {
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen' });
};

// noinspection JSUnusedLocalSymbols
test('custom GTIN part of other product → product not matching', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Pick using custom QR code with a GTIN part');
    allure.severity('critical');

    const otherGtin = uniqueGtin14('9');
    await createGtinMasterdata({ expectedGtin: uniqueGtin14('8'), otherGtin });
    await LoginScreen.login(masterdata.login.user);

    // the HU the label sits on belongs to the picking line's product, but the label carries ANOTHER article's GTIN
    const label = gtinLabel(otherGtin);
    await receiveHUWithLabel({ label });

    await startPickingJobAtScanScreen();
    await expectErrorToast('Scan label with the GTIN of another product', async () => {
        await PickLineScanScreen.typeQRCode(label);
    }, ({ textContent }) => {
        expect(textContent).toContain('PICKING_QR_PRODUCT_NOT_MATCHING');
    });
    await PickLineScanScreen.waitForScreen();

    // nothing picked
    await PickLineScanScreen.goBack();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
});

// noinspection JSUnusedLocalSymbols
test('custom GTIN part of expected product → picked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Pick using custom QR code with a GTIN part');
    allure.severity('critical');

    const expectedGtin = uniqueGtin14('8');
    await createGtinMasterdata({ expectedGtin, otherGtin: uniqueGtin14('9') });
    await LoginScreen.login(masterdata.login.user);

    const label = gtinLabel(expectedGtin);
    const huQRCode = await receiveHUWithLabel({ label });

    await startPickingJobAtScanScreen();
    await PickLineScanScreen.pickHU({ qrCode: label });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '99.999 kg' });
    await Backend.expect({ hus: { [huQRCode]: { huStatus: 'S', storages: { 'BOM': '1 PCE' } } } });
});

// noinspection JSUnusedLocalSymbols
test('partial unpick by GTIN part', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Pick using custom QR code with a GTIN part');
    allure.severity('critical');

    const expectedGtin = uniqueGtin14('8');
    await createGtinMasterdata({ expectedGtin, otherGtin: uniqueGtin14('9') });
    await LoginScreen.login(masterdata.login.user);

    const label = gtinLabel(expectedGtin);
    await receiveHUWithLabel({ label });

    await startPickingJobAtScanScreen();
    await PickLineScanScreen.pickHU({ qrCode: label });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '99.999 kg' });

    // the operator scans the same label to say which article to unpick: the GTIN part finds the product in the job
    await PickingJobScreen.unpickItemToFloor({ scannedCode: label, qty: '1' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
});

// noinspection JSUnusedLocalSymbols
test('14-digit stored GTIN (leading 0 + 13-digit EAN) of the expected product → picked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Pick using custom QR code with a GTIN part');
    allure.severity('critical');

    // M_Product.GTIN stored as 14 digits with a leading zero (0 + EAN-13): the leading zero must survive the lookup
    const expectedGtin = uniqueGtin14Padded();
    expect(expectedGtin).toMatch(/^0\d{13}$/);
    await createGtinMasterdata({ expectedGtin, otherGtin: uniqueGtin14('9') });
    await LoginScreen.login(masterdata.login.user);

    const label = gtinLabel(expectedGtin);
    const huQRCode = await receiveHUWithLabel({ label });

    await startPickingJobAtScanScreen();
    await PickLineScanScreen.pickHU({ qrCode: label });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '99.999 kg' });
    await Backend.expect({ hus: { [huQRCode]: { huStatus: 'S', storages: { 'BOM': '1 PCE' } } } });
});

// noinspection JSUnusedLocalSymbols
test('custom GTIN part of no product → product not matching', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Pick using custom QR code with a GTIN part');
    allure.severity('critical');

    // a label of an article that does not exist in the system yet: no M_Product carries this GTIN
    const unknownGtin = uniqueGtin14('7');
    await createGtinMasterdata({ expectedGtin: uniqueGtin14('8'), otherGtin: uniqueGtin14('9') });
    await LoginScreen.login(masterdata.login.user);

    const label = gtinLabel(unknownGtin);
    await receiveHUWithLabel({ label });

    await startPickingJobAtScanScreen();
    await expectErrorToast('Scan label with the GTIN of no product', async () => {
        await PickLineScanScreen.typeQRCode(label);
    }, ({ textContent }) => {
        // the same answer as for the GTIN of another product, never the lookup exception
        expect(textContent).toContain('PICKING_QR_PRODUCT_NOT_MATCHING');
        expect(textContent).not.toContain('GTIN');
    });
    await PickLineScanScreen.waitForScreen();

    // nothing picked
    await PickLineScanScreen.goBack();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '0 kg' });
});

// noinspection JSUnusedLocalSymbols
test('partial unpick with a GTIN of no product → falls through, no exception', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Pick using custom QR code with a GTIN part');
    allure.severity('critical');

    const expectedGtin = uniqueGtin14('8');
    const unknownGtin = uniqueGtin14('7');
    await createGtinMasterdata({ expectedGtin, otherGtin: uniqueGtin14('9') });
    await LoginScreen.login(masterdata.login.user);

    const label = gtinLabel(expectedGtin);
    await receiveHUWithLabel({ label });

    await startPickingJobAtScanScreen();
    await PickLineScanScreen.pickHU({ qrCode: label });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '12 Stk', qtyPicked: '1 Stk', qtyPickedCatchWeight: '99.999 kg' });

    // the operator scans a label of no known article to say which article to unpick: the job's generic rejection, not the GTIN lookup exception
    await PickingJobScreen.clickUnpickItem();
    // the generic toast is shared by every server error, so the unpick-resolve response itself must carry the job's rejection text
    const resolvePromise = page.waitForResponse((r) => r.url().includes('/unpick/resolve') && r.request().method() === 'POST', { timeout: 30000 });
    await expectErrorToast('Scan label with the GTIN of no product to unpick', async () => {
        await PickingJobScreen.scanProductCodeToUnpick({ scannedCode: gtinLabel(unknownGtin) });
    }, ({ textContent }) => {
        expect(textContent).toMatch(/Please try again/);
    });
    const resolveResponse = await resolvePromise;
    expect(resolveResponse.status()).toBe(422); // AdempiereException
    const errorMessage = JSON.stringify(await resolveResponse.json());
    // falls through to the generic "no product of this job matches" rejection; the GTIN lookup's own '@NotFound@ Product: GTIN' text must not appear
    expect(errorMessage).toContain('Cannot find a product matching the scanned code in this picking job');
    expect(errorMessage).not.toContain('Not found');
    expect(errorMessage).not.toContain('GTIN');
    await PickingJobScreen.expectOnProductScanStage();
});
