import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobsListFiltersScreen } from '../../utils/screens/picking/PickingJobsListFiltersScreen';

/**
 * Companion to facets.spec.js, which pins the same reveal behaviour with the customer group first.
 * Here the ready-date group is configured first, which is the point of having it: an operator can
 * narrow by the date the launcher entry shows them, without picking a customer beforehand.
 *
 * facets.spec.js is deliberately left untouched — it specifies the reveal behaviour itself, and this
 * feature only adds a group that can take the first position.
 */
const createMasterdata = async ({ salesOrders }) => {
    const salesOrdersEffective = {};
    Object.keys(salesOrders)
        .forEach(key => salesOrdersEffective[key] = {
            bpartner: salesOrders[key].bpartner,
            warehouse: 'wh',
            datePromised: salesOrders[key].datePromised,
            lines: [{
                product: 'P1',
                qty: 10,
                workplace: 'workplace1'
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
                    ],
                    // the ready-date group FIRST — the ordering this feature exists to make possible
                    filters: ['PreparationDate', 'Customer']
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
            salesOrders: salesOrdersEffective,
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Check facets when the ready date is the first filter group', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00240');
    allure.story('Picking facets');
    allure.severity('normal');

    const masterdata = await createMasterdata({
        salesOrders: {
            'SO1': { bpartner: 'customer1', datePromised: '2025-03-01T05:00:00.000+02:00' },
            'SO2': { bpartner: 'customer2', datePromised: '2025-03-02T05:00:00.000+02:00' },
        }
    });

    await LoginScreen.login(masterdata.login.user1);

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startPickingApplication();
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.clickFilterButton();

    await test.step('Check initial facets', async () => {
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'PreparationDate_2025-03-01', isChecked: false },
            { facetId: 'PreparationDate_2025-03-02', isChecked: false },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 2 });
    });

    await test.step('Tick the first ready date', async () => {
        await PickingJobsListFiltersScreen.clickFacet({ facetId: 'PreparationDate_2025-03-01' });
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'PreparationDate_2025-03-01', isChecked: true },
            { facetId: 'PreparationDate_2025-03-02', isChecked: false },
            { facetId: 'Customer_' + masterdata.bpartners.customer1.id, isChecked: false },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 1 });
    });

    await test.step('Untick the first ready date', async () => {
        await PickingJobsListFiltersScreen.clickFacet({ facetId: 'PreparationDate_2025-03-01' });
        await PickingJobsListFiltersScreen.expectFacets([
            { facetId: 'PreparationDate_2025-03-01', isChecked: false },
            { facetId: 'PreparationDate_2025-03-02', isChecked: false },
        ]);
        await PickingJobsListFiltersScreen.expectShowResults({ hitCount: 2 });
    });

    await test.step('Go back and logout', async () => {
        await PickingJobsListFiltersScreen.goBack();
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.logout();
    });
});
