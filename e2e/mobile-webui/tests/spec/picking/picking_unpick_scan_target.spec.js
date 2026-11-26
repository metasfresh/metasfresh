import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

// Whole-step unpick + scan a target HU must physically move the previously-picked goods onto the
// scanned target HU. Before this fix, it failed: PickingJobUnPickCommand forwarded the stale
// picked-CU (huId, QR) snapshot to MoveHUCommand after its own extractToTopLevel had already
// relocated the QR, so HUQRCodesService.assertQRCodeAssignedToHU threw HTTP 422
// "QR Code ... is not assigned to HU ..." and the goods never landed on the target.
//
// The target HU (HU2) must be an LU built from the SAME packing instruction as the picked HU:
//   - Only an LU accepts the extracted top-level TU (moving into a TU/CU target fails).
//   - The LU's TU child must be the SAME TU packing instruction as the picked HU's, else stacking
//     is rejected ("cannot stack TU ... no link between them"); the masterdata API mints a fresh TU
//     PI per packingInstructions entry, so the only LU that can receive the picked TU is one built
//     from PI itself.
//   - A PI-backed HU is force-filled to the PI's full capacity, so HU2 starts at 20 TU x 4 = 80 PCE.
// Hence the target ends at its starting 80 PCE + the whole-step unpicked qty (12 PCE) = 92 PCE.
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
                // HU2: the target LU the whole picked step is unpicked into (same PI as HU1, starts at 80 PCE).
                "HU2": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                }
            },
        }
    });
};

const loginAndStartJob = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    return { pickingJobId };
};

// noinspection JSUnusedLocalSymbols
test('Unpick whole step + scan target HU — goods land on the scanned target', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section
    allure.story('Unpick a whole step and scan a target HU');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const targetHUQRCode = masterdata.handlingUnits.HU2.qrCode;

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step("Pick all 3 TU (12 PCE) from HU1", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
        // Registers the dynamically-created `lu1` HU in the backend test context so the assertion
        // below can refer to it by identifier.
        await Backend.expect({
            title: 'after full pick: 12 PCE packed on lu1, target HU2 still at its starting 80 PCE',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
                [targetHUQRCode]: { storages: { P1: '80 PCE' } },
            }
        });
    });

    await test.step("Unpick the whole step and scan target HU2 — goods must move onto HU2", async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickStepButton({ index: 0 });
        await PickingJobStepScreen.unpick({ targetHUQRCode });
        await PickingJobLineScreen.goBack();
    });

    // Commit-confirming, user-visible signal: the line falls back to 0 TU picked once the unpick has
    // committed (guards the backend read below against reading pre-commit state).
    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    // The end result: the previously-picked 12 PCE now physically reside on the scanned target HU2
    // (its starting 80 PCE + the unpicked 12 PCE = 92 PCE), and the picked LU lu1 is emptied.
    await Backend.expect({
        title: 'after unpick + scan target: 12 PCE moved onto target HU2 (80 + 12 = 92), lu1 emptied',
        hus: {
            lu1: { storages: { P1: '0 PCE' } },
            [targetHUQRCode]: { storages: { P1: '92 PCE' } },
        }
    });
});
