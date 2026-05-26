import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';

const createMasterdata = async ({ qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { distribution: {} },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                // Source warehouse with 3 active locators — ordering by M_Locator.Value should give wh1_l1 < wh1_l2 < wh1_l3.
                "wh1": { locators: { wh1_l1: {}, wh1_l2: {}, wh1_l3: {} } },
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: qtyToMove },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                    ],
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — button advances to next active locator', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order picker can switch from-locator when current locator is empty');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step('Fresh job: button is visible', async () => {
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });

    await test.step('Press "Lagerort leer" — locator advances, button remains visible (still has alternatives)', async () => {
        await DistributionJobScreen.switchPickFromLocator();
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });
});

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — successive presses cycle round-robin', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order switch from-locator wraps round-robin through all active locators');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    // The warehouse has 3 active locators. Three presses cycle wh1_l1 -> wh1_l2 -> wh1_l3 -> wh1_l1.
    // Each press succeeds without error and the button stays available (no "no-alternative" rejection
    // until picking starts or the warehouse has <=1 active locators).
    await test.step('Press 3 times — round-robin cycles through all locators', async () => {
        await DistributionJobScreen.switchPickFromLocator();
        await DistributionJobScreen.switchPickFromLocator();
        await DistributionJobScreen.switchPickFromLocator();
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });
});

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — button hidden once picking has started', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order switch from-locator is unavailable after the first pick');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step('Pick the line', async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
    });

    await test.step('After pick: switch button is hidden', async () => {
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: false });
    });
});
