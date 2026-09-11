import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobsListFiltersScreen } from "../../utils/screens/distribution/DistributionJobsListFiltersScreen";

// Same stable, backend-side group identifiers as carrierFilter.spec.js.
const CARRIER_GROUP_ID = 'carrierProduct';
const CARRIER_GROUP_CAPTION = 'Lieferweg-Produkt';

// noinspection JSUnusedLocalSymbols
test('A candidate-generated job is offered under its carrier; a route-less job is offered under none and disappears once a carrier chip is selected', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5112.1: MobileUI Distribution Filter');
    allure.tag('F5112.1');  // Standalone tag for Tags section;
    allure.story('Filter distribution jobs by carrier product');
    allure.severity('critical');

    // Fixture note (mirrors carrierFilter.spec.js): a carrier product needs no gateway and no stub — a
    // shipper with no M_ShipperGateway is eligible for auto-advise regardless of isApiCarrierAdvise, and
    // CarrierAdviseCommand's no-gateway branch synthesises a carrier product named after the shipper.
    //
    // "candidateJob" reproduces a material-disposition (candidate-generated) distribution order: its
    // line references a real sales-order line (`salesOrderLine: 'SO_candidate_line1'`) exactly as
    // DD_OrderLine.C_OrderLineSO_ID does in production — the route the carrier filter's SECOND demand
    // route reads (DD_OrderLine.C_OrderLineSO_ID -> M_ShipmentSchedule.C_OrderLine_ID -> Carrier_Product_ID).
    // "routelessJob" is a plain warehouse-to-warehouse distribution order with neither a contributing
    // picking-job-schedule assignment nor a sales-order-line reference — the permanently carrier-less
    // shape of HU-move / receipt-line / manually-created orders (REQUIREMENTS.md §2).
    // Scoped to one workplace (matching both DD_Order lines' locatorTo) so the launcher offers exactly
    // these two jobs -- not every not-started distribution order this shared local stack has ever
    // accumulated across other runs (mirrors the workplace-scoping filter_by_workplace.spec.js relies on).
    const masterdata = await Backend.createMasterdata({
        language: "de_DE",
        request: {
            login: { mover: { language: "de_DE", workplace: 'packingWorkplace' } },
            shippers: { carrierA: {} },   // no gateway; isApiCarrierAdvise defaults false
            products: { "P1": { price: 1 } },
            bpartners: { customerA: {} },
            warehouses: {
                "wh1": { locators: { wh1Locator: {} } },
                "wh2": { locators: { packingLocator: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                packingWorkplace: { warehouse: 'wh2', pickFromLocator: 'packingLocator' },
            },
            salesOrders: {
                // No `schedules`/`workplace`: only the plain shipment schedule (and its auto-advised
                // carrier) is needed here, not a Traffic-Management picking-job-schedule assignment.
                "SO_candidate": {
                    bpartner: 'customerA', warehouse: 'wh2', shipper: 'carrierA',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 10 }],
                },
            },
            distributionOrders: {
                "candidateJob": {
                    warehouseFrom: 'wh1', warehouseTo: 'wh2', warehouseInTransit: 'whInTransit',
                    lines: [{ locatorFrom: 'wh1Locator', locatorTo: 'packingLocator', product: 'P1', qtyEntered: 10,
                              salesOrderLine: 'SO_candidate_line1' }],
                },
                "routelessJob": {
                    warehouseFrom: 'wh1', warehouseTo: 'wh2', warehouseInTransit: 'whInTransit',
                    lines: [{ locatorFrom: 'wh1Locator', locatorTo: 'packingLocator', product: 'P1', qtyEntered: 5 }],
                },
            },
        }
    });

    await LoginScreen.login(masterdata.login.mover);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step('Both the candidate-generated job and the route-less job are offered', async () => {
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.candidateJob.launcherTestId },
            { testId: masterdata.distributionOrders.routelessJob.launcherTestId },
        ]);
    });

    let carrierAFacetId;
    await test.step(`The launcher offers the carrier filter group captioned "${CARRIER_GROUP_CAPTION}", listing only carrierA with hit count 1 -- the route-less job carries no carrier`, async () => {
        await DistributionJobsListScreen.openFilters();
        await DistributionJobsListFiltersScreen.expectGroupOffered({ groupId: CARRIER_GROUP_ID, caption: CARRIER_GROUP_CAPTION });
        carrierAFacetId = await DistributionJobsListFiltersScreen.getFacetIdByCaptionContains({
            groupId: CARRIER_GROUP_ID, captionContains: masterdata.shippers.carrierA.name, expectHitCount: 1
        });
        await DistributionJobsListFiltersScreen.cancel();
    });

    await test.step('Selecting carrierA keeps the candidate-generated job listed and drops the route-less job', async () => {
        await DistributionJobsListScreen.filterByFacetId({ facetId: carrierAFacetId, expectHitCount: 1 });
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.candidateJob.launcherTestId },
        ]);
    });
});

// noinspection JSUnusedLocalSymbols
test('With only a route-less job on offer, no carrier filter group is offered at all', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5112.1: MobileUI Distribution Filter');
    allure.tag('F5112.1');  // Standalone tag for Tags section;
    allure.story('Filter distribution jobs by carrier product');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
        language: "de_DE",
        request: {
            login: { mover: { language: "de_DE", workplace: 'packingWorkplace' } },
            products: { "P1": {} },   // no `price`: no sales order in this scenario needs a price list
            warehouses: {
                "wh1": { locators: { wh1Locator: {} } },
                "wh2": { locators: { packingLocator: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: {
                packingWorkplace: { warehouse: 'wh2', pickFromLocator: 'packingLocator' },
            },
            distributionOrders: {
                "routelessJob": {
                    warehouseFrom: 'wh1', warehouseTo: 'wh2', warehouseInTransit: 'whInTransit',
                    lines: [{ locatorFrom: 'wh1Locator', locatorTo: 'packingLocator', product: 'P1', qtyEntered: 5 }],
                },
            },
        }
    });

    await LoginScreen.login(masterdata.login.mover);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step('The route-less job is offered', async () => {
        await DistributionJobsListScreen.expectJobButtons([
            { testId: masterdata.distributionOrders.routelessJob.launcherTestId },
        ]);
    });

    await test.step('No carrier filter group is offered -- nothing on offer carries a carrier', async () => {
        await DistributionJobsListScreen.openFilters();
        await DistributionJobsListFiltersScreen.expectGroupNotOffered({ groupId: CARRIER_GROUP_ID });
    });
});
