import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickingJobStepScreen } from "../../utils/screens/picking/PickingJobStepScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { expectErrorToast, VERY_SLOW_ACTION_TIMEOUT } from '../../utils/common';
import { QTY_NOT_FOUND_REASON_NOT_FOUND } from '../../utils/screens/picking/GetQuantityDialog';
import { SelectPickTargetLUScreen } from '../../utils/screens/picking/ReopenLUScreen';
import { ConfirmActivityErrorPanel } from '../../utils/components/ConfirmActivityErrorPanel';
import { expect } from '@playwright/test';
import { InventoryJobsListScreen } from '../../utils/screens/inventory/InventoryJobsListScreen';
import { InventoryJobScreen } from '../../utils/screens/inventory/InventoryJobScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';

const createMasterdata = async ({
                                    language = 'en_US',
                                    allowCompletingPartialPickingJob = false,
                                    shipOnCloseLU = false,
                                    salesOrdersQty = 12,
                                    shipperConfig = null,
                                    extraSysconfigs,
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
            ...(extraSysconfigs && { sysconfigs: extraSysconfigs }),
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
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
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
            // The picked LU carries the consignee (bpartner + delivery location) stamped at pick time.
            // BP1 is declared without an explicit location, so bpartnerLocation resolves to its single
            // default ship-to via the _singleBPLocationI fallback (same identifier as the bpartner).
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
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
            lu1: { huStatus: 'E', storages: { P1: '12 PCE' }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
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
            },
            // The partially-picked LU still carries the consignee stamped at pick time (BP1 has no
            // explicit location → single default ship-to via the _singleBPLocationI fallback).
            hus: {
                lu1: { huStatus: 'S', storages: { P1: '8 PCE' }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
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

    // noinspection JSUnusedLocalSymbols
    test('Network failure on complete shows retry panel; Retry then succeeds', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Picking job completion recovers from network flake');
        allure.severity('normal');

        const masterdata = await createMasterdata({ allowCompletingPartialPickingJob: true });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });

        const confirmationRoute = '**/userWorkflows/wfProcess/**/userConfirmation';
        await test.step('Block userConfirmation to simulate a network failure', async () => {
            await page.route(confirmationRoute, route => route.abort('failed'));
        });

        await PickingJobScreen.completeExpectingNetworkError();

        await test.step('Release the block and retry', async () => {
            await page.unroute(confirmationRoute);
        });
        await ConfirmActivityErrorPanel.clickRetry();
        await PickingJobsListScreen.waitForScreen({ timeout: VERY_SLOW_ACTION_TIMEOUT });
    });

    // Regression guard for flaky-test registry case 05 (recreate_shipment_after_void.spec.js): the
    // ORDINARY complete() must recover on its own from a single slow/lost confirmation response — the
    // real cause of the complete->jobs-list flake — without the caller doing anything special. Here the
    // FIRST userConfirmation is aborted at the network layer (the same signal a timed-out response
    // produces: the inline retry panel), then released; settleCompleteToJobsList must tap Retry itself
    // and land on the jobs list. Distinct from the test above, which drives the retry manually.
    //
    // noinspection JSUnusedLocalSymbols
    test('complete() recovers on its own from a transient confirmation network failure', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Picking job completion recovers from network flake');
        allure.severity('normal');

        const masterdata = await createMasterdata({ allowCompletingPartialPickingJob: true });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });

        const confirmationRoute = '**/userWorkflows/wfProcess/**/userConfirmation';
        let failedOnce = false;
        await test.step('Fail ONLY the first userConfirmation, then let the retry through', async () => {
            await page.route(confirmationRoute, async (route) => {
                if (!failedOnce) {
                    failedOnce = true;
                    await route.abort('failed');
                } else {
                    await route.continue();
                }
            });
        });

        // complete() must ride out the first failure via its own bounded Retry and reach the jobs list.
        await PickingJobScreen.complete();
        expect(failedOnce, 'the confirmation route should have fired (first attempt failed)').toBe(true);

        await page.unroute(confirmationRoute);
    });

    // noinspection JSUnusedLocalSymbols
    test('Cancel on retry panel hides it and leaves the job resumable', async ({ page }) => {
        // === ALLURE METADATA ===
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Picking job completion recovers from network flake');
        allure.severity('normal');

        const masterdata = await createMasterdata({ allowCompletingPartialPickingJob: true });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });

        const confirmationRoute = '**/userWorkflows/wfProcess/**/userConfirmation';
        await page.route(confirmationRoute, route => route.abort('failed'));

        await PickingJobScreen.completeExpectingNetworkError();
        await ConfirmActivityErrorPanel.clickCancel();
        await ConfirmActivityErrorPanel.waitForPanelDetached();

        // Clean up the route interception so it doesn't affect follow-on steps if the test is extended later.
        await page.unroute(confirmationRoute);
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
                // The picked LU carries the consignee stamped at pick time (BP1 has no explicit
                // location → single default ship-to via the _singleBPLocationI fallback).
                lu1: { huStatus: 'S', storages: { P1: '12 PCE' }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
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
            lu1: { huStatus: 'E', storages: { P1: '12 PCE' }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
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
test('Scan invalid HU QR code and recover', async ({ page }, testInfo) => {
    // Extended timeout: this test does error toast recovery + full pick cycle.
    // Since GRAI validation was added to picking (gh#23119), the total time
    // occasionally exceeds the default 120s on slower CI runners.
    testInfo.setTimeout(180_000);

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

// A long HU QR code can be split mid-stream by a slow scanner device: it arrives as TWO bad scans —
// the head fragment (keeps the valid "HU#<version>#" prefix but carries truncated, unparseable JSON) and
// the tail fragment (the prefix-less remainder). Each fragment, scanned on its own exactly as the device
// delivers it, must surface the friendly QR_NOT_RECOGNIZED message — never a silent failure or the raw
// "Failed converting payload" developer error.
//
// TWO delivery paths are covered per fragment:
//
//  • NO TERMINATOR (the DEFAULT / main coverage — what THIS customer ships): the production Zebra device
//    sends NO Enter/Tab suffix (the terminator broke login on this device, so it was removed). With no
//    end-of-scan key the fragment must still surface QR_NOT_RECOGNIZED on its own:
//      - TAIL is prefix-less (NOT_APPLICABLE) → it is NOT held back, so it flushes on the idle gap and
//        errors fast; no special config needed.
//      - HEAD keeps the "HU#" prefix with truncated JSON (PARTIAL_SCAN) → content-based completion holds
//        it back (indistinguishable from a still-arriving chunked scan) until the long idle-abandon
//        window. To keep the E2E fast we lower that window for the head test via the sysconfig
//        barcodeScanner.inputText.idleAbandonMillis, applied through the `sysconfigs` masterdata key
//        (the same sysconfig-override mechanism barcode_scanner_modes.spec.js uses for its scanner
//        settings). This exercises the REAL no-Enter path we ship: the partial is held, then the
//        abandon window surfaces the error.
//
//  • ENTER TERMINATOR (an additional VARIANT, kept for coverage): a device configured with an Enter/Tab
//    suffix (e.g. Zebra DataWedge "Enter as string") supplies an explicit end-of-scan signal, so the
//    fragment force-completes immediately and its error surfaces at once — no abandon-window wait.
//
// Each scenario is a SEPARATE test starting from a clean toast state. The app shows exactly one error
// toast per scan by design (a fixed toastId — see mobile-webui CLAUDE.md "the user must see exactly ONE
// error"), so two back-to-back scans within one test would race that de-duplication and leave the second
// fragment's toast suppressed. Asserting a single shared toast instead would also fail to catch a
// tail-specific regression — a raw tail error would be hidden under the head's friendly toast. One scan
// per scenario keeps each fragment's handling independently observable.

// A short idle-abandon window (ms) for the no-terminator HEAD test — the head is held as PARTIAL_SCAN
// until this window elapses, then flushed as the QR_NOT_RECOGNIZED error. A low value keeps the E2E fast
// while still exercising the real held-then-abandoned path.
//
// It must be comfortably SHORT: expectErrorToast() gives the assertion only ~2 s of grace after pickHU()
// returns (which, on the direct-scan HEAD path, is almost immediately — the scan dispatch is synchronous
// and no qty dialog opens). The abandon flush → backend round-trip → error-toast render must all land
// inside that budget under CI load, so the window is set well below it. The hook's idle interval ticks
// every rateMs*2 (rateMs = the 300 ms debounce default here → 600 ms), so 500 ms fires reliably on the
// first tick (~600 ms) with large margin, and still sits above rateMs so it never trips the normal
// debounce flush. (The window is deliberately tiny for the test ONLY because this HEAD is dispatched as
// ONE synchronous keystroke burst with zero inter-chunk gaps — there is no slow chunked scan here to
// protect; production keeps the 15000 ms default that clears the real 3-8 s inter-chunk gaps.)
const FAST_ABANDON_MS = 500;
const FAST_ABANDON_SYSCONFIG = { 'mobileui.frontend.barcodeScanner.inputText.idleAbandonMillis': String(FAST_ABANDON_MS) };

const expectTruncatedHuQRFragmentShowsFriendlyErrorDuringPicking = async ({ which, terminator }) => {
    // For the no-terminator HEAD path, lower the idle-abandon window so the held partial surfaces fast.
    // The TAIL is NOT_APPLICABLE (not held), and the Enter variants force-complete immediately, so
    // neither needs the override.
    const needsFastAbandon = !terminator && which === 'head';
    const masterdata = await createMasterdata(
        needsFastAbandon ? { extraSysconfigs: FAST_ABANDON_SYSCONFIG } : {}
    );

    const fullHuQRCode = masterdata.handlingUnits.HU1.qrCode;
    const splitAt = Math.floor(fullHuQRCode.length / 2);
    const truncatedHead = fullHuQRCode.substring(0, splitAt);
    const truncatedTail = fullHuQRCode.substring(splitAt);
    expect(truncatedHead).toMatch(/^HU#/);     // head keeps the HU# prefix, payload is cut off
    expect(truncatedTail).not.toMatch(/^HU#/); // tail is the prefix-less remainder
    const fragment = which === 'head' ? truncatedHead : truncatedTail;

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

    const label = `Scan the truncated HU QR ${which} during picking${terminator ? ` (${terminator} terminator)` : ' (no terminator)'}`;
    await expectErrorToast(label, async () => {
        await PickingJobScreen.pickHU({
            qrCode: fragment,
            isScanDirectly: true,
            // terminator undefined → no end-of-scan key (the customer's real no-terminator device);
            // 'Enter' → the additional device-suffix variant.
            terminator,
            expectedPickDirectly: true,
        });
    }, ({ textContent }) => {
        expect(textContent).toContain('QR_NOT_RECOGNIZED');
    });
};

// === DEFAULT / MAIN COVERAGE: NO terminator (the customer ships WITHOUT an Enter/Tab suffix). ===

// noinspection JSUnusedLocalSymbols
test('Scan a truncated (split) HU QR HEAD during picking, NO terminator → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - invalid HU QR code');
    allure.severity('critical');

    await expectTruncatedHuQRFragmentShowsFriendlyErrorDuringPicking({ which: 'head' });
});

// noinspection JSUnusedLocalSymbols
test('Scan a truncated (split) HU QR TAIL during picking, NO terminator → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - invalid HU QR code');
    allure.severity('critical');

    await expectTruncatedHuQRFragmentShowsFriendlyErrorDuringPicking({ which: 'tail' });
});

// === ADDITIONAL VARIANT: Enter terminator (device configured with an Enter/Tab suffix). ===

// noinspection JSUnusedLocalSymbols
test('Scan a truncated (split) HU QR HEAD during picking, Enter terminator → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - invalid HU QR code');
    allure.severity('critical');

    await expectTruncatedHuQRFragmentShowsFriendlyErrorDuringPicking({ which: 'head', terminator: 'Enter' });
});

// noinspection JSUnusedLocalSymbols
test('Scan a truncated (split) HU QR TAIL during picking, Enter terminator → user-friendly error', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Error handling - invalid HU QR code');
    allure.severity('critical');

    await expectTruncatedHuQRFragmentShowsFriendlyErrorDuringPicking({ which: 'tail', terminator: 'Enter' });
});

//
// Two valid HU QR codes scanned back-to-back with NO terminator (the customer's real device) must be
// recognised as TWO distinct scans, never merged into one unparseable "code1+code2" string. Each is a
// complete HU QR whose closing JSON brace force-completes it on content (COMPLETE_SCAN), so the reader
// separates them without an Enter/Tab suffix and without waiting on the idle timer. A merge regression
// would fail the FIRST pick (the buffer would still be accumulating the second code) — this asserts both
// picks land, proving separation on the shipped no-terminator path.
//
// noinspection JSUnusedLocalSymbols
test('Scan two valid HU QR codes back-to-back, NO terminator → both recognised as distinct picks (not merged)', async ({ page }) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');
    allure.story('Scan HU barcodes');
    allure.severity('critical');

    // TWO products, each on its own SO line (3 TU / 12 PCE), one full HU each. Both PIs use the SAME tu
    // name 'TU' (distinct products ⇒ distinct HUPIItemProduct identifiers "TU_P1"/"TU_P2", so no
    // "Identifier already exists" collision), which lets a SINGLE target LU accept both (the LU knows a
    // 'TU' sub-instruction). Each scan then picks its own line at the dialog's DEFAULT full qty (3) — no
    // partial pick, no not-found reason (which would close the line), no second setTargetLU (which stalls
    // on a "Select Target" screen). If the two no-terminator scans were MERGED into one garbage code, the
    // first HU would not resolve and pickHU would raise an error toast; two clean default-qty picks prove
    // the reader separated the two back-to-back scans.
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
            bpartners: { 'BP1': {} },
            warehouses: { 'wh': {} },
            pickingSlots: { slot1: {} },
            products: { 'P1': { prices: [{ price: 1 }] }, 'P2': { prices: [{ price: 1 }] } },
            packingInstructions: {
                'PI1': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
                'PI2': { lu: 'LU2', qtyTUsPerLU: 20, tu: 'TU', product: 'P2', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU1': { product: 'P1', warehouse: 'wh', packingInstructions: 'PI1' },
                'HU2': { product: 'P2', warehouse: 'wh', packingInstructions: 'PI2' },
            },
            salesOrders: {
                'SO1': {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 12, piItemProduct: 'TU' },
                        { product: 'P2', qty: 12, piItemProduct: 'TU' },
                    ],
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
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });

    // First scan (no terminator): HU1 (P1) → its line picked at full default qty. A merge regression would
    // garble this scan so it never resolves and pickHU would raise an error toast.
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, isScanDirectly: true, expectQtyEntered: '3' });
    // Second scan (no terminator), immediately after: HU2 (P2) → a DISTINCT pick into the same target LU.
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, isScanDirectly: true, expectQtyEntered: '3' });

    // Both back-to-back scans landed as two separate, valid picks → the job completes.
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
    // Select the P1 line explicitly — routes through PickingJobPickCommand (line-specific),
    // which surfaces the error via orElseThrow() instead of the generic "no matching lines" path.
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();

    // "TST2" matches the Constant anchor (pos 1-4); "WRONGPROD9" (pos 5-14) is the product code.
    // "WRONGPROD9" does not match P1's product value → PICKING_QR_PRODUCT_NOT_MATCHING.
    await expectErrorToast('Scan custom QR with wrong product code', async () => {
        await PickLineScanScreen.typeQRCode('TST2WRONGPROD9');
    }, ({ textContent }) => {
        expect(textContent).toContain('PICKING_QR_PRODUCT_NOT_MATCHING');
    });

    await PickLineScanScreen.waitForScreen();
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

    // While the job is still open, the picked target LU already carries the consignee (bpartner +
    // delivery location), stamped on the shipping target at pick time. BP1 is declared without an
    // explicit location, so bpartnerLocation resolves to its single default ship-to via the
    // _singleBPLocationI fallback (same identifier as the bpartner). The pickings block binds the
    // lu1 alias (via M_LU_HU_ID) AND gates on the shipment schedule becoming valid, so the hus read
    // below is not a pre-commit race.
    await Backend.expect({
        title: 'DHL picking: picked target LU carries consignee before close',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: '12 PCE', qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: false, shipmentLineId: '-' }]
                    }
                }
            }
        },
        hus: {
            lu1: { huStatus: 'S', storages: { P1: '12 PCE' }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        }
    });

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
                        qtyPicked: [{ qtyPicked: '12 PCE', qtyTUs: 3, qtyLUs: 1, vhu: 'vhu1', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }]
                    }
                }
            }
        },
        hus: {
            lu1: { huStatus: 'E', storages: { P1: '12 PCE' }, bpartner: 'BP1', bpartnerLocation: 'BP1' },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('DHL + catch weight picking (QA scenario 14)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230.1: MobileUI Order-based Picking');
    allure.tag('F00230.1');
    allure.story('DHL label + catch weight combined picking (QA scenario 14)');
    allure.severity('critical');

    // Setup: catch weight product with DHL shipper
    const wiremockUrl = process.env.WIREMOCK_BASE_URL || 'http://localhost:18080';
    const masterdata = await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'sales_order',
                    allowPickingAnyCustomer: true,
                    allowPickingAnyHU: true,
                    createShipmentPolicy: 'CL',
                    shipOnCloseLU: true,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            shippers: {
                SHP: {
                    name: 'DHL CatchWeight',
                    gateway: 'dhl',
                    dhlConfig: {
                        apiUrl: wiremockUrl,
                        applicationID: 'mock_app',
                        applicationToken: 'mock_token',
                        accountNumber: '22222222220104',
                        username: 'mock_user',
                        signature: 'mock_sig',
                    }
                }
            },
            bpartners: { BP1: {} },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: {
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    prices: [{ price: 5, uom: 'KGM', invoicableQtyBasedOn: 'CatchWeight' }],
                },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100, weightNet: 10, lotNo: 'lot1', bestBeforeDate: '2031-11-23' },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    shipper: 'SHP',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 12, piItemProduct: 'TU' }],
                },
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
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });
    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI.tuName });

    // Pick with catch weight — manual input mode, pick 1 CU
    await PickingJobScreen.pickHU({
        qrCode: masterdata.handlingUnits.HU1.qrCode,
        switchToManualInput: true,
        qtyEntered: '1',
        catchWeight: '0.789',
        qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
    });

    // Close LU — triggers DHL label via WireMock
    await PickingJobScreen.closeTargetLU();

    // Complete
    await PickingJobScreen.complete();

    // Verify shipment created with catch weight
    await Backend.expect({
        title: 'DHL + catch weight: shipment created',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: '1 PCE', qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1', catchWeight: '0.789 KGM' }]
                    }
                }
            }
        },
    });
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