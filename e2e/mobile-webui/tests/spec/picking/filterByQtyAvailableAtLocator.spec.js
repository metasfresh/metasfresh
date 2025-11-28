import { test } from "../../../playwright.config";
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobsListFiltersScreen } from '../../utils/screens/picking/PickingJobsListFiltersScreen';

const createMasterdata = async ({ qtyOnHand }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "workplace1" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                    activeWorkplaceRequired: true,
                    considerOnlyJobScheduledToWorkplace: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            workplaces: {
                workplace1: {},
                workplace2: {},
            },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { price: 1 },
            },
            packingInstructions: {
                "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 10 },
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: qtyOnHand, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }]
                },
                "SO2": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }]
                },
                "SO3": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }]
                },
                "SO4": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }]
                },
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Filter by Qty Available flag', async ({ page }) => {
    const masterdata = await createMasterdata({ qtyOnHand: 130 });

    await LoginScreen.login(masterdata.login.user);

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startPickingApplication();
    await PickingJobsListScreen.waitForScreen();

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, qtyToDeliver: 50, indicator: 'indicator-color-green' },
        { salesOrderId: masterdata.salesOrders.SO2.id, qtyToDeliver: 50, indicator: 'indicator-color-green' },
        { salesOrderId: masterdata.salesOrders.SO3.id, qtyToDeliver: 50, indicator: 'indicator-color-yellow' },
        { salesOrderId: masterdata.salesOrders.SO4.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
    ]);

    await PickingJobsListScreen.clickFilterButton();
    await PickingJobsListFiltersScreen.clickOnlyQtyAvailableButton();
    await PickingJobsListFiltersScreen.clickShowResults();

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, qtyToDeliver: 50, indicator: 'indicator-color-green' },
        { salesOrderId: masterdata.salesOrders.SO2.id, qtyToDeliver: 50, indicator: 'indicator-color-green' },
        { salesOrderId: masterdata.salesOrders.SO3.id, qtyToDeliver: 50, indicator: 'indicator-color-yellow' },
    ]);

});
