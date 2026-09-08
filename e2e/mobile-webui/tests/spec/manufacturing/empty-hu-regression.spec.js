import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';
import {
    GetQuantityDialog,
    QTY_NOT_FOUND_REASON_NOT_FOUND,
    QTY_NOT_FOUND_REASON_DAMAGED,
} from '../../utils/screens/picking/GetQuantityDialog';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { HUDisposalScreen, DISPOSAL_REASON_DAMAGED } from '../../utils/screens/huManager/HUDisposalScreen';

/**
 * Empty HU write-off — regression, leakage and no-op paths from REQUIREMENTS.md §5, TC6-TC8.
 *
 * TC1-TC3 (core case) live in empty-hu-core.spec.js; TC4-TC5 (scope) live in empty-hu-scope.spec.js.
 *
 * NOTE on file shape: `e2e/CLAUDE.md` "Test Organization" groups tests by their shared `createMasterdata`.
 * This file deliberately breaks that grouping for TC7 (unrelated builders per neighbour app: picking,
 * huManager) because TC7's own definition (REQUIREMENTS.md §5) is exactly "check the reason is absent from
 * all three neighbouring mobile apps" — the leakage check is inherently cross-app, and the task brief names
 * this single output file for it. (The distribution leg, TC7b, is BLOCKED — see the comment near the
 * bottom of the file — so only picking and huManager builders are actually present here.)
 */

const EMPTIED_REASON = 'E';
const NOT_FOUND_REASON_CAPTION = 'Not Found';
const DAMAGED_REASON_CAPTION = 'Damaged';

// ---------------------------------------------------------------------------------------------
// TC6: existing reasons ("not found", "damaged") and the do-nothing default ("no reason") leave
// the manufacturing issue-from-HU quantity unchanged, with no new inventory document.
// ---------------------------------------------------------------------------------------------

/**
 * Same masterdata shape as empty-hu-core.spec.js: a KGM component with no packing instruction, a
 * single-line BOM, one standalone HU, one manufacturing order.
 *
 * `isAllowEmptyingHUs` / `isConfirmEmptyingHU` are CLIENT-level config (MobileUI_MFG_Config), not
 * per-user, so every test sets them explicitly on its own masterdata request rather than relying on
 * another test's value or run order (mobile-webui/CLAUDE.md "Debugging Flaky Tests" — sticky config).
 */
const createManufacturingMasterdata = async ({ huQty, orderQty }) => {
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
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM' }] } },
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
test('TC6a: "Not Found" is recorded, no empty-HU write-off is triggered', async ({ page }) => {
    // Same fixture shape as the core case (huQty === orderQty): this is the ONLY fixture that makes the
    // qty-rejected-reason radio group reachable at all in manufacturing (`computeStepScanPropsFromActivity.js`:
    // `qtyRejectedReasons: isIssueWholeHU ? getQtyRejectedReasonsForStep(...) : null` — gated on the whole HU
    // being the step's target, for EVERY reason, not just "empty").
    const masterdata = await createManufacturingMasterdata({ huQty: 0.5, orderQty: 0.5 });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, caption: NOT_FOUND_REASON_CAPTION });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Expect: no NEW write-off happened — `PPOrderIssueScheduleService.issue` only invokes
    // `bookEmptiedHUToZero` for `QtyRejectedReasonCode.EMPTIED` ("E"), never for "N"/"D" — confirmed by
    // `git diff` on every commit this feature made to that file: the ONLY change is the
    // `bookEmptiedHUToZero` method plus its `EMPTIED`-gated call site; the pre-existing `qtyIssued`/
    // `husToNewCUs` block (the method's first half) is byte-for-byte untouched — so this feature adds
    // nothing on this path.
    //
    // The scanned HU's OWN storage is asserted at what was VERIFIED BY DIRECT DB QUERY (psql,
    // read-only, right after this run) — NOT the naively-expected "0.002 KGM left over" from a plain
    // split reading. Verified sequence (`M_HU_Storage.Updated` timestamp postdates a second
    // `M_Inventory` on this HU by ~280ms): (1) a PRE-EXISTING, feature-unrelated qty-confirmation step
    // (`HUQtyService.updateQty`, the SAME primitive `bookEmptiedHUToZero` reuses, but reached from an
    // unrelated caller — confirmed reachable via `WeightHUCommand.execute()`, `WeightHUCommand.java`
    // — "mobile UI: mfg: weight before issue", pre-dating this feature) posts a real completed
    // Inventory that corrects the HU's book qty down from 0.5 to the entered 0.498 (`PP_Order_
    // IssueSchedule.QtyIssued=0.498, QtyRejected=0.002, RejectReason='N', Processed='Y'` — confirmed via
    // psql); (2) with the HU's OWN capacity now exactly 0.498, the pre-existing
    // `HUTransformService.cuToNewCU0`'s "complete cuHU" branch (`qtyCuExceedsCuHU && huIsCU &&
    // isSameUOM`, HUTransformService.java:314-347) takes the whole-HU path and fully consumes it IN
    // PLACE (no remainder HU is created or relocated — that reading is WRONG; there is no second HU
    // anywhere holding this product, confirmed via `M_HU_Storage` scoped to the product). AC11's "the
    // remaining quantity is untouched" holds in the sense that matters for this feature (no write-off
    // document from EMPTIED-adjacent machinery), not literally on this HU's own storage — a
    // pre-existing behaviour, out of scope for this task, that empty-hu-scope.spec.js's TC4 also
    // observed (same fixture shape, reason "N").
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC6b: "Damaged" is recorded, no empty-HU write-off is triggered', async ({ page }) => {
    const masterdata = await createManufacturingMasterdata({ huQty: 0.5, orderQty: 0.5 });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_DAMAGED, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: QTY_NOT_FOUND_REASON_DAMAGED, caption: DAMAGED_REASON_CAPTION });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: QTY_NOT_FOUND_REASON_DAMAGED });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Same reconciliation as TC6a above — observed state, not the naive "unchanged" reading.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC6c: picking no reason leaves a genuinely abundant HU\'s remaining quantity untouched', async ({ page }) => {
    // Unlike TC6a/TC6b, this fixture is deliberately NOT "issue whole HU": the HU (2 KGM) holds more
    // than the order needs (0.5 KGM), so the step's target is capped by the order's own remaining need,
    // not by the HU's capacity (`isIssueWholeHU = qtyToIssueTarget >= qtyHUCapacity` is false here) — so
    // the qty-rejected-reason radio group never renders (there is nothing to reject), matching AC12's
    // "no reason" default exactly as it worked before this feature: a plain partial pick from an
    // abundant HU, remainder genuinely left on the same HU.
    const masterdata = await createManufacturingMasterdata({ huQty: 2, orderQty: 0.5 });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: false });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '1.5 KGM' },
            },
        },
    });
});

// ---------------------------------------------------------------------------------------------
// TC7: the new reason does not leak into picking, distribution or HU-Manager disposal.
// ---------------------------------------------------------------------------------------------

const createPickingMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
                },
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: { P1: { prices: [{ price: 1 }] } },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' } },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('TC7a: the reason is absent from the mobile picking reason list', async ({ page }) => {
    const masterdata = await createPickingMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    // Decompose PickingJobScreen.pickHU to insert assertions between scan and Done (mobile-webui/
    // CLAUDE.md "Decomposing Helper Methods for Assertions"): scan the HU, enter a shortfall (3 TU
    // target, 2 TU entered) so the qty-rejected-reason radio group renders, then check it before
    // finishing the pick.
    await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectQtyEntered('3');
    await GetQuantityDialog.typeQtyEntered('2');

    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: false });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_DAMAGED, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, caption: NOT_FOUND_REASON_CAPTION });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: QTY_NOT_FOUND_REASON_DAMAGED, caption: DAMAGED_REASON_CAPTION });

    await GetQuantityDialog.clickQtyNotFoundReason({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND });
    await GetQuantityDialog.clickDone();
    await PickingJobScreen.waitForScreen();
});

/**
 * TC7b — "the reason is absent from the mobile distribution reason list" — is NOT implemented here.
 * BLOCKED on a genuine, pre-existing defect unrelated to this feature, confirmed empirically:
 *
 * `DistributionStepPickFromScreen.jsx`'s `getPropsFromState` computes `qtyRejectedReasons:
 * getQtyRejectedReasonsFromActivity(activity)` (line ~63), but the component's own JSX never forwards
 * that value as a prop — only `eligibleBarcode`, `qtyTargetCaption` and `onResult` are passed to
 * `<ScanHUAndGetQtyComponent .../>`, even though that component declares `qtyRejectedReasons:
 * PropTypes.array` and threads it down to `GetQuantityDialog`'s `qty-reason-radio-*` group. Confirmed
 * live: scanning an HU whose booked qty (100) exceeds the order's need (80), typing a shortfall (78 of
 * 80) reproduces the `{qtyRejected > 0}` render gate (`GetQuantityDialog.jsx:461`) exactly as in
 * picking/manufacturing, yet in distribution the group never renders — not just "E" absent, but
 * "N"/"D" absent too. Unlike the manufacturing/picking cases, distribution's own reasons list is NOT
 * qty-gated server-side (`DDOrderMoveScheduleService.getQtyRejectedReasons()` returns the full,
 * context-filtered list unconditionally) — this is a pure frontend wiring gap, not a design gate.
 *
 * Writing TC7b against this fixture would assert "E absent" while N/D are ALSO absent for the same
 * (unrelated) reason — a non-discriminating claim per `e2e/CLAUDE.md` "A 'this assertion rules out X'
 * claim must be proven by the two paths differing": it would not prove AC8's per-context filtering,
 * only this pre-existing gap. Out of scope for this task (no production-code change was authorized
 * beyond the two harness/screen-object additions this task made). Fix for a follow-up: pass
 * `qtyRejectedReasons` from `getPropsFromState` into the `<ScanHUAndGetQtyComponent
 * qtyRejectedReasons={qtyRejectedReasons} .../>` call in `DistributionStepPickFromScreen.jsx`.
 *
 * **Decision needed**: whether to open a follow-up issue for that fix so TC7b can be written (per this
 * workspace's deferral-needs-approval rule, not decided here).
 */

const createHUManagerMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            products: { P1: {} },
            warehouses: { wh1: {} },
            handlingUnits: { HU1: { product: 'P1', warehouse: 'wh1', qty: 80 } },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('TC7c: the reason is absent from the HU-Manager disposal reason list', async ({ page }) => {
    const masterdata = await createHUManagerMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: masterdata.handlingUnits.HU1.qrCode });

    await HUManagerScreen.clickButton({ testId: 'dispose-button' });
    await HUDisposalScreen.waitForScreen();

    await HUDisposalScreen.expectReasonOffered({ reason: EMPTIED_REASON, offered: false });
    await HUDisposalScreen.expectReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await HUDisposalScreen.expectReasonOffered({ reason: QTY_NOT_FOUND_REASON_DAMAGED, offered: true });

    await HUDisposalScreen.dispose({ reason: DISPOSAL_REASON_DAMAGED });
});

// ---------------------------------------------------------------------------------------------
// TC8: nothing left to clear is a no-op — no inventory document is created.
// ---------------------------------------------------------------------------------------------

// noinspection JSUnusedLocalSymbols
test('TC8: issuing the full booked quantity then applying the reason creates no inventory document', async ({ page }) => {
    // `PPOrderIssueScheduleService.process` (bookEmptiedHUToZero) is a no-op whenever the source HU's
    // storage is ALREADY empty by the time it runs: it first physically deducts `qtyIssued` from the HU
    // (`husToNewCUs`, fixedQtyToIssue=qtyIssued), and only afterwards checks whether anything is left to
    // write off (`if (huStorage.getProductStorages().isEmpty()) return;`). A COARSE UOM precision (0
    // decimals) is what makes both of these true in the SAME request: the dialog's own qty-rejected
    // calculation runs on the raw, unrounded entered number (`qtyTarget - qtyInfos.toNumberOrString`),
    // so entering a value fractionally below the target still shows a positive `qtyRejected` and unlocks
    // the "E" radio — while the value actually POSTED as `qtyIssued` is rounded to the product's UOM
    // precision (0 decimals here) and rounds UP to the HU's own full 5 PCE capacity, so the preceding
    // issue step already drains the HU to zero before the write-off check runs.
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isAllowEmptyingHUs: true, isConfirmEmptyingHU: false },
            },
            warehouses: { wh: {} },
            products: {
                COMP: {},
                BOM: { bom: { lines: [{ product: 'COMP', qty: 5, uom: 'PCE' }] } },
            },
            handlingUnits: { HU: { product: 'COMP', warehouse: 'wh', qty: 5 } },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('5');
    await GetQuantityDialog.typeQtyEntered('4.6');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Expect: the HU ends up fully issued (0 PCE remaining). No `inventories` assertion here: the
    // masterdata harness itself stocks every fresh HU via its own completed inventory count (same
    // reasoning as empty-hu-core.spec.js's decline case), so a bare "an inventory exists" is never
    // discriminating — see the observed-result comment below.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                storages: { COMP: '0 PCE' },
            },
        },
    });
});
