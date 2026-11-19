import { test } from "../../../playwright.config";
import { allure } from 'allure-playwright';
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { PickingJobsListScreen } from "../../utils/screens/picking/PickingJobsListScreen";
import { PickingJobScreen } from "../../utils/screens/picking/PickingJobScreen";
import { PickingJobLineScreen } from "../../utils/screens/picking/PickingJobLineScreen";
import { PickLineScanScreen } from "../../utils/screens/picking/PickLineScanScreen";
import { buildCULabelFormat, uniqueProductValue } from "../../utils/cuLabel";

const MODE_SCAN_ONCE = 'SCAN_ONCE_ENTER_QTY';
const MODE_SCAN_EACH = 'SCAN_EACH_CU';

const createMasterdata = async ({ formatName, formatNameC }) => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US" } },
            mobileConfig: {
                picking: {
                    aggregationType: "product",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    pickTo: ['TU'],
                    // profile default = Scan once, with a label format
                    cuQtyEntryMode: MODE_SCAN_ONCE,
                    cuLabelFormatName: formatName,
                    customers: [
                        // buyer A: no record at all (not listed)
                        // buyer B: record with Scan each CU
                        { customer: "custB", cuQtyEntryMode: MODE_SCAN_EACH },
                        // buyer C: a job-options record with an EMPTY mode (its own format only): the mode falls back to the profile's.
                        // (a customer listed without any CU value gets no record at all, the same path as A)
                        { customer: "custC", cuLabelFormatName: formatNameC },
                    ],
                }
            },
            customQRCodeFormats: [buildCULabelFormat({ name: formatName }), buildCULabelFormat({ name: formatNameC })],
            bpartners: { "custA": {}, "custB": {}, "custC": {} },
            warehouses: { "wh": {} },
            pickingSlots: { slot1: {}, slot2: {}, slot3: {} },
            products: {
                "P1": { price: 1, randomValue: { size: 7, isIncludeDigits: true } },
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4' },
            },
            salesOrders: {
                "SO_A": {
                    bpartner: 'custA',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 20, piItemProduct: 'P1_4CU' }]
                },
                "SO_B": {
                    bpartner: 'custB',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 24, piItemProduct: 'P1_4CU' }]
                },
                "SO_C": {
                    bpartner: 'custC',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 28, piItemProduct: 'P1_4CU' }]
                },
            },
        }
    });
};

// noinspection JSUnusedLocalSymbols
test('TC2: CU quantity entry mode per picking line (profile default, customer record, empty customer record)', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story('CU quantity entry mode per line');
    allure.severity('normal');

    const masterdata = await createMasterdata({ formatName: `cuqe-cfg-${uniqueProductValue()}`, formatNameC: `cuqe-cfg-c-${uniqueProductValue()}` });

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    // product-based job of P1: one line per customer, in the order of the orders A, B, C (20, 24, 28 CU)
    await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver: 72, productId: masterdata.products.P1.id });
    await PickingJobScreen.expectLineButton({ index: 1, qtyToPick: '20 Stk' });
    await PickingJobScreen.expectLineButton({ index: 2, qtyToPick: '24 Stk' });
    await PickingJobScreen.expectLineButton({ index: 3, qtyToPick: '28 Stk' });

    const expectedModes = [
        { index: 1, customer: 'A (no record)', slot: masterdata.pickingSlots.slot1, mode: MODE_SCAN_ONCE },
        { index: 2, customer: 'B (record Scan each CU)', slot: masterdata.pickingSlots.slot2, mode: MODE_SCAN_EACH },
        { index: 3, customer: 'C (record with empty mode)', slot: masterdata.pickingSlots.slot3, mode: MODE_SCAN_ONCE },
    ];
    for (const { index, customer, slot, mode } of expectedModes) {
        await test.step(`Line ${index}, customer ${customer}: mode ${mode}`, async () => {
            await PickingJobScreen.scanPickingSlot({ qrCode: slot.qrCode });
            await PickingJobScreen.clickLineButton({ index });
            await PickingJobLineScreen.waitForScreen();
            await PickingJobLineScreen.clickScanButton();
            await PickLineScanScreen.waitForScreen();
            await PickLineScanScreen.expectCUQtyEntryMode(mode);
            await PickLineScanScreen.goBack();
            await PickingJobLineScreen.goBack();
        });
    }
});
