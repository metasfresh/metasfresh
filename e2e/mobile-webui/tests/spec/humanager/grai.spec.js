import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { GRAIScreen } from '../../utils/screens/huManager/GRAIScreen';

const GRAI_PREFIX = '8003076132640030951';

const makeGraiBarcodes = (count) => {
    return Array.from({ length: count }, (_, i) => {
        const serial = String(i + 1).padStart(11, '0');
        return `${GRAI_PREFIX}${serial}`;
    });
};

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
    await GRAIScreen.expectGraiChipWithText({ text: '...00691412000' });
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
    await GRAIScreen.expectClearAllButtonNotVisible();

    // Scan 3 GRAIs
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000001' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000002' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 2 });
    await GRAIScreen.scanGraiBarcode({ barcodeString: '800307613264003095100000000003' });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 3 });

    // Clear All button should now be visible
    await GRAIScreen.expectClearAllButtonVisible();

    // Clear all and confirm
    await GRAIScreen.clearAllGrais();
    await GRAIScreen.expectGraiChipCount({ expectedCount: 0 });

    // Clear All button should be hidden again
    await GRAIScreen.expectClearAllButtonNotVisible();
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
