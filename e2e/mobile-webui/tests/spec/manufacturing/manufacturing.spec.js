import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {},
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "FINISHED_GOOD": {
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            packingInstructions: {
                "PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "FINISHED_GOOD", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 100 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 100 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'FINISHED_GOOD',
                    qty: 5,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Simple manufacturing test', async ({ page }) => {
    // === ALLURE METADATA ===
    await allure.epic('E0160: Manufacturing Execution');
    await allure.tag('F8030: MobileUI Manufacturing');
    await allure.story('Simple manufacturing workflow');
    await allure.severity('critical');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await test.step('Issue COMP1', async () => {
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP1.qrCode, expectQtyEntered: '5' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
    });

    await test.step('Issue COMP2', async () => {
        await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '10 Stk', qtyIssued: '0 Stk' });
        await ManufacturingJobScreen.clickIssueButton({ index: 2 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP2.qrCode, expectQtyEntered: '10' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '10 Stk', qtyIssued: '10 Stk' });
    });

    await test.step('Receive finished goods', async () => {
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '5 Stk', qtyReceived: '0 Stk' });
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId })
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: '5', qtyEntered: '1' });
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '5 Stk', qtyReceived: '1 Stk' });
    });

    await ManufacturingJobScreen.complete();
    
    await Backend.expect({
        title: 'HUs after completing the manufacturing order',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '1 PCE' },
                ]
            }
        },
        hus: {
            'lu1': { huStatus: 'A', storages: { 'FINISHED_GOOD': '1 PCE' } },
            'HU_COMP1': { huStatus: 'A', storages: { 'COMP1': '95 PCE' } },
            'HU_COMP2': { huStatus: 'A', storages: { 'COMP2': '90 PCE' } },
        }
    });
});

