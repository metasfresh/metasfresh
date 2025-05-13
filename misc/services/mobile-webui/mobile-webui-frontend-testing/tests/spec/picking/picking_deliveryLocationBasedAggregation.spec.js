import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {
                picking: {
                    aggregationType: "delivery_location",
                    allowPickingAnyCustomer: false,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickWithNewLU: true,
                    allowNewTU: true,
                    filterByQRCode: false,
                    anonymousPickHUsOnTheFly: false,
                    customers: [
                        { customer: "customer1" },
                        { customer: "customer2" },
                    ],
                }
            },
            bpartners: {
                "customer1": {
                    locations: {
                        customer1_location1: {},
                        customer1_location2: {},
                    }
                },
                "customer2": {
                    locations: {
                        customer2_location1: {},
                        customer2_location2: {},
                    }
                },
            },
            warehouses: {
                "wh": {},
            },
            pickingSlots: {
                slot1: {},
                slot2: {},
                slot3: {},
            },
            products: {
                "P1": { price: 1 },
                "P2": { price: 1 },
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4, tu_ean: '7617027667210' },
                "P2_7x3": { lu: "LU", qtyTUsPerLU: 7, tu: "P2_3CU", product: "P2", qtyCUsPerTU: 3 },
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4' },
                "P2_HU": { product: 'P2', warehouse: 'wh', packingInstructions: 'P2_7x3' },
                "P2_HU_2": { product: 'P2', warehouse: 'wh', packingInstructions: 'P2_7x3' },
                "P2_HU_3": { product: 'P2', warehouse: 'wh', packingInstructions: 'P2_7x3' },
            },
            salesOrders: {
                "SO1": {
                    location: 'customer1_location1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 20, piItemProduct: 'P1_4CU' },
                        { product: 'P2', qty: 21, piItemProduct: 'P2_3CU' },
                    ]
                },
                "SO2": {
                    location: 'customer1_location1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 24, piItemProduct: 'P1_4CU' },
                        { product: 'P2', qty: 18, piItemProduct: 'P2_3CU' },
                    ]
                },
                "SO3": {
                    location: 'customer1_location2',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 28, piItemProduct: 'P1_4CU' },
                        { product: 'P2', qty: 15, piItemProduct: 'P2_3CU' },
                    ]
                },
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('Delivery Location based aggregation', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.expectJobButtons([
        { customerLocationId: masterdata.bpartners.customer1.locations.customer1_location1.id },
        { customerLocationId: masterdata.bpartners.customer1.locations.customer1_location2.id },
    ]);

    await test.step('Picking job for customer1_location1', async () => {
        await PickingJobsListScreen.startJob({ index: 1, customerLocationId: masterdata.bpartners.customer1.locations.customer1_location1.id });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.P1_20x4.luName });

        await test.step('Line 1 - SO1, P1', async () => {
            await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 TU', qtyPicked: '0 TU' });
            await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode, expectQtyEntered: 5 /*TU*/ });
            await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '5 TU', qtyPicked: '5 TU' });
        });
        await test.step('Line 2 - SO2, P1', async () => {
            await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '6 TU', qtyPicked: '0 TU' });
            await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode, expectQtyEntered: 6 /*TU*/ });
            await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '6 TU', qtyPicked: '6 TU' });
        });
        await test.step('Line 3 - SO1, P2', async () => {
            await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '7 TU', qtyPicked: '0 TU' });
            await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.P2_HU.qrCode, expectQtyEntered: 7 /*TU*/ });
            await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '7 TU', qtyPicked: '7 TU' });
        });
        await test.step('Line 4 - SO2, P2', async () => {
            await PickingJobScreen.expectLineButton({ index: 4, qtyToPick: '6 TU', qtyPicked: '0 TU' });
            await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.P2_HU_2.qrCode, expectQtyEntered: 6 /*TU*/ });
            await PickingJobScreen.expectLineButton({ index: 4, qtyToPick: '6 TU', qtyPicked: '6 TU' });
        });

        await PickingJobScreen.waitForScreen();
        await PickingJobScreen.complete();
    });

});

