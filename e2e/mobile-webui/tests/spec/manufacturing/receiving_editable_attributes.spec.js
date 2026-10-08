import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// Lot + Best-Before editability on the mobile Produktion receive dialog is ON by default,
// so the dialog offers both inputs without any config override (empty mobileConfig).
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {},
            warehouses: { 'wh': {} },
            products: {
                'COMP1': {},
                'BOM': {
                    bom: { lines: [{ product: 'COMP1', qty: 1 }] },
                },
            },
            packingInstructions: {
                'PI': { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                'HU_COMP1': { product: 'COMP1', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                'PP1': {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

const startReceiveFlow = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });

    return { jobId };
};

// noinspection JSUnusedLocalSymbols
test('Receive finished goods entering Lot + Best-Before — produced HU carries both attributes', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Editable Lot / Best-Before on the receive dialog');
    allure.severity('critical');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata);

    const lotNo = `LOT-${Date.now()}`;

    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '5',
        expectLotNoVisible: true,
        expectBestBeforeDateVisible: true,
        lotNo,
        bestBeforeDate: '23.11.2031',
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU carries the entered Lot and Best-Before',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '5 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '5 PCE' },
                attributes: {
                    'Lot-Nummer': lotNo,
                    'HU_BestBeforeDate': '2031-11-23',
                },
            },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Receive finished goods leaving Lot + Best-Before empty — no attribute is set', async ({ page }) => {
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Editable Lot / Best-Before on the receive dialog');
    allure.severity('normal');

    const masterdata = await createMasterdata();
    const { jobId } = await startReceiveFlow(masterdata);

    // Inputs are offered (default ON) but the operator leaves them empty — the produced HU
    // must NOT get a Lot / Best-Before attribute (default-ON must not fabricate values).
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '5',
        qtyEntered: '5',
        expectLotNoVisible: true,
        expectBestBeforeDateVisible: true,
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Produced HU has no Lot / Best-Before when left empty',
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '5 PCE' }],
            },
        },
        hus: {
            'lu1': {
                storages: { 'BOM': '5 PCE' },
                attributes: {
                    'Lot-Nummer': null,
                    'HU_BestBeforeDate': null,
                },
            },
        },
    });
});
