import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { HUConsolidationJobsListScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobsListScreen';
import { HUConsolidationJobScreen } from '../../utils/screens/huConsolidation/HUConsolidationJobScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { PickingSlotScreen } from '../../utils/screens/huConsolidation/PickingSlotScreen';

const createMasterdata = async ({
                                    products,
                                    packingInstructions,
                                    handlingUnits
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
                    createShipmentPolicy: 'NO',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
                    allowCompletingPartialPickingJob: false,
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
                "P2": { prices: [{ price: 1 }] },
                ...products,
            },
            packingInstructions: {
                "PI_P1": { lu: "LU", qtyTUsPerLU: 20, tu: "TU1", product: "P1", qtyCUsPerTU: 4 },
                "PI_P2": { lu: "LU", qtyTUsPerLU: 20, tu: "TU2", product: "P2", qtyCUsPerTU: 5 },
                ...packingInstructions,
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI_P1' },
                "HU2": { product: 'P2', warehouse: 'wh', packingInstructions: 'PI_P2' },
                ...handlingUnits,
            },
            salesOrders: {
                "SO1": {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [
                        { product: 'P1', qty: 12, piItemProduct: 'TU1' },
                        { product: 'P2', qty: 15, piItemProduct: 'TU2' },
                    ]
                }
            },
        }
    })
}

const pickHUsToPickingSlot = async ({ masterdata }) => await test.step("Pick", async () => {
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen' });
    await PickLineScanScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: '3', expectGoBackToPickingJob: false });
    await PickLineScanScreen.pickHU({ qrCode: masterdata.handlingUnits.HU2.qrCode, expectQtyEntered: '3' });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });
    await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '3 TU', qtyPicked: '3 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.complete();
    await PickingJobsListScreen.goBack();
    const { context } = await Backend.expect({
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [
                            { qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: '-', tu: 'tu11', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: '-', tu: 'tu12', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "4 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: '-', tu: 'tu13', lu: '-', processed: false, shipmentLineId: '-' },
                        ]
                    },
                    P2: {
                        qtyPicked: [
                            { qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: '-', tu: 'tu21', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: '-', tu: 'tu22', lu: '-', processed: false, shipmentLineId: '-' },
                            { qtyPicked: "5 PCE", qtyTUs: 1, qtyLUs: 0, vhuId: '-', tu: 'tu23', lu: '-', processed: false, shipmentLineId: '-' },
                        ]
                    },
                }
            }
        },
        pickingSlots: {
            [masterdata.pickingSlots.slot1.qrCode]: {
                queue: [
                    { hu: 'tu11' },
                    { hu: 'tu12' },
                    { hu: 'tu13' },
                    { hu: 'tu21' },
                    { hu: 'tu22' },
                    { hu: 'tu23' },
                ]
            }
        },
        hus: {
            tu11: { storages: { 'P1': '4 PCE' } },
            tu12: { storages: { 'P1': '4 PCE' } },
            tu13: { storages: { 'P1': '4 PCE' } },
            tu21: { storages: { 'P2': '5 PCE' } },
            tu22: { storages: { 'P2': '5 PCE' } },
            tu23: { storages: { 'P2': '5 PCE' } },
        }
    });

    return { context };
});

// noinspection JSUnusedLocalSymbols
test('Simple HU consolidate all test', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    await pickHUsToPickingSlot({ masterdata });

    await test.step("HU Consolidate & Ship", async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId })
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.consolidateAll({ pickingSlotId: masterdata.pickingSlots.slot1.id });
        await HUConsolidationJobScreen.complete();
    });
});

// noinspection JSUnusedLocalSymbols
test('Simple HU consolidate HUs one by one test', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    const { context } = await pickHUsToPickingSlot({ masterdata });
    console.log('Context: ' + JSON.stringify(context, null, 2));

    await test.step("HU Consolidate & Ship", async () => {
        await ApplicationsListScreen.expectVisible();
        await ApplicationsListScreen.startApplication('huConsolidation');
        await HUConsolidationJobsListScreen.waitForScreen();
        await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId })
        await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
        await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu11 });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu12 });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu13 });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu21 });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu22 });
        await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu23 });
        await PickingSlotScreen.goBack();
        await HUConsolidationJobScreen.complete();
    });
});

// noinspection JSUnusedLocalSymbols
test('Manual print current target label', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    const { context } = await pickHUsToPickingSlot({ masterdata });

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId })
    await HUConsolidationJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_P1.luName });
    await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });
    await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu11 });
    await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu21 }); // just to have 2 different products...
    await PickingSlotScreen.goBack();

    await HUConsolidationJobScreen.printTargetLabel();

    // await HUConsolidationJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('Consolidate to an existing LU', async ({ page }) => {
    const masterdata = await createMasterdata({
        products: {
            "P3": { prices: [{ price: 1 }] },
        },
        packingInstructions: {
            "PI_P3": { lu: "LU", qtyTUsPerLU: 20, tu: "TU3", product: "P3", qtyCUsPerTU: 10 },
        },
        handlingUnits: {
            "EXISTING_HU": { product: 'P3', warehouse: 'wh', packingInstructions: 'PI_P3' },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();

    const { context } = await pickHUsToPickingSlot({ masterdata });

    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('huConsolidation');
    await HUConsolidationJobsListScreen.waitForScreen();
    await HUConsolidationJobsListScreen.startJob({ customerLocationId: masterdata.bpartners.BP1.bpartnerLocationId })
    await HUConsolidationJobScreen.setTargetLU({ qrCode: masterdata.handlingUnits.EXISTING_HU.qrCode });
    await HUConsolidationJobScreen.clickPickingSlot({ pickingSlotId: masterdata.pickingSlots.slot1.id });
    await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu11 });
    await PickingSlotScreen.clickConsolidateHUButton({ huId: context.tu21 }); // just to have 2 different products...
    await PickingSlotScreen.goBack();

    await HUConsolidationJobScreen.complete();

    await Backend.expect({
        hus: {
            [masterdata.handlingUnits.EXISTING_HU.qrCode]: {
                storages: {
                    'P3': '200 PCE', // qty already existing before tihs aggregation
                    'P1': '4 PCE', // tu11
                    'P2': '5 PCE', // tu21
                },
            }
        }
    });
});