import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

// P1 has stock provisioned in the picking-group locator (workplace1 -> wh); P2 has none.
const createMasterdata = async ({ showQtyAvailableForLines }) => {
    return await Backend.createMasterdata({
        language: 'de_DE',
        request: {
            login: { user: { language: 'de_DE', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    allowQuickPackAll: false,
                    showQtyAvailableForLines,
                    shipOnCloseLU: false,
                    pickTo: ['CU', 'LU_CU'],
                    allowCompletingPartialPickingJob: false,
                    allowPickingAnyCustomer: false,
                    customers: [
                        { customer: 'customer1' },
                    ],
                }
            },
            bpartners: { 'customer1': {} },
            warehouses: { 'wh': {} },
            pickingSlots: { slot1: {} },
            workplaces: { 'workplace1': { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: {
                'P1': { price: 1 }, // WITH stock
                'P2': { price: 2 }, // WITHOUT stock
            },
            packingInstructions: {
                'LU_CU': { cu: true, lu: 'LU', qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                'HU1': { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                // no HU for P2 -> zero available stock in the picking-group locator
            },
            salesOrders: {
                'SO1': {
                    bpartner: 'customer1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 10 },
                        { product: 'P2', qty: 10 },
                    ]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Show available qty per line when flag is on', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Show available qty per line');
    allure.severity('normal');

    const masterdata = await createMasterdata({ showQtyAvailableForLines: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    // line with stock in the picking-group locator shows the actual available qty.
    await PickingJobScreen.expectLineQtyAvailable({ index: 1, qtyAvailable: 'Verfügbar: 10 Stk' });
    // line without any stock shows zero, not hidden.
    await PickingJobScreen.expectLineQtyAvailable({ index: 2, qtyAvailable: 'Verfügbar: 0 Stk' });
});

// noinspection JSUnusedLocalSymbols
test('Hide available qty per line when flag is off', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Show available qty per line');
    allure.severity('normal');

    const masterdata = await createMasterdata({ showQtyAvailableForLines: false });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    // flag off -> no Verfügbar element on any line.
    await PickingJobScreen.expectLineQtyAvailableNotVisible({ index: 1 });
    await PickingJobScreen.expectLineQtyAvailableNotVisible({ index: 2 });
});
