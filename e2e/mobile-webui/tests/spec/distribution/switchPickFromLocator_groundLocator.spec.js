import { test } from "../../../playwright.config";
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';

// Ground-locator priority layout for Spec A1:
//   n05 — non-ground, priorityNo=5,  P1 stock FULL   → always skipped (non-ground)
//   g10 — ground,     priorityNo=10, P1 stock FULL   ← DD order line starts here
//   g20 — ground,     priorityNo=20, P1 PARTIAL stock → still eligible (hasStock > 0)
//   g30 — ground,     priorityNo=30, P2 stock only   → skipped (no P1)
//   g40 — ground,     priorityNo=40, P1 stock FULL   → eligible
//
// Press sequence (resolver reads live ground list ordered by priorityNo, cycles past current):
//   press1: current=g10 → next ground with P1 stock = g20 ✓
//   press2: current=g20 → g30 has no P1 → g40 ✓
//   press3: current=g40 → wraps → g10 ✓ (round-robin proves cycling; button doesn't consume stock)

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { distribution: {} },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {}, "P2": {} },
            warehouses: {
                "whFrom": {
                    locators: {
                        n05: { isGroundLocator: false, priorityNo: 5,  x: "A", y: "01", z: "0B" },
                        g10: { isGroundLocator: true,  priorityNo: 10, x: "A", y: "01", z: "0A" },
                        g20: { isGroundLocator: true,  priorityNo: 20, x: "A", y: "02", z: "0A" },
                        g30: { isGroundLocator: true,  priorityNo: 30, x: "A", y: "03", z: "0A" },
                        g40: { isGroundLocator: true,  priorityNo: 40, x: "A", y: "04", z: "0A" },
                    },
                },
                "whTo": { locators: { whTo_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // n05: P1 stock — non-ground, must be skipped despite stock
                "HU_n05": { product: "P1", warehouse: "whFrom", locator: "n05", qty: 100 },
                // g10: P1 stock FULL — DD order starts here
                "HU_g10": { product: "P1", warehouse: "whFrom", locator: "g10", qty: 100 },
                // g20: P1 PARTIAL stock (less than line qty — still eligible per hasStock > 0 rule)
                "HU_g20": { product: "P1", warehouse: "whFrom", locator: "g20", qty: 30 },
                // g30: P2 only — no P1 → resolver skips it
                "HU_g30_P2": { product: "P2", warehouse: "whFrom", locator: "g30", qty: 100 },
                // g40: P1 stock FULL
                "HU_g40": { product: "P1", warehouse: "whFrom", locator: "g40", qty: 100 },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "whFrom",
                    warehouseTo: "whTo",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        // Start the line at g10; lineQty=100 so g20's partial stock (30) still counts as eligible
                        { product: "P1", qtyEntered: 100, locatorFrom: "g10" },
                    ],
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — ground-locator mode: skips non-ground and no-stock, respects priority, cycles round-robin', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order empty-locator switch skips non-ground and no-stock locators, respects priority, cycles round-robin');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    const g10Id = masterdata.warehouses.whFrom.locators.g10.id;
    const g20Id = masterdata.warehouses.whFrom.locators.g20.id;
    const g40Id = masterdata.warehouses.whFrom.locators.g40.id;

    await test.step('Fresh job: pick-from locator is g10, switch button is visible', async () => {
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
        expect(Number(await DistributionJobScreen.getPickFromLocator())).toEqual(g10Id);
    });

    await test.step('Press 1 — skips n05 (non-ground with stock), lands on g20 (partial stock counts)', async () => {
        await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId: g20Id });
    });

    await test.step('Press 2 — skips g30 (no P1 stock), lands on g40', async () => {
        await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId: g40Id });
    });

    await test.step('Press 3 — wraps round-robin back to g10 (proves cycling; button never consumes stock)', async () => {
        await DistributionJobScreen.switchPickFromLocator({ expectNextLocatorId: g10Id });
    });

    await test.step('Switch button still visible after three presses', async () => {
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
    });
});
