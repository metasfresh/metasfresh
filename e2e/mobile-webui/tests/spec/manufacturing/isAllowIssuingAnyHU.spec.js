import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { expectErrorToastIf } from '../../utils/common';

const createMasterdata = async ({ finishedProductUOMConfigs, isCreateRawMaterialsStock = true, manufacturing } = {}) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: { manufacturing },
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BOM": {
                    ...(finishedProductUOMConfigs ?? {}),
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
            handlingUnits: isCreateRawMaterialsStock
                ? {
                    "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100 },
                    "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 100 },
                }
                : {},
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    })
}

test.describe('Test isAllowIssuingAnyHU', () => {
    const runScenario = ({ isCreateRawMaterialsStock, isAllowIssuingAnyHU, expectError }) => {
        // noinspection JSUnusedLocalSymbols
        test(`isAllowIssuingAnyHU=${isAllowIssuingAnyHU}, isCreateRawMaterialsStock=${isCreateRawMaterialsStock} => expect ${expectError ? 'ERROR' : 'OK'}`, async ({ page }) => {
            const masterdata = await createMasterdata({
                isCreateRawMaterialsStock,
                manufacturing: {
                    isAllowIssuingAnyHU,
                },
            });

            await LoginScreen.login(masterdata.login.user);
            await ApplicationsListScreen.expectVisible();
            await ApplicationsListScreen.startApplication('mfg');
            await ManufacturingJobsListScreen.waitForScreen();

            await expectErrorToastIf(expectError, 'Expect start job to fail', async () => {
                await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
            });
        });
    };

    runScenario({ isAllowIssuingAnyHU: false, isCreateRawMaterialsStock: false, expectError: true });
    runScenario({ isAllowIssuingAnyHU: false, isCreateRawMaterialsStock: true, expectError: false });
    runScenario({ isAllowIssuingAnyHU: true, isCreateRawMaterialsStock: false, expectError: false });
    runScenario({ isAllowIssuingAnyHU: true, isCreateRawMaterialsStock: true, expectError: false });
});
