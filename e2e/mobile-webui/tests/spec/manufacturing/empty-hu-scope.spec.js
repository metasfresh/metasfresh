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
                BOM: { bom: { lines: [{ product: 'COMP', qty: 15, uom: 'KGM' }] } },
            },
            packingInstructions: {
                // 3 TUs x 5 KGM = 15 KGM total on the pallet, matching the order's own 15 KGM need
                // exactly (all of it comes from this one pallet), so the pallet's own capacity caps
                // the primary step's target. That is what makes the qty-rejected-reason radio group
                // reachable at all here: it only renders when `isIssueWholeHU`
                // (`qtyToIssueTarget >= qtyHUCapacity`), i.e. once the whole HU is being issued
                // (`computeStepScanPropsFromActivity.js`).
                PALLET_PI: { lu: 'PALLET_LU', qtyTUsPerLU: 3, tu: 'PALLET_TU', product: 'COMP', qtyCUsPerTU: 5 },
            },
            handlingUnits: {
                PALLET: { product: 'COMP', warehouse: 'wh', packingInstructions: 'PALLET_PI' },
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
    await GetQuantityDialog.expectQtyEntered('15');
    await GetQuantityDialog.typeQtyEntered('14.998');

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
                storages: { COMP: '15 KGM' },
            },
        },
    });
});

/**
 * TC5's second half — "open one of the alternative (per-TU) steps; expect the new reason IS
 * offered; using it empties that single TU only, and the pallet's other TUs are untouched"
 * (REQUIREMENTS.md §5) — is NOT implemented here. BLOCKED: the masterdata harness has no way to
 * obtain a scannable QR code for a TU included inside an LU created via `handlingUnits` +
 * `packingInstructions` (`lu`/`tu`). `CreateHUCommand`/`JsonCreateHUResponse`
 * (backend/de.metas.frontend-testing/.../masterdata/hu/) return the QR of only the single
 * top-level created HU (the LU itself, per `transformCU0`'s `producer.getSingleCreatedHU()`); the
 * included TUs' own `HUQRCode`s are never looked up or returned, and no other reachable
 * masterdata/REST endpoint lists an LU's included TUs with their QR codes. Per this task's
 * instruction, this is reported BLOCKED rather than driven through a debug/shortcut QR code or a
 * fabricated state the real system cannot produce. Minimal fix (not in scope here): extend
 * `CreateHUCommand`, when `packingInstructions.luPIItem != null`, to also resolve the created TUs
 * via `IHandlingUnitsDAO.retrieveIncludedHUs(HuId)` and return their `huId`/`qrCode` in
 * `JsonCreateHUResponse` (e.g. a `tus: [{ huId, qrCode }, ...]` list).
 */
