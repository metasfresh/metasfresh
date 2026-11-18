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
 * HU's content, so the older HU alone covers the demand.
 */
const createMasterdata = async ({ olderHuQty, newerHuQty, orderQty }) => {
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
                BOM: { bom: { lines: [{ product: 'COMP', qty: orderQty, uom: 'KGM' }] } },
            },
            handlingUnits: {
                // Created first -> lower HuId -> the plan's FEFO-default step (covers the whole demand).
                HU_OLDER: { product: 'COMP', warehouse: 'wh', qty: olderHuQty },
                // Created second -> the newer HU the operator wants to use up / empty.
                HU_NEWER: { product: 'COMP', warehouse: 'wh', qty: newerHuQty },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

const startIssueStepAndScanNewerHU = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.openScanScreen();
    // Scan the NEWER HU — the one the operator wants to use, which is NOT the FEFO default.
    await RawMaterialIssueLineScanScreen.typeQRCode(masterdata.handlingUnits.HU_NEWER.qrCode);
    await GetQuantityDialog.waitForDialog();
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
