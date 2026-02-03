import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";

const createMasterdata = async () => {
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
                    createShipmentPolicy: 'CREATE_COMPLETE_CLOSE',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
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
                "P1": { prices: [{ price: 1 }], isCatchWeight: false },
                "P2": { prices: [{ price: 2 }], isCatchWeight: false },
                "P3": { prices: [{ price: 3 }], isCatchWeight: false },
                "P4": { prices: [{ price: 4 }], isCatchWeight: false },
            },
            packingInstructions: {
                "PI1": { lu: "LU", qtyTUsPerLU: 50, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU", qtyTUsPerLU: 50, tu: "TU", product: "P2", qtyCUsPerTU: 4 },
                "PI3": { lu: "LU", qtyTUsPerLU: 50, tu: "TU", product: "P3", qtyCUsPerTU: 4 },
                "PI4": { lu: "LU", qtyTUsPerLU: 50, tu: "TU", product: "P4", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI1' },
                "HU2": { product: 'P2', warehouse: 'wh', packingInstructions: 'PI2' },
                "HU3": { product: 'P3', warehouse: 'wh', packingInstructions: 'PI3' },
                "HU4": { product: 'P4', warehouse: 'wh', packingInstructions: 'PI4' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 160, piItemProduct: 'TU' },
                        { product: 'P2', qty: 160, piItemProduct: 'TU' },
                        { product: 'P3', qty: 160, piItemProduct: 'TU' },
                        { product: 'P4', qty: 160, piItemProduct: 'TU' }
                    ]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Test 1: Pick aggregated TUs - Four products from CU to single LU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Pick aggregated TUs with four products');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    await test.step('Select LU as picking target', async () => {
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
    });

    await test.step('Scan HU product 1: full qty of TUs, as ordered', async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '40 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '40' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '40 TU', qtyPicked: '40 TU', qtyPickedCatchWeight: '' });
    });

    await test.step('Scan HU product 2: full qty of TUs, as ordered', async () => {
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '40 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '40' });
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '40 TU', qtyPicked: '40 TU', qtyPickedCatchWeight: '' });
    });

    await test.step('Scan HU product 3: full qty of TUs, as ordered', async () => {
        await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '40 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU3.qrCode, expectQtyEntered: '40' });
        await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '40 TU', qtyPicked: '40 TU', qtyPickedCatchWeight: '' });
    });

    await test.step('Scan HU product 4: full qty of TUs, as ordered', async () => {
        await PickingJobScreen.expectLineButton({ index: 4, qtyToPick: '40 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU4.qrCode, expectQtyEntered: '40' });
        await PickingJobScreen.expectLineButton({ index: 4, qtyToPick: '40 TU', qtyPicked: '40 TU', qtyPickedCatchWeight: '' });
    });

    await test.step('Verify backend state before completion', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                        P2: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                        P3: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu3', tu: 'tu3', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                        P4: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu4', tu: 'tu4', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        }
                    }
                }
            },
            hus: {
                tu1: { huStatus: 'S', storages: { P1: '160 PCE' } },
                tu2: { huStatus: 'S', storages: { P2: '160 PCE' } },
                tu3: { huStatus: 'S', storages: { P3: '160 PCE' } },
                tu4: { huStatus: 'S', storages: { P4: '160 PCE' } },
                lu1: { huStatus: 'S', storages: { P1: '160 PCE', P2: '160 PCE', P3: '160 PCE', P4: '160 PCE' } },
            }
        });
    });

    await test.step('Complete picking job', async () => {
        await PickingJobScreen.complete();
    });

    await test.step('Result: 1 LU with 4 different products in aggregated TUs', async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                        },
                        P2: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId2' }]
                        },
                        P3: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu3', tu: 'tu3', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId3' }]
                        },
                        P4: {
                            qtyPicked: [{ qtyPicked: "160 PCE", qtyTUs: 40, qtyLUs: 1, vhu: 'vhu4', tu: 'tu4', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId4' }]
                        }
                    }
                }
            },
            hus: {
                tu1: { huStatus: 'E', storages: { P1: '160 PCE' } },
                tu2: { huStatus: 'E', storages: { P2: '160 PCE' } },
                tu3: { huStatus: 'E', storages: { P3: '160 PCE' } },
                tu4: { huStatus: 'E', storages: { P4: '160 PCE' } },
                lu1: { huStatus: 'E', storages: { P1: '160 PCE', P2: '160 PCE', P3: '160 PCE', P4: '160 PCE' } },
            }
        });
    });
});
