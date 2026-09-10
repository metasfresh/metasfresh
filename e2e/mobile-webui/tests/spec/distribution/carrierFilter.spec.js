import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobsListFiltersScreen } from "../../utils/screens/distribution/DistributionJobsListFiltersScreen";

// The carrier facet group's code (DistributionFacetGroupType.CARRIER_PRODUCT) and its caption
// (the existing AD_Element_ID=584116, "Carrier_Product_ID") are stable, backend-side identifiers —
// unlike the carrier products themselves, which are DB-generated at masterdata-creation time.
const CARRIER_GROUP_ID = 'carrierProduct';
const CARRIER_GROUP_CAPTION = 'Lieferweg-Produkt';

// Fixture note: a carrier product needs no gateway and no stub. A shipper with no M_ShipperGateway
// is eligible for auto-advise regardless of IsApiCarrierAdvise (ShipmentScheduleService does not
// consider that flag), and CarrierAdviseCommand's no-gateway branch synthesises a carrier product
// named after the shipper. Three differently-named gateway-less shippers give three distinct
// carrier products.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "de_DE",
        request: {
            login: { mover: { language: "de_DE", workplace: "packingWorkplace" } },
            shippers: { carrierA: {}, carrierB: {}, carrierC: {} },   // no gateway; isApiCarrierAdvise defaults false
            products: { "P1": { price: 1 }, "P2": { price: 1 } },
            bpartners: { customerA: {}, customerB: {}, customerC: {} },
            warehouses: {
                "stockWH":   { locators: { stockLocator: { isGroundLocator: true, priorityNo: 10 } } },
                "packingWH": { autoDistributionOrder: true,
                               replenishment: { fromWarehouse: 'stockWH', shipper: 'carrierA' },
                               locators: { packingLocator: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                packingWorkplace: { warehouse: 'packingWH', pickFromLocator: 'packingLocator' },
            },
            // One ground locator holding exactly the summed demand per product: the stock-aware
            // allocation then has a single source locator to serve each group from.
            handlingUnits: {
                "stockHU_P1": { product: 'P1', warehouse: 'stockWH', locator: 'stockLocator', qty: 15 },
                "stockHU_P2": { product: 'P2', warehouse: 'stockWH', locator: 'stockLocator', qty: 3 },
            },
            salesOrders: {
                // SO_A and SO_B share product, UOM and target locator, so they aggregate into ONE
                // distribution job carrying two different carriers — a mixed-carrier job at a packing
                // workplace with no carrier products configured (so it accepts any carrier).
                "SO_A": { bpartner: 'customerA', warehouse: 'packingWH', shipper: 'carrierA',
                          datePromised: '2025-03-01T00:00:00.000+02:00',
                          lines: [{ product: 'P1', qty: 10,
                                    schedules: [{ workplace: 'packingWorkplace', qty: 10 }] }] },
                "SO_B": { bpartner: 'customerB', warehouse: 'packingWH', shipper: 'carrierB',
                          datePromised: '2025-03-01T00:00:00.000+02:00',
                          lines: [{ product: 'P1', qty: 5,
                                    schedules: [{ workplace: 'packingWorkplace', qty: 5 }] }] },
                // SO_C ships a different product, so it never aggregates with SO_A/SO_B — a separate
                // job carrying only carrierC, needed to prove that selecting a carrier the mixed job
                // does NOT carry removes that job from the list.
                "SO_C": { bpartner: 'customerC', warehouse: 'packingWH', shipper: 'carrierC',
                          datePromised: '2025-03-01T00:00:00.000+02:00',
                          lines: [{ product: 'P2', qty: 3,
                                    schedules: [{ workplace: 'packingWorkplace', qty: 3 }] }] },
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('A worker at a packing workplace narrows the mixed-carrier job list by carrier product', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5112.1: MobileUI Distribution Filter');
    allure.tag('F5112.1');  // Standalone tag for Tags section;
    allure.story('Filter distribution jobs by carrier product');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.mover);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step('The two mixed-carrier deliveries are offered as one job, alongside the separate carrierC job', async () => {
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }, { index: 2 }]);
    });

    let carrierAFacetId;
    let carrierBFacetId;
    let carrierCFacetId;

    await test.step(`The launcher offers a carrier filter group captioned "${CARRIER_GROUP_CAPTION}", listing all three carriers with their hit counts`, async () => {
        await DistributionJobsListScreen.openFilters();
        await DistributionJobsListFiltersScreen.expectGroupOffered({ groupId: CARRIER_GROUP_ID, caption: CARRIER_GROUP_CAPTION });
        carrierAFacetId = await DistributionJobsListFiltersScreen.getFacetIdByCaptionContains({
            groupId: CARRIER_GROUP_ID, captionContains: masterdata.shippers.carrierA.name, expectHitCount: 1
        });
        carrierBFacetId = await DistributionJobsListFiltersScreen.getFacetIdByCaptionContains({
            groupId: CARRIER_GROUP_ID, captionContains: masterdata.shippers.carrierB.name, expectHitCount: 1
        });
        carrierCFacetId = await DistributionJobsListFiltersScreen.getFacetIdByCaptionContains({
            groupId: CARRIER_GROUP_ID, captionContains: masterdata.shippers.carrierC.name, expectHitCount: 1
        });
        await DistributionJobsListFiltersScreen.cancel();
    });

    await test.step('Selecting CarrierA keeps the mixed-carrier job listed', async () => {
        await DistributionJobsListScreen.filterByFacetId({ facetId: carrierAFacetId, expectHitCount: 1 });
        // The badge count alone only proves the facet's internal hit count -- assert the RENDERED
        // list actually narrowed to the one mixed-carrier job (AC3: the job stays listed).
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }]);
    });

    await test.step('Adding CarrierB to the selection still lists only the one mixed-carrier job', async () => {
        await DistributionJobsListScreen.filterByFacetId({ facetId: carrierBFacetId, expectHitCount: 1 });
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }]);
    });

    await test.step('Switching the selection to CarrierC alone removes the mixed-carrier job', async () => {
        // Tapping an already-active chip toggles it off: deselect A and B first (back to the
        // unfiltered total of both jobs), then select only CarrierC.
        await DistributionJobsListScreen.filterByFacetId({ facetId: carrierAFacetId });
        await DistributionJobsListScreen.filterByFacetId({ facetId: carrierBFacetId, expectHitCount: 2 });
        // Both chips cleared: the rendered list is back to both jobs.
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }, { index: 2 }]);
        await DistributionJobsListScreen.filterByFacetId({ facetId: carrierCFacetId, expectHitCount: 1 });
        // The rendered list narrowed to one job again -- this time the mixed-carrier job must be GONE
        // (AC4), leaving only the carrierC job.
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }]);
    });
});
