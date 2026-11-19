/**
 * TL;DR — In HU Consolidation, the GRAI scanner on the picking-slot screen is shown by what the crates in the slot carry
 * (a GRAI), no longer by the partner's GRAIRequired flag. More job-scan scenarios (fallback, undo, existing pallet,
 * duplicate GRAI) join this file in later tasks.
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
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { PickingSlotScreen } from '../../utils/screens/huConsolidation/PickingSlotScreen';
import { createNoGraiPartnerMasterdata } from '../../utils/huConsolidationJobScanMasterdata';
import { graiWithSerial, pickOneCrateWithoutGrai, readSlotCrates, stampGraiViaHUManager } from '../../utils/huConsolidationJobScanFlows';

/** Picks the P1 crate (SO1) and the P2 crate (SO2) into slot1 while the partner is on 'N'; returns their ids / QR codes. */
const pickTwoCratesWithoutGrai = async ({ masterdata }) => {
    const { slot1 } = masterdata.pickingSlots;
    const { SO1, SO2 } = masterdata.salesOrders;
    const { PI_P1, PI_P2 } = masterdata.packingInstructions;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await pickOneCrateWithoutGrai({ documentNo: SO1.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P1.tuName, huQrCode: masterdata.handlingUnits.HU1.qrCode });
    await pickOneCrateWithoutGrai({ documentNo: SO2.documentNo, slotQrCode: slot1.qrCode, tuName: PI_P2.tuName, huQrCode: masterdata.handlingUnits.HU2.qrCode });
    return await readSlotCrates({ title: 'two crates without GRAI in slot1', slotQrCode: slot1.qrCode, aliases: ['crateP1', 'crateP2'] });
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
    });
    await test.step('Reloaded slot screen: no scanner (the remaining crate has no GRAI); tap still moves it', async () => {
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: slot1.id });
        await PickingSlotScreen.expectScannerNotVisible();
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.crateP2 });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.expectPickingSlotHUCount({ pickingSlotId: slot1.id, countHUs: 0 });
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
});
