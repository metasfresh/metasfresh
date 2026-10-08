import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { GRAIScreen } from '../../utils/screens/huManager/GRAIScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

// GRAI_PREFIX layout per GS1 AI 8003: AI(4) + pad(0) + companyPrefix(7613264) + assetType(00309) + check digit(5) + first serial digit(1) = 19 chars.
// The "5" at position 17 is the GS1 check digit (dropped from the EPCIS canonical);
// the trailing "1" is the first character of the variable-length serial that follows.
const GRAI_PREFIX = '8003076132640030951';

const makeGraiBarcodes = (count) => {
    return Array.from({ length: count }, (_, i) => {
        const serial = String(i + 1).padStart(11, '0');
        return `${GRAI_PREFIX}${serial}`;
    });
};

/** Expected chip display text for a generated GRAI serial number (1-based).
 *  Chips show the GS1 EPCIS "Pure Identity" URI canonical:
 *  {@code companyPrefix(7).assetType(5).serial} — the check digit at GTIN-13
 *  position 13 is omitted from the canonical, and the serial starts with the
 *  "1" that comes from GRAI_PREFIX (position 14 of the post-AI data). */
const graiChipText = (n) => `7613264.00309.1${String(n).padStart(11, '0')}`;

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            products: {
                "P1": {},
            },
            warehouses: {
                "wh1": {},
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI' }
            },
        }
    });
}

const createMasterdataForBatchScan = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            products: {
                "P1": {},
            },
            warehouses: {
                "wh1": {},
            },
            packingInstructions: {
                "PI_BATCH": { lu: "LU", qtyTUsPerLU: 40, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI_BATCH' }
            },
        }
    });
}

// noinspection DuplicatedCode
const createMasterdataAndScanLU = async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();

    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '80 PCE' });

    return masterdata;
}

// noinspection JSUnusedLocalSymbols
test('Scan GRAI button visible for LU', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Scan Button Visibility');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.expectButtonVisible({ testId: 'scan-grai-button' });
});

// noinspection JSUnusedLocalSymbols
test('Open GRAI screen and verify empty state', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Screen Empty State');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });
});

// noinspection JSUnusedLocalSymbols
test('Scan GRAI barcode and verify chip appears', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Scan Barcode');
    allure.severity('critical');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan a GS1 AI 8003 GRAI barcode (as emitted by a keyboard barcode reader) — MIGROS A crate
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100691412000' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectGraiChipWithText({ text: '7613264.00309.100691412000' });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: ['7613264.00309.100691412000'] });

    // Send to backend — chips should remain unchanged
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: ['7613264.00309.100691412000'] });

    // Verify backend persistence: reload from backend, confirm GRAI survived the round-trip
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: ['7613264.00309.100691412000'] });
});

// noinspection JSUnusedLocalSymbols
test('Duplicate GRAI scan is ignored', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Duplicate Handling');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan same GS1 AI 8003 GRAI barcode twice — MIGROS A crate
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100691412000' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });

    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100691412000' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 }); // still 1
});

// noinspection JSUnusedLocalSymbols
test('Remove GRAI chip', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Remove');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Add a GRAI — MIGROS B crate
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003071100691412000' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });

    // Remove it
    await GRAIScreen.removeGraiChip({ index: 0 });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });
});

// noinspection JSUnusedLocalSymbols
test('Non-GRAI barcode is silently ignored', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Invalid Barcode');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan a random EAN-13 barcode that is NOT a GRAI — should be ignored
    await GRAIScreen.scanGraiBarcode({ barcodeString: '4006381333931' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });

    // Scan a valid GRAI afterwards to confirm the scanner still works
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100691412000' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
});

// noinspection JSUnusedLocalSymbols
test('RFID batch scan with overlapping reads completes all 40 GRAIs', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI RFID Batch Scan');
    allure.severity('critical');

    const masterdata = await createMasterdataForBatchScan();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();

    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '160 PCE' });

    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    const allGrais = makeGraiBarcodes(40);

    // Scan 1: GRAIs 1–23 (indices 0–22)
    await GRAIScreen.scanGraiBatch({ graiCodes: allGrais.slice(0, 23) });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 23 });

    // Scan 2: GRAIs 9–35 (indices 8–34) — overlaps with scan 1 on GRAIs 9–23
    await GRAIScreen.scanGraiBatch({ graiCodes: allGrais.slice(8, 35) });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 35 });

    // Scan 3: GRAIs 19–40 (indices 18–39) — overlaps with previous scans on GRAIs 19–35
    await GRAIScreen.scanGraiBatch({ graiCodes: allGrais.slice(18, 40) });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 40 });

    // Verify exact chip texts for all 40 GRAIs
    const allExpectedTexts = Array.from({ length: 40 }, (_, i) => graiChipText(i + 1));
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: allExpectedTexts });

    // Send to backend — chips should remain unchanged
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 40 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: allExpectedTexts });

    // Verify backend persistence: reload from backend, confirm all 40 GRAIs survived the round-trip
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 40 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: allExpectedTexts });
});

// noinspection JSUnusedLocalSymbols
test('Clear All GRAIs removes all chips after confirmation', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Clear All');
    allure.severity('critical');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Clear All button should not be visible when no GRAIs scanned
    await GRAIScreen.expectClearAllButtonDisabled();

    // Scan 3 GRAIs
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000001' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000002' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 2 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000003' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 3 });

    // Clear All button should now be visible
    await GRAIScreen.expectClearAllButtonEnabled();

    // Clear all and confirm
    await GRAIScreen.clearAllGrais();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });

    // Clear All button should be hidden again
    await GRAIScreen.expectClearAllButtonDisabled();

    // Send the cleared state to backend — should still be empty
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });

    // Verify backend persistence: reload from backend, confirm empty state survived the round-trip
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });
});

// noinspection JSUnusedLocalSymbols
test('Clear All GRAIs cancelled keeps chips', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Clear All Cancel');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan 2 GRAIs (assert between scans so the keyboard hook's interval flush processes each barcode)
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000001' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000002' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 2 });

    // Click Clear All but cancel — chips should remain
    await GRAIScreen.clearAllGraisAndCancel();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 2 });
});

// noinspection JSUnusedLocalSymbols
test('Over-scan shows extra chips in orange', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Over-Scan');
    allure.severity('critical');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // LU has 20 TUs — scan 25 GRAIs via batch (5 extra)
    const grais = makeGraiBarcodes(25);
    await GRAIScreen.scanGraiBatch({ graiCodes: grais });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 5 });
    await GRAIScreen.expectCountExtraVisible({ expectedExtra: 5 });

    // Send button should be disabled while extras exist
    await GRAIScreen.expectSendButtonDisabled();

    // Remove all 5 extras
    for (let i = 0; i < 5; i++) {
        await GRAIScreen.removeExtraGraiChip({ index: 0 });
    }
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectCountExtraNotVisible();

    // Now Send should be enabled
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });

    // Reload from backend — all 20 assigned survive
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });
});

// noinspection JSUnusedLocalSymbols
test('Remove extra chip does not affect backend', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Remove Extra');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan 22 GRAIs (2 extra)
    const grais = makeGraiBarcodes(22);
    await GRAIScreen.scanGraiBatch({ graiCodes: grais });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 2 });

    // Send is disabled while extras exist
    await GRAIScreen.expectSendButtonDisabled();

    // Remove one extra — still 1 extra left, Send still disabled
    await GRAIScreen.removeExtraGraiChip({ index: 0 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectSendButtonDisabled();

    // Remove last extra — Send becomes enabled
    await GRAIScreen.removeExtraGraiChip({ index: 0 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });

    // Send and verify persistence
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });

    // Reload — 20 survive from backend
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });
});

// noinspection JSUnusedLocalSymbols
test('Remove assigned chip promotes extra', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Extra Promotion');
    allure.severity('critical');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan 22 GRAIs (2 extra)
    const grais = makeGraiBarcodes(22);
    await GRAIScreen.scanGraiBatch({ graiCodes: grais });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 2 });

    // Send is disabled while extras exist
    await GRAIScreen.expectSendButtonDisabled();

    // Remove an assigned chip — one extra promotes, still 1 extra left
    await GRAIScreen.removeGraiChip({ index: 0 });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectSendButtonDisabled();

    // Remove last extra — Send becomes enabled
    await GRAIScreen.removeExtraGraiChip({ index: 0 });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });

    // Send and verify — 20 assigned (including the promoted one)
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });

    // Reload — 20 survive from backend
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });
});

// noinspection JSUnusedLocalSymbols
test('Clear All removes both assigned and extra chips', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Clear All With Extras');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan 25 GRAIs (5 extra)
    const grais = makeGraiBarcodes(25);
    await GRAIScreen.scanGraiBatch({ graiCodes: grais });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 20 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 5 });

    // Clear All should remove everything
    await GRAIScreen.clearAllGrais();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });
});

// noinspection JSUnusedLocalSymbols
test('Send button starts disabled, enables after scan, disables after send', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Send Button State');
    allure.severity('normal');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Initially disabled (no unsaved changes)
    await GRAIScreen.expectSendButtonDisabled();

    // After scan, should be enabled
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100691412000' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectSendButtonEnabled();

    // After send, should return to disabled with chip still present
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectSendButtonDisabled();
});

// noinspection JSUnusedLocalSymbols
test('Undo discards unsaved changes and reloads from backend', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Undo');
    allure.severity('critical');

    await createMasterdataAndScanLU({ page });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Scan 2 GRAIs and send them to backend
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000001' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000002' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 2 });
    await GRAIScreen.tapSendButton();

    // Now scan a 3rd GRAI (unsaved)
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000003' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 3 });
    await GRAIScreen.expectSendButtonEnabled();

    // Undo — should discard the 3rd GRAI and reload the 2 saved ones
    await GRAIScreen.tapUndoButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 2 });
    await GRAIScreen.expectSendButtonDisabled();
});

// noinspection JSUnusedLocalSymbols
/**
 * Reproducer for me03#29827 — distribution of GRAIs across the TUs of a single-product LU.
 *
 * The LU is created with PI tuCount=3 (one product, three aggregated TUs). The customer
 * reported that, after https://github.com/metasfresh/metasfresh/pull/23927, three GRAIs
 * scanned onto such an LU could still end up "all on the first TU". The post-PR-23927
 * per-block loop in HUGraiSnapshot.computeDelta should fill the single aggregate block
 * with all three GRAIs as a comma-separated value on its VHU, and the UI round-trip
 * must preserve all three.
 *
 * If a regression ever collapses the storage back to "first slot only", the
 * reloadFromBackend() chip count after Send will drop below three.
 */
test('Three GRAIs distribute across a 3-TU single-product LU and survive the round-trip', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5120');
    allure.story('HU Manager - GRAI Distribution on 3-TU Single-Product LU');
    allure.severity('critical');

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            products: { "P1": {} },
            warehouses: { "wh1": {} },
            packingInstructions: {
                "PI_3TU": { lu: "LU", qtyTUsPerLU: 3, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', packingInstructions: 'PI_3TU' },
            },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();

    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });
    await HUManagerScreen.expectValue({ name: 'qty-value', expectedValue: '12 PCE' });

    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Three distinct GRAI barcodes — the canonical forms the parser produces after the
    // me03#29827 fix (serial starts with the "1" that follows the asset reference).
    const graiBarcodes = [
        '800307613264003095100691412001',
        '800307613264003095100691412002',
        '800307613264003095100691412003',
    ];
    const expectedCanonicals = [
        '7613264.00309.100691412001',
        '7613264.00309.100691412002',
        '7613264.00309.100691412003',
    ];

    // Scan one at a time; the assertion between scans gives the keyboard-hook interval
    // flush enough time to process each barcode (see e2e/mobile-webui/CLAUDE.md).
    await GRAIScreen.scanGraiBarcode({ barcodeString: graiBarcodes[0] });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: graiBarcodes[1] });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 2 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: graiBarcodes[2] });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 3 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: expectedCanonicals });

    // No extras for a 3-TU LU with 3 scans
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });

    // Send to backend — all three must reach the VHU under the single HA item
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 3 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: expectedCanonicals });

    // Round-trip via the backend: any regression that drops GRAIs into "first slot only"
    // would surface here as fewer than three chips.
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 3 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: expectedCanonicals });
});

// noinspection JSUnusedLocalSymbols
/**
 * Reproducer for me03#29827 — distribution of GRAIs across **multiple** aggregate
 * blocks on a multi-product LU (the real shape from me03#29753).
 *
 * The LU is built via a 3-product picking job in shape `3 / 5 / 1` TUs
 * (lines 10 / 20 / 30 of the original Migros LAF1021 order). After picking, the
 * LU has three HA items — one per product — so `HUGraiSnapshot.aggregateBlocks`
 * holds three entries. Scanning nine GRAIs through the Scan-GRAI screen must
 * fill block-0 with three GRAIs, block-1 with five GRAIs and block-2 with one,
 * NOT dump everything onto the first block (the pre-fix behaviour).
 *
 * The Send + reload round-trip is what catches an inter-block regression — if
 * the loop ever collapses back to "first block gets everything" the second
 * Send call would either lose GRAIs or write them to the wrong VHU.
 */
test('Nine GRAIs distribute across three aggregate blocks on a multi-product LU', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5230');
    allure.story('HU Manager - GRAI Distribution across Multiple Aggregate Blocks (3/5/1)');
    allure.severity('critical');

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
                "P2": { prices: [{ price: 1 }] },
                "P3": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI1": { lu: "LU", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 10 },
                "PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 10 },
                "PI3": { lu: "LU", qtyTUsPerLU: 20, tu: "TU3", product: "P3", qtyCUsPerTU: 10 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 200 },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 200 },
                "HU3": { product: 'P3', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 30, piItemProduct: 'TU1' }, // 30 CU / 10 per TU = 3 TU
                        { product: 'P2', qty: 50, piItemProduct: 'TU2' }, // 50 CU / 10 per TU = 5 TU
                        { product: 'P3', qty: 10, piItemProduct: 'TU3' }, // 10 CU / 10 per TU = 1 TU
                    ]
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });

    await test.step("Pick all three products onto the same LU", async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '5' });
        await PickingJobScreen.expectLineButton({ index: 2, color: 'green', qtyToPick: '5 TU', qtyPicked: '5 TU', qtyPickedCatchWeight: '' });

        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU3.qrCode, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 3, color: 'green', qtyToPick: '1 TU', qtyPicked: '1 TU', qtyPickedCatchWeight: '' });
    });

    // Confirm the picked LU has 3 products before we touch GRAIs.
    // The picking expectation's `lu: 'lu1'` field registers the picked LU's HuId
    // in the masterdata context under the identifier "lu1"; without that the
    // subsequent `hus: { lu1: ... }` block and getHUQRCodeByIdentifier call
    // cannot resolve it (context.getOptionalId returns null otherwise).
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: '30 PCE', qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] },
                    P2: { qtyPicked: [{ qtyPicked: '50 PCE', qtyTUs: 5, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: false, shipmentLineId: '-' }] },
                    P3: { qtyPicked: [{ qtyPicked: '10 PCE', qtyTUs: 1, qtyLUs: 1, vhu: 'vhu3', tu: 'tu3', lu: 'lu1', processed: false, shipmentLineId: '-' }] },
                }
            }
        },
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '30 PCE', P2: '50 PCE', P3: '10 PCE' } },
        }
    });

    // Resolve the picked LU's global QR code via the test backend so the HU
    // Manager can scan it. Picked LUs are not exposed in masterdata.handlingUnits
    // because they're created indirectly by the picking flow.
    const luQRCode = await Backend.getHUQRCodeByIdentifier({ identifier: 'lu1' });

    await PickingJobScreen.goBack();
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.goBack();
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();

    await HUManagerScreen.scanHUQRCode({ huQRCode: luQRCode });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();

    // Nine distinct GRAIs (3 + 5 + 1). The trailing "1" of the prefix becomes the
    // first serial digit per GS1 AI 8003 (post-me03#29827 parser fix).
    const graiBarcodes = makeGraiBarcodes(9);
    const expectedCanonicals = Array.from({ length: 9 }, (_, i) => graiChipText(i + 1));

    await GRAIScreen.scanGraiBatch({ graiCodes: graiBarcodes });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 9 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: expectedCanonicals });
    await GRAIScreen.expectExtraGraiChipCount({ expectedCount: 0 });

    // Send → the per-block loop must fill block-0 (P1, tuCount=3), block-1 (P2, tuCount=5)
    // and block-2 (P3, tuCount=1) — NOT collapse onto block-0.
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 9 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: expectedCanonicals });

    // Round-trip: any regression that drops GRAIs into "first block only" would
    // surface here as fewer than nine chips on reload (the second/third VHUs
    // would have been overwritten with empty / different values).
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 9 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: expectedCanonicals });

    // Backend-state assertion — the UI checks above can be satisfied by any
    // storage shape that produces nine flat chips on reload. To prove the GRAIs
    // *actually* landed on three distinct VHUs (one per HA item), assert the
    // exact M_HU_Attribute(GRAI) comma-separated value on each VHU. `vhu1` /
    // `vhu2` / `vhu3` were registered in the masterdata context by the picking
    // expectations earlier in this test.
    await Backend.expect({
        title: 'Three VHUs each carry the right share of GRAIs',
        hus: {
            vhu1: {
                storages: { P1: '30 PCE' },
                attributes: { GRAI: expectedCanonicals.slice(0, 3).join(',') },
            },
            vhu2: {
                storages: { P2: '50 PCE' },
                attributes: { GRAI: expectedCanonicals.slice(3, 8).join(',') },
            },
            vhu3: {
                storages: { P3: '10 PCE' },
                attributes: { GRAI: expectedCanonicals.slice(8, 9).join(',') },
            },
        },
    });
});
