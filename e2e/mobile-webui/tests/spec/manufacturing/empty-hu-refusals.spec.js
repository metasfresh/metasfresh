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

const emptiedHUInventoryDescription = (documentNo) => `Bei Materialzuteilung zu ${documentNo} geleert`;

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
 * qtyCUsPerTU, tuPackingMaterial } }` + `handlingUnits: { HU: { packingInstructions: 'PI', warehouse:
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
 * stock unchanged" (AC18). The earlier "live-confirmed bypass" recorded above this comment (now
 * removed) was itself wrong: the fixture HUs it inspected were never touched by the write-off at all
 * (still HUStatus 'A', qty unchanged) — the flow it drove never reached the booking step it claimed to
 * exercise. Re-traced: `HUQtyService.updateQty` -> `SyncInventoryQtyToHUsCommand:332-336` ->
 * `HandlingUnitsBL.destroyIfEmptyStorage:304-348` -> `markDestroyed:350-374`, which — unconditionally,
 * before setting `HUStatus = Destroyed` — calls `IHUPackageBL.retrievePackageIds(huId)` and throws
 * `HUException(ERR_HUHasPackages, huId, packageIds).markAsUserValidationError()` whenever that list is
 * non-empty. The refusal DOES exist on the write-off path; this is a normal test against it, not a
 * product-gap report.
 *
 * `AD_Message` `de.metas.handlingunits.impl.HUHasPackages` (en_US, verified via read-only psql):
 * "You can't destroy handling unit {0} because it's still linked to these
 * packages: {1}." — `{0}`/`{1}` are substituted with the raw `M_HU_ID`/`M_Package_ID` repo ids (not the
 * QR code or package name), so the test matches the stable, parameter-free portion of the sentence.
 */

// noinspection JSUnusedLocalSymbols
test('TC10b: an HU assigned to an M_Package refuses the write-off', async ({ page }) => {
    const masterdata = await createPackageAssignedMasterdata({ huQty: 0.5, orderQty: 0.5 });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });

    // isConfirmEmptyingHU is false in this masterdata, so Done attempts the booking directly (no
    // Yes/No prompt in between) — the refusal must surface right here.
    await GetQuantityDialog.clickDone({ expectedError: "still linked to these packages" });

    // The dialog stays open — the operator is not navigated away by a refused booking, same shape
    // as RawMaterialIssueLineScreen.scanQRCodeExpectError's over-issue case.
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.clickCancel();
    await RawMaterialIssueLineScanScreen.goBack();
    await RawMaterialIssueLineScreen.goBack();

    // Expect: the refusal is atomic — the HU is untouched, no write-off inventory was created.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '0.5 KGM' },
            },
        },
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: false,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});

// ---------------------------------------------------------------------------------------------
// TC11: a completed write-off inventory, and its packing material moved to the empties warehouse (AC21).
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
            warehouses: { WHSTD: { existing: 'standard' }, WHEMPTIES: { existing: 'empties' } },
            products: {
                COMP: { uom: 'KGM' },
                PM: { uom: 'KGM' },
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM' }] } },
            },
            packingInstructions: {
                PI: { tu: 'TU', product: 'COMP', qtyCUsPerTU: huQty, tuPackingMaterial: 'PM' },
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
test('TC11: a completed write-off inventory, and its packing material moved to the empties warehouse', async ({ page }) => {
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
    // behaviour" outcome — AND a completed write-off inventory carrying the AC4 description, same as
    // the plain-VHU core case (TC1). On a PI-produced TU, the entered qty is routed through the HU's
    // weight confirmation (`PPOrderIssueScheduleService.weightHU` -> `WeightHUCommand`, since a
    // packing-material TU is weight-tracked) BEFORE the ordinary issue drains it the rest of the way,
    // so the actual write-off is this weight-confirmation SingleHUInventory, not
    // `bookEmptiedHUToZero`'s own (which by then finds the HU already empty and no-ops) — see the task
    // report for the traced mechanism and the psql evidence.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
        },
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: true,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
        // AC21's actual subject: the packing material returned to the empties warehouse. Fires from the
        // generic HU-destroy path whenever HUStatus transitions to Destroyed, independent of the write-off
        // inventory above (psql-confirmed in the 15B report: an M_Movement/M_MovementLine row moves the PM
        // product from the standard warehouse to the empties one at the same moment this action completes).
        movements: {
            PM: { isExists: true, fromWarehouse: 'WHSTD', toWarehouse: 'WHEMPTIES' },
        },
    });
});
