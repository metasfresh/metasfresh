import { test } from "../../../playwright.config";
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';

// The 3 source ground locators in resolver order = M_Locator.PriorityNo ascending.
// createMasterdata assigns wh1_l1=10, wh1_l2=20, wh1_l3=30, so this is the exact sequence the
// round-robin advances through (each is a ground locator holding P1 stock, so all are eligible).
const locatorIdsOrderedByPriority = (masterdata) =>
    ['wh1_l1', 'wh1_l2', 'wh1_l3'].map((key) => masterdata.warehouses.wh1.locators[key].id);

const createMasterdata = async ({ qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // allowPickingAnyHU must be set explicitly: it is a sticky, global, unscoped distribution
            // config row (MobileConfigDistributionCommand keeps the previous value when omitted), and an
            // earlier spec in this folder (sweep_scan_product_after_autoAdvance) leaves it false. These
            // cases need it true — otherwise no scanQRCode-button renders and the pick-from steps time out.
            mobileConfig: { distribution: { allowPickingAnyHU: true } },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                // Source warehouse with 3 GROUND locators, distinct PriorityNo (10<20<30). The resolver
                // advances ground-only, by PriorityNo ascending, over locators that hold P1 stock.
                "wh1": { locators: {
                    wh1_l1: { isGroundLocator: true, priorityNo: 10 },
                    wh1_l2: { isGroundLocator: true, priorityNo: 20 },
                    wh1_l3: { isGroundLocator: true, priorityNo: 30 },
                } },
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // P1 stock on EACH ground locator so all 3 are round-robin-eligible (resolver filters by has-stock).
                "HU1": { product: 'P1', warehouse: 'wh1', locator: 'wh1_l1', qty: qtyToMove },
                "HU2": { product: 'P1', warehouse: 'wh1', locator: 'wh1_l2', qty: qtyToMove },
                "HU3": { product: 'P1', warehouse: 'wh1', locator: 'wh1_l3', qty: qtyToMove },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        // Start on the highest-priority ground locator (wh1_l1, PriorityNo=10).
                        { product: "P1", qtyEntered: qtyToMove, locatorFrom: "wh1_l1" },
                    ],
                },
            },
        },
    });
};

// Two HUs (qtyPerHU each) on two different locators of the SAME source warehouse, and a single DD_Order
// line of `lineQty` that starts on locator A. With lineQty between one and two HUs (e.g. 170 for two
// 100-HUs) the picker fulfills the line by picking HU1 fully from locator A, switching the pick-from
// locator mid-job to B, then picking the remainder from HU2 at locator B.
const createMasterdataTwoLocators = async ({ qtyPerHU, lineQty }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            // allowPickingAnyHU must be set explicitly: it is a sticky, global, unscoped distribution
            // config row (MobileConfigDistributionCommand keeps the previous value when omitted), and an
            // earlier spec in this folder (sweep_scan_product_after_autoAdvance) leaves it false. These
            // cases need it true — otherwise no scanQRCode-button renders and the pick-from steps time out.
            mobileConfig: { distribution: { allowPickingAnyHU: true } },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                // Source warehouse with 2 GROUND locators, PriorityNo 10<20 → round-robin A→B.
                "wh1": { locators: {
                    wh1_l1: { isGroundLocator: true, priorityNo: 10 },
                    wh1_l2: { isGroundLocator: true, priorityNo: 20 },
                } },
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // HU1 on locator A (wh1_l1), HU2 on locator B (wh1_l2) — same warehouse, different locators.
                "HU1": { product: 'P1', warehouse: 'wh1', locator: 'wh1_l1', qty: qtyPerHU },
                "HU2": { product: 'P1', warehouse: 'wh1', locator: 'wh1_l2', qty: qtyPerHU },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    // Single line; starts on locator A so the picker begins where HU1 sits.
                    lines: [
                        { product: "P1", qtyEntered: lineQty, locatorFrom: "wh1_l1" },
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

    await test.step('Press "Lagerort leer" — locator advances to the exact next ground locator (by PriorityNo)', async () => {
        const orderedLocatorIds = locatorIdsOrderedByPriority(masterdata);
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

    // The warehouse has 3 active locators. Three presses cycle through all of them by PriorityNo and wrap
    // back to the start. Each press lands on the exact locator the resolver dictates (not just "a
    // different one"), and the button stays available until picking starts.
    await test.step('Press 3 times — round-robin advances through the exact PriorityNo-ordered sequence and wraps back', async () => {
        const orderedLocatorIds = locatorIdsOrderedByPriority(masterdata);
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
test('Switch pick-from locator — button stays visible after picking has started', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order switch from-locator stays available after the first pick (mid-job switch)');
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

    await test.step('After pick: switch button is still visible (mid-job switch supported)', async () => {
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });
});

// noinspection JSUnusedLocalSymbols
// End-to-end: round-robin wrap back to the starting locator, then pick + drop + Complete navigates
// back to the jobs-list and the HU lands in the target warehouse.
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

    const orderedLocatorIds = locatorIdsOrderedByPriority(masterdata);
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

    const orderedLocatorIds = locatorIdsOrderedByPriority(masterdata);
    const startLocatorId = Number(await DistributionJobScreen.getPickFromLocator());
    const startIdx = orderedLocatorIds.indexOf(startLocatorId);
    expect(startIdx).toBeGreaterThanOrEqual(0);
    const nextLocatorId = orderedLocatorIds[(startIdx + 1) % orderedLocatorIds.length];

    await test.step('Switch to the next locator (HU1 now lives in the old, no-longer-current locator)', async () => {
        await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId: nextLocatorId });
    });

    await test.step('Scan HU1 → expect "HU is not at the target trolley" error', async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            expectedQtyToMove: '100',
            expectedError: `HU is not at the target trolley`,
        });
    });
});

// noinspection JSUnusedLocalSymbols
// End-to-end: fulfill one line from two locators (pick from A, switch mid-job, pick remainder from B),
// then drop + Complete navigates back to the jobs-list and the HU lands fully in the target warehouse.
test('Switch pick-from locator — pick from locator A, switch mid-job, pick from locator B, drop + complete', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order picker can fulfill one order from two locators via a mid-job Lagerort leer press');
    allure.severity('critical');

    // Line needs 170; HU1 (locator A) and HU2 (locator B) hold 100 each. So picking covers two cases:
    //   • locator A, HU1 (100 available): system proposes 100 (HU-limited)
    //   • locator B, HU2 (100 available, 70 still needed): system proposes 70 (remaining-line-limited)
    const masterdata = await createMasterdataTwoLocators({ qtyPerHU: 100, lineQty: 170 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    const locatorAId = masterdata.warehouses.wh1.locators.wh1_l1.id;
    const locatorBId = masterdata.warehouses.wh1.locators.wh1_l2.id;

    await test.step('Fresh job: pick-from locator is A and the switch button is visible', async () => {
        expect(Number(await DistributionJobScreen.getPickFromLocator())).toEqual(locatorAId);
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });

    await test.step('Pick HU1 from locator A — system proposes 100 (the HU holds 100)', async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
    });

    await test.step('After first pick: switch button STILL visible (v2 mid-job switch)', async () => {
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });

    await test.step('Tap Lagerort leer → pick-from locator advances to B', async () => {
        await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId: locatorBId });
    });

    await test.step('Pick HU2 from locator B — system proposes 70 (only 70 still needed)', async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU2.qrCode,
            qtyToMove: '70',
            expectedQtyToMove: '70',
        });
        await DistributionLineScreen.goBack();
    });

    await test.step('Drop to wh2 + complete + assert HU1 fully moved (100 → wh2)', async () => {
        await DistributionJobScreen.dropAllTo({
            dropToLocatorQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode,
        });
        await DistributionJobScreen.complete();
        await Backend.expect({
            title: 'After cross-locator pick (100 from A + 70 from B) + drop: HU1 is fully at wh2',
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
            },
        });
    });
});
