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
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';
import { expect } from '@playwright/test';
import { SelectPickTargetLUScreen } from '../../utils/screens/picking/ReopenLUScreen';
import { InventoryJobsListScreen } from '../../utils/screens/inventory/InventoryJobsListScreen';
import { InventoryJobScreen } from '../../utils/screens/inventory/InventoryJobScreen';

const createMasterdata = async ({
                                    language = 'en_US',
                                    allowCompletingPartialPickingJob = false,
                                    shipOnCloseLU = false,
                                    salesOrdersQty = 12,
                                } = {}) => {
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
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_NOT_RECOGNIZED');
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

//
// Unpick wrong HU, repick correct one:
// Pick from HU1, realize it's wrong, unpick via step screen, pick from HU2 instead, complete.
// Verifies that state is consistent after undo (line returns to 0 TU picked).
//
// noinspection JSUnusedLocalSymbols
test('Unpick wrong HU, repick correct one', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Unpick wrong HU and pick correct one');
    allure.severity('critical');

    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: { "P1": { prices: [{ price: 1 }] } },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
                "HU2": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }]
                },
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await test.step("Pick from HU1 (wrong one)", async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Unpick HU1", async () => {
        await PickingJobScreen.clickLineButton({ index: 1 });
        await PickingJobLineScreen.waitForScreen();
        await PickingJobLineScreen.clickStepButton({ index: 0 });
        await PickingJobStepScreen.unpick();
        await PickingJobLineScreen.goBack();
    });

    await test.step("Verify line is back to unpicked state", async () => {
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });
    });

    await test.step("Pick from HU2 (correct one) and complete", async () => {
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    });

    await PickingJobScreen.complete();
    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
        }
    });
});

//
// Scan invalid HU QR code and recover:
// Scan a non-existent HU QR code → error toast → verify screen still functional → pick real HU → complete.
//
// noinspection JSUnusedLocalSymbols
test('Scan invalid HU QR code and recover', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - invalid HU QR code');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await expectErrorToast('Scanning non-existent HU QR code', async () => {
        await PickingJobScreen.pickHU({
            qrCode: 'HU#1#{"id":"ffffffffffffffffffffffffffff-99999","packingInfo":{"huUnitType":"LU","packingInstructionsId":1,"caption":"NonExistent"},"product":{"id":1,"code":"FAKE","name":"FAKE"},"attributes":[]}',
            isScanDirectly: true,
            expectedPickDirectly: true,
        });
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_HU_NOT_FOUND');
    });

    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.complete();
});

//
// Scan a Leich+Mehl QR code (LMQ format) with an empty lot-number part.
// Format: LMQ#1#<weight>#<date>#<lot>#<product>
// Empty lot → backend throws PICKING_LM_QR_NO_LOT_NUMBER at scan time.
//
// noinspection JSUnusedLocalSymbols
test('Scan LM QR code without lot number → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - specialty QR code formats');
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

    await expectErrorToast('Scan LMQ QR code with empty lot', async () => {
        // Wait for the API response before returning: type() dispatches keyboard events
        // synchronously and returns immediately, but the barcode hook flushes the buffer
        // on an interval (~600ms). A blur/refocus timer in BarcodeScannerComponent can
        // reset that interval, pushing the API call past expectErrorToast's 2 s grace window.
        const responsePromise = page.waitForResponse(
            resp => resp.url().includes('/picking/nextEligibleLineToPack'),
            { timeout: 5000 }
        );
        await PickingJobScreen.pickHU({
            qrCode: 'LMQ#1#25.5#31.12.2025##',
            isScanDirectly: true,
            expectedPickDirectly: true,
        });
        await responsePromise;
    }, ({ textContent }) => {
        expect(textContent).toContain('PICKING_LM_QR_NO_LOT_NUMBER');
    });

    await PickingJobScreen.waitForScreen();
});

//
// Scan a Leich+Mehl QR code with a non-empty lot number that has no matching HU in stock.
// Format: LMQ#1#<weight>#<date>#<lot>
// Lot present but unknown → backend returns PICKING_NO_HU_FOR_EXTERNAL_LOT.
//
// noinspection JSUnusedLocalSymbols
test('Scan LM QR code with unknown lot number → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - specialty QR code formats');
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

    // Lot "NOSUCHLOT" is non-empty (no PICKING_LM_QR_NO_LOT_NUMBER) but no HU carries
    // this external lot number in stock → PICKING_NO_HU_FOR_EXTERNAL_LOT.
    await expectErrorToast('Scan LMQ QR code with unknown lot', async () => {
        // Same reason as the test above: wait for the API response so the toast
        // arrives within expectErrorToast's 2 s grace window.
        const responsePromise = page.waitForResponse(
            resp => resp.url().includes('/picking/nextEligibleLineToPack'),
            { timeout: 5000 }
        );
        await PickingJobScreen.pickHU({
            qrCode: 'LMQ#1#25.5#31.12.2025#NOSUCHLOT',
            isScanDirectly: true,
            expectedPickDirectly: true,
        });
        await responsePromise;
    }, ({ textContent }) => {
        expect(textContent).toContain('PICKING_NO_HU_FOR_EXTERNAL_LOT');
    });

    await PickingJobScreen.waitForScreen();
});

//
// Register a custom QR format (Constant prefix + LotNo), then scan a QR string whose
// lot value has no matching HU in stock.  Backend returns PICKING_QR_HU_NOT_FOUND_BY_ATTRIBUTE.
//
// noinspection JSUnusedLocalSymbols
test('Scan custom QR code with unknown lot → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - specialty QR code formats');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                },
            },
            customQRCodeFormats: [{
                name: 'E2E_ATTR',
                parts: [
                    { startPosition: 1, endPosition: 4, type: 'CONSTANT', constantValue: 'TST1' },
                    { startPosition: 5, endPosition: 14, type: 'LOT' },
                ],
            }],
            bpartners: { 'BP1': {} },
            warehouses: { 'wh': {} },
            pickingSlots: { slot1: {} },
            products: { 'P1': { prices: [{ price: 1 }] } },
            packingInstructions: {
                'PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU1': { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                'SO1': {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
                },
            },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    // "TST1" matches the Constant anchor (pos 1-4); "NOSUCHLOT0" (pos 5-14) is the lot value.
    // No HU in stock carries this custom QR code → PICKING_QR_HU_NOT_FOUND_BY_ATTRIBUTE.
    await expectErrorToast('Scan custom QR with unknown lot', async () => {
        await PickingJobScreen.pickHU({
            qrCode: 'TST1NOSUCHLOT0',
            isScanDirectly: true,
            expectedPickDirectly: true,
        });
    }, ({ textContent }) => {
        expect(textContent).toContain('PICKING_QR_HU_NOT_FOUND_BY_ATTRIBUTE');
    });

    await PickingJobScreen.waitForScreen();
});

//
// Register a custom QR format (Constant prefix + ProductCode), then scan a QR string whose
// product code does not match the picking line's expected product.
// Backend returns PICKING_QR_PRODUCT_NOT_MATCHING.
//
// noinspection JSUnusedLocalSymbols
test('Scan custom QR code with wrong product code → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - specialty QR code formats');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                },
            },
            customQRCodeFormats: [{
                name: 'E2E_PROD',
                parts: [
                    { startPosition: 1, endPosition: 4, type: 'CONSTANT', constantValue: 'TST2' },
                    { startPosition: 5, endPosition: 14, type: 'PRODUCT_CODE' },
                ],
            }],
            bpartners: { 'BP1': {} },
            warehouses: { 'wh': {} },
            pickingSlots: { slot1: {} },
            products: { 'P1': { prices: [{ price: 1 }] } },
            packingInstructions: {
                'PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU1': { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            salesOrders: {
                'SO1': {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
                },
            },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    // "TST2" matches the Constant anchor (pos 1-4); "WRONGPROD9" (pos 5-14) is the product code.
    // "WRONGPROD9" does not match P1's product value → PICKING_QR_PRODUCT_NOT_MATCHING.
    await expectErrorToast('Scan custom QR with wrong product code', async () => {
        await PickingJobScreen.pickHU({
            qrCode: 'TST2WRONGPROD9',
            isScanDirectly: true,
            expectedPickDirectly: true,
        });
    }, ({ textContent }) => {
        expect(textContent).toContain('PICKING_QR_PRODUCT_NOT_MATCHING');
    });

    await PickingJobScreen.waitForScreen();
});

//
// Partial pick blocked, recover by picking remaining:
// With allowCompletingPartialPickingJob=N, pick 1 of 3 TU → complete → error toast →
// pick remaining 2 TU → complete successfully.
//
// noinspection JSUnusedLocalSymbols
test('Partial pick blocked, recover by picking remaining', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Partial pick completion blocked then recovery');
    allure.severity('normal');

    const masterdata = await createMasterdata({ allowCompletingPartialPickingJob: false });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        qtyEntered: 1,
        expectQtyEntered: "3",
        qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
    });

    await expectErrorToast('Partial pick completion should be blocked', async () => {
        await PickingJobScreen.complete();
    });

    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        expectQtyEntered: '0',
        qtyEntered: '2',
    });
    await PickingJobScreen.complete();
});

//
// Scan HU containing a different product than the picking job expects:
// Error toast fires at scan time (backend product check), qty dialog never appears.
// Verify the screen recovers and picking continues with the correct HU.
//
// noinspection JSUnusedLocalSymbols
test('Scan HU with wrong product during picking', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - wrong product HU');
    allure.severity('normal');

    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { prices: [{ price: 1 }] },
                "P2": {},
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
                "PI2": { lu: "LU2", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
                "HU2": { product: 'P2', warehouse: 'wh', packingInstructions: 'PI2' },
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

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await expectErrorToast('Scan HU with wrong product', async () => {
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU2.qrCode,
            isScanDirectly: true,
            expectedPickDirectly: true,
        });
    }, ({ textContent }) => {
        expect(textContent).toContain('activities.picking.noMatchingLines');
    });

    await PickingJobScreen.waitForScreen();
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.complete();
});

//
// Simulate an HU removed from stock via a zero-count inventory (physical label still on
// the shelf), then scan its QR code during a picking job.  The real-world scenario:
// a warehouse worker counts the HU as 0, inventory is completed → HU destroyed in the
// system.  A picker later scans the dangling label → QR_CODE_HU_DESTROYED error toast.
//
// noinspection JSUnusedLocalSymbols
test('Scan destroyed HU QR code during picking → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - destroyed HU');
    allure.severity('normal');

    // HU1 is created in the warehouse.  An inventory document for P1 lets the test
    // count HU1 as 0 → complete inventory → HU1 status becomes 'D' (Destroyed).
    // SO1 provides a picking job so we can scan HU1 after it is destroyed.
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU'],
                },
            },
            bpartners: { 'BP1': {} },
            warehouses: { 'wh': {} },
            workplaces: { workplace1: { warehouse: 'wh' } },
            pickingSlots: { slot1: {} },
            products: { 'P1': { prices: [{ price: 1 }] } },
            packingInstructions: {
                'PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU1': { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' },
            },
            inventories: {
                'inv1': {
                    warehouse: 'wh',
                    date: '2025-03-01T00:00:00.000+02:00',
                    products: ['P1'],
                },
            },
            salesOrders: {
                'SO1': {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
                },
            },
        },
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    // Step 1: Destroy HU1 by counting it as 0 in inventory and completing the job.
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();
    await InventoryJobsListScreen.startJob({ index: 1 });
    await InventoryJobScreen.countHU({
        locatorQRCode: masterdata.warehouses.wh.locatorQRCode,
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        qtyCount: 0,
    });
    await InventoryJobScreen.complete();
    await InventoryJobsListScreen.goBack();

    // Step 2: Start picking for SO1, scan the now-destroyed HU1 → expect QR_CODE_HU_DESTROYED.
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    await expectErrorToast('Scan depleted (destroyed) HU', async () => {
        // Wait for the API response before returning: the barcode hook flushes on a
        // ~600ms interval, which may push the API call past expectErrorToast's 2 s grace window.
        const responsePromise = page.waitForResponse(
            resp => resp.url().includes('/picking/nextEligibleLineToPack'),
            { timeout: 5000 }
        );
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            isScanDirectly: true,
            expectedPickDirectly: true,
        });
        await responsePromise;
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_CODE_HU_DESTROYED');
    });

    await PickingJobScreen.waitForScreen();
});