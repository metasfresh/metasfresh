import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';
import { generateEAN13 } from '../../utils/ean13';

let previousSysconfigs = null;

const createMasterdata = async () => {
    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            sysconfigs: {
                "M_ShipmentSchedule_Close_PartiallyShipped": "Y",
            },
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CO',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { price: 1, gtin: generateEAN13().ean13 },
                "P2": { price: 1, gtin: generateEAN13().ean13 },
                "P3": { price: 1, gtin: generateEAN13().ean13 },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
                "PI3": { lu: "LU", qtyTUsPerLU: 20, tu: "TU3", product: "P3", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 200 },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 200 },
                "HU3": { product: 'P3', warehouse: 'wh', qty: 200 },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 12, piItemProduct: 'TU' },
                        { product: 'P2', qty: 12, piItemProduct: 'TU2' },
                        { product: 'P3', qty: 12, piItemProduct: 'TU3' },
                    ]
                }
            },
        }
    });

    previousSysconfigs = masterdata.previousSysconfigs;
    return masterdata;
}

// noinspection JSUnusedLocalSymbols
test('Sysconfig M_ShipmentSchedule_Close_PartiallyShipped: partial and unshipped schedules are closed', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Close partially shipped shipment schedules');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication("picking");
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await test.step("Pick line 1 (P1) - completely", async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '3',
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Pick line 2 (P2) - partially (2 TUs out of 3)", async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU2.qrCode,
            qtyEntered: 2,
            expectQtyEntered: "3",
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
        });
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '3 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Skip line 3 (P3) - do not pick", async () => {
        // Intentionally not picking P3
    });

    await test.step("Complete the picking job", async () => {
        await PickingJobScreen.complete();
    });

    await test.step("Assert shipment schedules: P2 and P3 should be closed", async () => {
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            isClosed: false,
                            qtyPicked: [{
                                qtyPicked: "12 PCE",
                                qtyTUs: 3,
                                qtyLUs: 1,
                                vhu: 'vhu1',
                                tu: 'tu1',
                                lu: 'lu1',
                                processed: true,
                                shipmentLineId: 'shipmentLineId1',
                            }]
                        },
                        P2: {
                            isClosed: true,
                            qtyPicked: [{
                                qtyPicked: "8 PCE",
                                qtyTUs: 2,
                                qtyLUs: 1,
                                vhu: 'vhu2',
                                tu: 'tu2',
                                lu: 'lu2',
                                processed: true,
                                shipmentLineId: 'shipmentLineId2',
                            }]
                        },
                        P3: {
                            isClosed: true,
                        },
                    }
                }
            },
        });
    });

    await test.step("Restore sysconfigs", async () => {
        if (previousSysconfigs && Object.keys(previousSysconfigs).length > 0) {
            await Backend.setSysconfigs(previousSysconfigs);
        }
    });
});
