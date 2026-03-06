import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";

const createMasterdata = async ({ externalBarcode } = {}) => {
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
                    allowCompletingPartialPickingJob: false,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { price: 1 } },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI', externalBarcode }
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
    })
}

// noinspection JSUnusedLocalSymbols
test('Pick by scanning HU QR Code', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scan HU barcodes');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        isScanDirectly: true,
        expectQtyEntered: '3'
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    }
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
        }
    });

    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Pick by scanning M_HU_ID', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.huId,
        expectQtyEntered: '3'
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    }
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
        }
    });

    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Pick by scanning ExternalBarcode', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scan HU barcodes');
    allure.severity('normal');

    const externalBarcode = "EXT" + Date.now();
    const masterdata = await createMasterdata({ externalBarcode });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await PickingJobScreen.pickHU({
        qrCode: externalBarcode,
        expectQtyEntered: '3'
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    }
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
        }
    });

    await PickingJobScreen.complete();
});
