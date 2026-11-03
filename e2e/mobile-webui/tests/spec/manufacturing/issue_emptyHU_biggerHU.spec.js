import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_NOT_FOUND, QTY_NOT_FOUND_REASON_DAMAGED } from '../../utils/screens/picking/GetQuantityDialog';
import { YesNoDialog } from '../../utils/dialogs/YesNoDialog';

/**
 * Empty HU write-off — an HU holding MORE than the step needs (addendum "always offer the reasons",
 * AC-A1 and AC-A2).
 *
 * This is the fixture the whole-HU gate used to lock out: the HU carries 0.502 KGM while the order
 * needs 0.5 KGM, so the step's target (0.5) is capped by the ORDER's remaining need, not by the HU's
 * own content — `qtyToIssueTarget >= qtyHUCapacity` is false. Before the gate was dropped
 * (`computeStepScanPropsFromActivity.js`), no reason at all was offered on such a step and the
 * operator could not declare a physically empty container empty; `computeIssueRequest.js` would also
 * have dropped `qtyRejected`/`qtyRejectedReasonCode` on the way to the backend.
 *
 * The two tests are the two halves of the new contract:
 *  - AC-A1: entering a shortfall (0.49 of the 0.5 target) DOES offer the reasons, and picking
 *    "empty (auto. inventory)" books the HU's whole remaining 0.012 KGM (0.502 - 0.49, NOT the 0.01
 *    shortfall) to zero and destroys the HU — and the confirmation prompt NAMES that same 12 g.
 *  - AC-A2: entering the full target (0.5) asks NOTHING — the reason group renders only once a
 *    shortfall exists (`qtyRejected > 0` in GetQuantityDialog.jsx) — and leaves the HU active with its
 *    0.002 KGM remainder, exactly as before this feature.
 *
 * TC1-TC3 (whole-HU core case) live in issue_emptyHU_writeOff.spec.js; TC4-TC5 (scope) in
 * issue_emptyHU_offering.spec.js; TC6-TC8 in issue_emptyHU_otherReasonsUnchanged.spec.js;
 * TC9-TC11 in issue_emptyHU_refusals.spec.js; the catch-weight gate in issue_emptyHU_catchWeight.spec.js.
 */

const EMPTIED_REASON = 'E';
const EMPTIED_REASON_CAPTION = 'empty (auto. inventory)';

// Same helper as issue_emptyHU_writeOff.spec.js: the AD_Message text (system base language, de_DE) that
// `PPOrderIssueScheduleService#bookEmptiedHUToZero` writes as the write-off inventory's Description.
const emptiedHUInventoryDescription = (documentNo) => `Bei Materialzuteilung zu ${documentNo} geleert`;

/**
 * Same masterdata shape as issue_emptyHU_otherReasonsUnchanged.spec.js's `createManufacturingMasterdata`
 * (a KGM component with no packing instruction, a single-line BOM, one standalone HU, one manufacturing
 * order) — only the huQty/orderQty pair differs: here the HU deliberately holds MORE than the order
 * needs. No tolerance is needed: both tests enter at most the step's target, never above it.
 *
 * `isAllowEmptyingHUs` / `isConfirmEmptyingHU` are CLIENT-level config (MobileUI_MFG_Config), not
 * per-user, so every test sets them explicitly on its own masterdata request rather than relying on
 * another test's value or run order (mobile-webui/CLAUDE.md "Debugging Flaky Tests" — sticky config).
 * AC-A1 switches the confirmation ON because the prompt's WORDING is part of what it pins here; AC-A2
 * leaves it off, having nothing to confirm.
 */
const createManufacturingMasterdata = async ({ huQty, orderQty, isConfirmEmptyingHU = false }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isAllowEmptyingHUs: true, isConfirmEmptyingHU },
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
test('AC-A1: an HU bigger than the need offers the reasons, and "empty" writes off its whole remainder', async ({ page }) => {
    const masterdata = await createManufacturingMasterdata({ huQty: 0.502, orderQty: 0.5, isConfirmEmptyingHU: true });

    await startIssueStep(masterdata);

    // The dialog opens at the ORDER's remaining need (0.5), below the HU's 0.502 content — this is
    // precisely the "not whole HU" step the old gate suppressed every reason on.
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.49');

    // Expect: the whole reason group renders, with the new reason and today's reasons alongside it.
    await GetQuantityDialog.expectQtyRejectedReasonsGroupVisible({ visible: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: EMPTIED_REASON, caption: EMPTIED_REASON_CAPTION });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_DAMAGED, offered: true });

    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    // Expect: the confirmation prompt names what will ACTUALLY be written off — the HU's remainder
    // 0.502 - 0.49 = 12 g — and not the dialog's order-side shortfall 0.5 - 0.49 = 10 g. Exact text:
    // computeEmptyingConfirmationPrompt.js rounds the subtraction to the operands' precision, so the
    // operator reads "12 g" and not the IEEE-754 tail "12.00000000000001 g".
    await YesNoDialog.waitForDialog();
    await YesNoDialog.expectPromptContains('remaining 12 g');
    await YesNoDialog.clickYesButton();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Expect: 0.49 issued, so the HU is left with 0.502 - 0.49 = 0.012 KGM, which the write-off books
    // to zero -> HU closed (Destroyed) with no storage left.
    //
    // `count: 1` with a `description` filter pins DESCRIBED inventories only (the harness itself stocks
    // every fresh HU via its own completed inventory count, so a bare `isExists: true` would never be
    // discriminating). qtyBook/qtyCount pin WHAT was written off: the HU's ACTUAL remainder 0.012 --
    // NOT the 0.01 shortfall the dialog computed as `target - typed`. The two are equal only on a
    // whole-HU step; this fixture is the one that tells them apart.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
        },
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                count: 1,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
                qtyBook: 0.012,
                qtyCount: 0,
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('AC-A2: taking the full target from a bigger HU still asks for no reason', async ({ page }) => {
    const masterdata = await createManufacturingMasterdata({ huQty: 0.502, orderQty: 0.5 });

    await startIssueStep(masterdata);

    // Take exactly the offered target: `qtyRejected` is 0, so `GetQuantityDialog.jsx` renders no reason
    // group at all. Asserting the GROUP's absence (not just the `E` radio's) is what makes this the real
    // AC-A2 check: offering the reasons on every step must NOT start asking for one where nothing is
    // short.
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.expectQtyRejectedReasonsGroupVisible({ visible: false });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: false });

    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Expect: unchanged from before this feature — 0.5 issued, the HU stays ACTIVE with its 0.002 KGM
    // remainder and nothing carries the write-off description.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '0.002 KGM' },
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

// noinspection JSUnusedLocalSymbols
test('AC-A3: a non-emptied reason on a bigger HU is accepted and leaves the HU alone', async ({ page }) => {
    // The reasons being offered on every step means "not found" / "damaged" now travel to the backend
    // from a step that takes only PART of its HU -- a combination the server never saw from this screen
    // before (computeIssueRequest.js used to zero `qtyRejected` / null the reason code there). This test
    // is that path's coverage, and the counterpart of AC-A1: same fixture, same shortfall, a reason that
    // must NOT touch the HU.
    const masterdata = await createManufacturingMasterdata({ huQty: 0.502, orderQty: 0.5 });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.49');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Expect: 0.49 issued and NOTHING else -- the HU keeps its 0.012 KGM and stays active, and no
    // write-off inventory is created (only the "empty" reason books one).
    //
    // The `qtyRejected`/reason-code pair itself is verified only indirectly, by the backend ACCEPTING
    // the request (a rejected qty the server refused would surface as an error toast and leave the
    // dialog open, failing `RawMaterialIssueLineScreen.waitForScreen()` above): the masterdata harness
    // has no expectation for the issue schedule's own qtyRejected -- pending question #20.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '0.012 KGM' },
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
