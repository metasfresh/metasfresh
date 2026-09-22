import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobsListFiltersScreen } from '../../utils/screens/picking/PickingJobsListFiltersScreen';

const createMasterdata = async ({ salesOrders }) => {
    const salesOrdersEffective = {};
    Object.keys(salesOrders)
        .forEach(key => salesOrdersEffective[key] = {
            bpartner: salesOrders[key].bpartner,
            warehouse: 'wh',
            datePromised: '2025-03-01T05:00:00.000+02:00',
            lines: [{
                product: 'P1',
                qty: 10,
                workplace: salesOrders[key].workplace
            }]
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
                        { customer: "customer3" },
                        { customer: "customer4" },
                    ],
                    filters: ['Customer', 'DeliveryDate']
                }
            },
            bpartners: {
                "customer1": {},
                "customer2": {},
                "customer3": {},
                "customer4": {},
            },
            warehouses: { "wh": {} },
            workplaces: {
                workplace1: {},
                workplace2: {},
            },
            pickingSlots: { slot1: {} },
            products: { "P1": { price: 1 } },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000 },
            },
            salesOrders: salesOrdersEffective,
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Check facets when only scheduled for workplace is enabled', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230.3');
    allure.story('Picking facets');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        salesOrders: {
            'SO1': { bpartner: 'customer1', workplace: 'workplace1' },
            'SO2': { bpartner: 'customer2', workplace: 'workplace2' },
            'SO3': { bpartner: 'customer3', workplace: 'workplace1' },
            'SO4': { bpartner: 'customer4', workplace: 'workplace2' },
        }
    });

    await LoginScreen.login(masterdata.login.user1);

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startPickingApplication();
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.clickFilterButton();

    await test.step('Check initial facets', async () => {
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'Customer_' + masterdata.bpartners.customer1.id, isChecked: false },
            { facetId: 'Customer_' + masterdata.bpartners.customer3.id, isChecked: false },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 2 });
    });

    await test.step('Tick first customer facet', async () => {
        await PickingJobsListFiltersScreen.clickFacet({ facetId: 'Customer_' + masterdata.bpartners.customer1.id });
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'Customer_' + masterdata.bpartners.customer1.id, isChecked: true },
            { facetId: 'Customer_' + masterdata.bpartners.customer3.id, isChecked: false },
            { facetId: 'DeliveryDate_2025-03-01', isChecked: false },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 1 });
    });

    await test.step('Untick first customer facet', async () => {
        await PickingJobsListFiltersScreen.clickFacet({ facetId: 'Customer_' + masterdata.bpartners.customer1.id });
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'Customer_' + masterdata.bpartners.customer1.id, isChecked: false },
            { facetId: 'Customer_' + masterdata.bpartners.customer3.id, isChecked: false },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 2 });
    });

    await test.step('Go back and logout', async () => {
        await PickingJobsListFiltersScreen.goBack();
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.logout();
    });
});
