/**
 * In HU Consolidation, the worker scans the GRAI of each crate on a separate scan screen ("Scan (GRAI)" button of the job screen),
 * no matter which picking slot the crate is in, instead of opening every picking slot first. Scanned crates go onto the open pallet;
 * the scan screen stays open after each scan (OK beep, or error beep + error toast), Back returns to the job screen, where the
 * crate counts have dropped; Fertigstellen prints the pallet label and ships.
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
import { GRAIScanScreen } from '../../utils/screens/huConsolidation/GRAIScanScreen';
import { SelectHUConsolidationTargetScreen } from '../../utils/screens/huConsolidation/SelectHUConsolidationTargetScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { SelectPickTargetTUScreen } from '../../utils/screens/picking/SelectPickTargetTUScreen';
import { PickGraiScreen } from '../../utils/screens/picking/PickGraiScreen';
import { HUManagerScreen } from '../../utils/screens/huManager/HUManagerScreen';
import { GRAIScreen } from '../../utils/screens/huManager/GRAIScreen';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';
import { ErrorToast } from '../../utils/dialogs/ErrorToast';
import { createJobScanMasterdata, createNoGraiPartnerMasterdata, jobScanSalesOrder, cleanUpAfterJobScanTest, HU_LABEL_PROCESS_ID, LIVE_DEFAULT_SYSCONFIGS, LU_LABEL_PROCESS_WITHOUT_HU_PROCESS_ID } from '../../utils/huConsolidationJobScanMasterdata';
import { graiAs8003, graiWithSerial } from '../../utils/grai';
import { attachScreenshot, expectErrorToast } from '../../utils/common';

// sysconfigs changed by a test are written back and its picking slots emptied afterwards (also when it failed), so later tests and specs see the DB as before
test.afterEach(async () => {
    await cleanUpAfterJobScanTest();
});

/** Opens the single-line picking job of the sales order and scans the picking slot. */
const openPickingJobInSlot = async ({ documentNo, slotQrCode }) => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(documentNo);
    await PickingJobsListScreen.startJob({ index: 1 });
    await PickingJobScreen.scanPickingSlot({ qrCode: slotQrCode });
};

/** On the line screen: scan the source HU and pick quantity 1 (optionally closing the LU target). */
const scanHUAndPickOne = async ({ huQrCode, closeTarget }) => {
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(huQrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1', ...(closeTarget ? { closeTarget } : {}) });
};

/** From the line screen: back to the job, complete it, back to the job list. */
const completePickingJob = async () => {
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();
    await PickingJobsListScreen.goBack();
};

/** Picks one GRAI-stamped top-level TU of a single-line picking job into the given picking slot (the TU target gets the GRAI by scan). */
const pickOneGraiCrate = async ({ documentNo, slotQrCode, grai, huQrCode }) => await test.step(`Pick crate ${grai} of ${documentNo} into its slot`, async () => {
    await openPickingJobInSlot({ documentNo, slotQrCode });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
    await PickingGraiScanPanel.scanGrai({ graiString: grai });
    await PickingJobLineScreen.waitForScreen();
    await scanHUAndPickOne({ huQrCode });
    await PickingJobLineScreen.waitForScreen();
    await completePickingJob();
});

/**
 * Picks one top-level TU WITHOUT a GRAI into the given picking slot. Only valid while the partner is on GRAIRequired 'N'
 * (a crate without GRAI cannot complete under 'Y'): the TU target is chosen on the TU button of the target screen.
 */
const pickOneCrateWithoutGrai = async ({ documentNo, slotQrCode, tuName, huQrCode }) => await test.step(`Pick crate without GRAI of ${documentNo} into its slot`, async () => {
    await openPickingJobInSlot({ documentNo, slotQrCode });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
    await SelectPickTargetTUScreen.waitForScreen();
    await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
    await PickingJobLineScreen.waitForScreen();
    await scanHUAndPickOne({ huQrCode });
    await PickingJobLineScreen.waitForScreen();
    await completePickingJob();
});

/**
 * Stamps a GRAI on a top-level TU through the HU Manager "Scan GRAI" action and returns to the applications list.
 * @param {string} huQrCode global QR code of the TU
 * @param {string} grai canonical dot-form GRAI (sent as GS1 AI 8003 barcode)
 */
const stampGraiViaHUManager = async ({ huQrCode, grai }) => await test.step(`Stamp GRAI ${grai} on a crate via the HU Manager`, async () => {
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

/**
 * Picks one pallet (LU with one GRAI-captured crate inside) into the given picking slot: job-level LU target, quantity 1 TU
 * confirmed with "OK und LU schliessen", inline GRAI capture, Save. Closing the LU queues the pallet in the slot (an open LU is not queued).
 */
const pickOnePallet = async ({ documentNo, slotQrCode, luName, crateGrai, huQrCode }) => await test.step(`Pick a pallet with crate ${crateGrai} of ${documentNo} into its slot`, async () => {
    await openPickingJobInSlot({ documentNo, slotQrCode });
    await PickingJobScreen.setTargetLU({ lu: luName });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await scanHUAndPickOne({ huQrCode, closeTarget: true });
    await PickGraiScreen.waitForScreen();
    await PickGraiScreen.scanGraiBatch({ graiStrings: [crateGrai] });
    await PickGraiScreen.expectCount({ scanned: 1, total: 1 });
    await PickGraiScreen.clickSave();
    await PickingJobLineScreen.waitForScreen();
    await completePickingJob();
});

// noinspection JSUnusedLocalSymbols
test('Scan (GRAI) opens the scan screen: 3 crates from 2 slots scanned there, Back, Fertigstellen', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    // GS1 manufacturer code + extension digit for the SSCC18 of the pallet;
    // the one test with a pallet label process that has an M_HU_Process row (labels collected via the HU process, no crate lookup)
    const masterdata = await createJobScanMasterdata({
        language: 'de_DE', // German UI, as the customer uses it
        huLabelConfigs: { lc1: { bpartner: 'BP1', huUnitType: 'LU', sourceDocType: 'PI', process: HU_LABEL_PROCESS_ID, autoPrint: true } },
        sysconfigs: {
            'de.metas.handlingunit.GS1ManufacturerCode': '1234567',
            'de.metas.handlingunit.GS1ExtensionDigit': '0',
        },
    });
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
        // proves the German UI: the caption of the pallet button before a pallet is chosen
        await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: 'Neues Gebinde' });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
    });

    await test.step('Scan (GRAI) opens the scan screen; 2 crates from 2 slots in a row, the screen stays open', async () => {
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.expectScanGRAIButtonBetweenPalletButtonAndSlots();
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.expectVisible();
        // header caption of the scan screen; the URL is the scan screen route of the job opened with the pallet chosen above
        await GRAIScanScreen.expectHeaderCaption({ caption: 'Scan (GRAI)' });
        await GRAIScanScreen.expectUrlIsScanScreenRoute();

        // scan 1: canonical dot form, slot1
        await GRAIScanScreen.scanGRAI({ grai: grai1 });
        await GRAIScanScreen.expectVisible();
        // scan 2: GS1 AI 8003 form (plain barcode), other slot: no tap in between
        await GRAIScanScreen.scanGRAI({ grai: graiAs8003(grai3) });
        await GRAIScanScreen.expectVisible();
    });

    await test.step('Back: the job screen shows the dropped crate counts, the pallet holds the 2 crates', async () => {
        await GRAIScanScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 0 });
        // after the first crate the pallet is a real LU: its caption shows the pallet QR, no longer the pallet type
        await HUConsolidationJobScreen.expectTargetLUCaptionNotContains({ text: masterdata.packingInstructions.PI_P1.luName });
        targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
        await Backend.expect({
            title: 'the 2 scanned crates are children of the pallet',
            hus: {
                [targetLUQRCode]: {
                    // the TUs are listed in creation order, not in scan order
                    tus: [
                        { storages: { P1: '4 PCE' }, attributes: { GRAI: grai1 } },
                        { storages: { P1: '4 PCE' }, attributes: { GRAI: grai3 } },
                    ],
                },
            },
        });
    });

    await test.step('Scan the last GRAI crate: the scan screen returns to the job screen by itself, the button is gone', async () => {
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.scanGRAI({ grai: grai2 });
        await GRAIScanScreen.expectClosedByItself();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
    });

    await test.step('Fertigstellen -> Ja: back on the job list, ship-to gone', async () => {
        await HUConsolidationJobsListScreen.expectJobNotListedAfter({
            customerLocationId,
            trigger: () => HUConsolidationJobScreen.complete(),
        });
    });

    await Backend.expect({
        title: 'pallet holds the 3 crates, SSCC18 set, partner BP1, shipment completed, 1 label archived',
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

// noinspection JSUnusedLocalSymbols
test('Rejected scans: one right message per scan, nothing moves, the scan screen stays open', async ({ page: _page }) => {
    // setup picks 4 jobs, then 7 scans (GRAI debounce ~1.5 s each + toast close) follow
    test.slow();
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    // German UI (AD_Message de.metas.handlingunits.picking.InvalidGRAIBarcode / de.metas.mobile.qr.HuNotFound, de_DE)
    const NOT_A_GRAI = 'Der gescannte Barcode ist kein gültiger GRAI.';
    const NO_HU = 'Keine HU für diesen QR-Code gefunden';

    // Job 1 (BP1): 2 crates in slot1 (as in the reference flow: one is scanned, the other stays so the job keeps a GRAI crate and the scanner stays open) + a pallet (with 1 crate inside) in slot2. Job 2 (BP2): one crate in slot3.
    const masterdata = await createJobScanMasterdata({
        language: 'de_DE', // German UI, as the customer uses it
        // the same GRAI scanned twice must reach the backend both times (no duplicate-scan guard)
        sysconfigs: { 'mobileui.frontend.barcodeScanner.scanDuplicatesIntervalMillis': '0' },
        pickTo: ['TU', 'LU_TU'],
        bpartners: { BP1: { graiRequired: 'Y' }, BP2: { graiRequired: 'Y' } },
        pickingSlots: { slot1: {}, slot2: {}, slot3: {} },
        salesOrders: {
            SO1: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
            SO2: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
            SO3: jobScanSalesOrder({ bpartner: 'BP2', product: 'P1', qty: 4, tu: 'TU1' }),
            SO4: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
        },
    });
    const { slot1, slot2, slot3 } = masterdata.pickingSlots;
    const { SO1, SO2, SO3, SO4 } = masterdata.salesOrders;

    const serialBase = String(Date.now());
    const graiOwn = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}1`);
    const graiInPallet = graiWithSerial(masterdata.packingInstructions.PI_P2.grai, `${serialBase}2`);
    const graiOtherJob = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}3`);
    const graiUnknown = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}4`);
    const graiStays = graiWithSerial(masterdata.packingInstructions.PI_P2.grai, `${serialBase}5`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: graiOwn, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO4.documentNo, slotQrCode: slot1.qrCode, grai: graiStays, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOnePallet({ documentNo: SO2.documentNo, slotQrCode: slot2.qrCode, luName: masterdata.packingInstructions.PI_P2.luName, crateGrai: graiInPallet, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot3.qrCode, grai: graiOtherJob, huQrCode: masterdata.handlingUnits.HU1.qrCode });

    await test.step('Open the BP1 job, choose a new pallet, open the scan screen', async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
    });

    /** after each rejected scan: the scan screen is still open, and no second (stale) toast arrived after the first was closed */
    const expectScreenStaysOpen = async () => {
        await GRAIScanScreen.expectVisible();
        await ErrorToast.expectNoErrorToast();
    };
    /** the scan screen shows no counters: Back to the job screen, check the crate counts of the slots, open the scan screen again */
    const expectNothingMovedOnJobScreen = async ({ crateCountInSlot1 }) => {
        await GRAIScanScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: crateCountInSlot1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
        await HUConsolidationJobScreen.clickScanGRAIButton();
    };

    await test.step('EAN-13 -> "not a valid GRAI"', async () => {
        await GRAIScanScreen.scanGRAIExpectError({ grai: '4006381333931', message: NOT_A_GRAI });
        await expectScreenStaysOpen();
    });
    await test.step('HU QR code -> "No HU found"', async () => {
        await GRAIScanScreen.scanGRAIExpectError({ grai: masterdata.handlingUnits.HU1.qrCode, message: NO_HU });
        await expectScreenStaysOpen();
    });
    await test.step('unknown GRAI -> "No HU found"', async () => {
        await GRAIScanScreen.scanGRAIExpectError({ grai: graiUnknown, message: NO_HU });
        await expectScreenStaysOpen();
    });
    await test.step('GRAI of the other job\'s crate -> "No HU found"', async () => {
        await GRAIScanScreen.scanGRAIExpectError({ grai: graiOtherJob, message: NO_HU });
        await expectScreenStaysOpen();
    });
    await test.step('Nothing moved by the 4 rejected scans: Back shows the counts as before', async () => {
        await expectNothingMovedOnJobScreen({ crateCountInSlot1: 2 });
    });

    await test.step('valid GRAI with a trailing space is accepted: crate moves onto the pallet, no toast, the screen stays (another GRAI crate is left)', async () => {
        await GRAIScanScreen.scanGRAI({ grai: `${graiOwn} ` });
        await expectScreenStaysOpen();
        const targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
        await Backend.expect({
            title: 'the scanned crate sits on the pallet',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: graiOwn } }] } },
        });
    });
    await test.step('the same GRAI again -> "No HU found" (crate already moved)', async () => {
        await GRAIScanScreen.scanGRAIExpectError({ grai: graiOwn, message: NO_HU });
        await expectScreenStaysOpen();
    });
    await test.step('GRAI of a crate inside the pallet -> "No HU found", pallet stays in its slot', async () => {
        await GRAIScanScreen.scanGRAIExpectError({ grai: graiInPallet, message: NO_HU });
        await expectScreenStaysOpen();
    });
    await test.step('Back: only the accepted crate left slot1, slot2 is untouched', async () => {
        await GRAIScanScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
    });

    await test.step('Nothing else moved: the pallet and the other job\'s crate are still in their slots', async () => {
        await Backend.expect({
            title: 'slot1 holds only the crate that was never scanned, pallet still in slot2, other job crate still in slot3',
            pickingSlots: {
                [slot1.qrCode]: { queue: [{ hu: 'stayingCrate' }] },
                [slot2.qrCode]: { queue: [{ hu: 'pallet' }] },
                [slot3.qrCode]: { queue: [{ hu: 'otherJobCrate' }] },
            },
        });
    });
});

const CAPTIONS = {
    // "Scan (GRAI)" reads the same in both languages
    de_DE: { newLU: 'Neues Gebinde', current: 'Offenes Gebinde: ', complete: 'Fertigstellen', abort: 'Rückgängig', scanGrai: 'Scan (GRAI)' },
    en_US: { newLU: 'New LU', current: 'Current: ', complete: 'Complete', abort: 'Abort', scanGrai: 'Scan (GRAI)' },
};

for (const language of ['de_DE', 'en_US']) {
    // noinspection JSUnusedLocalSymbols
    test(`Button states: disabled before the pallet, enabled after, scan screen not offered after Schliessen (${language})`, async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan screen');
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

        await test.step('Open the job: no pallet yet -> button shown but disabled', async () => {
            await ApplicationsListScreen.startApplication('huConsolidation');
            await HUConsolidationJobsListScreen.waitForScreen();
            await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: captions.newLU });
            await HUConsolidationJobScreen.expectCompleteAndAbortCaptions({ complete: captions.complete, abort: captions.abort });
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: true, caption: captions.scanGrai });
        });
        await test.step('Choose a pallet -> button enabled, it opens the scan screen', async () => {
            await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: captions.current });
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false, caption: captions.scanGrai });
            await HUConsolidationJobScreen.clickScanGRAIButton();
        });
        await test.step('Scan crate 1, Back, close the pallet (Schliessen): button disabled', async () => {
            await GRAIScanScreen.scanGRAI({ grai: grai1 });
            await GRAIScanScreen.goBack();
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
            await HUConsolidationJobScreen.closeTargetLU();
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: captions.newLU });
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: true, caption: captions.scanGrai });
        });
        await test.step('Choose the next pallet -> button enabled again', async () => {
            await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: captions.current });
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false, caption: captions.scanGrai });
        });
    });
}

// noinspection JSUnusedLocalSymbols
test('Presence rule: a job whose slot holds only a pallet shows no button, the other job does', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    // Job 1 (BP1): slot1 holds only a pallet (the crate inside it carries a GRAI, but is not a top-level HU). Job 2 (BP2): a GRAI crate in slot2.
    const masterdata = await createJobScanMasterdata({
        pickTo: ['TU', 'LU_TU'],
        bpartners: { BP1: { graiRequired: 'Y' }, BP2: { graiRequired: 'Y' } },
        pickingSlots: { slot1: {}, slot2: {} },
        salesOrders: {
            SO1: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
            SO2: jobScanSalesOrder({ bpartner: 'BP2', product: 'P1', qty: 4, tu: 'TU1' }),
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

    await test.step('Pallet-only job: button hidden', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P2.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
        // never had the button: no blank gap where it would be
        await HUConsolidationJobScreen.expectFirstPickingSlotDirectlyBelowPalletButton();
        await HUConsolidationJobScreen.goBack();
    });
    await test.step('The other job\'s crate is untouched in its slot', async () => {
        await Backend.expect({
            title: 'pallet in slot1, other job crate still in slot2',
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
test('Partner on N, one crate with a GRAI (stamped via the HU Manager): button shown, scan moves it, slot screen follows the presence rule', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    const { SO1 } = masterdata.salesOrders;
    const grai1 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${Date.now()}1`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneCrateWithoutGrai({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, tuName: masterdata.packingInstructions.PI_P1.tuName, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    const { qrCodes } = await Backend.getPickingSlotCrates({ title: 'the crate sits in slot1', slotQrCode: slot1.qrCode, aliases: ['crate1'] });
    await stampGraiViaHUManager({ huQrCode: qrCodes.crate1, grai: grai1 });

    await test.step('Open the job, choose a pallet: partner is on N, the crate carries a GRAI -> button shown', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
    });
    await test.step('Before the scan: the slot screen shows the scanner although the partner is on N', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerVisible();
        await PickingSlotScreen.goBack();
    });
    await test.step('Scan (GRAI) moves the crate; it was the last GRAI crate, so the scan screen returns to the job screen by itself', async () => {
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.scanGRAI({ grai: grai1 });
        await GRAIScanScreen.expectClosedByItself();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await ErrorToast.expectNoErrorToast();
        await Backend.expect({
            title: 'the crate sits on the pallet',
            hus: { [await HUConsolidationJobScreen.getCurrentTargetLUQRCode()]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: grai1 } }] } },
        });
    });
    await test.step('After the scan: no GRAI HU in the slot, the reloaded slot screen shows no scanner', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerNotVisible();
        await PickingSlotScreen.goBack();
    });
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

/**
 * Fixture: a crate without GRAI (P2) picked while the partner is on 'N'; the partner is on 'Y' afterwards and the P1 crate is picked
 * under 'Y' with a GRAI scan (the button reads no partner flag).
 */
const pickCrateWithoutGraiThenGraiCrate = async ({ masterdata }) => {
    const { slot1 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneCrateWithoutGrai({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P2.tuName, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await Backend.updateBPartner({ graiRequired: 'Y' });
    const grai = graiWithSerial(PI_P1.grai, `${Date.now()}1`);
    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    return { grai };
};

// noinspection JSUnusedLocalSymbols
test('Two crates without GRAI (partner on Y at Verdichtung time): no button', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    await pickTwoCratesWithoutGrai({ masterdata });
    await Backend.updateBPartner({ graiRequired: 'Y' });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
    await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
});

// noinspection JSUnusedLocalSymbols
test('One GRAI crate + one without: button shown, gone after the GRAI crate is scanned, the other crate is moved by tap', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    const { grai } = await pickCrateWithoutGraiThenGraiCrate({ masterdata });
    let targetLUQRCode;
    const { context } = await Backend.getPickingSlotCrates({ title: 'two crates in slot1', slotQrCode: slot1.qrCode, aliases: ['crateNoGrai', 'crateGrai'] });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
    await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });

    await test.step('Scan the GRAI crate: the scan screen returns to the job screen by itself, button gone (no GRAI crate left)', async () => {
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.scanGRAI({ grai });
        await GRAIScanScreen.expectClosedByItself();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
        await ErrorToast.expectNoErrorToast();
        targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
        await Backend.expect({
            title: 'the scanned GRAI crate sits on the pallet',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: grai } }] } },
        });
    });
    await test.step('Tap the other crate in the slot screen: it moves', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateNoGrai });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await Backend.expect({
            title: 'both crates sit on the pallet (the P2 crate was picked first, the P1 crate carries the GRAI)',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P2: '5 PCE' } }, { storages: { P1: '4 PCE' }, attributes: { GRAI: grai } }] } },
        });
    });
});

/** Fixture: two GRAI crates (P1, P2) picked into slot1, the job of BP1 opened. */
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
test('Rückgängig -> Ja before any crate: back to the job list', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('normal');

    await pickTwoGraiCratesAndOpenJob();
    await HUConsolidationJobScreen.abort();
});

// noinspection JSUnusedLocalSymbols
test('Rückgängig -> Ja after one scanned crate: stays on the job screen, nothing changes', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('normal');

    const { masterdata, slot1, grai1 } = await pickTwoGraiCratesAndOpenJob();
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
    await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
    await HUConsolidationJobScreen.clickScanGRAIButton();
    await GRAIScanScreen.scanGRAI({ grai: grai1 });
    await GRAIScanScreen.goBack();
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
    await ErrorToast.expectNoErrorToast();
    const targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();

    await HUConsolidationJobScreen.abortRefused();
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
    await HUConsolidationJobScreen.expectTargetLUCaptionNotContains({ text: masterdata.packingInstructions.PI_P1.luName });
    await ErrorToast.expectNoErrorToast();
    await Backend.expect({
        title: 'the scanned crate is still on the pallet after the refused abort',
        hus: { [targetLUQRCode]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: grai1 } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Partner on N, one GRAI crate + one without: slot screen scanner moves the GRAI crate, then disappears; tap still works', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
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
            title: 'the scanned GRAI crate sits on the pallet',
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
            title: 'both crates sit on the pallet',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: graiP1 } }, { storages: { P2: '5 PCE' } }] } },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Crates without GRAI picked under N, partner set to Y afterwards: no slot screen scanner; tap still works', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    const masterdata = await createNoGraiPartnerMasterdata();
    const { slot1 } = masterdata.pickingSlots;
    const { context } = await pickTwoCratesWithoutGrai({ masterdata });
    await Backend.updateBPartner({ graiRequired: 'Y' });

    await openJobWithPallet({ masterdata });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });

    await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
    await PickingSlotScreen.expectScannerNotVisible();
    await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateP1 });
    await PickingSlotScreen.goBack();
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
    await Backend.expect({
        title: 'the tapped crate sits on the pallet',
        hus: { [await HUConsolidationJobScreen.getCurrentTargetLUQRCode()]: { tus: [{ storages: { P1: '4 PCE' } }] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Fallback: 3 GRAI crates + 1 crate without GRAI, the tap moves the crate without GRAI, the last scan hides the button', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    const masterdata = await createJobScanMasterdata({
        bpartners: { BP1: { graiRequired: 'N' } },
        pickingSlots: { slot1: {} },
        salesOrders: {
            SO1: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
            SO2: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
            SO3: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
            SO4: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
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
    await Backend.updateBPartner({ graiRequired: 'Y' });
    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: grai1, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai: grai2, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot1.qrCode, grai: grai3, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    const { context } = await Backend.getPickingSlotCrates({ title: 'four crates in slot1', slotQrCode: slot1.qrCode, aliases: ['crateNoGrai', 'crateG1', 'crateG2', 'crateG3'] });

    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
    await HUConsolidationJobScreen.setTargetLU({ lu: PI_P1.luName });
    await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 4 });

    await test.step('Scan 2 of the 3 GRAI crates, Back', async () => {
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.scanGRAI({ grai: grai1 });
        await GRAIScanScreen.scanGRAI({ grai: grai2 });
        await ErrorToast.expectNoErrorToast();
        await GRAIScanScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
    });
    await test.step('Slot row, tap the crate without GRAI: back on the job screen the button is still shown', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateNoGrai });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
    });
    await test.step('Open the scan screen, scan the last GRAI crate: the screen returns to the job screen by itself, slot empty, button hidden', async () => {
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.scanGRAI({ grai: grai3 });
        await GRAIScanScreen.expectClosedByItself();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: false });
        await ErrorToast.expectNoErrorToast();
    });

    const targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
    await Backend.expect({
        title: 'all 4 crates on the pallet (P1 2 x 4, P2 2 x 5)',
        hus: { [targetLUQRCode]: { storages: { P1: '8 PCE', P2: '10 PCE' } } },
        pickingSlots: { [slot1.qrCode]: { queue: [] } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Existing pallet: scan its QR code as target, then 2 GRAI scans put the crates onto it', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
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

    await test.step('Scan (GRAI): 2 crates onto the existing pallet, the scan screen stays open between the scans and closes after the last crate', async () => {
        await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.scanGRAI({ grai: grai1 });
        await GRAIScanScreen.expectVisible();
        await ErrorToast.expectNoErrorToast();
        await GRAIScanScreen.scanGRAI({ grai: grai2 });
        await GRAIScanScreen.expectClosedByItself();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        await ErrorToast.expectNoErrorToast();
    });

    await Backend.expect({
        title: 'both crates sit under the existing pallet (P2 100 PCE before + 5, P1 4)',
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
    test(`Duplicate GRAI: 2 crates of the job carry the scanned GRAI, message, nothing moves, the scan screen stays open (${language})`, async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan screen');
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
        // picking has no duplicate check without an LU, so the same GRAI is scanned for the SO1 crate (slot1) and the SO3 crate (slot2)
        await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: graiDuplicate, huQrCode: masterdata.handlingUnits.HU1.qrCode });
        await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai: graiOther, huQrCode: masterdata.handlingUnits.HU2.qrCode });
        await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot2.qrCode, grai: graiDuplicate, huQrCode: masterdata.handlingUnits.HU1.qrCode });

        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: PI_P1.luName });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });

        await test.step('Scan the GRAI that two crates carry: message, nothing moves, the scan screen stays open', async () => {
            await HUConsolidationJobScreen.expectScanGRAIButton({ visible: true, disabled: false });
            await HUConsolidationJobScreen.clickScanGRAIButton();
            await GRAIScanScreen.scanGRAIExpectError({ grai: graiDuplicate, message: AMBIGUOUS_MESSAGES[language] });
            await GRAIScanScreen.expectVisible();
            await GRAIScanScreen.goBack();
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 2 });
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot2.id, countHUs: 1 });
        });

        await Backend.getPickingSlotCrates({ title: 'slot1 keeps its 2 crates', slotQrCode: slot1.qrCode, aliases: ['slot1CrateA', 'slot1CrateB'] });
        await Backend.getPickingSlotCrates({ title: 'slot2 keeps its crate', slotQrCode: slot2.qrCode, aliases: ['slot2Crate'] });
    });
}

/**
 * The label print of the closed pallet fails as for the customer's Einwegpalette: the pallet type has no SSCC18 attribute
 * (PI_P2 gets its own LU, the default one is shared with PI_P1, which puts SSCC18 on it), and the SSCC label prints the empty SSCC.
 * Picks one crate, opens the job, chooses that pallet and scans the crate onto it; returns the masterdata and the pallet's QR code.
 */
const putCrateOnPalletWithoutSSCC18 = async () => {
    const masterdata = await createJobScanMasterdata({
        language: 'de_DE',
        packingInstructions: {
            PI_P1: { lu: 'LU', luSSCC18: true, qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
            PI_P2: { lu: 'LU_NO_SSCC', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5, graiMapping: true },
        },
        huLabelConfigs: { lc1: { bpartner: 'BP1', huUnitType: 'LU', sourceDocType: 'PI', process: HU_LABEL_PROCESS_ID, autoPrint: true } },
        sysconfigs: {
            'de.metas.handlingunit.GS1ManufacturerCode': '1234567',
            'de.metas.handlingunit.GS1ExtensionDigit': '0',
        },
    });
    const { slot1 } = masterdata.pickingSlots;
    const { SO2 } = masterdata.salesOrders;
    const { PI_P2 } = masterdata.packingInstructions;
    const grai = graiWithSerial(PI_P2.grai, `${Date.now()}1`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, grai, huQrCode: masterdata.handlingUnits.HU2.qrCode });

    let targetLUQRCode;
    await test.step('Open the job, choose a pallet without SSCC18, scan the crate onto it', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: PI_P2.luName });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        await GRAIScanScreen.scanGRAI({ grai });
        await GRAIScanScreen.expectClosedByItself();
        targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
    });
    return { masterdata, targetLUQRCode };
};

/**
 * The message shown when the pallet was closed (or the job completed) and shipped, but its label print failed
 * (AD_Messages ...PALLET_SHIPPED_LABEL_NOT_PRINTED + ...LABEL_NOT_PRINTED_REASON_PRINT_FAILED, de_DE): a short reason, never the technical error
 * (here the FNC1 error of the Jasper report).
 */
const expectPalletShippedPrintFailedMessage = (textContent) => {
    expect(textContent).toContain('Abgeschlossen, Lieferung wird erstellt. Etikett nicht gedruckt: Fehler beim Drucken.');
    expect(textContent).not.toContain('FNC1');
    expect(textContent).not.toContain('SysConfig');
};

// noinspection JSUnusedLocalSymbols
test('Schliessen of a pallet whose label print fails: error shown, pallet closed anyway and shipped', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    const { targetLUQRCode } = await putCrateOnPalletWithoutSSCC18();

    await test.step('Schliessen: the message says closed and shipped, label not printed (error while printing), AND the pallet is closed (no open pallet on the job screen)', async () => {
        await expectErrorToast('label print of the pallet without SSCC18 fails', async () => {
            await HUConsolidationJobScreen.clickLUTargetButton();
            await SelectHUConsolidationTargetScreen.clickCloseTargetButton();
        }, ({ textContent }) => {
            expectPalletShippedPrintFailedMessage(textContent);
        });
        // the error toast keeps the operator on the target screen; the pallet is closed: Back shows the job screen without an open pallet
        await SelectHUConsolidationTargetScreen.goBack();
        await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: 'Neues Gebinde' });
    });

    await Backend.expect({
        title: 'the closed pallet is shipped: shipment completed once the async shipment work package ran',
        hus: { [targetLUQRCode]: { isShipmentCompleted: true } },
    });
    await Backend.expect({
        title: 'the shipped pallet has HUStatus E (shipped)',
        hus: { [targetLUQRCode]: { huStatus: 'E' } },
    });
});

// noinspection JSUnusedLocalSymbols
test('Fertigstellen of a pallet whose label print fails: dialog says done and shipped, label not printed; job completed, pallet shipped', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    const { masterdata, targetLUQRCode } = await putCrateOnPalletWithoutSSCC18();
    const customerLocationId = masterdata.bpartners.BP1.bpartnerLocationId;

    await test.step('Fertigstellen -> Ja: a dialog says the job is done and shipped, label not printed (error while printing); OK shows the job list without the job', async () => {
        await HUConsolidationJobScreen.tapCompleteAndConfirm();
        expectPalletShippedPrintFailedMessage(await HUConsolidationJobScreen.expectCompletedWithErrorDialog());
        await HUConsolidationJobsListScreen.expectJobNotListedAfter({
            customerLocationId,
            trigger: () => HUConsolidationJobScreen.confirmCompletedWithErrorDialog(),
        });
    });

    await Backend.expect({
        title: 'the pallet is shipped although its label print failed: shipment completed once the async shipment work package ran',
        hus: { [targetLUQRCode]: { isShipmentCompleted: true } },
    });
    await Backend.expect({
        title: 'the shipped pallet has HUStatus E (shipped)',
        hus: { [targetLUQRCode]: { huStatus: 'E' } },
    });
});

/** Closing a pallet in mobile picking prints its auto-print label once (PickingJobService.closeLUAndTUPickingTargets -> printLULabels) */
const PALLET_LABELS_PRINTED_BY_PICKING = 1;

const GS1_SYSCONFIGS = {
    'de.metas.handlingunit.GS1ManufacturerCode': '1234567',
    'de.metas.handlingunit.GS1ExtensionDigit': '0',
};

/** The customer's settings (legacy picking label off, live scanner timing / manual input) + the GS1 code of the pallet's SSCC18. */
const CUSTOMER_SYSCONFIGS = { ...LIVE_DEFAULT_SYSCONFIGS, ...GS1_SYSCONFIGS };

/**
 * Picks GRAI crates (one of SO1, one of SO2 if `twoCrates`) into slot1, opens the ship-to job, chooses a new pallet and scans the crates onto it;
 * returns the pallet's QR code.
 */
const putCratesOnANewPallet = async ({ masterdata, twoCrates = false }) => {
    const { slot1 } = masterdata.pickingSlots;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;
    const serialBase = String(Date.now());
    const crates = [
        { documentNo: masterdata.salesOrders.SO1.documentNo, grai: graiWithSerial(PI_P1.grai, `${serialBase}1`), huQrCode: masterdata.handlingUnits.HU1.qrCode },
        ...(twoCrates ? [{ documentNo: masterdata.salesOrders.SO2.documentNo, grai: graiWithSerial(PI_P2.grai, `${serialBase}2`), huQrCode: masterdata.handlingUnits.HU2.qrCode }] : []),
    ];

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    for (const crate of crates) {
        await pickOneGraiCrate({ ...crate, slotQrCode: slot1.qrCode });
    }

    return await test.step(`Open the job, choose a new pallet, scan the ${crates.length} crate(s) onto it`, async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: PI_P1.luName });
        await HUConsolidationJobScreen.clickScanGRAIButton();
        for (const [index, crate] of crates.entries()) {
            await GRAIScanScreen.scanGRAI({ grai: crate.grai });
            if (index < crates.length - 1) {
                await GRAIScanScreen.expectVisible();
            }
        }
        await GRAIScanScreen.expectClosedByItself();
        return await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
    });
};

const putOneCrateOnANewPallet = async ({ masterdata }) => await putCratesOnANewPallet({ masterdata });

/**
 * The customer's label setup: the partner's pallet label process is not an HU process (no M_HU_Process row), there is no crate (TU) label config,
 * and the legacy picking label sysconfig is off.
 */
const createPalletLabelOnlyMasterdata = async () => await createJobScanMasterdata({
    language: 'de_DE',
    sysconfigs: CUSTOMER_SYSCONFIGS,
});

/** Exactly one label of the pallet label process, archived and in the printing queue; no label for any crate on it; the pallet is shipped. */
const expectPalletShippedWithItsLabelPrinted = async ({ targetLUQRCode, crateCount }) => {
    await Backend.expect({
        title: 'the pallet is shipped; exactly one label of the pallet label process is archived and in the printing queue (nothing for the crates)',
        hus: {
            [targetLUQRCode]: {
                bpartner: 'BP1',
                isShipmentCompleted: true,
                shippedTUCount: crateCount,
                archiveCount: 1,
                archiveProcessId: LU_LABEL_PROCESS_WITHOUT_HU_PROCESS_ID,
                printingQueueCount: 1,
                // the crates on the pallet: no label archived for any of them
                tus: Array.from({ length: crateCount }, () => ({ archiveCount: 0 })),
            },
        },
    });
    await Backend.expect({
        title: 'the shipped pallet has HUStatus E (shipped)',
        hus: { [targetLUQRCode]: { huStatus: 'E' } },
    });
};

test.describe('Pallet label configured, crate label not (the customer\'s label setup)', () => {
    // noinspection JSUnusedLocalSymbols
    test('Schliessen: no error, the pallet is closed and shipped, its label is printed although no label is configured for the crates on it', async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan screen');
        await allure.severity('critical');

        const masterdata = await createPalletLabelOnlyMasterdata();

        const targetLUQRCode = await putCratesOnANewPallet({ masterdata, twoCrates: true });

        await test.step('Schliessen: back on the job screen without an error, no open pallet', async () => {
            await HUConsolidationJobScreen.closeTargetLU();
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: 'Neues Gebinde' });
        });

        await expectPalletShippedWithItsLabelPrinted({ targetLUQRCode, crateCount: 2 });
    });

    // noinspection JSUnusedLocalSymbols
    test('Fertigstellen: no error, the pallet is shipped, its label is printed although no label is configured for the crates on it', async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan screen');
        await allure.severity('critical');

        const masterdata = await createPalletLabelOnlyMasterdata();
        const customerLocationId = masterdata.bpartners.BP1.bpartnerLocationId;

        const targetLUQRCode = await putCratesOnANewPallet({ masterdata, twoCrates: true });

        await test.step('Fertigstellen -> Ja: back on the job list without an error, ship-to gone', async () => {
            await HUConsolidationJobsListScreen.expectJobNotListedAfter({
                customerLocationId,
                trigger: () => HUConsolidationJobScreen.complete(),
            });
        });

        await expectPalletShippedWithItsLabelPrinted({ targetLUQRCode, crateCount: 2 });
    });
});

// The customer's handheld (Zebra TC22)
test.describe('Pallet without any label config, on the customer\'s handheld', () => {
    test.use({ viewport: { width: 411, height: 823 }, deviceScaleFactor: 2.625 });

    // noinspection JSUnusedLocalSymbols
    test('Schliessen of a pallet without any label config: silent (no error), pallet closed and shipped, as in picking', async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan screen');
        await allure.severity('critical');

        const masterdata = await createJobScanMasterdata({
            language: 'de_DE',
            huLabelConfigs: {},
            sysconfigs: CUSTOMER_SYSCONFIGS,
        });

        const targetLUQRCode = await putOneCrateOnANewPallet({ masterdata });

        await test.step('Schliessen: back on the job screen without an error, no open pallet', async () => {
            await HUConsolidationJobScreen.closeTargetLU();
            await HUConsolidationJobScreen.expectTargetLUCaptionStartsWith({ text: 'Neues Gebinde' });
            await ErrorToast.expectNoErrorToast();
        });

        await Backend.expect({
            title: 'the closed pallet is shipped, no label archived',
            hus: { [targetLUQRCode]: { isShipmentCompleted: true, archiveCount: 0 } },
        });
        await Backend.expect({
            title: 'the shipped pallet has HUStatus E (shipped)',
            hus: { [targetLUQRCode]: { huStatus: 'E' } },
        });
    });

    // noinspection JSUnusedLocalSymbols
    test('Fertigstellen with a pallet without any label config: silent (no error), the pallet is shipped, back on the job list without the job', async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan screen');
        await allure.severity('critical');

        const masterdata = await createJobScanMasterdata({
            language: 'de_DE',
            huLabelConfigs: {},
            sysconfigs: CUSTOMER_SYSCONFIGS,
        });
        const customerLocationId = masterdata.bpartners.BP1.bpartnerLocationId;

        const targetLUQRCode = await putOneCrateOnANewPallet({ masterdata });

        await test.step('Fertigstellen -> Ja: back on the job list without an error, ship-to gone', async () => {
            await HUConsolidationJobsListScreen.expectJobNotListedAfter({
                customerLocationId,
                trigger: () => HUConsolidationJobScreen.complete(),
            });
        });

        await Backend.expect({
            title: 'the pallet is shipped, no label archived',
            hus: { [targetLUQRCode]: { isShipmentCompleted: true, archiveCount: 0 } },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Pallet-only picking (the customer\'s profile): the picked pallet is scanned as target, Fertigstellen prints its label and ships it', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    // The customer's picking profile: pick to pallets only (LU with crates), listed customers only, per sales order, always split
    const masterdata = await createJobScanMasterdata({
        language: 'de_DE',
        pickTo: ['LU_TU'],
        pickingProfile: {
            allowPickingAnyCustomer: false,
            customers: [{ customer: 'BP1' }],
            aggregationType: 'sales_order',
            alwaysSplitHUsEnabled: true,
        },
        sysconfigs: CUSTOMER_SYSCONFIGS,
    });
    const { slot1 } = masterdata.pickingSlots;
    const { PI_P1 } = masterdata.packingInstructions;
    const customerLocationId = masterdata.bpartners.BP1.bpartnerLocationId;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOnePallet({
        documentNo: masterdata.salesOrders.SO1.documentNo,
        slotQrCode: slot1.qrCode,
        luName: PI_P1.luName,
        crateGrai: graiWithSerial(PI_P1.grai, `${Date.now()}1`),
        huQrCode: masterdata.handlingUnits.HU1.qrCode,
    });
    const { qrCodes } = await Backend.getPickingSlotCrates({ title: 'slot1 holds the picked pallet', slotQrCode: slot1.qrCode, aliases: ['pallet1'] });

    await test.step('Open the job, scan the picked pallet as target, Fertigstellen -> Ja: back on the job list without an error', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ qrCode: qrCodes.pallet1 });
        await HUConsolidationJobsListScreen.expectJobNotListedAfter({
            customerLocationId,
            trigger: () => HUConsolidationJobScreen.complete(),
        });
    });

    await Backend.expect({
        title: 'the pallet is shipped; its labels: one when it was closed in picking, one more at Fertigstellen, both in the printing queue',
        hus: {
            [qrCodes.pallet1]: {
                bpartner: 'BP1',
                isShipmentCompleted: true,
                archiveCount: PALLET_LABELS_PRINTED_BY_PICKING + 1,
                archiveProcessId: LU_LABEL_PROCESS_WITHOUT_HU_PROCESS_ID,
                printingQueueCount: PALLET_LABELS_PRINTED_BY_PICKING + 1,
            },
        },
    });
});

// The customer's handheld (Zebra TC22)
test.describe('Pallet type that does not take the crate type, on the customer\'s handheld', () => {
    test.use({ viewport: { width: 411, height: 823 }, deviceScaleFactor: 2.625 });

    // noinspection JSUnusedLocalSymbols
    test('A pallet type that does not take the crate type: clear message, nothing moves; another pallet type takes the crate', async ({ page: _page }) => {
        // === ALLURE METADATA ===
        await allure.epic('E0105: Picking');
        await allure.tag('F00248');
        await allure.story('HU Consolidation - GRAI scan screen');
        await allure.severity('critical');

        // LU_OTHER is offered for BP1 (it carries TU3 of P1), but has no item for TU1: like a one-way pallet that does not take the customer's crate
        const masterdata = await createJobScanMasterdata({
            language: 'de_DE',
            packingInstructions: {
                PI_P1: { lu: 'LU', luSSCC18: true, qtyTUsPerLU: 20, tu: 'TU1', product: 'P1', qtyCUsPerTU: 4, graiMapping: true },
                PI_P2: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU2', product: 'P2', qtyCUsPerTU: 5, graiMapping: true },
                PI_P3: { lu: 'LU_OTHER', qtyTUsPerLU: 20, tu: 'TU3', product: 'P1', qtyCUsPerTU: 4 },
            },
            sysconfigs: CUSTOMER_SYSCONFIGS,
        });
        const { slot1 } = masterdata.pickingSlots;
        const { PI_P1, PI_P3 } = masterdata.packingInstructions;
        const grai = graiWithSerial(PI_P1.grai, `${Date.now()}1`);

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await pickOneGraiCrate({ documentNo: masterdata.salesOrders.SO1.documentNo, slotQrCode: slot1.qrCode, grai, huQrCode: masterdata.handlingUnits.HU1.qrCode });

        await test.step('Choose the pallet type that does not take the crate, scan the crate: message naming both types, the crate stays in its slot', async () => {
            await ApplicationsListScreen.startApplication('huConsolidation');
            await HUConsolidationJobsListScreen.waitForScreen();
            await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
            await HUConsolidationJobScreen.setTargetLU({ lu: PI_P3.luName });
            await HUConsolidationJobScreen.clickScanGRAIButton();
            await GRAIScanScreen.scanGRAIExpectError({
                grai,
                message: `Palettentyp ${PI_P3.luName} nimmt Kistentyp ${PI_P1.tuName} nicht auf.`,
                screenshotName: 'pallet-type-refuses-crate-message',
            });
            await GRAIScanScreen.goBack();
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 1 });
        });

        await test.step('Close the empty pallet, choose the pallet type that takes the crate: the scan moves the crate', async () => {
            await HUConsolidationJobScreen.closeTargetLU();
            await HUConsolidationJobScreen.setTargetLU({ lu: PI_P1.luName });
            await HUConsolidationJobScreen.clickScanGRAIButton();
            await GRAIScanScreen.scanGRAI({ grai });
            await GRAIScanScreen.expectClosedByItself();
            await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
        });
    });
});

/** Tones of the scanner beeps (utils/audio.js: OK 1000 Hz, error 100 Hz; BarcodeScannerComponent.jsx okBeepParams / errorBeepParams) */
const OK_BEEP_HZ = 1000;
const ERROR_BEEP_HZ = 100;
const SCANNER_ERROR_TOAST_ID = 'BarcodeScannerComponentError';

// noinspection JSUnusedLocalSymbols
test('Slot screen: a refused GRAI scan plays the error beep with the scanner\'s one error message, an accepted scan the OK beep', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    // the slot screen's scan handler must not swallow the error, else the scanner plays the OK beep for a refused scan
    const masterdata = await createJobScanMasterdata({
        language: 'de_DE',
        sysconfigs: { 'mobileui.frontend.barcodeScanner.scanDuplicatesIntervalMillis': '0' },
        pickingSlots: { slot1: {}, slot2: {} },
    });
    const { slot1, slot2 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;
    const serialBase = String(Date.now());
    const graiInSlot1 = graiWithSerial(masterdata.packingInstructions.PI_P1.grai, `${serialBase}1`);
    const graiInSlot2 = graiWithSerial(masterdata.packingInstructions.PI_P2.grai, `${serialBase}2`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: graiInSlot1, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot2.qrCode, grai: graiInSlot2, huQrCode: masterdata.handlingUnits.HU2.qrCode });

    await test.step('Open the job, choose a pallet, open slot1 (holds a GRAI crate: the scanner shows)', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerVisible();
        await BarcodeScannerComponent.startRecordingBeepTones();
    });

    /** the refused scan: exactly one error message box that carries the scanner's toast id, and the error beep (no OK beep) */
    const scanRefused = async (title, barcode, expectedText) => await test.step(`Refused: ${title}`, async () => {
        await expectErrorToast(title, async () => {
            await PickingSlotScreen.scanGRAI({ graiString: barcode });
            await PickingSlotScreen.waitForScreen();
        }, async ({ toast, textContent }) => {
            expect(textContent).toContain(expectedText);
            await ErrorToast.expectToastId(toast, SCANNER_ERROR_TOAST_ID);
        });
        const tones = await BarcodeScannerComponent.takeBeepTones();
        expect(tones, `beep tones played for ${title}`).toContain(ERROR_BEEP_HZ);
        expect(tones, `beep tones played for ${title}`).not.toContain(OK_BEEP_HZ);
    });

    await scanRefused('EAN-13', '4006381333931', 'Der gescannte Barcode ist kein gültiger GRAI.');
    await scanRefused('GRAI of the crate in slot2 (not in this slot)', graiInSlot2, 'Die HU befindet sich nicht am Kommissionierplatz');

    await test.step('Nothing moved by the refused scans', async () => {
        await Backend.expect({
            title: 'both crates are still in their slots',
            pickingSlots: {
                [slot1.qrCode]: { queue: [{ hu: 'crate1' }] },
                [slot2.qrCode]: { queue: [{ hu: 'crate2' }] },
            },
        });
    });

    await test.step('Accepted: GRAI of the crate in this slot -> OK beep, no message, crate moved', async () => {
        await PickingSlotScreen.scanGRAI({ graiString: graiInSlot1 });
        await BarcodeScannerComponent.waitForBeepTones();
        const tones = await BarcodeScannerComponent.takeBeepTones();
        expect(tones).toContain(OK_BEEP_HZ);
        expect(tones).not.toContain(ERROR_BEEP_HZ);
        await ErrorToast.expectNoErrorToast();
        await PickingSlotScreen.waitNotLoading();
        await PickingSlotScreen.goBack();
        const targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
        await Backend.expect({
            title: 'the accepted crate sits on the pallet, the refused crate is still in slot2',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P1: '4 PCE' }, attributes: { GRAI: graiInSlot1 } }] } },
            pickingSlots: { [slot2.qrCode]: { queue: [{ hu: 'crate2' }] } },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Slot screen, duplicate GRAI: 2 crates of the slot carry it, the duplicate message, nothing moves; a crate of another job with the same GRAI never blocks; a GRAI only outside the job is not at the picking slot, unless only a crate inside a pallet carries it', async ({ page: _page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0105: Picking');
    await allure.tag('F00248');
    await allure.story('HU Consolidation - GRAI scan screen');
    await allure.severity('critical');

    // the slot screen finds a scanned GRAI among the crates of its job, as the scan screen does: two crates of the job with that GRAI -> the duplicate
    // message; a crate with that GRAI outside the job is not looked at
    const masterdata = await createJobScanMasterdata({
        language: 'de_DE',
        sysconfigs: { 'mobileui.frontend.barcodeScanner.scanDuplicatesIntervalMillis': '0' },
        pickTo: ['TU', 'LU_TU'],
        bpartners: { BP1: { graiRequired: 'Y' }, BP2: { graiRequired: 'Y' } },
        pickingSlots: { slot1: {}, slot2: {}, slot3: {}, slot4: {} },
        salesOrders: {
            SO1: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
            SO2: jobScanSalesOrder({ bpartner: 'BP1', product: 'P2', qty: 5, tu: 'TU2' }),
            SO3: jobScanSalesOrder({ bpartner: 'BP1', product: 'P1', qty: 4, tu: 'TU1' }),
            SO4: jobScanSalesOrder({ bpartner: 'BP2', product: 'P2', qty: 5, tu: 'TU2' }),
            SO5: jobScanSalesOrder({ bpartner: 'BP2', product: 'P2', qty: 5, tu: 'TU2' }),
        },
    });
    const { slot1, slot2, slot3, slot4 } = masterdata.pickingSlots;
    const { SO1, SO2, SO3, SO4, SO5 } = masterdata.salesOrders;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;
    const serialBase = String(Date.now());
    const graiDuplicate = graiWithSerial(PI_P1.grai, `${serialBase}1`);
    const graiAlsoInOtherJob = graiWithSerial(PI_P2.grai, `${serialBase}2`);
    const graiInOtherJobsPallet = graiWithSerial(PI_P2.grai, `${serialBase}3`);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    // picking has no duplicate check without an LU, so the same GRAI is scanned for two crates of slot1, and for the slot2 crate and the crate of BP2's job
    await pickOneGraiCrate({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, grai: graiDuplicate, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO3.documentNo, slotQrCode: slot1.qrCode, grai: graiDuplicate, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneGraiCrate({ documentNo: SO2.documentNo, slotQrCode: slot2.qrCode, grai: graiAlsoInOtherJob, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    await pickOneGraiCrate({ documentNo: SO4.documentNo, slotQrCode: slot3.qrCode, grai: graiAlsoInOtherJob, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    // a crate inside a pallet is no crate of any job (only top-level HUs are)
    await pickOnePallet({ documentNo: SO5.documentNo, slotQrCode: slot4.qrCode, luName: PI_P2.luName, crateGrai: graiInOtherJobsPallet, huQrCode: masterdata.handlingUnits.HU2.qrCode });

    await test.step('Open the job of BP1, choose a pallet, open slot1 (holds the 2 crates with the same GRAI)', async () => {
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId });
        await HUConsolidationJobScreen.setTargetLU({ lu: PI_P1.luName });
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerVisible();
        await BarcodeScannerComponent.startRecordingBeepTones();
    });

    await test.step('Scan the GRAI that both crates of slot1 carry: the duplicate message in the scanner\'s message box, error beep', async () => {
        await expectErrorToast('GRAI carried by 2 crates of slot1', async () => {
            await PickingSlotScreen.scanGRAI({ graiString: graiDuplicate });
            await PickingSlotScreen.waitForScreen();
        }, async ({ toast, textContent }) => {
            expect(textContent).toContain(AMBIGUOUS_MESSAGES.de_DE);
            await ErrorToast.expectToastId(toast, SCANNER_ERROR_TOAST_ID);
            await attachScreenshot('slot-screen-duplicate-GRAI-message');
        });
        const tones = await BarcodeScannerComponent.takeBeepTones();
        expect(tones).toContain(ERROR_BEEP_HZ);
        expect(tones).not.toContain(OK_BEEP_HZ);
    });

    await Backend.getPickingSlotCrates({ title: 'slot1 keeps its 2 crates', slotQrCode: slot1.qrCode, aliases: ['slot1CrateA', 'slot1CrateB'] });

    await test.step('Open slot2: its crate carries a GRAI that a crate of BP2\'s job carries too; the scan moves the slot2 crate onto the pallet', async () => {
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot2.id });
        await PickingSlotScreen.scanGRAIAndWaitConsolidated({ graiString: graiAlsoInOtherJob });
        await attachScreenshot('slot-screen-GRAI-also-outside-job-crate-moved');
        await PickingSlotScreen.goBack();
        const targetLUQRCode = await HUConsolidationJobScreen.getCurrentTargetLUQRCode();
        await Backend.expect({
            title: 'the slot2 crate sits on the pallet; the crate of BP2\'s job is still in slot3; slot1 unchanged',
            hus: { [targetLUQRCode]: { tus: [{ storages: { P2: '5 PCE' }, attributes: { GRAI: graiAlsoInOtherJob } }] } },
            pickingSlots: {
                [slot2.qrCode]: { queue: [] },
                [slot3.qrCode]: { queue: [{ hu: 'bp2Crate' }] },
            },
        });
    });

    await test.step('Open slot1 again, scan the GRAI that now only the crate of BP2\'s job carries (the slot2 crate is inside the pallet): not at the picking slot, nothing moves', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerVisible();
        await expectErrorToast('GRAI carried only by a crate outside the job', async () => {
            await PickingSlotScreen.scanGRAI({ graiString: graiAlsoInOtherJob });
            await PickingSlotScreen.waitForScreen();
        }, async ({ toast, textContent }) => {
            expect(textContent).toContain('Die HU befindet sich nicht am Kommissionierplatz');
            await ErrorToast.expectToastId(toast, SCANNER_ERROR_TOAST_ID);
            await attachScreenshot('slot-screen-GRAI-only-outside-job-not-at-slot');
        });
        await Backend.expect({
            title: 'slot1 keeps its 2 crates, the crate of BP2\'s job is still in slot3',
            pickingSlots: {
                [slot1.qrCode]: { queue: [{ hu: 'slot1CrateA' }, { hu: 'slot1CrateB' }] },
                [slot3.qrCode]: { queue: [{ hu: 'bp2Crate' }] },
            },
        });
    });

    await test.step('Still on slot1: scan the GRAI that only a crate inside the pallet of BP2\'s job carries: no HU found, nothing moves', async () => {
        await expectErrorToast('GRAI carried only by a crate inside a pallet outside the job', async () => {
            await PickingSlotScreen.scanGRAI({ graiString: graiInOtherJobsPallet });
            await PickingSlotScreen.waitForScreen();
        }, async ({ toast, textContent }) => {
            expect(textContent).toContain('Keine HU für diesen QR-Code gefunden');
            await ErrorToast.expectToastId(toast, SCANNER_ERROR_TOAST_ID);
            await attachScreenshot('slot-screen-GRAI-only-in-pallet-outside-job-no-HU-found');
        });
        await Backend.expect({
            title: 'slot1 keeps its 2 crates, the pallet of BP2\'s job is still in slot4',
            pickingSlots: {
                [slot1.qrCode]: { queue: [{ hu: 'slot1CrateA' }, { hu: 'slot1CrateB' }] },
                [slot4.qrCode]: { queue: [{ hu: 'bp2Pallet' }] },
            },
        });
        await Backend.expect({
            title: 'the scanned GRAI is stored on the crate inside the pallet of BP2\'s job',
            hus: { bp2Pallet: { tus: [{ attributes: { GRAI: graiInOtherJobsPallet } }] } },
        });
    });
});
