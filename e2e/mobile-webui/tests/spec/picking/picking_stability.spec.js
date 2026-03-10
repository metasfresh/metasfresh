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
                    lines: [{ product: 'P1', qty: salesOrdersQty, piItemProduct: 'TU' }]
                },
            },
        }
    });
}

test.describe('Picking Stability - Pick and unpick recovery', () => {

    // noinspection JSUnusedLocalSymbols
    test('Pick from wrong HU, unpick, then pick from correct HU', async ({ page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Unpick wrong HU and pick correct one');
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
        // After unpick+repick, HU1 keeps 68 PCE (unpick decomposes LU into TUs, doesn't restore original LU)
        await Backend.expect({
            hus: {
                [masterdata.handlingUnits.HU1.qrCode]: { huStatus: 'A', storages: { P1: '68 PCE' } },
            }
        });
    });
});

test.describe('Picking Stability - Error handling', () => {

    // noinspection JSUnusedLocalSymbols
    test('Scan non-existent QR code for HU shows error toast', async ({ page }) => {
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
                qrCode: 'HU#1#{"id":"00000000000000000000000000000000-99999","packingInfo":{"huUnitType":"LU","packingInstructionsId":1,"caption":"NonExistent"},"product":{"id":1,"code":"FAKE","name":"FAKE"}}',
                isScanDirectly: true,
            });
        });

        // Verify we're still on the picking screen and can continue working
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '0 TU', qtyPickedCatchWeight: '' });

        // Now pick from the real HU and complete — proves the job is still functional after error
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.complete();
    });

    // noinspection JSUnusedLocalSymbols
    test('Go back from fully picked job then resume and complete', async ({ page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Go back from completed pick without completing');
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

        await test.step("Pick all 3 TU then go back without completing", async () => {
            await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
            await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
            await PickingJobScreen.goBack();
        });

        await test.step("Resume job and complete it", async () => {
            await PickingJobsListScreen.expectJobButtons([
                { salesOrderId: masterdata.salesOrders.SO1.id, alreadyStarted: true }
            ]);
            await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
            await PickingJobScreen.waitForScreen();
            await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
            await PickingJobScreen.complete();
        });

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
        });
    });

    // noinspection JSUnusedLocalSymbols
    test('Complete partial pick when not allowed shows error then recover', async ({ page }) => {
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

        // Recover: pick the remaining qty and complete successfully
        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            // After marking 2 as NOT_FOUND, expected remaining is 0
            // but user can still override and enter more
            expectQtyEntered: '0',
            qtyEntered: '2',
        });
        await PickingJobScreen.complete();
    });
});

test.describe('Picking Stability - Go back and resume', () => {

    // noinspection JSUnusedLocalSymbols
    test('Navigate away from started job and resume picking', async ({ page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Resume started picking job');
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

        await test.step("Pick 2 of 3 TU and navigate away", async () => {
            await PickingJobScreen.pickHU({
                qrCode: masterdata.handlingUnits.HU1.qrCode,
                expectQtyEntered: '3',
                qtyEntered: '2',
                qtyNotFoundReason: QTY_NOT_FOUND_REASON_NOT_FOUND,
            });
            await PickingJobScreen.expectLineButton({ index: 1, color: 'yellow', qtyToPick: '3 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });
            await PickingJobScreen.goBack();
        });

        await test.step("Resume the job — should show already started indicator", async () => {
            await PickingJobsListScreen.expectJobButtons([
                { salesOrderId: masterdata.salesOrders.SO1.id, alreadyStarted: true }
            ]);
            await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        });

        await test.step("Verify previous picks are preserved", async () => {
            await PickingJobScreen.waitForScreen();
            await PickingJobScreen.expectLineButton({ index: 1, color: 'yellow', qtyToPick: '3 TU', qtyPicked: '2 TU', qtyPickedCatchWeight: '' });
        });

        await test.step("Pick remaining and complete", async () => {
            // After NOT_FOUND, expected is 0 but user can still add more
            await PickingJobScreen.pickHU({
                qrCode: masterdata.handlingUnits.HU1.qrCode,
                expectQtyEntered: '0',
                qtyEntered: '1',
            });
            await PickingJobScreen.expectLineButton({ index: 1, color: 'green', qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
            await PickingJobScreen.complete();
        });
    });
});

test.describe('Picking Stability - Close and reopen LU', () => {

    // noinspection JSUnusedLocalSymbols
    test('Pick full qty, close LU, then ship on close LU', async ({ page }) => {
        allure.epic('E0105: Picking');
        allure.tag('F00230: MobileUI Picking');
        allure.tag('F00230');
        allure.story('Ship on close LU with full pick');
        allure.severity('critical');

        const masterdata = await createMasterdata({ shipOnCloseLU: true });

        await LoginScreen.login(masterdata.login.user);
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('picking');
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI.luName });

        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3' });
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

        await PickingJobScreen.closeTargetLU();
        await PickingJobScreen.complete();

        // After close + complete with shipOnCloseLU, the LU should be shipped (huStatus: E)
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
            pickingSlots: { [masterdata.pickingSlots.slot1.qrCode]: { queue: [] } },
            hus: {
                lu1: { huStatus: 'E', storages: { P1: '12 PCE' } },
            }
        });
    });
});
