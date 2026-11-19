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
 * Raw-material issue — using a SECOND active HU of the same product when a first (older) HU already
 * covers the whole line demand.
 *
 * A product's raw-material issue can draw from more than one active HU of that product in the source
 * warehouse. The plan suggests the HUs in FEFO order (earliest best-before first, then lowest HuId), and
 * when the first (older) HU already covers the BOM line's whole demand, every further HU of that product
 * is planned as a zero-quantity ALTERNATIVE step (`PPOrderIssuePlanCreateCommand.createAndCollectSteps`:
 * `.qtyToIssue(targetQty.toZero())`).
 *
 * FEFO is only the DEFAULT suggestion: the operator must still be able to pick up the second (newer) HU
 * and use it — issue from it toward the line (the demand reallocating away from the FEFO-preferred HU, the
 * total never exceeding the line demand) and/or declare it empty — exactly as they can when it is the only
 * active HU. The zero-target alternative step currently locks the quantity field at 0, so the newer HU can
 * be neither issued from nor emptied; that lock is what these tests pin.
 *
 * Fixture: two independent standalone HUs of one KGM component in the source warehouse — older/larger
 * (created first, the FEFO default) + newer/smaller — with the BOM-line demand set BELOW the older HU's
 * content, so the older HU alone covers it and the newer HU is the zero-target alternative. Config as the
 * feature ships: isAllowEmptyingHUs=Y, isConfirmEmptyingHU=Y, isAllowIssuingAnyHU=N (the newer HU is a
 * PLANNED candidate, not an on-the-fly "any HU").
 *
 * The regression tests at the end of this file pin what the unlock must NOT change: the DEFAULT suggestion
 * (which HU gets the pre-filled, non-zero step) and emptying the PRIMARY HU while a second HU exists — also
 * when the primary HU holds LESS than the line demand and the second HU covers the rest.
 */

const EMPTIED_REASON = 'E';
const EMPTIED_REASON_CAPTION = 'empty (auto. inventory)';

// The AD_Message text (system base language de_DE) that PPOrderIssueScheduleService#bookEmptiedHUToZero
// writes as the write-off inventory's Description — same helper as the sibling emptyHU specs.
const emptiedHUInventoryDescription = (documentNo) => `Bei Materialzuteilung zu ${documentNo} geleert`;

/**
 * Two independent standalone HUs of the SAME component (no packing instruction), older/larger created
 * first (lower HuId -> the plan's FEFO default under the reserved-first / expiring-first / lowest-HuId
 * allocation order) and newer/smaller created second. The order needs `orderQty` KGM, below the older
 * HU's content, so the older HU alone covers the demand (the last test deviates: there the older HU holds
 * LESS than the demand and the newer one covers the rest). The issuing tolerance defaults to none;
 * `issuingTolerancePerc` is set only to mirror a real-life BOM line.
 */
const createMasterdata = async ({ olderHuQty, newerHuQty, orderQty, olderBestBeforeDate, newerBestBeforeDate, issuingTolerancePerc }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: {
                    isAllowEmptyingHUs: true,
                    isConfirmEmptyingHU: true,
                    isAllowIssuingAnyHU: false,
                },
            },
            uoms: { KGM: { precision: 5 } },
            warehouses: { wh: {} },
            products: {
                COMP: { uom: 'KGM' },
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM', issuingTolerancePerc }] } },
            },
            handlingUnits: {
                // Created first -> lower HuId -> the plan's FEFO-default step (covers the whole demand).
                // An optional best-before date (MHD) ranks BEFORE the HuId in the allocation order.
                HU_OLDER: { product: 'COMP', warehouse: 'wh', qty: olderHuQty, ...bestBeforeDateProp(olderBestBeforeDate) },
                // Created second -> the newer HU the operator wants to use up / empty.
                HU_NEWER: { product: 'COMP', warehouse: 'wh', qty: newerHuQty, ...bestBeforeDateProp(newerBestBeforeDate) },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

const bestBeforeDateProp = (bestBeforeDate) => (bestBeforeDate ? { bestBeforeDate } : {});

const openIssueLine = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
};

const scanHU = async (huQRCode) => {
    await RawMaterialIssueLineScreen.openScanScreen();
    await RawMaterialIssueLineScanScreen.typeQRCode(huQRCode);
    await GetQuantityDialog.waitForDialog();
};

const startIssueStepAndScanNewerHU = async (masterdata) => {
    await openIssueLine(masterdata);
    // Scan the NEWER HU — the one the operator wants to use, which is NOT the FEFO default.
    await scanHU(masterdata.handlingUnits.HU_NEWER.qrCode);
};

// noinspection JSUnusedLocalSymbols
test('A newer HU can be ISSUED while an older HU is the FEFO default', async ({ page }) => {
    // Older 520.83 KGM (FEFO default), newer 0.1 KGM. Demand 50 KGM < 520.83, so the older HU alone covers
    // it and the newer HU is the zero-target alternative step.
    const masterdata = await createMasterdata({ olderHuQty: 520.83, newerHuQty: 0.1, orderQty: 50 });

    await startIssueStepAndScanNewerHU(masterdata);

    // FEFO only SUGGESTS the older HU; the operator may use up the newer HU instead. Scanning it opens a
    // usable step whose target is the newer HU's own content (0.1), capped by the line's remaining demand
    // (50). This is the regression guard: a non-FEFO HU used to get a zero-target alternative step whose
    // quantity field was locked at 0, so nothing could be issued from it.
    await GetQuantityDialog.expectQtyEntered('0.1');

    // Issue the whole newer HU toward the line (take exactly its content, so no reject reason is involved).
    await GetQuantityDialog.typeQtyEntered('0.1');
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // DESIRED: the newer HU's 0.1 kg is issued toward the line (reallocated from the FEFO default); the line
    // shows 0.1 kg (displayed as 100 g) of its 50 kg demand issued, the newer HU is consumed (Destroyed), the
    // older HU is untouched — the total issued never exceeds the demand (no over-issue).
    await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '50 kg', qtyIssued: '100 g' });

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU_NEWER.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
            [masterdata.handlingUnits.HU_OLDER.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '520.83 KGM' },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('A newer HU can be EMPTIED while an older HU is the FEFO default', async ({ page }) => {
    const masterdata = await createMasterdata({ olderHuQty: 520.83, newerHuQty: 0.1, orderQty: 50 });

    await startIssueStepAndScanNewerHU(masterdata);

    // Same as the issue case: the dialog opens at the newer HU's own content (0.1) so the operator can
    // account for it (before the fix it opened at the alternative step's zero target).
    await GetQuantityDialog.expectQtyEntered('0.1');

    // The operator uses most of the newer HU (0.08 KGM) and declares the rest empty: entering less than the
    // 0.1 target leaves a shortfall, which renders the reject-reason group with "empty (auto. inventory)".
    await GetQuantityDialog.typeQtyEntered('0.08');
    await GetQuantityDialog.expectQtyRejectedReasonsGroupVisible({ visible: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: EMPTIED_REASON, caption: EMPTIED_REASON_CAPTION });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_NOT_FOUND, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: QTY_NOT_FOUND_REASON_DAMAGED, offered: true });

    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    // The confirmation prompt names the newer HU's remaining 0.02 KGM (20 g) about to be written off (its
    // content 0.1 minus the 0.08 just issued).
    await YesNoDialog.waitForDialog();
    await YesNoDialog.expectPromptContains('remaining 20 g');
    await YesNoDialog.clickYesButton();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // The 0.08 kg the operator entered reached the line (displayed as 80 g of the 50 kg demand) — the issued
    // part is reallocated to the newer HU, no over-issue.
    await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '50 kg', qtyIssued: '80 g' });

    // The NEWER HU is consumed: 0.08 issued toward the line, the remaining 0.02 written off to zero via a
    // completed inventory doc, and the HU closed (Destroyed); the OLDER HU is untouched and still Active with
    // its full content.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU_NEWER.qrCode]: {
                huStatus: 'D',
                storages: { COMP: '0 KGM' },
            },
            [masterdata.handlingUnits.HU_OLDER.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '520.83 KGM' },
            },
        },
        inventories: {
            [masterdata.handlingUnits.HU_NEWER.qrCode]: {
                count: 1,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
                qtyBook: 0.02,
                qtyCount: 0,
            },
        },
    });
});

// ===== Regression: what the unlock of the alternative step must NOT change =====

/**
 * The plan's DEFAULT suggestion, before the operator scans anything: exactly one step per HU, the primary
 * (pre-filled) step carries the whole line demand, the other HU's step is the zero-target alternative.
 */
const expectDefaultSuggestion = async ({ primaryHU, alternativeHU, demand }) => {
    await RawMaterialIssueLineScreen.expectStepsCount({ count: 2 });
    await RawMaterialIssueLineScreen.expectStepForHU({ huQRCode: primaryHU.qrCode, qtyToIssue: demand, qtyIssued: '0 kg' });
    await RawMaterialIssueLineScreen.expectStepForHU({ huQRCode: alternativeHU.qrCode, qtyToIssue: '0 kg', qtyIssued: '0 kg' });
};

// noinspection JSUnusedLocalSymbols
test('Default unchanged: the pre-filled step is on the EARLIEST-EXPIRING HU, and issuing from it works as before', async ({ page }) => {
    // Both HUs can cover the 50 KGM demand on their own. The NEWER HU (higher HuId) expires first, so the
    // best-before date — not the HuId — decides the primary: the newer HU is suggested, the older one is the
    // zero-target alternative.
    const masterdata = await createMasterdata({
        olderHuQty: 80, olderBestBeforeDate: '2027-12-17',
        newerHuQty: 60, newerBestBeforeDate: '2027-06-30',
        orderQty: 50,
    });
    const primaryHU = masterdata.handlingUnits.HU_NEWER;
    const alternativeHU = masterdata.handlingUnits.HU_OLDER;

    await openIssueLine(masterdata);
    await expectDefaultSuggestion({ primaryHU, alternativeHU, demand: '50 kg' });

    // The operator follows the suggestion: scans the suggested HU and confirms the pre-filled demand.
    await scanHU(primaryHU.qrCode);
    await GetQuantityDialog.expectQtyEntered('50');
    await GetQuantityDialog.expectQtyRejectedReasonsGroupVisible({ visible: false });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.expectStepForHU({ huQRCode: primaryHU.qrCode, qtyToIssue: '50 kg', qtyIssued: '50 kg' });
    await RawMaterialIssueLineScreen.goBack();

    // The whole demand is issued from the suggested HU (60 - 50 = 10 left, still Active); the other HU is untouched.
    await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '50 kg', qtyIssued: '50 kg' });
    await Backend.expect({
        hus: {
            [primaryHU.qrCode]: { huStatus: 'A', storages: { COMP: '10 KGM' } },
            [alternativeHU.qrCode]: { huStatus: 'A', storages: { COMP: '80 KGM' } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Default unchanged: with equal best-before dates the pre-filled step is on the LOWEST HuId', async ({ page }) => {
    // Same best-before date on both HUs -> the tie is broken by the HuId: the older HU (created first) is the primary.
    const masterdata = await createMasterdata({
        olderHuQty: 80, olderBestBeforeDate: '2027-12-17',
        newerHuQty: 60, newerBestBeforeDate: '2027-12-17',
        orderQty: 50,
    });
    const primaryHU = masterdata.handlingUnits.HU_OLDER;
    const alternativeHU = masterdata.handlingUnits.HU_NEWER;

    await openIssueLine(masterdata);
    await expectDefaultSuggestion({ primaryHU, alternativeHU, demand: '50 kg' });

    await scanHU(primaryHU.qrCode);
    await GetQuantityDialog.expectQtyEntered('50');
    await GetQuantityDialog.expectQtyRejectedReasonsGroupVisible({ visible: false });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '50 kg', qtyIssued: '50 kg' });
    await Backend.expect({
        hus: {
            [primaryHU.qrCode]: { huStatus: 'A', storages: { COMP: '30 KGM' } },
            [alternativeHU.qrCode]: { huStatus: 'A', storages: { COMP: '60 KGM' } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('The PRIMARY HU can still be EMPTIED while a second HU exists', async ({ page }) => {
    // Older 52 KGM (the primary — created first, no best-before date on either HU), newer 10 KGM (the
    // alternative). Demand 50 KGM.
    const masterdata = await createMasterdata({ olderHuQty: 52, newerHuQty: 10, orderQty: 50 });
    const primaryHU = masterdata.handlingUnits.HU_OLDER;
    const alternativeHU = masterdata.handlingUnits.HU_NEWER;

    await openIssueLine(masterdata);
    await expectDefaultSuggestion({ primaryHU, alternativeHU, demand: '50 kg' });

    // The operator scans the PRIMARY HU: its step keeps its planned target (the line demand 50), NOT the HU's
    // own 52 KGM content — only a zero-target alternative step is lifted to its HU's content.
    await scanHU(primaryHU.qrCode);
    await GetQuantityDialog.expectQtyEntered('50');

    // They take 49.5 KGM and declare the HU empty.
    await GetQuantityDialog.typeQtyEntered('49.5');
    await GetQuantityDialog.expectQtyRejectedReasonsGroupVisible({ visible: true });
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: EMPTIED_REASON, caption: EMPTIED_REASON_CAPTION });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    // The prompt names the primary HU's real remainder: 52 - 49.5 = 2.5 KGM.
    await YesNoDialog.waitForDialog();
    await YesNoDialog.expectPromptContains('remaining 2.5 kg');
    await YesNoDialog.clickYesButton();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // The 49.5 KGM reached the line; the line demand is still 50 KGM (no change to the line's demand).
    await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '50 kg', qtyIssued: '49.5 kg' });

    // The primary HU is consumed: 49.5 issued, the remaining 2.5 written off to zero via a completed inventory
    // doc, and the HU closed (Destroyed). The second HU is untouched: still Active with its full 10 KGM, and no
    // write-off inventory for it.
    await Backend.expect({
        hus: {
            [primaryHU.qrCode]: { huStatus: 'D', storages: { COMP: '0 KGM' } },
            [alternativeHU.qrCode]: { huStatus: 'A', storages: { COMP: '10 KGM' } },
        },
        inventories: {
            [primaryHU.qrCode]: {
                count: 1,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
                qtyBook: 2.5,
                qtyCount: 0,
            },
            [alternativeHU.qrCode]: {
                isExists: false,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Two HUs, first 4.9498 kg on a 5 kg line: 4.5 kg issued, rest 449.8 g written off, second HU untouched', async ({ page }) => {
    // Real-life plan shape: the line needs 5 KGM (1 % issuing tolerance, as on the real BOM line). The older HU
    // (created first -> lowest HuId, no best-before date on either HU) holds only 4.9498 KGM, so the plan takes
    // it WHOLE as the first step (target 4.9498) and the newer 10 KGM HU covers the missing 0.0502 KGM as the
    // second step (`PPOrderIssuePlanCreateCommand.createAndCollectSteps`, cases 3 and 2). The plan needs the
    // full 5 KGM allocated at job start — the tolerance does not lower that — hence the second HU.
    const masterdata = await createMasterdata({ olderHuQty: 4.9498, newerHuQty: 10, orderQty: 5, issuingTolerancePerc: 1 });
    const firstHU = masterdata.handlingUnits.HU_OLDER;
    const secondHU = masterdata.handlingUnits.HU_NEWER;

    await openIssueLine(masterdata);

    // Expect: the plan described above — the first HU's step is its whole 4.9498 kg, the second covers 50.2 g.
    await RawMaterialIssueLineScreen.expectStepsCount({ count: 2 });
    await RawMaterialIssueLineScreen.expectStepForHU({ huQRCode: firstHU.qrCode, qtyToIssue: '4.9498 kg', qtyIssued: '0 kg' });
    await RawMaterialIssueLineScreen.expectStepForHU({ huQRCode: secondHU.qrCode, qtyToIssue: '50.2 g', qtyIssued: '0 kg' });

    // The operator scans the first HU: the dialog opens at its whole content, 4.9498 kg.
    await scanHU(firstHU.qrCode);
    await GetQuantityDialog.expectQtyEntered('4.9498');

    // They take 4.5 kg and declare the HU empty.
    await GetQuantityDialog.typeQtyEntered('4.5');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: EMPTIED_REASON, caption: EMPTIED_REASON_CAPTION });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    // Expect: the prompt names the first HU's remainder 4.9498 - 4.5 = 449.8 g — not the 500 g order-side
    // shortfall (5 - 4.5).
    await YesNoDialog.waitForDialog();
    await YesNoDialog.expectPromptContains('This will write off the remaining 449.8 g and empty the HU. Continue?');
    await YesNoDialog.clickYesButton();

    // Expect: the 4.5 kg came from the first HU; nothing was issued from the second.
    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.expectStepForHU({ huQRCode: firstHU.qrCode, qtyIssued: '4.5 kg' });
    await RawMaterialIssueLineScreen.expectStepForHU({ huQRCode: secondHU.qrCode, qtyIssued: '0 kg' });
    await RawMaterialIssueLineScreen.goBack();

    // Expect: 4.5 kg of the 5 kg demand issued.
    await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 kg', qtyIssued: '4.5 kg' });

    // Expect: the first HU is closed (Destroyed) with nothing left, and exactly one completed write-off inventory
    // booked its 0.4498 KGM remainder down to 0. The second HU is untouched: still Active with its full 10 KGM and
    // no write-off inventory.
    await Backend.expect({
        hus: {
            [firstHU.qrCode]: { huStatus: 'D', storages: { COMP: '0 KGM' } },
            [secondHU.qrCode]: { huStatus: 'A', storages: { COMP: '10 KGM' } },
        },
        inventories: {
            [firstHU.qrCode]: {
                count: 1,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
                qtyBook: 0.4498,
                qtyCount: 0,
            },
            [secondHU.qrCode]: {
                isExists: false,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});
