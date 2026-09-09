import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobsListFiltersScreen } from "../../../utils/screens/picking/PickingJobsListFiltersScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";

// F00230.3 — MobileUI Picking job-LIST caption must show the handover/delivery location and the
// delivery (ready) date alongside the document number, so pickers can tell apart several jobs for
// the same customer. Pure picking-profile configuration:
// the launcher caption is built from the profile's IsDisplayInSummary fields (DisplayValueProvider),
// joined with " | ". HANDOVER_LOCATION renders the handover location, falling back to the ship-to
// address when no handover location is set (this masterdata sets only the ship-to location, so it
// also covers that fallback path). DATE_READY renders the sales order's datePromised.

const createMasterdata = async () => {
    // Unique per run so parallel runs / repeated runs never collide on the bpartner name.
    const customerName = `Kunde-${Date.now()}`;
    const masterdata = await Backend.createMasterdata({
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
                    // The summary caption shows DocumentNo + HANDOVER_LOCATION + DATE_READY.
                    // CUSTOMER is configured but kept OUT of the summary (isShowInSummary:false,
                    // still in the detailed view): the handover-location address already leads
                    // with the customer name, so showing Customer in the summary too would repeat
                    // the name. This mirrors the picking-profile config the feature ships with.
                    fields: [
                        { field: 'DOCUMENT_NO', isShowInSummary: true },
                        { field: 'CUSTOMER', isShowInSummary: false, isShowInDetailed: true },
                        { field: 'HANDOVER_LOCATION', isShowInSummary: true },
                        { field: 'DATE_READY', isShowInSummary: true },
                    ],
                    // Facet filters under test — Customer + DeliveryDate (standard) + HandoverLocation (the addition).
                    filters: ['Customer', 'DeliveryDate', 'HandoverLocation'],
                }
            },
            bpartners: {
                "customer1": { name: customerName, locations: { customer1_location1: {} } },
            },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { prices: [{ price: 1 }] } },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'customer1',
                    location: 'customer1_location1',
                    warehouse: 'wh',
                    datePromised: '2025-03-15T08:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
                },
            },
        }
    });
    return masterdata;
};

// The `page` fixture param must stay even though it's unused here: destructuring it triggers the
// playwright.config fixture that calls setCurrentPage(page), which the screen objects rely on.
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
    const customerId = masterdata.bpartners.customer1.id;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    // The launcher caption shows exactly the three configured summary fields, in order:
    //  - the document number,
    //  - the handover/delivery location (HANDOVER_LOCATION) — the two additions this issue makes,
    //  - the delivery date (DATE_READY) — asserted year-only, address-format-agnostic.
    // Customer is configured but kept OUT of the summary (isShowInSummary:false): the handover-location
    // field already conveys the customer (its address leads with the customer name on the sp80 instance),
    // so a separate Customer field would repeat it. The 3-field count proves Customer is not in the summary.
    await PickingJobsListScreen.expectJobCaption({
        customerId, // locate by the bpartner id (exact, unique per run) — documentNo is only a caption substring
        contains: [documentNo, '2025'],
        fieldCount: 3,
    });
});

// noinspection JSUnusedLocalSymbols
test('Picking job-list filter offers a delivery-location (Lieferort) facet', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.tag('F00230.3');
    allure.story('MobileUI Picking Filter — job list can be filtered by delivery/handover location');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const customerId = masterdata.bpartners.customer1.id;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    // The facet groups are computed from the jobs currently in the list, which load async — wait for
    // our job to be present before opening the filter, otherwise no Customer facet is offered yet.
    // Locate by the bpartner id (exact) — not a documentNo substring (collides in a full-suite run).
    await PickingJobsListScreen.waitForJobVisible({ customerId });

    // Facets are progressive: Customer first, then DeliveryDate, then HandoverLocation. Drill the
    // cascade (select this run's customer → its delivery date) and assert the HandoverLocation
    // (Lieferort) facet is then offered — the delivery-location filter this issue adds.
    await PickingJobsListScreen.clickFilterButton();
    await PickingJobsListFiltersScreen.clickFacet({ facetId: 'Customer_' + masterdata.bpartners.customer1.id });
    await PickingJobsListFiltersScreen.clickFirstFacetOfGroup({ groupPrefix: 'DeliveryDate' });
    await PickingJobsListFiltersScreen.expectFacetGroupOffered({ groupPrefix: 'HandoverLocation' });
});
