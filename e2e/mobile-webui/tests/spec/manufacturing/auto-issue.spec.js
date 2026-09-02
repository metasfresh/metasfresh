import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { allure } from 'allure-playwright';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

// This spec validates that BOM lines configured with IssueOnlyForReceived are
// presented as read-only in the mobile UI and the Scan button is not shown.

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: {
                user: { language: 'en_US' },
            },
            mobileConfig: {},
            warehouses: {
                wh: {},
            },
            products: {
                COMP_AUTO: {},
                COMP_MANUAL: {},
                BOM: {
                    bom: {
                        lines: [
                            // Auto-issue component: the test framework supports an optional issueMethod flag
                            { product: 'COMP_AUTO', qty: 1, issueMethod: 'IssueOnlyForReceived' },
                            // Manual issue component
                            { product: 'COMP_MANUAL', qty: 1, issueMethod: 'Backflush' /* atm works exactly like `Issue` */ },
                        ],
                    },
                },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 10, tu: 'TU', product: 'BOM', qtyCUsPerTU: 2 },
            },
            handlingUnits: {
                HU_AUTO: { product: 'COMP_AUTO', warehouse: 'wh', qty: 100, sourceHU: true },
                HU_MAN: { product: 'COMP_MANUAL', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                PP1: {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Auto-issue first line hides Scan button; manual second line shows it', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0160: Manufacturing Execution');
    allure.tag('F8030: MobileUI Manufacturing');
    allure.tag('F8030');  // Standalone tag for Tags section;
    allure.story('Auto-issue BOM component behavior');
    allure.severity('normal');

    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();

    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await test.step('Check Issue Line 1: Auto-issue line => Scan button hidden and no indicators', async () => {
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '100 Stk', qtyIssued: '0 Stk', noIndicators: true });
        await ManufacturingJobScreen.clickIssueButton({ index: 1 });
        await RawMaterialIssueLineScreen.expectScanButtonVisible({ visible: false });
        await RawMaterialIssueLineScreen.goBack();
    });

    await test.step('Check Issue Line 2: Manual line (created second) => Scan button visible', async () => {
        await ManufacturingJobScreen.expectIssueButton({ index: 2, qtyToIssue: '100 Stk', qtyIssued: '0 Stk' });
        await ManufacturingJobScreen.clickIssueButton({ index: 2 });
        await RawMaterialIssueLineScreen.expectScanButtonVisible({ visible: true });
        await RawMaterialIssueLineScreen.goBack();
    });

    await test.step('Receive finished good', async () => {
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '0 Stk' });
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId })
        await MaterialReceiptLineScreen.receiveQty({
            expectQtyEntered: '100',
            qtyEntered: '1',
        });
        await ManufacturingJobScreen.waitForScreen();
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '1 Stk' });
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '100 Stk', qtyIssued: '1 Stk', noIndicators: true }); // AUTO_COMP

        await Backend.expect({
            manufacturings: {
                [jobId]: {
                    receivedHUs: [
                        { lu: 'HU_FINISHED_GOODS_1', qty: '1 PCE' },
                    ]
                }
            },
            hus: {
                'HU_FINISHED_GOODS_1': { huStatus: 'A', storages: { 'BOM': '1 PCE' } },
                'HU_AUTO': { huStatus: 'A', storages: { 'COMP_AUTO': '99 PCE' } },
                'HU_MAN': { huStatus: 'A', storages: { 'COMP_MANUAL': '1000 PCE' } },
            }
        });
    });

    await test.step('Receive finished good again', async () => {
        await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
        await MaterialReceiptLineScreen.selectNewLUTarget({ luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId })
        await MaterialReceiptLineScreen.receiveQty({
            expectQtyEntered: '99',
            qtyEntered: '9',
        });
        await ManufacturingJobScreen.waitForScreen();
        await ManufacturingJobScreen.expectReceiveButton({ index: 1, qtyToReceive: '100 Stk', qtyReceived: '10 Stk' });
        await ManufacturingJobScreen.expectIssueButton({ index: 1, qtyToIssue: '100 Stk', qtyIssued: '10 Stk', noIndicators: true }); // AUTO_COMP

        await Backend.expect({
            manufacturings: {
                [jobId]: {
                    receivedHUs: [
                        { lu: 'HU_FINISHED_GOODS_1', qty: '1 PCE' },
                        { lu: 'HU_FINISHED_GOODS_2', qty: '9 PCE' },
                    ]
                }
            },
            hus: {
                'HU_FINISHED_GOODS_1': { huStatus: 'A', storages: { 'BOM': '1 PCE' } },
                'HU_FINISHED_GOODS_2': { huStatus: 'A', storages: { 'BOM': '9 PCE' } },
                'HU_AUTO': { huStatus: 'A', storages: { 'COMP_AUTO': '90 PCE' } },
                'HU_MAN': { huStatus: 'A', storages: { 'COMP_MANUAL': '1000 PCE' } },
            }
        });
    });

    await test.step('Issue COMP_MANUAL (regression)', async () => {
        await ManufacturingJobScreen.issueRawProduct({
            index: 2,
            expectQtyEntered: 100,
            qrCode: masterdata.handlingUnits.HU_MAN.qrCode,
        });

        await Backend.expect({
            manufacturings: {
                [jobId]: {
                    receivedHUs: [
                        { lu: 'HU_FINISHED_GOODS_1', qty: '1 PCE' },
                        { lu: 'HU_FINISHED_GOODS_2', qty: '9 PCE' },
                    ]
                }
            },
            hus: {
                'HU_FINISHED_GOODS_1': { huStatus: 'A', storages: { 'BOM': '1 PCE' } },
                'HU_FINISHED_GOODS_2': { huStatus: 'A', storages: { 'BOM': '9 PCE' } },
                'HU_AUTO': { huStatus: 'A', storages: { 'COMP_AUTO': '90 PCE' } },
                'HU_MAN': { huStatus: 'A', storages: { 'COMP_MANUAL': '900 PCE' } },
            }
        });
    });
});
