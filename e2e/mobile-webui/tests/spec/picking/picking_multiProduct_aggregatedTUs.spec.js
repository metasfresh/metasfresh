import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';

// noinspection JSUnusedLocalSymbols
test('Aggregated TUs - 4 products into 1 LU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Multi-product picking - aggregated TUs');
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
                    allowCompletingPartialPickingJob: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
                "P2": { prices: [{ price: 1 }] },
                "P3": { prices: [{ price: 1 }] },
                "P4": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI1": { lu: "LU", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 10 },
                "PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 10 },
                "PI3": { lu: "LU", qtyTUsPerLU: 20, tu: "TU3", product: "P3", qtyCUsPerTU: 10 },
                "PI4": { lu: "LU", qtyTUsPerLU: 20, tu: "TU4", product: "P4", qtyCUsPerTU: 10 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 200 },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 200 },
                "HU3": { product: 'P3', warehouse: 'wh', qty: 200 },
                "HU4": { product: 'P4', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 20, piItemProduct: 'TU1' },
                        { product: 'P2', qty: 30, piItemProduct: 'TU2' },
                        { product: 'P3', qty: 40, piItemProduct: 'TU3' },
                        { product: 'P4', qty: 10, piItemProduct: 'TU4' },
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

    await test.step("Pick all 4 products", async () => {
        // P1: 20 CU / 10 CU per TU = 2 TU
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '2' });
        await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '2 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });

        // P2: 30 CU / 10 CU per TU = 3 TU
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 2, color: 'green', qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

        // P3: 40 CU / 10 CU per TU = 4 TU
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU3.qrCode, expectQtyEntered: '4' });
        await PickingJobScreen.expectLineButton({ index: 3, color: 'green', qtyToPick: '4 TU', qtyPicked: '4 TU', qtyPickedCatchWeight: '' });

        // P4: 10 CU / 10 CU per TU = 1 TU
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU4.qrCode, expectQtyEntered: '1' });
        await PickingJobScreen.expectLineButton({ index: 4, color: 'green', qtyToPick: '1 TU', qtyPicked: '1 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Verify backend state after picking", async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 2, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                        P2: {
                            qtyPicked: [{ qtyPicked: "30 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                        P3: {
                            qtyPicked: [{ qtyPicked: "40 PCE", qtyTUs: 4, qtyLUs: 1, vhu: 'vhu3', tu: 'tu3', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                        P4: {
                            qtyPicked: [{ qtyPicked: "10 PCE", qtyTUs: 1, qtyLUs: 1, vhu: 'vhu4', tu: 'tu4', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '180 PCE' } },
                [masterdata.handlingUnits.HU2.qrCode]: { huStatus: 'A', storages: { P2: '170 PCE' } },
                [masterdata.handlingUnits.HU3.qrCode]: { huStatus: 'A', storages: { P3: '160 PCE' } },
                [masterdata.handlingUnits.HU4.qrCode]: { huStatus: 'A', storages: { P4: '190 PCE' } },
                lu1: { huStatus: 'S', storages: { P1: '20 PCE', P2: '30 PCE', P3: '40 PCE', P4: '10 PCE' } },
            }
        });
    });

    await PickingJobScreen.complete();

    await test.step("Verify backend state after completion", async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "20 PCE", qtyTUs: 2, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine1' }]
                        },
                        P2: {
                            qtyPicked: [{ qtyPicked: "30 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine2' }]
                        },
                        P3: {
                            qtyPicked: [{ qtyPicked: "40 PCE", qtyTUs: 4, qtyLUs: 1, vhu: 'vhu3', tu: 'tu3', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine3' }]
                        },
                        P4: {
                            qtyPicked: [{ qtyPicked: "10 PCE", qtyTUs: 1, qtyLUs: 1, vhu: 'vhu4', tu: 'tu4', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine4' }]
                        },
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '180 PCE' } },
                [masterdata.handlingUnits.HU2.qrCode]: { huStatus: 'A', storages: { P2: '170 PCE' } },
                [masterdata.handlingUnits.HU3.qrCode]: { huStatus: 'A', storages: { P3: '160 PCE' } },
                [masterdata.handlingUnits.HU4.qrCode]: { huStatus: 'A', storages: { P4: '190 PCE' } },
                lu1: { huStatus: 'E', storages: { P1: '20 PCE', P2: '30 PCE', P3: '40 PCE', P4: '10 PCE' } },
            }
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('Standalone TUs - same product picked in separate scans', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Multi-product picking - standalone TUs');
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
                    allowCompletingPartialPickingJob: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
                "P2": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI1": { lu: "LU", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 10 },
                "PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 10 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 200 },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 20, piItemProduct: 'TU1' },
                        { product: 'P2', qty: 30, piItemProduct: 'TU2' },
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

    await test.step("Pick P1 partially - 1 out of 2 TU", async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '2',
            qtyEntered: '1',
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
        });
        await PickingJobScreen.expectLineButton({ index: 1, color: 'yellow', qtyToPick: '2 TU', qtyPicked: '1 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Pick P1 remaining - 1 TU", async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '0',
            qtyEntered: '1',
        });
        await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '2 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Pick P2 fully - 3 TU", async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 2, color: 'green', qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Verify backend state - P1 has standalone TUs, P2 has aggregated TU", async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [
                                { qtyPicked: "10 PCE", qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' },
                                { qtyPicked: "10 PCE", qtyTUs: 1, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: false, shipmentLineId: '-' },
                            ]
                        },
                        P2: {
                            qtyPicked: [{ qtyPicked: "30 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu3', tu: 'tu3', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        },
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '180 PCE' } },
                [masterdata.handlingUnits.HU2.qrCode]: { huStatus: 'A', storages: { P2: '170 PCE' } },
                lu1: { huStatus: 'S', storages: { P1: '20 PCE', P2: '30 PCE' } },
            }
        });
    });

    await PickingJobScreen.complete();

    await test.step("Verify backend state after completion", async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [
                                { qtyPicked: "10 PCE", qtyTUs: 1, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine1' },
                                { qtyPicked: "10 PCE", qtyTUs: 1, qtyLUs: 1, vhu: 'vhu2', tu: 'tu2', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine1' },
                            ]
                        },
                        P2: {
                            qtyPicked: [{ qtyPicked: "30 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu3', tu: 'tu3', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine2' }]
                        },
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '180 PCE' } },
                [masterdata.handlingUnits.HU2.qrCode]: { huStatus: 'A', storages: { P2: '170 PCE' } },
                lu1: { huStatus: 'E', storages: { P1: '20 PCE', P2: '30 PCE' } },
            }
        });
    });
});
