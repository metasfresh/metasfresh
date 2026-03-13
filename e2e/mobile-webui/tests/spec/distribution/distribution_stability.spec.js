import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { DistributionStepScreen } from '../../utils/screens/distribution/DistributionStepScreen';
import { allure } from 'allure-playwright';

const createMasterdata = async ({ products = { "P1": {}, "P2": {} }, handlingUnits, distributionLines, extraWarehouses = {} } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { distribution: {} },
            resources: { "plantId": { type: "PT" } },
            products,
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
                ...extraWarehouses,
            },
            packingInstructions: {
                "PI1": { tu: "TU1", product: "P1", qtyCUsPerTU: 4, lu: "LU", qtyTUsPerLU: 20 },
                "PI2": { tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
            },
            handlingUnits: handlingUnits || {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: 100 },
                "HU2": { product: 'P2', warehouse: 'wh1', qty: 100 },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: distributionLines || [
                        { product: "P1", qtyEntered: 100 },
                        { product: "P2", qtyEntered: 100 },
                    ],
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Unpick moved HU in step screen, then repick and complete', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution stability - unpick and repick');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
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

    await test.step("Verify line 1 is green, line 2 is red", async () => {
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

// noinspection JSUnusedLocalSymbols
test('Go back from partially moved job, resume, and complete', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution stability - go back and resume');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step("Pick line 1, move to in-transit, drop to wh2", async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({ huQRCode: masterdata.handlingUnits.HU1.qrCode, qtyToMove: '100', expectedQtyToMove: '100' });
        await DistributionLineScreen.clickStepButton({ index: 1 });
        await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await DistributionStepScreen.goBack();
        await DistributionLineScreen.goBack();
        await DistributionJobScreen.expectLineButton({ index: 1, color: 'green' });
    });

    await test.step("Go back to jobs list (leaving line 2 unfinished)", async () => {
        await DistributionJobScreen.goBack();
        await DistributionJobsListScreen.waitForScreen();
    });

    await test.step("Resume the same job", async () => {
        await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
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

// noinspection JSUnusedLocalSymbols
test('Pick all lines, drop-all, then abort remaining line', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Distribution stability - partial drop-all then complete remaining');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
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
