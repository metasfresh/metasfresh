import { test } from "../../../playwright.config";
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen as PickingJobListScreen, PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user1: { language: "en_US", workplace: "workplace1" },
                user2: { language: "en_US", workplace: "workplace2" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    shipOnCloseLU: false,
                    allowCompletingPartialPickingJob: true,
                    activeWorkplaceRequired: true,
                    considerOnlyJobScheduledToWorkplace: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            workplaces: {
                workplace1: {},
                workplace2: {},
            },
            pickingSlots: { slot1: {} },
            products: {
                "P1": { price: 1 },
                "P2": { price: 1 },
                "P3": { price: 1 },
            },
            packingInstructions: {
                "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 10 },
                "PI2": { tu: "TU", product: "P2", qtyCUsPerTU: 100 },
                "PI3": { tu: "TU", product: "P3", qtyCUsPerTU: 100 },
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                "HU2": { product: 'P2', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
                "HU3": { product: 'P3', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        {
                            product: 'P1',
                            qty: 10,
                            schedules: [{ workplace: 'workplace1', qty: 7 }, { workplace: 'workplace2', qty: 3 }],
                        },
                        {
                            product: 'P2',
                            qty: 20,
                            schedules: [{ workplace: 'workplace1', qty: 11 }, { workplace: 'workplace2', qty: 9 }],
                        },
                        {
                            product: 'P3',
                            qty: 30,
                            schedules: [{ workplace: 'workplace1', qty: 17 }, { workplace: 'workplace2', qty: 13 }],
                        },
                    ]
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Pick one sales order to different workplaces', async ({ page }) => {
    const masterdata = await createMasterdata();

    await test.step("Picking from workplace1", async () => {
        await LoginScreen.login(masterdata.login.user1);

        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startPickingApplication();
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '7 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' });
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '11 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' });
        await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '17 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' });

        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 7,
        });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU2.qrCode,
            expectQtyEntered: 11,
        });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU3.qrCode,
            expectQtyEntered: 17,
        });

        await PickingJobScreen.complete();
        // await PickingJobScreen.goBack();

        await PickingJobListScreen.goBack();

        await ApplicationsListScreen.logout();
    });

    await test.step("Picking from workplace2", async () => {
        await LoginScreen.login(masterdata.login.user2);

        await ApplicationsListScreen.startPickingApplication();
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' })
        await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '9 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' })
        await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '13 Stk', qtyPicked: '0 Stk', qtyPickedCatchWeight: '' })

        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU1.qrCode,
            expectQtyEntered: 3,
        });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU2.qrCode,
            expectQtyEntered: 9,
        });
        await PickingJobScreen.pickHU({
            qrCode: masterdata.handlingUnits.HU3.qrCode,
            expectQtyEntered: 13,
        });

        await PickingJobScreen.complete();
        // await PickingJobScreen.goBack();

        await PickingJobListScreen.goBack();
    });
});
