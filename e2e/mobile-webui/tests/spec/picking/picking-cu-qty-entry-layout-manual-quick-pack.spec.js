import { test } from "../../../playwright.config";
import { expectLayoutSound, expectReachable, PHONE_VIEWPORTS } from "../../utils/layout";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { allureMeta, BP_LOCATION_ORDER, createMasterdataRememberingSysconfigs, LAUNCHERS_LIMIT_SYSCONFIGS, login, MODE_MANUAL, restoreSysconfigsAfterEach } from "../../utils/cuQtyEntryCommon";

restoreSysconfigsAfterEach(test);

/** Manual mode: job screen with the Quick Pack All button */

/** Quick Pack All masterdata of pickAllButton.spec.js; `customers` = records of the mobile config */
const createQuickPackMasterdata = async ({ cuQtyEntryMode, customers = [{ customer: 'customer1' }], aggregationType = 'sales_order', secondCustomer = false }) => {
    const orderLines = [{ product: 'P1', qty: 10 }, { product: 'P2', qty: 10 }];
    return await createMasterdataRememberingSysconfigs({
        language: 'en_US',
        request: {
            sysconfigs: LAUNCHERS_LIMIT_SYSCONFIGS,
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType, createShipmentPolicy: 'CL', allowPickingAnyHU: true, allowQuickPackAll: true, shipOnCloseLU: false,
                    pickTo: ['CU', 'LU_CU'], allowCompletingPartialPickingJob: false, allowPickingAnyCustomer: false,
                    cuQtyEntryMode: cuQtyEntryMode ?? MODE_MANUAL, customers,
                },
            },
            bpartners: { customer1: {}, ...(secondCustomer ? { customer2: {} } : {}) },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            workplaces: { workplace1: { warehouse: 'wh', pickingSlot: 'slot1' } },
            products: { P1: { price: 1 }, P2: { price: 2 } },
            packingInstructions: { LU_CU: { cu: true, lu: 'LU', qtyTUsPerLU: 1 } },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' }, HU2: { product: 'P2', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' } },
            salesOrders: {
                SO1: { bpartner: 'customer1', ...BP_LOCATION_ORDER, lines: orderLines },
                ...(secondCustomer ? { SO2: { bpartner: 'customer2', ...BP_LOCATION_ORDER, lines: orderLines } } : {}),
            },
        },
    });
};

for (const viewport of PHONE_VIEWPORTS) {
    test.describe(`manual screen ${viewport.name}`, () => {
        test.use({ viewport: { width: viewport.width, height: viewport.height }, deviceScaleFactor: viewport.deviceScaleFactor });

        // noinspection JSUnusedLocalSymbols
        test(`Manual: job screen with the Quick Pack All button; button hidden in a job with a Scan once line (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const layout = (label, scope) => expectLayoutSound(label, viewport, scope);

            const manual = await createQuickPackMasterdata({});
            await login(manual);
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.filterByDocumentNo(manual.salesOrders.SO1.documentNo);
            await PickingJobsListScreen.startJob({ documentNo: manual.salesOrders.SO1.documentNo });
            await PickingJobScreen.expectPickAllButtonVisible();
            await layout('Manual job screen with Quick Pack All button');
            await expectReachable(PickingJobScreen.pickAllButtonLocator(), 'Quick Pack All button', viewport);
        });

        // noinspection JSUnusedLocalSymbols
        test(`Mixed job (Manual and Scan once customer): Quick Pack All button hidden, job screen sound (${viewport.name})`, async ({ page: _page }) => {
            allureMeta('Responsive Manual screens');
            const masterdata = await createQuickPackMasterdata({
                aggregationType: 'product',
                secondCustomer: true,
                customers: [{ customer: 'customer1', cuQtyEntryMode: MODE_MANUAL }, { customer: 'customer2', cuQtyEntryMode: 'SCAN_ONCE_ENTER_QTY' }],
            });
            await login(masterdata);
            await PickingJobsListScreen.waitForScreen();
            await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 20, productId: masterdata.products.P1.id });
            await PickingJobScreen.expectPickAllButtonHiddenAfterQtyAvailable();
            await expectLayoutSound('Mixed job screen, no Quick Pack All button', viewport);
        });
    });
}
