import { Backend } from '../../utils/screens/Backend';
import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingSlotScanScreen } from '../../utils/screens/picking/PickingSlotScanScreen';

const createMasterdata = async ({ displayPickingSlotSuggestions } = {}) => {
        return await Backend.createMasterdata({
            language: "en_US",
            request: {
                login: {
                    user: { language: "en_US" },
                },
                mobileConfig: {
                    picking: {
                        aggregationType: "product",
                        allowPickingAnyCustomer: false,
                        createShipmentPolicy: 'CL',
                        allowPickingAnyHU: true,
                        pickTo: ['LU_TU'],
                        filterByQRCode: false,
                        anonymousPickHUsOnTheFly: false,
                        displayPickingSlotSuggestions,
                        customers: [
                            { customer: "customer1" },
                            { customer: "customer2" },
                            { customer: "customer3" },
                        ],
                    }
                },
                bpartners: {
                    "customer1": { gln: 'random' },
                    "customer2": { gln: 'random' },
                    "customer3": { gln: 'random' },
                },
                warehouses: {
                    "wh": {},
                },
                pickingSlots: {
                    slot1: {},
                    slot2: {},
                    slot3: {},
                    // Other picking slots:
                    slot101: { bpartnerLocation: 'customer1' },
                    slot102: { bpartnerLocation: 'customer1' },
                    slot103: { bpartnerLocation: 'customer2' },
                    slot104: { bpartnerLocation: 'customer2' },
                    slot105: { bpartnerLocation: 'customer3' },
                    slot106: { bpartnerLocation: 'customer3' },
                },
                products: {
                    "P1": { price: 1 },
                    "P2": { price: 1, bpartners: [{ bpartner: "customer1", cu_ean: '7617027667203' }] },
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
                        bpartner: 'customer1',
                        warehouse: 'wh',
                        datePromised: '2025-03-01T00:00:00.000+02:00',
                        lines: [
                            { product: 'P1', qty: 20, piItemProduct: 'P1_4CU' },
                            { product: 'P2', qty: 21, piItemProduct: 'P2_3CU' },
                        ]
                    },
                    "SO2": {
                        bpartner: 'customer2',
                        warehouse: 'wh',
                        datePromised: '2025-03-01T00:00:00.000+02:00',
                        lines: [
                            { product: 'P1', qty: 24, piItemProduct: 'P1_4CU' },
                            { product: 'P2', qty: 18, piItemProduct: 'P2_3CU' },
                        ]
                    },
                    "SO3": {
                        bpartner: 'customer3',
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
;

// noinspection JSUnusedLocalSymbols
test('Test NO picking slot suggestions', async ({ page }) => {
    const masterdata = await createMasterdata({ displayPickingSlotSuggestions: false });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 72 });
    await PickingJobScreen.clickPickingSlotButton();
    await PickingSlotScanScreen.expectPickingSlotButtons([]);
});

// noinspection JSUnusedLocalSymbols
test('Test picking slot suggestions', async ({ page }) => {
    const masterdata = await createMasterdata({ displayPickingSlotSuggestions: true });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();

    await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 72 });
    await PickingJobScreen.clickPickingSlotButton();
    await PickingSlotScanScreen.expectPickingSlotButtons([
        { qrCode: masterdata.pickingSlots.slot101.qrCode, bpartnerLocationId: masterdata.bpartners.customer1.bpartnerLocationId, countHUs: 0 },
        { qrCode: masterdata.pickingSlots.slot102.qrCode, bpartnerLocationId: masterdata.bpartners.customer1.bpartnerLocationId, countHUs: 0 },
        { qrCode: masterdata.pickingSlots.slot103.qrCode, bpartnerLocationId: masterdata.bpartners.customer2.bpartnerLocationId, countHUs: 0 },
        { qrCode: masterdata.pickingSlots.slot104.qrCode, bpartnerLocationId: masterdata.bpartners.customer2.bpartnerLocationId, countHUs: 0 },
        { qrCode: masterdata.pickingSlots.slot105.qrCode, bpartnerLocationId: masterdata.bpartners.customer3.bpartnerLocationId, countHUs: 0 },
        { qrCode: masterdata.pickingSlots.slot106.qrCode, bpartnerLocationId: masterdata.bpartners.customer3.bpartnerLocationId, countHUs: 0 },
    ])
});
