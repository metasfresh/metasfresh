import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobsListFiltersScreen } from "../../utils/screens/distribution/DistributionJobsListFiltersScreen";

// Same stable, backend-side group identifiers as the carrier filter specs -- see
// DistributionFacetGroupType.SALES_ORDER (code "salesOrderNo", captioned from the existing
// C_OrderSO_ID AD_Element, "Auftrag" in de_DE).
const SALES_ORDER_GROUP_ID = 'salesOrderNo';
const SALES_ORDER_GROUP_CAPTION = 'Auftrag';

// noinspection JSUnusedLocalSymbols
test('A replenishment job is found by the sales order behind its contributing schedule, and a candidate-generated job by its own sales order', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5112.1: MobileUI Distribution Filter');
    allure.tag('F5112.1');  // Standalone tag for Tags section;
    allure.story('Filter distribution jobs by sales order');
    allure.severity('critical');

    // "replJob" reproduces the Traffic-Management replenishment path: SO_repl's line gets a
    // M_Picking_Job_Schedule assignment (via `schedules`) at the packing workplace, and
    // DDOrderPickingReplenishmentService generates the auto-distribution job from it -- a path that
    // never sets DD_Order.C_Order_ID (REQUIREMENTS.md §3). The association route
    // (DD_OrderLine -> DD_OrderLine_PickingJobSchedule -> M_Picking_Job_Schedule -> M_ShipmentSchedule
    // -> its sales-order line -> the sales order) is the only way to find this job by its sales order.
    //
    // "candidateJob" reproduces a material-disposition (candidate-generated) distribution order via the
    // same salesOrderLine fixture support carrierFilterNoRoute.spec.js uses for the carrier facet: the
    // line references a real sales-order line (DD_OrderLine.C_OrderLineSO_ID), the second demand route.
    //
    // Scoped to one workplace (matching both jobs' locatorTo) so the launcher offers exactly these two
    // jobs -- not every not-started distribution order this shared local stack has accumulated across
    // other runs (the workplace-scoping trap Task 6 already paid for).
    const masterdata = await Backend.createMasterdata({
        language: "de_DE",
        request: {
            login: { mover: { language: "de_DE", workplace: "packingWorkplace" } },
            shippers: { carrierA: {} },
            products: { "P1": { price: 1 }, "P2": { price: 1 } },
            bpartners: { customerA: {} },
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
            handlingUnits: {
                "stockHU_P1": { product: 'P1', warehouse: 'stockWH', locator: 'stockLocator', qty: 10 },
            },
            salesOrders: {
                "SO_repl": { bpartner: 'customerA', warehouse: 'packingWH',
                             datePromised: '2025-03-01T00:00:00.000+02:00',
                             lines: [{ product: 'P1', qty: 10,
                                       schedules: [{ workplace: 'packingWorkplace', qty: 10 }] }] },
                "SO_candidate": { bpartner: 'customerA', warehouse: 'packingWH',
                                  datePromised: '2025-03-01T00:00:00.000+02:00',
                                  lines: [{ product: 'P2', qty: 5 }] },
            },
            distributionOrders: {
                "candidateJob": {
                    warehouseFrom: 'stockWH', warehouseTo: 'packingWH', warehouseInTransit: 'whInTransit',
                    lines: [{ locatorFrom: 'stockLocator', locatorTo: 'packingLocator', product: 'P2', qtyEntered: 5,
                              salesOrderLine: 'SO_candidate_line1' }],
                },
            },
        }
    });

    await LoginScreen.login(masterdata.login.mover);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();

    await test.step('Both the replenishment job and the candidate-generated job are offered', async () => {
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }, { index: 2 }]);
    });

    let replSalesOrderFacetId;
    let candidateSalesOrderFacetId;
    await test.step(`The launcher offers a sales-order filter group captioned "${SALES_ORDER_GROUP_CAPTION}", listing both sales orders with hit count 1 each`, async () => {
        await DistributionJobsListScreen.openFilters();
        await DistributionJobsListFiltersScreen.expectGroupOffered({ groupId: SALES_ORDER_GROUP_ID, caption: SALES_ORDER_GROUP_CAPTION });
        replSalesOrderFacetId = await DistributionJobsListFiltersScreen.getFacetIdByCaptionContains({
            groupId: SALES_ORDER_GROUP_ID, captionContains: masterdata.salesOrders.SO_repl.documentNo, expectHitCount: 1
        });
        candidateSalesOrderFacetId = await DistributionJobsListFiltersScreen.getFacetIdByCaptionContains({
            groupId: SALES_ORDER_GROUP_ID, captionContains: masterdata.salesOrders.SO_candidate.documentNo, expectHitCount: 1
        });
        await DistributionJobsListFiltersScreen.cancel();
    });

    await test.step("Filtering by the replenishment job's sales order keeps only that job listed", async () => {
        await DistributionJobsListScreen.filterByFacetId({ facetId: replSalesOrderFacetId, expectHitCount: 1 });
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }]);
    });

    await test.step("Deselecting it and filtering by the candidate-generated job's sales order keeps only that job listed", async () => {
        await DistributionJobsListScreen.filterByFacetId({ facetId: replSalesOrderFacetId });
        await DistributionJobsListScreen.filterByFacetId({ facetId: candidateSalesOrderFacetId, expectHitCount: 1 });
        await DistributionJobsListScreen.expectJobButtons([{ index: 1 }]);
    });
});
