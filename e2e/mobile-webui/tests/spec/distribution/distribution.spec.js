import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { DistributionStepScreen } from '../../utils/screens/distribution/DistributionStepScreen';
import { expectErrorToast } from '../../utils/common';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';

const createMasterdata = async ({ HU1_warehouse = 'wh1', HU1_product = 'P1', qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { distribution: {} },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {}, "P2": {} },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "wh3": {},
                "whInTransit": { inTransit: true },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: HU1_product, warehouse: HU1_warehouse, qty: qtyToMove }
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

// noinspection JSUnusedLocalSymbols
test('Simple distribution test', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Simple distribution workflow');
    allure.severity('critical');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Try picking an HU from a different locator than pick from locator', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Distribution error handling');
    allure.severity('normal');

    const masterdata = await createMasterdata({ HU1_warehouse: 'wh3', qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        expectedQtyToMove: '100',
        expectedError: `The HU's locator does not match the order's locator.`
    });
});

// noinspection JSUnusedLocalSymbols
test('Try picking an HU containing a different product than expected', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Distribution error handling');
    allure.severity('normal');

    const masterdata = await createMasterdata({ HU1_product: 'P2', qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await expectErrorToast(
        'Expect product not matching error',
        async () => {
            await DistributionLineScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU1.qrCode,
                expectedQtyToMove: '100',
                expectedError: `The HU's locator does not match the order's locator.`
            });
        },
        ({ textContent }) => {
            expect(textContent).toContain('The scanned QR Product does not match');
        }
    )
});

// noinspection JSUnusedLocalSymbols
test('Distribution using 2 steps to pick the needed qty.', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Multi-step distribution');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 80 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '40', expectedQtyToMove: '80' });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '40', expectedQtyToMove: '40' });
    await DistributionLineScreen.clickStepButton({ index: 2 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Pick & Unpick in distribution step screen', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Distribution pick and unpick');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.unpick();
    await DistributionLineScreen.expectNoStepButton();
});

// noinspection JSUnusedLocalSymbols
test('Filter distribution orders by plantId', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5112.1: One QR for many HUs');
        allure.tag('F5112.1');  // Standalone tag for Tags section;
    allure.story('Filter distribution by plant');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.plantFacetId, expectHitCount: 1 });
});

//
// Helper for multi-line distribution tests (2 products, 2 HUs, 2 DD lines)
//
const createMultiLineMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { distribution: {} },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {}, "P2": {} },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            packingInstructions: {
                "PI1": { tu: "TU1", product: "P1", qtyCUsPerTU: 4, lu: "LU", qtyTUsPerLU: 20 },
                "PI2": { tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 100 },
                "HU2": { product: 'P2', warehouse: 'wh1', qty: 100 },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: 100 },
                        { product: "P2", qtyEntered: 100 },
                    ],
                }
            },
        }
    });
}

//
// Unpick and repick in distribution:
// Pick HU for line 1 → unpick via step screen → repick same HU → drop to destination →
// complete line 2 normally. Verifies state after undo.
//
// noinspection JSUnusedLocalSymbols
test('Unpick and repick in distribution', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution unpick and repick to completion');
    allure.severity('normal');

    const masterdata = await createMultiLineMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step("Pick line 1 HU and move to in-transit", async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.clickStepButton({ index: 1 });
    });

    await test.step("Unpick the step — HU returns to source", async () => {
        await DistributionStepScreen.unpick();
        await DistributionLineScreen.expectNoStepButton();
    });

    await test.step("Repick same HU, drop to destination, go back", async () => {
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.clickStepButton({ index: 1 });
        await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await DistributionStepScreen.goBack();
        await DistributionLineScreen.goBack();
    });

    await test.step("Verify line 1 green, line 2 red", async () => {
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
    });

    await test.step("Pick line 2 and drop to destination", async () => {
        await DistributionJobScreen.clickLineButton({ index: 2 });
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU2.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.clickStepButton({ index: 1 });
        await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await DistributionStepScreen.goBack();
        await DistributionLineScreen.goBack();
    });

    await DistributionJobScreen.complete();

    await Backend.expect({
        title: 'Both HUs moved to wh2',
        hus: {
            HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
            HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
        }
    });
});

//
// Resume partially completed distribution:
// Complete line 1 (pick + drop) → go back to jobs list → resume same job →
// verify line 1 still green → complete line 2 → complete job.
//
// noinspection JSUnusedLocalSymbols
test('Resume partially completed distribution', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution go back and resume');
    allure.severity('normal');

    const masterdata = await createMultiLineMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step("Pick line 1, drop to wh2", async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.clickStepButton({ index: 1 });
        await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await DistributionStepScreen.goBack();
        await DistributionLineScreen.goBack();
        await DistributionJobScreen.expectLineButton({ index: 1, color: 'green' });
    });

    await test.step("Go back to jobs list (line 2 unfinished)", async () => {
        await DistributionJobScreen.goBack();
        await DistributionJobsListScreen.waitForScreen();
    });

    await test.step("Resume the same job", async () => {
        await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
        await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    });

    await test.step("Verify line 1 still green, line 2 still red", async () => {
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
    });

    await test.step("Pick line 2 and drop to wh2", async () => {
        await DistributionJobScreen.clickLineButton({ index: 2 });
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU2.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.clickStepButton({ index: 1 });
        await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await DistributionStepScreen.goBack();
        await DistributionLineScreen.goBack();
    });

    await DistributionJobScreen.complete();

    await Backend.expect({
        title: 'Both HUs moved to wh2 after resume',
        hus: {
            HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
            HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
        }
    });
});

//
// Partial drop-all, then complete remaining:
// Pick only line 1 → drop-all (only line 1 moves) → verify line 1 green, line 2 red →
// pick line 2 manually → drop → complete.
//
// noinspection JSUnusedLocalSymbols
test('Partial drop-all, then complete remaining', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution partial drop-all then complete remaining');
    allure.severity('normal');

    const masterdata = await createMultiLineMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step("Pick line 1 only (move to in-transit)", async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.goBack();
    });

    await test.step("Drop-all (only line 1 is in-transit) to wh2", async () => {
        await DistributionJobScreen.expectDropAllButton({ enabled: true });
        await DistributionJobScreen.dropAllTo({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
    });

    await test.step("Verify line 1 green, line 2 still red", async () => {
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectDropAllButton({ enabled: false });
    });

    await test.step("Pick line 2, drop to wh2, and complete", async () => {
        await DistributionJobScreen.clickLineButton({ index: 2 });
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU2.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.clickStepButton({ index: 1 });
        await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await DistributionStepScreen.goBack();
        await DistributionLineScreen.goBack();
    });

    await DistributionJobScreen.complete();

    await Backend.expect({
        title: 'Both HUs in wh2 after partial drop-all + complete',
        hus: {
            HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
            HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
        }
    });
});