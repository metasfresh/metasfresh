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
    const response = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {},
            warehouses: {
                "wh1": {},
                "wh2": {},
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
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh1', qty: 100 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh1', qty: 100 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh2',
                    product: 'BOM',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    lines: [{ product: "COMP1", qtyEntered: 1 }],
                },
                "DD2": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    lines: [{ product: "COMP2", qtyEntered: 2 }],
                }
            },
        }
    })

    return {
        login: response.login.user,
        warehouseFrom1FacetId: response.distributionOrders.DD1.warehouseFromFacetId,
        launcherTestId: response.distributionOrders.DD1.launcherTestId,

        comp1_huQRCode: response.handlingUnits.HU_COMP1.qrCode,
        comp2_huQRCode: response.handlingUnits.HU_COMP2.qrCode,
        dropToLocatorQRCode: response.warehouses.wh2.locatorQRCode,
        luPIItemTestId: response.packingInstructions.PI.luPIItemTestId,
        documentNo: response.manufacturingOrders.PP1.documentNo,

    };
}

// noinspection JSUnusedLocalSymbols
test('Distribution and manufacturing test', async ({ page }) => {
    const {
        login, warehouseFrom1FacetId,
        launcherTestId,
        comp1_huQRCode,
        comp2_huQRCode,
        dropToLocatorQRCode,
        luPIItemTestId,
        documentNo

    } = await createMasterdata();

    await LoginScreen.login(login);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.filterByFacetId({ facetId: warehouseFrom1FacetId, expectHitCount: 2 });
    await DistributionJobsListScreen.startJob({ launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ comp1_huQRCode });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({ comp2_huQRCode });
    await DistributionLineScreen.clickStepButton({ index: 1 });
    await DistributionStepScreen.scanDropToLocator({ dropToLocatorQRCode });
    await DistributionStepScreen.expectVisible();
    await DistributionStepScreen.goBack();
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.complete();

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp1_huQRCode, expectQtyEntered: '1' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickIssueButton({ index: 2 });
    await RawMaterialIssueLineScreen.scanQRCode({ qrCode: comp2_huQRCode, expectQtyEntered: '2' });
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId })
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '1',
    })

    await ManufacturingJobScreen.complete();
});
