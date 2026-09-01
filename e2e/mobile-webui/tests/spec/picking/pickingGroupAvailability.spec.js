import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";

/**
 * End-to-end coverage of the picking-launcher availability indicator across a picking group.
 *
 * Two warehouses share ONE picking group:
 *   - whPacking: the workplace's (picker's) warehouse, where the order is delivered from
 *   - whStorage: where the product's stock physically sits
 * The launcher's availability indicator must consider stock across the WHOLE picking group
 * (spanning the workplace's own warehouse), not just the workplace warehouse alone.
 * Before the fix this launcher showed "not available" (red) — stock in a different warehouse
 * of the same picking group was ignored.
 */
// noinspection JSUnusedLocalSymbols
test('Picking launcher shows available when stock sits in a different warehouse of the same picking group', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Launcher availability indicator — cross-warehouse, same picking group');
    allure.severity('critical');

    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    allowPickingAnyHU: true,
                    createShipmentPolicy: 'NO',
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "customer": {} },
            warehouses: {
                // Both warehouses belong to the same picking group "pickingArea".
                "whPacking": { pickingGroup: 'pickingArea' },
                "whStorage": { pickingGroup: 'pickingArea' },
            },
            // Pin the slot to the workplace warehouse — with >1 warehouse the locator is otherwise ambiguous.
            pickingSlots: { slot1: { locator: 'whPacking' } },
            // The picker's workplace is in the PACKING warehouse (not where the product is stored).
            workplaces: { "workplace1": { warehouse: 'whPacking', pickingSlot: 'slot1' } },
            products: {
                "prd": { prices: [{ price: 10 }] },
            },
            packingInstructions: {
                "boxPI": { lu: "LU", qtyTUsPerLU: 1, tu: "TU", product: "prd", qtyCUsPerTU: 1 },
            },
            handlingUnits: {
                // The product's stock physically sits ONLY in the STORAGE warehouse.
                "hu": { product: 'prd', warehouse: 'whStorage', packingInstructions: 'boxPI' },
            },
            salesOrders: {
                // Demand is delivered from the PACKING warehouse (the picker's workplace warehouse).
                "SO1": {
                    bpartner: 'customer',
                    warehouse: 'whPacking',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'prd', qty: 1 }],
                },
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startPickingApplication();
    await PickingJobsListScreen.waitForScreen();

    // The launcher must show "fully available" (green) even though the stock lives in a
    // different warehouse (whStorage) of the same picking group as the workplace (whPacking).
    // Before the fix: the availability indicator only considered the workplace's own warehouse
    // and showed "not available" (red) despite the picking-group stock being pickable.
    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, qtyToDeliver: 1, indicator: 'indicator-color-green' },
    ]);
});
