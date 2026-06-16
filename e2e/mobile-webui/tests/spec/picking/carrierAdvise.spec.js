import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';

// Carrier advise on picking, validated against a NO-GATEWAY IsApiCarrierAdvise shipper.
// With no ShipperGateway, CarrierAdviseCommand resolves the advise locally: the carrier product
// is auto-created and named after the shipper. Goods-type/service are not set in this branch,
// so only the Carrier_Product is asserted. The carrier advise is auto-requested when the order
// (carrying the IsApiCarrierAdvise shipper) is completed, so the resolved carrier product is
// already shown in picking; the advise button re-runs the advise for the picked HUs.

const baseRequest = ({ pickTo }) => ({
    login: { user: { language: "en_US" } },
    mobileConfig: {
        picking: {
            aggregationType: "sales_order",
            allowPickingAnyCustomer: true,
            createShipmentPolicy: 'CL',
            allowPickingAnyHU: true,
            shipOnCloseLU: false,
            pickTo,
            allowCompletingPartialPickingJob: true,
        }
    },
    bpartners: { "BP1": {} },
    warehouses: { "wh": {} },
    pickingSlots: { slot1: {} },
    shippers: { "carrier": { name: 'NoGwCarrier', isApiCarrierAdvise: true } },
    products: { "P1": { price: 1 } },
    packingInstructions: {
        // LU/TU/CU all reachable: full TU = 100 CU, full LU = 20 TU.
        "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 20 },
        "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
    },
    handlingUnits: {
        "HU1": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
    },
    salesOrders: {
        "SO1": {
            bpartner: 'BP1',
            warehouse: 'wh',
            shipper: 'carrier',
            datePromised: '2025-03-01T00:00:00.000+02:00',
            // one full TU worth (100 CU) so we always pick complete TUs
            lines: [{ product: 'P1', qty: 100 }],
        }
    },
});

const expectCarrierResolvedOnSchedule = async ({ masterdata, title }) => {
    await Backend.expect({
        title,
        salesOrders: {
            'SO1': {
                carrierAdvise: {
                    P1: {
                        advisingStatus: 'CO',
                        carrierProductSet: true,
                        carrierProductName: masterdata.shippers.carrier.name,
                    }
                }
            }
        }
    });
};

const startJob = async ({ masterdata }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SO1.documentNo });
    return pickingJobId;
};

const allureMeta = (story) => {
    allure.epic('E0360: Transport (Extralogistik)');
    allure.feature('F29099: nShift Interface');
    allure.tag('F29099');
    allure.story(story);
    allure.severity('normal');
};

// noinspection JSUnusedLocalSymbols
test('Carrier advise — pick into LU', async ({ page }) => {
    allureMeta('Carrier advise resolved when picking into an LU');

    const masterdata = await Backend.createMasterdata({ language: "en_US", request: baseRequest({ pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'] }) });

    const pickingJobId = await startJob({ masterdata });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });

    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: 100 });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 TU', qtyPicked: '1 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobScreen.expectAdviseCarrierButtonVisible();
    await PickingJobScreen.clickAdviseCarrier();
    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });

    await PickingJobScreen.complete();
    await expectCarrierResolvedOnSchedule({ masterdata, title: "after complete, LU pick" });
});

// noinspection JSUnusedLocalSymbols
test('Carrier advise — pick into TU', async ({ page }) => {
    allureMeta('Carrier advise resolved when picking into a top-level TU');

    const masterdata = await Backend.createMasterdata({ language: "en_US", request: baseRequest({ pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'] }) });

    const pickingJobId = await startJob({ masterdata });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });

    await PickingJobScreen.setTargetTU({ tu: masterdata.packingInstructions.PI1.tuName });
    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: 100 });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '1 TU', qtyPicked: '1 TU', qtyPickedCatchWeight: '' });

    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobScreen.expectAdviseCarrierButtonVisible();
    await PickingJobScreen.clickAdviseCarrier();
    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });

    await PickingJobScreen.complete();
    await expectCarrierResolvedOnSchedule({ masterdata, title: "after complete, TU pick" });
});

// noinspection JSUnusedLocalSymbols
test('Carrier advise — pick into top-level CUs (CU-direct)', async ({ page }) => {
    allureMeta('Carrier advise resolved when picking directly into CUs');

    const masterdata = await Backend.createMasterdata({ language: "en_US", request: baseRequest({ pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'] }) });

    const pickingJobId = await startJob({ masterdata });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
    // No target set => pick to top-level CUs.

    await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HU1.qrCode, expectQtyEntered: 100 });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '100 Stk', qtyPicked: '100 Stk', qtyPickedCatchWeight: '' });

    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });
    await PickingJobScreen.expectAdviseCarrierButtonVisible();
    await PickingJobScreen.clickAdviseCarrier();
    await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrier.name });

    await PickingJobScreen.complete();
    await expectCarrierResolvedOnSchedule({ masterdata, title: "after complete, CU-direct pick" });
});

// noinspection JSUnusedLocalSymbols
test('Carrier advise — multiple HUs, each shipment carries its own carrier', async ({ page }) => {
    allureMeta('Carrier advise resolved per order, each with its own carrier');

    // Two orders, each with its own no-gateway IsApiCarrierAdvise shipper, picked into separate LUs.
    // Each resulting shipment (one top-level HU per order) carries its own carrier product.
    const masterdata = await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "sales_order",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU', 'TU', 'LU_CU', 'CU'],
                    allowCompletingPartialPickingJob: true,
                }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {} },
            shippers: {
                "carrierA": { name: 'NoGwCarrierA', isApiCarrierAdvise: true },
                "carrierB": { name: 'NoGwCarrierB', isApiCarrierAdvise: true },
            },
            products: { "P1": { price: 1 } },
            packingInstructions: {
                "PI1": { tu: "TU", product: "P1", qtyCUsPerTU: 100, lu: "LU", qtyTUsPerLU: 20 },
                "LU_CU": { cu: true, lu: "LU", qtyTUsPerLU: 1 },
            },
            handlingUnits: {
                "HUA": { product: 'P1', warehouse: 'wh', qty: 1000, packingInstructions: 'LU_CU' },
            },
            salesOrders: {
                "SOA": { bpartner: 'BP1', warehouse: 'wh', shipper: 'carrierA', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 100 }] },
                "SOB": { bpartner: 'BP1', warehouse: 'wh', shipper: 'carrierB', datePromised: '2025-03-01T00:00:00.000+02:00', lines: [{ product: 'P1', qty: 100 }] },
            },
        }
    });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');

    await test.step("Pick order A into its own LU", async () => {
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SOA.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SOA.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HUA.qrCode, expectQtyEntered: 100 });
        await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrierA.name });
        await PickingJobScreen.clickAdviseCarrier();
        await PickingJobScreen.complete();
    });

    await test.step("Pick order B into its own LU", async () => {
        await PickingJobsListScreen.waitForScreen();
        await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SOB.documentNo);
        await PickingJobsListScreen.startJob({ documentNo: masterdata.salesOrders.SOB.documentNo });
        await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode, expectNextScreen: 'PickLineScanScreen', gotoPickingJobScreen: true });
        await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI1.luName });
        await PickingJobScreen.pickHU({ qrCode: masterdata.handlingUnits.HUA.qrCode, expectQtyEntered: 100 });
        await PickingJobScreen.expectCarrierProductCaption({ caption: masterdata.shippers.carrierB.name });
        await PickingJobScreen.clickAdviseCarrier();
        await PickingJobScreen.complete();
    });

    await Backend.expect({
        title: "each order's shipment carries its own carrier",
        salesOrders: {
            'SOA': { carrierAdvise: { P1: { advisingStatus: 'CO', carrierProductSet: true, carrierProductName: masterdata.shippers.carrierA.name } } },
            'SOB': { carrierAdvise: { P1: { advisingStatus: 'CO', carrierProductSet: true, carrierProductName: masterdata.shippers.carrierB.name } } },
        }
    });
});
