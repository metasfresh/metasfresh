import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';
import { generateEAN13 } from '../../utils/ean13';
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';

const createMasterdata = async ({ qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user1: { language: "en_US", workplace: "workplace1" },
                user2: { language: "en_US", workplace: "workplace1" },
            },
            mobileConfig: {
                distribution: {
                    orderBys: 'SeqNo',
                    requireTrolley: true,
                }
            },
            resources: { "plantId": { type: "PT" } },
            products: {
                "P1": { gtin: generateEAN13().ean13 },
                "P2": { gtin: generateEAN13().ean13 },
            },
            warehouses: {
                "wh1": { locators: { wh1_l1: {} } },
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": {
                    inTransit: true,
                    locators: {
                        whInTransit_l1: {},
                        whInTransit_l2: {},
                    }
                },
            },
            workplaces: { workplace1: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
            handlingUnits: {
                "HU11": { product: 'P1', warehouse: 'wh1', qty: qtyToMove },
                "HU12": { product: 'P1', warehouse: 'wh1', qty: qtyToMove },
                "HU13": { product: 'P1', warehouse: 'wh1', qty: qtyToMove },
                "HU21": { product: 'P2', warehouse: 'wh1', qty: qtyToMove },
                "HU22": { product: 'P2', warehouse: 'wh1', qty: qtyToMove },
                "HU23": { product: 'P2', warehouse: 'wh1', qty: qtyToMove },
            },
            distributionOrders: {
                "DD1": {
                    seqNo: 10,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P2", qtyEntered: qtyToMove },
                    ],
                },
                "DD2": {
                    seqNo: 20,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P2", qtyEntered: qtyToMove },
                    ],
                },
                "DD3": {
                    seqNo: 30,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P2", qtyEntered: qtyToMove },
                    ],
                },
                "DD4": {
                    seqNo: 40,
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P2", qtyEntered: qtyToMove },
                    ],
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Pick multiple HUs (by HU code) to trolley and drop them all together in one step (using locator code)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
        allure.tag('F5114');  // Standalone tag for Tags section;
    allure.story('Trolley: Pick and drop multiple HUs');
    allure.severity('critical');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await test.step('As user1, pick P1 to whInTransit_l1', async () => {
        await LoginScreen.login(masterdata.login.user1);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();

        await DistributionJobsListScreen.scanTrolley({
            scannedCode: masterdata.warehouses.whInTransit.locators.whInTransit_l1.qrCode,
            expectHeader: masterdata.warehouses.whInTransit.locators.whInTransit_l1.code
        });

        await test.step("Before picking expectations", async () => {
            await DistributionJobsListScreen.expectDropAllButton({ visible: false });
            await DistributionJobsListScreen.expectJobButtons([
                { testId: masterdata.distributionOrders.DD1.launcherTestId, alreadyStarted: false },
                { testId: masterdata.distributionOrders.DD2.launcherTestId, alreadyStarted: false },
                { testId: masterdata.distributionOrders.DD3.launcherTestId, alreadyStarted: false },
                { testId: masterdata.distributionOrders.DD4.launcherTestId, alreadyStarted: false },
            ]);
        });

        await test.step("Pick job 1 - P1", async () => {
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU11.huId,
                productScannedCode: masterdata.products.P1.gtin,
                expectedQtyToMove: '100',
            });
            await DistributionJobScreen.goBack();
        });
        await test.step("Pick job 2 - P1", async () => {
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD2.launcherTestId });
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU12.huId,
                productScannedCode: masterdata.products.P1.gtin,
                expectedQtyToMove: '100',
            });
            await DistributionJobScreen.goBack();
        });
        await test.step("Pick job 3 - P1", async () => {
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD3.launcherTestId });
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU13.huId,
                productScannedCode: masterdata.products.P1.gtin,
                expectedQtyToMove: '100',
            });
            await DistributionJobScreen.goBack();
        });

        await Backend.expect({
            title: 'All HUs are in whInTransit_l1',
            hus: {
                HU11: { huStatus: 'A', locator: 'whInTransit_l1', storages: { P1: '100 PCE' } },
                HU12: { huStatus: 'A', locator: 'whInTransit_l1', storages: { P1: '100 PCE' } },
                HU13: { huStatus: 'A', locator: 'whInTransit_l1', storages: { P1: '100 PCE' } },
            }
        });
    });

    await test.step('Logout user1', async () => {
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.goBack();
        await ApplicationsListScreen.logout();
    });

    await test.step('As user2, pick P2 to whInTransit_l2', async () => {
        await LoginScreen.login(masterdata.login.user2);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();

        await DistributionJobsListScreen.scanTrolley({
            scannedCode: masterdata.warehouses.whInTransit.locators.whInTransit_l2.qrCode,
            expectHeader: masterdata.warehouses.whInTransit.locators.whInTransit_l2.code
        });

        await test.step("Pick job 1 - P2", async () => {
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU21.huId,
                productScannedCode: masterdata.products.P2.gtin,
                expectedQtyToMove: '100',
            });
            await DistributionJobScreen.goBack();
        });
        await test.step("Pick job 2 - P2", async () => {
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD2.launcherTestId });
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU22.huId,
                productScannedCode: masterdata.products.P2.gtin,
                expectedQtyToMove: '100',
            });
            await DistributionJobScreen.goBack();
        });
        await test.step("Pick job 3 - P2", async () => {
            await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD3.launcherTestId });
            await DistributionJobScreen.scanHUToMove({
                huQRCode: masterdata.handlingUnits.HU23.huId,
                productScannedCode: masterdata.products.P2.gtin,
                expectedQtyToMove: '100',
            });
            await DistributionJobScreen.goBack();
        });

        await Backend.expect({
            title: 'All HUs are in whInTransit_l2',
            hus: {
                HU21: { huStatus: 'A', locator: 'whInTransit_l2', storages: { P2: '100 PCE' } },
                HU22: { huStatus: 'A', locator: 'whInTransit_l2', storages: { P2: '100 PCE' } },
                HU23: { huStatus: 'A', locator: 'whInTransit_l2', storages: { P2: '100 PCE' } },
            }
        });
    });

    await test.step('Logout user2', async () => {
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.goBack();
        await ApplicationsListScreen.logout();
    });

    await test.step('As user1, drop content whInTransit_l1 to wh2_l1', async () => {
        await LoginScreen.login(masterdata.login.user1);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.expectTrolley({
            value: masterdata.warehouses.whInTransit.locators.whInTransit_l1.code
        });

        await test.step("Drop everything that was picked to wh2_l1", async () => {
            await DistributionJobsListScreen.dropAll({ dropToQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode });

            await DistributionJobsListScreen.expectDropAllButton({ visible: false });
            await Backend.expect({
                title: 'All HUs are in wh2',
                hus: {
                    HU11: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                    HU12: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                    HU13: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                }
            });
        });
    });

    await test.step('Logout user1', async () => {
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.goBack();
        await ApplicationsListScreen.logout();
    });

    await test.step('As user2, drop content whInTransit_l2 to wh2_l1', async () => {
        await LoginScreen.login(masterdata.login.user2);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('distribution');
        await DistributionJobsListScreen.waitForScreen();
        await DistributionJobsListScreen.expectTrolley({
            value: masterdata.warehouses.whInTransit.locators.whInTransit_l2.code
        });

        await test.step("Drop everything that was picked to wh2_l1", async () => {
            await DistributionJobsListScreen.dropAll({ dropToQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode });

            await DistributionJobsListScreen.expectDropAllButton({ visible: false });
            await Backend.expect({
                title: 'All HUs are in wh2',
                hus: {
                    HU11: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                    HU12: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                    HU13: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                    HU21: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                    HU22: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                    HU23: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                }
            });
        });
    });

});

// noinspection JSUnusedLocalSymbols
test('Wagen freigeben: holder releases own trolley returns to scan screen', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Trolley: holder can release their own Wagen');
    allure.severity('normal');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user1);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await DistributionJobsListScreen.scanTrolley({
        scannedCode: masterdata.warehouses.whInTransit.locators.whInTransit_l1.qrCode,
        expectHeader: masterdata.warehouses.whInTransit.locators.whInTransit_l1.code
    });

    await DistributionJobsListScreen.expectReleaseTrolleyButtonVisible({ visible: true });
    await DistributionJobsListScreen.clickReleaseTrolleyButton();
    await DistributionJobsListScreen.expectTrolleyScanScreen();

    await DistributionJobsListScreen.goBack();
    await ApplicationsListScreen.logout();
});

// noinspection JSUnusedLocalSymbols
test('Wagen freigeben: second user scanning held trolley sees conflict toast and original holder is not bumped', async ({ page }) => {
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114');
    allure.story('Trolley: conflict when second user scans held cart');
    allure.severity('critical');

    const masterdata = await createMasterdata({ qtyToMove: 100 });

    // user1 takes whInTransit_l1
    await LoginScreen.login(masterdata.login.user1);
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.scanTrolley({
        scannedCode: masterdata.warehouses.whInTransit.locators.whInTransit_l1.qrCode,
        expectHeader: masterdata.warehouses.whInTransit.locators.whInTransit_l1.code
    });
    await DistributionJobsListScreen.goBack();
    await ApplicationsListScreen.logout();

    // user2 tries to scan the same trolley — should see conflict toast
    await LoginScreen.login(masterdata.login.user2);
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    // Bypass expectErrorToast — its Promise.race rejects ~20ms before React renders the toast,
    // so the toast wait loses the race even though the backend correctly throws 422 and the
    // toast appears in the DOM. Assert the backend contract (422) and the toast text directly
    // via Playwright's auto-retry; this avoids the helper's race-timing bug.
    const postPromise = page.waitForResponse(
        (r) => r.url().endsWith('/userWorkflows/trolley') && r.request().method() === 'POST'
    );
    await BarcodeScannerComponent.type({
        scannedCode: masterdata.warehouses.whInTransit.locators.whInTransit_l1.qrCode
    });
    const postResponse = await postPromise;
    expect(postResponse.status()).toBe(422);
    await expect(page.getByRole('alert')).toContainText('already assigned', { timeout: 5000 });
    await DistributionJobsListScreen.goBack();
    await ApplicationsListScreen.logout();

    // user1 still holds the trolley
    await LoginScreen.login(masterdata.login.user1);
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.expectTrolley({
        value: masterdata.warehouses.whInTransit.locators.whInTransit_l1.code
    });
    await DistributionJobsListScreen.goBack();
    await ApplicationsListScreen.logout();
});