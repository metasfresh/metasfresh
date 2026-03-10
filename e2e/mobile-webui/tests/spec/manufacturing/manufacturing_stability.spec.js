import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

const createMasterdata = async ({ finishedGoodQty = 5 } = {}) => {
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
                    qty: finishedGoodQty,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Go back from manufacturing job mid-issue, resume and complete', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Manufacturing stability - go back and resume');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await test.step('Issue COMP1 only', async () => {
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP1.qrCode, expectQtyEntered: '5' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
    });

    await test.step('Go back to jobs list (COMP2 not yet issued)', async () => {
        await ManufacturingJobScreen.goBack();
        await ManufacturingJobsListScreen.waitForScreen();
    });

    await test.step('Resume the same job', async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });
    });

    await test.step('Verify COMP1 still issued, COMP2 still not issued', async () => {
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
        await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '10 Stk', qtyIssued: '0 Stk' });
    });

    await test.step('Issue COMP2', async () => {
        await ManufacturingJobScreen.clickIssueButton({ index: 2 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP2.qrCode, expectQtyEntered: '10' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '10 Stk', qtyIssued: '10 Stk' });
    });

    await test.step('Receive finished goods and complete', async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: '5' });
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Manufacturing completed after resume',
        hus: {
            'HU_COMP1': { huStatus: 'A', storages: { 'COMP1': '95 PCE' } },
            'HU_COMP2': { huStatus: 'A', storages: { 'COMP2': '90 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Issue both components, receive partial then receive remaining', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Manufacturing stability - partial receive then complete remaining');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await test.step('Issue both components', async () => {
        await ManufacturingJobScreen.issueRawProduct({ index: 1, qrCode: masterdata.handlingUnits.HU_COMP1.qrCode, expectQtyEntered: '5' });
        await ManufacturingJobScreen.issueRawProduct({ index: 2, qrCode: masterdata.handlingUnits.HU_COMP2.qrCode, expectQtyEntered: '10' });
    });

    await test.step('Receive partial qty (2 of 5)', async () => {
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '5 Stk', qtyReceived: '0 Stk' });
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: '5', qtyEntered: '2' });
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '5 Stk', qtyReceived: '2 Stk' });
    });

    await test.step('Receive remaining qty (3 of 5) into new LU', async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: '3' });
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '5 Stk', qtyReceived: '5 Stk' });
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'All finished goods received in 2 rounds',
        hus: {
            'HU_COMP1': { huStatus: 'A', storages: { 'COMP1': '95 PCE' } },
            'HU_COMP2': { huStatus: 'A', storages: { 'COMP2': '90 PCE' } },
        }
    });
});

// noinspection JSUnusedLocalSymbols
test('Issue component, go back from issue line, re-enter and issue second component', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');
    allure.story('Manufacturing stability - navigate between issue screens');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await test.step('Enter COMP1 issue screen then go back without scanning', async () => {
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.expectVisible();
        await RawMaterialIssueLineScreen.goBack();
        // Should be back on job screen with no changes
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '0 Stk' });
    });

    await test.step('Issue COMP2 first (out of order)', async () => {
        await ManufacturingJobScreen.clickIssueButton({ index: 2 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP2.qrCode, expectQtyEntered: '10' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '10 Stk', qtyIssued: '10 Stk' });
    });

    await test.step('Then issue COMP1', async () => {
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.scanQRCode({ qrCode: masterdata.handlingUnits.HU_COMP1.qrCode, expectQtyEntered: '5' });
        await RawMaterialIssueLineScreen.goBack();
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '5 Stk', qtyIssued: '5 Stk' });
    });

    await test.step('Receive and complete', async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId });
        await MaterialReceiptLineScreen.receiveQty({ expectQtyEntered: '5' });
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        title: 'Manufacturing completed with out-of-order issue',
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { lu: 'lu1', qty: '5 PCE' },
                ]
            }
        },
        hus: {
            'lu1': { huStatus: 'A', storages: { 'FINISHED_GOOD': '5 PCE' } },
            'HU_COMP1': { huStatus: 'A', storages: { 'COMP1': '95 PCE' } },
            'HU_COMP2': { huStatus: 'A', storages: { 'COMP2': '90 PCE' } },
        }
    });
});
