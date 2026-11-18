import { test } from '../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from '../utils/screens/ApplicationsListScreen';
import { Backend } from '../utils/screens/Backend';
import { LoginScreen } from '../utils/screens/LoginScreen';
import { BarcodeScannerComponent } from '../utils/components/BarcodeScannerComponent';
import { PickingJobsListScreen } from '../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../utils/screens/picking/PickingJobScreen';
import { expectErrorToast } from '../utils/common';

// Scannable code format with the part type PACK_ID. The label carries
// 01 GTIN(14) 91 product(7) 17 best-before(yyMMdd) 10 lot(9) 92 packId(1..n),
// no separators, as a Zebra TC22 in keystroke mode delivers it (47 characters for a 1-digit pack ID).
// The label below is a look-alike: made-up GTIN, generated product, made-up lot.
const GTIN_PART = '0109506000134352';
const LOT = 'A1/B002C3';
const BEST_BEFORE_YYMMDD = '261107';

// a product number like the ones on the label: 7 characters, unique per run
const newProductCode = () => 'P' + String(Date.now()).slice(-6);

const buildLabel = ({ productCode, packId }) => `${GTIN_PART}91${productCode}17${BEST_BEFORE_YYMMDD}10${LOT}92${packId}`;

// The 49-character form: a character (here `]`) inserted at positions 26 and 46, i.e. in front of the
// `17` and of the `92` (positions 1-based, counted on the final string).
const buildShiftedLabel49 = ({ productCode, packId }) => {
    let s = buildLabel({ productCode, packId });
    s = s.slice(0, 25) + ']' + s.slice(25);
    s = s.slice(0, 45) + ']' + s.slice(45);
    return s;
};

// The format on the 47-character string, with three CONSTANT guards (a different scanner setting shifts
// the string; the guards then give no match instead of wrong values). endPosition 0 = open end.
const FORMAT_NAME = 'Pack ID label (E2E)';
const FORMAT_PARTS = [
    { startPosition: 19, endPosition: 25, type: 'PRODUCT_CODE' },
    { startPosition: 26, endPosition: 27, type: 'CONSTANT', constantValue: '17' },
    { startPosition: 28, endPosition: 33, type: 'BEST_BEFORE_DATE', dateFormat: 'yyMMdd' },
    { startPosition: 34, endPosition: 35, type: 'CONSTANT', constantValue: '10' },
    { startPosition: 36, endPosition: 44, type: 'LOT' },
    { startPosition: 45, endPosition: 46, type: 'CONSTANT', constantValue: '92' },
    { startPosition: 47, endPosition: 0, type: 'PACK_ID' },
];

// Scanner sysconfigs of the production devices: hardware mode (keystroke wedge), inputMode=none, readOnly=Y.
// Same keys as barcode_scanner_modes.spec.js.
const HARDWARE_MODE_SYSCONFIGS = {
    'mobileui.frontend.barcodeScanner.mode.hardware.enabled': 'Y',
    'mobileui.frontend.barcodeScanner.mode.camera.enabled': 'N',
    'mobileui.frontend.barcodeScanner.mode.manual.enabled': 'N',
    'mobileui.frontend.barcodeScanner.defaultMode': 'hardware',
    'mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode': 'none',
    'mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly': 'Y',
};

// The scanner sysconfigs are global: put back what the masterdata call found, also when the test failed
// (same pattern as barcode_scanner_modes.spec.js).
let sysconfigsToRestore = null;

test.afterEach(async () => {
    const sysconfigs = sysconfigsToRestore;
    sysconfigsToRestore = null;
    if (sysconfigs && Object.keys(sysconfigs).length > 0) {
        await Backend.setSysconfigs(sysconfigs);
    }
});

const createMasterdata = async ({ productCode, huLabels }) => {
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            sysconfigs: { ...HARDWARE_MODE_SYSCONFIGS },
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            // 7 characters, like the product numbers on the label (positions 19-25)
            products: { P1: { price: 1, value: productCode } },
            packingInstructions: { LU_CU: { cu: true, lu: 'LU', qtyTUsPerLU: 1 } },
            // the HUs a scan resolves to: the scanned code is the HU's QR code attribute; the qty of the line is the qty of the HU
            handlingUnits: Object.fromEntries(huLabels.map((huLabel, i) => [`HU${i + 1}`, { product: 'P1', warehouse: 'wh', qty: 5, packingInstructions: 'LU_CU', qrCodeAttribute: huLabel }])),
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 5 }],
                },
            },
            customQRCodeFormats: [{ name: FORMAT_NAME, parts: FORMAT_PARTS }],
        },
    });
    if (sysconfigsToRestore == null) {
        sysconfigsToRestore = masterdata.previousSysconfigs ?? {};
    }
    return masterdata;
};

const startProductPickingJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.waitForScreen();
    // scanning the picking slot opens the scan screen of the line; back to the job screen, where a scan picks directly
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
};

// noinspection JSUnusedLocalSymbols
test('Scan a pack ID label (47 characters): the product is read from the label and the HU is picked', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scannable code format: pack ID');
    allure.severity('critical');

    const productCode = newProductCode();
    const label = buildLabel({ productCode, packId: '2' });
    expect(label).toHaveLength(47);
    const masterdata = await createMasterdata({ productCode, huLabels: [label] });

    PickingJobScreen.startCollectingPickEvents();
    await startProductPickingJob(masterdata);
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 Stk', qtyPicked: '0 Stk' });

    await test.step(`Scan the label ${label} (hardware scanner: one keydown per character, then Enter)`, async () => {
        await BarcodeScannerComponent.type(label);
    });

    await test.step('The app posted the pick with the complete 47-character label, and the HU is picked', async () => {
        // The app posts this pick TWICE per scan, ~60 ms apart (pre-existing base behaviour, not caused by the pack ID format):
        // PickLineScanScreen.jsx `if (isTargetsLoading) return <Spinner />` (:157) unmounts ScanHUAndGetQtyComponent right after its first mount,
        // because useAvailablePickingTargets (api/picking.js:14-27) goes loading=false -> true -> false; the remounted component's
        // useEffect on `scannedBarcode` (ScanHUAndGetQtyComponent.jsx:154) runs the same pick again. Both are untouched by this change.
        // The server ends with the HU picked once (qtyPicked 5 of 5, checked below).
        await PickingJobScreen.expectPickPostedWithHUQRCode({ huQRCode: label, expectedCount: 2 });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 Stk', qtyPicked: '5 Stk', waitForColor: 'green' });
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan a shifted 49-character label: refused, no pick is posted', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scannable code format: pack ID');
    allure.severity('critical');

    const productCode = newProductCode();
    const shifted = buildShiftedLabel49({ productCode, packId: '2' });
    expect(shifted).toHaveLength(49);
    // an HU carries exactly this shifted string, so the refusal comes from the scan handling, not from a missing HU
    const masterdata = await createMasterdata({ productCode, huLabels: [shifted] });

    PickingJobScreen.startCollectingPickEvents();
    await startProductPickingJob(masterdata);

    await expectErrorToast(`shifted label ${shifted} is refused`, async () => {
        await BarcodeScannerComponent.type(shifted);
    });

    PickingJobScreen.expectNoPickPosted();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 Stk', qtyPicked: '0 Stk' });
});

// noinspection JSUnusedLocalSymbols
test('Scan a label with a wrong marker at positions 26-27: refused by the guard, no pick is posted', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scannable code format: pack ID');
    allure.severity('critical');

    const productCode = newProductCode();
    // 47 characters, every value readable, only the marker `17` at positions 26-27 is wrong: without the guard this label would be accepted
    const wrongMarker = buildLabel({ productCode, packId: '2' }).replace(`${productCode}17`, `${productCode}XX`);
    expect(wrongMarker).toHaveLength(47);
    const masterdata = await createMasterdata({ productCode, huLabels: [wrongMarker] });

    PickingJobScreen.startCollectingPickEvents();
    await startProductPickingJob(masterdata);

    await expectErrorToast(`label ${wrongMarker} is refused`, async () => {
        await BarcodeScannerComponent.type(wrongMarker);
    });

    PickingJobScreen.expectNoPickPosted();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 Stk', qtyPicked: '0 Stk' });
});
