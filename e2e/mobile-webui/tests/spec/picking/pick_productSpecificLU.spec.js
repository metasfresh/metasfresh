import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { SelectPickTargetLUScreen } from "../../utils/screens/picking/SelectPickTargetLUScreen";

// Picking whole TUs (crates) into a NEW LU target, from a source LU or from a standalone source TU.
//
// LU packing instruction "LU" holds IFCO-like TUs:
// * generic LU item: 30 TUs per LU
// * LU item bound to product "XY": 40 TUs per LU
// Product "OTHER" has no product-specific LU item and uses the same LU and TU packing instructions.
// Each TU holds 4 pieces.
// LU source: one big LU ("SRC_LU", 60 TUs per LU) holding 60 TUs of the picked product; 50 TUs are picked.
// TU source (sourceIsTU): a standalone TU of the picked product is picked into the new LU.
// withXYPallet: one more pallet type "XY_LU" whose ONLY LU item is bound to product "XY" (40 TUs per LU), no generic item.
// looseQty: the order line has no TU packing instruction, so the line is picked in CUs (loose quantity).
const createMasterdata = async ({ pickedProduct, sourceIsTU = false, withXYPallet = false, looseQty = false }) => {
    const otherProduct = pickedProduct === 'XY' ? 'OTHER' : 'XY';
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: {
                "wh": {},
            },
            pickingSlots: {
                slot1: {},
            },
            products: {
                "XY": { prices: [{ price: 1 }] },
                "OTHER": { prices: [{ price: 1 }] },
                ...(withXYPallet ? { "Z": { prices: [{ price: 1 }] } } : {}),
            },
            packingInstructions: {
                "PI": {
                    lu: "LU", qtyTUsPerLU: 30, tu: "TU", product: pickedProduct, qtyCUsPerTU: 4,
                    additionalLUItems: [{ product: "XY", qtyTUsPerLU: 40 }],
                },
                // The source: a big LU (60 TUs) or one standalone TU, with the same TU packing instruction.
                // The product given here is only the (unused) TU capacity entry; a TU packing instruction can't have the same product twice,
                // so only one of the two exists per test.
                ...(sourceIsTU
                    ? { "TU_ONLY": { tu: "TU", product: otherProduct, qtyCUsPerTU: 4 } } // a standalone (real) TU, not on any LU
                    : { "SRC": { lu: "SRC_LU", qtyTUsPerLU: 60, tu: "TU", product: otherProduct, qtyCUsPerTU: 4 } }),
                // A dedicated XY pallet type: linked to the TU only via an item for XY. The product "Z" is only its (unused) TU capacity entry.
                ...(withXYPallet ? { "XY_PALLET": { lu: "XY_LU", qtyTUsPerLU: 40, luProduct: "XY", tu: "TU", product: "Z", qtyCUsPerTU: 4 } } : {}),
            },
            handlingUnits: {
                "HU1": { product: pickedProduct, warehouse: 'wh', packingInstructions: sourceIsTU ? 'TU_ONLY' : 'SRC' }, // a full SRC_LU (60 TUs) or one real TU
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [looseQty
                        ? { product: pickedProduct, qty: 200 } // no TU packing instruction => picked in CUs
                        : { product: pickedProduct, qty: sourceIsTU ? 4 : 200, piItemProduct: 'TU' }] // 50 TUs, or 1 TU
                }
            },
        }
    })
}

const startPickingJob = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    return { pickingJobId };
}

const pickIntoNewLU = async ({ masterdata, qtyTUs = 50, lu = masterdata.packingInstructions.PI.luName }) => {
    const { pickingJobId } = await startPickingJob({ masterdata });

    await PickingJobScreen.setTargetLU({ lu });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${qtyTUs} TU`, qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: `${qtyTUs}` });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: `${qtyTUs} TU`, qtyPicked: `${qtyTUs} TU`, qtyPickedCatchWeight: '' });
    return { pickingJobId };
}

// noinspection JSUnusedLocalSymbols
test('Pick TUs of a product with its own LU item into a new LU: LUs hold 40 TUs', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs into a new LU - product-specific LU item');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickedProduct: 'XY' });
    const { pickingJobId } = await pickIntoNewLU({ masterdata });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    XY: {
                        qtyPicked: [
                            { qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, lu: 'lu1' },
                            { qtyPicked: "40 PCE", qtyTUs: 10, qtyLUs: 1, lu: 'lu2' },
                        ]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'A', storages: { XY: '40 PCE' } }, // 10 TUs remain
            lu1: { huStatus: 'S', storages: { XY: '160 PCE' }, tus: [{ isAggregatedTU: true, qtyTUs: 40 }] },
            lu2: { huStatus: 'S', storages: { XY: '40 PCE' }, tus: [{ isAggregatedTU: true, qtyTUs: 10 }] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Pick TUs of a product without its own LU item into a new LU: LUs hold 30 TUs', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs into a new LU - product-specific LU item');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickedProduct: 'OTHER' });
    const { pickingJobId } = await pickIntoNewLU({ masterdata });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    OTHER: {
                        qtyPicked: [
                            { qtyPicked: "120 PCE", qtyTUs: 30, qtyLUs: 1, lu: 'lu1' },
                            { qtyPicked: "80 PCE", qtyTUs: 20, qtyLUs: 1, lu: 'lu2' },
                        ]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'A', storages: { OTHER: '40 PCE' } }, // 10 TUs remain
            lu1: { huStatus: 'S', storages: { OTHER: '120 PCE' }, tus: [{ isAggregatedTU: true, qtyTUs: 30 }] },
            lu2: { huStatus: 'S', storages: { OTHER: '80 PCE' }, tus: [{ isAggregatedTU: true, qtyTUs: 20 }] },
        }
    });
});

// A real TU can only be picked one at a time, so there is no roll-over to LU #2 here;
// what shows which LU item is used is the TU count of the item via which the picked TU is attached to its new LU.
// noinspection JSUnusedLocalSymbols
test('Pick a TU (not an LU) of a product with its own LU item into a new LU: attached via the 40 TUs item', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs into a new LU - product-specific LU item');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickedProduct: 'XY', sourceIsTU: true });
    const { pickingJobId } = await pickIntoNewLU({ masterdata, qtyTUs: 1 });

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { XY: { qtyPicked: [{ qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 1, lu: 'lu1' }] } } } },
        hus: {
            lu1: { huStatus: 'S', storages: { XY: '4 PCE' }, tus: [{ parentPIItemQtyTUs: 40 }] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Pick a TU (not an LU) of a product without its own LU item into a new LU: attached via the 30 TUs item', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs into a new LU - product-specific LU item');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickedProduct: 'OTHER', sourceIsTU: true });
    const { pickingJobId } = await pickIntoNewLU({ masterdata, qtyTUs: 1 });

    await Backend.expect({
        pickings: { [pickingJobId]: { shipmentSchedules: { OTHER: { qtyPicked: [{ qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 1, lu: 'lu1' }] } } } },
        hus: {
            lu1: { huStatus: 'S', storages: { OTHER: '4 PCE' }, tus: [{ parentPIItemQtyTUs: 30 }] },
        }
    });
});

// The pallet-type list ("new LU" targets) offers a pallet type only if picking onto it can work:
// it has an item without product, or an item for the picked product when whole TUs are picked.

// noinspection JSUnusedLocalSymbols
test('A pallet type linked to the TU only via another product\'s item is not offered', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs into a new LU - product-specific LU item');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickedProduct: 'OTHER', withXYPallet: true });
    await startPickingJob({ masterdata });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '50 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' }); // picked in TUs

    await PickingJobScreen.expectLUTargets({
        offered: [masterdata.packingInstructions.PI.luName],
        notOffered: [masterdata.packingInstructions.XY_PALLET.luName],
    });
});

// noinspection JSUnusedLocalSymbols
test('A product\'s own pallet type is offered and picking TUs onto it uses its item: LUs hold 40 TUs', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs into a new LU - product-specific LU item');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickedProduct: 'XY', withXYPallet: true });
    const { pickingJobId } = await startPickingJob({ masterdata });
    await PickingJobScreen.expectLUTargets({
        offered: [masterdata.packingInstructions.PI.luName, masterdata.packingInstructions.XY_PALLET.luName],
    });
    await SelectPickTargetLUScreen.clickLUButton({ lu: masterdata.packingInstructions.XY_PALLET.luName });
    await PickingJobScreen.waitForScreen();

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '50 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '50' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '50 TU', qtyPicked: '50 TU', qtyPickedCatchWeight: '' });

    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    XY: {
                        qtyPicked: [
                            { qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, lu: 'lu1' },
                            { qtyPicked: "40 PCE", qtyTUs: 10, qtyLUs: 1, lu: 'lu2' },
                        ]
                    }
                }
            },
        },
        hus: {
            lu1: { huStatus: 'S', storages: { XY: '160 PCE' }, tus: [{ isAggregatedTU: true, qtyTUs: 40, parentPIItemQtyTUs: 40 }] },
            lu2: { huStatus: 'S', storages: { XY: '40 PCE' }, tus: [{ isAggregatedTU: true, qtyTUs: 10, parentPIItemQtyTUs: 40 }] },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Loose quantity: a pallet type linked to the TU only via product-specific items is not offered, not even for that product', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick TUs into a new LU - product-specific LU item');
    allure.severity('normal');

    const masterdata = await createMasterdata({ pickedProduct: 'XY', withXYPallet: true, looseQty: true });
    await startPickingJob({ masterdata });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '200 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' }); // picked in CUs

    await PickingJobScreen.expectLUTargets({
        offered: [masterdata.packingInstructions.PI.luName],
        notOffered: [masterdata.packingInstructions.XY_PALLET.luName],
    });
});
