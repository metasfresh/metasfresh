import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';

const createMasterdata = async ({ qtyToMove }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            resources: {
                "plantId": { type: "PT" },
            },
            products: {
                "P1": {},
                "P2": {},
                "P3": {},
            },
            warehouses: {
                "wh1": {},
                "wh2": {},
                "whInTransit": { inTransit: true },
            },
            packingInstructions: {
                "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 4, lu: "LU", qtyTUsPerLU: 20 },
                "PI2": { tu: "TU", product: "P2", qtyCUsPerTU: 4 },
                "PI3": { tu: "TU", product: "P3", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh1', qty: qtyToMove },
                "HU2": { product: 'P2', warehouse: 'wh1', qty: qtyToMove },
                "HU3": { product: 'P3', warehouse: 'wh1', qty: qtyToMove },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [
                        { product: "P1", qtyEntered: qtyToMove },
                        { product: "P2", qtyEntered: qtyToMove },
                        { product: "P3", qtyEntered: qtyToMove },
                    ],
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Pick multiple HUs and drop them all together in one step', async ({ page }) => {
    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step("Assertions after job start, before any move", async () => {
        await Backend.expect({
            title: 'All HUs are in warehouse wh1',
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh1', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh1', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh1', storages: { P3: '100 PCE' } },
            }
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectLineButton({ index: 3, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectDropAllButton({ enabled: false });
    });

    await test.step("Pick line 1", async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.qrCode,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();

        await Backend.expect({
            hus: {
                HU1: { huStatus: 'A', warehouse: 'whInTransit', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh1', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh1', storages: { P3: '100 PCE' } },
            }
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectLineButton({ index: 3, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectDropAllButton({ enabled: true });
    });

    await test.step("Pick line 2", async () => {
        await DistributionJobScreen.clickLineButton({ index: 2 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU2.qrCode,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();

        await Backend.expect({
            hus: {
                HU1: { huStatus: 'A', warehouse: 'whInTransit', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'whInTransit', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh1', storages: { P3: '100 PCE' } },
            }
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
        await DistributionJobScreen.expectLineButton({ index: 3, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectDropAllButton({ enabled: true });
    });

    await test.step("Drop all (line 1 and 2) to wh2", async () => {
        await DistributionJobScreen.dropAllTo({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await Backend.expect({
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh1', storages: { P3: '100 PCE' } },
            }
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 3, qtyToPick: '100 Stk', qtyPicked: '0 Stk', color: 'red' });
        await DistributionJobScreen.expectDropAllButton({ enabled: false });
    });

    await test.step("Pick line 3", async () => {
        await DistributionJobScreen.clickLineButton({ index: 3 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU3.qrCode,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();

        await Backend.expect({
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'whInTransit', storages: { P3: '100 PCE' } },
            }
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 3, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'yellow' });
        await DistributionJobScreen.expectDropAllButton({ enabled: true });
    });

    await test.step("Drop all (line 3) to wh2", async () => {
        await DistributionJobScreen.dropAllTo({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorQRCode });
        await Backend.expect({
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh2', storages: { P3: '100 PCE' } },
            }
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 3, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectDropAllButton({ enabled: false });
    });

    await DistributionJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Pick multiple HUs (by HU code) and drop them all together in one step (using locator code)', async ({ page }) => {
    const masterdata = await createMasterdata({ qtyToMove: 100 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: masterdata.distributionOrders.DD1.warehouseFromFacetId, expectHitCount: 1 });
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });

    await test.step("Pick line 1", async () => {
        await DistributionJobScreen.clickLineButton({ index: 1 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU1.huId,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
    });
    await test.step("Pick line 2", async () => {
        await DistributionJobScreen.clickLineButton({ index: 2 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU2.huId,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
    });
    await test.step("Pick line 3", async () => {
        await DistributionJobScreen.clickLineButton({ index: 3 });
        await DistributionLineScreen.scanHUToMove({
            huQRCode: masterdata.handlingUnits.HU3.huId,
            qtyToMove: '100',
            expectedQtyToMove: '100',
        });
        await DistributionLineScreen.goBack();
    });

    await test.step("Drop all to wh2 by scanning the locator code", async () => {
        await DistributionJobScreen.dropAllTo({ dropToLocatorQRCode: masterdata.warehouses.wh2.locatorCode });
        await Backend.expect({
            hus: {
                HU1: { huStatus: 'A', warehouse: 'wh2', storages: { P1: '100 PCE' } },
                HU2: { huStatus: 'A', warehouse: 'wh2', storages: { P2: '100 PCE' } },
                HU3: { huStatus: 'A', warehouse: 'wh2', storages: { P3: '100 PCE' } },
            }
        });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 2, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectLineButton({ index: 3, qtyToPick: '100 Stk', qtyPicked: '100 Stk', color: 'green' });
        await DistributionJobScreen.expectDropAllButton({ enabled: false });
    });

    await DistributionJobScreen.complete();
});
