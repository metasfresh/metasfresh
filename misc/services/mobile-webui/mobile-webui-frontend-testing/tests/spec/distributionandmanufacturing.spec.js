import { Backend } from '../utils/screens/Backend';
import { test } from '../../playwright.config';
import { LoginScreen } from '../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from '../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

import { DistributionJobsListScreen } from "../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../utils/screens/distribution/DistributionLineScreen';
import { DistributionStepScreen } from '../utils/screens/distribution/DistributionStepScreen';

const createMasterdata = async () => {
    const ddOrder1Line1 = { product: "COMP1", qtyEntered: 5 };
    const ddOrder2Line1 = { product: "COMP2", qtyEntered: 10 };

    const response = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },

            warehouses: {
                "whSource": {},
                "whTarget": {},
                "whInTransit": { inTransit: true },
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BOM": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'whSource', qty: 5 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'whSource', qty: 10 },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "whSource",
                    warehouseTo: "whTarget",
                    warehouseInTransit: "whInTransit",
                    lines: [ddOrder1Line1],
                },
                "DD2": {
                    warehouseFrom: "whSource",
                    warehouseTo: "whTarget",
                    warehouseInTransit: "whInTransit",
                    lines: [ddOrder2Line1],
                }
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'whTarget',
                    product: 'BOM',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    })

    return {
        login: response.login.user,
        warehouseFrom1FacetId: response.distributionOrders.DD1.warehouseFromFacetId,
        launcherTest1Id: response.distributionOrders.DD1.launcherTestId,
        launcherTest2Id: response.distributionOrders.DD2.launcherTestId,
        comp1_huQRCode: response.handlingUnits.HU_COMP1.qrCode,
        comp2_huQRCode: response.handlingUnits.HU_COMP2.qrCode,
        dropToLocatorQRCode: response.warehouses.whTarget.locatorQRCode,
        luPIItemTestId: response.packingInstructions.PI.luPIItemTestId,
        documentNo: response.manufacturingOrders.PP1.documentNo,
        ddOrder1Line1,
        ddOrder2Line1,

    };
}

// noinspection JSUnusedLocalSymbols
test('Distribution and manufacturing test', async ({ page }) => {
    const {
        login,
        warehouseFrom1FacetId,
        launcherTest1Id,
        launcherTest2Id,
        comp1_huQRCode,
        comp2_huQRCode,
        dropToLocatorQRCode,
        luPIItemTestId,
        documentNo,
        ddOrder1Line1,
        ddOrder2Line1

    } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: warehouseFrom1FacetId, expectHitCount: 2 });
    await DistributionJobsListScreen.startJob({ launcherTestId: launcherTest1Id });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode: comp1_huQRCode, expectedQtyToMove: `${ddOrder1Line1.qtyEntered}` });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: dropToLocatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
    await DistributionJobsListScreen.startJob({ launcherTestId: launcherTest2Id });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ huQRCode: comp2_huQRCode, expectedQtyToMove: `${ddOrder2Line1.qtyEntered}` });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode: dropToLocatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();
    await DistributionJobsListScreen.goBack();
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp1_huQRCode, expectQtyEntered: '5' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickIssueButton({ index: 2 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp2_huQRCode, expectQtyEntered: '10' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId })
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '1',
    })

    await ManufacturingJobScreen.complete();

});
