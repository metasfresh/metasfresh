import { test } from "../../../playwright.config";
import { expect } from "@playwright/test";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { GetQuantityDialog } from "../../utils/screens/picking/GetQuantityDialog";
import { expectErrorToast, page as appPage } from "../../utils/common";
import { buildCULabel, buildCULabelFormat, uniqueGTIN, uniqueLot, uniqueProductValue } from "../../utils/cuLabel";

/**
 * CU quantity entry: reading of the pack label on the Scan once line (TC3 without pack-ID cases, TC3b, TC4, TC4b).
 * Scan once = one label scan, then the quantity block. Keyboard-wedge input: the raw label typed into the scan field.
 * Buyer A has no record: the line takes the profile's mode (Scan once) and the profile's format (without `PackId` part).
 */
const MODE_SCAN_ONCE = 'SCAN_ONCE_ENTER_QTY';
const LINE_QTY = 20;
const BEST_BEFORE = '2026-11-07';

const MSG_NO_FORMAT = 'No label format set on the picking profile';
const MSG_FORMAT_MISMATCH = 'The label does not match the label format of the picking profile';
const MSG_DIFFERENT_PRODUCT = 'The scanned QR Product does not match';

/**
 * @param {object} p
 * @param {string|null} p.profileFormatName format linked to the profile; null = no format linked
 * @param {object[]} p.formats formats to create (active in the DB)
 * @param {object} [p.p2] extra fields of product P2 (e.g. `gtin`)
 */
const createMasterdata = async ({ profileFormatName, formats, p2 = {} }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
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
                // product values of 7 characters, as the real label (REQUIREMENTS § 5 "Real data shape")
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

/** Starts the picking job of the single line, scans the slot and opens the line's scan screen (S5). */
const openLineScanScreen = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: LINE_QTY });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await reopenLineScanScreen();
    return { pickingJobId };
};

const reopenLineScanScreen = async () => {
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
};

/** Scan once: label scan, quantity block, OK; the app goes back to the job screen. */
const scanAndBook = async ({ label, qty }) => {
    await PickLineScanScreen.scanCULabel(label);
    await GetQuantityDialog.fillAndPressDone({ qtyEntered: qty });
    await PickingJobScreen.waitForScreen();
};

const allureMeta = (story) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story(story);
    allure.severity('normal');
};

// noinspection JSUnusedLocalSymbols
test('TC3 (buyer A): Scan once reads the label with the profile format without PackId part, with and without counter', async ({ page }) => {
    allureMeta('CU label reading');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })] });
    const gtin = uniqueGTIN();
    const productValue = masterdata.products.P1.productCode;
    const lot = uniqueLot();
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openBox();

    await test.step('A, L(1): label with counter is booked, the counter is not stored', async () => {
        await scanAndBook({ label: buildCULabel({ gtin, productValue, lot, packId: 1 }), qty: 1 });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${LINE_QTY} Stk`, qtyPicked: '1 Stk' });
    });

    await test.step('A, L0: label without counter is booked too (the format has no PackId part)', async () => {
        await reopenLineScanScreen();
        await scanAndBook({ label: buildCULabel({ gtin, productValue, lot }), qty: 1 });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${LINE_QTY} Stk`, qtyPicked: '2 Stk' });
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
    });
});

// noinspection JSUnusedLocalSymbols
test('TC3b (i): no effective format on the line, another active format that reads the label does not count', async ({ page }) => {
    allureMeta('CU label reading');

    // profile and customer record have no format; a different active format would read L(1)
    const otherFormatName = `cuqe-lbl-other-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: null, formats: [buildCULabelFormat({ name: otherFormatName })] });
    const label = buildCULabel({ gtin: uniqueGTIN(), productValue: masterdata.products.P1.productCode, lot: uniqueLot(), packId: 1 });
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openBox();
    await expectRefusedNoDialog({ label, expectedText: MSG_NO_FORMAT, title: 'no effective format' });

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: {} } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC3b (ii): label with a "]d2" prefix is refused by the linked format', async ({ page }) => {
    allureMeta('CU label reading');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })] });
    const label = ']d2' + buildCULabel({ gtin: uniqueGTIN(), productValue: masterdata.products.P1.productCode, lot: uniqueLot(), packId: 1 });
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openBox();
    await expectRefusedNoDialog({ label, expectedText: MSG_FORMAT_MISMATCH, title: 'prefixed label' });

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { P1: {} } } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC4: label of the line product opens the quantity block, books lot and best-before; other product is refused', async ({ page }) => {
    allureMeta('CU label product match');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })] });
    const gtin = uniqueGTIN();
    const lot = uniqueLot();
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openBox();

    await test.step('Label of the line product: quantity block opens, book 5', async () => {
        await scanAndBook({ label: buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, bestBefore: BEST_BEFORE, packId: 1 }), qty: 5 });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${LINE_QTY} Stk`, qtyPicked: '5 Stk' });
    });

    await test.step('Label of another product: refused with the app message, nothing booked', async () => {
        await reopenLineScanScreen();
        await expectRefusedNoDialog({
            label: buildCULabel({ gtin, productValue: masterdata.products.P2.productCode, lot, bestBefore: BEST_BEFORE, packId: 2 }),
            expectedText: MSG_DIFFERENT_PRODUCT,
            title: 'other product',
        });
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
test('TC4b: product match uses the label product part against M_Product.Value, the GTIN is not checked', async ({ page }) => {
    allureMeta('CU label product match');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const gtin = uniqueGTIN();
    // P2 carries the GTIN of the label
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })], p2: { gtin } });
    const lot = uniqueLot();
    const { pickingJobId } = await openLineScanScreen(masterdata);

    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
    await PickLineScanScreen.openBox();

    await test.step('(1) product part = P1 value, GTIN of P2: accepted', async () => {
        await scanAndBook({ label: buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, packId: 1 }), qty: 1 });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${LINE_QTY} Stk`, qtyPicked: '1 Stk' });
    });

    await test.step('(2) product part = P2 value: refused, nothing booked', async () => {
        await reopenLineScanScreen();
        await expectRefusedNoDialog({
            label: buildCULabel({ gtin, productValue: masterdata.products.P2.productCode, lot, packId: 2 }),
            expectedText: MSG_DIFFERENT_PRODUCT,
            title: 'product part of P2',
        });
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

/** Scans a label that must be refused: an error toast with the text, the quantity block never opens. */
const expectRefusedNoDialog = async ({ label, expectedText, title }) => {
    await expectErrorToast(
        title,
        async () => {
            await PickLineScanScreen.scanCULabel(label);
            // a refused scan keeps the scan screen; the toast races this wait
            await PickLineScanScreen.waitForScreen();
        },
        ({ textContent }) => expect(textContent).toContain(expectedText)
    );
    await expect(appPage.locator('.get-qty-dialog')).toHaveCount(0);
};
