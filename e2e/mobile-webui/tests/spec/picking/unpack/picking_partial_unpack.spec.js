import { test } from "../../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../../utils/screens/Backend";
import { LoginScreen } from "../../../utils/screens/LoginScreen";
import { expectErrorToast } from "../../../utils/common";

// A valid 14-digit GS1 GTIN, made unique per run so the strict GTIN->product resolve
// (ProductDAO.getProductIdByGTINStrictly, AD_Client_ID=METASFRESH) matches EXACTLY one product.
// The strict resolve throws QueryMoreThanOneRecordsFound if two products share the GTIN, so the
// value must be unique across products AND across runs — we combine the wall clock with a
// monotonic per-process counter (back-to-back calls in the same millisecond still differ).
//
// The per-call counter sits in a HIGH-ORDER position (right after the prefix), NOT in the trailing
// digits. Back-to-back GTINs (P1 vs P2 here) therefore differ by 2 high-order digits, never by a
// single trailing digit. This matters because the unpick product scan goes through a STRICT
// GTIN->product resolve: if P1 and P2 differed by only the last digit, a single dropped/stale
// keystroke in the document-level scanner buffer could silently turn P1's scan into P2's *valid*
// GTIN, which then resolves to a product not in this job and fails with "Cannot find a product".
let _gtinSeq = 0;
const uniqueGtin14 = () => {
    const seq = `${(_gtinSeq++) % 100}`.padStart(2, '0');      // 2 high-order digits, distinguishes back-to-back calls
    const ts = `${Date.now()}`.slice(-11).padStart(11, '0');  // 11 low-order digits from the wall clock (ms)
    // 14 numeric digits: "9" GS1-ish prefix + 2 high-order per-call sequence digits + 11 clock digits.
    return `9${seq}${ts}`;
};
// The GS1 scannable string for a product GTIN: Application Identifier 01 + the 14-digit GTIN.
const gs1GtinScan = (gtin14) => `01${gtin14}`;

// `createShipmentPolicy` selects what completing the picking job does to the shipment
// (CreateShipmentPolicy enum, code-or-name accepted by the backend):
//   'CL' = CREATE_COMPLETE_CLOSE (default here) — the un-shipped AC7/AC2 tests never complete the
//          job, so the value is inert for them.
//   'CO' = CREATE_AND_COMPLETE — completing the job generates AND completes a shipment for the net
//          packed qty (used by the partial-unpick shipment test).
const createMasterdata = async ({ packedGtin, otherGtin, createShipmentPolicy = 'CL' }) => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy,
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    // Completing the job after a partial unpick leaves the line partially picked,
                    // so the job must be completable while partial. Set it explicitly so this spec
                    // does not inherit a prior spec's value (the picking config persists in the DB;
                    // picking_multiProduct_aggregatedTUs sets it false and runs earlier).
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                // P1 is the packed product (scanning its GTIN must resolve and offer the packed qty).
                "P1": { gtin: packedGtin, prices: [{ price: 1 }] },
                // P2 exists but is NOT in this picking job (AC2 negative path).
                "P2": { gtin: otherGtin, prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
                // HU2: the mandatory target HU (an LU) the unpicked qty is unpicked INTO.
                //
                // It MUST share HU1's packing instruction `PI`, and that forces its starting fill:
                //   - The target MUST be an LU. The unpick extracts the unpicked qty to a top-level TU and
                //     moves THAT TU into the target; only an LU accepts a TU (moving into a TU or CU target
                //     fails with "Expecting only CUs to be moved").
                //   - The target LU's TU child MUST be the SAME TU packing instruction as the picked HU's,
                //     otherwise the stacking is rejected ("LU ... cannot stack TU ... no link between them").
                //     The masterdata API creates a fresh TU PI per packingInstructions entry (the TU
                //     identifier would collide if reused), so a SEPARATE small LU PI cannot reuse PI's TU —
                //     the only LU that can receive the picked TU is one built from `PI` itself.
                //   - A PI-backed HU is always force-filled to the PI's full capacity (qty:0 / a smaller
                //     starting qty are not supported), so HU2 starts at PI's full 20 TU x 4 = 80 PCE.
                //
                // Hence an EMPTY target HU is not achievable through the masterdata API here; the round
                // assertions below therefore verify the target as its starting 80 PCE + the unpicked qty.
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
test('Partial unpack - unpick item by scanning product GTIN, re-pick loop repeatable', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14(), otherGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);
    const targetHUQRCode = masterdata.handlingUnits.HU2.qrCode;

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick all 3 TU (12 PCE) from HU1', async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
        await Backend.expect({
            title: 'after full pick: 12 PCE packed on lu1',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
                lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
            }
        });
    });

    // Round 1: unpick 1 TU (4 PCE) by scanning the product GTIN, into target HU2; assert 8 PCE stays
    // packed and the unpicked 4 PCE lands in the target HU2; then re-pick it back to 12 PCE.
    // HU2 starts with 80 PCE (see masterdata note), so the unpick moves the unpicked 4 PCE into it
    // -> 80 + 4 = 84 PCE.
    await test.step('Round 1 - unpick 4 PCE then re-pick', async () => {
        // The "Unpack item" entry is on the picking JOB screen — no drilling into a step.
        await PickingJobScreen.unpickItem({ scannedCode: packedScan, expectDefaultQty: '12', qty: '4', targetHUQRCode });

        // The rest stays packed (2 TU = 8 PCE); the unpicked 4 PCE has been moved into target HU2,
        // which therefore now holds its starting 80 PCE + the unpicked 4 PCE = 84 PCE.
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
        await Backend.expect({
            title: 'round 1 after unpick: 8 PCE packed, unpicked 4 PCE moved into target HU2 (80 start + 4 = 84)',
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
                [targetHUQRCode]: { storages: { P1: '84 PCE' } },
            }
        });

        // Re-pick 1 TU (4 PCE) from the target HU2: the line returns to 3 TU and the target drops back
        // to its starting 80 PCE.
        await PickingJobScreen.pickHU({ qrCode: targetHUQRCode, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });
        await Backend.expect({
            title: 'round 1 after re-pick: back to 12 PCE packed, target HU2 back to its starting 80 PCE',
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
                [targetHUQRCode]: { storages: { P1: '80 PCE' } },
            }
        });
    });

    // Round 2: repeat the unpick -> re-pick loop to prove it is repeatable (AC7).
    await test.step('Round 2 - unpick 4 PCE then re-pick', async () => {
        await PickingJobScreen.unpickItem({ scannedCode: packedScan, expectDefaultQty: '12', qty: '4', targetHUQRCode });

        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
        await Backend.expect({
            title: 'round 2 after unpick: 8 PCE packed again, target HU2 again 80 start + 4 = 84',
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
                [targetHUQRCode]: { storages: { P1: '84 PCE' } },
            }
        });

        await PickingJobScreen.pickHU({ qrCode: targetHUQRCode, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });
        await Backend.expect({
            title: 'round 2 after re-pick: back to 12 PCE packed; target HU2 back to its starting 80 PCE',
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
                [targetHUQRCode]: { storages: { P1: '80 PCE' } },
            }
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Partial unpack - scanning a product not in the package shows one error, nothing unpicked', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('normal');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14(), otherGtin: uniqueGtin14() });
    const otherScan = gs1GtinScan(masterdata.products.P2.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    // Assert the post-pick state (also registers the dynamically-created `lu1` HU in the backend
    // test context so we can refer to it by identifier in the "nothing unpicked" check below).
    await Backend.expect({
        title: 'AC2 baseline: 12 PCE packed on lu1',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: { qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] }
                }
            }
        },
        hus: { lu1: { huStatus: 'S', storages: { P1: '12 PCE' } } },
    });

    await test.step('Scan a product GTIN that is not packed in this job -> one error, nothing unpicked', async () => {
        await PickingJobScreen.clickUnpickItem();
        // The backend rejects a product that is not packed in this job; the single error surface is
        // the scanner's toast ("Cannot find a product matching the scanned code in this picking job").
        await expectErrorToast('Scan a product not in the package', async () => {
            await PickingJobScreen.scanProductCodeToUnpick({ scannedCode: otherScan });
        });
        // The flow stays on the product-scan stage (no qty dialog advanced).
        await PickingJobScreen.expectOnProductScanStage();
    });

    // Nothing was unpicked: the 12 PCE are still fully packed on lu1.
    await Backend.expect({
        title: 'AC2: nothing unpicked after scanning a not-in-package product',
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Partial unpack - partial unpick then complete the job ships the NET qty in exactly one line, no negative counter-row', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    // CREATE_AND_COMPLETE: completing the picking job generates AND completes a shipment for the
    // net packed qty. This is what lets us assert the shipment carries the net 8 PCE (not 12) with
    // exactly one line and no negative counter-row.
    const masterdata = await createMasterdata({
        packedGtin: uniqueGtin14(),
        otherGtin: uniqueGtin14(),
        createShipmentPolicy: 'CO',
    });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);
    const targetHUQRCode = masterdata.handlingUnits.HU2.qrCode;

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick all 3 TU (12 PCE) from HU1', async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });
        // Assert the post-pick state. This also registers the dynamically-created `lu1` HU in the
        // backend test context, so the unpick assertion below can refer to it by identifier.
        await Backend.expect({
            title: 'TC2 baseline: 12 PCE packed on lu1',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: { lu1: { huStatus: 'S', storages: { P1: '12 PCE' } } },
        });
    });

    await test.step('Partial-unpick 4 PCE by scanning the product GTIN into the target HU; do NOT re-pick', async () => {
        await PickingJobScreen.unpickItem({ scannedCode: packedScan, expectDefaultQty: '12', qty: '4', targetHUQRCode });

        // 2 TU (8 PCE) stays packed on lu1; the unpicked 4 PCE has moved into target HU2.
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
        await Backend.expect({
            title: 'TC2 after partial unpick: 8 PCE stays packed on lu1, 4 PCE moved into target HU2',
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
                [targetHUQRCode]: { storages: { P1: '84 PCE' } },
            }
        });
    });

    await test.step('Complete the picking job -> a shipment is generated for the net packed qty', async () => {
        await PickingJobScreen.complete();
    });

    // The invariant: the shipment carries EXACTLY one line of 8 PCE of P1. The single-line exact match
    // (lines has exactly one entry, count-checked) is the guard that there is NO negative counter-row.
    await Backend.expect({
        title: 'TC2: shipment carries net 8 PCE of P1 in exactly one line, no negative counter-row',
        salesOrders: {
            SO1: {
                shipments: [
                    { docStatus: 'CO', movementQty: 8, lines: [{ product: 'P1', movementQty: 8 }] },
                ],
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Partial unpack - unpick item to the floor by canceling the target-HU scan', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14(), otherGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick all 3 TU (12 PCE) from HU1', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });
        await Backend.expect({
            title: 'after full pick: 12 PCE packed on lu1',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: { lu1: { huStatus: 'S', storages: { P1: '12 PCE' } } }
        });
    });

    await test.step('Unpick 4 PCE to the floor (cancel/skip the target-HU scan)', async () => {
        await PickingJobScreen.unpickItemToFloor({ scannedCode: packedScan, expectDefaultQty: '12', qty: '4' });
        // No target HU is scanned: the unpicked 4 PCE drops to the floor. The line falls to 2 TU and the
        // picked LU keeps the remaining 8 PCE.
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
        await Backend.expect({
            title: 'floor unpick: 8 PCE stays packed on lu1 (unpicked 4 PCE dropped to the floor, no target HU)',
            hus: { lu1: { huStatus: 'S', storages: { P1: '8 PCE' } } }
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Partial unpack - transient network failure on submit keeps the panel open and shows an error; retry succeeds', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14(), otherGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);
    const targetHUQRCode = masterdata.handlingUnits.HU2.qrCode;

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick all 3 TU (12 PCE) from HU1', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });
        // Registers the dynamically-created `lu1` HU in the backend test context so the assertions
        // below can refer to it by identifier.
        await Backend.expect({
            title: 'baseline: 12 PCE packed on lu1',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: { lu1: { huStatus: 'S', storages: { P1: '12 PCE' } } }
        });
    });

    // Drive the unpick panel up to the target-HU scan (the SCAN_TARGET stage). The submit fires only
    // once the target HU is scanned (UnpickTargetScanDialog -> onSubmit -> postStepPartiallyUnPicked,
    // i.e. the POST to picking/event).
    await PickingJobScreen.unpickAdvanceToTargetStage({ scannedCode: packedScan, expectDefaultQty: '12', qty: '4' });

    // Same network-fault technique as picking.spec.js "Network failure on complete": abort the submit
    // at the network layer (no HTTP response). With no `axiosError.response`, the panel treats this as
    // a transient failure -> toasts the error AND stays on SCAN_TARGET (does NOT close).
    await PickingJobScreen.blockUnpickSubmit();

    await test.step('Scan the target HU under the network fault -> error toast, panel stays on SCAN_TARGET', async () => {
        await expectErrorToast('Unpick submit network failure', async () => {
            await PickingJobScreen.scanCodeAtTargetStageNoCommit({ scannedCode: targetHUQRCode });
        });
        // The panel did NOT close: the target-HU scanner is still armed (a transient failure is
        // recoverable, so the operator can simply scan again).
        await PickingJobScreen.expectOnTargetScanStage();
    });

    // Nothing was committed: the 12 PCE are still fully packed on lu1, the target HU2 is untouched.
    await Backend.expect({
        title: 'after aborted submit: nothing unpicked, 12 PCE still packed on lu1, target HU2 untouched',
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
            [targetHUQRCode]: { storages: { P1: '80 PCE' } },
        }
    });

    await test.step('Release the network fault and retry the same target-HU scan', async () => {
        await PickingJobScreen.unblockUnpickSubmit();
        await PickingJobScreen.scanTargetHUAndCommit({ qrCode: targetHUQRCode });
    });

    // The retry succeeded: the panel closed back to the job screen, 4 PCE were unpicked (line drops to
    // 2 TU), and the unpicked 4 PCE landed in target HU2 (80 start + 4 = 84).
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
    await Backend.expect({
        title: 'after retry: 8 PCE stays packed on lu1, unpicked 4 PCE moved into target HU2 (80 + 4 = 84)',
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
            [targetHUQRCode]: { storages: { P1: '84 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Partial unpack - mis-scanning the product GTIN as the target HU is rejected by the backend; the panel stays open so the operator can scan the correct target', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial unpack by scanning a product GTIN');
    allure.severity('critical');

    const masterdata = await createMasterdata({ packedGtin: uniqueGtin14(), otherGtin: uniqueGtin14() });
    const packedScan = gs1GtinScan(masterdata.products.P1.gtin);
    const targetHUQRCode = masterdata.handlingUnits.HU2.qrCode;

    const { pickingJobId } = await loginAndStartJob({ masterdata });

    await test.step('Pick all 3 TU (12 PCE) from HU1', async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU' });
        // Registers the dynamically-created `lu1` HU in the backend test context so the assertions
        // below can refer to it by identifier.
        await Backend.expect({
            title: 'baseline: 12 PCE packed on lu1',
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: { qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }] }
                    }
                }
            },
            hus: { lu1: { huStatus: 'S', storages: { P1: '12 PCE' } } }
        });
    });

    // Drive the unpick panel up to the target-HU scan (the SCAN_TARGET stage).
    await PickingJobScreen.unpickAdvanceToTargetStage({ scannedCode: packedScan, expectDefaultQty: '12', qty: '4' });

    // The operator mis-scans: at the "scan the target HU" step they re-scan the product GTIN they are
    // holding instead of an actual target HU. The code IS submitted (postStepPartiallyUnPicked -> the
    // picking/event POST); the backend rejects it (a product GTIN is not a valid target HU) with a 4xx,
    // so axiosError.response is present. Unlike the transient-network case (no response), this is a real
    // server rejection — the panel must STILL stay on SCAN_TARGET so the operator can correct the scan.
    await test.step('Mis-scan the product GTIN as the target HU -> backend 4xx, error toast, panel stays on SCAN_TARGET', async () => {
        await expectErrorToast('Unpick target server rejection', async () => {
            await PickingJobScreen.scanCodeAtTargetStageNoCommit({ scannedCode: packedScan });
        });
        // The panel did NOT close: the target-HU scanner is still armed so the operator can scan the
        // correct target HU (or Cancel to abort).
        await PickingJobScreen.expectOnTargetScanStage();
    });

    // Nothing was committed: the 12 PCE are still fully packed on lu1, the target HU2 is untouched.
    await Backend.expect({
        title: 'after rejected mis-scan: nothing unpicked, 12 PCE still packed on lu1, target HU2 untouched',
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
            [targetHUQRCode]: { storages: { P1: '80 PCE' } },
        }
    });

    await test.step('Scan the correct target HU -> the unpick commits and the panel closes', async () => {
        await PickingJobScreen.scanTargetHUAndCommit({ qrCode: targetHUQRCode });
    });

    // The corrected scan succeeded: the panel closed back to the job screen, 4 PCE were unpicked (line
    // drops to 2 TU), and the unpicked 4 PCE landed in target HU2 (80 start + 4 = 84).
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU' });
    await Backend.expect({
        title: 'after corrected scan: 8 PCE stays packed on lu1, unpicked 4 PCE moved into target HU2 (80 + 4 = 84)',
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '8 PCE' } },
            [targetHUQRCode]: { storages: { P1: '84 PCE' } },
        }
    });
});
