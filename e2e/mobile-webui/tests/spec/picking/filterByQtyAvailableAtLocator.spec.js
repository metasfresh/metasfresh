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
                "P0": { price: 1 },
                "P1": { price: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: qtyOnHand },
            },
            salesOrders: {
                "SO_01": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_02": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_03": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_04": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_05": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_06": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_07": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_08": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_09": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_10": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_11": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_12": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_13": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_14": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_15": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_16": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_17": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_18": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_19": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_20": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_21": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_22": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_23": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_24": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_25": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_26": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_27": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_28": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_29": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                "SO_30": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P0', qty: 50, workplace: 'workplace1' }] },
                //
                "SO31": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }] },
                "SO32": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }] },
                "SO33": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }] },
                "SO34": { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 50, workplace: 'workplace1' }] },
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
        { salesOrderId: masterdata.salesOrders.SO_01.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_02.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_03.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_04.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_05.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_06.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_07.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_08.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_09.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_10.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_11.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_12.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_13.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_14.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_15.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_16.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_17.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_18.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_19.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
        { salesOrderId: masterdata.salesOrders.SO_20.id, qtyToDeliver: 50, indicator: 'indicator-color-red' },
    ]);

    await PickingJobsListScreen.clickFilterButton();
    await PickingJobsListFiltersScreen.clickOnlyQtyAvailableButton();
    await PickingJobsListFiltersScreen.clickShowResults();

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO31.id, qtyToDeliver: 50, indicator: 'indicator-color-green' },
        { salesOrderId: masterdata.salesOrders.SO32.id, qtyToDeliver: 50, indicator: 'indicator-color-green' },
        { salesOrderId: masterdata.salesOrders.SO33.id, qtyToDeliver: 50, indicator: 'indicator-color-yellow' },
    ]);
});
