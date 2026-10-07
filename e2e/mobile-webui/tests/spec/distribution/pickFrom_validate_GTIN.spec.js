import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { generateEAN13 } from '../../utils/ean13';
import { expectErrorToast } from '../../utils/common';
import { expect } from '@playwright/test';

const createMasterdata = async ({ qtyToMove, externalBarcode }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "workplace2" } },
            mobileConfig: {
                distribution: {
                    requireScanningProductCode: true,
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: {
                "P1": { gtin: generateEAN13().ean13 },
            },
            warehouses: {
                "wh1": {},
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                workplace2: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' },
            },
            handlingUnits: {
                "HU1": { product: "P1", warehouse: "wh1", qty: 100000, externalBarcode }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: qtyToMove }],
                }
            },
        }
    });
}

const createMasterdataAndStartJob = async ({ qtyToMove, externalBarcode }) => {
    const masterdata = await createMasterdata({ qtyToMove, externalBarcode });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    return masterdata;
};

// noinspection JSUnusedLocalSymbols
test.describe('Scan directly in job screen, expect scanning the product code too', () => {
    // noinspection JSUnusedLocalSymbols
    test('Scan the HU by QRCode', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0370: Intralogistic (HUs)');
        allure.tag('F5114.3');
        allure.story('Validate GTIN during pick from');
        allure.severity('normal');

        const masterdata = await createMasterdataAndStartJob({ qtyToMove: 100 });

        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            productScannedCode: masterdata.products.P1.gtin,
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
    });

    // noinspection JSUnusedLocalSymbols
    test('Scan the HU by Value/M_HU_ID', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0370: Intralogistic (HUs)');
        allure.tag('F5114.3');
        allure.story('Validate GTIN during pick from');
        allure.severity('normal');

        const masterdata = await createMasterdataAndStartJob({ qtyToMove: 100 });

        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.huId,
            productScannedCode: masterdata.products.P1.gtin,
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
    });

    // noinspection JSUnusedLocalSymbols
    test('Scan the HU by External Attribute', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0370: Intralogistic (HUs)');
        allure.tag('F5114.3');
        allure.story('Validate GTIN during pick from');
        allure.severity('normal');

        const externalBarcode = "EXT" + Date.now();
        const masterdata = await createMasterdataAndStartJob({ qtyToMove: 100, externalBarcode });

        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.scanHUToMove({
            huQRCode: externalBarcode,
            productScannedCode: masterdata.products.P1.gtin,
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
    });

});

// noinspection JSUnusedLocalSymbols
test('Do not ask for picked qty when it is one', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.story('Validate GTIN during pick from');
    allure.severity('normal');

    const masterdata = await createMasterdataAndStartJob({ qtyToMove: 1 });

    await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '0 Stk', color: 'red' });
    await DistributionJobScreen.scanHUToMove({
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        productScannedCode: masterdata.products.P1.gtin,
        expectQuantityDialog: false,
    });
    await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '1 Stk', qtyPicked: '1 Stk', color: 'yellow' });
});

// noinspection JSUnusedLocalSymbols
test('Scan an unknown product code yields a "no product found" error and the line stays unpicked', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.story('Validate GTIN during pick from');
    allure.severity('normal');

    const masterdata = await createMasterdataAndStartJob({ qtyToMove: 100 });

    // A second EAN13 that resolves to NO product (guard against the 1-in-a-trillion
    // collision with P1's gtin by regenerating if equal).
    let bogusProductCode = generateEAN13().ean13;
    while (bogusProductCode === masterdata.products.P1.gtin) {
        bogusProductCode = generateEAN13().ean13;
    }

    await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });

    // Scan the (valid) HU first → lands on the pick-from screen asking for the product code.
    await DistributionJobScreen.scanHU({ huQRCode: masterdata.handlingUnits.HU1.qrCode });

    // Scan a product code that matches no product → user-friendly error toast; covers the fix
    // that replaced the raw exception string with the translatable AD_Message
    // MobileUI_Distribution_NoProductForScannedCode (run language is en_US per the factory).
    await expectErrorToast(
        'Expect "no product found for scanned code" error',
        async () => {
            await DistributionLinePickFromScreen.typeProductCode(bogusProductCode);
        },
        ({ textContent }) => {
            expect(textContent).toContain('No product found for the scanned product code');
        }
    );

    // The error kept us on the pick-from screen; go back to the job screen and assert the
    // line is still unpicked (no quantity dialog appeared, no qty committed).
    await DistributionLinePickFromScreen.goBackToJobScreen();
    await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
});
