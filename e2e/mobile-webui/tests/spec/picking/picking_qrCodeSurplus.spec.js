import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";

/**
 * Mobile picking must TOLERATE an aggregate HU that carries MORE active QR-code assignments
 * than its current TU count (a "surplus").
 *
 * Real-world mechanism:
 *  - QR codes are generated one-per-TU for an aggregate HU at its TU count AT GENERATION TIME (the desktop
 *    "Print Labels" / M_HU_Report_QRCode process runs huQRCodesService.generateForExistingHUs on the selected
 *    HU while it stays Active), and are NEVER trimmed when TUs are later split/picked out.
 *  - So once full-count codes are generated on an aggregate and some TUs leave it, the active QR-code count
 *    exceeds the current TU count.
 *  - A later pick of the whole remaining aggregate reuses that same source HU (HUTransformService.luExtractTUs
 *    returns the source LU as-is when the requested qty equals its full TU count), so
 *    PickingJobPickCommand#toPickingJobStepPickedToHU reads the source's surplus codes via
 *    getOrCreateQRCodesByHuId and, pre-fix, hard-failed the `#codes == qtyTU` equality check with
 *    "Erwartet {0} QR-Codes, aber nur {1} erhalten".
 *  - The shipped fix relaxed the guard (PickingJobPickCommand.assertEnoughQRCodes) to
 *    error only on a DEFICIT (`< qtyTU`) and to consume only the first N codes — so a surplus is tolerated.
 *
 * This spec reproduces the surplus faithfully, entirely through the mobile UI + masterdata API:
 *   1. Masterdata builds an aggregate LU of 10 TUs (100 CU / 10 CU-per-TU) and generates full-count QR codes on it
 *      while it stays Active (`generateHUQRCodesForAllTUs` — mirrors Print Labels): 10 active QR-code assignments.
 *   2. Masterdata then splits 4 whole TUs OUT of the aggregate via a NON-picking repack
 *      (`splitOutTUsCountAfterQRCodes: 4` — HUTransformService.tuToNewTUs, the qty-decrease-without-QR-cleanup path
 *      a real repack uses). The source aggregate is left Active with 6 TUs but STILL 10 QR-code assignments ->
 *      SURPLUS (10 codes vs 6 TUs). This is the exact real-world sequence: generate labels at the high-water TU
 *      count, then repack fewer TUs.
 *   3. A mobile pick of the whole remaining 6-TU aggregate reads the source's 10 surplus codes against its 6 TUs
 *      (HUTransformService.luExtractTUs returns the source aggregate as-is when the requested qty equals its full
 *      TU count, so PickingJobPickCommand#toPickingJobStepPickedToHU calls getOrCreateQRCodesByHuId on that source).
 *      Pre-fix this threw "Erwartet 6 QR-Codes, aber nur 10 erhalten"; with the fix it SUCCEEDS (uses the first 6).
 */
test('Picking tolerates surplus QR codes on an aggregate LU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Picking tolerates surplus QR-code assignments on an aggregate');
    allure.severity('critical');

    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
                    allowSkippingRejectedReason: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                // aggregate LU: 10 TUs per LU, 10 CU per TU => a 10-TU / 100-CU aggregate LU
                "PI1": { lu: "LU", qtyTUsPerLU: 10, tu: "TU1", product: "P1", qtyCUsPerTU: 10 },
            },
            handlingUnits: {
                // One aggregate LU of 10 TUs (10 TU x 10 CU = 100 CU). Generate one QR code per TU on it WHILE it
                // stays Active (the "Print Labels on the full pallet" step) -> 10 codes; then split 4 TUs out via a
                // NON-picking repack, leaving 6 TUs but keeping all 10 codes -> a 10-code / 6-TU surplus.
                // (qty is NOT set: with finite-capacity packingInstructions the total qty comes from the PI.)
                "HU1": {
                    product: 'P1',
                    warehouse: 'wh',
                    packingInstructions: 'PI1',
                    generateHUQRCodesForAllTUs: true,
                    splitOutTUsCountAfterQRCodes: 4,
                },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        // Order the 6 TUs (60 CU) that remain on the surplus-bearing aggregate.
                        { product: 'P1', qty: 60, piItemProduct: 'TU1' },
                    ]
                }
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });

    await test.step("Pick the whole remaining 6-TU aggregate carrying a 10-code surplus -> must NOT fail", async () => {
        // This is the guarded pick: PickingJobPickCommand.toPickingJobStepPickedToHU reads getOrCreateQRCodesByHuId
        // on the source aggregate (10 active codes) against its current 6 TUs. Pre-fix threw
        // "Erwartet 6 QR-Codes, aber nur 10 erhalten"; the fix tolerates the surplus and uses only the first 6 codes.
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '6',
        });
        await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '6 TU', qtyPicked: '6 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Verify backend state after the surplus-tolerant pick", async () => {
        // The surplus-bearing source pallet HU1 is fully emptied — its remaining 6 TUs (60 PCE) were picked out of
        // it via the guarded pick against its 10-code surplus. Had that pick failed with the "Erwartet ... erhalten"
        // error (pre-fix), HU1 would still hold the un-picked 60 PCE.
        await Backend.expect({
            hus: {
                HU1: { storages: {} },
            }
        });
    });

    // Completing the job succeeds (the picked goods were accepted despite the surplus source).
    await PickingJobScreen.complete();
});
