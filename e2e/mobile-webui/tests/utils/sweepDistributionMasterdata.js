import { Backend } from './screens/Backend';
import { generateEAN13 } from './ean13';

//
// The fixture shared by the two "sweep" distribution specs:
//   - sweep_scan_product_after_autoAdvance.spec.js       (allowPickingAnyHU: false)
//   - sweep_scan_HU_after_autoAdvance_anyHU.spec.js      (allowPickingAnyHU: true)
//
// Handling units of ONE product stand at a ground locator and feed a series of single-line DD orders
// that drop onto a packing table. Both specs build their masterdata HERE, so what they share is
// structural instead of two ~60-line copies asked by a comment to stay in sync, and each covers one
// side of allowPickingAnyHU: with it off the backend pre-allocates a move plan pinning a source
// handling unit per step, with it on it builds no plan at all (the anyHU spec pins that as a
// precondition — DistributionUtils.expectPickAnyHUJobWithoutMovePlan). The operator's experience must
// be the SAME either way: after an auto-advance the screen is ready for the PRODUCT scan, never a
// repeat handling-unit scan.
//
// The two specs differ in that flag, in `handlingUnitCount`, and in the per-spec barcode prefix and
// workplace key (which only keep their data apart). The count follows the flag:
//   - true — the recorded customer situation, several handling units of one article at one source
//     locator with the operator identifying one of them; only then can an assertion pin a pick onto
//     THEIR choice rather than onto the only thing standing there.
//   - false — the move plan allocates from whichever eligible handling units the supplier offers first
//     (DDOrderMovePlanCreateCommand.createPlanStep), so several equivalent candidates leave that spec
//     no fixture-determined handling unit to scan; it takes the default of one.
//
// The auto-advance carry-forward rule itself is owned by postDistributionPickFromThunk.js.
//

// Single-line DD orders: auto-advance only fires once an order is FULLY picked, which a single-line
// order reaches in one pick. Both specs reference DD1..DD3 by name, so this is not a free knob.
const SWEEP_ORDER_COUNT = 3;

// Plenty of qty on each handling unit, so every DD order is a small PARTIAL pick and the handling unit
// stays at the source locator to serve the orders that follow. A handling unit an order takes WHOLE is
// moved into transit instead, which is the case carried_hu_cannot_serve_next_order.spec.js owns.
// Exported because a spec pinning WHICH handling unit a pick came off states the qty left on each.
export const SWEEP_HU_QTY = 1000;

/**
 * @param allowPickingAnyHU THE variable under test — the configuration difference between the
 *        two sweep specs. Always passed explicitly: it is a sticky, global config row that the masterdata
 *        API leaves untouched when omitted (see e2e/mobile-webui/CLAUDE.md § "Debugging Flaky Tests"
 *        rule 3).
 * @param barcodePrefix per-spec prefix of the handling units' external barcodes.
 * @param workplaceKey per-spec workplace name.
 * @param handlingUnitCount how many handling units of the product stand at the source locator. They
 *        are created as HU1..HU<n> and their external barcodes returned in
 *        `masterdata.huExternalBarcodes`, keyed by the same identifiers. One by default — what each
 *        spec passes, and why, is in the comment above.
 */
export const createSweepMasterdata = async ({ allowPickingAnyHU, barcodePrefix, workplaceKey, handlingUnitCount = 1 }) => {
    const huExternalBarcodes = {};
    const handlingUnits = {};
    for (let i = 1; i <= handlingUnitCount; i++) {
        const huIdentifier = `HU${i}`;
        const externalBarcode = `${barcodePrefix}-${i}-${Date.now()}`;
        huExternalBarcodes[huIdentifier] = externalBarcode;
        // Each identified by its own external barcode, the way the customer's handling units are
        // labelled — they carry no other label an operator could scan.
        handlingUnits[huIdentifier] = { product: "P", warehouse: "wh", locator: "LZ", qty: SWEEP_HU_QTY, externalBarcode };
    }

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
                        // The ground locator the handling units stand at.
                        LZ: { isGroundLocator: true, priorityNo: 10 },
                        // The non-ground target every DD order drops to.
                        packingTable: { isGroundLocator: false, priorityNo: 999 },
                    },
                },
                "whInTransit": { inTransit: true },
            },
            handlingUnits,
            distributionOrders,
        },
    });

    masterdata.huExternalBarcodes = huExternalBarcodes;
    return masterdata;
};
