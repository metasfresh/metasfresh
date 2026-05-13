import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { GRAIScreen } from '../../utils/screens/huManager/GRAIScreen';

// GRAI_PREFIX layout per GS1 AI 8003: AI(4) + pad(0) + companyPrefix(7613264) + assetType(003095, 6 digits incl. check digit) + first serial digit(1) = 19 chars.
// The trailing "1" is the first character of the variable-length serial that follows.
const GRAI_PREFIX = '8003076132640030951';

const makeGraiBarcodes = (count) => {
    return Array.from({ length: count }, (_, i) => {
        const serial = String(i + 1).padStart(11, '0');
        return `${GRAI_PREFIX}${serial}`;
    });
};

/** Expected chip display text for a generated GRAI serial number (1-based).
 *  Chips show the canonical GRAI: companyPrefix.assetType.serial — the serial
 *  starts with the "1" that comes from GRAI_PREFIX (position 14 of the post-AI
 *  data, per GS1 AI 8003: 1 pad + 13-digit asset reference + serial). */
const graiChipText = (n) => `7613264.003095.1${String(n).padStart(11, '0')}`;

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
    await GRAIScreen.expectGraiChipWithText({ text: '7613264.003095.100691412000' });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: ['7613264.003095.100691412000'] });

    // Send to backend — chips should remain unchanged
    await GRAIScreen.tapSendButton();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: ['7613264.003095.100691412000'] });

    // Verify backend persistence: reload from backend, confirm GRAI survived the round-trip
    await GRAIScreen.reloadFromBackend();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.expectGraiChipTexts({ expectedTexts: ['7613264.003095.100691412000'] });
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
        '7613264.003095.100691412001',
        '7613264.003095.100691412002',
        '7613264.003095.100691412003',
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
