/**
 * TL;DR — In HU Consolidation, the GRAI scanner on the picking-slot screen is shown by what the crates in the slot carry
 * (a GRAI), no longer by the partner's GRAIRequired flag.
 *
 * TC3 (duplicate GRAI, de_DE + en_US): 2 crates of the job (two slots) carry the same GRAI (picking with pickTo:['TU'] accepts the
 *   same GRAI twice, no dedupe without an LU); the scan shows the message AD_Message 545693 ("Mehrere HUs fuer diesen QR-Code
 *   gefunden" / "Multiple HUs found for this QR code"), nothing moves, the scanner stays armed.
 *
 * TC6 (fallback): 3 GRAI crates + 1 crate without GRAI in one slot: scan 2, move the crate without GRAI by tap in the slot
 *   screen, back on the job screen the scanner is closed and the button still shown, scan the last GRAI crate: all 4 crates
 *   on the pallet, button hidden.
 * TC17 (existing pallet): a pallet chosen by scanning its QR code ("QR scannen") takes the scanned crates; slot counts drop,
 *   the scanner stays armed between scans.
 *
 * TC22 (slot-screen scanner follows the presence rule):
 *   (a) partner on 'N', slot holds one crate with a GRAI (stamped via the HU Manager) and one without: the slot screen shows
 *       the scanner, a scan moves the GRAI crate, the reloaded slot screen then shows no scanner, the other crate is still
 *       moved by tap.
 *   (b) crates without GRAI picked under 'N', the partner is set to 'Y' afterwards: the slot screen shows no scanner,
 *       the tap still moves a crate.
 */

import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { ErrorToast } from '../../utils/dialogs/ErrorToast';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { PickingSlotScreen } from '../../utils/screens/huConsolidation/PickingSlotScreen';
import { createJobScanMasterdata, createNoGraiPartnerMasterdata } from '../../utils/huConsolidationJobScanMasterdata';
import { graiAs8003, graiWithSerial } from '../../utils/grai';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { SelectPickTargetTUScreen } from '../../utils/screens/picking/SelectPickTargetTUScreen';
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { GRAIScreen } from '../../utils/screens/huManager/GRAIScreen';

/**
 * Picks one GRAI-stamped top-level TU for a single-line picking job into the given picking slot
 * (same flow as hu_consolidation_grai_cross_slot.spec.js: slot scan, line, TU target GRAI scan, HU scan, qty, complete).
 */
export const pickOneGraiCrate = async ({ documentNo, slotQrCode, grai, huQrCode }) => await test.step(`Pick crate ${grai} of ${documentNo} into its slot`, async () => {
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

/**
 * Picks one top-level TU WITHOUT a GRAI for a single-line picking job into the given picking slot. Only valid while the
 * partner is on GRAIRequired 'N' (a crate without GRAI cannot complete under 'Y'): the line's TU target is chosen by the
 * TU button of the target screen (no GRAI panel then), as PickingJobScreen.setTargetTU; the rest is as pickOneGraiCrate.
 * @returns {Promise<{targetButton: string}>} which TU target button was tapped ('line PI' or 'fallback: first button')
 */
export const pickOneCrateWithoutGrai = async ({ documentNo, slotQrCode, tuName, huQrCode }) => await test.step(`Pick crate without GRAI of ${documentNo} into its slot`, async () => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: slotQrCode });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
    await SelectPickTargetTUScreen.waitForScreen();
    let targetButton = 'line PI';
    if (!(await SelectPickTargetTUScreen.hasTUButton({ tu: tuName }))) {
        targetButton = 'fallback: first target button';
        await SelectPickTargetTUScreen.clickFirstTargetButton();
    } else {
        await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
    }
    console.log('pickOneCrateWithoutGrai: TU target button = ' + targetButton);
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(huQrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();
    await PickingJobsListScreen.goBack();
    return { targetButton };
});

/**
 * Stamps a GRAI on a top-level TU through the HU Manager "Scan GRAI" action (PUT .../grai) and returns to the applications list.
 * @param {string} huQrCode global QR code of the TU
 * @param {string} grai canonical dot-form GRAI (sent as GS1 AI 8003 barcode, as humanager/grai.spec.js)
 */
export const stampGraiViaHUManager = async ({ huQrCode, grai }) => await test.step(`Stamp GRAI ${grai} on a crate via the HU Manager`, async () => {
    await ApplicationsListScreen.startApplication('huManager');
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.scanHUQRCode({ huQRCode: huQrCode });
    await HUManagerScreen.openGRAIScreen();
    await GRAIScreen.expectVisible();
    await GRAIScreen.scanGraiBarcode({ barcodeString: graiAs8003(grai) });
    await GRAIScreen.expectGraiChipCount({ expectedCount: 1 });
    await GRAIScreen.tapSendButton();
    await GRAIScreen.goBack();
    await HUManagerScreen.waitForScreen();
    await HUManagerScreen.goBack();
    await ApplicationsListScreen.expectVisible();
});

/** Picks the P1 crate (SO1) and the P2 crate (SO2) into slot1 while the partner is on 'N'; returns their ids / QR codes. */
const pickTwoCratesWithoutGrai = async ({ masterdata }) => {
    const { slot1 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneCrateWithoutGrai({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P1.tuName, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneCrateWithoutGrai({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P2.tuName, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    return await Backend.getPickingSlotCrates({ title: 'two crates without GRAI in slot1', slotQrCode: slot1.qrCode, aliases: ['crateP1', 'crateP2'] });
};

const openJobWithPallet = async ({ masterdata }) => {
    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
};

// noinspection JSUnusedLocalSymbols
test('TC22 (a) partner on N, one GRAI crate + one without: slot screen scanner moves the GRAI crate, then disappears; tap still works', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    let targetLUQRCode;
    const { context, qrCodes } = await pickTwoCratesWithoutGrai({ masterdata });
    const graiP1 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${Date.now()}1`);
    await stampGraiViaHUManager({ huQrCode: qrCodes.crateP1, grai: graiP1 });

    await openJobWithPallet({ masterdata });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });

    await test.step('Slot screen shows the scanner (a crate carries a GRAI) although the partner is on N; a scan moves that crate', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerVisible();
        await PickingSlotScreen.scanGRAI({ graiString: graiP1 });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await ErrorToast.expectNoErrorToast();
        targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
        await Backend.expect({
            title: 'TC22 (a): the scanned GRAI crate sits on the pallet',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: graiP1 } }] } },
        });
    });
    await test.step('Reloaded slot screen: no scanner (the remaining crate has no GRAI); tap still moves it', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerNotVisible();
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateP2 });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await Backend.expect({
            title: 'TC22 (a): both crates sit on the pallet',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: graiP1 } }, { storages: { P2: '5 PCE' } }] } },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('TC22 (b) crates without GRAI picked under N, partner set to Y afterwards: no slot screen scanner; tap still works', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    const { context } = await pickTwoCratesWithoutGrai({ masterdata });
    await Backend.updateBPartner({ bpartnerId: masterdata.bpartners.BP1.id, graiRequired: 'Y' });

    await openJobWithPallet({ masterdata });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });

    await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
    await PickingSlotScreen.expectScannerNotVisible();
    await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateP1 });
    await PickingSlotScreen.goBack();
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
    await Backend.expect({
        title: 'TC22 (b): the tapped crate sits on the pallet',
        hus: { [await HUConsolidationJobScreen.getCurrentTargetLUQRCode()]: { tus: [{ storages: { P1: '4 PCE' } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC6 fallback: 3 GRAI crates + 1 crate without GRAI, the tap moves the crate without GRAI, the last scan hides the button', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const datePromised = '2025-03-01T00:00:00.000+02:00';
    const masterdata = await createJobScanMasterdata({
        bpartners: { BP1: { graiRequired: 'N' } },
        pickingSlots: { slot1: {} },
        salesOrders: {
            SO1: { bpartner: 'BP1', warehouse: 'wh', datePromised, lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }] },
            SO2: { bpartner: 'BP1', warehouse: 'wh', datePromised, lines: [{ product: 'P2', qty: 5, piItemProduct: 'TU2' }] },
            SO3: { bpartner: 'BP1', warehouse: 'wh', datePromised, lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU1' }] },
            SO4: { bpartner: 'BP1', warehouse: 'wh', datePromised, lines: [{ product: 'P2', qty: 5, piItemProduct: 'TU2' }] },
        },
    });
    const { slot1 } = masterdata.pickingSlots;
    const { SO1, SO2, SO3, SO4 } = masterdata.salesOrders;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;
    const serialBase = String(Date.now());
    const grai1 = graiWithSerial(PI_P1.grai, `${serialBase}1`);
    const grai2 = graiWithSerial(PI_P2.grai, `${serialBase}2`);
    const grai3 = graiWithSerial(PI_P1.grai, `${serialBase}3`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    // the crate without GRAI is picked first, while the partner is on 'N'; the 3 GRAI crates under 'Y'
    await pickOneCrateWithoutGrai({ documentNo: SO4.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P2.tuName, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await Backend.updateBPartner({ bpartnerId: masterdata.bpartners.BP1.id, graiRequired: 'Y' });
    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: grai1, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai: grai2, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot1.qrCode, grai: grai3, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    const { context } = await Backend.getPickingSlotCrates({ title: 'TC6: four crates in slot1', slotQrCode: slot1.qrCode, aliases: ['crateNoGrai', 'crateG1', 'crateG2', 'crateG3'] });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ lu: PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 4 });

    await test.step('Scan 2 of the 3 GRAI crates', async () => {
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await HUConsolidationJobScreen.scanGRAI({ grai: grai1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 3 });
        await HUConsolidationJobScreen.scanGRAI({ grai: grai2 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
        await ErrorToast.expectNoErrorToast();
    });
    await test.step('Slot row, tap the crate without GRAI: back on the job screen the scanner is closed, the button still shown', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateNoGrai });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScannerOpen(false);
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
    });
    await test.step('Open the scanner, scan the last GRAI crate: slot empty, button hidden, scanner closed (AC19-VANISH)', async () => {
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await HUConsolidationJobScreen.scanGRAI({ grai: grai3 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
        await HUConsolidationJobScreen.expectScannerOpen(false);
        await ErrorToast.expectNoErrorToast();
    });

    const targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
    await Backend.expect({
        title: 'TC6: all 4 crates on the pallet (P1 2 x 4, P2 2 x 5)',
        hus: { [targetLUQRCode]: { storages: { P1: '8 PCE', P2: '10 PCE' } } },
        pickingSlots: { [slot1.qrCode]: { queue: [] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('TC17 existing pallet: scan its QR code as target, then 2 GRAI scans put the crates onto it', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan on the job screen');
    await allure.severity('critical');

    const masterdata = await createJobScanMasterdata({
        handlingUnits: { EXISTING_LU: { product: 'P2', warehouse: 'wh', packingInstructions: 'PI_P2' } },
    });
    const { slot1 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;
    const serialBase = String(Date.now());
    const grai1 = graiWithSerial(PI_P1.grai, `${serialBase}1`);
    const grai2 = graiWithSerial(PI_P2.grai, `${serialBase}2`);
    const existingLUQRCode = masterdata.handlingUnits.EXISTING_LU.qrCode;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: grai1, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai: grai2, huQrCode: masterdata.handlingUnits.HU2.qrCode });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ qrCode: existingLUQRCode });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });

    await test.step('Scan (GRAI): 2 crates onto the existing pallet, scanner armed between the scans', async () => {
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await HUConsolidationJobScreen.scanGRAI({ grai: grai1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScannerOpen(true);
        await ErrorToast.expectNoErrorToast();
        await HUConsolidationJobScreen.scanGRAI({ grai: grai2 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await ErrorToast.expectNoErrorToast();
    });

    await Backend.expect({
        title: 'TC17: both crates sit under the existing pallet (P2 100 PCE before + 5, P1 4)',
        hus: { [existingLUQRCode]: { storages: { P1: '4 PCE', P2: '105 PCE' } } },
        pickingSlots: { [slot1.qrCode]: { queue: [] } },
    });
});

const AMBIGUOUS_MESSAGES = {
    de_DE: 'Mehrere HUs für diesen QR-Code gefunden',
    en_US: 'Multiple HUs found for this QR code',
};

for (const language of ['de_DE', 'en_US']) {
    // noinspection JSUnusedLocalSymbols
    test(`TC3 duplicate GRAI: 2 crates of the job carry the scanned GRAI, message, nothing moves, scanner stays armed (${language})`, async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan on the job screen');
        await allure.severity('critical');

        const masterdata = await createJobScanMasterdata({ language });
        const { slot1, slot2 } = masterdata.pickingSlots;
        const { SO1, SO2, SO3 } = masterdata.salesOrders;
        const { PI_P1, PI_P2 } = masterdata.packingInstructions;
        const serialBase = String(Date.now());
        const graiDuplicate = graiWithSerial(PI_P1.grai, `${serialBase}1`);
        const graiOther = graiWithSerial(PI_P2.grai, `${serialBase}2`);

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        // E12: picking has no duplicate check without an LU, so the same GRAI is scanned for the SO1 crate (slot1) and the SO3 crate (slot2)
        await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: graiDuplicate, huQrCode: masterdata.handlingUnits.HU1.qrCode });
        await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai: graiOther, huQrCode: masterdata.handlingUnits.HU2.qrCode });
        await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot2.qrCode, grai: graiDuplicate, huQrCode: masterdata.handlingUnits.HU1.qrCode });

        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: PI_P1.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });

        await test.step('Scan the GRAI that two crates carry: message, nothing moves, scanner stays armed', async () => {
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
            await HUConsolidationJobScreen.clickScanGRAIButton();
            await HUConsolidationJobScreen.scanGRAIExpectError({ grai: graiDuplicate, message: AMBIGUOUS_MESSAGES[language] });
            await HUConsolidationJobScreen.expectScannerOpen(true);
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
        });

        await Backend.getPickingSlotCrates({ title: 'TC3: slot1 keeps its 2 crates', slotQrCode: slot1.qrCode, aliases: ['slot1CrateA', 'slot1CrateB'] });
        await Backend.getPickingSlotCrates({ title: 'TC3: slot2 keeps its crate', slotQrCode: slot2.qrCode, aliases: ['slot2Crate'] });
    });
}
