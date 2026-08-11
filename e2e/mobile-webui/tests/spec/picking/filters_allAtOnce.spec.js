import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobsListFiltersScreen } from '../../utils/screens/picking/PickingJobsListFiltersScreen';

const DELIVERY_DATE = '2025-03-01';

const createMasterdata = async () => {
    const salesOrderOf = (bpartner, datePromised) => ({
        bpartner,
        warehouse: 'wh',
        datePromised,
        lines: [{ product: 'P1', qty: 10, workplace: 'workplace1' }]
    });

    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user1: { language: "en_US", workplace: "workplace1" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    activeWorkplaceRequired: true,
                    considerOnlyJobScheduledToWorkplace: true,
                    allowPickingAnyCustomer: false,
                    customers: [
                        { customer: "customer1" },
                        { customer: "customer2" },
                    ],
                    filters: ['Customer', 'DeliveryDate'],
                    showAllFilterGroups: true,
                }
            },
            bpartners: {
                "customer1": {},
                "customer2": {},
            },
            warehouses: { "wh": {} },
            workplaces: { workplace1: {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { price: 1 } },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000 },
            },
            salesOrders: {
                'SO1': salesOrderOf('customer1', `${DELIVERY_DATE}T05:00:00.000+02:00`),
                'SO2': salesOrderOf('customer2', `${DELIVERY_DATE}T05:00:00.000+02:00`),
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Check facets when all filter groups are offered at once', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking facets');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user1);

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startPickingApplication();
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.clickFilterButton();

    await test.step('The delivery date is offered without picking a customer first', async () => {
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'Customer_' + masterdata.bpartners.customer1.id, isChecked: false },
            { facetId: 'Customer_' + masterdata.bpartners.customer2.id, isChecked: false },
            { facetId: 'DeliveryDate_' + DELIVERY_DATE, isChecked: false },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 2 });
    });

    await test.step('Tick the delivery date straight away', async () => {
        await PickingJobsListFiltersScreen.clickFacet({ facetId: 'DeliveryDate_' + DELIVERY_DATE });
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'Customer_' + masterdata.bpartners.customer1.id, isChecked: false },
            { facetId: 'Customer_' + masterdata.bpartners.customer2.id, isChecked: false },
            { facetId: 'DeliveryDate_' + DELIVERY_DATE, isChecked: true },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 2 });
    });

    await test.step('Tick one customer and see the other options narrow', async () => {
        await PickingJobsListFiltersScreen.clickFacet({ facetId: 'Customer_' + masterdata.bpartners.customer1.id });
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'Customer_' + masterdata.bpartners.customer1.id, isChecked: true },
            { facetId: 'Customer_' + masterdata.bpartners.customer2.id, isChecked: false },
            { facetId: 'DeliveryDate_' + DELIVERY_DATE, isChecked: true },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 1 });
    });

    await test.step('Go back and logout', async () => {
        await PickingJobsListFiltersScreen.goBack();
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.logout();
    });
});
