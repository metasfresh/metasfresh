import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { GRAIScreen } from '../../utils/screens/huManager/GRAIScreen';

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
