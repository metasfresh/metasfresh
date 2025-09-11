import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { QTY_NOT_FOUND_REASON_IGNORE } from '../../utils/screens/picking/GetQuantityDialog';

const createMasterdata = async ({
                                    salesOrdersQty,
                                } = {}) => {
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
                    allowCompletingPartialPickingJob: true,
                    allowSkippingRejectedReason: true,
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
                "P1": { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: salesOrdersQty, piItemProduct: 'TU' }]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Pick less than a LU because ordered qty is less than an LU', async ({ page }) => {
    const masterdata = await createMasterdata({
        salesOrdersQty: 76 // < 80 => less than a full LU 
    });
    await Backend.expect({
        hus: {
            HU1: { huStatus: 'A', storages: { P1: '80 PCE' } },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '19 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '19' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '19 TU', qtyPicked: '19 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "76 PCE", qtyTUs: 19, qtyLUs: 1, vhuId: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'A', storages: { P1: '4 PCE' } },
            lu1: { huStatus: 'S', storages: { P1: '76 PCE' } },
        }
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "76 PCE", qtyTUs: 19, qtyLUs: 1, vhuId: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'A', storages: { P1: '4 PCE' } },
            lu1: { huStatus: 'E', storages: { P1: '76 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Pick entire LU which is exactly the qty that was ordered', async ({ page }) => {
    const masterdata = await createMasterdata({
        salesOrdersQty: 80 // exactly one LU
    });
    await Backend.expect({
        hus: {
            HU1: { huStatus: 'A', storages: { P1: '80 PCE' } },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '20 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '20', qtyEntered: '20' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '20 TU', qtyPicked: '20 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "80 PCE", qtyTUs: 20, qtyLUs: 1, vhuId: 'vhu1', tu: 'tu1', lu: 'HU1', processed: false, shipmentLineId: '-' }]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'S', storages: { P1: '80 PCE' } },
        }
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "80 PCE", qtyTUs: 20, qtyLUs: 1, vhuId: 'vhu1', tu: 'tu1', lu: 'HU1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'E', storages: { P1: '80 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Pick entire LU but less then ordered', async ({ page }) => {
    const masterdata = await createMasterdata({
        salesOrdersQty: 160 // exactly one LU
    });
    await Backend.expect({
        hus: {
            HU1: { huStatus: 'A', storages: { P1: '80 PCE' } },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '40 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '20', qtyEntered: '20', expectQtyNotFoundReason: QTY_NOT_FOUND_REASON_IGNORE });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '40 TU', qtyPicked: '20 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "80 PCE", qtyTUs: 20, qtyLUs: 1, vhuId: 'vhu1', tu: 'tu1', lu: 'HU1', processed: false, shipmentLineId: '-' }]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'S', storages: { P1: '80 PCE' } },
        }
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "80 PCE", qtyTUs: 20, qtyLUs: 1, vhuId: 'vhu1', tu: 'tu1', lu: 'HU1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            },
        },
        hus: {
            HU1: { huStatus: 'E', storages: { P1: '80 PCE' } },
        }
    });
});



