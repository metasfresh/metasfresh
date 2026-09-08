import { expect } from '@playwright/test';
import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_NOT_FOUND, QTY_NOT_FOUND_REASON_DAMAGED } from '../../utils/screens/picking/GetQuantityDialog';

/**
 * Empty HU write-off — scope tests: the client-level flag off (AC2), and the pallet-sourced line
 * (AC3, AC9, AC10) from REQUIREMENTS.md §5, TC4 and TC5.
 *
 * TC1-TC3 (core case, decline/untick-confirm, dregs case) live in empty-hu-core.spec.js.
 */

const EMPTIED_REASON = 'E';
const NOT_FOUND_REASON_CAPTION = 'Not Found';
const DAMAGED_REASON_CAPTION = 'Damaged';

/**
 * Same masterdata shape as empty-hu-core.spec.js (same customer, same screen), but with
 * `isAllowEmptyingHUs` parameterized: TC4 needs it off.
 *
 * `isConfirmEmptyingHU` / `isAllowEmptyingHUs` are CLIENT-level config (MobileUI_MFG_Config), not
 * per-user, so every test sets them explicitly on its own masterdata request rather than relying on
 * another test's value or run order (mobile-webui/CLAUDE.md "Debugging Flaky Tests" — sticky config).
 */
const createMasterdata = async ({ huQty, orderQty, isAllowEmptyingHUs }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isAllowEmptyingHUs, isConfirmEmptyingHU: true },
            },
            uoms: { KGM: { precision: 5 } },
            warehouses: { wh: {} },
            products: {
                COMP: { uom: 'KGM' },
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM' }] } },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 10 },
            },
            handlingUnits: {
                HU: { product: 'COMP', warehouse: 'wh', qty: huQty },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

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

// noinspection JSUnusedLocalSymbols
test('TC4: Unticking the offer flag restores today\'s screen', async ({ page }) => {
    const masterdata = await createMasterdata({ huQty: 0.5, orderQty: 0.5, isAllowEmptyingHUs: false });

    await startIssueStep(masterdata);

    // Issue slightly less than the HU's booked quantity — the qty-rejected-reason radio group only
    // appears once a shortfall is entered, same trigger as the core case.
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');

    // Expect: the new reason is absent; today's reasons are present, exactly as before this feature.
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: false });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_DAMAGED, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, caption: NOT_FOUND_REASON_CAPTION });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: QTY_NOT_FOUND_REASON_DAMAGED, caption: DAMAGED_REASON_CAPTION });

    // Nothing can be booked from this screen: pick "Not Found" (today's pre-existing behaviour, no
    // confirmation prompt involved) and confirm the screen behaves exactly as it does today. A
    // qty-reject reason (existing functionality, unrelated to this feature) already fully consumes
    // the scanned VHU regardless of the reason picked — no separate inventory booking is involved,
    // so no `inventories` assertion is needed here (and a bare "an inventory document exists" would
    // not be discriminating anyway: the masterdata harness itself stocks the HU via its own
    // completed inventory count — same reasoning as empty-hu-core.spec.js's decline case).
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
        },
    });
});

/**
 * TC5 setup: the same component sourced from a loading unit (pallet) holding several TUs, so the
 * plan emits a primary LU step plus one zero-quantity alternative step per included TU
 * (`PPOrderIssuePlanCreateCommand`, per REQUIREMENTS.md §3 "The plan already decomposes an LU into
 * per-TU steps"). Built via `packingInstructions` with both `lu` and `tu` set, per the pattern in
 * `pick_from_LUs.spec.js` / `empty-hu-core.spec.js`'s own PI block (this one's `product` is the
 * pallet's own content, not the finished good).
 *
 * The pallet's total (13 KGM) is an explicit `qty`, deliberately LESS than the exact 3x5=15 KGM the
 * packing instructions would otherwise fill: 2 TUs load to their full 5 KGM capacity each (and
 * coalesce into a single aggregate row — `TUProducerDestination.loadHU` — with no individual QR), and
 * the 3rd TU is under-filled to 3 KGM, which forces it to be created as a real, individually
 * addressable (non-aggregate) HU (`CreateHUCommand.getTotalQtyCUs` / `getIncludedTUs`, this task's
 * harness extension) — the only way to get a scannable QR for a genuine per-TU alternative step.
 */
const createPalletMasterdata = async () => {
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
                BOM: { bom: { lines: [{ product: 'COMP', qty: 13, uom: 'KGM' }] } },
            },
            packingInstructions: {
                // 3 TUs rated at 5 KGM each (15 KGM rated capacity), but the pallet is loaded with
                // only 13 KGM (below) — matching the order's own 13 KGM need exactly (all of it comes
                // from this one pallet; the order's need cannot exceed what stock actually exists, or
                // manufacturing rejects the job upfront with "Not enough raw materials found"), so the
                // pallet's own capacity caps the primary step's target. That is what makes the
                // qty-rejected-reason radio group reachable at all here: it only renders when
                // `isIssueWholeHU` (`qtyToIssueTarget >= qtyHUCapacity`), i.e. once the whole HU is
                // being issued (`computeStepScanPropsFromActivity.js`).
                PALLET_PI: { lu: 'PALLET_LU', qtyTUsPerLU: 3, tu: 'PALLET_TU', product: 'COMP', qtyCUsPerTU: 5 },
            },
            handlingUnits: {
                // qty: 13 (not the exact 3x5=15 the packing instructions would otherwise fill) — see
                // the doc comment above: this under-fills the 3rd TU to 3 KGM, forcing it to be
                // created as a real, separately-scannable TU.
                PALLET: { product: 'COMP', warehouse: 'wh', packingInstructions: 'PALLET_PI', qty: 13 },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('TC5: A pallet-sourced line — primary LU step refuses the reason, pallet quantity untouched', async ({ page }) => {
    const masterdata = await createPalletMasterdata();

    // This task's harness extension: the under-filled 3rd TU (3 KGM, below its 5 KGM rated capacity)
    // is created as a real, individually addressable (non-aggregate) HU, and the masterdata response
    // lists its own QR code — the 2 full TUs coalesce into one aggregate row with no individual QR
    // (`CreateHUCommand.getIncludedTUs`), so exactly one entry is expected here.
    expect(masterdata.handlingUnits.PALLET.tus).toHaveLength(1);
    expect(masterdata.handlingUnits.PALLET.tus[0].qrCode).toEqual(expect.stringContaining('"huUnitType":"TU"'));

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.openScanScreen();
    // Scan the pallet's own QR code — this resolves to the plan's PRIMARY step, whose source is the
    // LU itself (REQUIREMENTS.md §3: "the primary step's source is genuinely the LU").
    await RawMaterialIssueLineScanScreen.typeQRCode(masterdata.handlingUnits.PALLET.qrCode);
    await GetQuantityDialog.waitForDialog();

    // Issue slightly less than the pallet's own (capping) capacity — the qty-rejected-reason radio
    // group only appears once a shortfall is entered, same trigger as the core case.
    await GetQuantityDialog.expectQtyEntered('13');
    await GetQuantityDialog.typeQtyEntered('12.998');

    // Expect: the new reason is absent on the LU (primary) step; the existing reasons are present.
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: false });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_DAMAGED, offered: true });

    // Back out without submitting anything — the pallet's quantity must stay untouched.
    await GetQuantityDialog.clickCancel();
    await RawMaterialIssueLineScanScreen.goBack();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.PALLET.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '13 KGM' },
            },
        },
    });
});

/**
 * TC5's second half — "open one of the alternative (per-TU) steps; expect the new reason IS
 * offered; using it empties that single TU only, and the pallet's other TUs are untouched"
 * (REQUIREMENTS.md §5) — is NOT implemented here. BLOCKED on a genuine, deeper gap than the
 * masterdata-harness one this task's round-2 fix closed.
 *
 * The harness fix works exactly as intended: `createPalletMasterdata()`'s under-filled 3rd TU (3 KGM,
 * below its 5 KGM rated capacity) is created as a real, individually addressable (non-aggregate) HU,
 * and the masterdata response lists its own QR code (asserted above, in TC5's first half —
 * `masterdata.handlingUnits.PALLET.tus` has exactly one entry). Scanning that QR code DOES resolve to
 * its plan's alternative (per-TU) step (confirmed empirically — the scan succeeds, `GetQuantityDialog`
 * opens, no "not eligible HU barcode" error).
 *
 * But that alternative step's qty-input opens at **"0"**, not the TU's own 3 KGM content (confirmed
 * empirically: a `GetQuantityDialog.expectQtyEntered('3')` assertion failed with `Received: "0"`).
 * Root cause, traced through three source files:
 *
 * - `PPOrderIssuePlanCreateCommand.createAndCollectSteps` (`:241`) builds every per-TU alternative
 *   step with `.qtyToIssue(targetQty.toZero())` — a literal, permanent ZERO at plan-creation time,
 *   regardless of what the TU actually holds (this is REQUIREMENTS.md §3's own "zero-quantity
 *   alternative step" wording — confirmed to mean exactly zero, not merely "not yet computed").
 * - That static zero is echoed verbatim onto the wire: `JsonRawMaterialsIssueLineStep.of` (`:55`) sets
 *   `qtyToIssue(step.getQtyToIssue().toBigDecimal())` with no recomputation.
 * - The ONLY place that ever overwrites a step's persisted `qtyToIssue` with something else,
 *   `ManufacturingJobService.recomputeQtyToIssueForSteps` (`:663`), is called from exactly one call
 *   site: `RawMaterialsIssueOnlyWhatWasReceivedActivityHandler` (`:143`) — a different manufacturing
 *   routing/activity type (source-HU/receipt-linked) that this masterdata (and the standard
 *   `RawMaterialsIssueActivityHandler` our BOM/routing uses) never engages. Even if it did, the
 *   recompute's own loop (`qtyLeftToBeIssued.isGreaterThan(step.getQtyToIssue())`) can never cap a
 *   step whose own static value is 0 — the comparison against a step's own zero is exactly what keeps
 *   it at zero, for as long as any material remains to be issued.
 *
 * Consequence: `computeStepScanPropsFromActivity.js`'s `qtyToIssueTarget = Math.min(stepQtyToIssue,
 * ...)` is pinned at 0 (the smallest of the four terms) for this step regardless of the TU's own
 * capacity (3, correctly read live from real HU storage — `ManufacturingJobLoaderAndSaverSupport
 * ingServices.getHUCapacity`). So `isIssueWholeHU = qtyToIssueTarget(0) >= qtyHUCapacity(3)` is always
 * `false`, and `qtyRejectedReasons: isIssueWholeHU ? getQtyRejectedReasonsForStep(...) : null` never
 * renders — not just `E`, but the ALREADY-EXISTING `N`/`D` reasons too, for ANY per-TU alternative
 * step, in the standard raw-materials-issue flow. This is a pre-existing mechanism, unrelated to this
 * feature (`allowEmptying`/reason `E`) or to the masterdata harness, and out of scope for this task
 * (no production-code change was authorized beyond the harness `qty`-with-`packingInstructions` fix).
 *
 * Minimal fix for a follow-up (out of scope here): either (a) have the standard
 * `RawMaterialsIssueActivityHandler` flow also call (or inline the equivalent of)
 * `ManufacturingJobService.recomputeQtyToIssueForSteps` when a step is scanned, so an alternative
 * step's live target reflects its own real capacity capped by the line's remaining need — with the
 * recompute's own comparison fixed to key off `step.getIssueFromHU().getHuCapacity()`, not the step's
 * own (permanently zero) `qtyToIssue`, since that is the actual defect stopping it from ever capping
 * an alternative step even where it IS called; or (b) reconsider whether `isIssueWholeHU`'s "target
 * must reach capacity" gate is the right precondition for offering reject reasons on an alternative
 * step at all, given its target is deliberately zero by design.
 *
 * **Decision needed**: whether to open a follow-up issue for that fix so TC5's second half can be
 * written (per this workspace's deferral-needs-approval rule, not decided here).
 */
