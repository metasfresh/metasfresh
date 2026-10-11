import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {},
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "BOM": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                        ]
                    },
                },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 7,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        }
    });
}

// A second manufacturing order whose launcher caption embeds `documentNo` inside a longer token (its product
// name), the way a neighbour's timestamped product name (e.g. BOM_20260910T163004976 contains 630049) does in
// a full-suite run. Reuses the warehouse and component created by createMasterdata.
const neighbourProductName = ({ documentNo }) => `NEIGHBOUR_1${documentNo}76`;

const createNeighbourManufacturingOrder = async ({ documentNo }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            products: {
                "NEIGHBOUR": {
                    name: neighbourProductName({ documentNo }),
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                        ]
                    },
                },
            },
            manufacturingOrders: {
                "PP_NEIGHBOUR": {
                    warehouse: 'wh',
                    product: 'NEIGHBOUR',
                    qty: 3,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Start job by documentNo while another launcher caption contains that documentNo', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.story('Start a manufacturing job from the jobs list');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const documentNo = masterdata.manufacturingOrders.PP1.documentNo;
    const neighbour = await createNeighbourManufacturingOrder({ documentNo });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();

    // Precondition: the collision is really on screen - the neighbour is listed, so a plain substring filter on
    // the documentNo resolves to more than one launcher.
    await ManufacturingJobsListScreen.expectJobButtonContainingText({ text: neighbourProductName({ documentNo }) });
    await expect.poll(() => ManufacturingJobsListScreen.countJobButtonsContainingText({ text: documentNo })).toBeGreaterThanOrEqual(2);

    await ManufacturingJobsListScreen.startJob({ documentNo });
    // The target job (qty 7) was opened, not the neighbour (qty 3).
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '7 Stk' });

    // Start the neighbour too, so it is assigned to this test's user and leaves the shared jobs list: that list
    // shows at most 20 unassigned orders (WorkflowRestAPIService.LaunchersLimit), and later specs need theirs listed.
    await ManufacturingJobScreen.goBack();
    await ManufacturingJobsListScreen.startJob({ documentNo: neighbour.manufacturingOrders.PP_NEIGHBOUR.documentNo });
    await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '3 Stk' });
});
