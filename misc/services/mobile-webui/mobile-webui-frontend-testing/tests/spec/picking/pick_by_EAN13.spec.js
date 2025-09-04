import { test } from "../../../playwright.config";
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { generateEAN13 } from '../../utils/ean13';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';

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
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickWithNewLU: false,
                    allowNewTU: true,
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { price: 1, ean13ProductCode: generateEAN13().productCode },
                "P2": { price: 1, ean13ProductCode: generateEAN13().productCode },
                "P3": { price: 1, ean13ProductCode: generateEAN13().productCode },
            },
            packingInstructions: {
                "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 20 },
                "PI2": { tu: "TU", product: "P2", qtyCUsPerTU: 100 },
                "PI3": { tu: "TU", product: "P3", qtyCUsPerTU: 100 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000 },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 1000 },
                "HU3": { product: 'P3', warehouse: 'wh', qty: 1000 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 11 },
                        { product: 'P2', qty: 12 },
                        { product: 'P3', qty: 13 },
                    ]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Pick from CUs by scanning EAN13 codes and pick into same top level TU', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen' });
    await PickLineScanScreen.goBack();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });

    await test.step("Pick all 3 lines", async () => {
        await PickingJobScreen.pickHU({
            qrCode: generateEAN13({ productCode: masterdata.products.P1.ean13ProductCode }).ean13,
            expectQtyEntered: 11
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '11 Stk', qtyPicked: '11 Stk', qtyPickedCatchWeight: '' });

        await PickingJobScreen.pickHU({
            qrCode: generateEAN13({ productCode: masterdata.products.P2.ean13ProductCode }).ean13,
            expectQtyEntered: 12
        });
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '12 Stk', qtyPicked: '12 Stk', qtyPickedCatchWeight: '' });

        await PickingJobScreen.pickHU({
            qrCode: generateEAN13({ productCode: masterdata.products.P3.ean13ProductCode }).ean13,
            expectQtyEntered: 13
        });
        await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '13 Stk', qtyPicked: '13 Stk', qtyPickedCatchWeight: '' });

        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "11 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }]
                        },
                        P2: {
                            qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: 'vhu2', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }]
                        },
                        P3: {
                            qtyPicked: [{ qtyPicked: "13 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: 'vhu3', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-' }]
                        },
                    }
                }
            },
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '989 PCE' } },
                [masterdata.handlingUnits.HU2.qrCode]: { huStatus: 'A', storages: { P2: '988 PCE' } },
                [masterdata.handlingUnits.HU3.qrCode]: { huStatus: 'A', storages: { P3: '987 PCE' } },
                tu1: { huStatus: 'S', storages: { P1: '11 PCE', P2: '12 PCE', P3: '13 PCE', } },
            }
        });
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "11 PCE", qtyTUs: 1, qtyLUs: 1, vhuId: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine1' }]
                    },
                    P2: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 1, qtyLUs: 1, vhuId: 'vhu2', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine2' }]
                    },
                    P3: {
                        qtyPicked: [{ qtyPicked: "13 PCE", qtyTUs: 1, qtyLUs: 1, vhuId: 'vhu3', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLine3' }]
                    },
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '989 PCE' } },
            [masterdata.handlingUnits.HU2.qrCode]: { huStatus: 'A', storages: { P2: '988 PCE' } },
            [masterdata.handlingUnits.HU3.qrCode]: { huStatus: 'A', storages: { P3: '987 PCE' } },
            tu1: { huStatus: 'E', storages: { P1: '11 PCE', P2: '12 PCE', P3: '13 PCE', } },
        }
    });
});
