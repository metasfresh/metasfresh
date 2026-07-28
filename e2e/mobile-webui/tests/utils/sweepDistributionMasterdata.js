import { Backend } from './screens/Backend';
import { generateEAN13 } from './ean13';

//
// The fixture shared by the two "sweep" distribution specs:
//   - sweep_scan_product_after_autoAdvance.spec.js       (allowPickingAnyHU: false)
//   - sweep_scan_HU_after_autoAdvance_anyHU.spec.js      (allowPickingAnyHU: true)
//
// ONE staging LU at a ground locator, feeding every DD order. The two specs must differ in NOTHING
// but allowPickingAnyHU (plus the per-spec barcode prefix and workplace key, which only keep their
// data apart), so each covers one side of that flag with everything else held constant: with it off
// the backend pre-allocates a move plan pinning a source HU per step, with it on it builds no plan at
// all (the anyHU spec pins that as a precondition —
// DistributionUtils.expectPickAnyHUJobWithoutMovePlan). The operator's experience must be the SAME
// either way: after an auto-advance the screen is ready for the PRODUCT scan, never a repeat HU scan.
// Both specs therefore build their masterdata HERE, so the identity is structural instead of two
// ~60-line copies asked by a comment to stay in sync.
//
// The auto-advance carry-forward rule itself is owned by postDistributionPickFromThunk.js.
//

// Single-line DD orders: auto-advance only fires once an order is FULLY picked, which a single-line
// order reaches in one pick. Both specs reference DD1..DD3 by name, so this is not a free knob.
const SWEEP_ORDER_COUNT = 3;

// Plenty of qty on the staging LU so every DD order is a small, partial pick off it.
const LU_QTY = 1000;

/**
 * @param allowPickingAnyHU THE variable under test — the ONLY configuration difference between the
 *        two sweep specs. Always passed explicitly: it is a sticky, global config row that the masterdata
 *        API leaves untouched when omitted (see e2e/mobile-webui/CLAUDE.md § "Debugging Flaky Tests"
 *        rule 3).
 * @param barcodePrefix per-spec prefix of the staging LU's external barcode.
 * @param workplaceKey per-spec workplace name.
 */
export const createSweepMasterdata = async ({ allowPickingAnyHU, barcodePrefix, workplaceKey }) => {
    const luExternalBarcode = `${barcodePrefix}-${Date.now()}`;

    const distributionOrders = {};
    for (let i = 1; i <= SWEEP_ORDER_COUNT; i++) {
        distributionOrders[`DD${i}`] = {
            seqNo: i * 10,
            warehouseFrom: "wh",
            warehouseTo: "wh",
            warehouseInTransit: "whInTransit",
            plant: "plantId",
            lines: [{ product: "P", qtyEntered: i * 10, locatorFrom: "LZ", locatorTo: "packingTable" }],
        };
    }

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: workplaceKey } },
            mobileConfig: {
                distribution: {
                    navigateToJobsListAfterPickFromComplete: true,
                    completeJobAutomatically: true,
                    requireScanningProductCode: true,
                    allowStartNextJobOnly: true,
                    allowPickingAnyHU,
                    orderBys: 'Priority, LocatorPriority',
                    // Job-level caption: asserted by neither sweep spec, but it makes the launcher/job
                    // header render a meaningful caption on the recordings.
                    captionFormat: 'LocatorFrom,LocatorTo,ProductValueAndName,Qty',
                },
            },
            // The workplace is the packing table: a single warehouse, pick-from = packingTable.
            workplaces: { [workplaceKey]: { warehouse: 'wh', pickFromLocator: 'packingTable' } },
            resources: { "plantId": { type: "PT" } },
            products: { "P": { gtin: generateEAN13().ean13 } },
            warehouses: {
                "wh": {
                    locators: {
                        // The ground locator where the ONE staging LU sits.
                        LZ: { isGroundLocator: true, priorityNo: 10 },
                        // The non-ground target every DD order drops to.
                        packingTable: { isGroundLocator: false, priorityNo: 999 },
                    },
                },
                "whInTransit": { inTransit: true },
            },
            handlingUnits: {
                // The single staging LU, scannable via its external barcode, holding plenty of P.
                LU: { product: "P", warehouse: "wh", locator: "LZ", qty: LU_QTY, externalBarcode: luExternalBarcode },
            },
            distributionOrders,
        },
    });

    masterdata.luExternalBarcode = luExternalBarcode;
    return masterdata;
};
