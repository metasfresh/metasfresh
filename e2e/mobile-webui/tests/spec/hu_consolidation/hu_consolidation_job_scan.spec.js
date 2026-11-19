/**
 * TL;DR — In HU Consolidation, the worker scans the GRAI of each crate directly on the job screen
 * ("Scan (GRAI)" button), no matter which picking slot the crate is in, instead of opening every
 * picking slot first. Scanned crates go onto the open pallet; Fertigstellen prints the pallet label
 * and ships.
 *

 * TC4 (button states, de_DE + en_US): "Scan (GRAI)" disabled before a pallet is chosen, enabled after, scanner closes when
 * the pallet is closed (Schliessen). TC7: a job whose slot holds only a pallet shows no button. TC19: partner on 'N' with a
 * GRAI crate: button shown, scan moves the crate, slot screen scanner follows the presence rule. TC20: button only when a
 * crate carries a GRAI; it goes away with the last GRAI crate (the tap path stays).
 *
 * TC14 (Rückgängig): (a) before any crate it voids the job and goes back to the job list (as today); (b) after a scan moved a
 * crate onto the pallet it is refused silently, the job screen stays and nothing changes (as today).
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
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { PickingSlotScreen } from '../../utils/screens/huConsolidation/PickingSlotScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { ErrorToast } from '../../utils/dialogs/ErrorToast';
import { expectErrorToast } from '../../utils/common';
import { createJobScanMasterdata, createNoGraiPartnerMasterdata, HU_LABEL_PROCESS_ID } from '../../utils/huConsolidationJobScanMasterdata';
import { graiAs8003, graiWithSerial, pickOneCrateWithoutGrai, pickOneGraiCrate, pickOnePallet, readSlotCrates, stampGraiViaHUManager } from '../../utils/huConsolidationJobScanFlows';

// GS1 sysconfigs changed by a test are written back afterwards, so later specs see the DB as before
let sysconfigsToRestore = null;
test.afterEach(async () => {
    const sysconfigs = sysconfigsToRestore;
    sysconfigsToRestore = null;
    if (sysconfigs && Object.keys(sysconfigs).length > 0) {
        await Backend.setSysconfigs(sysconfigs);
    }
});

// noinspection JSUnusedLocalSymbols
test('TC1 reference case: scan 3 crates from 2 slots on the job screen, Fertigstellen', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    // GS1 manufacturer code + extension digit for the SSCC18 of the pallet
    const masterdata = await createJobScanMasterdata({
        sysconfigs: {
            'de.metas.handlingunit.GS1ManufacturerCode': '1234567',
            'de.metas.handlingunit.GS1ExtensionDigit': '0',
        },
    });
    // the values found BEFORE the masterdata call; restored by the afterEach below (also when the test failed)
    sysconfigsToRestore = masterdata.previousSysconfigs ?? null;
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
        await HUConsolidationJobScreen.expectScannerOpen(true);

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
        await HUConsolidationJobsListScreen.expectJobNotListed({ customerLocationId });
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
                archiveProcessId: HU_LABEL_PROCESS_ID,
            },
        },
        pickingSlots: {
            [slot1.qrCode]: { queue: [] },
            [slot2.qrCode]: { queue: [] },
        },
    });
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
        await HUConsolidationJobScreen.expectScannerOpen(true);
    });

    /** after each rejected scan: nothing moved, scanner still armed, no toast left */
    const expectNothingMovedScannerArmed = async ({ crateInSlot1 }) => {
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: crateInSlot1 ? 1 : 0 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScannerOpen(true);
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

const CAPTIONS = {
    de_DE: { newLU: 'Neues Gebinde', current: 'Offenes Gebinde: ', complete: 'Fertigstellen', abort: 'Rückgängig' },
    en_US: { newLU: 'New LU', current: 'Current: ', complete: 'Complete', abort: 'Abort' },
};

for (const language of ['de_DE', 'en_US']) {
    // noinspection JSUnusedLocalSymbols
    test(`TC4 button states: disabled before the pallet, enabled after, scanner closed after Schliessen (${language})`, async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan on the job screen');
        await allure.severity('critical');

        const captions = CAPTIONS[language];
        const masterdata = await createJobScanMasterdata({ language });
        const { slot1, slot2 } = masterdata.pickingSlots;
        const { SO1, SO3 } = masterdata.salesOrders;

        const serialBase = String(Date.now());
        const grai1 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}1`);
        const grai3 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}3`);

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: grai1, huQrCode: masterdata.handlingUnits.HU1.qrCode });
        await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot2.qrCode, grai: grai3, huQrCode: masterdata.handlingUnits.HU1.qrCode });

        await test.step('Open the job: no pallet yet -> button shown but disabled, no scanner', async () => {
            await ApplicationsListScreen.startApplication('huConsolidation');
            await HUConsolidationJobsListScreen.waitForScreen();
            await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: captions.newLU });
            await HUConsolidationJobScreen.expectCompleteAndAbortCaptions({ complete: captions.complete, abort: captions.abort });
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: true });
            await HUConsolidationJobScreen.expectScannerOpen(false);
        });
        await test.step('Choose a pallet -> button enabled', async () => {
            await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: captions.current });
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
            await HUConsolidationJobScreen.clickScanGRAIButton();
            await HUConsolidationJobScreen.expectScannerOpen(true);
        });
        await test.step('Scan crate 1, then close the pallet (Schliessen): button disabled, scanner hidden', async () => {
            await HUConsolidationJobScreen.scanGRAI({ grai: grai1 });
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
            await HUConsolidationJobScreen.closeTargetLU();
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: captions.newLU });
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: true });
            await HUConsolidationJobScreen.expectScannerOpen(false);
        });
    });
}

// noinspection JSUnusedLocalSymbols
test('TC7 presence rule: a job whose slot holds only a pallet shows no button, the other job does', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    // Job 1 (BP1): slot1 holds only a pallet (the crate inside it carries a GRAI, but is not a top-level HU). Job 2 (BP2): a GRAI crate in slot2.
    const masterdata = await createJobScanMasterdata({
        pickTo: ['TU', 'LU_TU'],
        bpartners: { BP1: { graiRequired: 'Y' }, BP2: { graiRequired: 'Y' } },
        pickingSlots: { slot1: {}, slot2: {} },
        salesOrders: {
            SO1: { bpartner: 'BP1', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P2', qty: 5, piItemProduct: 'TU2' }] },
            SO2: { bpartner: 'BP2', warehouse: 'wh', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }] },
        },
    });
    const { slot1, slot2 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;

    const serialBase = String(Date.now());
    const graiInPallet = graiWithSerial(masterdata.packingInstructions.PI_P2.grai, `${serialBase}1`);
    const graiOtherJob = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}2`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOnePallet({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, luName: masterdata.packingInstructions.PI_P2.luName, crateGrai: graiInPallet, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot2.qrCode, grai: graiOtherJob, huQrCode: masterdata.handlingUnits.HU1.qrCode });

    await test.step('Pallet-only job: button hidden, no scanner', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P2.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
        await HUConsolidationJobScreen.expectScannerOpen(false);
        await HUConsolidationJobScreen.goBack();
    });
    await test.step('The other job\'s crate is untouched in its slot', async () => {
        await Backend.expect({
            title: 'TC7: pallet in slot1, other job crate still in slot2',
            pickingSlots: {
                [slot1.qrCode]: { queue: [{ hu: 'pallet' }] },
                [slot2.qrCode]: { queue: [{ hu: 'otherJobCrate' }] },
            },
        });
    });
    await test.step('The other job (a GRAI crate in its slot) shows the button', async () => {
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP2.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
    });
});

// noinspection JSUnusedLocalSymbols
test('TC19 partner on N, one crate with a GRAI (stamped via the HU Manager): button shown, scan moves it, slot screen follows the presence rule', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    const { SO1 } = masterdata.salesOrders;
    const grai1 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${Date.now()}1`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneCrateWithoutGrai({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, tuName: masterdata.packingInstructions.PI_P1.tuName, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    const { qrCodes } = await readSlotCrates({ title: 'TC19: the crate sits in slot1', slotQrCode: slot1.qrCode, aliases: ['crate1'] });
    await stampGraiViaHUManager({ huQrCode: qrCodes.crate1, grai: grai1 });

    await test.step('Open the job, choose a pallet: partner is on N, the crate carries a GRAI -> button shown', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
    });
    await test.step('Before the scan: the slot screen shows the scanner although the partner is on N (AC27)', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerVisible();
        await PickingSlotScreen.goBack();
    });
    await test.step('Scan (GRAI) moves the crate', async () => {
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await HUConsolidationJobScreen.scanGRAI({ grai: grai1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
    });
    await test.step('After the scan: no GRAI HU in the slot, the reloaded slot screen shows no scanner', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerNotVisible();
        await PickingSlotScreen.goBack();
    });
});

/**
 * TC20 fixture: two crates without GRAI picked while the partner is on 'N'; the partner is on 'Y' at Verdichtung time
 * (the button reads no partner flag). With withGraiCrate the P1 crate is picked afterwards under 'Y' with a GRAI scan.
 */
const pickTwoCrates = async ({ masterdata, withGraiCrate }) => {
    const { slot1 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;
    const bpartnerId = masterdata.bpartners.BP1.id;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneCrateWithoutGrai({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P2.tuName, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    if (!withGraiCrate) {
        await pickOneCrateWithoutGrai({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P1.tuName, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    }
    await Backend.updateBPartner({ bpartnerId, graiRequired: 'Y' });
    if (withGraiCrate) {
        const grai = graiWithSerial(PI_P1.grai, `${Date.now()}1`);
        await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai, huQrCode: masterdata.handlingUnits.HU1.qrCode });
        return { grai };
    }
    return {};
};

// noinspection JSUnusedLocalSymbols
test('TC20 (a) two crates without GRAI (partner on Y at Verdichtung time): no button', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    await pickTwoCrates({ masterdata, withGraiCrate: false });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
    await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
});

// noinspection JSUnusedLocalSymbols
test('TC20 (b) one GRAI crate + one without: button shown, gone after the GRAI crate is scanned, the other crate is moved by tap', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    const { grai } = await pickTwoCrates({ masterdata, withGraiCrate: true });
    const { context } = await readSlotCrates({ title: 'TC20 (b): two crates in slot1', slotQrCode: slot1.qrCode, aliases: ['crateNoGrai', 'crateGrai'] });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
    await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });

    await test.step('Scan the GRAI crate: button gone, scanner closed (no GRAI crate left)', async () => {
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await HUConsolidationJobScreen.scanGRAI({ grai });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
        await HUConsolidationJobScreen.expectScannerOpen(false);
    });
    await test.step('Tap the other crate in the slot screen (AC17): it moves', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateNoGrai });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
    });
});

/** TC14 fixture: two GRAI crates (P1, P2) picked into slot1, the job of BP1 opened. */
const pickTwoGraiCratesAndOpenJob = async () => {
    const masterdata = await createJobScanMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;
    const serialBase = String(Date.now());
    const grai1 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}1`);
    const grai2 = graiWithSerial(masterdata.packingInstructions.PI_P2.grai, `${serialBase}2`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: grai1, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai: grai2, huQrCode: masterdata.handlingUnits.HU2.qrCode });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    return { masterdata, slot1, grai1 };
};

// noinspection JSUnusedLocalSymbols
test('TC14 (a) Rückgängig -> Ja before any crate: back to the job list', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('normal');

    await pickTwoGraiCratesAndOpenJob();
    await HUConsolidationJobScreen.abort();
});

// noinspection JSUnusedLocalSymbols
test('TC14 (b) Rückgängig -> Ja after one scanned crate: stays on the job screen, nothing changes', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('normal');

    const { masterdata, slot1, grai1 } = await pickTwoGraiCratesAndOpenJob();
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
    await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
    await HUConsolidationJobScreen.clickScanGRAIButton();
    await HUConsolidationJobScreen.scanGRAI({ grai: grai1 });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });

    await HUConsolidationJobScreen.abortRefused();
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
    await HUConsolidationJobScreen.expectTargetLUCaptionNotContains({ text: masterdata.packingInstructions.PI_P1.luName });
});
