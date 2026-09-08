import { expect } from '@playwright/test';
import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { RawMaterialIssueLineScanScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';

/**
 * Empty HU write-off — the catch-weight gate on `RawMaterialIssueStepScanComponent.jsx`
 * (`isWeightable && isIssueWholeHU && uom === 'kg'` -> `huWeightGrossBeforeIssue`), PLAN.md Task 16e.
 *
 * Both cases below exercise a weightable component (isWeightable=true) whose step is whole-HU-offered
 * (isIssueWholeHU=true) — only `uom` (the entered UOM's symbol) differs:
 *  - TC-CW1: entered in a non-kg WEIGHT uom (Gramm/GRM — weight-typed, so `isWeightable` stays true,
 *    isolating the `uom === 'kg'` half of the gate) -> `huWeightGrossBeforeIssue` is null on the wire,
 *    and exactly one inventory carries the AC4 write-off description.
 *  - TC-CW2: kg, whole-HU, entered short with reason E -> the "complete cuHU" issue branch consumes
 *    the HU whole (HUStatus -> Issued) without touching its storage row, so `bookEmptiedHUToZero`
 *    must not book a second write-off against an HU the issue already consumed; exactly one inventory
 *    carries the AC4 description.
 *  - TC-CW3 (negative): kg, whole-HU, NO rejection reason, entered qty == the HU's already-recorded
 *    qty -> the weight-confirm is a zero-delta no-op (creates no inventory of its own; only the
 *    harness's seed inventory references the HU) and, in particular, nothing carries the AC4
 *    description (nothing was written off).
 */

const EMPTIED_REASON = 'E';
const MANUFACTURING_EVENT_URL_FRAGMENT = '/manufacturing/event';

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

/**
 * A weightable component on a whole-HU-offered step — same shape as empty-hu-core.spec.js's core
 * case (a bare VHU, no packing instruction, sized so the HU's own content is the binding constraint),
 * parametrized only by the component's own UOM.
 */
const createMasterdata = async ({ uom }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: { isAllowEmptyingHUs: true, isConfirmEmptyingHU: false },
            },
            ...(uom === 'KGM' ? { uoms: { KGM: { precision: 5 } } } : {}),
            warehouses: { wh: {} },
            products: {
                COMP: { uom },
                BOM: { bom: { lines: [{ product: 'COMP', qty: 0.5, uom }] } },
            },
            handlingUnits: {
                HU: { product: 'COMP', warehouse: 'wh', qty: 0.5 },
            },
            manufacturingOrders: {
                PP1: { warehouse: 'wh', product: 'BOM', qty: 1, datePromised: '2026-03-30T00:00:00.000+02:00' },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('TC-CW1: a non-kg weight UOM entered short does not send the weight', async ({ page }) => {
    // Gramm (GRM) is WEIGHT-typed (same UOMType as Kilogramm) so `isWeightable` stays true — only its
    // uom SYMBOL ('GRM', not 'kg') differs, isolating the `uom === 'kg'` half of the gate.
    const masterdata = await createMasterdata({ uom: 'GRM' });

    await startIssueStep(masterdata);
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.48');
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });

    const requestBody = await GetQuantityDialog.clickDoneAndCaptureRequestBody({ urlFragment: MANUFACTURING_EVENT_URL_FRAGMENT });
    expect(requestBody.issueTo.huWeightGrossBeforeIssue).toBeNull();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    await Backend.expect({
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                count: 1,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC-CW2: a kg whole-HU issue entered short with the emptied reason books exactly one write-off', async ({ page }) => {
    const masterdata = await createMasterdata({ uom: 'KGM' });

    await startIssueStep(masterdata);
    await GetQuantityDialog.expectQtyEntered('0.5');
    await GetQuantityDialog.typeQtyEntered('0.498');
    await GetQuantityDialog.clickQtyNotFoundReason({ reason: EMPTIED_REASON });

    await GetQuantityDialog.clickDoneAndCaptureRequestBody({ urlFragment: MANUFACTURING_EVENT_URL_FRAGMENT });

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // `count: 1` with a `description` filter pins DESCRIBED inventories only: an extra UNdescribed
    // write-off alongside it would not be caught here, but AC4 is specifically about the described document.
    await Backend.expect({
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                count: 1,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC-CW3: a kg whole-HU issue without a reason creates no write-off and no weight-confirm inventory', async ({ page }) => {
    const masterdata = await createMasterdata({ uom: 'KGM' });

    await startIssueStep(masterdata);
    await GetQuantityDialog.expectQtyEntered('0.5');
    // The full booked qty, no shortfall -> no reason offered/selected; `isIssueWholeHU` is unaffected
    // (it is fixed at offer time from capacity, not from what gets typed), so the weight is still sent.
    await GetQuantityDialog.typeQtyEntered('0.5');

    const requestBody = await GetQuantityDialog.clickDoneAndCaptureRequestBody({ urlFragment: MANUFACTURING_EVENT_URL_FRAGMENT });
    expect(requestBody.issueTo.huWeightGrossBeforeIssue).not.toBeNull();

    await RawMaterialIssueLineScreen.waitForScreen();
    await RawMaterialIssueLineScreen.goBack();

    // Entering the full booked qty leaves the counted weight equal to the HU's already-recorded qty
    // (0.5 -> 0.5), so `weightHU`'s own qty update is a zero-delta no-op and creates no inventory line
    // of its own — only the harness's seed inventory references the HU, and no write-off is booked
    // either (no rejection reason was given). `count: 1` without a `description` filter pins that exact
    // total, unlike a bare `isExists: true` check (which the seed inventory alone would already satisfy
    // even if an extra, unwanted inventory had been created).
    await Backend.expect({
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: { count: 1 },
        },
    });
    // ...and none of them carries the AC4 write-off description (confirms no write-off was created).
    await Backend.expect({
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: false,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});
