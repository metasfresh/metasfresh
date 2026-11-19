/**
 * TL;DR — In HU Consolidation, the worker scans the GRAI of each crate directly on the job screen
 * ("Scan (GRAI)" button), no matter which picking slot the crate is in, instead of opening every
 * picking slot first. Scanned crates go onto the open pallet; Fertigstellen prints the pallet label
 * and ships.
 *
 * TC2 (rejected scans + TOAST repro, BS-22): every rejected scan on the job screen shows exactly one error
 * toast with the right text, moves nothing and leaves the scanner armed (see the TC2 test for the sequence).
 *
 * Real-life flow (TC1, the reference case):
 *   1. Three crates (2 packing instructions, 3 distinct GRAIs) are picked with a GRAI scan into 2 picking slots.
 *   2. The worker opens HU Consolidation, taps the ship-to, chooses a new pallet ("Neues Gebinde" / "New LU").
 *   3. Taps "Scan (GRAI)" and scans the three crates (dot form, GS1 8003 form, dot form).
 *      Each accepted scan: crate onto the pallet, its slot count drops by 1, pallet caption shows the pallet QR.
 *   4. Fertigstellen -> Ja: the ship-to disappears from the job list; the pallet holds the 3 crates,
 *      has an SSCC18, belongs to the partner, its shipment is completed and one label was archived.
 */

import { test } from '../../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickGraiScreen } from '../../utils/screens/picking/PickGraiScreen';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { ErrorToast } from '../../utils/dialogs/ErrorToast';
import { page, expectErrorToast } from '../../utils/common';
import { createJobScanMasterdata } from '../../utils/huConsolidationJobScanMasterdata';

/** GS1 mod-10 check digit of a digit string (weights 3,1,3,... from the right). */
const gs1CheckDigit = (digits) => {
    let sum = 0;
    for (let i = 0; i < digits.length; i++) {
        const digit = Number(digits[digits.length - 1 - i]);
        sum += digit * (i % 2 === 0 ? 3 : 1);
    }
    return (10 - (sum % 10)) % 10;
};

/** Canonical GRAI (companyPrefix.assetType.serial) of the PI's GRAI mapping, with the given serial. */
const graiWithSerial = (piGrai, serial) => {
    const [companyPrefix, assetType] = piGrai.split('.');
    return `${companyPrefix}.${assetType}.${serial}`;
};

/** The same GRAI as GS1 AI 8003 barcode: 8003 + padding 0 + companyPrefix + assetType + check digit + serial. */
const graiAs8003 = (canonicalGrai) => {
    const [companyPrefix, assetType, serial] = canonicalGrai.split('.');
    const assetReference = `0${companyPrefix}${assetType}`;
    return `8003${assetReference}${gs1CheckDigit(assetReference)}${serial}`;
};

/**
 * Picks one GRAI-stamped top-level TU for a single-line picking job into the given picking slot
 * (same flow as hu_consolidation_grai_cross_slot.spec.js: slot scan, line, TU target GRAI scan, HU scan, qty, complete).
 */
const pickOneGraiCrate = async ({ documentNo, slotQrCode, grai, huQrCode }) => await test.step(`Pick crate ${grai} of ${documentNo} into its slot`, async () => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: slotQrCode });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
    await PickingGraiScanPanel.scanGrai({ graiString: grai });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(huQrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();
    await PickingJobsListScreen.goBack();
});

// noinspection JSUnusedLocalSymbols
test('TC1 reference case: scan 3 crates from 2 slots on the job screen, Fertigstellen', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    // GS1 manufacturer code + extension digit for the SSCC18 of the pallet
    await Backend.setSysconfigs({
        'de.metas.handlingunit.GS1ManufacturerCode': '1234567',
        'de.metas.handlingunit.GS1ExtensionDigit': '0',
    });

    const masterdata = await createJobScanMasterdata();
    const { slot1, slot2 } = masterdata.pickingSlots;
    const { SO1, SO2, SO3 } = masterdata.salesOrders;

    // 3 distinct GRAIs of 2 PIs (serial minted per crate)
    const serialBase = String(Date.now());
    const grai1 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}1`);
    const grai2 = graiWithSerial(masterdata.packingInstructions.PI_P2.grai, `${serialBase}2`);
    const grai3 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}3`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: grai1, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai: grai2, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot2.qrCode, grai: grai3, huQrCode: masterdata.handlingUnits.HU1.qrCode });

    const customerLocationId = masterdata.bpartners.BP1.bpartnerLocationId;
    let targetLUQRCode;

    await test.step('Open the ship-to job and choose a new pallet', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
    });

    await test.step('Scan (GRAI): 3 crates from 2 slots', async () => {
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await HUConsolidationJobScreen.expectScannerVisible(true);

        // scan 1: canonical dot form
        await HUConsolidationJobScreen.scanGRAI({ grai: grai1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        // after the first crate the pallet is a real LU: its caption shows the pallet QR, no longer the pallet type
        await HUConsolidationJobScreen.expectTargetLUCaptionNotContains({ text: masterdata.packingInstructions.PI_P1.luName });
        targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();

        // scan 2: GS1 AI 8003 form (plain barcode)
        await HUConsolidationJobScreen.scanGRAI({ grai: graiAs8003(grai2) });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });

        // scan 3: other slot
        await HUConsolidationJobScreen.scanGRAI({ grai: grai3 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 0 });
    });

    await test.step('Fertigstellen -> Ja: back on the job list, ship-to gone', async () => {
        await HUConsolidationJobScreen.complete();
        await expect(page.locator(`.wflauncher-button[data-bpartnerlocationid="${customerLocationId}"]`)).toHaveCount(0);
    });

    await Backend.expect({
        title: 'TC1: pallet holds the 3 crates, SSCC18 set, partner BP1, shipment completed, 1 label archived',
        hus: {
            [targetLUQRCode]: {
                bpartner: 'BP1',
                tus: [
                    { storages: { P1: '4 PCE' }, attributes: { GRAI: grai1 } },
                    { storages: { P2: '5 PCE' }, attributes: { GRAI: grai2 } },
                    { storages: { P1: '4 PCE' }, attributes: { GRAI: grai3 } },
                ],
                attributesNotEmpty: ['SSCC18'],
                isShipmentCompleted: true,
                shippedTUCount: 3,
                archiveCount: 1,
            },
        },
        pickingSlots: {
            [slot1.qrCode]: { queue: [] },
            [slot2.qrCode]: { queue: [] },
        },
    });
});

/**
 * Picks one pallet (LU with one GRAI-captured crate inside) into the given picking slot: job-level LU target,
 * line, source HU scan, quantity (1 TU) confirmed with "OK und LU schliessen", inline GRAI capture, Save, complete.
 * Closing the LU queues the pallet in the picking slot (as pick_by_ExternalBarcode.spec.js: an open LU is not queued).
 */
const pickOnePallet = async ({ documentNo, slotQrCode, luName, crateGrai, huQrCode }) => await test.step(`Pick a pallet with crate ${crateGrai} of ${documentNo} into its slot`, async () => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: slotQrCode });
    await PickingJobScreen.setTargetLU({ lu: luName });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(huQrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1', closeTarget: true });
    await PickGraiScreen.waitForScreen();
    await PickGraiScreen.scanGraiBatch({ graiStrings: [crateGrai] });
    await PickGraiScreen.expectCount({ scanned: 1, total: 1 });
    await PickGraiScreen.clickSave();
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();
    await PickingJobsListScreen.goBack();
});

/** One rejected scan on the job screen: exactly one error toast (checked + closed by expectErrorToast) with the given text. */
const scanAndExpectRejected = async ({ code, expectedText, title }) => await expectErrorToast(title, async () => {
    await HUConsolidationJobScreen.scanGRAI({ grai: code });
}, ({ textContent }) => {
    expect(textContent).toContain(expectedText);
});

// noinspection JSUnusedLocalSymbols
test('TC2 rejected scans: one right message per scan, nothing moves, scanner stays armed', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const NOT_A_GRAI = 'The scanned barcode is not a valid GRAI.';
    const NO_HU = 'No HU found for this QR code';

    // Job 1 (BP1): crate in slot1 + a pallet (with 1 crate inside) in slot2. Job 2 (BP2): one crate in slot3.
    const masterdata = await createJobScanMasterdata({
        pickTo: ['TU', 'LU_TU'],
        bpartners: { BP1: { graiRequired: 'Y' }, BP2: { graiRequired: 'Y' } },
        pickingSlots: { slot1: {}, slot2: {}, slot3: {} },
        salesOrders: {
            SO1: { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }] },
            SO2: { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P2', qty: 5, piItemProduct: 'TU2' }] },
            SO3: { bpartner: 'BP2', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }] },
        },
    });
    const { slot1, slot2, slot3 } = masterdata.pickingSlots;
    const { SO1, SO2, SO3 } = masterdata.salesOrders;

    const serialBase = String(Date.now());
    const graiOwn = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}1`);
    const graiInPallet = graiWithSerial(masterdata.packingInstructions.PI_P2.grai, `${serialBase}2`);
    const graiOtherJob = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}3`);
    const graiUnknown = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}4`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: graiOwn, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOnePallet({ documentNo: SO2.documentNo, slotQrCode: slot2.qrCode, luName: masterdata.packingInstructions.PI_P2.luName, crateGrai: graiInPallet, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot3.qrCode, grai: graiOtherJob, huQrCode: masterdata.handlingUnits.HU1.qrCode });

    await test.step('Open the BP1 job, choose a new pallet, open the scanner', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await HUConsolidationJobScreen.expectScannerVisible(true);
    });

    /** after each rejected scan: nothing moved, scanner still armed, no toast left */
    const expectNothingMovedScannerArmed = async ({ crateInSlot1 }) => {
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: crateInSlot1 ? 1 : 0 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScannerVisible(true);
    };

    await test.step('EAN-13 -> "not a valid GRAI"', async () => {
        await scanAndExpectRejected({ title: 'EAN-13', code: '4006381333931', expectedText: NOT_A_GRAI });
        await expectNothingMovedScannerArmed({ crateInSlot1: true });
    });
    await test.step('HU QR code -> "No HU found"', async () => {
        await scanAndExpectRejected({ title: 'HU QR code', code: masterdata.handlingUnits.HU1.qrCode, expectedText: NO_HU });
        await expectNothingMovedScannerArmed({ crateInSlot1: true });
    });
    await test.step('unknown GRAI -> "No HU found"', async () => {
        await scanAndExpectRejected({ title: 'unknown GRAI', code: graiUnknown, expectedText: NO_HU });
        await expectNothingMovedScannerArmed({ crateInSlot1: true });
    });
    await test.step('GRAI of the other job\'s crate -> "No HU found"', async () => {
        await scanAndExpectRejected({ title: 'other job GRAI', code: graiOtherJob, expectedText: NO_HU });
        await expectNothingMovedScannerArmed({ crateInSlot1: true });
    });

    await test.step('valid GRAI with a trailing space is accepted: crate moves, no toast', async () => {
        await HUConsolidationJobScreen.scanGRAI({ grai: `${graiOwn} ` });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await ErrorToast.expectNoErrorToast();
    });
    await test.step('the same GRAI again -> "No HU found" (crate already moved)', async () => {
        await scanAndExpectRejected({ title: 'same GRAI again', code: graiOwn, expectedText: NO_HU });
        await expectNothingMovedScannerArmed({ crateInSlot1: false });
    });
    await test.step('GRAI of a crate inside the pallet -> "No HU found", pallet stays in its slot', async () => {
        await scanAndExpectRejected({ title: 'GRAI of a crate inside a pallet', code: graiInPallet, expectedText: NO_HU });
        await expectNothingMovedScannerArmed({ crateInSlot1: false });
    });

    await test.step('Nothing else moved: the pallet and the other job\'s crate are still in their slots', async () => {
        await Backend.expect({
            title: 'TC2: slot1 empty (crate moved), pallet still in slot2, other job crate still in slot3',
            pickingSlots: {
                [slot1.qrCode]: { queue: [] },
                [slot2.qrCode]: { queue: [{ hu: 'pallet' }] },
                [slot3.qrCode]: { queue: [{ hu: 'otherJobCrate' }] },
            },
        });
    });
});
