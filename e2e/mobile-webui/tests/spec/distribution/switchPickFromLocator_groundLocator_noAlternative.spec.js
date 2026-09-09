import { test } from "../../../playwright.config";
import { expect } from '@playwright/test';
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { expectErrorToast } from '../../utils/common';

// AC6 — no eligible alternative: the job starts on the only ground locator that has the product;
// every other locator is non-ground (or has no P1). Pressing "Lagerort leer" must surface
// "Kein weiterer Lagerort verfügbar" and leave the pick-from unchanged.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: { distribution: {} },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": {} },
            warehouses: {
                "whFrom": {
                    locators: {
                        // g10: ground, has P1 — this is where the DD order starts
                        g10: { isGroundLocator: true,  priorityNo: 10, x: "A", y: "01", z: "0A" },
                        // n20: non-ground — the only "other" locator, must be skipped → no alternative
                        n20: { isGroundLocator: false, priorityNo: 20, x: "A", y: "02", z: "0B" },
                    },
                },
                "whTo": { locators: { whTo_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // Only g10 has P1; n20 is non-ground and has no stock — no eligible alternative
                "HU_g10": { product: "P1", warehouse: "whFrom", locator: "g10", qty: 100 },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "whFrom",
                    warehouseTo: "whTo",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: 100, locatorFrom: "g10" },
                    ],
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Switch pick-from locator — no eligible ground alternative (only non-ground or no-P1 locators) → shows the no-alternative toast and leaves the pick-from unchanged', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('mobileUI DD_Order empty-locator switch: no eligible ground-locator alternative shows "Kein weiterer Lagerort verfügbar" and keeps pick-from unchanged');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    const g10Id = masterdata.warehouses.whFrom.locators.g10.id;

    await test.step('Fresh job: pick-from locator is g10, switch button is visible', async () => {
        await DistributionJobScreen.expectSwitchPickFromLocatorButton({ visible: true });
        expect(Number(await DistributionJobScreen.getPickFromLocator())).toEqual(g10Id);
    });

    await test.step('Press "Lagerort leer" → no eligible alternative → no-alternative error toast', async () => {
        await expectErrorToast(
            'No alternative ground locator',
            async () => {
                await DistributionJobScreen.switchPickFromLocator();
            },
            async ({ textContent }) => {
                // Test runs in en_US, so the toast carries the English AD_Message text (German would be "Kein weiterer Lagerort verfügbar").
                expect(textContent).toContain('No alternative locator available');
            }
        );
    });

    await test.step('Pick-from locator unchanged after failed switch', async () => {
        expect(Number(await DistributionJobScreen.getPickFromLocator())).toEqual(g10Id);
    });
});
