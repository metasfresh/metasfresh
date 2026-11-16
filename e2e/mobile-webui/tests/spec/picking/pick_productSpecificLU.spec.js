import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

// Picking whole TUs (crates) from a source LU into a NEW LU target.
//
// LU packing instruction "LU" holds IFCO-like TUs:
// * generic LU item: 30 TUs per LU
// * LU item bound to product "XY": 40 TUs per LU
// Product "OTHER" has no product-specific LU item and uses the same LU and TU packing instructions.
// Each TU holds 4 pieces.
// The source HU is one big LU ("SRC_LU", 60 TUs per LU) holding 60 TUs of the picked product; 50 TUs are picked.
const createMasterdata = async ({ pickedProduct }) => {
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
            },
            packingInstructions: {
                "PI": {
                    lu: "LU", qtyTUsPerLU: 30, tu: "TU", product: pickedProduct, qtyCUsPerTU: 4,
                    additionalLUItems: [{ product: "XY", qtyTUsPerLU: 40 }],
                },
                // same TU packing instruction, big source LU.
                // The product given here is only the (unused) TU capacity entry: a TU packing instruction can't have the same product twice.
                "SRC": { lu: "SRC_LU", qtyTUsPerLU: 60, tu: "TU", product: pickedProduct === 'XY' ? 'OTHER' : 'XY', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: pickedProduct, warehouse: 'wh', packingInstructions: 'SRC' }, // a full SRC_LU: 60 TUs
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: pickedProduct, qty: 200, piItemProduct: 'TU' }] // 50 TUs
                }
            },
        }
    })
}

const pickIntoNewLU = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '50 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '50' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '50 TU', qtyPicked: '50 TU', qtyPickedCatchWeight: '' });
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
