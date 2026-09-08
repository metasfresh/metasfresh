import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { YesNoDialog } from '../../utils/dialogs/YesNoDialog';

/**
 * Empty HU write-off: the reason "empty (auto. inventory)" on a raw-materials issue step books the
 * source HU's remaining quantity to zero via a completed inventory document.
 *
 * TC1 (core case), TC2 (decline / untick-confirm) and TC3 (dregs case) from REQUIREMENTS.md §5.
 */

const EMPTIED_REASON = 'E';
const EMPTIED_REASON_CAPTION = 'empty (auto. inventory)';

/**
 * Same masterdata shape as manufacturing_small_qty_tolerance.spec.js (same customer, same screen):
 * a KGM component with no packing instruction, a single-line BOM, one standalone HU, one manufacturing
 * order — sized so the HU's own content is the binding constraint (qtyHUCapacity <= the line's
 * remaining need). That is what makes the qty-rejected-reason radio group reachable at all — the
 * mobile UI shows it only once the entered qty falls short of the target (GetQuantityDialog.jsx:
 * `{qtyRejected > 0 && <QtyReasonsRadioGroup .../>}`), and the target is only capped by the HU's own
 * content (not the order's need) when qtyHUCapacity <= the remaining need.
 *
 * `isConfirmEmptyingHU` / `isAllowEmptyingHUs` are CLIENT-level config (MobileUI_MFG_Config), not
 * per-user, so every test sets them explicitly on its own masterdata request rather than relying on
 * another test's value or run order (mobile-webui/CLAUDE.md "Debugging Flaky Tests" — sticky config).
 */
const createMasterdata = async ({ huQty, orderQty, isConfirmEmptyingHU = true }) => {
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

// noinspection JSUnusedLocalSymbols
test('TC1: Clear the leftover on a gas-bottle TU — the core case', async ({ page }) => {
    const masterdata = await createMasterdata({ huQty: 0.5, orderQty: 0.5, isConfirmEmptyingHU: true });

    await startIssueStep(masterdata);

    // Issue slightly less than the HU's booked quantity (0.498 of 0.5 KGM) — the qty-rejected-reason
    // radio group (and with it "empty (auto. inventory)") only appears once a shortfall is entered.
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');

    // Expect: "empty (auto. inventory)" is offered, and its caption states its effect.
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.expectQtyNotFoundReasonCaption({ reason: EMPTIED_REASON, caption: EMPTIED_REASON_CAPTION });

    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    // Expect: a confirmation prompt naming the leftover quantity (0.5 - 0.498 KGM = ~2 g) and its
    // UOM. Regex, not a literal string: 0.5 - 0.498 is not exact in IEEE-754 double, so the app
    // (correctly) shows the tiny binary remainder (e.g. "2.0000000000000018 g").
    await YesNoDialog.waitForDialog();
    await YesNoDialog.expectPromptContains(/remaining 2(\.\d+)? g/);
    await YesNoDialog.clickYesButton();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Expect via Backend.expect: the HU's quantity is zero (closed → Destroyed) and a completed
    // inventory document exists for it whose Description matches the AD_Message text in the system
    // base language (de_DE — see PPOrderIssueScheduleService#bookEmptiedHUToZero). No empties
    // movement is asserted: this HU has no packing material.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
            },
        },
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: true,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});

test('TC2: Declining the confirmation changes nothing', async ({ page }) => {
    const masterdata = await createMasterdata({ huQty: 0.5, orderQty: 0.5, isConfirmEmptyingHU: true });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    await YesNoDialog.waitForDialog();
    await YesNoDialog.clickNoButton();

    // Expect: no inventory document; HU quantity untouched; operator back on the step, free to
    // choose another reason — the qty dialog is back (not closed, not yet submitted), reason still
    // selectable.
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });

    // The masterdata harness itself stocks a fresh HU via its own (completed) inventory count, so a
    // bare "an inventory document exists" is never discriminating here. The stock staying at the
    // full booked 0.5 KGM (not zero) is: declining genuinely aborted the booking — a completed
    // write-off would have brought it to zero, exactly as TC1/TC3 assert.
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'A',
                storages: { COMP: '0.5 KGM' },
            },
        },
    });
});

test('TC2: Unticking the confirm flag skips the prompt', async ({ page }) => {
    const masterdata = await createMasterdata({ huQty: 0.5, orderQty: 0.5, isConfirmEmptyingHU: false });

    await startIssueStep(masterdata);

    // Expect: no prompt; HU cleared directly.
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
            },
        },
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: true,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC3: An already-nearly-empty HU behaves the same', async ({ page }) => {
    // The dregs path is the common one and must not be treated as special or suspicious: the HU is
    // booked at a small fraction of a full one, and most of what remains is issued.
    const masterdata = await createMasterdata({ huQty: 0.003, orderQty: 0.003, isConfirmEmptyingHU: true });

    await startIssueStep(masterdata);

    await GetQuantityDialog.expectQtyEntered('0.003');
    await GetQuantityDialog.typeQtyEntered('0.002');
    await GetQuantityDialog.expectQtyNotFoundReasonOffered({ reason: EMPTIED_REASON, offered: true });
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });
    await GetQuantityDialog.clickDone();

    await YesNoDialog.waitForDialog();
    await YesNoDialog.expectPromptContains(/remaining 1(\.\d+)? g/);
    await YesNoDialog.clickYesButton();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU.qrCode]: {
                huStatus: 'D',
            },
        },
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: true,
                docStatus: 'CO',
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});
