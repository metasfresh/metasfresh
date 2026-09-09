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
 * AC10 (corrected 2026-09-09): on a pallet-sourced line NO step offers the reason — neither the LU's
 * primary step (TC5) nor a per-TU alternative step (TC5b). An HU standing on a pallet is emptied by
 * issuing it as its own line, never from the pallet line.
 *
 * TC1-TC3 (core case, decline/untick-confirm, dregs case) live in empty-hu-core.spec.js.
 */

const EMPTIED_REASON = 'E';
const NOT_FOUND_REASON_CAPTION = 'Not Found';
const DAMAGED_REASON_CAPTION = 'Damaged';

const emptiedHUInventoryDescription = (documentNo) => `Bei Materialzuteilung zu ${documentNo} geleert`;

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
    // the scanned VHU regardless of the reason picked — no separate inventory booking is involved.
    // The `inventories` assertion below scopes to the write-off's own description (rather than a bare
    // "an inventory document exists", which would always be true regardless of a write-off: the
    // masterdata harness itself stocks the HU via its own completed inventory count — same reasoning
    // as empty-hu-core.spec.js's decline case) to confirm no write-off inventory was created.
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
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: false,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
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

// noinspection JSUnusedLocalSymbols
test('TC5b: a single TU on the pallet offers no reason either', async ({ page }) => {
    const masterdata = await createPalletMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.openScanScreen();
    // Scan the under-filled 3rd TU's own QR (`masterdata.handlingUnits.PALLET.tus[0].qrCode`) — this
    // resolves to the plan's per-TU ALTERNATIVE step (REQUIREMENTS.md §3: "the individual TUs on a
    // pallet are offered as zero-quantity alternative steps the operator can scan instead of the
    // pallet"), not the LU's primary step (covered by TC5's first half, above).
    await RawMaterialIssueLineScanScreen.typeQRCode(masterdata.handlingUnits.PALLET.tus[0].qrCode);
    await GetQuantityDialog.waitForDialog();

    // Observed state (task-13-report.md "Fix round 2"): an alternative step's target is a permanent
    // zero (`PPOrderIssuePlanCreateCommand.createAndCollectSteps`: `.qtyToIssue(targetQty.toZero())`),
    // regardless of what the TU actually holds (3 KGM here) — so the dialog opens already AT its
    // target, with no shortfall to enter. There is nothing to type: entering less than "0" is not a
    // valid quantity, and the reject-reason group only renders once a shortfall exists
    // (`qtyRejected > 0` in `GetQuantityDialog.jsx`), so this zero-shortfall state IS the alternative
    // step's whole behaviour, not a setup step towards it.
    await GetQuantityDialog.expectQtyEntered('0');

    // Expect: the new reason is absent here too — completing AC10 (neither the LU step nor a per-TU
    // alternative step ever offers "empty (auto. inventory)").
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: false });
    // `offered: false` above only proves the `E` radio itself is absent — `toHaveCount(0)` on that one
    // testid would equally pass if `E` were filtered out of an otherwise-rendered N/D group. Assert the
    // group's own container is absent too, so the test documents the REAL state: no qty-rejected
    // reason group at all on a zero-target step (not just "E" excluded from one that renders).
    await GetQuantityDialog.expectQtyRejectedReasonsGroupVisible({ visible: false });

    // Back out without submitting anything — both the TU's and the pallet's quantities must stay
    // untouched, and no write-off inventory must exist.
    await GetQuantityDialog.clickCancel();
    await RawMaterialIssueLineScanScreen.goBack();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.PALLET.tus[0].qrCode]: {
                huStatus: 'A',
                storages: { COMP: '3 KGM' },
            },
            [masterdata.handlingUnits.PALLET.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '13 KGM' },
            },
        },
        inventories: {
            [masterdata.handlingUnits.PALLET.tus[0].qrCode]: {
                isExists: false,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});
