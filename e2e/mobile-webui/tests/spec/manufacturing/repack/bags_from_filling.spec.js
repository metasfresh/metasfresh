import { Backend } from '../../../utils/screens/Backend';
import { test } from '../../../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
import { ReceiptReceiveTargetScreen } from '../../../utils/screens/manufacturing/receipt/ReceiptReceiveTargetScreen';
import { ReceiptNewHUScreen, VIRTUAL_TU_TARGET_TESTID } from '../../../utils/screens/manufacturing/receipt/ReceiptNewHUScreen';

// A filling order for a piece (Stk) article with catch weight receives one bag per receipt, each as its
// own bare CU (no carton, no pallet) with its own QR, weighed kg, lot and best-before date.
// Builds on receiving_without_packing_item.spec.js (which proves the "No Packing Item" target itself):
// this spec adds the per-bag attributes and the 1-Stk-per-receipt identity.
const LOT_NO = '2610-07';
const BEST_BEFORE_UI = '31.10.2028';
const BEST_BEFORE_DB = '2028-10-31';
const BAGS = [
    { id: 'bag1', weightNet: '1.012' },
    { id: 'bag2', weightNet: '0.998' },
    { id: 'bag3', weightNet: '1.050' },
];

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                manufacturing: {
                    isAllowReceiveWithoutPackingItem: true,
                },
            },
            warehouses: { 'wh': {} },
            products: {
                'COMP1': {},
                'BAG': {
                    // Stk with a Stk -> kg catch conversion at the nominal 1 kg per bag
                    uom: 'PCE',
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 1, isCatchUOMForProduct: true }],
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            // No packingInstructions for BAG: only the virtual "No Packing Item" target is on offer.
            handlingUnits: { 'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 100 } },
            manufacturingOrders: {
                'PP1': { warehouse: 'wh', product: 'BAG', qty: 3, datePromised: '2025-03-01T00:00:00.000+02:00' },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Filling order: 3 bags are received as bare 1-Stk CUs with own QR, weight, lot and best-before date', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.feature('F8034: Manufacturing Workflow Activity - Material Receipt');
    allure.tag('F8034');
    allure.story('Bags from filling as single 1-Stk units');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    for (const bag of BAGS) {
        await test.step(`Receive ${bag.id} (${bag.weightNet} kg)`, async () => {
            await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
            await MaterialReceiptLineScreen.clickReceiveTargetButton();
            await ReceiptReceiveTargetScreen.clickNewHUButton();
            await ReceiptNewHUScreen.clickTUTarget({ tuPIItemProductTestId: VIRTUAL_TU_TARGET_TESTID });
            await MaterialReceiptLineScreen.receiveQty({
                switchToManualInput: true,
                qtyEntered: 1,
                catchWeight: bag.weightNet,
                lotNo: LOT_NO,
                bestBeforeDate: BEST_BEFORE_UI,
                expectGoBackToJob: true,
            });
        });
    }

    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '3 Stk',
        qtyReceived: '3 Stk',
    });

    const attributesOf = (bag) => ({
        'WeightNet': bag.weightNet,
        'Lot-Nummer': LOT_NO,
        'HU_BestBeforeDate': BEST_BEFORE_DB,
    });
    await Backend.expect({
        title: 'Three bags were received, each as its own active bare CU with weight, lot and best-before date',
        manufacturings: {
            [jobId]: {
                receivedHUs: BAGS.map((bag) => ({ hu: bag.id, qty: '1 PCE' })),
            },
        },
        hus: Object.fromEntries(BAGS.map((bag) => [bag.id, {
            huStatus: 'A',
            huType: 'V',
            storages: { 'BAG': '1 PCE' },
            attributes: attributesOf(bag),
        }])),
    });

    // Each bag carries its own QR code.
    const qrCodes = [];
    for (const bag of BAGS) {
        qrCodes.push(await Backend.getHUQRCodeByIdentifier({ identifier: bag.id }));
    }
    for (const qrCode of qrCodes) {
        expect(qrCode).toBeTruthy();
    }
    expect(new Set(qrCodes).size).toBe(BAGS.length);

    await ManufacturingJobScreen.complete();
});
