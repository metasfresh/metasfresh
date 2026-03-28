import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { expectErrorToast } from '../../utils/common';
import { GetQuantityDialog, QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';
import { SelectPickTargetLUScreen } from '../../utils/screens/picking/ReopenLUScreen';
import { BarcodeScannerComponent } from '../../utils/components/BarcodeScannerComponent';

const createMasterdata = async ({
                                    language = 'en_US',
                                    allowCompletingPartialPickingJob = false,
                                    shipOnCloseLU = false,
                                    salesOrdersQty = 12,
                                    qtyCUsPerTU = 4,
                                    shipperConfig = null,
                                } = {}) => {
    const shippers = shipperConfig ? {
        "SHP": {
            name: "DHL Mock",
            gateway: "dhl",
            dhlConfig: shipperConfig,
        }
    } : undefined;

    return await Backend.createMasterdata({
        language,
        request: {
            login: { user: { language } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: allowCompletingPartialPickingJob ?? false,
                }
            },
            shippers,
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
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' }
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    shipper: shipperConfig ? 'SHP' : undefined,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: salesOrdersQty, piItemProduct: 'TU' }]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Simple picking test', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Simple picking workflow');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: []
                    }
                }
            }
        }
    });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
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
        pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } }, // the queue is empty because LU is not yet closed
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
        }
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            }
        },
        pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } }, // the queue is empty because LU everything is shipped now
        hus: {
            lu1: { huStatus: 'E', storages: { P1: '12 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Pick - unpick', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Picking pick and unpick');
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

    await test.step("Pick the HU", async () => {
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
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
            pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } }, // the queue is empty because the current LU target is not closed
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
                lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
            }
        });
    });

    await test.step("Un-pick the HU", async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickStepButton({ index: 0 });
        await PickingJobStepScreen.unpick();
        await PickingJobLineScreen.goBack();
    });

    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.closeTargetLU();

    await PickingJobScreen.abort();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "12 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-', },
                            { qtyPicked: "-4 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-', },
                            { qtyPicked: "-4 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-', },
                            { qtyPicked: "-4 PCE", qtyTUs: 1, qtyLUs: 0, vhu: 'vhu1', tu: 'tu1', lu: '-', processed: false, shipmentLineId: '-', },
                        ]
                    }
                }
            },
        },
        pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } }, // the queue is empty because nothing was actually picked
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
            lu1: { huStatus: 'D', storages: { P1: '0 PCE' } },
            // TODO find a way to test those 3 new TUs created when unpicking the LU
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Scan invalid picking slot QR code', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Picking error handling');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

    await expectErrorToast('Scanning invalid QR code', async () => {
        await PickingJobScreen.scanPickingSlot({ qrCode: 'this is an invalid QR code' });
    });
});

// noinspection JSUnusedLocalSymbols
test('Test picking line complete status - draft | in progress | complete', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Picking line status tracking');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await PickingJobScreen.expectLineButton({ index: 1, color: 'red', qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    await test.step('Partially pick the line', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '3',
            qtyEntered: '2',
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND
        });
        await PickingJobScreen.expectLineButton({ index: 1, color: 'yellow', qtyToPick: '3 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });
    });
    await test.step('Partially pick the line again, expect line completely picked', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '0',
            qtyEntered: '1',
        });
        await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    });

    await PickingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Scan HU and cancel — nothing picked', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.tag('F00230.1');
    allure.story('Cancel picking after scanning HU (QA scenario 1)');
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

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    // Scan the HU directly — dialog opens
    await BarcodeScannerComponent.type(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.waitForDialog();
    await GetQuantityDialog.expectQtyEntered('3');

    // Cancel — nothing should be picked
    await GetQuantityDialog.clickCancel();
    await PickingJobScreen.waitForScreen();

    // Verify: still 0 TU picked
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: []
                    }
                }
            }
        },
    });
});

test.describe('Picking Job Completion', () => {

    // noinspection JSUnusedLocalSymbols
    test("Should fail when partial picking and allowCompletingPartialPickingJob = N", async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
        allure.story('Picking job completion');
        allure.severity('normal');

        const masterdata = await createMasterdata({ allowCompletingPartialPickingJob: false });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication("picking");
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            qtyEntered: 2,
            expectQtyEntered: "3",
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND
        });
        await expectErrorToast('All steps must be completed in order to complete the job.', async () => {
            await PickingJobScreen.complete();
        });
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "8 PCE", qtyTUs: 2, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        }
                    }
                }
            }
        });

    });

    // noinspection JSUnusedLocalSymbols
    test("Should succeed when partial picking and allowCompletingPartialPickingJob = Y", async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
        allure.story('Picking job completion');
        allure.severity('normal');

        const masterdata = await createMasterdata({ allowCompletingPartialPickingJob: true });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication("picking");
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            qtyEntered: 2,
            expectQtyEntered: "3",
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
        });
        await PickingJobScreen.complete()
    });

});

// noinspection JSUnusedLocalSymbols
test('Ship on close LU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Ship on close LU');
    allure.severity('normal');

    const masterdata = await createMasterdata({ shipOnCloseLU: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });

    await test.step("Pick and close the LU", async () => {
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
        await Backend.expect({
            pickings: {
                [pickingJobId]: {
                    shipmentSchedules: {
                        P1: {
                            qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                        }
                    }
                },
            },
            pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } }, // the queue is empty because the current target LU is not yet closed
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
                lu1: { huStatus: 'S', storages: { P1: '12 PCE' } },
            }
        });

        await PickingJobScreen.closeTargetLU();
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            },
        },
        pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } }, // the queue is empty because LU was shipped after LU target was closed
        hus: {
            lu1: { huStatus: 'E', storages: { P1: '12 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Close LU / Reopen LU', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Close and reopen LU');
    allure.severity('normal');

    const masterdata = await createMasterdata({ language: 'de_DE' });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startPickingApplication();
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.closeTargetLU();

    await PickingJobScreen.clickReopenLUButton();
    await SelectPickTargetLUScreen.waitForScreen();
});

// noinspection JSUnusedLocalSymbols
test('Check launcher already started indicator', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('Launcher started indicator');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, alreadyStarted: false }
    ]);

    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.goBack();

    await PickingJobsListScreen.expectJobButtons([
        { salesOrderId: masterdata.salesOrders.SO1.id, alreadyStarted: true }
    ]);
});

// noinspection JSUnusedLocalSymbols
test('TU picking with CU-per-TU > 1 — step qty displayed as TU count', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.tag('F00230.1');
    allure.story('TU picking qty conversion (CU-per-TU > 1)');
    allure.severity('critical');

    // Setup with qtyCUsPerTU=2 to test that step qty is displayed as TU count (not CU count).
    // Sales order: 4 CU = 2 TU with packing "x 2 Stk" (2 CU per TU).
    // Without the fix, the step would show "4" (CU) instead of "2" (TU), and picking 4 TUs would fail.
    const masterdata = await createMasterdata({ salesOrdersQty: 4, qtyCUsPerTU: 2 });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    // Line should show 2 TU (not 4 CU)
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    // Pick via direct scan — dialog should propose 2 TUs
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        isScanDirectly: true,
        expectQtyEntered: '2',
    });

    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '2 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });

    // Verify backend state BEFORE completion: 4 CU = 2 TU picked, not yet processed
    await Backend.expect({
        title: 'After pick: 2 TU picked, source HU reduced',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "4 PCE", qtyTUs: 2, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    }
                }
            }
        },
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '36 PCE' } },
            lu1: { huStatus: 'S', storages: { P1: '4 PCE' } },
        }
    });

    await PickingJobScreen.complete();

    // Verify backend state AFTER completion: shipment created, LU shipped
    await Backend.expect({
        title: 'After complete: shipment created, LU shipped',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "4 PCE", qtyTUs: 2, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            }
        },
        hus: {
            lu1: { huStatus: 'E', storages: { P1: '4 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Switch device mid-picking — logout and resume on new session', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.tag('F00230.1');
    allure.story('Device switching mid-picking (QA scenario 13)');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    // === DEVICE 1: Start picking, pick partially, leave ===
    await test.step('Device 1: Start job and pick partially', async () => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

        // Pick 2 out of 3 TUs
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '3',
            qtyEntered: '2',
            qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });

        // Leave without completing — simulates closing the app on device 1
        await PickingJobScreen.goBack();
        await PickingJobsListScreen.waitForScreen();
    });

    await test.step('Device 1: Logout', async () => {
        await PickingJobsListScreen.goBack();
        await ApplicationsListScreen.logout();
    });

    // === DEVICE 2: Login and resume the same job ===
    await test.step('Device 2: Login and find the in-progress job', async () => {
        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);

        // Job should show "already started" indicator
        await PickingJobsListScreen.expectJobButtons([
            { salesOrderId: masterdata.salesOrders.SO1.id, alreadyStarted: true }
        ]);
    });

    await test.step('Device 2: Resume and complete the job', async () => {
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

        // Should see 2 TU already picked from device 1
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });

        // Pick the remaining 1 TU
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: '0',
            qtyEntered: '1',
        });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

        await PickingJobScreen.complete();
    });
});

// noinspection JSUnusedLocalSymbols
test('Pick and ship with DHL label (via mock)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.tag('F00230.1');
    allure.story('DHL label generation during picking (QA scenario 11)');
    allure.severity('critical');

    // Setup with DHL shipper pointing to WireMock
    // CI: http://wiremock:8080 (Docker internal), Local: http://localhost:18080
    const wiremockUrl = process.env.WIREMOCK_BASE_URL || 'http://localhost:18080';
    const masterdata = await createMasterdata({
        shipOnCloseLU: true,
        shipperConfig: {
            apiUrl: wiremockUrl,
            applicationID: 'mock_app',
            applicationToken: 'mock_token',
            accountNumber: '22222222220104',
            username: 'mock_user',
            signature: 'mock_sig',
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

    // Pick all 3 TUs
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        isScanDirectly: true,
        expectQtyEntered: '3',
    });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

    // Close LU — this triggers DHL label generation via WireMock
    await PickingJobScreen.closeTargetLU();

    // Complete the job
    await PickingJobScreen.complete();

    // Verify shipment was created and LU shipped
    await Backend.expect({
        title: 'DHL picking: shipment created, LU shipped',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: "12 PCE", qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            }
        },
        hus: {
            lu1: { huStatus: 'E', storages: { P1: '12 PCE' } },
        }
    });
});