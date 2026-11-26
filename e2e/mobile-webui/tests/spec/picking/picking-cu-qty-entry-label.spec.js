import { expect } from "@playwright/test";
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
import { MESSAGES } from "../../utils/cuQtyEntryMessages";
import { LAUNCHERS_LIMIT_SYSCONFIGS, MODE_SCAN_ONCE, allureMeta, createMasterdataRememberingSysconfigs, restoreSysconfigsAfterEach } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** CU quantity entry */
const LINE_QTY = 20;
const BEST_BEFORE = defaultBestBefore();


const createMasterdata = async ({ profileFormatName, formats, p2 = {}, language = "en_US" }) => {
    return await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language } },
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
    await PickLineScanScreen.scanCULabelRefused(label, MESSAGES.noFormat.en);

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
            MESSAGES.differentProduct.en
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
            MESSAGES.differentProduct.en
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


/** Box (Playwright boundingBox) and the styles that decide the look of the quantity block's buttons */
const measureButtons = async (page) => {
    const read = async (testId) => {
        const button = page.locator('.get-qty-dialog').getByTestId(testId);
        const box = await button.boundingBox();
        const style = await button.evaluate((el) => {
            const cs = getComputedStyle(el);
            return { fontSize: cs.fontSize, fontFamily: cs.fontFamily, lineHeight: cs.lineHeight, paddingTop: cs.paddingTop, paddingBottom: cs.paddingBottom, borderTopWidth: cs.borderTopWidth };
        });
        return { ...box, ...style };
    };
    return { ok: await read('done-button'), cancel: await read('cancel-button'), nextTU: await read('next-tu-button') };
};

// noinspection JSUnusedLocalSymbols
test('Scan once pop-up shows no scanner visuals, the barcode icon sits in "Weitere Kiste", which is as big as OK and Abbrechen', async ({ page }) => {
    allureMeta('CU label reading');

    const formatName = `cuqe-lbl-${uniqueProductValue()}`;
    const masterdata = await createMasterdata({ profileFormatName: formatName, formats: [buildCULabelFormat({ name: formatName })], language: 'de_DE' });
    const gtin = uniqueGTIN();
    const productValue = masterdata.products.P1.productCode;
    const lot = uniqueLot();
    const shotsDir = process.env.AFTER_SHOTS_DIR;
    const shot = async (name) => { if (shotsDir) await page.screenshot({ path: `${shotsDir}/after-${name}.png` }); };
    await page.setViewportSize({ width: 360, height: 720 });
    await openLineScanScreen(masterdata);
    await PickLineScanScreen.openTU();

    await test.step('first scan screen: its own visible scanner stays, the button brings no second scanner, the icon is in the button', async () => {
        await expect(page.getByTestId('scanHUBarcode-input')).toBeAttached();
        await expect(page.locator('.scan-prompt')).toHaveCount(1);
        await expect(page.getByTestId('rescanCULabel-input')).toHaveCount(0);
        await expect(page.locator('input.input-text-offscreen')).toHaveCount(1);
        await expect(page.getByTestId('next-tu-button').locator('[class*="fa-barcode"]')).toHaveCount(1);
        await shot('first-scan-screen-360x720');
    });

    await PickLineScanScreen.scanCULabel(buildCULabel({ gtin, productValue, lot, packId: 1 }));
    await GetQuantityDialog.waitForDialog();

    await test.step('pop-up: no scanner visuals (icon, "Barcode scannen", "Manuell eingeben"), one hidden scanner', async () => {
        await expect(page.locator('.scan-prompt')).toHaveCount(0);
        await expect(page.locator('.barcode-scanner-footer')).toHaveCount(0);
        await expect(page.getByTestId('barcode-scanner-enter-manually')).toHaveCount(0);
        await expect(page.getByText('Barcode scannen')).toHaveCount(0);
        await expect(page.getByText('Manuell eingeben')).toHaveCount(0);
        await expect(page.getByTestId('scanHUBarcode-input')).toHaveCount(0);
        await expect(page.getByTestId('rescanCULabel-input')).toBeAttached();
        await expect(page.locator('input.input-text-offscreen')).toHaveCount(1);
        // the only barcode icon of the dialog is the one inside "Weitere Kiste"
        await expect(page.locator('.get-qty-dialog [class*="fa-barcode"]')).toHaveCount(1);
        await expect(page.getByTestId('next-tu-button').locator('[class*="fa-barcode"]')).toHaveCount(1);
        await expect(page.getByTestId('next-tu-button')).toContainText('Weitere Kiste');
    });

    await test.step('pop-up: a pack label of a new lot, scanned on the hidden scanner, switches the lot', async () => {
        const newLot = uniqueLot();
        await PickLineScanScreen.rescanCULabel(buildCULabel({ gtin, productValue, lot: newLot, packId: 2 }));
        await expect(page.getByTestId('lotNo')).toHaveValue(newLot);
    });

    const viewports = [{ width: 360, height: 720 }, { width: 393, height: 727 }];
    const withIcon = {};
    for (const viewport of viewports) {
        await test.step(`pop-up at ${viewport.width}x${viewport.height}: "Weitere Kiste" with icon is as high as OK and Abbrechen`, async () => {
            await page.setViewportSize(viewport);
            await expect(page.getByTestId('next-tu-button')).toBeVisible();
            const { ok, cancel, nextTU } = await measureButtons(page);
            withIcon[viewport.width] = { ok, cancel, nextTU };
            console.log(`BUTTONS ${viewport.width}x${viewport.height} | ${JSON.stringify({ ok, cancel, nextTU })}`);
            expect.soft(nextTU.height, 'height of Weitere Kiste vs OK').toBe(ok.height);
            expect.soft(nextTU.height, 'height of Weitere Kiste vs Abbrechen').toBe(cancel.height);
            for (const key of ['fontSize', 'fontFamily', 'lineHeight', 'paddingTop', 'paddingBottom', 'borderTopWidth']) {
                expect.soft(nextTU[key], `${key} of Weitere Kiste vs OK`).toBe(ok[key]);
            }
            expect.soft(nextTU.x + nextTU.width, 'Weitere Kiste inside the screen').toBeLessThanOrEqual(viewport.width);
            await expect(page.locator('.Toastify__toast')).toHaveCount(0, { timeout: 15000 }); // clean picture: no toast over the buttons
            await page.getByTestId('next-tu-button').scrollIntoViewIfNeeded();
            await shot(`pop-up-${viewport.width}x${viewport.height}`);
        });
    }

    // At 393 px the three buttons just fit one row without the icon (57 + 111 + 129 px + 3 x 8 px margins = 321 of 329 px) and the
    // icon (16 px + gap) cannot fit: "Weitere Kiste" then takes its own row, the layout the Zebra TC22 (360 px) has anyway.
    await test.step('Zebra TC22 360x720: the icon moves no button (same boxes without it; icon removed from the DOM for this check only)', async () => {
        await page.getByTestId('next-tu-button').locator('.dialog-button-icon').evaluate((el) => el.remove());
        for (const viewport of viewports.slice(0, 1)) {
            await page.setViewportSize(viewport);
            const without = await measureButtons(page);
            for (const key of ['ok', 'cancel', 'nextTU']) {
                for (const dim of ['x', 'y', 'height']) {
                    expect.soft(withIcon[viewport.width][key][dim], `${viewport.width}: ${key}.${dim} with the icon vs without`).toBe(without[key][dim]);
                }
            }
        }
    });
});
