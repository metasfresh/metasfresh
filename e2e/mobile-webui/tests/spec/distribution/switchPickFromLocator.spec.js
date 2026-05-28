import { test } from "../../../playwright.config";
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';

// The 3 source locators sorted by M_Locator.Value — the resolver's ordering key. The masterdata
// `code` is the locator Value, so this is the exact sequence the round-robin advances through.
const locatorIdsOrderedByValue = (masterdata) =>
    Object.values(masterdata.warehouses.wh1.locators)
        .sort((a, b) => a.code.localeCompare(b.code))
        .map((locator) => locator.id);

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
    allure.tag('F5114: MobileUI Distribution');
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

    await test.step('Press "Lagerort leer" — locator advances to the exact next active locator (by Value)', async () => {
        const orderedLocatorIds = locatorIdsOrderedByValue(masterdata);
        const currentLocatorId = Number(await DistributionJobScreen.getPickFromLocator());
        const currentIdx = orderedLocatorIds.indexOf(currentLocatorId);
        expect(currentIdx).toBeGreaterThanOrEqual(0);
        const expectNextLocatorId = orderedLocatorIds[(currentIdx + 1) % orderedLocatorIds.length];

        await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId });
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });
});

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — successive presses cycle round-robin', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
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

    // The warehouse has 3 active locators. Three presses cycle through all of them by Value and wrap
    // back to the start. Each press lands on the exact locator the resolver dictates (not just "a
    // different one"), and the button stays available until picking starts.
    await test.step('Press 3 times — round-robin advances through the exact Value-ordered sequence and wraps back', async () => {
        const orderedLocatorIds = locatorIdsOrderedByValue(masterdata);
        const startLocatorId = Number(await DistributionJobScreen.getPickFromLocator());
        let idx = orderedLocatorIds.indexOf(startLocatorId);
        expect(idx).toBeGreaterThanOrEqual(0);

        const visited = [startLocatorId];
        for (let press = 0; press < 3; press++) {
            const expectNextLocatorId = orderedLocatorIds[(idx + 1) % orderedLocatorIds.length];
            await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId });
            idx = (idx + 1) % orderedLocatorIds.length;
            visited.push(expectNextLocatorId);
        }

        // The first three presses visit each of the 3 active locators exactly once...
        expect(new Set([visited[0], visited[1], visited[2]]).size).toEqual(3);
        // ...and the third press wraps back to the starting locator (round-robin).
        expect(visited[3]).toEqual(startLocatorId);

        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });
});

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — button hidden once picking has started', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
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

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — round-robin wrap then pick + drop completes end-to-end', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order picker can complete a full pick-and-drop after round-robin wrapping back to the starting locator');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    const orderedLocatorIds = locatorIdsOrderedByValue(masterdata);
    const startLocatorId = Number(await DistributionJobScreen.getPickFromLocator());
    let idx = orderedLocatorIds.indexOf(startLocatorId);
    expect(idx).toBeGreaterThanOrEqual(0);

    await test.step(`Press switch ${orderedLocatorIds.length} times — round-robin returns to the starting locator`, async () => {
        for (let press = 0; press < orderedLocatorIds.length; press++) {
            const expectNextLocatorId = orderedLocatorIds[(idx + 1) % orderedLocatorIds.length];
            await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId });
            idx = (idx + 1) % orderedLocatorIds.length;
        }
        expect(Number(await DistributionJobScreen.getPickFromLocator())).toEqual(startLocatorId);
    });

    await test.step('After wrap: pick HU1 from the (now-restored) starting locator', async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
    });

    await test.step('Drop HU1 to target warehouse + complete job + assert backend state', async () => {
        await DistributionJobScreen.dropAllTo({
            dropToLocatorQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode,
        });
        await DistributionJobScreen.complete();
        await Backend.expect({
            title: 'After switch + pick + drop: HU1 is in wh2',
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — after switch, scanning an HU from the original locator yields an error', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order picker cannot pick an HU whose locator does not match the (just-switched) from-locator');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    const orderedLocatorIds = locatorIdsOrderedByValue(masterdata);
    const startLocatorId = Number(await DistributionJobScreen.getPickFromLocator());
    const startIdx = orderedLocatorIds.indexOf(startLocatorId);
    expect(startIdx).toBeGreaterThanOrEqual(0);
    const nextLocatorId = orderedLocatorIds[(startIdx + 1) % orderedLocatorIds.length];

    await test.step('Switch to the next locator (HU1 now lives in the old, no-longer-current locator)', async () => {
        await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId: nextLocatorId });
    });

    await test.step('Scan HU1 → expect "locator does not match" error', async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            expectedQtyToMove: '100',
            expectedError: `The HU's locator does not match the order's locator.`,
        });
    });
});
