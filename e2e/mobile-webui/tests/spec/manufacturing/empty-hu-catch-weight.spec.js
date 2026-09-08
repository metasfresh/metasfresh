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
 *  - TC-CW3 (negative): kg, whole-HU, NO rejection reason -> a weight-confirm inventory exists, but
 *    WITHOUT the AC4 description (nothing was written off).
 *
 * TC-CW2 (kg, entered short, reason E) — NOT IMPLEMENTED. BLOCKED on a genuine product defect found
 * while building it (`.superpowers/sdd/PLAN/task-16e-report.md` has the DB-row evidence). On a bare
 * VHU (no packing instruction — the same fixture shape as empty-hu-core.spec.js's core case, only
 * with `uom: 'KGM'`), entering 0.498 of a 0.5 KGM HU with reason E produces THREE inventory lines for
 * the HU, not the two (seed + one write-off) TC1/TC3 show:
 *   1. the masterdata harness's own seed inventory (QtyBook=0 -> QtyCount=0.5, undescribed)
 *   2. `weightHU`'s weight-confirmation inventory (QtyBook=0.5 -> QtyCount=0.498) — carries the AC4
 *      description (`PPOrderIssueScheduleService.issue` passes `emptiedHUInventoryDescription` into
 *      `weightHU` whenever the EMPTIED reason is set, per commit 50c3092416e)
 *   3. `bookEmptiedHUToZero`'s own inventory (QtyBook=0.498 -> QtyCount=0) — ALSO carries the SAME
 *      AC4 description
 * i.e. TWO inventories both carry the write-off description, where TC11 (empty-hu-refusals.spec.js,
 * a PI-produced weight-tracked TU) shows only ONE — `bookEmptiedHUToZero` there no-ops because the
 * ordinary "qty issued" step already drained the HU to zero before it runs. On this bare-VHU fixture
 * the HU's storage is still at 0.498 (not empty) when `bookEmptiedHUToZero` runs, so its no-op guard
 * (`huStorage.getProductStorages().isEmpty()`) does not fire and it books a SECOND, redundant,
 * identically-described write-off. Smallest proposed fixes (not applied here — production-behaviour
 * decision, out of this task's scope): (a) suppress the description on `bookEmptiedHUToZero`'s call
 * when `weightHU` already ran for this issue (the weight-confirmation is the real write-off; the
 * zero-out that follows it is bookkeeping, not a second write-off) — the smaller, more targeted
 * change; or (b) find out why the ordinary "qty issued" step does not drain a bare VHU's storage the
 * way it does a PI-produced TU's, and fix that so `bookEmptiedHUToZero`'s existing no-op guard covers
 * this fixture too, as it already does TC11's.
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
test('TC-CW3: a kg whole-HU issue with no rejection reason leaves the weight-confirm inventory undescribed', async ({ page }) => {
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

    // An inventory exists (the weight-confirm one)...
    await Backend.expect({
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: { isExists: true },
        },
    });
    // ...but none of them carries the AC4 write-off description (nothing was written off).
    await Backend.expect({
        inventories: {
            [masterdata.handlingUnits.HU.qrCode]: {
                isExists: false,
                description: emptiedHUInventoryDescription(masterdata.manufacturingOrders.PP1.documentNo),
            },
        },
    });
});
