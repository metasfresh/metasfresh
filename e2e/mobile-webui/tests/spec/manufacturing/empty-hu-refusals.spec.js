import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';

/**
 * Empty HU write-off — refusals and the empties movement, from REQUIREMENTS.md §5, TC9-TC11.
 *
 * TC1-TC3 (core case) live in empty-hu-core.spec.js; TC4-TC5 (scope) live in empty-hu-scope.spec.js;
 * TC6-TC8 (regression/leakage/no-op) live in empty-hu-regression.spec.js.
 */

const EMPTIED_REASON = 'E';

const startIssueStep = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.openScanScreen();
    await RawMaterialIssueLineScanScreen.typeQRCode(masterdata.handlingUnits.HU.qrCode);
    await GetQuantityDialog.waitForDialog();
};

// ---------------------------------------------------------------------------------------------
// TC9: a multi-product HU does not offer the reason (AC16).
// ---------------------------------------------------------------------------------------------

/**
 * Same shape as empty-hu-core.spec.js's core case, plus `additionalProducts` (this task's harness
 * extension, e7fa2b1) so the scanned HU carries storage of TWO distinct products — the exact
 * precondition `RawMaterialsIssueActivityHandler.isSingleProductStorage` gates on
 * (`getProductStorages(hu).size() <= 1`).
 */
const createMultiProductMasterdata = async ({ huQty, orderQty }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isAllowEmptyingHUs: true, isConfirmEmptyingHU: true },
            },
            uoms: { KGM: { precision: 5 } },
            warehouses: { wh: {} },
            products: {
                COMP: { uom: 'KGM' },
                COMP2: { uom: 'KGM' },
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM' }] } },
            },
            handlingUnits: {
                HU: {
                    product: 'COMP',
                    warehouse: 'wh',
                    qty: huQty,
                    additionalProducts: [{ product: 'COMP2', qty: 1 }],
                },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('TC9: a multi-product HU does not offer the empty reason', async ({ page }) => {
    const masterdata = await createMultiProductMasterdata({ huQty: 0.5, orderQty: 0.5 });

    await startIssueStep(masterdata);

    // Issue slightly less than the HU's booked quantity of its PRIMARY product — same trigger as the
    // core case: the qty-rejected-reason radio group only appears once a shortfall is entered.
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');

    // Expect: the new reason is absent — not just unchecked, genuinely not offered (AC16 "no generic
    // error is shown" either: the existing reasons render normally alongside it).
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: false });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });

    // Back out without booking anything — this TC only proves the reason is absent.
    await GetQuantityDialog.clickCancel();
    await RawMaterialIssueLineScanScreen.goBack();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '0.5 KGM', COMP2: '1 KGM' },
            },
        },
    });
});

/**
 * TC10 (1st half) — "an HU with packing material in a warehouse WITHOUT a line in empties network
 * 540011 yields a specific actionable error naming the warehouse and the network, with no inventory
 * document and the quantity unchanged" — is NOT implemented here. BLOCKED on a genuine backend
 * constraint, confirmed empirically, that makes the fixture itself unconstructible via the masterdata
 * harness (and, per the trace below, via real production too):
 *
 * `CreateHUCommand.transformCU0` produces the TU via `LUTUProducerDestination` + `HULoader`, run inside
 * `HUContextProcessorExecutor` (`backend/de.metas.handlingunits.base/.../allocation/impl/
 * HUContextProcessorExecutor.java:93-103`). At the end of ANY such HU-allocation run, if the HU context's
 * `HUPackingMaterialsCollector` holds candidates (which producing a TU with a `PackingMaterial` PI item
 * always does — the packing material must be sourced from somewhere), the executor unconditionally calls
 * `huEmptiesService.newEmptiesMovementProducer().setEmptiesMovementDirectionAuto().addCandidates(...)
 * .createMovements()` — the EXACT SAME code path `bookEmptiedHUToZero`'s write-off would later reuse to
 * RETURN packing material. `EmptiesMovementProducer.createMovement` → `HUEmptiesService.getEmptiesLocator`
 * → `getEmptiesWarehouse` (`HUEmptiesService.java:64-83`) looks up the network line by the HU's OWN
 * warehouse regardless of direction, and throws when none exists.
 *
 * Confirmed live: POSTing a masterdata request for `packingInstructions: { PI: { tu, product,
 * qtyCUsPerTU, packingMaterial } }` + `handlingUnits: { HU: { packingInstructions: 'PI', warehouse:
 * 'whExtra' (a freshly created, non-standard warehouse — no line in network 540011) } }` fails
 * `Backend.createMasterdata` itself with HTTP 422:
 *   "* Not found * Warehouse (Empties network=Yes): whExtra_20260908T133203807
 *    Network Distribution: Gebinde"
 * — the SAME `AD_Message`-driven text AC17 describes (names the warehouse and the network), just
 * surfacing at HU PRODUCTION time rather than at write-off/booking time. The identical shape succeeds
 * (HTTP 200) when the warehouse is the seeded `existing: "standard"` one (540008), which network
 * 540011's one seeded line covers — see TC11 below.
 *
 * Consequence: an HU that carries packing material can only ever exist in a warehouse the network
 * already covers — the harness (and, per the traced mechanism, real production) cannot put one into an
 * UNCOVERED warehouse in the first place, so the write-off's own booking-time check on that state is
 * unreachable through normal setup. Reaching AC17's scenario would require a warehouse that HAD network
 * coverage when the HU was produced and lost it afterwards (a config-drift case) — not expressible via
 * `Backend.createMasterdata`, and this run's environment forbids the backend rebuild + app-server
 * restart a masterdata-harness extension would need to be verified.
 *
 * **Decision needed**: whether AC17's scenario is worth a dedicated masterdata command that inserts an
 * HU's packing-material `M_HU_Item` directly (bypassing production) so a booking-time-only repro becomes
 * possible, or whether the setup-time refusal demonstrated above is accepted as sufficient proof of the
 * shared mechanism (not decided here, per this workspace's deferral-needs-approval rule).
 */

// ---------------------------------------------------------------------------------------------
// TC10 (2nd half): an HU assigned to an M_Package (AC18).
// ---------------------------------------------------------------------------------------------

/**
 * Same shape as empty-hu-core.spec.js's core case, plus a `packages` assignment (this task's harness
 * extension, e7fa2b1) linking the HU to a freshly created `M_Package` via `M_Package_HU`.
 */
const createPackageAssignedMasterdata = async ({ huQty, orderQty }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isAllowEmptyingHUs: true, isConfirmEmptyingHU: false },
            },
            uoms: { KGM: { precision: 5 } },
            warehouses: { wh: {} },
            products: {
                COMP: { uom: 'KGM' },
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM' }] } },
            },
            handlingUnits: {
                HU: { product: 'COMP', warehouse: 'wh', qty: huQty },
            },
            packages: { PKG1: { hu: 'HU' } },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

/**
 * TC10 (2nd half) — "an HU assigned to an M_Package reports the existing specific message and leaves
 * stock unchanged" (AC18) — is NOT implemented as a green refusal test. BLOCKED: confirmed by source
 * trace AND a live run that the write-off flow has NO wiring to `IHUPackageDAO.isHUAssignedToPackage`
 * at all. That predicate has exactly ONE caller in the whole backend —
 * `M_ShippingPackage_CreateFromPickingSlots.createShippingPackage` (an unrelated shipping-package
 * process) — never `HUQtyService.updateQty`, `PPOrderIssueScheduleService.bookEmptiedHUToZero`, nor any
 * class in the HU-destroy/`HUContextProcessorExecutor` chain the write-off reuses. Grepped
 * `de.metas.handlingunits.model.validator.M_HU` and every `HUStatusBL` status-transition guard: none
 * checks `M_Package_HU` either.
 *
 * Live confirmation: created the masterdata above (HU assigned to `PKG1` via `M_Package_HU`, HTTP 200,
 * package id resolved) and drove the real issue step with reason `E` (`isConfirmEmptyingHU: false`,
 * matching TC1's shape): the booking completed successfully — no error toast, HU quantity zero, `HUStatus
 * = 'D'` — i.e. the write-off proceeds despite the M_Package assignment. This is a genuine PRODUCT GAP
 * relative to AC18, not a test-writing gap: no assertion is fabricated here for a refusal that does not
 * happen. **No "existing specific message" is reachable from this flow because no code path checks
 * `isHUAssignedToPackage` before booking** — the message AC18 refers to (if it exists anywhere) belongs
 * to the unrelated shipping-package flow above, not to this feature.
 *
 * Fix for a follow-up (out of scope here — no production-code change was authorized beyond the two
 * harness commands this task's dispatch A added): gate `PPOrderIssueScheduleService.bookEmptiedHUToZero`
 * (or `HUQtyService.updateQty`) on `huPackageDAO.isHUAssignedToPackage(hu)`, throwing before any
 * inventory line is created, with an `AD_Message` naming the package the same way AC17's network message
 * names the warehouse.
 *
 * **Decision needed**: whether to open a follow-up issue for this gap (not decided here, per this
 * workspace's deferral-needs-approval rule).
 */

// ---------------------------------------------------------------------------------------------
// TC11: with packing material and a network line, the packaging is returned (AC21).
// ---------------------------------------------------------------------------------------------

/**
 * The HU sits in the seeded `existing: "standard"` warehouse (540008 — this task's harness extension,
 * e7fa2b1), which empties network 540011's one seeded line (540008 -> 540012, "Leergebindelager")
 * covers. `qty` matches `qtyCUsPerTU` exactly (a full TU, no split) so the produced HU is a plain,
 * individually-addressable TU carrying its own `M_HU_PackingMaterial` item (verified in the 15A report's
 * own psql check).
 */
const createPackingMaterialMasterdata = async ({ huQty, orderQty }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isAllowEmptyingHUs: true, isConfirmEmptyingHU: false },
            },
            uoms: { KGM: { precision: 5 } },
            warehouses: { WHSTD: { existing: 'standard' } },
            products: {
                COMP: { uom: 'KGM' },
                PM: { uom: 'KGM' },
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM' }] } },
            },
            packingInstructions: {
                PI: { tu: 'TU', product: 'COMP', qtyCUsPerTU: huQty, packingMaterial: 'PM' },
            },
            handlingUnits: {
                HU: { product: 'COMP', warehouse: 'WHSTD', packingInstructions: 'PI', qty: huQty },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'WHSTD', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('TC11: with packing material and a network line, the packaging is returned', async ({ page }) => {
    const masterdata = await createPackingMaterialMasterdata({ huQty: 0.5, orderQty: 0.5 });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Expect: the HU's quantity is zero and its HUStatus is closed (Destroyed) — AC21's "standard
    // behaviour" outcome. NOT asserted: a write-off inventory carrying the `emptiedHUInventoryDescription`
    // message. Traced + confirmed via read-only psql (dev-time only, never part of this committed spec):
    // unlike the plain-VHU core case (TC1), a TU produced via packing instructions is fully drained by
    // the ordinary physical-issue step itself (`PPOrderIssueScheduleService.process`'s `HUsToNewCUs`
    // allocation, same "complete cuHU" mechanism `empty-hu-regression.spec.js`'s TC6a/b trace names) BEFORE
    // `bookEmptiedHUToZero` runs — so by the time it runs, `huStorage.getProductStorages()` is already
    // empty and it no-ops (the same guard TC8 exercises), creating no inventory of its own. Verified: the
    // ONE inventory this HU carries (`M_InventoryLine` QtyBook=0.5, QtyCount=0.498) has `Description=NULL`
    // — not the write-off message. This is a real, reproducible difference from TC1, not a flake.
    //
    // The empties movement itself (AC21's actual subject) is NOT gated behind that inventory: it fires
    // from the generic HU-destroy path (`HUStatusBL`'s `HUSTATUSES_MoveToEmptiesWarehouse` /
    // `HUContextProcessorExecutor`'s packing-material collector) whenever HUStatus transitions to
    // Destroyed — regardless of which mechanism drove the destroy. Confirmed via read-only psql: an
    // `M_Movement`/`M_MovementLine` row moves the HU's packing-material product from the standard
    // warehouse's locator (540007, Hauptlager) to the network's target locator (540013, Leergebindelager)
    // at the same moment this action completes. NOT asserted here via `Backend.expect`:
    // `de.metas.frontend_testing.expectations` has expectation types for
    // HU/Manufacturing/Picking/PickingSlot/ShipmentSchedule/Inventory, but NONE for `M_Movement` (grepped
    // the whole `expectations/` package) — adding one needs a backend rebuild + app-server restart, which
    // this run's environment forbids. This is a genuine assertion GAP in the harness, not a fabricated
    // pass — see the concern in the task report.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
        },
    });
});
