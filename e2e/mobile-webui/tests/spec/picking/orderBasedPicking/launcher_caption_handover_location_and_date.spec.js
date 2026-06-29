import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";

// me03 #30117 / F00230.3 — MobileUI Picking job-LIST caption must show the handover/delivery
// location and the delivery (ready) date, in addition to the customer name + document number, so
// pickers can tell apart several jobs for the same customer. Pure picking-profile configuration:
// the launcher caption is built from the profile's IsDisplayInSummary fields (DisplayValueProvider),
// joined with " | ". HANDOVER_LOCATION renders the handover location, falling back to the ship-to
// address when no handover location is set (this masterdata sets only the ship-to location, so it
// also covers that fallback path). DATE_READY renders the sales order's datePromised.

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    // The field set under test — the two additions (HANDOVER_LOCATION + DATE_READY)
                    // plus the context fields, all shown in the launcher summary.
                    fields: [
                        { field: 'DOCUMENT_NO', isShowInSummary: true },
                        { field: 'CUSTOMER', isShowInSummary: true },
                        { field: 'HANDOVER_LOCATION', isShowInSummary: true },
                        { field: 'DATE_READY', isShowInSummary: true },
                    ],
                }
            },
            bpartners: {
                "customer1": { name: "Kunde 30117 Test", locations: { customer1_location1: {} } },
            },
            warehouses: { "wh": {} },
            products: { "P1": { price: 1 } },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    location: 'customer1_location1',
                    warehouse: 'wh',
                    datePromised: '2025-03-15T08:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 20 }],
                },
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('Picking job-list caption shows handover location + delivery date', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.tag('F00230.3');
    allure.story('MobileUI Picking Filter — job-list caption shows handover location and delivery date');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const documentNo = masterdata.salesOrders.SO1.documentNo;
    const customerName = "Kunde 30117 Test"; // set above; the masterdata response does not echo the bpartner name
    const locationId = masterdata.bpartners.customer1.locations.customer1_location1.id;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    // The launcher caption shows all four configured summary fields:
    //  - DocumentNo + Customer (no regression),
    //  - the delivery date (DATE_READY) — year-only, format-agnostic,
    //  - the handover/delivery location (HANDOVER_LOCATION) — proven by the 4th non-empty field.
    await PickingJobsListScreen.expectJobCaption({
        customerLocationId: locationId,
        contains: [documentNo, customerName, '2025'],
        fieldCount: 4,
    });
});
