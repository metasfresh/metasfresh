import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { buildCULabel, buildCULabelFormat, defaultBestBefore, uniqueGTIN, uniqueLot, uniqueProductValue } from "../../utils/cuLabel";
import { MSG_FORMAT_MISMATCH } from "../../utils/cuQtyEntryScanEach";
import { LAUNCHERS_LIMIT_SYSCONFIGS, allureMeta, createMasterdataRememberingSysconfigs, restoreSysconfigsAfterEach } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** CU quantity entry */
const MODE_SCAN_ONCE = 'SCAN_ONCE_ENTER_QTY';
const LINE_QTY = 20;
const BEST_BEFORE = defaultBestBefore();

const MSG_NO_FORMAT = 'No label format set on the picking profile';
const MSG_DIFFERENT_PRODUCT = 'The scanned QR Product does not match';

const createMasterdata = async ({ profileFormatName, formats, p2 = {} }) => {
    return await createMasterdataRememberingSysconfigs({
        language: "en_US",
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "product",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
                    cuQtyEntryMode: MODE_SCAN_ONCE,
                    ...(profileFormatName ? { cuLabelFormatName: profileFormatName } : {}),
                }
            },
            customQRCodeFormats: formats,
            bpartners: { "custA": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                // product values of 7 characters, as on the real CU label
                "P1": { price: 1, randomValue: { size: 7, isIncludeDigits: true } },
                "P2": { price: 1, randomValue: { size: 7, isIncludeDigits: true }, ...p2 },
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4' },
            },
            salesOrders: {
                "SO_A": {
                    bpartner: 'custA',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: LINE_QTY, piItemProduct: 'P1_4CU' }]
                },
            },
        }
    });
};

const openLineScanScreen = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: LINE_QTY, productId: masterdata.products.P1.id });
    await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickLineScanScreen.waitForScreen();
    return { pickingJobId };
};

/** Scan once */
const bookWithLabel = async ({ label, qty, expectedQtyTarget, isRescan = false }) => {
    if (isRescan) {
        await PickLineScanScreen.rescanCULabel(label);
    } else {
        await PickLineScanScreen.scanCULabel(label);
    }
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.typeQtyEntered(qty);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget });
};

// noinspection JSUnusedLocalSymbols
test('buyer A: Scan once reads the label with the profile format without PackId part, with and without counter', async ({ page }) => {
    allureMeta('CU label reading');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })] });
    const gtin = uniqueGTIN();
    const productValue = masterdata.products.P1.productCode;
    const lot = uniqueLot();
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openTU();

    await test.step('A, L(1): label with counter is booked', async () => {
        await bookWithLabel({ label: buildCULabel({ gtin, productValue, lot, packId: 1 }), qty: 1, expectedQtyTarget: LINE_QTY - 1 });
    });

    await test.step('A, L0: label without counter is booked too (the format has no PackId part)', async () => {
        await bookWithLabel({ label: buildCULabel({ gtin, productValue, lot }), qty: 1, expectedQtyTarget: LINE_QTY - 2, isRescan: true });
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "1 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "1 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu2', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' },
                        ]
                    },
                }
            }
        },
        hus: {
            vhu1: { attributes: { 'Lot-Nummer': lot, 'PackId': null } },
            vhu2: { attributes: { 'Lot-Nummer': lot, 'PackId': null } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('no effective format on the line, another active format that reads the label does not count', async ({ page }) => {
    allureMeta('CU label reading');

    // profile and customer record have no format; a different active format would read L(1)
    const otherFormatName = `cuqe-lbl-other-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: null, formats: [buildCULabelFormat({ name: otherFormatName })] });
    const label = buildCULabel({ gtin: uniqueGTIN(), productValue: masterdata.products.P1.productCode, lot: uniqueLot(), packId: 1 });
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openTU();
    await PickLineScanScreen.scanCULabelRefused(label, MSG_NO_FORMAT);

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('label with a "]d2" prefix is refused by the linked format', async ({ page }) => {
    allureMeta('CU label reading');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })] });
    const label = ']d2' + buildCULabel({ gtin: uniqueGTIN(), productValue: masterdata.products.P1.productCode, lot: uniqueLot(), packId: 1 });
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openTU();
    await PickLineScanScreen.scanCULabelRefused(label, MSG_FORMAT_MISMATCH);

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: { qtyPicked: [] } } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('label of the line product opens the quantity block, books lot and best-before; other product is refused', async ({ page }) => {
    allureMeta('CU label product match');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })] });
    const gtin = uniqueGTIN();
    const lot = uniqueLot();
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openTU();

    await test.step('Label of the line product: quantity block opens, book 5', async () => {
        await bookWithLabel({ label: buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, bestBefore: BEST_BEFORE, packId: 1 }), qty: 5, expectedQtyTarget: LINE_QTY - 5 });
    });

    await test.step('Label of another product: refused with the app message, the block stays, nothing more is booked', async () => {
        await PickLineScanScreen.rescanCULabelRefused(
            buildCULabel({ gtin, productValue: masterdata.products.P2.productCode, lot, bestBefore: BEST_BEFORE, packId: 2 }),
            MSG_DIFFERENT_PRODUCT
        );
        await GetQuantityDialog.expectQtyTarget(LINE_QTY - 5);
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }]
                    },
                }
            }
        },
        hus: {
            vhu1: { storages: { P1: '5 PCE' }, attributes: { 'Lot-Nummer': lot, 'HU_BestBeforeDate': BEST_BEFORE } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('product match uses the label product part against M_Product.Value, the GTIN is not checked', async ({ page }) => {
    allureMeta('CU label product match');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const gtin = uniqueGTIN();
    // P2 carries the GTIN of the label
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })], p2: { gtin } });
    const lot = uniqueLot();
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openTU();

    await test.step('(1) product part = P1 value, GTIN of P2: accepted', async () => {
        await bookWithLabel({ label: buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, packId: 1 }), qty: 1, expectedQtyTarget: LINE_QTY - 1 });
    });

    await test.step('(2) product part = P2 value: refused, the block stays, nothing more is booked', async () => {
        await PickLineScanScreen.rescanCULabelRefused(
            buildCULabel({ gtin, productValue: masterdata.products.P2.productCode, lot, packId: 2 }),
            MSG_DIFFERENT_PRODUCT
        );
        await GetQuantityDialog.expectQtyTarget(LINE_QTY - 1);
    });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "1 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }]
                    },
                }
            }
        },
    });
});
