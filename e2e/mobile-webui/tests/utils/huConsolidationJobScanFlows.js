import { test } from '../../playwright.config';
import { ApplicationsListScreen } from './screens/ApplicationsListScreen';
import { PickingJobsListScreen } from './screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from './screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from './screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from './screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from './screens/picking/GetQuantityDialog';
import { PickingGraiScanPanel } from './screens/picking/PickingGraiScanPanel';
import { SelectPickTargetTUScreen } from './screens/picking/SelectPickTargetTUScreen';
import { PickGraiScreen } from './screens/picking/PickGraiScreen';
import { HUManagerScreen } from './screens/huManager/HUManagerScreen';
import { GRAIScreen } from './screens/huManager/GRAIScreen';
import { graiAs8003 } from './grai';

/** Flows shared by the HU Consolidation job-screen GRAI scan specs: picking crates / pallets into a slot, stamping a GRAI on a crate. */

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
    await SelectPickTargetTUScreen.clickTUButton({ tu: tuName });
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

/**
 * Picks one pallet (LU with one GRAI-captured crate inside) into the given picking slot: job-level LU target,
 * line, source HU scan, quantity (1 TU) confirmed with "OK und LU schliessen", inline GRAI capture, Save, complete.
 * Closing the LU queues the pallet in the picking slot (as pick_by_ExternalBarcode.spec.js: an open LU is not queued).
 */
export const pickOnePallet = async ({ documentNo, slotQrCode, luName, crateGrai, huQrCode }) => await test.step(`Pick a pallet with crate ${crateGrai} of ${documentNo} into its slot`, async () => {
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
