/**
 * Shared flows of the HU Consolidation "Scan (GRAI)" job-screen specs (picking the crates / pallets that
 * Verdichtung then consolidates, stamping a GRAI via the HU Manager, GRAI string helpers).
 */
import { test } from '../../playwright.config';
import { Backend } from './screens/Backend';
import { ApplicationsListScreen } from './screens/ApplicationsListScreen';
import { PickingJobsListScreen } from './screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from './screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from './screens/picking/PickingJobLineScreen';
import { PickLineScanScreen } from './screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from './screens/picking/GetQuantityDialog';
import { PickGraiScreen } from './screens/picking/PickGraiScreen';
import { PickingGraiScanPanel } from './screens/picking/PickingGraiScanPanel';
import { SelectPickTargetTUScreen } from './screens/picking/SelectPickTargetTUScreen';
import { HUManagerScreen } from './screens/huManager/HUManagerScreen';
import { GRAIScreen } from './screens/huManager/GRAIScreen';
import { ID_BACK_BUTTON, page } from './common';

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
export const graiWithSerial = (piGrai, serial) => {
    const [companyPrefix, assetType] = piGrai.split('.');
    return `${companyPrefix}.${assetType}.${serial}`;
};

/** The same GRAI as GS1 AI 8003 barcode: 8003 + padding 0 + companyPrefix + assetType + check digit + serial. */
export const graiAs8003 = (canonicalGrai) => {
    const [companyPrefix, assetType, serial] = canonicalGrai.split('.');
    const assetReference = `0${companyPrefix}${assetType}`;
    return `8003${assetReference}${gs1CheckDigit(assetReference)}${serial}`;
};

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
    if (await page.locator('button').filter({ hasText: tuName }).count() === 0) {
        targetButton = 'fallback: first target button';
        await page.locator('#SelectPickTargetScreen button').first().tap();
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
    await page.locator(ID_BACK_BUTTON).tap();
    await ApplicationsListScreen.expectVisible();
});

/** Registers the picking slot's queue under the given HU aliases and returns { context, qrCodes } (QR code per alias). */
export const readSlotCrates = async ({ title, slotQrCode, aliases }) => {
    const { context } = await Backend.expect({
        title,
        pickingSlots: { [slotQrCode]: { queue: aliases.map((hu) => ({ hu })) } },
    });
    const qrCodes = {};
    for (const alias of aliases) qrCodes[alias] = await Backend.getHUQRCodeByIdentifier({ identifier: alias });
    return { context, qrCodes };
};
